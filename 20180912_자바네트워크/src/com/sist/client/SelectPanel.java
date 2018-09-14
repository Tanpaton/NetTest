package com.sist.client;
import java.awt.*;
import javax.swing.*;
/*
 *   1. JFrame, JWindow : BorderLayout
 *         East, West, Center, North, South
 *   2. JPanel : FlowLayout => 1Â÷¹è¿­ 
 *         
 */
public class SelectPanel extends JPanel {
	
	JButton b1, b2;
	
	public SelectPanel() {
		setLayout(new BorderLayout());
		//Image g1=getImageSizeChange(icon, width, height)
		b1=new JButton("", new ImageIcon("c:\\javaDev\\toslogo.jpg"));
		b2=new JButton("", new ImageIcon("c:\\javaDev\\foodlogo.png"));
		JPanel p=new JPanel();
		p.add(b1); p.add(b2);
		
		add("Center",p);
	}
	public Image getImageSizeChange(ImageIcon icon,int width,int height)
    {
          Image img=icon.getImage();
          Image change=img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
          return change;
    }
}
