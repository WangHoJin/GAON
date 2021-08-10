package com.ssafy.api.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.ssafy.api.request.RollBookGetReq;
import com.ssafy.api.request.RollBookPostReq;
import com.ssafy.api.request.RoomMemberRegisterPostReq;
import com.ssafy.api.request.RoomRegisterPostReq;
import com.ssafy.api.response.BoardListRes;
import com.ssafy.api.response.BoardRes;
import com.ssafy.api.response.RollBookListRes;
import com.ssafy.api.response.RoomListRes;
import com.ssafy.api.response.RoomMemberListRes;
import com.ssafy.api.response.RoomMemberRes;
import com.ssafy.api.response.RoomRes;
import com.ssafy.api.service.RollBookService;
import com.ssafy.api.service.RoomMemberService;
import com.ssafy.api.service.RoomService;
import com.ssafy.api.vo.RoomVO;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.RollBook;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.RoomMember;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ExampleProperty;

/**
 * 출석부 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "출석부 API", tags = {"RollBook"})
@RestController
@RequestMapping("/api/v1/rollbook")
public class RollBookController {
	@Autowired
	RoomService roomService;
	
	@Autowired
	RoomMemberService roomMemberService;
	
	@Autowired
	RollBookService rollBookService;

		
	@PostMapping()
	@ApiOperation(value = "출석부 생성", notes = "<strong>(날짜, 방id, 유저id)로 출석부기록을 생성한다</strong>만약 해당 날짜,방,유저의 출석 정보가 존재할 경우 새로 요청한 state로 업데이트한다") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 404, message = "방,유저 id에 해당하는 룸멤버가 존재하지 않음"),
        @ApiResponse(code = 500, message = "서버 오류")
    }
    )
	public ResponseEntity<RollBookListRes> create(
			@RequestBody @ApiParam(value="출석부 생성 정보", required = true) List<RollBookPostReq> rollbookList) {
		try {
//			Room room = roomService.createRoom(rollbookInfo);			
//			roomMemberService.createRoomMember(new RoomMemberRegisterPostReq(rollbookInfo.getHost_id(), room.getId()));
			System.out.println(":::::::::: 출석부생성요청");
			List<RollBook> rollBook = new ArrayList<RollBook>();
			if(rollbookList != null) {
				for(RollBookPostReq r : rollbookList) {
					RollBook item = new RollBook();
					item.setDate(r.getDate());
					item.setState(r.getState());
					RoomMember rm = roomMemberService.getRoomMemberByUidAndRid(r.getUid(), r.getRid());
					System.out.println("출석부에 룸멤버추가"+rm);
					item.setRoomMember(rm);
					rollBookService.create(item);
					rollBook.add(item);
					
				}
			}
			
			return ResponseEntity.ok(RollBookListRes.of(200, "Success", rollBook));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(404).body(RollBookListRes.of(404, "No matching guser for host_id", null));
		}
	}
	
	
	@GetMapping("/{rid}/{datestring}")
	@ApiOperation(value = "출석부 조회", notes = "<strong>(날짜, 방id)로 출석부기록을 조회한다</strong>") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 404, message = "날짜,방 id에 해당하는 출석기록이 존재하지 않음"),
        @ApiResponse(code = 500, message = "서버 오류")
    }
    )
	public ResponseEntity<RollBookListRes> findRollBooks(
			@PathVariable @ApiParam(value="조회 날짜(ex:\"2021-08-09\")", required = true) String datestring, 
			@PathVariable @ApiParam(value="조회 방id", required = true) Long rid) {
		System.out.println(":::::::::: 출석부 조회 요청");
		LocalDate date = LocalDate.parse(datestring, DateTimeFormatter.ISO_DATE);
		try {			
			List<RollBook> rollBooks = new ArrayList<RollBook>();	
			rollBooks = rollBookService.getRollBooksByDateAndRid(date, rid);
			
			return ResponseEntity.ok(RollBookListRes.of(200, "Success", rollBooks));
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return ResponseEntity.status(404).body(RollBookListRes.of(404, "No matching rollbook.rid for date", null));
		}
	}
	
	
	
	

}
