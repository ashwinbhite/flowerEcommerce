package com.vgipl.server.apmc.dao;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.vgipl.server.apmc.model.User;
import com.vgipl.server.apmc.utils.AES;
import com.vgipl.server.apmc.utils.Base64EncoderDecoder;
import com.vgipl.server.apmc.utils.ConnectionSql;
import com.vgipl.server.apmc.utils.Crypto;
import com.vgipl.server.apmc.utils.DbConstant;

@Repository
public class LoginDao {

	public String userLoginDao(User strObj) {

		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray loginJsonArray = new JSONArray();
		JSONObject loginResult = new JSONObject();
		ResultSet resultSet = null;
		String pass = "";
		String oriPass = "";
		// CallableStatement cStatement = null;
		Statement statement = null;
		System.out.println("strObj------------" + strObj.toString());
		try {

			String encryptedPasword = Base64EncoderDecoder.encode(strObj.getPassword().getBytes());

			String query =

					"  select distinct a.id,a.CustomerGuid,a.PasswordFormatId,a.PasswordSalt,a.MobileNo, a.Email, "
							+ "			 a.Password,a.HasShoppingCartItems,a.BillingAddress_Id,  "
							+ "			 a.ShippingAddress_Id,a.VendorId,a.IsTaxExempt,a.FirstName,a.LastName from  "
							+ "			Customer a where (a.MobileNo ='" + strObj.getUserName() +"' or a.Username='"+ strObj.getUserName() +"' or a.Email='" + strObj.getUserName() +"' ) and a.Password='"
							+ encryptedPasword + "' ";

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			System.out.println("------------" + query);
			while (resultSet.next()) {

				JSONObject jsonObject = new JSONObject();
				// jsonObject.put("CUSTGUID", resultSet.getString("CustomerGuid"));
				jsonObject.put("ID", resultSet.getString("id"));
				jsonObject.put("HAS_SHOPPING_CARTITEMS", resultSet.getString("HasShoppingCartItems"));
				jsonObject.put("BILLING_ADDRESS_ID", resultSet.getString("BillingAddress_Id"));
				jsonObject.put("SHIPPING_ADDRESS_ID", resultSet.getString("ShippingAddress_Id"));
				jsonObject.put("EMAIL", resultSet.getString("Email"));
				jsonObject.put("FIRSTNAME", resultSet.getString("FirstName"));
				jsonObject.put("LASTNAME", resultSet.getString("LastName"));
				jsonObject.put("MOBILENO", resultSet.getString("MobileNo"));
				jsonObject.put("VENDORID", resultSet.getString("VendorId"));

				loginJsonArray.put(jsonObject);
			}

			loginResult.put("code", 1);
			loginResult.put("msg", "Success");
			loginResult.put("Result", loginJsonArray);
			System.out.println("loginResult---" + loginResult.toString());

		} catch (Exception e) {
			e.printStackTrace();
			try {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("code", 0);
				jsonObj.put("msg", "Error");
				loginJsonArray.put(jsonObj);
				loginResult.put("Result", loginJsonArray);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (connection != null)
				try {
					resultSet.close();
					statement.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return loginResult.toString();
	}

	public JSONObject userLoginByMobile(String mobileNo) {

		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray loginJsonArray = new JSONArray();
		JSONObject loginResult = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		try {

			String query = "  select distinct a.id,a.CustomerGuid,a.PasswordFormatId,a.PasswordSalt,a.MobileNo, a.Email,  "
					+ "					 a.Password,a.HasShoppingCartItems,a.BillingAddress_Id,   "
					+ "						 a.ShippingAddress_Id,a.VendorId,a.IsTaxExempt,a.FirstName,a.LastName from  "
					+ "						Customer a where a.MobileNo='" + mobileNo + "' ";

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			System.out.println("------------" + query);
			while (resultSet.next()) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ID", resultSet.getString("id"));
				jsonObject.put("HAS_SHOPPING_CARTITEMS", resultSet.getString("HasShoppingCartItems"));
				jsonObject.put("BILLING_ADDRESS_ID", resultSet.getString("BillingAddress_Id"));
				jsonObject.put("SHIPPING_ADDRESS_ID", resultSet.getString("ShippingAddress_Id"));
				jsonObject.put("EMAIL", resultSet.getString("Email"));
				jsonObject.put("FIRSTNAME", resultSet.getString("FirstName"));
				jsonObject.put("LASTNAME", resultSet.getString("LastName"));
				jsonObject.put("MOBILENO", resultSet.getString("MobileNo"));
				jsonObject.put("VENDORID", resultSet.getString("VendorId"));

				loginJsonArray.put(jsonObject);
			}

			loginResult.put("code", 1);
			loginResult.put("msg", "Success");
			loginResult.put("Data", loginJsonArray);
			System.out.println("loginResult---" + loginResult.toString());

		} catch (Exception e) {
			e.printStackTrace();
			try {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("code", 0);
				jsonObj.put("msg", "Error");
				loginJsonArray.put(jsonObj);
				loginResult.put("Data", loginJsonArray);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (connection != null)
				try {
					resultSet.close();
					statement.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return loginResult;
	}

	public String modifyUserProfile(User strObj) {

		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray loginJsonArray = new JSONArray();
		JSONObject loginResult = new JSONObject();
		ResultSet resultSet = null;

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();

		PreparedStatement statement = null;

		// CallableStatement cStatement = null;

		System.out.println("strObj------------" + strObj.toString());
		try {

			String query =

					"update Customer set FirstName='" + strObj.getfName() + "' , LastName='" + strObj.getlName()
							+ "', MobileNo='" + strObj.getMobileNo() + "', Email='" + strObj.getEmail() + "' where Id='"
							+ strObj.getUserId() + "' ";

			statement = connection.prepareStatement(query);

			int rowDeleted = statement.executeUpdate();

			if (rowDeleted > 0) {

				resultJson.put("updated", true);
				resultJson.put("code", 1);
				resultJson.put("msg", "Success");
				resultJson.put("Result", JsonArray);
			} else {
				resultJson.put("updated", false);
				resultJson.put("code", 1);
				resultJson.put("msg", "Success");
				resultJson.put("Result", JsonArray);
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("code", 0);
				jsonObj.put("msg", "Error");
				JsonArray.put(jsonObj);
				System.out.println("error result===" + JsonArray.toString());
				resultJson.put("Result", JsonArray);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

		} finally {
			if (connection != null)
				try {

					statement.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return resultJson.toString();
	}

	public String userRegistration(User user) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {

			String encryptedPasword = Base64EncoderDecoder.encode(user.getPassword().getBytes());

			String query = "INSERT INTO [LafleurDev].[dbo].[Customer] " + "           ([CustomerGuid] "
					+ "           ,[Username] " + "           ,[Email] " + "           ,[Password] "
					+ "           ,[PasswordFormatId] " + "           ,[PasswordSalt] " + "           ,[AdminComment] "
					+ "           ,[IsTaxExempt] " + "           ,[AffiliateId] " + "           ,[VendorId] "
					+ "           ,[StoreId] " + "           ,[HasShoppingCartItems] " + "           ,[Active] "
					+ "           ,[Deleted] " + "           ,[IsSystemAccount] " + "           ,[SystemName] "
					+ "           ,[LastIpAddress] " + "           ,[CreatedOnUtc] " + "           ,[LastLoginDateUtc] "
					+ "           ,[LastActivityDateUtc] " + "           ,[BillingAddress_Id] "
					+ "           ,[ShippingAddress_Id] " + "           ,[DeviceID] " + "           ,[FirstName] "
					+ "           ,[LastName] " + "           ,[MobileNo] " + "           ,[Cust_Type]) "
					+ "     VALUES " + "(Null " + "           ,'" + user.getUserName() + "' " + "           ,'"
					+ user.getEmail() + "'" + "           ,'" + encryptedPasword + "'" + "           ,Null "
					+ "           ,Null " + "           ,Null " + "           ,Null " + "           ,Null "
					+ "           ,Null " + "           ,Null " + "           ,Null " + "           ,1 "
					+ "           ,0 " + "           ,Null " + "           ,Null " + "           ,Null "
					+ "           ,GETDATE() " + "           ,Null " + "           ,GETDATE() " + "           ,Null "
					+ "           ,Null " + "           ,Null " + "           ,'" + user.getfName() + "' "
					+ "           ,'" + user.getlName() + "' " + "           ,'" + user.getMobileNo() + "' "
					+ "           ,'" + user.getcustomerType() + "') " + "" + "";
			System.out.println("query---------" + query);

			statement = connection.prepareStatement(query);
			int rowInserted = statement.executeUpdate();

			if (rowInserted > 0) {

				JSONObject jsonObj = new JSONObject();
				resultJson.put("code", 1);
				resultJson.put("msg", "SUCCESS");

			} else {

				JSONObject jsonObj = new JSONObject();
				resultJson.put("code", 0);
				resultJson.put("msg", "FAIL TO INSERT");
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				JSONObject jsonObj = new JSONObject();
				resultJson.put("code", 0);
				resultJson.put("msg", "Error");

			} catch (JSONException e1) {
				e1.printStackTrace();
			}

		} finally {
			if (connection != null)
				try {

					statement.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println("final result " + resultJson.toString());
		return resultJson.toString();
	}

	public String generateOtpForLogin(String mobileNo) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		try {
			int randomPIN = (int) (Math.random() * 9000) + 1000;
			String Otp = String.valueOf(randomPIN);

			final String uri = "http://login.smsgatewayhub.com/api/mt/SendSMS?user=APMCJR&password=477348&senderid=APMCJR&channel=2&DCS=7&flashsms=0&number="
					+ mobileNo + "&text=" + Otp + "&route=7";
			RestTemplate restTemplate = new RestTemplate();
			String res = restTemplate.getForObject(uri, String.class);
//		    JSONObject res = restTemplate.getForObject(uri,JSONObject.class);
//			ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(uri, JSONObject.class);
//			JSONObject res = responseEntity.getBody();
			System.out.println("result of otp url------" + res.substring(14, res.indexOf(",") - 1));

			String s = res.substring(14, (res.indexOf(",") - 1));

			if (s.equalsIgnoreCase("000")) {

				// String result = res.getString("ErrorMessage"); //

				resultJson.put("code", 1);
				resultJson.put("OTP", Otp);
				resultJson.put("msg", "Success");
				resultJson.put("Result", userLoginByMobile(mobileNo));

				return resultJson.toString();

			} else {

				String result = "Failure";
				System.out.println(result);
				resultJson.put("code", 0);
				resultJson.put("msg", "Failure");
				resultJson.put("Result", result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("code", 0);
				jsonObj.put("msg", "Error");
				JsonArray.put(jsonObj);
				System.out.println("error result===" + JsonArray.toString());
				resultJson.put("Result", JsonArray);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

		} finally {
			if (connection != null)
				try {
					// resultSet.close();

					// statement.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return resultJson.toString();
	}

}
