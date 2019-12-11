package com.vgipl.server.apmc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vgipl.server.apmc.dao.InvoiceDao;

@Service
public class InvoiceService {
	
	@Autowired
	InvoiceDao invoiceDao;

	public String getPdf(String userId) {
		// TODO Auto-generated method stub
		return invoiceDao.getPdf(userId);
	}

	
}
