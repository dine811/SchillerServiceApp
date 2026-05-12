/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Dropdownmaster;
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
 * @author MR
 */
public class DropdownDao {
      public void save(Dropdownmaster d) throws SQLException, ClassNotFoundException
    {  
        Connection con=null;
        con =DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("insert into dropdownmaster (ddname,ddvalue) values(?,?)");
        ps.setString(1,d.getDdname());
        ps.setString(2,d.getDdvalue());
        ps.executeUpdate();
        con.close();
    }
      
    public ArrayList getddvalues(Dropdownmaster d) throws ClassNotFoundException, SQLException{
        ArrayList<Dropdownmaster> v = new ArrayList<Dropdownmaster>();
        Connection conn;
         conn =  DbConnection.getConnection();
         String sql = "SELECT ddvalue FROM dropdownmaster WHERE ddname='"+3+"'";
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery();
         while(rs.next())
         {
            Dropdownmaster dd=new Dropdownmaster();
            dd.setDdname(rs.getString("ddname"));
            dd.setDdvalue(rs.getString("ddvalue"));
            v.add(d);
         }
         conn.close();
        return v;
    } 
    
    public static ArrayList<String> getAlldpdnames(String x) throws ClassNotFoundException, SQLException
    {
        ArrayList<String> list=new ArrayList<String>();  
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("SELECT DISTINCT ddvalue FROM dropdownmaster WHERE ddname='"+x+"'");
        while(rs.next()){   
                list.add(rs.getString("ddvalue"));  
            }  
        con.close();
        return list;  
     }
    
     public static List<String> getAlldp(String x) throws ClassNotFoundException, SQLException
    {  
        List<String> list=new ArrayList<String>();  
        Connection con=null;
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("select ddvalue,dd_id from dropdownmaster WHERE ddname='"+x+"'");  
        ResultSet rs=ps.executeQuery();  
        while(rs.next()){            
            list.add(rs.getString("dd_id"));
            list.add(rs.getString("ddvalue"));  
        }  
        con.close();
        return list;  
    } 
      
   public static String getdd(String id) throws ClassNotFoundException, SQLException
    {  
        String name="";
        Connection con=null;
         
            con=DbConnection.getConnection();  
            PreparedStatement ps=con.prepareStatement("SELECT ddvalue FROM dropdownmaster WHERE dd_id='"+id+"'");  
      
            ResultSet rs=ps.executeQuery();  
            if(rs.next()){  
                   name=rs.getString("ddvalue");  
            }  
            con.close();
        return name;  
    }   
   
    public static String getddv(String id) throws ClassNotFoundException, SQLException
    {  
        String name="";
        Connection con=null;
         
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select ddvalue from dropdownmaster where dd_id='"+id+"'");  
            ResultSet rs=ps.executeQuery();  
            if(rs.next()){  
                name=rs.getString("ddvalue");  
            }  
            con.close();
        return name;  
    }   
     
    public static String getRef(String id) throws ClassNotFoundException, SQLException
    {  
        String name="";
        Connection con=null; 
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("SELECT dd_id FROM dropdownmaster WHERE ddvalue ='"+id+"'");  
          
            ResultSet rs=ps.executeQuery();  
            if(rs.next()){  
                name=rs.getString("dd_id");    
            }  
            con.close();
        return name;  
    }
    
    public static List<Dropdownmaster> getDDV2() throws SQLException, ClassNotFoundException
    {  
        int x=2;
        List<Dropdownmaster> list=new ArrayList<Dropdownmaster>();  
        Connection con=null;
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select ddvalue,dd_id from dropdownmaster WHERE ddname='"+x+"'");  
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
                Dropdownmaster s=new Dropdownmaster();  
                s.setId(rs.getInt("dd_id"));
                s.setDdvalue(rs.getString("ddvalue"));
                list.add(s);  
            }  
            con.close();
        return list;  
    }  
    public static List<Dropdownmaster> getDDV3() throws SQLException, ClassNotFoundException
    {  
        int x=3;
        List<Dropdownmaster> list=new ArrayList<Dropdownmaster>();  
        Connection con=null;
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select ddvalue,dd_id from dropdownmaster WHERE ddname='"+x+"'");  
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
                Dropdownmaster s=new Dropdownmaster();  
                s.setId(rs.getInt("dd_id"));
                s.setDdvalue(rs.getString("ddvalue"));
                list.add(s);  
            }  
            con.close();
        return list;  
    }  
    
     public static List<Dropdownmaster> getDDV4() throws SQLException, ClassNotFoundException
    {  
        int x=4;
        List<Dropdownmaster> list=new ArrayList<Dropdownmaster>();  
        Connection con=null;
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select ddvalue,dd_id from dropdownmaster WHERE ddname='"+x+"'");  
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
                Dropdownmaster s=new Dropdownmaster();  
                s.setId(rs.getInt("dd_id"));
                s.setDdvalue(rs.getString("ddvalue"));
                list.add(s);  
            }  
            con.close();
        return list;  
    }  
     public static List<Dropdownmaster> getDDV6() throws SQLException, ClassNotFoundException
    {  
        int x=6;
        List<Dropdownmaster> list=new ArrayList<Dropdownmaster>();  
        Connection con=null;
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select ddvalue,dd_id from dropdownmaster WHERE ddname='"+x+"'");  
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
                Dropdownmaster s=new Dropdownmaster();  
                s.setId(rs.getInt("dd_id"));
                s.setDdvalue(rs.getString("ddvalue"));
                list.add(s);  
            }  
            con.close();
        return list;  
    }  
     
     public static List<Dropdownmaster> getDDV5() throws SQLException, ClassNotFoundException
    {  
    	// System.out.println("loadingggg");
    	 
    	 
        int x=5;
        List<Dropdownmaster> list=new ArrayList<Dropdownmaster>();  
        Connection con=null;
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select ddvalue,dd_id from dropdownmaster WHERE ddname='"+x+"'");  
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
                Dropdownmaster s=new Dropdownmaster();  
                s.setId(rs.getInt("dd_id"));
                s.setDdvalue(rs.getString("ddvalue"));
                list.add(s);  
            }  
            con.close();
        return list;  
    }  
     
      public static List<Dropdownmaster> getDDV1() throws SQLException, ClassNotFoundException
    {  
        int x=1;
        List<Dropdownmaster> list=new ArrayList<Dropdownmaster>();  
        Connection con=null;
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select ddvalue,dd_id from dropdownmaster WHERE ddname='"+x+"'");  
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
                Dropdownmaster s=new Dropdownmaster();  
                s.setId(rs.getInt("dd_id"));
                s.setDdvalue(rs.getString("ddvalue"));
                list.add(s);  
            } 
            con.close();
        return list;  
    }
     
    
      public static String getddName(String ddid) throws ClassNotFoundException, SQLException
    {
        String list="";  
        Connection con=null;      
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("SELECT ddvalue FROM dropdownmaster WHERE dd_id='"+ddid+"'");  
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list=rs.getString("ddvalue");  
        }  
         con.close();
        return list; 
    }  
      
        public static String getddId(String ddvalue) throws ClassNotFoundException, SQLException
    {  
       String list="";  
        Connection con=null;
        con=DbConnection.getConnection(); 
       
        PreparedStatement ps=con.prepareStatement("SELECT dd_id FROM dropdownmaster WHERE ddvalue='"+ddvalue+"'");  
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list=rs.getString("dd_id");  
        }  
        con.close();
        return list;  
    }  
      
      
}
