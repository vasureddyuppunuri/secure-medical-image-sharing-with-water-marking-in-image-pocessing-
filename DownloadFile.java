package com.servlets;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Controller.ImageEncrypt;

/**
 * Servlet implementation class Download
 */
@WebServlet("/DownloadFile")
public class DownloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int BUFFER_SIZE = 4096;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		String iid=request.getParameter("iid");
		byte[] image = null;
		Connection con = null;
		byte[] imgData = null;
		byte[] key=null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vtjns07", "root", "root");
			stmt = con.createStatement();
			rs = stmt.executeQuery("select ikey,image from images where iid="+iid);
			if (rs.next()) {
				image = rs.getBytes("image");
				key=rs.getBytes("ikey");
				//imgData = image.getBytes(1, (int) image.length());
			} else {
				return;
			}
			// display the image
			response.setContentType("application/jpeg");
			
			SecretKey key2 = new SecretKeySpec(key, 0, key.length, "AES");
			imgData=ImageEncrypt.decryptPdfFile(key2, image);
			System.out.println(key2);
			OutputStream o = response.getOutputStream();
			o.write(imgData);
			o.flush();
			o.close();
		} catch (Exception e) {
			e.printStackTrace();
			/* out.println("Unable To Display image");
			out.println("Image Display Error=" + e.getMessage());
			 */return;
		} 
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				}
			
		       }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		
		doGet(request, response);
	}

}
