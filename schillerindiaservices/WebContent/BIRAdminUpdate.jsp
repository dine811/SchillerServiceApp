<%-- 
    Document   : BIRAdminUpdate
    Created on : Oct 14, 2017, 11:23:56 AM
    Author     : ShineLoGics
--%>
<%@page import="com.schillerindiaservices.Dao.SupplierDao"%>
<%@page import="com.schillerindiaservices.Dao.ModelDao"%>
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
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html lang="en">
    <head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Batch Inspection Report</title>

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
<body onload="fun()">

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
    
%>
        <%
            if(role.equalsIgnoreCase("admin"))
            {
            %>
       <%@include file="admindashboard.jsp" %>
       <%
           }else{
           %>
           <%@include file="VPDashboard.jsp" %>
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
                                    <div class="col-lg-12">
                                    <%
                                        int id=0;
                                    String division="";
                                    String fc_date="";
                                    String model_name="";
                                    String configr="";
                                    String receivd_qty="";
                                    String unit_serial_no="";
                                    String invoice_no="";
                                    String invoice_date="";
                                    String sftwr_chngs="";
                                    String sftwr_ver="";
                                    String hrdwr_chang="";
                                    String hrdwr_details="";
                                    String accesory_chngs="";
                                    String accesory_details="";
                                    String user_manul_updt="";
                                    String service_manul_updt="";
                                    String fqc_remark="";
                                    String cnr_tech_ref_no="";
                                    String ts_team_verification_date="";
                                    String ps_team_verification_date="";
                                    String status="";
                                    String model="";
                                    String accory_change_remark="";
                                    String hrdwr_chang_remark="";
                                    String sftwr_chang_remark="";
                                    String cnr_relese_date="";
                                    String bir_ref_no="";
                                    String supllier="";
                                    String sc_engg="";
                                    String ps_engg="";
                                    String cnr_ref_no="";
                                    String approved_date="";
                                    String supName="";
                                    String sceName="";
                                    String unit_date="";
                                    String tech_remarks="";
                                   
                                    int lid=Integer.parseInt(session.getAttribute("loguserid").toString());
                                            if((request.getAttribute("bir"))!=null)
                                            {
                                            Birmaster cr=(Birmaster) request.getAttribute("bir");
                                            id=cr.getId();
                                            cnr_ref_no=cr.getCnrTecRefNum();
                                            System.out.println("sysout value isss"+cnr_ref_no);
                                            ps_engg=cr.getPsEngg();
                                            sceName=cr.getScEngg();
                                            approved_date=cr.getApprovedDate();
                                            System.out.println("sysout value isss approo"+approved_date);
                                            supllier=cr.getSupplier();
                                            division=cr.getDivision();
                                            fc_date=cr.getFqcInDate();
                                            model_name=cr.getModel();
                                            configr=cr.getConfiguration();
                                            receivd_qty=cr.getReceivedQty();
                                            unit_serial_no=cr.getUnitSerialNo();
                                            invoice_no=cr.getInvoiceNo();
                                            invoice_date=cr.getInvoiceDate();
                                            sftwr_chngs=cr.getSoftwrChanges();
                                            pageContext.setAttribute("software", sftwr_chngs);
                                            sftwr_ver=cr.getSoftwrVersion();
                                            hrdwr_chang=cr.getHardwrChanges();
                                            pageContext.setAttribute("hardware", hrdwr_chang);
                                            hrdwr_details=cr.getHardwareDetails();
                                            accesory_chngs=cr.getAccesoryChanges();
                                            pageContext.setAttribute("accessory", accesory_chngs);
                                            accesory_details=cr.getAccesoryDetails();
                                            user_manul_updt=cr.getUserManualUpdate();
                                            service_manul_updt=cr.getServiceManualUpdate();
                                            fqc_remark=cr.getFqcRemarks();
                                            cnr_tech_ref_no=cr.getCnrRefNo();
                                            pageContext.setAttribute("circulation", cnr_tech_ref_no);
                                            ts_team_verification_date=cr.getTsTeamRemark();
                                            ps_team_verification_date=cr.getPsTeamRemark();
                                            status=cr.getFinalStatus();
                                            accory_change_remark=cr.getAccesChngRemark();
                                            hrdwr_chang_remark=cr.getHrdwrChangRemark();
                                            sftwr_chang_remark=cr.getSftwrChngRemark();
                                            cnr_relese_date=cr.getCnrReleseDate();
                                            System.out.println("the releaese dat iss"+cnr_relese_date);
                                            bir_ref_no=cr.getBirRefNo();
                                            pageContext.setAttribute("stat", status);
                                            unit_date=cr.getUnitInDate();
                                            tech_remarks=cr.getTechRemarks();
                                            }
                                            model=ModelDao.getModelname(model_name);
                                             supName=SupplierDao.getSupName(supllier, model_name);
                                            sc_engg=EmployeeDao.geteName(sceName);
                                    %>
                                    <div class="col-lg-4">
                                    

                                    <input type="hidden" name="id" class="form-control" value="<%=id%>">
                                        <div class="form-group">
                                                <label> Division</label>
                                                <input type="text" name="division" class="form-control" value="<%=division%>" readonly="">
                                        </div>
                                        <div class="form-group">
                                                <label>Unit Inward Date</label>
                                                <input type="text" name="unit_inward_date" id="datepicker-7" value="<%=unit_date%>" class="form-control" required="">
                                            </div>
                                        <div class="form-group">
                                                <label>FQC Inward Date</label>
                                                <input type="text" name="fqc_inward_date" id="datepicker-1" value="<%=fc_date%>" class="form-control" min="1" max="100 ">
                                            </div>
                                            <div class="form-group">
                                                <label>BIR Reference No.</label>
                                               <input type="text" name="bir_ref_no"  value="<%=bir_ref_no%>" min="1" max="100" class="form-control" >
                                        </div>
                                        <div class="form-group">
                                                <label>Supplier</label> v<%=supllier%>
                                                <input type="text" name="supllier" value="<%=supllier%>"  class="form-control" readonly="">
                                        </div>
                                        <div class="form-group">
                                                <label>Model</label>
                                                <input type="text" name="model" class="form-control" value="<%=model%>" readonly="">
                                        </div>
                                        <div class="form-group">
                                                <label>Configuration</label>
                                                <input type="text" name="config" value="<%=configr%>" min="1" max="100" class="form-control" >
                                            </div>
                                        <div class="form-group">
                                                <label>Received Qty</label>
                                                <input type="text" name="receivd_qty" value="<%=receivd_qty%>"  class="form-control" >
                                            </div>
                                                 <div class="form-group">
                                                <label>Unit Serial No.</label>
                                               <input type="text" name="unit_serial_no" value="<%=unit_serial_no%>" class="form-control" min="1" max="500">
                                        </div>
                                                <div class="form-group">
                                                <label>Invoice No.</label>
                                                <input type="text" name="invoice_no"  value="<%=invoice_no%>"  class="form-control" min="1" max="100">
                                            </div>
                                                
                                              <div class="form-group">
                                                <label>Invoice Date</label>
                                                 <input type="text" name="invoice_date" id="datepicker-2" value="<%=invoice_date%>" min="1" max="100" class="form-control" >
                                            </div>
                                                 
                                    </div>
                                                <div class="col-lg-4">
                                            <div class="form-group">
                                                <label>Previous Software Version</label>
                                                <input type="text" class="form-control" name="sftwr_chngs" value="<%=sftwr_chngs%>">
                                                   
                                             </div>  
                                            <div class="form-group">
                                                <label>Present Software Version</label>
                                                 <input type="text" name="sftwr_ver"  value="<%=sftwr_ver%>" min="1" max="100" class="form-control" >
                                            </div>
                                            <div class="form-group">
                                                <label>Accessory Changes </label>
                                                    <select type="text" name="accesory_chngs" class="form-control" required="">
                                                    <option value="">Select </option>
                                                    <option value="Yes"${accessory=='Yes'?'selected':''}>Yes</option>
                                                    <option value="No"${accessory=='No'?'selected':''}>No</option>
                                                </select>  
                                            </div>
                                            <div class="form-group">
                                                <label>Accessory Details</label>
                                                <input type="text" name="accesory_details"  value="<%=accesory_details%>" min="1" max="500" class="form-control" >
                                        </div>
                                            
                                            
                                            <div class="form-group">
                                                <label>User Manual Update</label>
                                               <input type="text" name="user_manul_updt"  value="<%=user_manul_updt%>" min="1" max="100" class="form-control" >
                                        </div>
                                           
                                             <div class="form-group">
                                                <label>FQC Remarks </label>
                                                 <input type="text" name="fqc_remark"  value="<%=fqc_remark%>" min="1" max="1000" class="form-control" >
                                        </div>
                                        <div class="form-group">
                                                <label>SC Engineer </label>
                                                <input type="hidden" name="sc_engg"  value="<%=sceName%>">
                                                <input type="text"   value="<%=sc_engg%>" min="1" max="100" class="form-control" readonly="">
                                        </div>
                                        <div class="form-group">
                                                <label>Accessory Change Remarks</label>
                                                <input type="text" name="accory_change_remark"  value="<%=accory_change_remark%>" min="1" max="1000" class="form-control">
                                        </div>
                                        <div class="form-group">
                                                <label>Hardware Changes</label>
                                                <select type="text" name="hrdwr_chang" class="form-control">
                                                    <option value="">Select </option>
                                                    <option value="Yes"${hardware=='Yes'?'selected':''}>Yes</option>
                                                    <option value="No"${hardware=='No'?'selected':''}>No</option>
                                                </select>  
                                            </div>
                                                <div class="form-group">
                                                <label>Hardware Change Remarks</label>
                                                <input type="text" name="hrdwr_chang_remark" value="<%=hrdwr_chang_remark%>" class="form-control" min="1" max="1000" >
                                                    
                                            </div>
                                         <div class="form-group">
                                                <label>Service Manual Update</label>
                                                <input type="text" name="service_manul_updt"  value="<%=service_manul_updt%>" min="1" max="100" class="form-control" >
                                        </div>
                                          </div>
                                        <div class="col-lg-4">
                                            
                                                <div class="form-group">
                                                <label>Technical Remarks</label>
                                                 <input type="text" name="tech_remarks"  value="<%=tech_remarks%>" min="1" max="1000" class="form-control">
                                        </div> 
                                                <div class="form-group">
                                                <label>TS Team Verification Date</label>
                                                 <input type="text" name="ts_team_verification_date"  value="<%=ts_team_verification_date%>" id="datepicker-3" min="1" max="100" class="form-control" >
                                        </div>
                                        <div class="form-group">
                                                <label>PS Engineer</label>
                                                <input type="text" name="ps_engg" value="<%=ps_engg%>" class="form-control" readonly="" >
                                                
                                        </div>
                                        <div class="form-group">
                                                <label>Software Change Remarks</label>
                                                <input type="text" name="sftwr_chang_remark" class="form-control" value="<%=sftwr_chang_remark%>" min="1" max="1000">
                                                    
                                            </div>
                                                <div class="form-group">
                                                <label> CNR/Technews Circulation</label>
                                                
                                                <select name="cnr_tech_ref_no" class="form-control" id="cnr" onchange="fun()">
                                                    <option value="">Select Circulation</option>
                                                    <option value="Sale"${circulation=='Sale'?'selected':''}>Sale</option>
                                                    <option value="Servcie"${circulation=='Servcie'?'selected':''}>Servcie</option>
                                                    <option value="Export"${circulation=='Export'?'selected':''}>Export</option>
                                                    <option value="Internal"${circulation=='Internal'?'selected':''}>Internal</option>
                                                    <option value="Sales&Service&Export"${circulation=='Sales&Service&Export'?'selected':''}>Sales & Service & Export</option>
                                                    <option value="Sales&Service"${circulation=='Sales&Service'?'selected':''}>Sales & Service</option>
                                                    <option value="Not Required"${circulation=='Not Required'?'selected':''}>Not Required</option>
                                                </select>
                                                </div>
                                                
                                               <%--  <div class="form-group">
                                                <label> CNR/Technews Reference No. </label>
                                                <input type="text" name="cnr_ref_no" id="cnrref"   value="<%=tech_remarks%>" min="1" max="100" class="form-control" >
                                        </div>
                                        <div class="form-group">
                                                <label> CNR/Technews Release Date</label>
                                                <input type="text" name="cnr_relese_date" id="datepicker-4"    value="<%=tech_remarks%>" min="1" max="100" class="form-control" >
                                        </div>   --%>
                                        
                                                <div class="form-group">
                                                <label>CNR/Technews Reference No.</label>
                                                 <input type="text" name="cnr_ref_no"  value="<%=cnr_ref_no%>" min="1" max="1000" class="form-control">
                                        </div> 
                                         <div class="form-group">
                                                <label> CNR/Technews Release Date</label>
                                                <input type="text" name="cnr_relese_date" id="datepicker-4"   value="<%=cnr_relese_date%>" min="1" max="100" class="form-control">
                                        </div>
                                                
                                        <div class="form-group">
                                                <label>PS Team Verification Date</label>
                                                <input type="text" name="ps_team_verification_date" id="datepicker-5"   value="<%=ps_team_verification_date%>" min="1" max="100" class="form-control">
                                        </div>
                                      <%--   <div class="form-group">
                                                <label>PS Team Verification Date</label>
                                                <input type="text" name="ps_team_verification_date" id="datepicker-5"   value="<%=tech_remarks%>" min="1" max="100" class="form-control">
                                        </div> --%>
                                      <div class="form-group">
                                                <label>Approved Date</label>
                                                <input type="text" name="approved_date" id="aproved" value="<%=approved_date%>" min="1" max="100" class="form-control" readonly="">
                                        </div> 
                                        <div class="form-group">
                                                <label>Status</label>
                                                <select class="form-control"  name="status" id="status" onchange="appdate();" required="" >
                                                    <option value="">Select Type</option> 
                                                    <option value="Completed"${stat=='Completed'?'selected':''}>Completed</option>
                                                    <option value="Pending"${stat=='Pending'?'selected':''}>Pending</option>
                                                     
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
          function appdate()
          {
              var status=document.getElementById("status").value;
              var today=new Date();
              var dd=today.getDate();
              var mm=today.getMonth()+1;
              var yy=today.getFullYear();
              if(dd<10) {
                         dd = '0'+dd
                        } 

                if(mm<10) {
                           mm = '0'+mm
                            } 
             // var dat=dd+"-"+mm+"-"+yy;
             var dat=yy+"-"+mm+"-"+dd;
              if(status=="Completed")
              {
                  
              document.getElementById("aproved").value=dat;
             
          }else{
              document.getElementById("aproved").value="";
          }
          }
          function fun()
    {
       var a=document.getElementById("cnr").value;
       var cnrref=document.getElementById("cnrref");
       var cnrdate=document.getElementById("datepicker-4")
       if(a=="Not Required")
       {
          cnrref.disabled=true;
          cnrref.value="NIL";
          cnrdate.disabled=true;
          cnrdate.value="NIL";
       }else{
           cnrref.disabled=false;
          cnrref.value="";
          cnrdate.disabled=false;
          cnrdate.value="";
       }}
      </script>
      <script>
           $(function() {
            $( "#datepicker-1" ).datepicker({ 
                 changeMonth: true,
		 changeYear: true,
                dateFormat: 'yy-mm-dd' });
            $( "#datepicker-15" ).datepicker("show");
         });
         $(function() {
            $( "#datepicker-2" ).datepicker({ 
                 changeMonth: true,
		 changeYear: true,
                dateFormat: 'yy-mm-dd' });
            $( "#datepicker-15" ).datepicker("show");
         });
         $(function() {
            $( "#datepicker-3" ).datepicker({ 
                 changeMonth: true,
		 changeYear: true,
                dateFormat: 'yy-mm-dd' });
            $( "#datepicker-15" ).datepicker("show");
         });
          $(function() {
            $( "#datepicker-4" ).datepicker({ 
                 changeMonth: true,
		 changeYear: true,
                dateFormat: 'yy-mm-dd' });
            $( "#datepicker-15" ).datepicker("show");
         });
          $(function() {
            $( "#datepicker-5" ).datepicker({ 
                 changeMonth: true,
		 changeYear: true,
                dateFormat: 'yy-mm-dd' });
            $( "#datepicker-15" ).datepicker("show");
         });
          $(function() {
            $( "#datepicker-6" ).datepicker({ 
                 changeMonth: true,
		 changeYear: true,
                dateFormat: 'yy-mm-dd' });
            $( "#datepicker-15" ).datepicker("show");
         });
         $(function() {
            $( "#datepicker-7" ).datepicker({ 
                 changeMonth: true,
		 changeYear: true,
                dateFormat: 'yy-mm-dd' });
            $( "#datepicker-15" ).datepicker("show");
         });
    </script>
</body>

</html>