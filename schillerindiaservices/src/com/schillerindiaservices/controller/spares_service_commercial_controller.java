/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.EmployeeDao;
import com.schillerindiaservices.Dao.NonSaleDao;
import com.schillerindiaservices.Dao.PendingActivityDao;
import com.schillerindiaservices.Dao.ProductDao;
import com.schillerindiaservices.Dao.Spares_dao;
import com.schillerindiaservices.bean.Nonsaleablemaster;
import com.schillerindiaservices.bean.Pendingactmaster;
import com.schillerindiaservices.bean.Sparemaster;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ShineLoGics
 */
@WebServlet("/spares_service_commercial_controller")
public class spares_service_commercial_controller extends HttpServlet {

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
            out.println("<title>Servlet NonSaleControler</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NonSaleControler at " + request.getContextPath() + "</h1>");
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
        try{
             HttpSession session=request.getSession();
                String role=(String) session.getAttribute("logrole");
        int id=Integer.parseInt(request.getParameter("id"));
        String action=request.getParameter("action");
        String pg=request.getParameter("pg");
        Spares_dao nsd=new Spares_dao();
        if(action!=null)
        {
            if(action.equalsIgnoreCase("edit"))
           {
            	Sparemaster nsb=Spares_dao.getById(id);
                request.setAttribute("nonsale", nsb);
               if(role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("ViceChancellor"))
               {
                    RequestDispatcher rd=request.getRequestDispatcher("Spares_request_update.jsp");
                rd.forward(request, response);
               }
               else{
                RequestDispatcher rd=request.getRequestDispatcher("Spares_request_update.jsp");
                rd.forward(request, response);
               }
           }
           else
           {
              nsd.delete(id);
              if(pg.equalsIgnoreCase("sl"))
                   {
                       RequestDispatcher rd=request.getRequestDispatcher("spareslist_engg.jsp");
                rd.forward(request, response);
                   }
              RequestDispatcher rd=request.getRequestDispatcher("spareslist_engg.jsp");
              rd.include(request, response);
           }
            
        }
        }
       catch (ClassNotFoundException ex) {
                   Logger.getLogger(PendingActivityController.class.getName()).log(Level.SEVERE, null, ex);
               } catch (SQLException ex) {
                   Logger.getLogger(PendingActivityController.class.getName()).log(Level.SEVERE, null, ex);
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
    	
    	  String division1="";
          String sc_engg1="";
          String region1="";
          String divid1="";
    	
          LocalDate lDate=LocalDate.now();
       Nonsaleablemaster nsm=new Nonsaleablemaster();
          Sparemaster sp=new Sparemaster();
       NonSaleDao nsd=new NonSaleDao();
       Spares_dao sd=new Spares_dao();
       int id=Integer.parseInt(request.getParameter("id"));
       System.out.println("the id idssssss"+id);
        HttpSession session=request.getSession();
                String role=(String) session.getAttribute("logrole");
                int lid=Integer.parseInt(session.getAttribute("loguserid").toString());
                System.out.println("the print ln iss"+division1);
                String eid=String.valueOf(lid);
                try {
					divid1=ProductDao.getDiviName(division1);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                System.out.println("the print ln iss 22"+divid1);
                try {
					sc_engg1=EmployeeDao.geteName(eid);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
               System.out.println("sc_engg"+sc_engg1);
       if(id==0)
       {
           try {
               String division=request.getParameter("division");
               String sc_engg=sc_engg1;
               System.out.println(sc_engg+"sc_enggg in controller");
              // String unit_details=request.getParameter("unit_details");
               String entry_date=request.getParameter("fqc_inward_date");
             //  String region=request.getParameter("region");
             //  String branch=request.getParameter("branch");
             //  String engineer=request.getParameter("engineer");
             //  String dealer=request.getParameter("dealer");
               String supplier=request.getParameter("supplier");
               String model=request.getParameter("model");
               String part_number=request.getParameter("model_sn");
               String def_mod_brd_name=request.getParameter("unit_config");
               String reference=request.getParameter("cust_name");
               String reason=request.getParameter("reason");
               String gir_number=request.getParameter("gir_number");
               String issued_by=request.getParameter("issued_by");
               String return_status=request.getParameter("return_status");
               String remarks=request.getParameter("fqc_observation");
               String return_date_time=request.getParameter("defect_unit_recv_date");
               //String defect_unit_recv_date=request.getParameter("defect_unit_recv_date");
              /* String fqc_observation=request.getParameter("fqc_observation");
               String sc_in_date=request.getParameter("sc_in_date");
               String sc_observation=request.getParameter("sc_observation");
               String req_parts=request.getParameter("req_parts");
               String root_cause=request.getParameter("root_cause");
               String action_plan=request.getParameter("action_plan");
               String ship_date_fqc=request.getParameter("ship_date_fqc");
               String ship_date_fqc2=request.getParameter("ship_date_fqc2");
               String fqc_final_remarks=request.getParameter("fqc_final_remarks");*/
               String status=request.getParameter("status");
               String qty=request.getParameter("qty");
               
               sp.setDivision(division);
               sp.setSc_engg(sc_engg);
               sp.setSupplier(supplier);
               sp.setSc_engg(sc_engg);
               sp.setPartNumber(part_number);
               sp.setDef_Mod_Brd_name(def_mod_brd_name);
               sp.setReason(reason);
               sp.setReference(reference);
               sp.setSupplier(supplier);
               sp.setModel(model);
               sp.setGir_no(gir_number);
               sp.setIssued_by(issued_by);
               sp.setRemarks(remarks);
               sp.setReturnable_status(return_status);
               sp.setFinalStatus(status);
               sp.setQty(qty);
               
               
              
               
               DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
               java.util.Date dateobj = new java.util.Date();
               String  current_date=df.format(dateobj);
              // Date dt1=df.parse(dateobj);
               sp.setEntryDate(current_date);
               
               sd.Insert(sp);
               if(role.equalsIgnoreCase("FQC"))
               {
                   response.sendRedirect("spareslist_engg.jsp");
               }
               else
               {
               response.sendRedirect("spareslist_engg.jsp");
               }
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(spares_service_commercial_controller.class.getName()).log(Level.SEVERE, null, ex);
           } catch (SQLException ex) {
               Logger.getLogger(spares_service_commercial_controller.class.getName()).log(Level.SEVERE, null, ex);
           }
           
       }
       else
       {
           try {
        	   String division=request.getParameter("division");
               String sc_engg=sc_engg1;
               System.out.println(sc_engg+"sc_enggg in controller");
              // String unit_details=request.getParameter("unit_details");
               String entry_date=request.getParameter("fqc_inward_date");
             //  String region=request.getParameter("region");
             //  String branch=request.getParameter("branch");
             //  String engineer=request.getParameter("engineer");
             //  String dealer=request.getParameter("dealer");
               String supplier=request.getParameter("supplier");
               String model=request.getParameter("model");
               String part_number=request.getParameter("model_sn");
               String def_mod_brd_name=request.getParameter("unit_config");
               String reference=request.getParameter("cust_name");
               String reason=request.getParameter("reason");
               String gir_number=request.getParameter("gir_number");
               String issued_by=request.getParameter("issued_by");
               String return_status=request.getParameter("return_status");
               String remarks=request.getParameter("fqc_observation");
               String return_date_time=request.getParameter("defect_unit_recv_date"); 
               System.out.println(" rdat iss "+return_date_time);
               //String defect_unit_recv_date=request.getParameter("defect_unit_recv_date");
              /* String fqc_observation=request.getParameter("fqc_observation");
               String sc_in_date=request.getParameter("sc_in_date");
               String sc_observation=request.getParameter("sc_observation");
               String req_parts=request.getParameter("req_parts");
               String root_cause=request.getParameter("root_cause");
               String action_plan=request.getParameter("action_plan");
               String ship_date_fqc=request.getParameter("ship_date_fqc");
               String ship_date_fqc2=request.getParameter("ship_date_fqc2");
               String fqc_final_remarks=request.getParameter("fqc_final_remarks");*/
               String status=request.getParameter("status");
               String qty=request.getParameter("qty");
               
               //Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(return_date_time);  
               sp.setDivision(division);
               sp.setEntryDate(entry_date);
               sp.setSc_engg(sc_engg);
               sp.setSupplier(supplier);
               sp.setSc_engg(sc_engg);
               sp.setPartNumber(part_number);
               sp.setDef_Mod_Brd_name(def_mod_brd_name);
               sp.setReason(reason);
               sp.setReference(reference);
               sp.setSupplier(supplier);
               sp.setModel(model);
               sp.setGir_no(gir_number);
               sp.setIssued_by(issued_by);
               sp.setRemarks(remarks);
               sp.setReturnable_status(return_status);
               sp.setFinalStatus(status);
               sp.setReturned_date(return_date_time);
               sp.setQty(qty);
               /*if(cust_name!=null)
               {
                        nsm.setCustName(cust_name);
                        nsm.setReplacedUnitSN(repalce_unit_sn);
                        nsm.setReplaceShipDate(replace_ship_date);
                        nsm.setDefectUnitRecivedDate(def_unit_recv_date);
               }else{
                        nsm.setCustName("NIL");
                        nsm.setReplacedUnitSN("NIL");
                        nsm.setReplaceShipDate("NIL");
                        nsm.setDefectUnitRecivedDate("NIL");
               }
               nsm.setFqcObservation(fqc_observ);
               
               nsm.setScEngg(sc_engg);
               nsm.setScInwardDate(sc_in_date);
               nsm.setScObservation(sc_observation);
               nsm.setRequiredParts(req_parts);
               nsm.setActionPlan(action_plan);
               nsm.setRootCause(root_cause);
               nsm.setTentDateShipDate(ship_date_fqc);
               nsm.setFinalStatus(status);*/
               sp.setId(id);
               sd.update(sp);
               
               
     
       
               if(role.equalsIgnoreCase("engineer"))
               {
                   response.sendRedirect("spareslist_engg.jsp");
               }
               else if(role.equalsIgnoreCase("ServiceCoordinator"))
               {
                   response.sendRedirect("spareslist_update.jsp");
               }
               else 
               {
                   response.sendRedirect("spareslist_update.jsp");
               }
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(spares_service_commercial_controller.class.getName()).log(Level.SEVERE, null, ex);
           } catch (SQLException ex) {
               Logger.getLogger(spares_service_commercial_controller.class.getName()).log(Level.SEVERE, null, ex);
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
