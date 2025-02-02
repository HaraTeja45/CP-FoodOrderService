package com.cp.foodordermanagement.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.cp.foodordermanagement.bean.PartnerCusineRequestBean;
import com.cp.foodordermanagement.bean.PartnerMenuRequestBean;
import com.cp.foodordermanagement.bean.PartnerRestaurantRequestBean;
import com.cp.foodordermanagement.bean.ResponseBean;
import com.cp.foodordermanagement.helper.FOMConstants;
import com.cp.foodordermanagement.helper.FomUtil;
import com.cp.foodordermanagement.model.CusineCategory;
import com.cp.foodordermanagement.model.CusineDetails;
import com.cp.foodordermanagement.model.MenuDetails;
import com.cp.foodordermanagement.model.RestaurantDetails;
import com.cp.foodordermanagement.repository.CusineCategoryRepository;
import com.cp.foodordermanagement.repository.CusineDetailsRepository;
import com.cp.foodordermanagement.repository.MenuDetailsRepository;
import com.cp.foodordermanagement.repository.RestaurantDetailsRepository;

@Service
public class RestaurantManagementServiceImpl implements RestaurantManagementService {

	@Autowired
	private RestaurantDetailsRepository restaurantDetailsRepository;

	@Autowired
	private CusineCategoryRepository cusineCategoryRepository;

	@Autowired
	private CusineDetailsRepository cusineDetailsRepository;

	@Autowired
	private MenuDetailsRepository menuDetailsRepository;

	@Autowired
	private FomUtil fomUtil;

	@Override
	public ResponseBean registerRestaurant(PartnerRestaurantRequestBean partnerRestaurantRequestBean) {

		ResponseBean responseBean = new ResponseBean();

		if (!Objects.isNull(partnerRestaurantRequestBean)) {

			RestaurantDetails restaurantDetails = null;

			if (Objects.isNull(partnerRestaurantRequestBean.getRestaurantName())) {

				restaurantDetails = new RestaurantDetails();

				restaurantDetails.setRestaurantName(partnerRestaurantRequestBean.getRestaurantName());
				restaurantDetails.setRestaurantDescription(partnerRestaurantRequestBean.getRestaurantDescription());

				LocalTime openTime = fomUtil
						.convertStringtoLocalTime(partnerRestaurantRequestBean.getRestaurantOpenTime());
				restaurantDetails.setOpenTime(openTime);

				LocalTime closeTime = fomUtil
						.convertStringtoLocalTime(partnerRestaurantRequestBean.getRestaurantCloseTime());

				restaurantDetails.setCloseTime(closeTime);

				restaurantDetails.setIsActive(FOMConstants.IS_ACTIVE);

				if (!Objects.isNull(partnerRestaurantRequestBean.getPartnerMenuRequestBean())) {

					MenuDetails menuDetails = new MenuDetails();

					PartnerMenuRequestBean partnerMenuRequestBean = partnerRestaurantRequestBean
							.getPartnerMenuRequestBean();

					menuDetails.setMenuName(partnerMenuRequestBean.getMenuName());
					menuDetails.setMenuDescription(partnerMenuRequestBean.getMenuDescription());

					if (!CollectionUtils.isEmpty(partnerRestaurantRequestBean.getPartnerCusineRequestBeans())) {

						List<PartnerCusineRequestBean> partnerCusineRequestBeanList = partnerRestaurantRequestBean
								.getPartnerCusineRequestBeans();

						List<CusineDetails> cusineDetails = partnerCusineRequestBeanList.stream()
								.map(cusine -> prepareCusineDetails(cusine, menuDetails)).collect(Collectors.toList());

						cusineDetailsRepository.saveAll(cusineDetails);

						menuDetails.setCusineDetails(cusineDetails);
					} else {
						throw new NullPointerException();
					}

					menuDetailsRepository.save(menuDetails);

					restaurantDetails.setMenuKey(menuDetails);

				} else {
					throw new NullPointerException();
				}

				restaurantDetailsRepository.save(restaurantDetails);

			} else {

				throw new NullPointerException();
			}

		}

		return responseBean;
	}

	private CusineDetails prepareCusineDetails(PartnerCusineRequestBean cusine, MenuDetails menuDetails) {

		CusineDetails cusineDetails = null;
		if (!Objects.isNull(cusine)) {
			cusineDetails = new CusineDetails();

			CusineCategory cusineCategory = cusineCategoryRepository
					.findByCusineCatCodeAndIsActive(cusine.getCusineCode(), FOMConstants.IS_ACTIVE);
			cusineDetails.setCusineName(cusine.getCusineName());
			cusineDetails.setCusineDescription(cusine.getCusineDescription());
			cusineDetails.setCusineCategoryKey(cusineCategory);
			cusineDetails.setMenuDetails(menuDetails);
			cusineDetails.setIsActive(FOMConstants.IS_ACTIVE);

		}

		return cusineDetails;
	}

	@Override
	public ResponseBean updateRestaurant(PartnerRestaurantRequestBean partnerRestaurantRequestBean) {

		return null;
	}

}
