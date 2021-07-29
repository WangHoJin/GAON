package com.ssafy.api.service;

import java.util.Map;

import com.ssafy.api.request.GoogleUserRegisterPostReq;
import com.ssafy.api.request.GuserLoginPostReq;
import com.ssafy.db.entity.Guser;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface GoogleUserService {
	Guser saveGuser(Guser loginInfo);
	Guser getGuserByEmail(String email);
	void updateGuser(Guser user);
	void deleteGuser(String email);

}
