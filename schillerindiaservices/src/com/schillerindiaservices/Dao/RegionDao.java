package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Region;
import com.schillerindiaservices.connection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RegionDao 
{
    public int saveall(String[] regname, String[] regdesc, int comp_id) throws SQLException, ClassNotFoundException
    {  
        int i=0;
        int k=0;
        Connection con; 
        PreparedStatement ps;
        for(int j=0;j<regname.length;j++)
        {   
            k=0;
            k=checkregion(regname[j]);
            if(k==0)
            {
                con = DbConnection.getConnection();
                ps = con.prepareStatement("insert into region (company_id,region_name,region_desc)" + "values(?,?,?)");
                ps.setInt(1,comp_id);  
                ps.setString(2,regname[j]);  
                ps.setString(3,regdesc[j]);  
                i=ps.executeUpdate();  
                ps.close();
                con.close();
            }
        }
        
        return i;  
    }  
    
    
    public int updateall(String[] regid, String[] regname, String[] regdesc, int comp_id) throws SQLException, ClassNotFoundException
    {  
        int i=0;
        int k=0;
        Connection con; 
        PreparedStatement ps;
        for(int j=0;j<regname.length;j++)
        {
            int rid=Integer.parseInt(regid[j]);
            if(rid==0)
            {
                k=0;
                k=checkregion(regname[j]);
                if(k==0)
                {
                    con = DbConnection.getConnection();
                    ps = con.prepareStatement("insert into region (company_id,region_name,region_desc)" + "values(?,?,?)");
                    ps.setInt(1,comp_id);  
                    ps.setString(2,regname[j]);  
                    ps.setString(3,regdesc[j]);  
                    i=ps.executeUpdate();  
                    ps.close();
                    con.close();
                }
            }
            else
            {
                k=0;
                k=checkregion(regname[j]);
                if(k==0)
                {
                    con = DbConnection.getConnection();
                    ps = con.prepareStatement("update region set company_id='"+comp_id+"',region_name='"+regname[j]+"',region_desc='"+regdesc[j]+"' where region_id='"+regid[j]+"'");
                    i=ps.executeUpdate();  
                    ps.close();
                    con.close();
                }
                
            }
        }
        
        return i;  
    }  
    
    
    public static ArrayList<Region> getAllRecords() throws ClassNotFoundException, SQLException
    {
        ArrayList<Region> list=new ArrayList<Region>();  
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select * from region");
        while(rs.next()){   
                Region u=new Region();  
                u.setRegionId(rs.getInt("region_id"));
                u.setRegionName(rs.getString("region_name"));
                u.setRegionDesc(rs.getString("region_desc"));
                list.add(u); 
            }
        con.close();
        return list;  
     }
    
    public static ArrayList<String> getAllRegions() throws ClassNotFoundException, SQLException
    {
        ArrayList<String> list=new ArrayList<String>();  
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select region_name from region");
        while(rs.next()){   
                list.add(rs.getString("region_name"));  
            }  
        con.close();
        return list;  
     }
    
    
    public static int getRegionId(String regionname) throws ClassNotFoundException, SQLException
    {
        int regionid=0;
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select region_id from region where region_name='"+regionname+"'");
        if(rs.next())
        {   
                regionid=rs.getInt("region_id");  
        }  
        con.close();
        return regionid;  
     }
    
     public static int getRegionId1(String regionname) throws ClassNotFoundException, SQLException
    {
        int regionid=0;
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select region_id from region where region_id='"+regionname+"'");
        if(rs.next())
        {   
                regionid=rs.getInt("region_id");  
        }  
        con.close();
        return regionid;  
     }
    
    
    public static String getRegionName(int regionid) throws ClassNotFoundException, SQLException
    {
        String regionname="";
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select region_name from region where region_id='"+regionid+"'");
        if(rs.next())
        {   
                regionname=rs.getString("region_name");  
        }  
        con.close();
        return regionname;  
     }
    
    public void delete(int id) throws ClassNotFoundException, SQLException
    {
            Connection con=DbConnection.getConnection();
            Statement st=con.createStatement();
            st.executeUpdate("delete from region where region_id='"+id+"'");
            con.close();
    }
    
    public void deleteall(int id) throws ClassNotFoundException, SQLException
    {
            Connection con=DbConnection.getConnection();
            Statement st=con.createStatement();
            st.executeUpdate("delete from region where company_id='"+id+"'"); 
            con.close();
    }
    
    
    public int checkregion(String regname) throws ClassNotFoundException, SQLException
    {
                Connection con=DbConnection.getConnection();   
                PreparedStatement ps=con.prepareStatement("select * from region where region_name='"+regname+"'");  
                ResultSet rs=ps.executeQuery();  
                if(rs.next())
                {   
                    return 1;
                }
                else
                {
                    return 0;
                }
    }
    
    public static String getrName(String rname) throws ClassNotFoundException, SQLException
    {  
       String list="";  
        Connection con=null;
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("SELECT region_name FROM region WHERE region_id='"+rname+"'");  
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list=rs.getString("region_name");  
        }  
        con.close();
        return list;  
    }  
}
