<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.schillerindiaservices.connection.DbConnection"%>
<%@page import="com.schillerindiaservices.bean.Email"%>
<!DOCTYPE html>
<%@ page errorPage="error.jsp" %>  
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.schillerindiaservices.bean.Productmaster"%>
<%@page import="com.schillerindiaservices.Dao.ProductDao"%>
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
                    <h3 class="page-header">Email Details</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                                Details of Email receiver
                        </div>
                       
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <form role="form" action="#" method="POST">
                            <div align="Left">
                            </div>
                                 <br>
                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>Direct</th>
                                            <th>Mail Id</th>
                                            <th>Name</th>
                                            <th>Msg Body</th>
                                            <th>Sender Mail</th>
                                            <th>Password</th>
                                            <th>Port</th>
                                            <th>SSL</th>
                                            <th>Host</th>
                                            <th>Edit</th>
                                    </tr>
                                </thead>
                                <%
                                    try{
                                    Connection con=DbConnection.getConnection();
                                    PreparedStatement ps=con.prepareStatement("select * from email");
                                    ResultSet rs=ps.executeQuery();
                                    while(rs.next())
                                    {
                                        int id=rs.getInt("id");
                                        String dir=rs.getString("direction");
                                        String mail=rs.getString("mailid");
                                        String name=rs.getString("name");
                                        String email=rs.getString("semail");
                                        String pass=rs.getString("password_f");
                                        String port=rs.getString("port_no");
                                        String ssl=rs.getString("ssl_no");
                                        String msgbody=rs.getString("msg_body");
                                        String hos=rs.getString("hosing_id");
                                    
                                %>
                                <tbody>
                                    <tr> <th><%=dir%></th>
                                <td><%=mail%></td>
                                <td><%=name%></td>
                                <td><%=msgbody%></td>
                                <td><%=email%></td>
                                <td><%=pass%></td>
                                <td><%=port%></td>
                                <td><%=ssl%></td>
                                <td><%=hos%></td>
                                <td><a href="EmailController?action=edit&id=<%=id%>">Edit</a></td>
                                    </tr>
                                </tbody>
                                <%
                                }
                                   

                                    PreparedStatement ps1=con.prepareStatement("select mailid,semail,password_f,port_no,ssl_no,msg_body from email where id=1");
                                    ResultSet rs1=ps1.executeQuery();
                                    String mail1="";
                                    String email1="";
                                    String password="";
                                    String port="";
                                    String ssl="";
                                if(rs1.next()){
                                     mail1=rs1.getString("mailid");

//                                    SMTP Credentials set into session
                                    email1=rs1.getString("semail");
                                    password=rs1.getString("password_f");
                                    port=rs1.getString("port_no");
                                    ssl=rs1.getString("ssl_no");
                                               
                                    session.setAttribute("toadd", mail1);
                                    session.setAttribute("sender", email1);
                                     session.setAttribute("password", password);
                                     session.setAttribute("port", port);
                                     session.setAttribute("ssl", ssl);
}
//                                     ends here
%>
<%
                                     String msgbody=rs1.getString("msg_body");
                                     session.setAttribute("msgvalue", msgbody);
                                    



                                    PreparedStatement ps2=con.prepareStatement("select mailid from email where id=2");
                                    ResultSet rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    String mail2=rs2.getString("mailid");
                                    session.setAttribute("mailtwo", mail2);
                                }


                                     rs.close();
                                    con.close();
                                }
                                catch(SQLException e){}
                                %>
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
