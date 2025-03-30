package com.cp.foodordermanagement.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cp.foodordermanagement.bean.OrderBean;
import com.cp.foodordermanagement.bean.OrderRequestBean;
import com.cp.foodordermanagement.bean.PaymentRequestBean;
import com.cp.foodordermanagement.bean.PaymentResponse;
import com.cp.foodordermanagement.bean.ResponseBean;
import com.cp.foodordermanagement.customexception.FoodOrderManagementServiceException;
import com.cp.foodordermanagement.helper.FOMConstants;
import com.cp.foodordermanagement.helper.FOMHelper;
import com.cp.foodordermanagement.model.CusineDetails;
import com.cp.foodordermanagement.model.MasterOrderDetails;
import com.cp.foodordermanagement.model.MenuDetails;
import com.cp.foodordermanagement.model.OrderDetails;
import com.cp.foodordermanagement.model.RestaurantDetails;
import com.cp.foodordermanagement.repository.CusineDetailsRepository;
import com.cp.foodordermanagement.repository.MasterOrderDetailsRepository;
import com.cp.foodordermanagement.repository.MenuDetailsRepository;
import com.cp.foodordermanagement.repository.OrderDetailsRepository;
import com.cp.foodordermanagement.repository.RestaurantDetailsRepository;
import com.logging.CommonLoggingUtil;

@Service
public class OrderServiceImpl implements OrderService {

	private static final String CLASS_NAME = OrderServiceImpl.class.getCanonicalName();

	@Autowired
	private RestaurantDetailsRepository restaurantDetailsRepository;

	@Autowired
	private MasterOrderDetailsRepository masterOrderDetailsRepository;

	@Autowired
	private MenuDetailsRepository menuDetailsRepository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private CusineDetailsRepository cusineDetailsRepository;

	@Autowired
	private FOMHelper fomHelper;

	@Autowired
	private CommonLoggingUtil logger;

	@Value("${api.paymentservice.proceedpayment}")
	private String proceedPaymentUrl;

	@Override
	public ResponseBean fetchOrderDetails(String customerId) {

		logger.debug(CLASS_NAME, "Service", "inside fetchOrderDetails for customer id:" + customerId);

		ResponseBean responseBean = new ResponseBean();

		List<OrderBean> orderBeanList = new ArrayList<>();

		try {
			List<OrderDetails> orderDetails = orderDetailsRepository
					.findByOrderedByAndIsActive(Long.valueOf(customerId), FOMConstants.IS_ACTIVE);

			for (OrderDetails orderDets : orderDetails) {

				OrderBean orderBean = new OrderBean();

				RestaurantDetails restaurantDetails = restaurantDetailsRepository
						.findByRestaurantKeyAndIsActive(orderDets.getRestaurantKey(), FOMConstants.IS_ACTIVE);

				CusineDetails cusineDetails = cusineDetailsRepository
						.findByCusineKeyAndIsActive(orderDets.getCusineKey(), FOMConstants.IS_ACTIVE);
				orderBean.setPaymentStatus(orderDets.getStatus());
				orderBean.setPrice(orderDets.getPrice());

				if (cusineDetails != null) {
					orderBean.setCusineName(cusineDetails.getCusineName());
				}

				if (restaurantDetails != null) {
					orderBean.setRestaurantName(restaurantDetails.getRestaurantName());
					orderBean.setRestaurantBranchName(restaurantDetails.getBranchName());
				}

				orderBeanList.add(orderBean);

			}
			responseBean.setPayload(orderBeanList);
			responseBean.setStatus(FOMConstants.SUCCESS);
		} catch (Exception e) {

			logger.debug(CLASS_NAME, "Service", "error occured in fetchOrderDetails for customer id:" + customerId);
			throw new FoodOrderManagementServiceException("500", "Technical error occured while fetchOrderDetails");
		}

		logger.debug(CLASS_NAME, "Service", "End fetchOrderDetails for customer id:" + customerId);
		return responseBean;
	}

