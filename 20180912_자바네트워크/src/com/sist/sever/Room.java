package com.sist.sever;

import java.util.*;
/*
 *  ����Ŭ����
 *  class A
 *  {
 *     class B
 *     {
 *        
 *     }
 *  }
 */
public class Room {
	ArrayList<Server.Client> userList = new ArrayList<Server.Client>();
	//�濡 �� ��� ��� ����!!!!
	String roomName, roomState, roomPwd;
	int current; //�����ο�
	int maxcount; //������ �ο�

	public Room(String roomName, String roomState, String roomPwd, int maxcount) {
		current=1;
		this.roomName = roomName;
		this.roomState = roomState;
		this.roomPwd = roomPwd;
		this.maxcount = maxcount;
	}

}
