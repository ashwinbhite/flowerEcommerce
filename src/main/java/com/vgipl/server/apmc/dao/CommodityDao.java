package com.vgipl.server.apmc.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.vgipl.server.apmc.model.Inward;
import com.vgipl.server.apmc.model.Vendor;
import com.vgipl.server.apmc.utils.AllUtils;
import com.vgipl.server.apmc.utils.ConnectionSql;
import com.vgipl.server.apmc.utils.DbConstant;

@Service
public class CommodityDao {

	public String getCommodityList(String vendorId) {
		
		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		String pass="";
		String  oriPass="";
		//CallableStatement cStatement = null;
		Statement statement=null;
	
		try {
	
			String query="Select a.Id, a.Name " + 
					"from Product a " + 
					"where a.Published =1 and a.VendorId ="+vendorId;
			
			System.out.println("query="+query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("COMMODITY_ID", resultSet.getString("id"));
				jsonObject.put("COMMODITY_NAME", resultSet.getString("Name"));
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

	public static boolean isStringEmpty(String input){
        if(input != null && input.length() == 0){
            return true;
        }
        return false;
    }

	
	public String addToCart(Inward objInward) {
		
		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		CallableStatement statement =null;
		try {
			
			
			
			/*
			 * // String query= "INSERT INTO [Lafleur].[dbo].[ShoppingCart] " + //
			 * "           ([MarketID] " + // "           ,[VendorID] " + //
			 * "           ,[CustomerID] " + // "           ,[ProductID] " + //
			 * "           ,[Quantity] " + // "           ,[UnitID] " + //
			 * "           ,[Rate] " + // "           ,[Amount] " + //
			 * "           ,[CreatedOnUTC] " + // "           ,[UpdatedOnUTC] " + //
			 * "           ,[Attrib_Grp_ID] " + // "           ,[Attrib_Grp_Name] " + //
			 * "           ,[Cutting]) " + // "     VALUES " + // "           (0 " + //
			 * "           , "+objInward.getVendorId()+ " " + //
			 * "           , "+objInward.getCustomerId()+ " " + //
			 * "           , "+objInward.getProductId()+ " " + //
			 * "           , "+objInward.getQuantity()+ " " + //
			 * "           , "+objInward.getUnitId()+ " " + //
			 * "           , "+objInward.getRate()+ " " + //
			 * "           , "+objInward.getAmount()+ " " + // "           ,GETDATE() " + //
			 * "           ,GETDATE() " + // ", 0 " + // ", '0'" + // " , '0') " + // ""; //
			 * System.out.println("query---------"+query); // // statement =
			 * connection.prepareStatement(query);
			 * 
			 */			
			String SPsql = "{ call Pr_Shopping_Cart(?,?,?,?,?,?,?,?,?,?,?,?,?) } ";
			statement = connection.prepareCall(SPsql);
			
			statement.setInt(1,Integer.parseInt(objInward.getMarketId()) );
			statement.setInt(2, Integer.parseInt(objInward.getVendorId()));
			statement.setInt(3, Integer.parseInt(objInward.getCustomerId()));
			statement.setInt(4, Integer.parseInt(objInward.getProductId()));
			statement.setDouble(5, Double.parseDouble(objInward.getQuantity()));
			statement.setInt(6, Integer.parseInt(objInward.getUnitId()));
			statement.setDouble(7, Double.parseDouble(objInward.getRate()));
			statement.setDouble(8, Double.parseDouble(objInward.getAmount()));
			statement.setInt(9,0);
			statement.setString(10, "0");
			statement.setString(11, "0");
			statement.registerOutParameter(12, java.sql.Types.INTEGER);
			statement.registerOutParameter(13, java.sql.Types.VARCHAR);
			statement.execute();
			
			int outId= statement.getInt(12);
			System.out.println("OUT ID--"+outId);
			String error = statement.getString(13);
			System.out.println("error--"+error);

			if(AllUtils.isNullOrEmpty(error)) {
			
				JSONObject jsonObj = new JSONObject();
				resultJson.put("code",1);
				resultJson.put("msg", "SUCCESS");
			
			}else {
				
				JSONObject jsonObj = new JSONObject();
				resultJson.put("code",0);
				resultJson.put("msg", "FAIL TO INSERT");			
			}
			
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
		System.out.println("final result "+resultJson.toString());
		return resultJson.toString();
				
	}

	public String getCartItem(Inward objInward) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		String pass="";
		String  oriPass="";
		//CallableStatement cStatement = null;
		Statement statement=null;
	
		try {
		
			String query=
			
			" Select a.ID as CartID, a.ProductID, a.MarketID, " + 
			" a.VendorID, a.Quantity, a.UnitID, dbo.FN_Get_Unit_Name(a.UnitID) As UnitName, 	 " + 
			" a.Rate, a.Amount, b.Name as ProductName,a.Cutting, a.Attrib_Grp_ID ,  a.Attrib_Grp_Name ,	 " + 
			" dbo.FN_Get_Actual_Qty(a.ProductID, dbo.FN_Get_Converted_Qty(a.productId, " + 
			"  a.Quantity, a.UnitID), a.UnitID) as Stock  " + 
			"  From ShoppingCart a, Product_Master b " + 
			"  Where a.ProductID = b.ID and a.CustomerID = '"+objInward.getCustomerId()+"'   " + 
		
			"  and b.Published = 1 ";
			
			System.out.println("query="+query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("CART_ID", resultSet.getString("CartID"));
				jsonObject.put("PRODUCT_ID", resultSet.getString("ProductID"));
				jsonObject.put("MARKET_ID", resultSet.getString("MarketID"));
				jsonObject.put("VENDOR_ID", resultSet.getString("VendorID"));
				jsonObject.put("QUANTITY", resultSet.getString("Quantity"));
				jsonObject.put("UNIT_ID", resultSet.getString("UnitID"));
				jsonObject.put("UNIT_NAME", resultSet.getString("UnitName"));
				jsonObject.put("RATE", resultSet.getString("Rate"));
				jsonObject.put("AMOUNT", resultSet.getString("Amount"));
				jsonObject.put("PRODUCTNAME", resultSet.getString("ProductName"));
				
				//jsonObject.put("SELLING_QTY", resultSet.getString("Selling_Qty"));
				//jsonObject.put("SELLING_RATE", resultSet.getString("Selling_Rate"));
				jsonObject.put("CUTTING", resultSet.getString("Cutting"));
				jsonObject.put("ATTRIB_ID", resultSet.getString("Attrib_Grp_ID"));
				jsonObject.put("ATTRIB_NAME", resultSet.getString("Attrib_Grp_Name"));
				
				jsonObject.put("STOCK", resultSet.getString("Stock"));
				//jsonObject.put("STOCK", "8");
//				System.out.println("STOCK==="+resultSet.getString("Stock"));
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

	public String deleteCartItem(String cartId, String customerId) {

		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		PreparedStatement statement=null;
	
		try {
			
			String query ="delete from ShoppingCart where ID='"+cartId+"' and CustomerID='"+customerId+"'";
			statement = connection.prepareStatement(query);
		
			
			int rowDeleted = statement.executeUpdate();
			
			if(rowDeleted>0) {
				
				resultJson.put("delete", true);	
				resultJson.put("code",1);
				resultJson.put("msg", "Success");
				resultJson.put("Result", JsonArray);
			}else {
				resultJson.put("delete", false);	
				resultJson.put("code",1);
				resultJson.put("msg", "Success");
				resultJson.put("Result", JsonArray);
			}
			
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
