package com.ssafy.api.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Room;
import com.ssafy.db.repository.RoomRepository;
import com.ssafy.db.repository.RoomRepositorySupport;


/**
 *	방 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("roomService")
public class RoomServiceImpl implements RoomService {

	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	RoomRepositorySupport roomRepositorySupport;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public Room saveRoom(Room roomInfo) {
//		System.out.println("받은 roomInfo");
//		System.out.println(roomInfo.getPassword());
//		roomInfo.setPassword(passwordEncoder.encode(roomInfo.getPassword()));
//		Room room = new Room();
//		room.setName(roomInfo.getName());
//		room.setHost_id(roomInfo.getHost_id());
//		room.setPassword(passwordEncoder.encode(roomInfo.getPassword()));
//		room.setCode(roomInfo.getCode());
//		room.setDescription(roomInfo.getDescription());
		return roomRepository.save(roomInfo);
	}

	@Override
	public Room getRoomById(Long rid) {
		return roomRepository.findById(rid).get();
	}
	@Override
	public Room getRoomByCode(String code) {
		return roomRepositorySupport.findRoomByCode(code).get();
	}

	@Override
	public void removeRoom(Long rid) {
		System.out.println("진입2 "+rid);
		roomRepository.deleteById(rid);
	}
}
