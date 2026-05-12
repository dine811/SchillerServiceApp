<%-- 
    Document   : PendingActAdminForm
    Created on : Oct 4, 2017, 5:50:13 PM
    Author     : ShineLoGics
--%>

<%@page import="com.schillerindiaservices.bean.Pendingactmaster"%>
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

    <title>Pending Activity</title>

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
else if(role.equalsIgnoreCase("ViceChancellor"))
{
%>
<%@include  file="VPDashboard.jsp" %>
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
                    <h3 class="page-header">Pending Activity Form</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                           Pending Activity 
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <form role="form" action="PendingActivityController" method="post">
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
                                            Pendingactmaster cr=(Pendingactmaster) request.getAttribute("callrecords");
                                            id=cr.getId();
                                            }
                                    %>

                                    <input type="hidden" name="id" class="form-control" value="<%=id%>">
                                        <div class="form-group">
                                                <label> Division</label>
                                                <select class="form-control" name="division">
                                                    <option>Select Division</option>
                                                    <%
                                                    List<String> list=ProductDao.getAllProducts();
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
                                                <label> SC Engineer</label>
                                                <input type="hidden" name="sc_engg" value="<%=lid%>">
                                                <input type="text"  value="<%=sc_engg%>" minlength="1" maxlength="40" value="" class="form-control" readonly="" required="">
                                        </div>
                                        <div class="form-group">
                                                <label>Initiated Date</label>
                                                <input type="text" name="initiated_Date" id="datepicker-1"  value="" class="form-control" required="">
                                        </div>
                                        
                                        <div class="form-group">
                                                <label>Activity </label>
                                                <input type="text" name="model" class="form-control" min="1" max="500" required="" >
                                                
                                        </div>
                                       
                                        

                                    </div>
                                        <div class="col-lg-4">
                                             <div class="form-group">
                                                <label>Description</label>
                                                <input type="text" name="pending_activity" min="1" max="500" class="form-control" required="">
                                            </div>
                                            <div class="form-group">
                                                <label>Responsible</label>
                                                <input type="text" name="responsible"  min="1" max="250"  class="form-control" required="">
                                            </div>
                                       
                                            
                                             <div class="form-group">
                                                <label>Pending From</label>
                                                <input type="text" name="pending_form"  min="1" max="250" class="form-control">
                                            </div>
                                             
                                            <div class="form-group">
                                                <label>Target Date</label>
                                                <input type="text" name="tar_closed_date" id="datepicker-2" value="" class="form-control" >
                                        </div>
                                              
                                            </div>
                                        <div class="col-lg-4">
                                             
                                                 <div class="form-group">
                                                <label>Remarks</label>
                                                <input type="text" name="remarks" min="1" max="500"  class="form-control" >
                                        </div>
                                                
                                            <div class="form-group">
                                                <label>SC Incharge Remarks</label>
                                                <input type="text" name="sc_inchrg_remarks" min="1" max="500"  class="form-control"  readonly="">
                                        </div>
                                                
                                             <div class="form-group">
                                                <label>Status</label>
                                                <select class="form-control"  name="status" required="">
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
    </script>
</body>

</html>

