package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.Controller.DBConnect;

@WebServlet("/DownloadPrescriptionServlet")
public class DownloadPrescriptionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pid = request.getParameter("pid");
        if (pid == null || pid.trim().isEmpty()) {
            response.sendRedirect("ViewPrescriptionServlet?msg=Invalid Prescription ID");
            return;
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnect.connect();
            String sql = "SELECT prescription_text, created_at FROM prescriptions WHERE pid = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, pid);
            rs = ps.executeQuery();

            if (rs.next()) {
                String prescriptionText = rs.getString("prescription_text");
                String createdAt = rs.getString("created_at");

                // Set response headers for download
                response.setContentType("text/plain");
                response.setHeader("Content-Disposition", "attachment; filename=Prescription_" + pid + ".txt");

                PrintWriter out = response.getWriter();
                out.println("Prescription ID: " + pid);
                out.println("Date: " + createdAt);
                out.println("----------------------------------");
                out.println(prescriptionText);
                out.close();
            } else {
                response.sendRedirect("ViewPrescriptionServlet?msg=Prescription not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ViewPrescriptionServlet?msg=Error downloading prescription");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}
