<!DOCTYPE html>
<%@ page errorPage="error.jsp" %>  
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.schillerindiaservices.Dao.ProductDao"%>
<%@page import="com.schillerindiaservices.bean.Model"%>


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
                    <h3 class="page-header">Add Model</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                           Enter the details of the Model
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <form role="form" action="ModelController" method="post">
                                <div class="col-lg-6">
                                <%
                                int ID=0;
                                String productname  ="";
                                String modelno="";
                                String modelname ="";
                                String modeldesc="";
                                
                                if(request.getAttribute("editablerecord")!=null)
                                {
                                    Model d1=(Model)request.getAttribute("editablerecord");
                                    ID=d1.getModelId();
                                    
                                    int productid=d1.getProductId();
                                    productname    =ProductDao.getProdName(productid);
                                    
                                    modelno =d1.getModelNo();
                                    modelname   =d1.getModelName();
                                    modeldesc  =d1.getModelDesc();
                                }
                            %>
                                        
                                <input type="hidden" name="id" class="form-control" value="<%=ID%>">
                                        
                                <div class="form-group">
                                            <label>Product</label>
                                            <select class="form-control" name="productname">
                                                <%
                                                    List<String> list=ProductDao.getAllProducts();
                                                    Iterator itr=list.iterator();
                                                    while(itr.hasNext())
                                                    {
                                                        %><option><%=itr.next()%></option><%
                                                    }
                                                %>
                                            </select>
                                </div>
                                <div class="form-group">
                                            <label>Model Number</label>
                                            <input type="text" name="modelno" value="<%=modelno%>" class="form-control" placeholder="Enter Model Number" required>
                                </div>
                                <div class="form-group">
                                            <label>Model Name</label>
                                            <input type="text" name="modelname" value="<%=modelname%>" class="form-control" placeholder="Enter Model Name" required>
                                </div>
                                <div class="form-group">
                                            <label>Model Description</label>
                                            <input type="text" name="modeldesc" value="<%=modeldesc%>" class="form-control" placeholder="Enter Model Description" required>
                                </div>
                                <div align="left">
                                                            <center><button type="submit" class="btn btn-success">SAVE</button></center>
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
