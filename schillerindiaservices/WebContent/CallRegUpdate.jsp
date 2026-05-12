<%@page import="com.schillerindiaservices.Dao.ModelDao"%>
<%@page import="com.schillerindiaservices.Dao.BranchDao"%>
<!DOCTYPE html>
<%@page import="com.schillerindiaservices.bean.Callregister"%>
<%@page import="com.schillerindiaservices.bean.service_master"%>
<%@page import="com.schillerindiaservices.Dao.ProductDao"%>
<%@page import="com.schillerindiaservices.Dao.EmployeeDao"%>
<%--<%@ page errorPage="error.jsp" %>--%>  
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

    <title>Call Register</title>

    <!-- Bootstrap Core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="dist/css/sb-admin-2.css" rel="stylesheet">
    
    <link href="dist/css/datetextremover.css" rel="stylesheet">
<!--for date picker-->
    <link href = "dist/css/jquery-ui.css" rel = "stylesheet">
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
     String role=(String)session.getAttribute("logrole");
    if(role.equalsIgnoreCase("Engineer"))
    {
%>
        <%@include file="engineerdashboard.jsp" %><%
            }
else if(role.equalsIgnoreCase("FQC"))
{
        %>
        <%@include file="FQCdashboard.jsp" %>
        <% 
        }
else if(role.equalsIgnoreCase("ProductSupport"))
{
%>
 <%@include file="PSdashboard.jsp" %>
<%
    }
else if(role.equalsIgnoreCase("ViceChancellor"))
{
%>
<%@include file="VPDashboard.jsp" %>
<%
}
else if(role.equalsIgnoreCase("repairteam"))
{
  %>
  <%@include file="repairDashboard.jsp" %>
  <%
  }
else
{
        %>
         <%@include file="admindashboard.jsp" %>
        <% } %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Call Register </h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                           Register the details of Call 
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <form role="form" action="CallRegisterController" method="post">
                                    <div class="col-lg-4">
                                    <%
                                        int id=0;
                                    String division="";
                                    String sc_engg="";
                                    String region="";
                                    String sc_name="";
                                    String call_date="";
                                    String call_type="";
                                    String branch="";
                                    String dealer="";
                                    String engineer="";
                                    String model="";
                                    String type_of_call="";
                                    String type_of_commn="";
                                    String remarks="";
                                    String duration="";
                                    String status="";
                                    String mod_name="";
                                            if((request.getAttribute("callrecords"))!=null)
                                            {
                                            Callregister cr=(Callregister) request.getAttribute("callrecords");
                                            id=cr.getId();
                                            division=cr.getDivision();
                                            sc_engg=cr.getScEngg();
                                            sc_name=EmployeeDao.geteName(sc_engg);
                                            call_date=cr.getCallDate();
                                            call_type=cr.getCallType();
                                            pageContext.setAttribute("calltype", call_type);
                                            region=cr.getRegion();
                                            branch=cr.getBranch();
                                            dealer=cr.getDealer();
                                            engineer=cr.getEngineer();
                                            mod_name=cr.getModel();
                                            type_of_call=cr.getTypeOfCall();
                                            pageContext.setAttribute("typofcall", type_of_call);
                                            type_of_commn=cr.getTypeOfCommunication();
                                            pageContext.setAttribute("typcomm", type_of_commn);
                                            remarks=cr.getRemarks();
                                            duration=cr.getDuration();
                                            status=cr.getStatusType();
                                            pageContext.setAttribute("status", status);
                                            }
                                           
                                            String brname=BranchDao.getbName(branch);
                                            String deal=DealerDao.getdName(dealer);
                                            
                                    %>

                                    <input type="hidden" name="id" class="form-control" value="<%=id%>">
                                        <div class="form-group">
                                                <label> Division</label>
                                                <input type="text" name="division" value="<%=division%>" minlength="1" maxlength="40"  value="" class="form-control" readonly="">
                                        </div>
                                        <div class="form-group">
                                                <label> SC Engineer</label>
                                                <input type="hidden" name="sc_engg" value="<%=sc_engg%>">
                                                <input type="text"  value="<%=sc_name%>" minlength="1" maxlength="40" value="" class="form-control" readonly="" required="">
                                        </div>
                                                <%
                                                    if(role.equalsIgnoreCase("admin"))
                                                    {
                                                    %>
                                                    <div class="form-group">
                                                <label>Call Date</label>
                                                <input type="text" name="call_date" id="datepicker-1" value="<%=call_date%>" class="form-control">
                                        </div>
                                                    <%
                                                        }else{
                                                        %>
                                                        <div class="form-group">
                                                <label>Call Date</label>
                                                <input type="text" name="call_date" id="datepicker-1" value="<%=call_date%>" class="form-control" readonly="">
                                        </div>
                                                        <%
                                                            }
                                                            %>
                                        
                                        
