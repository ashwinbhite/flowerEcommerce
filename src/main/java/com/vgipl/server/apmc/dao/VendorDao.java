package com.vgipl.server.apmc.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.vgipl.server.apmc.model.DeliveryAgency;
import com.vgipl.server.apmc.model.DeliveryBoy;
import com.vgipl.server.apmc.model.Help;
import com.vgipl.server.apmc.model.Inward;
import com.vgipl.server.apmc.model.KYC;
import com.vgipl.server.apmc.model.UserImageData;
import com.vgipl.server.apmc.utils.AllUtils;
import com.vgipl.server.apmc.utils.ConnectionSql;
import com.vgipl.server.apmc.utils.DbConstant;

@Service
public class VendorDao {

	public String insertProduct(Help objHelp) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {

			String query = "INSERT INTO [CounterSaleTest].[dbo].[Product_Master] " + "           ([CategoryId] "
					+ "           ,[Name] " + "           ,[UnitID] " + "           ,[Published] "
					+ "           ,[CreatedOnUTC] " + "           ,[UpdatedOnUTC] " + "           ,[Name_Hindi] "
					+ "           ,[Name_Marathi] " + "           ,[Alt_UnitID] " + "           ,[Addl_Rate] "
					+ "           ,[Qty_Conv_Factor] " + "			,[CGST_Per] " + "           ,[SGST_Per] "
					+ "           ,[IGST_Per] " + "           ,[GST_Applicable]) " + "     VALUES " + "           ("
					+ objHelp.getCategory() + "" + "           ,'" + objHelp.getProductName() + "' " + "           ,"
					+ objHelp.getUnit() + " " + "           ,1 " + "           ,GETDATE() " + "           ,GETDATE() "
					+ "           ,N'" + objHelp.getHindiName() + "' " + "           ,N'" + objHelp.getRegionalName()
					+ "' " + "           ," + objHelp.getUnitId() + " " + "           ," + objHelp.getAdditionalRate()
					+ " " + "           ," + objHelp.getConversionFactor() + " " + "           ,"
					+ objHelp.getCgstPercentage() + " " + "           ," + objHelp.getSgstPercentage() + " "
					+ "           ," + objHelp.getIgstPercentage() + " " + "           ,'" + objHelp.getGstApplicable()
					+ " ' )";

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

	public String inwardEntry(Inward objInward) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);
		System.out.println("parmeters of inward entry====" + objInward.getInwardId());

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		CallableStatement pStatement = null;
		try {
			String SPsql = "{ call Pr_Inward_Entry(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) } ";

			pStatement = connection.prepareCall(SPsql);

			pStatement.setString(1, objInward.getInwardFlag());
			pStatement.setInt(2, Integer.parseInt(objInward.getInwardId()));
			pStatement.registerOutParameter(2, java.sql.Types.INTEGER);

			pStatement.setString(3, "");
			pStatement.setInt(4, Integer.parseInt(objInward.getVendorId()));
			pStatement.setInt(5, 0);
			pStatement.setInt(6, 0);
			pStatement.setInt(7, Integer.parseInt(objInward.getProductId()));
			pStatement.setDouble(8, Double.parseDouble(objInward.getQuantity()));
			pStatement.setInt(9, Integer.parseInt(objInward.getUnitId()));
			pStatement.setDouble(10, 0);
			pStatement.setInt(11, 0);
			pStatement.setDouble(12, Double.parseDouble(objInward.getRate()));
			pStatement.setDouble(13, Double.parseDouble(objInward.getAmount()));
			pStatement.setBoolean(14, true);
			pStatement.setBigDecimal(15, new BigDecimal(objInward.getSellingPrice()));
			pStatement.setBigDecimal(16, new BigDecimal(objInward.getOfferPrice()));
			pStatement.setBoolean(17, true);
			pStatement.setString(18, objInward.getAttriGrpName());
			pStatement.setInt(19, Integer.parseInt(objInward.getStoreId()));
			pStatement.executeUpdate();

			String inwardNo = String.valueOf(pStatement.getInt(2));
			if (inwardNo != null) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("INWARD_NO", inwardNo);
				JsonArray.put(jsonObject);
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

	public String getStock(String marketId) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		try {

			String query = "Select  a.VendorID, a.MarketID, "
					+ "a.ProductID, c.Name as Product_Name, c.Name_Hindi as product_Name_Hindi, "
					+ "c.Name_Marathi as product_name_marathi, b.SellingRate As MRP, a.UnitID as Mrp_Unit, "
					+ "dbo.FN_Get_Unit_Name(a.UnitID) as Mrp_Unit_Name, b.Offer_Price, Min(a.Min_Qty) Min_Qty, a.Min_Qty_Unit, "
					+ "dbo.FN_Get_Unit_Name(a.Min_Qty_Unit) as Min_Qty_Unit_Name, B.Offer_Price, c.Alt_UnitID, "
					+ "dbo.FN_Get_Unit_Name(c.Alt_UnitID) as Alt_Unit_Name, SUM(a.Quantity) as Stock, c.Qty_Conv_Factor,c.Addl_Rate "
					+ " From Inward_Master a, Product_Price b, Product_Master c Where a.ProductID = b.ProductID "
					+ " and a.MarketID = b.MarketID and a.VendorID = b.VendorID "
					+ " and a.ProductID = c.ID and a.MarketID = CASE WHEN '" + marketId
					+ "' IS NULL THEN a.MarketID ELSE '" + marketId + "' END "
					+ " and b.SellingRate in(select MAX(Sellingrate) from Product_Price pc where pc.ProductID = b.ProductID "
					+ "			   and pc.MarketID = b.MarketID and pc.VendorID = b.VendorID) "
					+ "  GROUP BY a.VendorID, a.MarketID,c.Qty_Conv_Factor,  "
					+ "  c.Addl_Rate,  a.ProductID, c.Name, c.Name_Hindi, c.Name_Marathi, b.SellingRate, a.UnitID, "
					+ "  dbo.FN_Get_Unit_Name(a.UnitID), b.Offer_Price, a.Min_Qty_Unit, "
					+ "  dbo.FN_Get_Unit_Name(a.Min_Qty_Unit), B.Offer_Price, c.Alt_UnitID,  dbo.FN_Get_Unit_Name(c.Alt_UnitID) "
					+ "  ORDER BY a.VendorID,a.MarketID,b.Offer_Price;";

			System.out.println("query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				JSONObject jsonObject = new JSONObject();
				// jsonObject.put("INWARD_DATE", resultSet.getString("InwardDt"));
				jsonObject.put("VENDOR_ID", resultSet.getString("VendorID"));
				jsonObject.put("MARKET_ID", resultSet.getString("MarketID"));
				jsonObject.put("PRODUCT_ID", resultSet.getString("ProductId"));
				jsonObject.put("PRODUCT_NAME", resultSet.getString("Product_Name"));
				jsonObject.put("HINDI_NAME", resultSet.getString("product_Name_Hindi"));
				jsonObject.put("MARATHI_NAME", resultSet.getString("product_name_marathi"));
				jsonObject.put("RATE", resultSet.getString("MRP"));
				jsonObject.put("UNIT_NAME", resultSet.getString("Mrp_Unit_Name"));
				jsonObject.put("UNIT", resultSet.getString("Mrp_Unit"));
				jsonObject.put("CONVERSION_FACTOR", resultSet.getString("Qty_Conv_Factor"));
				jsonObject.put("ADDITIONAL_RATE", resultSet.getString("Addl_Rate"));
				jsonObject.put("OFFER_PRICE", resultSet.getString("Offer_Price"));
				jsonObject.put("MIN_QTY", resultSet.getString("Min_Qty"));
				jsonObject.put("MIN_QTY_UNIT", resultSet.getString("Min_Qty_Unit"));
				jsonObject.put("MIN_QTY_UNIT_NAME", resultSet.getString("Min_Qty_Unit_Name"));
				jsonObject.put("OFFER_PRICE", resultSet.getString("Offer_Price"));
				jsonObject.put("ALT_UNITID", resultSet.getString("Alt_UnitID"));
				jsonObject.put("ALT_UNIT_NAME", resultSet.getString("Alt_Unit_Name"));
				jsonObject.put("STOCK", resultSet.getString("STOCK"));

				JsonArray.put(jsonObject);
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
					resultSet.close();
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

	public String getStorewiseStock(String marketId, String storeId) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		try {

			String query = "Select  a.VendorID, a.MarketID, "
					+ "a.ProductID, c.Name as Product_Name, c.Name_Hindi as product_Name_Hindi, "
					+ "c.Name_Marathi as product_name_marathi, b.SellingRate As MRP, a.UnitID as Mrp_Unit, "
					+ "dbo.FN_Get_Unit_Name(a.UnitID) as Mrp_Unit_Name, b.Offer_Price, Min(a.Min_Qty) Min_Qty, a.Min_Qty_Unit, "
					+ "dbo.FN_Get_Unit_Name(a.Min_Qty_Unit) as Min_Qty_Unit_Name, B.Offer_Price, c.Alt_UnitID, "
					+ "dbo.FN_Get_Unit_Name(c.Alt_UnitID) as Alt_Unit_Name, SUM(a.Quantity) as Stock, c.Qty_Conv_Factor,c.Addl_Rate "
					+ " From Inward_Master a, Product_Price b, Product_Master c Where  a.Store_ID='" + storeId
					+ "' and a.ProductID = b.ProductID " + " and a.MarketID = b.MarketID and a.VendorID = b.VendorID "
					+ " and a.ProductID = c.ID and a.MarketID = CASE WHEN '" + marketId
					+ "' IS NULL THEN a.MarketID ELSE '" + marketId + "' END "
					+ " and b.SellingRate in(select MAX(Sellingrate) from Product_Price pc where pc.ProductID = b.ProductID "
					+ "			   and pc.MarketID = b.MarketID and pc.VendorID = b.VendorID) "
					+ "  GROUP BY a.VendorID, a.MarketID,c.Qty_Conv_Factor,  "
					+ "  c.Addl_Rate,  a.ProductID, c.Name, c.Name_Hindi, c.Name_Marathi, b.SellingRate, a.UnitID, "
					+ "  dbo.FN_Get_Unit_Name(a.UnitID), b.Offer_Price, a.Min_Qty_Unit, "
					+ "  dbo.FN_Get_Unit_Name(a.Min_Qty_Unit), B.Offer_Price, c.Alt_UnitID,  dbo.FN_Get_Unit_Name(c.Alt_UnitID) "
					+ "  ORDER BY a.VendorID,a.MarketID,b.Offer_Price;";

			System.out.println("query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				JSONObject jsonObject = new JSONObject();
				// jsonObject.put("INWARD_DATE", resultSet.getString("InwardDt"));
				jsonObject.put("VENDOR_ID", resultSet.getString("VendorID"));
				jsonObject.put("MARKET_ID", resultSet.getString("MarketID"));
				jsonObject.put("PRODUCT_ID", resultSet.getString("ProductId"));
				jsonObject.put("PRODUCT_NAME", resultSet.getString("Product_Name"));
				jsonObject.put("HINDI_NAME", resultSet.getString("product_Name_Hindi"));
				jsonObject.put("MARATHI_NAME", resultSet.getString("product_name_marathi"));
				jsonObject.put("RATE", resultSet.getString("MRP"));
				jsonObject.put("UNIT_NAME", resultSet.getString("Mrp_Unit_Name"));
				jsonObject.put("UNIT", resultSet.getString("Mrp_Unit"));
				jsonObject.put("CONVERSION_FACTOR", resultSet.getString("Qty_Conv_Factor"));
				jsonObject.put("ADDITIONAL_RATE", resultSet.getString("Addl_Rate"));
				jsonObject.put("OFFER_PRICE", resultSet.getString("Offer_Price"));
				jsonObject.put("MIN_QTY", resultSet.getString("Min_Qty"));
				jsonObject.put("MIN_QTY_UNIT", resultSet.getString("Min_Qty_Unit"));
				jsonObject.put("MIN_QTY_UNIT_NAME", resultSet.getString("Min_Qty_Unit_Name"));
				jsonObject.put("OFFER_PRICE", resultSet.getString("Offer_Price"));
				jsonObject.put("ALT_UNITID", resultSet.getString("Alt_UnitID"));
				jsonObject.put("ALT_UNIT_NAME", resultSet.getString("Alt_Unit_Name"));
				jsonObject.put("STOCK", resultSet.getString("STOCK"));

				JsonArray.put(jsonObject);
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
					resultSet.close();
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

	public String getproductAttribute(Inward objInward) {

		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;

		Statement statement = null;

		try {

			String query = "select x.*,  CAST(x.Selling_Qty as varchar(10)) Qty ,y.UnitName,[dbo].[FN_Get_Inwardwise_Stock](x.InwardID) Balance_Stock "
					+ " from Product_Price x, Unit_Master y " + " where x.UnitID = y.ID and x.InwardID = "
					+ objInward.getInwardId() + " and x.ProductID = " + objInward.getProductId() + "";

			System.out.println("query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("RATE", resultSet.getString("Rate"));
				jsonObject.put("PRODUCT_PRICE_ID", resultSet.getString("ID"));

				jsonObject.put("QTY", resultSet.getString("Qty"));
				jsonObject.put("UNIT", resultSet.getString("UnitName"));
				jsonObject.put("STOCK", resultSet.getString("Balance_Stock"));
				JsonArray.put(jsonObject);
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
					resultSet.close();
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

	public String getCompleteStockList() {

		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;

		Statement statement = null;

		try {

			String query = "Select  CONVERT(VARCHAR(10), a.InwardDt, 103) As InwardDt, a.VendorID, a.MarketID, "
					+ "a.ProductID, c.Name as Product_Name, c.Name_Hindi as product_Name_Hindi, "
					+ "c.Name_Marathi as product_name_marathi, b.SellingRate As MRP, a.UnitID as Mrp_Unit, "
					+ "dbo.FN_Get_Unit_Name(a.UnitID) as Mrp_Unit_Name, b.Offer_Price, a.Min_Qty, a.Min_Qty_Unit, "
					+ "dbo.FN_Get_Unit_Name(a.Min_Qty_Unit) as Min_Qty_Unit_Name, B.Offer_Price, c.Alt_UnitID, "
					+ "dbo.FN_Get_Unit_Name(c.Alt_UnitID) as Alt_Unit_Name, SUM(a.Quantity) as Stock, c.Qty_Conv_Factor,c.Addl_Rate "
					+ " From Inward_Master a, Product_Price b, Product_Master c Where a.ProductID = b.ProductID "
					+ " and a.MarketID = b.MarketID and a.VendorID = b.VendorID " + " and a.ProductID = c.ID  "
					+ " and b.SellingRate in(select MAX(Sellingrate) from Product_Price pc where pc.ProductID = b.ProductID "
					+ "			   and pc.MarketID = b.MarketID and pc.VendorID = b.VendorID) "
					+ "  GROUP BY CONVERT(VARCHAR(10), a.InwardDt, 103), a.VendorID, a.MarketID,c.Qty_Conv_Factor,  "
					+ "  c.Addl_Rate,  a.ProductID, c.Name, c.Name_Hindi, c.Name_Marathi, b.SellingRate, a.UnitID, "
					+ "  dbo.FN_Get_Unit_Name(a.UnitID), b.Offer_Price, a.Min_Qty, a.Min_Qty_Unit, "
					+ "  dbo.FN_Get_Unit_Name(a.Min_Qty_Unit), B.Offer_Price, c.Alt_UnitID,  dbo.FN_Get_Unit_Name(c.Alt_UnitID) "
					+ "  ORDER BY a.VendorID,a.MarketID;";

			System.out.println("query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("INWARD_DATE", resultSet.getString("InwardDt"));
				jsonObject.put("VENDOR_ID", resultSet.getString("VendorID"));
				jsonObject.put("MARKET_ID", resultSet.getString("MarketID"));
				jsonObject.put("PRODUCT_ID", resultSet.getString("ProductId"));
				jsonObject.put("PRODUCT_NAME", resultSet.getString("Product_Name"));
				jsonObject.put("HINDI_NAME", resultSet.getString("product_Name_Hindi"));
				jsonObject.put("MARATHI_NAME", resultSet.getString("product_name_marathi"));
				jsonObject.put("RATE", resultSet.getString("MRP"));
				jsonObject.put("UNIT_NAME", resultSet.getString("Mrp_Unit_Name"));
				jsonObject.put("UNIT", resultSet.getString("Mrp_Unit"));
				jsonObject.put("CONVERSION_FACTOR", resultSet.getString("Qty_Conv_Factor"));
				jsonObject.put("ADDITIONAL_RATE", resultSet.getString("Addl_Rate"));
				jsonObject.put("OFFER_PRICE", resultSet.getString("Offer_Price"));
				jsonObject.put("MIN_QTY", resultSet.getString("Min_Qty"));
				jsonObject.put("MIN_QTY_UNIT", resultSet.getString("Min_Qty_Unit"));
				jsonObject.put("MIN_QTY_UNIT_NAME", resultSet.getString("Min_Qty_Unit_Name"));
				jsonObject.put("OFFER_PRICE", resultSet.getString("Offer_Price"));
				jsonObject.put("ALT_UNITID", resultSet.getString("Alt_UnitID"));
				jsonObject.put("ALT_UNIT_NAME", resultSet.getString("Alt_Unit_Name"));
				jsonObject.put("STOCK", resultSet.getString("STOCK"));

				JsonArray.put(jsonObject);
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
					resultSet.close();
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

	public String getInwardDetails(String vendorId) {

		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;

		Statement statement = null;

		try {

			String query = "select a.InwardID,a.ID as InwradNo,a.MarketID,a.VendorID, a.FarmerID, c.FirstName, c.LastName, a.ProductID, d.Name, d.Name_Hindi, d.Name_Marathi, "
					+ "	a.Quantity, e.UnitName, a.Rate as Purc_Rate, a.Amount, ISNULL(pp.SellingRate,0) as Sale_Rate,pp.Offer_Price ,e.UnitName as Sale_Unit "
					+ "from Inward_Master a " + "     INNER JOIN Customer b " + "     ON a.FarmerID = b.Id "
					+ "     INNER JOIN \"Address\" c " + "     ON c.Id = b.BillingAddress_Id "
					+ "     INNER JOIN Product_Master d " + "     ON d.ID = a.ProductID "
					+ "     INNER JOIN Unit_Master e " + "     ON e.ID = a.UnitID "
					+ "     LEFT OUTER JOIN Product_Price pp " + "     ON PP.ID = a.ID "
					+ "where Convert (VARCHAR(10),a.InwardDt, 103) = Convert (VARCHAR(10),GETDATE(), 103) "
					+ "and a.Published = 1 and a.VendorID ='" + vendorId + "'";

			System.out.println("query:getInwardDetails=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("INWARD_ID", resultSet.getString("InwardID"));
				jsonObject.put("INWARD_NO", resultSet.getString("InwradNo"));
				jsonObject.put("VENDOR_ID", resultSet.getString("VendorID"));
				jsonObject.put("MARKRT_ID", resultSet.getString("MarketID"));
				jsonObject.put("FARMER_ID", resultSet.getString("FarmerID"));
				jsonObject.put("FIRST_NAME", resultSet.getString("FirstName"));
				jsonObject.put("LASTNAME", resultSet.getString("LastName"));
				jsonObject.put("PRODUCT_ID", resultSet.getString("ProductID"));
				jsonObject.put("NAME", resultSet.getString("Name"));
				jsonObject.put("HINDI_NAME", resultSet.getString("Name_Hindi"));
				jsonObject.put("MARATHI_NAME", resultSet.getString("Name_Marathi"));
				jsonObject.put("QTY", resultSet.getString("Quantity"));
				jsonObject.put("UNIT_NAME", resultSet.getString("UnitName"));
				jsonObject.put("PURCHASE_RATE", resultSet.getString("Purc_Rate"));
				jsonObject.put("AMOUNT", resultSet.getString("Amount"));
				jsonObject.put("SALE_RATE", resultSet.getString("Sale_Rate"));
				jsonObject.put("OFFER_RATE", resultSet.getString("Offer_Price"));
				jsonObject.put("SALE_UNIT", resultSet.getString("Sale_Unit"));

				JsonArray.put(jsonObject);
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
					resultSet.close();
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

	public String doProductPriceEntry(Inward objInward) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		CallableStatement cStatement = null;
		try {

			String SPsql = "{ call Pr_Product_Price_Entry(?,?,?,?,?) } ";
			cStatement = connection.prepareCall(SPsql);
			cStatement.setString(1, objInward.getInwardFlag());
			cStatement.setInt(2, Integer.parseInt(objInward.getInwardNo()));
			cStatement.setBigDecimal(3, new BigDecimal(objInward.getRate()));
			cStatement.setBigDecimal(4, new BigDecimal(objInward.getOfferPrice()));
			System.out.println("flag=" + objInward.getInwardFlag() + ",inwardNo=" + objInward.getInwardNo() + ",rate="
					+ objInward.getRate() + ",offer=" + objInward.getOfferPrice());
			cStatement.registerOutParameter(5, java.sql.Types.VARCHAR);
			cStatement.executeUpdate();

			String pError = String.valueOf(cStatement.getString(5));

			if (pError.equals(null) || pError.equals("") || pError.equals("null")) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("success", 1);
				JsonArray.put(jsonObject);

			} else {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("success", 0);
				jsonObject.put("error", pError);
				JsonArray.put(jsonObject);
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

					cStatement.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println("final result " + resultJson.toString());
		return resultJson.toString();
	}

	public String getOrderResport(String vendorId) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;

		Statement statement = null;

		try {

			String query = "SELECT om.ID,convert(varchar(10),om.OrderDt,103) as OrderDt ,om.PaymentType,om.TotalAmount,c.FirstName,c.LastName "
					+ "from Order_Master om,Customer c " + "where om.VendorID='" + vendorId
					+ "' and om.CustomerID=c.Id";

			System.out.println("query:getOrderResport=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ORDER_ID", resultSet.getString("ID"));
				jsonObject.put("ORDER_DATE", resultSet.getString("OrderDt"));
				jsonObject.put("TOT_AMT", resultSet.getString("TotalAmount"));

				jsonObject.put("PAYMENT_TYPE", resultSet.getString("PaymentType"));
				jsonObject.put("FNAME", resultSet.getString("FirstName"));
				jsonObject.put("LNAME", resultSet.getString("LastName"));

				JsonArray.put(jsonObject);
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

	public String getStockBycategory(String attributeId) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		try {

			String query = "Select   " + "	a.VendorID,a.ProductID, c.Name as Product_Name,  "
					+ "	b.SellingRate As MRP, a.UnitID as Mrp_Unit,  "
					+ "	dbo.FN_Get_Unit_Name(a.UnitID) as Mrp_Unit_Name, b.Offer_Price, Min(a.Min_Qty) Min_Qty, a.Min_Qty_Unit,  "
					+ "	dbo.FN_Get_Unit_Name(a.Min_Qty_Unit) as Min_Qty_Unit_Name, B.Offer_Price, c.Alt_UnitID,  "
					+ "	dbo.FN_Get_Unit_Name(c.Alt_UnitID) as Alt_Unit_Name, SUM(a.Quantity) as Stock, c.Qty_Conv_Factor,c.Addl_Rate  "
					+ "From Inward_Master a, Product_Price b, Product_Master c, Attribute_Mapping d  "
					+ "Where a.ProductID = b.ProductID and a.MarketID = b.MarketID and a.VendorID = b.VendorID  "
					+ "	and a.ProductID = c.ID and a.MarketID = CASE WHEN '0' IS NULL THEN a.MarketID ELSE '0' END  "
					+ "	and b.SellingRate in(select MAX(Sellingrate) from Product_Price pc where pc.ProductID = b.ProductID  "
					+ "								   and pc.MarketID = b.MarketID and pc.VendorID = b.VendorID)  "
					+ "	and c.ID = d.Product_ID " + "	and d.Attrib_ID = " + attributeId + " "
					+ "GROUP BY a.VendorID, a.MarketID,c.Qty_Conv_Factor, c.Addl_Rate, a.ProductID, c.Name, b.SellingRate, a.UnitID,  "
					+ "	dbo.FN_Get_Unit_Name(a.UnitID), b.Offer_Price, a.Min_Qty_Unit, dbo.FN_Get_Unit_Name(a.Min_Qty_Unit), B.Offer_Price, c.Alt_UnitID,  dbo.FN_Get_Unit_Name(c.Alt_UnitID)  "
					+ "ORDER BY a.VendorID,a.MarketID;";

			System.out.println("query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("VENDOR_ID", resultSet.getString("VendorID"));
				jsonObject.put("PRODUCT_ID", resultSet.getString("ProductId"));
				jsonObject.put("PRODUCT_NAME", resultSet.getString("Product_Name"));
				jsonObject.put("RATE", resultSet.getString("MRP"));
				jsonObject.put("UNIT_NAME", resultSet.getString("Mrp_Unit_Name"));
				jsonObject.put("UNIT", resultSet.getString("Mrp_Unit"));
				jsonObject.put("CONVERSION_FACTOR", resultSet.getString("Qty_Conv_Factor"));
				jsonObject.put("ADDITIONAL_RATE", resultSet.getString("Addl_Rate"));
				jsonObject.put("OFFER_PRICE", resultSet.getString("Offer_Price"));
				jsonObject.put("STOCK", resultSet.getString("STOCK"));

				JsonArray.put(jsonObject);
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
					resultSet.close();
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

	public String createDeliveryBoy(DeliveryBoy objDeliveryBoy) {

		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		CallableStatement cStatement = null;
		try {

			String query = "{call Pr_Delivery_Boy(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			cStatement = connection.prepareCall(query);

			cStatement.setString(1, objDeliveryBoy.getOpFlag());
			
			cStatement.setInt(2, 0);
			cStatement.setInt(3, Integer.parseInt(objDeliveryBoy.getDeliveryAgencyId()));
			cStatement.setString(4, objDeliveryBoy.getfName());
			cStatement.setString(5, objDeliveryBoy.getmName());
			cStatement.setString(6, objDeliveryBoy.getlName());
			cStatement.setString(7, objDeliveryBoy.getEmail());
			cStatement.setBoolean(8, objDeliveryBoy.isActive());

			cStatement.setString(9, objDeliveryBoy.getMobNo());
			cStatement.setString(10, objDeliveryBoy.getVheicalNo());
			cStatement.setBoolean(11, objDeliveryBoy.isDrivingLicenseFlag());
			cStatement.setString(12, objDeliveryBoy.getDrivingLicNo());
			cStatement.setString(13, objDeliveryBoy.getVheicalType());
			cStatement.setString(14, objDeliveryBoy.getVheicalModel());
			cStatement.setBoolean(15, objDeliveryBoy.isInsuranceFlag());
			cStatement.setString(16, objDeliveryBoy.getInsuranceExpDate());
	    	cStatement.registerOutParameter(17, java.sql.Types.INTEGER);
			cStatement.registerOutParameter(18, java.sql.Types.VARCHAR);
			cStatement.setString(19, objDeliveryBoy.getArea());
			cStatement.setString(20, objDeliveryBoy.getPinCode());
			System.out.println("qualificatin and language"+objDeliveryBoy.getQualification()+"-----"+objDeliveryBoy.getLangKnown());
			cStatement.setString(21, objDeliveryBoy.getQualification());
			cStatement.setString(22, objDeliveryBoy.getLangKnown());
			cStatement.executeUpdate();
			String OutId = String.valueOf(cStatement.getInt(17));

			if (OutId != null) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("OUT_ID", OutId);
				JsonArray.put(jsonObject);
				resultJson.put("code", 1);
				resultJson.put("msg", "Success");
				resultJson.put("Result", JsonArray);
			} else {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Error", cStatement.getString(18));
				JsonArray.put(jsonObject);
				resultJson.put("code", 0);
				resultJson.put("msg", "Error");
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
					cStatement.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println("DeliveryBoy result----" + resultJson.toString());
		return resultJson.toString();

	}

	public String createDeliveryAgency(DeliveryAgency objDeliveryAgency) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		CallableStatement cStatement = null;
		try {

			String query = "{ call Pr_Delivery_Agency(?,?,?,?,?,?,?,?,?,?,?)}";
			cStatement = connection.prepareCall(query);

			cStatement.setString(1, objDeliveryAgency.getOpFlag());
			System.out.println(objDeliveryAgency.getOpFlag());
			cStatement.setInt(2, 0);
			cStatement.setString(3, objDeliveryAgency.getAgencyName());
			System.out.println(objDeliveryAgency.getAgencyName());
			cStatement.setString(4, objDeliveryAgency.getEmail());
			cStatement.setInt(5, Integer.parseInt(objDeliveryAgency.getMktId()));
			System.out.println(objDeliveryAgency.getMktId());
			cStatement.setBoolean(6, objDeliveryAgency.isActive());
			cStatement.setString(7, objDeliveryAgency.getfName());
			System.out.println(objDeliveryAgency.getfName());
			cStatement.setString(8, objDeliveryAgency.getlName());
			System.out.println(objDeliveryAgency.getlName());
			cStatement.setString(9, objDeliveryAgency.getMobNo());
			System.out.println(objDeliveryAgency.getMobNo());
			cStatement.registerOutParameter(10, java.sql.Types.INTEGER);
			cStatement.registerOutParameter(11, java.sql.Types.VARCHAR);
			cStatement.executeUpdate();
			String OutId = String.valueOf(cStatement.getInt(10));

			if (OutId != null) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("OUT_ID", OutId);
				JsonArray.put(jsonObject);
				resultJson.put("code", 1);
				resultJson.put("msg", "Success");
				resultJson.put("Result", JsonArray);
			} else {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Error", cStatement.getString(11));
				JsonArray.put(jsonObject);
				resultJson.put("code", 0);
				resultJson.put("msg", "Error");
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
					cStatement.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println("DeliveryAgency result----" + resultJson.toString());
		return resultJson.toString();

	}

	public String postKycDetails(KYC objKyc) {

		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		CallableStatement cStatement = null;

		try {

			String query = "{CALL  Pr_Kyc_Master(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
			cStatement = connection.prepareCall(query);

			cStatement.setString(1, objKyc.getFlag());
			cStatement.setInt(2, 0);

			cStatement.setString(3, objKyc.getFullName());
			System.out.println("full name=" + objKyc.getFullName());

			cStatement.setString(4, objKyc.getFathersName());
			System.out.println("father name" + objKyc.getFathersName());

			cStatement.setString(5, objKyc.getGender());
			System.out.println("gender" + objKyc.getGender());

			cStatement.setString(6, objKyc.getMaritalStat());
			System.out.println("marital stat=" + objKyc.getMaritalStat());

			cStatement.setString(7, objKyc.getDob());
			System.out.println("Birthdate=" + objKyc.getDob());

			cStatement.setString(8, objKyc.getPanNumber());
			System.out.println("PAN=" + objKyc.getPanNumber());

			cStatement.setString(9, objKyc.getAdharNmbr());
			System.out.println("ADHAR=" + objKyc.getAdharNmbr());

			cStatement.setString(10, objKyc.getMobileNo());
			System.out.println("MOB=" + objKyc.getMobileNo());

			cStatement.setInt(11, Integer.parseInt(objKyc.getResAddressId()));
			System.out.println("ADDR ID=" + objKyc.getResAddressId());

			cStatement.setInt(12, Integer.parseInt(objKyc.getPermAddressId()));
			System.out.println("PERM ID=" + objKyc.getPermAddressId());

			cStatement.setBoolean(13, objKyc.isActive());
			System.out.println("ACTI=" + objKyc.isActive());

			cStatement.setInt(14, Integer.parseInt(objKyc.getCustId()));
			System.out.println("CUST ID=" + objKyc.getCustId());

			cStatement.setString(15, objKyc.getRessidentialAddress());
			System.out.println("RES ADD=" + objKyc.getRessidentialAddress());

			cStatement.setString(16, objKyc.getPermanantAddress());
			System.out.println("PERM ADD=" + objKyc.getPermanantAddress());

			cStatement.registerOutParameter(17, java.sql.Types.INTEGER);
			cStatement.registerOutParameter(18, java.sql.Types.VARCHAR);
			cStatement.executeUpdate();
			String OutId = String.valueOf(cStatement.getInt(18));

			if (OutId != null) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("OUT_ID", OutId);
				JsonArray.put(jsonObject);
				resultJson.put("code", 1);
				resultJson.put("msg", "Success");
				resultJson.put("Result", JsonArray);
			} else {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Error", cStatement.getString(11));
				JsonArray.put(jsonObject);
				resultJson.put("code", 0);
				resultJson.put("msg", "Error");
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
					cStatement.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println("DeliveryAgency result----" + resultJson.toString());
		return resultJson.toString();
	}

	public String getDeliveryAgencyDetails() {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		try {

			String query = "select a.Id,a.Agency_Name from Delivery_Agency a where a.Active='1' and a.Deleted='0'";

			System.out.println("query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				JSONObject jsonObject = new JSONObject();

				jsonObject.put("AGENCY_ID", resultSet.getString("Id"));
				jsonObject.put("AGENCY_NAME", resultSet.getString("Agency_Name"));

				JsonArray.put(jsonObject);
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
					resultSet.close();
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

	public String getAreaHelp() {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		try {

			String query = "select *  from Area_Master a where  a.Published='1' ";

			System.out.println("query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				JSONObject jsonObject = new JSONObject();

				jsonObject.put("AREA", resultSet.getString("Area_Nm"));
				jsonObject.put("PIN_CODE", resultSet.getString("Pin_Code"));

				JsonArray.put(jsonObject);
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
					resultSet.close();
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

	public String getDetailReport(String orderId) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		try {

			String query = "SELECT OM.OrderID AS ORDER_NO,OM.Delv_Schedule,c.FirstName + ' ' + c.LastName As CustName, c.MobileNo, "
					+ "AD.Address1 + '~' + AD.Address2 + '~' + AD.City + '~' + AD.PinCode AS CustAddress, "
					+ "PM.Name AS ProductName, od.Quantity,od.UnitID,um.UnitName,od.Attrib_Grp_ID,od.Cutting,od.Packing,od.Amount,od.Delv_Status "
					+ "FROM Order_Master OM " + "LEFT OUTER JOIN Order_Details OD " + "ON OM.ID = OD.OrderID "
					+ "INNER JOIN Customer C " + "ON C.Id = OM.CustomerID " + "INNER JOIN Address_Details AD "
					+ "ON AD.CustomerID = C.Id " + "AND ad.Id = om.AddressID " + "INNER JOIN Product_Master PM "
					+ "ON PM.ID = OD.ProductID " + "inner Join Unit_Master um on " + " um.ID=od.UnitID "
					+ " WHERE OM.ID = " + orderId + " " + "AND om.Published = 1 " + "AND od.Published = 1;";

			System.out.println("query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ORDER_NO", resultSet.getString("ORDER_NO"));
				jsonObject.put("DELV_SCHEDULE", resultSet.getString("Delv_Schedule"));
				jsonObject.put("NAME", resultSet.getString("CustName"));
				jsonObject.put("MOB", resultSet.getString("MobileNo"));
				jsonObject.put("ADDRESS", resultSet.getString("CustAddress"));
				jsonObject.put("PRODUCT", resultSet.getString("ProductName"));
				jsonObject.put("QTY", resultSet.getString("Quantity"));
				jsonObject.put("UNIT_ID", resultSet.getString("UnitID"));
				jsonObject.put("UNIT_NAME", resultSet.getString("UnitName"));
				jsonObject.put("SIZE", resultSet.getString("Attrib_Grp_ID"));
				jsonObject.put("CUTTING", resultSet.getString("Cutting"));
				jsonObject.put("PACKING", resultSet.getString("Packing"));
				jsonObject.put("AMT", resultSet.getString("Amount"));
				jsonObject.put("DELV_STATUS", resultSet.getString("Delv_Status"));

				JsonArray.put(jsonObject);
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
					resultSet.close();
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

	public String getDeliveryBoyList() {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		Statement statement = null;

		try {

			String query = "select a.Id,a.Delv_Agency_ID,a.FirstName,a.MiddleName,a.LastName from Delivery_Boy a where a.Active='1'";

			System.out.println("query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				JSONObject jsonObject = new JSONObject();

				jsonObject.put("ID", resultSet.getString("Id"));
				jsonObject.put("AGENCY_ID", resultSet.getString("Delv_Agency_ID"));
				jsonObject.put("NAME", resultSet.getString("FirstName") + " " + resultSet.getString("MiddleName") + " "
						+ resultSet.getString("LastName"));

				JsonArray.put(jsonObject);
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
					resultSet.close();
					statement.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println("final dboy list " + resultJson.toString());
		return resultJson.toString();
	}

	public String allocateDeliveryBoy(String orderId, String deliveryBoyId) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {

			String query = "update Order_Master set Delv_Boy_ID='" + deliveryBoyId + "' where ID='" + orderId + "'";
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

	public String updateKycStatus(String deliveryId) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {

			String query = " update Delivery_Boy set KYC_Status='V' where Id='" + deliveryId + "'";
			statement = connection.prepareStatement(query);

			int rowUpdated = statement.executeUpdate();

			if (rowUpdated > 0) {

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

	public String insertNewAttribute(String attribute) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {

			String query = "INSERT INTO [CounterSaleTest].[dbo].[Attribute_Master] " + "           ([Attrib_Name] "
					+ "           ,[Published] " + "           ,[CreatedOnUTC]) " + "     VALUES " + "           ('"
					+ attribute + "', " + "           '1' " + "           ,GetDate());";

			System.out.println("insert Query" + query);
			statement = connection.prepareStatement(query);

			int rowInserted = statement.executeUpdate();

			if (rowInserted > 0) {

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

	public String assignAttributeToProduct(String attributeName) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		CallableStatement pStatement = null;
		try {
			String SPsql = "{ call Pr_Attribute_Mapping(?,?) } ";

			pStatement = connection.prepareCall(SPsql);

			pStatement.setString(1, attributeName);
			pStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
			pStatement.executeUpdate();

			String pError = pStatement.getString(2);
			System.out.println("Error---" + pError);

			if (isNullOrEmpty(pError)) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Success", "success");
				resultJson.put("code", 1);
				resultJson.put("msg", "Success");
				resultJson.put("Result", jsonObject);
			} else {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Error", pError);
				resultJson.put("code", 0);
				resultJson.put("msg", "Error");
				resultJson.put("Result", jsonObject);
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

	public String updateOrderStatus(String orderId, String statusFlag, String statusRemarks) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		CallableStatement pStatement = null;
		try {
			String SPsql = "{ call Pr_Update_Tracking(?,?,?,?) } ";

			pStatement = connection.prepareCall(SPsql);

			pStatement.setInt(1, Integer.parseInt(orderId));
			pStatement.setString(2, statusFlag);
			pStatement.setString(3, statusRemarks);
			pStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
			pStatement.executeUpdate();

			String pError = pStatement.getString(4);
			System.out.println("Error---" + pError);

			if (isNullOrEmpty(pError)) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Success", "success");
				resultJson.put("code", 1);
				resultJson.put("msg", "Success");
				resultJson.put("Result", jsonObject);
			} else {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Error", pError);
				resultJson.put("code", 0);
				resultJson.put("msg", "Error");
				resultJson.put("Result", jsonObject);
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

	public static boolean isNullOrEmpty(String str) {
		if (str != null && !str.isEmpty())
			return false;
		return true;
	}

	public String getInwardByProdId(Inward objInward) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;

		Statement statement = null;

		try {

			String query = "Select a.ID As Inward_Mst_ID, a.InwardID, CONVERT(VARCHAR(10), a.InwardDt, 103) As Inward_Date, "
					+ "	a.ProductID, c.Name As Product_Name, a.Quantity as Inward_Qty, a.UnitID, d.UnitName, a.Rate as Purc_Rate "
					+

					"From Inward_Master a " +

					"Inner Join Product_Master c On a.ProductID = c.ID "
					+ "Inner Join Unit_Master d On a.UnitID = d.ID "
					+ "Left Outer Join Unit_Master e On a.UnitID = e.ID and a.Published = 1 "
					+ "Where CONVERT(VARCHAR(10), a.InwardDt, 103) = '"
					+ AllUtils.getFormattedDateForSqlForWorkingdate(objInward.getInwardDate()) + "' "
					+ "and a.ProductID=' " + objInward.getProductId() + " ' " + "and a.VendorID = "
					+ objInward.getVendorId() + " ";

			System.out.println("query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("INWARD_MST_ID", resultSet.getString("Inward_Mst_ID"));
				jsonObject.put("INWARD_ID", resultSet.getString("InwardID"));
				jsonObject.put("INWARD_DATE", resultSet.getString("Inward_Date"));
				jsonObject.put("PRODUCT_ID", resultSet.getString("ProductID"));
				jsonObject.put("PRODUCT_NAME", resultSet.getString("Product_Name"));
				jsonObject.put("INWARD_QTY", resultSet.getString("Inward_Qty"));
				jsonObject.put("UNIT_ID", resultSet.getString("UnitID"));
				jsonObject.put("UNIT_NAME", resultSet.getString("UnitName"));
				jsonObject.put("PURCHASE_RATE", resultSet.getString("Purc_Rate"));
				/*
				 * jsonObject.put("GRADEWISE_STOCKID", resultSet.getString("GradeWiseStk_ID"));
				 * jsonObject.put("ATTRIBUTE_GRP_ID", resultSet.getString("AttribGrpId"));
				 * jsonObject.put("GRADEWISE_UNITID", resultSet.getString("GradeWise_UnitID"));
				 * jsonObject.put("GRADEWISE_QTY", resultSet.getString("GradeWise_Qty"));
				 * 
				 * jsonObject.put("GRADEWISE_UNIT", resultSet.getString("Gradewise_Unit"));
				 * jsonObject.put("SALE_RATE", resultSet.getString("Sale_Rate"));
				 * jsonObject.put("OFFER_RATE", resultSet.getString("Offer_Rate"));
				 */

				JsonArray.put(jsonObject);
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
					resultSet.close();
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

	public String getATtrwiseStock(Inward objInward) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;

		Statement statement = null;

		try {

			String query = /*
							 * "Select a.AttribGrpId, dbo.FN_Concat_Value(a.AttribGrpId) As AttribName, " +
							 * "Sum(a.Qty) As Qty, a.Sale_Rate, a.Offer_Rate " + " From GradewiseStock a " +
							 * "Where a.ProductId = '" + objInward.getProductId() + "' " +
							 * "Group by a.AttribGrpId, dbo.FN_Concat_Value(a.AttribGrpId), a.Sale_Rate, a.Offer_Rate;"
							 * ;
							 */

					"SELECT x.AttribGrpId, x.AttribName, (x.Qty - ISNULL(y.qty,0)) qty,x.UnitId,x.UnitName, x.Sale_Rate, x.Offer_Rate  "
							+ " FROM " + " (SELECT a.AttribGrpId, dbo.FN_Concat_Value(a.AttribGrpId) AS AttribName, "
							+ "		SUM(a.Qty) AS Qty,a.UnitId,b.UnitName, a.Sale_Rate, a.Offer_Rate  "
							+ "		FROM GradewiseStock a, Unit_Master b " + "		WHERE a.UnitId = b.ID  "
							+ "		AND a.ProductId = '" + objInward.getProductId() + "' "
							+ "		GROUP BY a.AttribGrpId, dbo.FN_Concat_Value(a.AttribGrpId), "
							+ "		a.Sale_Rate, a.Offer_Rate, a.UnitId, b.UnitName) x " + "LEFT OUTER JOIN "
							+ "(SELECT o.Attrib_Grp_ID, " + "        p.UnitID, "
							+ "        SUM(CASE WHEN o.UnitID <> P.UnitID AND o.UnitID = p.Alt_UnitID THEN (o.Quantity / p.Qty_Conv_Factor) ELSE o.Quantity END) AS Qty "
							+ " FROM Order_Details o " + " INNER JOIN Product_Master P " + " ON P.ID = o.ProductID "
							+ " WHERE o.ProductID = '" + objInward.getProductId() + "' "
							+ " GROUP BY o.Attrib_Grp_ID,p.UnitID) y " + "ON x.AttribGrpId = y.Attrib_Grp_ID  "
							+ "AND x.UnitId = y.UnitID ";

			System.out.println("query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ATTR_GROUP_ID", resultSet.getString("AttribGrpId"));
				jsonObject.put("ATTR_NAME", resultSet.getString("AttribName"));
				jsonObject.put("QTY", resultSet.getString("Qty"));
				jsonObject.put("UNIT_ID", resultSet.getString("UnitId"));
				jsonObject.put("UNIT_PRICE", resultSet.getString("UnitName"));
				jsonObject.put("SALE_PRICE", resultSet.getString("Sale_Rate"));
				jsonObject.put("OFFER_PRICE", resultSet.getString("Offer_Rate"));

				/*
				 * jsonObject.put("GRADEWISE_STOCKID", resultSet.getString("GradeWiseStk_ID"));
				 * jsonObject.put("ATTRIBUTE_GRP_ID", resultSet.getString("AttribGrpId"));
				 * jsonObject.put("GRADEWISE_UNITID", resultSet.getString("GradeWise_UnitID"));
				 * jsonObject.put("GRADEWISE_QTY", resultSet.getString("GradeWise_Qty"));
				 * 
				 * jsonObject.put("GRADEWISE_UNIT", resultSet.getString("Gradewise_Unit"));
				 * jsonObject.put("SALE_RATE", resultSet.getString("Sale_Rate"));
				 * jsonObject.put("OFFER_RATE", resultSet.getString("Offer_Rate"));
				 */

				JsonArray.put(jsonObject);
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
					resultSet.close();
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

	public String postGradewiseStock(Inward objInward) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		CallableStatement pStatement = null;
		try {
			String SPsql = "{ call Pr_Gradewise_Stock(?,?,?,?,?,?,?,?,?,?,?) } ";

			pStatement = connection.prepareCall(SPsql);

			pStatement.setString(1, objInward.getInwardFlag());
			pStatement.setInt(2, 0);
			pStatement.setInt(3, Integer.parseInt(objInward.getInwardId()));
			pStatement.setInt(4, Integer.parseInt(objInward.getProductId()));
			pStatement.setInt(5, Integer.parseInt(objInward.getAttriGrpId()));
			pStatement.setDouble(6, Double.parseDouble(objInward.getQuantity()));
			pStatement.setInt(7, Integer.parseInt(objInward.getUnitId()));
			pStatement.setDouble(8, Double.parseDouble(objInward.getSellingPrice()));
			pStatement.setDouble(9, Double.parseDouble(objInward.getOfferPrice()));
			pStatement.registerOutParameter(10, java.sql.Types.INTEGER);
			pStatement.registerOutParameter(11, java.sql.Types.VARCHAR);
			pStatement.executeUpdate();

			int outId = pStatement.getInt(10);
			String pError = pStatement.getString(11);
			System.out.println("Error---" + pError);

			if (isNullOrEmpty(pError)) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("OUT_ID", outId);
				resultJson.put("code", 1);
				resultJson.put("msg", "Success");
				resultJson.put("Result", jsonObject);
			} else {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Error", pError);
				resultJson.put("code", 0);
				resultJson.put("msg", "Error");
				resultJson.put("Result", jsonObject);
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

	public String verifyDeliveryBoy(String deliveryBoyId) {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		// JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {

			String query = "update Delivery_Boy set KYC_Status='V' where Id='" + deliveryBoyId + "'";
			statement = connection.prepareStatement(query);

			int updatedRow = statement.executeUpdate();

			if (updatedRow > 0) {

				resultJson.put("updated", true);
				resultJson.put("code", 1);
				resultJson.put("msg", "Success");
			} else {
				resultJson.put("updated", false);
				resultJson.put("code", 0);
				resultJson.put("msg", "Fail");
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("code", 0);
				jsonObj.put("msg", "Error");
				resultJson.put("Result", jsonObj);
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

	public String getListForKYCVerification() {
		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;

		Statement statement = null;

		try {

			String query = "select km.FullName,km.Father_Name,km.Gender, " + "km.Marital_Status,km.Birth_Date,km.Pan, "
					+ "km.Aadhar_Number,km.Mobile_No,km.Delv_Boy_ID  " + "from KYC_Master km ,delivery_boy db "
					+ "where db.ID= km.Delv_Boy_ID " + "and db.KYC_Status='U';";

			System.out.println("query=" + query);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("FULLNAME", resultSet.getString("FullName"));
				jsonObject.put("FATHER_NAME", resultSet.getString("Father_Name"));
				jsonObject.put("GENDER", resultSet.getString("Gender"));
				jsonObject.put("MARITAL_STAT", resultSet.getString("Marital_Status"));
				jsonObject.put("DOB", resultSet.getString("Birth_Date"));
				jsonObject.put("PAN", resultSet.getString("Pan"));
				jsonObject.put("AADHAR", resultSet.getString("Aadhar_Number"));
				jsonObject.put("MOB_NO", resultSet.getString("Mobile_No"));
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
				resultJson.put("code", 0);
				resultJson.put("msg", "Error");

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
		System.out.println("final result " + resultJson.toString());
		return resultJson.toString();
	}

	public String postImage(UserImageData objImgData) {

		JSONObject resultJson = new JSONObject();

		// String base64=
		// "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";

		byte[] decodedByteArray = Base64.getDecoder().decode(objImgData.getStrImageData());
		FileOutputStream fout;

		try {

			fout = new FileOutputStream("E:\\Ashwin Workspace\\imageForWMkt\\" + objImgData.getDocumentType() + "\\ "
					+ objImgData.getDeliveryBoyId() + ".jpg");
			// + objImgData.getDeliveryBoyId()+"@"+ objImgData.getDocumentType() + ".jpg");
			fout.write(decodedByteArray);

			File imgFile = new File("E:\\Ashwin Workspace\\imageForWMkt\\ " + objImgData.getDocumentType() + "\\ "
					+ objImgData.getDeliveryBoyId() + ".jpg");

			// + objImgData.getDeliveryBoyId()+"@"+ objImgData.getDocumentType()+ ".jpg");

			boolean exists = imgFile.exists();

			if (exists == true) {

				resultJson.put("updated", true);
				resultJson.put("code", 1);
				resultJson.put("msg", "Success");

			} else {
				resultJson.put("updated", false);
				resultJson.put("code", 0);
				resultJson.put("msg", "Fail");

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			try {
				JSONObject jsonObj = new JSONObject();
				resultJson.put("code", 0);
				resultJson.put("msg", "Error");

			} catch (JSONException e1) {
				e1.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
			try {
				JSONObject jsonObj = new JSONObject();
				resultJson.put("code", 0);
				resultJson.put("msg", "Error");

			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} catch (JSONException e) {
			e.printStackTrace();
			try {
				JSONObject jsonObj = new JSONObject();
				resultJson.put("code", 0);
				resultJson.put("msg", "Error");

			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}

		return resultJson.toString();
	}

	public String getStorewiseStockWithAdd(String marketId) {

		Connection connection = ConnectionSql.getConnection(DbConstant.USER, DbConstant.PASSWORD);

		JSONArray JsonArray = new JSONArray();
		JSONArray advArray = null;
		int count = 0;
		JSONObject resultJson = new JSONObject();
		ResultSet resultSet = null;
		CallableStatement pStatement = null;
		try {
			String SPsql = "{ call Pr_Get_Inw_Adv_Data(?) } ";

			pStatement = connection.prepareCall(SPsql);
			pStatement.setInt(1, Integer.parseInt(marketId));
			pStatement.executeQuery();
			resultSet = pStatement.getResultSet();

			int counter = 0;
			boolean includeAd = false;
			JSONArray adArray =null;
			while (resultSet.next()) {

				JSONObject jsonObject = new JSONObject();
				// Table name ==inward
				jsonObject.put("TABLE_NAME", resultSet.getString("TableName"));
				jsonObject.put("SEQ", resultSet.getString("Seq"));
				jsonObject.put("PRODUCT_ID", resultSet.getString("ProductID"));
				jsonObject.put("PRODUCT_NAME", resultSet.getString("ProductName"));
				jsonObject.put("MIN_QTY", resultSet.getString("Min_Qty"));
				jsonObject.put("RATE", resultSet.getString("MRP"));
				jsonObject.put("UNIT", resultSet.getString("Mrp_Unit"));
				jsonObject.put("UNIT_NAME", resultSet.getString("Mrp_Unit_Name"));
				jsonObject.put("OFFER_PRICE", resultSet.getString("Offer_Price"));
				jsonObject.put("ALT_UNITID", resultSet.getString("Alt_UnitID"));
				jsonObject.put("ALT_UNIT_NAME", resultSet.getString("Alt_Unit_Name"));
				jsonObject.put("STOCK", resultSet.getString("Stock"));
				jsonObject.put("CONVERSION_FACTOR", resultSet.getString("Qty_Conv_Factor"));
				jsonObject.put("ADDITIONAL_RATE", resultSet.getString("Addl_Rate"));
				// TAble Name == add
				jsonObject.put("ADVERTISE", resultSet.getString("AdvName"));
				jsonObject.put("ATTRIBUTE_NAME", resultSet.getString("AdvName"));
				jsonObject.put("SPONSOR", resultSet.getString("SponsorName"));
				jsonObject.put("URL", resultSet.getString("ImageUrl"));
				jsonObject.put("ATTRIBUTE_ID", resultSet.getString("AttribId"));
				
				if(counter ==0) {
					JsonArray.put(jsonObject);
					counter++;
					continue;
				}

				if (counter % 4 == 0) {
					if (includeAd) {
						includeAd = false;
						//JsonArray.put(adArray);
						JsonArray.put(new JSONObject().put("ADD", adArray));
					}else {
						includeAd = true;
						adArray = new JSONArray();
					}
				}
				
				if(includeAd) {
					
					adArray.put(jsonObject);
					
				}else {
					JsonArray.put(jsonObject);
				}

				counter++;

			}

//		while (resultSet.next()) {
//				JSONObject jsonObject = new JSONObject();
//				JSONObject advJsonObj = new JSONObject();
//				String sequence=resultSet.getString("Seq");
//				
//				
//				 //jsonObject = null;
//				
//
//				String tblName = resultSet.getString("TableName");
//				
//				System.out.println("tblName------------"+tblName);
//				if(tblName.equalsIgnoreCase("INWARD_MAST")) {
//				
//					int Seq=Integer.parseInt(sequence);
//					
//					jsonObject.put("Add",advArray);
//					advArray=new JSONArray();
//					JsonArray.put(jsonObject);
//					Flag="a";
//				
//					
//					
//					System.out.println("tblName------------"+tblName);
//					System.out.println("sequence----------"+sequence);
//				
//					
//					
//					jsonObject.put("TABLE_NAME", resultSet.getString("TableName"));
//					jsonObject.put("SEQ", resultSet.getString("Seq"));
//					jsonObject.put("PRODUCT_ID", resultSet.getString("ProductID"));
//					jsonObject.put("PRODUCT_NAME", resultSet.getString("ProductName"));
//					jsonObject.put("MIN_QTY", resultSet.getString("Min_Qty"));
//					jsonObject.put("RATE", resultSet.getString("MRP"));
//					jsonObject.put("UNIT", resultSet.getString("Mrp_Unit"));
//					jsonObject.put("UNIT_NAME", resultSet.getString("Mrp_Unit_Name"));
//					jsonObject.put("OFFER_PRICE", resultSet.getString("Offer_Price"));
//					jsonObject.put("ALT_UNITID", resultSet.getString("Alt_UnitID"));
//					jsonObject.put("ALT_UNIT_NAME", resultSet.getString("Alt_Unit_Name"));
//					jsonObject.put("STOCK", resultSet.getString("Stock"));
//					jsonObject.put("CONVERSION_FACTOR", resultSet.getString("Qty_Conv_Factor"));
//					
//					JsonArray.put(jsonObject);				
//					counter=0;
//				advJsonObj=null;
//				}else if(tblName.equalsIgnoreCase("ADV_MAST")) {
//					Flag="b";
//					System.out.println("tblName---------------"+tblName);
//					
//					advJsonObj.put("ADDITIONAL_RATE", resultSet.getString("Addl_Rate"));	
//					advJsonObj.put("ADVERTISE", resultSet.getString("AdvName"));
//				advJsonObj.put("SPONSOR", resultSet.getString("SponsorName"));
//				advJsonObj.put("URL", resultSet.getString("ImageUrl"));
//				
//				
//				advArray.put(advJsonObj);
//				
//			
//			}
//				
//				//jsonObject.put("ADD",advArray);
//				
//			
//			
//		}

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

}
