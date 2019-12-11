package com.vgipl.server.apmc.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.vgipl.server.apmc.model.Subscribtion;
import com.vgipl.server.apmc.utils.AllUtils;
import com.vgipl.server.apmc.utils.ConnectionSql;
import com.vgipl.server.apmc.utils.DbConstant;

@Service
public class SubscribtionDao {

	public String getSubscriptionTypeList(String planType) {
		
		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement=null;
				
		try {

			String query="select a.id,a.PlanName, " + 
					"a.NoOfBouquet,a.PlanCost,a.ProductId from " + 
					"Subscription_Plan a where a.PlanType='"+ planType+"'";
					
			System.out.println("query="+query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ID", resultSet.getString("id"));
				jsonObject.put("PLAN_NAME", resultSet.getString("PlanName"));
				
				jsonObject.put("NO_OF_BOUQUET", resultSet.getString("NoOfBouquet"));
				jsonObject.put("PRICE", resultSet.getString("PlanCost"));
				jsonObject.put("PROD_ID", resultSet.getString("ProductId"));
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

					e.printStackTrace();
				}
		}
		return resultJson.toString();
	}

	public String subscribtionEntry(Subscribtion subscribtion) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		CallableStatement pStatement = null;
		try {
			
			String strPayDate =AllUtils.getFormattedDateForSqlForWorkingdate(subscribtion.getPayDate());
			
			String SPsql = "{ call Pr_Subscription_Entry(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) } ";

			pStatement = connection.prepareCall(SPsql);
			
			pStatement.setString(1, subscribtion.getFlag());
			pStatement.setInt(2,Integer.parseInt(subscribtion.getSubscriptionId()) );
			pStatement.registerOutParameter(3, java.sql.Types.INTEGER);
			pStatement.setInt(4,Integer.parseInt( subscribtion.getCustId()));
			pStatement.setInt(5, Integer.parseInt(subscribtion.getSubPlanId()));
			pStatement.setString(6, subscribtion.getMsgText());
			pStatement.setDouble(7, Double.parseDouble(subscribtion.getPayAmount()));
			pStatement.setString(8, strPayDate);
			pStatement.setString(9, subscribtion.getPayMode());
			pStatement.setString(10, subscribtion.getExpectedDeliveryDate());
			pStatement.setString(11, subscribtion.getDeliveryAddressId());
			
			pStatement.setString(12, subscribtion.getDeliveryAddress1());
			pStatement.setString(13, subscribtion.getDeliveryAddress2());
			pStatement.setString(14, subscribtion.getDeliveryAddressCity());
			pStatement.setString(15, subscribtion.getDeliveryAddressPinCode());
			pStatement.registerOutParameter(16, java.sql.Types.VARCHAR);
			
			System.out.println( subscribtion.getFlag()+"|"+
								subscribtion.getSubscriptionId()+"|"+
								subscribtion.getCustId()+"|"+
								subscribtion.getSubPlanId()+"|"+
								subscribtion.getMsgText()+"|"+
								subscribtion.getPayAmount()+"|"+
								subscribtion.getPayMode()+"|"+
								subscribtion.getExpectedDeliveryDate()+"|"+
								subscribtion.getDeliveryAddressId()+"|"+strPayDate);
			
			pStatement.executeUpdate();

			String subscriptionMstId = String.valueOf(pStatement.getInt(3));
			String pError = pStatement.getString(16);
			
			
			if (subscriptionMstId != "0") {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("RESULT", subscriptionMstId);
				JsonArray.put(jsonObject);
				resultJson.put("code", 1);
				resultJson.put("msg", "Success");
				resultJson.put("Result", JsonArray);
			}
			if(AllUtils.isNullOrEmpty(pError)==false) {
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("RESULT", "0");
				jsonObject.put("ERROR", pError);
				JsonArray.put(jsonObject);
				resultJson.put("code", 1);
				resultJson.put("msg", "FAIL");
				resultJson.put("Result", JsonArray);
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

	public String getUsersubcsriptions(String custId) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement=null;
				
		try {

			String query="select sm.Id,sm.PayAmt,sp.PlanName,sp.PlanCost,sp.NoOfBouquet, " + 
					"CAST(sp.ApplicableUpto AS DATE) ApplicableUpto,sd.ExpectedDelvDate  " + 
					"from Subscription_Details sd " + 
					" inner join Subscription_Master sm " + 
					" on sm.Id = sd.SubMstId " + 
					" inner join Subscription_Plan sp " + 
					" on sp.Id = sm.SubPlanId " + 
					"where sm.CustomerId="+custId+" " + 
//					"and CAST(GETDATE() AS DATE) <= CAST(sd.ExpectedDelvDate AS DATE) " + 
//					"ORDER BY sd.ExpectedDelvDate " + 
					"";
					
			System.out.println("query="+query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ID", resultSet.getString("Id"));
				//jsonObject.put("PLAN_AMT", resultSet.getString("PayAmt"));
				jsonObject.put("PLAN_NAME", resultSet.getString("PlanName"));
				jsonObject.put("PLAN_VALIDITY", resultSet.getString("ApplicableUpto"));
				jsonObject.put("EXP_DELV_DATE", resultSet.getString("ExpectedDelvDate"));
				jsonObject.put("NO_OF_BOUQUET", resultSet.getString("NoOfBouquet"));
				jsonObject.put("PRICE", resultSet.getString("PlanCost"));
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

					e.printStackTrace();
				}
		}
		return resultJson.toString();
	}

}
