package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 구글유저 로그인 API ([POST] /api/v1/auth/glogin) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("GuserLoginPostRequest")
public class GuserLoginPostReq {
	@ApiModelProperty(name="유저 이메일", example="ssafy@google.com")
	String email;
	@ApiModelProperty(name="유저 이름", example="ssafys")
	String nickname;
}
