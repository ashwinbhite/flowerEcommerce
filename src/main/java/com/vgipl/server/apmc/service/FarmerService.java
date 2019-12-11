package com.vgipl.server.apmc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vgipl.server.apmc.dao.FarmerDao;
import com.vgipl.server.apmc.model.Farmer;

@Service
public class FarmerService {

	@Autowired
	FarmerDao farmerDao;
	
	/*
	 * public String getFarmerList(Farmer objFarmer) { // TODO Auto-generated method
	 * stub return farmerDao.getFarmerList(objFarmer); }
	 */

}
