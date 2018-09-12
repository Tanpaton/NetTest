package com.sist.sever;
import java.util.*;

import com.sist.common.Function;

import java.net.*;
import java.io.*;
/*
 *    1. ���� ��� (�ڵ���)  ==>����
 *       =====
 *        Socket : �ٸ� ��ǻ�Ϳ� ����
 *        
 *    2. ���� ==> ����(��ȭ��ȣ, ��ȭ��)����
 *              ===  ip, port
 *             ���ɿ� �ɴ´�
 *             ========
 *             bind(ip,port)
 *    
 *    3. ���(��ȭ�� �� �� ���� ��ٸ���.)
 *            listen()
 *    =========================== ���ο�(P2P)
 *    ��Ƽ
 *    ===== 
 *     1) ��ȯ����
 *     2) ��ż��� ==> �����ڸ��� ���� ==> Thread
 *    
 */
public class Server implements Runnable{
	// ���� ���� ����
	private ServerSocket ss;
	private final int PORT=7339;
	private String name;
	private String location;
	
	
	//Ŭ���̾�Ʈ�� ������ ����
	private ArrayList<Client> waitList =new ArrayList<Client>();
	//Ŭ���̾�Ʈ�� IP, id....
	
	public Server() { //���α׷����� ���۰� ���� ����: ������, main
		//���� ==> �����Ҷ� �Ѱ� ��ǻ�Ϳ��� �ι��� ���� �� �� ����.
		try {			
			ss=new ServerSocket(PORT);
			//������ �޼ҵ� �ȿ�   bind():����, listen(): ��Ⱑ ���Ե��ִ�.
			System.out.println("Server Start...");
		} catch (Exception e) {
			System.out.println("Server_Error: "+e.getMessage());
		}
	}
	//���� ���� �� ó���ϴ� ���
	public void run() {
		try {
			//Ŭ���̾�Ʈ�� �߽��� IPȮ�� ==> Socket�ȿ� IP�� �ֵ�~
			while(true) {
				//Socket s == ������ Ŭ���̾�Ʈ�� ����(IP, PORT)
				Socket s=ss.accept(); //�������� ���� accept�� ����ż� while�� �ѹ��� ����.(������������ ����)
				Client client=new Client(s);
				//������� Ŭ���̾�Ʈ�� ����� ���۵ȴ�.
				client.start();
				
				/*//Ŭ���̾�Ʈ�� IP�� Ȯ���غ���.
				System.out.println("Client IP: "+s.getInetAddress());
				System.out.println("Client Port: "+s.getPort());
				
				//�޼�������
				OutputStream out=s.getOutputStream();
				//�������� Ŭ���̾�Ʈ �޸� ����
				out.write(("Server=>���� �޼���...\n").getBytes());*/
			}			
		} catch (Exception e) {
			System.out.println("Server_run_Error: "+e.getMessage());
		}
		
	}
	public static void main(String[] args) {
		Server server=new Server();
		new Thread(server).start();
	}	
	
	//��� �غ� ==> ���� Ŭ����
	class Client extends Thread
	{
		//�α��ν� �����ϴ� ������ id, name
		String id;
		String name;
		Socket s; //����
		BufferedReader in;
		OutputStream out;
		public Client(Socket s)
		{
			try {
				this.s=s;
				in=new BufferedReader(new InputStreamReader(s.getInputStream()));
				//�����尡 ����ϴ� Ŭ���̾�Ʈ�� �޼����� ���� ����
				out=s.getOutputStream();
				//�����尡 ����ϴ� Ŭ���̾�Ʈ�� �޼��� ����
			} catch (Exception e) {
				System.out.println("Server.Client.run()����: "+e.getMessage());
			}
		}
		//���
		public void run()
		{
			try {
				// 100|id|name �̷������� ������.
				while(true) {
					String msg=in.readLine();//Ŭ���̾�Ʈ���� ������ �޼���  ó���� ����� ������
					System.out.println("Client=>��û�� : "+msg);
					StringTokenizer st=new StringTokenizer(msg, "|");
					int no=Integer.parseInt(st.nextToken());
					switch(no)
					{
						case Function.LOGIN:
						{
							name = st.nextToken();
							location = "ĳ���ͼ���";
							
							messageAll((st+"|"+location));
						}
						case Function.MYLOG:
						{
							location ="����";
						}
						case Function.CH:
						{
							location ="����";
						}
					}
					
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		/*
		 *  ���� => Ŭ���̾�Ʈ ���� �޼���
		 */
		
		// ��ü���� �޼���
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
		// ���������� �����ϴ� �޼���
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
