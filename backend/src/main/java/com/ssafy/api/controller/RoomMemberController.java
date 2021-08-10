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
import com.ssafy.api.service.RoomService;
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
@RequestMapping("/api/v1/room-member")
public class RoomMemberController {
	
	@Autowired
	RoomMemberService roomMemberService;
	
	@Autowired
	RoomService roomService;
	
	@PostMapping()
	@ApiOperation(value = "방 멤버를 추가한다", notes = "<strong>(유저 id(pk), 방 id(pk))로 룸 멤버를 추가한다.</strong>") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 409, message = "이미 방에 참여 중인 유저"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<RoomMemberRes> create(
			@RequestBody @ApiParam(value="유저 id, 방 id", required = true) RoomMemberRegisterPostReq roomMemberInfo) {
		try {
			RoomMember roomMember = roomMemberService.createRoomMember(roomMemberInfo);
			System.out.println(roomMember);
			return ResponseEntity.ok(RoomMemberRes.of(200, "Success",roomMember));
		} catch (Exception e) {
			System.out.println("error");
			e.printStackTrace();
			return ResponseEntity.status(409).body(RoomMemberRes.of(409, "이미 방에 참여중인 유저거나, rid, uid가 잘못되었습니다.", null));
		}
	}
	
	
	@DeleteMapping()
	@ApiOperation(value = "방 멤버를 삭제한다 ", notes = "<strong>방id, 유저id로 룸멤버를 삭제한다.</strong> </br>방장이 방을 나갈 경우엔 방도 삭제된다") 
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 404, message = "해당 방 없음"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<BaseResponseBody> delete(
			@RequestBody @ApiParam(value="유저 id, 방 id", required = true) RoomMemberRegisterPostReq roomMemberInfo){
		try {
			RoomMember rm = roomMemberService.getRoomMemberByUidAndRid(roomMemberInfo.getUser_id(),roomMemberInfo.getRoom_id());
			//방장일 경우 방을 통으로 삭제한다
			if(rm.getRoom().getHost().getId() == rm.getUser().getId()) {
				System.out.println("방장이 방을 나갔습니다. 방을 통째로 삭제합니다");
				roomService.removeRoom(rm.getRoom().getId());
				return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
			}
			//방장이 아닐 경우 룸맴버에서 삭제한다
			else if(roomMemberService.removeById(rm.getId())) {
				return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
			} else {
				return ResponseEntity.status(404).body(BaseResponseBody.of(404, "RoomMember does not exist"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(404).body(BaseResponseBody.of(500, "Server Error"));
		}		
	}
	
	

}
