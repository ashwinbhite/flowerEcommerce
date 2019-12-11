package com.vgipl.server.apmc.model;

public class Commodity {

	private String commodityId;
	private String commodityName;
	private String rate;
	private String qty;
	private String discount;
	private String itemTotal;
	private int finalTotal;
	
	public String getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	public int getFinalTotal() {
		return finalTotal;
	}
	public void setFinalTotal(int finalTotal) {
		this.finalTotal = finalTotal;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getItemTotal() {
		return itemTotal;
	}
	public void setItemTotal(String itemTotal) {
		this.itemTotal = itemTotal;
	}
	
}
