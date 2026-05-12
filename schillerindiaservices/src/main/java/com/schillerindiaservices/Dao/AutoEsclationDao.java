/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Dealermaster;
import com.schillerindiaservices.bean.Mail_id_entry;
import com.schillerindiaservices.connection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kavin_rkz
 */
public class AutoEsclationDao {
    
     public int save(Mail_id_entry d) throws SQLException, ClassNotFoundException
    {  
        int status=0;  
        Connection con=null;
        con =DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("insert into mail_id_entry (mail_id,report_type,temp1) values(?,?,?)");
        ps.setString(1,d.getMail_id());
        ps.setString(2,d.getReport_type());
       ps.setString(3, d.getTemp1());
        //ps.setString(4, d.getDealerPhone());
        // ps.setInt(5,d.getDealerRegion());
        
        status=ps.executeUpdate();
       con.close();
    return status;  
    }  
    
    
    
    
     public static List<Mail_id_entry> getAllRecords() throws SQLException
    {  
        List<Mail_id_entry> list=new ArrayList<Mail_id_entry>();  
        Connection con=null;
        try{  
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select * from mail_id_entry");  
            
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
            	Mail_id_entry d=new Mail_id_entry();  
                d.setMail_id_entry_id(rs.getInt("mail_id_entry_id"));
                d.setMail_id(rs.getString("mail_id"));
                d.setReport_type(rs.getString("report_type"));
                d.setTemp1(rs.getString("temp1"));
                d.setTemp2(rs.getString("temp2"));
             
                list.add(d);  
            }  
        }catch(Exception e){}  
        con.close();
        return list;  
    }  
     
     
     public static List<String> getAllDealers() throws ClassNotFoundException, SQLException
    {  
        List<String> list=new ArrayList<String>();  
        Connection con=null;
        
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select dealer_name from dealermaster");  
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
                list.add(rs.getString("dealer_name"));  
            } 
            con.close();
            return list;  
    }
    
    
     
public static  Mail_id_entry getById(int id) throws SQLException
{
    Mail_id_entry d=null;
    Connection con=null;
    
        try
        {   con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select * from mail_id_entry where mail_id_entry_id='"+id+"'");  
            
            ResultSet rs=ps.executeQuery();  
            
            while(rs.next())
            {   d=new Mail_id_entry();
                d.setMail_id_entry_id(rs.getInt("mail_id_entry_id"));
                d.setMail_id(rs.getString("mail_id"));
                d.setReport_type(rs.getString("report_type"));
                d.setTemp1(rs.getString("temp1"));
                 d.setTemp2(rs.getString("temp2"));
               
            }  
        }catch(Exception e){}  
        con.close();
        return d;
   }




public void update(Mail_id_entry d) throws ClassNotFoundException, SQLException{
         Connection con=DbConnection.getConnection();
         PreparedStatement st=con.prepareStatement("update mail_id_entry set mail_id='"+d.getMail_id()+"',report_type='"+d.getReport_type()+"',temp1='"+d.getTemp1()+"' where mail_id_entry_id='"+d.getMail_id_entry_id()+"'");
         st.executeUpdate();
         con.close();
    }
public void delete(int id) throws ClassNotFoundException, SQLException{
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        st.executeUpdate("delete from mail_id_entry where mail_id_entry_id='"+id+"'");
        con.close();
    }



 public static String getdName(String dname) throws ClassNotFoundException, SQLException
    {  
       String list="";  
        Connection con=null;
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("SELECT dealer_name FROM dealermaster WHERE dealer_id='"+dname+"'");  
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list=rs.getString("dealer_name");  
        }  
        con.close();
        return list;  
    }  

}
