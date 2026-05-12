/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Companymaster;
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
public class CompanyDao {
    
    public int save(Companymaster d) throws SQLException, ClassNotFoundException
    {  
        int status=0;  
        Connection con=null;
       
        con =DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("insert into companymaster (comp_name,comp_address,comp_email,comp_phone) values(?,?,?,?)");
        ps.setString(1,d.getCompName());
        ps.setString(2,d.getCompAddress());
        ps.setString(3, d.getCompEmail());
        ps.setString(4, d.getCompPhone());
        
        status=ps.executeUpdate();
        return status; 
    }  
    
    public int getid(String comp_name) throws SQLException, ClassNotFoundException
    {  
        int i=0;
        Connection con; 
        con = DbConnection.getConnection();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select comp_id from companymaster where comp_name='"+comp_name+"'");
        if(rs.next())
        {
            i=rs.getInt("comp_id");
        }
        con.close();
        return i;  
    } 
    
    public int checkCompany() throws SQLException, ClassNotFoundException
    {  
        int i=0;
        Connection con; 
        con = DbConnection.getConnection();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select * from companymaster");
        if(rs.next())
        {
            i=1;
        }
        con.close();
        return i;  
    }
    
    public static List<Companymaster> getAllRecords() throws SQLException
    {  
        List<Companymaster> list=new ArrayList<Companymaster>();  
        Connection con=null;
        try{  
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select * from companymaster");  
            
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
                Companymaster d=new Companymaster(); 
                d.setCompId(rs.getInt("comp_id"));
                d.setCompName(rs.getString("comp_name"));
                d.setCompAddress(rs.getString("comp_address"));
                d.setCompEmail(rs.getString("comp_email"));
                d.setCompPhone(rs.getString("comp_phone"));
                list.add(d);  
            }  
        }catch(Exception e){}  
         con.close();
        return list;  
    }  
    
    
     
public static  Companymaster getById(int id) throws SQLException
{
    Companymaster d=null;
    Connection con=null;
    
        try
        {   con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select * from companymaster where comp_id='"+id+"'");  
            
            ResultSet rs=ps.executeQuery();  
            
            while(rs.next())
            {   d=new Companymaster();
                d.setCompId(rs.getInt("comp_id"));
                d.setCompName(rs.getString("comp_name"));
                d.setCompAddress(rs.getString("comp_address"));
                d.setCompEmail(rs.getString("comp_email"));
                d.setCompPhone(rs.getString("comp_phone"));
            }  
        }catch(Exception e){}  
        con.close();
        return d;
   }




public void update(Companymaster d) throws ClassNotFoundException, SQLException{
         Connection con=DbConnection.getConnection();
         PreparedStatement st=con.prepareStatement("update companymaster set comp_name='"+d.getCompName()+"',comp_address='"+d.getCompAddress()+"',comp_email='"+d.getCompEmail()+"',comp_phone='"+d.getCompPhone()+"' where comp_id='"+d.getCompId()+"'");
         st.executeUpdate();
         con.close();
    }
public void delete(int id) throws ClassNotFoundException, SQLException
{
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        st.executeUpdate("delete from companymaster where comp_id='"+id+"'");  
        con.close();
    }
}
