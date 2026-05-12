<!DOCTYPE html>
<%@ page errorPage="error.jsp" %>  
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.schillerindiaservices.bean.Productmaster"%>
<%@page import="com.schillerindiaservices.Dao.ProductDao"%>
<%@page import="com.schillerindiaservices.Dao.ModelDao"%>
<%@page import="com.schillerindiaservices.Dao.DropdownvaluesDao"%>
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
                                <form role="form" action="SelectValuesController" method="post">
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
                                                    <label> Select Dropdown</label>
                                                    <select name="product" class="form-control" placeholder="select the product">
                                                        <%
                                                            List<String> dpdlist=DropdownvaluesDao.getAlldpdnames();
                                                            Iterator dpditr=dpdlist.iterator();
                                                            while(dpditr.hasNext())
                                                            {
                                                                %><option><%=dpditr.next()%></option><%
                                                            }
                                                        %>
                                                    </select>
                                            </div>
                                            <div class="form-group">
                                                    <label> Values of the Dropdown</label>
                                                    <select id="product" name="product" class="form-control" placeholder="select the product">
                                                        <%
                                                            
                                                        %>
                                                    </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-4">
                                                <div class="form-group">
                                                    <!--<label>Enter the models for this product</label>-->
                                                    <div class="panel panel-info">
                                                        <div class="panel-heading">
                                                            Enter the values for this Dropdown.
                                                        </div>
                                                        <div class="panel-body">
                                                            <TABLE id="dataTable" width="100%" class="table table-striped table-bordered table-hover" border="0">
                                                                    <tr>
                                                                        <td><b>Dropdown Item</b></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <TD> <INPUT type="text" id= "sname" class="form-control" name="selectname"  required /> </TD>
                                                                    </tr>
                                                            </TABLE>
                                                            <button type="button" class="btn btn-outline btn-default" onclick="addoption()" data-toggle="tooltip" data-placement="top" title="Add to dropdown List"><b>Add to dropdown</b></button>
                                                            <button type="button" class="btn btn-outline btn-default" onclick="deleteoption()" data-toggle="tooltip" data-placement="top" title="Delete from dropdown List"><b>Delete from dropdown</b></button>
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
//        function addRow(tableID) {
//
//            var table = document.getElementById(tableID);
//
//            var rowCount = table.rows.length;
//            var row = table.insertRow(rowCount);
//
//            var cell1 = row.insertCell(0);
//            var element1 = document.createElement("input");
//            element1.type = "checkbox";
//            cell1.appendChild(element1);
//
//            var cell2 = row.insertCell(1);
//            var element2 = document.createElement("input");
//            element2.type = "text";
//            element2.class="form-control";
//            element2.name="selectname";
//            element2.required="required";
//            cell2.appendChild(element2);
//        }

//        function deleteRow(tableID) {
//            try {
//            var table = document.getElementById(tableID);
//            var rowCount = table.rows.length;
//
//            for(var i=0; i<rowCount; i++) {
//                var row = table.rows[i];
//                var chkbox = row.cells[0].childNodes[0];
//                if(null != chkbox && true == chkbox.checked) {
//                    table.deleteRow(i);
//                    rowCount--;
//                    i--;
//                }
//            }
//            }catch(e) {
//                alert(e);
//            }
//        }
        
        
        

    </script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js">  
    </script>  
    <script type="text/javascript" language="javascript">  
        
        function addoption()
        {
                alert("hello");
                var option = document.createElement('option');
                var select = document.getElementById("product");   
                
                var val=document.getElementById("sname").value;
                alert(val);
                var x=checkelement(val);
                if(x==1)
                {
                    alert(val+" is available");
                }
                else
                {
                    option.text = option.value = val;
                    options.push(option.outerHTML);
                }
                select.insertAdjacentHTML('beforeEnd', options.join('\n'));
        }
        
        function checkelement(val)
        {
            int flag=0;
            for (i = 0; i < document.getElementById("product").length; ++i)
            {
                if (document.getElementById("product").options[i].value == val)
                {
                    flag=1;
                    break;
                }
            }
            return flag;
        }
        
//        $(document).ready(function () {
//            $('#new-item').click(function() {
//                var options = [];
//                var elements = document.getElementsByName("selectname");
//                var len=elements.length;
//                for(var i=0;i<len;i++)
//                {
//                    var value=document.getElementsByName("selectname")[i].value;
//                    var option = document.createElement('option');
//                    option.text = option.value = i;
//                    options.push(option.outerHTML);
//                }
//                select.insertAdjacentHTML('beforeEnd', options.join('\n'));
//                
////                console.log($('#text-to-add').val());
//                
//            });
//        });
    </script> 

</body>

</html>
