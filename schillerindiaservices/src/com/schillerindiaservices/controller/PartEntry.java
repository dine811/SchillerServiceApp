package com.schillerindiaservices.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.schillerindiaservices.Dao.partentrydao;
import com.schillerindiaservices.bean.partnumber_Entry;

/**
 * Servlet implementation class PartEntry
 */
@WebServlet("/PartEntry")
public class PartEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PartEntry() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("insidee contrller part entry");
		String part_no=request.getParameter("part_no");
		String brd_name=request.getParameter("brd_name");
		String compatible_models=request.getParameter("compatible_models");
		String cost=request.getParameter("cost");
		Double cost1=Double.parseDouble(cost);
		System.out.println("insidee contrller part entry"+part_no+"  "+brd_name+"  "+compatible_models+"  "+cost);
		partnumber_Entry pnt=new partnumber_Entry();
		pnt.setPartNumber(part_no);
		pnt.setBrdName(brd_name);
		pnt.setCompatiblemodels(compatible_models); 
		pnt.setCost(cost1);
		partentrydao pd=new partentrydao();
		try {
			pd.savepart_entry(pnt);
			
			 response.sendRedirect("partNumberForm.jsp");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//doGet(request, response);
	}

}
