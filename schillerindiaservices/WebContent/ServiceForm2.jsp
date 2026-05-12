
<%@page import="org.apache.poi.util.SystemOutLogger"%>
<%@page import="com.schillerindiaservices.bean.Dropdownmaster"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.schillerindiaservices.Dao.UnitStatusDao"%>
<%@page import="com.schillerindiaservices.bean.Emploeemaster"%>
<%--<%@ page errorPage="error.jsp" %>--%>  
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
<%@page import="com.schillerindiaservices.Dao.DropdownDao"%>
<%@page import="com.schillerindiaservices.Dao.ServiceDao"%>
<%@page import="com.schillerindiaservices.bean.service_master"%>
<%@page import="java.sql.Date"%>


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

    <!-- Custom Fonts -->
    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link rel="icon" type="image/png" href="img/logo.png"/>
    <link href = "dist/css/jquery-ui.css" rel = "stylesheet">
    


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
            String name=(String)session.getAttribute("logname");
           // String lid=(String)session.getAttribute("logid");
            System.out.println("the namee isss11"+name+"lidd iss");
            if("admin".equalsIgnoreCase(role))
            {   
                %><%@include file="admindashboard.jsp" %><%
            }
else if(role.equalsIgnoreCase("ViceChancellor"))
{
%>
<%@include  file="VPDashboard.jsp" %>
<%
}          else
            {   
                %><%@include file="engineerdashboard.jsp" %><%
            }
        %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Update Service</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Update the details of the Service 
                        </div>
                        <div class="panel-body">
                            <form role="form" name="vinform" action="Service_UpdateController" method="post" >
                                <%
                                                      int id=0;
                                                      String admbranch="";
                                                      String division="";
                                                      String scRefNo="";
                                                      String scEngnr="";
                                                      String raEngnr="";
                                                      String frnNo="";
                                                      String docketNo="";
                                                      String despatchDate="";
                                                      String custName="";
                                                      String productModel="";
                                                      String unitStatus="";
                                                      String defModBrdName="";
                                                      String defGirNo="";
                                                       String repGirNo="";
                                                          String defUnitGirNo="";
                                                          String technicalRemarks="";
                                                          String componentsUsedforRepair="";
                                                          String repairedBrdStkDate=null;
                                                          String finalRemarks="";
                                                          String typeOfWork="";
                                                          String shipDtFrmSerCntr=null;
                                                          String dispAdvNo="";
                                                          String shipDateFromCommercial=null;
                                                          String dcNo="";
                                                          String comrclDtlInvRmrk="";
                                                          String prod_name="";
                                                          String unit_staus="";
                                                          String work_type="";
                                                          String repairedBrdAdvNo="";
                                                         int raid=0;
                                                         String report_type="";
                                                         String Reptype="";
                                                         String destination="";
                                                          
                                                          int lid=Integer.parseInt(session.getAttribute("loguserid").toString());
                                         
                                            if(request.getParameter("id")!=null)
                                            {
                                                        DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
                                                        id=Integer.parseInt(request.getParameter("id"));
                                                        service_master d=ServiceDao.getById(id);
                                                        scRefNo=d.getScRefNo();
                                                        int scid=d.getScEngnr();  
                                                        scEngnr=EmployeeDao.getEmpName(scid);   
                                                         raid=d.getRaEngnr();
                                                         System.out.println("the ra enggg isss"+raid);
                                                        raEngnr=EmployeeDao.getEmpName(raid);   
                                                        System.out.println("the ra enggg isss"+raEngnr);
                                                        frnNo=d.getFrnNo();
                                                        docketNo=d.getDocketNo();
                                                        despatchDate=d.getDespatchDate();
                                                        custName=d.getCustName();
                                                        productModel=d.getProductModel();
                                                        prod_name=ModelDao.getModelname(productModel);
                                                        unitStatus=d.getUnitStatus();
                                                        unit_staus=UnitStatusDao.getUnitName(Integer.parseInt(unitStatus));
                                                        System.out.println("the unit status isss"+unit_staus);
//                                                        ModBrdName=d.getModBrdName();
                                                        defModBrdName=d.getDefModBrdName();
                                                        defGirNo=d.getDefGirNo();
                                                        defUnitGirNo=d.getDefUnitGirNo();
                                                        technicalRemarks=d.getTechnicalRemarks();
                                                        repairedBrdStkDate=d.getRepairedBrdStkDate();
                                                        finalRemarks=d.getFinalRemarks();
                                                        typeOfWork=d.getTypeOfWork(); 
                                                        work_type=UnitStatusDao.getUnitName(Integer.parseInt(typeOfWork));
                                                        shipDtFrmSerCntr=d.getShipDtFrmSerCntr();
                                                        System.out.println("the ship date iusss isss"+shipDtFrmSerCntr);
                                                        dispAdvNo=d.getDispAdvNo();
                                                        shipDateFromCommercial=d.getShipDateFromCommercial();  
                                                        if(d.getDcNo()!=""&&d.getDcNo()!=null){
                                                        	dcNo=d.getDcNo();
                                                        }
                                                        
                                                        comrclDtlInvRmrk=d.getComrclDtlInvRmrk();
                                                        division=EmployeeDao.getempdivision(lid);
                                                        technicalRemarks=d.getTechnicalRemarks();
                                                        repairedBrdStkDate=d.getRepairedBrdStkDate();
                                                     //   System.out.println("the rep stkdte iss"+repairedBrdStkDate);
                                                        finalRemarks=d.getFinalRemarks();
                                                        admbranch=d.getBranch();
                                                        componentsUsedforRepair=d.getComponentsUsedforRepair();
                                                        repairedBrdAdvNo=d.getRepairedBrdAdvNo();
                                                        repGirNo=d.getRepGirNo();
                                                      //  System.out.println("the rep gir iss iss"+repGirNo);
                                                        report_type=d.getReport_type();
                                                        Reptype=d.getRepType();
                                                        destination=d.getDestination();
                                                  //    System.out.println(d.getDestination()+"the dest isss isss");  
                                                        
                                            }
                                     %>
                                <div class="row">
                                    <div class="col-lg-4">
                                    <input type="hidden" name="id" value="<%=id%>" class="form-control"> 
                                        <div class="form-group" >
                                            <label> SC_REF_NO</label>&nbsp;&nbsp;&nbsp;:
                                            <label> <span style="font-weight:normal;color:blue"><%=scRefNo%></span></label>
                                        </div>
                                            
                                        <div class="form-group">
                                            <label> SC ENGINEER</label>:
                                            <label><span style="font-weight:normal;color:blue"><%=scEngnr%></label>
                                        </div>
                                        <div class="form-group">
                                            <label> FRN NO</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:
                                             <label><span style="font-weight:normal;color:blue"><%=frnNo%></label>
                                        </div>
                                    </div>
                                    <div class="col-lg-4">
                                        <div class="form-group">
                                            <label> CUSTOMER NAME</label>:
                                             <label><span style="font-weight:normal;color:blue"><%=custName%></label>
                                            <!--<input class="form-control" value="" readonly >-->
                                        </div>
                                        <div class="form-group">
                                            <label> MODEL</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:
                                             <label> <span style="font-weight:normal;color:blue"><%=prod_name%></label>
                                            <!--<input class="form-control" value="" readonly >-->
                                        </div>
                                        <div class="form-group">
                                            <label> UNIT STATUS</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:
                                             <label><span style="font-weight:normal;color:blue"><%=unit_staus%></label>
                                            <!--<input class="form-control" value="" readonly >-->
                                        </div>
                                    </div>
                                    <div class="col-lg-4">
                                        <div class="form-group">
                                            <label> DEF GIR NO</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:
                                             <label><span style="font-weight:normal;color:blue"><%=defGirNo%></label>
                                                   <input name="def_gir_no" type="hidden" id="defgir" value="<%=defGirNo%>" class="form-control" readonly="">
                                             
                                            <!--<input class="form-control" value="" readonly >-->
                                        </div>
                                        <div class="form-group">
                                            <label> DEF MOD/BRD NAME</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:
                                             <label><span style="font-weight:normal;color:blue"><%=defModBrdName%></label>
                                            <!--<input class="form-control" value="" readonly >-->
                                        </div>
                                        
                                        <div class="form-group">
                                            <label> TYPE OF WORK/STATUS</label>:
                                             <label><span style="font-weight:normal;color:blue"><%=work_type%></label>
                                            <!--<input class="form-control" value="" readonly >-->
                                        </div>
                                    </div>
                                </div>
                                <hr>        
                                <div class="row">
                                <div class="col-lg-4">
                                    <%if(raEngnr.isEmpty()||raEngnr.length()==0){
                                    	%>
                                    	 <div class="form-group">
                                            <label> RA ENGINEER</label>
                                            <select class="form-control" type="text" value="<%=name%>" name="ra_engineer" required="">
                                                 <option value="<%=lid%>"><%=name%></option>
                                                <%
                                                    List<String> list=null;
                                                    Iterator itr=null;
                                                    if("admin".equalsIgnoreCase(role))
                                                    {   
                                                      
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

                                                       if(raid==0)
                                                       {
                                                       
                                                   %> 
                                                   <option value="<%=eid%>"><%=ename%></option><%
                                                       }
                                                     else
                                                   {  
                                                   %>  <option value="<%=eid%>" <% if(eid==raid){out.print("selected='selected'");}%> ><%=ename%></option><%
                                                    }
                                                    }  
                                                    }
                                                      
                                                    }
                                                    else
                                                    {   
                                                      int divid=ServiceDao.getDivisionId(id);
                                                        int eid=0;
                                                      String ename="";
                                             List<Emploeemaster> elist=EmployeeDao.getAllEmpdiv(divid);
                                            if(elist!=null)
                                            {
                                                Iterator eitr=elist.iterator();
                                                while(eitr.hasNext())
                                                {
                                                    Emploeemaster e=(Emploeemaster)eitr.next();
                                                    eid=e.getEmpId();
                                                    ename=e.getEmpName();
                                                    
                                                   if(raid==0)
                                                   {
                                                       
                                                   %> 
                                                   <option value="<%=eid%>"><%=ename%></option><%
                                                   }
                                                     else
                                                   {  
                                                   %>  <option value="<%=eid%>" <% if(eid==raid){out.print("selected='selected'");}%> ><%=ename%></option><%
                                                    }
                                                }  
                                             }
                                                    }
                                                    
                                                %>
                                                
                                            </select>
                                        </div>
                                    	<%
                                    }else{
                                    	%> <div class="form-group">
                                            <label> RA ENGINEER</label>
                                            <select class="form-control" type="text" value="<%=raEngnr%>" name="ra_engineer" required="">
                                                 
                                                <%
                                                    List<String> list=null;
                                                    Iterator itr=null;
                                                    if("admin".equalsIgnoreCase(role))
                                                    {   
                                                      
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

                                                       if(raid==0)
                                                       {
                                                       
                                                   %> 
                                                   <option value="<%=eid%>"><%=ename%></option><%
                                                       }
                                                     else
                                                   {  
                                                   %>  <option value="<%=eid%>" <% if(eid==raid){out.print("selected='selected'");}%> ><%=ename%></option><%
                                                    }
                                                    }  
                                                    }
                                                      
                                                    }
                                                    else
                                                    {   
                                                      int divid=ServiceDao.getDivisionId(id);
                                                        int eid=0;
                                                      String ename="";
                                             List<Emploeemaster> elist=EmployeeDao.getAllEmpdiv(divid);
                                            if(elist!=null)
                                            {
                                                Iterator eitr=elist.iterator();
                                                while(eitr.hasNext())
                                                {
                                                    Emploeemaster e=(Emploeemaster)eitr.next();
                                                    eid=e.getEmpId();
                                                    ename=e.getEmpName();
                                                    
                                                   if(raid==0)
                                                   {
                                                       
                                                   %> 
                                                   <option value="<%=eid%>"><%=ename%></option><%
                                                   }
                                                     else
                                                   {  
                                                   %>  <option value="<%=eid%>" <% if(eid==raid){out.print("selected='selected'");}%> ><%=ename%></option><%
                                                    }
                                                }  
                                             }
                                                    }
                                                    
                                                %>
                                                
                                            </select>
                                        </div>
                                    	
                                    	
                                    	<% 
                                    }
                                    %>
                                       
                                        <div class="form-group">
                                            <!-- <label>DOCKET NO</label> -->
                                            <input name="doc_no" type="hidden" min="1" max="50" value="<%=((docketNo==null)?"":docketNo)%>" class="form-control" >
                                        </div>
                                        
                                         <div class="form-group">
                                            <label>DEF UNIT GIR NO</label>
                                            <input name="def_unit_gir_no" type="text" min="1" max="50" value="<%=defUnitGirNo%>" class="form-control">
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>TECHNICAL REMARKS</label>
                                            <input name="tech_remarks" type="text" min="1" max="100" value="<%=((technicalRemarks==null)?"":technicalRemarks)%>" class="form-control" >
                                        </div>
                                        <%-- <div class="form-group">
                                            <label>DISPATCH DATE FROM FIELD<span style="color:red">*</label>
                                            <input type="text" id = "datepicker-14" name="desp_frm_date" value="<%=despatchDate%>" class="form-control" required='true' onpaste="return false;">
                                        </div> --%>
                                          <div class="form-group">
                                            <label>COMPONENTS USED TO REPAIR</label>
                                            <input name="comp_repair" type="text" min="1" max="50" value="<%=((componentsUsedforRepair==null)?"":componentsUsedforRepair)%>" class="form-control">
                                        </div>
                                </div>
                                
                                <div class="col-lg-4">
                                    
                                     
                                        
                                       
                                        
                                        <div class="form-group">
                                            <label>REPAIRED BRD STK DATE<span style="color:red" class="label1">*</label>
                                            <input name="repair_stk_date" type="text" id = "datepicker-15" onchange="repairedDate()" value="<%=repairedBrdStkDate%>" class="form-control" required readonly>
                                        </div>
                                        <div class="form-group">
                                            <!-- <label>STOCK ADV NO</label> -->
                                            <input name="repaired_brd_adv_no" type="hidden" id="stockno" value="<%=repairedBrdAdvNo%>" class="form-control" readonly="">
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>FINAL REMARKS</label>
                                            <input name="final_remarks" type="text" min="1" max="100" value="<%=finalRemarks%>" class="form-control">
                                        </div>
                                         <%
                                         if(((unit_staus.equalsIgnoreCase("OW") || unit_staus.equalsIgnoreCase("LAMC"))&&(shipDtFrmSerCntr.isEmpty()||shipDtFrmSerCntr.length()==0) )) 
                                          
                                           {
                                        	   System.out.println("the expectedd elseee000");
                                               %>
                                       <div class="form-group">
                                            <label>TYPE OF WORK/STATUS_OB<span style="color:red">*</label>
                                            <select class="form-control" name="type_work" id="work_type" value="<%=typeOfWork%>" onchange="changefun5_save();change_scrapp1()">
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
                                                   if(typeOfWork=="")
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
                                          <%
                                           }else  if((shipDtFrmSerCntr.isEmpty()||shipDtFrmSerCntr.length()==0)&&(unit_staus!="OW" && unit_staus!="LAMC")){
                                        	   System.out.println("the expectedd elseee111");
                                        	%>
                                        	 <div class="form-group">
                                            <label>TYPE OF WORK/STATUS_PFRN<span style="color:red">*</label>
                                            <select class="form-control" name="type_work" value="<%=typeOfWork%>" id="work_type" onchange="changefun5_save();change_scrapp1()" >
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
                                                   if(typeOfWork=="")
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
                                        	
                                        	
                                        	<%    
                                           }
                                              else if((!shipDtFrmSerCntr.isEmpty()||shipDtFrmSerCntr.length()>=0)&&(repairedBrdStkDate.isEmpty() || repairedBrdStkDate.length()==0)) {
                                        	   System.out.println("the expectedd elseee");
                                          %>
                                           <div class="form-group">
                                            <label>TYPE OF WORK/STATUS_UR</label>
                                            <select class="form-control" name="type_work" id="work_type" value="<%=typeOfWork%>"   onchange="changefun5();change_scrapp()">
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
                                                   if(typeOfWork=="")
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
                                        <%
                                           }
                                        %> 
                                        
                                       
                                        
                                        <div class="form-group">
                                            <label>SHIP DATE FROM SERVICE CENTER<span style="color:red" class="label2">*</label>
                                            <input name="ship_date" type="text" id = "datepicker-16" onchange="shipDate()" value="<%=shipDtFrmSerCntr%>" class="form-control" required readonly>
                                        </div>
                                        
                                        <div class="form-group">
                                          <!--   <label>DISP ADVICE NO</label> -->
                                            <input name="disp_advice_no" type="hidden" id="dispadno" min="1" max="50" value="<%=dispAdvNo%>" class="form-control" readonly="">
                                        </div>
                                        
                                           
                                       <%
                                       if(((unit_staus.equalsIgnoreCase("OW") || unit_staus.equalsIgnoreCase("LAMC"))&&(shipDtFrmSerCntr.isEmpty()||shipDtFrmSerCntr.length()==0) ))  
                                          
                                           {
                                        	   System.out.println("the expectedd elseee000 rre comm");
                                               %>
                                                <div class="form-group">
                                            <label>SHIP DATE FROM COMMERCIAL</label>
                                            <input name="commercial_date" type="text" onchange="comercialDate()" id = "datepicker-17" value="<%=shipDateFromCommercial%>" class="form-control"  readonly="">
                                        </div>
                                          <%
                                           }else  if((shipDtFrmSerCntr.isEmpty()||shipDtFrmSerCntr.length()==0)&&(unit_staus!="OW" && unit_staus!="LAMC")){
                                        	   System.out.println("the expectedd elseee111 sdfd comm");
                                        	%>
                                        	<div class="form-group">
                                            <label>SHIP DATE FROM COMMERCIAL</label>
                                            <input name="commercial_date" type="text" onchange="comercialDate()" id = "datepicker-17" value="<%=shipDateFromCommercial%>" class="form-control"  readonly="">
                                        </div>
                                        	
                                        	
                                        	<%    
                                           }
                                              else if((shipDtFrmSerCntr!=("")||shipDtFrmSerCntr.length()>0)&&(repairedBrdStkDate.isEmpty()||repairedBrdStkDate.length()==0)) {
                                        	   System.out.println("the expectedd elseee sefsf   comm"+shipDtFrmSerCntr);
                                          %>
                                          <div class="form-group">
                                            <label>SHIP DATE FROM COMMERCIAL</label>
                                            <input name="commercial_date" type="text" onchange="comercialDate()" id = "datepicker-17" value="<%=shipDateFromCommercial%>" class="form-control"  >
                                        </div>
                                        <%
                                           }else {
                                        	   System.out.println("the expectedd elseee2222 sefsf commm");
                                        	   %>
                                        	   <div class="form-group">
                                            <label>SHIP DATE FROM COMMERCIAL<span style="color:red" class="label5a">*</label>
                                            <input name="commercial_date" type="text" onchange="comercialDate();changefun5()" id = "datepicker-17" value="<%=shipDateFromCommercial%>" class="form-control" required >
                                        </div>
                                        	   
                                        	   <% 
                                           }
                                        %>  
                                        
                                        
                                        
                                </div>
                                <div class="col-lg-4">
                                        
                                       <%
                                       if(((unit_staus.equalsIgnoreCase("OW") || unit_staus.equalsIgnoreCase("LAMC"))&&(shipDtFrmSerCntr.isEmpty()||shipDtFrmSerCntr.length()==0) ))  
                                          
                                           {
                                        	   System.out.println("the expectedd elseee000 rre");
                                               %>
                                                 <div class="form-group">
                                            <label>DC NO</label>
                                            <input name="dc_no" type="text" id="dcnos" min="1" max="50" value="<%=dcNo%>" class="form-control" readonly="" >
                                        </div>
                                          <%
                                           }else  if((shipDtFrmSerCntr.isEmpty()||shipDtFrmSerCntr.length()==0)&&(unit_staus!="OW" && unit_staus!="LAMC")){
                                        	   System.out.println("the expectedd elseee111 sdfd");
                                        	%>
                                        	 <div class="form-group">
                                            <label>DC NO</label>
                                            <input name="dc_no" type="text" id="dcnos" min="1" max="50" value="<%=dcNo%>" class="form-control" readonly=""  >
                                        </div>
                                        	
                                        	
                                        	<%    
                                           }
                                              else if((shipDtFrmSerCntr!=("")||shipDtFrmSerCntr.length()>0)&&(repairedBrdStkDate.isEmpty()||repairedBrdStkDate.length()==0)) {
                                        	   System.out.println("the expectedd elseee sefsf");
                                          %>
                                          <div class="form-group">
                                            <label>DC NO</label>
                                            <input name="dc_no" type="text" id="dcnos" min="1" max="50" value="<%=dcNo%>" class="form-control" >
                                        </div>
                                        <%
                                           }else {
                                        	   System.out.println("the expectedd elseee2222 sefsf");
                                        	   %>
                                        	    <div class="form-group">
                                            <label>DC NO<span style="color:red" class="label_dc1">*</label>
                                            <input name="dc_no" type="text" id="dcnos" min="1" max="50" value="<%=dcNo%>" class="form-control" onkeyup="changefun5()"  required>
                                        </div>
                                        	   
                                        	   <% 
                                           }
                                        %>  
                                     
                                     
                                      
                                        
                                        <%
                                           if(role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("ViceChancellor"))
                                           {
                                        	   System.out.println("ROLE   :"+role);
                                               %>
                                               <label>REP GIR SNO_AD</label>
                                            <input name="rep_gir_sno" type="text" id="repgir"  min="1" max="50" value="<%=repGirNo%>" onkeyup="changefun1();changefun5();changefun5_save();changeupper()" class="form-control"  >
                                       
                                               <%
                                           }
