/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Callregister;
import com.schillerindiaservices.bean.service_master;
import com.schillerindiaservices.connection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ShineLoGics
 */
public class CallRegisterDao {
    public int Insert(Callregister c) throws ClassNotFoundException, SQLException
    {
        int act=0;
        Connection con=null;
        con=DbConnection.getConnection();
        String s="INSERT INTO callregister (division,sc_engg,call_date,call_type,region,branch,dealer,engineer,model,type_of_call,type_of_communication,remarks,duration,status_type,entry_date)"
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps=con.prepareStatement(s);
        ps.setString(1, c.getDivision());
        ps.setString(2,c.getScEngg());
        ps.setString(3, c.getCallDate());
        ps.setString(4, c.getCallType());
        ps.setString(5, c.getRegion());
        ps.setString(6, c.getBranch());
        ps.setString(7, c.getDealer());
        ps.setString(8, c.getEngineer());
        ps.setString(9, c.getModel());
        ps.setString(10, c.getTypeOfCall());
        ps.setString(11, c.getTypeOfCommunication());
        ps.setString(12, c.getRemarks());
        ps.setString(13, c.getDuration());
        ps.setString(14, c.getStatusType());
        ps.setString(15, c.getEntryDate());
        act=ps.executeUpdate();
        con.close();
        return act;
    }
    
    public static  Callregister getById(int id) throws ClassNotFoundException, SQLException
{
    Callregister s=null;
    Connection con=null;
    con=DbConnection.getConnection();
    PreparedStatement ps=con.prepareStatement("SELECT * from callregister WHERE id='"+id+"'");
    ResultSet rs=ps.executeQuery();
    while(rs.next())
    {
       s=new Callregister();
       s.setId(id);
       s.setDivision(rs.getString("division"));
       s.setScEngg(rs.getString("sc_engg"));
       s.setCallDate(rs.getString("call_date"));
       s.setCallType(rs.getString("call_type"));
       s.setRegion(rs.getString("region"));
       s.setBranch(rs.getString("branch"));
       s.setDealer(rs.getString("dealer"));
       s.setEngineer(rs.getString("engineer"));
       s.setModel(rs.getString("model"));
       s.setTypeOfCall(rs.getString("type_of_call"));
       s.setTypeOfCommunication(rs.getString("type_of_communication"));
       s.setRemarks(rs.getString("remarks"));
       s.setDuration(rs.getString("duration"));
       s.setStatusType(rs.getString("status_type"));
    }
    con.close();
        return s;
    
}
    public void delete(int id) throws ClassNotFoundException, SQLException{
        Connection con=DbConnection.getConnection();
        PreparedStatement st=con.prepareStatement("delete from callregister where id='"+id+"'");
        st.executeUpdate();  
        con.close();
    }
    
    public static String getDivName(int d) throws ClassNotFoundException, SQLException
    {
        String div="";
        Connection con=null;
        con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT division FROM callregister WHERE sc_engg='"+d+"'");
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            div=rs.getString("division");
        }
        con.close();
        return div;
    }
    
             
    public static String getEnggName(String d) throws ClassNotFoundException, SQLException
    {
        String div="";
        Connection con=null;
        con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT emp_name FROM emploeemaster WHERE emp_id='"+d+"'");
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            div=rs.getString("emp_name");
        }
        con.close();
        return div;
    }
    
    public void update(Callregister cr) throws ClassNotFoundException, SQLException
    {
        Connection con=DbConnection.getConnection();
        PreparedStatement ps=null;   
        ps=con.prepareStatement("UPDATE callregister SET remarks='"+cr.getRemarks()+"',duration='"+cr.getDuration()+"',status_type='"+cr.getStatusType()+"',"
                + "call_date='"+cr.getCallDate()+"',call_type='"+cr.getCallType()+"',type_of_call='"+cr.getTypeOfCall()+"'"
                        + ",type_of_communication='"+cr.getTypeOfCommunication()+"' WHERE id='"+cr.getId()+"'");
        
        ps.executeUpdate();
        con.close();
    }
    
    public static String getDivision(String lid) throws ClassNotFoundException, SQLException
    {
        String name="";
        Connection con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT division FROM callregister WHERE sc_engg='"+lid+"'");
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            name=rs.getString("division");
        }
        con.close();
        return name;
    }
}
