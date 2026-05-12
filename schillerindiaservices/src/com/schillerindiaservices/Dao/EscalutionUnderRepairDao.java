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
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author ShineLoGics
 */
public class EscalutionUnderRepairDao {
     public static void UnderRepairEscalationExcel(String fromdate,String todate,String report_type) throws MessagingException {
         
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
			Connection con=DbConnection.getConnection();
			PreparedStatement ps = null;
			 ResultSet rs=null;
			 if(mie.getTemp1().equalsIgnoreCase("all")){
			
				 ps = con.prepareStatement("SELECT * FROM service_master WHERE ship_dt_frm_ser_cntr IS NOT NULL  AND (repaired_brd_stk_date IS NULL ) ");
                     //System.out.println("SELECT * FROM service_master WHERE ship_dt_frm_ser_cntr IS NOT NULL  AND (repaired_brd_stk_date IS NULL ) AND  currentDate between '"+fromdate+"' and '"+todate+"'"+" underrepair111");
				 rs = ps.executeQuery();
			 }else{
				 ps = con.prepareStatement("SELECT * FROM service_master WHERE ship_dt_frm_ser_cntr IS NOT NULL  AND (repaired_brd_stk_date IS NULL ) AND division_name='"+mie.getTemp1()+"'");
                //System.out.println("SELECT * FROM service_master WHERE ship_dt_frm_ser_cntr IS NOT NULL  AND (repaired_brd_stk_date IS NULL ) AND report_type='servicecenter' and currentDate between '"+fromdate+"' and '"+todate+"' and division_name='"+mie.getTemp1()+"'"+"  underrepairr222");
				 rs = ps.executeQuery();
			 }
//                        String xls = request.getServletContext().getRealPath("/escalution")+"/pendingFRNEscalution.xls";  

                       /* String path = request.getServletContext().getRealPath("/");
                        File f = new File (path +"escalution");
                        f.mkdir();*/

                        String xls = "UnderRepairEscalution.xls"; 
                 

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
//			rowhead.createCell((short) 0).setCellValue("Ser_ID");
                        rowhead.createCell((short) 0).setCellValue("DIVISION_NAME");
                        rowhead.createCell((short) 1).setCellValue("ENTRY DATE");
                        rowhead.createCell((short) 2).setCellValue("SC_REF_NO");
                        rowhead.createCell((short) 3).setCellValue("SC_ENGINEER");
                        rowhead.createCell((short) 4).setCellValue("RA_ENGINEER");
                        rowhead.createCell((short) 5).setCellValue("FRN_NO");
                       rowhead.createCell((short) 6).setCellValue("FRN_DATE");
                     //  rowhead.createCell((short) 8).setCellValue("DOCKET_NO");
                    //    rowhead.createCell((short) 9).setCellValue("DESPATCH_DATE");
                      //  rowhead.createCell((short) 10).setCellValue("SER_COMM_INWARD_DATE");
                        rowhead.createCell((short) 7).setCellValue("SER_CENTRE_RECEIVED_DATE");
                        rowhead.createCell((short) 8).setCellValue("STK_CUST");
                        rowhead.createCell((short) 9).setCellValue("REGION");
                        rowhead.createCell((short) 10).setCellValue("BRANCH");
                      //  rowhead.createCell((short) 15).setCellValue("ENGINEER_ID");
                        rowhead.createCell((short) 11).setCellValue("DEALER_NAME");
                        rowhead.createCell((short) 12).setCellValue("CUST_NAME");
                        rowhead.createCell((short) 13).setCellValue("SUPPLIER_NAME");
                        rowhead.createCell((short) 14).setCellValue("PRODUCT_MODEL");
                        rowhead.createCell((short) 15).setCellValue("UNIT_SLNO");
                     //   rowhead.createCell((short) 21).setCellValue("DOD");
                        rowhead.createCell((short) 16).setCellValue("UNIT_STATUS");
                        rowhead.createCell((short) 17).setCellValue("MOD_BRD_NAME");
                        rowhead.createCell((short) 18).setCellValue("DEF_MOD_BRD_NAME");
                        rowhead.createCell((short) 19).setCellValue("DEF_TYPE");
                        rowhead.createCell((short) 20).setCellValue("TYPE_OF_ACC");
                    //    rowhead.createCell((short) 27).setCellValue("DEF_PART_SN");
                        rowhead.createCell((short) 21).setCellValue("DEF_GIR_NO");
                     //   rowhead.createCell((short) 29).setCellValue("REP_TYPE");
                   //     rowhead.createCell((short) 30).setCellValue("REP_GIR_NO");
                        rowhead.createCell((short) 22).setCellValue("DEF_UNIT_GIR_NO");
                        rowhead.createCell((short) 23).setCellValue("FIELD_REMARKS");
                        rowhead.createCell((short) 24).setCellValue("TECHNICAL_REMARKS");
                      //  rowhead.createCell((short) 34).setCellValue("COMPONENTS_USEDFOR_REPAIR");
                     //   rowhead.createCell((short) 35).setCellValue("REPAIRED_BRD_STK_DATE");
                        rowhead.createCell((short) 25).setCellValue("FINAL_REMARKS");
                     //   rowhead.createCell((short) 37).setCellValue("TYPE_OF_WORK");
                     //   rowhead.createCell((short) 38).setCellValue("SHIP_DT_FRM_SER_CNTR");
                    //    rowhead.createCell((short) 39).setCellValue("DISP_ADV_NO");
                    //    rowhead.createCell((short) 40).setCellValue("SHIP_DATE_FROM_COMMERCIAL");
                  //      rowhead.createCell((short) 41).setCellValue("DC_NO");
                  //      rowhead.createCell((short) 42).setCellValue("COMRCL_DTL_INV_RMRK");
                  //      rowhead.createCell((short) 43).setCellValue("Division Name");
                        rowhead.createCell((short) 26).setCellValue("PENDING DAYS");
                        rowhead.createCell((short) 27).setCellValue("TIMESTAMP");

			int index = 1;
                        while (rs.next()) {
                            
                            String date_from_serCenter=rs.getString(12);
                                
                                 String startDateString = date_from_serCenter;
                                        DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
                                        Date startDate;
                                            startDate = df.parse(startDateString);
                                            String newDateString = df.format(startDate);
                                             
                                   Date currentdate;
                                   Date dateobj = new Date();
                                   String  current_date=df.format(dateobj);
                                   currentdate=df.parse(current_date);
                                  
                               SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                                            Date d1 = null;
                                            Date d2 = null;

                                                    d1 = startDate;
                                                    d2 = currentdate;

                                                    long diff = d2.getTime() - d1.getTime();

                                                    long diffDays = diff / (24 * 60 * 60 * 1000);
                                /* if(diffDays>=7)   
                              {    */                
				HSSFRow row = sheet.createRow((short) index);
//				                 row.createCell((short) 0).setCellValue(rs.getString(1));
				                row.createCell((short) 0).setCellValue(rs.getString(44));
                                row.createCell((short) 1).setCellValue(rs.getString(3));
                                row.createCell((short) 2).setCellValue(rs.getString(4));   
                                    String sc_engneer=EmployeeDao.geteName(rs.getString(5)); 
                                row.createCell((short) 3).setCellValue(sc_engneer);
                               String ra_engneer=EmployeeDao.geteName(rs.getString(6));
                                row.createCell((short) 4).setCellValue(ra_engneer);
                                row.createCell((short) 5).setCellValue(rs.getString(7));
                                row.createCell((short) 6).setCellValue(rs.getString(8));
                           //     row.createCell((short) 8).setCellValue(rs.getString(9));
                         //      row.createCell((short) 9).setCellValue(rs.getString(10));
                          //      row.createCell((short) 10).setCellValue(rs.getString(11));
                                row.createCell((short) 7).setCellValue(rs.getString(12));
                                    String stk_cust=DropdownDao.getddName(rs.getString(13)); 
                                row.createCell((short) 8).setCellValue(stk_cust);
                                row.createCell((short) 9).setCellValue(rs.getString(14));
                                    String branchname=BranchDao.getbName(rs.getString(15));     
                                row.createCell((short) 10).setCellValue(branchname);
                              //     String enggname=EmployeeDao.getname(rs.getString(16));
                             //   row.createCell((short) 15).setCellValue(enggname);
                                    String dealer_name=DealerDao.getdName(rs.getString(17));
                                row.createCell((short) 11).setCellValue(dealer_name);
                                row.createCell((short) 12).setCellValue(rs.getString(18));
                                    row.createCell((short) 13).setCellValue(rs.getString(19));
                               String model=ModelDao.getModelname(rs.getString(20));
                               row.createCell((short) 14).setCellValue(model);
                                row.createCell((short) 15).setCellValue(rs.getString(21));
                             //   row.createCell((short) 21).setCellValue(rs.getString(22));
                                    String unit_status=DropdownDao.getddName(rs.getString(23));
                                row.createCell((short) 16).setCellValue(unit_status);
                                row.createCell((short) 17).setCellValue(rs.getString(24));
                                row.createCell((short) 18).setCellValue(rs.getString(25));
                                    String def_type=DropdownDao.getddName(rs.getString(26));
                                row.createCell((short) 19).setCellValue(def_type);
                                    String type_of_acc=DropdownDao.getddName(rs.getString(27));
                                row.createCell((short) 20).setCellValue(type_of_acc);
                             //   row.createCell((short) 27).setCellValue(rs.getString(28));
                                row.createCell((short) 21).setCellValue(rs.getString(29));
                                    String rep_type=DropdownDao.getddName(rs.getString(30));
                              //  row.createCell((short) 29).setCellValue(rep_type);
                            //    row.createCell((short) 30).setCellValue(rs.getString(31));
                                row.createCell((short) 22).setCellValue(rs.getString(32));
                                row.createCell((short) 23).setCellValue(rs.getString(33));
                                row.createCell((short) 24).setCellValue(rs.getString(34));
                           //    row.createCell((short) 34).setCellValue(rs.getString(35));
                           //     row.createCell((short) 35).setCellValue(rs.getString(36));
                                row.createCell((short) 25).setCellValue(rs.getString(37));
                             //      String type_of_work=DropdownDao.getddName(rs.getString(38));
                             //   row.createCell((short) 37).setCellValue(type_of_work);
                            //    row.createCell((short) 38).setCellValue(rs.getString(39));
                            //    row.createCell((short) 39).setCellValue(rs.getString(40));
                            //    row.createCell((short) 40).setCellValue(rs.getString(41));
                            //    row.createCell((short) 41).setCellValue(rs.getString(42));
                           //     row.createCell((short) 42).setCellValue(rs.getString(43));
                               
                                row.createCell((short) 26).setCellValue(diffDays);
                                row.createCell((short) 27).setCellValue(rs.getString(53));
                                
				
                             // }   
                                 index++;
			}
			FileOutputStream fileOut = new FileOutputStream("UnderRepairEscalution.xls");
			wb.write(fileOut);
                        
                       /* HttpSession session=request.getSession();
                        String mail1=(String) session.getAttribute("toadd");
                        String mail2=(String) session.getAttribute("mailtwo");
                        String msg=(String) session.getAttribute("msgvalue");
                        String sender=(String) session.getAttribute("sender");
                        String pass=(String) session.getAttribute("password");*/
			  String from="Schillerindiaservice";
              String sub="Under_Repair Escalation('"+formattedString+"')"; 
              //String to=mail1;
             // String to="harini.keerthana95@gmail.com";  
              String to=mie.getMail_id();  
              //System.out.println(to+"the to mail idd iss");
             // String cc=mail2;  
              //String cc="dinesha123445@gmail.com";  
              String cc=mie.getMail_id();  
              //System.out.println(cc+"the cc mail id issss");
              String msgbody="Dear All! \n"
              		+ "Greetings!\n"
              		+ " Kindly find the attached Under_Repair Escalation Report for your reference and further proceedings\n";
                
              SendMailwithAttachment.sendEmailAttachment(from, to, sub, msgbody, "", cc, "UnderRepairEscalution.xls");
              fileOut.close();
			rs.close();
			con.close();
		} catch (ClassNotFoundException | SQLException | ParseException | IOException e) {
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
