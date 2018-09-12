package com.sist.client;

import java.util.*;
import java.awt.*;
import javax.swing.*;

import com.sist.common.Function;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
//네트워크 관련
import java.net.*;

public class MainFrame extends JFrame implements ActionListener, Runnable {
	Login login = new Login();
	WaitRoom wr = new WaitRoom();
	CardLayout card = new CardLayout();
	// 네트워크(전화기 필요)
	Socket s;
	BufferedReader in; // 서버에서 들어오는 결과값 받기
	OutputStream out; // 서버에서 들어오는 요청값보내기
	String id;
	String name;

	public MainFrame() {
		setLayout(card);
		add("LOGIN", login);
		add("WR", wr);
		setSize(1251, 750);
		setVisible(true);

		login.b1.addActionListener(this);
		//login.b2.addActionListener(this);
		wr.tf.addActionListener(this);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainFrame();
	}

	// 서버와 연결
	public void connection(String id, String name) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == login.b1) {		
			try {
				s = new Socket("211.238.142.65", 7339);
				// s=new Socket("211.238.142.46",3355);
				id = login.tf.getText();
				name = login.pf.getText();
				in = new BufferedReader(new InputStreamReader(s.getInputStream())); 
				System.out.println("in: "+in);
				// 서버에게 메세지값을 보낸다
				out = s.getOutputStream();
				out.write((Function.LOGIN + "|" + id + "|" + name + "\n").getBytes());
				
			} catch (Exception e1) {
				// TODO: handle exception
			}
			card.show(getContentPane(), "WR");
		}
		if(e.getSource() == wr.tf) {
	         String str = wr.tf.getText();
	         try {
	        	 out = s.getOutputStream();
	        	 out.write((Function.CH + "|" + id + "|" + name + "\n").getBytes());
			} catch (Exception e2) {
				// TODO: handle exception
			}
	         wr.ta.append(s + "\n");
	         wr.tf.setText("");
		}
		
	}

	@Override
	public void run() {
		
	}

}
