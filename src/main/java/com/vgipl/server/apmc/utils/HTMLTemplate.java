package com.vgipl.server.apmc.utils;

public class HTMLTemplate {
	
	public static final String INVOICE_HEAD_START="<html lang=\"en\">\r\n" + 
			"\r\n" + 
			"	<head>\r\n" + 
			"		<meta charset=\"utf-8\">\r\n" + 
			"		<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n" + 
			"		<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
			"        <title>";
	

	public static final String INVOICE_HEAD_END="</title>\r\n" + 
			"        \r\n" + 
			"        <style>\r\n" + 
			"            html, body{margin: 0;font-family: \"calibri\";}\r\n" + 
			"            .box table, th{border: 1px solid #555;}\r\n" + 
			"            .box th{background-color: #ffebc3}\r\n" + 
			"            .box td{border: 1px solid #555;}\r\n" + 
			"            .box .odd{background-color: #e8edf1;} \r\n" + 
			"            .bill-title{padding: 10px 0; letter-spacing: 20px;word-spacing: 10px;width:100%; text-align: center; font-weight: bold; font-size: 20px; background-color: #fffcf7}\r\n" + 
			"        </style>\r\n" + 
			"    </head>";
	
	public static final String CUST_NAME_START="<body>\r\n" + 
			"        <div style=\"width:100%\">\r\n" + 
			"            <div style=\"width:100%; float: left;\">\r\n" + 
			"                <div class=\"bill-title\">ORIGINAL BILL OF SUPPLY</div>\r\n" + 
			"            </div>\r\n" + 
			"            <div style=\"width:100%; float: left;\">\r\n" + 
			"                <div style=\"width:60%; float: left;\">\r\n" + 
			"                    <div style=\"width:100%; float: left;\">\r\n" + 
			"                        <table cellspacing=\"0\" cellpadding=\"2\">\r\n" + 
			"                            <tbody>\r\n" + 
			"                                <tr><td><b>Bill to / Ship to:</b></td></tr>\r\n" + 
			"                                <tr><td>";
	
	public static final String CUST_NAME_END=",</td></tr>";
	
	public static final String CUST_ADD1_START="<tr><td>";
	public static final String CUST_ADD1_END=",</td></tr>";
	
	public static final String CUST_ADD2_START="<tr><td>";
	public static final String CUST_ADD2_END="</td></tr>";
	
	public static final String CUST_CITY_START="<tr><td>";
	public static final String CUST_CITY_END="</td></tr>";
	
	public static final String CUST_PIN_S="<tr><td>";
	public static final String CUST_PIN_E="</td></tr>";
	
	public static final String ADD_END="</tbody>\r\n" + 
			"                        </table>\r\n" + 
			"                    </div>\r\n" + 
			"                    <div class=\"box\" style=\"width:100%; float: left;margin-top: 20px;\">\r\n" + 
			"                        <table cellspacing=\"0\" cellpadding=\"5\">\r\n" + 
			"                            <tbody>\r\n" + 
			"                                <tr>";
	
	public static final String BILL_NO_S="<td><b>Invoice No</b></td><td>";
	public static final String BILL_NO_E="</td></tr>";
	
	public static final String ORDER_ID_S="<tr><td><b>Order ID</b></td><td>";
	public static final String ORDER_ID_E="</td></tr>";
	
	public static final String BILL_DATE_S="<tr><td><b>Date of Issue of Invoice</b></td><td>";
	public static final String BILL_DATE_E="</td></tr>";
	
	public static final String SLOT_S="<tr><td><b>Slot</b></td><td>";
	public static final String SLOT_E="</td></tr>";
	
	public static final String FINAL_TOT_S="<tr><td><b>Final Total</b></td><td>";
	public static final String FINAL_TOT_E="</td> </tr>";
	
	public static final String NO_OF_ITEM_S="<tr><td><b>No of Items</b></td><td>";
	public static final String NO_OF_ITEM_E="</td>\r\n" + 
			"                                </tr>\r\n" + 
			"                            </tbody>\r\n" + 
			"                        </table>\r\n" + 
			"                    </div>\r\n" + 
			"                </div>";
	
	public static final String LAFLEUR_DETAILS="<div style=\"width:40%; float: left;\">\r\n" + 
			"                    <div style=\"width:100%; float: left;\">\r\n" + 
			"                        <table cellspacing=\"0\" cellpadding=\"2\">\r\n" + 
			"                            <tbody>\r\n" + 
			"                                <tr><td style=\"text-align: center\"><img src=\"http://192.168.1.180:8080/FlowerImages/lafleur.png\" style=\"width: 30%;\"></td></tr>\r\n" + 
			"                                <tr><td>Flat no 01, Tejas Apartment</td></tr>\r\n" + 
			"                                <tr><td>Plot no 218, Yashwant Nagar Talegaon Dhabhade</td></tr>\r\n" + 
			"                                <tr><td>Pune, Maharashtra 410507</td></tr>\r\n" + 
			"                                <tr><td><b>Tel.:</b> +1800.2666.424</td></tr>\r\n" + 
			"                                <tr><td><b>Mail:</b>sales@lafleur.in</td></tr>\r\n" + 
			"                                <tr><td><b>GSTN:</b>29AACC12053A1Z3</td></tr>\r\n" + 
			"                                <tr><td><b>CIN:</b>U74130KA2010PTC052192</td></tr>\r\n" + 
			"                            </tbody>\r\n" + 
			"                        </table>\r\n" + 
			"                    </div>\r\n" + 
			"                </div>\r\n" + 
			"            </div>";
	
	
	
	public static final String TABLE_COL_HEADER="<div style=\"width:100%; float: left; margin-top: 10px;\">\r\n" + 
			"                <div class=\"box\" style=\"width:100%;\">\r\n" + 
			"                    <table cellspacing=\"0\" cellpadding=\"5\" style=\"margin: 0 auto; width: 90%;\">\r\n" + 
			"                        <tbody>\r\n" + 
			"                            <tr>\r\n" + 
			"                                <th style=\"width: 5%\">Sr. No.</th>\r\n" + 
			"                                <th style=\"width: 35%\">Item Discription</th>\r\n" + 
			"                                <th style=\"width: 12%\">HSN Code</th>\r\n" + 
			"                                <th style=\"width: 12%\">Quantity, along with units</th>\r\n" + 
			"                                <th style=\"width: 12%\">Gross Value</th>\r\n" + 
			"                                <th style=\"width: 12%\">Discount, If Any</th>\r\n" + 
			"                                <th style=\"width: 12%\">Total Value for supply of goods</th>\r\n" + 
			"                            </tr>\r\n" + 
			"                            <tr>";
	
	
	
	
	public static final String ITEM_GR_TOT_S="<tr>\r\n" + 
			"                                <td colspan=\"6\" style=\"width: 90%; text-align: right; font-weight: bold;\">Total</td>\r\n" + 
			"                                <td style=\"width: 10%; font-weight: bold;\">";
	
	public static final String ITEM_GR_TOT_E="</td>\r\n" + 
			"                            </tr>\r\n" + 
			"                        </tbody>\r\n" + 
			"                    </table>\r\n" + 
			"                </div>\r\n" + 
			"            </div>";
	
	public static final String TABLE_END="</div>\r\n" + 
			"    </body>\r\n" + 
			"</html>";
	
			                                
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
