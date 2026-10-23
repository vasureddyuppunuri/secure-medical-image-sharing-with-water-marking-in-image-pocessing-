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
 * Servlet implementation class BookAppointmentServlet
 */
@WebServlet("/BookAppointmentServlet")
public class BookAppointmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookAppointmentServlet() {
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
        String patientName = request.getParameter("patient_name");
        String patientEmail = request.getParameter("patient_email");
        String appointmentDate = request.getParameter("appointment_date");
        String hour = request.getParameter("hour");
        String minute = request.getParameter("minute");
        String ampm = request.getParameter("ampm");
        String appointmentTime = hour + ":" + minute + " " + ampm;

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnect.connect();
            String sql = "INSERT INTO appointments (doctor_id, patient_name, patient_email, appointment_date, appointment_time) VALUES (?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, doctorId);
            ps.setString(2, patientName);
            ps.setString(3, patientEmail);
            ps.setString(4, appointmentDate);
            ps.setString(5, appointmentTime);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                request.setAttribute("message", "Appointment booked successfully! Pending approval.");
            } else {
                request.setAttribute("message", "Failed to book appointment.");
            }

            request.getRequestDispatcher("PatientApointmentSuccess.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Error: " + e.getMessage());
            request.getRequestDispatcher("PatientApointmentSuccess.jsp").forward(request, response);
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }

	}

