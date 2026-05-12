<!DOCTYPE html> 
<%@page import="com.schillerindiaservices.bean.Suppliermaster"%>
<%@page import="com.schillerindiaservices.bean.Model"%>
<%@page import="com.schillerindiaservices.bean.Productmaster"%>
<%@page import="com.schillerindiaservices.Dao.SupplierDao"%>
<%@page import="com.schillerindiaservices.Dao.ProductDao"%>
<%@page import="com.schillerindiaservices.Dao.ModelDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

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
                    <h3 class="page-header">Add Supplier</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Enter the details of Supplier
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <form role="form" name="supForm" action="SupplierController" method="post">
                                <div class="col-lg-6">
                                    <%
                                int ID = 0;
                                String name  ="";
                                String model="";
                                String division="";
                               
                                 if(request.getAttribute("editablerecord")!=null)
                                {
                                Suppliermaster s=(Suppliermaster)request.getAttribute("editablerecord");
                                ID = s.getSupplierId();
                                name=s.getSupplierName();
                                model=s.getModel();
                                division=s.getDivision();
                                }
                                %>
                                    <input type="hidden" name="id" class="form-control" value="<%=ID%>" placeholder="Enter Id">
                                    <div class="form-group">
                                            <label> Supplier  Name</label>
                                            <input type="text" name="sup_name" min="1" max="40" class="form-control" value="<%=name%>" placeholder="Enter name" required="">
                                    </div>
                                     
                                    <div class="form-group">
                                            <label> Division</label>
                                            <select name="product" class="form-control" id="myDivision" onchange="fetch_model()" value="<%=division%>" placeholder="select the product" required="">
                                              <%
                                                 int did=0;
                                                 String dname="";
                                                 List<Productmaster> dlist=ProductDao.getAllRecords();
                                                if(dlist!=null)
                                               {
                                                Iterator ditr=dlist.iterator();
                                                while(ditr.hasNext())
                                                {
                                                    Productmaster d=(Productmaster)ditr.next();
                                                   did=d.getProdId();
                                                   dname=d.getProdName();
                                                   if(division=="")
                                                   {
                                                   %>  <option value="<%=did%>"><%=dname%></option><%
                                                   }
                                                     else
                                                   {  
                                                    %>  <option value="<%=did%>" <% if(did==Integer.parseInt(division)){out.print("selected='selected'");}%> ><%=dname%></option><%
                                                   }
                                                }  
                                             }
                                                %>
                                            </select>
                                    </div>
                                    <div class="form-group">
                                            <label> Model</label>
                                            <select name="model" id="myModel" class="form-control"  value="<%=model%>" placeholder="select the model" required="">
                                         
                                                <%
                                                       int bid=0;
                                                      String bname="";
                                                    List<Model> slist=ModelDao.getAllRecords();
                                            if(slist!=null)
                                            {
                                                Iterator sitr=slist.iterator();
                                                while(sitr.hasNext())
                                                {
                                                    Model d=(Model)sitr.next();
                                                   bid=d.getModelId();
                                                   bname=d.getModelName();
                                                   if(model=="")
                                                   {
                                                   %>  <option value="<%=bid%>"><%=bname%></option><%
                                                   }
                                                     else
                                                   {  
                                                    %>  <option value="<%=bid%>" <% if(bid==Integer.parseInt(model)){out.print("selected='selected'");}%> ><%=bname%></option><%
                                                   }
                                                }  
                                             }
                                        %>

                                              %>
                                            </select>
                                    </div>
                                    <div align="center">
                                        <center><button type="submit" class="btn btn-success">SAVE</button></center>
                                    </div>
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
                                            <script>
    function fetch_model()
    {
        
        var request=new XMLHttpRequest();
        var mod=document.supForm.product.value;
        var url="getSModel.jsp?val="+mod;
     
        try
        {  
            request.onreadystatechange=function()
            {  
                if(request.readyState==4)
                {  
                    var val=request.responseText;  
                  
                    document.getElementById('myModel').innerHTML=val;
                    
                    
                }  
            }
            request.open("GET",url,true);  
            request.send();  
        }catch(e){alert("Unable to connect to server");}  
        
    }
                                            </script>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>

</body>

</html>
