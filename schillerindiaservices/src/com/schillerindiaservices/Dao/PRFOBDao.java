/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Prfobmaster;
import com.schillerindiaservices.connection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ShineLoGics
 */
public class PRFOBDao {

    public static Prfobmaster getById(int id) throws ClassNotFoundException, SQLException {
       Prfobmaster prf=null;
       Connection con=DbConnection.getConnection();
       PreparedStatement ps=con.prepareStatement("SELECT * FROM prfobmaster WHERE id='"+id+"'");
       ResultSet rs=ps.executeQuery();
       while(rs.next())
       {
           prf=new Prfobmaster();
           prf.setId(id);
           prf.setDivision(rs.getString("division"));
           prf.setEntryDate(rs.getString("entry_date"));
           prf.setScEngg(rs.getString("sc_engg"));
           prf.setWorkType(rs.getString("work_type"));
           prf.setRaisedDate(rs.getString("raised_date"));
           prf.setReceivedDate(rs.getString("received_date"));
           prf.setRegion(rs.getString("region"));
           prf.setBranch(rs.getString("branch"));
           prf.setEngineer(rs.getString("engineer"));
           prf.setDealer(rs.getString("dealer"));
           prf.setModel(rs.getString("model"));
           prf.setSupplier(rs.getString("supplier"));
           prf.setWarrentyStatus(rs.getString("warrenty_status"));
           prf.setPrfobRefNo(rs.getString("prfob_ref_no"));
           prf.setCrmRefNo(rs.getString("crm_ref_no"));
           prf.setRemarks(rs.getString("remarks"));
           prf.setStatusType(rs.getString("status_type"));
           prf.setExecutedDate(rs.getString("executed_date"));
           prf.setPartType(rs.getString("part_type"));
           prf.setPartDescription(rs.getString("part_description"));
           prf.setReceive_date_from_sc(rs.getString("rec_dt_frm_sc"));
           
       }
       con.close();
        return prf; 
    }
    public  int save(Prfobmaster prf) throws ClassNotFoundException, SQLException
    {
    	System.out.println("insidee prf daoo");
        int id=0;
        Connection con=null;
        con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("INSERT INTO prfobmaster(division,entry_date,sc_engg,"
                + "work_type,raised_date,received_date,region,branch,engineer,dealer,model,supplier,"
                + "warrenty_status,prfob_ref_no,crm_ref_no,remarks,status_type,executed_date,part_type,part_description,rec_dt_frm_sc) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

        ps.setString(1, prf.getDivision());
         ps.setString(2, prf.getEntryDate());
          ps.setString(3, prf.getScEngg());
           ps.setString(4, prf.getWorkType());
            ps.setString(5, prf.getRaisedDate());
             ps.setString(6, prf.getReceivedDate());
              ps.setString(7, prf.getRegion());
               ps.setString(8, prf.getBranch());
                ps.setString(9, prf.getEngineer());
                 ps.setString(10, prf.getDealer());
                  ps.setString(11, prf.getModel());
                   ps.setString(12, prf.getSupplier());
                    ps.setString(13, prf.getWarrentyStatus());
                     ps.setString(14, prf.getPrfobRefNo());
                      ps.setString(15, prf.getCrmRefNo());
                       ps.setString(16, prf.getRemarks());
                        ps.setString(17, prf.getStatusType());
                         ps.setString(18, prf.getExecutedDate());
                          ps.setString(19, prf.getPartType());
                           ps.setString(20, prf.getPartDescription());
                            ps.setString(21, prf.getReceive_date_from_sc());
                             id=ps.executeUpdate();
                         
                  con.close();       
     return id;   
    }
    
    public static String PrfObDivName(String d) throws ClassNotFoundException, SQLException
    {
        String div="";
        Connection con=null;
        con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT division FROM prfobmaster WHERE division='"+d+"'");
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            div=rs.getString("division");
        }
        con.close();
        return div;
    }
    
    public void update(Prfobmaster prf) throws ClassNotFoundException, SQLException
    {
        Connection con=null;
        con=DbConnection.getConnection();      
        PreparedStatement ps=con.prepareStatement("update prfobmaster set crm_ref_no='"+prf.getCrmRefNo()+"',"
                + "remarks='"+prf.getRemarks()+"',status_type='"+prf.getStatusType()+"',"
                        + "executed_date='"+prf.getExecutedDate()+"',work_type='"+prf.getWorkType()+"'"
                                + ",received_date='"+prf.getReceivedDate()+"',raised_date='"+prf.getRaisedDate()+"' ,"
                                        + "warrenty_status='"+prf.getWarrentyStatus()+"',prfob_ref_no='"+prf.getPrfobRefNo()+"',part_type='"+prf.getPartType()+"'"
                                                + ",part_description='"+prf.getPartDescription()+"',sc_engg='"+prf.getScEngg()+"',rec_dt_frm_sc='"+prf.getReceive_date_from_sc()+"' where id='"+prf.getId()+"'");
        ps.executeUpdate();
        con.close();
    }

    public void delete(int id) throws ClassNotFoundException, SQLException {
      Connection con=DbConnection.getConnection();
      PreparedStatement ps=con.prepareStatement("DELETE FROM prfobmaster WHERE id='"+id+"'");
      ps.executeUpdate();
      con.close();
    }
}
