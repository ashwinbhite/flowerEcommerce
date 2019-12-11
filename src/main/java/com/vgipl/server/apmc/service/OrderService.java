package com.vgipl.server.apmc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vgipl.server.apmc.dao.OrderDao;
import com.vgipl.server.apmc.model.Address;
import com.vgipl.server.apmc.model.Inward;

@Service
public class OrderService {

	@Autowired
	OrderDao orderDao;
	
	public String placeOrder(Inward objInward) {
		// TODO Auto-generated method stub
		return orderDao.placeOrder(objInward);
	}

	public String getExistingAddress(String customerId) {
		// TODO Auto-generated method stub
		return orderDao.getExistingAddress(customerId);
	}

	public String getOrdersHistory(String customerId) {
		// TODO Auto-generated method stub
		return orderDao.getOrdersHistory(customerId);
	}

	public String getProductByOrder(String orderId) {
		// TODO Auto-generated method stub
		return orderDao.getProductByOrder(orderId);
	}

	public String postAddress(Address obj) {
		// TODO Auto-generated method stub
		return orderDao.postAddress(obj);
	}

	
}
