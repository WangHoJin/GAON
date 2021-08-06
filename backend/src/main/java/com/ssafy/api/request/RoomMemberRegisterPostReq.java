package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 방 등록 API ([POST] /api/v1/room) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("RoomMemberRegisterPostRequest")
public class RoomMemberRegisterPostReq {


	@ApiModelProperty(name="유저의 pk", example="1")
    Long user_id;
	@ApiModelProperty(name="방의 pk", example="1")
    Long room_id;
	
	public RoomMemberRegisterPostReq() {}

	public RoomMemberRegisterPostReq(Long user_id, Long room_id) {
		this.user_id = user_id;
		this.room_id = room_id;
	}
	
	
}
