package com.vgipl.server.apmc.model;

public class Help {

	private String category;
	private String productName;
	private String unit;
	private String unitId;
	private String hindiName;
	private String regionalName;
	private String additionalRate;
	private String conversionFactor;
	private String cgstPercentage;
	private String sgstPercentage;
	private String igstPercentage;
	private String gstApplicable;
	
	
	
	
	public String getCgstPercentage() {
		return cgstPercentage;
	}
	public void setCgstPercentage(String cgstPercentage) {
		this.cgstPercentage = cgstPercentage;
	}
	public String getSgstPercentage() {
		return sgstPercentage;
	}
	public void setSgstPercentage(String sgstPercentage) {
		this.sgstPercentage = sgstPercentage;
	}
	public String getIgstPercentage() {
		return igstPercentage;
	}
	public void setIgstPercentage(String igstPercentage) {
		this.igstPercentage = igstPercentage;
	}
	public String getGstApplicable() {
		return gstApplicable;
	}
	public void setGstApplicable(String gstApplicable) {
		this.gstApplicable = gstApplicable;
	}
	
	
	
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getHindiName() {
		return hindiName;
	}
	public void setHindiName(String hindiName) {
		this.hindiName = hindiName;
	}
	public String getRegionalName() {
		return regionalName;
	}
	public void setRegionalName(String regionalName) {
		this.regionalName = regionalName;
	}
	public String getAdditionalRate() {
		return additionalRate;
	}
	public void setAdditionalRate(String additionalRate) {
		this.additionalRate = additionalRate;
	}
	public String getConversionFactor() {
		return conversionFactor;
	}
	public void setConversionFactor(String conversionFactor) {
		this.conversionFactor = conversionFactor;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
