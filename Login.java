package com.servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.beans.UserBean;
import com.Controller.DBConnect;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String utype=request.getParameter("usertype");
		System.out.println("usertype is"+utype);
		if(utype==null)
		{
			RequestDispatcher rd=request.getRequestDispatcher("success.jsp?msg=select user type&&to=login.jsp");
			rd.forward(request, response);

		}
		String uname=request.getParameter("uname");
		String pass=request.getParameter("pass");
		if(utype.equals("Medical Cloud Server"))
		{
			if(uname.equals("admin")&&pass.equals("admin"))
			{
				response.sendRedirect("adminhome.jsp");
			}else{
				RequestDispatcher rd=request.getRequestDispatcher("Medicalcloudsucess.jsp?msg=MEDICAL CLOUD SERVER has a  login failed &&to=login.jsp");
				rd.forward(request, response);

			}
		}else if(utype.equals("Medical Image Manager")){
			UserBean u=new UserBean();
			u.setEmail(uname);
			u.setPass(pass);
			 try {
				if(DBConnect.checkLog(u, "user"))
				{
					HttpSession h=request.getSession();
					h.setAttribute("email", uname);
					response.sendRedirect("ManagerUserhome.jsp");
				}else{
					RequestDispatcher rd=request.getRequestDispatcher("success.jsp?msg=IMAGE MANAGER has a login failed &&to=login.jsp");
					rd.forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(utype.equals("Healthcare Provider")){
			UserBean u=new UserBean();
			u.setEmail(uname);
			u.setPass(pass);
			 try {
				if(DBConnect.checkLog(u, "health"))
				{
					HttpSession h=request.getSession();
					h.setAttribute("email", uname);
					response.sendRedirect("HealthproviderHome.jsp");
				}else{
					RequestDispatcher rd=request.getRequestDispatcher("healthsuccess.jsp?msg=Health Provider has  login failed &&to=login.jsp");
					rd.forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(utype.equals("Patient")){
			UserBean u=new UserBean();
			u.setEmail(uname);
			u.setPass(pass);
			 try {
				if(DBConnect.checkLog(u, "patient"))
				{
					HttpSession h=request.getSession();
					h.setAttribute("email", uname);
					response.sendRedirect("PatientLogin.jsp");
				}else{
					RequestDispatcher rd=request.getRequestDispatcher("patientsuccess.jsp?msg=Patient has a  login failed &&to=login.jsp");
					rd.forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doget(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
