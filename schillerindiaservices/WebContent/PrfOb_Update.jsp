<%@page import="com.schillerindiaservices.Dao.BranchDao"%>
<%@page import="com.schillerindiaservices.bean.Prfobmaster"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.schillerindiaservices.connection.DbConnection"%>
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

    <title>PRF/OB Register</title>

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

<body onload="fetch_scEngg();">

    <div id="wrapper">
<%
            Connection con=DbConnection.getConnection();
            session=request.getSession();
            if(session.getAttribute("logname")==null)
            {   
                response.sendRedirect("index.html");
            }
%>
<%
    String role=(String)session.getAttribute("logrole");
    if(role.equalsIgnoreCase("admin"))
    {
%>
        <%@include file="admindashboard.jsp" %>
<%
    }
else if(role.equalsIgnoreCase("ViceChancellor"))
{
%>
<%@include file="VPDashboard.jsp" %>
<%
}
else if(role.equalsIgnoreCase("engineer"))
{
%>
 <%@include file="engineerdashboard.jsp" %>
 <%
     }
else if(role.equalsIgnoreCase("servicecoordinator"))
{
%>
<%@include  file="ServiceCoorDashBoard.jsp" %>
<%
    }
%>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">PRF/OB Register </h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                           PRF/OB Register Form 
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <form role="form" action="PRFOBController" method="post">
                                    <div class="col-lg-4">
                                    <%
                                        int id=0;
                                    String division="";
                                    String sc_engg="";
                                    String divid="";
                                    String type="";
                                    String raise_date="";
                                    String recv_date="";
                                    String region="";
                                    String branch="";
                                    String dealer="";
                                    String engineer="";
                                    String supplier="";
                                    String model="";
                                    String warrent="";
                                    String prfob_ref="";
                                    String crm_re="";
                                    String remarks="";
                                    String status="";
                                    String part_type="";
                                    String part_desc="";
                                    String rec_date_svc="";
                                    String rec_date_svc1="";
                                    
                                    int lid=Integer.parseInt(session.getAttribute("loguserid").toString());
                                            division=EmployeeDao.getempdivision(lid);
                                            divid=ProductDao.getDiviName(division);
                                            String eid=String.valueOf(lid);
                                            if((request.getAttribute("prfobrecors"))!=null)
                                            {
                                            Prfobmaster cr=(Prfobmaster) request.getAttribute("prfobrecors");
                                            id=cr.getId();
                                            sc_engg=cr.getScEngg();
                                            part_type=cr.getPartType();
                                            part_desc=cr.getPartDescription();
                                            type=cr.getWorkType();
                                            pageContext.setAttribute("type", type);
                                            raise_date=cr.getRaisedDate();
                                            recv_date=cr.getReceivedDate();
                                            region=cr.getRegion();
                                            branch=cr.getBranch();
                                            dealer=cr.getDealer();
                                            engineer=cr.getEngineer();
                                            supplier=cr.getSupplier();
                                            model=cr.getModel();
                                            warrent=cr.getWarrentyStatus();
                                            pageContext.setAttribute("warrenty", warrent);
                                            prfob_ref=cr.getPrfobRefNo();
                                            crm_re=cr.getCrmRefNo();
                                            remarks=cr.getRemarks();
                                            status=cr.getStatusType();
                                           rec_date_svc=cr.getReceive_date_from_sc();
                                           // rec_date_svc1=rec_date_svc.substring(0,11);
                                           pageContext.setAttribute("stat", status);
                                            }
                                            String sc_name=EmployeeDao.getname(sc_engg);
                                            String br_name=BranchDao.getbName(branch);
                                            String dr_name=DealerDao.getdName(dealer);
                                            String eng_name=EmployeeDao.getname(engineer);
                                    %>

                                    <input type="hidden" name="id" class="form-control" value="<%=id%>">
                                        <div class="form-group">
                                                <label> Division</label>
                                                <input type="text"  name="division" id="division" value="<%=division%>" minlength="1" maxlength="40"  value="" class="form-control" readonly="">
                                        </div>
                                        
                                                <%
                                                    if(role.equalsIgnoreCase("admin"))
                                                    {
                                                    %>
                                                    <div class="form-group">
                                                <label>Type</label>
                                                <select name="type" class="form-control">
                                                    <option value="PRF"${type=='PRF'?'selected':''}>PRF</option>
                                                    <option value="OB"${type=='OB'?'selected':''}>OB</option>
                                                </select>
                                        </div>
                                                   
                                                    <%
                                                        }else{
                                                        %>
                                                         <div class="form-group">
                                                <label>Type</label>
                                                 <input type="text"  value="<%=type%>" name="type" minlength="1" maxlength="40" value="" class="form-control" readonly="" required="">
                                        </div>
                                                        <%
                                                            }
                                                            %>
                                        
                                        
                                        <%
                                                    if(role.equalsIgnoreCase("admin"))
                                                    {
                                                    %>
                                                     <div class="form-group">
                                                <label>Raised Date</label>
                                                <input type="text"   name="raised_date" value="<%=raise_date%>" minlength="1" maxlength="40" value="" class="form-control">
                                            </div>
                                                    <%
                                                        }else{
                                                        %>
                                                        <div class="form-group">
                                                <label>Raised Date</label>
                                                <input type="text"   name="raised_date" value="<%=raise_date%>" minlength="1" maxlength="40" value="" class="form-control" readonly="">
                                            </div>
                                                        <%
                                                            }
                                                            %>
                                        
                                            
                                            <%
                                                    if(role.equalsIgnoreCase("admin"))
                                                    {
                                                    %>
                                                    <div class="form-group">
                                                <label>Entry Date from SC</label>
                                                <input type="text"   name="recv_date_sc" value="<%=recv_date%>" minlength="1" maxlength="40" value="" class="form-control">
                                            </div>
                                                    <%
                                                        }else{
                                                        %>
                                                        <div class="form-group">
                                                <label>Entry Date from SC</label>
                                                <input type="text"   name="recv_date_sc" value="<%=recv_date%>" minlength="1" maxlength="40" value="" class="form-control" readonly=""  required="">
                                            </div>
                                                        <%
                                                            }
                                                            %>
                                             
                                        <div class="form-group">
                                                <label>Region</label>
                                                 <input type="text"  value="<%=region%>" minlength="1" maxlength="40" value="" class="form-control" readonly="" required="">
                                            </div>
                                        <div class="form-group">
                                                <label>Branch</label>
                                                 <input type="text"  value="<%=br_name%>" minlength="1" maxlength="40" value="" class="form-control" readonly="" required="">
                                            </div>

                                    </div>
                                        <div class="col-lg-4">
                                            
                                        <div class="form-group">
                                                <label>Dealer</label>
                                                 <input type="text"  value="<%=dr_name%>" minlength="1" maxlength="40" value="" class="form-control" readonly="" required="">
                                            </div>
                                             <div class="form-group">
                                                <label>Engineer</label>
                                                <input type="text"  value="<%=eng_name%>" minlength="1" maxlength="40" value="" class="form-control" readonly="" required="">
                                        </div>
                                             <div class="form-group">
                                                <label>Supplier</label>
                                                 <input type="text"  value="<%=supplier%>" minlength="1" maxlength="40" value="" class="form-control" readonly="" required="">
                                        </div>
                                             <div class="form-group">
                                                <label>Model</label>
                                                <input type="text"  value="<%=model%>" minlength="1" maxlength="40" value="" class="form-control" readonly="" required="">
                                        </div>
                                            
                                        <%
                                                    if(role.equalsIgnoreCase("admin"))
                                                    {
                                                    %>
                                                    <div class="form-group">
                                                <label>Warranty Status</label>
                                                <select class="form-control"  name="Warranty" required="">
                                                    <option value="">Select Type</option> 
                                                     <option value="IW"${warrenty=='IW'?'selected':''}>IW</option>
                                                    <option value="OW"${warrenty=='OW'?'selected':''}>OW</option>
                                                     <option value="EW"${warrenty=='EW'?'selected':''}>EW</option>
                                                    <option value="CAMC"${warrenty=='CAMC'?'selected':''}>CAMC</option>
                                                    <option value="LAMC"${warrenty=='LAMC'?'selected':''}>LAMC</option>
                                                     <option value="STOCK"${warrenty=='STOCK'?'selected':''}>STOCK</option>
                                                </select>
                                            </div>
                                                    <%
                                                        }else{
                                                        %>
                                                        <div class="form-group">
                                                <label>Warranty Status</label>
                                                <input type="text"  value="<%=warrent%>" name="Warranty" minlength="1" maxlength="40" value="" class="form-control" readonly="" required="">
                                            </div>
                                                        <%
                                                            }
                                                            %>
                                            
                                            
                                               <%
                                                    if(role.equalsIgnoreCase("admin"))
                                                    {
                                                    %>
                                                    <div class="form-group">
                                                <label>PRF/OB Reference No.</label>
                                                <input type="text" name="prfob_ref_no" value="<%=prfob_ref%>" class="form-control" placeholder="PRF/OB Reference No.">
                                        </div>
                                                    <%
                                                        }else{
                                                        %>
                                                        <div class="form-group">
                                                <label>PRF/OB Reference No.</label>
                                                <input type="text" name="prfob_ref_no" value="<%=prfob_ref%>" class="form-control" placeholder="PRF/OB Reference No."  readonly="">
                                        </div>
                                                        <%
                                                            }
                                                            %>
                                            </div>
                                        <div class="col-lg-4">
                                            <%
                                                if(role.equalsIgnoreCase("servicecoordinator")||role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("vicechancellor"))
                                                {
                                                    %>
                                                    <div class="form-group">
                                                <label> SC Engineer</label>
                                                <input type="hidden" name="sc_engg" value="<%=lid%>">
                                                <input type="text"  value="<%=sc_name%>" minlength="1" maxlength="40" value="" class="form-control" readonly="">
                                        </div>
                                                    <%
                                                }else{
                                                if(sc_name==""||sc_name.equalsIgnoreCase(null))
                                                            {
                                                        %>
                                                        <div class="form-group">
                                                <label> SC Engineer</label>
                                                
                                                <select name="sc_engg" id="myScEngg" class="form-control" >
                                                    <option value="">Select Engineer</option>
                                                </select>
                                            </div>
                                                        <%
                                                                }
                                                            else{
                                                            %>
                                                            <div class="form-group">
                                                <label> SC Engineer</label>
                                                <input type="hidden" name="sc_engg" value="<%=lid%>">
                                                <input type="text"  value="<%=sc_name%>" minlength="1" maxlength="40" value="" class="form-control" readonly="">
                                        </div>
                                                            <%
                                                                }
                                                    }
                                            %>
                                           
                                            
                                        <div class="form-group">
                                                <label>Spares Received at SVC</label>
                                                <input type="text" id="datepicker-3"  name="receive_date_from_sc" value="<%=rec_date_svc%>" minlength="1" maxlength="40" value="" class="form-control"  >
                                            </div>
                                        
                                        <div class="form-group">
                                                <label>Part type</label>
                                                <input type="text" name="part_type" value="<%=part_type%>" class="form-control">
                                        </div>
                                        <div class="form-group">
                                                <label>Parts Description</label>
                                                <input type="text" name="part_desc" min="1" max="500" value="<%=part_desc%>" class="form-control">
                                        </div>
                                                <div class="form-group">
                                                <label>CRM Reference No.</label>
                                                <input type="text" name="crm_ref_no" value="<%=crm_re%>" class="form-control" placeholder="CRM Reference No." >
                                        </div>
                                                 <div class="form-group">
                                                <label>Remarks</label>
                                                <input type="text" name="remarks" value="<%=remarks%>" class="form-control" min="1" max="550" placeholder="remarks" >
                                        </div>
                                                
                                                 
                                                <%
                                                    if(role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("vicechancellor"))
                                                    {
                                                        %>
                                                        <div class="form-group">
                                                <label>Status</label>
                                                <select class="form-control"  name="status" required="" >
                                                    <option value="">Select Type</option> 
                                                   <option value="Completed"${stat=='Completed'?'selected':''}>Completed</option>
                                                    <option value="Pending"${stat=='Pending'?'selected':''}>Pending</option>
                                                     
                                                </select></div>
                                            <div>&nbsp;</div>
                                          
                                            <div align="center">
                                                <center><button type="submit" class="btn btn-success">SAVE</button></center>
                                            </div>   
                                                        <%
                                                    }else{
                                                    if(status.equalsIgnoreCase("pending"))
                                                        {
                                                        %>
                                                        <div class="form-group">
                                                <label>Status</label>
                                                <select class="form-control"  name="status" required="" >
                                                    <option value="">Select Type</option> 
                                                   <option value="Completed"${stat=='Completed'?'selected':''}>Completed</option>
                                                    <option value="Pending"${stat=='Pending'?'selected':''}>Pending</option>
                                                     
                                                </select></div>
                                            <div>&nbsp;</div>
                                          
                                            <div align="center">
                                                <center><button type="submit" class="btn btn-success">SAVE</button></center>
                                            </div>   
                                                    <%
                                                        }else{
                                                        %>
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
      
</body>
<script>
    function fetch_scEngg()
    {  
        var request=new XMLHttpRequest();
        var name=document.getElementById("division").value;
        var url="getSCENGG.jsp?val="+name;  
        try
        {  
            request.onreadystatechange=function()
            {  
                if(request.readyState==4)
                {  
                    var val=request.responseText;  
                    document.getElementById('myScEngg').innerHTML=val;  
                }  
            }
            request.open("GET",url,true);  
            request.send(); 
            
        }catch(e){alert("Unable to connect to server");}  
        
        setTimeout(function(){fetch_engineer();},1000); 
        
    }  
    
    $(function() {
        $( "#datepicker-3" ).datepicker({ 
             changeMonth: true,
	 changeYear: true,
            dateFormat: 'dd-mm-yy',
            minDate: 0,
            maxDate:0
          	//  timeFormat:  "hh:mm:ss"
            
        });
        $( "#datepicker-15" ).datepicker("show");
     });
    </script>
</html>
