<%-- 
    Document   : nonSaleUpdate
    Created on : Oct 9, 2017, 4:19:23 PM
    Author     : ShineLoGics
--%>


<%@page import="com.schillerindiaservices.bean.Sparemaster"%>
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
<%@page import="com.schillerindiaservices.bean.Emploeemaster"%>

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
String namee=(String)session.getAttribute("logname");
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
  <%@include file="ServiceCoorDashBoard.jsp" %>
  <%
      }
  %>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Spares Request Update </h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                           Spares Request 
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <form role="form" action="spares_service_commercial_controller" method="post">
                                    <div class="col-lg-4">
                                    <%
                                        int id=0;
                                    String division="";
                                    String sc_engg="";
                                    String entry_date="";
                                    String partnumber="";
                                    String def_Mod_Brd_name="";
                                    String reason="";
                                    String reference="";
                                    String gir_no="";
                                    String supplier="";
                                    String model="";
                                    String issued_by="";
                                    String returnable_status="";
                                    String remarks="";
                                    String returned_date="";
                                    String final_status="";
                                    String sc_name="";
                                    String qty="";
                                   /*  String replace_ship_date="";
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
                                    String fqc_remaks=""; */
                                    Integer engineerId=0;
                                    
                                    String divid="";
                                    int lid=Integer.parseInt(session.getAttribute("loguserid").toString());
                                            division=EmployeeDao.getempdivision(lid);
                                            String eid=String.valueOf(lid);
                                            
                                            divid=ProductDao.getDiviName(division);
                                      // sc_engg=EmployeeDao.geteName(eid);
                                       System.out.println("the sc engg id"+sc_engg+" "+lid);
                                            if((request.getAttribute("nonsale"))!=null)
                                            {
                                            Sparemaster cr=(Sparemaster) request.getAttribute("nonsale");
                                            id=cr.getId();
                                            division=cr.getDivision();
                                            sc_engg=cr.getSc_engg();
                                            System.out.println("the sc engg id2222"+sc_engg+" "+lid);
                                            entry_date=cr.getEntryDate();
                                            supplier=cr.getSupplier();
                                            partnumber=cr.getPartNumber();
                                            def_Mod_Brd_name=cr.getDef_Mod_Brd_name();
                                            reason=cr.getReason();
                                            reference=cr.getReference();
                                            gir_no=cr.getGir_no();
                                            supplier=cr.getSupplier();
                                            // engineerId=d.getEngineerId();
                                             model=cr.getModel();
                                             issued_by=cr.getIssued_by();
                                             returnable_status=cr.getReturnable_status();
                                             remarks=cr.getRemarks();
                                             returned_date=cr.getReturned_date();
                                             final_status=cr.getFinalStatus();
                                             qty=cr.getQty();
                                             pageContext.setAttribute("stat", final_status);
                                            }
                                          /*  branch=BranchDao.getbName(br_name);  
                                            engineer=EmployeeDao.geteName(eng_name);
                                            dealer=DealerDao.getdName(del_name); 
                                            model=ModelDao.getModelname(mod_name);
                                            supplier=SupplierDao.getSupName(sup_name,mod_name); */
                                            sc_name=EmployeeDao.geteName(sc_engg);
                                         //   System.out.println("the sc engg id3333"+sc_name+" "+lid);
                                    %>

                                    <input type="hidden" name="id" class="form-control" value="<%=id%>">
                                    <%
                                        if(role.equalsIgnoreCase("fqc")||role.equalsIgnoreCase("admin"))
                                        {
                                    %>
                                        <div class="form-group">
                                                <label> Division</label>
                                                <input type="text" name="division"  value="<%=division%>" minlength="1" maxlength="40" class="form-control" readonly="" required="">
                                        </div>
                                                <%
                                                    }
                                                    else
{
                                                    %>
                                                    <div class="form-group">
                                                <label> Division</label>
                                                <input type="text" name="division" id="division" value="<%=division%>" onload="fetch_scEngg();" class="form-control" readonly="" required="">
                                        </div>
                                                    <%
                                                        }
                                                        %>
                                                         <div class="form-group">
                                                <label>Entry date </label>
                                                <input type="text" name="fqc_inward_date" id="datepicker-1" value="<%=entry_date%>" class="form-control" required="" readonly="">
                                        </div>
                                       <%--  <div class="form-group">
                                                <label>Unit Details</label>
                                                <input type="text" name="unit_details"  value="<%=unit_details%>" class="form-control" readonly="">
                                            </div> --%>
                                        <%-- <div class="form-group">
                                                <label>FQC Inward date </label>
                                                <input type="text" name="fqc_in_date"  value="<%=fqc_inward_date%>" class="form-control" readonly="">
                                        </div> --%>
                                          <% 
                                        if(role.equalsIgnoreCase("engineer"))
                        {
                                        	if(sc_engg.isEmpty()||sc_engg.length()==0){
                                        		%>
                                        		
                                        		  <div class="form-group">
                                                <label>SC Engineer </label>
                                                <select name="sc_engg" class="form-control" id="myScEngg"  required="">
                                                   <option value="<%=lid%>"><%=namee%></option>
                                                    
                                                </select>
                                               
                                        </div>
                                        		
                                        		<% 
                                        	}else{
                                        		%>
                                        		 <div class="form-group">
                                                <label>SC Engineer </label>
                                                <input type="hidden" name="sc_engg"  value="<%=sc_engg%>">
                                                <input type="text"   value="<%=sc_name%>" min="1" max="100" class="form-control" readonly="">
                                        </div>
                                        		
                                        		<% 
                                        	}
                            %>

                                      
 <%
     }else{
    	 %> 
    	  <div class="form-group">
                                                <label>SC Engineer </label>
                                                <input type="hidden" name="sc_engg"  value="<%=sc_engg%>">
                                                <input type="text"   value="<%=sc_name%>" min="1" max="100" class="form-control" readonly="">
                                        </div>
    	 
    	 
    	 <%
     }
         %>        
                                        <%-- <div class="form-group">
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
                                                 --%>
                                                 <div class="form-group">
                                                <label>Supplier</label>
                                                <input type="text" name="supplier" value="<%=supplier%>" class="form-control" readonly="">
                                            </div>
                                            <div class="form-group">
                                                <label>Model</label>
                                        <input type="text" name="model"  value="<%=model%>" class="form-control" readonly="">
                                             </div>
                                             
                                              <div class="form-group">
                                                <label>Part number</label>
                                                 <input type="text" name="model_sn" value="<%=partnumber %>" class="form-control"  required="" readonly="">
                                            </div>
                                             <div class="form-group">
                                                <label>DEF MOD/BRD NAME</label>
                                                 <input type="text" name="unit_config" value="<%=def_Mod_Brd_name %>" minlength="1" maxlength="40" class="form-control"  required="" readonly="">
                                                </div>
                                    </div>
                                        <div class="col-lg-4">
                                            
                                            
                                            <div class="form-group">
                                                <label>Reason</label>
                                                <input type="text" name="reason"  value="<%=reason%>" class="form-control" readonly="">
                                            </div>
                                            <div class="form-group">
                                                <label>Reference</label>
                                                <input type="text" name="cust_name"  value="<%=reference%>" class="form-control" readonly="">
                                                </div>
                                                <%
                                       if(role.equalsIgnoreCase("engineer")&&(gir_no.equalsIgnoreCase("null")||gir_no.isEmpty()))
                                       {
                                       %>
                                                <div class="form-group">
                                                <label>Gir Number</label>
                                                <input type="text" name="gir_number"  value="<%=gir_no%>" class="form-control" readonly="">
                                        </div>
                                              <div class="form-group">
                                                <label>Issued By</label>
                                                <input type="text" name="issued_by"  value="<%=issued_by%>" class="form-control" readonly="">
                                        </div>
                                       
                                         <div class="form-group">
                                                <label>Returnable Status</label>
                                                <select class="form-control" value="<%=returnable_status%>" name="return_status" required="" readonly="">
                                                   <option value="1">Yes</option>
                                                    <option value="0">No</option>
                                                   
                                                     
                                                </select>
                                        </div>
                                         <div class="form-group">
                                                <label>Remarks</label>
                                                <input type="text" name="fqc_observation" minlength="1" maxlength="250" value="<%=gir_no%>" class="form-control" required="" readonly="">
                                        </div>
                                         <div class="form-group">
                                                <label>Returned Date</label>
                                                <input type="text" name="defect_unit_recv_date"  id="datepicker-3" minlength="1" maxlength="50" value="<%=returned_date%>" class="form-control" required="" readonly="">
                                        </div>
                                          <% }else if(role.equalsIgnoreCase("ServiceCoordinator")&&(gir_no.equalsIgnoreCase("null")||gir_no.isEmpty())){
                                        	  
                                         
                                               
                                          
                                          %>
                                           <div class="form-group">
                                                <label>Gir Number</label>
                                                <input type="text" name="gir_number"  value="<%=gir_no%>" class="form-control"  required="" >
                                        </div>
                                         <div class="form-group">
                                                <label>Quantity</label>
                                                <input type="text" name="qty"  value="<%=qty%>" class="form-control"  required="" >
                                        </div>
                                              <div class="form-group">
                                                <label>Issued By</label>
                                                <input type="text" name="issued_by"  value="<%=issued_by%>" class="form-control"  required="" >
                                        </div>
                                           <div class="form-group">
                                                <label>Returnable Status</label>
                                                <select class="form-control"  name="return_status" value="<%=returnable_status%>" required="" readonly="">
                                                   <option value="1">Yes</option>
                                                    <option value="0">No</option>
                                                   
                                                     
                                                </select>
                                        </div>
                                         <div class="form-group">
                                                <label>Remarks</label>
                                                <input type="text" name="fqc_observation" minlength="1" maxlength="250" value="<%=remarks%>" class="form-control" required="" readonly="">
                                        </div>
                                         <div class="form-group">
                                                <label>Returned Date</label>
                                                <input type="text" name="defect_unit_recv_date"  id="datepicker-3" minlength="1" maxlength="50" value="<%=returned_date%>" class="form-control" required="" readonly="">
                                        </div>
                                           <%
                                          }else if(role.equalsIgnoreCase("engineer")){
                                        	  %>
                                        	   <div class="form-group">
                                                <label>Gir Number</label>
                                                <input type="text" name="gir_number"  value="<%=gir_no%>" class="form-control"  readonly="">
                                        </div>
                                        <div class="form-group">
                                                <label>Quantity</label>
                                                <input type="text" name="qty"  value="<%=qty%>" class="form-control"  readonly="">
                                        </div>
                                              <div class="form-group">
                                                <label>Issued By</label>
                                                <input type="text" name="issued_by"  value="<%=issued_by%>" class="form-control" readonly="">
                                        </div>
                                           <div class="form-group">
                                                <label>Returnable Status</label>
                                                <select class="form-control"  name="return_status" value="<%=returnable_status%>" required="" >
                                                   <option value="1">Yes</option>
                                                    <option value="0">No</option>
                                                   
                                                     
                                                </select>
                                        </div>
                                         <div class="form-group">
                                                <label>Remarks</label>
                                                <input type="text" name="fqc_observation" minlength="1" maxlength="250" value="<%=remarks%>" class="form-control" required="" >
                                        </div>
                                         <div class="form-group">
                                                <label>Returned Date</label>
                                                <input type="text" name="defect_unit_recv_date"  id="datepicker-3" minlength="1" maxlength="50" value="<%=returned_date%>" class="form-control" required="" >
                                        </div>
                                        	  
                                        	  <% 
                                          }else{
                                        	  %>
                                        	  
                                        	   <div class="form-group">
                                                <label>Gir Number</label>
                                                <input type="text" name="gir_number"  value="<%=gir_no%>" class="form-control"  readonly="">
                                        </div>
                                         <div class="form-group">
                                                <label>Quantity</label>
                                                <input type="text" name="qty"  value="<%=qty%>" class="form-control"  readonly="">
                                        </div>
                                              <div class="form-group">
                                                <label>Issued By</label>
                                                <input type="text" name="issued_by"  value="<%=issued_by%>" class="form-control" readonly="">
                                        </div>
                                           <div class="form-group">
                                                <label>Returnable Status</label>
                                                <select class="form-control"  name="return_status" value="<%=returnable_status%>" required="" readonly="">
                                                   <option value="1">Yes</option>
                                                    <option value="0">No</option>
                                                   
                                                     
                                                </select>
                                        </div>
                                         <div class="form-group">
                                                <label>Remarks</label>
                                                <input type="text" name="fqc_observation" minlength="1" maxlength="250" value="<%=remarks%>" class="form-control" required="" readonly="">
                                        </div>
                                         <div class="form-group">
                                                <label>Returned Date</label>
                                                <input type="text" name="defect_unit_recv_date"  id="datepicker-3" minlength="1" maxlength="50" value="<%=returned_date%>" class="form-control" required="" readonly="">
                                        </div>
                                        	  
                                        	  
                                        	  
                                        	  
                                        	  
                                        	  <%
                                          }
                                          %> 
                                          
                                          
                                            <%-- <div class="form-group">
                                                <label>Replaced Unit S/N</label>
                                                <input type="text" name="repalce_unit_sn"  value="<%=replace_unit%>" class="form-control" readonly="">
                                        </div>
                                             <div class="form-group">
                                                <label>Replacement Ship Date</label>
                                                <input type="text" name="replace_ship_date"  value="<%=replace_ship_date%>" class="form-control" readonly="">
                                        </div>
                                            <div class="form-group">
                                                <label>Defective unit received  Date</label>
                                                <input type="text" name="def_unit_recv_date"  value="<%=defect_unit_recv_date%>" class="form-control" readonly="">
                                        </div>
                                        <div class="form-group">
                                                <label>FQC Observation</label>
                                                <input type="text" name="fqc_observ"  value="<%=fqc_observation%>" class="form-control" readonly="">
                                        </div> --%>
                                             
                                                    
                                            </div>
                                        <div class="col-lg-4">
                                            <%--  <%
                                                                if(role.equalsIgnoreCase("fqc"))
                                                                {
                                                   %>
                                                   <div class="form-group">
                                                <label>SC Inward Date</label>
                                                <input type="text" name="sc_in_date"  minlength="1" maxlength="50" value="<%=sc_in_date%>" class="form-control" readonly="">
                                        </div> 
                                                <%
                                                    }else{
                                                    if(sc_in_date==""||sc_in_date.equals(""))
                                                    {
                                                    %>
                                                    <div class="form-group">
                                                <label>SC Inward Date</label>
                                                <input type="text" name="sc_in_date" id="datepicker-4" minlength="1" maxlength="50" value="<%=sc_in_date%>" class="form-control" placeholder="" >
                                        </div> 
                                                    <%
                                                        }else{
                                                        %>
                                                        <div class="form-group">
                                                <label>SC Inward Date</label>
                                                <input type="text" name="sc_in_date"  minlength="1" maxlength="50" value="<%=sc_in_date%>" class="form-control" readonly="">
                                        </div> 
                                                        <%
                                                            }}
                                                            %>
                                                        
                                                             <%
                                                                if(role.equalsIgnoreCase("fqc"))
                                                                {
                                                   %>
                                                   <div class="form-group">
                                                <label>SC Observation</label>
                                                <input type="text" name="sc_observation" minlength="1" maxlength="250" value="<%=sc_observation%>" class="form-control" readonly="">
                                        </div>
                                                <%
                                                    }else{
                                                    if(sc_observation==""||sc_observation.equals(""))
                                                    {
                                                    %>
                                                    <div class="form-group">
                                                <label>SC Observation</label>
                                                <input type="text" name="sc_observation" minlength="1" maxlength="250" value="<%=sc_observation%>" class="form-control" placeholder="" >
                                        </div>
                                                    <%
                                                        }else{
                                                        %>
                                                         <div class="form-group">
                                                <label>SC Observation</label>
                                                <input type="text" name="sc_observation" minlength="1" maxlength="250" value="<%=sc_observation%>" class="form-control" readonly="" >
                                        </div>
                                                        <%
                                                            }}
                                                            %>
                                            
                                                            <%
                                                                if(role.equalsIgnoreCase("fqc"))
                                                                {
                                                   %>
                                                   <div class="form-group">
                                                <label>Required Parts</label>
                                                <input type="text" name="req_parts" minlength="1" maxlength="250" value="<%=req_parts%>" class="form-control" readonly="">
                                        </div>
                                                       <%
                                                           }else{
                                                    if(req_parts==""||req_parts.equals(""))
                                                    {
                                                    %>
                                                    <div class="form-group">
                                                <label>Required Parts</label>
                                                <input type="text" name="req_parts" minlength="1" maxlength="250" value="<%=req_parts%>" class="form-control" placeholder="" >
                                        </div>
                                                    <%
                                                        }else{
                                                        %>
                                                         <div class="form-group">
                                                <label>Required Parts</label>
                                                <input type="text" name="req_parts" minlength="1" maxlength="250" value="<%=req_parts%>" class="form-control" readonly="" >
                                        </div>
                                                        <%
                                                            }}
                                                            %>     
                                                            
                                                              <div class="form-group">
                                                <label>Root Cause</label>
                                                <input type="text" name="root_cause" minlength="1" maxlength="250" value="<%=root_cause%>" class="form-control" >
                                        </div>  
                                                            
                                            <div class="form-group">
                                                <label>SC Action Plan</label>
                                                <input type="text" name="action_plan" minlength="1" maxlength="550" value="<%=action_plan%>" class="form-control">
                                        </div>
                                           
                                                            <%
                                                                if(role.equalsIgnoreCase("fqc"))
                                                                {
                                                   %>
                                                   <div class="form-group">
                                                <label>Tentative Date </label>
                                                <input type="text" name="ship_date_fqc"  minlength="1" maxlength="50" value="<%=ship_date_fqc%>" class="form-control" readonly="">
                                        </div>
                                                   <%
                                                       }else{
                                                                    if(ship_date_fqc==""||ship_date_fqc.equals(""))
                                                    {
                                                    %>
                                                     <div class="form-group">
                                                <label>Tentative Date </label>
                                                <input type="text" name="ship_date_fqc" id="datepicker-5" minlength="1" maxlength="50" value="<%=ship_date_fqc%>" class="form-control" placeholder="" >
                                        </div>
                                                    <%
                                                        }else{
                                                        %>
                                                         <div class="form-group">
                                                <label>Tentative Date</label>
                                                <input type="text" name="ship_date_fqc"  minlength="1" maxlength="50" value="<%=ship_date_fqc%>" class="form-control" readonly="">
                                        </div>
                                                        <%
                                                            }}
                                                            %>   
                                                            <%
                                                                if(role.equalsIgnoreCase("fqc"))
                                                                {
                                                                %>
                                                                 <div class="form-group">
                                                <label>Ship Date to FQC </label>
                                                <input type="text" name="ship_date_fqc2"  minlength="1" maxlength="550" value="<%=ship_date%>" class="form-control" readonly="" >
                                        </div>
                                                            <%
                                                                }
                                                                else{
                                                                if(ship_date==""||ship_date.equalsIgnoreCase(""))
                                                                {
                                                                %>
                                            <div class="form-group">
                                                <label>Ship Date to FQC </label>
                                                <input type="text" name="ship_date_fqc2" id="datepicker-6" minlength="1" maxlength="550" value="<%=ship_date%>" class="form-control" >
                                        </div>
                                                <%
                                                    }else{
                                                    %>
                                                    <div class="form-group">
                                                <label>Ship Date to FQC </label>
                                                <input type="text" name="ship_date_fqc2"  minlength="1" maxlength="550" value="<%=ship_date%>" class="form-control" readonly="" >
                                        </div>
                                                    <%
                                                        }}
                                                        %> --%>
                                              
                                                           
                                                          <%  
                                                            
                                     if(role.equalsIgnoreCase("engineer")||gir_no.equalsIgnoreCase("null")||gir_no.isEmpty()){
                                                    
                                                            %>
                                                             <div class="form-group">
                                                <label>Status</label>
                                                <input type="text" name="status" value="<%=final_status%>" class="form-control" readonly="">
                                                             </div>
                                                <%
                                                    if(final_status.equalsIgnoreCase("pending"))
                                                    {
                                                    %>
                                                    <div align="center">
                                                <center><button type="submit" class="btn btn-success">SAVE</button></center>
                                            </div>
                                                    <%
                                                        }else
                                                            {
                                                        %>
                                                        <div> &nbsp;</div>
                                        <div class="form-group">
                                            <center><label  style="font-size: medium;font-weight: 800; color: #ff4015">Service Closed</label></center>
                                        </div>
                                                            <%
                                                           } }
                                                            else  
                                                        {
                                                    if(role.equalsIgnoreCase("ServiceCoordinator")&&final_status.equalsIgnoreCase("pending")&&(returned_date.isEmpty()||returned_date.equalsIgnoreCase("null")||returned_date.equals(null)))
                                                    {
                                                    %>
                                                     <div class="form-group">
                                                <label>Status</label>
                                                <select class="form-control"  name="status" required="" readonly="">
                                                    <option value="">Select Type</option> 
                                                    <option value="Completed"${stat=='Completed'?'selected':''}>Completed</option>
                                                    <option value="Pending"${stat=='Pending'?'selected':''}>Pending</option>
                                                     
                                                </select></div>
                                                    <div align="center">
                                                <center><button type="submit" class="btn btn-success">SAVE</button></center>
                                            </div>
                                                    <%
                                                        }
                                                       else if(final_status.equalsIgnoreCase("Completed")){
                                                        %>
                                                         <div class="form-group">
                                                <label>Status </label>
                                                <input type="text" name="status"  minlength="1" maxlength="50" value="<%=final_status%>" class="form-control" readonly="">
                                        </div>
                                        <div> &nbsp;</div>
                                        <div class="form-group">
                                            <center><label  style="font-size: medium;font-weight: 800; color: #ff4015">Service Closed</label></center>
                                        </div>
                                                        <%
                                                            }else{
                                                            	%>
                                                            	<div class="form-group">
                                                <label>Status</label>
                                                <select class="form-control"  name="status" required="" >
                                                    <option value="">Select Type</option> 
                                                    <option value="Completed"${stat=='Completed'?'selected':''}>Completed</option>
                                                    <option value="Pending"${stat=='Pending'?'selected':''}>Pending</option>
                                                     
                                                </select></div>
                                                    <div align="center">
                                                <center><button type="submit" class="btn btn-success">SAVE</button></center>
                                            </div>
                                                            	
                                                            	<% 
                                                            }
                                                                }
                                                            %>   
                                             
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
    function fetch_engineer()
    {  
    	//alert("insidee");
    	var request=new XMLHttpRequest();
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
    
    window.onload=function(){
  	 // alert("ss");
  	fetch_scEngg();	
    	//fetch_engineer();
  	  //changefn2();
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
                dateFormat: 'yy-mm-dd' });
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

