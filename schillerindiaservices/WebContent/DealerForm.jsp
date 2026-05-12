<!DOCTYPE html>
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
                response.sendRedirect("index.html");
            }
%>
        <%@include file="admindashboard.jsp" %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Add Dealer </h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                           Enter the details of Dealer
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <form role="form" action="DealerController" method="post">
                                    <div class="col-lg-6">
                                    <%
                                    int ID=0;
                                    String name  ="";
                                    String address="";
                                    String email ="";
                                    String mobile="";
                                    int region=0;

                                    if(request.getAttribute("editablerecord")!=null)
                                    {
                                        Dealermaster d1=(Dealermaster)request.getAttribute("editablerecord");
                                        ID=d1.getDealerId();
                                        name    =d1.getDealerName();
                                        address =d1.getDealerAddress();
                                        email   =d1.getDealerMail();
                                        mobile  =d1.getDealerPhone();
                                        region  =d1.getDealerRegion();
                                    }
                                    %>

                                        <input type="hidden" name="id" class="form-control" value="<%=ID%>">
                                        <div class="form-group">
                                                <label> Dealer Name</label>
                                                <input type="text" name="del_name" minlength="1" maxlength="40"  value="<%=name%>" class="form-control" placeholder="Enter name" required="">
                                        </div>
                                        <div class="form-group">
                                                <label> Dealer Address</label>
                                                <input type="text" name="del_add" minlength="1" maxlength="40" value="<%=address%>" class="form-control" placeholder="Enter address" required="">
                                        </div>
                                        <div class="form-group">
                                                <label>Dealer Email</label>
                                                <input type="email" name="del_email" value="<%=email%>" class="form-control" placeholder="Enter email-id" required="">
                                        </div>

                                    </div>
                                    <div>
                                        <div class="col-lg-6">
                                            <div class="form-group">
                                                <label>Dealer Mobile</label>
                                                <input type="text" name="del_mob" value="<%=mobile%>" pattern="[789][0-9]{9}" class="form-control" placeholder="Enter mobile no." required="">
                                            </div>

                                            <div class="form-group">
                                                <label>Region</label>
                                                <select class="form-control" value="<%=region%>" name="del_reg" required="">
                                                    <option>Select Region</option>
                                                    <%
                                                    int rid=0;
                                                    String rname="";
                                                    List<Region> dlist=RegionDao.getAllRecords();
                                                    if(dlist!=null)
                                                    {
                                                        Iterator sitr=dlist.iterator();
                                                        while(sitr.hasNext())
                                                        {
                                                           Region d=(Region)sitr.next();
                                                           rid=d.getRegionId();
                                                           rname=d.getRegionName();
                                                           if(region==0)
                                                           {
                                                           %>  <option value="<%=rid%>"><%=rname%></option><%
                                                           }
                                                             else
                                                           {  
                                                            %>  <option value="<%=rid%>" <% if(rid==region){out.print("selected='selected'");}%> ><%=rname%></option><%
                                                           }
                                                        }  
                                                     }
                                                    %>
                                                </select>
                                            </div>
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
