package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Emploeemaster;
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
public class EmployeeDao {
    
    public int save(Emploeemaster u) throws SQLException, ClassNotFoundException
    {  
         int status=0;  
    Connection con=null;
    
    PreparedStatement ps=null;
        try{
    con=null;
        con=DbConnection.getConnection();  
         ps=con.prepareStatement("insert into emploeemaster(emp_name,emp_address,emp_email,emp_password,emp_mobile,emp_role,emp_active,emp_branch,emp_dept,emp_division) values(?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1,u.getEmpName());  
        ps.setString(2,u.getEmpAddress());  
        ps.setString(3,u.getEmpEmail());  
        ps.setString(4,u.getEmpPassword());  
        ps.setString(5,u.getEmpMobile()); 
        ps.setString(6,u.getEmpRole()); 
        ps.setString(7,u.getEmpActive()); 
        ps.setString(8,u.getEmpBranch());
        ps.setString(9,u.getEmpDept());
        ps.setString(10,u.getEmpDivision());
        status=ps.executeUpdate();
        }
        finally{
                if(con!=null){
                    con.close();
                }
                }
    return status;  
    }  
    
    
    
    
    public static List<Emploeemaster> getAllRecords() throws SQLException
    {  
        List<Emploeemaster> list=new ArrayList<Emploeemaster>();  
        Connection con=null;
        try{  
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select * from emploeemaster");  
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
                Emploeemaster u=new Emploeemaster();  
                u.setEmpId(rs.getInt("emp_id"));
                u.setEmpName(rs.getString("emp_name"));
                u.setEmpAddress(rs.getString("emp_address"));
                u.setEmpEmail(rs.getString("emp_email"));
                u.setEmpPassword(rs.getString("emp_password"));
                u.setEmpMobile(rs.getString("emp_mobile"));
                u.setEmpRole(rs.getString("emp_role"));
                u.setEmpActive(rs.getString("emp_active"));
                u.setEmpBranch(rs.getString("emp_branch"));
                u.setEmpDept(rs.getString("emp_dept"));
                u.setEmpDivision(rs.getString("emp_division"));
                list.add(u);  
            }  
        }catch(Exception e){
        e.printStackTrace();}
        finally{
            if(con!=null){
                con.close();
            }
        }
        return list;  
    } 
    public static List<Emploeemaster> getAllEmpdiv(int divid) throws ClassNotFoundException, SQLException
    {  
        List<Emploeemaster> list=new ArrayList<Emploeemaster>();  
        Connection con=null;
        try{
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("select * from emploeemaster where emp_division='"+divid+"'");  
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            Emploeemaster u=new Emploeemaster();  
                u.setEmpId(rs.getInt("emp_id"));
                u.setEmpName(rs.getString("emp_name"));
                u.setEmpAddress(rs.getString("emp_address"));
                u.setEmpEmail(rs.getString("emp_email"));
                u.setEmpPassword(rs.getString("emp_password"));
                u.setEmpMobile(rs.getString("emp_mobile"));
                u.setEmpRole(rs.getString("emp_role"));
                u.setEmpActive(rs.getString("emp_active"));
                u.setEmpBranch(rs.getString("emp_branch"));
                u.setEmpDept(rs.getString("emp_dept"));
                u.setEmpDivision(rs.getString("emp_division"));
                list.add(u);  
        }  }
        finally{
            if(con!=null){
                con.close();
            }
        }
        return list;  
    }
    
    
    public static List<String> getAllEmployees() throws ClassNotFoundException, SQLException
    {  
        List<String> list=new ArrayList<String>();  
        Connection con=null;
        try{
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("select emp_name from emploeemaster");  
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list.add(rs.getString("emp_name"));  
        }  }
        finally{
            if(con!=null){
                con.close();
            }
        }
        return list;  
    }
    
