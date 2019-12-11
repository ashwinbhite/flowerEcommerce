package com.vgipl.server.apmc.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class IndexController {

	@RequestMapping("/")
	public String index() {
		return "WELCOME TO FLOWER MARKET";
	}
}
