package com.ssafy.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("RoomMemberVO")
public class RoomMemberVO{
	@ApiModelProperty(name="유저id", example="1")
	Long user_id;
	@ApiModelProperty(name="방id", example="1")
	Long room_id;
	@ApiModelProperty(name="룸멤버의 이메일", example="ss@s.s")
	String email;
	@ApiModelProperty(name="룸멤버의 이름", example="누구니")
	String nickname;
	
	
}
