package com.vgipl.server.apmc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vgipl.server.apmc.dao.SubscribtionDao;
import com.vgipl.server.apmc.model.Subscribtion;

@Service
public class SubscribtionService {

	@Autowired
	SubscribtionDao subscribtionDao;
	
	public String subscribtionEntry(Subscribtion subscribtion) {
		
		return subscribtionDao.subscribtionEntry(subscribtion);
	}

}
