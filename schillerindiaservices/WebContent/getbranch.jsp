<%@ page errorPage="error.jsp" %>  
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.schillerindiaservices.connection.DbConnection" %>
<%  
String name=request.getParameter("val");  
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
        PreparedStatement ps=con.prepareStatement("select region_id from region where region_name='"+name+"'");  
        ResultSet rs=ps.executeQuery();  
        if(rs.next())
        {
            int regid=rs.getInt("region_id");
            ps=con.prepareStatement("select branchname,branchid from branch where regionid="+regid+"");  
            rs=ps.executeQuery();  
            
            while(rs.next())
            {  
                int x=rs.getInt("branchid");
                    out.print("<option value="+x+">"+rs.getString("branchname")+"</option>");  
            }  
        }
        ps.close();
        con.close();  
    }catch(Exception e){out.print(e);}  
}  
%>  