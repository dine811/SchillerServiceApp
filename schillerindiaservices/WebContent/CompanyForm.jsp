<!DOCTYPE html>
<%@ page errorPage="error.jsp" %>  
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.schillerindiaservices.bean.Companymaster"%>
<%@page import="com.schillerindiaservices.Dao.CompanyDao"%>
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
        <%
            if(request.getAttribute("editablerecord")==null)
            {
                try
                {
                    CompanyDao comp=new CompanyDao();
                    if(comp.checkCompany()==1)
                    {
                        %><jsp:forward page="CompanyList.jsp"/><%
                            out.print("<center><h4 style=color:red>The company has been already created! You cannot create another company.<h4></center>" );
                    }
                }
                catch(Exception e)
                {

                }
            }
        %>
        <%@include file="admindashboard.jsp" %>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Add Company </h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                           Enter the details of the Company
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <form role="form" action="CompanyController" method="post">
                                <div class="col-lg-4">
                                <%
                                int ID=0;
                                String name  ="";
                                String address="";
                                String email ="";
                                String mobile="";
                                
                                if(request.getAttribute("editablerecord")!=null)
                                {
                                    Companymaster d1=(Companymaster)request.getAttribute("editablerecord");
                                    ID=d1.getCompId();
                                    name    =d1.getCompName();
                                    address =d1.getCompAddress();
                                    email   =d1.getCompEmail();
                                    mobile  =d1.getCompPhone();
                                }
                            %>
                                        
                                <input type="hidden" name="id" class="form-control" value="<%=ID%>">
                                        
                                <div class="form-group">
                                            <label>Company Name</label>
                                            <input type="text" name="comp_name"  value="<%=name%>" class="form-control" min="1" max="40" placeholder="Enter Company Name" required>
                                </div>
                                <div class="form-group">
                                            <label>Company Address</label>
                                            <input type="text" name="comp_add" value="<%=address%>" class="form-control" min="1" max="100" placeholder="Enter Company Address" required>
                                </div>
                                <div class="form-group">
                                            <label>Company Email</label>
                                            <input type="email" name="comp_email" value="<%=email%>" class="form-control" min="1" max="50" placeholder="Enter Company Email" required>
                                </div>
                                <div class="form-group">
                                            <label>Company Mobile</label>
                                            <input type="text" name="comp_mob" value="<%=mobile%>" class="form-control" pattern="[789][0-9]{9}" placeholder="Enter Company Mobile" required>
                                </div>
                                </div>
                                <div class="col-lg-8">
                                    <div class="form-group">
                                                    <div class="panel panel-info">
                                                        <div class="panel-heading">
                                                            Enter the regions for this company.
                                                        </div>
                                                        <div class="panel-body">
                                                            <TABLE id="dataTable" width="100%" class="table table-striped table-bordered table-hover" border="0">
                                                                    <tr>
                                                                        <td><b>Select</b></td>
                                                                        <td><b>Region Name</b></td>
                                                                        <td><b>Region Description</b></td>
                                                                        <%
                                                                                if(ID!=0)
                                                                                {
                                                                                    %>
                                                                                        <td><b>Delete</b></td>
                                                                                    <%
                                                                                }
                                                                        %>
                                                                    </tr>
                                                                    <%  
                                                                        int regid=0;
                                                                        String regname="";
                                                                        String regdesc="";
                                                                        
                                                                            List<Region> list=RegionDao.getAllRecords();
                                                                            if(list!=null)
                                                                            {
                                                                                Iterator itr=list.iterator();
                                                                                while(itr.hasNext())
                                                                                {
                                                                                    Region d=(Region)itr.next();
                                                                                    regid=d.getRegionId();
                                                                                    regname=d.getRegionName();
                                                                                    regdesc=d.getRegionDesc();
                                                                    %>    
                                                                                    <tr>
                                                                                        <TD> <INPUT type="checkbox" name="checkbox"/></TD>
                                                                                        <TD> <INPUT type="hidden" class="form-control" name="regid" value="<%=regid%>" />
                                                                                            <INPUT type="text" class="form-control" name="regname" value="<%=regname%>" required /> </TD>
                                                                                        <TD> <INPUT type="text" class="form-control" name="regdesc" value="<%=regdesc%>" required/> </TD>
                                                                                        <%
                                                                                            if(ID!=0)
                                                                                            {
                                                                                                %>
                                                                                                    <td><a href="RegionController?cid=<%=ID%>&rid=<%=regid%>&action=delete">Delete</a></td>
                                                                                                <%
                                                                                            }
                                                                                        %>
                                                                                    </tr>
                                                                    <%
                                                                                }
                                                                            }
                                                                            
                                                                       
                                                                    %>
                                                                    
                                                            </TABLE>
                                                            <button type="button" class="btn btn-outline btn-default" onclick="addRow('dataTable')" data-toggle="tooltip" data-placement="top" title="Add another region"><b>+</b></button>
                                                            <button type="button" class="btn btn-outline btn-default" onclick="deleteRow('dataTable')" data-toggle="tooltip" data-placement="top" title="Delete selected region"><b>Delete</b></button>
                                                        </div>
                                                    </div>
                                                    <div align="left">
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
    <script>
        function addRow(tableID) {

            var table = document.getElementById(tableID);

            var rowCount = table.rows.length;
            var row = table.insertRow(rowCount);

            var cell1 = row.insertCell(0);
            var element1 = document.createElement("input");
            element1.type = "checkbox";
            cell1.appendChild(element1);

            var cell2 = row.insertCell(1);
            var element2 = document.createElement("input");
            element2.type = "text";
            element2.class="form-control";
            element2.name="regname";
            element2.required="required";
            cell2.appendChild(element2);
            var element2id = document.createElement("input");
            element2id.type = "hidden";
            element2id.class="form-control";
            element2id.name="regid";
            element2id.value="0";
            cell2.appendChild(element2id);

            var cell3 = row.insertCell(2);
            var element3 = document.createElement("input");
            element3.type = "text";
            element3.class="form-control";
            element3.name="regdesc";
            element3.required="required";
            cell3.appendChild(element3);
            
            var cell4 = row.insertCell(3);
            cell4.appendChild(element4);
           
        }

        function deleteRow(tableID) {
            try {
            var table = document.getElementById(tableID);
            var rowCount = table.rows.length;

            for(var i=0; i<rowCount; i++) {
                var row = table.rows[i];
                var chkbox = row.cells[0].childNodes[0];
                if(null != chkbox && true == chkbox.checked) {
                    table.deleteRow(i);
                    rowCount--;
                    i--;
                }

            }
            }catch(e) {
                alert(e);
            }
        }
    </script>

</body>

</html>
