<%@ page errorPage="error.jsp" %> 
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
                                                        int scEngnr=0;
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
                                                        String technicalRemarks="";
                                                        String componentsUsedforRepair="";
                                                        String repairedBrdStkDate="";
                                                        String finalRemarks="";
                                                        String typeOfWork="";
                                                        String shipDtFrmSerCntr="";
                                                        String dispAdvNo="";
                                                        String shipDateFromCommercial="";
                                                        String dcNo="";
                                                        String comrclDtlInvRmrk="";
                                            
                                            if(request.getAttribute("editablerecord")!=null)
                                            {
                                                service_master d=(service_master)request.getAttribute("editablerecord");
                                                        id=d.getSerId();
                                                        scRefNo=d.getScRefNo();
                                                        entryDate=d.getEntryDate();
                                                        scEngnr=d.getScEngnr();
                                                        raEngnr=d.getRaEngnr();
                                                        frnNo=d.getFrnNo();
                                                        frnDate=d.getFrnDate();
                                                        docketNo=d.getDocketNo();
                                                        despatchDate= d.getDespatchDate();
                                                        serCommInwardDate= d.getSerCommInwardDate();
                                                        serCentreReceivedDate=d.getSerCentreReceivedDate();
                                                        stkCust=d.getStkCust();
                                                        region=d.getRegion();
                                                        branch=d.getBranch();
                                                        engineerId=d.getEngineerId();
                                                        dealerName=d.getDealerName();
                                                        custName=d.getCustName();
                                                        supplierName=d.getSupplierName();
                                                        productModel=d.getProductModel();
                                                        unitSlno=d.getUnitSlno();
                                                        dod=d.getDod();
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
                                                        technicalRemarks=d.getTechnicalRemarks();
                                                        componentsUsedforRepair=d.getComponentsUsedforRepair();
                                                        repairedBrdStkDate= d.getRepairedBrdStkDate();
                                                        finalRemarks=d.getFinalRemarks();
                                                        typeOfWork=d.getTypeOfWork();
                                                        shipDtFrmSerCntr=d.getShipDtFrmSerCntr();
                                                        dispAdvNo=d.getDispAdvNo();
                                                        shipDateFromCommercial=d.getShipDateFromCommercial();
                                                        dcNo=d.getDcNo();
                                                        comrclDtlInvRmrk=d.getComrclDtlInvRmrk();
                                            }
                                            int lid=Integer.parseInt(session.getAttribute("loguserid").toString());
                                            division=EmployeeDao.getempdivision(lid);
                                            divid=ProductDao.getid(division);
                                     %>
                                    <input type="hidden" name="id" value="<%=id%>" class="form-control">
                                        <div class="form-group required">
                                            <label class="control-label"> Division</label>
                                            <input name="division" class="form-control"   value="<%=division%>" readonly>
                                        </div>
                                        <div class="form-group required">
                                            <label class="control-label"> SC_REF_NO<span style="color:red">*</label>
                                            <input name="sc_ref_no" class="form-control" value="<%=scRefNo%>" required>
                                        </div>
                                           
                                        <div class="form-group required">
                                            <label class="control-label"> SC ENGINEER<span style="color:red">*</label>
                                            <select class="form-control" name="sc_eng"  required>
                                                <%
                                                    List<String> list=EmployeeDao.getAllEmployeesofdivision(divid);
                                                    Iterator itr=list.iterator();
                                                    while(itr.hasNext())
                                                    {
                                                        %><option><%=itr.next()%></option><%
                                                    }
                                                %>
                                            </select>
                                        </div>
                                          
                                        <div class="form-group required">
                                            <label class="control-"> FRN NO<span style="color:red">*</label>
                                            <input name="frn_no" class="form-control" value="<%=frnNo%>"  required>
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
                                            <label>STK/CUST<span style="color:red">*</label><%=stkCust%>
                                            <select class="form-control" required  name="stk">
                                                <%
                                                    list=DropdownDao.getAlldp("1");
                                                    itr=list.iterator();
                                                    while(itr.hasNext())
                                                    {
                                                          %> <option value="<%=itr.next()%>" ${itr.next == stkCust ? 'selected="selected"' : ''}><%=itr.next()%></option><%
                                                    }
                                                %>
                                            </select>
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>SELECT REGION<span style="color:red">*</label>
                                            <select class="form-control" name="regionselect" onchange="fetch_dealer(); fetch_branch()" value="<%=region%>" required>
                                                <%
                                                    list=RegionDao.getAllRegions();
                                                    itr=list.iterator();
                                                    int i=0;
                                                    while(itr.hasNext())
                                                    {
                                                        %><option><%=itr.next()%></option><%
                                                    }
                                                %>
                                            </select>
                                        </div>
                                           
                                        <div class="form-group">
                                            <label>BRANCH<span style="color:red">*</label>
                                            <select id="mybranch" class="form-control" name="branchselect" onchange="fetch_engineer()" value="<%=branch%>" required>
                                            </select>
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>ENGINEER<span style="color:red">*</label>
                                            <select id="myengineer" class="form-control" name="engineerselect" required>
                                                
                                            </select>
                                        </div>
                                             
                                           
                                       
                                </div>
                                            
                                <div class="col-lg-4">
                                    
                                        <div class="form-group">
                                            <label>DEALER NAME<span style="color:red">*</label>
                                            <select id="mydealer" class="form-control" name="del_name" required>
                                                
                                            </select>
                                        </div>    
                                    
                                        <div class="form-group">
                                            <label>CUSTOMER NAME<span style="color:red">*</label>
                                            <input name="cus_name" type="text" value="<%=custName%>" class="form-control" required>
                                        </div>
                                    
                                        <div class="form-group">
                                                <label>SUPPLIER<span style="color:red">*</label>
                                                <select class="form-control" name="sup" required>
                                                <%
                                                    list=SupplierDao.getAllSuppliers();
                                                    itr=list.iterator();
                                                    while(itr.hasNext())
                                                    {
                                                           %> <option value="<%=itr.next()%>"><%=itr.next()%></option><%
                                                    }
                                                %>
                                                </select>
                                        </div>
                                    
                                        <div class="form-group">
                                                <label>MODEL<span style="color:red">*</label>
                                                <select class="form-control" name="model" value="<%=productModel%>" required>
                                                <%
                                                    list=ModelDao.getAllModelsofdivision(divid);
                                                    itr=list.iterator();
                                                    while(itr.hasNext())
                                                    {
                                                           %> <option value="<%=itr.next()%>"><%=itr.next()%></option><%
                                                    }
                                                %>
                                                </select>
                                        </div>
                                    
                                        <div class="form-group">
                                            <label>UNIT SL NO<span style="color:red">*</label>
                                            <input name="unit_sl_no" type="text" value="<%=unitSlno%>" class="form-control" required>
                                        </div>
                                    
                                        <div class="form-group">
                                            <label>DOD<span style="color:red">*</label>
                                            <input type="text" name="dod" id = "datepicker-4" class="form-control" value="<%=dod%>" required readonly> 
                                        </div>
                                    
                                        <div class="form-group">
                                            <label>UNIT STATUS<span style="color:red">*</label>
                                            <select class="form-control" name="unit_status" required>
                                                <%
                                                    list=DropdownDao.getAlldp("2");
                                                    itr=list.iterator();
                                                    while(itr.hasNext())
                                                    {
                                                         %> <option value="<%=itr.next()%>"><%=itr.next()%></option><%
                                                    }
                                                %>
                                            </select>
                                        </div>
                                    
                                        <div class="form-group">
                                            <label>MOD/BRD NAME<span style="color:red">*</label>
                                            <input name="mod_name" type="text" value="<%=ModBrdName%>" class="form-control" required>
                                        </div>
                                    
                                        <div class="form-group">
                                            <label>DEF MOD/BRD NAME<span style="color:red">*</label>
                                            <input name="def_name" type="text" value="<%=defModBrdName%>" class="form-control" required >
                                        </div>
                                    
                                        <div class="form-group">
                                            <label>DEF TYPE<span style="color:red">*</label>
                                            <select class="form-control" name="def_type" required>
                                                <%
                                                    list=DropdownDao.getAlldp("3");
                                                    itr=list.iterator();
                                                    while(itr.hasNext())
                                                    {
                                                       %> <option value="<%=itr.next()%>"><%=itr.next()%></option><%
                                                    }
                                                %>
                                            </select>
                                        </div>
                                    
                                        <div class="form-group">
                                            <label>TYPE OF ACC<span style="color:red">*</label>
                                            <select class="form-control" name="type_acc" required>
                                                <%
                                                   list=DropdownDao.getAlldp("4");
                                                    itr=list.iterator();
                                                    while(itr.hasNext())
                                                    {
                                                      %> <option value="<%=itr.next()%>"><%=itr.next()%></option><%
                                                    }
                                                %>
                                            </select>
                                        </div>
                                </div>
                                            
                                            
                                            
                                            
                                            
                                <div class="col-lg-4">
                                    
                                    <div class="form-group">
                                            <label>DEF PART SNO<span style="color:red">*</label>
                                                <input name="def_sn_no" type="text" value="<%=defPartSn%>" class="form-control" required>
                                        </div>
                                    
                                        <div class="form-group">
                                            <label>DEF GIR NO<span style="color:red">*</label>
                                            <input name="def_gir_no" type="text" value="<%=defGirNo%>" class="form-control" required>
                                        </div>
                                    
                                        <div class="form-group">
                                            <label>REP TYPE<span style="color:red">*</label>
                                            <select class="form-control" name="rep_type" required>
                                            <%
                                                   list=DropdownDao.getAlldp("6");
                                                    itr=list.iterator();
                                                    while(itr.hasNext())
                                                    {
                                                       %> <option value="<%=itr.next()%>"><%=itr.next()%></option><%
                                                    }
                                            %>
                                        </SELECT>
                                        </div>
                                    
                                        <div class="form-group">
                                            <label>REP GIR SNO</label>
                                            <input name="rep_gir_sno" type="text" value="<%=repGirNo%>" class="form-control">
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>DEF UNIT GIR NO</label>
                                            <input name="def_unit_gir_no" type="text" value="<%=defUnitGirNo%>" class="form-control">
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>FIELD REMARKS<span style="color:red">*</label>
                                            <input name="field_remarks" type="text" value="<%=fieldRemarks%>" class="form-control" required>
                                        </div>
                                        <div class="form-group">
                                            <label>TYPE OF WORK/STATUS<span style="color:red">*</label><%=typeOfWork%>
                                            <select class="form-control" name="type_work" value="<%=typeOfWork%>" required>
                                                <%
                                                   list=DropdownDao.getAlldp("5");
                                                    itr=list.iterator();
                                                    while(itr.hasNext())
                                                    {
                                                       %> <option value="<%=itr.next()%>" ${itr.next == typeOfWork ? 'selected="selected"' : ''}><%=itr.next()%></option><%
                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>SHIP DATE FROM SERVICE CENTER</label>
                                            <input name="ship_date" type="text"  id = "datepicker-13" value="<%=shipDtFrmSerCntr%>" class="form-control" readonly >
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>DISP ADVICE NO</label>
                                            <input name="disp_advice_no" type="text" value="<%=dispAdvNo%>" class="form-control">
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>SHIP DATE FROM COMMERCIAL</label>
                                            <input name="commercial_date" type="text" id = "datepicker-14" class="form-control" value="<%=shipDateFromCommercial%>" readonly>
                                        </div>
                                        
<!--                                        <div class="form-group">
                                            <label>DC NO</label>
                                            <input name="dc_no" type="text" value="<%=dcNo%>" class="form-control">
                                        </div>-->
                                        
                                        <div class="form-group">
                                            <label>COMMERCIAL DETAILS INVOICE REMARKS</label>
                                            <input name="invoice" type="text" value="<%=comrclDtlInvRmrk%>" class="form-control">
                                        </div>
                                </div>
                                        <div>
                                            <center><button type="submit" class="btn btn-success">SAVE</button></center>
                                              
                                        </div>
                                </form>  
                                </div>
                                <!-- /.col-lg-6 (nested) -->
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

<!--    </div>-->
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
                dateFormat: 'dd-mm-yy' });
            $( "#datepicker-3" ).datepicker("show");
         });
          $(function() {
            $( "#datepicker-4" ).datepicker({
                 changeMonth: true,
			changeYear: true,
                dateFormat: 'dd-mm-yy' });
            $( "#datepicker-4" ).datepicker("show");
         });
         $(function() {
            $( "#datepicker-15" ).datepicker({ 
                 changeMonth: true,
			changeYear: true,
                dateFormat: 'dd-mm-yy' });
            $( "#datepicker-15" ).datepicker("show");
         });
      </script>
</body>

</html>
