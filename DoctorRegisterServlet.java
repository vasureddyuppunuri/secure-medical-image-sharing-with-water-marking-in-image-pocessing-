package com.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Controller.DBConnect;

/**
 * Servlet implementation class DoctorRegisterServlet
 */
@WebServlet("/DoctorRegisterServlet")
public class DoctorRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoctorRegisterServlet() {
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
		doGet(request, response);
		String doctorId = request.getParameter("doctor_id");
	    String name = request.getParameter("name");
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");
	    String specialization = request.getParameter("specialization");
	    String phone = request.getParameter("phone");

	    try (Connection con = DBConnect.connect()) {

	        String sql = "INSERT INTO doctors (doctor_id, name, email, password, specialization, phone) VALUES (?, ?, ?, ?, ?, ?)";
	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setString(1, doctorId);
	        ps.setString(2, name);
	        ps.setString(3, email);
	        ps.setString(4, password);
	        ps.setString(5, specialization);
	        ps.setString(6, phone);

	        int rows = ps.executeUpdate();

	        if (rows > 0) {

	            // Store success message
	            request.setAttribute("successMsg", 
	                "Doctor Registered Successfully! Waiting for Admin Approval.");

	            // Forward to success page
	            request.getRequestDispatcher("doctor_success.jsp")
	                   .forward(request, response);

	        } else {
	            request.setAttribute("errorMsg", "Registration Failed!");
	            request.getRequestDispatcher("doctorRegister.jsp")
	                   .forward(request, response);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("errorMsg", "Something went wrong!");
	        request.getRequestDispatcher("doctorRegister.jsp")
	               .forward(request, response);
	    }
	}
}