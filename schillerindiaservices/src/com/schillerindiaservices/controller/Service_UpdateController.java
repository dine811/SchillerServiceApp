/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.EmployeeDao;
import com.schillerindiaservices.Dao.ServiceDao;
import com.schillerindiaservices.bean.service_master;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ShineLoGics
 */
public class Service_UpdateController extends HttpServlet {

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
            out.println("<title>Servlet Service_UpdateController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Service_UpdateController at " + request.getContextPath() + "</h1>");
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
        try {
            service_master sm=new service_master();
            ServiceDao sd=new ServiceDao();
            
            int id=Integer.parseInt(request.getParameter("id"));
//            String ra_engg=request.getParameter("ra_engineer");
//            int ra_engineer=EmployeeDao.getEmpId(ra_engg);
            String ra_engineer=request.getParameter("ra_engineer");
            String docket_no=request.getParameter("doc_no");
            String def_unit_gir_no=request.getParameter("def_unit_gir_no");
            String tech_remarks=request.getParameter("tech_remarks");
            String invoice=request.getParameter("invoice");
            String comp_repair=request.getParameter("comp_repair");
            String repair_stk_date=request.getParameter("repair_stk_date");
            String final_remarks=request.getParameter("final_remarks");
            String type_work=request.getParameter("type_work");
            String ship_date=request.getParameter("ship_date");
            String disp_advice_no=request.getParameter("disp_advice_no");
            String commercial_date=request.getParameter("commercial_date");
            String dc_no=request.getParameter("dc_no");
            String repairedBrdAdvNo=request.getParameter("repaired_brd_adv_no");  
            String rep_gir_no=request.getParameter("rep_gir_sno");
            String disp_date=request.getParameter("desp_frm_date");
            String report_type=request.getParameter("report_type");
            String destination=request.getParameter("destination");
            System.out.println("destination issdnakscnaskld"+destination);
            sm.setSerId(id);
            sm.setDespatchDate(disp_date);
            sm.setRaEngnr(Integer.parseInt(ra_engineer));
            sm.setDocketNo(docket_no);
            sm.setDefUnitGirNo(def_unit_gir_no);
            sm.setTechnicalRemarks(tech_remarks);
            sm.setComrclDtlInvRmrk(invoice);
            sm.setComponentsUsedforRepair(comp_repair);
            sm.setRepairedBrdStkDate(repair_stk_date);
            sm.setRepairedBrdAdvNo(repairedBrdAdvNo);
            sm.setRepGirNo(rep_gir_no);
            sm.setReport_type(report_type);
            
             String str=repair_stk_date;
            if(str.isEmpty()||str==null) 
           {
            
           } 
        else
           {
            sm.setYear(str.substring(6,10));
            sm.setMonth(str.substring(3,5));
            }
            sm.setFinalRemarks(final_remarks);
            sm.setTypeOfWork(type_work);
            sm.setShipDtFrmSerCntr(ship_date);
             sm.setShipDateFromCommercial(commercial_date);
            sm.setDispAdvNo(disp_advice_no);
            sm.setDcNo(dc_no);
            sm.setDestination(destination);
            sd.updateService(sm);
            
            HttpSession session = request.getSession(true);
             String role=(String)session.getAttribute("logrole");
                if("admin".equalsIgnoreCase(role)||role.equalsIgnoreCase("ViceChancellor"))
                {
                    response.sendRedirect("ServiceList.jsp");
                }
                else
                {
                    response.sendRedirect("ServiceListEngg.jsp");
                }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Service_UpdateController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Service_UpdateController.class.getName()).log(Level.SEVERE, null, ex);
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
