package com.ssafy.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.service.RoomService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Room;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 방 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "방 API", tags = {"Room"})
@RestController
@RequestMapping("/api/v1/room")
public class RoomController {
	@Autowired
	RoomService roomService;
	
	@PostMapping()
	@ApiOperation(value = "방 생성", notes = "<strong>방 이름과 비밀번호로 방을 생성한다.</strong>") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public Room create(
			@RequestBody @ApiParam(value="방 생성 정보", required = true) Room roomInfo) {
		
		//임의로 리턴된 Room 인스턴스. 현재 코드는 룸 생성 성공 여부만 판단하기 때문에 굳이 Insert 된 방 정보를 응답하지 않음.
		Room room = roomService.createRoom(roomInfo);
		System.out.println("request");
		System.out.println(roomInfo);
		
//		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		return room;
	}
	
	
	@GetMapping("/{code}")
	@ApiOperation(value = "방 정보 코드로 찾기", notes = "<strong>방 코드로 방을 찾는다</strong>") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public Room findByCode(
			@PathVariable @ApiParam(value="방 코드", required = true) String code) {
		try {
			Room room = roomService.getRoomByCode(code);
			System.out.println(room);
			return room;
		} catch (Exception e) {
			System.out.println("catch _ null");
			System.out.println(e);
			return null;
		}
	}
}
