package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Productmaster;
import com.schillerindiaservices.connection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductDao 
{
    public int save(Productmaster u) throws SQLException, ClassNotFoundException
    {  
        int i;
        Connection con; 
        con = DbConnection.getConnection();
        PreparedStatement ps;
        ps = con.prepareStatement("insert into productmaster (prod_name,prod_description)" + "values(?,?)");
        ps.setString(1,u.getProdName());  
        ps.setString(2,u.getProdDescription());  
        i=ps.executeUpdate();  
        ps.close();
       
        return i;  
    }  
    
    public static int getid(String pro_name) throws SQLException, ClassNotFoundException
    {  
        int i=0;
        Connection con; 
        con = DbConnection.getConnection();
        PreparedStatement st=con.prepareStatement("select prod_id from productmaster where prod_name='"+pro_name+"'");
        ResultSet rs=st.executeQuery();
        if(rs.next())
        {
            i=rs.getInt("prod_id");
        }
     con.close();
        return i;  
    } 
     public static int getid1(String pro_name) throws SQLException, ClassNotFoundException
    {  
        int i=0;
        Connection con; 
        con = DbConnection.getConnection();
        PreparedStatement st=con.prepareStatement("select prod_id from productmaster where prod_id='"+pro_name+"'");
        ResultSet rs=st.executeQuery();
        if(rs.next())
        {
            i=rs.getInt("prod_id");
        }
     con.close();
        return i;  
    }
    
    public static String getProdName(String id) throws SQLException, ClassNotFoundException
    {  
        String prodname="";
        Connection con;  
        con = DbConnection.getConnection();
        PreparedStatement st=con.prepareStatement("select prod_name from productmaster where prod_id='"+id+"'");
        ResultSet rs=st.executeQuery();
        if(rs.next())
        {
            prodname=rs.getString("prod_name");
        }
    con.close();
        return prodname;  
    } 
   
    
    public static ArrayList<Productmaster> getAllRecords() throws ClassNotFoundException, SQLException
    {
        ArrayList<Productmaster> list=new ArrayList<Productmaster>();  
        Connection con=DbConnection.getConnection();
        PreparedStatement st=con.prepareStatement("select * from productmaster");
        ResultSet rs=st.executeQuery();
        while(rs.next()){   
                Productmaster u=new Productmaster();  
                u.setProdId(rs.getInt("prod_id"));
                u.setProdName(rs.getString("prod_name"));
                u.setProdDescription(rs.getString("prod_description"));
                list.add(u);  
            }  
        con.close();
        return list;  
     }
    
    public static ArrayList<String> getAllProducts() throws ClassNotFoundException, SQLException
    {
        ArrayList<String> list=new ArrayList<String>(); 
        Connection con=DbConnection.getConnection();
        PreparedStatement st=con.prepareStatement("select prod_name,prod_id from productmaster");
        ResultSet rs=st.executeQuery();
        while(rs.next())
        {   
           
            list.add(rs.getString("prod_name"));  
             
        }  
        con.close();
        return list;  
     }
    public static ArrayList<Integer> getAllProductId() throws ClassNotFoundException, SQLException
    {
        ArrayList<Integer> list=new ArrayList<Integer>();  
        Connection con=DbConnection.getConnection();
        PreparedStatement st=con.prepareStatement("select prod_name,prod_id from productmaster");
        ResultSet rs=st.executeQuery();
        while(rs.next())
        {   
            list.add(rs.getInt("prod_id"));  
                 
        }  
        con.close();
        return list;  
     }
    
     
    public static  Productmaster getById(int id) throws SQLException
    {
        Productmaster u=new Productmaster();
        Connection con=null;
            try
            {   con=DbConnection.getConnection();   
                PreparedStatement ps=con.prepareStatement("select * from productmaster where prod_id='"+id+"'");  
                ResultSet rs=ps.executeQuery();  

                if(rs.next())
                {   u.setProdId(rs.getInt("prod_id"));
                    u.setProdName(rs.getString("prod_name"));
                    u.setProdDescription(rs.getString("prod_description"));
                }  
            }catch(Exception e){}  
            con.close();
            return u;
    }

 
    public void update(Productmaster d) throws ClassNotFoundException, SQLException
    {
             Connection con=DbConnection.getConnection();
             PreparedStatement st=con.prepareStatement("update productmaster set prod_name='"+d.getProdName()+"', prod_description='"+d.getProdDescription()+"' where prod_id='"+d.getProdId()+"'");
             st.executeUpdate();
             con.close();
    }
 
    public void delete(int id) throws ClassNotFoundException, SQLException
    {
            Connection con=DbConnection.getConnection();
            PreparedStatement st=con.prepareStatement("delete from productmaster where prod_id='"+id+"'");
            st.executeUpdate();    
            con.close();
    }
    
     public static String getDivName(String dname) throws ClassNotFoundException, SQLException
    {  
       
        String list="";  
        Connection con=null;
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("SELECT product_id,e.emp_name FROM `model` m INNER JOIN `emploeemaster`e ON e.emp_division=m.product_id WHERE emp_id='"+dname+"'");  
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list=rs.getString("sup_name");  
        }  
        con.close();
        return list;  
    }  
    public static String getDiviName(String name) throws ClassNotFoundException, SQLException
    {
        String id="";
        Connection con=null;
        con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT prod_id FROM productmaster WHERE prod_name='"+name+"'");
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            id=rs.getString("prod_id");
        }
        con.close();
        return id;
    }
      
}
