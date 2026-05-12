
package com.schillerindiaservices.Dao;


import com.schillerindiaservices.connection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ReportDao 
{
     public static List<String> getAllRegions() throws SQLException
    {  
        List<String> list=new ArrayList<String>();  
        Connection con=null;
        try{  
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select region_name from region");  
            ResultSet rs=ps.executeQuery();  
            while(rs.next())
            {  
                list.add(rs.getString("region_name"));  
            }  
        }catch(Exception e){} 
        con.close();
        return list;  
    }  
     
}


