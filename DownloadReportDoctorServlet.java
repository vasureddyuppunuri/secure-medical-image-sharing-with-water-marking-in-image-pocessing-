package com.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Controller.DBConnect;

@WebServlet("/DownloadReportDoctorServlet")
public class DownloadReportDoctorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String iid = request.getParameter("iid");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnect.connect();
            String sql = "SELECT iname, plain_image FROM images WHERE iid=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(iid));
            rs = ps.executeQuery();

            if (rs.next()) {
                String fileName = rs.getString("iname");
                byte[] fileData = rs.getBytes("plain_image");

                // Detect extension from name or default to jpg
                String lowerName = fileName.toLowerCase();
                String contentType = "image/jpeg";
                if (lowerName.endsWith(".png")) {
                    contentType = "image/png";
                } else if (lowerName.endsWith(".gif")) {
                    contentType = "image/gif";
                } else if (!lowerName.endsWith(".jpg") && !lowerName.endsWith(".jpeg")) {
                    fileName = fileName + ".jpg";
                }

                // Set proper content type for gallery apps
                response.setContentType(contentType);
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                response.setContentLength(fileData.length);

                OutputStream out = response.getOutputStream();
                out.write(fileData);
                out.flush();
            } else {
                response.getWriter().write("No image found for id: " + iid);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error: " + e.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch (Exception e) {}
            if (ps != null) try { ps.close(); } catch (Exception e) {}
            if (con != null) try { con.close(); } catch (Exception e) {}
        }
    }
}
