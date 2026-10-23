package com.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Controller.DBConnect;

/**
 * Servlet implementation class PatientLoginDataServlet
 */
@WebServlet("/PatientLoginDataServlet")
public class PatientLoginDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PatientLoginDataServlet() {
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
		 /* String patientId = request.getParameter("patient_id");
	        String pass = request.getParameter("pass");

	        Connection con = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        try {
	            con = DBConnect.connect();
	            String sql = "SELECT * FROM patients WHERE patient_id=? AND pass=?";
	            ps = con.prepareStatement(sql);
	            ps.setString(1, patientId);
	            ps.setString(2, pass);
	            rs = ps.executeQuery();

	            if (rs.next()) {
	                String status = rs.getString("status");

	                if ("Active".equalsIgnoreCase(status)) {
	                    // ✅ Create Session and store patientId
	                    HttpSession session = request.getSession();
	                    session.setAttribute("patientId", patientId);
	                    session.setAttribute("patientName", rs.getString("uname")); 
	                    session.setAttribute("email", rs.getString("email"));

	                    response.sendRedirect("PatientHome.jsp");

	                } else if ("Pending".equalsIgnoreCase(status)) {
	                    response.sendRedirect("PatientLoginData.jsp?msg=Your account is still pending doctor approval.");
	                } else if ("Rejected".equalsIgnoreCase(status)) {
	                    response.sendRedirect("PatientLoginData.jsp?msg=Your registration has been rejected. Contact hospital.");
	                } else {
	                    response.sendRedirect("PatientLoginData.jsp?msg=Invalid account status.");
	                }
	            } else {
	                response.sendRedirect("PatientLoginData.jsp?msg=Invalid Patient ID or Password.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendRedirect("PatientLoginData.jsp?msg=Login error, try again.");
	        } finally {
	            try { if (rs != null) rs.close(); } catch (Exception e) {}
	            try { if (ps != null) ps.close(); } catch (Exception e) {}
	            try { if (con != null) con.close(); } catch (Exception e) {}
	        }
	    }
	}*/
		String patientId = request.getParameter("patient_id");
        String pass = request.getParameter("pass");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnect.connect();
            String sql = "SELECT * FROM patients WHERE patient_id=? AND pass=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, patientId);
            ps.setString(2, pass);
            rs = ps.executeQuery();

            if (rs.next()) {
                String status = rs.getString("status");

                if ("Active".equalsIgnoreCase(status)) {
                    // ✅ Create Session and store patientId
                    HttpSession session = request.getSession();
                    session.setAttribute("patientId", patientId);
                    session.setAttribute("patientName", rs.getString("uname"));
                    session.setAttribute("email", rs.getString("email"));

                    response.sendRedirect("PatientHome.jsp");

                } else if ("Pending".equalsIgnoreCase(status)) {
                    response.sendRedirect("PatientLogin.jsp?msg=Your account is still pending doctor approval.");
                } else {
                    response.sendRedirect("PatientLogin.jsp?msg=Your account is not active. Contact hospital.");
                }

            } else {
                response.sendRedirect("PatientLogin.jsp?msg=Invalid Patient ID or Password.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("PatientLogin.jsp?msg=Login error, try again.");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}