//    to get PS in BIR Update Form
    public static List<String> getPSEngg() throws ClassNotFoundException, SQLException
    {
        List<String> list=new ArrayList<String>();
        Connection con=DbConnection.getConnection();
        try
        {
        PreparedStatement ps=con.prepareStatement("SELECT emp_name FROM emploeemaster WHERE emp_role='productsupport'");
       ResultSet rs= ps.executeQuery();
       while(rs.next())
       {
           list.add(rs.getString("emp_name"));
       }}
        finally{
            if(con!=null){
                con.close();
            }
        }
       return list;
    }
    
    public static List<String> getAllEmployeesofdivision(int divid) throws ClassNotFoundException, SQLException
    {  
        List<String> list=new ArrayList<String>();  
        Connection con=null;
        try{
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("select * from emploeemaster where emp_division='"+divid+"'");  
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list.add(rs.getString("emp_name"));  
        }  }
        finally{
            if(con!=null){
                con.close();
            }
        }
        return list;  
    }
    
    public static List<String> getEmployeesofDivision(int divid) throws ClassNotFoundException, SQLException
    {  
        List<String> list=new ArrayList<String>();  
        Connection con=null;
        try{
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("select emp_name from emploeemaster where emp_division="+divid);  
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list.add(rs.getString("emp_name"));  
        }  }
        finally{
            if(con!=null){
                con.close();
            }
        }
        return list;  
    }
   
    public static String getname(String d) throws ClassNotFoundException, SQLException
            
    {
        String dd="";
        Connection con=null;
        try{
        con=DbConnection.getConnection();
         PreparedStatement ps=con.prepareStatement("select emp_name from emploeemaster where emp_id='"+d+"'"); 
        
          ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            dd=rs.getString("emp_name");  
        }  }
        finally{
            if(con!=null){
                con.close();
            }
        }
        return dd;
        
    }
    
    
    public static int getEmpId(String engineerstring) throws ClassNotFoundException, SQLException
    {  
        int id=0;
        Connection con=null;
         try{
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select emp_id from emploeemaster where emp_name='"+engineerstring+"'");  
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
                id=rs.getInt("emp_id");  
            }  }
         finally{
            if(con!=null){
                con.close();
            }
        }
        return id;  
    }
    
    public static int getEmpId1(String engineerstring) throws ClassNotFoundException, SQLException
    {  
        int id=0;
        Connection con=null;
         try{
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select emp_id from emploeemaster where emp_id='"+engineerstring+"'");  
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
                id=rs.getInt("emp_id");  
            }  }
         finally{
            if(con!=null){
                con.close();
            }
        }
        return id;  
    }
    
    public static int getEmpDivisionId(int empid) throws ClassNotFoundException, SQLException
    {  
        int divid=0;
        Connection con=null;
        try{
            con=DbConnection.getConnection();    
            PreparedStatement ps=con.prepareStatement("select emp_division from emploeemaster where emp_id='"+empid+"'");  
            ResultSet rs=ps.executeQuery();  
            if(rs.next()){  
                divid=rs.getInt("emp_division");  
            }  
           
        }
        finally{
            if(con!=null){
                con.close();
            }
        }
        return divid;  
    }
     public static int getEmpDivisionI(int empid) throws ClassNotFoundException, SQLException
    {  
        int divid=0;
        Connection con=null;
         try{
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select emp_id from emploeemaster where emp_id='"+empid+"'");  
            ResultSet rs=ps.executeQuery();  
            if(rs.next()){  
                divid=rs.getInt("emp_id");  
            }  }
         finally{
            if(con!=null){
                con.close();
            }
        }
        return divid;  
    }
//    for re_engr in admin login
     public static int getEmpDivisionbr(String empid) throws ClassNotFoundException, SQLException
    {  
        int divid=0;
        Connection con=null;
         try{
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select emp_division from emploeemaster where emp_branch='"+empid+"'");  
            ResultSet rs=ps.executeQuery();  
            if(rs.next()){  
                divid=rs.getInt("emp_division");  
            }  }
         finally{
            if(con!=null){
                con.close();
            }
        }
        return divid;  
    }
