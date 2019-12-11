package com.vgipl.server.apmc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.vgipl.server.apmc.model.User;
import com.vgipl.server.apmc.service.LoginService;
import com.vgipl.server.apmc.utils.AES;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	LoginService loginService;

	@RequestMapping(value = "/username", method = RequestMethod.POST)
	public @ResponseBody String userLogin(/* @RequestHeader(value="Authorization",required = true) String authString, */
			@RequestBody User strObj) {
		if (strObj == null)
			return "null";

		System.out.println(strObj.getUserName()+"--"+strObj.getPassword());
		/*
		 * System.out.println("user=" + strObj.getUserName() + ",header="+authString
		 * pass= "+strObj.getPassword());
		 */ // isUserAuthenticated(authString);
		return loginService.userLoginService(strObj);

	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public @ResponseBody String userRegistration(@RequestBody User user) {
		if(user==null)
			return "No data found";
		
		return loginService.userRegistration(user);
	}
	
	@RequestMapping(value="/updateProfile",method=RequestMethod.POST)
	public @ResponseBody String userProfile(@RequestBody User user) {
		if(user==null)
			return "No data found";
		
		return loginService.modifyUserProfile(user);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getOtpForLogin/{mobileNo}")
	public String getOtpForLogin(@PathVariable(value="mobileNo")String mobileNo) {
		return loginService.getOtpForLogin(mobileNo);
	}
	

//	@PostMapping(path= "/username", consumes = "application/json", produces = "application/json")
//	public String doUserLogin(@RequestHeader(value="Authorization",required = true) String authString,
//			@RequestParam(value="userName")String userName,
//			@RequestParam(value="password")String password){
//		
//		System.out.println("username="+userName+" password="+password+" Header="+authString);
//		User user = new User();
//		user.setUserName(userName);
//		user.setPassword(password);
//		return loginService.userLoginService(user);
//	}

	private boolean isUserAuthenticated(String authString) {

		String decodedAuth;
		try {

			decodedAuth = AES.decrypt(authString);
			String First = authString.substring(authString.indexOf(" "));

			String[] authParts = authString.split("");
			String authInfo = authParts[1];
			// Decode the data back to original string
			System.out.println(decodedAuth);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

}
