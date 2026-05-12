/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.BranchDao;
import com.schillerindiaservices.Dao.CompanyDao;
import com.schillerindiaservices.Dao.CompanyDao;
import com.schillerindiaservices.Dao.RegionDao;
import com.schillerindiaservices.bean.Companymaster;
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
public class CompanyController extends HttpServlet {

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
            out.println("<title>Servlet CompanyController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CompanyController at " + request.getContextPath() + "</h1>");
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
            int id=Integer.parseInt(request.getParameter("id"));
            String action=request.getParameter("action");
            CompanyDao comp=new CompanyDao();
            try
            {
                if(action!=null)
                {
                    if(action.equalsIgnoreCase("edit"))
                    { 
                        Companymaster d=  CompanyDao.getById(id);
                        request.setAttribute("editablerecord", d);
                        RequestDispatcher rd=request.getRequestDispatcher("CompanyForm.jsp");
                        rd.forward(request, response);
                    }
                    else
                    {
                        BranchDao b=new BranchDao();
                        RegionDao r=new RegionDao();
                        b.deleteall(id);
                        r.deleteall(id);
                        comp.delete(id);
                        RequestDispatcher rd=request.getRequestDispatcher("CompanyList.jsp");
                        rd.forward(request, response);
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
//        processRequest(request, response);
                    Companymaster v=new Companymaster();
                    int id=Integer.parseInt(request.getParameter("id"));
                    v.setCompId(id);
                    v.setCompName(request.getParameter("comp_name"));
                    String comp_name=request.getParameter("comp_name");
                    v.setCompAddress(request.getParameter("comp_add"));
                    v.setCompPhone(request.getParameter("comp_mob"));
                    v.setCompEmail(request.getParameter("comp_email"));
                    
                    String[] regid=request.getParameterValues("regid");
                    String[] regname=request.getParameterValues("regname");
                    String[] regdesc=request.getParameterValues("regdesc");
                    
                    CompanyDao comp=new CompanyDao();
                    RegionDao reg=new RegionDao();
                    
                    try 
                    {     
                          if(id==0)
                          {
                                int result=comp.save(v);
                                int comp_id=comp.getid(comp_name);
                                if(regname!=null)
                                {
                                    int regionresult=reg.saveall(regname, regdesc,comp_id);
                                }
                                RequestDispatcher rd=request.getRequestDispatcher("CompanyList.jsp");  
                                rd.forward(request, response);
                          }
                          else
                          { 
                              comp.update(v);
                              int comp_id=comp.getid(comp_name);
                              if(regname!=null)
                                {
                                    int regionresult=reg.updateall(regid, regname, regdesc,comp_id);
                                }
                              RequestDispatcher rd=request.getRequestDispatcher("CompanyList.jsp");  
                              rd.forward(request, response);
                          }
                     }
                    catch (ClassNotFoundException | SQLException ex) 
                    {
                         Logger.getLogger(CompanyController.class.getName()).log(Level.SEVERE, null, ex);
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
