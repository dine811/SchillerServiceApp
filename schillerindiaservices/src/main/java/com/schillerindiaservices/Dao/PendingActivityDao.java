/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Pendingactmaster;
import com.schillerindiaservices.common.UtilFunctions;
import com.schillerindiaservices.connection.DbConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ShineLoGics
 */
public class PendingActivityDao {
    public int Insert(Pendingactmaster pam) throws ClassNotFoundException, SQLException
    {
        int id=0;
        UtilFunctions utilfn = new UtilFunctions();
        Connection con=null;
        con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("INSERT INTO pendingact_master(division,sc_engg,"
                + "entry_date,initiate_date,model,pending_activity,responsible,pending_form,tar_closed_date,remarks,status_type,closed_date,sc_incharge_remark)\n" +
"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, pam.getDivision());
        ps.setString(2, pam.getScEngg());
        ps.setDate(3, utilfn.getDbDateFormat(pam.getEntryDate()));
        ps.setDate(4, utilfn.getDbDateFormat(pam.getInitiateDate()));
        ps.setString(5, pam.getModel());
        ps.setString(6, pam.getPendingActivity());
        ps.setString(7, pam.getResponsible());
        ps.setString(8, pam.getPendingForm());
        ps.setDate(9, utilfn.getDbDateFormat(pam.getTarClosedDate()));
        ps.setString(10, pam.getRemarks());
        ps.setString(11, pam.getStatusType());
        ps.setString(12, pam.getClosedDate());
        ps.setString(13, pam.getScInchargeRemark());
        id=ps.executeUpdate();
        con.close();
        return id;
    }
    public static Pendingactmaster getById(int id) throws ClassNotFoundException, SQLException
    {
        Pendingactmaster  pm=null;
        Connection con=null;
        con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT * from pendingact_master WHERE id='"+id+"'");
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            pm=new Pendingactmaster();
            pm.setId(id);
            pm.setDivision(rs.getString("division"));
            pm.setScEngg(rs.getString("sc_engg"));
            pm.setEntryDate(rs.getString("entry_date"));
            pm.setInitiateDate(rs.getString("initiate_date"));
            pm.setModel(rs.getString("model"));
            pm.setPendingActivity(rs.getString("pending_activity"));
            pm.setResponsible(rs.getString("responsible"));
            pm.setPendingForm(rs.getString("pending_form"));
            pm.setTarClosedDate(rs.getString("tar_closed_date"));
            pm.setRemarks(rs.getString("remarks"));
            pm.setStatusType(rs.getString("status_type"));
            pm.setClosedDate(rs.getString("closed_date"));
            pm.setScInchargeRemark(rs.getString("sc_incharge_remark"));
            
        }
        con.close();
        return pm;
        
    }
    
    public void update(Pendingactmaster pam) throws ClassNotFoundException, SQLException
    {
        Connection con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("update pendingact_master set pending_form='"+pam.getPendingForm()+"',closed_date='"+pam.getClosedDate()+"',"
                + "remarks='"+pam.getRemarks()+"',status_type='"+pam.getStatusType()+"',sc_incharge_remark='"+pam.getScInchargeRemark()+"',tar_closed_date='"+pam.getTarClosedDate()+"'"
                        + ",initiate_date='"+pam.getInitiateDate()+"',model='"+pam.getModel()+"',pending_activity='"+pam.getPendingActivity()+"'"
                                + ",responsible='"+pam.getResponsible()+"' where id='"+pam.getId()+"'");
        ps.executeUpdate();
        con.close();
    }
    public static String PendingActDivName(int d) throws ClassNotFoundException, SQLException
    {
        String div="";
        Connection con=null;
        con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT division FROM pendingact_master WHERE sc_engg='"+d+"'");
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            div=rs.getString("division");
        }
        con.close();
        return div;
    }
    public void delete(int id) throws ClassNotFoundException, SQLException
    {
        Connection con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("DELETE FROM pendingact_master WHERE id='"+id+"'");
        ps.executeUpdate();
        con.close();
    }
}
