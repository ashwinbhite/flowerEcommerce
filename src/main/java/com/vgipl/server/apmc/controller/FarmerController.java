package com.vgipl.server.apmc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vgipl.server.apmc.dao.FarmerDao;
import com.vgipl.server.apmc.model.Farmer;
import com.vgipl.server.apmc.model.Market;
import com.vgipl.server.apmc.service.FarmerService;
import com.vgipl.server.apmc.service.MarketService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/farmer")
public class FarmerController {

	@Autowired 
	FarmerDao farmerDao;	
	
	
	@RequestMapping(value ="/getlist/{farmerName}" , method = RequestMethod.POST)
	public @ResponseBody String getFarmerList(@PathVariable String farmerName) {
		if(farmerName==null)
			return "null";
		
		System.out.println("strObj"+farmerName);
		
		return farmerDao.getFarmerList(farmerName);
	}
	
	
}
