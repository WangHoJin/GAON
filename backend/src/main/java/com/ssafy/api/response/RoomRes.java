package com.ssafy.api.response;

import com.ssafy.db.entity.Room;
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
@ApiModel("RoomResponse")
public class RoomRes {
	
	@ApiModelProperty(name="방 pk", example="1")
	Long id;
	@ApiModelProperty(name="방 Name", example="room_name")
    String name;
	@ApiModelProperty(name="방 Code", example="room_code")
	String code;
	@ApiModelProperty(name="방장의 pk", example="1")
    Long host_id;
	@ApiModelProperty(name="방 Description", example="room_description")
	String description;
	
	public static RoomRes of(Room room) {
		RoomRes res = new RoomRes();
		res.setId(room.getId());		
		res.setName(room.getName());
		res.setDescription(room.getDescription());
		res.setHost_id(room.getHost().getId());
		res.setCode(room.getCode());
		return res;
	}
}
