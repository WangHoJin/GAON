package com.ssafy.api.service;

import java.util.Map;

import com.ssafy.api.request.RoomRegisterPostReq;
import com.ssafy.db.entity.Room;

/**
 *	방 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface RoomService {
	Room createRoom(RoomRegisterPostReq roomInfo);
	Room getRoomByCode(String code);
	Room getRoomById(Long id);
	void removeRoom(Long id);
	Room modifyRoom(Long id, RoomRegisterPostReq roomInfo);
	boolean joinRoom(Map<String, String> roomInfo);
	String makeCode(Room room);
}
