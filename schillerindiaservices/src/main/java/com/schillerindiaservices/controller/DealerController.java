/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.bean.Dealermaster;
import com.schillerindiaservices.Dao.DealerDao;
import com.schillerindiaservices.Dao.RegionDao;
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
 * @author Kavin_rkz
 */
public class DealerController extends HttpServlet {

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
            out.println("<title>Servlet DealerController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DealerController at " + request.getContextPath() + "</h1>");
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
            DealerDao demo=new DealerDao();
            if(action!=null)
            {
                if(action.equalsIgnoreCase("edit"))
                { 
                    try {
                        Dealermaster d=  DealerDao.getById(id);
                        request.setAttribute("editablerecord", d);
                        RequestDispatcher rd=request.getRequestDispatcher("DealerForm.jsp");
                        rd.forward(request, response);
                    } catch (SQLException ex) {
                        Logger.getLogger(DealerController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    try 
                    {
                        demo.delete(id);
                        RequestDispatcher rd=request.getRequestDispatcher("DealerList.jsp");  
                        rd.forward(request, response);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(DealerController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(DealerController.class.getName()).log(Level.SEVERE, null, ex);
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
        
        
            Dealermaster delmas=new Dealermaster();
            
            String name=request.getParameter("del_name");
            String address=request.getParameter("del_add");
            String email=request.getParameter("del_email");
            String mobile=request.getParameter("del_mob");
            String regionname=(request.getParameter("del_reg"));
          
            try
            {
            int region=RegionDao.getRegionId1(regionname);
            int id=Integer.parseInt(request.getParameter("id"));

            delmas.setDealerName(name);
            delmas.setDealerAddress(address);
            delmas.setDealerMail(email);
            delmas.setDealerPhone(mobile);
            delmas.setDealerRegion(region);

            DealerDao dd=new DealerDao();

            
                    if(id==0)
                    {
                        int status;
                        status = dd.save(delmas);
                        RequestDispatcher rd=request.getRequestDispatcher("DealerList.jsp");
                        rd.forward(request, response);
                    }
                    else
                    {
                        delmas.setDealerId(id);
                        dd.update(delmas);
                        RequestDispatcher rd=request.getRequestDispatcher("DealerList.jsp");  
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
}



