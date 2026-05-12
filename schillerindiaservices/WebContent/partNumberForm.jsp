<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@include file="admindashboard.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    
   
 <title>Spares Entry</title>
     <!-- Bootstrap Core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="dist/css/sb-admin-2.css" rel="stylesheet">
    
    <link href="dist/css/datetextremover.css" rel="stylesheet">
<!--for date picker-->
    <link href = "dist/css/jquery-ui.css" rel = "stylesheet">
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
      session=request.getSession();
  if(session.getAttribute("logname")==null){
	  response.sendRedirect("index.html");
  }
  
  
  
  %>
  
    <div id="page-wrapper">
  
          <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Spares Entry</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
            <div class="row">
                   <div class="panel panel-default">
                        <div class="panel-heading">
                           Spares Entry 
                        </div>
                        <div class="panel-body">
                                 <form role="form" action="PartEntry" method="post">   
                                 <div class="col-lg-4">
                                      <div class="form-group">
                                                <label> PartNumber</label>
                                                <input type="text" name="part_no" id="division" value="" minlength="1" maxlength="40"  value="" class="form-control"  required="">
                                        </div>
                                        <div class="form-group">
                                                <label>Def Mod/Brd Name </label>
                                                <input type="hidden" name="sc_engg" value="">
                                                <input type="text" name="brd_name" value="" minlength="1" maxlength="40" value="" class="form-control"  required="">
                                        </div>
                                        <div class="form-group">
                                                <label>Compatible Models</label>
                                               <input type="text" name="compatible_models" value="" minlength="1" maxlength="40" value="" class="form-control"  required="">
                                        </div>
                                        
                                        <div class="form-group">
                                                <label>Cost </label>
                                                <input type="text" name="cost" class="form-control" min="1" max="500" required="" >
                                                
                                        </div>
                                        <div align="center">
                                          <center><button type="submit" class="btn btn-success">SAVE</button></center>
                                         </div>
                                 </div>
                                 
                        
                        </div>
                       
            
            
            
            
            
            
            </div>
  
  
  
  
  
  
  
  
  
    <script src="vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>
    <script src = "dist/js/jquery-1.12.4.js"></script>
      <script src = "dist/js/jquery-ui.js"></script>
  
   <script>
         $(function() {
            $( "#datepicker-1" ).datepicker({ 
                 changeMonth: true,
		 changeYear: true,
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
         $(function() {
            $( "#datepicker-3" ).datepicker({ 
                 changeMonth: true,
		 changeYear: true,
                dateFormat: 'dd-mm-yy' });
            $( "#datepicker-15" ).datepicker("show");
         });
    </script>
  
  
  
  
  
  
  
  
  
  
  
  
  </div>

</body>
</html>