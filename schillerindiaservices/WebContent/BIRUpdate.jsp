<%-- 
    Document   : nonSaleEnggForm
    Created on : Oct 9, 2017, 11:30:23 AM
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

<body onload="fetch_scEngg();">

    <div id="wrapper">
<%
            
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
else if(role.equalsIgnoreCase("fqc"))
{
 %>
  <%@include file="FQCdashboard.jsp" %>
  <%
      }
else
{
  %>
  <%@include  file="PSdashboard.jsp" %>
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
                                    String sc_engg="";
                                    String ps_engg="";
                                    String cnr_ref_no="";
                                    String approved_date="";
                                    String supllier="";
                                    String supName="";
                                    String sceName="";
                                    String unit_in_date="";
                                    String tech_remarks="";
                                    
                                    
                                    int lid=Integer.parseInt(session.getAttribute("loguserid").toString());
                                            if((request.getAttribute("bir"))!=null)
                                            {
                                            Birmaster cr=(Birmaster) request.getAttribute("bir");
                                            id=cr.getId();
                                            approved_date=cr.getApprovedDate();
                                            cnr_ref_no=cr.getCnrTecRefNum();
                                            System.out.println(" cnrr number iss"+cnr_ref_no);
                                            ps_engg=cr.getPsEngg();
                                            supllier=cr.getSupplier();
                                            sceName=cr.getScEngg();
                                            division=cr.getDivision();
                                            fc_date=cr.getFqcInDate();
                                            model_name=cr.getModel();
                                            configr=cr.getConfiguration();
                                            receivd_qty=cr.getReceivedQty();
                                            unit_serial_no=cr.getUnitSerialNo();
                                            invoice_no=cr.getInvoiceNo();
                                            invoice_date=cr.getInvoiceDate();
                                            sftwr_chngs=cr.getSoftwrChanges();
                                            sftwr_ver=cr.getSoftwrVersion();
                                            hrdwr_chang=cr.getHardwrChanges();
//                                            hrdwr_details=cr.getHardwareDetails();
                                            accesory_chngs=cr.getAccesoryChanges();
                                            accesory_details=cr.getAccesoryDetails();
                                            user_manul_updt=cr.getUserManualUpdate();
                                            service_manul_updt=cr.getServiceManualUpdate();
                                            fqc_remark=cr.getFqcRemarks();
                                            cnr_tech_ref_no=cr.getCnrRefNo();
                                            System.out.println(" cnrr number tech ref no iss"+cnr_tech_ref_no);
                                            ts_team_verification_date=cr.getTsTeamRemark();
                                            ps_team_verification_date=cr.getPsTeamRemark();
                                            status=cr.getFinalStatus();
                                            accory_change_remark=cr.getAccesChngRemark();
                                            hrdwr_chang_remark=cr.getHrdwrChangRemark();
                                            sftwr_chang_remark=cr.getSftwrChngRemark();
                                            cnr_relese_date=cr.getCnrReleseDate();
                                            bir_ref_no=cr.getBirRefNo();
                                            pageContext.setAttribute("stat", status);
                                            unit_in_date=cr.getUnitInDate();
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
                                                <input type="text" name="division" id="division" class="form-control" value="<%=division%>" readonly="">
                                        </div>
                                        <div class="form-group">
                                                <label>Unit Inward Date</label>
                                                <input type="text" name="unit_inward_date"  value="<%=unit_in_date%>" class="form-control" readonly="">
                                            </div>
                                        <div class="form-group">
                                                <label>FQC Inward Date</label>
                                                <input type="text" name="fqc_inward_date"  value="<%=fc_date%>" class="form-control" readonly>
                                            </div>
                                                <div class="form-group">
                                                <label>BIR Reference No.</label>
                                                <input type="text" name="bir_ref_no"  value="<%=bir_ref_no%>" min="1" max="100" class="form-control" readonly="">
                                        </div>
                                                <div class="form-group">
                                                <label>Supplier</label>
                                                <input type="text" name="supllier" value="<%=supllier%>"  class="form-control" readonly="">
                                                
                                        </div>
                                        <div class="form-group">
                                                <label>Model</label> 
                                                <input type="text" name="model" value="<%=model%>" class="form-control" readonly="">
                                                
                                        </div>
                                        
                                        <div class="form-group">
                                                <label>Configuration</label>
                                                <input type="text" name="config"  value="<%=configr%>" min="1" max="100" class="form-control" readonly="">
                                            </div>
                                        <div class="form-group">
                                                <label>Received Qty</label>
                                                <input type="text" name="receivd_qty"  value="<%=receivd_qty%>" min="1" max="100" class="form-control" readonly="">
                                            </div>
                                                 <div class="form-group">
                                                <label>Unit Serial No.</label>
                                               <input type="text" name="unit_serial_no" id="" value="<%=unit_serial_no%>" min="1" max="500" class="form-control" readonly="">
                                        </div>
                                                <div class="form-group">
                                                <label>Invoice No.</label>
                                                <input type="text" name="invoice_no" id="" value="<%=invoice_no%>" min="1" max="100" class="form-control" readonly="">
                                            </div>
                                                
                                              <div class="form-group">
                                                <label>Invoice Date</label>
                                                 <input type="text" name="invoice_date"  value="<%=invoice_date%>" min="1" max="100" class="form-control" readonly="">
                                            </div>
                                                  
                                    </div>
                                        <div class="col-lg-4">
                                            <div class="form-group">
                                                <label>Previous Software Version</label>
                                                <input type="text" name="sftwr_chngs" value="<%=sftwr_chngs%>" class="form-control" min="1" max="100" readonly="">
                                                
                                             </div> 
                                            <div class="form-group">
                                                <label>Present Software Version</label>
                                                 <input type="text" name="sftwr_ver"  value="<%=sftwr_ver%>" min="1" max="100" class="form-control" readonly="">
                                            </div>
                                            <div class="form-group">
                                                <label>Accessory Changes </label>
                                                <input type="text" name="accesory_chngs" value="<%=accesory_chngs%>" class="form-control" readonly="">
                                                 
                                            </div>
                                            <div class="form-group">
                                                <label>Accessory Details</label>
                                                <input type="text" name="accesory_details"  value="<%=accesory_details%>" min="1" max="500" class="form-control" readonly>
                                        </div>
                                            
                                            
                                            <div class="form-group">
                                                <label>User Manual Update</label>
                                               <input type="text" name="user_manul_updt"  value="<%=user_manul_updt%>" min="1" max="100" class="form-control" readonly="">
                                        </div>
                                            
                                             <div class="form-group">
                                                <label>FQC Remarks </label>
                                                 <input type="text" name="fqc_remark"  value="<%=fqc_remark%>" min="1" max="1000" class="form-control" readonly="">
                                        </div>
                                                 <%
                                                     if(role.equalsIgnoreCase("engineer")&&status.equalsIgnoreCase("pending"))
                                                     {
                                                         %>
                                                         
                                                         <%
                                                             if(sc_engg=="")
                                                             {
                                                               %>
                                                               <div class="form-group">
                                                <label>SC Engineer </label>
                                                <select name="sc_engg" class="form-control" id="myScEngg" required="">
                                                    <option value="">Select Engineer</option>
                                                </select>
                                               
                                        </div>
                                                               <%
                                                             }else{
                                                               %>
                                                               <div class="form-group">
                                                <label>SC Engineer </label>
                                                <input type="hidden" name="sc_engg"  value="<%=sceName%>">
                                                <input type="text"   value="<%=sc_engg%>" min="1" max="100" class="form-control" readonly="">
                                        </div>
                                                               <%
                                                             }
                                                             %>
                                                         
                                                         <%
                                                             if(accory_change_remark==""||accory_change_remark.equalsIgnoreCase(""))
                                                             {
                                                               %>
                                                               <div class="form-group">
                                                <label>Accessory Change Remarks</label>
                                                <input type="text" name="accory_change_remark"  value="<%=accory_change_remark%>" min="1" max="1000" class="form-control" required="">
                                        </div>
                                                               <%
                                                             }else{
                                                               %>
                                                               <div class="form-group">
                                                <label>Accessory Change Remarks</label>
                                                <input type="text" name="accory_change_remark"  value="<%=accory_change_remark%>" min="1" max="1000" class="form-control" readonly="">
                                        </div>
                                                               <%
                                                             }
                                                             %>
                                           
                                            <%
                                                             if(hrdwr_chang==""||hrdwr_chang.equalsIgnoreCase(""))
                                                             {
                                                               %>
                                                               <div class="form-group">
                                                <label>Hardware Changes</label>
                                                <select name="hrdwr_chang" class="form-control" required="">
                                                    <option value="">Select </option>
                                                    <option value="Yes">Yes</option>
                                                    <option value="No">No</option>
                                                </select>
                                                    
                                            </div>
                                                               <%
                                                             }else{
                                                               %>
                                                               <div class="form-group">
                                                <label>Hardware Changes</label>
                                                <input type="text" name="hrdwr_chang" value="<%=hrdwr_chang%>" class="form-control" min="1" max="100" readonly="">
                                                    
                                            </div>
                                                               <%
                                                             }
                                                             %>
                                               <%
                                                             if(hrdwr_chang_remark==""||hrdwr_chang_remark.equalsIgnoreCase(""))
                                                             {
                                                               %>
                                                               <div class="form-group">
                                                <label>Hardware Change Remarks</label>
                                                <input type="text" name="hrdwr_chang_remark" value="<%=hrdwr_chang_remark%>" class="form-control" min="1" max="1000" required="">
                                                    
                                            </div>
                                                               <%
                                                             }else{
                                                               %>
                                                               <div class="form-group">
                                                <label>Hardware Change Remarks</label>
                                                <input type="text" name="hrdwr_chang_remark" value="<%=hrdwr_chang_remark%>" min="1" max="1000" class="form-control" readonly="">
                                                    
                                            </div>
                                                               <%
                                                             }
                                                             %>
                                               <%
                                                             if(service_manul_updt==""||service_manul_updt.equalsIgnoreCase(""))
                                                             {
                                                               %>
                                                               <div class="form-group">
                                                <label>Service Manual Update</label>
                                                <input type="text" name="service_manul_updt"  value="<%=service_manul_updt%>" min="1" max="100" class="form-control" required="">
                                                </div>
                                            </div>
                                                               <%
                                                             }else{
                                                               %>
                                                               <div class="form-group">
                                                <label>Service Manual Update</label>
                                                <input type="text" name="service_manul_updt"  value="<%=service_manul_updt%>" min="1" max="100" class="form-control" readonly="">
                                                </div>
                                            </div>
                                                               <%
                                                             }
                                                             %>
                                           
                                        <div class="col-lg-4">
                                           
                                                             <%
                                                                 if(tech_remarks.equalsIgnoreCase("")||tech_remarks=="")
                                                                 {
                                                                   %>
                                                                   <div class="form-group">
                                                <label>Technical Remarks</label>
                                                <input type="text" name="tech_remarks"  value="<%=tech_remarks%>" min="1" max="1000" class="form-control" required="">
                                        </div> 
                                                                   <%
                                                                 }else{
                                                                     %>
                                                                     <div class="form-group">
                                                <label>Technical Remarks</label>
                                                 <input type="text" name="tech_remarks"  value="<%=tech_remarks%>" min="1" max="1000" class="form-control" readonly="">
                                        </div> 
                                                                     <%
                                                                 }
                                                                 %>
                                            
                                                <%
                                                             if(ts_team_verification_date==""||ts_team_verification_date.equalsIgnoreCase(""))
                                                             {
                                                               %>
                                                               <div class="form-group">
                                                <label>TS Team Verification Date</label>
                                                <input type="text" name="ts_team_verification_date" id="datepicker-1"  value="<%=ts_team_verification_date%>" min="1" max="100" class="form-control" required="">
                                        </div> 
                                                               <%
                                                             }else{
                                                               %>
                                                               <div class="form-group">
                                                <label>TS Team Verification Date</label>
                                                 <input type="text" name="ts_team_verification_date"  value="<%=ts_team_verification_date%>" min="1" max="100" class="form-control" readonly="">
                                        </div> 
                                                               <%
                                                             }
                                                             %>
                                            
                                                         <%
                                                     }else{
                                                     %>
                                                      <div class="form-group">
                                                <label>SC Engineer </label>
                                                <input type="hidden" name="sc_engg"  value="<%=sceName%>">
                                                <input type="text"   value="<%=sc_engg%>" min="1" max="100" class="form-control" readonly="">
                                        </div>
                                            <div class="form-group">
                                                <label>Accessory Change Remarks</label>
                                                <input type="text" name="accory_change_remark"  value="<%=accory_change_remark%>" min="1" max="1000" class="form-control" readonly="">
                                        </div>
                                            
                                               <div class="form-group">
                                                <label>Hardware Changes</label>
                                                <input type="text" name="hrdwr_chang" value="<%=hrdwr_chang%>" class="form-control" min="1" max="100" readonly="">
                                                    
                                            </div>
                                                <div class="form-group">
                                                <label>Hardware Change Remarks</label>
                                                <input type="text" name="hrdwr_chang_remark" value="<%=hrdwr_chang_remark%>" class="form-control" min="1" max="1000" readonly="">
                                                    
                                            </div>
                                           <div class="form-group">
                                                <label>Service Manual Update</label>
                                                <input type="text" name="service_manul_updt"  value="<%=service_manul_updt%>" min="1" max="100" class="form-control" readonly="">
                                                </div>
                                            </div>
                                        <div class="col-lg-4">
                                           
                                            
                                                <div class="form-group">
                                                <label>Technical Remarks</label>
                                                 <input type="text" name="tech_remarks"  value="<%=tech_remarks%>" min="1" max="1000" class="form-control" readonly="">
                                        </div> 
                                            <div class="form-group">
                                                <label>TS Team Verification Date</label>
                                                 <input type="text" name="ts_team_verification_date"  value="<%=ts_team_verification_date%>" min="1" max="100" class="form-control" readonly="">
                                        </div> 
                                                     <%
                                                         }
                                                         %>
                                            
                                                         
                                                         
                                            <%
                                                if(role.equalsIgnoreCase("productsupport")&&status.equalsIgnoreCase("pending"))
                                                {
                                                   %>
                                                   
                                                   <%
                                                       if(ps_engg==""||ps_engg.equalsIgnoreCase(""))
                                                       {
                                                         %>
                                                         <div class="form-group">
                                                <label>PS Engineer</label>
                                                <select name="ps_engg" class="form-control" required="">
                                                    <option value="">Select Engineer</option>
                                                    <%
                                                        List list=EmployeeDao.getPSEngg();
                                                        Iterator itr=list.iterator();
                                                        while(itr.hasNext())
                                                        {
                                                    %><option><%=itr.next()%></option><%
                                                        }
                                                        %>
                                                </select>
                                        </div>
                                                         <%
                                                       }else{
                                                          %>
                                                          <div class="form-group">
                                                <label>PS Engineer</label>
                                                <input type="text" name="ps_engg" value="<%=ps_engg%>" class="form-control" readonly="" >
                                                
                                        </div>
                                                          <%
                                                       }
                                                       %>
                                                   
                                                 <%
                                                       if(sftwr_chang_remark==""||sftwr_chang_remark.equalsIgnoreCase(""))
                                                       {
                                                         %>
                                                         <div class="form-group">
                                                <label>Software Change Remarks</label>
                                                <input type="text" name="sftwr_chang_remark" class="form-control" min="1" max="1000" required="" >
                                                    
                                            </div>
                                                         <%
                                                       }else{
                                                          %>
                                                          <div class="form-group">
                                                <label>Software Change Remarks</label>
                                                <input type="text" name="sftwr_chang_remark" value="<%=sftwr_chang_remark%>" class="form-control" readonly="">
                                                    
                                            </div>
                                                          <%
                                                       }
                                                       %>
                                            
                                             <%
                                                       if(cnr_tech_ref_no.equalsIgnoreCase("")||cnr_tech_ref_no.equalsIgnoreCase(null))
                                                       {
                                                         %>
                                                         <div class="form-group">
                                                <label> CNR/Technews Circulation </label>
                                                <select name="cnr_tech_ref_no" class="form-control" id="cnr" required="" onchange="fun()">
                                                    <option value="">Select Circulation</option>
                                                    <option value="Sale">Sale</option>
                                                    <option value="Servcie">Servcie</option>
                                                    <option value="Export">Export</option>
                                                    <option value="Internal">Internal</option>
                                                    <option value="Sales&Service&Export">Sales & Service & Export</option>
                                                    <option value="Sales & Service">Sales & Service</option>
                                                    <option value="Not Required">Not Required</option>
                                                </select>
                                        </div>
                                                         <%
                                                       }else{
                                                          %>
                                                          <div class="form-group">
                                                <label> CNR/Technews Circulation </label>
                                                <input type="text" name="cnr_tech_ref_no"  value="<%=cnr_tech_ref_no%>" min="1" max="100" class="form-control" readonly="" >
                                        </div>
                                                          <%
                                                       }
                                                       %>
                                            
                                             <%
                                                       if(cnr_ref_no==""||cnr_ref_no.equalsIgnoreCase(""))
                                                       {
                                                         %>
                                                         <div class="form-group">
                                                <label> CNR/Technews Reference No. </label>
                                                <input type="text" name="cnr_ref_no" id="cnrref"  value="" min="1" max="100" class="form-control" required="" >
                                        </div>
                                                         <%
                                                       }else{
                                                          %>
                                                          <div class="form-group">
                                                <label> CNR/Technews Reference No. </label>
                                                <input type="text" name="cnr_ref_no"  value="<%=cnr_ref_no%>" min="1" max="100" class="form-control" readonly="">
                                        </div>
                                                          <%
                                                       }
                                                       %>
                                             
                                              <%
                                                    
                                                       if(cnr_relese_date==""||cnr_relese_date.equalsIgnoreCase(""))
                                                       {
                                                         %>
                                                         <div class="form-group">
                                                <label> CNR/Technews Release Date</label>
                                                <input type="text" name="cnr_relese_date" id="datepicker-1"  value="" min="1" max="100" class="form-control" required="" >
                                        </div> 
                                                         <%
                                                       }else{
                                                          %>
                                                          <div class="form-group">
                                                <label> CNR/Technews Release Date</label>
                                                <input type="text" name="cnr_relese_date"   value="<%=cnr_relese_date%>" class="form-control" readonly="">
                                        </div> 
                                                          <%
                                                       }
                                                       %>
                                               
                                                
                                                <%
                                                       if(ps_team_verification_date==""||ps_team_verification_date.equalsIgnoreCase(""))
                                                       {
                                                         %>
                                                         <div class="form-group">
                                                <label>PS Team Verification Date</label>
                                                <input type="text" name="ps_team_verification_date" id="datepicker-2"  value="" min="1" max="100" class="form-control" required="" >
                                        </div>
                                                         <%
                                                       }else{
                                                          %>
                                                          <div class="form-group">
                                                <label>PS Team Verification Date</label>
                                                <input type="text" name="ps_team_verification_date"   value="<%=ps_team_verification_date%>" class="form-control" readonly="">
                                        </div>
                                                          <%
                                                       }
                                                       %>  
                                            
                                                   <%
                                                }else{
                                                %>
                                                <div class="form-group">
                                                <label>PS Engineer</label>
                                                <input type="text" name="ps_engg" value="<%=ps_engg%>" class="form-control" readonly="" >
                                                
                                        </div>
                                            <div class="form-group">
                                                <label>Software Change Remarks</label>
                                                <input type="text" name="sftwr_chang_remark" value="<%=sftwr_chang_remark%>" class="form-control" min="1" max="1000" readonly="" >
                                                    
                                            </div>
                                            <div class="form-group">
                                                <label> CNR/Technews Circulation </label>
                                                <input type="text" name="cnr_tech_ref_no"  value="<%=cnr_tech_ref_no%>" min="1" max="100" class="form-control" readonly="" >
                                        </div>
                                             <div class="form-group">
                                                <label> CNR/Technews Reference No. </label>
                                                <input type="text" name="cnr_ref_no"  value="<%=cnr_ref_no%>" min="1" max="100" class="form-control" readonly="" >
                                        </div>
                                              <div class="form-group">
                                                <label> CNR/Technews Release Date</label>
                                                <input type="text" name="cnr_relese_date"  value="<%=cnr_relese_date%>" min="1" max="100" class="form-control" readonly="" >
                                        </div>  
                                                
                                                 
                                            <div class="form-group">
                                                <label>PS Team Verification Date</label>
                                               <input type="text" name="ps_team_verification_date"  value="<%=ps_team_verification_date%>" min="1" max="100" class="form-control" readonly="" >
                                        </div>
                                                <%
                                                    }
                                                    %>
                                            
                                            <div class="form-group">
                                                <label>Approved Date</label>
                                               <input type="text" name="approved_date"  value="<%=approved_date%>" min="1" max="100" class="form-control" readonly="">
                                        </div>
                                               <%
                                                   if(status.equalsIgnoreCase("pending"))
                                                   {
                                                      %>
                                                      <div class="form-group">
                                                <label>Status</label>
                                                <input type="text" name="status" value="<%=status%>" class="form-control" readonly="">
                                                </div>
                                            <div>&nbsp;</div>
                                          
                                                 <div align="center">
                                                <center><button type="submit" class="btn btn-success">SAVE</button></center>
                                            </div>   
                                                      <%
                                                   }else{
                                                      %>
                                                      <div class="form-group">
                                                <label>Status </label>
                                                <input type="text" name="status" id="datepicker-5" minlength="1" maxlength="50" value="<%=status%>" class="form-control" readonly="">
                                        </div>
                                        <div> &nbsp;</div>
                                        <div class="form-group">
                                            <center><label  style="font-size: medium;font-weight: 800; color: #ff4015">Service Closed</label></center>
                                        </div>
                                                      <%
                                                   }
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
    function fun()
    {
       var a=document.getElementById("cnr").value;
       var cnrref=document.getElementById("cnrref");
       var cnrdate=document.getElementById("datepicker-1")
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
       }
    }
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
    </script>
</body>

</html>
