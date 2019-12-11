
package com.vgipl.server.apmc.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
   


public class AllUtils {
	
	/**
	 * 
	 * @param strDate
	 * @return
	 */
	
	/**
	 * 
	 * @param strDate
	 * @return
	 */
	public  static String getFormattedDateForXML(String strDate)
	{
		Date sDate=null;
		try
		{
			strDate=strDate.replaceAll("/", "-");
		}catch(Exception e)
		{
			
			System.out.println("Eception in Formatting Date"+e);
		}
		return  strDate;

	}
	
	/**
	 * 
	 * @param strDate
	 * @return
	 */
	public  static String getFormattedDateForSql(String strDate)
	{
		String sDate=null;
		try
		{
			SimpleDateFormat fromformat = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat myformat = new SimpleDateFormat("dd/MMM/yyyy");
			sDate=myformat.format(fromformat.parse(strDate));
			System.out.println("date received "+sDate);
		}catch(Exception e)
		{
			
			System.out.println("Exception in Formatting Date"+e);
		}
		return sDate;

	}
	
	
	/**
	 * 
	 * @param strDate
	 * @return
	 */
	public  static String getFormattedDateForOracle(String strDate)
	{
		String sDate=null;
		try
		{
			SimpleDateFormat fromformat = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat myformat = new SimpleDateFormat("dd/MMM/yyyy");
			sDate=myformat.format(fromformat.parse(strDate));
			System.out.println("date received "+sDate);
		}catch(Exception e)
		{
			
			System.out.println("Exception in Formatting Date"+e);
		}
		return sDate;

	}
	
	
	public  static String getFormattedPadtaDateForOracle(String strDate)
	{
		String sDate=null;
		try
		{
			SimpleDateFormat fromformat = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat myformat = new SimpleDateFormat("dd/MMM/yyyy");
			sDate=myformat.format(fromformat.parse(strDate));
			System.out.println("date received "+sDate);
		}catch(Exception e)
		{
			
			System.out.println("Exception in Formatting Date"+e);
		}
		return sDate;

	}
	
	
	/**
	 * 
	 * @param strDate
	 * @return
	 */

	public  static String getFormattedDateForSummary(String strDate)
	{
		String sDate=null;
		try
		{
			SimpleDateFormat fromformat = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat myformat = new SimpleDateFormat("dd/MMM/yyyy");
			sDate=myformat.format(fromformat.parse(strDate));
			System.out.println("date received "+sDate);
		}catch(Exception e)
		{
			
			System.out.println("Exception in Formatting Date"+e);
		}
		return sDate;

	}
	
	/**
	 * 
	 * @param strDate
	 * @return
	 */
	public  static String getFormattedDateForSqlForWorkingdate(String strDate)
	{
		String sDate=null;
		try
		{
			SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat myformat = new SimpleDateFormat("dd-MM-yyyy");
			sDate=myformat.format(fromformat.parse(strDate));

		}catch(Exception e)
		{
			
			System.out.println("Exception in Formatting Date"+e);
		}
		return sDate;

	}
	
	

