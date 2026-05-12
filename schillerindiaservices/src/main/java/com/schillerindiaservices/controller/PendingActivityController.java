/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.PendingActivityDao;
import com.schillerindiaservices.bean.Pendingactmaster;
import com.schillerindiaservices.common.UtilFunctions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ShineLoGics
 */
public class PendingActivityController extends HttpServlet {

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
            out.println("<title>Servlet PendingActivityController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PendingActivityController at " + request.getContextPath() + "</h1>");
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
        try {
       int id=Integer.parseInt(request.getParameter("id"));
       String action=request.getParameter("action");
       PendingActivityDao pact=new PendingActivityDao();
       if(action!=null)
       {
           if(action.equalsIgnoreCase("edit"))
           {
                   Pendingactmaster pm=PendingActivityDao.getById(id);
                   System.out.println("pending act status----->   "+pm.getStatusType());
                   request.setAttribute("pendingrecord", pm);
                RequestDispatcher rd=request.getRequestDispatcher("PendingActUpdate.jsp");
                rd.forward(request, response);
           }
           else
           {
              pact.delete(id);
               RequestDispatcher rd=request.getRequestDispatcher("PendingActListAdmin.jsp");
                rd.forward(request, response);
           }
       }
         } catch (ClassNotFoundException ex) {
                   Logger.getLogger(PendingActivityController.class.getName()).log(Level.SEVERE, null, ex);
               } catch (SQLException ex) {
                   Logger.getLogger(PendingActivityController.class.getName()).log(Level.SEVERE, null, ex);
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
       Pendingactmaster pam=new Pendingactmaster();
       PendingActivityDao pad=new PendingActivityDao();
       UtilFunctions utilfn = new UtilFunctions();
               HttpSession session=request.getSession();
               String role =(String) session.getAttribute("logrole");
       int id=Integer.parseInt(request.getParameter("id"));
//       for insert
       if(id==0)
       {
           try {
               String division=request.getParameter("division");
               String sc_engg =request.getParameter("sc_engg");
               String initiated_Date =request.getParameter("initiated_Date");
               String model=request.getParameter("model");
               String pending_activity=request.getParameter("pending_activity");
               String responsible=request.getParameter("responsible");
               String pending_form=request.getParameter("pending_form");
               String tar_closed_date=request.getParameter("tar_closed_date");
               String remarks=request.getParameter("remarks");
               String status=request.getParameter("status");
               String sc_inchrg_remarks=request.getParameter("sc_inchrg_remarks");
               
               pam.setId(id);
               pam.setScInchargeRemark(sc_inchrg_remarks);
               pam.setDivision(division);
               pam.setScEngg(sc_engg);
               pam.setInitiateDate(utilfn.getRev_DateFormat(initiated_Date));
               pam.setModel(model);
               pam.setPendingActivity(pending_activity);
               pam.setResponsible(responsible);
               pam.setPendingForm(pending_form);
               pam.setTarClosedDate(utilfn.getRev_DateFormat(tar_closed_date));
               pam.setRemarks(remarks);
               pam.setStatusType(status);
               
               DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
               java.util.Date dateobj = new java.util.Date();
               String  current_date=df.format(dateobj);
               System.out.println("current_date-->"+current_date);
               pam.setEntryDate(current_date);
                String comp="";
               if(status.equalsIgnoreCase("completed"))
               {
                    pam.setClosedDate(current_date);
               }
               else
               {
                   pam.setClosedDate(null);
               }
               pad.Insert(pam);
              if(role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("ViceChancellor"))
              {
                  response.sendRedirect("PendingActListAdmin.jsp");
              }
              else
              {
               response.sendRedirect("PendingActListENGG.jsp");
              }
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(PendingActivityController.class.getName()).log(Level.SEVERE, null, ex);
           } catch (SQLException ex) {
               Logger.getLogger(PendingActivityController.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       else
       {
           try {
               String pending_form=request.getParameter("pending_form");
               String remarks=request.getParameter("remarks");
               String status=request.getParameter("status");
               String sc_inchrg_remarks=request.getParameter("sc_inchrg_remarks");
               String tar_closed_date=request.getParameter("tar_closed_date");
               String initiated_Date =request.getParameter("initiated_Date");
               String model=request.getParameter("model");
               String pending_activity=request.getParameter("pending_activity");
               String responsible=request.getParameter("responsible");
               
               model =  utilfn.addEscapestring(model);
               pending_activity =  utilfn.addEscapestring(pending_activity);
               remarks =  utilfn.addEscapestring(remarks);
               responsible =  utilfn.addEscapestring(responsible);
               
               
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
               java.util.Date dateobj = new java.util.Date();
               String  closed_Date=df.format(dateobj);
               String comp="";
               if(status.equalsIgnoreCase("completed"))
               {
                    pam.setClosedDate(closed_Date);
               }
               else
               {
                   pam.setClosedDate(comp);
               }
               
               pam.setId(id);
                pam.setInitiateDate(utilfn.getRev_DateFormat(initiated_Date));
               pam.setModel(model);
               System.out.println("modal --- "+model);
               pam.setPendingActivity(pending_activity);
               pam.setResponsible(responsible);
               pam.setTarClosedDate(utilfn.getRev_DateFormat(tar_closed_date));
               pam.setScInchargeRemark(sc_inchrg_remarks);
               pam.setPendingForm(pending_form);
              
               pam.setRemarks(remarks);
               pam.setStatusType(status);
               pad.update(pam);
              
               if(role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("ViceChancellor"))
               {
                  response.sendRedirect("PendingActListAdmin.jsp");
               }
               else{
               response.sendRedirect("PendingActListENGG.jsp");
               }
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(PendingActivityController.class.getName()).log(Level.SEVERE, null, ex);
           } catch (SQLException ex) {
               Logger.getLogger(PendingActivityController.class.getName()).log(Level.SEVERE, null, ex);
           }
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
