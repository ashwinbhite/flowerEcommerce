package com.vgipl.server.apmc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vgipl.server.apmc.dao.SubscribtionDao;
import com.vgipl.server.apmc.model.Subscribtion;
import com.vgipl.server.apmc.model.User;
import com.vgipl.server.apmc.service.SubscribtionService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/subscription")
public class SubscribitionController {

	@Autowired
	SubscribtionService subscribtionService;
	@Autowired
	SubscribtionDao subscribtionDao;
	
	@GetMapping("/getSubscriptionTypeList/{planType}")
	public String getSubscriptionTypeList(@PathVariable String planType) {
		
		return subscribtionDao.getSubscriptionTypeList(planType);
		
	}
	
	@RequestMapping(value="/subscriptionEntry",method=RequestMethod.POST)
	public @ResponseBody String subscriptionEntry(@RequestBody Subscribtion subscribtion) {
		if(subscribtion==null)
			return "No data found";
		
		return subscribtionService.subscribtionEntry(subscribtion);
	}
	
	
	@GetMapping("/mysubcsriptions/{custId}")
	public String getUsersubcsriptions(@PathVariable String custId) {
		
		return subscribtionDao.getUsersubcsriptions(custId);
		
	}
	
	
}
