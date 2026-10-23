package com.servlets;

import java.io.IOException;
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
 * Servlet implementation class Reg
 */
@WebServlet("/Reg")
public class Reg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Reg() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uname=request.getParameter("uname");
		String email=request.getParameter("email");
		String pass=request.getParameter("pass");
		String gen=request.getParameter("gen");
		String usertype=request.getParameter("usertype");
		UserBean u=new UserBean();
		u.setCname(uname);
		u.setEmail(email);
		u.setGender(gen);
		u.setPass(pass);
		u.setUsertype(usertype);
		try {
			int i=DBConnect.Register(u);
			if(i>0)
			{
				RequestDispatcher rd=request.getRequestDispatcher("success.jsp?msg=registered successfully &&to=login.jsp");
				rd.forward(request, response);
			}else
			{
				RequestDispatcher rd=request.getRequestDispatcher("success.jsp?msg=registration failed&&to=reg.jsp");
				rd.forward(request, response);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
