<!DOCTYPE html>
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
    <link href="vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="dist/css/sb-admin-2.css" rel="stylesheet">
    <link href="dist/css/datetextremover.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
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
                    
                     <form role="form" action="ScarpListEnggController" method="POST">
                                        <input  type="hidden" name="id"/>                            
                             <input  type="hidden" name="logname" value="<%=session.getAttribute("logname")%>"/>                            
                            <div class="form-group col-md-3" style="padding-right: 50px;">
                                <label class="col-sm-8 " for="form-field-1"> Month </label>

                                    <div class="col-sm-12">
                                        <select class="chosen-select form-control"  name="month" id="form-field-select-3" required="true"> 
                                             <option value=""/>Select Month</option>
                                            <option value="01"/>January</option>
                                            <option value="02"/>February</option>
                                            <option value="03"/>March</option>  
                                            <option value="04"/>April</option>  
                                            <option value="05"/>May</option>  
                                            <option value="06"/>June</option>  
                                            <option value="07"/>July</option>  
                                            <option value="08"/>August</option>  
                                            <option value="09"/>September</option>  
                                            <option value="10"/>October</option>  
                                            <option value="11"/>November</option>  
                                            <option value="12"/>December</option>  


                                                                             
                                        </select>
                                                                                      
                                    </div>
                    </div>
                                        
                           <div class="form-group col-md-3" style="padding-right: 50px;">
                                <label class="col-sm-8 " for="form-field-1"> Year </label>

                                    <div class="col-sm-12">
                                        <select class="chosen-select form-control"  name="year" id="form-field-select-3" required="true" >   
                                             <option value=""/>Select Year</option>
                                             <option value="2011"/>2011</option>
                                             <option value="2012"/>2012</option>
                                             <option value="2013"/>2013</option>    
                                             <option value="2014"/>2014</option>
                                             <option value="2015"/>2015</option>
                                             <option value="2016"/>2016</option>
                                             <option value="2017"/>2017</option>    
                                             <option value="2018"/>2018</option>
                                             <option value="2019"/>2019</option>
                                             <option value="2020"/>2020</option>    
                                             <option value="2021"/>2021</option>
                                             <option value="2022"/>2022</option>
                                             <option value="2023"/>2023</option>    
                                             <option value="2024"/>2024</option>
                                             <option value="2025"/>2025</option>
                                             <option value="2026"/>2026</option>    
                                             <option value="2027"/>2027</option>
                                             <option value="2028"/>2028</option>
                                             <option value="2029"/>2029</option>    
                                             <option value="2030"/>2030</option>
                                                                             
                                        </select>
                                                                                      
                                    </div>
                   </div>
                             
                                      <div>     
                                          <input  type="submit" class="btn btn-info" value="submit" style="margin-top: 20px;"/>
                                        </div>
                             </form>
                    
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <br>
            
                                 <br>
                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                            <th>Id</th>
                                               <th>Status</th>
                                            <th>Entry Date</th>
                                            <th>Sc RNo</th>
                                            <th>Sc Eng</th>
                                            <th>Frn No</th>
                                            <th>Region</th>
                                            <th>Engineer</th>
                                            <th>Cust Name</th>
                                            <th>Model</th>
                                            <th>Unit Status</th>
                                            <th>Def Mod / brd name</th>
                                            <th>Def Gir No</th>
                                            <th>Type of work</th>
                                            <th>Pend. Days (PFRN)</th>
                                            <th>Pend. Days (OBP)</th>
                                            <th>Pend. Days (URP)</th>
                                            <th>Pend. Days (SCC)</th>
                                            
                                            <th>Job Sheet</th>
                                            
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
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>
                                                       
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
        
            $(document).ready(function() {
             
    var table= $('#dataTables-example').DataTable( {
        "processing": true,
        "serverSide": true,
        "ajax":  "ScarpListEnggController",
        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {  
//                $('td:eq(1)', nRow).html(aData[1]); 
                $('td:eq(17)', nRow).html('<a href="JobSheetController?action=edit&id='+aData[0]+'">JobSheet</a>'); 
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
          <script>
        $(function() {
            $( "#datepicker-1" ).datepicker({ 
                 //changeMonth: true,
		         // changeYear: true,
                dateFormat: 'dd-mm-yy' });
          $( "#datepicker-15" ).datepicker("show");
         });
         
         $(function() {
            $( "#datepicker-2" ).datepicker({ 
                 changeMonth: true,
		 changeYear: true,
                dateFormat: 'dd-mm-yy' });
            $( "#datepicker-15" ).datepicker("show");
         });
        </script>

</body>

</html>