	@Override
	public ResponseBean placeOrder(OrderRequestBean orderRequestBean) {

		logger.debug(CLASS_NAME, "Service", "inside placeOrder");

		ResponseBean responseBean = new ResponseBean();

		try {
			if (!Objects.isNull(orderRequestBean)) {

				List<OrderDetails> orderDetailList = new ArrayList<>();

				MasterOrderDetails masterOrderDetails = new MasterOrderDetails();

				masterOrderDetails.setIsactive(FOMConstants.IS_ACTIVE);
				masterOrderDetails.setLstUpdatedTime(new Timestamp(new Date().getTime()));
				masterOrderDetails.setCreatedTime(new Timestamp(new Date().getTime()));
				masterOrderDetails.setOrderStatus(FOMConstants.ACTIVE_STATUS);
				masterOrderDetails.setCustomerId(orderRequestBean.getCustomerId());

				for (OrderBean order : orderRequestBean.getOrderBeans()) {

					RestaurantDetails restaurantDetails = restaurantDetailsRepository
							.findByRestaurantNameAndBranchNameAndIsActive(order.getRestaurantName(),
									order.getRestaurantBranchName(), FOMConstants.IS_ACTIVE);

					if (!Objects.isNull(restaurantDetails)) {
						MenuDetails menuDetails = menuDetailsRepository.findByMenuKeyAndIsActive(
								restaurantDetails.getMenuDetails().getMenuKey(), FOMConstants.IS_ACTIVE);
						List<CusineDetails> restaurantCusineDetails = menuDetails.getCusineDetails();
						CusineDetails cusineDetails = restaurantCusineDetails.stream()
								.filter(resCusine -> resCusine.getCusineName().equalsIgnoreCase(order.getCusineName())
										&& resCusine.getIsActive().equals(FOMConstants.IS_ACTIVE))
								.findFirst().orElse(null);
						if (!Objects.isNull(cusineDetails)) {

							OrderDetails orderDetails = prepareOrderDetails(masterOrderDetails, order,
									restaurantDetails, cusineDetails);

							orderDetailList.add(orderDetails);

						} else {

							throw new FoodOrderManagementServiceException("500",
									"Technical error occured while placeOrder");
						}
					} else {

						logger.debug(CLASS_NAME, "Service",
								"Error occured in placeOrder as restaurant details are null or empty");
						throw new FoodOrderManagementServiceException("400", "restaurant details cannot be null");
					}

				}

				if (!orderDetailList.isEmpty()) {
					masterOrderDetails.setOrderDetails(orderDetailList);

					PaymentRequestBean paymentRequest = preparePaymentRequest(orderRequestBean);

					PaymentResponse paymentResponse = proceedForPayment(paymentRequest);

					if (paymentResponse != null) {

						masterOrderDetails.setTransactionId(paymentResponse.getTransactionId().toString());
						masterOrderDetails.setOrderStatus(paymentResponse.getStatus());

					}

					masterOrderDetailsRepository.save(masterOrderDetails);
				}

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("message", "Order placed Successfully");
				responseBean.setStatus("Success");
				responseBean.setPayload(jsonObject);

			} else {

				logger.debug(CLASS_NAME, "Service", "Error occured in placeOrder as order request in null or empty");
				throw new FoodOrderManagementServiceException("400", "Order request cannot be null");
			}

		} catch (FoodOrderManagementServiceException e) {
			throw e;
		}

		catch (Exception e) {

			logger.debug(CLASS_NAME, "Service", "Error occured in placeOrder");

			throw new FoodOrderManagementServiceException("500", "Technical error occured while fetchOrderDetails");
		}

		logger.debug(CLASS_NAME, "Service", "inside placeOrder");

		return responseBean;
	}

	private PaymentResponse proceedForPayment(PaymentRequestBean paymentRequest) {

		PaymentResponse paymentResponse = null;

		JSONObject requestJSON = new JSONObject(paymentRequest);

		ResponseEntity<?> responseEntity = fomHelper.executeRestCall(proceedPaymentUrl, HttpMethod.POST,
				ResponseBean.class, null, requestJSON.toString(), null);

		if (responseEntity != null && responseEntity.getBody() != null
				&& responseEntity.getStatusCode().equals(HttpStatus.OK)) {

			ResponseBean responseBean = (ResponseBean) responseEntity.getBody();

			if (responseBean.getPayload() != null) {
				paymentResponse = (PaymentResponse) responseBean.getPayload();
			}

		}

		return paymentResponse;
	}

	private PaymentRequestBean preparePaymentRequest(OrderRequestBean orderRequestBean) {

		PaymentRequestBean paymentRequestBean = null;

		BigDecimal totalPrice = orderRequestBean.getOrderBeans().stream().map(OrderBean::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		if (totalPrice != null) {
			paymentRequestBean = new PaymentRequestBean();
			paymentRequestBean.setCustomerId(orderRequestBean.getCustomerId());
			paymentRequestBean.setPaymentAmount(totalPrice);
			paymentRequestBean.setPaymentMethod(orderRequestBean.getPaymentMode());
			paymentRequestBean.setRewardPoints(orderRequestBean.getRewardsPoints());

		}

		return paymentRequestBean;

	}

	private OrderDetails prepareOrderDetails(MasterOrderDetails masterOrderDetails, OrderBean order,
			RestaurantDetails restaurantDetails, CusineDetails cusineDetails) {

		logger.debug(CLASS_NAME, "Service", "inside prepareOrderDetails");
		OrderDetails orderDetails = new OrderDetails();

		orderDetails.setCusineKey(cusineDetails.getCusineKey());
		orderDetails.setPrice(order.getPrice());
		orderDetails.setRestaurantKey(restaurantDetails.getRestaurantKey());
		orderDetails.setMasterOrderDetails(masterOrderDetails);
		orderDetails.setStatus(FOMConstants.ACTIVE_STATUS);
		orderDetails.setOrderedBy(1l);// need to provide customer id
		orderDetails.setOrderCreateddateTime(new Timestamp(new Date().getTime()));
		orderDetails.setLstUpdatedDateTime(new Timestamp(new Date().getTime()));
		orderDetails.setIsActive(FOMConstants.IS_ACTIVE);

		logger.debug(CLASS_NAME, "Service", "end prepareOrderDetails");
		return orderDetails;
	}

}
