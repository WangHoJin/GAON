package com.ssafy.api.response;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.api.vo.RoomVO;
import com.ssafy.api.vo.RoomVO;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Room;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 해당 방의 게시판들을 얻어오는 API ([POST] /api/v1/rooms/uid/{uid}) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("RoomListResponse")
public class RoomListRes extends BaseResponseBody {

	List<RoomVO> rooms = new ArrayList<>();
	public static RoomListRes of(Integer statusCode , String message, List<Room> rooms) {
		RoomListRes res = new RoomListRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		if(rooms!=null) {
			for(Room room : rooms) {
				RoomVO item = new RoomVO(room.getId(), room.getCode(), room.getName(), room.getHost().getId(), room.getDescription(), room.getCreated_time(), room.getModified_time());
				res.rooms.add(item);
			}
		}
		return res;
	}
}
