<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.schillerindiaservices.connection.DbConnection"%>
<%@ page errorPage="error.jsp" %>  
<%--<%@page import="java.sql.DriverManager"%>--%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%  
String name=request.getParameter("val");  
System.out.println(name);
if(name==null||name.trim().equals(""))
{  
    out.print("<p>Please Enter PartNumber!</p>");  
}
else
{  
   
    try
    {  
        Connection con;
        con=DbConnection.getConnection();
        
//        Class.forName("com.mysql.jdbc.Driver");  
//        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schillerindiaservices","root","");
        
        PreparedStatement ps=con.prepareStatement("select * from partnumber_entry where Part_number='"+name+"'"); 
        System.out.println("db connected");
        ResultSet rs=ps.executeQuery();  
        if(rs.next())
        {
           String brd_Name=rs.getString("brd_Name");
           String compatible_models=rs.getString("compatible_models");
           Double Cost=rs.getDouble("compatible_models");
           String cost=Cost.toString();
            
           
         //  out.print("<option value="+x+">"+rs.getString("dealer_name")+"</option>"); 
           
           Map<String,String> m1=new HashMap<String,String>();
            m1.put("brd_name",brd_Name);
            m1.put("comp_models",compatible_models);
            m1.put("cost",cost); 
            
            String json = new Gson().toJson(m1);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            
            
            
           /*  while(rs.next())
            {  
                 int x=rs.getInt("dealer_id");
                    out.print("<option value="+x+">"+rs.getString("dealer_name")+"</option>");  
            }  */
        }
        ps.close();
        con.close(); 
    }catch(Exception e){out.print(e);}  
     
    
}  
%>  