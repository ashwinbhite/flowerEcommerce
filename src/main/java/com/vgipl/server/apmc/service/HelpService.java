package com.vgipl.server.apmc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vgipl.server.apmc.dao.HelpDao;
import com.vgipl.server.apmc.model.Category;

@Service
public class HelpService {

	@Autowired
	HelpDao helpDao;
	
	public String getCategoryList() {
		// TODO Auto-generated method stub
		return helpDao.getCategoryList();
	}

	/*
	 * public String getProductList(Category objCategory) { // TODO Auto-generated
	 * method stub return helpDao.getProductList(objCategory); }
	 */

	public String getUnitList(String prodId) {
		// TODO Auto-generated method stub
		return helpDao.getUnitList(prodId);
	}

	public String getdate() {
		return helpDao.getdate();
	}

	public String getUnitListForNewProd() {
		// TODO Auto-generated method stub
		return helpDao.getUnitListForNewProd();
	}

}
