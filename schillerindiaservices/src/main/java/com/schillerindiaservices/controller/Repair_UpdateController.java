/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.PendingActivityDao;
import com.schillerindiaservices.Dao.RepairActivityDao;
import com.schillerindiaservices.bean.Pendingactmaster;
import com.schillerindiaservices.bean.Repair_master;
import com.schillerindiaservices.bean.service_master;
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
public class Repair_UpdateController extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
            out.println("<title>Servlet PendingActivityController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PendingActivityController at " + request.getContextPath() + "</h1>");
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
       String category=request.getParameter("cat");
       PendingActivityDao pact=new PendingActivityDao();
       if(action!=null)
       {
           if(action.equalsIgnoreCase("edit"))
           {
                   Repair_master pm=RepairActivityDao.getById(id);
                   request.setAttribute("pendingrecord", pm);
                   request.setAttribute("category", category);
                RequestDispatcher rd=request.getRequestDispatcher("RepairActUpdate.jsp");
                rd.forward(request, response);
           }
           else
           {
              pact.delete(id);
               RequestDispatcher rd=request.getRequestDispatcher("PendingActListAdmin.jsp");
                rd.forward(request, response);
           }
       }
         } catch (ClassNotFoundException ex) {
                   Logger.getLogger(Repair_UpdateController.class.getName()).log(Level.SEVERE, null, ex);
               } catch (SQLException ex) {
                   Logger.getLogger(Repair_UpdateController.class.getName()).log(Level.SEVERE, null, ex);
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
       Pendingactmaster pam=new Pendingactmaster();
       Repair_master rem=new Repair_master();
       PendingActivityDao pad=new PendingActivityDao();
       RepairActivityDao rad = new RepairActivityDao();
       UtilFunctions utilfn = new UtilFunctions();
       service_master pm = new service_master();
               HttpSession session=request.getSession();
               String role =(String) session.getAttribute("logrole");
       int id=Integer.parseInt(request.getParameter("id"));
//       for insert
       if(id==0)
       {
    	   System.out.println("exception 111");
           Logger.getLogger(Repair_UpdateController.class.getName()).log(Level.SEVERE, "id not valid!");;
           response.sendRedirect("error2.jsp");
       }
       else
       {
           try {
        	   String category=request.getParameter("cat");
               String division=request.getParameter("division");
               String entrydate=request.getParameter("initiated_Date");
               String status=request.getParameter("status");
               String sc_inchrg_remarks=request.getParameter("sc_ref_no");
               String tar_closed_date=request.getParameter("def_gir_no");
               String initiated_Date =request.getParameter("Def_brd_mod_name");
               String model=request.getParameter("model");
               String repaired_by=request.getParameter("repaired_by");
               String comp_used_to_repair=request.getParameter("comp_used_to_repair");
               String tech_remarks=request.getParameter("remarks");
               String final_remarks=request.getParameter("sc_inchrg_remarks");
               String ser_id=request.getParameter("ser_id");
               
               
               rem.setComp_used_to_repair(comp_used_to_repair);
               rem.setRepaired_by(repaired_by);
               rem.setTech_remarks(tech_remarks);
               rem.setFinal_remarks(final_remarks);
               rem.setStatusType(status);
               rem.setId(id);
               
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
               java.util.Date dateobj = new java.util.Date();
               String  closed_Date=df.format(dateobj);
             
               
               
               String strdate = entrydate;
               
             
               java.util.Date startDate = null;
               java.util.Date currentdate = null;
               try {
				startDate = df.parse(strdate);
				currentdate=df.parse(closed_Date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
               
               
               
             
               java.util.Date d1 = null;
               java.util.Date d2 = null;
                    d1 = startDate;
                    d2 = currentdate;
                    
                   
                    
               long diff = d2.getTime() - d1.getTime();
               long diffDays = diff / (24 * 60 * 60 * 1000);
               System.out.println("diff dayssss   "+diffDays);
               rem.setNo_of_days((int)diffDays);
               String comp="";
               if(status.equalsIgnoreCase("completed"))
               {
                   
                    rem.setFinished_date(closed_Date);
                  id =   rad.update(rem);
                  if(id != 0) {
                	  System.out.println();
                	  pm.setSerId(Integer.parseInt(ser_id));
                	  pm.setRepair_status("RC");
                	  pm.setComponentsUsedforRepair(comp_used_to_repair);
                	  pm.setReport_type("repairlist");
                	  rad.update_repairStatus(pm);
                	  
                	  System.out.println("service master updated");
                  }
               }
               else
               {
            	   rem.setFinished_date(null);
            	   id =   rad.update(rem);
                   if(id != 0) {
                 	  pm.setSerId(Integer.parseInt(ser_id));
                 	  pm.setRepair_status("RP");
                 rad.update_repairStatus(pm);
                 	  System.out.println("service master updated");
                   }
               }
               
           switch (category) {
		case "UR":
			 response.sendRedirect("repairList_UR.jsp");
			break;
		case "PFRN":
			 response.sendRedirect("repairList.jsp");
			break;
		case "OB":
			 response.sendRedirect("repairList_OB.jsp");
			break;

		default:
			 response.sendRedirect("repairList.jsp");
			break;
		}
           } catch (ClassNotFoundException ex) {
        	   System.out.println("exception 111");
               Logger.getLogger(Repair_UpdateController.class.getName()).log(Level.SEVERE, null, ex);
               response.sendRedirect("error2.jsp");
           } catch (SQLException ex) {
        	   System.out.println("exception 222");
               Logger.getLogger(Repair_UpdateController.class.getName()).log(Level.SEVERE, null, ex);
               response.sendRedirect("error2.jsp");
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
