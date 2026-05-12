<%@page import="com.schillerindiaservices.connection.DbConnection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.schillerindiaservices.Dao.ModelDao"%>

<%  
String name=request.getParameter("val");  
//System.out.println(" the sup-name iss222"+name);
 String role=(String)session.getAttribute("logrole");
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
          PreparedStatement ps=con.prepareStatement("SELECT model_name,model_id FROM model WHERE sup_name='"+name+"'");  
         ResultSet rs=ps.executeQuery(); 
       
        while(rs.next())
        {  
   
             int x=rs.getInt("model_id");
              out.print("<option value="+x+">"+rs.getString("model_name")+"</option>");  
        }  
        ps.close();
        con.close();  
    }catch(Exception e){
}
}
%>  