package com.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLogin() {
        super();
        // TODO Auto-generated constructor stub
    }
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin";

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
		String userid = request.getParameter("userid");
        String password = request.getParameter("password");

        if (ADMIN_USER.equals(userid) && ADMIN_PASS.equals(password)) {
            // ✅ Login success
            HttpSession session = request.getSession();
            session.setAttribute("admin", userid);

            response.sendRedirect("adminhome.jsp?msg=Welcome Admin!");
        } else {
            // ❌ Login failed
            response.sendRedirect("AdminLogin.jsp?msg=Invalid Admin Credentials");
	}

}
}