else{
                                  String qid=request.getParameter("qid");
                                  System.out.println("qid---"+qid);
             if(qid.equalsIgnoreCase("1")||qid.equalsIgnoreCase("2"))
             {
                                        %>
                 <div class="form-group">
                                            <label>REP GIR SNO_pfrn&&OB<span style="color:red" class="label3">*</label>
                                            <input name="rep_gir_sno" id="repgir" type="text"  min="1" max="50" value="<%=repGirNo%>" onkeyup="changefun1();changefun5();changefun5_save();changeupper()" class="form-control"  required="">
                                        </div>
            <% }
else 
{
         %>
    <div class="form-group">
                                            <label>REP GIR SNO_UR</label>
                                            <input name="rep_gir_sno" id="repgir" type="text"  min="1" max="50" value="<%=repGirNo%>" onkeyup="changefun1();changefun5();changeupper()" readonly="" class="form-control" >
                                        </div>
                                        <%
                                            
}}
                                        %>
                                          <%
                                            if(((unit_staus.equalsIgnoreCase("OW") || unit_staus.equalsIgnoreCase("LAMC"))&&(shipDtFrmSerCntr.isEmpty()||shipDtFrmSerCntr.length()==0) )) 
                                          
                                           {
                                        	   System.out.println("the expectedd elseee000V    FIRSTTT111"+shipDtFrmSerCntr.length());
                                        	   
                                               %>
                                        <div class="form-group">
                                            <label>TYPE OF REPORT<span style="color:red">*</label>
                                            <select class="form-control" id="report_type" name="report_type" value="<%=report_type%>"  onchange="changefn2()"  required>
                                                 <%-- <option value="<%=report_type%>"><%=report_type%></option> --%>
                                               <!--   <option value="servicecenter">SERVICE CENTER</option> -->
                                               <!--  <option value="scraplist">SCRAP LIST</option>  -->
                                                  <option value="dispatchlist">DISPATCH LIST</option>
                                                   <!-- <option value="stocklist">STOCK LIST</option>  -->
                                            </select>
                                        </div>
                                          <%
                                           }else  if((shipDtFrmSerCntr.isEmpty()||shipDtFrmSerCntr.length()==0)&&(unit_staus!="OW" && unit_staus!="LAMC")){
                                        	   System.out.println("the expectedd elseee111");
                                        	%>
                                        	<div class="form-group">
                                            <label>TYPE OF REPORT<span style="color:red">*</label>
                                            <select class="form-control" id="report_type" name="report_type" value="<%=report_type%>"  onchange="changefn2()"  required>
                                                  <%-- <option value="<%=report_type%>"><%=report_type%></option> --%>
                                             <!--  <option value="servicecenter">SERVICE CENTER</option> -->
                                           <!--   <option value="scraplist">SCRAP LIST</option>  -->
                                                <option value="dispatchlist">DISPATCH LIST</option> 
                                                 <!--  <option value="stocklist">STOCK LIST</option>  --> 
                                            </select>
                                        </div>
                                        	
                                        	
                                        	<%    
                                           }
                                              else if((shipDtFrmSerCntr!=("")||shipDtFrmSerCntr.length()>0)&&(repairedBrdStkDate.isEmpty()||repairedBrdStkDate.length()==0)) {
                                        	   System.out.println("the expectedd elseee");
                                          %>
                                          <div class="form-group">
                                            <label>TYPE OF REPORT<span style="color:red">*</label>
                                            <select class="form-control" id="report_type" name="report_type" value="<%=report_type%>" onchange="changefn2()" required>
                                                <%--  <option value="<%=report_type%>"><%=report_type%></option> --%>
                                                 <option value="servicecenter" class="opt1">SERVICE CENTER</option>
                                                 <option value="scraplist" class="opt2">SCRAP LIST</option>
                                                 <!--  <option value="dispatchlist">DISPATCH LIST</option> -->
                                                  <option value="stocklist" class="opt3">STOCK LIST</option>  
                                            </select>
                                        </div>
                                        <%
                                           }else {
                                        	   System.out.println(" the   rep typee isss"+report_type);
                                        	   %>
                                        	    <label>REPORT TYPE</label>
                                      <input name="report_type" type="text" id="report_type1" min="1" max="50" value="<%=report_type%>" class="form-control" readonly=""  >
                                        	   
                                        	   <%
                                        	   
                                        	  
                                           }
                                        %>  
                                       <div class="form-group">
                                            <label>DESTINATION<span style="color:red">*</label>
                                            <input name="destination" id="destination" type="text" min="1" max="50" value="<%=destination%>" class="form-control" required readonly="">
                                        </div>
                                      
                                         </div>
                                </div>
                                </div>
                                    
                                        <div class="save_button">
                                            <center><button type="submit" class="btn btn-success">UPDATE</button></center>
                                        </div>
                                </form>
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
    <script src="dist/js/sb-admin-2.js"></script>
    <script src = "dist/js/jquery-1.12.4.js"></script>
      <script src = "dist/js/jquery-ui.js"></script>
    
    <script>

