package com.sist.client;
import java.awt.*;
import javax.swing.*;
public class GameView extends Canvas{
   int sx=-50,sy=-50,ex=0,ey=0;
   public void paint(Graphics g)
   {
	    //g.setColor(Color.WHITE);
	    //g.fillRect(0, 0, getWidth(), getHeight());
	   
	    g.setColor(Color.black);
		 //g.fillOval(sx, sy, 7, 7);
		g.drawLine(sx, sy, ex, ey);
		
		sx=ex;
		sy=ey;
	   
   }
   public void update(Graphics g){

	   paint(g);

   }
   
}
