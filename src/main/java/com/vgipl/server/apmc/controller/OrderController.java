package com.vgipl.server.apmc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vgipl.server.apmc.model.Address;
import com.vgipl.server.apmc.model.Inward;
import com.vgipl.server.apmc.model.Vendor;
import com.vgipl.server.apmc.service.OrderService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/order")
public class OrderController {
	

	@Autowired
	OrderService orderservice;
	
	@RequestMapping(value ="/placeOrder" , method = RequestMethod.POST)
	public @ResponseBody String placeOrder(@RequestBody Inward objInward) {
		if(objInward==null)
			return "null";
	
		return orderservice.placeOrder(objInward);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/getAddressList/{customerId}")
	public String getExistingAddress(@PathVariable(value="customerId")String customerId) {
		return orderservice.getExistingAddress(customerId);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/postAddress")
	public @ResponseBody String postAddress(@RequestBody Address obj) {
		
		if(obj.getAddress1().equalsIgnoreCase(null) || obj.getAddress1().equalsIgnoreCase(""))
			return "Please mention FlatNumber or Landmark";
		if(obj.getAddress2().equalsIgnoreCase(null) || obj.getAddress2().equalsIgnoreCase(""))
			return "Please mention Area";
		if(obj.getCity().equalsIgnoreCase(null) || obj.getCity().equalsIgnoreCase(""))
			return "Please mention your city";
		if(obj.getPinCode().equalsIgnoreCase(null) || obj.getPinCode().equalsIgnoreCase(""))
			return "Please mention pincode";
		
		return orderservice.postAddress(obj);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getOrdersHistory/{customerId}")
	public String getOrdersHistory(@PathVariable(value="customerId")String customerId) {
		return orderservice.getOrdersHistory(customerId);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/getProductByOrder/{orderId}")
	public String getProductByOrder(@PathVariable(value="orderId")String orderId) {
		return orderservice.getProductByOrder(orderId);
	}
	
}
