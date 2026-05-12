/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Pendingactmaster;
import com.schillerindiaservices.bean.Repair_master;
import com.schillerindiaservices.bean.service_master;
import com.schillerindiaservices.common.UtilFunctions;
import com.schillerindiaservices.connection.DbConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
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
import org.apache.poi.sl.usermodel.TextParagraph.TextAlign;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author ShineLoGics
 */
public class RepairActivityDao {
    public int Insert_repair(Repair_master pam) throws ClassNotFoundException, SQLException
    {
        int id=0;
        Connection con=null;
        UtilFunctions utilfn = new UtilFunctions();
        con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("INSERT INTO repair_master(entry_date,division,"
                + "sc_ref_no,def_gir_no,category,model,def_brd_mod_name,unit_status,tech_remarks,comp_used_to_repair,finished_date,repaired_by,final_remarks,status,no_of_days,ser_id)\n" +
"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
       
        ps.setDate(1, utilfn.getDbDateFormat(pam.getEntry_date()));
        ps.setString(2, pam.getDivision());
        ps.setString(3, pam.getSc_ref_no());
        ps.setString(4, pam.getDef_gir_no());
        ps.setString(5, pam.getCategory());
        ps.setString(6, pam.getModel());
        ps.setString(7, pam.getDef_brd_mod_name());
        ps.setString(8, pam.getUnit_status());
        ps.setString(9, pam.getTech_remarks());
        ps.setString(10, pam.getComp_used_to_repair());
        ps.setDate(11, utilfn.getDbDateFormat(pam.getFinished_date()));
        ps.setString(12, pam.getRepaired_by());
        ps.setString(13, pam.getFinal_remarks());
        ps.setString(14, pam.getStatusType());
        ps.setInt(15, pam.getNo_of_days());
        ps.setString(16, pam.getSer_id());
        
        id=ps.executeUpdate();
        con.close();
        return id;
    }
    public static Repair_master getById(int id) throws ClassNotFoundException, SQLException
    {
    	Repair_master  pm=null;
        Connection con=null;
        UtilFunctions utilfn = new UtilFunctions();
        con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT * from repair_master WHERE id='"+id+"'");
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            pm=new Repair_master();
            pm.setId(id);
            pm.setDivision(rs.getString("division"));
            pm.setEntry_date(rs.getString("entry_date"));
            pm.setSc_ref_no(rs.getString("sc_ref_no"));
            pm.setDef_gir_no(rs.getString("def_gir_no"));
            pm.setModel(rs.getString("model"));
            pm.setCategory(rs.getString("category"));
            pm.setDef_brd_mod_name(rs.getString("def_brd_mod_name"));
            pm.setUnit_status(rs.getString("unit_status"));
            pm.setTech_remarks(utilfn.getUITextFormat(rs.getString("tech_remarks")));
            pm.setComp_used_to_repair(utilfn.getUITextFormat(rs.getString("comp_used_to_repair")));
            pm.setFinished_date(rs.getString("finished_date"));
            pm.setRepaired_by(rs.getString("repaired_by"));
            pm.setFinal_remarks(utilfn.getUITextFormat(rs.getString("final_remarks")));
            pm.setStatusType(rs.getString("status"));
            pm.setSer_id(rs.getString("ser_id"));
            pm.setNo_of_days(rs.getInt("no_of_days"));
            
        }
        con.close();
        return pm;
        
    }
    
