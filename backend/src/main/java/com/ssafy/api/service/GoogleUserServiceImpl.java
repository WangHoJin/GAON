package com.ssafy.api.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.GoogleUserRegisterPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.Guser;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.GoogleUserRepository;
import com.ssafy.db.repository.GoogleUserRepositorySupport;
import com.ssafy.db.repository.RoomRepository;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("googleuserService")
public class GoogleUserServiceImpl implements GoogleUserService{

	@Autowired
	GoogleUserRepository googleuserRepository;
	
	@Autowired
	GoogleUserRepositorySupport googleuserRepositorySupport;

	@Override
	public Guser saveGuser(Guser loginInfo) {
		System.out.println("::::	db에 계정 저장 : "+loginInfo+"	:::: ");
		return googleuserRepository.save(loginInfo);
	}

	
	@Override
	public Guser getGuserById(Long id) {
		return googleuserRepository.findById(id).get();
	}
	
	@Override
	public Guser getGuserByEmail(String email) {
		return googleuserRepositorySupport.findGuserByEmail(email).get();
	}
	@Override
	public void updateGuser(Guser guser) {
		System.out.println("updateGuser active");
		googleuserRepositorySupport.updateByUserId(guser);
	}

	@Override
	public void deleteGuser(String email) {
		googleuserRepositorySupport.deleteByEmail(email);
		
	}
	


}
