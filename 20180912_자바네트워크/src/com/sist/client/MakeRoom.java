package com.sist.client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MakeRoom extends JFrame implements ActionListener {
	JLabel la1, la2, la3, la4/*la4 = 인원수제한*/;
	JTextField tf;
	JRadioButton rb1, rb2;
	JPasswordField pf;
	JButton b1, b2;
	JComboBox box=new JComboBox();
	public MakeRoom() {
		la1 = new JLabel("방이름");// 중복이 안되게 해야됨
		la2 = new JLabel("공개/비공개");
		la3 = new JLabel("비밀번호");

		tf = new JTextField();
		pf = new JPasswordField();

		b1 = new JButton("방만들기");
		b2 = new JButton("취소");

		// 라디오버튼
		rb1 = new JRadioButton("공개");
		rb1.setSelected(true);
		rb2 = new JRadioButton("비공개");
		// 그룹으로 묶어야 하나만 선택된다.
		ButtonGroup bg = new ButtonGroup();
		bg.add(rb1);
		bg.add(rb2);
		
		la4=new JLabel("인원");
		for(int i=2; i<=4; i++ )
		{
			box.addItem(i+"명");
		}
		// 배치
		setLayout(null);
		la1.setBounds(10, 15, 80, 30);
		tf.setBounds(95, 15, 150, 30);

		la2.setBounds(10, 50, 80, 30);
		rb1.setBounds(95, 50, 70, 30);
		rb2.setBounds(170, 50, 70, 30);

		la3.setVisible(false);
		pf.setVisible(false);
		la3.setBounds(10, 85, 80, 30);
		pf.setBounds(95, 85, 150, 30);

		la4.setBounds(10, 120, 80, 30);
		box.setBounds(95, 120, 150, 30);
		
		add(la4);add(box);
		
		JPanel p=new JPanel();
		p.add(b1);
		p.add(b2);
		p.setBounds(10, 155, 235, 35);
		
		add(la1);
		add(tf);
		add(la2);
		add(rb1);
		add(rb2);
		add(la3);
		add(pf);
		add(p);

		setSize(300, 250);
		rb1.addActionListener(this);
		rb2.addActionListener(this);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MakeRoom();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == rb1) {
			la3.setVisible(false);
			pf.setVisible(false);
			pf.setText("");
		}
		if (e.getSource() == rb2) {
			la3.setVisible(true);
			pf.setVisible(true);
			pf.setText("");
			pf.requestFocus();
		}

	}

}
