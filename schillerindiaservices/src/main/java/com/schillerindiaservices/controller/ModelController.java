/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.ModelDao;
import com.schillerindiaservices.Dao.ProductDao;
import com.schillerindiaservices.bean.Model;
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
public class ModelController extends HttpServlet {

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
            out.println("<title>Servlet ModelController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ModelController at " + request.getContextPath() + "</h1>");
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
            int pid=Integer.parseInt(request.getParameter("pid"));
            int mid=Integer.parseInt(request.getParameter("mid"));
            String action=request.getParameter("action");
            ModelDao demo=new ModelDao();
            if(action!=null)
            {
                    try 
                    {
                        demo.delete(mid);
//                        RequestDispatcher rd=request.getRequestDispatcher("ProductForm.jsp?id=pid&action=edit");  
                        response.sendRedirect("ProductsController?id="+pid+"&action=edit");
//                        rd.forward(request, response);
                    } catch (ClassNotFoundException ex) 
                    {
                        Logger.getLogger(DealerController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(DealerController.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                
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
            
            Model mod=new Model();
            try
            {
            int id=Integer.parseInt(request.getParameter("id"));
            String productname=request.getParameter("productname");
            int productid=ProductDao.getid(productname);
            String modelname=request.getParameter("modelname");
            String modeldesc=request.getParameter("modeldesc");
           
                
                mod.setProductId(productid);
                mod.setModelName(modelname);
                mod.setModelDesc(modeldesc);
                
                ModelDao md=new ModelDao();
                    if(id==0)
                    {
                        int status;
                        status = md.save(mod);
                        RequestDispatcher rd=request.getRequestDispatcher("ProductList.jsp");
                        rd.forward(request, response);
                    }
                    else
                    {
                        mod.setModelId(id);
                        md.update(mod);
                        RequestDispatcher rd=request.getRequestDispatcher("ProductList.jsp");  
                        rd.forward(request, response);
                    }
            }
            catch(SQLException ex)
            {
               ex.printStackTrace();
            } catch (ClassNotFoundException ex) 
            {
              Logger.getLogger(DealerController.class.getName()).log(Level.SEVERE, null, ex);
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
