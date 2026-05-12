package com.schillerindiaservices.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.schillerindiaservices.bean.partnumber_Entry;
import com.schillerindiaservices.connection.DbConnection;

/**
 * Servlet implementation class getsapare_parts
 */
@WebServlet("/getsapare_parts")
public class getsapare_parts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getsapare_parts() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected   void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("inside controller");
		String name=request.getParameter("val");  
		System.out.println(name);
		if(name==null||name.trim().equals(""))
		{  
		   // out.print("<p>Please Enter PartNumber!</p>");  
		}
		else
		{  
		   
		    try
		    {  
		        Connection con;
		        con=DbConnection.getConnection();
		        
//		        Class.forName("com.mysql.jdbc.Driver");  
//		        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schillerindiaservices","root","");
		        
		        PreparedStatement ps=con.prepareStatement("select * from partnumber_entry where Part_number='"+name+"'"); 
		        System.out.println("db connected");
		        ResultSet rs=ps.executeQuery();  
		        if(rs.next())
		        {
		        	System.out.println("inside whileee"+rs.getString("brd_Name"));
		        	partnumber_Entry pnt=new partnumber_Entry();
		        	pnt.setBrdName(rs.getString("brd_Name"));
		        	pnt.setCompatiblemodels(rs.getString("compatible_models"));
		        	pnt.setCost(rs.getDouble("cost"));
		           //String brd_Name=rs.getString("brd_Name");
		         //  String compatible_models=rs.getString("compatible_models");
		         //  Double Cost=rs.getDouble("compatible_models");
		          // String cost=Cost.toString();
		            System.out.println(pnt.getCompatiblemodels()+"compatible models");
		           System.out.println(pnt.getBrdName()+"brd_Name"+pnt.getCompatiblemodels()+"compatible_models"+pnt.getCost()+"cost");
		         //  out.print("<option value="+x+">"+rs.getString("dealer_name")+"</option>"); 
		           
		           Map<String,String> m1=new HashMap<String,String>();
		            m1.put("brd_name",pnt.getBrdName());
		            m1.put("comp_models",pnt.getCompatiblemodels());
		            m1.put("cost",pnt.getCost().toString()); 
		     
		         
		            response.setContentType("application/json");
		            response.setCharacterEncoding("UTF-8");
		            response.getWriter().write(new Gson().toJson(m1));
		           /*  while(rs.next())
		            {  
		                 int x=rs.getInt("dealer_id");
		                    out.print("<option value="+x+">"+rs.getString("dealer_name")+"</option>");  
		            }  */
		        }
		        System.out.println("outside whileee");
		        ps.close();
		        con.close(); 
		        
		    }catch(Exception e){}  
		     
		    
		}
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
