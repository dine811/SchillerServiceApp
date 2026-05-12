
<%
//            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//            response.setHeader("Pragma","no-cache");
//            response.setDateHeader("Expires", 0);
            
            session=request.getSession(false);
            if(session.getAttribute("logname")==null)
            {   
                response.sendRedirect("index.html");
            }
%>
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.jsp">SchillerIndia Services</a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
                
                <!-- /.dropdown -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <%=session.getAttribute("logname")%><i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        
                       
                        <li><a href="logout.jsp"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
<!--                        <li>
                            <a href="ServiceList.jsp"><i class="fa fa-android"></i>Services<span class="fa arrow"></span></a>
                        </li>-->
                       <!--  <li>
                             <a href="PendingActListENGG.jsp">Pending Activity </a>
                        </li> 
                        <li>
                             <a href="ClosedActivity.jsp"> Closed Activity </a>
                        </li>
                        <li>
                             <a href="nonSaleAdminList.jsp">Non Saleable </a>
                        </li>
                        <li>
                             <a href="SalablesList.jsp">Saleables </a>
                        </li>
                        <li>
                             <a href="BIRAdminList.jsp">BIR LIST </a>
                        </li>
                        <li>
                             <a href="ClosedBIRList_admin.jsp">Closed BIR LIST</a>
                        </li> -->
                         <li>
                            <a href="repairList.jsp">Repair List PFRN</a>
                        </li>
                         <li>
                            <a href="repairList_OB.jsp">Repair List OB</a>
                        </li>
                         <li>
                            <a href="repairList_UR.jsp">Repair List UR</a>
                        </li>
                        <li>
                            <a href="repairList_Closed.jsp">Closed Repair List</a>
                        </li>
                         <li>
                             <a href="CallListEngg.jsp">Call Register </a>
                        </li> 
                        <li>
                             <a href="ClosedCalls.jsp">Closed Calls </a>
                        </li> 
                        <!-- <li>
                            <a href="emp_OBPending.jsp?qid=2">OB Pending</a>
                        </li>
                        <li>
                            <a href="emp_URpending.jsp?qid=3">Under Repair Pending</a>
                        </li> -->
                        
                    </ul>
                </div>
            </div>
            <!-- /.navbar-static-side -->
        </nav>