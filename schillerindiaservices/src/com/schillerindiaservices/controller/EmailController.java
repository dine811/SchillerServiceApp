/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.EmailDao;
import com.schillerindiaservices.bean.Email;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author ShineLoGics
 */
public class EmailController extends HttpServlet {

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
            out.println("<title>Servlet EmailController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmailController at " + request.getContextPath() + "</h1>");
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
        try {
        int id=Integer.parseInt(request.getParameter("id"));
        
        String action=request.getParameter("action");
        EmailDao ed=new EmailDao();
        if(action!=null)
        {
        if(action.equalsIgnoreCase("edit"))
        {
            
                Email mail=EmailDao.getById(id);
                request.setAttribute("mail", mail);
                RequestDispatcher rd=request.getRequestDispatcher("EmailForm.jsp");
                rd.forward(request, response);
            } 
            } 
            
        }
        catch (ClassNotFoundException ex) {
                Logger.getLogger(EmailController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(EmailController.class.getName()).log(Level.SEVERE, null, ex);
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
            Email em=new Email();
            EmailDao ed=new EmailDao();
            int id=Integer.parseInt(request.getParameter("id"));
            String mail=request.getParameter("mailid");
            String name=request.getParameter("name");
            String smail=request.getParameter("smail");
            String password=request.getParameter("password");
            String port=request.getParameter("port");
            String ssl=request.getParameter("ssl");
            String msgbody=request.getParameter("msgbody");
            String hos=request.getParameter("host");
            
            HttpSession session=request.getSession();
            em.setMsgBody(msgbody);
            em.setMailid(mail);
            em.setName(name);
            em.setId(id);
            em.setSemail(smail);
            em.setPasswordF(password);
            em.setPortNo(port);
            em.setSslNo(ssl);
            em.setHosingId(hos);
            
            ed.update(em);
            response.sendRedirect("EmailSettings.jsp");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmailController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EmailController.class.getName()).log(Level.SEVERE, null, ex);
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
