package com.vgipl.server.apmc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.vgipl.server.apmc.model.Category;
import com.vgipl.server.apmc.utils.ConnectionSql;
import com.vgipl.server.apmc.utils.DbConstant;

@Service
public class HelpDao {

	public String getUnitListForNewProd() {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		String pass="";
		String  oriPass="";
		Statement statement=null;
				
		try {

			String query="Select ID, UnitName " + 
					"From Unit_Master " + 
					"Where Published = 1 " + 
					"Order by UnitName " + 
					"";
					
			System.out.println("query="+query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("UNIT_ID", resultSet.getString("ID"));
				jsonObject.put("UNIT_NAME", resultSet.getString("UnitName"));

				
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
	
	
	public String getUnitList(String productId) {
		
		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		String pass="";
		String  oriPass="";
		Statement statement=null;
				
		try {

//			String query="Select ID, UnitName " + 
//					"From Unit_Master " + 
//					"Where Published = 1 " + 
//					"Order by UnitName " + 
//					"";
			
			String query="Select a.UnitID, b.UnitName " + 
					"From Product_Master a, Unit_Master b  " + 
					"where a.ID= "+productId+" " + 
					"and a.UnitID=b.ID";
			
			
			System.out.println("query="+query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("UNIT_ID", resultSet.getString("UnitID"));
				jsonObject.put("UNIT_NAME", resultSet.getString("UnitName"));
				
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

	public String getProductList(String categoryId ) {
		
		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		String pass="";
		String  oriPass="";
		Statement statement=null;
				
		try {

			String query="Select ID, Name,CategoryId,UnitID " + 
					"From Product_Master " + 
					"Where CategoryID = 2"   + 
					"And Published = 1 " + 
					"Order by Name";
			
			System.out.println("query="+query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("PRODUCT_ID", resultSet.getString("ID"));
				jsonObject.put("PRODUCT_NAME", resultSet.getString("Name"));
				jsonObject.put("CATEGORY_ID", resultSet.getString("CategoryId"));
				jsonObject.put("UNIT_ID", resultSet.getString("UnitID"));
				
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

	public String getCategoryList() {
		
		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		String pass="";
		String  oriPass="";
		Statement statement=null;
				
		try {

			String query="Select Id, Name " + 
					"From Category " + 
					"Where Published = 1 and Deleted = 0 " + 
					"Order by Name";
			
			System.out.println("query="+query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("CATEGORY_ID", resultSet.getString("Id"));
				jsonObject.put("CATEGORY_NAME", resultSet.getString("Name"));
				
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

	
	public String getdate() {
		
		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement =null;
		try {
			
			String query= "select convert(varchar, getdate(), 5) as CurrentDate";

			System.out.println("query="+query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("CURRENT_DATE", resultSet.getString("CurrentDate"));
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
				resultJson.put("code",0);
				resultJson.put("msg", "Error");				
			}
			catch(JSONException e1)
			{
				e1.printStackTrace();
			}

		}finally {
			if(connection!=null)
				try {

					statement.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println("DATE==="+resultJson.toString());
		return resultJson.toString();
	}

	public String getAttributeList() {

		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		String pass="";
		String  oriPass="";
		Statement statement=null;
				
		try {

			String query="select a.ID,a.Attrib_Name from Attribute_Master a where a.Published='1'";
			
			System.out.println("query="+query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ATTRIBUTE_ID", resultSet.getString("ID"));
				jsonObject.put("ATTRIBUTE_NAME", resultSet.getString("Attrib_Name"));
				
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

	public String getAttributeListByProduct(String productId) {
		
		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		String pass="";
		String  oriPass="";
		Statement statement=null;
				
		try {

			String query="Select a.Attrib_Grp_ID, [dbo].[FN_Concat_Value](a.Attrib_Grp_ID) " + 
					"as ATTRIBUTE_NAME,c.Offer_Rate,c.Sale_Rate From Attribute_Mapping a, Attribute_Master b,GradewiseStock c " + 
					"Where a.Attrib_ID = b.ID " + 
					"and a.Product_ID = "+productId+" " + 
					"and c.ProductId=   "+productId+" " + 
					"and c.AttribGrpId=a.Attrib_Grp_ID  " +
					" group by a.Attrib_Grp_ID,c.Offer_Rate ,c.Sale_Rate " + 
					"Order by Attrib_Grp_ID " + 
					"";
			
			System.out.println("query="+query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ATTRIBUTE_ID", resultSet.getString("Attrib_Grp_ID"));
				jsonObject.put("ATTRIBUTE_NAME", resultSet.getString("ATTRIBUTE_NAME"));
				jsonObject.put("OFFER_RATE", resultSet.getString("Offer_Rate"));
				jsonObject.put("SALE_RATE", resultSet.getString("Sale_Rate"));
				
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


	public String getAttributeListMenu(String attributeParent) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		String pass="";
		String  oriPass="";
		Statement statement=null;
				
		try {

			String query="select a.ID,a.Attrib_Name from Attribute_Master a where a.Attr_Parent='"+attributeParent+"' and a.Published='1'";
			
			System.out.println("query="+query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ATTRIBUTE_ID", resultSet.getString("ID"));
				jsonObject.put("ATTRIBUTE_NAME", resultSet.getString("Attrib_Name"));
				
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


	public String getStoreList() {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		String pass="";
		String  oriPass="";
		Statement statement=null;
				
		try {

			String query="select a.ID,a.Store_Name,a.Longitude,a.Latitude,a.Address_1,a.Address_2,a.Address_3,a.Pin_Code,a.Open_Time,a.Close_Time from Store_Master a where a.Published='1';";
			
			System.out.println("query="+query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("STORE_ID", resultSet.getString("ID"));
				jsonObject.put("STORE_NAME", resultSet.getString("Store_Name"));
				jsonObject.put("STORE_LONG", resultSet.getString("Longitude"));
				jsonObject.put("STORE_LAT", resultSet.getString("Latitude"));
				jsonObject.put("STORE_ADDR1", resultSet.getString("Address_1"));
				jsonObject.put("STORE_ADDR2", resultSet.getString("Address_2"));
				jsonObject.put("STORE_ADDR3", resultSet.getString("Address_3"));
				jsonObject.put("STORE_PIN", resultSet.getString("Pin_Code"));
				jsonObject.put("STORE_OPEN_TIME", resultSet.getString("Open_Time"));
				jsonObject.put("STORE_CLOSE_TIME", resultSet.getString("Close_Time"));


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
