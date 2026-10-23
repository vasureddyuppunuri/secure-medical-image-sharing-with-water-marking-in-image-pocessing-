package com.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CloudLogin")
public class CloudLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 🔑 Static credentials
    private static final String STATIC_USER = "cloud";
    private static final String STATIC_PASS = "cloud";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userid = request.getParameter("userid");
        String password = request.getParameter("password");

        if (STATIC_USER.equals(userid) && STATIC_PASS.equals(password)) {
            // ✅ Login success
            HttpSession session = request.getSession();
            session.setAttribute("user", userid);

            response.sendRedirect("CloudDashboard.jsp");
        } else {
            // ❌ Invalid login -> go back to login.jsp with error message
            response.sendRedirect("CloudLogin.jsp?msg=Invalid User ID or Password!");
        }
    }
}
