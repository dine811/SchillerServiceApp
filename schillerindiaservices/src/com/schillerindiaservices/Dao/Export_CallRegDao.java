/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.Dao;

import com.schillerindiaservices.connection.DbConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author ShineLoGics
 */
public class Export_CallRegDao {
    public static void CallRegList(String from,String to, HttpServletRequest request,HttpServletResponse response) {
		
    	System.out.println("insidee callregistlist6");
    	try {
                    int nam=0;
               HttpSession session = request.getSession();
               nam= (int) session.getAttribute("loguserid");
               String lid=String.valueOf(nam);
                    
			Connection con=DbConnection.getConnection();
			PreparedStatement ps = null;
			Statement st = con.createStatement();
                     
                        String division=CallRegisterDao.getDivision(lid);
                        ResultSet rs = st.executeQuery("SELECT * FROM callregister WHERE division='"+division+"' AND call_date BETWEEN '"+from+"' AND '"+to+"' AND status_type='Pending'");
 
                        String path = request.getServletContext().getRealPath("/");
                        File f = new File (path +"CallRegister");
                        f.mkdir();

                        String xls = request.getServletContext().getRealPath("/CallRegister")+"/CallRegister.xls"; 
                 System.out.println("before excel11");
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
                      //  rowhead.createCell((short) 0).setCellValue("DIVISION");
                        rowhead.createCell((short) 1).setCellValue("ENTRY DATE");
                        rowhead.createCell((short) 2).setCellValue("SC_ENGINEER");
                        rowhead.createCell((short) 3).setCellValue("CALL_DATE");
                        rowhead.createCell((short) 4).setCellValue("CALL");
                        rowhead.createCell((short) 5).setCellValue("ENGINEER");
                        rowhead.createCell((short) 6).setCellValue("MODEL/REASON");
                        rowhead.createCell((short) 7).setCellValue("TYPE_OF_CALL");
                        rowhead.createCell((short) 8).setCellValue("TYPE_OF_COMMUNICATION");
                        rowhead.createCell((short) 9).setCellValue("REMARKS");
                        rowhead.createCell((short) 10).setCellValue("DURATION");
                        rowhead.createCell((short) 11).setCellValue("STATUS");
                        
			int index = 1;
                        while (rs.next()) {
                        	//System.out.println("insideee whhileee");
				HSSFRow row = sheet.createRow((short) index);
				//row.createCell((short) 0).setCellValue(rs.getString(2));
				//System.out.println("the db valuee"+rs.getString(2));
                                row.createCell((short) 1).setCellValue(rs.getString(16));
                                String emname=EmployeeDao.geteName(rs.getString(3));
                                row.createCell((short) 2).setCellValue(emname);
                                row.createCell((short) 3).setCellValue(rs.getString(4));  
                                row.createCell((short) 4).setCellValue(rs.getString(5));
                                row.createCell((short) 5).setCellValue(rs.getString(9));
                                row.createCell((short) 6).setCellValue(rs.getString(10));
                                row.createCell((short) 7).setCellValue(rs.getString(11));
                                row.createCell((short) 8).setCellValue(rs.getString(12));
                                row.createCell((short) 9).setCellValue(rs.getString(13));
                                row.createCell((short) 10).setCellValue(rs.getString(14));
                                row.createCell((short) 11).setCellValue(rs.getString(15));
                                    
				index++;
				//System.out.println("indesxx iss"+index);
                                
			}
                       // System.out.println("indesxx iss"+index);
                        
                      // System.out.println("after ecxcel");
			 FileOutputStream fileOut = new FileOutputStream(xls);
			 wb.write(fileOut);
			 fileOut.close();
       //excel download code                 
                     String outfile="CallRegister.xls";
                     response.setHeader("Content-disposition","attachment; filename="+outfile);          
                     FileInputStream in = new FileInputStream(xls);
                     OutputStream out = response.getOutputStream();

                     byte[] buffer= new byte[8192]; // use bigger if you want
                     int length = 0;

                     while ((length = in.read(buffer)) > 0){
                         out.write(buffer, 0, length);
                     }
                     in.close();
                     out.close();
			rs.close();
			con.close();
		} catch (ClassNotFoundException | SQLException | IOException e) {
                   
   		}
    
}
    
