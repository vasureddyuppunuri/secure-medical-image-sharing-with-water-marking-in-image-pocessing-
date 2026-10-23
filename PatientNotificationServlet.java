package com.servlets;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.Controller.DBConnect;

@WebServlet("/PatientNotificationServlet")
public class PatientNotificationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public static class Notification {
        private int id;
        private String message;
        private Timestamp notifyTime;
        private boolean seen;

        public Notification(int id, String message, Timestamp notifyTime, boolean seen) {
            this.id = id;
            this.message = message;
            this.notifyTime = notifyTime;
            this.seen = seen;
        }

        public int getId() { return id; }
        public String getMessage() { return message; }
        public Timestamp getNotifyTime() { return notifyTime; }
        public boolean isSeen() { return seen; }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("user");

		/*
		 * if (email == null) {
		 * response.sendRedirect("PatientLoginData.jsp?msg=Please login first"); return;
		 * }
		 */

        List<Notification> notifications = new ArrayList<>();

        try (Connection con = DBConnect.connect()) {
            // get patient_id from email
            String sql1 = "SELECT patient_id FROM patients WHERE email=?";
            PreparedStatement ps1 = con.prepareStatement(sql1);
            ps1.setString(1, email);
            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                String patientId = rs1.getString("patient_id");

                // fetch notifications
                String sql2 = "SELECT id, message, notify_time, seen FROM patient_notifications WHERE patient_id=? ORDER BY notify_time DESC";
                PreparedStatement ps2 = con.prepareStatement(sql2);
                ps2.setString(1, patientId);
                ResultSet rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    notifications.add(new Notification(
                            rs2.getInt("id"),
                            rs2.getString("message"),
                            rs2.getTimestamp("notify_time"),
                            rs2.getInt("seen") == 1
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("notifications", notifications);
        request.getRequestDispatcher("PatientNotification.jsp").forward(request, response);
    }
}
