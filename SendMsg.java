package com.servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import com.Controller.DBConnect;
import com.beans.MsgBean;

/**
 * Servlet implementation class SendMsg
 */
@WebServlet("/SendMsg")
public class SendMsg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMsg() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String iid=request.getParameter("iid");
		HttpSession hs=request.getSession();
		String iname="",ioemail="";
		try {
			
			ResultSet r2=DBConnect.getImages();
			while(r2.next())
			{
				if(r2.getString("iid").equals(iid))
				{
					iname=r2.getString("iname");
					ioemail=r2.getString("ioemail");
				}
			}
			MsgBean mb=new MsgBean();
			mb.setFrom((String)hs.getAttribute("email"));
			mb.setTo(ioemail);
			mb.setParpus("request for Image Key");
			mb.setCid(iid);
			mb.setCname(iname);
			int i=DBConnect.storeMsgs(mb);
			if (i > 0)
    	 	{
    	 			response .sendRedirect("success.jsp?msg=Message sended successfully&&to=userhome.jsp");
    	 	}
		} catch (SQLException e) {
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
