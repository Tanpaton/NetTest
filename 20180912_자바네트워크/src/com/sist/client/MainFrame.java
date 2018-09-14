package com.sist.client;

import java.util.*;
import java.awt.*;
import javax.swing.*;

import com.sist.common.Function;
import com.sist.sever.Server.Client;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
//네트워크 관련
import java.net.*;

/*
 *  1) 클라이언트에서 요청
 *     요청 => 이벤트 발생(버튼클릭, 마우스클릭, 엔터....)
 *  2) 서버
 *     case Function.LOGIN....여기서 처리
 *  3) 클라이언트 ==> 결과값을 출력
 *  
 */
public class MainFrame extends JFrame implements ActionListener, Runnable {
	Login login = new Login();
	WaitRoom wr = new WaitRoom();
	SelectPanel sp = new SelectPanel();
	MakeRoom mr = new MakeRoom();
	GameRoom gr = new GameRoom();

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
		add("SP", sp);
		add("WR", wr);
		add("GR", gr);
		setSize(1251, 750);
		setVisible(true);

		// 로그인화면 버튼
		login.b1.addActionListener(this);
		// login.b2.addActionListener(this);

		// 채팅
		wr.tf.addActionListener(this);// textfield에서 엔터쳤을때!! actionPerformed
		wr.b1.addActionListener(this);
		mr.b1.addActionListener(this);
		mr.b2.addActionListener(this);
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
				System.out.println("in: " + in);
				// 서버에게 메세지값을 보낸다
				out = s.getOutputStream();
				out.write((Function.LOGIN + "|" + id + "|" + name + "\n").getBytes());
				// 네트워크 => out.write ==> 반드시 마지막에 \n을 보내야된다.

			} catch (Exception e1) {
				// TODO: handle exception
			}
			new Thread(this).start();
		}
		// 채팅요청
		if (e.getSource() == wr.tf) {
			try {
				String msg = wr.tf.getText();
				if (msg.trim().length() < 1)
					return; // 입력안했을떄 다시입력하게한다.
				out.write((Function.CH + "|" + msg + "\n").getBytes());
				wr.tf.setText("");
				wr.tf.requestFocus(); // 포커스가 계속 있게 해주는것
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		// 방만들기창
		if (e.getSource() == wr.b1) {
			mr.tf.setText("");
			mr.pf.setText("");
			mr.rb1.setSelected(true);
			mr.la3.setVisible(false);
			mr.pf.setVisible(false);
			mr.setLocation(250, 190);
			mr.setVisible(true);
		}
		// 실제 방만들기.
		if (e.getSource() == mr.b1) {
			// 입력된 방정보 읽기
			String rname = mr.tf.getText();
			if (rname.trim().length() < 1) // 공백을 제거한 상태에서 입력이 안된 상태
			{
				JOptionPane.showMessageDialog(this, "방이름을 입력하세요");
				mr.tf.requestFocus();
				return;
			}

			// 방 이름 중복 찾고, 다시만들라하기
			String temp = "";
			for (int i = 0; i < wr.model1.getRowCount(); i++) {
				temp = wr.model1.getValueAt(i, 0).toString();
				if (temp.equals(rname)) {
					JOptionPane.showMessageDialog(this, "이미 존재하는 방입니다.\n다시 입력하세요");
					mr.tf.setText("");
					mr.tf.requestFocus();
					return;
				}
			}

			String state = "";
			String pwd = "";
			if (mr.rb1.isSelected()) // 공개버튼이 선택됐다.
			{
				state = "공개";
				pwd = "";
			} else {
				state = "비공개";
				pwd = new String(mr.pf.getPassword());
				// 또는 pwd=String.valueOf(mr.pf.getPassword());
			}
			int inwon = mr.box.getSelectedIndex();
			try {
				if(state.equals("비공개")) {
					out.write((Function.MAKEROOM + "|" + rname.trim() + "|" + state.trim() + "|" + pwd.trim() + "|" + (inwon + 2) + "\n")
						.getBytes());
				}else {
					out.write((Function.MAKEROOM + "|" + rname.trim() + "|" + state.trim() + "|" + (inwon + 2)+"\n")
							.getBytes());
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
			mr.setVisible(false);
		}
		if (e.getSource() == mr.b2) {
			mr.setVisible(false);
		}
	}

	// 출력
	@Override
	public void run() {
		try {
			// 100|id|name 이런식으로 받는다
			while (true) {
				String msg = in.readLine();// 클라이언트에서 전송한 메세지 처리후 결과값 보내기
				System.out.println("Client=>요청값 : " + msg);

				// 100|id|name 이런식으로 받는다
				// 번호 ==> 기능(행위)의 요청번호
				StringTokenizer st = new StringTokenizer(msg, "|");
				int no = Integer.parseInt(st.nextToken());
				switch (no) {
				// 출력은 case안에서
				case Function.LOGIN: {
					String[] data = { st.nextToken(), // ID
							st.nextToken(), // Name
							st.nextToken() // POS
					};
					wr.model2.addRow(data);
				}
					break;
				case Function.MYLOG: {
					card.show(getContentPane(), "WR");
				}
					break;
				case Function.CH: {
					wr.bar.setValue(wr.bar.getMaximum());
					wr.ta.append(st.nextToken() + "\n");
				}
					break;
				case Function.MAKEROOM: {
					String roomNumber=st.nextToken();
					String rname=st.nextToken();
					String state=st.nextToken();
					if(state.equals("비공개")) {
						String pwd=st.nextToken();
						String inwon=st.nextToken();
						System.out.println("비공개 인원 : "+inwon);
						String[] data = {roomNumber,rname,state,(inwon+"명"),pwd};
						wr.model1.addRow(data);
					}else{
						String inwon=st.nextToken();
						System.out.println("공개 인원 : "+inwon);
						String[] data = {roomNumber,rname,state,(inwon+"명")};
						wr.model1.addRow(data);
					}					
				}
					break;
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
