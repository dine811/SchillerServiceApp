<%-- 
    Document   : nonSaleAdminUpdate
    Created on : Oct 14, 2017, 4:37:18 PM
    Author     : ShineLoGics
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.schillerindiaservices.Dao.ModelDao"%>
<%@page import="com.schillerindiaservices.Dao.SupplierDao"%>
<%@page import="com.schillerindiaservices.Dao.BranchDao"%>
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
    </head>

<body onload="call();fetch_scEngg(); ">

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
else if(role.equalsIgnoreCase("ViceChancellor"))
{
%>
 <%@include file="VPDashboard.jsp" %>
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
                                    String unit_details="";
                                    String fqc_inward_date="";
                                    String region="";
                                    String branch="";
                                    String engineer="";
                                    String dealer="";
                                    String supplier="";
                                    String model="";
                                    String model_sn="";
                                    String unit_config="";
                                    String cust_name="";
                                    String report_problm="";
                                    String replace_unit="";
                                    String replace_ship_date="";
                                    String defect_unit_recv_date="";
                                    String fqc_observation="";
                                    String sc_in_date="";
                                    String sc_observation="";
                                    String req_parts="";
                                    String root_cause="";
                                    String action_plan="";
                                    String ship_date_fqc="";
                                    String status="";
                                    String br_name="";
                                    String eng_name="";
                                    String del_name=""; 
                                    String sup_name="";
                                    String mod_name="";
                                    String sc_name="";
                                    String ship_date="";
                                    String fqc_rem="";
                                    
                                    
                                    
                                    String divid="";
                                    int lid=Integer.parseInt(session.getAttribute("loguserid").toString());
                                            division=EmployeeDao.getempdivision(lid);
                                            String eid=String.valueOf(lid);
                                            divid=ProductDao.getDiviName(division);
//                                            sc_engg=EmployeeDao.geteName(eid);
                                            if((request.getAttribute("nonsale"))!=null)
                                            {
                                            Nonsaleablemaster cr=(Nonsaleablemaster) request.getAttribute("nonsale");
                                            id=cr.getId();
                                            division=cr.getDivision();
                                            sc_engg=cr.getScEngg();
                                             unit_details=cr.getUnitDetails();
                                             pageContext.setAttribute("unitdetails", unit_details);
                                             fqc_inward_date=cr.getFqcInDate();
                                             region=cr.getRegion();
                                             br_name=cr.getBranch();
                                             eng_name=cr.getEngineer();
                                             del_name=cr.getDealer();
                                             sup_name=cr.getSupplier();
                                             mod_name=cr.getModel();
                                             model_sn=cr.getModelSN();
                                             unit_config=cr.getUnitConfig();
                                             cust_name=cr.getCustName();
                                             report_problm=cr.getReportedProblm();
                                             replace_unit=cr.getReplacedUnitSN();
                                             replace_ship_date=cr.getReplaceShipDate();
                                             defect_unit_recv_date=cr.getDefectUnitRecivedDate();
                                             fqc_observation=cr.getFqcObservation();
                                             sc_in_date=cr.getScInwardDate();
                                             sc_observation=cr.getScObservation();
                                             req_parts=cr.getRequiredParts();
                                             root_cause=cr.getRootCause();
                                             action_plan=cr.getActionPlan();
                                             ship_date_fqc=cr.getTentDateShipDate();
                                             status=cr.getFinalStatus();
                                             ship_date=cr.getShipDateFqc();
                                             fqc_rem=cr.getFqcFinalRemark();
                                             pageContext.setAttribute("stat", status);
                                            }
                                            branch=BranchDao.getbName(br_name);  
                                            engineer=EmployeeDao.geteName(eng_name);
                                            dealer=DealerDao.getdName(del_name);
                                            model=ModelDao.getModelname(mod_name);
                                            supplier=SupplierDao.getSupName(sup_name,mod_name);
                                            sc_name=EmployeeDao.geteName(sc_engg);
                                    %>

                                    <input type="hidden" name="id" class="form-control" value="<%=id%>">
                                    <div class="form-group">
                                                <label> Division</label>
                                                <input type="text" name="division" value="<%=division%>" minlength="1" maxlength="40" class="form-control" readonly="" required="">
                                        </div>
                                        <div class="form-group">
                                                <label>Unit Details</label>
                                                <select class="form-control" id="unit_details"  name="unit_details" onchange="call()" >
                                                    <option value="">Select Type</option> 
                                                    <option value="Sales Stock"${unitdetails=='Sales Stock'?'selected':''} >Sales Stock</option>
                                                    <option value="DOA"${unitdetails=='DOA'?'selected':''}>DOA</option>
                                                    <option value="Demo"${unitdetails=='Demo'?'selected':''}>Demo</option>
                                                </select>
                                            </div>
                                                <div class="form-group">
                                                <label>FQC Inward date </label>
                                                <input type="text" name="fqc_in_date" id="datepicker-1"  value="<%=fqc_inward_date%>" class="form-control">
                                        </div>
                                        <div class="form-group">
                                                <label>Region</label>
                                                <input type="text"  value="<%=region%>" class="form-control" readonly="">
                                            </div>
                                        <div class="form-group">
                                                <label>Branch</label>
                                                <input type="text"  value="<%=branch%>" class="form-control" readonly="">
                                            </div>
                                                 <div class="form-group">
                                                <label>Engineer</label>
                                               <input type="text"  value="<%=engineer%>" class="form-control" readonly="">
                                        </div>
                                                <div class="form-group">
                                                <label>Dealer</label>
                                                <input type="text"  value="<%=dealer%>" class="form-control" readonly="">
                                            </div>
                                                
                                                 <div class="form-group">
                                                <label>Supplier</label>
                                                <input type="text"  value="<%=sup_name%>" class="form-control" readonly="">
                                            </div>
                                            <div class="form-group">
                                                <label>Model</label>
                                        <input type="text"  value="<%=model%>" class="form-control" readonly="">
                                             </div>
                                    </div>
                                             <div class="col-lg-4">
                                             
                                            
                                            <div class="form-group">
                                                <label>Model S/N</label>
                                                <input type="text" name="modelsn"  value="<%=model_sn%>" class="form-control" >
                                            </div>
                                            <div class="form-group">
                                                <label>Unit Configuration</label>
                                                <input type="text" name="unit_configur"  value="<%=unit_config%>" class="form-control">
                                                </div>
                                                <div class="form-group">
                                                <label>Reported Problem</label>
                                                <input type="text" name="report_prblm"  value="<%=report_problm%>" class="form-control">
                                        </div>
                                              <div class="form-group">
                                                <label>Customer name</label>
                                                <input type="text" name="cust_name" id="custname"  value="<%=cust_name%>" class="form-control">
                                        </div>
                                            
                                            <div class="form-group">
                                                <label>Replaced Unit S/N</label>
                                                <input type="text" name="repalce_unit_sn" id="replacesn"  value="<%=replace_unit%>" class="form-control">
                                        </div>
                                             <div class="form-group">
                                                <label>Replacement Ship Date</label>
                                                <input type="text" id="datepicker-2" name="replace_ship_date"  value="<%=replace_ship_date%>" class="form-control">
                                        </div>
                                            <div class="form-group">
                                                <label>Defective unit received  Date</label>
                                                <input type="text" id="datepicker-3" name="def_unit_recv_date"  value="<%=defect_unit_recv_date%>" class="form-control">
                                        </div>
                                        <div class="form-group">
                                                <label>FQC Observation</label>
                                                <input type="text" name="fqc_observ"  value="<%=fqc_observation%>" class="form-control">
                                        </div>
                                        <div class="form-group">
                                                <label> SC Engineer</label>
                                                <input type="hidden" value="<%=sc_engg%>" name="sc_engg" >
                                                <input type="text"  value="<%=sc_name%>" class="form-control" readonly="">
                                        </div>
                                         </div>
                                        <div class="col-lg-4">
                                        <div class="form-group">
                                                <label>SC Inward Date</label>
                                                <input type="text" name="sc_in_date" id="datepicker-4" minlength="1" maxlength="50" value="<%=sc_in_date%>" class="form-control" >
                                        </div> 
                                        <div class="form-group">
                                                <label>SC Observation</label>
                                                <input type="text" name="sc_observation" minlength="1" maxlength="500" value="<%=sc_observation%>" class="form-control">
                                        </div>
                                        <div class="form-group">
                                                <label>Required Parts</label>
                                                <input type="text" name="req_parts" minlength="1" maxlength="250" value="<%=req_parts%>" class="form-control" placeholder="" >
                                        </div>
                                        <div class="form-group">
                                                <label>Root Cause</label>
                                                <input type="text" name="root_cause" minlength="1" maxlength="500" value="<%=root_cause%>" class="form-control" placeholder="" >
                                        </div>
                                        <div class="form-group">
                                                <label>SC Action Plan</label>
                                                <input type="text" name="action_plan" minlength="1" maxlength="550" value="<%=action_plan%>" class="form-control" placeholder="" >
                                        </div>
                                        <div class="form-group">
                                                <label>Tentative Date</label>
                                                <input type="text" name="ship_date_fqc" id="datepicker-6" value="<%=ship_date_fqc%>" class="form-control">
                                                
                                        </div>
                                        <div class="form-group">
                                                <label>Ship Date to FQC </label>
                                                <input type="text" name="ship_date_fqc2" id="datepicker-7" minlength="1" maxlength="50" value="<%=ship_date%>" class="form-control" placeholder="" >
                                        </div>
                                        <div class="form-group">
                                                <label>FQC Final Remarks </label>
                                                <input type="text" name="fqc_final_remarks" minlength="1" maxlength="550" value="<%=fqc_rem%>" class="form-control" placeholder="" >
                                        </div>
                                        <div class="form-group">
                                                <label>Status</label>
                                                <select class="form-control"  name="status" required="" >
                                                    <option value="">Select Type</option> 
                                                    <option value="Completed"${stat=='Completed'?'selected':''}>Completed</option>
                                                    <option value="Pending"${stat=='Pending'?'selected':''}>Pending</option>
                                                     
                                                </select></div>
                                                    <div class="form-group">
                                                        &nbsp;
                                                    </div>
                                                    <div align="center">
                                                <center><button type="submit" class="btn btn-success">SAVE</button></center>
                                            </div>
                                                      
                                            <div>&nbsp;</div>
                                          
                                                       
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
         $(function() {
            $( "#datepicker-4" ).datepicker({ 
                 changeMonth: true,
		 changeYear: true,
                dateFormat: 'dd-mm-yy' });
            $( "#datepicker-15" ).datepicker("show");
         });
         $(function() {
            $( "#datepicker-7" ).datepicker({ 
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
</body>

</html>
