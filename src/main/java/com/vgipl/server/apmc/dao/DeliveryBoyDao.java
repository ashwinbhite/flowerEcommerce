package com.vgipl.server.apmc.dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vgipl.server.apmc.model.DeliveryBoy;
import com.vgipl.server.apmc.utils.Base64EncoderDecoder;
import com.vgipl.server.apmc.utils.ConnectionSql;
import com.vgipl.server.apmc.utils.DbConstant;

@Service
public class DeliveryBoyDao {

	public String getOrderDetails(String orderId, String marketId) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		try {

			String query ="Select a.ID As OrderNo, Convert(VARCHAR(10),a.OrderDt, 103) As OrderDt, b.ID As Order_Dtls_ID,  " + 
					"	c.Name, c.Name_Hindi, c.Name_Marathi, b.Quantity, Dbo.FN_Get_Unit_Name(b.UnitID) As Unit, b.Amount, " + 
					"	a.Delv_Schedule, a.AddressID, d.Address1, d.Address2, d.City, d.PinCode, a.TotalAmount, b.Delv_Status " + 
					"From Order_Master a, Order_Details b, Product_Master c, Address_Details d " + 
					"Where a.ID = b.OrderID " + 
					"and b.ProductID = c.ID " + 
					"and a.AddressID = d.Id " + 
					"and a.OrderID = "+orderId +" " + 
					"and a.MarketID = "+ marketId+" " + 
					"and a.Published = 1 and b.Published = 1";

			System.out.println("ADDRESS query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ORDER_NO)", resultSet.getString("OrderNo"));
				jsonObject.put("ORDER_DATE", resultSet.getString("OrderDt"));
				jsonObject.put("ORDER_DETAILS_ID", resultSet.getString("Order_Dtls_ID"));
				jsonObject.put("NAME", resultSet.getString("Name"));
				jsonObject.put("HINDI_NAME", resultSet.getString("Name_Hindi"));
				jsonObject.put("MARATHI_NAME", resultSet.getString("Name_Marathi"));
				jsonObject.put("QTY", resultSet.getString("Quantity"));
				jsonObject.put("UNIT", resultSet.getString("Unit"));
				
				jsonObject.put("AMOUNT", resultSet.getString("Amount"));
				jsonObject.put("DELIVERY_SCH", resultSet.getString("Delv_Schedule"));
				jsonObject.put("ADD_ID", resultSet.getString("AddressID"));
				jsonObject.put("ADDRESS1", resultSet.getString("Address1"));
				jsonObject.put("ADDRESS2", resultSet.getString("Address2"));
				jsonObject.put("CITY", resultSet.getString("City"));
				jsonObject.put("PINCODE", resultSet.getString("PinCode"));
				jsonObject.put("TOT_AMOUNT", resultSet.getString("TotalAmount"));
				jsonObject.put("DELIVERY_STATUS", resultSet.getString("Delv_Status"));
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

	public String updateOrderDetails(String orderId, String deliveryStatus) {

		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		PreparedStatement statement=null;
		try {
			
			String query ="Update Order_Details Set Delv_Status = '"+ deliveryStatus +"' Where ID In (" + orderId+ ")";
			statement = connection.prepareStatement(query);
		
			
			int rowDeleted = statement.executeUpdate();
			
			if(rowDeleted>0) {
				
				resultJson.put("updated", true);	
				resultJson.put("code",1);
				resultJson.put("msg", "Success");
				resultJson.put("Result", JsonArray);
			}else {
				resultJson.put("updated", false);	
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

	public String getOrderIdList(String marketId) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		try {

			String query ="select a.OrderID from Order_Master a where a.MarketID='" + marketId+"'";

			System.out.println("ADDRESS query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ORDER_NO", resultSet.getString("OrderID"));
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

	public String getDeliveryCharges(Double weight, Double distance,Integer unitID) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		try {

			String query ="select dbo.FN_Get_Delv_Charges("+weight+","+distance+","+unitID+") as deliveryCharges";

			System.out.println("ADDRESS query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("DELIVERY_CHARGE", resultSet.getString("deliveryCharges"));
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

	public String doLoginDBoy(DeliveryBoy objDBoy) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		try {

			String query ="select a.Id,a.FirstName,a.LastName,a.MiddleName,a.MobileNo,a.Delv_Agency_ID,a.Vehicle_No,a.Driving_Lic_No from Delivery_Boy a where a.Email='"+objDBoy.getEmail()+"' and a.Login_Pwd='"+objDBoy.getMobNo()+"'";

			System.out.println("LoginDBoy query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("FNAME", resultSet.getString("FirstName"));
				jsonObject.put("MIDDLENAME", resultSet.getString("MiddleName"));
				jsonObject.put("LNAME", resultSet.getString("LastName"));
				jsonObject.put("ID", resultSet.getString("Id"));
			
				jsonObject.put("MOB_NO", resultSet.getString("MobileNo"));
				jsonObject.put("AGENCY_ID", resultSet.getString("Delv_Agency_ID"));
				jsonObject.put("VHE_NO", resultSet.getString("Vehicle_No"));
				jsonObject.put("LIC_NO", resultSet.getString("Driving_Lic_No"));

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

	public String getDeliveryOrdersList(String dBoyId) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		try {

			String query = /*
							 * "Select a.ID, " + "	convert(varchar(10),a.OrderDt,103) as OrderDt , " +
							 * "	a.PaymentType, " + "	a.TotalAmount, " + "	f.MobileNo, " +
							 * "	f.FirstName, " + "	f.LastName, " + "	e.Area_Nm, " +
							 * "	c.Address1, c.Address2, c.City, b.Delv_Status, a.Delv_Schedule  " +
							 * "from Order_Master a, Order_Details b, Address_Details c, DelvBoy_Area_Mapping d, Area_Master e, Customer f "
							 * + "Where a.ID = b.OrderID " + "and a.AddressID = c.Id " +
							 * "and a.CustomerID=f.Id " + "and a.Delv_Boy_ID='"+dBoyId+"' " + " " +
							 * "and d.Area_Mst_ID = e.ID " + "and c.PinCode = e.Pin_Code " +
							 * "and b.Delv_Status = 'U' " +
							 * "and a.Published = 1 and b.Published = 1 and d.Published =1";
							 */
			
			
			 
			" select  a.ID,a.PaymentType,a.TotalAmount ,convert(varchar(10),a.OrderDt,103) as OrderDt,a.Delv_Schedule, " + 
			" b.Delv_Status,c.Address1,c.Address2,c.City	,f.FirstName,f.LastName,f.MobileNo " + 
			" from Order_Master a,Order_Details b  ,Address_Details c,Customer f " + 
			" where a.Delv_Boy_ID='"+dBoyId+"' and a.Published=1 and a.ID=b.OrderID " + 
			" and b.Delv_Status='U' and a.AddressID=c.Id and a.CustomerID=f.Id ";

			System.out.println("LoginDBoy query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ORDER_ID", resultSet.getString("ID"));
				jsonObject.put("FULLNAME", resultSet.getString("FirstName")+" "+resultSet.getString("LastName"));
				jsonObject.put("MOB_NO", resultSet.getString("MobileNo"));
				jsonObject.put("PAY_TYPE", resultSet.getString("PaymentType"));
				jsonObject.put("TOT_AMT", resultSet.getString("TotalAmount"));
				jsonObject.put("DELIVERY_STATUS", resultSet.getString("Delv_Status"));
				
			//	jsonObject.put("AREA_NAME", resultSet.getString("Area_Nm"));
				jsonObject.put("ADDRESS1", resultSet.getString("Address1"));
				jsonObject.put("ADDRESS2", resultSet.getString("Address2"));
				jsonObject.put("CITY", resultSet.getString("City"));


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

	public String getDetailsByOrderId(String orderId) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		try {

			String query ="select CONVERT(VARCHAR(10),om.OrderDt,103) as ORDER_DATE, " + 
					"om.CustomerID,om.MarketID,om.TotalAmount,om.Delv_Schedule,om.PaymentType,od.Quantity,um.UnitName , " + 
					"od.Delv_Status,c.FirstName+' '+c.LastName as FULLNAME,c.MobileNo,p.Name,ad.Address1,ad.Address2   " + 
					" from Order_Master om,Order_Details od,Customer c,Product_Master p,Unit_Master um,Address_Details ad  " + 
					" where om.ID='"+orderId+"' " + 
					" and od.OrderID = om.ID " + 
					" and c.Id = om.CustomerID " + 
					" and od.ProductID=p.ID " + 
					" and od.UnitID=um.ID " + 
					" and ad.Id = om.AddressID;";

			System.out.println("detailOrder query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ORDER_DATE", resultSet.getString("ORDER_DATE"));
				jsonObject.put("TIME_TO_DELIVER", resultSet.getString("Delv_Schedule"));
				jsonObject.put("MOB_NO", resultSet.getString("MobileNo"));
				jsonObject.put("FULLNAME", resultSet.getString("FULLNAME"));
				jsonObject.put("PAY_TYPE", resultSet.getString("PaymentType"));
				jsonObject.put("TOT_AMT", resultSet.getString("TotalAmount"));
				jsonObject.put("DELIVERY_STATUS", resultSet.getString("Delv_Status"));
				jsonObject.put("ADDRESS1", resultSet.getString("Address1"));
				jsonObject.put("ADDRESS2", resultSet.getString("Address2"));
				//------LOOPING ITEMS
				jsonObject.put("QTY", resultSet.getString("Quantity"));
				jsonObject.put("UNIT", resultSet.getString("UnitName"));
				jsonObject.put("PRODUCT", resultSet.getString("NAME"));

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

	public String updateDeliveryStatus(String orderId) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER,DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		PreparedStatement statement=null;
		try {
			
			String query ="update Order_Details set Delv_Status = 'D' where OrderID='"+orderId+"'";
			statement = connection.prepareStatement(query);
		
			int rowDeleted = statement.executeUpdate();
			
			if(rowDeleted>0) {
				
				resultJson.put("updated", true);	
				resultJson.put("code",1);
				resultJson.put("msg", "Success");
				resultJson.put("Result", JsonArray);
			}else {
				resultJson.put("updated", false);	
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
	
	public String getKycStatus(String deliveryBoyId) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		try {

			String query ="select a.KYC_Status from Delivery_Boy a where a.Id='"+deliveryBoyId+"' ";

			System.out.println("KYC_Status query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("STATUS", resultSet.getString("KYC_Status"));
				

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
	
	public String genOtp(String mobileNo) {
		
		int randomPIN = (int)(Math.random()*9000)+1000;
		String Otp=String .valueOf(randomPIN);
		System.out.println("Otp---------"+Otp);
		
        JSONObject jsonObj=new JSONObject();
        try {
        	jsonObj.put("code", 1);
			jsonObj.put("msg", "Success");
			jsonObj.put("OTP", Otp);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

//			URL url = new URL("http://login.smsgatewayhub.com/api/mt/SendSMS?user=APMCJR&password=477348&senderid=APMCJR&channel=2&DCS=7&flashsms=0&number="+mobileNo+"&text="+Otp+"&route=7");
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			con.setRequestMethod("GET");
//			System.out.println("request hit"+url);
//			con.disconnect();
	
			final String uri = "http://login.smsgatewayhub.com/api/mt/SendSMS?user=APMCJR&password=477348&senderid=APMCJR&channel=2&DCS=7&flashsms=0&number="+"9665974013"+"&text="+Otp+"&route=7";
			RestTemplate restTemplate = new RestTemplate();
		    //String result = restTemplate.getForObject(uri, String.class);
		    JSONObject res = restTemplate.getForObject(uri,JSONObject.class);
		    String result = res.getString("ErrorMessage");
		    System.out.println(result);
		}
		catch (Exception e1)
		{
		e1.printStackTrace();	
		}
		System.out.println(jsonObj.toString());
		
		return jsonObj.toString();
	}

	public String verifyUploadingStatus(String dboyId) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		JSONObject statusObj = new JSONObject();
		Statement statement = null;
		String[] documentArray = {"license","addressProof","aadhar","photo"};
		
		try {

			for(int i=0;i<documentArray.length;i++) {
				
				File docFile = new File("E:\\Ashwin Workspace\\imageForWMkt\\"+documentArray[i]+"\\ " 
						+ dboyId+".jpg");

//						+ dboyId+"@"+documentArray[i]+".jpg");
				
				
				boolean doExist = docFile.exists();
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put(documentArray[i], doExist);
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
			}
			catch (JSONException e1) {
				e1.printStackTrace();
			}

		} finally {
			if (connection != null)
				try {
					
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return resultJson.toString();

	}

	public String getKycDetails(String dboyId) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		try {

			String query ="select * from KYC_Master where Delv_Boy_ID='"+dboyId+"' ";

			System.out.println("KYC_Status query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("NAME", resultSet.getString("FullName"));
				jsonObject.put("PHONE", resultSet.getString("Mobile_No"));


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
	
	
	public String getDboyPhoto(String dboyId) {
		
		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		JSONObject statusObj = new JSONObject();
		Statement statement = null;
		FileInputStream fileInputStream = null;

		try {
		
			File docFile = new File("E:\\Ashwin Workspace\\imageForWMkt\\photo\\ "+ dboyId+".jpg");
			
//			NOT WORKING**********************************************************
//	    	BufferedImage image = ImageIO.read(docFile);
//	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
//	    	ImageIO.write(image, "jpg", baos);
			//*******************************************************************
			
			FileInputStream fis = new FileInputStream(docFile);
	        //create FileInputStream which obtains input bytes from a file in a file system
	        //FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.
	 
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        byte[] buf = new byte[1024];
	       
	            for (int readNum; (readNum = fis.read(buf)) != -1;) {
	                //Writes to this byte array output stream
	                bos.write(buf, 0, readNum); 
	                System.out.println("read " + readNum + " bytes,");
	            }
	        			
	    	byte[] imageInByte = bos.toByteArray();
			
			String img=Base64.getEncoder().encodeToString(imageInByte);   
	
			System.out.println("img==="+img);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("PHOTO", img);
			JsonArray.put(jsonObject);
	
			resultJson.put("code", 1);
			resultJson.put("msg", "Success");
			resultJson.put("Result", JsonArray);

		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(DeliveryBoyDao.class.getName()).log(Level.SEVERE, null, e);
			try {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("code", 0);
				jsonObj.put("msg", "Error");
				JsonArray.put(jsonObj);
				System.out.println("error result===" + JsonArray.toString());
				resultJson.put("Result", JsonArray);
			}
			catch (JSONException e1) {
				e1.printStackTrace();
			}

		} 
		return resultJson.toString();
	}
	
}
