/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.BranchDao;
import com.schillerindiaservices.Dao.RegionDao;
import com.schillerindiaservices.bean.Branch;
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
 * @author MR
 */
public class CompanyBranchController extends HttpServlet {

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
            out.println("<title>Servlet CompanyBranchController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CompanyBranchController at " + request.getContextPath() + "</h1>");
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
        
      
            int id=Integer.parseInt(request.getParameter("id"));
            String action=request.getParameter("action");
            BranchDao bd= new BranchDao();
//            if(action.equalsIgnoreCase("delete"));
//            {
//                bd.delete(id);
//                response.sendRedirect("CompanyBranchList.jsp");
//            }
         try
            {
                if(action!=null)
                {
                    if(action.equalsIgnoreCase("edit"))
                    { 
                        Branch d=  BranchDao.getById(id);
                        request.setAttribute("editablerecord", d);
                        RequestDispatcher rd=request.getRequestDispatcher("BranchEdit.jsp");
                        rd.forward(request, response);
                    }
                    else
                    {
                        bd.delete(id);
                        response.sendRedirect("CompanyBranchList.jsp");
                    }
                }
            } 
            catch (ClassNotFoundException ex) 
            {
                Logger.getLogger(CompanyController.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(CompanyController.class.getName()).log(Level.SEVERE, null, ex);
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
                    
                    String regionname=request.getParameter("regionname");
                    String[] branchname=request.getParameterValues("branchname");
                    String[] branchlocation=request.getParameterValues("branchlocation");
                    String[] branchaddress=request.getParameterValues("branchaddress");
                    String[] branchphone=request.getParameterValues("branchphone");
                    BranchDao bd= new BranchDao();
                    try 
                    {
                              int regionid=RegionDao.getRegionId(regionname);
                              int branchresult=bd.saveall(branchname,branchlocation,branchaddress,branchphone, regionid);
                              RequestDispatcher rd=request.getRequestDispatcher("CompanyBranchList.jsp");  
                              rd.forward(request, response);
                     }
                    catch (ClassNotFoundException | SQLException ex) 
                    {
                         Logger.getLogger(CompanyBranchController.class.getName()).log(Level.SEVERE, null, ex);
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
