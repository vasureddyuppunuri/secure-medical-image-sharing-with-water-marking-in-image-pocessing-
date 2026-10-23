package com.servlets;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Controller.DBConnect;
import com.beans.PatientBeans;
import com.beans.UserBean;

/**
 * Servlet implementation class PatientReg
 */
@WebServlet("/PatientReg")
public class PatientReg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PatientReg() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*String uname=request.getParameter("uname");
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
			int i=DBConnect.PatientRegister(u);
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
			e.printStackTrace();*/
		/*
		 * String uname = request.getParameter("uname"); String email =
		 * request.getParameter("email"); String pass = request.getParameter("pass");
		 * String gen = request.getParameter("gen");
		 * 
		 * String patientId = generatePatientId(); // generate unique ID
		 * 
		 * PatientBeans u = new PatientBeans(); u.setPatientId(patientId);
		 * u.setCname(uname); u.setEmail(email); u.setGender(gen); u.setPass(pass);
		 * u.setStatus("Pending");
		 * 
		 * try { int i = DBConnect.PatientRegister(u); if (i > 0) { RequestDispatcher rd
		 * = request.getRequestDispatcher(
		 * "success.jsp?msg=Registered successfully! Your Patient ID is " + patientId +
		 * " (waiting for doctor approval) &&to=login.jsp" ); rd.forward(request,
		 * response); } else { RequestDispatcher rd = request.getRequestDispatcher(
		 * "success.jsp?msg=Registration failed &&to=reg.jsp" ); rd.forward(request,
		 * response); } } catch (SQLException e) { e.printStackTrace(); } }
		 * 
		 * private String generatePatientId() { String prefix = "PAT"; int randomNum =
		 * (int)(Math.random() * 90000) + 10000; // 5-digit random return prefix +
		 * randomNum; } }
		 */
		 String uname = request.getParameter("uname");
	        String email = request.getParameter("email");
	        String pass = request.getParameter("pass");
	        String gen = request.getParameter("gen");

	        // generate unique Patient ID
	        String patientId = generatePatientId();

	        // ✅ get Server IPv4 address
	        String serverIp = InetAddress.getLocalHost().getHostAddress();

	        PatientBeans u = new PatientBeans();
	        u.setPatientId(patientId);
	        u.setCname(uname);
	        u.setEmail(email);
	        u.setGender(gen);
	        u.setPass(pass);
	        u.setStatus("Pending");
	        u.setIpAddress(serverIp);   // 👈 storing server IPv4 instead of client

	        try {
	            int i = DBConnect.PatientRegister(u);
	            if (i > 0) {
	                RequestDispatcher rd = request.getRequestDispatcher(
	                        "success.jsp?msg=Registered successfully! Your Patient ID is "
	                                + patientId
	                                + " (waiting for doctor approval) &&to=login.jsp");
	                rd.forward(request, response);
	            } else {
	                RequestDispatcher rd = request.getRequestDispatcher(
	                        "success.jsp?msg=Registration failed (Duplicate Email/Patient ID) &&to=reg.jsp");
	                rd.forward(request, response);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            response.sendRedirect("reg.jsp?msg=Database error: " + e.getMessage());
	        }
	    }

	    private String generatePatientId() {
	        String prefix = "PAT";
	        int randomNum = (int) (Math.random() * 90000) + 10000; // 5-digit random
	        return prefix + randomNum;
	    }
	}