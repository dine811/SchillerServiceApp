/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.DropdownDao;
import com.schillerindiaservices.Dao.EmployeeDao;
import com.schillerindiaservices.Dao.ModelDao;
import com.schillerindiaservices.Dao.ScarpListDao;
import com.schillerindiaservices.connection.DbConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author SHINELOGICS
 */

public class ScarpListEnggController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ScarpListEnggController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ScarpListEnggController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//      processRequest(request, response);
    	System.out.println("insidee scra0plist controllerrr");
        String divsionname=request.getParameter("div");
        System.out.println(divsionname+"division name issss");
try
   {
     
       PrintWriter out = response.getWriter();
       String[] cols = { "sc_ref_no","status","ser_id", "sc_engnr","frn_no","cust_name","product_model","unit_status","def_mod_brd_name","def_gir_no","type_of_work","ser_centre_received_date","ship_dt_frm_ser_cntr","repaired_brd_stk_date"};
       String table = "service_master";
       DbConnection dbo = new DbConnection();
       JSONObject result = new JSONObject();
       JSONArray array = new JSONArray();
       int amount = 10;
       int start = 0;
       int echo = 0;
       int col = 0;
       
       String engine = "";
       String browser = "";
       String platform = "";
       String version = "";
       String grade = "";
       String dir = "asc";
       String sStart = request.getParameter("start");
       String sAmount = request.getParameter("length");
       String sEcho = request.getParameter("sEcho");
       String sCol = request.getParameter("order[0][column]");
       String sdir = request.getParameter("order[0][dir]");
       
        String individualSearch = "";
       if (sStart != null)
       {
           start = Integer.parseInt(sStart);
           if (start < 0) {
               start = 0;
           }
       }
       if (sAmount != null)
           amount = Integer.parseInt(sAmount);
       if ((amount < 10) || (amount > 100)) {
           amount = 10;
       }
       
       if (sEcho != null) {
           echo = Integer.parseInt(sEcho);
       }
       if (sCol != null)
       {
           col = Integer.parseInt(sCol);
           if ((col < 0) || (col > 6)) {
               col = 0;
           }
       }
       if ((sdir != null) &&
               (!sdir.equals("desc"))) {
           dir = "asc";
       }
       String colName = cols[col];
       int total = 0;
       Connection conn = DbConnection.getConnection();
       try
       {
          
          
           int nam=0;
           HttpSession session = request.getSession();
           nam= (int) session.getAttribute("loguserid");
           String one=EmployeeDao.getEmp(nam);
           int done=Integer.parseInt(one);
           String divName= EmployeeDao.getdivEmpName(done);
           
           
           String role=(String)session.getAttribute("logrole");
           
          if("admin".equalsIgnoreCase(role))
          {
           String sql = "SELECT count(*) FROM " + table;
         
           PreparedStatement ps = conn.prepareStatement(sql);
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               total = rs.getInt("count(*)");
           }
          }
          else
          {
               {
           String sql = "SELECT count(*) FROM service_master WHERE division='"+divName+"' AND type_of_work=23";
          
           PreparedStatement ps = conn.prepareStatement(sql);
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               total = rs.getInt("count(*)");
           }
          }
          }
           int totalAfterFilter = total;
           try
           {
               
             
               
               String searchSQL = "";
//               String sql1 = "SELECT * FROM " + table;
               String sql1;
               
               
                   if("admin".equalsIgnoreCase(role))
                      {
                        sql1 = "SELECT * FROM " + table;
                      }  
                   else
                      {
                          sql1 = "SELECT * FROM service_master WHERE division='"+divName+"'AND type_of_work=23";
                      }
                  
              String unit_status="";
              String type_of_work="";
              String product_model="";
              String sc_engnr="";
              String engineer="";
      
               String searchTerm = request.getParameter("search[value]");
               
               if("admin".equalsIgnoreCase(role))
                      {
                       String globeSearch = " where (def_gir_no like '%" + searchTerm + "%'" +" or sc_ref_no like '%" + searchTerm + "%'" +" or frn_no like '%" + searchTerm + "%')" + "" ;
                          if ((searchTerm != "") && (individualSearch != "")) {
                              searchSQL = globeSearch + " and " + individualSearch;
                          } else if (individualSearch != "") {
                              searchSQL = " and " + individualSearch;
                          } else if (searchTerm != "") {
                              searchSQL = globeSearch;
                          }
                           sql1 = sql1 + searchSQL;
      sql1 = sql1 + " order by " + colName + " " + dir;
      sql1 = sql1 + " limit " + start + ", " + amount;
      System.out.println(sql1+"this is sql");
      PreparedStatement ps1 = conn.prepareStatement(sql1);
      ResultSet rs1 = ps1.executeQuery();
      while (rs1.next())
      {
          JSONArray ja = new JSONArray();
          ja.put(rs1.getString("ser_id"));
          System.out.println(rs1.getString("status")+" the statusss issss");
          ja.put(rs1.getString("status"));
          ja.put(rs1.getString("entry_date"));
          ja.put(rs1.getString("sc_ref_no"));
          sc_engnr=EmployeeDao.geteName(rs1.getString("sc_engnr"));
          ja.put(sc_engnr);
          ja.put(rs1.getString("frn_no"));
          ja.put(rs1.getString("region"));
          engineer=EmployeeDao.geteName(rs1.getString("engineer_id"));
          ja.put(engineer);
          ja.put(rs1.getString("cust_name"));
//          product_model = ModelDao.getpName(rs1.getString("product_model"));
//          ja.put(product_model);
              String model=ModelDao.getModelname(rs1.getString("product_model"));
          ja.put(model);
          unit_status=DropdownDao.getddName(rs1.getString("unit_status"));
          ja.put(unit_status);
          ja.put(rs1.getString("def_mod_brd_name"));
          ja.put(rs1.getString("def_gir_no"));
          type_of_work=DropdownDao.getddName(rs1.getString("type_of_work"));
          ja.put(type_of_work);
           //pending days
//           String ship_dt_frm_sc=rs1.getString("ship_dt_frm_ser_cntr");
//           if(ship_dt_frm_sc.equalsIgnoreCase("")){
//                  String date_from_serCenter = rs1.getString("ser_centre_received_date");
//                    String startDateString = date_from_serCenter;
//                       DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
//                           java.util.Date startDate;
//                               startDate = df.parse(startDateString);
//                                  String newDateString = df.format(startDate);
//                                java.util.Date currentdate;
//                              java.util.Date dateobj = new java.util.Date();
//                            String  current_date=df.format(dateobj);
//                          currentdate=df.parse(current_date);
//                                
//                  SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//
//                      java.util.Date d1 = null;
//                      java.util.Date d2 = null;
//                           d1 = startDate;
//                           d2 = currentdate;
//                           long diff = d2.getTime() - d1.getTime();
//                           long diffDays = diff / (24 * 60 * 60 * 1000);
//          ja.put(diffDays);
//
//           }else{
//               ja.put(type_of_work);
//           }
              //            for PFRN Days Calculation
                 String ship_dt_frm_sc=rs1.getString("ship_dt_frm_ser_cntr");
                  if(ship_dt_frm_sc == null)
                  {
                    String ship_date_from_serCenter = rs1.getString("ser_centre_received_date");
                    String startDatePFRN = ship_date_from_serCenter;
                       DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd"); 
                       
                           java.util.Date PFRNstartDate;
                               PFRNstartDate = df1.parse(startDatePFRN);
                         //         String PFRNDateString = df1.format(PFRNstartDate);
                                java.util.Date PFRNcurrentdate;
                              java.util.Date PFRNdateobj = new java.util.Date();
                            String  PFRNcurrent_date=df1.format(PFRNdateobj);
                          PFRNcurrentdate=df1.parse(PFRNcurrent_date);
                                
                  //SimpleDateFormat PFRNformat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                      java.util.Date d11 = null;
                      java.util.Date d21 = null;
                           d11 = PFRNstartDate;
                           d21 = PFRNcurrentdate;
                           long PFRNdiff = d21.getTime() - d11.getTime();
                           long PFRNdiffDays = PFRNdiff / (24 * 60 * 60 * 1000);
          ja.put(PFRNdiffDays);  
                  }
                  else
                  {
                      try{
                      String date1=rs1.getString("ser_centre_received_date");
                      DateFormat df11=new SimpleDateFormat("yyyy-MM-dd");
                      
                      java.util.Date SCdate;
                      SCdate=df11.parse(date1);
                      
                     
                      String date2=ship_dt_frm_sc;
                      java.util.Date shipDate;
                      shipDate=df11.parse(date2);
                      
                  //    SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                     
                      java.util.Date d21=null;
                      java.util.Date d22=null;
                      
                      d21=SCdate;
                      d22=shipDate;
                      
                      long diffrnce=d22.getTime()-d21.getTime();
                      long difDays=diffrnce/(24 * 60 * 60 * 1000);
                      
                      ja.put(difDays+" SC");}
                      catch(Exception e)
                      {
                          e.printStackTrace();
                      }
                  }
//ends 

//                    pending days for OBPending
                          if(ship_dt_frm_sc == null)
                  {
                    String ship_date_from_serCenter = rs1.getString("ser_centre_received_date");
                    String startDatePFRN = ship_date_from_serCenter;
                       DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd"); 
                       
                           java.util.Date PFRNstartDate;
                               PFRNstartDate = df1.parse(startDatePFRN);
                              
                                java.util.Date PFRNcurrentdate;
                              java.util.Date PFRNdateobj = new java.util.Date();
                            String  PFRNcurrent_date=df1.format(PFRNdateobj);
                          PFRNcurrentdate=df1.parse(PFRNcurrent_date);
               
                      java.util.Date d11 = null;
                      java.util.Date d21 = null;
                           d11 = PFRNstartDate;
                           d21 = PFRNcurrentdate;
                           long PFRNdiff = d21.getTime() - d11.getTime();
                           long PFRNdiffDays = PFRNdiff / (24 * 60 * 60 * 1000);
          ja.put(PFRNdiffDays);  
                  }
                  else
                  {
                      try{
                      String date1=rs1.getString("ser_centre_received_date");
                      DateFormat df11=new SimpleDateFormat("yyyy-MM-dd");
                      
                      java.util.Date SCdate;
                      SCdate=df11.parse(date1);
                      
                     
                      String date2=ship_dt_frm_sc;
                      java.util.Date shipDate;
                      shipDate=df11.parse(date2);
                      
                  //    SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                     
                      java.util.Date d21=null;
                      java.util.Date d22=null;
                      
                      d21=SCdate;
                      d22=shipDate;
                      
                      long diffrnce=d22.getTime()-d21.getTime();
                      long difDays=diffrnce/(24 * 60 * 60 * 1000);
                      
                      ja.put(difDays+" OB");}
                      catch(Exception e)
                      {
                          e.printStackTrace();
                      }
                  }
//                    ends

//                      pending Days UnderRepair
                          String recived_date=rs1.getString("repaired_brd_stk_date");
                          if(recived_date == null)
                  {
                    String ship_date_from_serCenter = rs1.getString("ser_centre_received_date");
                    String startDatePFRN = ship_date_from_serCenter;
                       DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd"); 
                       
                           java.util.Date PFRNstartDate;
                               PFRNstartDate = df1.parse(startDatePFRN);
                                 
                                java.util.Date PFRNcurrentdate;
                              java.util.Date PFRNdateobj = new java.util.Date();
                            String  PFRNcurrent_date=df1.format(PFRNdateobj);
                          PFRNcurrentdate=df1.parse(PFRNcurrent_date);
                                
               //   SimpleDateFormat PFRNformat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                      java.util.Date d11 = null;
                      java.util.Date d21 = null;
                           d11 = PFRNstartDate;
                           d21 = PFRNcurrentdate;
                           long PFRNdiff = d21.getTime() - d11.getTime();
                           long PFRNdiffDays = PFRNdiff / (24 * 60 * 60 * 1000);
          ja.put(PFRNdiffDays);  
                  }
                  else
                  {
                      try{
                      String date1=rs1.getString("ser_centre_received_date");
                      DateFormat df11=new SimpleDateFormat("yyyy-MM-dd");
                      
                      java.util.Date SCdate;
                      SCdate=df11.parse(date1);
                      
                     
                      String date2=recived_date;
                      java.util.Date shipDate;
                      shipDate=df11.parse(date2);
                      
                    //  SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                     
                      java.util.Date d21=null;
                      java.util.Date d22=null;
                      
                      d21=SCdate;
                      d22=shipDate;
                      
                      long diffrnce=d22.getTime()-d21.getTime();
                      long difDays=diffrnce/(24 * 60 * 60 * 1000);
                      
                      ja.put(difDays+" UR");}
                      catch(Exception e)
                      {
                          e.printStackTrace();
                      }
                  }
//                          ends

//                      Pending Days SC_Completed
                               if(recived_date == null)
                  {
                    String ship_date_from_serCenter = rs1.getString("ser_centre_received_date");
                    String startDatePFRN = ship_date_from_serCenter;
                       DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd"); 
                       
                           java.util.Date PFRNstartDate;
                               PFRNstartDate = df1.parse(startDatePFRN);
                                  String PFRNDateString = df1.format(PFRNstartDate);
                                java.util.Date PFRNcurrentdate;
                              java.util.Date PFRNdateobj = new java.util.Date();
                            String  PFRNcurrent_date=df1.format(PFRNdateobj);
                          PFRNcurrentdate=df1.parse(PFRNcurrent_date);
                                
                  SimpleDateFormat PFRNformat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                      java.util.Date d11 = null;
                      java.util.Date d21 = null;
                           d11 = PFRNstartDate;
                           d21 = PFRNcurrentdate;
                           long PFRNdiff = d21.getTime() - d11.getTime();
                           long PFRNdiffDays = PFRNdiff / (24 * 60 * 60 * 1000);
          ja.put(PFRNdiffDays);  
                  }
                  else
                  {
                      try{
                      String date1=rs1.getString("ser_centre_received_date");
                      DateFormat df11=new SimpleDateFormat("yyyy-MM-dd");
                      
                      java.util.Date SCdate;
                      SCdate=df11.parse(date1);
                      
                     
                      String date2=recived_date;
                      java.util.Date shipDate;
                      shipDate=df11.parse(date2);
                      
                   //   SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                     
                      java.util.Date d21=null;
                      java.util.Date d22=null;
                      
                      d21=SCdate;
                      d22=shipDate;
                      
                      long diffrnce=d22.getTime()-d21.getTime();
                      long difDays=diffrnce/(24 * 60 * 60 * 1000);
                      
                      ja.put(difDays+" SCC");}
                      catch(Exception e)
                      {
                          e.printStackTrace();
                      }
                  }
//                          Ends
                               //System.out.println(rs1.getString("status")+" the statusss issss");
                               //ja.put(rs1.getString("status")); 
          ja.put("");
          ja.put("");
            
          System.out.println(rs1.getString("status")+"   the statuss issss");
         

          array.put(ja);
      }
                      }  
                   else
                      {
                          String globeSearch = " and (def_gir_no like '%" + searchTerm + "%'" +" or sc_ref_no like '%" + searchTerm + "%'" +" or frn_no like '%" + searchTerm + "%')" + "" ;
                            if ((searchTerm != "") && (individualSearch != "")) {
                                searchSQL = globeSearch + " and " + individualSearch;
                            } else if (individualSearch != "") {
                                searchSQL = " and " + individualSearch;
                            } else if (searchTerm != "") {
                                searchSQL = globeSearch;
                            }
                            
                             sql1 = sql1 + searchSQL;
      sql1 = sql1 + " order by " + colName + " " + dir;
      sql1 = sql1 + " limit " + start + ", " + amount;
      System.out.println(sql1+"this is query");
      PreparedStatement ps1 = conn.prepareStatement(sql1);
      ResultSet rs1 = ps1.executeQuery();
      while (rs1.next())
      {
          JSONArray ja = new JSONArray();
          ja.put(rs1.getString("ser_id"));
          System.out.println(rs1.getString("status")+" the statusss issss");
          ja.put(rs1.getString("status"));
          ja.put(rs1.getString("entry_date"));
          ja.put(rs1.getString("sc_ref_no"));
          sc_engnr=EmployeeDao.geteName(rs1.getString("sc_engnr"));
          ja.put(sc_engnr);
          ja.put(rs1.getString("frn_no"));
          ja.put(rs1.getString("region"));
          engineer=EmployeeDao.geteName(rs1.getString("engineer_id"));
          ja.put(engineer);
          ja.put(rs1.getString("cust_name"));
//          product_model = ModelDao.getpName(rs1.getString("product_model"));
//          ja.put(product_model);
          String model=ModelDao.getModelname(rs1.getString("product_model"));
          ja.put(model);
          unit_status=DropdownDao.getddName(rs1.getString("unit_status"));
          ja.put(unit_status);
          ja.put(rs1.getString("def_mod_brd_name"));
          ja.put(rs1.getString("def_gir_no"));
          type_of_work=DropdownDao.getddName(rs1.getString("type_of_work"));
          ja.put(type_of_work);
          //pending days
        
//                  String date_from_serCenter = rs1.getString("ser_centre_received_date");
//                    String startDateString = date_from_serCenter;
//                       DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
//                           java.util.Date startDate;
//                               startDate = df.parse(startDateString);
//                                  String newDateString = df.format(startDate);
//                                java.util.Date currentdate;
//                              java.util.Date dateobj = new java.util.Date();
//                            String  current_date=df.format(dateobj);
//                          currentdate=df.parse(current_date);
//                                
//                  SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//
//                      java.util.Date d1 = null;
//                      java.util.Date d2 = null;
//                           d1 = startDate;
//                           d2 = currentdate;
//                           long diff = d2.getTime() - d1.getTime();
//                           long diffDays = diff / (24 * 60 * 60 * 1000);
//          ja.put(diffDays);
          
//          for PFRN Days Calculation
                 String ship_dt_frm_sc=rs1.getString("ship_dt_frm_ser_cntr");
                  if(ship_dt_frm_sc == null)
                  {
                    String ship_date_from_serCenter = rs1.getString("ser_centre_received_date");
                    String startDatePFRN = ship_date_from_serCenter;
                       DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd"); 
                       
                           java.util.Date PFRNstartDate;
                               PFRNstartDate = df1.parse(startDatePFRN);
                                  String PFRNDateString = df1.format(PFRNstartDate);
                                java.util.Date PFRNcurrentdate;
                              java.util.Date PFRNdateobj = new java.util.Date();
                            String  PFRNcurrent_date=df1.format(PFRNdateobj);
                          PFRNcurrentdate=df1.parse(PFRNcurrent_date);
                                
                  SimpleDateFormat PFRNformat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                      java.util.Date d11 = null;
                      java.util.Date d21 = null;
                           d11 = PFRNstartDate;
                           d21 = PFRNcurrentdate;
                           long PFRNdiff = d21.getTime() - d11.getTime();
                           long PFRNdiffDays = PFRNdiff / (24 * 60 * 60 * 1000);
          ja.put(PFRNdiffDays);  
                  }
                  else
                  {
                      try{
                      String date1=rs1.getString("ser_centre_received_date");
                      DateFormat df11=new SimpleDateFormat("yyyy-MM-dd");
                      
                      java.util.Date SCdate;
                      SCdate=df11.parse(date1);
                      
                     
                      String date2=ship_dt_frm_sc;
                      java.util.Date shipDate;
                      shipDate=df11.parse(date2);
                      
                      SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                     
                      java.util.Date d21=null;
                      java.util.Date d22=null;
                      
                      d21=SCdate;
                      d22=shipDate;
                      
                      long diffrnce=d22.getTime()-d21.getTime();
                      long difDays=diffrnce/(24 * 60 * 60 * 1000);
                      
                      ja.put(difDays+" SC");}
                      catch(Exception e)
                      {
                          e.printStackTrace();
                      }
                  }
//ends 

//                    pending days for OBPending
                          if(ship_dt_frm_sc == null)
                  {
                    String ship_date_from_serCenter = rs1.getString("ser_centre_received_date");
                    String startDatePFRN = ship_date_from_serCenter;
                       DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd"); 
                       
                           java.util.Date PFRNstartDate;
                               PFRNstartDate = df1.parse(startDatePFRN);
                                  String PFRNDateString = df1.format(PFRNstartDate);
                                java.util.Date PFRNcurrentdate;
                              java.util.Date PFRNdateobj = new java.util.Date();
                            String  PFRNcurrent_date=df1.format(PFRNdateobj);
                          PFRNcurrentdate=df1.parse(PFRNcurrent_date);
                                
              

                      java.util.Date d11 = null;
                      java.util.Date d21 = null;
                           d11 = PFRNstartDate;
                           d21 = PFRNcurrentdate;
                           long PFRNdiff = d21.getTime() - d11.getTime();
                           long PFRNdiffDays = PFRNdiff / (24 * 60 * 60 * 1000);
          ja.put(PFRNdiffDays);  
                  }
                  else
                  {
                      try{
                      String date1=rs1.getString("ser_centre_received_date");
                      DateFormat df11=new SimpleDateFormat("yyyy-MM-dd");
                      
                      java.util.Date SCdate;
                      SCdate=df11.parse(date1);
                      
                     
                      String date2=ship_dt_frm_sc;
                      java.util.Date shipDate;
                      shipDate=df11.parse(date2);
                      
                    //  SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                     
                      java.util.Date d21=null;
                      java.util.Date d22=null;
                      
                      d21=SCdate;
                      d22=shipDate;
                      
                      long diffrnce=d22.getTime()-d21.getTime();
                      long difDays=diffrnce/(24 * 60 * 60 * 1000);
                      
                      ja.put(difDays+" OB");}
                      catch(Exception e)
                      {
                          e.printStackTrace();
                      }
                  }
//                    ends

//                      pending Days UnderRepair
                          String recived_date=rs1.getString("repaired_brd_stk_date");
                          if(recived_date == null)
                  {
                    String ship_date_from_serCenter = rs1.getString("ser_centre_received_date");
                    String startDatePFRN = ship_date_from_serCenter;
                       DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd"); 
                       
                           java.util.Date PFRNstartDate;
                               PFRNstartDate = df1.parse(startDatePFRN);
                                  String PFRNDateString = df1.format(PFRNstartDate);
                                java.util.Date PFRNcurrentdate;
                              java.util.Date PFRNdateobj = new java.util.Date();
                            String  PFRNcurrent_date=df1.format(PFRNdateobj);
                          PFRNcurrentdate=df1.parse(PFRNcurrent_date);
                                
                  SimpleDateFormat PFRNformat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                      java.util.Date d11 = null;
                      java.util.Date d21 = null;
                           d11 = PFRNstartDate;
                           d21 = PFRNcurrentdate;
                           long PFRNdiff = d21.getTime() - d11.getTime();
                           long PFRNdiffDays = PFRNdiff / (24 * 60 * 60 * 1000);
          ja.put(PFRNdiffDays);  
                  }
                  else
                  {
                      try{
                      String date1=rs1.getString("ser_centre_received_date");
                      DateFormat df11=new SimpleDateFormat("yyyy-MM-dd");
                      
                      java.util.Date SCdate;
                      SCdate=df11.parse(date1);
                      
                     
                      String date2=recived_date;
                      java.util.Date shipDate;
                      shipDate=df11.parse(date2);
                      
                      SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                     
                      java.util.Date d21=null;
                      java.util.Date d22=null;
                      
                      d21=SCdate;
                      d22=shipDate;
                      
                      long diffrnce=d22.getTime()-d21.getTime();
                      long difDays=diffrnce/(24 * 60 * 60 * 1000);
                      
                      ja.put(difDays+" UR");}
                      catch(Exception e)
                      {
                          e.printStackTrace();
                      }
                  }
//                          ends

//                      Pending Days SC_Completed
                               if(recived_date == null)
                  {
                    String ship_date_from_serCenter = rs1.getString("ser_centre_received_date");
                    String startDatePFRN = ship_date_from_serCenter;
                       DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd"); 
                       
                           java.util.Date PFRNstartDate;
                               PFRNstartDate = df1.parse(startDatePFRN);
                                  String PFRNDateString = df1.format(PFRNstartDate);
                                java.util.Date PFRNcurrentdate;
                              java.util.Date PFRNdateobj = new java.util.Date();
                            String  PFRNcurrent_date=df1.format(PFRNdateobj);
                          PFRNcurrentdate=df1.parse(PFRNcurrent_date);
                                
               //   SimpleDateFormat PFRNformat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                      java.util.Date d11 = null;
                      java.util.Date d21 = null;
                           d11 = PFRNstartDate;
                           d21 = PFRNcurrentdate;
                           long PFRNdiff = d21.getTime() - d11.getTime();
                           long PFRNdiffDays = PFRNdiff / (24 * 60 * 60 * 1000);
          ja.put(PFRNdiffDays);  
                  }
                  else
                  {
                      try{
                      String date1=rs1.getString("ser_centre_received_date");
                      DateFormat df11=new SimpleDateFormat("yyyy-MM-dd");
                      
                      java.util.Date SCdate;
                      SCdate=df11.parse(date1);
                      
                     
                      String date2=recived_date;
                      java.util.Date shipDate;
                      shipDate=df11.parse(date2);
                      
                   //   SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                     
                      java.util.Date d21=null;
                      java.util.Date d22=null;
                      
                      d21=SCdate;
                      d22=shipDate;
                      
                      long diffrnce=d22.getTime()-d21.getTime();
                      long difDays=diffrnce/(24 * 60 * 60 * 1000);
                      
                      ja.put(difDays+" SCC");}
                      catch(Exception e)
                      {
                          e.printStackTrace();
                      }
                  }
//                          Ends
                             
         ja.put("");
           ja.put("");
          // ja.put(rs1.getString("status"));
          // System.out.println(rs1.getString("status")+"   the statuss issss");
         
         

          array.put(ja);
      }
                      }
              
     
      String sql2;
//      String sql2 = "SELECT count(*) FROM " + table;
        if("admin".equalsIgnoreCase(role))
                      {
                          sql2 = "SELECT count(*) FROM " + table;
                          if (searchTerm != "")
                          {
                              sql2 = sql2 + searchSQL;
                              PreparedStatement ps2 = conn.prepareStatement(sql2);
                              ResultSet rs2 = ps2.executeQuery();
                              if (rs2.next()) {
                                  totalAfterFilter = rs2.getInt("count(*)");
                              }
                          }
                      }  
                   else
                      {
                          sql2 = "SELECT count(*) FROM service_master WHERE division='"+divName+"'";
                          if (searchTerm != "")
                          {
                              sql2 = sql2 + searchSQL;
                              PreparedStatement ps2 = conn.prepareStatement(sql2);
                              ResultSet rs2 = ps2.executeQuery();
                              if (rs2.next()) {
                                  totalAfterFilter = rs2.getInt("count(*)");
                              }
                          }
                      }
     
      result.put("iTotalRecords", total);
      result.put("iTotalDisplayRecords", totalAfterFilter);
      result.put("aaData", array);
      response.setContentType("application/json");
      response.setHeader("Cache-Control", "no-store");
      out.print(result);
                   }
                   catch (Exception e) {}
               }
               catch (Throwable localThrowable1)
               {
                   Throwable localThrowable3 = localThrowable1;
               }
               finally
               {
                   if (out != null) {
                       Object localThrowable3 = null;
                       if (localThrowable3 != null) {
                           try
                           {
                               out.close();
                           }
                           catch (Throwable localThrowable2)
                           {
      //             localThrowable3.addSuppressed(localThrowable2);
                           }
                       } else {
                           out.close();
                       }
                   }
               }
           }
          catch (ClassNotFoundException ex)
          {
                  Logger.getLogger(Under_RepairController.class.getName()).log(Level.SEVERE, null, ex);
          } catch (SQLException ex) {
          Logger.getLogger(Service_Controller.class.getName()).log(Level.SEVERE, null, ex);
      }
  
    	
//        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //        processRequest(request, response);
            String logname=request.getParameter("logname");
            int eId=EmployeeDao.getEmpId(logname);
            String scarp="SCRAPPED";
            String ddId=DropdownDao.getddId(scarp);
            String month=request.getParameter("month");
            String year=request.getParameter("year");
            ScarpListDao.scarpEnggList(year, month, eId, ddId, request, response);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ScarpListEnggController.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
