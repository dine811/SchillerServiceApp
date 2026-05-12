<%@page import="com.schillerindiaservices.Dao.BranchDao"%>
<!DOCTYPE html>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.schillerindiaservices.bean.Emploeemaster"%>
<%@page import="com.schillerindiaservices.Dao.EmployeeDao"%>
<%@page import="com.schillerindiaservices.Dao.ProductDao"%>
<%--<%@ page errorPage="error.jsp" %>--%>  
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SchillerIndia</title>
<link rel="icon" type="image/png" href="img/logo.png"/>
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
        <%@include file="admindashboard.jsp" %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Engineer List</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                                Details if Engineers
                        </div>
                       
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <form role="form" action="EmployeeController" method="POST">
                            <div align="Left">
                                <a href="EmployeeForm.jsp"><button type="button" class="btn btn-primary">Add New Employee</button></a>
                            </div>
                                 <br>
                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                            <th>Emp. Id</th>
                                            <th>Name</th>
                                            <th>Address</th>
                                            <th>Email</th>
                                            <th>Password</th>
                                            <th>Mobile</th>
                                            <th>Location</th>
                                            <th>Branch</th>
                                            <th>Division</th>
                                            <th>Role</th>
                                            <th>Active</th>
                                            <th>Edit/Delete</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%  
                                            int rowstatus=1;
                                            int id=0;
                                            String name="";
                                            String email="";
                                            String mobile="";
                                            String address="";
                                            String role="";
                                            String activestring="";
                                            String active="";
                                            String branch="";
                                            String divisionid="";
                                            String division="";
                                            String dept="";
                                            String pass="";
                                            List<Emploeemaster> list=EmployeeDao.getAllRecords();
                                            if(list!=null)
                                            {
                                                Iterator itr=list.iterator();
                                                while(itr.hasNext())
                                                {
                                                    Emploeemaster d=(Emploeemaster)itr.next();
                                                    
                                                    id=d.getEmpId();
                                                    name=d.getEmpName();
                                                    email=d.getEmpEmail();
                                                    mobile=d.getEmpMobile();
                                                    address=d.getEmpAddress();
                                                    role=d.getEmpRole();
                                                    active=d.getEmpActive();
                                                    dept=d.getEmpDept();
                                                    pass=d.getEmpPassword();
                                                    
                                                    if(active=="")
                                                        activestring="yes";
//                                                    else
//                                                        activestring="no";
                                                    branch=d.getEmpBranch();
                                                    String bran=BranchDao.getbName(branch);
                                                    divisionid=d.getEmpDivision();
                                                    if(role!="admin")
                                                    {
                                                    division=ProductDao.getProdName(divisionid);
                                                    }
                                                    else
                                                    {
                                                     division="";   
                                                    }
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
                                                    
                                                        <td><%=id%></td>
                                                        <td><%=name%></td>
                                                        <td><%=address%></td>
                                                        <td><%=email%></td>
                                                        <td><%=pass%></td>
                                                        <td><%=mobile%></td>
                                                        <td><%=dept%></td>
                                                        <td><%=bran%></td>
                                                        <td><%=division%></td>
                                                        <td><%=role%></td>
                                                        <td><%=active%></td>
                                                        <td><a href="EmployeeController?id=<%=id%>&action=edit">Edit</a>|<a href="EmployeeController?id=<%=id%>&action=delete">Delete</a></td>
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
