<!DOCTYPE html>
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
                    <h3 class="page-header">Add Dropdown Values</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
                <!-- /.row -->
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                              Enter the values of the various Dropdowns
                            </div>
                            <div class="panel-body">
                                <form role="form"  action="DropdownController" method="post" >
                                    <div class="row">
                                        <div class="col-lg-4">
                                             <input type="hidden" name="id" class="form-control" value="">
                                            <div class="form-group">
                                                    <label> Select Dropdown</label>
                                                    <select id="dropdowncategory" name="dropdowncategory" class="form-control">
                                                     <option value="">choose category</option>
                                                     <option value="1">stock</option>
                                                      <option value="2">cust</option>
                                                      <option value="3">unit status</option>
                                                      <option value="4">def type</option>
                                                      <option value="5">type of acc</option>
                                                      <option value="6">type of work</option>
                                                    </select>
  
                                            </div>
                                            <div class="form-group">
                                                    <label> Values of the Dropdown</label>
                                                    <input type="text" id= "product" class="form-control" name="product"/> 
                                            </div>
                                             
                                           <div align="center">
                                                <button type="submit" class="btn btn-success" >SAVE</button>
                                            </div>  
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                </div>
            </div>
        </div>

    </div>
    <!-- jQuery -->
    <script src="vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js">  
    </script>  
   
        
</body> 

</html>
