package com.cp.foodordermanagement.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

		try {

			RestaurantDetails restaurantDetails = null;

			restaurantDetails = new RestaurantDetails();

			restaurantDetails.setRestaurantName(partnerRestaurantRequestBean.getRestaurantName());
			restaurantDetails.setRestaurantDescription(partnerRestaurantRequestBean.getRestaurantDescription());

			LocalTime openTime = fomUtil.convertStringtoLocalTime(partnerRestaurantRequestBean.getRestaurantOpenTime());
			restaurantDetails.setOpenTime(openTime);

			LocalTime closeTime = fomUtil
					.convertStringtoLocalTime(partnerRestaurantRequestBean.getRestaurantCloseTime());

			restaurantDetails.setCloseTime(closeTime);

			restaurantDetails.setIsActive(FOMConstants.IS_ACTIVE);

			MenuDetails menuDetails = saveMenuDetails(partnerRestaurantRequestBean);

			restaurantDetails.setMenuKey(menuDetails);

			restaurantDetailsRepository.save(restaurantDetails);
		} catch (Exception e) {

			throw e;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("message", "Successfully registered restaurent details");
		responseBean.setStatus("Success");

		responseBean.setPayload(jsonObject);

		return responseBean;
	}

	private MenuDetails saveMenuDetails(PartnerRestaurantRequestBean partnerRestaurantRequestBean) {
		MenuDetails menuDetails = new MenuDetails();

		PartnerMenuRequestBean partnerMenuRequestBean = partnerRestaurantRequestBean.getPartnerMenuRequestBean();

		menuDetails.setMenuName(partnerMenuRequestBean.getMenuName());
		menuDetails.setMenuDescription(partnerMenuRequestBean.getMenuDescription());

		List<CusineDetails> cusineDetails = saveCusineDetails(partnerRestaurantRequestBean, menuDetails);

		menuDetails.setCusineDetails(cusineDetails);

		menuDetailsRepository.save(menuDetails);
		return menuDetails;
	}

	private List<CusineDetails> saveCusineDetails(PartnerRestaurantRequestBean partnerRestaurantRequestBean,
			MenuDetails menuDetails) {
		List<PartnerCusineRequestBean> partnerCusineRequestBeanList = partnerRestaurantRequestBean
				.getPartnerCusineRequestBeans();

		List<CusineDetails> cusineDetails = partnerCusineRequestBeanList.stream()
				.map(cusine -> prepareCusineDetails(cusine, menuDetails)).collect(Collectors.toList());

		cusineDetailsRepository.saveAll(cusineDetails);
		return cusineDetails;
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

		ResponseBean responseBean = new ResponseBean();

		try {
			RestaurantDetails existingRestaurantDetails = restaurantDetailsRepository
					.findByRestaurantNameAndBranchNameAndIsActive(partnerRestaurantRequestBean.getRestaurantName(),
							partnerRestaurantRequestBean.getBranch(), FOMConstants.IS_ACTIVE);

			MenuDetails menuDetails = existingRestaurantDetails.getMenuKey();

			menuDetails.setMenuName(partnerRestaurantRequestBean.getPartnerMenuRequestBean().getMenuName());
			menuDetails
					.setMenuDescription(partnerRestaurantRequestBean.getPartnerMenuRequestBean().getMenuDescription());

			List<CusineDetails> cusineDetails = menuDetails.getCusineDetails();

			List<PartnerCusineRequestBean> partnerCusineRequestBeans = partnerRestaurantRequestBean
					.getPartnerCusineRequestBeans();

			Map<String, PartnerCusineRequestBean> cusineNameToBeanMap = partnerCusineRequestBeans.stream()
					.collect(Collectors.toMap(bean -> bean.getCusineName().toLowerCase(), bean -> bean));

			List<CusineDetails> updateCusineDetails = cusineDetails.stream()
					.filter(cusineDet -> cusineNameToBeanMap.containsKey(cusineDet.getCusineName().toLowerCase()))
					.map(cusineDet -> updateCusineDetails(cusineDet,
							cusineNameToBeanMap.get(cusineDet.getCusineName().toLowerCase())))
					.collect(Collectors.toList());

			cusineDetailsRepository.saveAll(updateCusineDetails);

			menuDetails.setCusineDetails(updateCusineDetails);
		} catch (Exception e) {
			throw e;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("message", "Successfully registered restaurent details");
		responseBean.setStatus("Success");

		responseBean.setPayload(jsonObject);

		return responseBean;
	}

	private CusineDetails updateCusineDetails(CusineDetails cusine, PartnerCusineRequestBean partnerCusineRequestBean) {

		cusine.setCusineDescription(partnerCusineRequestBean.getCusineDescription());
		cusine.setCusineName(partnerCusineRequestBean.getCusineName());
		cusine.setIsActive(partnerCusineRequestBean.getStatus().equalsIgnoreCase("In-Stock") ? FOMConstants.IS_ACTIVE
				: FOMConstants.IN_ACTIVE);

		return cusine;
	}

}
