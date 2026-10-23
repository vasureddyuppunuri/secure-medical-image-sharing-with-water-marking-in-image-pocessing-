package com.Controller;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import com.beans.MsgBean;
import com.beans.PatientBeans;
import com.beans.RBean;
import com.beans.UserBean;
import com.Controller.DBConnect;

public class DBConnect {

	public static Connection connect()
	{
		Connection con = null;
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/vtjim02-2025","root","root");
		
			return con;
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
			return con;
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return con;
		}
		
	}

	
	//Method for Customer Registration
	public static int Register(UserBean u) throws SQLException
	{
		Connection con =DBConnect.connect();
		String sql="insert into user values(0,?,?,?,?,?,?);";
		PreparedStatement p=con.prepareStatement(sql);
		p.setString(1, u.getCname());
		p.setString(3, u.getPass());
		p.setString(2, u.getEmail());
		p.setString(4, u.getGender());
		p.setString(6,u.getUsertype());
		p.setString(5, "false");
		int i=p.executeUpdate();
		return i;
	}
	
	public static int HealthRegister(UserBean u) throws SQLException
	{
		Connection con =DBConnect.connect();
		String sql="insert into health values(0,?,?,?,?,?,?);";
		PreparedStatement p=con.prepareStatement(sql);
		p.setString(1, u.getCname());
		p.setString(3, u.getPass());
		p.setString(2, u.getEmail());
		p.setString(4, u.getGender());
		p.setString(6,u.getUsertype());
		p.setString(5, "false");
		int i=p.executeUpdate();
		return i;
	}
	
	public static int PatientRegister(UserBean u) throws SQLException
	{
		Connection con =DBConnect.connect();
		String sql="insert into patient values(0,?,?,?,?,?,?);";
		PreparedStatement p=con.prepareStatement(sql);
		p.setString(1, u.getCname());
		p.setString(3, u.getPass());
		p.setString(2, u.getEmail());
		p.setString(4, u.getGender());
		p.setString(6,u.getUsertype());
		p.setString(5, "false");
		int i=p.executeUpdate();
		return i;
	}
	//Method For Check userLogin
	public static boolean checkLog(UserBean u, String db) throws SQLException
	{
		Connection con =DBConnect.connect();
		String sql="select * from "+db+";";
		Statement s=con.createStatement();
		ResultSet r=s.executeQuery(sql);
		int count=0;
		while(r.next())
		{
			if(r.getString(4).equals(u.getPass())&&r.getString(3).equals(u.getEmail()))
			{
				count++;
			}
		}
		if(count>0)
		{
			return true;
		}else
		{
			return false;
		}
		
	}
	
	//Method for get userDetails
	public static ResultSet getUser(String Email) throws SQLException
	{
		Connection con =DBConnect.connect();
		String sql="select * from user where email='"+Email+"'";
		Statement s=con.createStatement();
		ResultSet r=s.executeQuery(sql);
		return r;
	}
	
	//Method for get userDetails
		public static ResultSet getMsgs(String Email) throws SQLException
		{
			Connection con =DBConnect.connect();
			String sql="select * from msgs where dest='"+Email+"'";
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(sql);
			return r;
		}
	
		//Method for get userDetails
				public static ResultSet getResMsgs(String Email) throws SQLException
				{
					Connection con =DBConnect.connect();
					String sql="select * from resmsg where dest='"+Email+"'";
					Statement s=con.createStatement();
					ResultSet r=s.executeQuery(sql);
					return r;
				}
				
	//Method for Create Repository
	public static int createRep(String uid) throws SQLException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException
	{
	
		Connection con =DBConnect.connect();
		String sql="update user set upload='true' where uid="+uid;
		PreparedStatement p=con.prepareStatement(sql);
		int i=p.executeUpdate();	
	  	return i;
	}
	
	
	public static int adminRep(RBean rb) throws SQLException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException
	{
		Cipher cipher;
		  KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
	      keyGenerator.init(128);
	      Key key = keyGenerator.generateKey();
	      System.out.println(key);
	      byte[] encrypted = null;
		Connection con =DBConnect.connect();
		String sql="insert into adminrep values(0,?,?,?,?,?);";
		PreparedStatement p=con.prepareStatement(sql);
		p.setInt(1, rb.getOid());
		p.setString(2, rb.getOname());
		p.setString(3, rb.getOemail());
		p.setString(4, rb.getRname());
        p.setString(5, rb.getRdes());
		int i=p.executeUpdate();	
	  	return i;
	}
	
	//Method for getRepositories
	public static ResultSet getRep() throws SQLException
	{
		Connection con =DBConnect.connect();
		String sql="select * from repository;";
		Statement s=con.createStatement();
		ResultSet r=s.executeQuery(sql);
		return r;
	}
	//Method for getRepository requests
		public static ResultSet getReq() throws SQLException
		{
			Connection con =DBConnect.connect();
			String sql="select * from user where upload='false'";
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(sql);
			return r;
		}
		
		
		public static ResultSet getTrueReq() throws SQLException
		{
			Connection con =DBConnect.connect();
			String sql="select * from user where upload='true'";
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(sql);
			return r;
		}
		
		//Method for getRepository requests
				public static int deleteReq(int id) throws SQLException
				{
					Connection con =DBConnect.connect();
					String sql="delete from user where uid="+id;
					Statement s=con.createStatement();
					int i=s.executeUpdate(sql);
					return i;
				}
		
		//Method for store msg
		public static int storeMsgs(MsgBean mb) throws SQLException
	{
		Connection con =DBConnect.connect();
		String sql="insert into msgs values(0,?,?,?,?,?);";
		PreparedStatement p=con.prepareStatement(sql);
		p.setString(1, mb.getFrom());
		p.setString(2, mb.getTo());
		p.setString(3, mb.getParpus());
		p.setString(4, mb.getCid());
		p.setString(5, mb.getCname());
		int i=p.executeUpdate();
		return i;
	}
	
		public static boolean checkupload(String email)
		{
			try {
			Connection con =DBConnect.connect();
			String sql="select upload from user where email='"+email+"'";
			
			
			
				PreparedStatement p=con.prepareStatement(sql);
				ResultSet r=p.executeQuery();
				if(r.next())
				{
					if(r.getString("upload").equals("true"))
					{
						return true;
					}else
					{
						return false;
					}
				}else
				{
					return false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
			
		}
		
	//Method for store Resmsg
	public static int storeResMsgs(MsgBean mb) throws SQLException
	{
		Connection con =DBConnect.connect();
		String sql="insert into resmsg values(0,?,?,?,?,?);";
		PreparedStatement p=con.prepareStatement(sql);
		p.setString(2, mb.getFrom());
		p.setString(1, mb.getTo());
		p.setString(3, mb.getParpus());
		p.setString(4, mb.getCid());
		p.setString(5, mb.getCname());
		int i=p.executeUpdate();
		return i;
	}
	
	//Method for getImages
	public static List<String> getImages(String uid) throws SQLException
	{
		Connection con =DBConnect.connect();
		Statement s=con.createStatement();
		String sql1="select * from images where rid='"+uid+"'";
		List<String> al=new ArrayList();
		ResultSet r=s.executeQuery(sql1);
		if(r.next()){
			al.add(r.getString("iname"));
			al.add(r.getString("ides"));
		}
		
		
		return al;
	}
	
	public static ResultSet getImages() throws SQLException
	{
		Connection con =DBConnect.connect();
		Statement s=con.createStatement();
		String sql1="select * from images";
		ResultSet r=s.executeQuery(sql1);
		
		
		return r;
	}
	
	public static List<String> getKey(String sql) throws SQLException{
		Connection con =DBConnect.connect();
	
		Statement s=con.createStatement();
		ResultSet r=s.executeQuery(sql);
	   List<String> al=new ArrayList();
	   if(r.next()){
		   al.add(r.getString("ukey"));
		   al.add(r.getString("uid"));
	   }
	   r.close();
		return al;
		
	
		
	}
	public static <K,V extends Comparable<? super V>> 
	java.util.List<Entry<K, V>> entriesSortedByValues(Map<K,V> map) {

	    java.util.List<Entry<K,V>> sortedEntries = new ArrayList<Entry<K,V>>(map.entrySet());

	    Collections.sort(sortedEntries, 
	            new Comparator<Entry<K,V>>() {
	                @Override
	                public int compare(Entry<K,V> e1, Entry<K,V> e2) {
	                    return e2.getValue().compareTo(e1.getValue());
	                }
	            }
	    );

	    return sortedEntries;
	}


/*
 * public static int PatientRegister(PatientBeans u) throws SQLException { int i
 * = 0; try (Connection con = DBConnect.connect()) { String query =
 * "INSERT INTO patients (patient_id, uname, email, pass, gender, status) VALUES (?, ?, ?, ?, ?, ?)"
 * ; PreparedStatement ps = con.prepareStatement(query); ps.setString(1,
 * u.getPatientId()); ps.setString(2, u.getCname()); ps.setString(3,
 * u.getEmail()); ps.setString(4, u.getPass()); ps.setString(5, u.getGender());
 * ps.setString(6, u.getStatus()); i = ps.executeUpdate(); } return i; } }
 */
	public static int PatientRegister(PatientBeans u) throws SQLException {
	    int i = 0;
	    Connection con = DBConnect.connect();

	    String sql = "INSERT INTO patients(patient_id, uname, email, pass, gender, ip_address, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setString(1, u.getPatientId());
	        ps.setString(2, u.getCname());
	        ps.setString(3, u.getEmail());
	        ps.setString(4, u.getPass());
	        ps.setString(5, u.getGender());
	        ps.setString(6, u.getIpAddress());
	        ps.setString(7, u.getStatus());

	        i = ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace(); // check logs if duplicate email or patient_id
	    } finally {
	        if (con != null) con.close();
	    }

	    return i;
	}
}


