/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.DropdownvaluesDao;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
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
 * @author MR
 */
public class DropdownValuesController extends HttpServlet {

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
            out.println("<title>Servlet DropdownValuesController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DropdownValuesController at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        String dropdownname=request.getParameter("dropdowncategory");
        String dropdownvalue=request.getParameter("totalstring");
        String ddvalue=request.getParameter("selectname");
        try 
        {
            int i=DropdownvaluesDao.update(dropdownname, dropdownvalue,ddvalue);
            if(i==1)
            {   
//                RequestDispatcher rd=request.getRequestDispatcher("DropdownValuesForm.jsp");  
                response.sendRedirect("DropdownValuesForm.jsp");
                out.print("Dropdown has been updated successfully!");
            }
            else
            {
               
               out.print("Value Exisit");
                response.sendRedirect("DropdownValuesForm.jsp");
                
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DropdownValuesController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(DropdownValuesController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        Dropdownmaster d=new Dropdownmaster();
//        
//        d.setDdname(request.getParameter("dropdowncategory"));
//        d.setDdvalue(request.getParameter("product"));
//        
//        DropdownvaluesDao dd=new DropdownvaluesDao();
//        try {
//            dd.save(d);
//        } catch (SQLException | ClassNotFoundException ex) {
//            Logger.getLogger(DropdownController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        response.sendRedirect("DropdownValuesForm.jsp");  
          
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
