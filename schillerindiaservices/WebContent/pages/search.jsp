<%@page import="com.schillerindiaservices.connection.DbConnection"%>
<%@ page import="java.sql.*" %>  
<%  
String name=request.getParameter("val");  
if(name==null||name.trim().equals("")){  
out.print("<p>Please enter name!</p>");  
}else{  
try{  
    Connection con;
    con=DbConnection.getConnection();
//Class.forName("com.mysql.jdbc.Driver");  
//Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/airceldistribution","root","");
PreparedStatement ps=con.prepareStatement("select * from emploeemaster where emp_name like '"+name+"%'");  
ResultSet rs=ps.executeQuery();  
  
if(!rs.isBeforeFirst()) 
{      
    out.println("<p>No Record Found!</p>");   
}
else
{  
    out.print("<table border='1' cellpadding='2' width='100%'>");  
    out.print("<tr><th>Id</th><th>Name</th><th>Email</th><th>Address</th></tr>");  
    while(rs.next())
    {  
        out.print("<tr><td>"+rs.getInt(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(3)+"</td></tr>");  
    }  
    out.print("</table>");  
}//end of else for rs.isBeforeFirst  
con.close();  
}catch(Exception e){out.print(e);}  
}//end of else  
%>  