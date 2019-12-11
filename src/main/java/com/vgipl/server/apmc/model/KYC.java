package com.vgipl.server.apmc.model;

public class KYC {

	private String flag;
	private String fullName;
	private String fathersName ;
	private String gender;
	private String maritalStat;
	private String dob;
    private String panNumber;
    private String adharNmbr;
    private String mobileNo ;
	private String resAddressId;
	private String permAddressId;
	private boolean isActive;
	private String custType;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getFathersName() {
		return fathersName;
	}
	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMaritalStat() {
		return maritalStat;
	}
	public void setMaritalStat(String maritalStat) {
		this.maritalStat = maritalStat;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public String getAdharNmbr() {
		return adharNmbr;
	}
	public void setAdharNmbr(String adharNmbr) {
		this.adharNmbr = adharNmbr;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getResAddressId() {
		return resAddressId;
	}
	public void setResAddressId(String resAddressId) {
		this.resAddressId = resAddressId;
	}
	public String getPermAddressId() {
		return permAddressId;
	}
	public void setPermAddressId(String permAddressId) {
		this.permAddressId = permAddressId;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getRessidentialAddress() {
		return ressidentialAddress;
	}
	public void setRessidentialAddress(String ressidentialAddress) {
		this.ressidentialAddress = ressidentialAddress;
	}
	public String getPermanantAddress() {
		return permanantAddress;
	}
	public void setPermanantAddress(String permanantAddress) {
		this.permanantAddress = permanantAddress;
	}
	private String custId;
	private String ressidentialAddress;
	private String permanantAddress;
	
	
}