//    for admin update
    public static int getADEmpDivisionId(int empid) throws ClassNotFoundException, SQLException
    {  
        int divid=0;
        Connection con=null;
         try{
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select emp_division from emploeemaster where emp_branch='"+empid+"'");  
            ResultSet rs=ps.executeQuery();  
            if(rs.next()){  
                divid=rs.getInt("emp_division");  
            }  }
         finally{
            if(con!=null){
                con.close();
            }
        }
        return divid;  
    }
    public static String getEmpName(int id) throws ClassNotFoundException, SQLException
    { 
        String name="";
        Connection con=null;
        con=DbConnection.getConnection();   
    try{
            PreparedStatement ps=con.prepareStatement("select emp_name from emploeemaster where emp_id="+id);  
            ResultSet rs=ps.executeQuery();  
            if(rs.next()){  
                name=rs.getString("emp_name");  
            }  }
    finally{
            if(con!=null){
                con.close();
            }
        }
        return name;  
    }
    
    
     
public static  Emploeemaster getById(int id) throws ClassNotFoundException, SQLException
{
    Emploeemaster u=null;
    
    Connection con=DbConnection.getConnection();   
    PreparedStatement ps=con.prepareStatement("select * from emploeemaster where emp_id='"+id+"'"); 
    ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {   u=new Emploeemaster();
                u.setEmpId(rs.getInt("emp_id"));
                u.setEmpName(rs.getString("emp_name"));
                u.setEmpAddress(rs.getString("emp_address"));
                u.setEmpEmail(rs.getString("emp_email"));
                u.setEmpPassword(rs.getString("emp_password"));
                u.setEmpMobile(rs.getString("emp_mobile"));
                u.setEmpRole(rs.getString("emp_role"));
                u.setEmpActive(rs.getString("emp_active"));
                u.setEmpBranch(rs.getString("emp_branch"));
                u.setEmpDept(rs.getString("emp_dept"));
                u.setEmpDivision(rs.getString("emp_division"));
        }  
        con.close();
    return u;
   }


    public void update(Emploeemaster d) throws ClassNotFoundException, SQLException
    {
         Connection con=DbConnection.getConnection();
        int i= d.getEmpId();
         PreparedStatement st=con.prepareStatement("update emploeemaster set emp_name='"+d.getEmpName()+"',emp_address='"+d.getEmpAddress()+"',emp_email='"+d.getEmpEmail()+"', emp_password='"+d.getEmpPassword()+"', emp_mobile='"+d.getEmpMobile()+"', emp_role='"+d.getEmpRole()+"', emp_active='"+d.getEmpActive()+"', emp_branch='"+d.getEmpBranch()+"', emp_dept='"+d.getEmpDept()+"', emp_division='"+d.getEmpDivision()+"' where emp_id='"+d.getEmpId()+"'");
         st.executeUpdate();
    }
    public void delete(int id) throws ClassNotFoundException, SQLException
    {
            Connection con=DbConnection.getConnection();
            Statement st=con.createStatement();
            st.executeUpdate("delete from emploeemaster where emp_id='"+id+"'");       
    }


public static int checkUser(String userid, String password,String status) throws ClassNotFoundException, SQLException
{  
            Connection con=DbConnection.getConnection();  
            if(status.equalsIgnoreCase("yes")){
            PreparedStatement ps=con.prepareStatement("select * from emploeemaster where emp_email=? and emp_password=?");  
            ps.setString(1,userid);  
            ps.setString(2,password);  
            ResultSet rs=ps.executeQuery(); 
            if(rs.next())
            {
                if("admin".equalsIgnoreCase(rs.getString("emp_role")))
                    return 0;
                else
                    return 1;
            }
            else
            {
                return 2;
            }}
            return 2;
}  

