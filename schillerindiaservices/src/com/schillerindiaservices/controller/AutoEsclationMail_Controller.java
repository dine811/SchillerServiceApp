/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.bean.Dealermaster;
import com.schillerindiaservices.bean.Mail_id_entry;
import com.schillerindiaservices.Dao.AutoEsclationDao;
import com.schillerindiaservices.Dao.DealerDao;
import com.schillerindiaservices.Dao.RegionDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kavin_rkz
 */
@WebServlet("/AutoEsclationMail_Controller")
public class AutoEsclationMail_Controller extends HttpServlet {

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
        System.out.println("insidee automail edit contyrollerr");
            int id=Integer.parseInt(request.getParameter("id"));
            String action=request.getParameter("action");
            System.out.println("the action isss"+action);
            AutoEsclationDao demo=new AutoEsclationDao();
            if(action!=null)
            {
                if(action.equalsIgnoreCase("edit"))
                { 
                    try {
                        Mail_id_entry d=  AutoEsclationDao.getById(id);
                        request.setAttribute("editablerecord", d);
                        RequestDispatcher rd=request.getRequestDispatcher("AutoEsclationmail_Entry.jsp");
                        rd.forward(request, response);
                    } catch (SQLException ex) {
                        Logger.getLogger(AutoEsclationMail_Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    try 
                    {
                        demo.delete(id);
                        RequestDispatcher rd=request.getRequestDispatcher("Automail_id_list.jsp");  
                        rd.forward(request, response);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(AutoEsclationMail_Controller.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(AutoEsclationMail_Controller.class.getName()).log(Level.SEVERE, null, ex);
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
        System.out.println("insidee the automail controller");
        
            Mail_id_entry delmas=new Mail_id_entry();
            
            String email=request.getParameter("email");
            String report_type=request.getParameter("report_type");
            String division=request.getParameter("division");
          //  String mobile=request.getParameter("del_mob");
           // String regionname=(request.getParameter("del_reg"));
          
            try
            {
           // int region=RegionDao.getRegionId1(regionname);
            int id=Integer.parseInt(request.getParameter("id"));

            delmas.setMail_id(email);;
            delmas.setReport_type(report_type);
            delmas.setTemp1(division);
           /* delmas.setDealerMail(email);
            delmas.setDealerPhone(mobile);
            delmas.setDealerRegion(region);*/

            AutoEsclationDao dd=new AutoEsclationDao();

            
                    if(id==0)
                    {
                        int status;
                        status = dd.save(delmas);
                        RequestDispatcher rd=request.getRequestDispatcher("Automail_id_list.jsp");
                        rd.forward(request, response);
                    }
                    else
                    {
                        delmas.setMail_id_entry_id(id);
                        dd.update(delmas);
                        RequestDispatcher rd=request.getRequestDispatcher("Automail_id_list.jsp");  
                        rd.forward(request, response);
                    }
            }
            catch(SQLException ex)
            {
               ex.printStackTrace();
            } catch (ClassNotFoundException ex) 
            {
              Logger.getLogger(AutoEsclationMail_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}



