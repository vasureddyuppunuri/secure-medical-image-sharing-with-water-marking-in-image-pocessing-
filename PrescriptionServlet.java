package com.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Controller.DBConnect;

@WebServlet("/PrescriptionServlet")
public class PrescriptionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String doctorId = request.getParameter("doctor_id");
        String appointmentId = request.getParameter("appointment_id");
        String prescriptionText = request.getParameter("prescription_text");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String message = "";
        try {
            con = DBConnect.connect();

            // ✅ Fetch patient email only if appointment is approved
            String fetchSql = "SELECT patient_email FROM appointments WHERE aid=? AND doctor_id=? AND status='Approved'";
            ps = con.prepareStatement(fetchSql);
            ps.setInt(1, Integer.parseInt(appointmentId));
            ps.setString(2, doctorId);
            rs = ps.executeQuery();

            if (rs.next()) {
                String patientEmail = rs.getString("patient_email");
                rs.close();
                ps.close();

                // ✅ Insert into prescriptions
                String insertSql = "INSERT INTO prescriptions (appointment_id, doctor_id, patient_email, prescription_text) VALUES (?, ?, ?, ?)";
                ps = con.prepareStatement(insertSql);
                ps.setInt(1, Integer.parseInt(appointmentId));
                ps.setString(2, doctorId);
                ps.setString(3, patientEmail);
                ps.setString(4, prescriptionText);

                int row = ps.executeUpdate();
                if (row > 0) {
                    message = " Prescription sent successfully!";
                } else {
                    message = " Failed to send prescription.";
                }
            } else {
                message = " No approved appointment found for this doctor and appointment ID.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "Error: " + e.getMessage();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }

        // ✅ Forward message to JSP
        request.setAttribute("msg", message);
        RequestDispatcher rd = request.getRequestDispatcher("prescription_status.jsp");
        rd.forward(request, response);
    }
}
