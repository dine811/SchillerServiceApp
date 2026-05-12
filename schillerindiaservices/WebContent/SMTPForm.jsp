<%-- 
    Document   : SMTPForm
    Created on : Oct 16, 2017, 4:07:18 PM
    Author     : ShineLoGics
--%>

<%@page import="com.schillerindiaservices.bean.Smtp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
         <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Sender Settings</title>

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
                    <h3 class="page-header">SMTP Settings </h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                           SMTP Center
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <form role="form" action="#" method="post">
                                    <div class="col-lg-6">
                                        <%
                                            int id=0;
                                            String mail="";
                                            String pass="";
                                            String port="";
                                            if((request.getAttribute("mail"))!=null)
                                            {
                                                Smtp em=(Smtp) request.getAttribute("mail");
                                                id=em.getId();
                                            }
                                        %>
                                        <input type="hidden" name="id" value="<%=id%>">

                                       
                                        <div class="form-group">
                                                <label>Email</label>
                                                <input type="email" name="email" value="<%=mail%>" minlength="1" maxlength="40"  value="" class="form-control"  required="">
                                        </div>
                                        <div class="form-group">
                                                <label>Password</label>
                                                <input type="text" name="password" value="<%=pass%>" minlength="1" maxlength="40" value="" class="form-control" required="">
                                        </div>
                                        <div class="form-group">
                                                <label>Port No.</label>
                                                <input type="text" name="port" value="<%=port%>" minlength="1" maxlength="40" value="" class="form-control" required="">
                                        </div>
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
