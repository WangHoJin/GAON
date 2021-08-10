package com.ssafy.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.request.GuserLoginPostReq;
import com.ssafy.api.response.GoogleUserLoginPostRes;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.service.GoogleUserService;
//import com.ssafy.api.service.UserService;
//import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Guser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
//	@Autowired
//	UserService userService;
//	
	@Autowired
	GoogleUserService googleuserService;
	
	
	
	@PostMapping("/glogin")
	@ApiOperation(value = "구글로그인", notes = "구글로그인") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
        @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
        @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
        @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
	public ResponseEntity<GoogleUserLoginPostRes> glogin(@RequestBody @ApiParam(value="구글로그인정보", required = true) Guser loginInfo) {
		String email = loginInfo.getEmail();
		String nickname = loginInfo.getNickname();
		Guser guser = new Guser();
		System.out.println(":::: 로그인시도 : "+email+" | "+nickname+"   ::::");
		try {
			System.out.println(":::::  접속한 계정의 정보가 DB에 있는지 확인합니다.  ::::");
			guser = googleuserService.getGuserByEmail(email);
		}catch (Exception e) {
			System.out.println("::::  저장된 계정의 정보가 없습니다. 회원 정보를  DB에 저장합니다.	::::");
			guser = googleuserService.saveGuser(loginInfo);
			System.out.println("::::	신규 유저추가 완료: "+guser.getEmail()+" | "+guser.getNickname()+" 	::::");
			return ResponseEntity.ok(GoogleUserLoginPostRes.of(200, "Success", guser));			
		}		
		
		System.out.println("DB에 등록된 유저입니다. 등록하지 않고 로그인을 진행합니다.");	
		System.out.println(email+" 의 "+" google닉네임 : "+nickname+" / DB에 저장된 닉네임 : "+guser.getNickname());		
		return ResponseEntity.ok(GoogleUserLoginPostRes.of(200, "Success", guser));
	}
	
	@PutMapping()
	@ApiOperation(value = "구글 회원 닉네임 변경", notes = "구글 로그인 중인 회원의 닉네임을 변경한다. {email:data, nickname:data} 형식의 데이터를 필요로함") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
        @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
        @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
        @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
	public ResponseEntity<GoogleUserLoginPostRes> update(@RequestBody @ApiParam(value="수정될 회원정보", required = true) GuserLoginPostReq loginInfo) {
		
		String email = loginInfo.getEmail();
		String nickname = loginInfo.getNickname();
		System.out.println("::::::::   "+email+"의 닉네임을 "+nickname+"로 변경 요청     ::::::::");
		
		Guser user = new Guser();
		user.setEmail(email);
		user.setNickname(nickname);
		googleuserService.updateGuser(user);
		return ResponseEntity.ok(GoogleUserLoginPostRes.of(200, "Success", user));
	}
	
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "구글로그인 유저 데이터 삭제", notes = "구글로그인 유저의 데이터를 삭제한다. 유저 id값을 파라미터로함") 
    @ApiResponses({
    	@ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 409, message = "삭제완료"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseBody> deleteGoogleUser(
			@PathVariable @ApiParam(value="user id", required = true) Long id) {
		try {
			System.out.println("::::::::    "+id+" 계정 삭제요청    :::::::");			
			googleuserService.deleteGuser(id);
			
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		} catch (Exception e) {
			return ResponseEntity.status(200).body(BaseResponseBody.of(404, "User does not exist"));
		}
	}
	
	
	/////////////////////////
	
//	
//	@PostMapping()
//	@ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.") 
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "성공"),
//        @ApiResponse(code = 401, message = "인증 실패"),
//        @ApiResponse(code = 404, message = "사용자 없음"),
//        @ApiResponse(code = 500, message = "서버 오류")
//    })
//	public ResponseEntity<? extends BaseResponseBody> register(
//			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) {
//		
//		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
//		User user = userService.createUser(registerInfo);
//		System.out.println(user);
//		System.out.println(registerInfo);
//		
//		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
//	}
//	
//	@GetMapping("/me")
//	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.") 
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "성공"),
//        @ApiResponse(code = 401, message = "인증 실패"),
//        @ApiResponse(code = 404, message = "사용자 없음"),
//        @ApiResponse(code = 500, message = "서버 오류")
//    })
//	public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication) {
//		/**
//		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
//		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
//		 */
//		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
//		String userId = userDetails.getUsername();
//		User user = userService.getUserByUserId(userId);
//		
//		return ResponseEntity.status(200).body(UserRes.of(user));
//	}
//	
//	
//	@GetMapping("/{userId}")
//	@ApiOperation(value = "유저 정보", notes = "<strong>존재하는 회원 확인용</strong></br>로그인 한 사용자가 아닌 경우에만 응답한다.") 
//    @ApiResponses({
//        @ApiResponse(code = 409, message = "이미 존재하는 사용자 ID 입니다.")
//    })
//	public ResponseEntity<? extends BaseResponseBody> isExistUser(
//			@PathVariable @ApiParam(value="userId", required = true) String userId) {
//		try {
//			User user = userService.getUserByUserId(userId);
//			System.out.println(user);
//			return ResponseEntity.status(200).body(BaseResponseBody.of(409, "이미 존재하는 사용자 ID 입니다."));
//		} catch (Exception e) {
//			return null;
//		}
//	}
}
