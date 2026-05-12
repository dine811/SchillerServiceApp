<%--<%@ page errorPage="error.jsp" %>--%> 
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.lang.*"%>
<%@page import="java.util.*"%>
<%@page import="com.schillerindiaservices.Dao.ReportDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.schillerindiaservices.Dao.ProductDao"%>
<%@page import="com.schillerindiaservices.Dao.BranchDao"%>
<%@page import="com.schillerindiaservices.Dao.EmployeeDao"%>
<%@page import="com.schillerindiaservices.Dao.RegionDao"%>
<%@page import="com.schillerindiaservices.Dao.DealerDao"%>
<%@page import="com.schillerindiaservices.Dao.ModelDao"%>
<%@page import="com.schillerindiaservices.Dao.SupplierDao"%>
<%@page import="com.schillerindiaservices.Dao.ServiceDao"%>
<%@page import="com.schillerindiaservices.bean.service_master"%>
<%@page import="com.schillerindiaservices.bean.Suppliermaster"%>
<%@page import="com.schillerindiaservices.bean.Model"%>
<%@page import="com.schillerindiaservices.bean.Dealermaster"%>
<%@page import="com.schillerindiaservices.bean.Branch"%>
<%@page import="com.schillerindiaservices.bean.Dropdownmaster"%>
<%@page import="com.schillerindiaservices.bean.Emploeemaster"%>
<%@page import="java.sql.Date"%>
<%@page import="com.schillerindiaservices.Dao.DropdownvaluesDao"%>
<%@page import="com.schillerindiaservices.Dao.DropdownDao"%>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SchillerIndia</title>

    <!-- Bootstrap Core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="dist/css/sb-admin-2.css" rel="stylesheet">
    <link href="dist/css/datetextremover.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link rel="icon" type="image/png" href="img/logo.png"/>
    <link href = "dist/css/jquery-ui.css" rel = "stylesheet">
      

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
        session=request.getSession();
            String role=(String)session.getAttribute("logrole");
            if("admin".equalsIgnoreCase(role))
            {   
                %><%@include file="admindashboard.jsp" %><%
            }
else if(role.equalsIgnoreCase("ViceChancellor"))
{
%>
<%@include file="VPDashboard.jsp" %>
<%
}
            else
            {   
                %><%@include file="engineerdashboard.jsp" %><%
            }
        %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Add Service</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Enter the details of the Service 
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <form role="form" name="vinform" action="ServiceController" method="post" >
                                <div class="col-lg-4">
                                    <%
                                                        int id=0;
                                                        int divid=0;
                                                        String division="";
                                                        String scRefNo="";
                                                        String entryDate="";
                                                        String scEngnr="";
                                                        int raEngnr=0;
                                                        String frnNo="";
                                                        String frnDate="";
                                                        String docketNo="";
                                                        String despatchDate="";
                                                        String serCommInwardDate="";
                                                        String serCentreReceivedDate="";
                                                        String stkCust="";
                                                        String region="";
                                                        String branch="";
                                                        Integer engineerId=0;
                                                        String dealerName="";
                                                        String custName="";
                                                        String supplierName="";
                                                        String productModel="";
                                                        String unitSlno="";
                                                        String dod="";
                                                        String unitStatus="";
                                                        String ModBrdName="";
                                                        String defModBrdName="";
                                                        String defType="";
                                                        String typeOfAcc="";
                                                        String defPartSn="";
                                                        String defGirNo="";
                                                        String repType="";
                                                        String repGirNo="";
                                                        String defUnitGirNo="";
                                                        String fieldRemarks="";
                                                        String componentsUsedforRepair="";
                                                        String repairedBrdStkDate="";
                                                        String finalRemarks="";
                                                        String typeOfWork="";
                                                        String shipDtFrmSerCntr="";
                                                        String dispAdvNo="";
                                                        String shipDateFromCommercial="";
                                                        String dcNo="";
                                                        String repairedBrdAdvNo="";
                                                        String admindiv="";
                                                        String done="";
                                                        String technicalRemarks="";
                                                        String comrclDtlInvRmrk="";
                                                        String model_config="";
                                                        String part_number="";
                                                        String report_type="";
                                                        String destination="";
                                                        String compatible_models="";
                                                        
                                            
                                            if(request.getAttribute("editablerecord")!=null)
                                            {
                                                service_master d=(service_master)request.getAttribute("editablerecord");
                                                        scRefNo=d.getScRefNo();
                                                        entryDate=d.getEntryDate();
                                                        scEngnr=d.getScEngnr().toString();
                                                        raEngnr=d.getRaEngnr();
                                                        frnNo=d.getFrnNo();
                                                        frnDate= d.getFrnDate();
                                                        docketNo=d.getDocketNo();
                                                        despatchDate= d.getDespatchDate();
                                                        serCommInwardDate= d.getSerCommInwardDate();
                                                        serCentreReceivedDate= d.getSerCentreReceivedDate();
                                                        stkCust=d.getStkCust();
                                                        region=d.getRegion();
                                                        branch=d.getBranch();
                                                        engineerId=d.getEngineerId();
                                                        dealerName=d.getDealerName();
                                                        custName=d.getCustName();
                                                        supplierName=d.getSupplierName();
                                                        productModel=d.getProductModel();
                                                        unitSlno=d.getUnitSlno();
                                                        dod= d.getDod();
                                                        unitStatus=d.getUnitStatus();
                                                        ModBrdName=d.getModBrdName();
                                                        defModBrdName=d.getDefModBrdName();
                                                        defType=d.getDefType();
                                                        typeOfAcc=d.getTypeOfAcc();
                                                        defPartSn=d.getDefPartSn();
                                                        defGirNo=d.getDefGirNo();
                                                        repType=d.getRepType();
                                                        repGirNo=d.getRepGirNo();
                                                        defUnitGirNo=d.getDefUnitGirNo();
                                                        fieldRemarks=d.getFieldRemarks();
                                                        componentsUsedforRepair=d.getComponentsUsedforRepair();
                                                        repairedBrdStkDate=d.getRepairedBrdStkDate();
                                                        finalRemarks=d.getFinalRemarks();
                                                        compatible_models=d.getCompatibleModels();
                                                        
                                                        if(d.getTypeOfWork()!=null&&d.getTypeOfWork().isEmpty()){
                                                        	typeOfWork=d.getTypeOfWork();
                                                        	 System.out.println("typr workk iss"+typeOfWork);
                                                        }
                                                       
                                                        shipDtFrmSerCntr= d.getShipDtFrmSerCntr();
                                                        dispAdvNo=d.getDispAdvNo();
                                                        shipDateFromCommercial=d.getShipDateFromCommercial();
                                                        dcNo=d.getDcNo();
                                                        repairedBrdAdvNo=d.getRepairedBrdAdvNo();
                                                        done=d.getDivisionName();
                                                        technicalRemarks=d.getTechnicalRemarks();
                                                        comrclDtlInvRmrk=d.getComrclDtlInvRmrk();
                                                        model_config=d.getModelConfig();
                                                        System.out.println("the mod config issss"+model_config);
                                                       part_number=d.getPart_Number();
                                                        report_type=d.getReport_type();
                                                        if(d.getDestination()!=null||d.getDestination()!=("")){
                                                        	destination=d.getDestination();
                                                        }
                                                      
                                            }
                                     %>
                                    <input type="hidden" name="id" value="0" class="form-control">
                                    
                                   <div class="form-group required">
                                                 <label class="control-label"> Division</label>
                                                    <input name="division" class="form-control"   value="<%=done%>" readonly>
                                             </div>
                          
                                    
                                        <div class="form-group required">
                                            <label class="control-label"> SC_REF_NO<span style="color:red">*</label>
                                            <input type="text" name="sc_ref_no" min="1" max="50" class="form-control" value="<%=scRefNo%>" required>
                                        </div>
                                           
                                        <div class="form-group required">
                                            <label class="control-label"> SC ENGINEER<span style="color:red" >*</label>
                                            <select class="form-control" name="sc_eng"   required readonly>
                                            <%
                                                        int eid=0;
                                                      String ename="";
                                             List<Emploeemaster> elist=EmployeeDao.getAllRecords();
                                            if(elist!=null)
                                            {
                                                Iterator eitr=elist.iterator();
                                                while(eitr.hasNext())
                                                {
                                                    Emploeemaster e=(Emploeemaster)eitr.next();
                                                    eid=e.getEmpId();
                                                    ename=e.getEmpName();
                                                    
                                                   if(scEngnr=="")
                                                   {
                                                       
                                                   %> 
                                                   <option value="<%=ename%>"><%=ename%></option><%
                                                   }
                                                     else
                                                   {  
                                                   %>  <option value="<%=eid%>" <% if(eid==Integer.parseInt(scEngnr)){out.print("selected='selected'");}%> ><%=ename%></option><%
                                                    }
                                                }  
                                             }
                                                %>
                                            </select>
                                        </div>
                                          
                                        <div class="form-group required">
                                            <label class="control-"> FRN NO<span style="color:red">*</label>
                                            <input type="text" min="1" max="50" name="frn_no" class="form-control" value="<%=frnNo%>"  required>
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>FRN DATE<span style="color:red">*</label>
                                            <input type="text" name="frn_date" id = "datepicker-3" class="form-control" value="<%=frnDate%>" required readonly>
                                        </div>
                                        
                                        
                                           
                                        <div class="form-group">
                                            <label>SER COMM INWARD DATE<span style="color:red">*</label>
                                            <input type="text" name="ser_comm_inward_date" id = "datepicker-1" class="form-control" value="<%=serCommInwardDate%>" required readonly>
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>RECEIVED DATE SER CENTRE<span style="color:red">*</label>
                                            <input type="text" name="receive_date_ser_centre" id = "datepicker-2" class="form-control" value="<%=serCentreReceivedDate%>" required readonly>
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>STK/CUST<span style="color:red">*</label>
                                            <select class="form-control" required  name="stk">
                                               <%
                                                    int dd1id=0;
                                                    String dd1name="";
                                                    List<Dropdownmaster> d1list=DropdownDao.getDDV1();
                                                     if(d1list!=null)
                                            {
                                                Iterator d1itr=d1list.iterator();
                                                while(d1itr.hasNext())
                                                {
                                                    Dropdownmaster dd=(Dropdownmaster)d1itr.next();
                                                   dd1id=dd.getId();
                                                   dd1name=dd.getDdvalue(); 
                                                   if(stkCust=="")
                                                   {
                                                   %>  <option value="<%=dd1id%>"><%=dd1name%></option><%
                                                   }
                                                     else
                                                   {  
                                                    %>  <option value="<%=dd1id%>" <% if(dd1id==Integer.parseInt(stkCust)){out.print("selected='selected'");}%> ><%=dd1name%></option><%
                                                   }
                                                }  
                                             }
                                                %>
                                            </select>
                                        </div>
                                            
                                        <div class="form-group">
                                            <label>SELECT REGION<span style="color:red">*</label>
                                            <input type="text"  class="form-control" name="regionselect" value="<%=region%>" readonly="">
                                        </div>
