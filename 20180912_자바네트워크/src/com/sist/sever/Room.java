package com.sist.sever;

import java.util.*;
/*
 *  내부클래스
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
	//방에 들어간 사람 명단 저장!!!!
	String roomName, roomState, roomPwd;
	int current; //현재인원
	int maxcount; //설정된 인원

	public Room(String roomName, String roomState, String roomPwd, int maxcount) {
		current=1;
		this.roomName = roomName;
		this.roomState = roomState;
		this.roomPwd = roomPwd;
		this.maxcount = maxcount;
	}

}
