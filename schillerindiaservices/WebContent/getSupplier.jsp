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
    try
    {  
        Connection con;
        con=DbConnection.getConnection(); 
          PreparedStatement ps=con.prepareStatement("SELECT DISTINCT sup_name,product_id FROM model WHERE product_id='"+ids+"'"); 
        
         ResultSet rs=ps.executeQuery(); 
       
        while(rs.next())
        {  
   
             int x=rs.getInt("product_id");
              out.print("<option value="+x+">"+rs.getString("sup_name")+"</option>"); 
        }  
        ps.close();
        con.close();  
    }catch(Exception e){
}
}
%>  