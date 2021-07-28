package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Guser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 구글유저 로그인 API ([POST] /api/v1/auth) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("GoogleUserLoginPostResponse")
public class GoogleUserLoginPostRes extends BaseResponseBody{
	@ApiModelProperty(name="유저 정보", example="유저 정보 객체")
	Guser guserinfo;
	
	public static GoogleUserLoginPostRes of(Integer statusCode, String message, Guser userinfo) {
		GoogleUserLoginPostRes res = new GoogleUserLoginPostRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setGuserinfo(userinfo);
		return res;
	}
}
