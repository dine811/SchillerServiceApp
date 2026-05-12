<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.schillerindiaservices.connection.DbConnection"%>
<%@page import="com.schillerindiaservices.bean.Email"%>
<!DOCTYPE html>
<%--<%@ page errorPage="error.jsp" %>--%>  
<%@page import="com.schillerindiaservices.bean.Dealermaster"%>
<%@page import="com.schillerindiaservices.Dao.DealerDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.schillerindiaservices.Dao.RegionDao"%>
<%@page import="com.schillerindiaservices.bean.Region"%>

<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SchillerIndia</title>

    <!-- Bootstrap Core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link rel="icon" type="image/png" href="img/logo.png"/>

</head>

<body>

    <div id="wrapper">
<%
//            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//            response.setHeader("Pragma","no-cache");
//            response.setDateHeader("Expires", 0);
            
            session=request.getSession();
            if(session.getAttribute("logname")==null)
            {   
                response.sendRedirect("index.html");
            }
%>
        <%@include file="admindashboard.jsp" %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Email </h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                           Modify Email
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <form role="form" action="EmailController" method="post">
                                    <div class="col-lg-6">
                                        <%
                                            String ng=(String)session.getAttribute("sender");
                                            int id=0;
                                            String mail="";
                                            String name="";
                                            String email="";
                                            String pass="";
                                            String port="";
                                            String ssl="";
                                            String msgbody="";
                                            String hos_val="";
                                            
                                            if((request.getAttribute("mail"))!=null)
                                            {
                                                Email em=(Email) request.getAttribute("mail");
                                                id=em.getId();
                                                mail=em.getMailid();
                                                name=em.getName();
                                                email=em.getSemail();
                                                pass=em.getPasswordF();
                                                port=em.getPortNo();
                                                ssl=em.getSslNo();
                                                msgbody=em.getMsgBody();
                                                hos_val=em.getHosingId();
                                            }
                                            
                                        %>
                                        <input type="hidden" name="id" value="<%=id%>">

                                       
                                        <div class="form-group">
                                                <label>Mail id</label>
                                                <input type="email" name="mailid" value="<%=mail%>" minlength="1" maxlength="40"  value="" class="form-control"  required="">
                                        </div>
                                        <div class="form-group">
                                                <label>Name </label>
                                                <input type="text" name="name" value="<%=name%>" minlength="1" maxlength="40" value="" class="form-control" required="">
                                        </div>
                                         <div class="form-group">
                                             <label>Message Body </label>
                                                <input type="text" name="msgbody" value="<%=msgbody%>" minlength="1" maxlength="250" value="" class="form-control" required="">
                                        </div>
                                            <%
                                                if(id==1)
                                                {
                                               %>
                                        <div class="form-group">
                                                <label>Sender Mail</label>
                                                <input type="text" name="smail" value="<%=email%>" minlength="1" maxlength="40" value="" class="form-control" >
                                        </div>
                                        <div class="form-group">
                                                <label>password </label>
                                                <input type="text" name="password" value="<%=pass%>" minlength="1" maxlength="40" value="" class="form-control" >
                                        </div>
                                        <div class="form-group">
                                                <label>Port No. </label>
                                                <input type="text" name="port" value="<%=port%>" minlength="1" maxlength="40" value="" class="form-control" >
                                        </div>
                                        <div class="form-group">
                                                <label>SSl </label>
                                                <input type="text" name="ssl" value="<%=ssl%>" minlength="1" maxlength="40" value="" class="form-control" >
                                        </div>
                                        <div class="form-group">
                                                <label>HOST </label>
                                                <input type="text" name="host" value="<%=hos_val%>" minlength="1" maxlength="40" value="" class="form-control" >
                                        </div>
                                         <%
                                                   } else
                                                    {
                                                    %>
                                                   <div class="form-group">
                                                <input type="hidden" name="smail" value="<%=email%>" minlength="1" maxlength="40" value="" class="form-control" >
                                        </div>
                                        <div class="form-group">
                                                <input type="hidden" name="password" value="<%=pass%>" minlength="1" maxlength="40" value="" class="form-control" >
                                        </div>
                                        <div class="form-group">
                                                <input type="hidden" name="port" value="<%=port%>" minlength="1" maxlength="40" value="" class="form-control" >
                                        </div>
                                        <div class="form-group">
                                                <input type="hidden" name="ssl" value="<%=ssl%>" minlength="1" maxlength="40" value="" class="form-control" >
                                        </div>
                                        <div class="form-group">
                                                <input type="hidden" name="host" value="<%=hos_val%>" minlength="1" maxlength="40" value="" class="form-control" >
                                        </div> 
                                        <%
                                            }
                                            %>
                                         </div>
                                      
                                    <div>
                                        <div class="col-lg-6">
                                          
                                            <div align="center">
                                                <center><input type="submit" class="btn btn-success" value="Save"></center>
                                            </div>           
                                        </div>
                                    </div>
                                                    
                                </form> 
                            </div>
                            <!-- /.row (nested) -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>

</body>

</html>
