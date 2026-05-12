<%-- 
    Document   : nonSaleEnggForm
    Created on : Oct 9, 2017, 11:30:23 AM
    Author     : ShineLoGics
--%>

<%@page import="com.schillerindiaservices.bean.Nonsaleablemaster"%>
<%@page import="com.schillerindiaservices.connection.DbConnection"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<!DOCTYPE html>
<%@page import="com.schillerindiaservices.bean.Callregister"%>
<%@page import="com.schillerindiaservices.bean.service_master"%>
<%@page import="com.schillerindiaservices.Dao.ProductDao"%>
<%@page import="com.schillerindiaservices.Dao.EmployeeDao"%>
<%--<%@ page errorPage="error.jsp" %>--%>  
<%@page import="com.schillerindiaservices.bean.Dealermaster"%>
<%@page import="com.schillerindiaservices.Dao.DealerDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.schillerindiaservices.Dao.RegionDao"%>
<%@page import="com.schillerindiaservices.bean.Region"%>

<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Non Saleable Form</title>

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
            Connection con=DbConnection.getConnection();
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
                    <h3 class="page-header">Non Saleable Form </h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Non Saleable 
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <form role="form" action="NonSaleControler" method="post">
                                    <div class="col-lg-4">
                                    <%
                                        int id=0;
                                    String division="";
                                    String sc_engg="";
                                    String region="";
                                    String divid="";
                                    int lid=Integer.parseInt(session.getAttribute("loguserid").toString());
                                            division=EmployeeDao.getempdivision(lid);
                                            System.out.println("the print ln iss"+division);
                                            String eid=String.valueOf(lid);
                                            divid=ProductDao.getDiviName(division);
                                            System.out.println("the print ln iss 22"+divid);
                                            sc_engg=EmployeeDao.geteName(eid);
                                           System.out.println("sc_engg"+sc_engg);
                                            if((request.getAttribute("nonsale"))!=null)
                                            {
                                            Nonsaleablemaster cr=(Nonsaleablemaster) request.getAttribute("nonsale");
                                            id=cr.getId();
                                            }
                                    %>

                                    <input type="hidden" name="id" class="form-control" value="<%=id%>">
                                        <div class="form-group">
                                                <label> Division</label>
                                                <select name="division" class="form-control" id="division" onchange="fetch_supplier();" required="">
                                                    <option value="">Select Division</option>
                                                    <%
                                                  List list1=ProductDao.getAllProducts();
                                                   Iterator itr1=list1.iterator();
                                                    int d=0;
                                                    while(itr1.hasNext())
                                                    {
                                                        
                                                        %><option><%=itr1.next()%></option><%
                                                    }
                                                %>
                                                </select>
                                        </div>
                                        
                                        <div class="form-group">
                                                <label>Unit Details</label>
                                                <select class="form-control"  name="unit_details" id="unit_details"  onchange="call()" required="">
                                                    <option value="">Select Type</option> 
                                                    <option value="Sales Stock" >Sales Stock</option>
                                                    <option value="DOA">DOA</option>
                                                    <option value="Demo">Demo</option>
                                                </select>
                                            </div>
                                        <div class="form-group">
                                                <label>FQC Inward date </label>
                                                <input type="text" name="fqc_inward_date" id="datepicker-1" value="" class="form-control" required="">
                                        </div>
                                        
                                        <div class="form-group">
                                                <label>Region</label>
                                                <select class="form-control" id="region"  name="region" onchange="fetch_dealer(); fetch_branch();fetch_engineer()" required="">
                                                    <option value="">Select Region</option> 
                                                     <%
                                                  List list=RegionDao.getAllRegions();
                                                   Iterator itr=list.iterator();
                                                    int i=0;
                                                    while(itr.hasNext())
                                                    {
                                                        %><option><%=itr.next()%></option><%
                                                    }
                                                %>
                                                </select>
                                            </div>
                                        <div class="form-group">
                                                <label>Branch</label>
                                                <select class="form-control" id="mybranch" onchange="fetch_engineer()"  name="branch" required="">
                                                    <option>Select Branch</option> 
                                                    
                                                </select>
                                            </div>
                                                 <div class="form-group">
                                                <label>Engineer</label>
                                               <select name="engineer" id="myengineer" class="form-control" required="">
                                                    <option>Select Engineer</option>
                                                </select>
                                        </div>
                                                <div class="form-group">
                                                <label>Dealer</label>
                                                <select class="form-control" id="mydealer"  name="dealer" required="">
                                                    <option>Select Dealer</option> 
                                                    
                                                </select>
                                            </div>
                                                
                                                 <div class="form-group">
                                                <label>Supplier</label>
                                                <select class="form-control" id="mySupplier" onchange="fetch_model();"  name="supplier" required="">
                                                    <option value="">Select Supplier</option> 
                                                   
                                                </select>
                                            </div>
                                                <div class="form-group">
                                                <label>Model</label>
                                        <select class="form-control" id="myModel"  name="model">
                                            <option value="">Select Model</option> 
                                                   
                                                </select>
                                             </div>
                                    </div>
                                        <div class="col-lg-4">
                                             
                                            
                                            <div class="form-group">
                                                <label>Model S/N</label>
                                                 <input type="text" name="model_sn" value="" class="form-control"  required="">
                                            </div>
                                            <div class="form-group">
                                                <label>Unit Configuration</label>
                                                 <input type="text" name="unit_config" value="" minlength="1" maxlength="40" class="form-control"  required="">
                                                </div>
                                            <div class="form-group">
                                                <label>Reported Problem</label>
                                                <input type="text" name="report_problm" minlength="1" maxlength="250" value="" class="form-control" required="">
                                        </div>
                                              <div class="form-group">
                                                <label>Customer name</label>
                                                <input type="text" name="cust_name" id="custname" value="" minlength="1" maxlength="40" class="form-control"  required="">
                                        </div>
                                            
                                            <div class="form-group">
                                                <label>Replaced Unit S/N</label>
                                                <input type="text" name="replace_unit" id="replacesn" minlength="1" maxlength="250" value="" class="form-control" required="" >
                                        </div>
                                             <div class="form-group">
                                                <label>Replacement Ship Date</label>
                                                <input type="text" name="replace_ship_date" id="datepicker-2" minlength="1" maxlength="250" value="" class="form-control" required="" >
                                        </div>
                                            <div class="form-group">
                                                <label>Defective unit received  Date</label>
                                                <input type="text" name="defect_unit_recv_date" id="datepicker-3" minlength="1" maxlength="50" value="" class="form-control" required="" >
                                        </div>
                                        <div class="form-group">
                                                <label>FQC Observation</label>
                                                <input type="text" name="fqc_observation" minlength="1" maxlength="250" value="" class="form-control" required="" >
                                        </div>
                                        <% 
                                        if(role.equalsIgnoreCase("engineer"))
                        {
                            %>

                                       <div class="form-group">
                                                <label> SC Engineer</label>
                                                <input type="text" name="sc_engg"  minlength="1" maxlength="40" value="" class="form-control" >
<!--                                                <select name="sc_engg" id="scEngineer" class="form-control" required="">
                                                    <option>Select Enginner</option>
                                                </select>-->
                                        </div>

 <%
     }else{
    	 %> 
    	 <div class="form-group">
                                                <label> SC Engineer</label>
                                                <input type="text" name="sc_engg"  minlength="1" maxlength="40" value="" class="form-control" readonly="">
<!--                                                <select name="sc_engg" id="scEngineer" class="form-control" required="">
                                                    <option>Select Enginner</option>
                                                </select>-->
                                        </div>
    	 
    	 
    	 <%
     }
         %>                               
                                          
                                            </div>
                                        <div class="col-lg-4">
                                            
                                                
                                                <div class="form-group">
                                                <label>SC Inward Date</label>
                                                <input type="text" name="sc_in_date" readonly="" minlength="1" maxlength="50" value="" class="form-control" placeholder="" >
                                        </div> 
                                                
                                                 
                                            <div class="form-group">
                                                <label>SC Observation</label>
                                                <input type="text" name="sc_observation" minlength="1" maxlength="500" value="" class="form-control" readonly="" >
                                        </div>
                                            <div class="form-group">
                                                <label>Required Parts</label>
                                                <input type="text" name="req_parts" minlength="1" maxlength="250" value="" class="form-control" readonly="" >
                                        </div>
                                            <div class="form-group">
                                                <label>Root Cause</label>
                                                <input type="text" name="root_cause" minlength="1" maxlength="500" value="" class="form-control" readonly="" >
                                        </div>
                                            <div class="form-group">
                                                <label>SC Action Plan</label>
                                                <input type="text" name="action_plan" minlength="1" maxlength="600" value="" class="form-control" readonly="" >
                                        </div>
                                            <div class="form-group">
                                                <label>Tentative Date </label>
                                                <input type="text" name="ship_date_fqc" readonly="" minlength="1" maxlength="50" value="" class="form-control" placeholder="" >
                                        </div>
                                             <div class="form-group">
                                                <label>Ship Date to FQC </label>
                                                <input type="text" name="ship_date_fqc2" readonly="" minlength="1" maxlength="50" value="" class="form-control" placeholder="" >
                                        </div>
                                             <div class="form-group">
                                                <label>FQC Final Remarks </label>
                                                <input type="text" name="fqc_final_remarks" minlength="1" maxlength="550" value="" class="form-control" readonly="" >
                                        </div>
                                             <div class="form-group">
                                                <label>Status</label>
                                                <select class="form-control"  name="status" required="" >
                                                   <option value="Pending">Pending</option>
                                                    <option value="Completed">Completed</option>
                                                   
                                                     
                                                </select></div>
                                            <div>&nbsp;</div>
                                          
                                            <div align="center">
                                                <center><button type="submit" class="btn btn-success">SAVE</button></center>
                                            </div>           
                                        </div>
                                      </form> 
                                    </div>
                                      
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

    <!-- /#wrapper -->

    <!-- jQuery -->
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
                                        function fetch_branch()
                                  {  
                                      var request=new XMLHttpRequest();
                                      var name=document.getElementById("region").value;  
                                      var url="getbranch.jsp?val="+name;  
                                      try
                                      {  
                                          request.onreadystatechange=function()
                                          {  
                                              if(request.readyState==4)
                                              {  
                                                  var val=request.responseText;  
                                                  document.getElementById('mybranch').innerHTML=val;  
                                              }  
                                          }
                                          request.open("GET",url,true);  
                                          request.send(); 

                                      }catch(e){alert("Unable to connect to server");}  

                                      setTimeout(function(){fetch_engineer();},1000); 

                                  }  


                                  function fetch_dealer()
                                  {   var request=new XMLHttpRequest();
                                      var name=document.getElementById("region").value;  
                                      var url="getdealer.jsp?val="+name;  
                                      try
                                      {  
                                          request.onreadystatechange=function()
                                          {  
                                              if(request.readyState==4)
                                              {  
                                                  var val=request.responseText;  

                                                  document.getElementById('mydealer').innerHTML=val;  
                                              }  
                                          }
                                          request.open("GET",url,true);  
                                          request.send();  
                                      }catch(e){alert("Unable to connect to server");}  
                                  } 
                                  function fetch_engineer()
                                  {   var request=new XMLHttpRequest();
                                  var name=document.getElementById("region").value;  

                                      var url="getengineer.jsp?val="+name;  
                                      try
                                      {  
                                          request.onreadystatechange=function()
                                          {  
                                              if(request.readyState==4)
                                              {  
                                                  var val=request.responseText;  

                                                  document.getElementById('myengineer').innerHTML=val;

                                              }  
                                          }
                                          request.open("GET",url,true);  
                                          request.send();  
                                      }catch(e){alert("Unable to connect to server");}  

                                  } 
                                  function fetch_supplier()
                                  {
                                      var request=new XMLHttpRequest();
                                      var name=document.getElementById("division").value; 
                                   //  alert(name);
                                      var url="getSupplierNew.jsp?val="+name;  
                                      try
                                      {  
                                          request.onreadystatechange=function()
                                          {  
                                              if(request.readyState==4)
                                              {  
                                                  var val=request.responseText;  

                                                  document.getElementById('mySupplier').innerHTML=val;

                                              }  
                                          }
                                          request.open("GET",url,true);  
                                          request.send();  
                                      }catch(e){alert("Unable to connect to server");}  
                                  }
                                  function  fetch_model()
                                  {
                                      var request=new XMLHttpRequest();
                                      var name=document.getElementById("mySupplier").value;  
                                       // alert(name);

                                      var url="getmodel.jsp?val="+name;  
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
                                  
                                  function call()
                                  {
                                       
                                    
                                     var val=document.getElementById("unit_details").value; 
                                     
                                     var custname=document.getElementById("custname");
                                     var replacesn=document.getElementById("replacesn");
                                     var datepicker2=document.getElementById("datepicker-2");
                                     var  datepicker3=document.getElementById("datepicker-3");
                                     
                                     if(val.match("Sales Stock")||val=="Demo")
                                     {
                                         custname.disabled=true;
                                         document.getElementById("custname").value="Nil";
                                         
                                         replacesn.disabled=true;
                                         document.getElementById("replacesn").value="Nil";
                                         
                                         datepicker2.disabled=true;
                                         document.getElementById("datepicker-2").value="Nil";
                                         
                                         datepicker3.disabled=true;
                                         document.getElementById("datepicker-3").value="Nil";
                                     }else{
                                         custname.disabled=false;
                                         document.getElementById("custname").value="";
                                         
                                         replacesn.disabled=false;
                                         document.getElementById("replacesn").value="";
                                         
                                         datepicker2.disabled=false;
                                         document.getElementById("datepicker-2").value="";
                                         
                                         datepicker3.disabled=false;
                                         document.getElementById("datepicker-3").value="";
                                     }
                                  }
                                    </script>
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
                                       $(function() {
                                          $( "#datepicker-4" ).datepicker({ 
                                               changeMonth: true,
                                               changeYear: true,
                                              dateFormat: 'dd-mm-yy' });
                                          $( "#datepicker-15" ).datepicker("show");
                                       });
                                       $(function() {
                                          $( "#datepicker-5" ).datepicker({ 
                                               changeMonth: true,
                                               changeYear: true,
                                              dateFormat: 'dd-mm-yy' });
                                          $( "#datepicker-15" ).datepicker("show");
                                       });
                                       $(function() {
                                          $( "#datepicker-6" ).datepicker({ 
                                               changeMonth: true,
                                               changeYear: true,
                                              dateFormat: 'dd-mm-yy' });
                                          $( "#datepicker-15" ).datepicker("show");
                                       });
                                  </script>
                              </body>
                              </html>
