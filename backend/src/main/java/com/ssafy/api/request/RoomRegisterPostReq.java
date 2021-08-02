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
@ApiModel("RoomRegisterPostRequest")
public class RoomRegisterPostReq {
	
	@ApiModelProperty(name="방 Name", example="room_name")
    String name;
	@ApiModelProperty(name="방장의 pk", example="1")
    Long host_id;
	@ApiModelProperty(name="방 Password", example="room_password")
	String password;
	@ApiModelProperty(name="방 Description", example="room_description")
	String description;
	
}
