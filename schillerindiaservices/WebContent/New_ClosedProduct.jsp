<!DOCTYPE html>
<%@ page errorPage="error.jsp" %>  
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Date"%>
<%@page import="com.schillerindiaservices.bean.service_master"%>
<%@page import="com.schillerindiaservices.Dao.UnderRepairDao"%>
<%@page import="com.schillerindiaservices.controller.UnderRepairController"%>
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
       <%
            String role=(String)session.getAttribute("logrole");
            if(role.equalsIgnoreCase("admin"))
            {
                %>
                <%@include file="admindashboard.jsp" %>
                <%
            }else
{
%>
<%@include  file="VPDashboard.jsp" %>
<%
}
            %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Closed Product </h3>
                    <div align="left">
                            <div class="col-lg-1" >
                        <form role="form" action="Export_NewClosedController" method="post">
                            <button type="submit" class="btn btn-success"> Export </button>
                        </form>
                            </div>
                    
                    </div>
                    
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12" style="margin-top: 15px;">
                    
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                               Closed Product List
                        </div>
                       
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <form role="form" action="New_ClosedController" method="get">
                            
<!--                                <div align="Left">
                                        <button type="submit" class="btn btn-success">Under Repair</button>
                                    </div>
                                 <br>-->
                              <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                           <th>Id</th>
                                            <th>Entry Date</th>
                                            <th>Sc RNo</th>
                                            <th>Sc Eng</th>
                                            <th>Frn No</th>
                                            <th>Region</th>
                                            <th>Eng</th>
                                            <th>Cust Name</th>
                                            <th>Model</th>
                                            <th>Unit Status</th>
                                            <th>Def Mod / brd name</th>
                                            <th>Def Gir No</th>
                                            <th>Type of work</th>
                                            <th>Days taken to complete</th>
                                    </tr>
                                </thead>
                                 <thead>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td><input type="text" data-column="1" class="search-input-text" placeholder="Search Item" style="width: 120px;"></td>                                                                                                               
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                </thead>
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

    
    
   <script>
        
            $(document).ready(function() {
             
    var table= $('#dataTables-example').DataTable( {
        "processing": true,
        "serverSide": true,
        "ajax":  "New_ClosedController",
        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {  
                $('td:eq(1)', nRow).html(aData[1]);  
//                $('td:eq(12)', nRow).html('<a href="ClosedController?action=delete&id='+aData[0]+'">Delete</a>');  
//                $('td:eq(12)', nRow).html('<a href="ClosedController?action=edit&id='+aData[0]+'">Edit</a>');  
                return nRow;  
            },  
} );

$("#dynamic-table_filter").css("display","none");

$('.search-input-text').on('keyup click', function(){
        var i=$(this).attr('data-column');
        var v=$(this).val();
        table.columns(i).search(v).draw();
    }
  );
     });   
        </script> 

</body>

</html>
