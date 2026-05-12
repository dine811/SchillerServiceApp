/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.EmployeeDao;
import com.schillerindiaservices.Dao.ProductDao;
import com.schillerindiaservices.bean.Emploeemaster;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author SHINELOGICS
 */
public class AddUserController extends HttpServlet {

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
            out.println("<title>Servlet AddUserController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddUserController at " + request.getContextPath() + "</h1>");
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
//        processRequest(request, response);
 try {
            Emploeemaster emp=new Emploeemaster();
            String name=request.getParameter("emp_name");
            String address=request.getParameter("emp_add");
            String email=request.getParameter("emp_email");
            String password=request.getParameter("emp_pass");
            String mobile=request.getParameter("emp_mob");
            String role=request.getParameter("emp_role");
            String active=request.getParameter("emp_active");
            String empdept=request.getParameter("dept");
            
//            String id=request.getParameter("id");
            emp.setEmpName(name);
            emp.setEmpAddress(address);
            emp.setEmpEmail(email);
            emp.setEmpPassword(password);
            emp.setEmpMobile(mobile);
            emp.setEmpRole(role);
            emp.setEmpDept(empdept);
            emp.setEmpActive(active);
            
            EmployeeDao empdao=new EmployeeDao();
                empdao.save(emp);
                RequestDispatcher rd=request.getRequestDispatcher("login.html");
                rd.forward(request, response);
            
    }   catch (SQLException ex) {
            Logger.getLogger(AddUserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddUserController.class.getName()).log(Level.SEVERE, null, ex);
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
