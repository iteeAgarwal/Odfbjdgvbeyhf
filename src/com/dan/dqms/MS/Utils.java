package com.dan.dqms.MS;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Utils {

	
	public static ArrayList<Long> date_ten()
	
	{
		ArrayList<Long> al = new ArrayList<Long>();
		
		for(int i = 0;i<11;i++)
		{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH,-i);
        
      // System.out.println(cal.getTimeInMillis()/1000); 
       
       String s = dateFormat.format(cal.getTime());
       
       SimpleDateFormat df = new SimpleDateFormat(
    	        "yyyy/MM/dd");
    	    Date date;
			try {
				date = df.parse(s);
				long epoch = date.getTime();
				//System.out.println(epoch);
				al.add(epoch/1000);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	    
       
		}
		
		return al;
		
	}
	
	public static long getTodayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH,-0);
        long epoch = 0;
      // System.out.println(cal.getTimeInMillis()/1000); 
       //System.out.println(cal.getTime().getDate()); 
       String s = dateFormat.format(cal.getTime());
       
       SimpleDateFormat df = new SimpleDateFormat(
    	        "yyyy/MM/dd");
    	    Date date;
			try {
				date = df.parse(s);
				 epoch = date.getTime()/1000;
				//System.out.println(epoch);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	    
        return epoch;
}
	
	
	public static String minIntoHour(long data)
	{
		int hours = (int) (data / 60); //since both are ints, you get an int
		int minutes = (int) (data % 60);
		String time = null;
		if(hours!=0)
		{
			time= String.valueOf(hours) + " hours " + String.valueOf(minutes) +" min " ;
		}
		 
		else
		{
			time = String.valueOf(minutes) +" min ";
		}
		return time;
	}
	
	
	public static int currentTime()
	{
		
		 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	        Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DAY_OF_MONTH,-0);
	       int epoch = 0;
	      
	   epoch = (int) (cal.getTimeInMillis()/1000);
		
		return epoch;
		
	}
	public static void main(String[] args) {
		Utils u = new Utils();
//		System.out.println(u.date_ten());
//		System.out.println(u.getTodayDateString());
		System.out.println(getTodayDateString());
	}
}
