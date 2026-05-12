<%@page import="com.schillerindiaservices.bean.Prfobmaster"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.schillerindiaservices.connection.DbConnection"%>
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

    <title>PRF/OB Register</title>

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
else if(role.equalsIgnoreCase("servicecoordinator"))
{
%>
<%@include  file="ServiceCoorDashBoard.jsp" %>
<%
}
%>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">PRF/OB Register </h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                           PRF/OB Register Form 
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <form role="form" action="PRFOBController" method="post">
                                    <div class="col-lg-4">
                                    <%
                                        int id=0;
                                    String division="";
                                    String sc_engg="";
                                    String region="";
                                    String divid="";
                                    int lid=Integer.parseInt(session.getAttribute("loguserid").toString());
                                            division=EmployeeDao.getempdivision(lid);
                                            divid=ProductDao.getDiviName(division);
                                            String eid=String.valueOf(lid);
                                            sc_engg=EmployeeDao.geteName(eid);
                                            if((request.getAttribute("prfobrecors"))!=null)
                                            {
                                            Prfobmaster cr=(Prfobmaster) request.getAttribute("prfobrecors");
                                            id=cr.getId();
                                            }
                                    %>

                                    <input type="hidden" name="id" class="form-control" value="<%=id%>">
                                        <div class="form-group">
                                                <label> Division</label>
                                                <select class="form-control" name="division" id="division" onchange="fetch_supplier(); " >
                                                    <option>Select Division</option>
                                                    <%
                                                    List<String> list1=ProductDao.getAllProducts();
                                                     Iterator itr1=list1.iterator();
                                                   
                                                    while(itr1.hasNext())
                                                    {
                                                        %><option><%=itr1.next()%></option><%
                                                    }
                                            %>
                                                </select>
                                        </div>
                                        
                                        <div class="form-group">
                                                <label>Type</label>
                                                <select name="type" class="form-control" required="">
                                                    <option value="">Select Type</option>
                                                    <option value="PRF">PRF</option>
                                                    <option value="OB">OB</option>
                                                </select>
                                        </div>
                                        
                                        <div class="form-group">
                                                <label>Raised Date</label>
                                                <input type="text" id="datepicker-1"  name="raised_date" minlength="1" maxlength="40" value="" class="form-control"  required="">
                                            </div>
                                            
                                             <div class="form-group">
                                                <label>Entry Date from SC</label>
                                                <input type="text" id="datepicker-2"  name="recv_date_sc" minlength="1" maxlength="40" value="" class="form-control"  required="">
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
                                    </div>
                                        <div class="col-lg-4">
                                            
                                        <div class="form-group">
                                                <label>Dealer</label>
                                                <select class="form-control" id="mydealer"  name="dealer" required="">
                                                    <option>Select Dealer</option> 
                                                    
                                                </select>
                                            </div>
                                             <div class="form-group">
                                                <label>Engineer</label>
                                                <select name="engineer" id="myengineer" class="form-control" required="">
                                                    <option>Select Engineer</option>
                                                </select>
                                        </div>
                                             <div class="form-group">
                                                <label>Supplier</label>
                                                <select name="supplier" id="mySupplier" onchange="fetch_model(); " class="form-control" required="">
                                                    <option value="">Select Supplier</option>
                                                   
                                                </select>
                                        </div>
                                             <div class="form-group">
                                                <label>Model</label>
                                                <select name="model" id="myModel" class="form-control" required="">
                                                    <option value="">Select Model</option>
                                                     
                                                </select>
                                        </div>
                                            
                                            <div class="form-group">
                                                <label>Warranty Status</label>
                                                <select class="form-control"  name="Warranty" required="">
                                                    <option value="">Select Type</option> 
                                                    <option value="IW">IW</option>
                                                    <option value="OW">OW</option>
                                                    <option value="EW">EW</option>
                                                    <option value="CAMC">CAMC</option>
                                                    <option value="LAMC">LAMC</option>
                                                    <option value="STOCK">STOCK</option>
                                                    <option value="DEMO">DEMO</option>
                                                    <option value="TESTING">TESTING</option>
                                                    
                                                </select>
                                            </div>
                                            
                                               <div class="form-group">
                                                <label>PRF/OB Reference No.</label>
                                                <input type="text" name="prfob_ref_no" value="" class="form-control" placeholder="PRF/OB Reference No." required="">
                                        </div>
                                            </div>
                                        <div class="col-lg-4">
                                           
                                            <div class="form-group">
                                                <label> SC Engineer</label>
                                                <input type="text"  name="sc_engg" minlength="1" maxlength="40" value="" class="form-control" readonly="">
                                        </div>
                                        <div class="form-group">
                                                <label>Spares Received at SVC</label>
                                                <input type="text" id="datepicker-3"  name="receive_date_from_sc" minlength="1" maxlength="40" value="" class="form-control"  readonly="">
                                            </div>
                                        <div class="form-group">
                                                <label>Part type</label>
                                                <input type="text" name="part_type" value="" class="form-control" readonly="">
                                        </div>
                                        <div class="form-group">
                                                <label>Parts Description</label>
                                                <input type="text" name="part_desc" min="1" max="500" value="" class="form-control" readonly="">
                                        </div>
                                                <div class="form-group">
                                                <label>CRM Reference No.</label>
                                                <input type="text" name="crm_ref_no" value="" class="form-control" readonly="">
                                        </div>
                                                 <div class="form-group">
                                                <label>Remarks</label>
                                                <input type="text" name="remarks" value="" min="1" max="550" class="form-control" readonly="">
                                        </div>
                                        
                                        <% 
                                        if(role.equalsIgnoreCase("servicecoordinator"))
{
%>
                                             <div class="form-group">
                                                <label>Status</label>
                                                <select class="form-control"  name="status" required="" readonly="">
                                                   <!--  <option value="">Select Type</option>  -->
                                                    <!-- <option value="Completed">Completed</option> -->
                                                    <option value="Pending">Pending</option>
                                                     
                                                </select></div>
 <%
     }
else 
{
%>
                                                <div class="form-group">
                                                <label>Status</label>
                                                <select class="form-control"  name="status" required="">
                                                   <!--  <option value="">Select Type</option>  -->
                                                    <option value="Completed">Completed</option>
                                                    <option value="Pending">Pending</option>
                                                     
                                                </select></div>
<%
}
%>   
                                                 
                                            
                                            
                                                
                                                
                                                
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
                  dateFormat: 'dd-mm-yy',
                  minDate: 0,
                  maxDate:0
                	//  timeFormat:  "hh:mm:ss"
                  
              });
              $( "#datepicker-15" ).datepicker("show");
           });
    </script>
</body>

</html>
