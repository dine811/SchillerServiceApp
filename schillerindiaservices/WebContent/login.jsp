<%-- 
    Document   : airceladmin
    Created on : Jun 5, 2017, 2:30:53 PM
    Author     : MR
--%>
<%@page import="org.apache.poi.util.SystemOutLogger"%>
<%@ page errorPage="error.jsp" %>  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.schillerindiaservices.connection.DbConnection"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.schillerindiaservices.Dao.EmployeeDao"%>

<!DOCTYPE html>
<html>
    <head>
                
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <title>SchillerIndia</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/landing-page.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">
    <link rel="icon" type="image/png" href="img/logo.png"/>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    </head>
    <body>
        <%  
         
            String username=(String)request.getParameter("email");
            String password=(String)request.getParameter("password");
           
            String stat=EmployeeDao.getActive(username);
            int i=EmployeeDao.checkUser(username, password, stat);  
            if(i==0 || i==1)
            {  
                Connection con=DbConnection.getConnection();  
                PreparedStatement ps=con.prepareStatement("select * from emploeemaster where emp_email=? and emp_password=?");  
                ps.setString(1,username);  
                ps.setString(2,password);  
                ResultSet rs=ps.executeQuery(); 
                if(rs.next())
                {
                    int userid=rs.getInt("emp_id");
                    String role=rs.getString("emp_role");
                    String name=rs.getString("emp_name");
                    session.setAttribute("logname", name);
                    session.setAttribute("logusername", username);
                    session.setAttribute("loguserid", userid);
                    session.setAttribute("logrole", role);
                }
                
               %><jsp:forward page="index.jsp" /><%
            }
            else
            {
        %>
                <jsp:include page="login.html" />
        <%
            if(stat.equalsIgnoreCase("yes"))
            {
                out.print("<center><h4 style=color:red>Sorry! Check username and password !<h4></center>" );
            }
            else
            {
                out.print("<center><h4 style=color:red>Your Account not activated<h4></center>" );
            }
        }
        %>  
    </body>
</html>
