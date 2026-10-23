package com.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Controller.DBConnect;
import com.beans.MsgBean;

/**
 * Servlet implementation class ResendMsg
 */
@WebServlet("/ResendMsg")
public class ResendMsg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResendMsg() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String to=request.getParameter("uname");
		HttpSession hs=request.getSession();
		String from=(String)hs.getAttribute("email");
		String parpus=request.getParameter("parpus");
		String cid=request.getParameter("msg");
		String sname=request.getParameter("cname");;
		MsgBean mb=new MsgBean();
		mb.setFrom((String)hs.getAttribute("email"));
		mb.setTo(to);
		mb.setParpus(parpus);
		mb.setCid(cid);
		mb.setCname(sname);
		int i;
		try {
			i = DBConnect.storeResMsgs(mb);
			if (i > 0)
    	 	{
    	 			response .sendRedirect("success.jsp?msg=Response sended successfully&&to=userhome.jsp");
    	 	}		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
