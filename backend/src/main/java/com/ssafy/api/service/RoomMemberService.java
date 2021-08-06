package com.ssafy.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.ssafy.api.request.RoomMemberRegisterPostReq;
import com.ssafy.api.request.RoomRegisterPostReq;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.RoomMember;

/**
 *	룸 멤버 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface RoomMemberService {
	RoomMember createRoomMember(RoomMemberRegisterPostReq roomMemberInfo);
	RoomMember getRoomMemberByUserId(Long userId);
//	RoomMember getRoomMemberByRoomId(Long roomId);
	RoomMember getRoomMemberById(Long id);
	List<RoomMember> getRoomMembersbyRoomId(long roomId);
	void removeRoomById(Long id);
}
