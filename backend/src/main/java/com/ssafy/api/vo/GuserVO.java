package com.ssafy.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("GuserVO")
public class GuserVO{
	@ApiModelProperty(name="유저id", example="1")
	Long user_id;
	@ApiModelProperty(name="유저 이메일", example="ss@s.s")
	String email;
	@ApiModelProperty(name="유저 닉네임", example="누구니")
	String nickname;
	
	
}
