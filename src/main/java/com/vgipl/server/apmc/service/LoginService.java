package com.vgipl.server.apmc.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vgipl.server.apmc.dao.LoginDao;

import com.vgipl.server.apmc.model.User;

@Service
public class LoginService {
	
	@Autowired
	LoginDao loginDao;

	/*
	 * public String userLoginService(String userName,String customerType,String
	 * password) { // TODO Auto-generated method stub return
	 * loginDao.userLoginDao(userName, customerType, password); }
	 */

	public String userLoginService(User strObj) {
		
		return loginDao.userLoginDao(strObj); 
	}

	public String userRegistration(User user) {	
		
		return loginDao.userRegistration(user);
	}
public String modifyUserProfile(User user) {	
		
		return loginDao.modifyUserProfile(user);
	}

public String getOtpForLogin(String mobileNo) {

	JSONArray loginJsonArray = new JSONArray();
	JSONObject loginResult = new JSONObject();
	
	if(mobileNo.length()!=10) {
		
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("code",0);
			jsonObj.put("msg", "Mobile Number Invalid");
			loginJsonArray.put(jsonObj);
			loginResult.put("Result", loginJsonArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  loginResult.toString();
	}
	return loginDao.generateOtpForLogin(mobileNo);
}
	

	

}
