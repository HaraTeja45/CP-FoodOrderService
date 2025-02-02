package com.cp.foodordermanagement.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class MenuDetails {

	@Id
	private Long menuKey;

	private String menuName;

	private String menuDescription;

	@OneToMany // default fetch type is lazy for one to many
	private List<CusineDetails> cusineDetails;

	private Integer isActive;

	public Long getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(Long menuKey) {
		this.menuKey = menuKey;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuDescription() {
		return menuDescription;
	}

	public void setMenuDescription(String menuDescription) {
		this.menuDescription = menuDescription;
	}

	public List<CusineDetails> getCusineDetails() {
		return cusineDetails;
	}

	public void setCusineDetails(List<CusineDetails> cusineDetails) {
		this.cusineDetails = cusineDetails;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

}
