package com.sist.client;
import javax.swing.*;// J~
import java.awt.*;// Color,Layout
import javax.swing.table.*;
public class GameRoom extends JPanel{
    JButton b1,b2,b3;
    JLabel la1,la2,la3,la4;
    JTextField tf1,tf2,tf3,tf4;
    JTextArea ta;
    JTextField tf;
    JPanel gp;
    
    
    GameRoom()
    {
    	// 초기값
    	b1=new JButton("게임준비");
    	b2=new JButton("게임시작");
    	b3=new JButton("나가기"); // 클래스 초기화
    	la1=new JLabel(new ImageIcon("c:\\javaDev\\m2.jpg"));
    	la2=new JLabel(new ImageIcon("c:\\javaDev\\m2.jpg"));
    	la3=new JLabel(new ImageIcon("c:\\javaDev\\m2.jpg"));
    	la4=new JLabel(new ImageIcon("c:\\javaDev\\m2.jpg"));
    	
    	tf1=new JTextField();
    	tf2=new JTextField();
    	tf3=new JTextField();
    	tf4=new JTextField();
    	
    	ta=new JTextArea();
    	JScrollPane js=new JScrollPane(ta);
    	tf=new JTextField();
    	
    	gp=new JPanel();
    	gp.setBackground(Color.GREEN);
    	// 배치
    	setLayout(null);
    	// null 직접 배치
    	la1.setBounds(10, 15, 80, 80);
    	tf1.setBounds(10, 100, 80, 30);
    	gp.setBounds(95, 15, 500, 500);
    	la2.setBounds(600, 15, 80, 80);
    	tf2.setBounds(600, 100, 80, 30);// 515-30 480-80
    	
    	la3.setBounds(10, 400, 80, 80);
    	tf3.setBounds(10, 485, 80, 30);
    	la4.setBounds(600, 400, 80, 80);
    	tf4.setBounds(600, 485, 80, 30);
    	
    	add(la1);
    	add(tf1);
    	add(gp);
    	add(la2);
    	add(tf2);
    	
    	add(la3);
    	add(tf3);
    	add(la4);
    	add(tf4);
    	
    	js.setBounds(10, 520, 670, 150);
    	add(js);
    	tf.setBounds(10,675,670,30);
    	add(tf);
    	
    	JPanel p=new JPanel();
    	p.setLayout(new GridLayout(3, 1,5,5));
    	p.add(b1);
    	p.add(b2);
    	p.add(b3);
    	
    	p.setBounds(685, 520, 140,180);
    	add(p);
    	// div {x:10 y:10 }
    	// 윈도우 크기 결정
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        new GameRoom();
	}

}






