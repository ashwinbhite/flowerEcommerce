package com.vgipl.server.apmc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vgipl.server.apmc.dao.CommodityDao;
import com.vgipl.server.apmc.model.Inward;
import com.vgipl.server.apmc.service.CommodityService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/commodity")
public class CommodityController {
	
	@Autowired
	CommodityService commodityservice;
	
	@Autowired
	CommodityDao commoditydao;

	@RequestMapping(value ="/getlist/{vendorId}" , method = RequestMethod.GET)
	public @ResponseBody String getCommodityList(@PathVariable String vendorId) {
		if(vendorId==null)
			return "null";
	
		return commoditydao.getCommodityList(vendorId);
	}
	
	@RequestMapping(value ="/addtoCart" , method = RequestMethod.POST)
	public @ResponseBody String addToCart(@RequestBody(required=true) Inward objInward) {
		if(objInward==null)
			return "null";
		
		System.out.println(objInward.getMarketId());
		
		return commodityservice.addToCart(objInward);
	}
	
	
	@RequestMapping(value ="/getCartItem" , method = RequestMethod.POST)
	public @ResponseBody String getCartItem(@RequestBody Inward objInward) {
		if(objInward==null)
			return "null";
		
		System.out.println(objInward.getCustomerId());
		
		return commodityservice.getCartItem(objInward);
	}
	
	
	@DeleteMapping("/deleteCartItem/{cartId}/{customerId}")
	public String deleteCartItem(@PathVariable String cartId,@PathVariable String customerId) {
		
		return commoditydao.deleteCartItem(cartId,customerId);
	}
	
	
}