<!--                                        <div class="form-group">
                                            <label>SELECT REGION<span style="color:red">*</label>
                                            <select class="form-control" name="regionselect" onchange="fetch_dealer(); fetch_branch()" value="<%=region%>" required>
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
                                        </div>-->
                                           
                                        <div class="form-group">
                                            <label>BRANCH<span style="color:red">*</label>
                                            <select id="mybranch" class="form-control" name="branchselect" onchange="fetch_engineer()" value="<%=branch%>" required>
                                             <%
                                                      int brid=0;
                                                      String brname="";
                                                    List<Branch> brlist=BranchDao.getAllRecords();
                                            if(brlist!=null)
                                            {
                                                Iterator britr=brlist.iterator();
                                                while(britr.hasNext())
                                                {
                                                    Branch d=(Branch)britr.next();
                                                   brid=d.getBranchid();
                                                   brname=d.getBranchname();
                                                   if(branch=="")
                                                   {
                                                   %>  <option value="<%=brid%>"><%=brname%></option><%
                                                   }
                                                     else
                                                   {  
                                                    %>  <option value="<%=brid%>" <% if(brid==Integer.parseInt(branch)){out.print("selected='selected'");}%> ><%=brname%></option><%
                                                   }
                                                }  
                                             }
                                                %>
                                            </select>
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>ENGINEER<span style="color:red">*</label>
                                            <select id="myengineer" class="form-control" name="engineerselect" value="<%=engineerId%>" >
                                               <%
                                                      int raid=0;
                                                      String raname="";
                                                    List<Emploeemaster> ralist=EmployeeDao.getAllRecords();
                                            if(ralist!=null)
                                            {
                                                Iterator raitr=ralist.iterator();
                                                while(raitr.hasNext())
                                                {
                                                    Emploeemaster e=(Emploeemaster)raitr.next();
                                                    raid=e.getEmpId();
                                                    raname=e.getEmpName();
                                                    
                                                   if(engineerId==0)
                                                   {
                                                   %>  <option value="<%=raid%>"><%=raname%></option><%
                                                   }
                                                     else
                                                   {  
                                                    %>  <option value="<%=raid%>" <% if(raid==engineerId){out.print("selected='selected'");}%> ><%=raname%></option><%
                                                   }
                                                }  
                                             }
                                                %>  
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>DEALER NAME<span style="color:red">*</label>
                                            <select id="mydealer" class="form-control" name="del_name" value="<%=dealerName%>"  required>
                                              <%
                                                      int delid=0;
                                                      String delname="";
                                                    List<Dealermaster> dellist=DealerDao.getAllRecords();
                                            if(dellist!=null)
                                            {
                                                Iterator delitr=dellist.iterator();
                                                while(delitr.hasNext())
                                                {
                                                    Dealermaster d=(Dealermaster)delitr.next();
                                                   delid=d.getDealerId();
                                                   delname=d.getDealerName();
                                                   if(dealerName=="")
                                                   {
                                                   %>  <option value="<%=delid%>"><%=delname%></option><%
                                                   }
                                                     else
                                                   {  
                                                    %>  <option value="<%=delid%>" <% if(delid==Integer.parseInt(dealerName)){out.print("selected='selected'");}%> ><%=delname%></option><%
                                                   }
                                                }  
                                             }
                                                %>   
                                            </select>
                                        </div>
                                            <div class="form-group">
                                            <label>CUSTOMER NAME<span style="color:red">*</label>
                                            <input name="cus_name" type="text" min="1" max="50" value="<%=custName%>" class="form-control" required>
                                        </div>
                                         <div class="form-group">
                                                <label>SUPPLIER<span style="color:red"/>*</label>
                                                <select class="form-control" name="sup" id="suplier" value="<%=supplierName%>" onchange="fetch_model()"  required>
                                                     <!--<option value="">Select Supplier</option>-->
                                                
                                                <%
                                                  int Did=Integer.parseInt(session.getAttribute("loguserid").toString());
                                                  String abc= EmployeeDao.getdivEmpName(Did);
                                                  String dname="";
                                                  List<Model> slist=ModelDao.getDiv(abc);
                                                     if(slist!=null)
                                            {
                                                Iterator sitr=slist.iterator();
                                                while(sitr.hasNext())
                                                {
                                                    Model m=(Model)sitr.next();
                                                   dname=m.getSupName();
                                                   if(supplierName=="")
                                                   {
                                                   %>  <option value="<%=dname%>"><%=dname%></option><%
                                                   }
                                                     else
                                                   {  
                                                   %>  <option value="<%=dname%>" <% if(dname.equals(supplierName)){out.print("selected='selected'");}%> ><%=dname%></option><%
                                                   }
                                                }  
                                             }
                                                %>
                                                </select>
                                        </div>
                                </div>
                                            
                                <div class="col-lg-4">
                                    
                                          
                                    <%--  <div class="form-group">
                                            
                                            <label>PART NUMBER</label>
                                            <input name="part_no" type="text" min="1" max="50" value="<%=part_number%>" id="part_no" 
                                            class="form-control" onChange="selectsparepart();" >
                                        </div>
                                         --%>
                                    
                                      
                                    
                                        <div class="form-group">
                                                <label>MODEL<span style="color:red">*</label>
                                                <select id="fmodel" class="form-control" name="model" value="<%=productModel%>" required>
                                                     <!--<option value="">Select Model</option>-->
                                               <%
                                                    
                                                       int mid=0;
                                                      String mname="";
                                                    List<Model> mlist=ModelDao.getPModel();
                                                     if(mlist!=null)
                                            {
                                                Iterator mitr=mlist.iterator();
                                                while(mitr.hasNext())
                                                {
                                                    Model m=(Model)mitr.next();
                                                   mid=m.getModelId();
                                                   mname=m.getModelName();
                                                   if(productModel=="")
                                                   {
                                                   %>  <option value="<%=mid%>"><%=mname%></option><%
                                                   }
                                                     else
                                                   {  
                                                    %>  <option value="<%=mid%>" <% if(mid==Integer.parseInt(productModel)){out.print("selected='selected'");}%> ><%=mname%></option><%
                                                   }
                                                }  
                                             }
                                                %>
                                                </select>
                                        </div>
                                    <div class="form-group">
                                            <label>Model Configuration<span style="color:red">*</label>
                                            <input name="model_config" type="text" min="1" max="1000" value="<%=model_config%>"  class="form-control" required>
                                        </div>
                                        <div class="form-group">
                                            <label>UNIT SL NO<span style="color:red">*</label>
                                            <input name="unit_sl_no" type="text" min="1" max="50" value="<%=unitSlno%>" class="form-control" required>
                                        </div>
                                    
                                        
                                    
                                        <div class="form-group">
                                            <label>UNIT STATUS<span style="color:red">*</label>
                                            <select class="form-control" id="select22" onchange="Select()" name="unit_status" required>
                                                <%
                                                    int ddid=0;
                                                    String ddname="";
                                                    List<Dropdownmaster> dlist=DropdownDao.getDDV2();
                                                     if(dlist!=null)
                                            {
                                                Iterator ditr=dlist.iterator();
                                                while(ditr.hasNext())
                                                {
                                                   
                                                    Dropdownmaster dd=(Dropdownmaster)ditr.next();
                                                   ddid=dd.getId();
                                                   ddname=dd.getDdvalue(); 
                                                   
                                                   if(unitStatus=="")
                                                   {
                                                   %>  <option value="<%=ddid%>"><%=ddname%></option><%
                                                   }
                                                     else
                                                   {  
                                                    %>  <option value="<%=ddid%>" <% if(ddid==Integer.parseInt(unitStatus)){out.print("selected='selected'");}%> ><%=ddname%></option><%
                                                   }
                                                }  
                                             }
                                                %>
                                            </select>
                                        </div>
                                            
                                            <div class="form-group">
                                            <label>DOD</label>
                                            <input type="text" name="dod" id = "test22"  class="form-control" value="<%=dod%>"  > 
                                        </div>
                                    
                                        <div class="form-group">
                                            
                                            <label>MOD/BRD NAME<span style="color:red">*</label>
                                            <input name="mod_name" type="text" min="1" max="50" value="<%=ModBrdName%>" class="form-control" required>
                                        </div>
                                        <div class="form-group">
                                            
                                            <label>PART NUMBER</label>
                                            <input name="part_no" type="text" min="1" max="50" value="<%=part_number%>" id="part_no" 
                                            class="form-control" onchange="sparelistfn()">
                                            
                                        </div>
                                        <div class="form-group">
                                            
                                           <label>COMPATIBLE MODELS</label>
                                            <input name="comp_models" type="text" min="1" max="50" id="brd_name1" onchange="sparelistfn2()"value="<%=compatible_models%>" class="form-control" >
                                        </div>
                                    
                                        <div class="form-group">
                                            <label>DEF MOD/BRD NAME</label>
                                            <input name="def_name" type="text" min="1" max="50" value="<%=defModBrdName%>" class="form-control">
                                        </div>
                                    
                                        <div class="form-group">
                                            <label>DEF TYPE<span style="color:red">*</label>
                                            <select class="form-control" name="def_type" required>
                                                                                            <%
                                                    int dd3id=0;
                                                    String dd3name="";
                                                    List<Dropdownmaster> d3list=DropdownDao.getDDV3();
                                                     if(d3list!=null)
                                            {
                                                Iterator d3itr=d3list.iterator();
                                                while(d3itr.hasNext())
                                                {
                                                    Dropdownmaster dd=(Dropdownmaster)d3itr.next();
                                                   dd3id=dd.getId();
                                                   dd3name=dd.getDdvalue(); 
                                                   if(defType=="")
                                                   {
                                                   %>  <option value="<%=dd3id%>"><%=dd3name%></option><%
                                                   }
                                                     else
                                                   {  
                                                    %>  <option value="<%=dd3id%>" <% if(dd3id==Integer.parseInt(defType)){out.print("selected='selected'");}%> ><%=dd3name%></option><%
                                                   }
                                                }  
                                             }
                                                %>
                                            </select>
                                        </div>
                                    
                                        <div class="form-group">
                                            <label>TYPE OF ACC<span style="color:red">*</label>
                                            <select class="form-control" name="type_acc" required>
                                                <%
                                                    int dd4id=0;
                                                    String dd4name="";
                                                    List<Dropdownmaster> d4list=DropdownDao.getDDV4();
                                                     if(d4list!=null)
                                            {
                                                Iterator d4itr=d4list.iterator();
                                                while(d4itr.hasNext())
                                                {
                                                    Dropdownmaster dd=(Dropdownmaster)d4itr.next();
                                                   dd4id=dd.getId();
                                                   dd4name=dd.getDdvalue(); 
                                                   if(typeOfAcc=="")
                                                   {
                                                   %>  <option value="<%=dd4id%>"><%=dd4name%></option><%
                                                   }
                                                     else
                                                   {  
                                                    %>  <option value="<%=dd4id%>" <% if(dd4id==Integer.parseInt(typeOfAcc)){out.print("selected='selected'");}%> ><%=dd4name%></option><%
                                                   }
                                                }  
                                             }
                                                %>
                                            </select>
                                        </div>
                                           
                                         <div class="form-group">
                                            <label>REPAIRED BRD STK DATE<span style="color:red" class="label4">*</label>
                                            <input name="repair_stk_date" type="text" id = "datepicker-15" onchange="repairedDate()" value="<%=repairedBrdStkDate%>" class="form-control" >
                                        </div>  
                                         <div class="form-group">
                                           <!--  <label>STOCK ADV NO</label> -->
                                            <input name="repaired_brd_adv_no" type="hidden"  id="stock" value="<%=repairedBrdAdvNo%>" class="form-control" readonly>
                                        </div>  
                                          
                                        <div class="form-group">
                                            <label>FINAL REMARKS</label>
                                            <input name="final_remarks" type="text" min="1" max="100" value="<%=finalRemarks%>" class="form-control">
                                        </div> 
                                        <div class="form-group">
                                            <label>DEF PART SNO<span style="color:red">*</label>
                                                <input name="def_sn_no" type="text" min="1" max="50" value="<%=defPartSn%>" class="form-control" required>
                                        </div>
                                        
                                         
                                     
                                        
                                </div>
                                            
                                            
                                            
                                            
                                            
                                <div class="col-lg-4">
                                     <div class="form-group">
                                            <label>DEF GIR NO<span style="color:red">*</label>
                                            <input name="def_gir_no" type="text" id="defgir" min="1" max="50" value="<%=defGirNo%>" onkeyup="changeupper(); changefun_1()" class="form-control" required>
                                        </div>
                                    
                                       <div class="form-group">
                                            <label>REP GIR SNO<span style="color:red" class="label1">*</label>
                                            <input name="rep_gir_sno" id="repgir" type="text" min="1" max="50" value="<%=repGirNo%>" onKeyup="changeupper();changefun_1()" class="form-control">
                                        </div>
                                    
                                      
                                    
                                        <div class="form-group">
                                            <label>REP TYPE<span style="color:red">*</label>
                                            <select class="form-control" name="rep_type" id="rep_type" value="<%=repType%>" onchange="empty_fun();changefun_1();" required>
                                                 <%
                                                    int dd6id=0;
                                                    String dd6name="";
                                                    List<Dropdownmaster> d6list=DropdownDao.getDDV6();
                                                     if(d6list!=null)
                                            {
                                                Iterator d6itr=d6list.iterator();
                                                while(d6itr.hasNext())
                                                {
                                                    Dropdownmaster dd=(Dropdownmaster)d6itr.next();
                                                   dd6id=dd.getId();
                                                   dd6name=dd.getDdvalue(); 
                                               
                                                   if(repType.isEmpty())
                                                   {    
                                                   %>  <option value="<%=dd6id%>"><%=dd6name%></option><%
                                                   }
                                                     else
                                                   {      
                                                    %>  <option value="<%=dd6id%>" <% if(dd6id==Integer.parseInt(repType)){out.print("selected='selected'");}%> ><%=dd6name%></option><%
                                                   }
                                                }  
                                             }
                                                %>
                                        </select>
                                        </div>
                                    
                                        <div class="form-group">
                                            <label>DEF UNIT GIR NO</label>
                                            <input name="def_unit_gir_no" type="text" min="1" max="50" value="<%=defUnitGirNo%>"  class="form-control">
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>FIELD REMARKS<span style="color:red">*</label>
                                            <input name="field_remarks" type="text" min="1" max="50" value="<%=fieldRemarks%>" class="form-control" required>
                                        </div>
                                        <div class="form-group">
                                            <label>COMPONENTS USED TO REPAIR</label>
                                            <input name="comp_repair" type="text" min="1" max="50" value="<%=((componentsUsedforRepair==null)?"":componentsUsedforRepair)%>" class="form-control">
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>TECHNICAL REMARKS</label>
                                            <input name="tech_remarks" type="text" min="1" max="100" value="<%=((technicalRemarks==null)?"":technicalRemarks)%>" class="form-control" >
                                        </div>
                                        <div class="form-group">
                                            <label>COMMERCIAL DETAILS INVOICE REMARKS</label>
                                            <input name="invoice" type="text" min="1" max="100" value="<%=((comrclDtlInvRmrk==null)?"":comrclDtlInvRmrk)%>" class="form-control">
                                        </div>
                                        
                                     <div class="form-group">
                                            <label>TYPE OF WORK/STATUS<span style="color:red">*</label>
                                            <select class="form-control" name="type_work" id="work_type" value="<%=typeOfWork%>" onchange="changefun_scrapp();changefn_save()" required>
                                                 <option value="">Select Work</option>
                                                <%
                                                    int dd5id=0;
                                                    String dd5name="";
                                                    List<Dropdownmaster> d5list=DropdownDao.getDDV5();
                                                     if(d5list!=null)
                                            {
                                                Iterator d5itr=d5list.iterator();
                                                while(d5itr.hasNext())
                                                {
                                                    Dropdownmaster dd=(Dropdownmaster)d5itr.next();
                                                   dd5id=dd.getId();
                                                  
                                                   dd5name=dd.getDdvalue(); 
                                                   System.out.println("the dd id isss"+dd5id+" the iddd name iss"+dd5name);
                                                   //if(typeOfWork=="")
                                                	   if(typeOfWork.equalsIgnoreCase(""))
                                                   {
                                                   %>  <option value="<%=dd5id%>"><%=dd5name%></option><%
                                                   }
                                                     else
                                                   {  
                                                    %>  <option value="<%=dd5id%>" <% if(dd5id==Integer.parseInt(typeOfWork)){out.print("selected='selected'");}%> ><%=dd5name%></option><%
                                                   }
                                                }  
                                             }
                                                %>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>SHIP DATE FROM SERVICE CENTER<span style="color:red" class="label3">*</label>
                                            <input name="ship_date" type="text"  id = "datepicker-13" value="<%=shipDtFrmSerCntr%>" onchange="shipDate()" class="form-control" readonly="">
                                        </div>
                                        
                                        <div class="form-group">
                                           <!--  <label>DISP ADVICE NO</label> -->
                                            <input name="disp_advice_no"  type="hidden" id="disp" min="1" max="50" value="<%=dispAdvNo%>" class="form-control" readonly="">
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>SHIP DATE FROM COMMERCIAL<span style="color:red" class="label5">*</label>
                                            <input name="commercial_date" type="text" id = "datepicker-16" onchange="commercialDate()" value="<%=shipDateFromCommercial%>" class="form-control" readonly="">
                                        </div>
                                       
                                          
                                        
                                        <div class="form-group">
                                            <label>DC NO<span style="color:red" class="label6">*</label>
                                            <input name="dc_no" type="text" id="dcno" min="1" max="50" value="<%=dcNo%>" class="form-control" readonly="">
                                        </div>
                                        <div class="form-group">
                                            <label>REPORT_TYPE<span style="color:red">*</label>
                                            <select class="form-control" name="report_type" id="report_type" value="" onchange="rep_type_change();" required>
                                                 <%--  <option value="<%=report_type%>"><%=report_type%></option> --%>
                                                <option value="servicecenter" class="opt1">SERVICE CENTER</option>
                                                <option value="scraplist" class="opt2">SCRAP LIST</option>
                                                  <option value="dispatchlist" class="opt3">DISPATCH LIST</option>
                                                   <option value="stocklist" class="opt4">STOCK LIST</option>
                                            </select>
                                        </div>
                                         <div class="form-group">
                                            <label>DESTINATION<span style="color:red">*</label>
                                            <input name="destination" id="destination" type="text" min="1" max="50" value="<%=destination%>" class="form-control" required readonly="">
                                        </div>
                                             
                                </div>
                                <div style="padding-right: 20px;" class="save1">
                                     <button type="submit" class="btn btn-success" style="margin-top: 25px; float: right;">SAVE</button>
                                              
                                </div>
                                </form>  
                                </div>
                                
                            </div>
                          
                        </div>
                       
                    </div>
                   
                </div>
               
            </div>
         
        </div>
      
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
    {   var request=new XMLHttpRequest();
        var name=document.vinform.regionselect.value;  
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
        var name=document.vinform.regionselect.value;  
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
        var name=document.vinform.branchselect.value;  
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
            $( "#datepicker-13" ).datepicker({ 
                 changeMonth: true,
			changeYear: true,
                dateFormat: 'dd-mm-yy' });
            $( "#datepicker-13" ).datepicker("show");
         });
         $(function() {
            $( "#datepicker-14" ).datepicker({ 
                 changeMonth: true,
			changeYear: true,
                dateFormat: 'dd-mm-yy' });
            $( "#datepicker-14" ).datepicker("show");
         });
         $(function() {
            $( "#datepicker-1" ).datepicker({ 
                 changeMonth: true,
			changeYear: true,
                dateFormat: 'dd-mm-yy' });
            $( "#datepicker-1" ).datepicker("show");
         });
         $(function() {
            $( "#datepicker-2" ).datepicker({ 
                 changeMonth: true,
			changeYear: true,
                dateFormat: 'dd-mm-yy' });
            $( "#datepicker-2" ).datepicker("show");
         });
         $(function() {
            $( "#datepicker-3" ).datepicker({ 
                 changeMonth: true,
			changeYear: true,
                dateFormat: 'dd-mm-yy',
            minDate: -1,
                maxDate:0});
            $( "#datepicker-3" ).datepicker("show");
         });
          $(function() {
            $( "#datepicker-4" ).datepicker({
                 changeMonth: true,
			changeYear: true,
                dateFormat: 'dd-mm-yy'});
            $( "#datepicker-4" ).datepicker("show");
         });
         $(function() {
            $( "#datepicker-15" ).datepicker({ 
                 changeMonth: true,
			changeYear: true,
                dateFormat: 'dd-mm-yy' });
            $( "#datepicker-15" ).datepicker("show");
         });
         $(function() {
            $( "#datepicker-16" ).datepicker({ 
                 changeMonth: true,
			changeYear: true,
                dateFormat: 'dd-mm-yy' });
            $( "#datepicker-16" ).datepicker("show");
         });
         
      </script>
      
      <script>
          function shipDate(){
              var date=document.getElementById("datepicker-13").value;
              if(date=="")
              {
                  document.getElementById("disp").readOnly=true;
              }
              else
              {
                  document.getElementById("disp").required=true;
                  document.getElementById("disp").readOnly=false;
              }
          }
          
         function commercialDate()
         {
             var date=document.getElementById("datepicker-16").value;
             
             if(date=="")
             {
                 document.getElementById("dcno").readOnly=true;
             }
             else
             {
               
                 document.getElementById("dcno").required=true;
                  document.getElementById("dcno").readOnly=false;
             }
         }
         function repairedDate()
         {
             var date=document.getElementById("datepicker-15").value;
             
             if(date=="")
             {
                 document.getElementById("stock").readOnly=true;
             }
             else
             {
                 document.getElementById("stock").required=true;
                 document.getElementById("stock").readOnly=false;
             }
         }
      </script>
      
      <script>
