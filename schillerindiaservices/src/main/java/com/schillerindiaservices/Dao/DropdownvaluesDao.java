package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Dropdownmaster;
import com.schillerindiaservices.connection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DropdownvaluesDao 
{ 
     public static int update(String Dropdownname, String Dropdownvalues,String ddvalue) throws SQLException, ClassNotFoundException
    {  
        int i=0;
        String unsplittedstring=Dropdownvalues;  
        String[] splittedstring = unsplittedstring.split("\\,");
//        String ddvalue=Arrays.toString(splittedstring);
        Connection con; 
        con = DbConnection.getConnection();
        PreparedStatement ps=null;
//        ps = con.prepareStatement("delete from dropdownmaster where ddname='"+Dropdownname+"'");
//        i=ps.executeUpdate();
            ps=con.prepareStatement("SELECT * FROM dropdownmaster WHERE ddname=? AND ddvalue=? ");
            ps.setString(1, Dropdownname);
            ps.setString(2, ddvalue);
            ResultSet rs=ps.executeQuery();
           
//        for(int j=0;j<splittedstring.length;j++)
//        {
             if(rs.next())
             {
                 i=0;
             }else{
            ps = con.prepareStatement("insert into dropdownmaster (ddname,ddvalue) values(?,?)");
            ps.setString(1,Dropdownname);
            ps.setString(2,ddvalue);
            i=ps.executeUpdate();  
             }
//        }
        ps.close();
        con.close();
        return i;  
    }  
    
   
    
    public static int getDropdownId(String dropdownname) throws ClassNotFoundException, SQLException
    {
        int dropdownid=0;
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select id from dropdownmaster where ddvalue='"+dropdownname+"'");
        if(rs.next())
        {   
                dropdownid=rs.getInt("branchid");  
        }  
        con.close();
        return dropdownid;  
     }
    
    
    public static List<String> getdpvaluesfor(String ddname) throws ClassNotFoundException, SQLException
    {
        List<String> list=new ArrayList<String>();
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select * from dropdownmaster where ddname='"+ddname+"'");
        while(rs.next())
        {   
            list.add(rs.getString("ddvalue"));
        }  
      con.close();
        return list;
     }
    
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
   
     public void delete(String name,String id) throws ClassNotFoundException, SQLException
     {
         Connection con=DbConnection.getConnection();
         PreparedStatement ps=con.prepareStatement("DELETE FROM dropdownmaster WHERE ddname='"+id+"' AND ddvalue='"+name+"'");
         ps.executeUpdate();
         con.close();
         
     }
    
}
