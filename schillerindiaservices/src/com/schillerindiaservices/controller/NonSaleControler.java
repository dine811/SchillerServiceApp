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
import com.schillerindiaservices.bean.Nonsaleablemaster;
import com.schillerindiaservices.bean.Pendingactmaster;
import com.schillerindiaservices.common.UtilFunctions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class NonSaleControler extends HttpServlet {

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
        NonSaleDao nsd=new NonSaleDao();
        if(action!=null)
        {
            if(action.equalsIgnoreCase("edit"))
           {
                   Nonsaleablemaster nsb=NonSaleDao.getById(id);
                request.setAttribute("nonsale", nsb);
               if(role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("ViceChancellor"))
               {
                    RequestDispatcher rd=request.getRequestDispatcher("nonSaleAdminUpdate.jsp");
                rd.forward(request, response);
               }
               else{
                RequestDispatcher rd=request.getRequestDispatcher("nonSaleUpdate.jsp");
                rd.forward(request, response);
               }
           }
           else
           {
              nsd.delete(id);
              if(pg.equalsIgnoreCase("sl"))
                   {
                       RequestDispatcher rd=request.getRequestDispatcher("SalablesList.jsp");
                rd.forward(request, response);
                   }
              RequestDispatcher rd=request.getRequestDispatcher("nonSaleAdminList.jsp");
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
    	UtilFunctions utilfn = new UtilFunctions();
       Nonsaleablemaster nsm=new Nonsaleablemaster();
       NonSaleDao nsd=new NonSaleDao();
       int id=Integer.parseInt(request.getParameter("id"));
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
               String unit_details=request.getParameter("unit_details");
               String fqc_Inward_date=request.getParameter("fqc_inward_date");
               String region=request.getParameter("region");
               String branch=request.getParameter("branch");
               String engineer=request.getParameter("engineer");
               String dealer=request.getParameter("dealer");
               String supplier=request.getParameter("supplier");
               String model=request.getParameter("model");
               String model_sn=request.getParameter("model_sn");
               String unit_config=request.getParameter("unit_config");
               String cust_name=request.getParameter("cust_name");
               String report_problm=request.getParameter("report_problm");
               String replace_unit=request.getParameter("replace_unit");
               String replace_ship_date=request.getParameter("replace_ship_date");
               String defect_unit_recv_date=request.getParameter("defect_unit_recv_date");
               String fqc_observation=request.getParameter("fqc_observation");
               String sc_in_date=request.getParameter("sc_in_date");
               String sc_observation=request.getParameter("sc_observation");
               String req_parts=request.getParameter("req_parts");
               String root_cause=request.getParameter("root_cause");
               String action_plan=request.getParameter("action_plan");
               String ship_date_fqc=request.getParameter("ship_date_fqc");
               String ship_date_fqc2=request.getParameter("ship_date_fqc2");
               String fqc_final_remarks=request.getParameter("fqc_final_remarks");
               String status=request.getParameter("status");
               
               
               fqc_Inward_date =  utilfn.getRev_DateFormat(fqc_Inward_date);
               sc_in_date =  utilfn.getRev_DateFormat(sc_in_date);
               ship_date_fqc =  utilfn.getRev_DateFormat(ship_date_fqc);
               ship_date_fqc2 =  utilfn.getRev_DateFormat(ship_date_fqc2);
               
               System.out.println("fqc_Inward_date ---  "+fqc_Inward_date);
               System.out.println("sc_in_date ---  "+sc_in_date);
               System.out.println("ship_date_fqc ---  "+ship_date_fqc);
               System.out.println("ship_date_fqc2 ---  "+ship_date_fqc2);
             //  System.out.println("current_date ---  "+current_date);
              // System.out.println("current_date ---  "+current_date);
               
               
               
               nsm.setDivision(division);
               nsm.setScEngg(sc_engg);
               nsm.setUnitDetails(unit_details);
               nsm.setFqcInDate(fqc_Inward_date);
               nsm.setRegion(region);
               nsm.setBranch(branch);
               nsm.setEngineer(engineer);
               nsm.setDealer(dealer);
               nsm.setSupplier(supplier);
               nsm.setModel(model);
               nsm.setModelSN(model_sn);
               nsm.setUnitConfig(unit_config);
               nsm.setReportedProblm(report_problm);
               if(cust_name!=null)
               {
            	   
            	   fqc_Inward_date =  utilfn.getRev_DateFormat(fqc_Inward_date);
                        nsm.setCustName(cust_name);
                        nsm.setReplacedUnitSN(replace_unit);
                        nsm.setReplaceShipDate(utilfn.getRev_DateFormat(replace_ship_date));
                        nsm.setDefectUnitRecivedDate(utilfn.getRev_DateFormat(defect_unit_recv_date));
               }else{
                        nsm.setCustName("NIL");
                        nsm.setReplacedUnitSN("NIL");
                        nsm.setReplaceShipDate("NIL");
                        nsm.setDefectUnitRecivedDate("NIL");
               }
              
               
               nsm.setFqcObservation(fqc_observation);
               nsm.setScInwardDate(sc_in_date);
               nsm.setScObservation(sc_observation);
               nsm.setRequiredParts(req_parts);
               nsm.setRootCause(root_cause);
               nsm.setActionPlan(action_plan);
               nsm.setTentDateShipDate(ship_date_fqc);
               nsm.setShipDateFqc(ship_date_fqc2);
               nsm.setFqcFinalRemark(fqc_final_remarks);
               nsm.setFinalStatus(status);
               
               DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
               java.util.Date dateobj = new java.util.Date();
               String  current_date=df.format(dateobj);
               System.out.println("current_date ---  "+current_date);
               nsm.setEntryDate(current_date);
               
               nsd.Insert(nsm);
               if(role.equalsIgnoreCase("FQC"))
               {
                   response.sendRedirect("nonSaleAdminList.jsp");
               }
               else
               {
               response.sendRedirect("nonsaleEnggList.jsp");
               }
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(NonSaleControler.class.getName()).log(Level.SEVERE, null, ex);
           } catch (SQLException ex) {
               Logger.getLogger(NonSaleControler.class.getName()).log(Level.SEVERE, null, ex);
           }catch (ParseException e) {
               Logger.getLogger(NonSaleControler.class.getName()).log(Level.SEVERE, null, ex);

			// TODO: handle exception
		}
           
       }
       else
       {
           try {
               String unit_details=request.getParameter("unit_details");
               String fqc_in_date=request.getParameter("fqc_in_date");
               String modelsn=request.getParameter("modelsn");
               String unit_configur=request.getParameter("unit_configur");
               String cust_name=request.getParameter("cust_name");
               String report_prblm=request.getParameter("report_prblm");
               String repalce_unit_sn=request.getParameter("repalce_unit_sn");
               String replace_ship_date=request.getParameter("replace_ship_date");
               String def_unit_recv_date=request.getParameter("def_unit_recv_date");
               String fqc_observ=request.getParameter("fqc_observ");
               
               String sc_in_date=request.getParameter("sc_in_date");
               String sc_observation=request.getParameter("sc_observation");
               String req_parts=request.getParameter("req_parts");
               String root_cause=request.getParameter("root_cause");
               String action_plan=request.getParameter("action_plan");
               String ship_date_fqc=request.getParameter("ship_date_fqc");
               String status=request.getParameter("status");
                String sc_engg=request.getParameter("sc_engg");
                String ship_date_fqc2=request.getParameter("ship_date_fqc2");
                String fqc_final_remarks=request.getParameter("fqc_final_remarks");
               
                nsm.setShipDateFqc(ship_date_fqc2);
                nsm.setFqcFinalRemark(fqc_final_remarks);
               nsm.setUnitDetails(unit_details);
               nsm.setFqcInDate(fqc_in_date);
               nsm.setModelSN(modelsn);
               nsm.setUnitConfig(unit_configur);
               nsm.setReportedProblm(report_prblm);
               
               if(cust_name!=null)
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
               nsm.setFinalStatus(status);
               nsm.setId(id);
               nsd.update(nsm);
               
               
     
       
               if(role.equalsIgnoreCase("engineer"))
               {
                   response.sendRedirect("nonsaleEnggList.jsp");
               }
               else if(role.equalsIgnoreCase("fqc"))
               {
                   response.sendRedirect("nonSaleAdminList.jsp");
               }
               else 
               {
                   response.sendRedirect("nonSaleAdminList.jsp");
               }
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(NonSaleControler.class.getName()).log(Level.SEVERE, null, ex);
           } catch (SQLException ex) {
               Logger.getLogger(NonSaleControler.class.getName()).log(Level.SEVERE, null, ex);
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
