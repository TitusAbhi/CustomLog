/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.logcat_crashreport;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

/**
 *
 * @author RajuCPS
 */
public class DateUtils {
    
    
    // MS SQL Date Time Format 12/31/2011 21:45:45
	static String newDateStr;
    public static String getMSSQLDateTimeString(Calendar cal)
   {
    	String dateStr = cal.get(cal.DATE)+"-"+(cal.get(cal.MONTH)+1)+"-"+cal.get(cal.YEAR); 

    	SimpleDateFormat curFormater = new SimpleDateFormat("dd-MM-yyyy"); 
    	Date dateObj;
		try 
		{
			dateObj = curFormater.parse(dateStr);
			SimpleDateFormat postFormater = new SimpleDateFormat("dd-MMM-yyyy"); 

	    	 newDateStr = postFormater.format(dateObj); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
        return newDateStr;//+" "+cal.get(cal.HOUR_OF_DAY)+":"+cal.get(cal.MINUTE)+":"+cal.get(cal.SECOND);
    }
    
    
    public static String getCurrentDateTime()
	{
		
		   final Calendar c = Calendar.getInstance();
	        int mYear = c.get(Calendar.YEAR);
	        int mMonth = c.get(Calendar.MONTH);
	        int mDay = c.get(Calendar.DAY_OF_MONTH);

	        int mHour=c.get(Calendar.HOUR_OF_DAY);
	        int mMinute=c.get(Calendar.MINUTE);
	        int mSecond=c.get(Calendar.SECOND);
	       
	        Date d = new Date();
	        CharSequence s  = DateFormat.getDateTimeInstance().format(new Date());
	        
	        Log.e("date format", ""+mDay+"-"+mMonth+"-"+mYear+"---"+mHour+":"+mMinute+":"+mSecond);
	        
	        String dateStr=""+mDay+"-"+(mMonth+1)+"-"+mYear;
	        String newDateStr = "";
	        
	        SimpleDateFormat curFormater = new SimpleDateFormat("dd-MM-yyyy"); 
	    	Date dateObj;
			try 
			{
				dateObj = curFormater.parse(dateStr);
				SimpleDateFormat postFormater = new SimpleDateFormat("yyyyMMdd"); 

		    	 newDateStr = postFormater.format(dateObj); 
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
		return newDateStr+""+mHour+""+mMinute+""+mSecond;
		
	}
    
    
    public static String ChangeDateFormat(String date)
	{
		 String dateStr=date;
	        String newDateStr = "";
	        
	        SimpleDateFormat curFormater = new SimpleDateFormat("MM/dd/yyyy"); 
	    	Date dateObj;
			try 
			{
				dateObj = curFormater.parse(dateStr);
				SimpleDateFormat postFormater = new SimpleDateFormat("dd-MMM-yyyy"); 

		    	 newDateStr = postFormater.format(dateObj); 
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
		return newDateStr;

	}
	  
    
}
