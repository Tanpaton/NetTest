package com.sist.client;
import javax.swing.*;
import java.awt.*;

public class Login extends JPanel {
	Image back;
	JLabel la1,la2;
	JTextField tf;
	JTextField pf;
	JButton b1,b2;
	

	Login() {
		back=Toolkit.getDefaultToolkit().getImage("C:\\javaDev\\login.png");
		//��ġ ��ġ
		la1=new JLabel("���̵�",JLabel.RIGHT);
		la2=new JLabel("�̸�",JLabel.RIGHT);
		
		tf=new JTextField();
		pf=new JTextField();
		
		b1=new JButton("�α���");
		b2=new JButton("����");
		
		//��ġ
		setLayout(null);//��ġ�� ������� �ʰ� ���� ��ġ�Ѵ�.
		la1.setForeground(Color.black);
		// 1400/2-20
		// 876/2-30
		la1.setBounds(650, 358, 80, 30);
		add(la1); //add(); �߰��ض�
		la2.setForeground(Color.black);
		la2.setBounds(650, 393, 80, 30);
		add(la2);
		
		tf.setBounds(730, 358, 100, 30);
		add(tf);
		pf.setBounds(730, 393, 100, 30);
		add(pf);
		
		JPanel p=new JPanel(); //��ư �����ֱ�
		p.setOpaque(false); //�����ϰ��Ѵ�.
		p.add(b1);
		p.add(b2);
		p.setBounds(650, 430, 185, 30);
		add(p);
		
		
		/*b1.setBounds(10, 100, 100, 30);
		add(b1);
		b2.setBounds(120, 100, 100, 30);
		add(b2);*/
	}
	
	//Ŭ�������� ������ Ŭ�� -> Source->Override����� ->paincomponent
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(back, 0, 0, getWidth(),getHeight(), this);
		              
	}
	
}
