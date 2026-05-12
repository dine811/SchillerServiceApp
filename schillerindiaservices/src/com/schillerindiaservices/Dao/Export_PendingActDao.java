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
public class Export_PendingActDao {
    public static void PendingEngg(String from,String to,String status, HttpServletRequest request,HttpServletResponse response) {
		try {
                    int nam=0;
               HttpSession session = request.getSession();
               nam= (int) session.getAttribute("loguserid");
               String role=(String)session.getAttribute("logrole");
//               String lid=String.valueOf(nam);
               String division=PendingActivityDao.PendingActDivName(nam);
               
			Connection con=DbConnection.getConnection();
			PreparedStatement ps = null;
			Statement st = con.createStatement();
                        ResultSet rs=null;
                        if(role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("'ViceChancellor'"))
                        {
                          
                         rs = st.executeQuery("SELECT * FROM pendingactmaster WHERE status_type='"+status+"' AND entry_date between '"+from+"' and '"+to+"'");
                        }else{
                           // String division=PendingActivityDao.PendingActDivName(nam);
                        rs = st.executeQuery("SELECT * FROM pendingactmaster WHERE status_type='"+status+"' AND division='"+division+"' and entry_date between '"+from+"' and '"+to+"'");
                        }
                     
                        
 
                        String path = request.getServletContext().getRealPath("/");
                        File f = new File (path +"PendingActivity");
                        f.mkdir();

                        String xls = request.getServletContext().getRealPath("/PendingActivity")+"/PendingActivity.xls"; 
                 
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
                        // rowhead.createCell((short) 0).setCellValue("DIVISION");
                        rowhead.createCell((short) 0).setCellValue("ENTRY DATE");
                        rowhead.createCell((short) 1).setCellValue("SC_ENGINEER");
                        rowhead.createCell((short) 2).setCellValue("INITIATE_DATE");
                        rowhead.createCell((short) 3).setCellValue("ACTIVITY");
                        rowhead.createCell((short) 4).setCellValue("DESCRIPTION");
                        rowhead.createCell((short) 5).setCellValue("RESPONSIBLE");
                        rowhead.createCell((short) 6).setCellValue("PENDING FORM");
                        rowhead.createCell((short) 7).setCellValue("TARGET");
                        rowhead.createCell((short) 8).setCellValue("REMARKS");
                        rowhead.createCell((short) 9).setCellValue("CLOSED DATE");
                         rowhead.createCell((short) 10).setCellValue("SC INCHARGE REMARKS");
                        rowhead.createCell((short) 11).setCellValue("STATUS");
                        
			int index = 1;
                        while (rs.next()) {
				HSSFRow row = sheet.createRow((short) index);
                               // row.createCell((short) 0).setCellValue(rs.getString(2));
                                row.createCell((short) 0).setCellValue(rs.getString(4));
                                String emname=EmployeeDao.geteName(rs.getString(3));
                                row.createCell((short) 1).setCellValue(emname);
                                row.createCell((short) 2).setCellValue(rs.getString(5));
                                row.createCell((short) 3).setCellValue(rs.getString(6));
                                row.createCell((short) 4).setCellValue(rs.getString(7));
                                row.createCell((short) 5).setCellValue(rs.getString(8));
                                row.createCell((short) 6).setCellValue(rs.getString(9));
                                row.createCell((short) 7).setCellValue(rs.getString(10));
                                row.createCell((short) 8).setCellValue(rs.getString(11));
                                row.createCell((short) 9).setCellValue(rs.getString(12));
                                row.createCell((short) 10).setCellValue(rs.getString(13));
                                row.createCell((short) 11).setCellValue(rs.getString(14));

				index++;
                                
			}
			 FileOutputStream fileOut = new FileOutputStream(xls);
			 wb.write(fileOut);
			 fileOut.close();
       //excel download code                 
                     String outfile="PendingActivity.xls";
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
    
    public static void PendingAdmin(String from,String to,String division,String status, HttpServletRequest request,HttpServletResponse response) {
	//	System.out.println("inside ending ddaaoo"+status);
    	try {
                    int nam=0;
               HttpSession session = request.getSession();
               nam= (int) session.getAttribute("loguserid");
//               String lid=String.valueOf(nam);
                    
			Connection con=DbConnection.getConnection();
			PreparedStatement ps = null;
			Statement st = con.createStatement();
                        ResultSet rs=null;
                     if(division.equalsIgnoreCase("1"))
                     {
                         
                             rs = st.executeQuery("SELECT * FROM pendingactmaster where entry_date between '"+from+"' and '"+to+"' and  status_type='"+status+"'");
                        
                         
                     }else 
                     {
                       
                             rs = st.executeQuery("SELECT * FROM pendingactmaster WHERE division='"+division+"' and entry_date between '"+from+"' and '"+to+"' and status_type='"+status+"'");
                        
                       
                           // rs = st.executeQuery("SELECT * FROM pendingactmaster WHERE status_type='"+status+"' AND division='"+division+"'");
                       
                     }
                         
 
                        String path = request.getServletContext().getRealPath("/");
                        File f = new File (path +"PendingActivity");
                        f.mkdir();

                        String xls = request.getServletContext().getRealPath("/PendingActivity")+"/PendingActivity.xls"; 
                 
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
                         //rowhead.createCell((short) 0).setCellValue("DIVISION");
                        rowhead.createCell((short) 0).setCellValue("ENTRY DATE");
                        rowhead.createCell((short) 1).setCellValue("SC_ENGINEER");
                        rowhead.createCell((short) 2).setCellValue("INITIATE_DATE");
                        rowhead.createCell((short) 3).setCellValue("ACTIVITY");
                        rowhead.createCell((short) 4).setCellValue("DESCRIPTION");
                        rowhead.createCell((short) 5).setCellValue("RESPONSIBLE");
                        rowhead.createCell((short) 6).setCellValue("PENDING FORM");
                        rowhead.createCell((short) 7).setCellValue("TARGET");
                        rowhead.createCell((short) 8).setCellValue("REMARKS");
                        rowhead.createCell((short) 9).setCellValue("CLOSED DATE");
                         rowhead.createCell((short) 10).setCellValue("SC INCHARGE REMARKS");
                        rowhead.createCell((short) 11).setCellValue("STATUS");
                        
			int index = 1;
                        while (rs.next()) {
				HSSFRow row = sheet.createRow((short) index);
                                //row.createCell((short) 0).setCellValue(rs.getString(2));
                                row.createCell((short) 0).setCellValue(rs.getString(4));
                                String emname=EmployeeDao.geteName(rs.getString(3));
                                row.createCell((short) 1).setCellValue(emname);
                                row.createCell((short) 2).setCellValue(rs.getString(5));  
                                row.createCell((short) 3).setCellValue(rs.getString(6));
                                row.createCell((short) 4).setCellValue(rs.getString(7));
                                row.createCell((short) 5).setCellValue(rs.getString(8));
                                row.createCell((short) 6).setCellValue(rs.getString(9));
                                row.createCell((short) 7).setCellValue(rs.getString(10));
                                row.createCell((short) 8).setCellValue(rs.getString(11));
                                row.createCell((short) 9).setCellValue(rs.getString(12));
                                row.createCell((short) 10).setCellValue(rs.getString(13));
                                row.createCell((short) 11).setCellValue(rs.getString(14));

				index++;
                                
			}
			 FileOutputStream fileOut = new FileOutputStream(xls);
			 wb.write(fileOut);
			 fileOut.close();
       //excel download code                 
                     String outfile="PendingActivity.xls";
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
