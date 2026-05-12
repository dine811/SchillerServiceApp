<%-- 
    Document   : BranchEdit
    Created on : Sep 15, 2017, 3:55:42 PM
    Author     : SHINELOGICS
--%>
<!DOCTYPE html>
<%@page import="java.lang.*"%>
<%@page import="java.util.*"%>
<%@page import="com.schillerindiaservices.Dao.RegionDao"%>
<%@page import="com.schillerindiaservices.bean.Region"%>
<%@page import="com.schillerindiaservices.Dao.BranchDao"%>
<%@page import="com.schillerindiaservices.bean.Branch"%>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SchillerIndia Services</title>

    <!-- Bootstrap Core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="vendor/morrisjs/morris.css" rel="stylesheet">

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
                    <h3 class="page-header">Branch Edit</h3>
                </div>
                <!-- /.col-lg-12 -->
               <form role="form" action="BranchController" method="POST">
                  <div class="row">
                                        <div class="col-lg-12">
                                                <div class="form-group">
                                                    <div class="panel panel-info">
                                                        <div class="panel-heading">
                                                            Add/Update the branches for this company.
                                                        </div>
                                                        <div class="panel-body">
                                                            <TABLE id="dataTable" width="100%" class="table table-striped table-bordered table-hover" border="0">
                                                                    <tr>
                                                                        <td><b>Select</b></td>
                                                                        <td><b>Branch Name</b></td>
                                                                        <td><b>Branch Location</b></td>
                                                                        <td><b>Branch Address</b></td>
                                                                        <td><b>Branch Contact No</b></td>
                                                                    </tr>
                                                                    <%
                                                                           int id=0;
                                                                           String name  ="";
                                                                           String address="";
                                                                           String Location ="";
                                                                           String mobile="";

                                                                           if(request.getAttribute("editablerecord")!=null)
                                                                           {
                                                                               Branch d1=(Branch)request.getAttribute("editablerecord");
                                                                               id=d1.getBranchid();
                                                                               name    =d1.getBranchname();
                                                                               address =d1.getBranchaddress();
                                                                               Location   =d1.getBranchlocation();
                                                                               mobile  =d1.getBranchphone();
                                                                           }
                                                                       %>
                                                                  
                                                                                            
                                                                                          
                                                                                        <TD> <INPUT type="checkbox" name="checkbox"/>
                                                                                         <INPUT type="hidden" class="form-control" name="branchid" value="<%=id%>" /></TD>
                                                                                        <TD><INPUT type="text" class="form-control" name="branchname" min="1" max="40" value="<%=name%>" required/> </TD>
                                                                                        <TD> <INPUT type="text" class="form-control" name="branchlocation" min="1" max="40" value="<%=Location%>"  required/> </TD>
                                                                                        <TD> <INPUT type="text" class="form-control" name="branchaddress" min="1" maxl="40" value="<%=address%>"  required/> </TD>
                                                                                        <TD> <INPUT type="text" class="form-control" name="branchphone" min="1" maxl="40"  value="<%=mobile%>" required/> </TD>
                                                                                
                                                                                </tr>
                                                                    
                                                            </TABLE>
                                                                   
                                                                                <button type="button" class="btn btn-outline btn-default" onclick="addRow('dataTable')" data-toggle="tooltip" data-placement="top" title="Add another model"><b>+</b></button>
                                                                                <button type="button" class="btn btn-outline btn-default" onclick="deleteRow('dataTable')" data-toggle="tooltip" data-placement="top" title="Delete selected models"><b>Delete</b></button>
                                                                
                                                            </div>
                                                    </div>
                                                </div>
                                                        <div align="center">
                                                            <button type="submit" class="btn btn-success" >SAVE</button>
                                                        </div>
                                        </div>
                                    </div>  
                </form>
            </div>
          
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

    <!-- Morris Charts JavaScript -->
    <script src="vendor/raphael/raphael.min.js"></script>
    <script src="vendor/morrisjs/morris.min.js"></script>
    <script src="data/morris-data.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>

    <script>
        function addRow(tableID) {

            var table = document.getElementById(tableID);

            var rowCount = table.rows.length;
            var row = table.insertRow(rowCount);

            var cell1 = row.insertCell(0);
            var element1 = document.createElement("input");
            element1.type = "checkbox";
            cell1.appendChild(element1);

            var cell2 = row.insertCell(1);
            var element2 = document.createElement("input");
            element2.type = "text";
            element2.class="form-control";
            element2.name="branchname";
            element2.required="required";
            cell2.appendChild(element2);
            var element2id = document.createElement("input");
            element2id.type = "hidden";
            element2id.class="form-control";
            element2id.name="branchid";
            element2id.value="0";
            cell2.appendChild(element2id);

            var cell3 = row.insertCell(2);
            var element3 = document.createElement("input");
            element3.type = "text";
            element3.class="form-control";
            element3.name="branchlocation";
            element3.required="required";
            cell3.appendChild(element3);
            
            var cell4 = row.insertCell(3);
            var element4 = document.createElement("input");
            element4.type = "text";
            element4.class="form-control";
            element4.name="branchaddress";
            element4.required="required";
            cell4.appendChild(element4);
            
            var cell5 = row.insertCell(4);
            var element5 = document.createElement("input");
            element5.type = "text";
            element5.class="form-control";
            element5.name="branchphone";
            element5.required="required";
            cell5.appendChild(element5);
            
            var cell6 = row.insertCell(5);
            cell6.appendChild(element6);

        }

        function deleteRow(tableID) {
            try {
            var table = document.getElementById(tableID);
            var rowCount = table.rows.length;

            for(var i=0; i<rowCount; i++) 
            {
                var row = table.rows[i];
                var chkbox = row.cells[0].childNodes[0];
                if(null != chkbox && true == chkbox.checked) 
                {
                    table.deleteRow(i);
                    rowCount--;
                    i--;
                }
            }
            }catch(e) {
                alert(e);
            }
        }
    </script>
</body>

</html>
