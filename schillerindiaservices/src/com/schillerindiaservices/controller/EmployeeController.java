
package com.schillerindiaservices.controller;

import com.schillerindiaservices.Dao.BranchDao;
import com.schillerindiaservices.bean.Emploeemaster;
import com.schillerindiaservices.Dao.EmployeeDao;
import com.schillerindiaservices.Dao.ProductDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MR
 */
public class EmployeeController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EmployeeController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmployeeController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try 
        {
        int id=Integer.parseInt(request.getParameter("id"));
        String action=request.getParameter("action");
        EmployeeDao demo=new EmployeeDao();
        if(action!=null)
        {
            if(action.equalsIgnoreCase("edit"))
            { 
                Emploeemaster d=  EmployeeDao.getById(id);
                request.setAttribute("editablerecord", d);
                RequestDispatcher rd=request.getRequestDispatcher("EmployeeForm.jsp");
                rd.forward(request, response);
            }
            else
            {
                    demo.delete(id);
                    RequestDispatcher rd=request.getRequestDispatcher("EmployeeList.jsp");  
                    rd.forward(request, response);
            }
        }
        } 
        catch (ClassNotFoundException ex) {
                    Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                } 
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Emploeemaster emp=new Emploeemaster();
            String name=request.getParameter("emp_name");
            String address=request.getParameter("emp_add");
            String email=request.getParameter("emp_email");
            String password=request.getParameter("emp_pass");
            String mobile=request.getParameter("emp_mob");
            String role=request.getParameter("emp_role");
            String branch=request.getParameter("emp_branch");
            
            String active=request.getParameter("emp_active");
            String empdept=request.getParameter("dept");
            String divisionstring=request.getParameter("product");
            int divisionid=ProductDao.getid1(divisionstring);
            int id=Integer.parseInt(request.getParameter("id"));
            
            
            emp.setEmpName(name.toUpperCase());
            emp.setEmpAddress(address);
            emp.setEmpEmail(email);
            emp.setEmpPassword(password);
            emp.setEmpMobile(mobile);
            emp.setEmpRole(role);
            emp.setEmpBranch(branch);
            emp.setEmpDept(empdept);
            emp.setEmpDivision(String.valueOf(divisionid));
            if("yes".equalsIgnoreCase(active))
                emp.setEmpActive(active);
            else
                emp.setEmpActive(active);
            
            EmployeeDao empdao=new EmployeeDao();
            if(id==0)
            {
                empdao.save(emp);
                response.sendRedirect("EmployeeList.jsp");
//                RequestDispatcher rd=request.getRequestDispatcher("EmployeeList.jsp");
//                rd.forward(request, response);
            }
            else
            {
                emp.setEmpId(id);
                empdao.update(emp);
                 response.sendRedirect("EmployeeList.jsp");
//                RequestDispatcher rd=request.getRequestDispatcher("EmployeeList.jsp");
//                rd.forward(request, response);
            }
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
        }
            
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
