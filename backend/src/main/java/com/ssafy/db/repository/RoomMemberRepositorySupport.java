package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QRoom;
import com.ssafy.db.entity.QRoomMember;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.RoomMember;
import com.ssafy.db.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 방 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Transactional
@Repository
public class RoomMemberRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
//    QRoom qRoom = QRoom.room;
    QRoomMember qRoomMember = QRoomMember.roomMember;
//    public Optional<Room> findRoomByCode(String code){
//    	Room room = jpaQueryFactory.select(qRoom).from(qRoom).where(qRoom.code.eq(code)).fetchOne();
//    	if(room == null) return Optional.empty();
//    	return Optional.ofNullable(room);
//    }

	public List<RoomMember> findRoomMembersbyRoomId(Long roomId) {
		List<RoomMember> list = jpaQueryFactory.select(qRoomMember).from(qRoomMember).where(qRoomMember.room.id.eq(roomId)).fetch();
		System.out.println(list);
		return list;
	}
	
	public List<Room> findRoomsByUid(Long uid) {
		List<Room> rooms = new ArrayList<>();
		List<RoomMember> roomMember = jpaQueryFactory.select(qRoomMember).from(qRoomMember).where(qRoomMember.user.id.eq(uid)).fetch();
		for(RoomMember rm : roomMember) {
			rooms.add(rm.getRoom());
		}
		return rooms;
	}
	public Long deleteById(Long id) {
		Long res = jpaQueryFactory.delete(qRoomMember).where(qRoomMember.id.eq(id)).execute();
		return res;
	}
}
