package com.vgipl.server.apmc.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vgipl.server.apmc.service.InvoiceService;
import com.vgipl.server.apmc.utils.HtmlBuilder;
import com.vgipl.server.apmc.utils.PDFGenerator;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

	@Autowired
	InvoiceService invoiceService;
	
	@GetMapping("/getbill")
	public String getPdf(@RequestParam(name = "id") String orderId) {
		
		return invoiceService.getPdf(orderId);
		
//		ArrayList list = new ArrayList();
//		for(int i=0;i<8;i++) {
//			list.add(i);
//		}
//		//PDFGenerator.createPdf(list);
//		HtmlBuilder.InvoiceHtmlBuilder(list);
	}
}
