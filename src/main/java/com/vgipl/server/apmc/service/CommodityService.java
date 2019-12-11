package com.vgipl.server.apmc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vgipl.server.apmc.dao.CommodityDao;
import com.vgipl.server.apmc.model.Commodity;
import com.vgipl.server.apmc.model.Inward;
import com.vgipl.server.apmc.model.Vendor;

@Service
public class CommodityService {

	@Autowired
	CommodityDao commodityDao;
	
	/*
	 * public String getCommodityList(Vendor objVendor) { // TODO Auto-generated
	 * method stub return commodityDao.getCommodityList(objVendor); }
	 */

	public String addToCart(Inward objInward) {
		// TODO Auto-generated method stub
		return commodityDao.addToCart(objInward);
	}

	public String getCartItem(Inward objInward) {
		// TODO Auto-generated method stub
		return commodityDao.getCartItem(objInward);
	}

}
