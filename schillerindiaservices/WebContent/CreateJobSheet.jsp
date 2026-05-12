<%@page import="com.schillerindiaservices.Dao.ModelDao"%>
<!DOCTYPE html>
<%@page import="java.lang.*"%>
<%@page import="java.util.*"%>
<%@page import="com.schillerindiaservices.bean.service_master"%>
<%@page import="com.schillerindiaservices.bean.Jobsheet"%>
<%@page import="com.schillerindiaservices.Dao.JobSheetDao"%>

<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SchillerIndia Services</title>

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
      <link href="dist/css/datetextremover.css" rel="stylesheet">
      

    
    <!-- Custom Fonts -->
    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <!-- Custom Fonts -->
    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link rel="icon" type="image/png" href="img/logo.png"/>
    <link href = "dist/css/jquery-ui.css" rel = "stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<link rel="icon" type="image/png" href="img/logo.png"/>
<style>
table, th, td {
    border: 1px solid black;
    border-collapse: collapse;
}
th, td {
    padding: 15px;
}
</style>
</head>

<body>

    <div id="wrapper">
        <%
            session=request.getSession();
            String role=(String)session.getAttribute("logrole");
            if("admin".equalsIgnoreCase(role))
            {   
                %><%@include file="admindashboard.jsp" %><%
            }
else if(role.equalsIgnoreCase("ViceChancellor"))
{
%>
<%@include  file="VPDashboard.jsp" %>
<%
}
            else
            {   
                %><%@include file="engineerdashboard.jsp" %><%
            }
        %>
                
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Job Sheet Update</h3>
                </div>
            </div>
            <div>
                 <%
                                                        int id=0;
                                                        int divid=0;
                                                        String division="";
                                                        String scRefNo="";
                                                        String frnNo="";
                                                        String serCentreReceivedDate="";
                                                        String productModel="";
                                                        String defPartSn="";
                                                        String defGirNo="";
                                                        String  model="";
                                                        String def_mod_brd_name="";
                                                        String Status_type="";
                                                        
                                            if(request.getAttribute("editablerecord")!=null)
                                            { 
                                                service_master d=(service_master)request.getAttribute("editablerecord");
                                                        
                                                        id=d.getSerId();
                                                        System.out.println("the id disss"+id);
                                                        scRefNo=d.getScRefNo();
                                                        frnNo=d.getFrnNo();
                                                       
                                                        serCentreReceivedDate=d.getSerCentreReceivedDate() ;
                                                        if(serCentreReceivedDate==null)
                                                        {
                                                            serCentreReceivedDate="";
                                                        }
                                                        productModel=d.getProductModel();
                                                        model=ModelDao.getModelname(productModel);
                                                        defPartSn=d.getDefPartSn();
                                                        defGirNo=d.getDefGirNo();
                                                        def_mod_brd_name=d.getDefModBrdName();
                                                        if(d.getStatus()!=null){
                                                        	Status_type=d.getStatus();
                                                        }
                                                        
                                                        System.out.println("re ty isss"+Status_type);
                                        }
                                     %>
                
            <label> Frn No : <%=frnNo%></label><br>
            <label> Reference No : <%=scRefNo%></label><br>
            <label> Model : <%=model%></label><br>
            <label> Defective GIR No : <%=defGirNo%></label><br>
           <%--  <label> Defective Part : <%=defPartSn%></label><br> --%>
            <label>Defective Model Brand Name:<%=def_mod_brd_name%></label><br>
            <label> Received Date at Service Center : <%=serCentreReceivedDate%></label>
            
            </div>
         
            <form action="JobSheetController" method="post" >
              <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                          <tr>
                                            <th>Repair Date</th>
                                            <th>Engineer Name</th> 
                                            <th>Observation</th>
                                            <th>Repair Activity</th>
                                            <th>Time Spent</th>
                                            
                                            <th>Remark</th>
                                          </tr>                                    
                                    </tr>
                                </thead>
                                <tbody>
                       <%
                                                        int id1=0;
                                                        String repairDate="nil";
                                                        String enginnerName="";
                                                        String observation="";
                                                        String repairActivity="";
                                                        String timeSpent="";
                                                        String remark="";
                                                        String repairDate1="nil";
                                                        String enginnerName1="";
                                                        String observation1="";
                                                        String repairActivity1="";
                                                        String timeSpent1="";
                                                        String remark1="";
                                                        String repairDate2="nil";
                                                        String enginnerName2="";
                                                        String observation2="";
                                                        String repairActivity2="";
                                                        String timeSpent2="";
                                                        String remark2="";
                                                        String repairDate3="nil";
                                                        String enginnerName3="";
                                                        String observation3="";
                                                        String repairActivity3="";
                                                        String timeSpent3="";
                                                        String remark3="";
                                                        String repairDate4="nil";
                                                        String enginnerName4="";
                                                        String observation4="";
                                                        String repairActivity4="";
                                                        String timeSpent4="";
                                                        String remark4="";
                                                        String status="";
                                                        
