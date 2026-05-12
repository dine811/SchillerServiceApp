/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.service_master;
import com.schillerindiaservices.connection.DbConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author Kavin_rkz
 */
public class ServiceDao {
   
    
    public int save(service_master d) throws SQLException, ClassNotFoundException
    {  
        int status=0;  
        Connection con=null;
        con =DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("insert into service_master(entry_date, division, sc_ref_no, sc_engnr, frn_no, frn_date, ser_comm_inward_date, ser_centre_received_date, stk_cust, region, branch, engineer_id, dealer_name, cust_name, supplier_name, product_model, unit_slno, dod, unit_status,mod_brd_name, def_mod_brd_name, def_type, type_of_acc, def_part_sn, def_gir_no, rep_type, rep_gir_no, def_unit_gir_no, field_remarks, type_of_work, ship_dt_frm_ser_cntr, disp_adv_no, ship_date_from_commercial, division_name,despatch_date,repaired_brd_stk_date,final_remarks,dc_no,repaired_brd_adv_no,month,year,ra_engnr,components_usedfor_repair,technical_remarks,comrcl_dtl_inv_rmrk,model_config,status,part_Number,compatible_models,cost,report_type,destination) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1,d.getEntryDate());
        ps.setInt(2,d.getDivision());
        ps.setString(3,d.getScRefNo());
        ps.setInt(4, d.getScEngnr());
        ps.setString(5,d.getFrnNo());
        ps.setString(6,d.getFrnDate());
        ps.setString(7,d.getSerCommInwardDate());
        ps.setString(8, d.getSerCentreReceivedDate());
        ps.setString(9,d.getStkCust());
        ps.setString(10, d.getRegion());
        ps.setString(11, d.getBranch());
        ps.setInt(12,d.getEngineerId());
        ps.setString(13,d.getDealerName());
        ps.setString(14,d.getCustName());
        ps.setString(15, d.getSupplierName());
        ps.setString(16, d.getProductModel());
        ps.setString(17,d.getUnitSlno());
        ps.setString(18, d.getDod());
        ps.setString(19,d.getUnitStatus());
        ps.setString(20,d.getModBrdName());
        ps.setString(21,d.getDefModBrdName());
        ps.setString(22,d.getDefType());
        ps.setString(23,d.getTypeOfAcc());
        ps.setString(24,d.getDefPartSn());
        ps.setString(25,d.getDefGirNo());
        ps.setString(26,d.getRepType());
        ps.setString(27, d.getRepGirNo());
        ps.setString(28,d.getDefUnitGirNo());
        ps.setString(29,d.getFieldRemarks());
        ps.setString(30,d.getTypeOfWork());
        ps.setString(31,  d.getShipDtFrmSerCntr());
        ps.setString(32,d.getDispAdvNo());
        ps.setString(33, d.getShipDateFromCommercial());
        ps.setString(34,d.getDivisionName());
        ps.setString(35, d.getDespatchDate());
        ps.setString(36,d.getRepairedBrdStkDate());
        ps.setString(37, d.getFinalRemarks());
        ps.setString(38,d.getDcNo());
        ps.setString(39,d.getRepairedBrdAdvNo());
        ps.setString(40, d.getMonth());
        ps.setString(41, d.getYear());
        ps.setInt(42,d.getRaEngnr());
        ps.setString(43,d.getComponentsUsedforRepair());
        ps.setString(44,d.getTechnicalRemarks());
        ps.setString(45, d.getComrclDtlInvRmrk());
        ps.setString(46, d.getModelConfig());
        ps.setString(47, d.getStatus());
        ps.setString(48, d.getPart_Number());
        ps.setString(49, d.getCompatibleModels());
        ps.setDouble(50, d.getCost());
        ps.setString(51, d.getReport_type());
        ps.setString(52, d.getDestination());
        
        
        
        
        status=ps.executeUpdate();
        con.close();
        return status;  
    }  
    
    
     public static List<service_master> getAllRecordsincluded(String logrole, int empdivisionid) throws SQLException
    {  
        List<service_master> list=new ArrayList<service_master>();  
        Connection con=null;
        try{  
            con=DbConnection.getConnection();   
            PreparedStatement ps=null; 
            if("admin".equalsIgnoreCase(logrole))
            {    
                ps=con.prepareStatement("select * from service_master");
            }
            else
            {
                
                ps=con.prepareStatement("select * from service_master where service_master.sc_engnr='"+empdivisionid+"'");
            }
                
                
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
                service_master s=new service_master();  
            s.setSerId(rs.getInt("ser_id"));
            s.setScRefNo(rs.getString("sc_ref_no"));
            s.setEntryDate(rs.getString("entry_date"));
            s.setScEngnr(rs.getInt("sc_engnr"));
            s.setRaEngnr(rs.getInt("ra_engnr"));
            s.setFrnNo(rs.getString("frn_no"));
            s.setFrnDate(rs.getString("frn_date"));
            s.setDocketNo(rs.getString("docket_no"));
            s.setDespatchDate(rs.getString("despatch_date"));
            s.setSerCommInwardDate(rs.getString("ser_comm_inward_date"));
            s.setSerCentreReceivedDate(rs.getString("ser_centre_received_date"));
            s.setStkCust(rs.getString("stk_cust"));
            s.setRegion(rs.getString("region"));
            s.setBranch(rs.getString("branch"));
            s.setEngineerId(rs.getInt("engineer_id"));
            s.setDealerName(rs.getString("dealer_name"));
            s.setCustName(rs.getString("cust_name"));
            s.setSupplierName(rs.getString("supplier_name"));
            s.setProductModel(rs.getString("product_model"));
            s.setUnitSlno(rs.getString("unit_slno"));
            s.setDod(rs.getString("dod"));
            s.setUnitStatus(rs.getString("unit_status"));
            s.setModBrdName(rs.getString("mod_brd_name"));
            s.setDefModBrdName(rs.getString("def_mod_brd_name"));
            s.setDefType(rs.getString("def_type"));
            s.setTypeOfAcc(rs.getString("type_of_acc"));
            s.setDefPartSn(rs.getString("def_part_sn"));
            s.setDefGirNo(rs.getString("def_gir_no"));
            s.setRepType(rs.getString("rep_type"));
            s.setRepGirNo(rs.getString("rep_gir_no"));
            s.setDefUnitGirNo(rs.getString("def_unit_gir_no"));
            s.setFieldRemarks(rs.getString("field_remarks"));
            s.setTechnicalRemarks(rs.getString("technical_remarks"));
            s.setComponentsUsedforRepair(rs.getString("components_usedfor_repair"));
            s.setRepairedBrdStkDate(rs.getString("repaired_brd_stk_date"));
            s.setFinalRemarks(rs.getString("final_remarks"));
            s.setTypeOfWork(rs.getString("type_of_work"));
            s.setShipDtFrmSerCntr(rs.getString("ship_dt_frm_ser_cntr"));
            s.setDispAdvNo(rs.getString("disp_adv_no"));
            s.setShipDateFromCommercial(rs.getString("ship_date_from_commercial"));
            s.setDcNo(rs.getString("dc_no"));
            s.setComrclDtlInvRmrk(rs.getString("comrcl_dtl_inv_rmrk"));
            s.setRepairedBrdAdvNo(rs.getString("repaired_brd_adv_no"));
            s.setDestination(rs.getString("destination"));
              
            list.add(s);  
            }  
        }catch(Exception e){}  
        con.close();
        return list;  
    }  
     
     
     public static List<service_master> getAllRecords(int pendingtypeid, String logrole, int empid, int empdivision) throws SQLException
    {  
        List<service_master> list=new ArrayList<service_master>();  
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{  
            con=DbConnection.getConnection();  
            if("admin".equalsIgnoreCase(logrole))
            {
                if(pendingtypeid==1)
                {
                    ps=con.prepareStatement("SELECT * FROM service_master WHERE service_master.unit_status != \"OW\" AND service_master.unit_status != \"LAM\" AND service_master.ship_dt_frm_ser_cntr IS NULL ");  
                }
                else if(pendingtypeid==2)
                {
                    ps=con.prepareStatement("SELECT * FROM service_master WHERE  service_master.ship_dt_frm_ser_cntr IS NULL  AND (service_master.unit_status = \"OW\")AND (service_master.unit_status = \"LAM\")");  
                }
                else if(pendingtypeid==3)
                {
                    ps=con.prepareStatement("SELECT * FROM service_master WHERE ship_dt_frm_ser_cntr IS NOT NULL  AND repaired_brd_stk_date IS NULL ");  
                }
                else if(pendingtypeid==4)
                {
                    ps=con.prepareStatement("SELECT * FROM service_master WHERE ship_dt_frm_ser_cntr IS NOT NULL  AND repaired_brd_stk_date IS NOT NULL ");  
                }
                else
                {
                
                }
            }
            else
            {
                if(pendingtypeid==1)
                {
                    ps=con.prepareStatement("SELECT * FROM service_master WHERE service_master.division="+empdivision+" AND service_master.unit_status != \"OW\" AND service_master.unit_status != \"LAM\"  AND service_master.ship_dt_frm_ser_cntr IS NULL ");  
                }
                else if(pendingtypeid==2)
                {
                    ps=con.prepareStatement("SELECT * FROM service_master WHERE  service_master.division="+empdivision+" AND service_master.ship_dt_frm_ser_cntr IS NULL  AND service_master.unit_status = \"OW\" AND service_master.unit_status = \"LAM\"");  
                }
                else if(pendingtypeid==3)
                {
                    ps=con.prepareStatement("SELECT * FROM service_master WHERE service_master.division="+empdivision+" AND ship_dt_frm_ser_cntr IS NOT NULL  AND repaired_brd_stk_date IS NULL ");  
                }
                else if(pendingtypeid==4)
                {
                    ps=con.prepareStatement("SELECT * FROM service_master WHERE service_master.division="+empdivision+" AND ship_dt_frm_ser_cntr IS NOT NULL  AND repaired_brd_stk_date IS NOT NULL ");  
                }
                else
                {
                
                }
            }
            if(ps!=null)
            {
                rs=ps.executeQuery();  
            
            while(rs.next()){  
                service_master s=new service_master();  
            s.setSerId(rs.getInt("ser_id"));
            s.setScRefNo(rs.getString("sc_ref_no"));
            s.setEntryDate(rs.getString("entry_date"));
            s.setScEngnr(rs.getInt("sc_engnr"));
            s.setRaEngnr(rs.getInt("ra_engnr"));
            s.setFrnNo(rs.getString("frn_no"));
            s.setFrnDate(rs.getString("frn_date"));
            s.setDocketNo(rs.getString("docket_no"));
            s.setDespatchDate(rs.getString("despatch_date"));
            s.setSerCommInwardDate(rs.getString("ser_comm_inward_date"));
            s.setSerCentreReceivedDate(rs.getString("ser_centre_received_date"));
            s.setStkCust(rs.getString("stk_cust"));
            s.setRegion(rs.getString("region"));
            s.setBranch(rs.getString("branch"));
            s.setEngineerId(rs.getInt("engineer_id"));
            s.setDealerName(rs.getString("dealer_name"));
            s.setCustName(rs.getString("cust_name"));
            s.setSupplierName(rs.getString("supplier_name"));
            s.setProductModel(rs.getString("product_model"));
            s.setUnitSlno(rs.getString("unit_slno"));
            s.setDod(rs.getString("dod"));
            s.setUnitStatus(rs.getString("unit_status"));
            s.setModBrdName(rs.getString("mod_brd_name"));
            s.setDefModBrdName(rs.getString("def_mod_brd_name"));
            s.setDefType(rs.getString("def_type"));
            s.setTypeOfAcc(rs.getString("type_of_acc"));
            s.setDefPartSn(rs.getString("def_part_sn"));
            s.setDefGirNo(rs.getString("def_gir_no"));
            s.setRepType(rs.getString("rep_type"));
            s.setRepGirNo(rs.getString("rep_gir_no"));
            s.setDefUnitGirNo(rs.getString("def_unit_gir_no"));
            s.setFieldRemarks(rs.getString("field_remarks"));
            s.setTechnicalRemarks(rs.getString("technical_remarks"));
            s.setComponentsUsedforRepair(rs.getString("components_usedfor_repair"));
            s.setRepairedBrdStkDate(rs.getString("repaired_brd_stk_date"));
            s.setFinalRemarks(rs.getString("final_remarks"));
            s.setTypeOfWork(rs.getString("type_of_work"));
            s.setShipDtFrmSerCntr(rs.getString("ship_dt_frm_ser_cntr"));
            s.setDispAdvNo(rs.getString("disp_adv_no"));
            s.setShipDateFromCommercial(rs.getString("ship_date_from_commercial"));
            s.setDcNo(rs.getString("dc_no"));
            s.setComrclDtlInvRmrk(rs.getString("comrcl_dtl_inv_rmrk"));
            s.setRepairedBrdAdvNo(rs.getString("repaired_brd_adv_no"));
            s.setDestination(rs.getString("destination"));

            list.add(s);  
            }  
            }
        }catch(Exception e){}  
        con.close();
        return list;  
    }
     
public static  service_master getById(int id) throws SQLException
{
    service_master s=null;
    Connection con=null;
        try
        {  
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select * from service_master where ser_id='"+id+"'");  
            
            ResultSet rs=ps.executeQuery();  
            
            while(rs.next())
            {  
                s=new service_master();
                s.setSerId(id);
                s.setEntryDate(rs.getString("entry_date"));
              
                s.setScRefNo(rs.getString("sc_ref_no"));
                s.setScEngnr(rs.getInt("sc_engnr"));
                s.setRaEngnr(rs.getInt("ra_engnr"));
                System.out.println(rs.getInt("ra_engnr")+"the raa engg isdsss daoo");
                s.setFrnNo(rs.getString("frn_no"));
                s.setFrnDate(rs.getString("frn_date"));
                s.setDocketNo(rs.getString("docket_no"));
                s.setDespatchDate(rs.getString("despatch_date"));
              
                s.setSerCommInwardDate(rs.getString("ser_comm_inward_date"));
                s.setSerCentreReceivedDate(rs.getString("ser_centre_received_date"));
                s.setStkCust(rs.getString("stk_cust"));
                s.setRegion(rs.getString("region"));
                s.setBranch(rs.getString("branch"));
                s.setEngineerId(rs.getInt("engineer_id"));
                s.setDealerName(rs.getString("dealer_name"));
                s.setCustName(rs.getString("cust_name"));
                s.setSupplierName(rs.getString("supplier_name"));
                s.setProductModel(rs.getString("product_model"));
                s.setUnitSlno(rs.getString("unit_slno"));
                s.setDod(rs.getString("dod"));
                s.setUnitStatus(rs.getString("unit_status"));
                s.setModBrdName(rs.getString("mod_brd_name"));
                s.setDefModBrdName(rs.getString("def_mod_brd_name"));
                s.setDefType(rs.getString("def_type"));
                s.setTypeOfAcc(rs.getString("type_of_acc"));
                s.setDefPartSn(rs.getString("def_part_sn"));
                s.setDefGirNo(rs.getString("def_gir_no"));
                s.setRepType(rs.getString("rep_type"));
                s.setRepGirNo(rs.getString("rep_gir_no"));
                s.setDefUnitGirNo(rs.getString("def_unit_gir_no"));
                s.setFieldRemarks(rs.getString("field_remarks"));
                s.setTechnicalRemarks(rs.getString("technical_remarks"));
                s.setComponentsUsedforRepair(rs.getString("components_usedfor_repair"));
                s.setRepairedBrdStkDate(rs.getString("repaired_brd_stk_date"));
                s.setFinalRemarks(rs.getString("final_remarks"));
                s.setTypeOfWork(rs.getString("type_of_work"));
                s.setShipDtFrmSerCntr(rs.getString("ship_dt_frm_ser_cntr"));
                s.setDispAdvNo(rs.getString("disp_adv_no"));
                s.setShipDateFromCommercial(rs.getString("ship_date_from_commercial"));
                s.setDcNo(rs.getString("dc_no"));
                s.setComrclDtlInvRmrk(rs.getString("comrcl_dtl_inv_rmrk"));
                s.setRepairedBrdAdvNo(rs.getString("repaired_brd_adv_no"));
                s.setDivisionName(rs.getString("division_name"));
                s.setModelConfig(rs.getString("model_config"));
                System.out.println("the modddd isss"+rs.getString("model_config"));
                s.setReport_type(rs.getString("report_type"));
                s.setDestination(rs.getString("destination"));
                s.setStatus(rs.getString("status"));

            }  
        }catch(Exception e){}  
        con.close();
        return s;
   }

public static service_master getForCopy(String di) throws SQLException
{
    service_master s=null;
    Connection con=null;
        try
        {  
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("SELECT * FROM service_master WHERE sc_engnr='"+di+"' ORDER BY ser_id DESC LIMIT 1");  
           
            ResultSet rs=ps.executeQuery();  
            
            while(rs.next())
            {  
                s=new service_master();
                
                s.setEntryDate(rs.getString("entry_date"));
              
                s.setScRefNo(rs.getString("sc_ref_no"));
                s.setScEngnr(rs.getInt("sc_engnr"));
                s.setRaEngnr(rs.getInt("ra_engnr"));
                s.setFrnNo(rs.getString("frn_no"));
                s.setFrnDate(rs.getString("frn_date"));
                s.setDocketNo(rs.getString("docket_no"));
                s.setDespatchDate(rs.getString("despatch_date"));
              
                s.setSerCommInwardDate(rs.getString("ser_comm_inward_date"));
                s.setSerCentreReceivedDate(rs.getString("ser_centre_received_date"));
                s.setStkCust(rs.getString("stk_cust"));
                s.setRegion(rs.getString("region"));
                s.setBranch(rs.getString("branch"));
                s.setEngineerId(rs.getInt("engineer_id"));
                s.setDealerName(rs.getString("dealer_name"));
                s.setCustName(rs.getString("cust_name"));
                s.setSupplierName(rs.getString("supplier_name"));
                s.setProductModel(rs.getString("product_model"));
                s.setUnitSlno(rs.getString("unit_slno"));
                s.setDod(rs.getString("dod"));
                s.setUnitStatus(rs.getString("unit_status"));
                s.setModBrdName(rs.getString("mod_brd_name"));
                s.setDefModBrdName(rs.getString("def_mod_brd_name"));
                s.setDefType(rs.getString("def_type"));
                s.setTypeOfAcc(rs.getString("type_of_acc"));
                s.setDefPartSn(rs.getString("def_part_sn"));
                s.setDefGirNo(rs.getString("def_gir_no"));
                s.setRepType(rs.getString("rep_type"));
                s.setRepGirNo(rs.getString("rep_gir_no"));
                s.setDefUnitGirNo(rs.getString("def_unit_gir_no"));
                s.setFieldRemarks(rs.getString("field_remarks"));
                s.setTechnicalRemarks(rs.getString("technical_remarks"));
                s.setComponentsUsedforRepair(rs.getString("components_usedfor_repair"));
                s.setRepairedBrdStkDate(rs.getString("repaired_brd_stk_date"));
                s.setFinalRemarks(rs.getString("final_remarks"));
                s.setTypeOfWork(rs.getString("type_of_work"));
                s.setShipDtFrmSerCntr(rs.getString("ship_dt_frm_ser_cntr"));
                s.setDispAdvNo(rs.getString("disp_adv_no"));
                s.setShipDateFromCommercial(rs.getString("ship_date_from_commercial"));
                s.setDcNo(rs.getString("dc_no"));
                s.setComrclDtlInvRmrk(rs.getString("comrcl_dtl_inv_rmrk"));
                s.setRepairedBrdAdvNo(rs.getString("repaired_brd_adv_no"));
                s.setModelConfig(rs.getString("model_config"));
                System.out.println("the modddd isss"+rs.getString("model_config"));
                s.setDivisionName(rs.getString("division_name"));
                s.setPart_Number(rs.getString("part_number"));
                s.setReport_type(rs.getString("report_type"));
                s.setDestination(rs.getString("destination"));

            }  
        }catch(Exception e){}  
        con.close();
        return s;
}


public static  int getDivisionId(int serid) throws SQLException
{
    int divid=0;
    Connection con=null;
        try
        {  
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select division from service_master where ser_id='"+serid+"'");  
            
            ResultSet rs=ps.executeQuery();  
            
            if(rs.next())
            {  
                divid=rs.getInt("division");
            }  
        }catch(Exception e){}  
        con.close();
        return divid;
   }


public void update(service_master d) throws ClassNotFoundException, SQLException{
         Connection con=DbConnection.getConnection();
         PreparedStatement st=con.prepareStatement("update service_master set sc_ref_no='"+d.getScRefNo()+"',sc_engnr='"+d.getScEngnr()+"',ra_engnr='"+d.getRaEngnr()+"', frn_no ='"+d.getFrnNo()+"',frn_date='"+d.getFrnDate()+"',docket_no='"+d.getDocketNo()+"',despatch_date='"+d.getDespatchDate()+"',ser_comm_inward_date='"+d.getSerCommInwardDate()+"', ser_centre_received_date ='"+d.getSerCentreReceivedDate()+"',region='"+d.getRegion()+"',branch='"+d.getBranch()+"',engineer_id ='"+d.getEngineerId()+"', dealer_name  ='"+d.getDealerName()+"',cust_name ='"+d.getCustName()+"',supplier_name='"+d.getSupplierName()+"',product_model='"+d.getProductModel()+"',unit_slno='"+d.getUnitSlno()+"', dod ='"+d.getDod()+"',unit_status='"+d.getUnitStatus()+"',mod_brd_name='"+d.getModBrdName()+"', def_mod_brd_name ='"+d.getDefModBrdName()+"',def_type='"+d.getDefType()+"',type_of_acc='"+d.getTypeOfAcc()+"',def_part_sn='"+d.getDefPartSn()+"',def_gir_no='"+d.getDefGirNo()+"',rep_type='"+d.getRepType()+"',rep_gir_no='"+d.getRepGirNo()+"',def_unit_gir_no ='"+d.getDefUnitGirNo()+"', field_remarks ='"+d.getFieldRemarks()+"',repaired_brd_stk_date ='"+d.getRepairedBrdStkDate()+"',final_remarks='"+d.getFinalRemarks()+"',type_of_work='"+d.getTypeOfWork()+"',ship_dt_frm_ser_cntr='"+d.getShipDtFrmSerCntr()+"', disp_adv_no ='"+d.getDispAdvNo()+"',ship_date_from_commercial='"+d.getShipDateFromCommercial()+"',dc_no  ='"+d.getDcNo()+"', repaired_brd_adv_no ='"+d.getRepairedBrdAdvNo()+"',destination='"+d.getDestination()+"' where ser_id='"+d.getSerId()+"'");
         st.executeUpdate();
         con.close();
    }

public void updatepart(service_master d) throws ClassNotFoundException, SQLException{
         Connection con=DbConnection.getConnection();
         PreparedStatement st=null;
              st=con.prepareStatement("UPDATE service_master SET division='"+d.getDivision()+"' ,sc_ref_no='"+d.getScRefNo()+"' ,sc_engnr='"+d.getScEngnr()+"',frn_no='"+d.getFrnNo()+"',frn_date='"+d.getFrnDate()+"',despatch_date='"+d.getDespatchDate()+"',ser_comm_inward_date='"+d.getSerCommInwardDate()+"',\n" +
                                       "ser_centre_received_date='"+d.getSerCentreReceivedDate()+"',stk_cust='"+d.getStkCust()+"',\n" +
                                       "region='"+d.getRegion()+"',branch='"+d.getBranch()+"',engineer_id='"+d.getEngineerId()+"',dealer_name='"+d.getDealerName()+"',cust_name='"+d.getCustName()+"',supplier_name='"+d.getSupplierName()+"',\n" +
                                       "product_model='"+d.getProductModel()+"',unit_slno='"+d.getUnitSlno()+"',dod='"+d.getDod()+"',unit_status='"+d.getUnitStatus()+"',mod_brd_name='"+d.getModBrdName()+"',def_mod_brd_name='"+d.getDefModBrdName()+"',def_type='"+d.getDefType()+"',type_of_acc='"+d.getTypeOfAcc()+"',\n" +
                                       "def_part_sn='"+d.getDefPartSn()+"',def_gir_no='"+d.getDefGirNo()+"',rep_type='"+d.getRepType()+"',rep_gir_no='"+d.getRepGirNo()+"',def_unit_gir_no='"+d.getDefUnitGirNo()+"',repaired_brd_stk_date='"+d.getRepairedBrdStkDate()+"',\n" +
                                       "field_remarks='"+d.getFieldRemarks()+"',final_remarks='"+d.getFinalRemarks()+"',type_of_work='"+d.getTypeOfWork()+"',ship_dt_frm_ser_cntr='"+d.getShipDtFrmSerCntr()+"',disp_adv_no='"+d.getDispAdvNo()+"',dc_no='"+d.getDcNo()+"',ship_date_from_commercial='"+d.getShipDateFromCommercial()+"',\n" +
                                       "repaired_brd_adv_no='"+d.getRepairedBrdAdvNo()+"',month='"+d.getMonth()+"',year='"+d.getYear()+"',ra_engnr='"+d.getRaEngnr()+"',components_usedfor_repair='"+d.getComponentsUsedforRepair()+"',technical_remarks='"+d.getTechnicalRemarks()+"',comrcl_dtl_inv_rmrk='"+d.getComrclDtlInvRmrk()+"',model_config='"+d.getModelConfig()+"',status='"+d.getStatus()+"',destination='"+d.getDestination()+"' where ser_id='"+d.getSerId()+"'");
           
         st.executeUpdate();
         con.close();
    }

public void updateService(service_master d) throws ClassNotFoundException, SQLException
{
    Connection con=DbConnection.getConnection();
            PreparedStatement st=null;
            st=con.prepareStatement("UPDATE service_master SET ra_engnr='"+d.getRaEngnr()+"',"
                + "docket_no='"+d.getDocketNo()+"',repaired_brd_adv_no='"+d.getRepairedBrdAdvNo()+"',rep_gir_no='"+d.getRepGirNo()+"',def_unit_gir_no='"+d.getDefUnitGirNo()+"',technical_remarks='"+d.getTechnicalRemarks()+"',"
                + "components_usedfor_repair='"+d.getComponentsUsedforRepair()+"',\n" +
                    "repaired_brd_stk_date='"+d.getRepairedBrdStkDate()+"',final_remarks='"+d.getFinalRemarks()+"',type_of_work='"+d.getTypeOfWork()+"',"
                + "ship_dt_frm_ser_cntr='"+d.getShipDtFrmSerCntr()+"',disp_adv_no='"+d.getDispAdvNo()+"',ship_date_from_commercial='"+d.getShipDateFromCommercial()+"',dc_no='"+d.getDcNo()+"',month='"+d.getMonth()+"',year='"+d.getYear()+"',despatch_date='"+d.getDespatchDate()+"',part_Number='"+d.getPart_Number()+"',compatible_models='"+d.getCompatibleModels()+"',cost='"+d.getCost()+"',report_type='"+d.getReport_type()+"',destination='"+d.getDestination()+"' where ser_id='"+d.getSerId()+"'");
            st.executeUpdate();
            con.close();
}


public void delete(int id) throws ClassNotFoundException, SQLException{
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        st.executeUpdate("delete from service_master where ser_id='"+id+"'"); 
        con.close();
    }

public static void Excel(HttpServletRequest request,HttpServletResponse response) {
		String from=request.getParameter("from");
		System.out.println(from+"from date");
		String to=request.getParameter("to");
		System.out.println("to date   "+to);
	
	try {
			Connection con=DbConnection.getConnection();
			PreparedStatement ps = null;
			ps= con.prepareStatement("Select * from service_master where entry_date between '"+from+"' and '"+to+"'");
			ResultSet rs = ps.executeQuery();
                        String path = request.getServletContext().getRealPath("/");
                        File f = new File ((path +"Export"));
                        f.mkdir();
                        String xls = request.getServletContext().getRealPath("/Export")+"/ServiceList.xls"; 
                        
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
//			rowhead.createCell((short) 0).setCellValue("Ser_ID");
                       rowhead.createCell((short) 0).setCellValue("DIVISION");
                        rowhead.createCell((short) 1).setCellValue("ENTRY DATE");
                        rowhead.createCell((short) 2).setCellValue("SC_REF_NO");
                        rowhead.createCell((short) 3).setCellValue("SC_ENGINEER");
                        rowhead.createCell((short) 4).setCellValue("RA_ENGINEER");
                        rowhead.createCell((short) 5).setCellValue("FRN_NO");
                        rowhead.createCell((short) 6).setCellValue("FRN_DATE");
                       /* rowhead.createCell((short) 8).setCellValue("DOCKET_NO");
                        rowhead.createCell((short) 9).setCellValue("DESPATCH_DATE");*/
                        rowhead.createCell((short) 7).setCellValue("SER_COMM_INWARD_DATE");
                        rowhead.createCell((short) 8).setCellValue("SER_CENTRE_RECEIVED_DATE");
                        rowhead.createCell((short) 9).setCellValue("STK_CUST");
                        rowhead.createCell((short) 10).setCellValue("REGION");
                        rowhead.createCell((short) 11).setCellValue("BRANCH");
                        rowhead.createCell((short) 12).setCellValue("ENGINEER");
                        rowhead.createCell((short) 13).setCellValue("DEALER_NAME");
                        rowhead.createCell((short) 14).setCellValue("CUST_NAME");
                        rowhead.createCell((short) 15).setCellValue("SUPPLIER_NAME");
                        rowhead.createCell((short) 16).setCellValue("PRODUCT_MODEL");
                        rowhead.createCell((short) 17).setCellValue("UNIT_SLNO");
                        rowhead.createCell((short) 18).setCellValue("DOD");
                        rowhead.createCell((short) 19).setCellValue("UNIT_STATUS");
                        rowhead.createCell((short) 20).setCellValue("MOD_BRD_NAME");
                        rowhead.createCell((short) 21).setCellValue("DEF_MOD_BRD_NAME");
                        rowhead.createCell((short) 22).setCellValue("DEF_TYPE");
                        rowhead.createCell((short) 23).setCellValue("TYPE_OF_ACC");
                        rowhead.createCell((short) 24).setCellValue("DEF_PART_SN");
                        rowhead.createCell((short) 25).setCellValue("DEF_GIR_NO");
                        rowhead.createCell((short) 26).setCellValue("REP_TYPE");
                        rowhead.createCell((short) 27).setCellValue("REP_GIR_NO");
                        rowhead.createCell((short) 28).setCellValue("DEF_UNIT_GIR_NO");
                        rowhead.createCell((short) 29).setCellValue("FIELD_REMARKS");
                        rowhead.createCell((short) 30).setCellValue("TECHNICAL_REMARKS");
                        rowhead.createCell((short) 31).setCellValue("COMPONENTS_USEDFOR_REPAIR");
                        rowhead.createCell((short) 32).setCellValue("REPAIRED_BRD_STK_DATE");
                        rowhead.createCell((short) 33).setCellValue("FINAL_REMARKS");
                        rowhead.createCell((short) 34).setCellValue("TYPE_OF_WORK");
                        rowhead.createCell((short) 35).setCellValue("SHIP_DT_FRM_SER_CNTR");
                        rowhead.createCell((short) 36).setCellValue("DISP_ADV_NO");
                        rowhead.createCell((short) 37).setCellValue("SHIP_DATE_FROM_COMMERCIAL");
                        rowhead.createCell((short) 38).setCellValue("DC_NO");
                        rowhead.createCell((short) 39).setCellValue("COMRCL_DTL_INV_RMRK");
                        rowhead.createCell((short) 40).setCellValue("PENDING DAYS(PFRN)");
                        rowhead.createCell((short) 41).setCellValue("PENDING DAYS(OBP)");
                        rowhead.createCell((short) 42).setCellValue("PENDING DAYS(URP)");
                        rowhead.createCell((short) 43).setCellValue("PENDING DAYS(SCC)");
                        rowhead.createCell((short) 44).setCellValue("STATUS");
                        rowhead.createCell((short) 45).setCellValue("PART_NUMBER");
                        rowhead.createCell((short) 46).setCellValue("COMPATIBLE_MODELS");
                        rowhead.createCell((short) 47).setCellValue("COST");
                        rowhead.createCell((short) 48).setCellValue("REPORT_TYPE");
                        rowhead.createCell((short) 49).setCellValue("CURRENT DATE");
                        rowhead.createCell((short) 50).setCellValue("DESTINATION");
                        rowhead.createCell((short) 51).setCellValue("MODEL_CONFIG");
            //  System.out.println(rs.getFetchSize()+"the get fetcgh sizee iss");
			int index = 1;
                        while (rs.next()) {
				HSSFRow row = sheet.createRow((short) index);
//				row.createCell((short) 0).setCellValue(rs.getString(1));
                                row.createCell((short) 0).setCellValue(rs.getString(44));
                                row.createCell((short) 1).setCellValue(rs.getString(3));
                                row.createCell((short) 2).setCellValue(rs.getString(4));   
                                    String sc_engneer=EmployeeDao.geteName(rs.getString(5)); 
                                row.createCell((short) 3).setCellValue(sc_engneer);
                                String ra_name=EmployeeDao.geteName(rs.getString(6));
                                row.createCell((short) 4).setCellValue(ra_name);
                                row.createCell((short) 5).setCellValue(rs.getString(7));
                                row.createCell((short) 6).setCellValue(rs.getString(8));
                                /*row.createCell((short) 8).setCellValue(rs.getString(9));
                                row.createCell((short) 9).setCellValue(rs.getString(10));*/
                                row.createCell((short) 7).setCellValue(rs.getString(11));
                                   String date_from_serCenter=rs.getString(12);
                                row.createCell((short) 8).setCellValue(rs.getString(12));
                                    String stk_cust=DropdownDao.getddName(rs.getString(13)); 
                                row.createCell((short) 9).setCellValue(stk_cust);
                                row.createCell((short) 10).setCellValue(rs.getString(14));
                                    String branchname=BranchDao.getbName(rs.getString(15));     
                                row.createCell((short) 11).setCellValue(branchname);
                                
                                String enggname=EmployeeDao.getname(rs.getString(16));
                                row.createCell((short) 12).setCellValue(enggname);
                                
                                    String dealer_name=DealerDao.getdName(rs.getString(17));
                                row.createCell((short) 13).setCellValue(dealer_name);
                                row.createCell((short) 14).setCellValue(rs.getString(18));
                                 row.createCell((short) 15).setCellValue(rs.getString(19));
                                  String model=ModelDao.getModelname(rs.getString(20));
                               row.createCell((short) 16).setCellValue(model);
                                row.createCell((short) 17).setCellValue(rs.getString(21));
                                row.createCell((short) 18).setCellValue(rs.getString(22));
                                    String unit_status=DropdownDao.getddName(rs.getString(23));
                                row.createCell((short) 19).setCellValue(unit_status);
                                row.createCell((short) 20).setCellValue(rs.getString(24));
                                row.createCell((short) 21).setCellValue(rs.getString(25));
                                    String def_type=DropdownDao.getddName(rs.getString(26));
                                row.createCell((short) 22).setCellValue(def_type);
                                    String type_of_acc=DropdownDao.getddName(rs.getString(27));
                                row.createCell((short) 23).setCellValue(type_of_acc);
                                row.createCell((short) 24).setCellValue(rs.getString(28));
                                row.createCell((short) 25).setCellValue(rs.getString(29));
                                    String rep_type=DropdownDao.getddName(rs.getString(30));
                                row.createCell((short) 26).setCellValue(rep_type);
                                row.createCell((short) 27).setCellValue(rs.getString(31));
                                row.createCell((short) 28).setCellValue(rs.getString(32));
                                row.createCell((short) 29).setCellValue(rs.getString(33));
                                row.createCell((short) 30).setCellValue(rs.getString(34));
                                row.createCell((short) 31).setCellValue(rs.getString(35));
                                row.createCell((short) 32).setCellValue(rs.getString(36));
                                row.createCell((short) 33).setCellValue(rs.getString(37));
                                   String type_of_work=DropdownDao.getddName(rs.getString(38));
                                row.createCell((short) 34).setCellValue(type_of_work);
                                row.createCell((short) 35).setCellValue(rs.getString(39));
                                row.createCell((short) 36).setCellValue(rs.getString(40));
                                row.createCell((short) 37).setCellValue(rs.getString(41));
                                row.createCell((short) 38).setCellValue(rs.getString(42));
                                row.createCell((short) 39).setCellValue(rs.getString(43));
                                
//                                for pending days
                               String shipDate1=rs.getString(39);
//                                for PFRN
                                if(shipDate1.equalsIgnoreCase(""))
                                {
                                    String startDateString = date_from_serCenter;
                                        DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
                                        java.util.Date startDate;
                                    
                                            startDate = df.parse(startDateString);
                                            String newDateString = df.format(startDate);
                                    java.util.Date currentdate;
                                   java.util.Date dateobj = new java.util.Date();
                                   String  current_date=df.format(dateobj);
                                   currentdate=df.parse(current_date);
                                  
                               SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                                            java.util.Date d1 = null;
                                            java.util.Date d2 = null;

//                                            try {
                                                    d1 = startDate;
                                                    d2 = currentdate;

                                                    //in milliseconds
                                                    long diff = d2.getTime() - d1.getTime();
                                                    long diffDays = diff / (24 * 60 * 60 * 1000);
                                row.createCell((short) 40).setCellValue(diffDays);
                                }
                                else
                                {
                        String date1=date_from_serCenter;;
                        DateFormat df11=new SimpleDateFormat("dd-MM-yyyy");
                        
                        java.util.Date SCdate;
                        SCdate=df11.parse(date1);
                        
                       
                        String date2=shipDate1;
                        java.util.Date shipDate;
                        shipDate=df11.parse(date2);
                        
                        SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                       
                        java.util.Date d21=null;
                        java.util.Date d22=null;
                        
                        d21=SCdate;
                        d22=shipDate;
                        
                        long diffrnce=d22.getTime()-d21.getTime();
                        long difDays=diffrnce/(24 * 60 * 60 * 1000);
                                row.createCell((short) 40).setCellValue(difDays+" SC");
                                }
//                                ends

//                                for OBPending
                                if(shipDate1.equalsIgnoreCase(""))
                                {
                                    String startDateString = date_from_serCenter;
                                        DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
                                        java.util.Date startDate;
                                    
                                            startDate = df.parse(startDateString);
                                            String newDateString = df.format(startDate);
                                    java.util.Date currentdate;
                                   java.util.Date dateobj = new java.util.Date();
                                   String  current_date=df.format(dateobj);
                                   currentdate=df.parse(current_date);
                                  
                               SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                                            java.util.Date d1 = null;
                                            java.util.Date d2 = null;

//                                            try {
                                                    d1 = startDate;
                                                    d2 = currentdate;

                                                    //in milliseconds
                                                    long diff = d2.getTime() - d1.getTime();
                                                    long diffDays = diff / (24 * 60 * 60 * 1000);
                                row.createCell((short) 41).setCellValue(diffDays);
                                }
                                else
                                {
                        String date1=date_from_serCenter;;
                        DateFormat df11=new SimpleDateFormat("dd-MM-yyyy");
                        
                        java.util.Date SCdate;
                        SCdate=df11.parse(date1);
                        
                       
                        String date2=shipDate1;
                        java.util.Date shipDate;
                        shipDate=df11.parse(date2);
                        
                        SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                       
                        java.util.Date d21=null;
                        java.util.Date d22=null;
                        
                        d21=SCdate;
                        d22=shipDate;
                        
                        long diffrnce=d22.getTime()-d21.getTime();
                        long difDays=diffrnce/(24 * 60 * 60 * 1000);
                                row.createCell((short) 41).setCellValue(difDays+" OB");
                                }
//                                ends

//                            for URPending 
                                    String recv_date=rs.getString(36);
                                    
                                    if(recv_date.equalsIgnoreCase(""))
                                {
                                    String startDateString = date_from_serCenter;
                                        DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
                                        java.util.Date startDate;
                                    
                                            startDate = df.parse(startDateString);
                                            String newDateString = df.format(startDate);
                                    java.util.Date currentdate;
                                   java.util.Date dateobj = new java.util.Date();
                                   String  current_date=df.format(dateobj);
                                   currentdate=df.parse(current_date);
                                  
                               SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                                            java.util.Date d1 = null;
                                            java.util.Date d2 = null;

//                                            try {
                                                    d1 = startDate;
                                                    d2 = currentdate;

                                                    //in milliseconds
                                                    long diff = d2.getTime() - d1.getTime();
                                                    long diffDays = diff / (24 * 60 * 60 * 1000);
                                row.createCell((short) 42).setCellValue(diffDays);
                                }
                                else
                                {
                        String date1=date_from_serCenter;;
                        DateFormat df11=new SimpleDateFormat("dd-MM-yyyy");
                        
                        java.util.Date SCdate;
                        SCdate=df11.parse(date1);
                        
                       
                        String date2=recv_date;
                        java.util.Date shipDate;
                        shipDate=df11.parse(date2);
                        
                        SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                       
                        java.util.Date d21=null;
                        java.util.Date d22=null;
                        
                        d21=SCdate;
                        d22=shipDate;
                        
                        long diffrnce=d22.getTime()-d21.getTime();
                        long difDays=diffrnce/(24 * 60 * 60 * 1000);
                                row.createCell((short) 42).setCellValue(difDays+" UR");
                                }
//                                ends

//                                SC Completed
                                        if(recv_date.equalsIgnoreCase(""))
                                {
                                    String startDateString = date_from_serCenter;
                                        DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
                                        java.util.Date startDate;
                                    
                                            startDate = df.parse(startDateString);
                                            String newDateString = df.format(startDate);
                                    java.util.Date currentdate;
                                   java.util.Date dateobj = new java.util.Date();
                                   String  current_date=df.format(dateobj);
                                   currentdate=df.parse(current_date);
                                  
                               SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                                            java.util.Date d1 = null;
                                            java.util.Date d2 = null;

//                                            try {
                                                    d1 = startDate;
                                                    d2 = currentdate;

                                                    //in milliseconds
                                                    long diff = d2.getTime() - d1.getTime();
                                                    long diffDays = diff / (24 * 60 * 60 * 1000);
                                row.createCell((short) 43).setCellValue(diffDays);
                                }
                                else
                                {
                        String date1=date_from_serCenter;;
                        DateFormat df11=new SimpleDateFormat("dd-MM-yyyy");
                        
                        java.util.Date SCdate;
                        SCdate=df11.parse(date1);
                        
                       
                        String date2=recv_date;
                        java.util.Date shipDate;
                        shipDate=df11.parse(date2);
                        
                        SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                       
                        java.util.Date d21=null;
                        java.util.Date d22=null;
                        
                        d21=SCdate;
                        d22=shipDate;
                        
                        long diffrnce=d22.getTime()-d21.getTime();
                        long difDays=diffrnce/(24 * 60 * 60 * 1000);
                                row.createCell((short) 43).setCellValue(difDays+" UR");
                                }
                                        
                                        row.createCell((short) 44).setCellValue(rs.getString(49));
                                        row.createCell((short) 45).setCellValue(rs.getString(50));
                                        row.createCell((short) 46).setCellValue(rs.getString(51));
                                        row.createCell((short) 47).setCellValue(rs.getString(52));
                                        row.createCell((short) 48).setCellValue(rs.getString(54));
                                        row.createCell((short) 49).setCellValue(rs.getString(53));
                                        row.createCell((short) 50).setCellValue(rs.getString(55));
                                        row.createCell((short) 51).setCellValue(rs.getString(48));
                                       System.out.println(rs.getString(53)+"dest");
                                        
//                                    Ends
				index++;
			}
			FileOutputStream fileOut = new FileOutputStream(xls);
			wb.write(fileOut);
			fileOut.close();
                        
                         String outfile="ServiceList.xls";
                     response.setHeader("Content-disposition","attachment; filename="+outfile);          
                     FileInputStream in = new FileInputStream(xls);
                     OutputStream out = response.getOutputStream();
                     
                      byte[] buffer= new byte[8192]; // use bigger if you want
                     int length = 0;
                     
                     while ((length = in.read(buffer)) > 0){
                         out.write(buffer, 0, length);
                     }
                     in.close();
                     out.close();
                     
			rs.close();
			con.close();
		} catch (Exception e) {
                    e.printStackTrace();
		}
	} 

public static void ExcelForEngg(HttpServletRequest request,HttpServletResponse response) {
	  System.out.println("inside servise list daoo forr ennggggg");
	  
	  String from=request.getParameter("from");
		System.out.println(from+"from date");
		String to=request.getParameter("to");
		System.out.println("to date   "+to);
	  
	  
		try {
			
			   int nam=0;
               HttpSession session = request.getSession();
               nam= (int) session.getAttribute("loguserid");
               String one=EmployeeDao.getEmp(nam);
               int done=Integer.parseInt(one);
               String divName= EmployeeDao.getdivEmpName(done);
                   System.out.println(divName+"the div name isss");

			Connection con=DbConnection.getConnection();
			PreparedStatement ps = null;
			ps= con.prepareStatement("Select * from service_master where entry_date between '"+from+"' and '"+to+"' and division='"+divName+"'");
			ResultSet rs = ps.executeQuery();
                        String path = request.getServletContext().getRealPath("/");
                        File f = new File ((path +"Export"));
                        f.mkdir();
                        String xls = request.getServletContext().getRealPath("/Export")+"/ServiceList.xls"; 
                        
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
//			rowhead.createCell((short) 0).setCellValue("Ser_ID");
			 rowhead.createCell((short) 0).setCellValue("DIVISION");
             rowhead.createCell((short) 1).setCellValue("ENTRY DATE");
             rowhead.createCell((short) 2).setCellValue("SC_REF_NO");
             rowhead.createCell((short) 3).setCellValue("SC_ENGINEER");
             rowhead.createCell((short) 4).setCellValue("RA_ENGINEER");
             rowhead.createCell((short) 5).setCellValue("FRN_NO");
             rowhead.createCell((short) 6).setCellValue("FRN_DATE");
            /* rowhead.createCell((short) 8).setCellValue("DOCKET_NO");
             rowhead.createCell((short) 9).setCellValue("DESPATCH_DATE");*/
             rowhead.createCell((short) 7).setCellValue("SER_COMM_INWARD_DATE");
             rowhead.createCell((short) 8).setCellValue("SER_CENTRE_RECEIVED_DATE");
             rowhead.createCell((short) 9).setCellValue("STK_CUST");
             rowhead.createCell((short) 10).setCellValue("REGION");
             rowhead.createCell((short) 11).setCellValue("BRANCH");
             rowhead.createCell((short) 12).setCellValue("ENGINEER");
             rowhead.createCell((short) 13).setCellValue("DEALER_NAME");
             rowhead.createCell((short) 14).setCellValue("CUST_NAME");
             rowhead.createCell((short) 15).setCellValue("SUPPLIER_NAME");
             rowhead.createCell((short) 16).setCellValue("PRODUCT_MODEL");
             rowhead.createCell((short) 17).setCellValue("UNIT_SLNO");
             rowhead.createCell((short) 18).setCellValue("DOD");
             rowhead.createCell((short) 19).setCellValue("UNIT_STATUS");
             rowhead.createCell((short) 20).setCellValue("MOD_BRD_NAME");
             rowhead.createCell((short) 21).setCellValue("DEF_MOD_BRD_NAME");
             rowhead.createCell((short) 22).setCellValue("DEF_TYPE");
             rowhead.createCell((short) 23).setCellValue("TYPE_OF_ACC");
             rowhead.createCell((short) 24).setCellValue("DEF_PART_SN");
             rowhead.createCell((short) 25).setCellValue("DEF_GIR_NO");
             rowhead.createCell((short) 26).setCellValue("REP_TYPE");
             rowhead.createCell((short) 27).setCellValue("REP_GIR_NO");
             rowhead.createCell((short) 28).setCellValue("DEF_UNIT_GIR_NO");
             rowhead.createCell((short) 29).setCellValue("FIELD_REMARKS");
             rowhead.createCell((short) 30).setCellValue("TECHNICAL_REMARKS");
             rowhead.createCell((short) 31).setCellValue("COMPONENTS_USEDFOR_REPAIR");
             rowhead.createCell((short) 32).setCellValue("REPAIRED_BRD_STK_DATE");
             rowhead.createCell((short) 33).setCellValue("FINAL_REMARKS");
             rowhead.createCell((short) 34).setCellValue("TYPE_OF_WORK");
             rowhead.createCell((short) 35).setCellValue("SHIP_DT_FRM_SER_CNTR");
             rowhead.createCell((short) 36).setCellValue("DISP_ADV_NO");
             rowhead.createCell((short) 37).setCellValue("SHIP_DATE_FROM_COMMERCIAL");
             rowhead.createCell((short) 38).setCellValue("DC_NO");
             rowhead.createCell((short) 39).setCellValue("COMRCL_DTL_INV_RMRK");
             rowhead.createCell((short) 40).setCellValue("PENDING DAYS(PFRN)");
             rowhead.createCell((short) 41).setCellValue("PENDING DAYS(OBP)");
             rowhead.createCell((short) 42).setCellValue("PENDING DAYS(URP)");
             rowhead.createCell((short) 43).setCellValue("PENDING DAYS(SCC)");
             rowhead.createCell((short) 44).setCellValue("STATUS");
             rowhead.createCell((short) 45).setCellValue("PART_NUMBER");
             rowhead.createCell((short) 46).setCellValue("COMPATIBLE_MODELS");
             rowhead.createCell((short) 47).setCellValue("COST");
             rowhead.createCell((short) 48).setCellValue("REPORT_TYPE");
             rowhead.createCell((short) 49).setCellValue("CURRENT DATE");
             rowhead.createCell((short) 50).setCellValue("DESTINATION");
             rowhead.createCell((short) 51).setCellValue("MODEL_CONFIG");
                        
              //System.out.println(rs.getFetchSize()+"the get fetcgh sizee iss");
			int index = 1;
                        while (rs.next()) {
            				HSSFRow row = sheet.createRow((short) index);
//            				row.createCell((short) 0).setCellValue(rs.getString(1));
                                            row.createCell((short) 0).setCellValue(rs.getString(44));
                                            row.createCell((short) 1).setCellValue(rs.getString(3));
                                            row.createCell((short) 2).setCellValue(rs.getString(4));   
                                                String sc_engneer=EmployeeDao.geteName(rs.getString(5)); 
                                            row.createCell((short) 3).setCellValue(sc_engneer);
                                            String ra_name=EmployeeDao.geteName(rs.getString(6));
                                            row.createCell((short) 4).setCellValue(ra_name);
                                            row.createCell((short) 5).setCellValue(rs.getString(7));
                                            row.createCell((short) 6).setCellValue(rs.getString(8));
                                          /*  row.createCell((short) 8).setCellValue(rs.getString(9));
                                            row.createCell((short) 9).setCellValue(rs.getString(10));*/
                                            row.createCell((short) 7).setCellValue(rs.getString(11));
                                               String date_from_serCenter=rs.getString(12);
                                            row.createCell((short) 8).setCellValue(rs.getString(12));
                                                String stk_cust=DropdownDao.getddName(rs.getString(13)); 
                                            row.createCell((short) 9).setCellValue(stk_cust);
                                            row.createCell((short) 10).setCellValue(rs.getString(14));
                                                String branchname=BranchDao.getbName(rs.getString(15));     
                                            row.createCell((short) 11).setCellValue(branchname);
                                            
                                            String enggname=EmployeeDao.getname(rs.getString(16));
                                            row.createCell((short) 12).setCellValue(enggname);
                                            
                                                String dealer_name=DealerDao.getdName(rs.getString(17));
                                            row.createCell((short) 13).setCellValue(dealer_name);
                                            row.createCell((short) 14).setCellValue(rs.getString(18));
                                             row.createCell((short) 15).setCellValue(rs.getString(19));
                                              String model=ModelDao.getModelname(rs.getString(20));
                                           row.createCell((short) 16).setCellValue(model);
                                            row.createCell((short) 17).setCellValue(rs.getString(21));
                                            row.createCell((short) 18).setCellValue(rs.getString(22));
                                                String unit_status=DropdownDao.getddName(rs.getString(23));
                                            row.createCell((short) 19).setCellValue(unit_status);
                                            row.createCell((short) 20).setCellValue(rs.getString(24));
                                            row.createCell((short) 21).setCellValue(rs.getString(25));
                                                String def_type=DropdownDao.getddName(rs.getString(26));
                                            row.createCell((short) 22).setCellValue(def_type);
                                                String type_of_acc=DropdownDao.getddName(rs.getString(27));
                                            row.createCell((short) 23).setCellValue(type_of_acc);
                                            row.createCell((short) 24).setCellValue(rs.getString(28));
                                            row.createCell((short) 25).setCellValue(rs.getString(29));
                                                String rep_type=DropdownDao.getddName(rs.getString(30));
                                            row.createCell((short) 26).setCellValue(rep_type);
                                            row.createCell((short) 27).setCellValue(rs.getString(31));
                                            row.createCell((short) 28).setCellValue(rs.getString(32));
                                            row.createCell((short) 29).setCellValue(rs.getString(33));
                                            row.createCell((short) 30).setCellValue(rs.getString(34));
                                            row.createCell((short) 31).setCellValue(rs.getString(35));
                                            row.createCell((short) 32).setCellValue(rs.getString(36));
                                            row.createCell((short) 33).setCellValue(rs.getString(37));
                                               String type_of_work=DropdownDao.getddName(rs.getString(38));
                                            row.createCell((short) 34).setCellValue(type_of_work);
                                            row.createCell((short) 35).setCellValue(rs.getString(39));
                                            row.createCell((short) 36).setCellValue(rs.getString(40));
                                            row.createCell((short) 37).setCellValue(rs.getString(41));
                                            row.createCell((short) 38).setCellValue(rs.getString(42));
                                            row.createCell((short) 39).setCellValue(rs.getString(43));
                                            
//                                            for pending days
                                           String shipDate1=rs.getString(39);
//                                            for PFRN
                                            if(shipDate1.equalsIgnoreCase(""))
                                            {
                                                String startDateString = date_from_serCenter;
                                                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
                                                    java.util.Date startDate;
                                                
                                                        startDate = df.parse(startDateString);
                                                        String newDateString = df.format(startDate);
                                                java.util.Date currentdate;
                                               java.util.Date dateobj = new java.util.Date();
                                               String  current_date=df.format(dateobj);
                                               currentdate=df.parse(current_date);
                                              
                                           SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                                                        java.util.Date d1 = null;
                                                        java.util.Date d2 = null;

//                                                        try {
                                                                d1 = startDate;
                                                                d2 = currentdate;

                                                                //in milliseconds
                                                                long diff = d2.getTime() - d1.getTime();
                                                                long diffDays = diff / (24 * 60 * 60 * 1000);
                                            row.createCell((short) 40).setCellValue(diffDays);
                                            }
                                            else
                                            {
                                    String date1=date_from_serCenter;;
                                    DateFormat df11=new SimpleDateFormat("dd-MM-yyyy");
                                    
                                    java.util.Date SCdate;
                                    SCdate=df11.parse(date1);
                                    
                                   
                                    String date2=shipDate1;
                                    java.util.Date shipDate;
                                    shipDate=df11.parse(date2);
                                    
                                    SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                                   
                                    java.util.Date d21=null;
                                    java.util.Date d22=null;
                                    
                                    d21=SCdate;
                                    d22=shipDate;
                                    
                                    long diffrnce=d22.getTime()-d21.getTime();
                                    long difDays=diffrnce/(24 * 60 * 60 * 1000);
                                            row.createCell((short) 40).setCellValue(difDays+" SC");
                                            }
//                                            ends

//                                            for OBPending
                                            if(shipDate1.equalsIgnoreCase(""))
                                            {
                                                String startDateString = date_from_serCenter;
                                                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
                                                    java.util.Date startDate;
                                                
                                                        startDate = df.parse(startDateString);
                                                        String newDateString = df.format(startDate);
                                                java.util.Date currentdate;
                                               java.util.Date dateobj = new java.util.Date();
                                               String  current_date=df.format(dateobj);
                                               currentdate=df.parse(current_date);
                                              
                                           SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                                                        java.util.Date d1 = null;
                                                        java.util.Date d2 = null;

//                                                        try {
                                                                d1 = startDate;
                                                                d2 = currentdate;

                                                                //in milliseconds
                                                                long diff = d2.getTime() - d1.getTime();
                                                                long diffDays = diff / (24 * 60 * 60 * 1000);
                                            row.createCell((short) 41).setCellValue(diffDays);
                                            }
                                            else
                                            {
                                    String date1=date_from_serCenter;;
                                    DateFormat df11=new SimpleDateFormat("dd-MM-yyyy");
                                    
                                    java.util.Date SCdate;
                                    SCdate=df11.parse(date1);
                                    
                                   
                                    String date2=shipDate1;
                                    java.util.Date shipDate;
                                    shipDate=df11.parse(date2);
                                    
                                    SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                                   
                                    java.util.Date d21=null;
                                    java.util.Date d22=null;
                                    
                                    d21=SCdate;
                                    d22=shipDate;
                                    
                                    long diffrnce=d22.getTime()-d21.getTime();
                                    long difDays=diffrnce/(24 * 60 * 60 * 1000);
                                            row.createCell((short) 41).setCellValue(difDays+" OB");
                                            }
//                                            ends

//                                        for URPending 
                                                String recv_date=rs.getString(36);
                                                
                                                if(recv_date.equalsIgnoreCase(""))
                                            {
                                                String startDateString = date_from_serCenter;
                                                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
                                                    java.util.Date startDate;
                                                
                                                        startDate = df.parse(startDateString);
                                                        String newDateString = df.format(startDate);
                                                java.util.Date currentdate;
                                               java.util.Date dateobj = new java.util.Date();
                                               String  current_date=df.format(dateobj);
                                               currentdate=df.parse(current_date);
                                              
                                           SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                                                        java.util.Date d1 = null;
                                                        java.util.Date d2 = null;

//                                                        try {
                                                                d1 = startDate;
                                                                d2 = currentdate;

                                                                //in milliseconds
                                                                long diff = d2.getTime() - d1.getTime();
                                                                long diffDays = diff / (24 * 60 * 60 * 1000);
                                            row.createCell((short) 42).setCellValue(diffDays);
                                            }
                                            else
                                            {
                                    String date1=date_from_serCenter;;
                                    DateFormat df11=new SimpleDateFormat("dd-MM-yyyy");
                                    
                                    java.util.Date SCdate;
                                    SCdate=df11.parse(date1);
                                    
                                   
                                    String date2=recv_date;
                                    java.util.Date shipDate;
                                    shipDate=df11.parse(date2);
                                    
                                    SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                                   
                                    java.util.Date d21=null;
                                    java.util.Date d22=null;
                                    
                                    d21=SCdate;
                                    d22=shipDate;
                                    
                                    long diffrnce=d22.getTime()-d21.getTime();
                                    long difDays=diffrnce/(24 * 60 * 60 * 1000);
                                            row.createCell((short) 42).setCellValue(difDays+" UR");
                                            }
//                                            ends

//                                            SC Completed
                                                    if(recv_date.equalsIgnoreCase(""))
                                            {
                                                String startDateString = date_from_serCenter;
                                                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
                                                    java.util.Date startDate;
                                                
                                                        startDate = df.parse(startDateString);
                                                        String newDateString = df.format(startDate);
                                                java.util.Date currentdate;
                                               java.util.Date dateobj = new java.util.Date();
                                               String  current_date=df.format(dateobj);
                                               currentdate=df.parse(current_date);
                                              
                                           SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                                                        java.util.Date d1 = null;
                                                        java.util.Date d2 = null;

//                                                        try {
                                                                d1 = startDate;
                                                                d2 = currentdate;

                                                                //in milliseconds
                                                                long diff = d2.getTime() - d1.getTime();
                                                                long diffDays = diff / (24 * 60 * 60 * 1000);
                                            row.createCell((short) 43).setCellValue(diffDays);
                                            }
                                            else
                                            {
                                    String date1=date_from_serCenter;;
                                    DateFormat df11=new SimpleDateFormat("dd-MM-yyyy");
                                    
                                    java.util.Date SCdate;
                                    SCdate=df11.parse(date1);
                                    
                                   
                                    String date2=recv_date;
                                    java.util.Date shipDate;
                                    shipDate=df11.parse(date2);
                                    
                                    SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                                   
                                    java.util.Date d21=null;
                                    java.util.Date d22=null;
                                    
                                    d21=SCdate;
                                    d22=shipDate;
                                    
                                    long diffrnce=d22.getTime()-d21.getTime();
                                    long difDays=diffrnce/(24 * 60 * 60 * 1000);
                                            row.createCell((short) 43).setCellValue(difDays+" UR");
                                            }
                                                    
                                                    row.createCell((short) 44).setCellValue(rs.getString(49));
                                                    row.createCell((short) 45).setCellValue(rs.getString(50));
                                                    row.createCell((short) 46).setCellValue(rs.getString(51));
                                                    row.createCell((short) 47).setCellValue(rs.getString(52));
                                                    row.createCell((short) 48).setCellValue(rs.getString(54));
                                                    row.createCell((short) 49).setCellValue(rs.getString(53));
                                                    row.createCell((short) 50).setCellValue(rs.getString(55));
                                                    row.createCell((short) 51).setCellValue(rs.getString(48));
                                                    
//                                                Ends
            				index++;
            			}
			FileOutputStream fileOut = new FileOutputStream(xls);
			wb.write(fileOut);
			fileOut.close();
                        
                         String outfile="ServiceList.xls";
                     response.setHeader("Content-disposition","attachment; filename="+outfile);          
                     FileInputStream in = new FileInputStream(xls);
                     OutputStream out = response.getOutputStream();
                     
                      byte[] buffer= new byte[8192]; // use bigger if you want
                     int length = 0;
                     
                     while ((length = in.read(buffer)) > 0){
                         out.write(buffer, 0, length);
                     }
                     in.close();
                     out.close();
                     
			rs.close();
			con.close();
		
			
			
			
			
			/*
                       int nam=0;
               HttpSession session = request.getSession();
               nam= (int) session.getAttribute("loguserid");
               String one=EmployeeDao.getEmp(nam);
               int done=Integer.parseInt(one);
               String divName= EmployeeDao.getdivEmpName(done);
                   System.out.println(divName+"the div name isss");     
			Connection con=DbConnection.getConnection();
			PreparedStatement ps = null;
			//ps= con.prepareStatement("Select * from service_master s WHERE s.division='"+divName+"'");
			ps= con.prepareStatement("Select * from service_master");
        
			ResultSet rs = ps.executeQuery();
                        String path = request.getServletContext().getRealPath("/");
                        System.out.println(path+"11");
                        File f = new File ((path +"export"));
                        f.mkdir();
                        String xls = request.getServletContext().getRealPath("/export")+"/ServiceList.xls"; 
                        
               System.out.println("before excel");         
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
//			rowhead.createCell((short) 0).setCellValue("Ser_ID");
                        rowhead.createCell((short) 1).setCellValue("DIVISION");
                        rowhead.createCell((short) 2).setCellValue("ENTRY DATE");
                        rowhead.createCell((short) 3).setCellValue("SC_REF_NO");
                        rowhead.createCell((short) 4).setCellValue("SC_ENGINEER");
                        rowhead.createCell((short) 5).setCellValue("RA_ENGINEER");
                        rowhead.createCell((short) 6).setCellValue("FRN_NO");
                        rowhead.createCell((short) 7).setCellValue("FRN_DATE");
                        rowhead.createCell((short) 8).setCellValue("DOCKET_NO");
                        rowhead.createCell((short) 9).setCellValue("DESPATCH_DATE");
                        rowhead.createCell((short) 10).setCellValue("SER_COMM_INWARD_DATE");
                        rowhead.createCell((short) 11).setCellValue("SER_CENTRE_RECEIVED_DATE");
                        rowhead.createCell((short) 12).setCellValue("STK_CUST");
                        rowhead.createCell((short) 13).setCellValue("REGION");
                        rowhead.createCell((short) 14).setCellValue("BRANCH");
                        rowhead.createCell((short) 15).setCellValue("ENGINEER_ID");
                        rowhead.createCell((short) 16).setCellValue("DEALER_NAME");
                        rowhead.createCell((short) 17).setCellValue("CUST_NAME");
                        rowhead.createCell((short) 18).setCellValue("SUPPLIER_NAME");
                        rowhead.createCell((short) 19).setCellValue("PRODUCT_MODEL");
                        rowhead.createCell((short) 20).setCellValue("UNIT_SLNO");
                        rowhead.createCell((short) 21).setCellValue("DOD");
                        rowhead.createCell((short) 22).setCellValue("UNIT_STATUS");
                        rowhead.createCell((short) 23).setCellValue("MOD_BRD_NAME");
                        rowhead.createCell((short) 24).setCellValue("DEF_MOD_BRD_NAME");
                        rowhead.createCell((short) 25).setCellValue("DEF_TYPE");
                        rowhead.createCell((short) 26).setCellValue("TYPE_OF_ACC");
                        rowhead.createCell((short) 27).setCellValue("DEF_PART_SN");
                        rowhead.createCell((short) 28).setCellValue("DEF_GIR_NO");
                        rowhead.createCell((short) 29).setCellValue("REP_TYPE");
                        rowhead.createCell((short) 30).setCellValue("REP_GIR_NO");
                        rowhead.createCell((short) 31).setCellValue("DEF_UNIT_GIR_NO");
                        rowhead.createCell((short) 32).setCellValue("FIELD_REMARKS");
                        rowhead.createCell((short) 33).setCellValue("TECHNICAL_REMARKS");
                        rowhead.createCell((short) 34).setCellValue("COMPONENTS_USEDFOR_REPAIR");
                        rowhead.createCell((short) 35).setCellValue("REPAIRED_BRD_STK_DATE");
                        rowhead.createCell((short) 36).setCellValue("FINAL_REMARKS");
                        rowhead.createCell((short) 37).setCellValue("TYPE_OF_WORK");
                        rowhead.createCell((short) 38).setCellValue("SHIP_DT_FRM_SER_CNTR");
                        rowhead.createCell((short) 39).setCellValue("DISP_ADV_NO");
                        rowhead.createCell((short) 40).setCellValue("SHIP_DATE_FROM_COMMERCIAL");
                        rowhead.createCell((short) 41).setCellValue("DC_NO");
                        rowhead.createCell((short) 42).setCellValue("COMRCL_DTL_INV_RMRK");
                        rowhead.createCell((short) 43).setCellValue("PENDING DAYS(PFRN)");
                        rowhead.createCell((short) 44).setCellValue("PENDING DAYS(OBP)");
                        rowhead.createCell((short) 45).setCellValue("PENDING DAYS(URP)");
                        rowhead.createCell((short) 46).setCellValue("PENDING DAYS(SCC)");

			int index = 1;
			System.out.println(rs.getFetchSize()+"the sizee isss");
                        while (rs.next()) {
                        	System.out.println("inside whileee");
				HSSFRow row = sheet.createRow((short) index);
//				row.createCell((short) 0).setCellValue(rs.getString(1));
				System.out.println(rs.getString(44)+"firsttt");
                                row.createCell((short) 1).setCellValue(rs.getString(44));
                                row.createCell((short) 2).setCellValue(rs.getString(3));
                                row.createCell((short) 3).setCellValue(rs.getString(4));   
                                    String sc_engneer=EmployeeDao.geteName(rs.getString(5)); 
                                row.createCell((short) 4).setCellValue(sc_engneer);
                                 String ra_name=EmployeeDao.geteName(rs.getString(6));
                                row.createCell((short) 5).setCellValue(ra_name);
                                row.createCell((short) 6).setCellValue(rs.getString(7));
                                row.createCell((short) 7).setCellValue(rs.getString(8));
                                row.createCell((short) 8).setCellValue(rs.getString(9));
                                row.createCell((short) 9).setCellValue(rs.getString(10));
                                row.createCell((short) 10).setCellValue(rs.getString(11));
                                   String date_from_serCenter=rs.getString(12);
                                row.createCell((short) 11).setCellValue(rs.getString(12));
                                    String stk_cust=DropdownDao.getddName(rs.getString(13)); 
                                row.createCell((short) 12).setCellValue(stk_cust);
                                row.createCell((short) 13).setCellValue(rs.getString(14));
                                    String branchname=BranchDao.getbName(rs.getString(15));     
                                row.createCell((short) 14).setCellValue(branchname);
                                String enggname=EmployeeDao.getname(rs.getString(16));
                                row.createCell((short) 15).setCellValue(enggname);

                                    String dealer_name=DealerDao.getdName(rs.getString(17));
                                row.createCell((short) 16).setCellValue(dealer_name);
                                row.createCell((short) 17).setCellValue(rs.getString(18));
                                
                                row.createCell((short) 18).setCellValue(rs.getString(19));
                                  String model=ModelDao.getModelname(rs.getString(20));
                               row.createCell((short) 19).setCellValue(model);
                                
                                row.createCell((short) 20).setCellValue(rs.getString(21));
                                row.createCell((short) 21).setCellValue(rs.getString(22));
                                    String unit_status=DropdownDao.getddName(rs.getString(23));
                                row.createCell((short) 22).setCellValue(unit_status);
                                row.createCell((short) 23).setCellValue(rs.getString(24));
                                row.createCell((short) 24).setCellValue(rs.getString(25));
                                    String def_type=DropdownDao.getddName(rs.getString(26));
                                row.createCell((short) 25).setCellValue(def_type);
                                    String type_of_acc=DropdownDao.getddName(rs.getString(27));
                                row.createCell((short) 26).setCellValue(type_of_acc);
                                row.createCell((short) 27).setCellValue(rs.getString(28));
                                row.createCell((short) 28).setCellValue(rs.getString(29));
                                    String rep_type=DropdownDao.getddName(rs.getString(30));
                                row.createCell((short) 29).setCellValue(rep_type);
                                row.createCell((short) 30).setCellValue(rs.getString(31));
                                row.createCell((short) 31).setCellValue(rs.getString(32));
                                row.createCell((short) 32).setCellValue(rs.getString(33));
                                row.createCell((short) 33).setCellValue(rs.getString(34));
                                row.createCell((short) 34).setCellValue(rs.getString(35));
                                row.createCell((short) 35).setCellValue(rs.getString(36));
                                row.createCell((short) 36).setCellValue(rs.getString(37));
                                   String type_of_work=DropdownDao.getddName(rs.getString(38));
                                row.createCell((short) 37).setCellValue(type_of_work);
                                row.createCell((short) 38).setCellValue(rs.getString(39));//ship date from service center
                                row.createCell((short) 39).setCellValue(rs.getString(40));
                                row.createCell((short) 40).setCellValue(rs.getString(41));
                                row.createCell((short) 41).setCellValue(rs.getString(42));
                                row.createCell((short) 42).setCellValue(rs.getString(43));
                                
                                String shipDate1=rs.getString(39);
                                System.out.println("after excel");
//                                for PFRN
                                if(shipDate1.equalsIgnoreCase(""))
                                {
                                    String startDateString = date_from_serCenter;
                                        DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
                                        java.util.Date startDate;
                                    
                                            startDate = df.parse(startDateString);
                                            String newDateString = df.format(startDate);
                                    java.util.Date currentdate;
                                   java.util.Date dateobj = new java.util.Date();
                                   String  current_date=df.format(dateobj);
                                   currentdate=df.parse(current_date);
                                  
                               SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                                            java.util.Date d1 = null;
                                            java.util.Date d2 = null;

//                                            try {
                                                    d1 = startDate;
                                                    d2 = currentdate;

                                                    //in milliseconds
                                                    long diff = d2.getTime() - d1.getTime();
                                                    long diffDays = diff / (24 * 60 * 60 * 1000);
                                row.createCell((short) 43).setCellValue(diffDays);
                                }
                                else
                                {
                        String date1=date_from_serCenter;;
                        DateFormat df11=new SimpleDateFormat("dd-MM-yyyy");
                        
                        java.util.Date SCdate;
                        SCdate=df11.parse(date1);
                        
                       
                        String date2=shipDate1;
                        java.util.Date shipDate;
                        shipDate=df11.parse(date2);
                        
                        SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                       
                        java.util.Date d21=null;
                        java.util.Date d22=null;
                        
                        d21=SCdate;
                        d22=shipDate;
                        
                        long diffrnce=d22.getTime()-d21.getTime();
                        long difDays=diffrnce/(24 * 60 * 60 * 1000);
                                row.createCell((short) 43).setCellValue(difDays+" SC");
                                }
//                                ends

//                                for OBPending
                                if(shipDate1.equalsIgnoreCase(""))
                                {
                                    String startDateString = date_from_serCenter;
                                        DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
                                        java.util.Date startDate;
                                    
                                            startDate = df.parse(startDateString);
                                            String newDateString = df.format(startDate);
                                    java.util.Date currentdate;
                                   java.util.Date dateobj = new java.util.Date();
                                   String  current_date=df.format(dateobj);
                                   currentdate=df.parse(current_date);
                                  
                               SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                                            java.util.Date d1 = null;
                                            java.util.Date d2 = null;

//                                            try {
                                                    d1 = startDate;
                                                    d2 = currentdate;

                                                    //in milliseconds
                                                    long diff = d2.getTime() - d1.getTime();
                                                    long diffDays = diff / (24 * 60 * 60 * 1000);
                                row.createCell((short) 44).setCellValue(diffDays);
                                }
                                else
                                {
                        String date1=date_from_serCenter;;
                        DateFormat df11=new SimpleDateFormat("dd-MM-yyyy");
                        
                        java.util.Date SCdate;
                        SCdate=df11.parse(date1);
                        
                       
                        String date2=shipDate1;
                        java.util.Date shipDate;
                        shipDate=df11.parse(date2);
                        
                        SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                       
                        java.util.Date d21=null;
                        java.util.Date d22=null;
                        
                        d21=SCdate;
                        d22=shipDate;
                        
                        long diffrnce=d22.getTime()-d21.getTime();
                        long difDays=diffrnce/(24 * 60 * 60 * 1000);
                                row.createCell((short) 44).setCellValue(difDays+" OB");
                                }
//                                ends

//                            for URPending 
                                    String recv_date=rs.getString(36);
                                    
                                    if(recv_date.equalsIgnoreCase(""))
                                {
                                    String startDateString = date_from_serCenter;
                                        DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
                                        java.util.Date startDate;
                                    
                                            startDate = df.parse(startDateString);
                                            String newDateString = df.format(startDate);
                                    java.util.Date currentdate;
                                   java.util.Date dateobj = new java.util.Date();
                                   String  current_date=df.format(dateobj);
                                   currentdate=df.parse(current_date);
                                  
                               SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                                            java.util.Date d1 = null;
                                            java.util.Date d2 = null;

//                                            try {
                                                    d1 = startDate;
                                                    d2 = currentdate;

                                                    //in milliseconds
                                                    long diff = d2.getTime() - d1.getTime();
                                                    long diffDays = diff / (24 * 60 * 60 * 1000);
                                row.createCell((short) 45).setCellValue(diffDays);
                                }
                                else
                                {
                        String date1=date_from_serCenter;;
                        DateFormat df11=new SimpleDateFormat("dd-MM-yyyy");
                        
                        java.util.Date SCdate;
                        SCdate=df11.parse(date1);
                        
                       
                        String date2=recv_date;
                        java.util.Date shipDate;
                        shipDate=df11.parse(date2);
                        
                        SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                       
                        java.util.Date d21=null;
                        java.util.Date d22=null;
                        
                        d21=SCdate;
                        d22=shipDate;
                        
                        long diffrnce=d22.getTime()-d21.getTime();
                        long difDays=diffrnce/(24 * 60 * 60 * 1000);
                                row.createCell((short) 45).setCellValue(difDays+" UR");
                                }
//                                ends

//                                SC Completed
                                        if(recv_date.equalsIgnoreCase(""))
                                {
                                    String startDateString = date_from_serCenter;
                                        DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
                                        java.util.Date startDate;
                                    
                                            startDate = df.parse(startDateString);
                                            String newDateString = df.format(startDate);
                                    java.util.Date currentdate;
                                   java.util.Date dateobj = new java.util.Date();
                                   String  current_date=df.format(dateobj);
                                   currentdate=df.parse(current_date);
                                  
                               SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                                            java.util.Date d1 = null;
                                            java.util.Date d2 = null;

//                                            try {
                                                    d1 = startDate;
                                                    d2 = currentdate;

                                                    //in milliseconds
                                                    long diff = d2.getTime() - d1.getTime();
                                                    long diffDays = diff / (24 * 60 * 60 * 1000);
                                row.createCell((short) 46).setCellValue(diffDays);
                                }
                                else
                                {
                        String date1=date_from_serCenter;;
                        DateFormat df11=new SimpleDateFormat("dd-MM-yyyy");
                        
                        java.util.Date SCdate;
                        SCdate=df11.parse(date1);
                        
                       
                        String date2=recv_date;
                        java.util.Date shipDate;
                        shipDate=df11.parse(date2);
                        
                        SimpleDateFormat simp=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                       
                        java.util.Date d21=null;
                        java.util.Date d22=null;
                        
                        d21=SCdate;
                        d22=shipDate;
                        
                        long diffrnce=d22.getTime()-d21.getTime();
                        long difDays=diffrnce/(24 * 60 * 60 * 1000);
                                row.createCell((short) 46).setCellValue(difDays+" UR");
                                }
//                                    Ends
				index++;
			}
			FileOutputStream fileOut = new FileOutputStream(xls);
			wb.write(fileOut);
			fileOut.close();
                        
                        
                         String outfile="ServiceList.xls";
                     response.setHeader("Content-disposition","attachment; filename="+outfile);          
                     FileInputStream in = new FileInputStream(xls);
                     OutputStream out = response.getOutputStream();
                     
                      byte[] buffer= new byte[8192]; // use bigger if you want
                     int length = 0;
                     
                     while ((length = in.read(buffer)) > 0){
                         out.write(buffer, 0, length);
                     }
                     in.close();
                     out.close();
                     
			rs.close();
			con.close();
		*/} catch (Exception e) {
		}
	}

public static String getDivisionName(int id) throws ClassNotFoundException, SQLException
{
    String name="";
    Connection con=null;
    con=DbConnection.getConnection();
    PreparedStatement ps=con.prepareStatement("SELECT DISTINCT division_name FROM service_master WHERE division='"+id+"'");
    ResultSet rs=ps.executeQuery();
    while(rs.next())
    {
        name=rs.getString("division_name");
    }
    con.close();
    return name;
}
public static void updateService_Status(int id,String status) throws ClassNotFoundException, SQLException{
	Connection con=null;
	con=DbConnection.getConnection();
    PreparedStatement st=con.prepareStatement("update service_master set status='"+status+"' where ser_id="+id);
    st.executeUpdate();
    con.close();
     
}

}
