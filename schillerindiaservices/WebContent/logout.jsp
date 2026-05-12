<%-- 
    Document   : logout
    Created on : Jun 26, 2017, 2:44:54 PM
    Author     : MR
--%>
<%@ page errorPage="error.jsp" %>  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       <%
            session.invalidate();
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma","no-cache");
            response.setDateHeader("Expires", 0);
            response.sendRedirect("index.html");
       %>
     </body>
</html>
