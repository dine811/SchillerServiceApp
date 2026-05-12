<!DOCTYPE html>
<%@page errorPage="error.jsp" %>  
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.schillerindiaservices.bean.Productmaster"%>
<%@page import="com.schillerindiaservices.Dao.ProductDao"%>
<%@page import="com.schillerindiaservices.Dao.ModelDao"%>
<%@page import="com.schillerindiaservices.bean.Model"%>
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
        <%@include file="admindashboard.jsp"%>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Add Division</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
                <!-- /.row -->
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                              Enter the details of Division
                            </div>
                            <div class="panel-body">
                                <form role="form" action="ProductsController" method="POST">
                                    <div class="row">
                                        <div class="col-lg-4">
                                            <%
                                                    int ID=0;
                                                    String name ="";
                                                    String desc ="";
                                                   
                                                    if(request.getAttribute("editablerecord")!=null)
                                                    {
                                                        Productmaster d1=(Productmaster)request.getAttribute("editablerecord");
                                                        ID=d1.getProdId();
                                                        name =d1.getProdName();
                                                        desc =d1.getProdDescription();
                                                    }
                                            %>
                                                <input type="hidden" name="id" class="form-control" value="<%=ID%>">
                                                <div class="form-group">
                                                    <label> Division Name</label>
                                                    <input type="text" name="pro_name" class="form-control" minlength="2" maxlength="40" value="<%=name%>" required>
                                                </div>
                                                <div class="form-group">
                                                    <label> Division Description</label>
                                                    <input type="text" name="pro_desc"  class="form-control" minlength="2" maxlength="40" value="<%=desc%>" required>
                                                </div>
                                        </div>
                                        <div class="col-lg-8">
                                                <div class="form-group">
                                                    <!--<label>Enter the models for this product</label>-->
                                                    <div class="panel panel-info">
                                                        <div class="panel-heading">
                                                            Enter the Models for this Division.
                                                        </div>
                                                        <div class="panel-body">
                                                            <TABLE id="dataTable" width="100%" class="table table-striped table-bordered table-hover" border="0">
                                                                    <tr>
                                                                        <td><b>Select</b></td>
                                                                        <td><b>Supplier Name</b></td>
                                                                        <td><b>Model Name</b></td>
                                                                        <td><b>Model Description</b></td>
                                                                        <%
                                                                                if(ID!=0)
                                                                                {
                                                                                    %>
                                                                                        <td><b>Delete</b></td>
                                                                                    <%
                                                                                }
                                                                        %>
                                                                    </tr>
                                                                    <%  
                                                                    if(ID!=0)
                                                                    {   int modelid=0;
                                                                        String suppliername="";
                                                                        String modelname="";
                                                                        String modeldesc="";
                                                                        
                                                                            List<Model> list=ModelDao.getModelsofProduct(ID);
                                                                            if(list!=null)
                                                                            {
                                                                                Iterator itr=list.iterator();
                                                                                while(itr.hasNext())
                                                                                {
                                                                                    Model d=(Model)itr.next();
                                                                                    modelid=d.getModelId();
                                                                                    suppliername=d.getSupName();
                                                                                    modelname=d.getModelName();
                                                                                    modeldesc=d.getModelDesc();
                                                                    %>    
                                                                                    <tr>
                                                                                        <TD> <INPUT type="checkbox" name="checkbox"/></TD>
                                                                                        <TD> <INPUT type="hidden" class="form-control" name="modelid"  value="<%=modelid%>" />
                                                                                            <INPUT type="text" class="form-control" name="suppliername" minlength="1" maxlength="40"  value="<%=suppliername%>" required /> </TD>
                                                                                        <TD> <INPUT type="text" class="form-control" name="modelname" minlength="1" maxlength="40" value="<%=modelname%>" required/> </TD>
                                                                                        <TD> <INPUT type="text" class="form-control" name="modeldesc" minlength="1" maxlength="40" value="<%=modeldesc%>" required/> </TD>
                                                                                        <%
                                                                                            if(ID!=0)
                                                                                            {
                                                                                                %>
                                                                                                    <td><a href="ModelController?pid=<%=ID%>&mid=<%=modelid%>&action=delete">Delete</a></td>
                                                                                                <%
                                                                                            }
                                                                                        %>
                                                                                    </tr>
                                                                    <%
                                                                                }
                                                                            }
                                                                    }    
                                                                    %>
                                                                    
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
                                    <div class="row">
                                        
                                    </div>
                                                
                                    
                                </form>
                            </div>
                            <!-- /.row (nested) -->
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
            element2.name="suppliername";
            element2.required="required";
            cell2.appendChild(element2);
            var element2id = document.createElement("input");
            element2id.type = "hidden";
            element2id.class="form-control";
            element2id.name="modelid";
            element2id.value="0";
            cell2.appendChild(element2id);
            
            

            var cell3 = row.insertCell(2);
            var element3 = document.createElement("input");
            element3.type = "text";
            element3.class="form-control";
            element3.name="modelname";
            element3.required="required";
            cell3.appendChild(element3);
            
            var cell4 = row.insertCell(3);
            var element4 = document.createElement("input");
            element4.type = "text";
            element4.class="form-control";
            element4.name="modeldesc";
            element4.required="required";
            cell4.appendChild(element4);
            
            var cell5 = row.insertCell(4);
            cell5.appendChild(element5);

        }

        function deleteRow(tableID) {
            try {
            var table = document.getElementById(tableID);
            var rowCount = table.rows.length;

            for(var i=0; i<rowCount; i++) {
                var row = table.rows[i];
                var chkbox = row.cells[0].childNodes[0];
                if(null != chkbox && true == chkbox.checked) {
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
