/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Nonsaleablemaster;
import com.schillerindiaservices.bean.Sparemaster;
import com.schillerindiaservices.connection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ShineLoGics
 */
public class Spares_dao {
    public static Sparemaster getById(int id) throws ClassNotFoundException, SQLException
    {
    	Sparemaster nsm=null;
        Connection con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT * FROM sparemaster WHERE id='"+id+"'");
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            nsm=new Sparemaster();
            nsm.setId(id);
            nsm.setDivision(rs.getString("division"));
           // nsm.setUnitDetails(rs.getString("entry_date"));
            nsm.setEntryDate(rs.getString("entry_date"));
            nsm.setSupplier(rs.getString("supplier"));
            nsm.setModel(rs.getString("model"));
            nsm.setSc_engg(rs.getString("sc_engg"));
            nsm.setPartNumber(rs.getString("partnumber"));
            nsm.setDef_Mod_Brd_name(rs.getString("def_Mod_Brd_name"));
           
            nsm.setReason(rs.getString("reason"));
            nsm.setReference(rs.getString("reference"));
            nsm.setGir_no(rs.getString("gir_no"));
            nsm.setIssued_by(rs.getString("issued_by"));
            nsm.setReturnable_status(rs.getString("returnable_status"));
            nsm.setRemarks(rs.getString("remarks"));
            nsm.setReturned_date(rs.getString("returned_date"));
           
            nsm.setFinalStatus(rs.getString("final_status"));
            nsm.setQty(rs.getString("qty"));
           
        }
        con.close();
        return nsm;
    }
    
    public int Insert(Sparemaster nsm) throws ClassNotFoundException, SQLException
    {
        int id=0;
        Connection con=DbConnection.getConnection();
       /* PreparedStatement ps=con.prepareStatement("INSERT INTO sparemaster(division"
                + "entry_date,supplier,model,partnumber,def_Mod_Brd_name,reason,reference,gir_no,sc_engg,issued_by,returnable_status,\n" +
"remarks,returned_date,final_status,\n" +
" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");*/
        PreparedStatement ps=con.prepareStatement("INSERT INTO sparemaster(division,entry_date,supplier,model,partnumber,def_Mod_Brd_name,reason,reference,gir_no,sc_engg,issued_by,returnable_status,remarks,returned_date,final_status,qty) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        
        
        
        ps.setString(1, nsm.getDivision());
        ps.setString(2, nsm.getEntryDate());
        ps.setString(3, nsm.getSupplier());
        ps.setString(4, nsm.getModel());
        ps.setString(5, nsm.getPartNumber());
        ps.setString(6, nsm.getDef_Mod_Brd_name());
        ps.setString(7, nsm.getReason());
        ps.setString(8, nsm.getReference());
        ps.setString(9, nsm.getGir_no());
        ps.setString(10, nsm.getSc_engg());
        ps.setString(11, nsm.getIssued_by());
        ps.setString(12, nsm.getReturnable_status());
        ps.setString(13, nsm.getRemarks());
        ps.setString(14, nsm.getReturned_date());
        ps.setString(15, nsm.getFinalStatus());
        ps.setString(16, nsm.getQty());
        id=ps.executeUpdate();
        
        con.close();
        return id;
    }
    
    public static String getDivName(int d) throws ClassNotFoundException, SQLException
    {
        String div="";
        Connection con=null;
        con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT division FROM nonsaleablemaster WHERE sc_engg='"+d+"'");
        
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            div=rs.getString("division");
        }
        con.close();
        return div;
    }

    public void update(Sparemaster nsm) throws ClassNotFoundException, SQLException {
        Connection con=DbConnection.getConnection();
                     
        PreparedStatement ps=con.prepareStatement("UPDATE sparemaster SET division='"+nsm.getDivision()+"',"
         + "entry_date='"+nsm.getEntryDate()+"',supplier='"+nsm.getSupplier()+"',"
         + "model='"+nsm.getModel()+"',partnumber='"+nsm.getPartNumber()+"'"
         + ",def_Mod_Brd_name='"+nsm.getDef_Mod_Brd_name()+"',reason='"+nsm.getReason()+"',"
         + "reference='"+nsm.getReference()+"',gir_no='"+nsm.getGir_no()+"',sc_engg='"+nsm.getSc_engg()+"'"
         + ",issued_by='"+nsm.getIssued_by()+"',returnable_status='"+nsm.getReturnable_status()+"',remarks='"+nsm.getRemarks()+"'"
         + ",returned_date='"+nsm.getReturned_date()+"',final_status='"+nsm.getFinalStatus()+"',qty='"+nsm.getQty()+"' WHERE id='"+nsm.getId()+"'");
        ps.executeUpdate();
        con.close();
    }
    public static String getDivision(String lid) throws ClassNotFoundException, SQLException
    {
        String name="";
        Connection con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT division FROM nonsaleablemaster WHERE sc_engg='"+lid+"'");
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            name=rs.getString("division");
        }
        con.close();
        return name;
    }
    public static String getDivision_name(int lid) throws ClassNotFoundException, SQLException
    {
    	System.out.println("inside get divisionnn");
        String name="";
        Connection con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT emp_division FROM emploeemaster WHERE emp_id='"+lid+"'");
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            name=rs.getString("emp_division");
        }
        con.close();
        System.out.println("the sysout name isss"+name);
        return name;
    }
    public void delete(int id) throws ClassNotFoundException, SQLException
    {
        Connection con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("DELETE FROM nonsaleablemaster WHERE id="+id+"");
        ps.executeUpdate();
        con.close();
    }
}