function Select()
{
    var cat=document.getElementById("select22").value;
    
    if(cat=="44"||cat=="43")
    {
        document.getElementById("test22").required=true;
        $(function() {
            $( "#test22" ).datepicker({ 
                changeMonth: true,
		changeYear: true,
                dateFormat: 'dd-mm-yy' });
           
         });
          $( "#test22" ).datepicker();
    }
    else
    {
        document.getElementById("test22").required=false;
      $( "#test22" ).datepicker("destroy");
    }
}


function changefun_10(){
	  $("#work_type").val('');
	  var value=$("#rep_type").val();
	 //alert(value+"the valueee iasdjaks");
	  var gir=$("#repgir").val();
	  var gir1=$("#defgir").val();
	  var work_type=$("#work_type").val();
	  //alert(work_type);
	  
	  var v1=gir;
	  var v2=$("#datepicker-13").val();
	  var v3=$("#datepicker-16").val();
	  var v4=$("#datepicker-15").val();
	  var v5=$("#dcno").val();
	  
	  if(value==56&&gir==''){
		  
		  
		//alert("ggrgtr");
		  $('.label1').hide();
		 document.getElementById("repgir").required = false;
		 $('.opt1').show();
		 $('.opt2').hide();
		 $('.opt4').hide();
		 $('.opt3').hide();
		// $("#report_type").val('');
		
		document.getElementById("datepicker-13").readOnly = true;
		document.getElementById("datepicker-15").readOnly = true;
		document.getElementById("datepicker-16").readOnly = true;
		document.getElementById("dcno").readOnly = true;
	  }else if((value==56&&gir!='') && (gir.toUpperCase()!=gir1.toUpperCase())){
		 // alert("matchedd");
		  $('.label1').show();
		  document.getElementById("datepicker-15").readOnly = true;
  		document.getElementById("datepicker-13").readOnly = false;
 		//document.getElementById("datepicker-15").readOnly = true;
 		document.getElementById("datepicker-16").readOnly = true;
 		document.getElementById("dcno").readOnly = true;
		  
		 document.getElementById("repgir").required = true;
		  document.getElementById("datepicker-13").required = true;
		  
		 document.getElementById("datepicker-15").required = false;
		 document.getElementById("datepicker-16").required = false;
		 document.getElementById("dcno").required = false;
		 $('.label3').show();
		 $('.label4').hide();
		 $('.label5').hide();
		 $('.label6').hide();
		 $('.opt2').hide();
		 $('.opt4').hide();
		 $('.opt3').show();
		 $('.opt1').hide();
		
		// $("#report_type").val('');
	  }else if((value==56&&gir!='') && (gir.toUpperCase()==gir1.toUpperCase())){
		///alert("matchedd 22");
		  $('.label1').show();
		  document.getElementById("datepicker-15").readOnly = false;
		document.getElementById("datepicker-13").readOnly = false;
		//document.getElementById("datepicker-15").readOnly = true;
		document.getElementById("datepicker-16").readOnly = true;
		document.getElementById("dcno").readOnly = true;
		  
		  
 		 document.getElementById("repgir").required = true;
 		  document.getElementById("datepicker-13").required = true;
 		 document.getElementById("datepicker-15").required = true;
 		 document.getElementById("datepicker-16").required = false;
 		 document.getElementById("dcno").required = false;
 		$('.label1').show();
 		 $('.label3').show();
 		 $('.label4').show();
 		 $('.label5').hide();
 		 $('.label6').hide();
 		 $('.opt2').hide();
 		 $('.opt4').hide();
 		 $('.opt3').show();
 		 $('.opt1').hide();
 		//document.getElementById("datepicker-15").readOnly = false;
 		// $("#report_type").val('');
	  }else if(value==60&&v1==''){
	/// alert("first");
		 //$("#rep_type").val();
		  document.getElementById("datepicker-15").readOnly = true;
		document.getElementById("datepicker-13").readOnly = true;
		//document.getElementById("datepicker-15").readOnly = true;
		document.getElementById("datepicker-16").readOnly = true;
		document.getElementById("dcno").readOnly = true;
		 
		 
		 $('.opt2').hide();
 		 $('.opt4').hide();
 		 $('.opt1').show();
 		 $('.opt3').hide();
 		 
 		 
 		 document.getElementById("repgir").required = true;
		  document.getElementById("datepicker-13").required = true;
		 document.getElementById("datepicker-15").required = false;
		 document.getElementById("datepicker-16").required = true;
		 document.getElementById("dcno").required = true;
		 $('.label1').show();
		 $('.label3').show();
		 $('.label4').hide();
		 $('.label5').show();
		 $('.label6').show();
	//	document.getElementById("datepicker-15").readOnly = false;
		$('.save1').hide();
	  }else if(value==60&&v1!=''){
		/// alert("seconddd");
		// document.getElementById("datepicker-15").readOnly = true;
		document.getElementById("datepicker-13").readOnly = false;
		document.getElementById("datepicker-15").readOnly = true;
		document.getElementById("datepicker-16").readOnly = false;
		document.getElementById("dcno").readOnly = false;
		
		
		//$("#report_type").val('');
		 $('.opt2').hide();
  		 $('.opt4').hide();
  		 $('.opt1').show();
  		 $('.opt3').hide();
  		 
  		 
  		 
  		 document.getElementById("repgir").required = true;
 		  document.getElementById("datepicker-13").required = true;
 		 document.getElementById("datepicker-15").required = false;
 		 document.getElementById("datepicker-16").required = true;
 		 document.getElementById("dcno").required = true;
 		 $('.label1').show();
 		 $('.label3').show();
 		 $('.label4').hide();
 		 $('.label5').show();
 		 $('.label6').show();
	  }
	  
}


