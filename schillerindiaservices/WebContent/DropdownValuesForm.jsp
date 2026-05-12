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
                                <form role="form" name="vinform" action="DropdownValuesController" method="post" onload="" onsubmit="beforesubmit()">
                                    <div class="row">
                                        <div class="col-lg-4">
                                            <%
//                                                   
                                            %>
                                                <input type="hidden" name="id" class="form-control" value="">
                                            <div class="form-group">
                                                    <label> Select Dropdown</label>
                                                    <select id="dropdowncategory" name="dropdowncategory" class="form-control" onchange="fetch_dropdownvalues()" required="">
                                                        <option value="">choose category</option>
                                                        <option value="1">stock/cust</option>
                                                        <option value="2">unit status</option>
                                                        <option value="3">def type</option>
                                                        <option value="4">type of acc</option>
                                                        <option value="5">type of work</option>
                                                        <option value="6">rep type</option>
                                                    </select>
                                            </div>
                                            <div class="form-group">
                                                    <label> Values of the Dropdown</label>
                                                    <select id="product" name="product" class="form-control" size="4" onchange="selectproduct()">
                                                        <!--<input type="text" id= "product" class="form-control" name="product"/>--> 
                                                    </select>
                                            </div>
                                           <div align="center">
                                                <button type="submit" class="btn btn-success" >SAVE</button>
                                            </div>      
                                        </div>
                                        <div class="col-lg-4">
                                                <div class="form-group">
                                                    <label>Values for this Dropdown</label>
                                                    <div class="panel panel-info">
                                                        <div class="panel-heading">
                                                            Enter the values for this Dropdown.
                                                        </div>
                                                        <div class="panel-body">
                                                            <input id="totalstring" name="totalstring" type="hidden" value="" class="form-control" >
                                                            <TABLE id="dataTable" width="100%" class="table table-striped table-bordered table-hover" border="0">
                                                                    <tr>
                                                                        <td><b>Dropdown Item</b></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <TD> <INPUT type="text" id= "sname" class="form-control" name="selectname"/> </TD>
                                                                    </tr>
                                                            </TABLE>
                                                            <!--<button type="button" class="btn btn-outline btn-default" onclick="addoption()" data-toggle="tooltip" data-placement="top" title="Add to dropdown List"><b>Add</b></button>-->
                                                            
                                                            <button type="button" class="btn btn-outline btn-default" onclick="removeselecteditem()" data-toggle="tooltip" data-placement="top" title="Delete from dropdown List" ><b>Delete</b></button>
                                                            <span id="btn"></span>
                                                        </div>
                                                        
                                                    </div>
                                                </div>
<!--                                            <div align="center">
                                                <button type="submit" class="btn btn-success" >SAVE</button>
                                            </div>-->
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
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js">  
    </script>  
    <script>
        document.addEventListener("DOMContentLoaded", function(event) 
        {
            var val=document.getElementById("dropdowncategory").value;
            if(val!=null || val!="")
            {
                fetch_dropdownvalues();
            }
        });
        
        function addoption()
        {
                var options=[];
                var option = document.createElement('option');
                var select = document.getElementById("product");   
                
                var val=document.getElementById("sname").value;
                var x=checkelement(val);
                if(x==1)
                {
                    alert(val+" is already available");
                }
                else
                {
                    option.text = option.value = val;
                    options.push(option.outerHTML);
                }
                select.insertAdjacentHTML('beforeEnd', options.join('\n'));
                document.getElementById('sname').value="";
                document.getElementById('sname').focus();
        }
        
        function checkelement(val)
        {
            var flag=0;
            for(var i=0;i<document.getElementById("product").length;i++)
            {
                if(document.getElementById("product").options[i].value==val)
                {
                    flag=1;
                    break;
                }
            }
            return flag;
        }
        
        
        function beforesubmit()
        {
            var flag="";
            for(var i=0;i<document.getElementById("product").length;i++)
            {
                if(flag=="")
                {
                    flag=flag+document.getElementById("product").options[i].value;
                }
                else
                {
                    flag=flag+","+document.getElementById("product").options[i].value;
                }
            }
            document.getElementById("totalstring").value=flag;
//            alert("Successfully Executed");
        }
        
        function selectproduct()
        {
            var productdropdown = document.getElementById('product');
            var a = productdropdown.options[productdropdown.selectedIndex].value;
            var textbox = document.getElementById('sname');
            textbox.value = a;  
        }
        
        function removeselecteditem()
        {
            var x = document.getElementById("product").value;
            var id=document.getElementById("dropdowncategory").value;
            document.getElementById("btn").innerHTML="<a href='DropdownController?val="+x+"&id="+id+"' class='btn btn-outline btn-success' onclick='removeselecteditem()' data-toggle='tooltip' data-placement='top' title='Delete from dropdown List' ><b>Confirm</b></a>";
           
        }
        
        
        var request=new XMLHttpRequest();  
        function fetch_dropdownvalues()
        {  
            var name=document.vinform.dropdowncategory.value;  
            var url="getdropdownvalues.jsp?val="+name;  
            try
            {  
                request.onreadystatechange=function()
                {  
                    if(request.readyState==4)
                    {  
                        var val=request.responseText;  
                        document.getElementById('product').innerHTML=val;  
                    }  
                }
                request.open("GET",url,true);  
                request.send();  
            }catch(e){alert("Unable to connect to server");}  
            
            document.getElementById('sname').value="";
        }  
    </script>
        
</body> 

</html>
