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

import com.vgipl.server.apmc.model.Farmer;
import com.vgipl.server.apmc.utils.ConnectionSql;
import com.vgipl.server.apmc.utils.DbConstant;

@Service
public class FarmerDao {

	public String getFarmerList(String farmerName) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		String pass="";
		String  oriPass="";
		//CallableStatement cStatement = null;
		Statement statement=null;
	
		try {
	
			String query="Select Distinct a.id, dbo.FN_Get_Attribute('Customer','Name',a.Id) Farmer_Name " + 
					"from Customer a, GenericAttribute b, Customer_CustomerRole_Mapping c, CustomerRole d " + 
					"Where a.Id = b.EntityId " + 
					"and a.Id = c.Customer_Id and c.CustomerRole_Id = d.Id " + 
					"and d.Name = 'Farmer' " + 
					"and b.KeyGroup = 'Customer' " + 
					"and b.[Key] In ('FirstName','LastName') " + 
					"and dbo.FN_Get_Attribute('Customer','Name',a.Id) Like '%"+farmerName +"%'";
			
			System.out.println("query="+query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("FARMER_ID", resultSet.getString("id"));
				jsonObject.put("FARMER_NAME", resultSet.getString("Farmer_Name"));
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
