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
 * Servlet implementation class PatientApprovalServlet
 */
@WebServlet("/PatientApprovalServlet")
public class PatientApprovalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PatientApprovalServlet() {
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
		 int pid = Integer.parseInt(request.getParameter("pid"));
	        String action = request.getParameter("action");
	        String newStatus = "Pending";

	        if ("approve".equalsIgnoreCase(action)) {
	            newStatus = "Active";
	        } else if ("reject".equalsIgnoreCase(action)) {
	            newStatus = "Rejected";
	        }

	        try (Connection con = DBConnect.connect()) {
	            String sql = "UPDATE patients SET status=? WHERE pid=?";
	            PreparedStatement ps = con.prepareStatement(sql);
	            ps.setString(1, newStatus);
	            ps.setInt(2, pid);
	            ps.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        response.sendRedirect("DoctorApproval.jsp");
	    }
	
	}


