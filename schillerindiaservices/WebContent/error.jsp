<%@ page isErrorPage="true" %>  
  

<%
    session=request.getSession();  
            session.invalidate();  
    %>
  
<h3>Session timeout Login Again!</h3>  
<a href="login.jsp">LOGIN </a>      