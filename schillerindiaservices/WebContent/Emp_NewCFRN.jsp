<!DOCTYPE html>
<%--<%@ page errorPage="error.jsp" %>--%>  
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.schillerindiaservices.bean.Companymaster"%>
<%@page import="com.schillerindiaservices.Dao.EmployeeDao"%>
<%@page import="com.schillerindiaservices.Dao.CompanyDao"%>
<%@page import="com.schillerindiaservices.Dao.ServiceDao"%>
<%@page import="com.schillerindiaservices.Dao.ModelDao"%>
<%@page import="com.schillerindiaservices.Dao.DropdownDao"%>
<%@page import="com.schillerindiaservices.bean.service_master"%>
<%@page import="java.sql.Date"%>


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
      <%
            session=request.getSession();
            String role=(String)session.getAttribute("logrole");
            if("admin".equalsIgnoreCase(role))
            {   
                %><%@include file="admindashboard.jsp" %><%
            }
            else
            {   
                %><%@include file="engineerdashboard.jsp" %><%
            }
        %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Completed FRN</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Details of Services
                        </div>
                       
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="col-sm-6">
                                <div class="col-sm-2">
                            <form action="Emp_NewCFRNController" method="post">
                                <input type="submit" value="Export" class="btn btn-primary">
                             </form>
                                </div>
                           </div>
                            <form role="form" style="margin-top: 60px;" >
                            
                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                             <th>Id</th>
                                            <th>Entry Date</th>
                                            <th>Sc RNo</th>
                                            <th>Sc Eng</th>
                                            <th>Frn No</th>
                                            <th>Region</th>
                                            <th>Engineer</th>
                                            <th>Cust Name</th>
                                            <th>Model</th>
                                            <th>Unit St</th>
                                            <th>Def Mod / brd name</th>
                                            <th>Def Gir No</th>
                                            <th>Type of work</th>
                                            <th>Pend Days</th>
                                            
                                    </tr>
                                </thead>
                                <tbody>
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
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>
                                                    </tr>
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
<!--    <script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
            responsive: true
        });
    });
    </script>-->
    <script>
        
            $(document).ready(function() {
             
    var table= $('#dataTables-example').DataTable( {
        "processing": true,
        "serverSide": true,
        "ajax":  "Emp_NewCFRNController",
        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {  
                $('td:eq(1)', nRow).html(aData[1]); 
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
