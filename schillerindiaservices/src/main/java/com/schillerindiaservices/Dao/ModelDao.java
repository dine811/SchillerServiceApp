package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Model;
import com.schillerindiaservices.connection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ModelDao 
{
    public int save(Model u) throws SQLException, ClassNotFoundException
    {  
        int i;
        Connection con; 
        con = DbConnection.getConnection();
        PreparedStatement ps;
        ps = con.prepareStatement("insert into model(product_id,sup_name,model_name,model_desc)values(?,?,?,?)");
        ps.setInt(1,u.getProductId());  
        ps.setString(2,u.getSupName());  
        ps.setString(3,u.getModelName());
        ps.setString(4,u.getModelDesc());
        i=ps.executeUpdate();  
        ps.close();
        con.close();
        return i;  
    }  
    
    public int saveall(String[] suppliername, String[] modelname, String[] modeldesc, int prod_id) throws SQLException, ClassNotFoundException
    {  
        int i=0;
        int k=0;
        Connection con; 
        PreparedStatement ps;
        for(int j=0;j<suppliername.length;j++)
        {
            k=0;
            k=checkmodel(modelname[j]);
            if(k==0)
            {
                con = DbConnection.getConnection();
                ps = con.prepareStatement("insert into model (product_id,sup_name,model_name,model_desc)" + "values(?,?,?,?)");
                ps.setInt(1,prod_id);  
                ps.setString(2,suppliername[j]);  
                ps.setString(3,modelname[j]);  
                ps.setString(4,modeldesc[j]);
                i=ps.executeUpdate();  
                ps.close();
                con.close();
            }
        }
        
        return i;  
    }  
    
    public int updateall(String[] modelid, String[] suppliername, String[] modelname, String[] modeldesc, int prod_id) throws SQLException, ClassNotFoundException
    {  
        int i=0;
        int k=0;
        Connection con; 
        PreparedStatement ps;
        for(int j=0;j<suppliername.length;j++)
        {
            int mid=Integer.parseInt(modelid[j]);
            if(mid==0)
            {
                k=0;
                k=checkmodel(modelname[j]);
                if(k==0)
                {
                    con = DbConnection.getConnection();
                    ps = con.prepareStatement("insert into model (product_id,sup_name,model_name,model_desc)" + "values(?,?,?,?)");
                    ps.setInt(1,prod_id);  
                    ps.setString(2,suppliername[j]);  
                    ps.setString(3,modelname[j]);  
                    ps.setString(4,modeldesc[j]);
                    i=ps.executeUpdate();  
                    ps.close();
                    con.close();
                }
            }
            else
            {
                k=0;
                k=checkmodel(modelname[j]);
                if(k==0)
                {
                    con = DbConnection.getConnection();
                    PreparedStatement pst=con.prepareStatement("update model set sup_name='"+suppliername[j]+"',model_name='"+modelname[j]+"', model_desc='"+modeldesc[j]+"' where model_id='"+modelid[j]+"'");
                    i=pst.executeUpdate();  
                    pst.close();
                    con.close();
                }
            }
        }
        return i;  
    } 
    
    
    public static ArrayList<String> getAllModels() throws ClassNotFoundException, SQLException
    {
        ArrayList<String> list=new ArrayList<String>();  
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select model_name from model");
        while(rs.next())
        {   
            list.add(rs.getString("model_name"));  
        }  
        con.close();
        return list;  
     }
    
    
    public static ArrayList<String> getAllModelsofdivision(int id) throws ClassNotFoundException, SQLException
    {
        ArrayList<String> list=new ArrayList<String>();  
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select model_name,model_id from model where product_id='"+id+"'");
        while(rs.next())
        {   
            list.add(rs.getString("model_id")); 
            list.add(rs.getString("model_name"));  
        }  
        con.close();
        return list;  
     }
    
     public static List<String> getAllModelValue() throws ClassNotFoundException, SQLException
    {  
        List<String> list=new ArrayList<String>();  
        Connection con=null;
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("SELECT  model_id,model_name FROM model");  
        ResultSet rs=ps.executeQuery();  
        while(rs.next()){            
            list.add(rs.getString("model_id"));
            list.add(rs.getString("model_name"));  
        }  
        con.close();
        return list;  
    } 
    
    
    public static ArrayList<Model> getModelsofProduct(int id) throws ClassNotFoundException, SQLException
    {
        ArrayList<Model> list=new ArrayList<Model>();  
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select * from model where product_id='"+id+"'");
        while(rs.next()){   
                Model u=new Model();  
                u.setModelId(rs.getInt("model_id"));
                u.setSupName(rs.getString("sup_name"));
                u.setModelName(rs.getString("model_name"));
                u.setModelDesc(rs.getString("model_desc"));
                list.add(u);  
            }  
        con.close();
        return list;  
     }
    
    public static ArrayList<Model> getAllRecords() throws ClassNotFoundException, SQLException
    {
        ArrayList<Model> list=new ArrayList<Model>();  
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select * from model");
        while(rs.next()){   
                Model u=new Model();  
                u.setModelId(rs.getInt("model_id"));
                u.setSupName(rs.getString("sup_name"));
                u.setModelName(rs.getString("model_name"));
                u.setModelDesc(rs.getString("model_desc"));
                list.add(u);  
            }  
        con.close();
        return list;  
     }
    
     
    public static  Model getById(int id) throws SQLException
    {
        Model u=null;
        Connection con=null;
            try
            {   con=DbConnection.getConnection();   
                PreparedStatement ps=con.prepareStatement("select * from model where model_id='"+id+"'");  
                ResultSet rs=ps.executeQuery();  
                while(rs.next())
                {   
                    u=new Model();
                    u.setModelId(id);
                    u.setProductId(rs.getInt("product_id"));
                    u.setSupName(rs.getString("sup_name"));
                    u.setModelName(rs.getString("model_name"));
                    u.setModelDesc(rs.getString("model_desc"));
                }  
            }catch(Exception e){}  
            con.close();
            return u;
    }

 
    public void update(Model d) throws ClassNotFoundException, SQLException
    {
             Connection con=DbConnection.getConnection();
             PreparedStatement st=con.prepareStatement("update model set product_id='"+d.getProductId()+"', sup_name='"+d.getSupName()+"',model_name='"+d.getModelName()+"', model_desc='"+d.getModelDesc()+"' where model_id='"+d.getModelId() +"'");
             st.executeUpdate();
             con.close();
    }
 
    public void delete(int id) throws ClassNotFoundException, SQLException
    {
            Connection con=DbConnection.getConnection();
            Statement st=con.createStatement();
            st.executeUpdate("delete from model where model_id='"+id+"'"); 
            con.close();
    }
    
    public void deletemodelsofproduct(int pid) throws ClassNotFoundException, SQLException
    {
            Connection con=DbConnection.getConnection();
            Statement st=con.createStatement();
            st.executeUpdate("delete from model where product_id="+pid+"");   
            con.close();
    }
    
    
    public int checkmodel(String modelname) throws ClassNotFoundException, SQLException
    {
                Connection con=DbConnection.getConnection();   
                PreparedStatement ps=con.prepareStatement("select * from model where model_name='"+modelname+"'");  
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
    
    
    public static String getModal(String id) throws ClassNotFoundException, SQLException
    {  
        String name="";
        Connection con=null;
         
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select model_name from model where model_id='"+id+"'");  
            ResultSet rs=ps.executeQuery();  
            if(rs.next()){  
                name=rs.getString("model_name");  
            }  
            con.close();
        return name;  
    }
   public static List<Model> getPModel() throws SQLException, ClassNotFoundException
    {  
        List<Model> list=new ArrayList<Model>();  
        Connection con=null;
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select * from model");  
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
                Model s=new Model();  
                s.setModelId(rs.getInt("model_id"));
                s.setModelName(rs.getString("model_name"));
                s.setModelDesc(rs.getString("model_desc"));
                s.setSupName(rs.getString("sup_name"));
                s.setProductId(rs.getInt("product_id"));
                list.add(s);  
            }  
            con.close();
        return list;  
    }
   
     public static String getpName(String ddid) throws ClassNotFoundException, SQLException
    {  
       
       String list="";  
        Connection con=null;
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("SELECT model_name FROM model WHERE model_id='"+ddid+"'");  
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list=rs.getString("model_name");  
        }  
        con.close();
        return list;  
    }  
     
       public static ArrayList<Model> getAllSupp() throws ClassNotFoundException, SQLException
    {
        ArrayList<Model> list=new ArrayList<Model>();  
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("SELECT DISTINCT sup_name FROM model");
        while(rs.next()){   
                Model u=new Model();  
//                u.setModelId(rs.getInt("model_id"));
                u.setSupName(rs.getString("sup_name"));
//                u.setModelName(rs.getString("model_name"));
//                u.setModelDesc(rs.getString("model_desc"));
                list.add(u);  
            }  
        con.close();
        return list;  
     }
        public static String getsName(int sname) throws ClassNotFoundException, SQLException
    {  
       
       String list="";  
        Connection con=null;
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("SELECT sup_name FROM model WHERE model_id='"+sname+"'");  
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list=rs.getString("sup_name");  
        }  
        con.close();
        return list;  
    }  
     public static ArrayList<Model> getSupNameByDiv(int snbd) throws ClassNotFoundException, SQLException
    {
        ArrayList<Model> list=new ArrayList<Model>();  
        Connection con=DbConnection.getConnection();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("SELECT DISTINCT m.sup_name,m.model_id,product_id,e.emp_name FROM `model` m INNER JOIN `emploeemaster`e ON e.emp_division=m.product_id WHERE emp_id='"+snbd+"'");
        while(rs.next()){   
                Model u=new Model();  
                u.setModelId(rs.getInt("model_id"));
                u.setSupName(rs.getString("sup_name"));
                list.add(u);  
            }  
        con.close();
        return list;  
     }    
    
       public static ArrayList<Model> getDiv(String sname) throws ClassNotFoundException, SQLException
    {
        ArrayList<Model> list=new ArrayList<Model>();  
        Connection con=DbConnection.getConnection();
        PreparedStatement st=con.prepareStatement("SELECT DISTINCT sup_name FROM model WHERE product_id='"+sname+"'");
        ResultSet rs=st.executeQuery();
        while(rs.next()){   
                Model u=new Model();  
//                u.setModelId(rs.getInt("model_id"));
                u.setSupName(rs.getString("sup_name"));
//                u.setModelName(rs.getString("model_name"));
//                u.setModelDesc(rs.getString("model_desc"));
                list.add(u);  
            }  
        con.close();
        return list;  
     }
       public static String getModelname(String name) throws ClassNotFoundException, SQLException
       {
           String na="";
           Connection con=null;
           con=DbConnection.getConnection();
           PreparedStatement ps=con.prepareStatement("SELECT model_name FROM model WHERE model_id='"+name+"'");
           ResultSet rs=ps.executeQuery();
           while(rs.next()){
               na=rs.getString("model_name");
           }
           return na;
       }
      
       public static String getmodelid(String nam) throws ClassNotFoundException, SQLException
       {
           String name="";
           Connection con=null;
           con=DbConnection.getConnection();
           PreparedStatement ps=con.prepareStatement("SELECT model_id FROM model WHERE model_name='"+nam+"'");
           ResultSet rs=ps.executeQuery();
           while(rs.next())
           {
             int a=rs.getInt("model_id");
            name=String.valueOf(a);
           }
           con.close();
        return name;
       }
}

