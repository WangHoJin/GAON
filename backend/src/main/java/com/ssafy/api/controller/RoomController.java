package com.ssafy.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.request.RoomRegisterPostReq;
import com.ssafy.api.response.BoardListRes;
import com.ssafy.api.response.BoardRes;
import com.ssafy.api.response.RoomMemberListRes;
import com.ssafy.api.response.RoomRes;
import com.ssafy.api.service.RoomMemberService;
import com.ssafy.api.service.RoomService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.RoomMember;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ExampleProperty;

/**
 * 방 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "방 API", tags = {"Room"})
@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {
	@Autowired
	RoomService roomService;
	
	@Autowired
	RoomMemberService roomMemberService;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PostMapping("/join")
	@ApiOperation(value = "방 참가", notes = "<strong>방 code와 password로 방의 패스워드가 일치하는지 확인한다</strong>") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "방 참가 성공, 패스워드 일치함"),
        @ApiResponse(code = 401, message = "패스워드 불일치"),
        @ApiResponse(code = 404, message = "해당 코드의 방이 존재하지 않음")
    })
	public  ResponseEntity<BaseResponseBody> join(
			@RequestBody @ApiParam(value="방 코드와 패스워드", required = true, example="{\n \"code\":\"String\", \n \"password\":\"String\"\n}") Map<String, String> roomInfo) {
		try {
			if(roomService.joinRoom(roomInfo)) {
				return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
			} else {
				return ResponseEntity.status(401).body(BaseResponseBody.of(401, "password is not correct"));
			}
		} catch (Exception e) {
			return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room does not exist"));
		}		
	}
	
	@PostMapping()
	@ApiOperation(value = "방 생성", notes = "<strong>(방 이름, 비밀번호, 설명, 방장id(pk))로 방을 생성한다.</strong>") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 404, message = "host_id에 해당하는 guser가 존재하지 않음"),
        @ApiResponse(code = 500, message = "서버 오류")
    }
    )
	public ResponseEntity<RoomRes> create(
			@RequestBody @ApiParam(value="방 생성 정보", required = true) RoomRegisterPostReq roomInfo) {
		try {
			Room room = roomService.createRoom(roomInfo);
			return ResponseEntity.ok(RoomRes.of(200, "Success", room));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(404).body(RoomRes.of(404, "No matching guser for host_id", null));
		}
	}
	
	
	@GetMapping("/id/{id}")
	@ApiOperation(value = "방 정보 id로 찾기", notes = "<strong>방 id로 방을 찾는다</strong>") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 404, message = "id에 해당하는 방이 존재하지 않음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<RoomRes> findById(
			@PathVariable @ApiParam(value="방 id(pk)", required = true) Long id) {
		try {
			Room room = roomService.getRoomById(id);
			return ResponseEntity.ok(RoomRes.of(200, "Success", room));
		} catch (Exception e) {
			return ResponseEntity.status(404).body(RoomRes.of(404, "Room does not exist using this code", null));
		}
	}
	
	@GetMapping("/code/{code}")
	@ApiOperation(value = "방 정보 코드로 찾기", notes = "<strong>방 코드로 방을 찾는다</strong>") 
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 404, message = "code에 해당하는 방이 존재하지 않음"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<RoomRes> findByCode(
			@PathVariable @ApiParam(value="방 코드", required = true) String code) {
		try {
			Room room = roomService.getRoomByCode(code);
			return ResponseEntity.ok(RoomRes.of(200, "Success", room));
		} catch (Exception e) {
			return ResponseEntity.ok(RoomRes.of(200, "Room does not exist using this code", null));
		}
	}

	
	@PutMapping("/{id}")
	@ApiOperation(value = "방 수정", notes = "<strong>방 id로 방을 찾아서 수정한다. </strong> </br> 수정하고 싶은 속성을 입력하면 된다 (입력하지 않은 속성은 유지) ") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
//        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "id에 해당하는 방이 존재하지 않음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<RoomRes> update(
			@PathVariable @ApiParam(value="수정하고싶은 방 id", required = true) Long id,
			@RequestBody @ApiParam(value="수정하고싶은 방 정보", required = true) RoomRegisterPostReq roomInfo) {
		try {
			Room room = roomService.modifyRoom(id, roomInfo);
			return ResponseEntity.ok(RoomRes.of(200, "Success", room));
		} catch (Exception e) {
			return ResponseEntity.status(404).body(RoomRes.of(404, "Room does not exist using this id", null));
		}
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "방 삭제", notes = "<strong>방 id로 방을 삭제한다.</strong>") 
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
//		@ApiResponse(code = 401, message = "인증 실패"),
		@ApiResponse(code = 404, message = "해당 방 없음"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<BaseResponseBody> delete(
			@PathVariable @ApiParam(value="방 id", required = true) Long id) {
		try {
			roomService.removeRoom(id);
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		} catch (Exception e) {
			return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room does not exist"));
		}
	}
	
	@GetMapping("/id/{id}/boards")
	@ApiOperation(value = "방의 게시판들을 찾기", notes = "<strong>해당 방 id의 게시판들을 반환한다</strong>") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 404, message = "id에 해당하는 방이 존재하지 않음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<BoardListRes> findBoards(
			@PathVariable @ApiParam(value="방 id(pk)", required = true) Long id) {
		try {
			Room room = roomService.getRoomById(id);
			List<Board> list = room.getBoards();
			return ResponseEntity.ok(BoardListRes.of(200, "Success", list));
		} catch (Exception e) {
			return ResponseEntity.status(404).body(BoardListRes.of(404, "Room does not exist using this id",null));
		}
	}
	
	@GetMapping("id/{id}/members")
	@ApiOperation(value = "방의 멤버들을 찾기", notes = "<strong>해당 방 id의 멤버들을 반환한다</strong>") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 404, message = "id에 해당하는 방이 존재하지 않음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<RoomMemberListRes> findMembers(
			@PathVariable @ApiParam(value="방 id(pk)", required = true) Long id) {
		try {
			System.out.println("방의 멤버들을 찾는다");
//			List<RoomMember> list = room.getRoomMembers();
			List<RoomMember> list = new ArrayList<RoomMember>();
			list = roomMemberService.getRoomMembersbyRoomId(id);
			
			return ResponseEntity.ok(RoomMemberListRes.of(200, "Success", list));
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(404).body(RoomMemberListRes.of(404, "Room does not exist using this id",null));
		}
	}
}
