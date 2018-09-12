package com.sist.client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.table.*;

public class WaitRoom extends JPanel {
	Image back; //배경화면
	JLabel la1, la2;
	JTable table1, table2;
	DefaultTableModel model1, model2;
	JTextArea ta;
	JTextField tf;
	JButton b1;
	ImageIcon mb; //버튼
	
	WaitRoom() {
		setLayout(null);
		back = Toolkit.getDefaultToolkit().getImage("C:\\javaDev\\waitingroom.png");

		la1 = new JLabel("방개설 정보");
		la2 = new JLabel("접속자 정보");
		ta = new JTextArea();
		JScrollPane js3 = new JScrollPane(ta);
		tf = new JTextField();
		
		//방만들기 버튼
		mb=new ImageIcon("C:\\javaDev\\newroombtn.png");
		b1 = new JButton("",mb);		

		// table
		String[] col1 = { "방이름", "공개/비공개", "방인원" }; //비밀번호
		String[][] row1 = new String[0][4];
		model1 = new DefaultTableModel(row1, col1);
		table1 = new JTable(model1);
		JScrollPane js1 = new JScrollPane(table1);

		String[] col2 = { "아이디", "방위치" };
		String[][] row2 = new String[0][2];
		model2 = new DefaultTableModel(row2, col2);
		table2 = new JTable(model2);
		JScrollPane js2 = new JScrollPane(table2);

		// 배치
		la1.setBounds(30, 70, 100, 30);
		js1.setBounds(30, 95, 700, 350);
		js1.setOpaque(false);
		js1.getViewport().setOpaque(false);
		add(js1);

		la2.setBounds(820, 70, 130, 30);
		js2.setBounds(820, 95, 340, 350);
		js2.setOpaque(false);
		js2.getViewport().setOpaque(false);
		//add(la2);
		add(js2);
		
		
		ta.setBounds(30, 470, 700, 205); // 채팅창
		ta.setOpaque(true);
		add(ta);
		tf.setBounds(30, 685, 700, 20); // 채팅치는곳
		tf.setOpaque(true);
		add(tf);
		
		b1.setBounds(930, 645, 120, 50); //방만들기 버튼
		b1.setOpaque(false);
		//이미지 뒤에 버튼 라인 안보이게 하는것
		b1.setBorderPainted(false); 
		b1.setFocusPainted(false); 
		b1.setContentAreaFilled(false);
		
		add(b1);

		setSize(1251, 750);
		setVisible(true);

		//tf.addActionListener(this);
	}
	/*@Override
	public void actionPerformed(ActionEvent e) { // 채팅을치면 채팅창에 입력된게 올라가는고
		if (e.getSource() == tf) {
			String s = tf.getText();
			ta.append(s + "\n");
			tf.setText("");
		}
	}*/

	//백그라운드 배경화면 : 클래스파일 오른쪽 클릭 -> Source->Override어찌고 ->paincomponent
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(back, 0, 0, getWidth(), getHeight(), this);
	}
}
