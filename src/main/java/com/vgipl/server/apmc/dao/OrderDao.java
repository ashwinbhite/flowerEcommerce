package com.vgipl.server.apmc.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.vgipl.server.apmc.model.Address;
import com.vgipl.server.apmc.model.Inward;
import com.vgipl.server.apmc.utils.AllUtils;
import com.vgipl.server.apmc.utils.ConnectionSql;
import com.vgipl.server.apmc.utils.DbConstant;

@Service
public class OrderDao {

	public String placeOrder(Inward objInward) {

		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);
		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		CallableStatement pStatement = null;
		try {

			String SPsql = "{ call Pr_Place_Order(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) } ";

//			System.out.println("getInwardFlag =============" + objInward.getInwardFlag());
//			System.out.println("getVendorId =============" + objInward.getVendorId());
//			System.out.println("getCustomerId =============" + objInward.getCustomerId());
//			System.out.println("getMarketId =============" + objInward.getMarketId());
//			System.out.println("getAmount =============" + objInward.getAmount());
//			System.out.println("getDeliverySchedule =============" + objInward.getDeliverySchedule());
//			System.out.println("getOrderDetail =============" + objInward.getOrderDetail());
//
//			System.out.println("getCartId =============" + objInward.getCartId());
//			System.out.println("getAddressId =============" + objInward.getAddressId());
//			System.out.println("getCustomerId =============" + objInward.getCustomerId());
//			System.out.println("getAddress1 =============" + objInward.getAddress1());
//			System.out.println("getAddress2 =============" + objInward.getAddress2());
//			System.out.println("getCity =============" + objInward.getCity());
//			System.out.println("getPinCode =============" + objInward.getPinCode());
//			System.out.println("getPackingType =============" + objInward.getPackingType());

			pStatement = connection.prepareCall(SPsql);
			pStatement.setString(1, objInward.getInwardFlag());
			pStatement.setInt(2, 0);
			pStatement.registerOutParameter(2, java.sql.Types.INTEGER);

			pStatement.setInt(3, Integer.parseInt(objInward.getVendorId()));
			pStatement.setInt(4, Integer.parseInt(objInward.getCustomerId()));
			pStatement.setInt(5, Integer.parseInt(objInward.getMarketId()));

			pStatement.setDouble(6, Double.parseDouble(objInward.getAmount()));

			pStatement.setBoolean(7, true);

			pStatement.setString(8, objInward.getDeliverySchedule());
			pStatement.registerOutParameter(9, java.sql.Types.VARCHAR);
			pStatement.setString(10, objInward.getCartId());
			// pStatement.setString(11, objInward.getInwardId());
			pStatement.setString(11, objInward.getAddressId());
			pStatement.setString(12, objInward.getAddress1());
			pStatement.setString(13, objInward.getAddress2());
			pStatement.setString(14, objInward.getCity());
			pStatement.setString(15, objInward.getPinCode());
			pStatement.setString(16, objInward.getPackingType());
			pStatement.setString(17, objInward.getPaymentType());
			pStatement.setString(18, objInward.getOrderType());
			pStatement.setInt(19, Integer.parseInt(objInward.getStoreId()));
			pStatement.execute();

			String error = pStatement.getString(9);
			System.out.println("Error-----------"+error);
			String orderNo = String.valueOf(pStatement.getInt(2));

			if (!orderNo.equalsIgnoreCase("0") || orderNo.equalsIgnoreCase("")) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ORDER_NO", orderNo);
				JsonArray.put(jsonObject);
			} else {

				JSONObject jsonObj = new JSONObject();
				resultJson.put("code", 0);
				resultJson.put("msg", error);
			}

			resultJson.put("code", 1);
			resultJson.put("msg", "Success");
			resultJson.put("Result", JsonArray);
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

