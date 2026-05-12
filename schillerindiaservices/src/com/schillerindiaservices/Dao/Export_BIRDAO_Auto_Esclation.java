/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Mail_id_entry;
import com.schillerindiaservices.connection.DbConnection;
import com.schillerindiaservices.email.SendMailwithAttachment;

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
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
public class Export_BIRDAO_Auto_Esclation {
    public static void BIREngg(String from,String to,String status,HttpServletRequest request,HttpServletResponse response) {
	//	String from1=from.concat(" 00:00:00");
	//	String to1=from.concat(" 23:59:59");
    	
    	try {
			
                    int nam=0;
               HttpSession session = request.getSession();
               nam= (int) session.getAttribute("loguserid");
               String role=(String)session.getAttribute("logrole");
               System.out.println(role+"the role isss");
               String lid=String.valueOf(nam);
			Connection con=DbConnection.getConnection();
			PreparedStatement st = null;
                        String div_id=BIRDao.getDivision(lid);
                        String division=ProductDao.getProdName(div_id);
                        ResultSet rs =null;
                        if(role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("FQC"))
                        {
                            st=con.prepareStatement("SELECT * FROM birmaster WHERE final_status='"+status+"' AND fqc_in_date between '"+from+"' and '"+to+"'");
                            System.out.println("st "+st);
                        }else{
                            st=con.prepareStatement("SELECT * FROM birmaster WHERE division='"+division+"' AND final_status='"+status+"' and fqc_in_date between '"+from+"' and '"+to+"'");
                        }
                        rs=st.executeQuery();
                        String path = request.getServletContext().getRealPath("/");
                        File f = new File (path +"Bir");
                        f.mkdir();

                        String xls = request.getServletContext().getRealPath("/Bir")+"/BIR.xls"; 
                 
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
                         rowhead.createCell((short) 0).setCellValue("DIVISION");
                        rowhead.createCell((short) 1).setCellValue("INWARD DATE");
                        rowhead.createCell((short) 2).setCellValue("ENTRY DATE");
                        rowhead.createCell((short) 3).setCellValue("BIR REF NO.");
                        rowhead.createCell((short) 4).setCellValue("SUPPLIER");
                        rowhead.createCell((short) 5).setCellValue("MODEL");
                        rowhead.createCell((short) 6).setCellValue("CONFIGURATION");
                        rowhead.createCell((short) 7).setCellValue("RECEIVED QTY");
                        rowhead.createCell((short) 8).setCellValue("UNIT SERIAL NO.");
                        rowhead.createCell((short) 9).setCellValue("INVOICE NO.");
                        rowhead.createCell((short) 10).setCellValue("INVOICE DATE");
                        rowhead.createCell((short) 11).setCellValue("PREVIOUS SOFTWARE VERSION");
                        rowhead.createCell((short) 12).setCellValue("PRESENT SOFTWARE VERSION");
                        rowhead.createCell((short) 13).setCellValue("ACCESSORY CHANGES");
                        rowhead.createCell((short) 14).setCellValue("ACCESSORY DETAILS");
                        rowhead.createCell((short) 15).setCellValue("USER MANUAL UPDATE");
                        rowhead.createCell((short) 16).setCellValue("FQC REMARKS");
                        rowhead.createCell((short) 17).setCellValue("SC ENGINEER");
                        rowhead.createCell((short) 18).setCellValue("ACCESSORY CHANGES REMARKS");
                        rowhead.createCell((short) 19).setCellValue("HARDWARE CHANGES");
                        rowhead.createCell((short) 20).setCellValue("SERVICE MANUAL UPDATE");
                        rowhead.createCell((short) 21).setCellValue("HARDWARE CHANGES REMARKS");
                        rowhead.createCell((short) 22).setCellValue("TS TEAM VERIFICATION DATE");
                        rowhead.createCell((short) 23).setCellValue("PS ENGINEER");
                        rowhead.createCell((short) 24).setCellValue("SOFTWARE CHANGES REMARKS");
                        rowhead.createCell((short) 25).setCellValue("CNR/TECHNEWS CIRCULATION");
                        rowhead.createCell((short) 26).setCellValue("CNR/TECHNEWS REF NO.");
                        rowhead.createCell((short) 27).setCellValue("CNR/TECHNEWS RELESE DATE");
                        rowhead.createCell((short) 28).setCellValue("PS TEAM VERIFICATION DATE");
                        rowhead.createCell((short) 29).setCellValue("APPROVIED DATE");
                        rowhead.createCell((short) 30).setCellValue("STATUS");
                        rowhead.createCell((short) 31).setCellValue("CLOSED DATE");
                        rowhead.createCell((short) 32).setCellValue("TIMESTAMP");
                        
			int index = 1;
                        while (rs.next()) {
                            String model=ModelDao.getModelname(rs.getString(5));
				HSSFRow row = sheet.createRow((short) index);
				                row.createCell((short) 0).setCellValue(rs.getString(2));
                                row.createCell((short) 1).setCellValue(rs.getString(4));
                                row.createCell((short) 2).setCellValue(rs.getString(3));
                                row.createCell((short) 3).setCellValue(rs.getString(29));
                                row.createCell((short) 4).setCellValue(rs.getString(30));
                                
                                row.createCell((short) 5).setCellValue(model);//Model
                                
                                row.createCell((short) 6).setCellValue(rs.getString(6));
                                row.createCell((short) 7).setCellValue(rs.getString(7));
                                row.createCell((short) 8).setCellValue(rs.getString(8));
                                row.createCell((short) 9).setCellValue(rs.getString(9));
                                row.createCell((short) 10).setCellValue(rs.getString(10));
                                row.createCell((short) 11).setCellValue(rs.getString(11));
                                row.createCell((short) 12).setCellValue(rs.getString(12));
                                row.createCell((short) 13).setCellValue(rs.getString(15));
                                row.createCell((short) 14).setCellValue(rs.getString(16));
                                row.createCell((short) 15).setCellValue(rs.getString(17));
                                row.createCell((short) 16).setCellValue(rs.getString(19));
                                
                                String scname=EmployeeDao.geteName(rs.getString(31));
                                row.createCell((short) 17).setCellValue(scname);//SC Engineer
                                
                                row.createCell((short) 18).setCellValue(rs.getString(25));
                                row.createCell((short) 19).setCellValue(rs.getString(13));
                                row.createCell((short) 20).setCellValue(rs.getString(18));
                                row.createCell((short) 21).setCellValue(rs.getString(26));
                                row.createCell((short) 22).setCellValue(rs.getString(21));
                                row.createCell((short) 23).setCellValue(rs.getString(32));
                                row.createCell((short) 24).setCellValue(rs.getString(27));
                                row.createCell((short) 25).setCellValue(rs.getString(20));
                                row.createCell((short) 26).setCellValue(rs.getString(34));
                                row.createCell((short) 27).setCellValue(rs.getString(28));
                                row.createCell((short) 28).setCellValue(rs.getString(22));
                                row.createCell((short) 29).setCellValue(rs.getString(33));
                                row.createCell((short) 30).setCellValue(rs.getString(23));
                                row.createCell((short) 31).setCellValue(rs.getString(24));
                                row.createCell((short) 32).setCellValue(rs.getString(36));
                               
				index++;
                                
			}
			 FileOutputStream fileOut = new FileOutputStream(xls);
			 wb.write(fileOut);
			 fileOut.close();
       //excel download code                 
                     String outfile="BIR.xls";
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
                   e.printStackTrace();
   		}
    
}
    public static void BIRAdmin_AutoEsclation(String fromdate,String todate,String report_type) throws MessagingException {
    	//String from1=from.concat(" 00:00:00");
//		String to1=to.concat(" 23:59:59");
    	//System.out.println("the from 1 iss"+from);
    //	System.out.println("the to 1 iss"+to);
    //	System.out.println("the status iss"+status);
    //	System.out.println("the div iuss 22"+division);
    	LocalDate ldt1=LocalDate.now();
		//System.out.println("today date issss");
    	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String formattedString = ldt1.format(formatter);
		//System.out.println(formattedString+"the f string isss");
		//('"+formattedString+"')"; 
    	 
   	 List<Mail_id_entry> list=null;
      	try {
  			list=getemailid_list(report_type);
  		} catch (ClassNotFoundException e1) {
  			// TODO Auto-generated catch block
  			e1.printStackTrace();
  		} catch (SQLException e1) {
  			// TODO Auto-generated catch block
  			e1.printStackTrace();
  		}
     	 
      	if(list.size()>0&&list!=null){
         	
         	for(Mail_id_entry mie:list){
    	
    	
    	try {/*
                    int nam=0;
               HttpSession session = request.getSession();
               nam= (int) session.getAttribute("loguserid");
               String lid=String.valueOf(nam);*/
                    
			Connection con=DbConnection.getConnection();
			PreparedStatement ps = null;
			Statement st = con.createStatement();
                        ResultSet rs=null;
                       
                        
                        if(mie.getTemp1().equalsIgnoreCase("all"))
                        {
                        rs = st.executeQuery("SELECT * FROM birmaster WHERE final_status='Pending' and current_date between '"+fromdate+"' and '"+todate+"'");
                       
                       // System.out.println("SELECT * FROM birmaster WHERE final_status='Pending' and current_date between '"+fromdate+"' and '"+todate+"'"+"     bir--pendingg"); 
                        
                        }
                        else
                        {
                         rs = st.executeQuery("SELECT * FROM birmaster WHERE division='"+mie.getTemp1()+"' AND final_status='Pending' and current_date between '"+fromdate+"' and '"+todate+"'");
                          
                       //  System.out.println("SELECT * FROM birmaster WHERE final_status='Pending' and current_date between '"+fromdate+"' and '"+todate+"'"+"     bir--pendingg1");
                        }
                       /* String path = request.getServletContext().getRealPath("/");
                        File f = new File (path +"Bir");
                        f.mkdir();*/

                        String xls = "BIR.xls"; 
                 
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
             rowhead.createCell((short) 0).setCellValue("DIVISION");
			// rowhead.createCell((short) 1).setCellValue("INWARD DATE");
             rowhead.createCell((short) 1).setCellValue("ENTRY DATE");
             rowhead.createCell((short) 2).setCellValue("BIR REF NO.");
             rowhead.createCell((short) 3).setCellValue("SUPPLIER");
             rowhead.createCell((short) 4).setCellValue("MODEL");
             rowhead.createCell((short) 5).setCellValue("CONFIGURATION");
             rowhead.createCell((short) 6).setCellValue("RECEIVED QTY");
            // rowhead.createCell((short) 8).setCellValue("UNIT SERIAL NO.");
           //  rowhead.createCell((short) 9).setCellValue("INVOICE NO.");
           //  rowhead.createCell((short) 10).setCellValue("INVOICE DATE");
           //  rowhead.createCell((short) 11).setCellValue("PREVIOUS SOFTWARE VERSION");
           //  rowhead.createCell((short) 12).setCellValue("PRESENT SOFTWARE VERSION");
           //  rowhead.createCell((short) 13).setCellValue("ACCESSORY CHANGES");
           //  rowhead.createCell((short) 14).setCellValue("ACCESSORY DETAILS");
           //  rowhead.createCell((short) 15).setCellValue("USER MANUAL UPDATE");
             rowhead.createCell((short) 7).setCellValue("FQC REMARKS");
           //  rowhead.createCell((short) 17).setCellValue("SC ENGINEER");
             //rowhead.createCell((short) 18).setCellValue("ACCESSORY CHANGES REMARKS");
            // rowhead.createCell((short) 19).setCellValue("HARDWARE CHANGES");
           //  rowhead.createCell((short) 20).setCellValue("SERVICE MANUAL UPDATE");
           //  rowhead.createCell((short) 21).setCellValue("HARDWARE CHANGES REMARKS");
             rowhead.createCell((short) 8).setCellValue("TS TEAM VERIFICATION DATE");
          //   rowhead.createCell((short) 23).setCellValue("PS ENGINEER");
          ///   rowhead.createCell((short) 24).setCellValue("SOFTWARE CHANGES REMARKS");
          //   rowhead.createCell((short) 25).setCellValue("CNR/TECHNEWS CIRCULATION");
          //   rowhead.createCell((short) 26).setCellValue("CNR/TECHNEWS REF NO.");
          //   rowhead.createCell((short) 27).setCellValue("CNR/TECHNEWS RELESE DATE");
             rowhead.createCell((short) 9).setCellValue("PS TEAM VERIFICATION DATE");
         //    rowhead.createCell((short) 29).setCellValue("APPROVIED DATE");
             rowhead.createCell((short) 10).setCellValue("STATUS");
         //    rowhead.createCell((short) 31).setCellValue("CLOSED DATE");
             rowhead.createCell((short) 11).setCellValue("TIMESTAMP");
			int index = 1;
                        while (rs.next()) {
                        	//System.out.println("iside whileeee");
                             String model=ModelDao.getModelname(rs.getString(5));
				HSSFRow row = sheet.createRow((short) index);
				row.createCell((short) 0).setCellValue(rs.getString(2));
			//	 row.createCell((short) 1).setCellValue(rs.getString(4));
                 row.createCell((short) 1).setCellValue(rs.getString(3));
                 row.createCell((short) 2).setCellValue(rs.getString(29));
                 row.createCell((short) 3).setCellValue(rs.getString(30));
                 
                 row.createCell((short) 4).setCellValue(model);//Model
                 
                 row.createCell((short) 5).setCellValue(rs.getString(6));
                 row.createCell((short) 6).setCellValue(rs.getString(7));
            //     row.createCell((short) 8).setCellValue(rs.getString(8));
           //      row.createCell((short) 9).setCellValue(rs.getString(9));
             //    row.createCell((short) 10).setCellValue(rs.getString(10));
           //      row.createCell((short) 11).setCellValue(rs.getString(11));
             //    row.createCell((short) 12).setCellValue(rs.getString(12));
             //    row.createCell((short) 13).setCellValue(rs.getString(15));
             //    row.createCell((short) 14).setCellValue(rs.getString(16));
             //    row.createCell((short) 15).setCellValue(rs.getString(17));
                 row.createCell((short) 7).setCellValue(rs.getString(19));
                 
                 String scname=EmployeeDao.geteName(rs.getString(31));
            //     row.createCell((short) 17).setCellValue(scname);//SC Engineer
                 
           //      row.createCell((short) 18).setCellValue(rs.getString(25));
           //      row.createCell((short) 19).setCellValue(rs.getString(13));
            //     row.createCell((short) 20).setCellValue(rs.getString(18));
          //       row.createCell((short) 21).setCellValue(rs.getString(26));
                 row.createCell((short) 8).setCellValue(rs.getString(21));
         //        row.createCell((short) 23).setCellValue(rs.getString(32));
         //        row.createCell((short) 24).setCellValue(rs.getString(27));
        //         row.createCell((short) 25).setCellValue(rs.getString(20));
       //          row.createCell((short) 26).setCellValue(rs.getString(34));
       //          row.createCell((short) 27).setCellValue(rs.getString(28));
                 row.createCell((short) 9).setCellValue(rs.getString(22));
      //           row.createCell((short) 29).setCellValue(rs.getString(33));
                 row.createCell((short) 10).setCellValue(rs.getString(23));
      //           row.createCell((short) 31).setCellValue(rs.getString(24));
                 row.createCell((short) 11).setCellValue(rs.getString(37));   
				index++;
                   // System.out.println("end of loop");            
			}
			 FileOutputStream fileOut = new FileOutputStream(xls);
			 wb.write(fileOut);
			 fileOut.close();
       //excel download code                 
                     String outfile="BIR.xls";
                    // response.setHeader("Content-disposition","attachment; filename="+outfile);    
                     
                     
                   //  String pass=(String) session.getAttribute("password");*/
       			  String from="Schillerindiaservice";
                     String sub="BIR-PENDING Escalation('"+formattedString+"')"; 
                     //String to=mail1;
                    // String to="harini.keerthana95@gmail.com";  
                     String to=mie.getMail_id();  
                   //  System.out.println(to+"the to mail idd iss");
                    // String cc=mail2;  
                     //String cc="dinesha123445@gmail.com";  
                     String cc=mie.getMail_id();  
                    // System.out.println(cc+"the cc mail id issss");
                     String msgbody="Dear All, "
                      		+ "Greetings!"
                       		+ "Kindly find the attached BIR-PENDING Escalation Report for your reference and further proceedings";
                       
                     SendMailwithAttachment.sendEmailAttachment(from, to, sub, msgbody, "", cc, "BIR.xls");
                     
                     FileInputStream in = new FileInputStream(xls);
                  //   OutputStream out = response.getOutputStream();

                     byte[] buffer= new byte[8192]; // use bigger if you want
                     int length = 0;

                    /* while ((length = in.read(buffer)) > 0){
                         out.write(buffer, 0, length);
                     }*/
                     in.close();
                    // out.close();
			rs.close();
			con.close();
		} catch (ClassNotFoundException | SQLException | IOException e) {
		}         
   		
         	}}
}
    
    public static List<Mail_id_entry> getemailid_list(String report_type) throws ClassNotFoundException, SQLException{
  		Connection con=null;
  		List<Mail_id_entry> list=new ArrayList<Mail_id_entry>();
  		con=DbConnection.getConnection();
  		PreparedStatement ps=null;
  		Statement st = con.createStatement();
          ResultSet rs = st.executeQuery("select * from mail_id_entry where report_type='"+report_type+"' or report_type='all'");
  		
  		while(rs.next()){
  			Mail_id_entry m_entry=new Mail_id_entry();
  			
  			m_entry.setMail_id(rs.getString("mail_id"));
  			m_entry.setReport_type(rs.getString("report_type"));
  			m_entry.setTemp1(rs.getString("temp1"));
  			list.add(m_entry);
  		}
  	//	System.out.println("the list size iss"+list.size());
  		return list;
  	} 
    
    
}
