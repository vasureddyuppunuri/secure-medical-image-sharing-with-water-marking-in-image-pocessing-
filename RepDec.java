package com.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class RepDec {
public static void main(String[] args) {
	 Connection con=ImageEncrypt.connect();
     try {
			PreparedStatement p=con.prepareStatement("select * from rks;");
			ResultSet rs=p.executeQuery();
			while(rs.next())
			{
				byte[] key=rs.getBytes(2);
				SecretKey key2 = new SecretKeySpec(key, 0, key.length, "AES");
				//System.out.println(key2.getEncoded());
				String s=rs.getString(2);
				System.out.println(s);
				if(s.equals("[B@199197b"))
				{
					System.out.println(rs.getString(1));	
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}
}
