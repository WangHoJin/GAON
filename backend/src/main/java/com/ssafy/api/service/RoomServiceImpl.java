package com.ssafy.api.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.RoomRegisterPostReq;
import com.ssafy.db.entity.Guser;
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
	GoogleUserService guserService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public Room createRoom(RoomRegisterPostReq roomInfo) {
		Room room = new Room();
		Guser host = guserService.getGuserById(roomInfo.getHost_id());
		room.setName(roomInfo.getName());
		room.setHost(host);
		room.setDescription(roomInfo.getDescription());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		room.setPassword(passwordEncoder.encode(roomInfo.getPassword()));		
		return roomRepository.save(room);
	}

	@Override
	public Room modifyRoom(Long id, RoomRegisterPostReq roomInfo) {
		System.out.println("id/"+id);
		Room room = roomRepository.findById(id).get();
		System.out.println(room);
		if(roomInfo.getHost_id() != null) {
			Guser host = guserService.getGuserById(roomInfo.getHost_id());
			room.setHost(host);
		}
		if(roomInfo.getName()!=null) {
			room.setName(roomInfo.getName());
		}
		if(roomInfo.getPassword()!=null) {
			// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
			room.setPassword(passwordEncoder.encode(roomInfo.getPassword()));		
		}
		if(roomInfo.getDescription()!=null) {
			room.setDescription(roomInfo.getDescription());
		}
		return roomRepository.save(room);
	}
	
	@Override
	public Room getRoomById(Long id) {
		return roomRepository.findById(id).get();
	}
	@Override
	public Room getRoomByCode(String code) {
		return roomRepositorySupport.findRoomByCode(code).get();
	}

	@Override
	public void removeRoom(Long id) {
		roomRepository.deleteById(id);
	}

	@Override
	public boolean joinRoom(Map<String, String> roomInfo) {
		String code = roomInfo.get("code");
		String raw_password = roomInfo.get("password");
		String encode_password = roomRepository.findByCode(code).get().getPassword();
		try {
			return passwordEncoder.matches(raw_password, encode_password);
		}catch(Exception e){
			return false;
		}
	}
}
