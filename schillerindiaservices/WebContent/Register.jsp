<%-- 
    Document   : Register
    Created on : Aug 16, 2017, 2:25:08 PM
    Author     : SHINELOGICS
--%>

<%@page import="com.schillerindiaservices.Dao.ProductDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.schillerindiaservices.Dao.BranchDao"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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

        <%@include file="admindashboard.jsp" %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Add User</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Enter the details of User
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                
                                <form role="form" action="AddUserController" method="POST">
                                    
                                
                                <div class="col-lg-4">
                                    <!--<input type="hidden" name="id" class="form-control">-->
                                    <div class="form-group">
                                        <label> User Name</label>
                                        <input name="emp_name" class="form-control" placeholder="Enter name" required>
                                    </div>
                                    <div class="form-group">
                                        <label> User Address</label>
                                        <input name="emp_add" class="form-control" placeholder="Enter address" required>
                                    </div>
                                    <div class="form-group">
                                        <label> User Email</label>
                                        <input type="email" name="emp_email" class="form-control" placeholder="Enter email-id" required>
                                            
                                    </div>
                                    <div class="form-group">
                                        <label>User Password</label>
                                        <input name="emp_pass" type="password" class="form-control"  placeholder="Enter password" required>
                                    </div>
                                
                                  </div>  
                                   
                                
                                <div class="col-lg-4">
                                     
                                    <div class="form-group">
                                        <label>User Mobile</label >
                                        <input type="text" name="emp_mob" class="form-control" placeholder="Enter mobile number" required>
                                    </div>
                                    <div class="form-group">
                                        <label>User Department</label>
                                        <select name="dept" class="form-control" placeholder="Select department" required>
                                            <option>Sales</option>
                                            <option>Service</option>
                                        </select>
                                    </div>
                                      <div class="form-group" >
                                        <label>User Role</label>
                                        <select name="emp_role" class="form-control" placehoder="Select Type" required>
                                            <option>Admin</option>
                                            <option>Engineer</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>User Active</label>
                                        <select name="emp_active" class="form-control" placeholder="Select Employee status" required>
                                            <option>select </option>
                                            <option>YES</option>
                                            <option>NO </option>
                                        </select>
                                    </div>
                                </div>
                                <div align="center">
                                    <center><button type="submit" class="btn btn-success" style="margin-top: 250px;width: 120px;">SAVE</button></center>
                                </div>
                           </form>           
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