    public static void AdminCallRegList(String from,String to,String division, HttpServletRequest request,HttpServletResponse response) {
		try {
                    int nam=0;
               HttpSession session = request.getSession();
               nam= (int) session.getAttribute("loguserid");
               String lid=String.valueOf(nam);
                    
			Connection con=DbConnection.getConnection();
			PreparedStatement ps = null;
                        ResultSet rs=null;
			Statement st = con.createStatement();
                        if(division.equalsIgnoreCase("1"))
                        {
                           rs = st.executeQuery("SELECT * FROM callregister WHERE call_date BETWEEN '"+from+"' AND '"+to+"'"); //ignore this line 
                        }else if(division.equalsIgnoreCase("2"))
                        {
                            rs=st.executeQuery("SELECT * FROM callregister WHERE status_type='Pending' ");
                        }
                        else{
                           rs = st.executeQuery("SELECT * FROM callregister WHERE division='"+division+"' AND call_date BETWEEN '"+from+"' AND '"+to+"' AND status_type='Pending'");  
                        }
                     
                       
 
                        String path = request.getServletContext().getRealPath("/");
                        File f = new File (path +"CallRegister");
                        f.mkdir();

                        String xls = request.getServletContext().getRealPath("/CallRegister")+"/CallRegister.xls"; 
                 
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
                     //   rowhead.createCell((short) 0).setCellValue("DIVISION");
                        rowhead.createCell((short) 1).setCellValue("ENTRY DATE");
                        rowhead.createCell((short) 2).setCellValue("SC_ENGINEER");
                        rowhead.createCell((short) 3).setCellValue("CALL_DATE");
                        rowhead.createCell((short) 4).setCellValue("CALL");
                        rowhead.createCell((short) 5).setCellValue("ENGINEER");
                        rowhead.createCell((short) 6).setCellValue("MODEL/REASON");
                        rowhead.createCell((short) 7).setCellValue("TYPE_OF_CALL");
                        rowhead.createCell((short) 8).setCellValue("TYPE_OF_COMMUNICATION");
                        rowhead.createCell((short) 9).setCellValue("REMARKS");
                        rowhead.createCell((short) 10).setCellValue("DURATION");
                        rowhead.createCell((short) 11).setCellValue("STATUS");
                        
			int index = 1;
                        while (rs.next()) {
				HSSFRow row = sheet.createRow((short) index);
			//	row.createCell((short) 0).setCellValue(rs.getString(2));
                                row.createCell((short) 1).setCellValue(rs.getString(16));
                                String emname=EmployeeDao.geteName(rs.getString(3));
                                row.createCell((short) 2).setCellValue(emname);
                                row.createCell((short) 3).setCellValue(rs.getString(4));  
                                row.createCell((short) 4).setCellValue(rs.getString(5));
                                 row.createCell((short) 5).setCellValue(rs.getString(9));
                                row.createCell((short) 6).setCellValue(rs.getString(10));
                                row.createCell((short) 7).setCellValue(rs.getString(11));
                                row.createCell((short) 8).setCellValue(rs.getString(12));
                                row.createCell((short) 9).setCellValue(rs.getString(13));
                                row.createCell((short) 10).setCellValue(rs.getString(14));
                                row.createCell((short) 11).setCellValue(rs.getString(15));
                                    
				index++;
                                
			}
			 FileOutputStream fileOut = new FileOutputStream(xls);
			 wb.write(fileOut);
			 fileOut.close();
       //excel download code                 
                     String outfile="CallRegister.xls";
                     response.setHeader("Content-disposition","attachment; filename="+outfile);          
                     FileInputStream in = new FileInputStream(xls);
                     OutputStream out = response.getOutputStream();

                     byte[] buffer= new byte[8192]; // use bigger if you want
                     int length = 0;

                     while ((length = in.read(buffer)) > 0){
                         out.write(buffer, 0, length);
                     }
                     in.close();
                     out.close();
			rs.close();
			con.close();
		} catch (ClassNotFoundException | SQLException | IOException e) {
                   
   		}
    
}
     public static void ClosedCalls(String from,String to,String division,String mi, HttpServletRequest request,HttpServletResponse response) {
	//	System.out.println("insidee exportcall controller daooo");
    	 
    	 try {
                    int nam=0;
               HttpSession session = request.getSession();
               nam= (int) session.getAttribute("loguserid");
               String lid=String.valueOf(nam);
                    String divi=CallRegisterDao.getDivision(lid);
                   // System.out.println("the divu isss"+divi);
			Connection con=DbConnection.getConnection();
			PreparedStatement ps = null;
                        ResultSet rs=null;
			Statement st = con.createStatement();
                        if(mi.equalsIgnoreCase("cc"))//check for admin 
                        {
                        //	System.out.println("insidee if");
                        if(division.equalsIgnoreCase("1"))
                        {
                           rs=st.executeQuery("SELECT * FROM callregister WHERE status_type='Completed'");
                        }else 
                        {
                            rs = st.executeQuery("SELECT * FROM callregister WHERE division='"+division+"' AND call_date BETWEEN '"+from+"' AND '"+to+"' AND status_type='Completed'");  
                            
                        }}
                        else{
                        	//System.out.println("inside the else conditionnnnnn");
                           rs = st.executeQuery("SELECT * FROM callregister WHERE division='"+divi+"' AND call_date BETWEEN '"+from+"' AND '"+to+"' AND status_type='Completed'");  
                        }
                     
                       
 
                        String path = request.getServletContext().getRealPath("/");
                        File f = new File (path +"CallRegister");
                        f.mkdir();

                        String xls = request.getServletContext().getRealPath("/CallRegister")+"/CallRegister.xls"; 
               //  System.out.println("before excel");
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
                       // rowhead.createCell((short) 0).setCellValue("DIVISION");
                        rowhead.createCell((short) 0).setCellValue("ENTRY DATE");
                        rowhead.createCell((short) 1).setCellValue("SC_ENGINEER");
                        rowhead.createCell((short) 2).setCellValue("CALL_DATE");
                        rowhead.createCell((short) 3).setCellValue("CALL");
                        rowhead.createCell((short) 4).setCellValue("ENGINEER");
                        rowhead.createCell((short) 5).setCellValue("MODEL/REASON");
                        rowhead.createCell((short) 6).setCellValue("TYPE_OF_CALL");
                        rowhead.createCell((short) 7).setCellValue("TYPE_OF_COMMUNICATION");
                        rowhead.createCell((short) 8).setCellValue("REMARKS");
                        rowhead.createCell((short) 9).setCellValue("DURATION");
                        rowhead.createCell((short) 10).setCellValue("STATUS");
                        
			int index = 1;
                        while (rs.next()) {
                        	//System.out.println("insidee whileee exportcalls");
				HSSFRow row = sheet.createRow((short) index);
				//row.createCell((short) 0).setCellValue(rs.getString(2));
				//System.out.println("value 1"+rs.getString(16));
                                row.createCell((short) 0).setCellValue(rs.getString(16));
                                String emname=EmployeeDao.geteName(rs.getString(3));
                                row.createCell((short) 1).setCellValue(emname);
                                row.createCell((short) 2).setCellValue(rs.getString(4));  
                                row.createCell((short) 3).setCellValue(rs.getString(5));
                                 row.createCell((short) 4).setCellValue(rs.getString(9));
                                row.createCell((short) 5).setCellValue(rs.getString(10));
                                row.createCell((short) 6).setCellValue(rs.getString(11));
                                row.createCell((short) 7).setCellValue(rs.getString(12));
                                row.createCell((short) 8).setCellValue(rs.getString(13));
                                row.createCell((short) 9).setCellValue(rs.getString(14));
                                row.createCell((short) 10).setCellValue(rs.getString(15));
                                    
				index++;
				System.out.println("the inex ss"+index);
                                
			}
                    	System.out.println("the inex ss"+index);
                        System.out.println("after excel");
			 FileOutputStream fileOut = new FileOutputStream(xls);
			 wb.write(fileOut);
			 fileOut.close();
       //excel download code                 
                     String outfile="CallRegister.xls";
                     response.setHeader("Content-disposition","attachment; filename="+outfile);          
                     FileInputStream in = new FileInputStream(xls);
                     OutputStream out = response.getOutputStream();

                     byte[] buffer= new byte[8192]; // use bigger if you want
                     int length = 0;

                     while ((length = in.read(buffer)) > 0){
                         out.write(buffer, 0, length);
                     }
                     in.close();
                     out.close();
			rs.close();
			con.close();
		} catch (ClassNotFoundException | SQLException | IOException e) {
                   
   		}
    
}
}
