<%@ page errorPage="error.jsp" %>  
<%--<%@page import="java.sql.DriverManager"%>--%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.schillerindiaservices.Dao.DropdownvaluesDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

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
        List<String> list=DropdownvaluesDao.getdpvaluesfor(name);
        Iterator itr=list.iterator();
        while(itr.hasNext())
        {
            out.print("<option>"+itr.next()+"</option>");
        }
//        for (int p=0; p<dpdvalues.length; p++)
//        {
//            out.print("<option>"+dpdvalues[p]+"</option>");
//        }
    }catch(Exception e){out.print(e);}  
}  
%>  