var request=new XMLHttpRequest();  
function fetch_branch_and_dealer()
{  
    var name=document.vinform.regionselect.value;  
    var url="getbranchanddealer.jsp?val="+name;  
   // alert(name);
    try
    {  
        request.onreadystatechange=function()
        {  
            if(request.readyState==4)
            {  
                var val=request.responseText;  
                document.getElementById('mylocation').innerHTML=val;  
            }  
        }//end of function  
        request.open("GET",url,true);  
        request.send();  
    }catch(e){alert("Unable to connect to server");}  
}  
    </script>
<script>
         $(function() {
            $( "#datepicker-14" ).datepicker({
                 changeMonth: true,
			changeYear: true,
                dateFormat: 'yy-mm-dd' });
            $( "#datepicker-14" ).datepicker("show");
         });
      </script>
      
       <script>
         $(function() {
            $( "#datepicker-15" ).datepicker({ 
                 changeMonth: true,
			changeYear: true,
                dateFormat: 'yy-mm-dd' });
            $( "#datepicker-15" ).datepicker("show");
         });
          $(function() {
            $( "#datepicker-16" ).datepicker({ 
                 changeMonth: true,
			changeYear: true,
                dateFormat: 'yy-mm-dd' });
            $( "#datepicker-16" ).datepicker("show");
         });
      </script>
      
       <script>
         $(function() {
            $( "#datepicker-17" ).datepicker({ 
                 changeMonth: true,
			changeYear: true,
                dateFormat: 'yy-mm-dd' });
            $( "#datepicker-17" ).datepicker("show");
         });
      </script>
      
      <script>
         function repairedDate()
         {
             var date=document.getElementById("datepicker-15").value;
             if(date=="")
             {
                 document.getElementById("stockno").readOnly=true;
             }
             else
             {
                 document.getElementById("stockno").required=true;
                 document.getElementById("stockno").readOnly=false;
             }
         }
         
         function shipDate()
         {
             var date=document.getElementById("datepicker-16").value;
             if(date=="")
             {
                  document.getElementById("dispadno").readOnly=true;
             }
              else
             {
                 document.getElementById("dispadno").required=true;
                 document.getElementById("dispadno").readOnly=false;
             }
         }
         
         function comercialDate()
         {
             var date=document.getElementById("datepicker-17").value;
             if(date=="")
             {
                  document.getElementById("dcnos").readOnly=true;
             }
              else
             {
                 document.getElementById("dcnos").required=true;
                 document.getElementById("dcnos").readOnly=false;
             }
         }
         
         
         function changefn(){
         	// alert("its workingg");
         	  
         	  var value=$("#report_type").val();
         	 // alert(value);
         	  if(value=="servicecenter"){
         // alert("yess");
         	 
         	 // $('.label1').hide();
         	 // document.getElementById("repgir").required = false;
         	  
         	/*   $('.repdiv').hide();
         	  $('.repdiv2').show(); */
           }else  {
         	//  alert("noo");
         	// $('.label1').show();
         	 // document.getElementById("repgir").required = true;
         	  /* $('.repdiv').show();
         	  $('.repdiv2').hide(); */
           }
         	   
           }
           
           function changefn2(){
         	//  alert("its working 22");
         	  var value=$("#report_type").val();
         	  if(value=="dispatchlist"){
         	//	  alert("yess");
         		  document.getElementById("destination").readOnly = false; 
         		 $("#destination").val('');
         		  
         	  }else{
         		  document.getElementById("destination").readOnly = true; 
         	  }
           }
           
           function changefun1(){
        	//   alert("first");
        	   var value=$("#defgir").val();
        	   var value1=$("#repgir").val();
        	  if(value1!=''&&value.toUpperCase()!=value1.toUpperCase()){
        		  document.getElementById("datepicker-15").readOnly = true; 
       		      document.getElementById("datepicker-16").readOnly = false; 
       		   //   document.getElementById("dcnos").readOnly = true;
       		//   document.getElementById("dcnos").required = false; 
       		   document.getElementById("datepicker-15").required=false;
       		   document.getElementById("datepicker-16").required=true;
       		 $('.label1').hide();
  		   $('.label2').show();
  		 $('.label_dc').hide();
  		 
       		   
        	  } else if(value.toUpperCase()==value1.toUpperCase()&&value1!=''){
        		   document.getElementById("datepicker-15").readOnly = false; 
        		   document.getElementById("datepicker-16").readOnly = false;
        		//   document.getElementById("dcnos").readOnly = true;
           	//	   document.getElementById("dcnos").required = false;  
        		   document.getElementById("datepicker-15").required=true;
        		   document.getElementById("datepicker-16").required=true;
        		   $('.label1').show();
        		   $('.label2').show();
        		   $('.label_dc').hide();
        	   }else{
        		   document.getElementById("datepicker-15").readOnly = true; 
        		   document.getElementById("datepicker-16").readOnly = true;
        		 //  document.getElementById("dcnos").readOnly = true;
           		//   document.getElementById("dcnos").required = false; 
        		   document.getElementById("datepicker-15").required=false;
        		   document.getElementById("datepicker-16").required=false;
        		   $('.label1').hide();
        		   $('.label2').hide();
        		   $('.label_dc').hide();
        	   }
           }
           function change_scrapp1(){
        	   //alert("yess");
        	   
        	   var value1=$("#repgir").val();
        	   var value=$("#defgir").val();
        	   var type=$("#work_type").val();
        	   
        	   if((value1.toUpperCase()==value.toUpperCase())&&(type==20||type==21||type==26||type==29)){
        		 //  alert("false00");
        		   document.getElementById("datepicker-15").readOnly = false; 
        		   document.getElementById("datepicker-16").readOnly = false;
        	//	   document.getElementById("dcnos").readOnly = false;
        	//	   document.getElementById("datepicker-17").readOnly = false;
        		   document.getElementById("datepicker-15").required=true;
        		   document.getElementById("datepicker-16").required=true;
        	//	   document.getElementById("datepicker-17").required=false;
        		//   document.getElementById("dcnos").required = false;
        		   $('.label1').show();
        		   $('.label2').show();
        		   $('.label_dc').hide();
        		   $('.label5').hide();
        		   
        		  /*  $('.opt1').hide();
        		   $('.opt2').show();
        		   $('.opt3').hide(); */
        		   $('.save_button').show();
        		   
        		   
        	   }else if((value1.toUpperCase()!=value.toUpperCase())&&(type==25)){
        		 //  alert("true1");
        		   document.getElementById("datepicker-15").readOnly = true; 
        		   document.getElementById("datepicker-16").readOnly = false;
        		//   document.getElementById("dcnos").readOnly = false;
        		//   document.getElementById("datepicker-17").readOnly = false;
        		   document.getElementById("datepicker-15").required=false;
        		   document.getElementById("datepicker-16").required=true;
        	//	   document.getElementById("dcnos").required = false;
        		//   document.getElementById("datepicker-17").required=false;
        		   $('.label1').hide();
        		   $('.label2').show();
        		   $('.label_dc').hide();
        		   $('.label5').hide();
        		   
        		  /*  $('.opt1').hide();
        		   $('.opt2').hide();
        		   $('.opt3').hide(); */
        		   $('.save_button').show();
        		   
        	   }else{
        		//   alert("false1");
        		   document.getElementById("datepicker-15").readOnly = false; 
        		   document.getElementById("datepicker-16").readOnly = true;
        		//   document.getElementById("dcnos").readOnly = false;
        		//   document.getElementById("datepicker-17").readOnly = false;
        		   document.getElementById("datepicker-15").required=true;
        		   document.getElementById("datepicker-16").required=false;
        	//	   document.getElementById("dcnos").required = false;
        		//   document.getElementById("datepicker-17").required=false;
        		   $('.label1').show();
        		   $('.label2').hide();
        		   $('.label_dc').hide();
        		   $('.label5').hide();
        		   
        		  /*  $('.opt1').hide();
        		   $('.opt2').hide();
        		   $('.opt3').show(); */
        		   
        		   $('.save_button').hide();
        	   }
        	   
           }
           
           function change_scrapp(){
        	   //alert("yess");
        	   
        	   var value1=$("#repgir").val();
        	   var value=$("#defgir").val();
        	   var type=$("#work_type").val();
        	   
        	   if(type==23){
        		  // alert("false00");
        		   document.getElementById("datepicker-15").readOnly = false; 
        		   document.getElementById("datepicker-16").readOnly = true;
        	//	   document.getElementById("dcnos").readOnly = false;
        	//	   document.getElementById("datepicker-17").readOnly = false;
        		   document.getElementById("datepicker-15").required=true;
        		   document.getElementById("datepicker-16").required=false;
        	//	   document.getElementById("datepicker-17").required=false;
        		//   document.getElementById("dcnos").required = false;
        		   $('.label1').show();
        		   $('.label2').hide();
        		   $('.label_dc').hide();
        		   $('.label5').hide();
        		   
        		   $('.opt1').hide();
        		   $('.opt2').show();
        		   $('.opt3').hide();
        		   $('.save_button').show();
        		   
        		   
        	   }else if(type==25){
        		 //  alert("true1");
        		   document.getElementById("datepicker-15").readOnly = true; 
        		   document.getElementById("datepicker-16").readOnly = true;
        		//   document.getElementById("dcnos").readOnly = false;
        		//   document.getElementById("datepicker-17").readOnly = false;
        		   document.getElementById("datepicker-15").required=false;
        		   document.getElementById("datepicker-16").required=false;
        	//	   document.getElementById("dcnos").required = false;
        		//   document.getElementById("datepicker-17").required=false;
        		   $('.label1').hide();
        		   $('.label2').show();
        		   $('.label_dc').hide();
        		   $('.label5').hide();
        		   
        		   $('.opt1').show();
        		   $('.opt2').hide();
        		   $('.opt3').hide();
        		   $('.save_button').hide();
        		   
        	   }else{
        		 //  alert("false1");
        		   document.getElementById("datepicker-15").readOnly = false; 
        		   document.getElementById("datepicker-16").readOnly = true;
        		//   document.getElementById("dcnos").readOnly = false;
        		//   document.getElementById("datepicker-17").readOnly = false;
        		   document.getElementById("datepicker-15").required=true;
        		   document.getElementById("datepicker-16").required=false;
        	//	   document.getElementById("dcnos").required = false;
        		//   document.getElementById("datepicker-17").required=false;
        		   $('.label1').show();
        		   $('.label2').hide();
        		   $('.label_dc').hide();
        		   $('.label5').hide();
        		   
        		   $('.opt1').hide();
        		   $('.opt2').hide();
        		   $('.opt3').show();
        		   
        		   $('.save_button').show();
        	   }
        	   
           }
           function  changefun4(){
        	   $('.label_dc').hide();
        	   var value1=$("#repgir").val();
        	   var value=$("#defgir").val();
        	   var type=$("#work_type").val();
        	   
        	   if(type==25){
        		   
        		  
        		   $('.save_button').hide();
        		   
        		   }else{
        			   
            		   
            		   $('.save_button').show();
        		   }
           }
           function changefun5_save(){
        	  //alert("a5");
        	   var type=$("#work_type").val();
        	
        	   var value1=$("#repgir").val();
        	   var value=$("#defgir").val();
        	  // alert(value+""+value1+""+type+"hgshabfabs")
        	   if((value1.toUpperCase()==value.toUpperCase())&&(type==20||type==21||type==26||type==29||type==30)){
        		  // alert("c1");
        		   $('.save_button').show();
        	   }else if((value1.toUpperCase()!=value.toUpperCase())&&(type==25)){
        		  // alert("c2");
        		   $('.save_button').show();
        	   }else{
        		//   alert("c elsee");
        		   $('.save_button').hide();
        	   }
        	   
        	   
           }
           function changefun5(){
        	//  alert("insidee");
        	   $("#report_type").val('');
        	   
        	   var dc=$("#dcnos").val();
        	   var date=$("#datepicker-17").val();
        	   
        	 //  alert(dc);
        	   if(dc==''||date==''){
        		//   alert("1st");
        		   $('.save_button').hide();
        	   }else if(dc!=''&&date!=''){
        		//  alert("2nd");
        		   $('.save_button').show();
        	   }
        	   
           }
           
           function changeupper(){
          	//  alert("caps");
          	 // alert($("#repgir").val());
          var x1=	$("#repgir").val().toUpperCase();
          //	alert(x1);
          	$("#repgir").val(x1);
          //	  $("#defir").toUpperCase();
            }
           window.onload=function(){
        	  changefun1();
        	
        	  change_scrapp(); 
        	  changefun5();
        	  changefun4();
         	//  changefn();
         	// changefn2();
           }
           
      </script>
      
        
     
</body>

</html>
