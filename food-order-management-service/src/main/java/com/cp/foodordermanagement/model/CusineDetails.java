package com.cp.foodordermanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cusinedetails", schema = "public")
public class CusineDetails {

	@Id
	private Long cusineKey;

	private String cusineName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menuKey", nullable = false)
	private MenuDetails menuDetails;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cusineCategoryKey", nullable = false)
	private CusineCategory cusineCategory;

	private String cusineDescription;

	private String cusineRating;

	private Integer isActive;

	public Long getCusineKey() {
		return cusineKey;
	}

	public void setCusineKey(Long cusineKey) {
		this.cusineKey = cusineKey;
	}

	public String getCusineName() {
		return cusineName;
	}

	public void setCusineName(String cusineName) {
		this.cusineName = cusineName;
	}

	public String getCusineRating() {
		return cusineRating;
	}

	public void setCusineRating(String cusineRating) {
		this.cusineRating = cusineRating;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public CusineCategory getCusineCategory() {
		return cusineCategory;
	}

	public void setCusineCategory(CusineCategory cusineCategory) {
		this.cusineCategory = cusineCategory;
	}

	public MenuDetails getMenuDetails() {
		return menuDetails;
	}

	public void setMenuDetails(MenuDetails menuDetails) {
		this.menuDetails = menuDetails;
	}

	public String getCusineDescription() {
		return cusineDescription;
	}

	public void setCusineDescription(String cusineDescription) {
		this.cusineDescription = cusineDescription;
	}
	
	

}
