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
//��Ʈ��ũ ����
import java.net.*;

/*
 *  1) Ŭ���̾�Ʈ���� ��û
 *     ��û => �̺�Ʈ �߻�(��ưŬ��, ���콺Ŭ��, ����....)
 *  2) ����
 *     case Function.LOGIN....���⼭ ó��
 *  3) Ŭ���̾�Ʈ ==> ������� ���
 *  
 */
public class MainFrame extends JFrame implements ActionListener, Runnable {
	Login login = new Login();
	WaitRoom wr = new WaitRoom();
	SelectPanel sp = new SelectPanel();
	MakeRoom mr = new MakeRoom();
	GameRoom gr = new GameRoom();

	CardLayout card = new CardLayout();
	// ��Ʈ��ũ(��ȭ�� �ʿ�)
	Socket s;
	BufferedReader in; // �������� ������ ����� �ޱ�
	OutputStream out; // �������� ������ ��û��������
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

		// �α���ȭ�� ��ư
		login.b1.addActionListener(this);
		// login.b2.addActionListener(this);

		// ä��
		wr.tf.addActionListener(this);// textfield���� ����������!! actionPerformed
		wr.b1.addActionListener(this);
		mr.b1.addActionListener(this);
		mr.b2.addActionListener(this);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainFrame();
	}

	// ������ ����
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
				// �������� �޼������� ������
				out = s.getOutputStream();
				out.write((Function.LOGIN + "|" + id + "|" + name + "\n").getBytes());
				// ��Ʈ��ũ => out.write ==> �ݵ�� �������� \n�� �����ߵȴ�.

			} catch (Exception e1) {
				// TODO: handle exception
			}
			new Thread(this).start();
		}
		// ä�ÿ�û
		if (e.getSource() == wr.tf) {
			try {
				String msg = wr.tf.getText();
				if (msg.trim().length() < 1)
					return; // �Է¾������� �ٽ��Է��ϰ��Ѵ�.
				out.write((Function.CH + "|" + msg + "\n").getBytes());
				wr.tf.setText("");
				wr.tf.requestFocus(); // ��Ŀ���� ��� �ְ� ���ִ°�
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		// �游���â
		if (e.getSource() == wr.b1) {
			mr.tf.setText("");
			mr.pf.setText("");
			mr.rb1.setSelected(true);
			mr.la3.setVisible(false);
			mr.pf.setVisible(false);
			mr.setLocation(250, 190);
			mr.setVisible(true);
		}
		// ���� �游���.
		if (e.getSource() == mr.b1) {
			// �Էµ� ������ �б�
			String rname = mr.tf.getText();
			if (rname.trim().length() < 1) // ������ ������ ���¿��� �Է��� �ȵ� ����
			{
				JOptionPane.showMessageDialog(this, "���̸��� �Է��ϼ���");
				mr.tf.requestFocus();
				return;
			}

			// �� �̸� �ߺ� ã��, �ٽø�����ϱ�
			String temp = "";
			for (int i = 0; i < wr.model1.getRowCount(); i++) {
				temp = wr.model1.getValueAt(i, 0).toString();
				if (temp.equals(rname)) {
					JOptionPane.showMessageDialog(this, "�̹� �����ϴ� ���Դϴ�.\n�ٽ� �Է��ϼ���");
					mr.tf.setText("");
					mr.tf.requestFocus();
					return;
				}
			}

			String state = "";
			String pwd = "";
			if (mr.rb1.isSelected()) // ������ư�� ���õƴ�.
			{
				state = "����";
				pwd = "";
			} else {
				state = "�����";
				pwd = new String(mr.pf.getPassword());
				// �Ǵ� pwd=String.valueOf(mr.pf.getPassword());
			}
			int inwon = mr.box.getSelectedIndex();
			try {
				if(state.equals("�����")) {
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

	// ���
	@Override
	public void run() {
		try {
			// 100|id|name �̷������� �޴´�
			while (true) {
				String msg = in.readLine();// Ŭ���̾�Ʈ���� ������ �޼��� ó���� ����� ������
				System.out.println("Client=>��û�� : " + msg);

				// 100|id|name �̷������� �޴´�
				// ��ȣ ==> ���(����)�� ��û��ȣ
				StringTokenizer st = new StringTokenizer(msg, "|");
				int no = Integer.parseInt(st.nextToken());
				switch (no) {
				// ����� case�ȿ���
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
					if(state.equals("�����")) {
						String pwd=st.nextToken();
						String inwon=st.nextToken();
						System.out.println("����� �ο� : "+inwon);
						String[] data = {roomNumber,rname,state,(inwon+"��"),pwd};
						wr.model1.addRow(data);
					}else{
						String inwon=st.nextToken();
						System.out.println("���� �ο� : "+inwon);
						String[] data = {roomNumber,rname,state,(inwon+"��")};
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
