<!DOCTYPE html>
<%@ page errorPage="error.jsp" %>  
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.schillerindiaservices.bean.Companymaster"%>
<%@page import="com.schillerindiaservices.Dao.CompanyDao"%>
<%@page import="com.schillerindiaservices.Dao.ServiceDao"%>
<%@page import="com.schillerindiaservices.bean.service_master"%>
<%@page import="java.sql.Date"%>


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

    <!-- DataTables CSS -->
    <link href="vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

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
                    <h3 class="page-header">Service List</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Details of Services
                        </div>
                       
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <form role="form" >
                            <div align="Left">
                                <a href="ServiceForm.jsp"><button type="button" class="btn btn-primary">Add New Service</button></a>
                            
                                <%
                                    ServiceDao sd=new ServiceDao();
//                                    sd.Excel();
                                %>
                                <a href="#"><button type="button" class="btn btn-primary" onclick= "<% sd.Excel(); %>">Export Service List</button></a>
                            </div>
                                 <br>
                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                            <th>S.ID</th>
                                            <th>SC_REF_NO</th>
                                            <th>ENTRY DATE</th>
                                            <th>SC_ENGINEER</th>
                                            <th>RA_ENGINEER</th>
                                            <th>FRN_NO</th>
                                            <th>FRN_DATE</th>
                                            <th>DOCKET_NO</th>
                                            <th>DESPATCH_DATE_FROM_FIELD</th>
                                            <th>SER_COMM_INWARD_DATE</th>
                                            <th>RECEIVED_DATE_SER_CENTRE</th>
                                            <th>STK/CUST</th>
                                            <th>REGION</th>
                                            <th>BRANCH</th>
                                            <th>ENGINEER</th>
                                            <th>DEALER_NAME</th>
                                            <th>CUSTOMER_NAME</th>
                                            <th>SUPPLIER</th>
                                            <th>MODEL</th>
                                            <th>UNIT_SL_NO</th>
                                            <th>DOD</th>
                                            <th>UNIT_STATUS</th>
                                            <th>MOD/BRD_NAME</th>
                                            <th>DEF_MOD/BRD_NAME</th>
                                            <th>DEF_TYPE</th>
                                            <th>TYPE_OF_ACC</th>
                                            <th>DEF_PART_SNO</th>
                                            <th>DEF_GIR_NO</th>
                                            <th>REP_TYPE</th>
                                            <th>REP_GIR_NO</th>
                                            <th>DEF_UNIT_GIR_NO</th>
                                            <th>FIELD_REMARKS</th>
                                            <th>TECHNICAL_REMARKS</th>
                                            <th>COMPONENTS_USED_TO_REPAIR</th>
                                            <th>REPAIRED_BRD_STK_DATE</th>
                                            <th>FINAL REMARKS</th>
                                            <th>TYPE OF WORK</th>
                                            <th>SHIP_DATE_FROM_SERVICE_CENTRE</th>
                                            <th>DISP_ADVICE_NO</th>
                                            <th>SHIP_DATE_FROM_COMMERCIAL</th>
                                            <th>DC_NO</th>
                                            <th>COMMERCIAL_DETAILS_INVOICE_REMARKS</th>
                                            <%
                                                            if("admin".equalsIgnoreCase(role))
                                                            {   
                                                                %><th>Edit/Delete</th><%
                                                            }
                                                            else
                                                            {   
                                                                %><th>Update</th><%
                                                            }
                                            %>
                                            
                                    </tr>
                                </thead>
                                <tbody>
                                    
                                    <%
                                            int rowstatus=1;
                                                        int serId=0;
                                                        String scRefNo="";
                                                        Date entryDate=null;
                                                        String scEngnr="";
                                                        String raEngnr="";
                                                        String frnNo="";
                                                        Date frnDate=null;
                                                        String docketNo="";
                                                        Date despatchDate=null;
                                                        Date serCommInwardDate=null;
                                                        Date serCentreReceivedDate=null;
                                                        String stkCust="";
                                                        String region="";
                                                        String branch="";
                                                        Integer engineerId=0;
                                                        String dealerName="";
                                                        String custName="";
                                                        String supplierName="";
                                                        String productModel="";
                                                        String unitSlno="";
                                                        Date dod=null;
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
                                                        Date repairedBrdStkDate=null;
                                                        String finalRemarks="";
                                                        String typeOfWork="";
                                                        Date shipDtFrmSerCntr=null;
                                                        String dispAdvNo="";
                                                        Date shipDateFromCommercial=null;
                                                        String dcNo="";
                                                        String comrclDtlInvRmrk="";
                                            
                                            List<service_master> list=ServiceDao.getAllRecords();
                                            if(list!=null)
                                            {
                                                Iterator itr=list.iterator();
                                                while(itr.hasNext())
                                                {
                                                    service_master d=(service_master)itr.next();
                                                        serId=d.getSerId();
                                                        scRefNo=d.getScRefNo();
                                                        entryDate=(Date)d.getEntryDate();
                                                        scEngnr=d.getScEngnr();
                                                        raEngnr=d.getRaEngnr();
                                                        frnNo=d.getFrnNo();
                                                        frnDate=(Date) d.getFrnDate();
                                                        docketNo=d.getDocketNo();
                                                        despatchDate=(Date) d.getDespatchDate();
                                                        serCommInwardDate=(Date) d.getSerCommInwardDate();
                                                        serCentreReceivedDate=(Date) d.getSerCentreReceivedDate();
                                                        stkCust=d.getStkCust();
                                                        region=d.getRegion();
                                                        branch=d.getBranch();
                                                        engineerId=d.getEngineerId();
                                                        dealerName=d.getDealerName();
                                                        custName=d.getCustName();
                                                        supplierName=d.getSupplierName();
                                                        productModel=d.getProductModel();
                                                        unitSlno=d.getUnitSlno();
                                                        dod=(Date) d.getDod();
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
                                                        repairedBrdStkDate=(Date) d.getRepairedBrdStkDate();
                                                        finalRemarks=d.getFinalRemarks();
                                                        typeOfWork=d.getTypeOfWork();
                                                        shipDtFrmSerCntr=(Date) d.getShipDtFrmSerCntr();
                                                        dispAdvNo=d.getDispAdvNo();
                                                        shipDateFromCommercial=(Date) d.getShipDateFromCommercial();
                                                        dcNo=d.getDcNo();
                                                        comrclDtlInvRmrk=d.getComrclDtlInvRmrk();
                                                    
                                                    if(rowstatus==1)
                                                    {
                                                        %><tr class="odd gradeA"> <%
                                                        rowstatus=2;
                                                    }
                                                    else
                                                    {
                                                        %><tr class="even gradeA"> <%
                                                        rowstatus=1;
                                                    }
                                                    %>
                                                    
                                                        <td><%=serId%></td>
                                                        <td><%=scRefNo%></td>
                                                        <td><%=entryDate%></td>
                                                        <td><%=scEngnr%></td>
                                                        <td><%=raEngnr%></td>
                                                        <td><%=frnNo%></td>
                                                        <td><%=frnDate%></td>
                                                        <td><%=docketNo%></td>
                                                        <td><%=despatchDate%></td>
                                                        <td><%=serCommInwardDate%></td>
                                                        <td><%=serCentreReceivedDate%></td>
                                                        <td><%=stkCust%></td>
                                                        <td><%=region%></td>
                                                        <td><%=branch%></td>
                                                        <td><%=engineerId%></td>
                                                        <td><%=dealerName%></td>
                                                        <td><%=custName%></td>
                                                        <td><%=supplierName%></td>
                                                        <td><%=productModel%></td>
                                                        <td><%=unitSlno%></td>
                                                        <td><%=dod%></td>
                                                        <td><%=unitStatus%></td>
                                                        <td><%=ModBrdName%></td>
                                                        <td><%=defModBrdName%></td>
                                                        <td><%=defType%></td>
                                                        <td><%=typeOfAcc%></td>
                                                        <td><%=defPartSn%></td>
                                                        <td><%=defGirNo%></td>
                                                        <td><%=repType%></td>
                                                        <td><%=repGirNo%></td>
                                                        <td><%=defUnitGirNo%></td>
                                                        <td><%=fieldRemarks%></td>
                                                        <td><%=technicalRemarks%></td>
                                                        <td><%=componentsUsedforRepair%></td>
                                                        <td><%=repairedBrdStkDate%></td>
                                                        <td><%=finalRemarks%></td>
                                                        <td><%=typeOfWork%></td>
                                                        <td><%=shipDtFrmSerCntr%></td>
                                                        <td><%=dispAdvNo%></td>
                                                        <td><%=shipDateFromCommercial%></td>
                                                        <td><%=dcNo%></td>
                                                        <td><%=comrclDtlInvRmrk%></td>
                                                        <%
                                                            if("admin".equalsIgnoreCase(role))
                                                            {   
                                                                %><td><a href="ServiceController?id=<%=serId%>&action=edit">Edit</a>|<a href="ServiceController?id=<%=serId%>&action=delete">Delete</a></td><%
                                                            }
                                                            else
                                                            {   
                                                                %><td><a href="ServiceForm2.jsp?id=<%=serId%>&action=update">Update</a></td><%
                                                            }
                                                        %>
                                                        
                                                    </tr>
                                        <%
                                                }
                                            }
                                        %>
                                </tbody>
                            </table>
                            <!-- /.table-responsive -->
                            </form>
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

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="vendor/metisMenu/metisMenu.min.js"></script>

    <!-- DataTables JavaScript -->
    <script src="vendor/datatables/js/jquery.dataTables.min.js"></script>
    <script src="vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
    <script src="vendor/datatables-responsive/dataTables.responsive.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
            responsive: true
        });
    });
    </script>

</body>

</html>
