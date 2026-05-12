/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Dealermaster;
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
public class DealerDao {
    
     public int save(Dealermaster d) throws SQLException, ClassNotFoundException
    {  
        int status=0;  
        Connection con=null;
        con =DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("insert into dealermaster (dealer_name,dealer_address,dealer_mail,dealer_phone,dealer_region_id) values(?,?,?,?,?)");
        ps.setString(1,d.getDealerName());
        ps.setString(2,d.getDealerAddress());
        ps.setString(3, d.getDealerMail());
        ps.setString(4, d.getDealerPhone());
         ps.setInt(5,d.getDealerRegion());
        
        status=ps.executeUpdate();
       con.close();
    return status;  
    }  
    
    
    
    
     public static List<Dealermaster> getAllRecords() throws SQLException
    {  
        List<Dealermaster> list=new ArrayList<Dealermaster>();  
        Connection con=null;
        try{  
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select * from dealermaster");  
            
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
                Dealermaster d=new Dealermaster();  
                d.setDealerId(rs.getInt("dealer_id"));
                d.setDealerName(rs.getString("dealer_name"));
                d.setDealerAddress(rs.getString("dealer_address"));
                d.setDealerMail(rs.getString("dealer_mail"));
                d.setDealerPhone(rs.getString("dealer_phone"));
                d.setDealerRegion(rs.getInt("dealer_region_id"));
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
    
    
     
public static  Dealermaster getById(int id) throws SQLException
{
    Dealermaster d=null;
    Connection con=null;
    
        try
        {   con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select * from dealermaster where dealer_id='"+id+"'");  
            
            ResultSet rs=ps.executeQuery();  
            
            while(rs.next())
            {   d=new Dealermaster();
                d.setDealerId(rs.getInt("dealer_id"));
                d.setDealerName(rs.getString("dealer_name"));
                d.setDealerAddress(rs.getString("dealer_address"));
                d.setDealerMail(rs.getString("dealer_mail"));
                 d.setDealerPhone(rs.getString("dealer_phone"));
                d.setDealerRegion(rs.getInt("dealer_region_id"));
            }  
        }catch(Exception e){}  
        con.close();
        return d;
   }




public void update(Dealermaster d) throws ClassNotFoundException, SQLException{
         Connection con=DbConnection.getConnection();
         PreparedStatement st=con.prepareStatement("update dealermaster set dealer_name='"+d.getDealerName()+"',dealer_address='"+d.getDealerAddress()+"',dealer_mail='"+d.getDealerMail()+"',dealer_phone='"+d.getDealerPhone()+"',dealer_region_id='"+d.getDealerRegion()+"' where dealer_id='"+d.getDealerId()+"'");
         st.executeUpdate();
         con.close();
    }
public void delete(int id) throws ClassNotFoundException, SQLException{
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        st.executeUpdate("delete from dealermaster where dealer_id='"+id+"'");
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
