package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Branch;
import com.schillerindiaservices.bean.Companymaster;
import com.schillerindiaservices.bean.Region;
import com.schillerindiaservices.connection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BranchDao 
{
    public int saveall(String[] branchname, String[] branchlocation, String[] branchaddress, String[] branchphone, int reg_id) throws SQLException, ClassNotFoundException
    {  
        int i=0;
        Connection con; 
        PreparedStatement ps;
        for(int j=0;j<branchname.length;j++)
        {
            con = DbConnection.getConnection();
            ps = con.prepareStatement("insert into branch(regionid,branchname,branchlocation,branchaddress,branchphone) values(?,?,?,?,?)");
            ps.setInt(1,reg_id);  
            ps.setString(2,branchname[j]);  
            ps.setString(3,branchlocation[j]);  
            ps.setString(4,branchaddress[j]);  
            ps.setString(5,branchphone[j]);  
            i=ps.executeUpdate();  
            ps.close();
            con.close();
        }
        return i;  
    }  
    
    
    public static ArrayList<Branch> getAllRecords() throws ClassNotFoundException, SQLException
    {
         ArrayList<Branch> list=new ArrayList<Branch>(); 
         Connection con=DbConnection.getConnection();
        try{
       
        
        PreparedStatement st=con.prepareStatement("select * from branch");
        ResultSet rs=st.executeQuery();
        while(rs.next())
        {   
            Branch u=new Branch();  
            u.setBranchid(rs.getInt("branchid"));
            u.setRegionid(rs.getInt("regionid"));
            u.setBranchname(rs.getString("branchname"));
            u.setBranchlocation(rs.getString("branchlocation"));
            u.setBranchaddress(rs.getString("branchaddress"));
            u.setBranchphone(rs.getString("branchphone"));
            list.add(u);  
        }  
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        con.close();
      return list;  
     }
    
    public static ArrayList<String> getAllBranches() throws ClassNotFoundException, SQLException
    {
        ArrayList<String> list=new ArrayList<String>();  
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select branchname from branch");
        while(rs.next())
        {   
            list.add(rs.getString("branchname"));  
        }  
        con.close();
        return list;  
     }
    
    
    public static int getBranchId(String branchname) throws ClassNotFoundException, SQLException
    {
        int branchid=0;
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select branchid from branch where branchname='"+branchname+"'");
        if(rs.next())
        {   
                branchid=rs.getInt("branchid");  
        }  
        con.close();
        return branchid;  
     }
    
    public void delete(int id) throws ClassNotFoundException, SQLException
    {
            Connection con=DbConnection.getConnection();
            Statement st=con.createStatement();
            st.executeUpdate("delete from branch where branchid='"+id+"'");   
            con.close();
       
    }
    
    public void deleteall(int id) throws ClassNotFoundException, SQLException
    {
            Connection con=DbConnection.getConnection();
            Statement st=con.createStatement();
            st.executeUpdate("delete from branch where companyid='"+id+"'");
            con.close();
    }
    
    
            
    public void deletebranchesofregion(int regid) throws ClassNotFoundException, SQLException
    {
            Connection con=DbConnection.getConnection();
            Statement st=con.createStatement();
            st.executeUpdate("delete from branch where regionid='"+regid+"'"); 
            con.close();
    }
    
     public static String getbName(String bname) throws ClassNotFoundException, SQLException
    {  
       String list="";  
        Connection con=null;
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("SELECT branchname FROM branch WHERE branchid='"+bname+"'"); 
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list=rs.getString("branchname");  
        }  
        con.close();
        return list;
        
    }  
     
          
public static  Branch getById(int id) throws SQLException
{
    Branch d=null;
    Connection con=null;
    
        try
        {   con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select * from branch where branchid='"+id+"'");  
            
            ResultSet rs=ps.executeQuery();  
            
            while(rs.next())
            {   d=new Branch();
                d.setBranchid(rs.getInt("branchid"));
                d.setBranchname(rs.getString("branchname"));
                d.setBranchlocation(rs.getString("branchlocation"));
                d.setBranchaddress(rs.getString("branchaddress"));
                d.setBranchphone(rs.getString("branchphone"));
            }  
        }catch(Exception e){}  
        con.close();
        return d;
   }

     public void update(Branch d) throws ClassNotFoundException, SQLException{
         Connection con=DbConnection.getConnection();
         PreparedStatement st=con.prepareStatement("update branch set branchname='"+d.getBranchname()+"',branchlocation='"+d.getBranchlocation()+"',branchaddress='"+d.getBranchaddress()+"',branchphone='"+d.getBranchphone()+"' where branchid='"+d.getBranchid()+"'");
         st.executeUpdate();
         con.close();
    }
}
