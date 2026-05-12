<!DOCTYPE html>
<%@page import="com.schillerindiaservices.bean.Mail_id_entry"%>
<%@ page errorPage="error.jsp" %>  
<%@page import="com.schillerindiaservices.bean.Dealermaster"%>
<%@page import="com.schillerindiaservices.Dao.DealerDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.schillerindiaservices.Dao.RegionDao"%>
<%@page import="com.schillerindiaservices.bean.Region"%>
<%@page import="com.schillerindiaservices.Dao.EmployeeDao"%>

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
            	System.out.println("the logname issss");
                response.sendRedirect("index.html");
            }
%>
        <%@include file="admindashboard.jsp" %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Add Mail_Id </h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                           Enter the Mail_Id Details
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <form role="form" action="AutoEsclationMail_Controller" method="post">
                                    <div class="col-lg-6">
                                    <%
                                    int ID=0;
                                    String name ="";
                                    String address="";
                                    String email="";
                                    String report_type="";
                                    String division="";
                                    int region=0;

                                    if(request.getAttribute("editablerecord")!=null)
                                    {
                                        Mail_id_entry d1=(Mail_id_entry)request.getAttribute("editablerecord");
                                        ID=d1.getMail_id_entry_id();
                                        email    =d1.getMail_id();
                                        report_type =d1.getReport_type();
                                        division=d1.getTemp1();
                                        		
                                    }
                                    %>

                                        <input type="hidden" name="id" class="form-control" value="<%=ID%>">
                                        <div class="form-group">
                                                <label>Email</label>
                                                <input type="email" name="email" value="<%=email%>" class="form-control" placeholder="Enter email-id" required="">
                                        </div>
                                        <div class="form-group">
                                                <label>TYPE OF REPORT</label>
                                                <select class="form-control" value="<%=report_type%>" name="report_type" required="">
                                                  <!--   <option>Select Region</option> -->
                                                      <option value="all">ALL</option>
                                                    <option value="scraplist">SCRAP LIST</option>
                                                    <option value="dispatchlist">DISPATCH LIST</option>
                                                    <option value="stocklist">STOCK LIST</option>
                                                    <option value="servicecenter">SERVICE CENTER</option>
                                                    <option value="nonsalepending">NON SALE PENDING</option>
                                                   <!--  <option value="prf/ob_completed">PRF/OB COMPLETED</option> -->
                                                    <option value="joblist">JOBLIST</option>
                                                    <option value="joblist_pending">JOBLIST PENDING</option>
                                                      <option value="under_repair">UNDER REPAIR</option>
                                                        <option value="ob_pending">OB PENDING</option>
                                                          <option value="pendingfrn"> PENDING FRN</option>
                                                           <option value="bir_pending"> BIR-PENDING</option>
                                                            <option value="jobsheet_pending"> JOBSHEET-PENDING</option>
                                                             <option value="prf/ob_completed"> COMPLETED-PRF/OB</option>
                                                               <option value="prf/ob_pending"> PENDING-PRF/OB</option>
                                                </select>
                                            </div>
                                        
                                <div class="form-group required">
                                                 <label class="control-label"> Division</label>
                                                 <select name="division" class="form-control"  value="<%=division%>"  >
                                                 <option value="all">ALL</option>
                                                     <%
                                                  List list=EmployeeDao.getAllDivi();
                                                   Iterator itr=list.iterator();
                                                    int i=0;
                                                    while(itr.hasNext())
                                                    {
                                                        %><option><%=itr.next()%></option><%
                                                    }
                                                %>
                                                   
                                                 </select>
                                             </div>
                                        
                                          
                                        <%-- <div class="form-group">
                                                <label> Dealer Address</label>
                                                <input type="text" name="temp2" minlength="1" maxlength="40" value="<%=address%>" class="form-control" placeholder="Enter address" required="">
                                        </div>
                                         --%>

                                    </div>
                                    <div>
                                        <div class="col-lg-6">
                                           

                                            <br/>
                                            <div align="center">
                                                <center><button type="submit" class="btn btn-success">SAVE</button></center>
                                            </div>           
                                        </div>
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

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>

</body>

</html>