<%
                                                    if(role.equalsIgnoreCase("admin"))
                                                    {
                                                    %>
                                                    <div class="form-group">
                                                <label>Call</label>
                                                <select class="form-control"  name="call_type">
                                                    <option value="Received" ${calltype=='Received'?'selected':''}>Received</option>
                                                    <option value="Made"${calltype=='Made'?'selected':''}>Made</option>
                                                </select>
                                            </div>
                                                    <%
                                                        }else{
                                                        %>
                                                        <div class="form-group">
                                                <label>Call</label>
                                                <input type="text" name="call_type" id="datepicker-1" value="<%=call_type%>" class="form-control" readonly="">
                                            </div>
                                                        <%
                                                            }
                                                            %>
                            
                                                        
                                        
                                    </div>
                                        <div class="col-lg-4">
                                            <div class="form-group">
                                                <label>Region</label>
                                                <input type="text" name="engineer" value="<%=region%>" class="form-control" placeholder="Enter engineer name" readonly="">
                                        </div>  
                                            <div class="form-group">
                                                <label>Engineer</label>
                                                <input type="text" name="engineer" value="<%=engineer%>" class="form-control" placeholder="Enter engineer name">
                                        </div>    
                                             <div class="form-group">
                                                <label>Model/ Reason</label>
                                                <input type="text" name="model" value="<%=mod_name%>" class="form-control" placeholder="Enter model">
                                        </div>
                                            
                                        
