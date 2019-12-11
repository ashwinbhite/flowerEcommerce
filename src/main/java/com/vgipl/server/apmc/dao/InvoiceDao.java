package com.vgipl.server.apmc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.vgipl.server.apmc.model.Commodity;
import com.vgipl.server.apmc.model.Customer;
import com.vgipl.server.apmc.model.Invoice;
import com.vgipl.server.apmc.utils.AllUtils;
import com.vgipl.server.apmc.utils.ConnectionSql;
import com.vgipl.server.apmc.utils.DbConstant;
import com.vgipl.server.apmc.utils.HtmlBuilder;

@Service
public class InvoiceDao {

	public String getPdf(String orderId) {

		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		ResultSet firstResultSet=null;
		Statement statement = null;
		ArrayList<Commodity> prodList = new ArrayList<>();
		float finalTotal=0;
		Invoice invoice = new Invoice();
		Customer customer = new Customer();

		try {
			
			String query ="select a. AddressID,c.FullName,a.Delv_Schedule,d.Name,b.Quantity,b.UnitID,b.Rate, " + 
					"c.PinCode,(b.Amount-b.Rate) AS Discount, CAST(ROUND(b.Quantity * b.Rate,2) AS NUMERIC(12,2)) AS ItemTotal, " + 
					"b.Packing,c.Address1, " + 
					"c.EmailId,c.Address2,c.City,c.PinCode,c.MobileNo " + 
					"from Order_Master a,Order_Details b,Address_Details c,Product_Master d " + 
					" where a.OrderID='131' and a.OrderID=b.OrderID " + 
					" and a.AddressID=c.Id " + 
					" and b.ProductID=d.ID;";
			
			System.out.println("query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			firstResultSet = resultSet;
			
			while (resultSet.next()) {
				
				Commodity commodity = new Commodity();
				commodity.setCommodityName(resultSet.getString("Name"));
				commodity.setQty(resultSet.getString("Quantity"));
				commodity.setRate(resultSet.getString("Rate"));
				commodity.setDiscount(resultSet.getString("Discount"));
				commodity.setItemTotal(resultSet.getString("ItemTotal"));				
				prodList.add(commodity);	
				//final tot
				float temItemTot = Float.parseFloat(resultSet.getString("ItemTotal"));
				finalTotal = finalTotal+temItemTot;
				
				//Invoice invoice = new Invoice();
				invoice.setOrderId(orderId);
				invoice.setInvoiceNumber("****");
				invoice.setDeliverySlot(firstResultSet.getString("Delv_Schedule"));
				//invoice.setInvoiceDate(AllUtils.getCustomLotDate((new Date()).toString()));
				invoice.setInvoiceDate("2/12/2019");
				invoice.setInvoiceAmt(String.valueOf(finalTotal));
				
				//Customer customer = new Customer();
				customer.setCustomerName(firstResultSet.getString("FullName"));
				customer.setCustomerAddress1(firstResultSet.getString("Address1"));
				customer.setCustomerAddress2(firstResultSet.getString("Address2"));
				customer.setCustomerCity(firstResultSet.getString("City"));
				customer.setCustomerPin(firstResultSet.getString("PinCode"));
			}
			

			
//				Invoice invoice = new Invoice();
//				invoice.setOrderId(orderId);
//				invoice.setInvoiceNumber("****");
//				invoice.setDeliverySlot(firstResultSet.getString("Delv_Schedule"));
//				invoice.setInvoiceDate(AllUtils.getCustomLotDate((new Date()).toString()));
//				invoice.setInvoiceAmt(String.valueOf(finalTotal));
//				
//				Customer customer = new Customer();
//				customer.setCustomerName(firstResultSet.getString("FullName"));
//				customer.setCustomerAddress1(firstResultSet.getString("Address1"));
//				customer.setCustomerAddress2(firstResultSet.getString("Address2"));
//				customer.setCustomerCity(firstResultSet.getString("City"));
//				customer.setCustomerPin(firstResultSet.getString("PinCode"));
				//customer.setCustomerMobile(resultSet.getString("MobileNo"));
				HtmlBuilder.InvoiceHtmlBuilder(invoice, customer, prodList);
				
			
			
			
			
		}catch (Exception e) {
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

}
