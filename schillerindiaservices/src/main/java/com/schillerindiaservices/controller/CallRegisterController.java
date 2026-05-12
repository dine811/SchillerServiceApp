/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.CallRegisterDao;
import com.schillerindiaservices.Dao.ServiceDao;
import com.schillerindiaservices.bean.Callregister;
import com.schillerindiaservices.bean.service_master;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
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
public class CallRegisterController extends HttpServlet {

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
            out.println("<title>Servlet CallRegisterController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CallRegisterController at " + request.getContextPath() + "</h1>");
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
        int id=Integer.parseInt(request.getParameter("id"));
        String action=request.getParameter("action");
        String pg=request.getParameter("pg");
         CallRegisterDao demo=new  CallRegisterDao();
        if(action!=null)
        {
            if(action.equalsIgnoreCase("edit"))
            {   
                Callregister s=  CallRegisterDao.getById(id);
                request.setAttribute("callrecords", s);
                RequestDispatcher rd=request.getRequestDispatcher("CallRegUpdate.jsp");
                rd.forward(request, response);
            }
            else
            {
                demo.delete(id);
                if(pg.equalsIgnoreCase("cllist"))
                {
                    RequestDispatcher rd=request.getRequestDispatcher("CallListAdmin.jsp");  
                rd.forward(request, response);
                }else{
                    RequestDispatcher rd=request.getRequestDispatcher("ClosedCalls.jsp");  
                rd.forward(request, response);
                }
                
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
        
        Callregister cr=new Callregister();
        CallRegisterDao crd=new CallRegisterDao();
//        int id=Integer.parseInt(request.getParameter("id"));
        int id=Integer.parseInt(request.getParameter("id"));
        if(id==0)
        {
            try {
                String division=request.getParameter("division");
                String sc_engg=request.getParameter("sc_engg");
                String call_date=request.getParameter("call_date");
                System.out.println("call_date"+call_date);
                String call=request.getParameter("call");
                String region=request.getParameter("region");
                String branch=request.getParameter("branch");
                String dealer=request.getParameter("dealer");
                String engineer=request.getParameter("engineer");
                String model=request.getParameter("model");
                String type_of_call=request.getParameter("type_of_call");
                String type_of_commn=request.getParameter("type_of_commn");
                String remarks=request.getParameter("remarks");
                String duration=request.getParameter("duration");
                String status=request.getParameter("status");
                
                cr.setDivision(division);
                cr.setScEngg(sc_engg);
                cr.setCallDate(call_date);
                cr.setCallType(call);
                cr.setRegion(region);
                cr.setBranch(branch);
                cr.setDealer(dealer);
                cr.setEngineer(engineer);
                cr.setModel(model);
                cr.setTypeOfCall(type_of_call);
                cr.setTypeOfCommunication(type_of_commn);
                cr.setRemarks(remarks);
                cr.setDuration(duration);
                cr.setStatusType(status);
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
          java.util.Date dateobj = new java.util.Date();
          String  current_date=df.format(dateobj);
         cr.setEntryDate(current_date);
                crd.Insert_demo(cr);
                response.sendRedirect("CallListEngg.jsp");
                
            } catch (ClassNotFoundException ex) {
            } catch (SQLException ex) {
            }
        }
//        For Updating
        else
        {
            try {
                String call_date=request.getParameter("call_date");
                String call_type=request.getParameter("call_type");
                String type_of_call=request.getParameter("type_of_call");
                String type_of_commn=request.getParameter("type_of_commn");
                
                String remarks=request.getParameter("remarks");
                String duration=request.getParameter("duration");
                String status_t=request.getParameter("status_t");
                
                cr.setCallDate(call_date);
                cr.setCallType(call_type);
                cr.setTypeOfCall(type_of_call);
                cr.setTypeOfCommunication(type_of_commn);
                cr.setRemarks(remarks);
                cr.setDuration(duration);
                cr.setStatusType(status_t);
                cr.setId(id);
                crd.update(cr);
                
                
                HttpSession session=request.getSession();
                String role=(String) session.getAttribute("logrole");
                if(role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("ViceChancellor"))
                {
                    response.sendRedirect("CallListAdmin.jsp");
                }
                else
                {
                    response.sendRedirect("CallListEngg.jsp");
                }
            }
            catch (ClassNotFoundException ex) {
            } catch (SQLException ex) {
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
