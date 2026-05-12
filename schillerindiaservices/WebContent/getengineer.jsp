<%@page import="com.schillerindiaservices.connection.DbConnection"%>
<%@ page errorPage="error.jsp" %>  
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%  
String name=request.getParameter("val");  
if(name==null||name.trim().equals(""))
{  
    out.print("<p>Please enter name!</p>");  
}
else
{  
    System.out.println("the region name iss"+name);
   try
    {  
        Connection con;
        con=DbConnection.getConnection();
//        Class.forName("com.mysql.jdbc.Driver");  
//        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schillerindiaservices","root","");
        //PreparedStatement ps=con.prepareStatement("select  emp_id,emp_name,emp_branch,emp_division from emploeemaster where emp_branch='"+name+"'"); 
        
        PreparedStatement ps=con.prepareStatement("select emp_id,emp_name,emp_branch,emp_division from emploeemaster e inner join branch b on b.branchid=e.emp_branch inner join region r on r.region_id=b.regionid where  r.region_name='"+name+"'");  

        ResultSet rs=ps.executeQuery(); 
       
        while(rs.next())
        {  
   
             int x=rs.getInt("emp_id");
              out.print("<option value="+x+">"+rs.getString("emp_name")+"</option>");  
        }  
        ps.close();
        con.close();  
    }catch(Exception e){
}
}
%>  