package com.vgipl.server.apmc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.vgipl.server.apmc.model.Market;
import com.vgipl.server.apmc.model.User;
import com.vgipl.server.apmc.utils.ConnectionSql;
import com.vgipl.server.apmc.utils.DbConstant;

@Service
public class MarketDao {

	public String getmarketList(Market strObj) {
		// TODO Auto-generated method stub
		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject loginResult = new JSONObject();
		ResultSet resultSet = null;
		
		Statement statement=null;
		System.out.println("strObj------------"+strObj.toString());
		try {
			
			LocalDate date = LocalDate.now();
			DayOfWeek dow = date.getDayOfWeek();
			String dayName = dow.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
			System.out.println("CURRENT DAY==="+dayName);

			String query="SELECT * FROM MARKET " + 
					" WHERE WORKING_DAY LIKE '%"+"Mon"+"%'" + 
					"AND PUBLISHED = 1";
			
			System.out.println("query="+query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("MARKETID", resultSet.getString("ID"));
				jsonObject.put("MARKETNAME", resultSet.getString("Market_Name"));
				jsonObject.put("ADDRESS1", resultSet.getString("Address_1"));
				jsonObject.put("ADDRESS2", resultSet.getString("Address_2"));
				jsonObject.put("PINCODE", resultSet.getString("Pin_Code"));
				jsonObject.put("LATITUDE", resultSet.getString("Latitude"));
				jsonObject.put("LONGITUDE", resultSet.getString("Longitude"));
				jsonObject.put("WORKINGDAY", resultSet.getString("Working_Day"));

				JsonArray.put(jsonObject);
			}

			loginResult.put("code",1);
			loginResult.put("msg", "Success");
			loginResult.put("Result", JsonArray);

		}catch(Exception e) {
			e.printStackTrace();
			try
			{
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("code",0);
				jsonObj.put("msg", "Error");
				JsonArray.put(jsonObj);
				System.out.println("error result==="+JsonArray.toString());
				loginResult.put("Result", JsonArray);
			}
			catch(JSONException e1)
			{
				e1.printStackTrace();
			}

		}finally {
			if(connection!=null)
				try {
					resultSet.close();
					statement.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		System.out.println("final result of market list is---"+loginResult.toString());
		return loginResult.toString();
	}

	public String getVendorList(Market objMkt) {
		
				Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

				JSONArray JsonArray = new JSONArray();
				JSONObject resultJson = new JSONObject();
				ResultSet resultSet = null;
				String pass="";
				String  oriPass="";
				//CallableStatement cStatement = null;
				Statement statement=null;
				System.out.println("strObj------------"+objMkt.toString());	
				
	
				
				try {

					String query="Select a.Id, a.Name From Vendor a, Market b, Vendor_Market_Mapping c " + 
							 " Where a.Id = c.VendorId " + 
							" and b.ID = c.MarketID " + 
							" and b.ID = "+ objMkt.getMarketId()+ " " + 
							" and b.Published = 1 ";
					System.out.println("query="+query);
					statement = connection.createStatement();
					resultSet = statement.executeQuery(query);
					while(resultSet.next()) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("VENDOR_ID", resultSet.getString("Id"));
						jsonObject.put("VENDOR_NAME", resultSet.getString("Name"));
						
						JsonArray.put(jsonObject);
					}

					resultJson.put("code",1);
					resultJson.put("msg", "Success");
					resultJson.put("Result", JsonArray);

				}catch(Exception e) {
					e.printStackTrace();
					try
					{
						JSONObject jsonObj = new JSONObject();
						jsonObj.put("code",0);
						jsonObj.put("msg", "Error");
						JsonArray.put(jsonObj);
						System.out.println("error result==="+JsonArray.toString());
						resultJson.put("Result", JsonArray);
					}
					catch(JSONException e1)
					{
						e1.printStackTrace();
					}

				}finally {
					if(connection!=null)
						try {
							resultSet.close();
							statement.close();
							connection.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
				return resultJson.toString();
	}

}