	public static String getuserLogTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		return dateFormat.format(date);

	}
	
	
	/**
	 * 
	 * @param strDate
	 * @return
	 */
	public  static String getFormattedDateForSqlnew(String strDate)
	{
		String sDate=null;
		try
		{
			SimpleDateFormat fromformat = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat myformat = new SimpleDateFormat("M/d/yyyy");
			sDate=myformat.format(fromformat.parse(strDate));
			System.out.println("date is "+sDate);
		}catch(Exception e)
		{
			
			System.out.println("Exception in Formatting Date"+e);
		}
		return sDate;

	}
	
	/**
	 * 
	 * @param strDate
	 * @param format
	 * @return
	 */
	public  static String getCustomFormattedDateForSql(String strDate,String format)
	{
		String sDate=null;
		try
		{
			SimpleDateFormat fromformat = new SimpleDateFormat(format);//"yyyy-MM-dd"
			SimpleDateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
			sDate=myformat.format(fromformat.parse(strDate));

			System.out.println("date is "+sDate);


		}catch(Exception e)
		{
			
		System.out.println("Exception in Formatting Date"+e);
		}
		return sDate;

	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String getroundvalue(Double value)
	{
		String formate="0.00";
		try
		{
			DecimalFormat df=new DecimalFormat("0.00");
			formate = df.format(value); 

		}
		catch(Exception e)
		{
			
			System.out.println("check Exception in rounding value"+e.getMessage());
		}
		return formate;

	}
	
	
	public static String getroundvaluePadta(Double value)
	{
		String formate="0.00";
		try
		{
			DecimalFormat df=new DecimalFormat("0.00");
			formate = df.format(value); 
			System.out.println("formate is "+formate);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("check Exception in rounding value"+e.getMessage());
		}
		return formate;

	}
	
	
	
	public static String getCurrentDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		return dateFormat.format(date);

	}
	
	/**
	 * 
	 * @param branchCode
	 * @return
	 */
	public static String getBrIPDateTime(String branchCode){
		String brIPDateTime="";
		String ipAddress = "HHD";
		String DATE_FORMAT_NOW = "dd/MM/yyyy-HH:mm:ss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

		brIPDateTime = ipAddress +"-"+branchCode+"-"+ sdf.format(cal.getTime());
		if (brIPDateTime.length() > 50){
			brIPDateTime.substring(0, 50);
		}
		else{
			brIPDateTime = brIPDateTime;	
		}
		System.out.println("brIpDate "+brIPDateTime);
		return brIPDateTime;

	}
	
	
	public static String getAccurateTime(){
		//SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
		Date now = new Date();
		String strTime = sdfTime.format(now);
		System.out.println("Time: " + strTime);
		return strTime;
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * @param strDate
	 * @return
	 */
	public  static String getCustomFormattedDateTime(String strDate)
	{
		String sDate=null;
		try
		{
			SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//"yyyy-MM-dd"
			SimpleDateFormat myformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss aa");
			sDate=myformat.format(fromformat.parse(strDate));

			System.out.println("date is "+sDate);


		}catch(Exception e)
		{
		
			System.out.println("Exception in Formatting Date"+e);
		}
		return sDate;

	}

	public static String getCurrentDateFormat()
	{
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		return dateFormat.format(date);

	}
	
	public static String getCurrentDateForLimit()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");  
	    Date date = new Date();  
	    System.out.println(formatter.format(date));  
	    return formatter.format(date);
	}
	
	
	
	
	
	
	

	public static String getTime(){
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
		Date now = new Date();
		String strTime = sdfTime.format(now);
		System.out.println("Time: " + strTime);
		return strTime;
	}

	/**
	 * 
	 * @param strDate
	 * @return
	 */
	public  static String getFormattedDateForMobile(String strDate)
	{
		String sDate=null;
		try
		{
			SimpleDateFormat fromformat = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat myformat = new SimpleDateFormat("dd/MMM/yyyy");
			sDate=myformat.format(fromformat.parse(strDate));
			System.out.println("date received "+sDate);
		}catch(Exception e)
		{
			
			System.out.println("Exception in Formatting Date"+e);
		}
		return sDate;

	}

	///// date formation for agent weighment Payment
	
	public  static String getFormattedDateForAndroid(String strDate)
	{
		String sDate=null;
		try
		{
			SimpleDateFormat fromformat = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat myformat = new SimpleDateFormat("dd/MMM/yyyy");
			sDate=myformat.format(fromformat.parse(strDate));
			System.out.println("date received "+sDate);
		}catch(Exception e)
		{
		
			System.out.println("Exception in Formatting Date"+e);
		}
		return sDate;

	}
	
	public  static String getCustomLotDate(String strDate)
	{
		String sDate=null;
		try
		{
			SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//"yyyy-MM-dd"
			SimpleDateFormat myformat = new SimpleDateFormat("dd-MM-yyyy");
			sDate=myformat.format(fromformat.parse(strDate));

			System.out.println("date is "+sDate);


		}catch(Exception e)
		{
			
			System.out.println("Exception in Formatting Date"+e);
		}
		return sDate;

	}
	

	public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }
	
}
