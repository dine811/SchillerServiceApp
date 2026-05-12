/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.ModelDao;
import com.schillerindiaservices.Dao.PRFOBDao;
import com.schillerindiaservices.Dao.ProductDao;
import com.schillerindiaservices.Dao.SupplierDao;
import com.schillerindiaservices.bean.Prfobmaster;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class PRFOBController extends HttpServlet {

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
            out.println("<title>Servlet PRFOBController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PRFOBController at " + request.getContextPath() + "</h1>");
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
        try
        {
            PRFOBDao prfd=new PRFOBDao();
        int id=Integer.parseInt(request.getParameter("id"));
        String action=request.getParameter("action");
        if(action!=null)
        {
            if(action.equalsIgnoreCase("edit"))
            {
            	System.out.println("inside edit!");
                Prfobmaster prf=PRFOBDao.getById(id);
                System.out.println("inside edit dataa!"+prf.getId());
                request.setAttribute("prfobrecors", prf);
                RequestDispatcher rd=request.getRequestDispatcher("PrfOb_Update.jsp");
                rd.forward(request, response);
            }
            else
            {
                prfd.delete(id);
                RequestDispatcher rd=request.getRequestDispatcher("PRFOB_AdminList.jsp");
                rd.forward(request, response);
            }
        }
        }
        catch(Exception e){}
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
        HttpSession sess=request.getSession();
        String role=(String) sess.getAttribute("logrole");
        Prfobmaster prf=new Prfobmaster();
        PRFOBDao prfd=new PRFOBDao();
        int id=Integer.parseInt(request.getParameter("id"));
        
        LocalDateTime dt1=LocalDateTime.now();
        System.out.println(dt1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
        String formattedString = dt1.format(formatter);
        System.out.println("the format stringg isss"+formattedString);
        String final_dt1=formattedString.substring(11);
        System.out.println(final_dt1+" ffff");
        
        if(id==0)
        {
            try{
            String division=request.getParameter("division");
            String sc_engg=request.getParameter("sc_engg");
            System.out.println(sc_engg+" the sc ssssss");
             String type=request.getParameter("type");
             String raised_date=request.getParameter("raised_date");
             String recv_date_sc=request.getParameter("recv_date_sc");
             String region=request.getParameter("region");
             String branch=request.getParameter("branch");
             String dealer=request.getParameter("dealer");
             String engineer=request.getParameter("engineer");
             
             String supplier=request.getParameter("supplier");
            String model=request.getParameter("model");
            // System.out.println(model+ "m isss");
             String ModName=ModelDao.getModelname(model);
            // System.out.println(ModName+ "m isss");
             String Warranty=request.getParameter("Warranty");
             String prfob_ref_no=request.getParameter("prfob_ref_no");
             String crm_ref_no=request.getParameter("crm_ref_no");
             String remarks=request.getParameter("remarks");
             String status=request.getParameter("status");
             String part_type=request.getParameter("part_type");
             String part_desc=request.getParameter("part_desc");
             String rec_date_frm_sc=request.getParameter("receive_date_from_sc");
          //  String rec_date_frm_sc1= rec_date_frm_sc.concat(" "+final_dt1);
             prf.setId(id);
             prf.setPartType(part_type);
             prf.setPartDescription(part_desc);
             prf.setDivision(division);
             prf.setScEngg(sc_engg);
             prf.setWorkType(type);
             prf.setRaisedDate(raised_date);
             prf.setReceivedDate(recv_date_sc);
             prf.setRegion(region);
             prf.setBranch(branch);
             prf.setDealer(dealer);
             prf.setEngineer(engineer);
             prf.setSupplier(supplier);
             prf.setModel(ModName);
             prf.setWarrentyStatus(Warranty);
             prf.setPrfobRefNo(prfob_ref_no);
             prf.setCrmRefNo(crm_ref_no);
             prf.setRemarks(remarks);
             prf.setStatusType(status);
             prf.setReceive_date_from_sc(rec_date_frm_sc);
             
             
             DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
             java.util.Date dateobj=new java.util.Date();
             String curent_date=df.format(dateobj);
             
             prf.setEntryDate(curent_date);
             if(status.equalsIgnoreCase("Completed"))
             {
             prf.setExecutedDate(curent_date);
             }
             else
             {
                prf.setExecutedDate(""); 
             }
             prfd.save(prf);
             response.sendRedirect("PRFOB_AdminList.jsp");
            }
            catch(Exception e){
            }
        }
        else
        {
            try {
                String crm_ref_no=request.getParameter("crm_ref_no");
                String remarks=request.getParameter("remarks");
                String status=request.getParameter("status");
                String type=request.getParameter("type");
                String raised_date=request.getParameter("raised_date");
                String recv_date_sc=request.getParameter("recv_date_sc");
                String Warranty=request.getParameter("Warranty");
                String prfob_ref_no=request.getParameter("prfob_ref_no");
                String part_type=request.getParameter("part_type");
                String part_desc=request.getParameter("part_desc");
                String sc_engg=request.getParameter("sc_engg");
              //  System.out.println(sc_engg+" theee sc engg isss");
                String rec_date_frm_sc=request.getParameter("receive_date_from_sc");
                String rec_date_frm_sc1=null;
                if(rec_date_frm_sc.length()>13){
                	//System.out.println("firstt"+rec_date_frm_sc.length()+"  "+rec_date_frm_sc);
                	rec_date_frm_sc=rec_date_frm_sc.substring(0,11);
                	rec_date_frm_sc1=rec_date_frm_sc.concat(" "+final_dt1);
                	//System.out.println(rec_date_frm_sc1);
                }else{
                	//System.out.println("seconddd"+rec_date_frm_sc.length()+"  "+rec_date_frm_sc);
                	  rec_date_frm_sc1= rec_date_frm_sc.concat(" "+final_dt1);
                	//  System.out.println(rec_date_frm_sc1);
                }
                //
                
               
                
                prf.setWorkType(type);
                prf.setPartType(part_type);
                prf.setScEngg(sc_engg);
                prf.setPartDescription(part_desc);
                prf.setReceivedDate(recv_date_sc);
                prf.setRaisedDate(raised_date);
                prf.setWarrentyStatus(Warranty);
                prf.setPrfobRefNo(prfob_ref_no);
               
                prf.setId(id);
                prf.setCrmRefNo(crm_ref_no);
                prf.setRemarks(remarks);
                prf.setStatusType(status);
                prf.setReceive_date_from_sc(rec_date_frm_sc1);
                DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
             java.util.Date dateobj=new java.util.Date();
             String curent_date=df.format(dateobj);
             
             prf.setEntryDate(curent_date);
             if(status.equalsIgnoreCase("Completed"))
             {
             prf.setExecutedDate(curent_date);
             }
             else
             {
                prf.setExecutedDate(""); 
             }
                prfd.update(prf);
                
                if(role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("ViceChancellor")||role.equalsIgnoreCase("servicecoordinator"))
                {
                    response.sendRedirect("PRFOB_AdminList.jsp");
                }else{
                response.sendRedirect("PRFOB_EnggList.jsp");
                }
            } catch (ClassNotFoundException ex) {
                
            } catch (SQLException ex) {
               
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
