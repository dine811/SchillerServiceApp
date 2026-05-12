<!DOCTYPE html>
<%@page import="com.schillerindiaservices.bean.Callregister"%>
<%@page import="com.schillerindiaservices.bean.service_master"%>
<%@page import="com.schillerindiaservices.Dao.ProductDao"%>
<%@page import="com.schillerindiaservices.Dao.EmployeeDao"%>
<%@ page errorPage="error.jsp" %>  
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

    <title>Call Register</title>

    <!-- Bootstrap Core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="dist/css/sb-admin-2.css" rel="stylesheet">
    
    <link href="dist/css/datetextremover.css" rel="stylesheet">
<!--for date picker-->
    <link href = "dist/css/jquery-ui.css" rel = "stylesheet">
    
    <!--for time picker-->
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
    <!-- Custom Fonts -->
    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link rel="icon" type="image/png" href="img/logo.png"/>
    
    <link rel="stylesheet" href="css/duration-picker.css">
    <link rel="stylesheet" href="css/duration-picker.min.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body onload="fetch_model(); fetch_scEngg();">

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
       else if(role.equalsIgnoreCase("repairteam"))
     {
   	  %>
   	  <%@include file="repairDashboard.jsp" %>
   	  <%
   	  }
else
{
 %>
  <%@include file="PSdashboard.jsp" %>
  <%
      }
  %>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Call Register </h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                           Register the details of Call 
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <form role="form" action="CallRegisterController" method="post">
                                    <div class="col-lg-4">
                                    <%
                                        int id=0;
                                    String division="";
                                    String sc_engg="";
                                    String region="";
                                    int lid=Integer.parseInt(session.getAttribute("loguserid").toString());
                                            division=EmployeeDao.getempdivision(lid);
                                            String eid=String.valueOf(lid);
                                            sc_engg=EmployeeDao.geteName(eid);
                                            if((request.getAttribute("callrecords"))!=null)
                                            {
                                            Callregister cr=(Callregister) request.getAttribute("callrecords");
                                            id=cr.getId();
                                            }
                                    %>

                                    <input type="hidden" name="id" class="form-control" value="<%=id%>">
                                        <div class="form-group">
                                                <label> Division</label>
                                                <input type="text" name="division" id="division" value="<%=division%>" minlength="1" maxlength="40"  value="" class="form-control" readonly="" required="">
                                        </div>
                                        <div class="form-group">
                                                <label> SC Engineer</label>
                                                <input type="hidden" name="sc_engg" value="<%=lid%>">
                                                <input type="text"  value="<%=sc_engg%>" minlength="1" maxlength="40" value="" class="form-control" readonly="" required="">
                                        </div>
                                        <div class="form-group">
                                                <label>Call Date</label>
                                                <input type="text" name="call_date" id="datepicker-1" value="" class="form-control" required="">
                                        </div>
                                        
                                        <div class="form-group">
                                                <label>Call</label>
                                                <select class="form-control"  name="call" required="">
                                                    <option value="">Select Type</option> 
                                                    <option value="Received" >Received</option>
                                                    <option value="Made">Made</option>
                                                </select>
                                            </div>
                                        
                                        

                                    </div>
                                        <div class="col-lg-4">
                                            <div class="form-group">
                                            <label>SELECT REGION</label>
                                            <select class="form-control" name="region"  required>
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
                                                <label>Engineer</label>
                                                <input name="engineer" class="form-control" required="">
                                               
                                        </div>
                                            
                                             <div class="form-group">
                                                <label>Model/ Reason</label>
                                                <input name="model" class="form-control" required="">
                                                
                                        </div>
                                            
                                            <div class="form-group">
                                                <label>Type of Call</label>
                                                <select class="form-control"  name="type_of_call" required="">
                                                    <option value="">Select Type</option> 
                                                    <option value="Application">Application</option>
                                                    <option value="Software">Software</option>
                                                    <option value="Technical">Technical</option>
                                                    <option value="Demo">Demo</option>
                                                    <option value="CRM">CRM</option>
                                                    <option value="Training">Training</option>
                                                    <option value="Others">Others</option>
                                                </select>
                                            </div>
                                            
                                              
                                            </div>
                                        <div class="col-lg-4">
                                            
                                            <div class="form-group">
                                                <label>Type of communication</label>
                                                <select class="form-control"  name="type_of_commn" required="">
                                                    <option value="">Select Type</option> 
                                                    <option value="Telephonic">Telephonic</option>
                                                    <option value="Messaging">Messaging</option>
                                                    <option value="Online">Online</option>
                                                    <option value="FieldVisit">Field Visit</option>
                                                     
                                                </select></div>
                                                
                                                 <div class="form-group">
                                                <label>Remarks</label>
                                                <input type="text" name="remarks" value="" min="1" max="500" class="form-control" placeholder="remarks" >
                                        </div>
                                                
                                                 <div class="form-group">
                                                <label>Duration</label>
                                                <input  type="text" name="duration" id="duration1" onkeyup="dura()"  class="form-control"  placeholder="Ex.05:01" >
                                        
                                                 </div>
                                            
                                             <div class="form-group">
                                                <label>Status</label>
                                                <select class="form-control"  name="status" required="">
                                                    <option value="">Select Type</option> 
                                                    <option value="Completed">Completed</option>
                                                    <option value="Pending">Pending</option>
                                                     
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
      <script src="js/duration-picker.js"></script>
       <script src="js/duration-picker.min.js"></script>
     
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
    function fetch_scEngg()
    {  
        var request=new XMLHttpRequest();
        var name=document.getElementById("division").value;
        var url="getSCENGG.jsp?val="+name;  
        try
        {  
            request.onreadystatechange=function()
            {  
                if(request.readyState==4)
                {  
                    var val=request.responseText;  
                    document.getElementById('myScEngg').innerHTML=val;  
                }  
            }
            request.open("GET",url,true);  
            request.send(); 
            
        }catch(e){alert("Unable to connect to server");}  
        
        setTimeout(function(){fetch_engineer();},1000); 
        
    }  
    function  fetch_model()
    {
        var request=new XMLHttpRequest();
        var name=document.getElementById("division").value;  
        var url="getFModel.jsp?val="+name;  
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
    function dura()
    {
        var nu=document.getElementById("duration1").value;
        if(nu.length==2)
        {
            document.getElementById("duration1").value=nu+" : ";
        }else if(nu.length>2)
        {
             document.getElementById("duration1").value=nu;
        }
    }
      </script>
      <script>
         $(function() {
            $( "#datepicker-1" ).datepicker({ 
                 changeMonth: true,
		 changeYear: true,
                dateFormat: 'dd-mm-yy',
            minDate: -1,
                maxDate: 0});
                
            $( "#datepicker-15" ).datepicker("show");
         });
    </script>
</body>

</html>
