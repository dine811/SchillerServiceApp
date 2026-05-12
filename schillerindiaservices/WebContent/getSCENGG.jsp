<%-- 
    Document   : getSCENGG
    Created on : Oct 10, 2017, 12:33:50 PM
    Author     : ShineLoGics
--%>

<%-- 
    Document   : getFModel
    Created on : Oct 9, 2017, 6:40:58 PM
    Author     : ShineLoGics
--%>

<%@page import="com.schillerindiaservices.Dao.ProductDao"%>
<%@page import="com.schillerindiaservices.connection.DbConnection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.schillerindiaservices.Dao.ModelDao"%>

<%  
String name=request.getParameter("val");  
 String role=(String)session.getAttribute("logrole");
if(name==null||name.trim().equals(""))
{  
    out.print("<p>Please enter name!</p>");  
}
else
{  
    int ids=0;
    ids=ProductDao.getid(name);
    System.out.println(ids+"idss isss");
    try
    {  
        Connection con;
        con=DbConnection.getConnection(); 
          PreparedStatement ps=con.prepareStatement("SELECT emp_name,emp_id FROM emploeemaster WHERE emp_division='"+ids+"'"); 
       
         ResultSet rs=ps.executeQuery(); 
       out.print("<option value=''>Select Engineer</option>");
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