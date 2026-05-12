/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.JobSheetDao;
import com.schillerindiaservices.Dao.ServiceDao;
import com.schillerindiaservices.bean.Jobsheet;
import com.schillerindiaservices.bean.service_master;
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
public class JobSheetController extends HttpServlet {

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
            out.println("<title>Servlet JobSheetController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet JobSheetController at " + request.getContextPath() + "</h1>");
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
        if(action!=null)
        {
            if(action.equalsIgnoreCase("edit"))
            {   
                try {
                    service_master s=  ServiceDao.getById(id);
                    request.setAttribute("editablerecord", s);
                    RequestDispatcher rd=request.getRequestDispatcher("CreateJobSheet.jsp");
                    rd.forward(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(JobSheetController.class.getName()).log(Level.SEVERE, null, ex);
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
    	System.out.println("insideee job sheet controoo");
      int service_id=Integer.parseInt(request.getParameter("id"));
       int id1=Integer.parseInt(request.getParameter("id1"));
       System.out.println(id1+"the id1111 issss");
       String status= request.getParameter("status");
       
       if(id1==0){
        String id=request.getParameter("id");
        
        String enginnerName=request.getParameter("enginnerName");
        String repairDate=request.getParameter("repairDate");
        String observation=request.getParameter("observation");
        String repairActivity=request.getParameter("repairActivity");
        String timeSpent=request.getParameter("timeSpent");
        String remark=request.getParameter("remark");
        String enginnerName1=request.getParameter("enginnerName1");
        String repairDate1=request.getParameter("repairDate1");
        String observation1=request.getParameter("observation1");
        String repairActivity1=request.getParameter("repairActivity1");
        String timeSpent1=request.getParameter("timeSpent1");
        String remark1=request.getParameter("remark1");
        String enginnerName2=request.getParameter("enginnerName2");
        String repairDate2=request.getParameter("repairDate2");
        String observation2=request.getParameter("observation2");
        String repairActivity2=request.getParameter("repairActivity2");
        String timeSpent2=request.getParameter("timeSpent2");
        String remark2=request.getParameter("remark2");
        String enginnerName3=request.getParameter("enginnerName3");
        String repairDate3=request.getParameter("repairDate3");
        String observation3=request.getParameter("observation3");
        String repairActivity3=request.getParameter("repairActivity3");
        String timeSpent3=request.getParameter("timeSpent3");
        String remark3=request.getParameter("remark3");
        String enginnerName4=request.getParameter("enginnerName4");
        String repairDate4=request.getParameter("repairDate4");
        String observation4=request.getParameter("observation4");
        String repairActivity4=request.getParameter("repairActivity4");
        String timeSpent4=request.getParameter("timeSpent4");
        String remark4=request.getParameter("remark4");
        
        Jobsheet js=new Jobsheet();
        js.setRepairDate(repairDate);
        js.setEnginnerName(enginnerName);
        js.setObservation(observation);
        js.setRepairActivity(repairActivity);
        js.setTimeSpent(timeSpent);
        js.setRemark(remark);
        js.setSerId(Integer.parseInt(id));
        js.setRepairDate1(repairDate1);
        js.setEnginnerName1(enginnerName1);
        js.setObservation1(observation1);
        js.setRepairActivity1(repairActivity1);
        js.setTimeSpent1(timeSpent1);
        js.setRemark1(remark1);
        js.setRepairDate2(repairDate2);
        js.setEnginnerName2(enginnerName2);
        js.setObservation2(observation2);
        js.setRepairActivity2(repairActivity2);
        js.setTimeSpent2(timeSpent2);
        js.setRemark2(remark2);
        js.setRepairDate3(repairDate3);
        js.setEnginnerName3(enginnerName3);
        js.setObservation3(observation3);
        js.setRepairActivity3(repairActivity3);
        js.setTimeSpent3(timeSpent3);
        js.setRemark3(remark3);
        js.setRepairDate4(repairDate4);
        js.setEnginnerName4(enginnerName4);
        js.setObservation4(observation4);
        js.setRepairActivity4(repairActivity4);
        js.setTimeSpent4(timeSpent4);
        js.setRemark4(remark4);
       
        JobSheetDao j=new JobSheetDao();
        ServiceDao sd=new ServiceDao();
        try {
            j.save(js);
            sd.updateService_Status(service_id, status);
             response.sendRedirect("JobSheetController?action=edit&id="+id+"");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(JobSheetController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
       
       else
       {
    	   System.out.println("insideee job sheet elseee");
            String id=request.getParameter("id");
        
        String enginnerName=request.getParameter("enginnerName");
        String repairDate=request.getParameter("repairDate");
        String observation=request.getParameter("observation");
        String repairActivity=request.getParameter("repairActivity");
        String timeSpent=request.getParameter("timeSpent");
        String remark=request.getParameter("remark");
        String enginnerName1=request.getParameter("enginnerName1");
        String repairDate1=request.getParameter("repairDate1");
        String observation1=request.getParameter("observation1");
        String repairActivity1=request.getParameter("repairActivity1");
        String timeSpent1=request.getParameter("timeSpent1");
        String remark1=request.getParameter("remark1");
         String enginnerName2=request.getParameter("enginnerName2");
        String repairDate2=request.getParameter("repairDate2");
        String observation2=request.getParameter("observation2");
        String repairActivity2=request.getParameter("repairActivity2");
        String timeSpent2=request.getParameter("timeSpent2");
        String remark2=request.getParameter("remark2");
        String enginnerName3=request.getParameter("enginnerName3");
        String repairDate3=request.getParameter("repairDate3");
        String observation3=request.getParameter("observation3");
        String repairActivity3=request.getParameter("repairActivity3");
        String timeSpent3=request.getParameter("timeSpent3");
        String remark3=request.getParameter("remark3");
        String enginnerName4=request.getParameter("enginnerName4");
        String repairDate4=request.getParameter("repairDate4");
        String observation4=request.getParameter("observation4");
        String repairActivity4=request.getParameter("repairActivity4");
        String timeSpent4=request.getParameter("timeSpent4");
        String remark4=request.getParameter("remark4");
        
        Jobsheet js=new Jobsheet();
        js.setId(id1);
        js.setRepairDate(repairDate);
        js.setEnginnerName(enginnerName);
        js.setObservation(observation);
        js.setRepairActivity(repairActivity);
        js.setTimeSpent(timeSpent);
        js.setRemark(remark);
        js.setRepairDate1(repairDate1);
        js.setEnginnerName1(enginnerName1);
        js.setObservation1(observation1);
        js.setRepairActivity1(repairActivity1);
        js.setTimeSpent1(timeSpent1);
        js.setRemark1(remark1);
        js.setSerId(Integer.parseInt(id));
        js.setRepairDate2(repairDate2);
        js.setEnginnerName2(enginnerName2);
        js.setObservation2(observation2);
        js.setRepairActivity2(repairActivity2);
        js.setTimeSpent2(timeSpent2);
        js.setRemark2(remark2);
        js.setRepairDate3(repairDate3);
        js.setEnginnerName3(enginnerName3);
        js.setObservation3(observation3);
        js.setRepairActivity3(repairActivity3);
        js.setTimeSpent3(timeSpent3);
        js.setRemark3(remark3);
        js.setRepairDate4(repairDate4);
        js.setEnginnerName4(enginnerName4);
        js.setObservation4(observation4);
        js.setRepairActivity4(repairActivity4);
        js.setTimeSpent4(timeSpent4);
        js.setRemark4(remark4);
        JobSheetDao j=new JobSheetDao();
        ServiceDao sd=new ServiceDao();
          try {
              j.update(js);
              sd.updateService_Status(service_id, status);
              
               response.sendRedirect("JobSheetController?action=edit&id="+id+"");
          } catch (ClassNotFoundException | SQLException ex) {
              Logger.getLogger(JobSheetController.class.getName()).log(Level.SEVERE, null, ex);
          }
              
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
