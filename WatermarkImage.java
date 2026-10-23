package com.testing;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.Controller.ImageEncrypt;
 
public class WatermarkImage {
 
      public static void main(String[] args) {
 
      
        	byte[] image = null;
    		Connection con = null;
    		byte[] imgData = null;
    		byte[] key=null;
    		Statement stmt = null;
    		ResultSet rs = null;
    		try {
  
            File origFile = new File("temp.jpg");
            ImageIcon icon = new ImageIcon(origFile.getPath());
            // create BufferedImage object of same width and height as of original image
            BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
 
            // create graphics object and add original image to it
            Graphics graphics = bufferedImage.getGraphics();
            graphics.drawImage(icon.getImage(), 0, 0, null);
 
            // set font for the watermark text
            graphics.setFont(new Font("Arial", Font.BOLD, 30));
 
            //unicode characters for (c) is \u00a9
            String watermark = "lavan nirudi";
 
            // add the watermark text
            graphics.drawString(watermark, 0, icon.getIconHeight() / 2);
            
 
            File newFile = new File("D:/abc.jpg");
            try {
                  ImageIO.write(bufferedImage, "jpg", newFile);
            } catch (IOException e) {
                  e.printStackTrace();
            }
 
            System.out.println(newFile.getPath() + " created successfully!");
    		}catch(Exception e)
    		{
    			e.printStackTrace();
    		}
      }
 
}
