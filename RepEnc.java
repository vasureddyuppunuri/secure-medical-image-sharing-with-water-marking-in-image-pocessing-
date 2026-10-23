package com.Controller;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

public class RepEnc {
public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException {
	  Cipher cipher;
	  KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      keyGenerator.init(128);
      Key key = keyGenerator.generateKey();
      System.out.println(key);
      byte[] encrypted = null;
      try {
          cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
          cipher.init(Cipher.ENCRYPT_MODE, key);
          byte[] Rk=key.getEncoded();
          System.out.println(Rk);
          Connection con=ImageEncrypt.connect();
          try {
  			PreparedStatement p=con.prepareStatement("insert into rks values(?,?)");
  			p.setString(1,"lavan");
  			p.setString(2,Rk.toString());
  			System.out.println(p.executeUpdate());
  		} catch (SQLException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
          //encrypted = cipher.doFinal("lsvsn");
      } catch (Exception e) {
          e.printStackTrace();
      }
}
}
