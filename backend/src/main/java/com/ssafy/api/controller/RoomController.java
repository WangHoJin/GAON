package com.ssafy.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.service.RoomService;
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
	@ApiOperation(value = "방 생성", notes = "<strong>(방 이름, 비밀번호, 설명, 방장uid)로 방을 생성한다.</strong> </br> rid와 code는 자동 생성된다") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public Room create(
			@RequestBody @ApiParam(value="방 생성 정보", required = true) Room roomInfo) {
		
		//임의로 리턴된 Room 인스턴스. 현재 코드는 룸 생성 성공 여부만 판단하기 때문에 굳이 Insert 된 방 정보를 응답하지 않음.
		Room room = roomService.saveRoom(roomInfo);
		System.out.println("request");
		System.out.println(roomInfo);
		
//		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		return room;
	}
	
	
	@GetMapping("/rid/{rid}")
	@ApiOperation(value = "방 정보 rid로 찾기", notes = "<strong>방 rid로 방을 찾는다</strong>") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public Room findById(
			@PathVariable @ApiParam(value="방 코드", required = true) Long rid) {
		try {
			Room room = roomService.getRoomById(rid);
			System.out.println(room);
			return room;
		} catch (Exception e) {
			System.out.println("catch _ null");
			System.out.println(e);
			return null;
		}
	}
	
	@GetMapping("/code/{code}")
	@ApiOperation(value = "방 정보 코드로 찾기", notes = "<strong>방 코드로 방을 찾는다</strong>") 
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 401, message = "인증 실패"),
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

	
	@PutMapping()
	@ApiOperation(value = "방 수정", notes = "<strong>방 rid로 방을 찾아서 수정한다.</strong></br> 컬럼 값을 넣지 않을 경우 null이 된다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public Room update(
			@RequestBody @ApiParam(value="수정하고싶은 방 정보", required = true) Room roomInfo) {
		
		Room room = roomService.saveRoom(roomInfo);
		System.out.println("request");
		System.out.println(roomInfo);
		return room;
	}
	
	@DeleteMapping("/{rid}")
	@ApiOperation(value = "방 삭제", notes = "<strong>방 rid로 방을 삭제한다.</strong>") 
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 401, message = "인증 실패"),
		@ApiResponse(code = 404, message = "해당 방 없음"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<BaseResponseBody> delete(
			@PathVariable @ApiParam(value="방 rid", required = true) Long rid) {
		System.out.println("진입1 "+rid);
		try {
			roomService.removeRoom(rid);
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		} catch (Exception e) {
			return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Room does not exist"));
		}
	}
}
