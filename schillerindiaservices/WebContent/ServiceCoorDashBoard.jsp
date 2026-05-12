
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
                        <li>
                            <a href="index.jsp">DashBoard</a>
                        </li>
                       <!--  <li>
                            <a href="PRFOB_AdminList.jsp">PRF/OB</a>
                        </li>
                          -->
                           <li>
                             <a href="PRFOB_AdminList.jsp">PRF/OB Register </a>
                        </li>
                       <li>
                             <a href="PRFOB_AdminList_closed.jsp">Closed PRF/OB Register </a>
                        </li> 
                         <li>
                             <a href="spareslist_update.jsp">Spares Request Update</a>
                        </li>  
                          <li>
                             <a href="spareslist_serviceco2_Completed2.jsp">Spares_List_Completed </a>
                        </li> 
                    </ul>
                </div>
            </div>
            <!-- /.navbar-static-side -->
        </nav>