//                                             List<Jobsheet> list=JobSheetDao.getAllRecords();getById
                                            List<Jobsheet> list=JobSheetDao.getById(id);
System.out.println(list.size()+"the size iss");
                                            if(list!=null)
                                            {
                                                Iterator itr=list.iterator();
                                                while(itr.hasNext())
                                                {
                                                    Jobsheet j=(Jobsheet)itr.next();
                                                    id1=j.getId();
                                                    repairDate=j.getRepairDate();
                                                    System.out.println(" repdate11"+repairDate);
                                                    enginnerName=j.getEnginnerName();
                                                    observation=j.getObservation();
                                                    repairActivity=j.getRepairActivity();
                                                    timeSpent=j.getTimeSpent();
                                                    remark=j.getRemark();
                                                    repairDate1=j.getRepairDate1();
                                                    System.out.println(" repdate22"+repairDate1);
                                                    enginnerName1=j.getEnginnerName1();
                                                    observation1=j.getObservation1();
                                                    repairActivity1=j.getRepairActivity1();
                                                    System.out.println("repaired activityy111  "+repairActivity1);
                                                    timeSpent1=j.getTimeSpent1();
                                                    System.out.println("repaired timespent1   "+timeSpent1);
                                                    remark1=j.getRemark1();
                                                    repairDate2=j.getRepairDate2();
                                                    System.out.println(" repdate22"+repairDate2);
                                                    enginnerName2=j.getEnginnerName2();
                                                    observation2=j.getObservation2();
                                                    repairActivity2=j.getRepairActivity2();
                                                    timeSpent2=j.getTimeSpent2();
                                                    remark2=j.getRemark2();
                                                    repairDate3=j.getRepairDate3();
                                                    System.out.println(" repdate22"+repairDate3);
                                                    enginnerName3=j.getEnginnerName3();
                                                    observation3=j.getObservation3();
                                                    repairActivity3=j.getRepairActivity3();
                                                    timeSpent3=j.getTimeSpent3();
                                                    remark3=j.getRemark3();
                                                    repairDate4=j.getRepairDate4();
                                                    System.out.println(" repdate33"+repairDate4);
                                                    enginnerName4=j.getEnginnerName4();
                                                    observation4=j.getObservation4();
                                                    repairActivity4=j.getRepairActivity4();
                                                    timeSpent4=j.getTimeSpent4();
                                                    remark4=j.getRemark4();
                                                    
                                                }
                                            }
                                     %> 
                                     <%
                                     if(repairDate.isEmpty()||repairDate.equalsIgnoreCase("nil")){
                                    
                                     %>            
                                 <tr>
                      <input name="id" type="hidden" value="<%=id%>" class="form-control">        
                      <input name="id1" type="hidden" value="<%=id1%>" class="form-control">      
                      <td><%-- <input name="repairDate" type="text" value="<%=repairDate%>" class="form-control"> --%><input type="text" name="repairDate" value="<%=repairDate%>" class="form-control" id="datepicker-1" ></td>
                      <td><input name="enginnerName" type="text" value="<%=enginnerName%>" class="form-control"></td>
                      <td><input name="observation" type="text" value="<%=observation%>" class="form-control"></td>
                      <td><input name="repairActivity" type="text" value="<%=repairActivity%>" class="form-control"></td>
                      <td><input name="timeSpent" type="text" value="<%=timeSpent%>" class="form-control"></td>
                      <td><input name="remark" type="text" value="<%=remark%>" class="form-control"></td>
                    </tr>
                    <%
                                     }else{
                    %>
                      <tr>
                      <input name="id" type="hidden" value="<%=id%>" class="form-control">        
                      <input name="id1" type="hidden" value="<%=id1%>" class="form-control">      
                      <td><%-- <input name="repairDate" type="text" value="<%=repairDate%>" class="form-control"> --%><input type="text" name="repairDate" value="<%=repairDate%>" class="form-control" id="datepicker-1" readonly=""></td>
                      <td><input name="enginnerName" type="text" value="<%=enginnerName%>" class="form-control"   readonly=""></td>
                      <td><input name="observation" type="text" value="<%=observation%>" class="form-control"   readonly=""></td>
                      <td><input name="repairActivity" type="text" value="<%=repairActivity%>" class="form-control"   readonly=""></td>
                      <td><input name="timeSpent" type="text" value="<%=timeSpent%>" class="form-control"   readonly=""></td>
                      <td><input name="remark" type="text" value="<%=remark%>" class="form-control"  readonly=""></td>
                    </tr>
                    
                    <% } %>
                     <%
                                     if(repairDate1.isEmpty()||repairDate1.equalsIgnoreCase("nil")){
                                    System.out.println("inside if111");
                                     %>    
                    
                    <tr>
                     <td><%-- <input name="repairDate1" type="text" value="<%=repairDate1%>" class="form-control"> --%><input type="text" name="repairDate1" value="<%=repairDate1%>" class="form-control" id="datepicker-2" ></td>
                      <td><input name="enginnerName1" type="text" value="<%=enginnerName1%>" class="form-control"></td>
                      <td><input name="observation1" type="text" value="<%=observation1%>" class="form-control"></td>
                      <td><input name="repairActivity1" type="text" value="<%=repairActivity1%>" class="form-control"></td>
                      <td><input name="timeSpent1" type="text" value="<%=timeSpent1%>" class="form-control"></td>
                      <td><input name="remark1" type="text" value="<%=remark1%>" class="form-control"></td>
                    </tr>
                    
                     <%
                                     }else{
                                    	 System.out.println("inside elsee111");
                    %>
                      <tr>
                     <td><%-- <input name="repairDate1" type="text" value="<%=repairDate1%>" class="form-control"> --%><input type="text" name="repairDate1" value="<%=repairDate1%>" class="form-control" id="datepicker-2"  readonly=""></td>
                      <td><input name="enginnerName1" type="text" value="<%=enginnerName1%>" class="form-control"   readonly=""></td>
                      <td><input name="observation1" type="text" value="<%=observation1%>" class="form-control"  readonly=""></td>
                      <td><input name="repairActivity1" type="text" value="<%=repairActivity1%>" class="form-control"  readonly=""></td>
                      <td><input name="timeSpent1" type="text" value="<%=timeSpent1%>" class="form-control"  readonly=""></td>
                      <td><input name="remark1" type="text" value="<%=remark1%>" class="form-control"  readonly=""></td>
                    </tr>
                    
                     <% } %>
                     <%
                                     if(repairDate2.isEmpty()||repairDate2.equalsIgnoreCase("nil")){
                                    	 System.out.println("inside if122");
                                     %>
                    
                    
                    <tr>
                     <td><%-- <input name="repairDate2" type="text" value="<%=repairDate2%>" class="form-control"> --%><input type="text" name="repairDate2" value="<%=repairDate2%>" class="form-control" id="datepicker-3" ></td>
                      <td><input name="enginnerName2" type="text" value="<%=enginnerName2%>" class="form-control"></td>
                      <td><input name="observation2" type="text" value="<%=observation2%>" class="form-control"></td>
                      <td><input name="repairActivity2" type="text" value="<%=repairActivity2%>" class="form-control"></td>
                      <td><input name="timeSpent2" type="text" value="<%=timeSpent2%>" class="form-control"></td>
                      <td><input name="remark2" type="text" value="<%=remark2%>" class="form-control"></td>
                    </tr>
                      <%
                                     }else{
                                    	 System.out.println("inside elsee22");
                    %>
                      <tr>
                     <td><%-- <input name="repairDate2" type="text" value="<%=repairDate2%>" class="form-control"> --%><input type="text" name="repairDate2" value="<%=repairDate2%>" class="form-control" id="datepicker-3"  readonly=""></td>
                      <td><input name="enginnerName2" type="text" value="<%=enginnerName2%>" class="form-control"  readonly=""></td>
                      <td><input name="observation2" type="text" value="<%=observation2%>" class="form-control"  readonly=""></td>
                      <td><input name="repairActivity2" type="text" value="<%=repairActivity2%>" class="form-control"  readonly=""></td>
                      <td><input name="timeSpent2" type="text" value="<%=timeSpent2%>" class="form-control"  readonly=""></td>
                      <td><input name="remark2" type="text" value="<%=remark2%>" class="form-control"  readonly=""></td>
                    </tr>
                     <% } %>
                     <%
                                     if(repairDate3.isEmpty()||repairDate3.equalsIgnoreCase("nil")){
                                    	 System.out.println("inside if33");
                                     %>
                    <tr>
                     <td><%-- <input name="repairDate3" type="text" value="<%=repairDate3%>" class="form-control"> --%><input type="text" name="repairDate3" value="<%=repairDate3%>"  class="form-control" id="datepicker-4" ></td>
                      <td><input name="enginnerName3" type="text" value="<%=enginnerName3%>" class="form-control"></td>
                      <td><input name="observation3" type="text" value="<%=observation3%>" class="form-control"></td>
                      <td><input name="repairActivity3" type="text" value="<%=repairActivity3%>" class="form-control"></td>
                      <td><input name="timeSpent3" type="text" value="<%=timeSpent3%>" class="form-control"></td>
                      <td><input name="remark3" type="text" value="<%=remark3%>" class="form-control"></td>
                    </tr>
                     <%
                                     }else{
                                    	 System.out.println("inside else33");
                    %>
                     <tr>
                     <td><%-- <input name="repairDate3" type="text" value="<%=repairDate3%>" class="form-control"> --%><input type="text" name="repairDate3" value="<%=repairDate3%>"  class="form-control" id="datepicker-4"  readonly=""></td>
                      <td><input name="enginnerName3" type="text" value="<%=enginnerName3%>" class="form-control"  readonly=""></td>
                      <td><input name="observation3" type="text" value="<%=observation3%>" class="form-control"  readonly=""></td>
                      <td><input name="repairActivity3" type="text" value="<%=repairActivity3%>" class="form-control"  readonly=""></td>
                      <td><input name="timeSpent3" type="text" value="<%=timeSpent3%>" class="form-control"  readonly=""></td>
                      <td><input name="remark3" type="text" value="<%=remark3%>" class="form-control"  readonly=""></td>
                    </tr>
                     <% } %>
                     <%
                                     if(repairDate4.isEmpty()||repairDate4.equalsIgnoreCase("nil")){
                                    	 System.out.println("inside if44");
                                     %>
                    <tr>
                     <td><%-- <input name="repairDate4" type="text" value="<%=repairDate4%>" class="form-control"> --%><input type="text" name="repairDate4" value="<%=repairDate4%>" class="form-control" id="datepicker-5" ></td>
                      <td><input name="enginnerName4" type="text" value="<%=enginnerName4%>" class="form-control"></td>
                      <td><input name="observation4" type="text" value="<%=observation4%>" class="form-control"></td>
                      <td><input name="repairActivity4" type="text" value="<%=repairActivity4%>" class="form-control"></td>
                      <td><input name="timeSpent4" type="text" value="<%=timeSpent4%>" class="form-control"></td>
                      <td><input name="remark4" type="text" value="<%=remark4%>" class="form-control"></td>
                    </tr>
                    
                     <%
                                     }else{
                                    	 System.out.println("inside elsee44");
                    %>
                      <tr>
                     <td><%-- <input name="repairDate4" type="text" value="<%=repairDate4%>" class="form-control"> --%><input type="text" name="repairDate4" value="<%=repairDate4%>" class="form-control" id="datepicker-5"   readonly=""></td>
                      <td><input name="enginnerName4" type="text" value="<%=enginnerName4%>" class="form-control"  readonly=""></td>
                      <td><input name="observation4" type="text" value="<%=observation4%>" class="form-control"  readonly=""></td>
                      <td><input name="repairActivity4" type="text" value="<%=repairActivity4%>" class="form-control"  readonly=""></td>
                      <td><input name="timeSpent4" type="text" value="<%=timeSpent4%>" class="form-control"  readonly=""></td>
                      <td><input name="remark4" type="text" value="<%=remark4%>" class="form-control"  readonly=""></td>
                    </tr>
                     <% } %>
                    
                    
                    
                    
                    
                    
                    
                   
                                </tbody>
                            </table>  
                 <%
                 if(Status_type.equalsIgnoreCase("Pending")||Status_type.isEmpty()){
                	 %>
                	  <div class="form-group col-md-3" style="padding-right: 50px;padding-bottom: 20px;">
                                <label class="col-sm-8 " for="form-field-1"> Status </label>
                                <select name="status" class="form-control">
                                    <option>Pending</option>
                                   
                                    <option>Completed</option>
                                   
                                </select>
                                        
                      </div>
                       
                      <div style="padding-right: 40px;padding-top: 26px;">
                    <button type="submit" class="btn btn-success">SAVE</button>
                                            
                  </div>
                	 
                	 <% 
                 }else{
                 
                 %>
                  <div class="form-group col-md-3" style="padding-right: 50px;padding-bottom: 20px;">
                                <label class="col-sm-8 " for="form-field-1"> Status </label>
                                <select name="status" class="form-control" readonly="">
                                   <!--  <option value="1">Selected All</option> -->
                                     <option value="<%=Status_type%>"><%=Status_type%></option>
                                    <option>Pending</option>
                                    <option>Completed</option>
                                </select>
                                        
                      </div>
                       
                    <!--   <div style="padding-right: 40px;padding-top: 26px;">
                    <button type="submit" class="btn btn-success">SAVE</button>
                                            
                  </div> -->
                 
                 
                 <%} %>
                
            </form>
        </div>
    </div>
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
<!--         <script src = "dist/js/jquery-1.12.4.js"></script>
 -->    
    
