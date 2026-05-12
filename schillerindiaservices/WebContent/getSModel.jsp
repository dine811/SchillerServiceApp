<%@page import="com.schillerindiaservices.connection.DbConnection"%>
<%@ page errorPage="error.jsp" %>  
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%  
String name=request.getParameter("val");  
System.out.println("the name isss"+name);
if(name==null||name.trim().equals(""))
{  
    out.print("<p>Please enter name!</p>");  
}
else
{  
    
    try
    {  
        Connection con;
        con=DbConnection.getConnection();
//        Class.forName("com.mysql.jdbc.Driver");  
//        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schillerindiaservices","root","");
        PreparedStatement ps=con.prepareStatement("SELECT model_name,product_id FROM model WHERE product_id='"+name+"'");  
        ResultSet rs=ps.executeQuery(); 
       
        while(rs.next())
        {  
   
             int x=rs.getInt("product_id");
              out.print("<option value="+x+">"+rs.getString("model_name")+"</option>");  
        }  
        ps.close();
        con.close();  
    }catch(Exception e){
}
}
%>  