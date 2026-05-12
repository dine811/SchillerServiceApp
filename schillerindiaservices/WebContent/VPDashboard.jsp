
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
                        <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                        </li>
                        <li class="divider"></li>
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
                        
                        <li>
                            <a href="index.jsp"><i class="fa fa-dashboard fa-fw"></i>Dashboard</a>
                        </li>
                      <li>
                            <a href="ServiceList.jsp"><i class="fa fa-send-o"></i> Services </a>
                        </li>
                                <li>
                            <a href="under_repair.jsp"><i class="fa fa-unlock"></i> Under Repair </a>
                        </li>
                         <li>
                            <a href="pending_FRN.jsp"><i class="fa fa-puzzle-piece"></i> Pending FRN </a>
                        </li>
                         <li>
                           <a href="ob_pending.jsp"><i class="fa fa-pencil-square"></i> OB pending </a>
                        </li>
                        
                         <li>
                             <a href="closedproduct.jsp"><i class="fa fa-close"></i> SC_Closed </a>
                        </li>
                        <li>
                           <a href="New_ClosedProduct.jsp"><i class="fa fa-fa"></i> Closed </a>
                        </li>
                        <li>
                             <a href="ScarpList.jsp"><i class="fa fa-star-o"></i> Scarp List </a>
                        </li>
                          <li>
                             <a href="CallListAdmin.jsp"><i class="fa fa-phone"></i> Call Register</a>
                        </li> 
                        <li>
                             <a href="ClosedCalls.jsp"><i class="fa fa-phone"></i> Closed Calls</a>
                        </li> 
                            <li>
                             <a href="PendingActListAdmin.jsp"><i class="fa fa-balance-scale"></i> Pending Activity </a>
                        </li> 
                        <li>
                             <a href="ClosedActivity.jsp"><i class="fa fa-balance-scale"></i> Closed Activity </a>
                        </li>
                        <li>
                            <a href="PRFOB_AdminList.jsp"><i class="fa fa-list"></i> PRF/OB List</a>
                        </li>
                        <li>
                            <a href="nonSaleAdminList.jsp"><i class="fa fa-list"></i> Non Salable List</a>
                        </li>
                        <li>
                            <a href="SalablesList.jsp"><i class="fa fa-list"></i> Salables</a>
                        </li>
                        <li>
                            <a href="BIRAdminList.jsp"><i class="fa fa-list"></i> BIR List</a>
                        </li>
                        <li>
                             <a href="ClosedBIRList.jsp"><i class="fa fa-list"></i>Closed BIR </a>
                        </li>
                        
                    </ul>
                </div>
            </div>
            <!-- /.navbar-static-side -->
        </nav>