package com.ssafy.api.response;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.api.vo.BoardVO;
import com.ssafy.api.vo.RoomMemberVO;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.Guser;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.RoomMember;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 방 등록 API ([POST] /api/v1/member) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("RoomMemberResponse")
public class RoomMemberListRes extends BaseResponseBody {	
	

	List<RoomMemberVO> members = new ArrayList<>();
	public static RoomMemberListRes of(Integer statusCode , String message, List<RoomMember> roomMember) {
		RoomMemberListRes res = new RoomMemberListRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		if(roomMember!=null) {
			for(RoomMember r : roomMember) {
				RoomMemberVO item = new RoomMemberVO();
				item.setRoom_id(r.getRoom().getId());
				item.setUser_id(r.getUser().getId());
				item.setEmail(r.getUser().getEmail());	
				item.setNickname(r.getUser().getNickname());
				
				res.members.add(item);
			}
		}
		return res;
	}
	
	
}
