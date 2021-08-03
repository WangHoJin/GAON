package com.ssafy.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.request.RoomMemberRegisterPostReq;
import com.ssafy.api.request.RoomRegisterPostReq;
import com.ssafy.api.response.RoomMemberRes;
import com.ssafy.api.response.RoomRes;
import com.ssafy.api.service.RoomMemberService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.RoomMember;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 방 멤버 관련 API 컨트롤러
 */
@Api(value = "룸 멤버 API", tags = {"RoomMember"})
@RestController
@RequestMapping("/api/v1/roommem")
public class RoomMemberController {
	
	@Autowired
	RoomMemberService roomMemberService;
	
	@PostMapping()
	@ApiOperation(value = "룸 멤버 추가", notes = "<strong>(유저 id(pk), 방 id(pk))로 룸 멤버를 추가한다.</strong>") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 404, message = "DB 에러"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<RoomMemberRes> create(
			@RequestBody @ApiParam(value="룸 멤버 생성 정보", required = true) RoomMemberRegisterPostReq roomMemberInfo) {
		try {
//			Room room = roomService.createRoom(roomInfo);
			RoomMember roomMember = roomMemberService.createRoomMember(roomMemberInfo);
			System.out.println(roomMember);
			return ResponseEntity.ok(RoomMemberRes.of(200, "Success",roomMember));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(404).body(RoomMemberRes.of(404, "No matching guser for user_id or romm for room_id", null));
		}
	}
	
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "룸 멤버 삭제", notes = "<strong>룸멤버 id로 룸멤버를 삭제한다.</strong>") 
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 404, message = "해당 방 없음"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<BaseResponseBody> delete(
			@PathVariable @ApiParam(value="룸멤버 id", required = true) Long id){
		try {
			System.out.println("룸 멤버 id:"+id+" 삭제요청");
			roomMemberService.removeRoomById(id);
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		} catch (Exception e) {
			return ResponseEntity.status(404).body(BaseResponseBody.of(404, "RoomMember does not exist"));
		}		
	}
	
	

}
