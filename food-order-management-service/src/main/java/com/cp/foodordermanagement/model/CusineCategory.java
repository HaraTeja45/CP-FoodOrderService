package com.cp.foodordermanagement.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cusinecategory",schema = "public")
public class CusineCategory {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cusineCategoryKey;

	private String cusineCatCode;

	private String categoryDescription;

	private Integer isActive;
	
	@OneToMany(mappedBy = "cusineCategory")
	private List<CusineDetails> cusineDetails;

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

	public List<CusineDetails> getCusineDetails() {
		return cusineDetails;
	}

	public void setCusineDetails(List<CusineDetails> cusineDetails) {
		this.cusineDetails = cusineDetails;
	}

}
