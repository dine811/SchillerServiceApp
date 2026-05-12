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
public class ExportNonSaleDao2_Auotesclation {
    public static void NonSaleEngg(String from,String to,String status,HttpServletRequest request,HttpServletResponse response) {
		//System.out.println("insie nonsale dao");
    	try {
                    int nam=0;
               HttpSession session = request.getSession();
               nam = (int) session.getAttribute("loguserid");
               ////System.out.println("nam isss"+nam);
               String lid=String.valueOf(nam);
             // String name1= EmployeeDao.geteName(lid);
             // //System.out.println("the sysout name isss"+name1);
                     String division_id=NonSaleDao.getDivision_name(nam);
                   //  //System.out.println("the division issssss"+division_id);
                 String division=ProductDao.getProdName(division_id);
               //  //System.out.println("the division name issssss"+division_id);
			Connection con=DbConnection.getConnection();
			PreparedStatement ps = null;
                        if(status.equalsIgnoreCase("1"))
                        {
                            ps = con.prepareStatement("SELECT * FROM nonsaleablemaster WHERE division='"+division+"' and entry_date between '"+from+"' and '"+to+"'");
                        }else{
                        ////System.out.println("inside else");
			ps = con.prepareStatement("SELECT * FROM nonsaleablemaster WHERE division='"+division+"' AND final_status='"+status+"' and entry_date between '"+from+"' and '"+to+"'");
                        }
                       
                        ResultSet rs = ps.executeQuery();
                        String path = request.getServletContext().getRealPath("/");
                        File f = new File (path +"Non-Salable");
                        f.mkdir();

                        String xls = request.getServletContext().getRealPath("/Non-Salable")+"/Non-Salable.xls"; 
                // //System.out.println("before excel");
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
                        //rowhead.createCell((short) 0).setCellValue("DIVISION");
                        rowhead.createCell((short) 1).setCellValue("ENTRY DATE");
                        rowhead.createCell((short) 2).setCellValue("UNIT DETAILS");
                        rowhead.createCell((short) 3).setCellValue("FQC INWARD DATE");
                        rowhead.createCell((short) 4).setCellValue("REGION");
                        rowhead.createCell((short) 5).setCellValue("BRANCH");
                        rowhead.createCell((short) 6).setCellValue("ENGINEER");
                        rowhead.createCell((short) 7).setCellValue("DEALER");
                        rowhead.createCell((short) 8).setCellValue("SUPPLIER");
                        rowhead.createCell((short) 9).setCellValue("MODEL");
                        rowhead.createCell((short) 10).setCellValue("MODEL S/N");
                        rowhead.createCell((short) 11).setCellValue("UNIT CONFIGURATION");
                        rowhead.createCell((short) 12).setCellValue("CUSTOMER NAME");
                        rowhead.createCell((short) 13).setCellValue("REPORTED PROBLEM");
                        rowhead.createCell((short) 14).setCellValue("REPLACED UNIT S/N");
                        rowhead.createCell((short) 15).setCellValue("REPLACEMENT SHIP DATE");
                        rowhead.createCell((short) 16).setCellValue("DEFECTIVE UNIT RECEIVED DATE");
                        rowhead.createCell((short) 17).setCellValue("FQC OBSERVATION");
                        rowhead.createCell((short) 18).setCellValue("SC INWARD DATE");
                        rowhead.createCell((short) 19).setCellValue("SC ENGINEER");
                        rowhead.createCell((short) 20).setCellValue("SC OBSERVATION");
                        rowhead.createCell((short) 21).setCellValue("REQUIRED PARTS");
                        rowhead.createCell((short) 22).setCellValue("ROOT CAUSE");
                        rowhead.createCell((short) 23).setCellValue("SC ACTION PLAN");
                        rowhead.createCell((short) 24).setCellValue("TENTATIVE DATE");
                        rowhead.createCell((short) 25).setCellValue("SHIP DATE TO FQC");
                        rowhead.createCell((short) 26).setCellValue("FQC FINAL REMARKS");
                        rowhead.createCell((short) 27).setCellValue("FINAL STATUS");
                        
			int index = 1;
                        while (rs.next()) {
                        	////System.out.println("insidee whileee");
				HSSFRow row = sheet.createRow((short) index);
				row.createCell((short) 0).setCellValue(rs.getString(2));
				////System.out.println("insidee whileee"+rs.getString(3));
                                row.createCell((short) 1).setCellValue(rs.getString(3));
                                row.createCell((short) 2).setCellValue(rs.getString(4));
                                row.createCell((short) 3).setCellValue(rs.getString(5));  
                                row.createCell((short) 4).setCellValue(rs.getString(6));
                                String branch=BranchDao.getbName(rs.getString(7));
                                row.createCell((short) 5).setCellValue(branch);
                                 String emname=EmployeeDao.geteName(rs.getString(8));
                                row.createCell((short) 6).setCellValue(emname);
                                String dealer=DealerDao.getdName(rs.getString(9));
                                row.createCell((short) 7).setCellValue(dealer);
                                row.createCell((short) 8).setCellValue(rs.getString(10));
                                String model=ModelDao.getModelname(rs.getString(11));
                                row.createCell((short) 9).setCellValue(model);
                                row.createCell((short) 10).setCellValue(rs.getString(12));
                                row.createCell((short) 11).setCellValue(rs.getString(13));
                                row.createCell((short) 12).setCellValue(rs.getString(14));
                                row.createCell((short) 13).setCellValue(rs.getString(15));
                                row.createCell((short) 14).setCellValue(rs.getString(16));
                                 row.createCell((short) 15).setCellValue(rs.getString(17));
                                 row.createCell((short) 16).setCellValue(rs.getString(18));
                                 row.createCell((short) 17).setCellValue(rs.getString(19));
                                 row.createCell((short) 18).setCellValue(rs.getString(20));
                                String engg=EmployeeDao.getname(rs.getString(21));
                                 row.createCell((short) 19).setCellValue(engg);
                                 row.createCell((short) 20).setCellValue(rs.getString(22));
                                 row.createCell((short) 21).setCellValue(rs.getString(23));
                                 row.createCell((short) 22).setCellValue(rs.getString(24));
                                 row.createCell((short) 23).setCellValue(rs.getString(25));
                                 row.createCell((short) 24).setCellValue(rs.getString(26));
                                 row.createCell((short) 25).setCellValue(rs.getString(27));
                                 row.createCell((short) 26).setCellValue(rs.getString(28));
                                 row.createCell((short) 27).setCellValue(rs.getString(29));
                                    
				index++;
			//	//System.out.println(index+"  indes iss");
                                
			}
                        
                       // //System.out.println("after excel nojnsaa");
			 FileOutputStream fileOut = new FileOutputStream(xls);
			 wb.write(fileOut);
			 fileOut.close();
       //excel download code                 
                     String outfile="Non-Salable.xls";
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
    
    public static void NonSaleAdmin_list(String fromdate,String todate,String report_type) throws MessagingException {
		//System.out.println("insidee nonsale admin dao");
    	
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
		
		try {
                    /*int nam=0;
               HttpSession session = request.getSession();
               nam= (int) session.getAttribute("loguserid");
               String lid=String.valueOf(nam);*/
                    
			Connection con=DbConnection.getConnection();
			PreparedStatement ps = null;
			Statement st = con.createStatement();
			////System.out.println("divin nonsale iss"+division);
                       ResultSet   rs=null;
                    if(mie.getTemp1().equalsIgnoreCase("all")){
   			        	rs= st.executeQuery("select * from nonsaleablemaster where current_date between '"+fromdate+"' and '"+todate+"' and final_status='Pending'");
                        //System.out.println("select * from nonsaleablemaster where current_date between '"+fromdate+"' and '"+todate+"' and final_status='Pending'"+"  nonsale_pendingggg");
                    }else{
   			        	rs= st.executeQuery("select * from nonsaleablemaster where current_date between '"+fromdate+"' and '"+todate+"'  and division='"+mie.getTemp1()+"' and final_status='Pending'");
                          //System.out.println("select * from nonsaleablemaster where current_date between '"+fromdate+"' and '"+todate+"'  and division='"+mie.getTemp1()+"' and final_status='Pending'"+"  nonsale_pendinggg22");
                    }

   			       
                       
                        /*String path = request.getServletContext().getRealPath("/");
                        File f = new File (path +"Non-Salable");
                        f.mkdir();*/

                        String xls = "Non-Salable.xls"; 
                       ////System.out.println("before excel");
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
                        rowhead.createCell((short) 0).setCellValue("DIVISION");
                        rowhead.createCell((short) 1).setCellValue("ENTRY DATE");
                        rowhead.createCell((short) 2).setCellValue("UNIT DETAILS");
                        rowhead.createCell((short) 3).setCellValue("FQC INWARD DATE");
                      //  rowhead.createCell((short) 4).setCellValue("REGION");
                        rowhead.createCell((short) 4).setCellValue("BRANCH");
                        rowhead.createCell((short) 5).setCellValue("ENGINEER");
                       // rowhead.createCell((short) 7).setCellValue("DEALER");
                        rowhead.createCell((short) 6).setCellValue("SUPPLIER");
                        rowhead.createCell((short) 7).setCellValue("MODEL");
                        rowhead.createCell((short) 8).setCellValue("MODEL S/N");
                        rowhead.createCell((short) 9).setCellValue("UNIT CONFIGURATION");
                        rowhead.createCell((short) 10).setCellValue("CUSTOMER NAME");
                        rowhead.createCell((short) 11).setCellValue("REPORTED PROBLEM");
                     //   rowhead.createCell((short) 14).setCellValue("REPLACED UNIT S/N");
                    //    rowhead.createCell((short) 15).setCellValue("REPLACEMENT SHIP DATE");
                        rowhead.createCell((short) 12).setCellValue("DEFECTIVE UNIT RECEIVED DATE");
                        rowhead.createCell((short) 13).setCellValue("FQC OBSERVATION");
                        rowhead.createCell((short) 14).setCellValue("SC INWARD DATE");
                        rowhead.createCell((short) 15).setCellValue("SC ENGINEER");
                        rowhead.createCell((short) 16).setCellValue("SC OBSERVATION");
                        rowhead.createCell((short) 17).setCellValue("REQUIRED PARTS");
                    //    rowhead.createCell((short) 22).setCellValue("ROOT CAUSE");
                        rowhead.createCell((short) 18).setCellValue("SC ACTION PLAN");
                        rowhead.createCell((short) 19).setCellValue("TENTATIVE DATE");
                    //    rowhead.createCell((short) 25).setCellValue("SHIP DATE TO FQC");
                    //    rowhead.createCell((short) 26).setCellValue("FQC FINAL REMARKS");
                        rowhead.createCell((short) 20).setCellValue("FINAL STATUS");
                        rowhead.createCell((short) 21).setCellValue("TIMESTAMP");
                        
			int index = 1;
                        while (rs.next()) {
				HSSFRow row = sheet.createRow((short) index);
				                row.createCell((short) 0).setCellValue(rs.getString(2));
                                row.createCell((short) 1).setCellValue(rs.getString(3));
                                row.createCell((short) 2).setCellValue(rs.getString(4));
                                row.createCell((short) 3).setCellValue(rs.getString(5));  
                              //  row.createCell((short) 4).setCellValue(rs.getString(6));
                                String branch=BranchDao.getbName(rs.getString(7));
                                row.createCell((short) 4).setCellValue(branch);
                                 String emname=EmployeeDao.geteName(rs.getString(8));
                                row.createCell((short) 5).setCellValue(emname);
                           //     String dealer=DealerDao.getdName(rs.getString(9));
                           //     row.createCell((short) 7).setCellValue(dealer);
                                row.createCell((short) 6).setCellValue(rs.getString(10));
                                String model=ModelDao.getModelname(rs.getString(11));
                                row.createCell((short) 7).setCellValue(model);
                                row.createCell((short) 8).setCellValue(rs.getString(12));
                                row.createCell((short) 9).setCellValue(rs.getString(13));
                                row.createCell((short) 10).setCellValue(rs.getString(14));
                                row.createCell((short) 11).setCellValue(rs.getString(15));
                             //   row.createCell((short) 14).setCellValue(rs.getString(16));
                             //    row.createCell((short) 15).setCellValue(rs.getString(17));
                                 row.createCell((short) 12).setCellValue(rs.getString(18));
                                 row.createCell((short) 13).setCellValue(rs.getString(19));
                                 row.createCell((short) 14).setCellValue(rs.getString(20));
                                String engg=EmployeeDao.getname(rs.getString(21));
                                 row.createCell((short) 15).setCellValue(engg);
                                 row.createCell((short) 16).setCellValue(rs.getString(22));
                                 row.createCell((short) 17).setCellValue(rs.getString(23));
                            //     row.createCell((short) 22).setCellValue(rs.getString(24));
                                 row.createCell((short) 18).setCellValue(rs.getString(25));
                                 row.createCell((short) 19).setCellValue(rs.getString(26));
                           //      row.createCell((short) 25).setCellValue(rs.getString(27));
                           //      row.createCell((short) 26).setCellValue(rs.getString(28));
                                 row.createCell((short) 20).setCellValue(rs.getString(29));
                                 row.createCell((short) 21).setCellValue(rs.getString(30));
                                    
				index++;
				////System.out.println(index);
                                
			}
                      //  //System.out.println("after excel non sale admin");
			 FileOutputStream fileOut = new FileOutputStream(xls);
			 wb.write(fileOut);
			 fileOut.close();
       //excel download code      
                        
                        String from="Schillerindiaservice";
                        String sub="Non-Salable Escalation('"+formattedString+"')";
                        //String to=mail1;
                       // String to= "dinesha123445@gmail.com";
                        String to= mie.getMail_id();
                        //System.out.println(to+"the to mail idd iss");
                       // String cc=mail2;  
                        //String cc="dinesha123445@gmail.com"; 
                        String cc= mie.getMail_id();  
                       
                        //System.out.println(cc+"the cc mail id issss");
                        String msgbody="Dear All, "
                          		+ "Greetings!"
                          		+ "Kindly find the attached Non-Salable Escalation Report for your reference and further proceedings";
                          
                        SendMailwithAttachment.sendEmailAttachment(from, to, sub, msgbody, "", cc, "Non-Salable.xls");
                      //  fileOut.close();
           			 
           			 
           			 
           			 
           			 
           			// fileOut.close();
                        
                        
                        
                   /*     
                     String outfile="Non-Salable.xls";
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
			con.close();*/
		} catch (ClassNotFoundException | SQLException | IOException e) {
                   
   		}
	    	}
		}
    
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
		//System.out.println("the list size iss"+list.size());
		return list;
	}
    
    
    
    
    
    
    
    
    
}
