package com.cp.foodordermanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cusinecategory",schema = "public")
public class CusineCategory {

	@Id
	private Long cusineCategoryKey;

	private String cusineCatCode;

	private String categoryDescription;

	private Integer isActive;

	public Long getCusineCategoryKey() {
		return cusineCategoryKey;
	}

	public void setCusineCategoryKey(Long cusineCategoryKey) {
		this.cusineCategoryKey = cusineCategoryKey;
	}

	public String getCusineCatCode() {
		return cusineCatCode;
	}

	public void setCusineCatCode(String cusineCatCode) {
		this.cusineCatCode = cusineCatCode;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

}
