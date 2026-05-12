<%@page import="com.schillerindiaservices.bean.Repair_master"%>
<%@page import="com.schillerindiaservices.Dao.ModelDao"%>
<%@page import="com.schillerindiaservices.bean.Pendingactmaster"%>
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
else if(role.equalsIgnoreCase("fqc"))
{
 %>
  <%@include file="FQCdashboard.jsp" %>
  <%
      }
else if(role.equalsIgnoreCase("ViceChancellor"))
{
%>
<%@include  file="VPDashboard.jsp" %>
<%
}else if(role.equalsIgnoreCase("repairteam"))
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
                                <form role="form" action="Repair_UpdateController" method="post">
                                    <div class="col-lg-4">
                                    <%
                                        int id=0;
                                    String division="";
                                    String repair_by="";
                                    String init_date="";
                                    String model="";
                                    String sc_ref_no="";
                                    String def_gir_no="";
                                    String Def_brd_mod_name="";
                                    String tar_col_date="";
                                    String remark="";
                                    String status="";
                                    String div="";
                                    String mod="";
                                    String ser_id="";
                                    String comp_used_to_repair="";
                                    String sc_inchrg_remarks="";
                                    String cat = "PFRN";
                                    int lid=Integer.parseInt(session.getAttribute("loguserid").toString());
                                           // division=EmployeeDao.getempdivision(lid);
                                            String eid=String.valueOf(lid);
                                            cat = (String) request.getAttribute("category");
                                            if((request.getAttribute("pendingrecord"))!=null)
                                            {
                                            Repair_master cr=(Repair_master) request.getAttribute("pendingrecord");
                                            id=cr.getId();
                                            division=cr.getDivision();
                                            init_date=cr.getEntry_date();
                                            model=cr.getModel();
                                            sc_ref_no=cr.getSc_ref_no();
                                            def_gir_no=cr.getDef_gir_no();
                                            Def_brd_mod_name=cr.getDef_brd_mod_name();
                                          //  tar_col_date=cr.getTarClosedDate();
                                            remark=cr.getTech_remarks();
                                            repair_by=cr.getRepaired_by();
                                            div=cr.getDivision();
                                            status=cr.getStatusType();
                                            comp_used_to_repair=cr.getComp_used_to_repair();
                                            sc_inchrg_remarks=cr.getFinal_remarks();
                                            ser_id=cr.getSer_id();
                                            pageContext.setAttribute("repaired_by", repair_by);
                                            pageContext.setAttribute("stat", status);
                                            }
                                        //    String sc_name=EmployeeDao.getname(sc_engg);
                                    %>

                                    <input type="hidden" name="id" class="form-control" value="<%=id%>">
                                    <%
                                        if(role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("ViceChancellor"))
                                        {
                                          
                                    %>
                                         <div class="form-group">
                                                <label> Division</label>
                                                <input type="text" name="division" value="<%=div%>" minlength="1" maxlength="40"  value="" class="form-control" readonly="" >
                                        </div>
                                        <%
                                            }
                                            else
                                            {
                                        %>
                                        <div class="form-group">
                                                <label> Division</label>
                                                 <input type="hidden" name="ser_id" value="<%=ser_id%>">
                                                  <input type="hidden" name="cat" value="<%=cat%>">
                                                <input type="text" name="division" value="<%=division%>" minlength="1" maxlength="40"  value="" class="form-control" readonly="">
                                        </div>
                                        <%
                                            }
                                        %>
                                       <%--  <div class="form-group">
                                                <label> SC Engineer</label>
                                               
                                                <input type="text"  value="<%=sc_name%>" minlength="1" maxlength="40" value="" class="form-control" readonly="">
                                        </div> --%>
                                                <%
                                                    if(role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("ViceChancellor"))
                                                    {
                                                    %>
                                                    <div class="form-group">
                                                <label>Entry Date</label>
                                                <input type="text" name="initiated_Date"  id="datepicker-1"    value="<%=init_date%>" class="form-control">
                                        </div>
                                                    <%
                                                        }else{
                                                        %>
                                                        <div class="form-group">
                                                <label>Initiated Date</label>
                                                <input type="text" name="initiated_Date"  value="<%=init_date%>" class="form-control"readonly="">
                                        </div>
                                                        <%
                                                            }
                                                            %>
                                        
                                        <%
                                                    if(role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("ViceChancellor"))
                                                    {
                                                    %>
                                                     <div class="form-group">
                                                <label>model </label>
                                                <input type="text" name="model" class="form-control" value="<%=model%>" min="1" max="500">
                                                
                                        </div>
                                                    <%
                                                        }else{
                                                        %>
                                                         <div class="form-group">
                                                <label>model </label>
                                                <input type="text" name="model" class="form-control" value="<%=model%>" min="1" max="500" readonly="">
                                                
                                        </div>
                                                        <%
                                                            }
                                                            %>
                                       
                                       
                                         <%
                                                    if(role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("ViceChancellor"))
                                                    {
                                                    %>
                                                    <div class="form-group">
                                                <label>Sc_ref_no</label>
                                                <input type="text" name="sc_ref_no" value="<%=sc_ref_no%>" min="1" max="500" class="form-control">
                                            </div>
                                                    <%
                                                        }else{
                                                        %>
                                                        <div class="form-group">
                                                <label>Sc_ref_no</label>
                                                <input type="text" name="sc_ref_no" value="<%=sc_ref_no%>" min="1" max="500" class="form-control" readonly="">
                                            </div>
                                                        <%
                                                            }
                                                            %>

                                    </div>
                                        <div class="col-lg-4">
                                           
                                             
                                            <%
                                                    if(role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("ViceChancellor"))
                                                    {
                                                    %>
                                                    <div class="form-group">
                                                <label>def_gir_no</label>
                                                <input type="text" name="def_gir_no" value="<%=def_gir_no%>"  min="1" max="250"  class="form-control">
                                            </div>
                                                    <%
                                                        }else{
                                                        %>
                                                        <div class="form-group">
                                                <label>def_gir_no</label>
                                                <input type="text" name="def_gir_no" value="<%=def_gir_no%>"  min="1" max="250"  class="form-control" readonly="">
                                            </div>
                                                        <%
                                                            }
                                                            %>
                                            
                                       
                                          
                                             <div class="form-group">
                                                <label>Def Mod/Brd Name</label>
                                                <input type="text" name="Def_brd_mod_name" value="<%=Def_brd_mod_name%>"  min="1" max="250" class="form-control" readonly="">
                                            </div>
                                            
                                            <div class="form-group">
                                                <label>Repaired by</label>
                                                <select class="form-control"  name="repaired_by" required="">
                                                    <option value="">Select Type</option> 
                                                    <option value="Gajenthiran"${repaired_by=='Gajenthiran'?'selected':''}>Gajenthiran</option>
                                                    <option value="Vassougui"${repaired_by=='Vassougui'?'selected':''}>Vassougui</option>
                                                     
                                                </select></div>
                                               <%
                                                    if(role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("ViceChancellor"))
                                                    {
                                                    %>
                                                    <div class="form-group">
                                                <label>Comp_used_to_repair</label>
                                                <input type="text" name="comp_used_to_repair" id="datepicker-2"  value="<%=comp_used_to_repair%>" id="datepicker-2" value="" class="form-control" >
                                        </div>
                                                    <%
                                                        }else{
                                                        %>
                                                        <div class="form-group">
                                                <label>Comp_used_to_repair</label>
                                                <input type="text" name="comp_used_to_repair"  value="<%=comp_used_to_repair%>"  class="form-control" >
                                        </div>
                                                        <%
                                                            }
                                                            %> 
                                            
                                              
                                            </div>
                                        <div class="col-lg-4">
                                               <div class="form-group">
                                                <label>Tech Remarks</label>
                                                <input type="text" name="remarks" min="1" max="500" value="<%=remark%>"  class="form-control">
                                        </div>
                                                 
                                                
                                           <%
                                            role=(String)session.getAttribute("logrole");
                                           
                                            
                                            if(role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("ViceChancellor")){
                                            	%>
                                            	<div class="form-group">
                                                <label>Final Remarks</label>
                                                <input type="text" name="sc_inchrg_remarks"  value="<%=sc_inchrg_remarks%>" min="1" max="500"  class="form-control"  >
                                        </div>
                                            	
                                            	<% 
                                            }else{
                                            
                                            %> 
                                            <div class="form-group">
                                                <label>Final Remarks</label>
                                                <input type="text" name="sc_inchrg_remarks" value="<%=sc_inchrg_remarks%>"  min="1" max="500"  class="form-control" >
                                        </div>
                                            <%}
                                            %>   
                                        <%
                                            
                                            if(role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("vicechancellor"))
                                            {
                                            %>
                                              <div class="form-group">
                                                <label>Status</label>
                                                <select class="form-control"  name="status" required="">
                                                    <option value="">Select Type</option> 
                                                    <option value="completed"${stat=='completed'?'selected':''}>completed</option>
                                                    <option value="pending"${stat=='pending'?'selected':''}>pending</option>
                                                     
                                                </select></div>
                                                    <div align="center">
                                                        <center><button type="submit" class="btn btn-success" onclick="closeFun()">SAVE</button></center>
                                            </div> 
                                                    <%
                                                        }else{
                                                        if(status.equalsIgnoreCase("pending"))
                                                            {
                                                            %>
                                                            <div class="form-group">
                                                <label>Status</label>
                                                <select class="form-control"  name="status" required="">
                                                    <option value="">Select Type</option> 
                                                    <option value="completed"${stat=='completed'?'selected':''}>Completed</option>
                                                    <option value="pending"${stat=='pending'?'selected':''}>Pending</option>
                                                     
                                                </select></div>
                                                    <div align="center">
                                                        <center><button type="submit" class="btn btn-success" onclick="closeFun()">SAVE</button></center>
                                            </div> 
                                                            <%

                                                                }else{
                                                        %>
                                                        <div class="form-group">
                                                <label>Status </label>
                                                <input type="text" name="status"  minlength="1" maxlength="50" value="<%=status%>" class="form-control" readonly="">
                                        </div>
                                        <div> &nbsp;</div>
                                        <div class="form-group">
                                            <center><label  style="font-size: medium;font-weight: 800; color: #ff4015">Service Closed</label></center>
                                        </div>
                                                        <%
                                                           } }
                                                            %>
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
