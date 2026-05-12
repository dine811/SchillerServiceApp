package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Model;
import com.schillerindiaservices.bean.Suppliermaster;
import com.schillerindiaservices.connection.DbConnection;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class SupplierDao {
     public int save(Suppliermaster s) throws SQLException, ClassNotFoundException
    {  
        int status=0;  
        Connection con=null;
        con =DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("insert into suppliermaster (supplier_name,division,model) values(?,?,?)");
        ps.setString(1,s.getSupplierName());
        ps.setString(2,s.getDivision());
        ps.setString(3, s.getModel());
        status=ps.executeUpdate();
        con.close();
        return status;  
    }  
    
    
    public static List<Suppliermaster> getAllRecords() throws SQLException, ClassNotFoundException
    {  
         Connection con=null;
         List<Suppliermaster> list=new ArrayList<Suppliermaster>(); 
            
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select * from suppliermaster");  
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
                Suppliermaster s=new Suppliermaster();  
                s.setSupplierId(rs.getInt("supplier_id"));
                s.setSupplierName(rs.getString("supplier_name"));
                s.setDivision(rs.getString("division"));
                s.setModel(rs.getString("model"));
                list.add(s);  
            }  
           
        con.close();
        return list; 
        
    }  
    
    public static List<String> getAllSuppliers() throws ClassNotFoundException, SQLException
    {  
        List<String> list=new ArrayList<String>();  
        Connection con=null;
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("select supplier_name,supplier_id from suppliermaster");  
        ResultSet rs=ps.executeQuery();  
        while(rs.next()){            
            list.add(rs.getString("supplier_id"));
            list.add(rs.getString("supplier_name"));  
        }  
        con.close();
        return list;  
    } 
    
     
public static  Suppliermaster getById(int id) throws SQLException, ClassNotFoundException
{
    Suppliermaster s=null;
    Connection con=null;
       con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select * from suppliermaster where supplier_id='"+id+"'");  
            ResultSet rs=ps.executeQuery();  
            while(rs.next())
            {   s=new Suppliermaster();
            s.setSupplierId(rs.getInt("supplier_id"));
            s.setSupplierName(rs.getString("supplier_name"));
            s.setDivision(rs.getString("division"));
            s.setModel(rs.getString("model"));
            } 
            con.close();
        return s;
   }

 public void update(Suppliermaster s) throws ClassNotFoundException, SQLException{
         Connection con=DbConnection.getConnection();
         PreparedStatement st=con.prepareStatement("update suppliermaster set supplier_name='"+s.getSupplierName()+"',model='"+s.getModel()+"',division='"+s.getDivision()+"' where supplier_id='"+s.getSupplierId()+"'");
         st.executeUpdate();
         con.close();
    }
public void delete(int id) throws ClassNotFoundException, SQLException{
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        st.executeUpdate("delete from suppliermaster where supplier_id='"+id+"'"); 
        con.close();
    }

 public static String getsName(String sname) throws ClassNotFoundException, SQLException
    {  
       String list="";  
        Connection con=null;
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("SELECT DISTINCT sup_name FROM model WHERE Product_id='"+sname+"'"); 
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list=rs.getString("sup_name");  
        } 
        con.close();
        return list;  
    }  
 
      
public static  Suppliermaster getdivName(int id) throws SQLException, ClassNotFoundException
{
    Suppliermaster s=null;
    Connection con=null;
       con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("SELECT supplier_name FROM suppliermaster WHERE supplier_phone='"+id+"'");  
            ResultSet rs=ps.executeQuery();  
            while(rs.next())
            {   s=new Suppliermaster();
            s.setSupplierId(rs.getInt("supplier_id"));
            s.setSupplierName(rs.getString("supplier_name"));
            s.setDivision(rs.getString("division"));
            s.setModel(rs.getString("model"));
            }  
            con.close();
        return s;
   }

     
public static  List<Suppliermaster> getSupplierBYId(int id) throws SQLException, ClassNotFoundException
{
    {  
         Connection con=null;
         List<Suppliermaster> list=new ArrayList<Suppliermaster>(); 
            
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("SELECT * FROM suppliermaster s INNER JOIN emploeemaster e ON e.emp_division=s.division WHERE emp_id='"+id+"'");  
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
                Suppliermaster s=new Suppliermaster();  
                s.setSupplierId(rs.getInt("supplier_id"));
                s.setSupplierName(rs.getString("supplier_name"));
                s.setDivision(rs.getString("division"));
                s.setModel(rs.getString("model"));
                list.add(s);  
            }  
           con.close();
        return list; 
        
    }
}

public static String getSupName(String sname,String id) throws ClassNotFoundException, SQLException
    {  
       String list="";  
        Connection con=null;
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("SELECT DISTINCT sup_name FROM model WHERE Product_id='"+sname+"'AND model_id='"+id+"'"); 
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list=rs.getString("sup_name");  
        } 
        con.close();
        return list;  
    }  
public static String getSuppName(String sname,String id) throws ClassNotFoundException, SQLException
    {  
       String list="";  
        Connection con=null;
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("SELECT DISTINCT sup_name FROM model WHERE Product_id='"+sname+"'AND model_name='"+id+"'"); 
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list=rs.getString("sup_name");  
        } 
        con.close();
        return list;  
    } 
}
