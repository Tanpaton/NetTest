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
		//배치 위치
		la1=new JLabel("아이디",JLabel.RIGHT);
		la2=new JLabel("이름",JLabel.RIGHT);
		
		tf=new JTextField();
		pf=new JTextField();
		
		b1=new JButton("로그인");
		b2=new JButton("종료");
		
		//배치
		setLayout(null);//배치를 사용하지 않고 직접 배치한다.
		la1.setForeground(Color.black);
		// 1400/2-20
		// 876/2-30
		la1.setBounds(650, 358, 80, 30);
		add(la1); //add(); 추가해라
		la2.setForeground(Color.black);
		la2.setBounds(650, 393, 80, 30);
		add(la2);
		
		tf.setBounds(730, 358, 100, 30);
		add(tf);
		pf.setBounds(730, 393, 100, 30);
		add(pf);
		
		JPanel p=new JPanel(); //버튼 묶어주기
		p.setOpaque(false); //투명하게한다.
		p.add(b1);
		p.add(b2);
		p.setBounds(650, 430, 185, 30);
		add(p);
		
		
		/*b1.setBounds(10, 100, 100, 30);
		add(b1);
		b2.setBounds(120, 100, 100, 30);
		add(b2);*/
	}
	
	//클래스파일 오른쪽 클릭 -> Source->Override어찌고 ->paincomponent
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(back, 0, 0, getWidth(),getHeight(), this);
		              
	}
	
}
