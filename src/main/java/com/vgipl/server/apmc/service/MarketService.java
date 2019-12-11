package com.vgipl.server.apmc.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vgipl.server.apmc.dao.MarketDao;
import com.vgipl.server.apmc.model.Market;

@Service
public class MarketService {

	@Autowired
	 MarketDao marketdao;
	
	public String getmarketList(Market strObj) {
		return marketdao.getmarketList(strObj);
		// TODO Auto-generated method stub
		
	
	}

	public String getVendorList(Market objMkt) {
		// TODO Auto-generated method stub
		return marketdao.getVendorList(objMkt);
	}

}
