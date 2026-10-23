package com.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.Controller.DBConnect;

@WebServlet("/DoctorLoginServlet")
public class DoctorLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnect.connect();

            String sql = "SELECT doctor_id, name FROM doctors WHERE email=? AND password=? AND status='Active'";
            ps = con.prepareStatement(sql);
            ps.setString(1, email.trim());
            ps.setString(2, password.trim());

            rs = ps.executeQuery();

            if (rs.next()) {

                HttpSession session = request.getSession(true);
                session.setAttribute("doctorId", rs.getString("doctor_id").trim());
                session.setAttribute("doctorName", rs.getString("name"));
                session.setMaxInactiveInterval(30 * 60); // 30 minutes

                response.sendRedirect("DoctorDashboard.jsp");

            } else {
                response.getWriter().println("Invalid credentials or account not approved.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}