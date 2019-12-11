package com.vgipl.server.apmc.model;

public class UserImageData {

	private String strImageData;
    private String deliveryBoyId;
    private String documentType;
    
    
	public String getDeliveryBoyId() {
		return deliveryBoyId;
	}
	public void setDeliveryBoyId(String deliveryBoyId) {
		this.deliveryBoyId = deliveryBoyId;
	}
	public String getStrImageData() {
		return strImageData;
	}
	public void setStrImageData(String strImageData) {
		this.strImageData = strImageData;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
}