public static String getempdivision(int id) throws ClassNotFoundException, SQLException
{
        String division="" ;
        int division_id=0;
        Connection con=DbConnection.getConnection();  
        try{
        PreparedStatement ps=con.prepareStatement("select emp_division from emploeemaster where emploeemaster.emp_id = "+id);  
        ResultSet rs=ps.executeQuery(); 
        if(rs.next())
        {
            division_id=rs.getInt("emp_division");
        }
        if(division_id!=0)
        {
            ps=con.prepareStatement("select prod_name from productmaster where productmaster.prod_id = "+division_id);  
            rs=ps.executeQuery(); 
            if(rs.next())
            {
                division=rs.getString("prod_name");
            } 
        }}
        finally{
            if(con!=null){
                con.close();
            }
        }
        
        return division;

}
     public static List<String> getCurrentId(int lid) throws ClassNotFoundException, SQLException
    {  
        List<String> list=new ArrayList<String>();  
        Connection con=null;
        try{
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement(" SELECT emp_name FROM emploeemaster WHERE emp_id='"+lid+"'");  
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list.add(rs.getString("emp_name"));  
        } }
        finally{
            if(con!=null){
                con.close();
            }
        }
        return list;  
    }
     
     public static String geteName(String ename) throws ClassNotFoundException, SQLException
    {  
    	   String list="";  
    	 int a=0;
    	 if(ename.isEmpty()||ename.length()==0){
    		 
    	 }else{
    		 try{
    			 a =Integer.parseInt(ename);
    			 
    			 
    			 
    		 }catch (NumberFormatException ex){
    			 System.out.println("n format excep");
    			 list=ename;
    			 return list;  
    		 }
    		
    	 }
    	
    
        Connection con=null;
        try{
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("SELECT emp_name FROM emploeemaster WHERE emp_id="+a);  
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list=rs.getString("emp_name");
           // System.out.println("emp name isss"+rs.getString("emp_name"));
        }  
        }
        finally{
            if(con!=null){
                con.close();
            }
        }
        return list;  
    }  
     
       public static String getDivName(String id) throws ClassNotFoundException, SQLException
    {  
       String list="";  
        Connection con=null;
        try{
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("SELECT prod_name FROM productmaster WHERE prod_id="+id);  
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list=rs.getString("prod_name");  
        }  }
        finally{
            if(con!=null){
                con.close();
            }
        }
        return list;  
    }  
       
       public static ArrayList<String> getAllDivi() throws ClassNotFoundException, SQLException
       {
          ArrayList<String> list =new ArrayList<String>();
        Connection con=DbConnection.getConnection();
        try{
        PreparedStatement st=con.prepareCall("SELECT prod_name FROM productmaster");
         ResultSet rs=st.executeQuery();
//        PreparedStatement ps=con.prepareStatement("SELECT prod_name FROM productmaster");  
//        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list.add(rs.getString("prod_name"));  
        }  }
        finally{
            if(con!=null){
                con.close();
            }
        }
        return list;
         
       }
public static String getEmp(int id) throws ClassNotFoundException, SQLException
    {  
       String list="";  
        Connection con=null;
        try{
        con=DbConnection.getConnection();   
        PreparedStatement ps=con.prepareStatement("SELECT emp_id FROM emploeemaster WHERE emp_id="+id);  
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list=rs.getString("emp_id");  
        }  }
        finally{
            if(con!=null){
                con.close();
            }
        }
        return list;  
    }  

public static String getdivEmpName(int id) throws ClassNotFoundException, SQLException
    {  
       String list="";  
        Connection con=null;
        con=DbConnection.getConnection(); 
        try{
//        PreparedStatement ps=con.prepareStatement("SELECT emp_division,p.prod_name FROM emploeemaster e INNER JOIN productmaster p WHERE emp_id='"+id+"'");  
        PreparedStatement ps=con.prepareStatement("SELECT emp_division FROM emploeemaster WHERE emp_id="+id);         
        ResultSet rs=ps.executeQuery();  
        while(rs.next())
        {  
            list=rs.getString("emp_division");  
        }  }
        finally{
            if(con!=null){
                con.close();
            }
        }
        return list;  
    }  

public static String getActive(String mail) throws ClassNotFoundException, SQLException
{
  System.out.println("in firsttt aoo");
    String m="";
    Connection con=null;
    try{
    con=DbConnection.getConnection();
    PreparedStatement ps=con.prepareStatement("SELECT emp_active FROM emploeemaster WHERE emp_email='"+mail+"'");
    ResultSet rs=ps.executeQuery();
    while(rs.next())
    {
        m=rs.getString("emp_active");  
    }}
    finally{
            if(con!=null){
                con.close();
            }
        }
   
        return m;
}
}