function clearfn(){
	 var value=$("#rep_type").val();
	 $('.save1').hide();
	 if(value==56){
		 $("#repgir").val('');
		  $("#defgir").val('');
		 $("#work_type").val('');
		  $("#report_type").val('');
		  $("#datepicker-15").val('');
		  $("#datepicker-13").val('');
		  $("#datepicker-16").val('');
		
		  $("#dcno").val('');
		  $("#destination").val('');
	 }else{
		 $("#repgir").val('');
		  $("#defgir").val('');
		 $("#work_type").val('');
		  $("#datepicker-15").val('');
		 // $("#datepicker-13").val('');
		 // $("#datepicker-16").val('');
		  $("#report_type").val('');
		 // $("#dcno").val('');
		  $("#destination").val('');
	 }
}
  function changefun_1(){
	 /// alert("change1");
	  $("#work_type").val('');
	  $("#report_type").val('');
	  var value=$("#rep_type").val();
	  //alert(value+" the valueee iasdjaks");
	  var gir=$("#repgir").val();
	  var gir1=$("#defgir").val();
	  var work_type=$("#work_type").val();
	 // alert(work_type);
	  $('.save1').hide();
	  if(value==56&&gir==''){
		//  alert("ggrgtr11");
		  $('.label1').hide();
		 document.getElementById("repgir").required = false;
		 $('.opt1').show();
		 $('.opt2').hide();
		 $('.opt4').hide();
		 $('.opt3').hide();
		// $("#report_type").val('');
		
		document.getElementById("datepicker-13").readOnly = true;
		document.getElementById("datepicker-15").readOnly = true;
		document.getElementById("datepicker-16").readOnly = true;
		document.getElementById("dcno").readOnly = true;
	  }else if((value==56&&gir!='') && (gir.toUpperCase()!=gir1.toUpperCase())){
		  
		//  alert("matchedd11");
		  $('.label1').show();
		  document.getElementById("datepicker-15").readOnly = true;
  		document.getElementById("datepicker-13").readOnly = false;
 		//document.getElementById("datepicker-15").readOnly = true;
 		document.getElementById("datepicker-16").readOnly = true;
 		document.getElementById("dcno").readOnly = true;
		  
		 document.getElementById("repgir").required = true;
		  document.getElementById("datepicker-13").required = true;
		  
		 document.getElementById("datepicker-15").required = false;
		 document.getElementById("datepicker-16").required = false;
		 document.getElementById("dcno").required = false;
		 $('.label3').show();
		 $('.label4').hide();
		 $('.label5').hide();
		 $('.label6').hide();
		 $('.opt2').hide();
		 $('.opt4').hide();
		 $('.opt3').show();
		 $('.opt1').hide();
		 
		  
	  }else  if((value==56&&gir!='') && (gir.toUpperCase()==gir1.toUpperCase())){
			//alert("matchedd 22");
			  $('.label1').show();
			  document.getElementById("datepicker-15").readOnly = false;
			document.getElementById("datepicker-13").readOnly = false;
			//document.getElementById("datepicker-15").readOnly = true;
			document.getElementById("datepicker-16").readOnly = true;
			document.getElementById("dcno").readOnly = true;
			  
			  
	 		 document.getElementById("repgir").required = true;
	 		  document.getElementById("datepicker-13").required = true;
	 		 document.getElementById("datepicker-15").required = true;
	 		 document.getElementById("datepicker-16").required = false;
	 		 document.getElementById("dcno").required = false;
	 		$('.label1').show();
	 		 $('.label3').show();
	 		 $('.label4').show();
	 		 $('.label5').hide();
	 		 $('.label6').hide();
	 		 $('.opt2').hide();
	 		 $('.opt4').hide();
	 		 $('.opt3').show();
	 		 $('.opt1').hide();
	 		//document.getElementById("datepicker-15").readOnly = false;
	 		// $("#report_type").val('');
		  }else if(value==60&&gir==''){
				// alert("first");
				 //$("#rep_type").val();
				  document.getElementById("datepicker-15").readOnly = true;
				document.getElementById("datepicker-13").readOnly = true;
				//document.getElementById("datepicker-15").readOnly = true;
				document.getElementById("datepicker-16").readOnly = true;
				document.getElementById("dcno").readOnly = true;
				 
				 
				 $('.opt2').hide();
		 		 $('.opt4').hide();
		 		 $('.opt1').show();
		 		 $('.opt3').hide();
		 		 
		 		 
		 		 document.getElementById("repgir").required = true;
				  document.getElementById("datepicker-13").required = true;
				 document.getElementById("datepicker-15").required = false;
				 document.getElementById("datepicker-16").required = true;
				 document.getElementById("dcno").required = true;
				 $('.label1').show();
				 $('.label3').show();
				 $('.label4').hide();
				 $('.label5').show();
				 $('.label6').show();
			//	document.getElementById("datepicker-15").readOnly = false;
				$('.save1').hide();
			  }else if((value==60&&gir!='')&&(gir.toUpperCase()!=gir1.toUpperCase())){
				// alert("seconddd1111");
				// document.getElementById("datepicker-15").readOnly = true;
				document.getElementById("datepicker-13").readOnly = false;
				document.getElementById("datepicker-15").readOnly = true;
				document.getElementById("datepicker-16").readOnly = false;
				document.getElementById("dcno").readOnly = false;
				
				
				//$("#report_type").val('');
				 $('.opt2').hide();
		  		 $('.opt4').hide();
		  		 $('.opt1').show();
		  		 $('.opt3').hide();
		  		 
		  		 
		  		 
		  		 document.getElementById("repgir").required = true;
		 		  document.getElementById("datepicker-13").required = true;
		 		 document.getElementById("datepicker-15").required = false;
		 		 document.getElementById("datepicker-16").required = true;
		 		 document.getElementById("dcno").required = true;
		 		 $('.label1').show();
		 		 $('.label3').show();
		 		 $('.label4').hide();
		 		 $('.label5').show();
		 		 $('.label6').show();
			  }else if((value==60&&gir!='')&&(gir.toUpperCase()==gir1.toUpperCase())){
					// alert("seconddd");
						// document.getElementById("datepicker-15").readOnly = true;
						document.getElementById("datepicker-13").readOnly = false;
						document.getElementById("datepicker-15").readOnly = false;
						document.getElementById("datepicker-16").readOnly = false;
						document.getElementById("dcno").readOnly = false;
						
						
						//$("#report_type").val('');
						 $('.opt2').hide();
				  		 $('.opt4').hide();
				  		 $('.opt1').show();
				  		 $('.opt3').hide();
				  		 
				  		 
				  		 
				  		 document.getElementById("repgir").required = true;
				 		  document.getElementById("datepicker-13").required = true;
				 		 document.getElementById("datepicker-15").required = true;
				 		 document.getElementById("datepicker-16").required = true;
				 		 document.getElementById("dcno").required = true;
				 		 $('.label1').show();
				 		 $('.label3').show();
				 		 $('.label4').show();
				 		 $('.label5').show();
				 		 $('.label6').show();
					  }
	  
  }
  
  
  
  function changeupper(){
		/*  alert("caps");
		 alert("caps");
	 	  alert($("#repgir").val()); */
	 var x1=	$("#repgir").val().toUpperCase();
	 var x2=	$("#defgir").val().toUpperCase();
	 	//alert(x1);
	 	$("#repgir").val(x1);
	 	$("#defgir").val(x2);
		
	  }
  
  
  
  function changefun_scrapp(){
	  var value=$("#rep_type").val();
	 // alert(value+"the valueee iasdjaks");
	  var gir=$("#repgir").val();
	  var gir1=$("#defgir").val();
	// alert(gir1);

	  var type=$("#work_type").val();
	//  alert(type);
	  var v1=gir;
	  var v2=$("#datepicker-13").val();
	  var v3=$("#datepicker-16").val();
	  var v4=$("#datepicker-15").val();
	  var v5=$("#dcno").val();
	  
	  if(type==23&&value==60){
		 // $("#report_type").val('');
		  
		  document.getElementById("datepicker-15").readOnly = true;
    		document.getElementById("datepicker-13").readOnly = false;
   		//document.getElementById("datepicker-15").readOnly = false;
   		document.getElementById("datepicker-16").readOnly = false;
   		document.getElementById("dcno").readOnly = false;
   		 $('.opt2').show();
     		 $('.opt4').hide();
     		 $('.opt1').hide();
     		 $('.opt3').hide();
     		 
     		 
     		 
     		 document.getElementById("repgir").required = true;
    		  document.getElementById("datepicker-13").required = true;
    		 document.getElementById("datepicker-15").required = true;
    		 document.getElementById("datepicker-16").required = true;
    		 document.getElementById("dcno").required = true;
    		 $('.label1').show();
    		 $('.label3').show();
    		 $('.label4').show();
    		 $('.label5').show();
    		 $('.label6').show();
    		 
    		 $('.save1').show();
	  }else if(type==25&&value==60){
		//  $("#report_type").val('');
		  $('.opt2').hide();
    		 $('.opt4').hide();
    		 $('.opt1').show();
    		 $('.opt3').hide();
    		 
    		  document.getElementById("datepicker-15").readOnly = false;
      		document.getElementById("datepicker-13").readOnly = false;
     		document.getElementById("datepicker-15").readOnly = false;
     		document.getElementById("datepicker-16").readOnly = false;
     		document.getElementById("dcno").readOnly = false;
    		 
    		 document.getElementById("repgir").required = true;
   		  document.getElementById("datepicker-13").required = true;
   		
   		 document.getElementById("datepicker-16").required = true;
   		 document.getElementById("dcno").required = true;
   		 $('.label1').show();
   		 $('.label3').show();
   		 $('.label4').hide();
   		 $('.label5').show();
   		 $('.label6').show();
   		document.getElementById("datepicker-15").readOnly = true;
   	 document.getElementById("datepicker-15").required = false;
	 
   //	 $('.save1').show();
	  }else if((type==25&&value==56)&&(gir=='')){
		  document.getElementById("datepicker-15").readOnly = true;
	  		document.getElementById("datepicker-13").readOnly = true;
	 		document.getElementById("datepicker-15").readOnly = true;
	 		document.getElementById("datepicker-16").readOnly = true;
	 		document.getElementById("dcno").readOnly = true;
	 		 $('.opt2').hide();
	   		 $('.opt4').hide();
	   		 $('.opt1').show();
	   		 $('.opt3').hide();
	   		 
	   		 
	   		 
	   		 document.getElementById("repgir").required = false;
	  		  document.getElementById("datepicker-13").required = false;
	  		 document.getElementById("datepicker-15").required = false;
	  		 document.getElementById("datepicker-16").required = false;
	  		 document.getElementById("dcno").required = false;
	  		 $('.label1').show();
	  		 $('.label3').hide();
	  		 $('.label4').hide();
	  		 $('.label5').hide();
	  		 $('.label6').hide();
	  		 
	  		 $('.save1').show();
	  }else  if((type==25&&value==56)&&(gir!=gir1)){
		  document.getElementById("datepicker-15").readOnly = true;
  		document.getElementById("datepicker-13").readOnly = false;
 		document.getElementById("datepicker-15").readOnly = true;
 		document.getElementById("datepicker-16").readOnly = true;
 		document.getElementById("dcno").readOnly = true;
 		 $('.opt2').hide();
   		 $('.opt4').hide();
   		 $('.opt1').hide();
   		 $('.opt3').show();
   		 
   		 
   		 
   		 document.getElementById("repgir").required = true;
  		  document.getElementById("datepicker-13").required = true;
  		 document.getElementById("datepicker-15").required = false;
  		 document.getElementById("datepicker-16").required = false;
  		 document.getElementById("dcno").required = false;
  		 $('.label1').show();
  		 $('.label3').hide();
  		 $('.label4').show();
  		 $('.label5').hide();
  		 $('.label6').hide();
  		 
  		 $('.save1').show();
	  }else  if((type==25&&value==56)&&(gir==gir1)){
		  document.getElementById("datepicker-15").readOnly = false;
	  		document.getElementById("datepicker-13").readOnly = false;
	 		document.getElementById("datepicker-15").readOnly = true;
	 		document.getElementById("datepicker-16").readOnly = true;
	 		document.getElementById("dcno").readOnly = true;
	 		 $('.opt2').show();
	   		 $('.opt4').hide();
	   		 $('.opt1').hide();
	   		 $('.opt3').hide();
	   		 
	   		 
	   		 
	   		 document.getElementById("repgir").required = true;
	  		  document.getElementById("datepicker-13").required = true;
	  		 document.getElementById("datepicker-15").required = true;
	  		 document.getElementById("datepicker-16").required = false;
	  		 document.getElementById("dcno").required = false;
	  		 $('.label1').show();
	  		 $('.label3').show();
	  		 $('.label4').show();
	  		 $('.label5').show();
	  		 $('.label6').show();
	  		 
	  		 $('.save1').show();
		  }
	  
	  
  }
  
  function change_work_type(){
	  var value=$("#rep_type").val();
	  //alert(value+"the valueee iasdjaks");
	  var gir=$("#repgir").val();
	  var gir1=$("#defgir").val();
	// alert(gir1);

	  var type=$("#work_type").val();
	//  alert(type);
	  
	  
	  if(type==23&&value==56){

		  document.getElementById("datepicker-15").readOnly = false;
    		document.getElementById("datepicker-13").readOnly = false;
   		document.getElementById("datepicker-15").readOnly = false;
   		document.getElementById("datepicker-16").readOnly = false;
   		document.getElementById("dcno").readOnly = false;
   		 $('.opt2').show();
     		 $('.opt4').hide();
     		 $('.opt1').hide();
     		 $('.opt3').hide();
     		 
     		 
     		 
     		 document.getElementById("repgir").required = true;
    		  document.getElementById("datepicker-13").required = true;
    		 document.getElementById("datepicker-15").required = true;
    		 document.getElementById("datepicker-16").required = true;
    		 document.getElementById("dcno").required = true;
    		 $('.label1').show();
    		 $('.label3').show();
    		 $('.label4').show();
    		 $('.label5').show();
    		 $('.label6').show();
    		 
    		 $('.save1').show();
		  
		  
		  
	  }
	  
	  
	  
	  
	  
	  
	  
  }
  
  function empty_fun(){
	//  alert("emptyy");
	  $("#work_type").val('');
	  $("#datepicker-15").val('');
	  $("#datepicker-13").val('');
	  $("#datepicker-16").val('');
	  $("#report_type").val('');
	  $("#dcno").val('');
	  $("#destination").val('');
	  
	  
  }
  
  function changefn_save(){
	  
			//  alert("in save");
			  var s1=$("#rep_type").val();
			  var type=$("#work_type").val();
			 // alert(type+"type iss");
			  var gir=$("#repgir").val();
			  var gir1=$("#defgir").val();
			  if((s1==60)&&(type==25||type==23)){
				 // alert("c0");
				  $('.save1').show();
			  }else if((s1==56)&&(gir=='')&&(type==25||type==60)){
				//  alert("c1");
				  $('.save1').show();
			  }else if((s1==56)&&(gir.toUpperCase()==gir1.toUpperCase())&&(type==20||type==21||type==26||type==29||type==30)){
				///  alert("c2");
				  $('.save1').show();
			  }else if((s1==56)&&(gir.toUpperCase()!=gir1.toUpperCase())&&(type==25)){
				//  alert("c3");
				  $('.save1').show();
				  
			  }else {
				 ///////////////// alert("c else");
				  $('.save1').hide();
			  }
			  
		  
  }
  function rep_type_change(){
	  var r1=$("#report_type").val();
	  if(r1=="dispatchlist"){
		  document.getElementById("destination").readOnly = false;
		  document.getElementById("destination").required=true;
		  
	  }else{
		  document.getElementById("destination").readOnly = true;
		  document.getElementById("destination").required=false;
	  }
  }
  function sparelistfn(){
	 // alert("keypress");
	  var request=new XMLHttpRequest();
	    var name=document.vinform.regionselect.value;  
	    var val1=$("#part_no").val();
    	  var val2=$("#division").val();
	        var url="sparelist.jsp?part_no="+val1+"&division="+val2+"";  
	        try
	        {  
	            request.onreadystatechange=function()
	            {  
	                if(request.readyState==4)
	                {  
	                    var val=request.responseText;  
	                    console.log();
	                  var  array  = val.split(",");
	                  $('#brd_name1').val(array[0]);
	                  $('#brd_name3').val(array[1]);
	               //   $('#brd_name3').val(array[1]);
	                  var opt = document.getElementById("def_type");
	                  opt.value = parseInt(array[2]);
	                    
	                   // document.getElementById('showlist').innerHTML=val;
	                    
	                }  
	            }
	            request.open("GET",url,true);  
	            request.send();  
	        }catch(e){alert("Unable to connect to server");}  
	  
  }
  
  window.onload=function(){
	  //alert("wefijqn");
	  clearfn();
	 /// loadchange();
	//  changefun4();
	//  changefun1();
	// changefun3();
		
	//  changefn2();
  }
  


      </script>   
</body>
</html>