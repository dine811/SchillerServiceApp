<!DOCTYPE html>
<%@ page errorPage="error.jsp" %>  
<%@page import="com.schillerindiaservices.bean.Suppliermaster"%>
<%@page import="com.schillerindiaservices.Dao.SupplierDao"%>
<%@page import="com.schillerindiaservices.Dao.ProductDao"%>
<%@page import="com.schillerindiaservices.Dao.ModelDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

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

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

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
                    <h3 class="page-header">Settings</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                   
                            <div class="row">
                                <form role="form" action="SelectValuesController" method="post">
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <%
                                            session=request.getSession();
                                            String role=(String)session.getAttribute("logrole");
                                            if("admin".equalsIgnoreCase(role))
                                            {   
                                                %><a href="DropdownValuesForm.jsp"><button type="button" class="btn btn-outline btn-default" data-toggle="tooltip" data-placement="top" title="Dynamically add or remove the values in select box"><b>Dropdown Settings</b></button> </a><%
                                            }
                                        %>
                                        
                                    </div>
                                    
                                </div>
                                <div class="col-lg-6">
                                    <div class="form-group">
                                           
                                    </div>
                                    <div class="form-group">
                                            
                                    </div>
                                   
                                </div>
                                </form> 

                            </div>
                            <!-- /.row (nested) -->
                       
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