<!--    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script> -->

<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>  -->
    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>
  <!--    <script src="dist/js/sb-admin-2.js"></script> -->
    
      <script src = "dist/js/jquery-ui.js"></script>
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->

  <script>
         $(function() {
            $( "#datepicker-13" ).datepicker({ dateFormat: 'yy-mm-dd' });
            $( "#datepicker-13" ).datepicker("show");
         });
      </script>   
       <script>
        $(function() {
            $( "#datepicker-1" ).datepicker({ 
            	changeMonth: true,
        		changeYear: true,
                        dateFormat: 'dd-mm-yy',
                        minDate: -1,
                        maxDate:0 });
          $( "#datepicker-15" ).datepicker("show");
         });
        $(function() {
            $( "#datepicker-2" ).datepicker({ 
            	changeMonth: true,
        		changeYear: true,
                        dateFormat: 'dd-mm-yy',
                        minDate: -1,
                        maxDate:0 });
          $( "#datepicker-15" ).datepicker("show");
         });
        $(function() {
            $( "#datepicker-3" ).datepicker({ 
            	changeMonth: true,
        		changeYear: true,
                        dateFormat: 'dd-mm-yy',
                        minDate: -1,
                        maxDate:0 });
          $( "#datepicker-15" ).datepicker("show");
         });
        $(function() {
            $( "#datepicker-4" ).datepicker({ 
            	changeMonth: true,
        		changeYear: true,
                        dateFormat: 'dd-mm-yy',
                        minDate: -1,
                        maxDate:0 });
          $( "#datepicker-15" ).datepicker("show");
         });
        $(function() {
            $( "#datepicker-5" ).datepicker({ 
            	changeMonth: true,
        		changeYear: true,
                        dateFormat: 'dd-mm-yy',
                        minDate: -1,
                        maxDate:0 });
          $( "#datepicker-15" ).datepicker("show");
         });
         
         $(function() {
            $( "#datepicker-2" ).datepicker({ 
            	changeMonth: true,
        		changeYear: true,
                        dateFormat: 'dd-mm-yy',
                        minDate: -1,
                        maxDate:0 });
            $( "#datepicker-15" ).datepicker("show");
         });
        </script> 
    
</body>

</html>
