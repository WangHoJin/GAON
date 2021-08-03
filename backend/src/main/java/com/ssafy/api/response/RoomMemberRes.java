package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
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
public class RoomMemberRes extends BaseResponseBody {
	
	@ApiModelProperty(name="방멤버목록 pk", example="1")
	Long id;
	@ApiModelProperty(name="방 PK", example="1")
	Long room_id;
	@ApiModelProperty(name="유저 pk", example="1")
    Long user_id;	


	public static RoomMemberRes of(Integer statusCode , String message, RoomMember roomMember) {
		RoomMemberRes res = new RoomMemberRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		if(roomMember!=null) {
			res.setId(roomMember.getId());	
			res.setRoom_id(roomMember.getRoom().getId());
			res.setUser_id(roomMember.getUser().getId());
		}
		return res;
	}
	
	
}
