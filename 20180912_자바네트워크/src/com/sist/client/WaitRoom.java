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
	   JLabel la1, la2, mc, nickName, rank; 
	   //la1 = 방개설 테이블을 붙이는 라벨,  la2 = 접속자 테이블을 붙이는라벨,  mc=내 캐릭터이미지,  nickName =닉네임,    rank=등급
	   JTable table1, table2; //table1 = 방개설 테이블 뷰,  table2 = 접속자 테이블 뷰
	   DefaultTableModel model1, model2; //테이블들의 row col설정 
	   JTextArea ta; //채팅창 뷰
	   JTextField tf; //채팅치는 곳
	   JButton b1,b2; //b1 = 방만들기 버튼,   b2= 다음페이지버튼(게임룸이동) ※ 잠시 쓰는것.
	   ImageIcon mb, ci, rankI; //방만들기버튼 이미지, 대기실 캐릭터 이미지. , 등급이미지
	   JScrollBar bar;

   WaitRoom() {
      setLayout(null); //사용자 지정 레이아웃으로 
      back = Toolkit.getDefaultToolkit().getImage("image\\waitingroom.png");//뒷배경화면

      la1 = new JLabel(""); 
      la2 = new JLabel(""); 
      ta=new JTextArea();
  	  ta.setEditable(false);
  	  JScrollPane js3=new JScrollPane(ta);
      /*ta = new JTextArea();  
      ta.setEditable(false); //편집 못하게 하기!!! 왜안돼   
      JScrollPane js3 = new JScrollPane(ta); //채팅뷰에 스크롤팬 넣어주기.  */    
  	  bar=js3.getVerticalScrollBar();
      tf = new JTextField();      
      b2 = new JButton("다음페이지"); 
      mc = new JLabel("");
      nickName = new JLabel("닉네임");
      rank = new JLabel("");
      
      
      mb=new ImageIcon("image\\newroombtn.png");//방만들기 버튼 이미지
      b1 = new JButton("",mb); //방만들기 버튼에 이미지 입히면서 초기화.
      ci=new ImageIcon("image\\jam.png");
      rankI=new ImageIcon("image\\RANK\\FF.png");
      Image  img = getImageSizeChange(ci, 90, 90);
      mc.setIcon(new ImageIcon(img));
      img = getImageSizeChange(rankI, 20, 20);
      rank.setIcon(new ImageIcon(img));

      // 방개설 테이블 
      String[] col1 = {"No", "방이름", "공개/비공개", "방인원" };
      String[][] row1 = new String[0][4];
      model1 = new DefaultTableModel(row1, col1) {
    	  @Override
          public boolean isCellEditable(int row, int column)      // 테이블의 편집 가능 여부를 알려주는 메소드
          {
              return false;       // 편집이 안되도록 한다.
          }
      };
      table1 = new JTable(model1);
      table1.getColumn("No").setPreferredWidth(60);
      table1.getColumn("방이름").setPreferredWidth(440); 
      table1.getColumn("공개/비공개").setPreferredWidth(120);
      table1.getColumn("방인원").setPreferredWidth(80);
      table1.getTableHeader().setReorderingAllowed(false); // 이동 불가 
      table1.getTableHeader().setResizingAllowed(false); //크기변경불가
      JScrollPane js1 = new JScrollPane(table1);
      
      // 접속자 테이블 
      String[] col2 = {"아이디", "닉네임", "방위치" };
      String[][] row2 = new String[0][3];
      model2 = new DefaultTableModel(row2, col2) {
    	  @Override
          public boolean isCellEditable(int row, int column)      // 테이블의 편집 가능 여부를 알려주는 메소드
          {
              return false;       // 편집이 안되도록 한다.
          }
      };

      table2 = new JTable(model2);
      table2.getTableHeader().setReorderingAllowed(false); // 이동 불가 
      table2.getTableHeader().setResizingAllowed(false); //크기변경불가
      JScrollPane js2 = new JScrollPane(table2);

      // 방개설 테이블 배치
      la1.setBounds(30, 70, 100, 30);
      js1.setBounds(30, 95, 700, 307);
      js1.setOpaque(false);
      js1.getViewport().setOpaque(false);
      add(js1);

      // 접속자 테이블 배치
      la2.setBounds(820, 70, 130, 30);
      js2.setBounds(820, 95, 340, 350);
      js2.setOpaque(false);
      js2.getViewport().setOpaque(false);
      add(js2);
      
      // 채팅창 뷰 배치
      js3.setBounds(30, 440, 700, 237); 
      js3.setOpaque(true);
      add(js3);
      // 채팅치는곳 배치
      tf.setBounds(30, 685, 700, 20); 
      tf.setOpaque(true);
      add(tf);
      
      
      b1.setBounds(930, 645, 120, 50); //방만들기 버튼 위치
      b2.setBounds(930, 600, 120, 30); //다음페이지 버튼 위치 //곧 사라질애
      
      b1.setOpaque(false);
      //이미지 뒤에 버튼 라인 안보이게 하는것
      b1.setBorderPainted(false); 
      b1.setFocusPainted(false); 
      b1.setContentAreaFilled(false);
      
      //버튼배치
      add(b1);
      add(b2);
      
    //My Info~
      mc.setBounds(876, 510, 90, 90);
      mc.setOpaque(false);
      nickName.setBounds(1025, 540, 40, 14);
      nickName.setOpaque(false);
      rank.setBounds(1035, 570, 20, 20);
      rank.setOpaque(false);
      add(mc);
      add(nickName);
      add(rank);

      setSize(1251, 750);
      setVisible(true);
     
   }

   public static void main(String[] args) {
      WaitRoom wr = new WaitRoom();
   }
   
   public Image getImageSizeChange(ImageIcon icon,int width,int height)
   {
         Image img=icon.getImage();
         Image change=img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
         return change;
   }

   //백그라운드 배경화면 : 클래스파일 오른쪽 클릭 -> Source->Override어찌고 ->paintComponent
   @Override
   protected void paintComponent(Graphics g) {
      g.drawImage(back, 0, 0, getWidth(), getHeight(), this);

   }
}