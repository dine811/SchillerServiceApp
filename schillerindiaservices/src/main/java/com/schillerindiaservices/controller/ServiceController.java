package com.schillerindiaservices.controller;


import com.schillerindiaservices.Dao.BranchDao;
import com.schillerindiaservices.Dao.DropdownDao;
import com.schillerindiaservices.Dao.EmployeeDao;
import com.schillerindiaservices.Dao.ModelDao;
import com.schillerindiaservices.Dao.ProductDao;
import com.schillerindiaservices.Dao.RegionDao;
import com.schillerindiaservices.Dao.ServiceDao;
import com.schillerindiaservices.bean.service_master;
import com.schillerindiaservices.common.UtilFunctions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
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
 * @author Kavin_rkz
 */
public class ServiceController extends HttpServlet {

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
            out.println("<title>Servlet ServiceController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServiceController at " + request.getContextPath() + "</h1>");
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
//        processRequest(request, response);
        try 
        {
        int id=Integer.parseInt(request.getParameter("id"));
        String action=request.getParameter("action");
        ServiceDao demo=new ServiceDao();
        if(action!=null)
        {
            if(action.equalsIgnoreCase("edit"))
            {   
                service_master s=  ServiceDao.getById(id);
                request.setAttribute("editablerecord", s);
                RequestDispatcher rd=request.getRequestDispatcher("ServiceFormEdit.jsp");
                rd.forward(request, response);
            }
            else
            {
                demo.delete(id);
                RequestDispatcher rd=request.getRequestDispatcher("ServiceList.jsp");  
                rd.forward(request, response);
            }
        }
        } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(SupplierController.class.getName()).log(Level.SEVERE, null, ex);
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
    	 
