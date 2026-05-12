/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.ModelDao;
import com.schillerindiaservices.Dao.ProductDao;
import com.schillerindiaservices.bean.Productmaster;
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
public class ProductsController extends HttpServlet {

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
            out.println("<title>Servlet ProductsController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductsController at " + request.getContextPath() + "</h1>");
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
        ProductDao demo=new ProductDao();
        if(action!=null)
        {
            if(action.equalsIgnoreCase("edit"))
            {
                try {
                    Productmaster d=  ProductDao.getById(id);
                    request.setAttribute("editablerecord", d);
                    RequestDispatcher rd=request.getRequestDispatcher("ProductForm.jsp");
                    rd.forward(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {  
                try 
                {
                    ModelDao m=new ModelDao();
                    m.deletemodelsofproduct(id);
                    demo.delete(id);
                    RequestDispatcher rd=request.getRequestDispatcher("ProductList.jsp");  
                    rd.forward(request, response);
                } catch (ClassNotFoundException | SQLException ex) 
                {
                    Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
                }
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

                    Productmaster v=new Productmaster();
                    int id=Integer.parseInt(request.getParameter("id"));
                     
                    v.setProdId(id);
                    v.setProdName(request.getParameter("pro_name"));
                    String pro_name=request.getParameter("pro_name");
                     
                    v.setProdDescription(request.getParameter("pro_desc"));
                     
                    String[] modelid=request.getParameterValues("modelid");
                    String[] suppliername=request.getParameterValues("suppliername");
                    String[] modelname=request.getParameterValues("modelname");
                    String[] modeldesc=request.getParameterValues("modeldesc");
                  
                    ProductDao pdt=new ProductDao();
                    ModelDao mdl=new ModelDao();
                    try 
                    {
                          if(id==0)
                          {
                              int result=pdt.save(v);
                              int prod_id=pdt.getid(pro_name);
                              if(modelname!=null)
                              {
                                int modelresult=mdl.saveall(suppliername, modelname, modeldesc,prod_id);
                              }
                              RequestDispatcher rd=request.getRequestDispatcher("ProductList.jsp");  
                              rd.forward(request, response);
                          }
                          else
                          { 
                              pdt.update(v);
                              int prod_id=pdt.getid(pro_name);
                              if(modelname!=null)
                              {
                                int modelresult=mdl.updateall(modelid, suppliername, modelname, modeldesc,prod_id);
                              }
                              RequestDispatcher rd=request.getRequestDispatcher("ProductList.jsp");  
                              rd.forward(request, response);
                          }
                     }
                    catch (ClassNotFoundException | SQLException ex) 
                    {
                         Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
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
