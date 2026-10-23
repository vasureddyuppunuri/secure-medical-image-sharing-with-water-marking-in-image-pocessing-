package com.servlets;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.Controller.DBConnect;

@WebServlet("/AttackerLogin")
public class AttackerLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // ✅ Get client IPv4 (ignore IPv6)
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty()) {
            ip = ip.split(",")[0].trim();
        } else {
            ip = request.getRemoteAddr();
        }
        // force IPv4 only
        if (ip.contains(":")) { // IPv6 detected
            return "0.0.0.0";
        }
        return ip;
    }

    // ✅ Get server IPv4 (real-time, ignore IPv6)
    private String getServerIp() {
        try {
            InetAddress inet = InetAddress.getLocalHost();
            if (inet instanceof Inet4Address) {
                return inet.getHostAddress(); // Example: 192.168.1.100
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0.0.0.0"; // fallback
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String clientIp = getClientIp(request);
        String serverIp = getServerIp();

        try (Connection con = DBConnect.connect()) {
            // 🚨 Detect basic SQL Injection attempt
            if (password.contains("'") || password.toLowerCase().contains(" or ")) {
                logAttempt(con, email, clientIp, serverIp, "SQL INJECTION ATTEMPT");
                response.sendRedirect("AttackerLogin.jsp?msg=🚨 Attack detected from Client IP: " + clientIp);
                return;
            }

            // ✅ Safe query
            String sql = "SELECT patient_id, email FROM patients WHERE email=? AND pass=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Login success
                String patientId = rs.getString("patient_id"); // patient_id from DB
                HttpSession session = request.getSession();
                session.setAttribute("user", email);

                // Log success attempt
                logAttempt(con, email, clientIp, serverIp, "SUCCESS");

                // 🚨 Send notification to patient
                String message = " Login detected!\nServer IP: " + serverIp;
                sendNotification(con, patientId, message);

                response.sendRedirect("AttackerHome.jsp");
            } else {
                // Failed login → brute force/dictionary
                logAttempt(con, email, clientIp, serverIp, "FAILED LOGIN");
                response.sendRedirect("AttackerLogin.jsp?msg=❌ Invalid Login! Attempt logged from Client IP: " + clientIp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("AttackerLogin.jsp?msg=Error: " + e.getMessage());
        }
    }

    // ✅ Helper method to log attempts
    private void logAttempt(Connection con, String email, String clientIp, String serverIp, String status) throws SQLException {
        String sql = "INSERT INTO attacker_login_attempts (email, ip_address, server_ip, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, clientIp);
            ps.setString(3, serverIp);
            ps.setString(4, status);
            ps.executeUpdate();
        }
    }

    // ✅ Helper method to notify patient
    private void sendNotification(Connection con, String patientId, String message) throws SQLException {
        String sql = "INSERT INTO patient_notifications (patient_id, message) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, patientId);
            ps.setString(2, message);
            ps.executeUpdate();
        }
    }
}
