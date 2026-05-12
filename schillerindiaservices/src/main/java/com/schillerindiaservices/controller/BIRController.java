/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.BIRDao;
import com.schillerindiaservices.bean.Birmaster;
import com.schillerindiaservices.common.UtilFunctions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class BIRController extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			/* TODO output your page here. You may use following sample code. */
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet BIRController</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet BIRController at " + request.getContextPath() + "</h1>");
			out.println("</body>");
			out.println("</html>");
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
	// + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String role = (String) session.getAttribute("logrole");
		int id = Integer.parseInt(request.getParameter("id"));
		String action = request.getParameter("action");
		BIRDao bid = new BIRDao();
		if (action != "") {
			if (action.equalsIgnoreCase("edit")) {
				try {
					Birmaster mi = BIRDao.getById(id);
					request.setAttribute("bir", mi);
					if (role.equalsIgnoreCase("admin")) {
						RequestDispatcher rd = request.getRequestDispatcher("BIRAdminUpdate.jsp");
						rd.forward(request, response);
					} else if (role.equalsIgnoreCase("ViceChancellor")) {
						RequestDispatcher rd = request.getRequestDispatcher("BIRAdminUpdate.jsp");
						rd.forward(request, response);
					} else {
						RequestDispatcher rd = request.getRequestDispatcher("BIRUpdate.jsp");
						rd.forward(request, response);
					}
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			} else {
				try {
					bid.delete(id);
					RequestDispatcher rd = request.getRequestDispatcher("BIRAdminList.jsp");
					rd.forward(request, response);
				} catch (ClassNotFoundException ex) {
					Logger.getLogger(BIRController.class.getName()).log(Level.SEVERE, null, ex);
				} catch (SQLException ex) {
					Logger.getLogger(BIRController.class.getName()).log(Level.SEVERE, null, ex);
				}

			}
		}

	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Date d1 = new Date();
		Birmaster bim = new Birmaster();
		BIRDao bid = new BIRDao();
		UtilFunctions uitlfn = new UtilFunctions();

		int id = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		String role = (String) session.getAttribute("logrole");
		if (id == 0) {
			try {
				String division = request.getParameter("division");
				String fqc_inward_date = request.getParameter("fqc_inward_date");
				String model = request.getParameter("model");
				String config = request.getParameter("config");
				String receivd_qty = request.getParameter("receivd_qty");
				String unit_serial_no = request.getParameter("unit_serial_no");
				String invoice_no = request.getParameter("invoice_no");
				String invoice_date = request.getParameter("invoice_date");
				String sftwr_chngs = request.getParameter("sftwr_chngs");
				String sftwr_ver = request.getParameter("sftwr_ver");
				String hrdwr_chang = request.getParameter("hrdwr_chang");
				String hrdwr_details = request.getParameter("hrdwr_details");
				String accesory_chngs = request.getParameter("accesory_chngs");
				String accesory_details = request.getParameter("accesory_details");
				String user_manul_updt = request.getParameter("user_manul_updt");
				String service_manul_updt = request.getParameter("service_manul_updt");
				String fqc_remark = request.getParameter("fqc_remark");
				String cnr_tech_ref_no = request.getParameter("cnr_tech_ref_no");
				String ts_team_verification_date = request.getParameter("ts_team_verification_date");
				String ps_team_verification_date = request.getParameter("ps_team_verification_date");
				String status = request.getParameter("status");
				String accory_change_remark = request.getParameter("accory_change_remark");
				String hrdwr_chang_remark = request.getParameter("hrdwr_chang_remark");
				String sftwr_chang_remark = request.getParameter("sftwr_chang_remark");
				String cnr_relese_date = request.getParameter("cnr_relese_date");
				String bir_ref_no = request.getParameter("bir_ref_no");
				String close = null;
				String supllier = request.getParameter("supllier");
				String ps_engg = request.getParameter("ps_engg");
				String cnr_ref_no = request.getParameter("cnr_ref_no");
				String approved_date = request.getParameter("approved_date");
				String sc_engg = request.getParameter("sc_engg");
				String unit_inward_date = request.getParameter("unit_inward_date");
				String tech_remark = request.getParameter("tech_remarks");
				
				 System.out.println("ts_team_verification_date111 --- >"+ts_team_verification_date+"  ");
                 
				 
				 if(ts_team_verification_date != null) {
					 System.out.println("is nulll");
				 }else {
					 System.out.println("not nulll");
				 }
				 
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date dateobj = new java.util.Date();
				System.out.println("dateobj    --->");
				System.out.println("dateobj    --->" + dateobj);
				String current_date = df.format(dateobj);
				System.out.println("dateobj    --->" + current_date);
                 System.out.println("ts_team_verification_date --- >"+ts_team_verification_date);
				fqc_inward_date = uitlfn.getRev_DateFormat(fqc_inward_date);
				invoice_date = uitlfn.getRev_DateFormat(invoice_date);
				approved_date = uitlfn.getRev_DateFormat(approved_date);
				unit_inward_date = uitlfn.getRev_DateFormat(unit_inward_date);
				cnr_relese_date = uitlfn.getRev_DateFormat(cnr_relese_date);
				ts_team_verification_date = uitlfn.getRev_DateFormat(ts_team_verification_date);
				ps_team_verification_date = uitlfn.getRev_DateFormat(ps_team_verification_date);
				current_date = uitlfn.getRev_DateFormat(current_date);
				

				bim.setDivision(division);
				bim.setScEngg(sc_engg);
				bim.setPsEngg(ps_engg);
				bim.setCnrTecRefNum(cnr_ref_no);
				bim.setFqcInDate(fqc_inward_date);
				bim.setApprovedDate(approved_date);
				bim.setModel(model);
				bim.setSupplier(supllier);
				bim.setConfiguration(config);
				bim.setReceivedQty(receivd_qty);
				bim.setUnitSerialNo(unit_serial_no);
				bim.setInvoiceNo(invoice_no);
				bim.setInvoiceDate(invoice_date);
				bim.setSoftwrChanges(sftwr_chngs);
				bim.setSoftwrVersion(sftwr_ver);
				bim.setHardwrChanges(hrdwr_chang);
				bim.setHardwareDetails(hrdwr_details);
				bim.setAccesoryChanges(accesory_chngs);
				bim.setAccesoryDetails(accesory_details);
				bim.setUserManualUpdate(user_manul_updt);
				bim.setServiceManualUpdate(service_manul_updt);
				bim.setFqcRemarks(fqc_remark);
				bim.setCnrRefNo(cnr_tech_ref_no);
				bim.setTsTeamRemark(ts_team_verification_date);
				bim.setPsTeamRemark(ps_team_verification_date);
				bim.setFinalStatus(status);
				bim.setAccesChngRemark(accory_change_remark);
				bim.setHrdwrChangRemark(hrdwr_chang_remark);
				bim.setSftwrChngRemark(sftwr_chang_remark);
				bim.setCnrReleseDate(cnr_relese_date);
				bim.setBirRefNo(bir_ref_no);
				bim.setUnitInDate(unit_inward_date);
				bim.setTechRemarks(tech_remark);

				

				bim.setEntryDate(current_date);
				System.out.println(" entry dateeeee  "+bim.getEntryDate()+" ");
				if (status.equalsIgnoreCase("completed")) {
					bim.setClosedDate(current_date);
				} else {
					bim.setClosedDate(close);
				}
				bim.setCurrentDate(d1);
				bid.Insert(bim);
				response.sendRedirect("BIRAdminList.jsp");
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(BIRController.class.getName()).log(Level.SEVERE, null, ex);
			} catch (SQLException ex) {
				Logger.getLogger(BIRController.class.getName()).log(Level.SEVERE, null, ex);
			}

		} else {
			try {
				String fqc_inward_date = request.getParameter("fqc_inward_date");
				String config = request.getParameter("config");
				String receivd_qty = request.getParameter("receivd_qty");
				String unit_serial_no = request.getParameter("unit_serial_no");
				String invoice_no = request.getParameter("invoice_no");
				String invoice_date = request.getParameter("invoice_date");
				String sftwr_chngs = request.getParameter("sftwr_chngs");
				String sftwr_ver = request.getParameter("sftwr_ver");
				String accesory_chngs = request.getParameter("accesory_chngs");
				String accesory_details = request.getParameter("accesory_details");
				String user_manul_updt = request.getParameter("user_manul_updt");
				String service_manul_updt = request.getParameter("service_manul_updt");
				String fqc_remark = request.getParameter("fqc_remark");
				String accory_change_remark = request.getParameter("accory_change_remark");
				String hrdwr_chang = request.getParameter("hrdwr_chang");
				String hrdwr_details = request.getParameter("hrdwr_details");
				String hrdwr_chang_remark = request.getParameter("hrdwr_chang_remark");
				String ts_team_verification_date = request.getParameter("ts_team_verification_date");
				String sftwr_chang_remark = request.getParameter("sftwr_chang_remark");
				String cnr_tech_ref_no = request.getParameter("cnr_tech_ref_no");
				String cnr_relese_date = request.getParameter("cnr_relese_date");
				String ps_team_verification_date = request.getParameter("ps_team_verification_date");
				String bir_ref_no = request.getParameter("bir_ref_no");
				String status = request.getParameter("status");

				String supllier = request.getParameter("supllier");
				String ps_engg = request.getParameter("ps_engg");
				String cnr_ref_no = request.getParameter("cnr_ref_no");
				String approved_date = request.getParameter("approved_date");
				String sc_engg = request.getParameter("sc_engg");
				String unit_inward_date = request.getParameter("unit_inward_date");
				String tech_remark = request.getParameter("tech_remarks");

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date dateobj = new java.util.Date();
				String current_date = df.format(dateobj);
                 System.out.println(" fqc inward 1st------>  "+fqc_inward_date);
				//fqc_inward_date = uitlfn.getRev_DateFormat(fqc_inward_date);
				System.out.println(" fqc inward 2nd------>  "+fqc_inward_date);
			//	invoice_date = uitlfn.getRev_DateFormat(invoice_date);
			//	approved_date = uitlfn.getRev_DateFormat(approved_date);
			//	unit_inward_date = uitlfn.getRev_DateFormat(unit_inward_date);
			//	cnr_relese_date = uitlfn.getRev_DateFormat(cnr_relese_date);
			//	ts_team_verification_date = uitlfn.getRev_DateFormat(ts_team_verification_date);
			//	ps_team_verification_date = uitlfn.getRev_DateFormat(ps_team_verification_date);
			//	current_date = uitlfn.getRev_DateFormat(current_date);

				if (status.equalsIgnoreCase("completed")) {

					bim.setClosedDate(current_date);
				}

				bim.setSupplier(supllier);
				bim.setPsEngg(ps_engg);

				if (status.equalsIgnoreCase("pending")) {
					String appdate = null;
					bim.setApprovedDate(appdate);
				} else {
					
					bim.setApprovedDate(approved_date);
				}

				bim.setScEngg(sc_engg);

				bim.setFqcInDate(fqc_inward_date);
				bim.setConfiguration(config);
				bim.setReceivedQty(receivd_qty);
				bim.setUnitSerialNo(unit_serial_no);
				bim.setInvoiceNo(invoice_no);
				bim.setInvoiceDate(invoice_date);
				bim.setSoftwrChanges(sftwr_chngs);
				bim.setSoftwrVersion(sftwr_ver);
				bim.setAccesoryChanges(accesory_chngs);
				bim.setAccesoryDetails(accesory_details);
				bim.setUserManualUpdate(user_manul_updt);
				bim.setServiceManualUpdate(service_manul_updt);
				bim.setFqcRemarks(fqc_remark);
				bim.setAccesChngRemark(accory_change_remark);
				bim.setHardwrChanges(hrdwr_chang);
				bim.setHardwareDetails(hrdwr_details);
				bim.setHrdwrChangRemark(hrdwr_chang_remark);
				bim.setTsTeamRemark(ts_team_verification_date);
				bim.setSftwrChngRemark(sftwr_chang_remark);
				bim.setCnrRefNo(cnr_tech_ref_no);
				if (cnr_tech_ref_no.equalsIgnoreCase("Not Required")) {
					bim.setCnrReleseDate(null);
					bim.setCnrTecRefNum("NIL");
				} else {
					bim.setCnrReleseDate(cnr_relese_date);
					bim.setCnrTecRefNum(cnr_ref_no);
				}
				bim.setPsTeamRemark(ps_team_verification_date);
				bim.setBirRefNo(bir_ref_no);
				bim.setFinalStatus(status);
				bim.setClosedDate(current_date);
				bim.setId(id);
				bim.setUnitInDate(unit_inward_date);
				bim.setTechRemarks(tech_remark);

				bid.update(bim);
				if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("ViceChancellor")) {
					response.sendRedirect("BIRAdminList.jsp");
				} else if (role.equalsIgnoreCase("fqc")) {
					response.sendRedirect("BIRAdminList.jsp");
				} else if (role.equalsIgnoreCase("productsupport")) {
					response.sendRedirect("BIRAdminList.jsp");
				} else {
					response.sendRedirect("BIREngList.jsp");
				}
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
				 response.sendRedirect("error2.jsp");
			} catch (SQLException ex) {
				ex.printStackTrace();
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