    public int update( Repair_master nsm) throws ClassNotFoundException, SQLException
    {
        Connection con=DbConnection.getConnection();
       UtilFunctions utilfn = new UtilFunctions();
       
        int id=0;
        System.out.println("update complete , no of days---->"+nsm.getNo_of_days());
        PreparedStatement ps=con.prepareStatement("UPDATE repair_master SET repaired_by=?,"
                + "comp_used_to_repair=?,finished_date=?,tech_remarks=?,final_remarks=?,status=?,"
                + "no_of_days=? WHERE id=?");
               
               ps.setString(1, nsm.getRepaired_by());
               ps.setString(2, nsm.getComp_used_to_repair());
               ps.setDate(3, utilfn.getDbDateFormat(nsm.getFinished_date()));
               ps.setString(4, nsm.getTech_remarks());
               ps.setString(5, nsm.getFinal_remarks());
               ps.setString(6, nsm.getStatusType());
               ps.setInt(7, nsm.getNo_of_days());
               ps.setInt(8, nsm.getId());
             
        
        
        
        
      id =   ps.executeUpdate();
        con.close();
        System.out.println("updated db");
        return id;
    }
    public static String PendingActDivName(int d) throws ClassNotFoundException, SQLException
    {
        String div="";
        Connection con=null;
        con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT division FROM pendingactmaster WHERE sc_engg='"+d+"'");
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            div=rs.getString("division");
        }
        con.close();
        return div;
    }
    public void delete(int id) throws ClassNotFoundException, SQLException
    {
        Connection con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("DELETE FROM pendingactmaster WHERE id='"+id+"'");
        ps.executeUpdate();
        con.close();
    }
    
    public void update_repairStatus(service_master pam) throws ClassNotFoundException, SQLException
    {
//        Connection con=DbConnection.getConnection();
//        PreparedStatement ps=con.prepareStatement("update service_master set repair_status=?,components_usedfor_repair=? where ser_id=?");
//        
//        ps.setString(1, pam.getRepair_status());
//        ps.setString(2, pam.getComponentsUsedforRepair());
//        ps.setInt(3, pam.getSerId());
//        ps.executeUpdate();
//        con.close();

        Connection con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("update service_master set repair_status=?,components_usedfor_repair=?,report_type=? where ser_id=?");
        
        ps.setString(1, pam.getRepair_status());
        ps.setString(2, pam.getComponentsUsedforRepair());
        ps.setString(3, pam.getReport_type());
        ps.setInt(4, pam.getSerId());
        ps.executeUpdate();
        con.close();
    }
    public void updaterepairStatus2(service_master pam) throws ClassNotFoundException, SQLException
    {
        Connection con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("update service_master set repair_status='"+pam.getRepair_status()+"'  where ser_id='"+pam.getSerId()+"'");
        ps.executeUpdate();
        con.close();
    }
    
    
 public static void RepairExcelReport(String from,String to,String status,String cat, HttpServletRequest request,HttpServletResponse response) {
		
    	
    	try {
                    int nam=0;
                    UtilFunctions utilfn = new UtilFunctions();
               HttpSession session = request.getSession();
            //   nam= (int) session.getAttribute("loguserid");
            //   String role=(String)session.getAttribute("logrole");
//               String lid=String.valueOf(nam);
           //    String division=PendingActivityDao.PendingActDivName(nam);
               
			Connection con=DbConnection.getConnection();
			PreparedStatement ps = null;
			Statement st = con.createStatement();
                        ResultSet rs=null;
                      System.out.println("before execute"+status);
                      
                      if(cat == null || ("").equals(cat) ) {
                          ps = con.prepareStatement("SELECT * FROM repair_master WHERE status='"+status+"' AND entry_date between '"+from+"' and '"+to+"'");

                      }else if(cat.equalsIgnoreCase("ALL_Closed")){
                          ps = con.prepareStatement("SELECT * FROM repair_master WHERE status='"+status+"' AND entry_date between '"+from+"' and '"+to+"'");
                          System.out.println("SELECT * FROM repair_master WHERE status='"+status+"' AND entry_date between '"+from+"' and '"+to+"'");
                      }else {
                    	  System.out.println("third category");
                          ps = con.prepareStatement("SELECT * FROM repair_master WHERE status='"+status+"'AND category ='"+cat+"'  AND entry_date between '"+from+"' and '"+to+"'");

                      }
                   //      rs = st.executeQuery("SELECT * FROM repair_master WHERE status='completed' AND entry_date between '"+from+"' and '"+to+"'");
                   //     rs = st.executeQuery();
                      
                        rs = ps.executeQuery();
                   //   System.out.println(rs.next());  
 
                        String path = request.getServletContext().getRealPath("/");
                        File f = new File (path +"RepairActivity");
                        f.mkdir();

                        String xls = request.getServletContext().getRealPath("/RepairActivity")+"/RepairActivity_"+cat+".xls"; 
                // System.out.println("the xls path:   "+xls);
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
			
		//	   sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 4));
			
			             rowhead.createCell((short) 0).setCellValue("ENTRY DATE");
                         rowhead.createCell((short) 1).setCellValue("DIVISION");
                       
                        rowhead.createCell((short) 2).setCellValue("SC_REF_NO");
                        rowhead.createCell((short) 3).setCellValue("DEF_GIR_NO");
                        rowhead.createCell((short) 4).setCellValue("CATEGORY");
                        rowhead.createCell((short) 5).setCellValue("MODEL");
                        rowhead.createCell((short) 6).setCellValue("DEF_BRD/MOD_NAME");
                        rowhead.createCell((short) 7).setCellValue("TECH REMARKS");
                        rowhead.createCell((short) 8).setCellValue("COMP_USED_TO_REPAIR");
                        rowhead.createCell((short) 9).setCellValue("FINESHED_DATE");
                        rowhead.createCell((short) 10).setCellValue("REPAIRED BY");
                         rowhead.createCell((short) 11).setCellValue("FINAL REMARKS");
                        rowhead.createCell((short) 12).setCellValue("STATUS");
                        rowhead.createCell((short) 13).setCellValue("NO_OF_DAYS");
                     //   rowhead.createCell((short) 11).setCellValue("STATUS");
                       
			int index = 1;
			int id = 0;
		//	System.out.println("before loop"+rs.getString("entry_date")+"   "+rs.getString("id")+"   "+rs.next());
		//	if(rs.next()) {   
			//	System.out.println("its true");
		//	}
                        while (rs.next()) {
                        	System.out.println("inside loop    "+ ++id);
				HSSFRow row = sheet.createRow((short) index);
				System.out.println("id -->    "+rs.getInt("id"));
				System.out.println("before date   "+rs.getString(12));
				
				  System.out.println("entry date"+rs.getString(1));
				                row.createCell((short) 0).setCellValue(rs.getString(2));
                                row.createCell((short) 1).setCellValue(rs.getString(3));
				             
                               System.out.println("DEF_GIR_NO   "+rs.getString(5));
                                row.createCell((short) 2).setCellValue(rs.getString(4));
                                row.createCell((short) 3).setCellValue(rs.getString(5));
                                row.createCell((short) 4).setCellValue(rs.getString(6));//cat
                                row.createCell((short) 5).setCellValue(rs.getString(7));
                                row.createCell((short) 6).setCellValue(rs.getString(8));//def
                                row.createCell((short) 7).setCellValue(rs.getString(10));
                                row.createCell((short) 8).setCellValue(rs.getString(11));
                                row.createCell((short) 9).setCellValue(rs.getString(12));
                                row.createCell((short) 10).setCellValue(rs.getString(13));
                                row.createCell((short) 11).setCellValue(rs.getString(14));
                                System.out.println("no of days---"+rs.getInt(16));
                                row.createCell((short) 12).setCellValue(rs.getString(15));
                                row.createCell((short) 13).setCellValue(rs.getInt(16));

				index++;
                                
			}
                        
                        int colindex=rowhead.getLastCellNum();
                        for(int i=0;i<colindex;i++) {
                        	sheet.autoSizeColumn(i);
                        }

                        
                        System.out.println("after excel");
			 FileOutputStream fileOut = new FileOutputStream(xls);
			 wb.write(fileOut);
			 fileOut.close();
       //excel download code                 
                     String outfile="RepairActivity_"+cat+".xls";
                     response.setHeader("Content-disposition","attachment; filename="+outfile);          
                     FileInputStream in = new FileInputStream(xls);
                     OutputStream out = response.getOutputStream();

                     byte[] buffer= new byte[8192]; // use bigger if you want
                     int length = 0;

                     while ((length = in.read(buffer)) > 0){
                         out.write(buffer, 0, length);
                     }
                     System.out.println("excel creation");
                     in.close();
                     out.close();
                   //  wb.close();
			rs.close();
			con.close();
		} catch (ClassNotFoundException | SQLException | IOException e) {
                   
   		}
    
}
 
 public static void PerformanceExcelReport(String from,String to,String status, HttpServletRequest request,HttpServletResponse response) {
		
 	
 	try {
                 int nam=0;
                 int rowcount1 = 0;
                 UtilFunctions utilfn = new UtilFunctions();
            HttpSession session = request.getSession();
         //   nam= (int) session.getAttribute("loguserid");
         //   String role=(String)session.getAttribute("logrole");
//            String lid=String.valueOf(nam);
        //    String division=PendingActivityDao.PendingActDivName(nam);
            
			Connection con=DbConnection.getConnection();
			PreparedStatement ps = null;
			Statement st = con.createStatement();
                     ResultSet rs=null;
                   status = "completed";
                //      rs = st.executeQuery("SELECT * FROM repair_master WHERE status='completed' AND entry_date between '"+from+"' and '"+to+"'");
                     ps = con.prepareStatement("SELECT * FROM repair_master WHERE status='"+status+"' AND entry_date between '"+from+"' and '"+to+"'");
                //     rs = st.executeQuery();
                    
                     rs = ps.executeQuery();
                   System.out.println(rs.first());  

                     String path = request.getServletContext().getRealPath("/");
                     File f = new File (path +"RepairActivity");
                     f.mkdir();

                     String xls = request.getServletContext().getRealPath("/RepairActivity")+"/RepairActivity.xls"; 
              System.out.println("the xls path:   "+xls);
			Workbook wb = new HSSFWorkbook();
			CreationHelper createHelper = wb.getCreationHelper();
			Sheet sheet = wb.createSheet("Excel Sheet");
			sheet.setDefaultColumnWidth(20);
			sheet.autoSizeColumn(1);
			Row rowhead = sheet.createRow((short) 1);
			     //    rowhead.createCell((short) 0).setCellValue("ENTRY DATE");
                     rowhead.createCell((short) 1).setCellValue("DIVISION");
                    
                     rowhead.createCell((short) 2).setCellValue("ASHOK");
                     rowhead.createCell((short) 3).setCellValue("DEF_GIR_NO");
                     rowhead.createCell((short) 4).setCellValue("CATEGORY");
                     rowhead.createCell((short) 5).setCellValue("VASANTH");
                     rowhead.createCell((short) 6).setCellValue("DEF_BRD/MOD_NAME");
                     rowhead.createCell((short) 7).setCellValue("TECH REMARKS");
                     rowhead.createCell((short) 8).setCellValue("RAMA");
                     rowhead.createCell((short) 9).setCellValue("FINESHED_DATE");
                     rowhead.createCell((short) 10).setCellValue("REPAIRED BY");
                      rowhead.createCell((short) 11).setCellValue("FINAL REMARKS");
                     rowhead.createCell((short) 12).setCellValue("STATUS");
                     rowhead.createCell((short) 13).setCellValue("NO_OF_DAYS");
                     
                    sheet.addMergedRegion(new CellRangeAddress(1, 2, 2, 4));
                    sheet.addMergedRegion(new CellRangeAddress(1, 2, 5, 7));
                    sheet.addMergedRegion(new CellRangeAddress(1, 2, 8, 10));
                    
                    sheet.addMergedRegion(new CellRangeAddress(3, 4, 2, 2));
                    sheet.addMergedRegion(new CellRangeAddress(3, 4, 3, 3));
                    sheet.addMergedRegion(new CellRangeAddress(3, 4, 4, 4));
                    sheet.addMergedRegion(new CellRangeAddress(3, 4, 5, 5));
                    sheet.addMergedRegion(new CellRangeAddress(3, 4, 6, 6));
                    sheet.addMergedRegion(new CellRangeAddress(3, 4, 7, 7));
                    sheet.addMergedRegion(new CellRangeAddress(3, 4, 8, 8));
                    sheet.addMergedRegion(new CellRangeAddress(3, 4, 9, 9));
                    sheet.addMergedRegion(new CellRangeAddress(3, 4, 10, 10));
                    sheet.addMergedRegion(new CellRangeAddress(4, 4, 11, 13));
                    sheet.addMergedRegion(new CellRangeAddress(4, 4, 14, 16));
                    
                    Row rowhead2 = sheet.createRow((short) 3);
                    rowhead2.createCell((short) 2).setCellValue("TOTAL I/W");
                    rowhead2.createCell((short) 3).setCellValue("EXE within tar");
                    rowhead2.createCell((short) 4).setCellValue("%of Executions");
                    rowhead2.createCell((short) 5).setCellValue("TOTAL I/W");
                    rowhead2.createCell((short) 6).setCellValue("EXE within tar");
                    rowhead2.createCell((short) 7).setCellValue("%of Executions");
                    rowhead2.createCell((short) 8).setCellValue("TOTAL I/W");
                    rowhead2.createCell((short) 9).setCellValue("EXE within tar");
                    rowhead2.createCell((short) 10).setCellValue("%of Executions");
                   rowhead2.createCell((short) 11).setCellValue("TOTAL I/W");
                   rowhead2.createCell((short) 12).setCellValue("Overall Exe");
                   rowhead2.createCell((short) 13).setCellValue("% of Overall Executions");
                   rowhead2.createCell((short) 14).setCellValue("TOTAL I/W");
                   rowhead2.createCell((short) 15).setCellValue("Overall Exe");
                   rowhead2.createCell((short) 16).setCellValue("% of Overall Executions");
                   
                   
                   Row rowhead4 = sheet.createRow((short) 4);
                
                   rowhead4.createCell((short) 12).setCellValue("This Month");
                   rowhead4.createCell((short) 15).setCellValue("Prev Month");
                 //  rowhead4.createCell((short) 16).setCellValue("% of Overall Executions");
                    Row rowhead3 = sheet.createRow((short) 6);
                    //  sheet.setColumnWidth(1, 20);
                    CellStyle cellstyel = wb.createCellStyle();
                    cellstyel.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
                    cellstyel.setBorderTop(BorderStyle.DASH_DOT);
                    rowhead3.createCell((short) 1).setCellValue("IW/CAMC/Stock via FRN");
                    Row rownum7 = sheet.createRow((short) 7);
                    Cell row7cell1 = rownum7.createCell(1);
                    row7cell1.setCellValue("IW/CAMC/STOCK - PCB, Sub units, Units & Spares\r\n"
                    		+ "");
                    row7cell1.setCellStyle(cellstyel);
                    
                   
                    Row rownum8 = sheet.createRow((short) 8);
                    rownum8.createCell((short) 1).setCellValue("IW/CAMC/Stock via FRN");
                    Row rownum9 = sheet.createRow((short) 9);
                    rownum9.createCell((short) 1).setCellValue("IW/CAMC/Stock via FRN");
                    Row rownum10 = sheet.createRow((short) 10);
                    rownum10.createCell((short) 1).setCellValue("IW/CAMC/Stock via FRN");
                    Row rownum11 = sheet.createRow((short) 11);
                    rownum11.createCell((short) 1).setCellValue("IW/CAMC/Stock via FRN");
                    Row rownum12 = sheet.createRow((short) 12);
                    rownum12.createCell((short) 1).setCellValue("IW/CAMC/Stock via FRN");
                  //   rowhead.createCell((short) 11).setCellValue("STATUS");
                     System.out.println("fetch size   "+rs.getFetchSize());
			int index = 9;
			System.out.println("before loop");
                     while (rs.next()) {
                     	System.out.println("inside loop");
				Row row = sheet.createRow((short) index);
				System.out.println("before date   ");
				System.out.println("before date   "+rs.getString("entry_date"));
				
				  System.out.println("entry date"+rs.getString(1));
				                row.createCell((short) 0).setCellValue(rs.getString(2));
                             row.createCell((short) 1).setCellValue(rs.getString(3));
				             
                            System.out.println("DEF_GIR_NO   "+rs.getString(4));
                             row.createCell((short) 2).setCellValue(rs.getString(4));
                             row.createCell((short) 3).setCellValue(rs.getString(5));
                             row.createCell((short) 4).setCellValue(rs.getString(7));
                             row.createCell((short) 5).setCellValue(rs.getString(8));
                             row.createCell((short) 6).setCellValue(rs.getString(10));
                             row.createCell((short) 7).setCellValue(rs.getString(11));
                             row.createCell((short) 8).setCellValue(rs.getString(12));
                             row.createCell((short) 9).setCellValue(rs.getString(13));
                             row.createCell((short) 10).setCellValue(rs.getString(14));
                             row.createCell((short) 11).setCellValue(rs.getString(15));
                             System.out.println("no of days---"+rs.getInt(16));
                             row.createCell((short) 12).setCellValue(rs.getInt(16));

				index++;
                             
			}
                     
                     System.out.println("after excel");
			 FileOutputStream fileOut = new FileOutputStream(xls);
			 wb.write(fileOut);
			 fileOut.close();
    //excel download code                 
                  String outfile="RepairActivity.xls";
                  response.setHeader("Content-disposition","attachment; filename="+outfile);          
                  FileInputStream in = new FileInputStream(xls);
                  OutputStream out = response.getOutputStream();

                  byte[] buffer= new byte[8192]; // use bigger if you want
                  int length = 0;

                  while ((length = in.read(buffer)) > 0){
                      out.write(buffer, 0, length);
                  }
                  System.out.println("excel creation");
                  in.close();
                  out.close();
                //  wb.close();
			rs.close();
			con.close();
		} catch (ClassNotFoundException | SQLException | IOException e) {
                
		}
 
}
    
    
}