        try
        {
        	 HttpSession session1 = request.getSession(true);
        service_master service=new service_master();
        ServiceDao sdao=new ServiceDao();
        UtilFunctions utilfn = new UtilFunctions();
        int id=Integer.parseInt(request.getParameter("id"));
        String name=(String)session1.getAttribute("logname");
        if(id!=0)//for updating
        {
//            String ra_engineername=request.getParameter("ra_engineer");
//            int ra_engineer=EmployeeDao.getEmpId(ra_engineername);
//            String tech_remarks=request.getParameter("tech_remarks");
//            String comp_repair=request.getParameter("comp_repair");
        String division=request.getParameter("division");
        int divisio=ProductDao.getid(division);
        String sc_rf_no=request.getParameter("sc_ref_no");
        String sc_engineername=request.getParameter("sc_eng");
        int sc_engineer=EmployeeDao.getEmpId1(sc_engineername);
        String frn_no=request.getParameter("frn_no");
        String stk_cust=request.getParameter("stk");
        String region=request.getParameter("regionselect");
        String branch=request.getParameter("branchselect");
        int engineerstring=Integer.parseInt(request.getParameter("engineerselect"));
        String dealer_name=request.getParameter("del_name");
        String cust_name=request.getParameter("cus_name");
        String supplier=request.getParameter("sup");
        String model=request.getParameter("model");
        String modelname=ModelDao.getmodelid(model); 
        String unit_sl_no=request.getParameter("unit_sl_no");
        String unit_status=request.getParameter("unit_status");
        String mod_name=request.getParameter("mod_name");
        String def_mod_name=request.getParameter("def_name");
        String def_type=request.getParameter("def_type");
        String type_of_acc=request.getParameter("type_acc");
        String def_sno=request.getParameter("def_sn_no");
        String def_gir_no=request.getParameter("def_gir_no");
        String reptype=request.getParameter("rep_type");
        String rep_gir_no=request.getParameter("rep_gir_sno");
        String def_unit_gir_no=request.getParameter("def_unit_gir_no");
        String field_remarks=request.getParameter("field_remarks");
        String Type_of_work=request.getParameter("type_work");
        String frn_date=(request.getParameter("frn_date"));
        String ser_comm_inward_date=(request.getParameter("ser_comm_inward_date"));
        String received_date_ser_centre=(request.getParameter("receive_date_ser_centre"));
        String dod=(request.getParameter("dod"));
        String ship_date=request.getParameter("ship_date");
        String disp_ad_no=request.getParameter("disp_advice_no");
        String commercial_date=request.getParameter("commercial_date");
        String repairedBrdAdvNo=request.getParameter("repaired_brd_adv_no");  
        String desp_date=request.getParameter("desp_frm_date");
        String repair_stk_date=request.getParameter("repair_stk_date");
        String final_remarks=request.getParameter("final_remarks");
        String dc_no=request.getParameter("dc_no");
        int raeng=Integer.parseInt(request.getParameter("engineerselect"));
        String compt_repair=request.getParameter("comp_repair");
        String tech_remarks=request.getParameter("tech_remarks");
         String comr_invc_remark=request.getParameter("invoice");
         String model_config=request.getParameter("model_config");
         String part_Number=request.getParameter("part_no");
         String comp_models=request.getParameter("comp_models");
         String cost=request.getParameter("cost");
         double cost1=Double.parseDouble(cost);
        System.out.println(cost1+"the cost 1 issss");
        String report_type=request.getParameter("report_type");
        String destination=request.getParameter("destination");
        String f_status="Pending";
        
      //  frn_date = utilfn.getRev_DateFormat(frn_date);
        
        
            service.setModelConfig(model_config);
            service.setComponentsUsedforRepair(compt_repair);
            service.setTechnicalRemarks(tech_remarks);
            service.setSerId(id);
            service.setDivision(divisio);
            service.setScRefNo(sc_rf_no);
            service.setScEngnr(sc_engineer);
//            service.setRaEngnr(ra_engineer);
//            service.setTechnicalRemarks(tech_remarks);
//            service.setComponentsUsedforRepair(comp_repair);
//            service.setDefUnitGirNo(def_unit_gir_no);
            service.setRepairedBrdStkDate(repair_stk_date);
            String str=repair_stk_date;
               if(str.isEmpty()||str==null) 
                    {
                    } 
              else
                    {
                     service.setYear(str.substring(6,10));
                     service.setMonth(str.substring(3,5));
                     }
            service.setFinalRemarks(final_remarks);
            service.setTypeOfWork(Type_of_work);
            service.setShipDtFrmSerCntr(ship_date);
            service.setDispAdvNo(disp_ad_no);
            service.setShipDateFromCommercial(commercial_date);
            service.setDcNo(dc_no);
//            service.setComrclDtlInvRmrk(invoice);
            service.setFrnNo(frn_no);
            service.setStkCust(stk_cust);
            service.setRegion(region);
            service.setBranch(branch);
            service.setEngineerId(engineerstring);
            service.setDealerName(dealer_name);
            service.setCustName(cust_name);
            service.setSupplierName(supplier);
            service.setProductModel(modelname);
            service.setUnitSlno(unit_sl_no);
            service.setUnitStatus(unit_status);
            service.setModBrdName(mod_name);
            service.setDefModBrdName(def_mod_name);
            service.setDefType(def_type);
            service.setTypeOfAcc(type_of_acc);
            service.setDefPartSn(def_sno);
            service.setDefGirNo(def_gir_no);
            service.setRepType(reptype);
            service.setRepGirNo(rep_gir_no);
            service.setDefUnitGirNo(def_unit_gir_no);
            service.setFieldRemarks(field_remarks);
            service.setFrnDate(frn_date);
            service.setSerCommInwardDate(ser_comm_inward_date);
            service.setSerCentreReceivedDate(received_date_ser_centre);
            service.setDod(dod);
            service.setRepairedBrdAdvNo(repairedBrdAdvNo);
            service.setDespatchDate(desp_date);
            service.setRaEngnr(raeng);
            service.setComrclDtlInvRmrk(comr_invc_remark);
            service.setPart_Number(part_Number);
            service.setCompatibleModels(comp_models);
            service.setCost(cost1);
            service.setReport_type(report_type);
            service.setDestination(destination);
            service.setStatus(f_status);
            
            
            sdao.updatepart(service);
            
            
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
        }
        
        
        
        
        else// for inserting
        {
        	  
        String divisionstring=request.getParameter("division");
        int division=ProductDao.getid(divisionstring);
        String sc_rf_no=request.getParameter("sc_ref_no");
        String sc_engineername=request.getParameter("sc_eng");
        int sc_engineer=EmployeeDao.getEmpId1(sc_engineername);
        String frn_no=request.getParameter("frn_no");
        String stk_cust=request.getParameter("stk");
        String region=request.getParameter("regionselect");
        String branch=request.getParameter("branchselect");
        int engineerstring=Integer.parseInt(request.getParameter("engineerselect"));
        int raeng=0;
//        EmployeeDao EmpDao =new EmployeeDao();
//        int engineer=EmpDao.getEmpId(engineerstring);
        String dealer_name=request.getParameter("del_name");
        String cust_name=request.getParameter("cus_name");
        String supplier=request.getParameter("sup");
        String model=request.getParameter("model");
        System.out.println(model+ " mmmmmm");
        String unit_sl_no=request.getParameter("unit_sl_no");
        String unit_status=request.getParameter("unit_status");
        String mod_name=request.getParameter("mod_name");
        String def_mod_name=request.getParameter("def_name");
        String def_type=request.getParameter("def_type");
        String type_of_acc=request.getParameter("type_acc");
        String def_sno=request.getParameter("def_sn_no");
        String def_gir_no=request.getParameter("def_gir_no");
        String reptype=request.getParameter("rep_type");
        String rep_gir_no=request.getParameter("rep_gir_sno");
        String def_unit_gir_no=request.getParameter("def_unit_gir_no");
        String field_remarks=request.getParameter("field_remarks");
        String Type_of_work=request.getParameter("type_work");
        String frn_date=(request.getParameter("frn_date"));
        String ser_comm_inward_date=(request.getParameter("ser_comm_inward_date"));
        String received_date_ser_centre=(request.getParameter("receive_date_ser_centre"));
        String dod=(request.getParameter("dod"));
        String ship_date=request.getParameter("ship_date");
        String disp_ad_no=request.getParameter("disp_advice_no");
        String commercial_date=request.getParameter("commercial_date");
        String repairedBrdAdvNo=request.getParameter("repaired_brd_adv_no");  
        String desp_date=request.getParameter("desp_frm_date");
        String repair_stk_date=request.getParameter("repair_stk_date");
        String final_remarks=request.getParameter("final_remarks");
        String dc_no=request.getParameter("dc_no");
        raeng=Integer.parseInt(session1.getAttribute("loguserid").toString());
        String cmpnt_repair=request.getParameter("comp_repair");
        String tech_remarks=request.getParameter("tech_remarks");
        String comr_invc_remark=request.getParameter("invoice");
         String model_config=request.getParameter("model_config");
         String part_Number=request.getParameter("part_no");
         String comp_models=request.getParameter("comp_models");
         String cost=request.getParameter("cost");
         String f_status="Pending";
         
         double cost1=0.0;
         if(cost!=null && cost!="" ){
        	   cost1=Double.parseDouble(cost);
         }
       
         System.out.println(part_Number+"part number"+comp_models+"compatible modelsss"+cost1+"cost issss");
        String report_type=request.getParameter("report_type");
        String destination=request.getParameter("destination");
        String str=repair_stk_date;
        
        
        frn_date = utilfn.getRev_DateFormat(frn_date);
        ser_comm_inward_date = utilfn.getRev_DateFormat(ser_comm_inward_date);
        received_date_ser_centre = utilfn.getRev_DateFormat(received_date_ser_centre);
        ship_date = utilfn.getRev_DateFormat(ship_date);
        commercial_date =  utilfn.getRev_DateFormat(commercial_date);
        repair_stk_date =  utilfn.getRev_DateFormat(repair_stk_date);
        
        service.setModelConfig(model_config);
        service.setComrclDtlInvRmrk(comr_invc_remark);
        service.setDespatchDate(desp_date);
        service.setDivisionName(divisionstring);
        service.setDivision(division);
        service.setScRefNo(sc_rf_no);
        service.setScEngnr(sc_engineer);
        service.setFrnNo(frn_no);
        service.setFrnDate(frn_date);
        service.setSerCommInwardDate(ser_comm_inward_date);
        service.setSerCentreReceivedDate(received_date_ser_centre);
        service.setStkCust(stk_cust);
        service.setRegion(region);
        service.setBranch(branch);
        service.setEngineerId(engineerstring);
        service.setDealerName(dealer_name);
        service.setCustName(cust_name);
        service.setSupplierName(supplier);
        service.setProductModel(model);
        service.setUnitSlno(unit_sl_no);
        service.setDod(dod);
        service.setUnitStatus(unit_status);
        service.setModBrdName(mod_name);
        service.setDefModBrdName(def_mod_name);
        service.setDefType(def_type);
        service.setTypeOfAcc(type_of_acc);
        service.setDefPartSn(def_sno);
        service.setDefGirNo(def_gir_no);
        service.setRepType(reptype);
        service.setRepGirNo(rep_gir_no);
        service.setDefUnitGirNo(def_unit_gir_no);
        service.setFieldRemarks(field_remarks);
        service.setTypeOfWork(Type_of_work);
        service.setShipDtFrmSerCntr(ship_date);
        service.setDispAdvNo(disp_ad_no);
        service.setShipDateFromCommercial(commercial_date);
        service.setRepairedBrdAdvNo(repairedBrdAdvNo);
        service.setRepairedBrdStkDate(repair_stk_date);
       
        service.setFinalRemarks(final_remarks);
        service.setDcNo(dc_no);
        service.setRaEngnr(raeng);
        service.setComponentsUsedforRepair(cmpnt_repair);
        service.setTechnicalRemarks(tech_remarks);
        service.setPart_Number(part_Number);
        service.setCompatibleModels(comp_models);
        service.setCost(cost1);
        service.setReport_type(report_type);
        service.setDestination(destination);
        service.setStatus(f_status);

        
        if(str.isEmpty()||str==null) 
           {
            
           } 
        else
           {
            service.setYear(str.substring(6,10));
            service.setMonth(str.substring(3,5));
            }
      
        
          DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
          java.util.Date dateobj = new java.util.Date();
          String  current_date=df.format(dateobj);
          
           
         service.setEntryDate(current_date);
       //  sdao.save(service);
         sdao.save_demo(service);
        
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
         }
        } 
        catch (SQLException | ClassNotFoundException ex) 
        {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
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
