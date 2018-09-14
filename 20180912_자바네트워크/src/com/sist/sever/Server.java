package com.sist.sever;
import java.util.*;

import com.sist.common.Function;

import java.net.*;
import java.io.*;
/*
 *    1. 연결 기계 (핸드폰)  ==>구매
 *       =====
 *        Socket : 다른 컴퓨터와 연결
 *        
 *    2. 셋팅 ==> 개통(전화번호, 전화선)연결
 *              ===  ip, port
 *             유심에 심는다
 *             ========
 *             bind(ip,port)
 *    
 *    3. 대기(전화가 올 때 까지 기다린다.)
 *            listen()
 *    =========================== 개인용(P2P)
 *    멀티
 *    ===== 
 *     1) 교환소켓
 *     2) 통신소켓 ==> 접속자마다 생성 ==> Thread
 *    
 */
public class Server implements Runnable{
	// 서버 소켓 생성
	private ServerSocket ss;
	private final int PORT=7339;
	private String name;
	private String location;
	private int roomnumber=1;
	
	
	//클라이언트의 정보를 저장
	private ArrayList<Client> waitList =new ArrayList<Client>();
	//클라이언트의 IP, id....
	
	public Server() { //프로그램에서 시작과 동시 수행: 생선자, main
		//서버 ==> 구동할때 한개 컴퓨터에서 두번을 실행 할 수 없다.
		try {			
			ss=new ServerSocket(PORT);
			//생성자 메소드 안에   bind():개통, listen(): 대기가 포함돼있다.
			System.out.println("Server Start...");
		} catch (Exception e) {
			System.out.println("Server_Error: "+e.getMessage());
		}
	}
	//접속 했을 때 처리하는 기능
	public void run() {
		try {
			//클라이언트의 발신자 IP확인 ==> Socket안에 IP가 있따~
			while(true) {
				//Socket s == 접속한 클라이언트의 정보(IP, PORT)
				Socket s=ss.accept(); //접속했을 때만 accept가 실행돼서 while을 한바퀴 돈다.(접속했을때만 실행)
				Client client=new Client(s);
				//스레드와 클라이언트의 통신이 시작된다.
				client.start();
				
				/*//클라이언트의 IP를 확인해보자.
				System.out.println("Client IP: "+s.getInetAddress());
				System.out.println("Client Port: "+s.getPort());
				
				//메세지전송
				OutputStream out=s.getOutputStream();
				//서버에서 클라이언트 메모리 연결
				out.write(("Server=>전송 메세지...\n").getBytes());*/
			}			
		} catch (Exception e) {
			System.out.println("Server_run_Error: "+e.getMessage());
		}
		
	}
	public static void main(String[] args) {
		Server server=new Server();
		new Thread(server).start();
	}	
	
	//통신 준비 ==> 내부 클래스
	public class Client extends Thread
	{
		//로그인시 전송하는 데이터 id, name
		String id;
		String name;
		//String str;
		String pos;
		Socket s; //연결
		BufferedReader in;
		OutputStream out;
		public Client(Socket s)
		{
			try {
				this.s=s;
				in=new BufferedReader(new InputStreamReader(s.getInputStream()));
				//쓰레드가 담당하는 클라이언트의 메세지를 받을 변수
				out=s.getOutputStream();
				//쓰레드가 담당하는 클라이언트에 메세지 전송
			} catch (Exception e) {
				System.out.println("Server.Client.run()에러: "+e.getMessage());
			}
		}
		//통신
		/*
		 *  <======서버 ========>
		 *     (2)데이터를 받아서 처리!
		 *     
		 *      
		 *  <======클라이언트 =====>
		 *     (1)요청을 위해 필요한 데이터를 전송
		 *        ex) 로그인  : id, pwd
		 *     (3)결과값을 받아서 화면을 출력!
		 */
		public void run()
		{
			try {
				// 100|id|name 이런식으로 받는다
				while(true) {
					String msg=in.readLine();//클라이언트에서 전송한 메세지  처리후 결과값 보내기
					System.out.println("Client=>요청값 : "+msg);
					
					
					// 100|id|name 이런식으로 받는다
					// 번호 ==> 기능(행위)의 요청번호
					StringTokenizer st=new StringTokenizer(msg, "|");
					int no=Integer.parseInt(st.nextToken());
					switch(no)
					{
						case Function.LOGIN:
						{
							//name = st.nextToken();
							//location = "캐릭터선택";
							id=st.nextToken();
							name=st.nextToken();
							pos="대기실";
							
							// (*1*)제일 먼저 접속한 사람들에게 자신이 접속했다는 것을 알린다.
							messageAll(Function.LOGIN+"|"+id+"|"+name+"|"+pos);//접속한 모든 사람에게 로그인을 알려준다~(테이블에 출력)
							
							//(*2*)이후에 자신을 접속 시킨다.			
							waitList.add(this);
							
							// (*3*) 로그인 ==> 대기실로 화면을 변경시킨다.
							messageAll(Function.MYLOG+"|"+(id+"님이 접속하셨습니다"));
							
							// (*4*) 자신에게만 접속한 사람들의 정보를 뿌린다.
							for(Client client:waitList)
							{
								messageTo(Function.LOGIN+"|"
							              +client.id+"|"
										  +client.name+"|"
							              +client.pos);
							}
							
							//개설된 방 전송!
							/*
							 *  
							 *  로그인
							 *  방개설
							 *  방들어가기
							 *  방나가기
							 *  ==> 위에 4개만 잘하면 게임프로그램은 아무것도 아니다~
							 */
						}
						break;
						//채팅 요청 처리
						case Function.CH:
	    				  {
	    					  String data=st.nextToken();
	    					  messageAll(Function.CH+"|["+name+"]"+data);
	    				  }
	    				  break;
	    				  
						case Function.MAKEROOM:
						{
//(Function.MAKEROOM + "|" + rname + "|" + state + "|" + pwd + "|" + (inwon + 2) 
							String rname=st.nextToken();
							String state=st.nextToken();
							String pwd;
							String inwon;
							
							if(state.equals("비공개")) {
								pwd=st.nextToken();
								inwon=st.nextToken();
								messageAll(Function.MAKEROOM+"|"+(roomnumber++)+"|"+rname+"|"+state+"|"+inwon+"|"+pwd);
							}else {
								inwon=st.nextToken();
								messageAll(Function.MAKEROOM+"|"+(roomnumber++)+"|"+rname+"|"+state+"|"+inwon);
							}
							
						}
					}
					
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		/*
		 *  서버 => 클라이언트 전송 메세지
		 */
		
		// 전체전송 메세지
		public void messageAll(String msg)
		{
			try {
				for(Client client:waitList) {
					client.messageTo(msg);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		// 개인적으로 전송하는 메세지
		public void messageTo(String msg)
		{
			try {
				out.write((msg+"\n").getBytes());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
}
