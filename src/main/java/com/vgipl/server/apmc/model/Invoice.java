package com.vgipl.server.apmc.model;

import java.sql.Date;

public class Invoice {

	private String invoiceNumber;
	private String orderId;
	private String invoiceDate;
	private String deliverySlot;
	private String invoiceAmt;
	private int itemCount;
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
	public String getDeliverySlot() {
		return deliverySlot;
	}
	public void setDeliverySlot(String deliverySlot) {
		this.deliverySlot = deliverySlot;
	}
	public String getInvoiceAmt() {
		return invoiceAmt;
	}
	public void setInvoiceAmt(String invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	
	
}