					pStatement.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println("final result " + resultJson.toString());
		return resultJson.toString();

	}

	public String getExistingAddress(String customerId) {
		
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		try {

			String query ="SELECT ad.FullName  CustName, ad.EmailId, ad.MobileNo ," + 
					"		ad.Id AddressId, ad.Address1,ad.Address2,ad.City,ad.PinCode " + 
					"  FROM Address_Details ad " + 
					" INNER JOIN Customer Cm " + 
					" ON ad.CustomerID = Cm.Id " + 
					" WHERE  ad.CustomerID='" + customerId + "';" ;
			
			System.out.println("ADDRESS query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ID", resultSet.getString("AddressId"));
				jsonObject.put("ADDRESS1", resultSet.getString("Address1"));
				jsonObject.put("ADDRESS2", resultSet.getString("Address2"));
				jsonObject.put("CITY", resultSet.getString("City"));
				jsonObject.put("PINCODE", resultSet.getString("PinCode"));

				jsonObject.put("CustName", resultSet.getString("CustName"));
				jsonObject.put("Email", resultSet.getString("EmailId"));
				jsonObject.put("MobileNo", resultSet.getString("MobileNo"));
				
				JsonArray.put(jsonObject);
			}

			resultJson.put("code", 1);
			resultJson.put("msg", "Success");
			resultJson.put("Result", JsonArray);

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

	public String getOrdersHistory(String customerId) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		
		JSONArray newJsonArray = new JSONArray();

		
		try {

			/*
			 * String query
			 * ="select od.ID,od.OrderID,od.ProductID,om.TotalAmount,om.OrderDt,om.PaymentType,od.Delv_Status,pm.Name "
			 * + "from Order_Details od,Order_Master om,Product_Master pm " +
			 * "where od.Delv_Status='U' " + "and om.CustomerID = '"+customerId+"'" +
			 * "and pm.ID = od.ProductID " + "and om.ID = od.OrderID;" ;
			 */
			
			
			String query = "SELECT om.ID,om.OrderID,om.Delv_Boy_ID,om.OrderDt,om.PaymentType,om.TotalAmount,od.Delv_Status " + 
					"  FROM Order_Master om  " + 
					"INNER JOIN Order_Details od " + 
					"		ON om.ID = od.OrderID " + 
					"     WHERE od.ID in(select MAX(x.id) FROM Order_Details x where x.OrderID = od.OrderID) " + 
					"       AND om.CustomerID = '"+customerId+"'" + 
					"       AND od.Delv_Status <> 'D' " + 
					"  ORDER BY om.ID;";
			
			
			System.out.println("ADDRESS query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				//jsonObject.put("ORDER_ID", resultSet.getString("OrderID"));
				//jsonObject.put("PROD_ID", resultSet.getString("ProductID"));
				jsonObject.put("ORDER_ID", resultSet.getString("ID"));
				jsonObject.put("TOT_AMT", resultSet.getString("TotalAmount"));
		String  strDate = AllUtils.getCustomLotDate(resultSet.getString("OrderDt"));
				jsonObject.put("ORDER_DATE",strDate);
				jsonObject.put("PAYMENT_TYPE", resultSet.getString("PaymentType"));
				jsonObject.put("STATUS", resultSet.getString("Delv_Status"));
				jsonObject.put("DBOY_ID", resultSet.getString("Delv_Boy_ID"));
				
				JsonArray.put(jsonObject);
			}
			

			resultJson.put("code", 1);
			resultJson.put("msg", "Success");
			resultJson.put("Result", JsonArray);

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

	public String getProductByOrder(String orderId) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		JSONArray newJsonArray = new JSONArray();

		
		try {

			String query ="select od.Id,od.Quantity,od.Amount,pm.Name,pm.ID as productId from Order_Details od,Product_Master pm where OrderID="+orderId+" " + 
					"and pm.ID=od.ProductID" ;
			
			System.out.println("ADDRESS query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ID", resultSet.getString("Id"));
				jsonObject.put("QTY", resultSet.getString("Quantity"));
				jsonObject.put("AMOUNT", resultSet.getString("Amount"));
				jsonObject.put("PROD_NAME", resultSet.getString("Name"));
				jsonObject.put("PROD_ID", resultSet.getString("productId"));
				JsonArray.put(jsonObject);
			}
			

			resultJson.put("code", 1);
			resultJson.put("msg", "Success");
			resultJson.put("Result", JsonArray);

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

	public String postAddress(Address obj) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		PreparedStatement statement =null;
		try {
			
			String query= "INSERT INTO [dbo].[Address_Details]" + 
					"           ([CustomerID]" + 
					"            ,[Address1]" + 
					"           ,[Address2]" + 
					"           ,[City]" + 
					"           ,[PinCode]" + 
					"           ,[CreatedOnUtc]" +
					"            , [FullName] ,  [MobileNo]   , [EmailId]   )" +
					"            VALUES" + 
					"           ( '"+obj.getCustomerId().trim()+"'," + 
					"            '"+obj.getAddress1().trim()+"'," + 
					"            '"+obj.getAddress2().trim()+"'," + 
					"            '"+obj.getCity().trim()+"'," + 
					"            '"+obj.getPinCode().trim()+"'," + 
					"                GETDATE()   ,    " +
					"            '"+obj.getName().trim()+"'," + 
					"            '"+obj.getMobileNo().trim()+"'," + 
					"            '"+obj.getEmailId().trim()+"')" ;
				
			
			
			
			
			System.out.println("query---------"+query);
			
			statement = connection.prepareStatement(query);
			int rowInserted = statement.executeUpdate();
			
			if(rowInserted > 0) {
			
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

}
