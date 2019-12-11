package com.vgipl.server.apmc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vgipl.server.apmc.dao.HelpDao;
import com.vgipl.server.apmc.service.HelpService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/gethelp")
public class HelpController { 

	@Autowired
	HelpService helpService;
	
	@Autowired
	HelpDao helpdao;
	
	@RequestMapping(value ="/category" , method = RequestMethod.GET)
	public String getCategoryList() {
		
		return helpdao.getCategoryList();
	}

	
	@RequestMapping(value ="/product/{categoryId}" , method = RequestMethod.GET)
	public @ResponseBody String getProductList(@PathVariable String categoryId) {
		if(categoryId==null)
			return "null";
		
		System.out.println(categoryId);
		
		return helpdao.getProductList(categoryId);
	}
	
	
	@RequestMapping(value ="/unit/{prodId}" , method = RequestMethod.POST)
	public String getUnitList(@PathVariable String prodId) {
		if(prodId==null || prodId.equalsIgnoreCase(""))
			return "productId missing";
		
		return helpService.getUnitList(prodId);
	}
	
	@RequestMapping(value ="/unitForNewProd" , method = RequestMethod.GET)
	public String getUnitListForNewProd() {
		
		return helpService.getUnitListForNewProd();
	}
	
	
	
	@RequestMapping(value ="/getdate" , method = RequestMethod.POST)
	public String getdate() {
		
		return helpService.getdate();
	}
	
	@RequestMapping(value ="/getAttributeList" , method = RequestMethod.GET)
	public @ResponseBody String getAttributeList() {
		
		return helpdao.getAttributeList();
	}
	
	@RequestMapping(value ="/getAttributeMenu/{attributeParent}" , method = RequestMethod.GET)
	public @ResponseBody String getAttributeListMenu(@PathVariable String attributeParent) {
		
		return helpdao.getAttributeListMenu(attributeParent);
	}
	
	
	@RequestMapping(value ="/getAttributeListByProduct/{productId}" , method = RequestMethod.GET)
	public @ResponseBody String getAttributeListByProduct(@PathVariable String productId) {
		if(productId==null)
			return "No productId present";
	
		return helpdao.getAttributeListByProduct(productId);
	}
	
	@RequestMapping(value ="/getStoreList" , method = RequestMethod.GET)
	public String getStoreList() {
		
		return helpdao.getStoreList();
	}
}
