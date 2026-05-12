/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.ServiceDao;
import com.schillerindiaservices.Dao.UnderRepairDao;
import com.schillerindiaservices.bean.service_master;
import com.schillerindiaservices.connection.DbConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.json.JSONArray;
 import org.json.JSONObject;

/**
 *
 * @author Kavin_rkz
 */
public class UnderRepairController extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();Throwable localThrowable3 = null;
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UnderRepairController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UnderRepairController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            
            
       String[] cols = { "ser_id", "repaired_brd_stk_date", "sc_engnr","frn_no","cust_name","product_model","unit_status","def_mod_brd_name","def_gir_no","type_of_work","ship_dt_frm_ser_cntr","disp_adv_no"};
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
       (!sdir.equals("asc"))) {
        dir = "desc";
      }
      String colName = cols[col];
      int total = 0;
      Connection conn = DbConnection.getConnection();
     try
   {
     String sql = "SELECT count(*) FROM " + table;
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery();
       if (rs.next()) {
         total = rs.getInt("count(*)");
     }
     
     int totalAfterFilter = total;
     try
      {
         String searchSQL = "";
         String sql1 = "SELECT * FROM " + table;
         String searchTerm = request.getParameter("search[value]");
         String globeSearch = " where (ser_id like '%" + searchTerm + "%'" +" or repaired_brd_stk_date like '%" + searchTerm + "%'" + " or sc_engnr like '%" + searchTerm + "%'" + " or frn_no like '%" + searchTerm + "%'" + " or cust_name like '%" + searchTerm + "%'" + " or product_model like '%" + searchTerm + "%')";
        if ((searchTerm != "") && (individualSearch != "")) {
          searchSQL = globeSearch + " and " + individualSearch;
        } else if (individualSearch != "") {
          searchSQL = " where " + individualSearch;
        } else if (searchTerm != "") {
         searchSQL = globeSearch;
       }
        sql = sql + searchSQL;
        sql = sql + " order by " + colName + " " + dir;
        sql = sql + " limit " + start + ", " + amount;
        PreparedStatement ps1 = conn.prepareStatement(sql);
        ResultSet rs1 = ps1.executeQuery();
        while (rs1.next())
    {
         JSONArray ja = new JSONArray();
         ja.put(rs1.getString("ser_id"));
         ja.put(rs1.getString("repaired_brd_stk_date"));
         ja.put(rs1.getString("ship_dt_frm_ser_cntr"));
         array.put(ja);
      }
        String sql2 = "SELECT count(*) FROM " + table;
        if (searchTerm != "")
       {
          sql2 = sql2 + searchSQL;
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
    localThrowable3 = localThrowable1;
    }
    finally
    {
      if (out != null) {
        if (localThrowable3 != null) {
        try
          {
           out.close();
          }
          catch (Throwable localThrowable2)
          {
             localThrowable3.addSuppressed(localThrowable2);
      }
        } else {
         out.close();
        }
       }
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
//        processRequest(request, response);
 try 
        {
          System.out.println("inside underrepair fn");
         String qid=request.getParameter("qid");
         System.out.println(qid+" qid---");
        String action=request.getParameter("action");
        ServiceDao demo=new ServiceDao();
        if(action!=null)
        {
            if(action.equalsIgnoreCase("edit"))
            {   
                int id=Integer.parseInt(request.getParameter("id"));
                service_master s=  ServiceDao.getById(id);
                request.setAttribute("editablerecord", s);
                RequestDispatcher rd=request.getRequestDispatcher("ServiceForm2.jsp");
                rd.forward(request, response);
            }
            else
            {
                int id=Integer.parseInt(request.getParameter("id"));
                demo.delete(id);
                RequestDispatcher rd=request.getRequestDispatcher("ServiceList.jsp");  
                rd.forward(request, response);
            }
        }
        } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(SupplierController.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
//          List list=UnderRepairDao.underRepair();
//          request.setAttribute("UnderRepair", list);
//          RequestDispatcher rd=request.getRequestDispatcher("UnderRepair.jsp"); 
//          rd.forward(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UnderRepairController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UnderRepairController.class.getName()).log(Level.SEVERE, null, ex);
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
