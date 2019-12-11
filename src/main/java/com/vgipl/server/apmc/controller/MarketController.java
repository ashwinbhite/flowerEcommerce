package com.vgipl.server.apmc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vgipl.server.apmc.model.Market;
import com.vgipl.server.apmc.model.User;
import com.vgipl.server.apmc.service.MarketService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/market")
public class MarketController {
	
	@Autowired 
	MarketService marketservice;
	
	
	@RequestMapping(value ="/getlist" , method = RequestMethod.POST)
	public @ResponseBody String getmarketList(@RequestBody Market strObj) {
		if(strObj==null)
			return "null";
		
		System.out.println(strObj.toString());
		System.out.println("strObj"+strObj.getCurrentDay());
		
		return marketservice.getmarketList(strObj);
	}

	@RequestMapping(value ="/getVendorlist" , method = RequestMethod.POST)
	public @ResponseBody String getVendorList(@RequestBody Market objMkt) {
		if(objMkt==null)
			return "null";
		
		System.out.println(objMkt.toString());
		
		return marketservice.getVendorList(objMkt);
	}
}
