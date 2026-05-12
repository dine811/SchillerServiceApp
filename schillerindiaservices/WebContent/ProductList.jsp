<!DOCTYPE html>
<%@ page errorPage="error.jsp" %>  
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.schillerindiaservices.bean.Productmaster"%>
<%@page import="com.schillerindiaservices.Dao.ProductDao"%>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SchillerIndia</title>
<link rel="icon" type="image/png" href="img/logo.png"/>
    <!-- Bootstrap Core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

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
                    <h3 class="page-header">Division List</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                                Details of Divisions
                        </div>
                       
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <form role="form" action="EmployeeController" method="POST">
                            <div align="Left">
                                <a href="ProductForm.jsp"><button type="button" class="btn btn-primary">Add Division</button></a>
                                <!--<a href="ProductAndDealer.jsp"><button type="button" class="btn btn-primary">Division, Supplier and Engineer Relations</button></a>-->

                            </div>
                                 <br>
                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                            <th>Division Id</th>
                                            <th>Name</th>
                                            <th>Description</th>
                                            <th>Edit/Delete</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%  
                                            int rowstatus=1;
                                            int id=0;
                                            String name="";
                                            String description="";
                                            
                                            List<Productmaster> list=ProductDao.getAllRecords();
                                            if(list!=null)
                                            {
                                                Iterator itr=list.iterator();
                                                while(itr.hasNext())
                                                {
                                                    Productmaster d=(Productmaster)itr.next();
                                                    id=d.getProdId();
                                                    name=d.getProdName();
                                                    description=d.getProdDescription();
                                                    
                                                    
                                                    if(rowstatus==1)
                                                    {
                                                        %><tr class="odd gradeA"> <%
                                                        rowstatus=2;
                                                    }
                                                    else
                                                    {
                                                        %><tr class="even gradeA"> <%
                                                        rowstatus=1;
                                                    }
                                                    %>
                                                    
                                                        <td><%=id%></td>
                                                        <td><%=name%></td>
                                                        <td><%=description%></td>
                                                        <td><a href="ProductsController?id=<%=id%>&action=edit">Edit</a>|<a href="ProductsController?id=<%=id%>&action=delete">Delete</a></td>
                                                    </tr>
                                        <%
                                                }
                                            }
                                        %>
                                </tbody>
                            </table>
                            <!-- /.table-responsive -->
                            </form>
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

    <!-- DataTables JavaScript -->
    <script src="vendor/datatables/js/jquery.dataTables.min.js"></script>
    <script src="vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
    <script src="vendor/datatables-responsive/dataTables.responsive.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
            responsive: true
        });
    });
    </script>

</body>

</html>
