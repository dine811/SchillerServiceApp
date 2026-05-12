/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.PendingFRNDao;
import com.schillerindiaservices.Dao.ServiceDao;
import com.schillerindiaservices.bean.service_master;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kavin_rkz
 */
public class PendingFRNController extends HttpServlet {

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
            out.println("<title>Servlet PendingFRNController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PendingFRNController at " + request.getContextPath() + "</h1>");
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
//        processRequest(request, response);
 try 
        {
            
         String qid=request.getParameter("qid");
        System.out.println("qid -- >"+qid);
        int id=Integer.parseInt(request.getParameter("id"));
        String action=request.getParameter("action");
        ServiceDao demo=new ServiceDao();
        if(action!=null)
        {
            if(action.equalsIgnoreCase("edit"))
            {   
                service_master s=  ServiceDao.getById(id);
                request.setAttribute("editablerecord", s);
                RequestDispatcher rd=request.getRequestDispatcher("ServiceForm2.jsp");
                rd.forward(request, response);
            }
            else
            {
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
            //        processRequest(request, response);
            
            List list=PendingFRNDao.pendinfFRNDao();
            request.setAttribute("PendingFRN", list);
            RequestDispatcher rd=request.getRequestDispatcher("PendingFRN.jsp");
            rd.forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(PendingFRNController.class.getName()).log(Level.SEVERE, null, ex);
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
