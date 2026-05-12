package com.schillerindiaservices.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.schillerindiaservices.controller.NonSaleControler;

public class UtilFunctions {
      
	Date date = null;
	
	//to convert format from  yyyy-MM-dd to dd-MM-yyyy
	public String getDateFormat(String date)  {
		System.err.println("insideee date formatt");
		// Date date = new Date();  
	 
		    
		    if("" != date && null != date && !date.equals("null")) {
		    	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			    Date dt = null;
				try {
					dt = new SimpleDateFormat("yyyy-MM-dd").parse(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Logger.getLogger(UtilFunctions.class.getName()).log(Level.SEVERE,"parse error in date",e);
				} 
			    
			    String strDate= formatter.format(dt);  
			    System.err.println("strdatee ---> "+strDate);
			
			return strDate;
			}else {
				return null;
			}
		
	
		
	}
	
	//to convert format from dd-MM-yyyy to yyyy-MM-dd
	public String getRev_DateFormat(String date) {
		System.out.println("insideee date formatt  222        "+ date);
		String format = "([0-9]{2})-([0-9]{2})-([0-9]{4})";
		String format2 = "([0-9]{4})-([0-9]{2})-([0-9]{2})";
		// Date date = new Date();  
		if( date != null && "" != date) {
			if(date.matches(format)) {
				System.out.println("match format 1");
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			    Date dt = null;
			    String strDate= null;
				try {
					dt = new SimpleDateFormat("dd-MM-yyyy").parse(date);
					strDate = formatter.format(dt);  
				} catch (ParseException e) {
					strDate = null;
					// TODO Auto-generated catch block
					
					e.printStackTrace();
				}  
			     
			    System.err.println("strdatee 2 ---> "+strDate);  
			
			return strDate;
			//}else {
			//	return null;
			//}
			
		}else if(date.matches(format2)) {
			System.out.println("match format 2");
			/*
			 * SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); Date dt =
			 * null; String strDate= null; try { dt = new
			 * SimpleDateFormat("yyyy-MM-dd").parse(date); strDate = formatter.format(dt); }
			 * catch (ParseException e) { strDate = null; // TODO Auto-generated catch block
			 * 
			 * e.printStackTrace(); }
			 * 
			 * System.out.println("strdatee format2 ---> "+strDate);
			 */
		
		return date;
		//}else {
		//	return null;
		//}
		
	
		}else {
			System.out.println("no format match");
			return null;
		}
			
		}else {
			return null;
		}
		
		
	}
	
	public String addEscapestring(String text) {
	
		text =  text.replaceAll("'", "\\\\'");
		return text;
		
	}
	
	 public Date getSqldateFormat(String date) {
	    	
	    	System.out.println("input dateee --- >"+date+"   "+date.length());
	    	
	    	
	    	switch (date) {
			case "null":
				date = null;
				break;
            case "NIL":
            	date = null;  
	            break;
			default:
				break;
			}
	    	System.out.println("input dateee after switch case--- >"+date);
	    	
	    	
	    	try {
	    		return date != null  ? java.sql.Date.valueOf(date):null;
			} catch (IllegalArgumentException e) {
				  Logger.getLogger(NonSaleControler.class.getName()).log(Level.SEVERE, null, e);
				return null;
				// TODO: handle exception
			}
		
	    	
	    }
	 
	 
	 public java.sql.Date getDbDateFormat(String date){

	    	
	    	System.out.println("input dateee --- >"+date+"   ");
	    	
	    	
	    	String format = "([0-9]{2})-([0-9]{2})-([0-9]{4})";
			String format2 = "([0-9]{4})-([0-9]{2})-([0-9]{2})";
	    	
			
			  if(null != date  && "" != date) {
				  
				  if(date.matches(format)) {
					  
					  
					  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					    Date dt = null;
						try {
							dt = new SimpleDateFormat("dd-MM-yyyy").parse(date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Logger.getLogger(UtilFunctions.class.getName()).log(Level.SEVERE,"parse error in date",e);
						} 
					    
					    String stDate = formatter.format(dt);  
					    System.err.println("formatted stdate---> "+stDate);
					  
					  System.out.println(" converted date__>"+java.sql.Date.valueOf(stDate));
					  return java.sql.Date.valueOf(stDate);
					  
				  }else if(date.matches(format2)) {
					 
					  System.out.println(" converted date 222 __>"+java.sql.Date.valueOf(date));
					  return java.sql.Date.valueOf(date);
					  
				  }else {
					  System.out.println("no date format matched");
					  return null;
				  }
				  
			
			  
			  }else {
				 date = null; 
				  System.out.println("date is null"); 
				  
			  
			  
			  
			  
			  }
			 
	    	
			
	    //	 System.out.println(" converted date__>"+ date == null  ? null :java.sql.Date.valueOf(date));
	    	try {
	    		return date != null  ? java.sql.Date.valueOf(date):null;
			} catch (IllegalArgumentException e) {
				  Logger.getLogger(NonSaleControler.class.getName()).log(Level.SEVERE, null, e);
				return null;
				// TODO: handle exception
			}
		
	    	
	    
	 }
	 
	 public String getUIDateFormat(String date) {
		
		 if(date == null || ("").equals(date)) {
			System.out.println("date is null in utils ---> "+date);
			 
			 return "";
		 }
		 
		 
		 return date;
		 
	 }
	 
	 public String getUITextFormat(String text) {
		 if(text == null || ("").equals(text)) {
				System.out.println("input string is null in utils ---> "+text);
				 
				 return "";
			 }
			 
			 
			 return text;
	 }
}
