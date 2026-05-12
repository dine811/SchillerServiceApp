<!DOCTYPE html>
<%@ page errorPage="error.jsp" %>  



<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Call Registry</title>

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

<!--for date picker-->
    <link href = "dist/css/jquery-ui.css" rel = "stylesheet">
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
    String role=(String)session.getAttribute("logrole");
    if(role.equalsIgnoreCase("admin"))
    {
%>
        <%@include file="admindashboard.jsp" %>
<%
    }
else if(role.equalsIgnoreCase("engineer"))
{
%>
 <%@include file="engineerdashboard.jsp" %>
 <%
     }
else if(role.equalsIgnoreCase("ProductSupport"))
{
 %>
  <%@include file="PSdashboard.jsp" %>
  <%
      } else if(role.equalsIgnoreCase("repairteam"))
      {
    	  %>
    	  <%@include file="repairDashboard.jsp" %>
    	  <%
    	  }
else 
{
  %>
   <%@include file="FQCdashboard.jsp" %>
  <%
      }
  %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Call List</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="col-lg-10">
                    
                     <form role="form" action="ExportCallReg_EnggController" method="post">
                                        <input  type="hidden" name="id"/>   
                                        
                            <div class="form-group col-md-3" style="padding-right: 50px;">
                                <label class="col-sm-8 " for="form-field-1">From Date </label>
                                
                                    <div class="col-sm-12">
                                        <input type="text" name="from" class="form-control" id="datepicker-1" required="">                                      
                                    </div>
                            </div>
                                        
                           <div class="form-group col-md-3" style="padding-right: 50px;">
                                <label class="col-sm-8 " for="form-field-1">To Date</label>

                                    <div class="col-sm-12">
                                        <input type="text" name="to" class="form-control" id="datepicker-2" required="">                                            
                                    </div>
                            </div>
                                      <div class="form-group col-md-1" style="padding-right: 50px;">     
                                          <!--<input  type="submit" class="btn btn-info" value="Salary Generate"/>-->
                                          <input  type="submit" class="btn btn-info" value="Export" style="margin-top: 20px;"/>
                                        </div> </form>
                                        <div class="form-group col-md-3" style="margin-top: 20px;"> 
                                <a href="CallRegisterForm.jsp">
                             <button type="button" class="btn btn-primary">Add New Entry</button></a> 
                    </div>
                            
                                
                </div>
                      <p>*From and to date based on call date</p>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Call Details
                        </div>
                        <input type="hidden" id="qid" name="qid" value="">
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            
                            <form role="form" style="margin-top: 0px;" >
                            <div align="Left">
                          
                            </div>
                                 <br>
                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>Id</th>
                                            <th>Division</th>
                                            <th>Entry Date</th>
                                            <th>Call Date</th>
                                            <th>Sc Engg</th>
                                            <th>Engineer</th>
                                            <th>Model</th>
                                            <th>Type of Call</th>
                                            <th>Remarks</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                      
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
                                                        
                                                   
                                </tbody>
                            </table>
                            <!-- /.table-responsive -->
                            </form>
                        </div></div>
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
    <!--<script src = "dist/js/jquery-1.12.4.js"></script>-->
      <script src = "dist/js/jquery-ui.js"></script>
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
<!--    <script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
            responsive: true
        });
    });
    </script>-->
 <script>
         $(function() {
            $( "#datepicker-1" ).datepicker({ 
                 changeMonth: true,
		 changeYear: true,
                dateFormat: 'yy-mm-dd' });
            $( "#datepicker-15" ).datepicker("show");
         });
         
         $(function() {
            $( "#datepicker-2" ).datepicker({ 
                 changeMonth: true,
		 changeYear: true,
                dateFormat: 'yy-mm-dd' });
            $( "#datepicker-15" ).datepicker("show");
         });
    </script>
    <script>
        
            $(document).ready(function() {
             
    var table= $('#dataTables-example').DataTable( {
        "processing": true, 
        "serverSide": true,
        "ajax":  "Call_regPageController",
        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {  
//                $('td:eq(1)', nRow).html(aData[1]);  
                var qid=document.getElementById("qid").value;
//                 $('td:eq(10)', nRow).html('<a href="CallRegUpdate.jsp">Update</a>'); 
                $('td:eq(9)', nRow).html('<a href="CallRegisterController?action=edit&id='+aData[0]+'">Update</a>');  
                return nRow;  
            },  
} );
table.columns(0).visible(false);

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
