/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.BranchDao;
import com.schillerindiaservices.Dao.CallRegisterDao;
import com.schillerindiaservices.Dao.ModelDao;
import com.schillerindiaservices.Dao.PendingActivityDao;
import com.schillerindiaservices.common.UtilFunctions;
import com.schillerindiaservices.connection.DbConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ShineLoGics
 */
public class RepairActClosed_Controller extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
            out.println("<title>Servlet RepairAct_Controller</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RepairAct_Controller at " + request.getContextPath() + "</h1>");
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
        try
     {
         PrintWriter out = response.getWriter();
         String[] cols = { "id","entry_date","division", "sc_ref_no","def_gir_no","category","model","def_brd_mod_name","no_of_days","status"};
      //   String table = "pendingactmaster";
    //     DbConnection dbo = new DbConnection();
         JSONObject result = new JSONObject();
         JSONArray array = new JSONArray();
         
         UtilFunctions utilfn = new UtilFunctions();
         int amount = 10;
         int start = 0;
         int echo = 0;
         int col = 0;
         
     //    String engine = "";
     //    String browser = "";
    //     String platform = "";
     //    String version = "";
    //     String grade = "";
         String dir = "desc";
         String sStart = request.getParameter("start");
         String sAmount = request.getParameter("length");
         String sEcho = request.getParameter("sEcho");
         String sCol = request.getParameter("order[0][column]");
         String sdir = request.getParameter("order[0][dir]");
         
          String individualSearch = "";
           String entry_date = request.getParameter("columns[1][search][value]");
         List<String> sArray = new ArrayList<String>();
         if (!entry_date.equals("")) {
         String sCode = " LOWER(division) like LOWER('" + entry_date + "%')";
         sArray.add(sCode);
 
    }
    if(sArray.size()==1){
        individualSearch = sArray.get(0);
    }else if(sArray.size()>1){
        for(int i=0;i<sArray.size()-1;i++){
            individualSearch += sArray.get(i)+ " and ";
        }
        individualSearch += sArray.get(sArray.size()-1);
    }

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
                 (!sdir.equals("asc"))) {
             dir = "desc";
         }
         String colName = cols[col];
         int total = 0;
         Connection conn = DbConnection.getConnection();
         try
         {
             int nam=0;
             System.out.println("inside fn 1st");
               HttpSession session = request.getSession();
               nam = (int) session.getAttribute("loguserid");
            //   String divName=PendingActivityDao.PendingActDivName(nam);
               String divName="OEM";
               System.out.println("divName -- "+divName);
             String sql = "SELECT COUNT(*) FROM repair_master WHERE  status='completed'";
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             if (rs.next()) {
                 total = rs.getInt("count(*)");
             }
             
             int totalAfterFilter = total;
             try
             {
                 
                 String searchSQL = "";
//                 String sql1 = "SELECT * FROM " + table;
                 String sql1 = "SELECT * FROM repair_master WHERE  status='completed'";
    
            //    String unit_status="";
         //       String type_of_work="";
         //       String product_model="";
        
                 String searchTerm = request.getParameter("search[value]");
               String globeSearch = " and (division like '%" + searchTerm + "%'" +" or sc_ref_no like '%" + searchTerm + "%'" +" or def_gir_no like '%" + searchTerm + "%')" + "" ;
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
               
        PreparedStatement ps1 = conn.prepareStatement(sql1);
        ResultSet rs1 = ps1.executeQuery();
        System.out.println("afetr execute qury "+rs1.getFetchSize());
        while (rs1.next())
        {
        	System.out.println("inside loop");
            JSONArray ja = new JSONArray();
            ja.put(rs1.getInt("id"));
         //  ja.put(rs1.getString("division"));
          //  ja.put(rs1.getString("entry_date"));
         //  System.out.println("entry date --->"+utilfn.getDateFormat(rs1.getDate("entry_date").toString()));
            
           ja.put(rs1.getString("entry_date"));
           ja.put(rs1.getString("division"));
          //  String engg=CallRegisterDao.getEnggName(rs1.getString("sc_engg"));
        //    ja.put(engg);
            ja.put(rs1.getString("sc_ref_no"));
            ja.put(rs1.getString("def_gir_no"));
            ja.put(rs1.getString("category"));
          //  String product_model = ModelDao.getpName();
            ja.put(rs1.getString("model"));
            ja.put(rs1.getString("def_brd_mod_name"));
            //ja.put(rs1.getString("ser_id"));
           
            
            
            String strdate = rs1.getString("entry_date");
            
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
            java.util.Date startDate = null;
            startDate = df.parse(strdate);
            
            java.util.Date currentdate = null;
            java.util.Date dateobj = new java.util.Date();
            String  current_date=df.format(dateobj);
          currentdate=df.parse(current_date);
          
            java.util.Date d1 = null;
            java.util.Date d2 = null;
                 d1 = startDate;
                 d2 = currentdate;
                 
                
                 
            long diff = d2.getTime() - d1.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            System.out.println("diff dayssss   "+diffDays);
          
            ja.put(rs1.getString("no_of_days"));
            ja.put(rs1.getString("status"));
       //     ja.put(rs1.getString("pending_form"));
     //       ja.put(utilfn.getDateFormat(rs1.getString("tar_closed_date").toString()));
     //       ja.put(rs1.getString("closed_date"));
     //       ja.put(rs1.getString("remarks"));
      //      ja.put(rs1.getString("status"));
            //pending days
//                    String date_from_serCenter = rs1.getString("ser_centre_received_date");
//                      String startDateString = date_from_serCenter;
//                         DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
//                             java.util.Date startDate;
//                                 startDate = df.parse(startDateString);
//                                    String newDateString = df.format(startDate);
//                                  java.util.Date currentdate;
//                                java.util.Date dateobj = new java.util.Date();
//                              String  current_date=df.format(dateobj);
//                            currentdate=df.parse(current_date);
//                                  
//                    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//
                  
//                             long diff = d2.getTime() - d1.getTime();
//                             long diffDays = diff / (24 * 60 * 60 * 1000);
//            ja.put(diffDays);
            
         //   ja.put("");

            array.put(ja);
        }
        
        
        String sql2 = "SELECT COUNT(*) FROM repair_master WHERE  status='completed'";
        if (searchTerm != "")
        {
            sql2 = sql2 + searchSQL;
           // out.println(" Valahsdfh " + sql2);
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                totalAfterFilter = rs2.getInt("count(*)");
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
            Logger.getLogger(EmpPFRNController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        processRequest(request, response);
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
