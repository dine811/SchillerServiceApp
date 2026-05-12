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
public class Export_PRFOBDao {
    public static void PrfObEngg(String status,String from,String to, HttpServletRequest request,HttpServletResponse response) {
		try {
                    int nam=0;
               HttpSession session = request.getSession();
               nam= (int) session.getAttribute("loguserid");
               String lid=String.valueOf(nam);
                System.out.println(lid+"the lid issss");    
			Connection con=DbConnection.getConnection();
			PreparedStatement ps = null;
			Statement st = con.createStatement();
                     
                        String division=CallRegisterDao.getDivision(lid);
                        System.out.println(division+"division  in daooo");
                        ResultSet rs = st.executeQuery("SELECT * FROM prfobmaster WHERE division='"+division+"' AND status_type='"+status+"' AND entry_date BETWEEN '"+from+"' AND '"+to+"'");
 
                        String path = request.getServletContext().getRealPath("/");
                        File f = new File (path +"PRF-OB");
                        f.mkdir();

                        String xls = request.getServletContext().getRealPath("/PRF-OB")+"/PRF-OB.xls"; 
                 
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
                     // rowhead.createCell((short) 0).setCellValue("DIVISION");
                        rowhead.createCell((short) 0).setCellValue("ENTRY DATE");
                        rowhead.createCell((short) 1).setCellValue("SC_ENGINEER");
                        rowhead.createCell((short) 2).setCellValue("TYPE");
                        rowhead.createCell((short) 3).setCellValue("RAISED DATE");
                        rowhead.createCell((short) 4).setCellValue("RECEIVED DATE FROM SC");
                        rowhead.createCell((short) 5).setCellValue("REGION");
                        rowhead.createCell((short) 6).setCellValue("BRANCH");
                        rowhead.createCell((short) 7).setCellValue("ENGINEER");
                        rowhead.createCell((short) 8).setCellValue("DEALER");
                        rowhead.createCell((short) 9).setCellValue("MODEL");
                        rowhead.createCell((short) 10).setCellValue("SUPPLIER");
                        rowhead.createCell((short) 11).setCellValue("WARRENTY STATUS");
                        rowhead.createCell((short) 12).setCellValue("PRF/OB REF NO.");
                        rowhead.createCell((short) 13).setCellValue("CRM REF NO.");
                        rowhead.createCell((short) 14).setCellValue("PART TYPE");
                        rowhead.createCell((short) 15).setCellValue("PART DESCRIPTION");
                        rowhead.createCell((short) 16).setCellValue("REMARKS");
                        rowhead.createCell((short) 17).setCellValue("STATUS");
                        rowhead.createCell((short) 18).setCellValue("EXECUTED DATE");
                        rowhead.createCell((short) 19).setCellValue("SPARES RECEIVED DATE AT SVC");
                        
			int index = 1;
                        while (rs.next()) {
				HSSFRow row = sheet.createRow((short) index);
				//row.createCell((short) 0).setCellValue(rs.getString(2));
                                row.createCell((short) 0).setCellValue(rs.getString(3));
                                String emname=EmployeeDao.geteName(rs.getString(4));
                                row.createCell((short) 1).setCellValue(emname);
                                row.createCell((short) 2).setCellValue(rs.getString(5));  
                                row.createCell((short) 3).setCellValue(rs.getString(6));
                                row.createCell((short) 4).setCellValue(rs.getString(7));
                                row.createCell((short) 5).setCellValue(rs.getString(8));
                                String branch=BranchDao.getbName(rs.getString(9));
                                row.createCell((short) 6).setCellValue(branch);
                                String engg=EmployeeDao.getEmpName(Integer.parseInt(rs.getString(10)));
                                row.createCell((short) 7).setCellValue(engg);
                                String dealer=DealerDao.getdName(rs.getString(11));
                                row.createCell((short) 8).setCellValue(dealer);
                                row.createCell((short) 9).setCellValue(rs.getString(12));
                                row.createCell((short) 10).setCellValue(rs.getString(13));
                                row.createCell((short) 11).setCellValue(rs.getString(14));
                                row.createCell((short) 12).setCellValue(rs.getString(15));
                                row.createCell((short) 13).setCellValue(rs.getString(16));
                                
                                row.createCell((short) 14).setCellValue(rs.getString(20));
                                row.createCell((short) 15).setCellValue(rs.getString(21));
                                
                                row.createCell((short) 16).setCellValue(rs.getString(17));
                                row.createCell((short) 17).setCellValue(rs.getString(18));
                                row.createCell((short) 18).setCellValue(rs.getString(19));
                                row.createCell((short) 19).setCellValue(rs.getString(23));
                                    
				index++;
                                
			}
			 FileOutputStream fileOut = new FileOutputStream(xls);
			 wb.write(fileOut);
			 fileOut.close();
       //excel download code                 
                     String outfile="PRF-OB.xls";
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
    public static void PrfObAdmin(String status,String from,String to,String division, HttpServletRequest request,HttpServletResponse response) {
		try {
                    int nam=0;
               HttpSession session = request.getSession();
               nam= (int) session.getAttribute("loguserid");
               String lid=String.valueOf(nam);
                    
			Connection con=DbConnection.getConnection();
			PreparedStatement ps = null;
			Statement st = con.createStatement();
                     ResultSet rs=null;
                        if(division.equalsIgnoreCase("1"))
                        {
                            rs = st.executeQuery("SELECT * FROM prfobmaster WHERE status_type='"+status+"' AND entry_date BETWEEN '"+from+"' AND '"+to+"'"); 
                       
                        }
                        else 
                        {
                             rs = st.executeQuery("SELECT * FROM prfobmaster WHERE division='"+division+"' AND status_type='"+status+"' AND entry_date BETWEEN '"+from+"' AND '"+to+"'");
                          
                        }
                        String path = request.getServletContext().getRealPath("/");
                        File f = new File (path +"PRF-OB");
                        f.mkdir();

                        String xls = request.getServletContext().getRealPath("/PRF-OB")+"/PRF-OB.xls"; 
                 
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
                       // rowhead.createCell((short) 0).setCellValue("DIVISION");
			  rowhead.createCell((short) 0).setCellValue("ENTRY DATE");
              rowhead.createCell((short) 1).setCellValue("SC_ENGINEER");
              rowhead.createCell((short) 2).setCellValue("TYPE");
              rowhead.createCell((short) 3).setCellValue("RAISED DATE");
              rowhead.createCell((short) 4).setCellValue("RECEIVED DATE FROM SC");
              rowhead.createCell((short) 5).setCellValue("REGION");
              rowhead.createCell((short) 6).setCellValue("BRANCH");
              rowhead.createCell((short) 7).setCellValue("ENGINEER");
              rowhead.createCell((short) 8).setCellValue("DEALER");
              rowhead.createCell((short) 9).setCellValue("MODEL");
              rowhead.createCell((short) 10).setCellValue("SUPPLIER");
              rowhead.createCell((short) 11).setCellValue("WARRENTY STATUS");
              rowhead.createCell((short) 12).setCellValue("PRF/OB REF NO.");
              rowhead.createCell((short) 13).setCellValue("CRM REF NO.");
              rowhead.createCell((short) 14).setCellValue("PART TYPE");
              rowhead.createCell((short) 15).setCellValue("PART DESCRIPTION");
              rowhead.createCell((short) 16).setCellValue("REMARKS");
              rowhead.createCell((short) 17).setCellValue("STATUS");
              rowhead.createCell((short) 18).setCellValue("EXECUTED DATE");
              rowhead.createCell((short) 19).setCellValue("SPARES RECEIVED DATE AT SVC");
                        
			int index = 1;
                        while (rs.next()) {
				HSSFRow row = sheet.createRow((short) index);
				//row.createCell((short) 0).setCellValue(rs.getString(2));
				 row.createCell((short) 0).setCellValue(rs.getString(3));
                 String emname=EmployeeDao.geteName(rs.getString(4));
                 row.createCell((short) 1).setCellValue(emname);
                 row.createCell((short) 2).setCellValue(rs.getString(5));  
                 row.createCell((short) 3).setCellValue(rs.getString(6));
                 row.createCell((short) 4).setCellValue(rs.getString(7));
                 row.createCell((short) 5).setCellValue(rs.getString(8));
                 String branch=BranchDao.getbName(rs.getString(9));
                 row.createCell((short) 6).setCellValue(branch);
                 String engg=EmployeeDao.getEmpName(Integer.parseInt(rs.getString(10)));
                 row.createCell((short) 7).setCellValue(engg);
                 String dealer=DealerDao.getdName(rs.getString(11));
                 row.createCell((short) 8).setCellValue(dealer);
                 row.createCell((short) 9).setCellValue(rs.getString(12));
                 row.createCell((short) 10).setCellValue(rs.getString(13));
                 row.createCell((short) 11).setCellValue(rs.getString(14));
                 row.createCell((short) 12).setCellValue(rs.getString(15));
                 row.createCell((short) 13).setCellValue(rs.getString(16));
                 
                 row.createCell((short) 14).setCellValue(rs.getString(20));
                 row.createCell((short) 15).setCellValue(rs.getString(21));
                 
                 row.createCell((short) 16).setCellValue(rs.getString(17));
                 row.createCell((short) 17).setCellValue(rs.getString(18));
                 row.createCell((short) 18).setCellValue(rs.getString(19));
                 row.createCell((short) 19).setCellValue(rs.getString(23));
                                    
				index++;
                                
			}
			 FileOutputStream fileOut = new FileOutputStream(xls);
			 wb.write(fileOut);
			 fileOut.close();
       //excel download code                 
                     String outfile="PRF-OB.xls";
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
