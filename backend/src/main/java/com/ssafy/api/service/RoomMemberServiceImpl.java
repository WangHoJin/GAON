package com.ssafy.api.service;

import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.api.request.RoomMemberRegisterPostReq;
import com.ssafy.api.request.RoomRegisterPostReq;
import com.ssafy.db.entity.Guser;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.RoomMember;
import com.ssafy.db.repository.RoomMemberRepository;
import com.ssafy.db.repository.RoomMemberRepositorySupport;
import com.ssafy.db.repository.RoomRepository;
import com.ssafy.db.repository.RoomRepositorySupport;


/**
 *	방 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("roomMemberService")
public class RoomMemberServiceImpl implements RoomMemberService {

	@Autowired
	RoomMemberRepository roomMemberRepository;
	
	@Autowired
	RoomMemberRepositorySupport roomMemberRepositorySupport;
	
	@Autowired
	GoogleUserService guserService;
	
	@Autowired
	RoomService roomService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public RoomMember createRoomMember(RoomMemberRegisterPostReq roomMemberInfo) {
		RoomMember roomMember = new RoomMember();
		Guser user = guserService.getGuserById(roomMemberInfo.getUser_id());
		Room room = roomService.getRoomById(roomMemberInfo.getRoom_id()); 
		roomMember.setUser(user);
		roomMember.setRoom(room);
		return roomMemberRepository.save(roomMember);
	}
	
	@Override
	public RoomMember getRoomMemberById(Long id) {		
		return roomMemberRepository.findById(id).get();
	}

	@Override
	public RoomMember getRoomMemberByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}	

	@Override
	@Transactional
	public void removeRoomById(Long id) {
		System.out.println("removeRoomById:"+id);
		roomMemberRepository.deleteById(id);
	}

	@Override
	public List<RoomMember> getRoomMembersbyRoomId(long roomId) {
		System.out.println("룸멤버 찾기 서비스 호출");
		List<RoomMember> list = roomMemberRepositorySupport.findRoomMembersbyRoomId(roomId);
		return list;
	}

	@Override
	public List<Room> getRoomsByUid(Long uid) {
		return roomMemberRepositorySupport.findRoomsByUid(uid);
	}
	
}
