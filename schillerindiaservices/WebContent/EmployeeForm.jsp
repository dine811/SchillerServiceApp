<!DOCTYPE html>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.schillerindiaservices.bean.Emploeemaster"%>
<%@page import="com.schillerindiaservices.Dao.EmployeeDao"%>
<%@page import="com.schillerindiaservices.Dao.BranchDao"%>
<%@page import="com.schillerindiaservices.Dao.ProductDao"%>
<%@page import="com.schillerindiaservices.bean.Branch"%>
<%@page import="com.schillerindiaservices.bean.Productmaster"%>

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
                    <h3 class="page-header">Add Engineer</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Enter the details of Engineer
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                
                                <form role="form" action="EmployeeController" method="post">
                                    
                                <%
                                int ID=0;
                                String name  ="";
                                String address="";
                                String email ="";
                                String pass="";
                                String mobile="";
                                String role   ="";
                                String active="";
                                String branch="";
                                String dept="";
                                String products="";
                                String division="";


                                if(request.getAttribute("editablerecord")!=null)
                                {
                                    Emploeemaster d1=(Emploeemaster)request.getAttribute("editablerecord");
                                    ID=d1.getEmpId();
                                    name    =d1.getEmpName();
                                    address =d1.getEmpAddress();
                                    email   =d1.getEmpEmail();
                                    pass    =d1.getEmpPassword();
                                    mobile  =d1.getEmpMobile();
                                    role    =d1.getEmpRole();
                                    pageContext.setAttribute("role", role);
                                    active  =d1.getEmpActive();
                                     pageContext.setAttribute("active", active);
                                    branch  =d1.getEmpBranch();
                                    division=d1.getEmpDivision();
                                    dept=d1.getEmpDept().trim();
                                    pageContext.setAttribute("dept", dept);
                                }
                            %>
                                <div class="col-lg-4">
                                    <input type="hidden" name="id" class="form-control" value="<%=ID%>">
                                    <div class="form-group">
                                        <label> Employee Name</label>
                                        <input type="text" style=" text-transform:uppercase;" name="emp_name" class="form-control" minlength="1" maxlength="40" value="<%=name%>" placeholder="Enter name" required>
                                    </div>
                                    <div class="form-group">
                                        <label> Employee Address</label>
                                        <input type="text" name="emp_add" class="form-control" minlength="1" maxlength="40" value="<%=address%>" placeholder="Enter address" required>
                                    </div>
                                    <div class="form-group">
                                        <label> Employee Email</label>
                                        <input type="email" name="emp_email" value="<%=email%>"  class="form-control" placeholder="Enter email-id" required>
                                            
                                    </div>
                                    <div class="form-group">
                                        <label>Employee Password</label>
                                        <input name="emp_pass" type="password" class="form-control" minlength="8" maxlength="40" value="<%=pass%>" placeholder="Enter password" required>
                                    </div>
                                </div>
                                
                                <div class="col-lg-4">
                                    <div class="form-group">
                                        <label>Employee Mobile</label >
                                        <input type="text" name="emp_mob" pattern="[789][0-9]{9}" value="<%=mobile%>" class="form-control" placeholder="Enter mobile number" required>
                                    </div>
                                    <div class="form-group">
                                        <label>Employee Location</label> 
                                        
                                        <select name="dept" class="form-control"  placeholder="Select department"  required>
                                            <option value="">Select Location</option>
                                                <option value="InHouse" ${dept=='InHouse' ? 'selected':''}>InHouse</option>
                                            <option value="Field" ${dept=='Field' ? 'selected':''}>Field</option>
                                            
                                                            
                                         
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Employee Branch</label>
                                        <select name="emp_branch" class="form-control" value="<%=branch%>" placeholder="Select Employee branch" required>
                                            <option value="">Select Branch</option>
                                            <%
                                                      int bid=0;
                                                      String bname="";
                                                    List<Branch> slist=BranchDao.getAllRecords();
                                            if(slist!=null)
                                            {
                                                Iterator sitr=slist.iterator();
                                                while(sitr.hasNext())
                                                {
                                                    Branch d=(Branch)sitr.next();
                                                   bid=d.getBranchid();
                                                   bname=d.getBranchname();
                                                   if(branch=="")
                                                   {
                                                   %>  <option value="<%=bid%>"><%=bname%></option><%
                                                   }
                                                     else
                                                   {  
                                                    %>  <option value="<%=bid%>" <% if(bid==Integer.parseInt(branch)){out.print("selected='selected'");}%> ><%=bname%></option><%
                                                   }
                                                }  
                                             }
                                        %>
                                        </select>
                                    </div>
                                    <div class="form-group" >
                                        <label>Employee Role</label>
                                        <select name="emp_role" class="form-control" value="<%=role%>" placehoder="Select Type" required>
                                            <option value="">select role</option>
                                            <option value="Admin" ${role=='Admin' ? 'selected':''}>Admin</option>
                                            <option value="Engineer" ${role=='Engineer' ? 'selected':''}>Engineer</option>
                                            <option value="FQC" ${role=='FQC' ? 'selected':''}>FQC</option>
                                            <option value="ProductSupport" ${role=='ProductSupport' ? 'selected':''}>Product Support</option>
                                            <option value="ViceChancellor"${role=='ViceChancellor'?'selectd':''}>Vice Chancellor</option>
                                            <option value="ServiceCoordinator"${role=='ViceChancellor'?'selectd':''}>Service Coordinator</option>
                                             <option value="repairteam"${role=='repairteam'?'selectd':''}>Repair Team</option>
                                        </select>
                                    </div>
                                    
                                </div>
                                <div class="col-lg-4">
                                    <div class="form-group">
                                        <label>Employee Division</label>
                                        <select name="product" class="form-control" value="<%=division%>" placeholder="Select products" required="">
                                            <option value="">Select Division</option>
                                            <%
                                                      int did=0;
                                                      String dname="";
                                                    List<Productmaster> dlist=ProductDao.getAllRecords();
                                            if(dlist!=null)
                                            {
                                                Iterator ditr=dlist.iterator();
                                                while(ditr.hasNext())
                                                {
                                                    Productmaster d=(Productmaster)ditr.next();
                                                   did=d.getProdId();
                                                   dname=d.getProdName();
                                                   if(division=="")
                                                   {
                                                   %>  <option value="<%=did%>"><%=dname%></option><%
                                                   }
                                                     else
                                                   {  
                                                    %>  <option value="<%=did%>" <% if(did==Integer.parseInt(division)){out.print("selected='selected'");}%> ><%=dname%></option><%
                                                   }
                                                }  
                                             }

                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Employee Active</label>
                                        <select name="emp_active" class="form-control" value="<%=active%>" placeholder="Select Employee status" required>
                                            <option value="">select </option>
                                            <option value="Yes" ${active=='Yes' ? 'selected':''}>yes</option>
                                            <option value="No" ${active=='No' ? 'selected':''}>no</option>
                                        </select>
                                    </div>
                                </div>
                                <div align="center">
                                    <center><button type="submit" class="btn btn-success">SAVE</button></center>
                                </div>
                           </form>           
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