<%
                                                    if(role.equalsIgnoreCase("admin"))
                                                    {
                                                    %>
                                                    <div class="form-group">
                                                <label>Type of Call</label>
                                                <select class="form-control"  name="type_of_call">
                                                    <option value="Application"${typofcall=='Application'?'selected':''}>Application</option>
                                                    <option value="Software"${typofcall=='Software'?'selected':''}>Software</option>
                                                    <option value="Technical"${typofcall=='Technical'?'selected':''}>Technical</option>
                                                    <option value="Demo"${typofcall=='Demo'?'selected':''}>Demo</option>
                                                    <option value="CRM"${typofcall=='CRM'?'selected':''}>CRM</option>
                                                    <option value="Training"${typofcall=='Training'?'selected':''}>Training</option>
                                                    <option value="Others"${typofcall=='Others'?'selected':''}>Others</option>
                                                </select>
                                            </div>
                                                    <%
                                                        }else{
                                                        %>
                                                        <div class="form-group">
                                                <label>Type of Call</label>
                                                 <input type="text" name="type_of_call" value="<%=type_of_call%>" class="form-control" placeholder="Enter model" readonly="">
                                                
                                            </div>
                                                   <%
                                                            }
                                                            %>     
                                                
                                            
                                              
                                            </div> 
                                                 <div class="col-lg-4">
                                                
                                             <%
                                                    if(role.equalsIgnoreCase("admin"))
                                                    {
                                                    %>
                                                    <div class="form-group">
                                                <label>Type of communication</label>
                                                <select class="form-control"  name="type_of_commn">
                                                    <option value="Telephonic"${typcomm=='Telephonic'?'selected':''}>Telephonic</option>
                                                    <option value="Messaging"${typcomm=='Messaging'?'selected':''}>Messaging</option>
                                                    <option value="Online"${typcomm=='Online'?'selected':''}>Online</option>
                                                    <option value="FieldVisit"${typcomm=='FieldVisit'?'selected':''}>Field Visit</option>
                                                     
                                                </select></div>
                                                    <%
                                                        }else{
                                                        %>
                                                        <div class="form-group">
                                                <label>Type of communication</label>
                                                <input type="text" name="type_of_commn" value="<%=type_of_commn%>" class="form-control" placeholder="Enter model" readonly="">
                                                </div>
                                                        <%
                                                            }
                                                            %>
                                            
                                                            <%
                                                                if(role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("vicechancellor"))
                                                                {
                                                                   %>
                                                                  
                                               
                                                 <div class="form-group">
                                                <label>Remarks</label>
                                                <input type="text" name="remarks" value="<%=remarks%>" class="form-control" min="1" max="500" placeholder="remarks" >
                                        </div>
                                                
                                          <div class="form-group">
                                                <label>Duration</label>
                                                <input  type="text" name="duration" id="duration1" onkeyup="dura()" value="<%=duration%>"  class="form-control"  placeholder="Ex.05:01" >
                                        
                                                 </div>
                                             <div class="form-group">
                                                <label>Status</label>
                                                <select class="form-control"  name="status_t" required="">
                                                    <option value="">Select Type</option> 
                                                    <option value="Completed" ${status=='Completed'?'selected':''}>Completed</option>
                                                    <option value="Pending"${status=='Pending'?'selected':''}>Pending</option>
                                                     
                                                </select></div>
                                            <div>&nbsp;</div>
                                          
                                            <div align="center">
                                                <center><button type="submit" class="btn btn-success">SAVE</button></center>
                                            </div>           
                                        
                                                                   <%
                                                                }else{
                                                                    if(status.equalsIgnoreCase("Pending"))
                                                                        {
                                                                        %>
                                                                        
                                               
                                                 <div class="form-group">
                                                <label>Remarks</label>
                                                <input type="text" name="remarks" value="<%=remarks%>" class="form-control" placeholder="remarks" >
                                        </div>
                                                
                                                 <div class="form-group">
                                                <label>Duration</label>
                                                <input type="text" name="duration" id="duration1" value="<%=duration%>" onkeyup="dura()" class="form-control" placeholder="call duration" >
                                        </div>
                                          
                                             <div class="form-group">
                                                <label>Status</label>
                                                <select class="form-control"  name="status_t" required="">
                                                    <option value="">Select Type</option> 
                                                    <option value="Completed" ${status=='Completed'?'selected':''}>Completed</option>
                                                    <option value="Pending"${status=='Pending'?'selected':''}>Pending</option>
                                                     
                                                </select></div>
                                            <div>&nbsp;</div>
                                          
                                            <div align="center">
                                                <center><button type="submit" class="btn btn-success">SAVE</button></center>
                                            </div>           
                                        
                                                    <%
                                                                            }else{
                                                                %>
                                                                <div class="form-group">
                                                <label>Remarks</label>
                                                <input type="text" name="remarks" value="<%=remarks%>" class="form-control" readonly="" >
                                        </div>
                                                
                                                 <div class="form-group">
                                                <label>Duration</label>
                                                <input type="text" name="duration" id="duration1" onkeyup="dura()" value="<%=duration%>" class="form-control" readonly="">
                                        </div>
                                        <div class="form-group">
                                                <label>Status </label>
                                                <input type="text" name="status" id="datepicker-5" minlength="1" maxlength="50" value="<%=status%>" class="form-control" readonly="">
                                        </div>
                                        <div> &nbsp;</div>
                                        <div class="form-group">
                                            <center><label  style="font-size: medium;font-weight: 800; color: #ff4015">Service Closed</label></center>
                                        </div>
                                                                <%
                                                                    }}
                                                                    %>
                                        </div>
                                      </form> 
                                    </div>
                                                    
                              
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
    <script src = "dist/js/jquery-1.12.4.js"></script>
      <script src = "dist/js/jquery-ui.js"></script>
      
    <script>
         $(function() {
            $( "#datepicker-1" ).datepicker({ 
                 changeMonth: true,
		 changeYear: true,
                dateFormat: 'dd-mm-yy',
                minDate: -1,
                maxDate: 0});
            $( "#datepicker-15" ).datepicker("show");
         });
         
         function dura()
    {
        var nu=document.getElementById("duration1").value;
        if(nu.length==2)
        {
            document.getElementById("duration1").value=nu+" : ";
        }else if(nu.length>2)
        {
             document.getElementById("duration1").value=nu;
        }
    }
    </script>
</body>

</html>
