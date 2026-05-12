
<% 
            session=request.getSession();
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
                        <li>
                            <a href="ServiceListEngg.jsp">Services</a>
                        </li>
                        <li>
                            <a href="emp_PendingFRN.jsp?qid=1">Pending FRN</a>
                            
                        </li>
                        <li>
                            <a href="emp_OBPending.jsp?qid=2">OB Pending</a>
                        </li>
                        <li>
                            <a href="emp_URpending.jsp?qid=3">Under Repair Pending</a>
                        </li>
                        <li>
                            <a href="emp_CompletedFRN.jsp?qid=4">SC_Completed FRN</a>
                        </li>
                         <li>
                            <a href="Emp_NewCFRN.jsp">Completed FRN</a>
                        </li>
                         <li>
                             <a href="ScarpListEngg.jsp">Scrap List </a>
                        </li>   
                        <li>
                             <a href="CallListEngg.jsp">Call Register </a>
                        </li> 
                        <li>
                             <a href="ClosedCalls.jsp">Closed Calls </a>
                        </li> 
                        <li>
                             <a href="PendingActListENGG.jsp">Pending Activity </a>
                        </li> 
                        <li>
                             <a href="ClosedActivity.jsp"> Closed Activity </a>
                        </li>
                        <li>
                             <a href="PRFOB_EnggList.jsp">PRF/OB Register </a>
                        </li>
                       <li>
                             <a href="PRFOB_EnggList_closed.jsp">Closed PRF/OB Register </a>
                        </li> 
                         <!-- <li>
                             <a href="ClosedPRFOB.jsp">Closed PRF/OB Register </a>
                        </li>  -->
                        <li>
                             <a href="nonsaleEnggList.jsp">Non Saleable </a>
                        </li>
                        <li>
                             <a href="SalablesList2_engg.jsp">Saleables </a>
                        </li>
                        <li>
                             <a href="BIREngList.jsp">BIR LIST </a>
                        </li>
                        <li>
                             <a href="ClosedBIRList.jsp">Closed BIR </a>
                        </li>
                  <li>
                             <a href="spareslist_engg.jsp">Spares_List </a>
                        </li> 
                        <li>
                             <a href="spareslist_engg2_Completed.jsp">Spares_List_Completed </a>
                        </li> 
                    </ul>
                </div>
            </div>
            <!-- /.navbar-static-side -->
        </nav>