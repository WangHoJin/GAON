package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("GoogleUserRegisterPostRequest")
public class GoogleUserRegisterPostReq {
	@ApiModelProperty(name="유저 이메일", example="ssafy@google.com")
	String email;
	@ApiModelProperty(name="유저 이름", example="ssafys")
	String nickname;
	
}
