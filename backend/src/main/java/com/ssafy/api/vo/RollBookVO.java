package com.ssafy.api.vo;

import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("RollBookVO")
public class RollBookVO{
	@ApiModelProperty(name="Room id", example="1")
	Long rid;
	@ApiModelProperty(name="Date", example="2020-10-10")
	LocalDate date;	
	@ApiModelProperty(name="User id", example="1")
	Long uid;
	@ApiModelProperty(name="유저 이메일", example="s@s.s")
	String email;
	@ApiModelProperty(name="유저 닉네임", example="ssafy")
	String nickname;
	@ApiModelProperty(name="출결상태", example="출석")
	String state;	
	
}
