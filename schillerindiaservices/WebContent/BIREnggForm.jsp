<%-- 
    Document   : nonSaleEnggForm
    Created on : Oct 9, 2017, 11:30:23 AM
    Author     : ShineLoGics
--%>

<%@page import="com.schillerindiaservices.bean.Birmaster"%>
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
//            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//            response.setHeader("Pragma","no-cache");
//            response.setDateHeader("Expires", 0);
            
            
            session=request.getSession();
            if(session.getAttribute("logname")==null)
            {   
                response.sendRedirect("index.html");
            }
            Connection con=DbConnection.getConnection();
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
                    <h3 class="page-header">Batch Inspection Report </h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Batch Inspection Report 
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <form role="form" action="BIRController" method="post">
                                    <div class="col-lg-4">
                                    <%
                                        int id=0;
                                            if((request.getAttribute("bir"))!=null)
                                            {
                                            Birmaster cr=(Birmaster) request.getAttribute("bir");
                                            id=cr.getId();
                                            }
                                    %>

                                    <input type="hidden" name="id" class="form-control" value="<%=id%>">
                                        <div class="form-group">
                                                <label> Division</label>
                                                <select name="division" class="form-control" id="division" onchange="fetch_model();fetch_supplier();" required="">
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
                                                <label>Unit Inward Date</label>
                                                <input type="text" name="unit_inward_date" id="datepicker-6" value="" class="form-control" required="">
                                            </div>
                                        <div class="form-group">
                                                <label>FQC Inward Date</label>
                                                <input type="text" name="fqc_inward_date" id="datepicker-1" value="" class="form-control" required="">
                                            </div>
                                                <div class="form-group">
                                                <label>BIR Reference No.</label>
                                                <input type="text" name="bir_ref_no"  value="" min="1" max="100" class="form-control" required="">
                                        </div>
                                                <div class="form-group">
                                                <label>Supplier</label>
                                                <select name="supllier" class="form-control" onchange="fetch_model();" id="mySupplier">
                                                    <option value="">Select Model</option>
                                                </select>
                                        </div>
                                        <div class="form-group">
                                                <label>Model</label>
                                                <select name="model" class="form-control" id="myModel">
                                                    <option value="">Select Model</option>
                                                </select>
                                        </div>
                                        
                                        <div class="form-group">
                                                <label>Configuration</label>
                                                <input type="text" name="config" id="" value="" min="1" max="100" class="form-control" required="">
                                            </div>
                                        <div class="form-group">
                                                <label>Received Qty</label>
                                                <input type="text" name="receivd_qty" id="" value="" min="1" max="100" class="form-control" required="">
                                            </div>
                                                 <div class="form-group">
                                                <label>Unit Serial No.</label>
                                               <input type="text" name="unit_serial_no" id="" value="" min="1" max="500" class="form-control" required="">
                                        </div>
                                                <div class="form-group">
                                                <label>Invoice No.</label>
                                                <input type="text" name="invoice_no" id="" value="" min="1" max="100" class="form-control" required="">
                                            </div>
                                                
                                              <div class="form-group">
                                                <label>Invoice Date</label>
                                                 <input type="text" name="invoice_date" id="datepicker-2" value="" min="1" max="100" class="form-control" required="">
                                            </div>
                                                  
                                    </div>
                                        <div class="col-lg-4">
                                            <div class="form-group">
                                                <label>Previous Software Version</label>
                                                <input type="text" name="sftwr_chngs" class="form-control" min="1" max="100" required="">
                                                
                                             </div> 
                                            <div class="form-group">
                                                <label>Present Software Version</label>
                                                 <input type="text" name="sftwr_ver"  value="" min="1" max="100" class="form-control" required="">
                                            </div>
                                            <div class="form-group">
                                                <label>Accessory Changes </label>
                                                 <select name="accesory_chngs" class="form-control" required="">
                                                    <option value="">Select </option>
                                                    <option value="Yes">Yes</option>
                                                    <option value="No">No</option>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label>Accessory Details</label>
                                                <input type="text" name="accesory_details"  value="" min="1" max="500" class="form-control" required="">
                                        </div>
                                            
                                            
                                            <div class="form-group">
                                                <label>User Manual Update</label>
                                               <input type="text" name="user_manul_updt"  value="" min="1" max="100" class="form-control" required="">
                                        </div>
                                            
                                             <div class="form-group">
                                                <label>FQC Remarks </label>
                                                 <input type="text" name="fqc_remark"  value="" min="1" max="1000" class="form-control" required="">
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
                                            <div class="form-group">
                                                <label>Accessory Change Remarks</label>
                                                <input type="text" name="accory_change_remark"  value="" min="1" max="1000" class="form-control" readonly="">
                                        </div>
                                            
                                               <div class="form-group">
                                                <label>Hardware Changes</label>
                                                <input type="text" name="hrdwr_chang" class="form-control" min="1" max="100" readonly="">
                                                    
                                            </div>
                                            <div class="form-group">
                                                <label>Hardware Change Remarks</label>
                                                <input type="text" name="hrdwr_chang_remark" class="form-control" min="1" max="1000" readonly="">
                                                    
                                            </div>
                                           <div class="form-group">
                                                <label>Service Manual Update</label>
                                                <input type="text" name="service_manul_updt"  value="" min="1" max="100" class="form-control" readonly="">
                                                </div>
                                            </div>
                                        <div class="col-lg-4">
                                           
                                            
                                            <div class="form-group">
                                                <label>Technical Remarks</label>
                                                 <input type="text" name="tech_remarks"  value="" min="1" max="1000" class="form-control" readonly="">
                                        </div> 
                                            <div class="form-group">
                                                <label>TS Team Verification Date</label>
                                                 <input type="text" name="ts_team_remark"  value="" min="1" max="100" class="form-control" readonly="">
                                        </div> 
                                            <div class="form-group">
                                                <label>PS Engineer</label>
                                                <input type="text" name="ps_engg" class="form-control" readonly="">
                                                
                                        </div>
                                            <div class="form-group">
                                                <label>Software Change Remarks</label>
                                                <input type="text" name="sftwr_chang_remark" class="form-control" min="1" max="1000" readonly="">
                                                    
                                            </div>
                                            <div class="form-group">
                                                <label> CNR/Technews Circulation </label>
                                                <input type="text" name="cnr_tech_ref_no"  value="" min="1" max="100" class="form-control" readonly="">
                                        </div>
                                             <div class="form-group">
                                                <label> CNR/Technews Reference No. </label>
                                                <input type="text" name="cnr_ref_no"  value="" min="1" max="100" class="form-control" readonly="">
                                        </div>
                                              <div class="form-group">
                                                <label> CNR/Technews Release Date</label>
                                                <input type="text" name="cnr_relese_date"  value="" min="1" max="100" class="form-control" readonly="">
                                        </div>  
                                                
                                                 
                                            <div class="form-group">
                                                <label>PS Team Verification Date</label>
                                               <input type="text" name="ps_team_verification_date"  value="" min="1" max="100" class="form-control" readonly="">
                                        </div>
                                            <div class="form-group">
                                                <label>Approved Date</label>
                                               <input type="text" name="approved_date"  value="" min="1" max="100" class="form-control" readonly="">
                                        </div>
                                            <div class="form-group">
                                                <label>Status</label>
                                                <select class="form-control"  name="status" required="" >
                                                      <option value="Pending">Pending</option>
                                                    <option value="Completed">Completed</option>
                                                  
                                                     
                                                </select></div>
                                            <div>&nbsp;</div>
                                          
                                                     
                                        </div>
                                                <div align="center">
                                                <center><button type="submit" class="btn btn-success">SAVE</button></center>
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
        var name=document.getElementById("mybranch").value;  
     
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
      // alert(name+" kjadsnksjdKSDJKADJKSJDkjdkDSJK");
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
