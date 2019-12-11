package com.vgipl.server.apmc.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;

import com.vgipl.server.apmc.model.Commodity;
import com.vgipl.server.apmc.model.Customer;
import com.vgipl.server.apmc.model.Invoice;
import com.vgipl.server.apmc.model.User;

public class HtmlBuilder {

//	public static void InvoiceHtmlBuilder(User user,ArrayList productList) {
	public static void InvoiceHtmlBuilder(Invoice invoice,Customer customer,ArrayList productList) {
		
		try{
			
			//define a HTML String Builder
            StringBuilder htmlStringBuilder=new StringBuilder();
            //append html header and title
            htmlStringBuilder.append(HTMLTemplate.INVOICE_HEAD_START+
            						 "Original Bill Of Supply"+
            						 HTMLTemplate.INVOICE_HEAD_END);
            
            htmlStringBuilder.append(HTMLTemplate.CUST_NAME_START+customer.getCustomerName()+HTMLTemplate.CUST_NAME_END);
            htmlStringBuilder.append(HTMLTemplate.CUST_ADD1_START+customer.getCustomerAddress1()+HTMLTemplate.CUST_ADD1_END);
            htmlStringBuilder.append(HTMLTemplate.CUST_ADD2_START+customer.getCustomerAddress2()+HTMLTemplate.CUST_ADD2_START);
            htmlStringBuilder.append(HTMLTemplate.CUST_CITY_START+customer.getCustomerCity()+HTMLTemplate.CUST_CITY_END);
            htmlStringBuilder.append(HTMLTemplate.CUST_PIN_S+customer.getCustomerPin()+HTMLTemplate.CUST_PIN_E);
            htmlStringBuilder.append(HTMLTemplate.ADD_END);
            
            htmlStringBuilder.append(HTMLTemplate.BILL_NO_S+invoice.getInvoiceNumber()+HTMLTemplate.BILL_NO_E);
            htmlStringBuilder.append(HTMLTemplate.ORDER_ID_S+invoice.getOrderId()+HTMLTemplate.ORDER_ID_E);
            htmlStringBuilder.append(HTMLTemplate.BILL_DATE_S+invoice.getInvoiceDate()+HTMLTemplate.BILL_DATE_E);
            htmlStringBuilder.append(HTMLTemplate.SLOT_S+invoice.getDeliverySlot()+HTMLTemplate.SLOT_E);
            htmlStringBuilder.append(HTMLTemplate.FINAL_TOT_S+invoice.getInvoiceAmt()+HTMLTemplate.FINAL_TOT_E);
            htmlStringBuilder.append(HTMLTemplate.NO_OF_ITEM_S+productList.size()+HTMLTemplate.NO_OF_ITEM_E);
            htmlStringBuilder.append(HTMLTemplate.LAFLEUR_DETAILS);
            
            htmlStringBuilder.append(HTMLTemplate.TABLE_COL_HEADER);
            
            for(int i=0;i<productList.size();i++) {
            	Commodity commodity = (Commodity) productList.get(i);
            	
            	htmlStringBuilder.append("<tr><td>"+(i+1)+"</td>");
            	htmlStringBuilder.append("<td>"+ commodity.getCommodityName()+"</td>");
            	htmlStringBuilder.append("<td>"+""+"</td>");
            	htmlStringBuilder.append("<td>"+commodity.getQty()+"</td>");
            	htmlStringBuilder.append("<td>"+commodity.getRate()+"</td>");
            	htmlStringBuilder.append("<td>"+commodity.getDiscount()+"</td>");
            	htmlStringBuilder.append("<td>"+commodity.getItemTotal()+"</td></tr>");
            	
            }
            
            
            htmlStringBuilder.append(HTMLTemplate.ITEM_GR_TOT_S+invoice.getInvoiceAmt()+HTMLTemplate.ITEM_GR_TOT_E);
            htmlStringBuilder.append(HTMLTemplate.TABLE_END);
            
          //write html string content to a file
            WriteToFile(htmlStringBuilder.toString(),"testfile.html");
            

            
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	 public static void WriteToFile(String fileContent, String fileName) throws IOException {
		 
		 try {
//		        String projectPath = System.getProperty("user.dir");
//		        String tempFile = projectPath + File.separator+fileName;
		        
			 File file = new File("C:\\Users\\anirudh.patil\\Desktop\\"+"invoice.html");
		        // if file does exists, then delete and create a new file
//		        if (file.exists()) {
//		            try {
//		                File newFileName = new File(projectPath + File.separator+ "backup_"+fileName);
//		                file.renameTo(newFileName);
//		                file.createNewFile();
//		            } catch (IOException e) {
//		                e.printStackTrace();
//		            }
//		        }
		        //write to file with OutputStreamWriter
		        OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
		        Writer writer=new OutputStreamWriter(outputStream);
		        writer.write(fileContent);
		        writer.close();
			 
		 }catch(FileAlreadyExistsException e) {
			 e.printStackTrace();
		 }catch(FileSystemException e) {
			 e.printStackTrace();
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 


	    }
	
}
