<!DOCTYPE html>
<%@page import="java.util.Iterator" %>
    <%@page import="java.util.List" %>
        <%@page import="com.schillerindiaservices.bean.Companymaster" %>
            <%@page import="com.schillerindiaservices.Dao.EmployeeDao" %>
                <%@page import="com.schillerindiaservices.Dao.CompanyDao" %>
                    <%@page import="com.schillerindiaservices.Dao.ServiceDao" %>
                        <%@page import="com.schillerindiaservices.Dao.ModelDao" %>
                            <%@page import="com.schillerindiaservices.Dao.DropdownDao" %>
                                <%@page import="com.schillerindiaservices.bean.service_master" %>
                                    <%@page import="java.sql.Date" %>
                                        <%@ page errorPage="error.jsp" %>


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
                                                <link href="vendor/datatables-plugins/dataTables.bootstrap.css"
                                                    rel="stylesheet">

                                                <!-- DataTables Responsive CSS -->
                                                <link href="vendor/datatables-responsive/dataTables.responsive.css"
                                                    rel="stylesheet">

                                                <!-- Custom CSS -->
                                                <link href="dist/css/sb-admin-2.css" rel="stylesheet">
                                                <link href="dist/css/datetextremover.css" rel="stylesheet">



                                                <!-- Custom Fonts -->
                                                <link href="vendor/font-awesome/css/font-awesome.min.css"
                                                    rel="stylesheet" type="text/css">
                                                <!-- Custom Fonts -->
                                                <link href="vendor/font-awesome/css/font-awesome.min.css"
                                                    rel="stylesheet" type="text/css">
                                                <link rel="icon" type="image/png" href="img/logo.png" />
                                                <link href="dist/css/jquery-ui.css" rel="stylesheet">
                                                <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
                                                <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
                                                <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
                                                <link rel="icon" type="image/png" href="img/logo.png" />
                                            </head>

                                            <body>

                                                <div id="wrapper">
                                                    <% //
                                                        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"
                                                        ); // response.setHeader("Pragma","no-cache"); //
                                                        response.setDateHeader("Expires", 0);
                                                        session=request.getSession();
                                                        if(session.getAttribute("logname")==null) {
                                                        response.sendRedirect("index.html"); } %>
                                                        <% session=request.getSession(); String
                                                            role=(String)session.getAttribute("logrole");
                                                            if("admin".equalsIgnoreCase(role)) { %>
                                                            <%@include file="admindashboard.jsp" %>
                                                                <% } else if(role.equalsIgnoreCase("ViceChancellor")) {
                                                                    %>
                                                                    <%@include file="VPDashboard.jsp" %>
                                                                        <% }else { %>
                                                                            <%@include file="engineerdashboard.jsp" %>
                                                                                <% } %>

                                                                                    <div id="page-wrapper">
                                                                                        <div class="row">
                                                                                            <div class="col-lg-12">
                                                                                                <h3 class="page-header">
                                                                                                    Service List</h3>
                                                                                                <div align="left">
                                                                                                    <div
                                                                                                        class="col-lg-10">
                                                                                                        <form
                                                                                                            role="form"
                                                                                                            action="Service_Controller"
                                                                                                            method="post">
                                                                                                            <div class="form-group col-md-4"
                                                                                                                style="padding-right: 50px;">
                                                                                                                <label
                                                                                                                    class="col-sm-8 "
                                                                                                                    for="form-field-1">
                                                                                                                    From
                                                                                                                    Date
                                                                                                                </label>

                                                                                                                <div
                                                                                                                    class="col-sm-12">
                                                                                                                    <input
                                                                                                                        type="text"
                                                                                                                        name="from"
                                                                                                                        class="form-control"
                                                                                                                        id="datepicker-1"
                                                                                                                        required="">
                                                                                                                </div>
                                                                                                            </div>

                                                                                                            <div class="form-group col-md-3"
                                                                                                                style="padding-right: 50px;">
                                                                                                                <label
                                                                                                                    class="col-sm-8 "
                                                                                                                    for="form-field-1">
                                                                                                                    To
                                                                                                                    Date
                                                                                                                </label>

                                                                                                                <div
                                                                                                                    class="col-sm-12">
                                                                                                                    <input
                                                                                                                        type="text"
                                                                                                                        name="to"
                                                                                                                        class="form-control"
                                                                                                                        id="datepicker-2"
                                                                                                                        required="">
                                                                                                                </div>
                                                                                                            </div>

                                                                                                            <button
                                                                                                                type="submit"
                                                                                                                class="btn btn-success">
                                                                                                                Export
                                                                                                            </button>
                                                                                                        </form>
                                                                                                    </div>
                                                                                                    <div align="Left">
                                                                                                        <a
                                                                                                            href="ServiceForm.jsp"><button
                                                                                                                type="button"
                                                                                                                class="btn btn-primary">Add
                                                                                                                New
                                                                                                                Record</button></a>
                                                                                                    </div>

                                                                                                </div>
                                                                                            </div>
                                                                                            <!-- /.col-lg-12 -->
                                                                                        </div>
                                                                                        <br>
                                                                                        <!-- /.row -->
                                                                                        <div class="row">
                                                                                            <div class="col-lg-12">
                                                                                                <div
                                                                                                    class="panel panel-default">
                                                                                                    <div
                                                                                                        class="panel-body">
                                                                                                        <form
                                                                                                            role="form">
                                                                                                            <table
                                                                                                                width="100%"
                                                                                                                class="table table-striped table-bordered table-hover"
                                                                                                                id="dataTables-example">
                                                                                                                <thead>
                                                                                                                    <tr>
                                                                                                                        <th>Id
                                                                                                                        </th>
                                                                                                                        <th>Status
                                                                                                                        </th>
                                                                                                                        <th>Entry
                                                                                                                            Date
                                                                                                                        </th>
                                                                                                                        <th>Sc
                                                                                                                            ReNo
                                                                                                                        </th>
                                                                                                                        <th>Sc
                                                                                                                            Eng
                                                                                                                        </th>
                                                                                                                        <th>Frn
                                                                                                                            No
                                                                                                                        </th>
                                                                                                                        <th>Reg
                                                                                                                        </th>
                                                                                                                        <th>Eng
                                                                                                                        </th>
                                                                                                                        <th>Cust
                                                                                                                            Name
                                                                                                                        </th>
                                                                                                                        <th>Model
                                                                                                                        </th>
                                                                                                                        <th>Unit
                                                                                                                            Sts
                                                                                                                        </th>
                                                                                                                        <th>Def
                                                                                                                            Mod
                                                                                                                            /
                                                                                                                            brd
                                                                                                                            name
                                                                                                                        </th>
                                                                                                                        <th>Def
                                                                                                                            Gir
                                                                                                                            No
                                                                                                                        </th>
                                                                                                                        <th>Type
                                                                                                                            of
                                                                                                                            work
                                                                                                                        </th>
                                                                                                                        <th>PD
                                                                                                                            (PFRN)
                                                                                                                        </th>
                                                                                                                        <th>PD
                                                                                                                            (OBP)
                                                                                                                        </th>
                                                                                                                        <th>PD
                                                                                                                            (URP)
                                                                                                                        </th>
                                                                                                                        <th>PD
                                                                                                                            (SCC)
                                                                                                                        </th>
                                                                                                                        <th>Edit
                                                                                                                            /Upd
                                                                                                                            /Del
                                                                                                                        </th>
                                                                                                                        <th>Job
                                                                                                                            Sht
                                                                                                                        </th>
                                                                                                                    </tr>
                                                                                                                </thead>
                                                                                                                <tbody>
                                                                                                                    <td>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                    </td>
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
                                                <script
                                                    src="vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
                                                <script
                                                    src="vendor/datatables-responsive/dataTables.responsive.js"></script>
                                                <!--         <script src = "dist/js/jquery-1.12.4.js"></script>
 -->

                                                <!--    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script> -->

                                                <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>  -->
                                                <!-- Custom Theme JavaScript -->
                                                <script src="dist/js/sb-admin-2.js"></script>
                                                <!--    <script src="dist/js/sb-admin-2.js"></script> -->

                                                <script src="dist/js/jquery-ui.js"></script>

                                                <!-- Page-Level Demo Scripts - Tables - Use for reference -->
                                                <!--    <script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
            responsive: true
        });
    });
    </script>-->
                                                <script>

                                                    $(document).ready(function () {

                                                        var table = $('#dataTables-example').DataTable({
                                                            "processing": true,
                                                            "serverSide": true,
                                                            "ajax": "Service_Controller",
                                                            "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                                                                //                $('td:eq(1)', nRow).html(aData[1]);  
                                                                $('td:eq(17)', nRow).html('<a href="ServiceController?action=edit&id=' + aData[0] + '">E </a>|<a href="ServiceForm2.jsp?action=update&id=' + aData[0] + '"> U </a>|<a href="ServiceController?action=delete&id=' + aData[0] + '"> D</a>');
                                                                $('td:eq(18)', nRow).html('<a href="JobSheetController?action=edit&id=' + aData[0] + '">JobSheet</a>');
                                                                return nRow;
                                                            },
                                                        });
                                                        table.columns(0).visible(false);

                                                        $("#dynamic-table_filter").css("display", "none");

                                                        $('.search-input-text').on('keyup click', function () {
                                                            var i = $(this).attr('data-column');
                                                            var v = $(this).val();
                                                            table.columns(i).search(v).draw();
                                                        }
                                                        );
                                                    });   
                                                </script>
                                                <script>
                                                    $(function () {
                                                        $("#datepicker-1").datepicker({
                                                            changeMonth: true,
                                                            changeYear: true,
                                                            dateFormat: 'yy-mm-dd'
                                                        });
                                                        $("#datepicker-15").datepicker("show");
                                                    });

                                                    $(function () {
                                                        $("#datepicker-2").datepicker({
                                                            changeMonth: true,
                                                            changeYear: true,
                                                            dateFormat: 'yy-mm-dd'
                                                        });
                                                        $("#datepicker-15").datepicker("show");
                                                    });
                                                </script>

                                            </body>

                                            </html>