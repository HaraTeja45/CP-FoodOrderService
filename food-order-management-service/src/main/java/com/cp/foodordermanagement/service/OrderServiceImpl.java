package com.cp.foodordermanagement.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cp.foodordermanagement.bean.OrderBean;
import com.cp.foodordermanagement.bean.OrderRequestBean;
import com.cp.foodordermanagement.bean.ResponseBean;
import com.cp.foodordermanagement.customexception.FoodOrderManagementServiceException;
import com.cp.foodordermanagement.helper.FOMConstants;
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

@Service
public class OrderServiceImpl implements OrderService {

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

	@Override
	public ResponseBean fetchOrderDetails(String customerId) {

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
				orderBean.setPrice(orderDets.getPrice() != null ? orderDets.getPrice().toString() : null);

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
			throw new FoodOrderManagementServiceException("500", "Technical error occured while fetchOrderDetails");
		}
		return responseBean;
	}

	@Override
	public ResponseBean placeOrder(OrderRequestBean orderRequestBean) {

		ResponseBean responseBean = new ResponseBean();

		try {
			if (!Objects.isNull(orderRequestBean)) {

				List<OrderDetails> orderDetailList = new ArrayList<>();

				MasterOrderDetails masterOrderDetails = new MasterOrderDetails();

				masterOrderDetails.setIsactive(FOMConstants.IS_ACTIVE);
				masterOrderDetails.setLstUpdatedTime(new Timestamp(new Date().getTime()));
				masterOrderDetails.setCreatedTime(new Timestamp(new Date().getTime()));
				masterOrderDetails.setOrderStatus(FOMConstants.ACTIVE_STATUS);

				for (OrderBean order : orderRequestBean.getOrderBeans()) {

					RestaurantDetails restaurantDetails = restaurantDetailsRepository
							.findByRestaurantNameAndBranchNameAndIsActive(order.getRestaurantName(),
									order.getRestaurantBranchName(), FOMConstants.IS_ACTIVE);

					MenuDetails menuDetails = menuDetailsRepository.findByMenuKeyAndIsActive(
							restaurantDetails.getMenuDetails().getMenuKey(), FOMConstants.IS_ACTIVE);

					List<CusineDetails> restaurantCusineDetails = menuDetails.getCusineDetails();

					CusineDetails cusineDetails = restaurantCusineDetails.stream()
							.filter(resCusine -> resCusine.getCusineName().equalsIgnoreCase(order.getCusineName())
									&& resCusine.getIsActive().equals(FOMConstants.IS_ACTIVE))
							.findFirst().orElse(null);

					if (!Objects.isNull(cusineDetails)) {

						OrderDetails orderDetails = prepareOrderDetails(masterOrderDetails, order, restaurantDetails,
								cusineDetails);

						orderDetailList.add(orderDetails);

					} else {

						throw new FoodOrderManagementServiceException("500",
								"Technical error occured while placeOrder");
					}

				}

				if (!orderDetailList.isEmpty()) {
					masterOrderDetailsRepository.save(masterOrderDetails);
				}

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("message", "Order placed Successfully");
				responseBean.setStatus("Success");
				responseBean.setPayload(jsonObject);

			} else {
				throw new Exception();
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return responseBean;
	}

	private OrderDetails prepareOrderDetails(MasterOrderDetails masterOrderDetails, OrderBean order,
			RestaurantDetails restaurantDetails, CusineDetails cusineDetails) {
		OrderDetails orderDetails = new OrderDetails();

		orderDetails.setCusineKey(cusineDetails.getCusineKey());
		orderDetails.setPrice(new BigDecimal(order.getPrice()));
		orderDetails.setRestaurantKey(restaurantDetails.getRestaurantKey());
		// orderDetails.setMasterOrderDetails(masterOrderDetails);
		orderDetails.setStatus(FOMConstants.ACTIVE_STATUS);
		orderDetails.setOrderedBy(1l);// need to provide customer id
		orderDetails.setOrderCreateddateTime(new Timestamp(new Date().getTime()));
		orderDetails.setLstUpdatedDateTime(new Timestamp(new Date().getTime()));
		orderDetails.setIsActive(FOMConstants.IS_ACTIVE);
		return orderDetails;
	}

}
