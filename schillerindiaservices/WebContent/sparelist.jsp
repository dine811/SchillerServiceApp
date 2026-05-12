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
String part_no=request.getParameter("part_no");  
String division=request.getParameter("division");  

 String role=(String)session.getAttribute("logrole");
 
    try
    {  
        Connection con;
        con=DbConnection.getConnection(); 
          PreparedStatement ps=con.prepareStatement("SELECT *  FROM sparepart_master s inner join dropdownmaster d on d.ddvalue = s.def_type where part_number='"+part_no+"' and division='"+division+"'"); 
        
         ResultSet rs=ps.executeQuery(); 
       
        while(rs.next())
        {  
        	
   
              out.print(rs.getString("comp_models")+","+rs.getString("def_mod_brd_name")+","+rs.getString("dd_id")); 
        }  
        ps.close();
        con.close();  
    }catch(Exception e){
}

%>  