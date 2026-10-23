package com.servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Controller.DBConnect;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
 * Servlet implementation class SortImages
 */
@WebServlet("/SortImages")
public class SortImages extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SortImages() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String content=request.getParameter("content");
	
			TreeMap< String,Integer> tm=new TreeMap<String, Integer>(); 
		
			ResultSet r=DBConnect.getImages();
			while(r.next())
			{	int count=0;
				String cs=r.getString("ides");
				String[] sc=content.split(" ");
				for(String s:sc)
				{
					if(cs.contains(s))
					{
						count++;
					}

				}
				System.out.println(r.getString("iname")+" ; "+r.getString(1)+" : "+count);
				tm.put(r.getString(1), count);
			}
			System.out.println(tm);
			System.out.println(DBConnect.entriesSortedByValues(tm));
			response.sendRedirect("dispalyimages.jsp?map="+tm);
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
