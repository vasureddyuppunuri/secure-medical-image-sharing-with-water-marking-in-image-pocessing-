package com.testing;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.sun.org.apache.bcel.internal.classfile.Attribute;
public class Removewatermark {
	public static void main(String[] args) throws IOException {
		File origFile = new File("D:/abc.jpg");
		ImageIcon icon = new ImageIcon(origFile.getPath());
		// create BufferedImage object of same width and height as of original image
		BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(),
		icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		bufferedImage=ImageIO.read(origFile);
	
		
		File origFile2 = new File("D:/backmail.jpg");
		ImageIcon icon2 = new ImageIcon(origFile.getPath());
		// create BufferedImage object of same width and height as of original image
		BufferedImage bufferedImage2 = new BufferedImage(icon.getIconWidth(),
		icon2.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		bufferedImage2=ImageIO.read(origFile2);
	
		
		
	// create graphics object and add original image to it
		Graphics graphics = bufferedImage.getGraphics();
		//graphics.drawImage(icon.getImage(), 1, 0, null);
		// set font for the watermark text
	//	System.out.println(bufferedImage.getRGB(icon.getIconWidth()/1, icon.getIconHeight()/1));
		System.out.println(bufferedImage.getRGB(icon.getIconWidth()/2, icon.getIconHeight()/2));
		System.out.println(bufferedImage.getRGB(icon.getIconWidth()/3, icon.getIconHeight()/3));
		System.out.println(bufferedImage.getRGB(icon.getIconWidth()/4, icon.getIconHeight()/4));
		System.out.println(bufferedImage.getRGB(icon.getIconWidth()/5, icon.getIconHeight()/5));
		System.out.println(bufferedImage.getRGB(icon.getIconWidth()/6, icon.getIconHeight()/6));
		System.out.println(bufferedImage.getRGB(icon.getIconWidth()/7, icon.getIconHeight()/7));
		System.out.println(bufferedImage.getRGB(icon.getIconWidth()/7, icon.getIconHeight()/7));
		System.out.println(bufferedImage.getRGB(icon.getIconWidth()/8, icon.getIconHeight()/8));
		System.out.println(bufferedImage.getRGB(icon.getIconWidth()/9, icon.getIconHeight()/9));
		
		int[] img1 ={
				//bufferedImage.getRGB(icon.getIconWidth()/1, icon.getIconHeight()/1),
				bufferedImage.getRGB(icon.getIconWidth()/2, icon.getIconHeight()/2),
				bufferedImage.getRGB(icon.getIconWidth()/3, icon.getIconHeight()/3),
				bufferedImage.getRGB(icon.getIconWidth()/4, icon.getIconHeight()/4),
				bufferedImage.getRGB(icon.getIconWidth()/5, icon.getIconHeight()/5),
				bufferedImage.getRGB(icon.getIconWidth()/6, icon.getIconHeight()/6),
				bufferedImage.getRGB(icon.getIconWidth()/7, icon.getIconHeight()/7),
				bufferedImage.getRGB(icon.getIconWidth()/8, icon.getIconHeight()/8),
				bufferedImage.getRGB(icon.getIconWidth()/9, icon.getIconHeight()/9),
				
				
		};
		
		int[] img2={
				
			//	bufferedImage2.getRGB(icon2.getIconWidth()/1, icon2.getIconHeight()/1),
				bufferedImage2.getRGB(icon2.getIconWidth()/2, icon2.getIconHeight()/2),
				bufferedImage2.getRGB(icon2.getIconWidth()/3, icon2.getIconHeight()/3),
				bufferedImage2.getRGB(icon2.getIconWidth()/4, icon2.getIconHeight()/4),
				bufferedImage2.getRGB(icon2.getIconWidth()/5, icon2.getIconHeight()/5),
				bufferedImage2.getRGB(icon2.getIconWidth()/6, icon2.getIconHeight()/6),
				bufferedImage2.getRGB(icon2.getIconWidth()/7, icon2.getIconHeight()/7),
				bufferedImage2.getRGB(icon2.getIconWidth()/8, icon2.getIconHeight()/8),
				bufferedImage2.getRGB(icon2.getIconWidth()/9, icon2.getIconHeight()/9),
				};
		
		
		for(int i=0;i<=7;i++)
		{
			System.out.println(img1[i]+"    --     "+img2[i]);
			if(img1[i]==img2[i])
			{
				System.out.println("true");
			}else{
				System.out.println("false");
			}
		}
		}
}
