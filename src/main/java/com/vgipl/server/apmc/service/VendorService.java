package com.vgipl.server.apmc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vgipl.server.apmc.dao.VendorDao;
import com.vgipl.server.apmc.model.DeliveryAgency;
import com.vgipl.server.apmc.model.DeliveryBoy;
import com.vgipl.server.apmc.model.Help;
import com.vgipl.server.apmc.model.Inward;
import com.vgipl.server.apmc.model.KYC;
import com.vgipl.server.apmc.model.Vendor;

@Service
public class VendorService {

	@Autowired
	VendorDao vendorDao;
	
	public String insertProduct(Help objHelp) {
		// TODO Auto-generated method stub
		
		return vendorDao.insertProduct(objHelp);
	}

	public String inwardEntry(Inward objInward) {
		// TODO Auto-generated method stub
		return vendorDao.inwardEntry(objInward);
	}



	public String getproductAttribute(Inward objInward) {
		// TODO Auto-generated method stub
		return vendorDao.getproductAttribute(objInward);
	}

	public String getCompleteStockList() {
		// TODO Auto-generated method stub
		return vendorDao.getCompleteStockList();
	}

	public String getOrderResport(String vendorId) {
		// TODO Auto-generated method stub
		/*
		 * String startDate,endDate; startDate = fromDate.replace("-", "/"); endDate =
		 * toDate.replace("-", "/"); System.out.println(startDate+"DATE"+endDate);
		 */
		
		return vendorDao.getOrderResport(vendorId);
	}

	public String createDeliveryBoy(DeliveryBoy objDeliveryBoy) {
		// TODO Auto-generated method stub
		return vendorDao.createDeliveryBoy(objDeliveryBoy);
	}

	public String createDeliveryAgency(DeliveryAgency objDeliveryAgency) {
		// TODO Auto-generated method stub
		return vendorDao.createDeliveryAgency(objDeliveryAgency);
	}

	public String postKycDetails(KYC objKyc) {
		// TODO Auto-generated method stub
		return vendorDao.postKycDetails(objKyc);
	}

	public String insertNewAttribute(String attribute) {
		// TODO Auto-generated method stub
		return vendorDao.insertNewAttribute(attribute);
	}

	public String assignAttributeToProduct(String attribute) {
		// TODO Auto-generated method stub
		return vendorDao.assignAttributeToProduct(attribute);
	}
	
	public String updateOrderStatus(String orderId,String statusFlag,String statusRemarks) {
		// TODO Auto-generated method stub
		return vendorDao.updateOrderStatus(orderId,statusFlag,statusRemarks);
	}
	
	

	public String getInwardByProdId(Inward objInward) {
		// TODO Auto-generated method stub
		
		return vendorDao.getInwardByProdId(objInward);
		
	}
	
	
	public String getAttrwiseStock(Inward objInward) {
		// TODO Auto-generated method stub
		
		return vendorDao.getATtrwiseStock(objInward);
		
	}
	
	

	public String postGradewiseStock(Inward objInward) {
		// TODO Auto-generated method stub
		return vendorDao.postGradewiseStock(objInward);
	}

	public String verifyDeliveryBoy(String deliveryBoyId) {
		// TODO Auto-generated method stub
		return vendorDao.verifyDeliveryBoy(deliveryBoyId);
	}

	public String getStorewiseStockWithAdd(String marketId) {
		
		return vendorDao.getStorewiseStockWithAdd(marketId);

	}	
}
