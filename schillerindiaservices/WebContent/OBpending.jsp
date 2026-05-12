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
                    <h3 class="page-header">OBPending List </h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                       
                       
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <form role="form" action="OBPendingController" method="POST">
                            
                                <div align="Left">
                                        <button type="submit" class="btn btn-success">OB Pending</button>
                                    </div>
                                 <br>
                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                            <th>Supplier Id</th>
                                            <th>Repaired Brd Stock Date</th>
                                            <th>Ship Date From Service Center</th>
                                            
                                            <!--<th>Edit/Delete</th>-->
                                    </tr>
                                </thead>
                                <tbody>
                                    <%  
                                            int id=0;
                                            Date t1=null;
                                            Date t2=null;
                                           List list = (List)request.getAttribute("OBPending");
                                            if(list!=null)
                                            {
                                                Iterator itr=list.iterator();
                                                while(itr.hasNext())
                                                {
                                                    service_master d=(service_master)itr.next();
                                                    id=d.getSerId();
                                                    t1=(Date)d.getRepairedBrdStkDate();
                                                    t2=(Date) d.getShipDtFrmSerCntr();
                                                    %>
                                                    
                                                        <td><%=id%></td>
                                                        <td><%=t1%></td>
                                                        <td><%=t2%></td>
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
