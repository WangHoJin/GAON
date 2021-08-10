package com.ssafy.api.response;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.api.vo.BoardVO;
import com.ssafy.api.vo.GuserVO;
import com.ssafy.api.vo.RollBookVO;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.Guser;
import com.ssafy.db.entity.RollBook;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.RoomMember;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 방 출석부 찾기 API ([GET] /api/v1/rollbook/{rid}) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("RollBookListRes")
public class RollBookListRes extends BaseResponseBody {	


	List<RollBookVO> rollbooks = new ArrayList<RollBookVO>();	
	
	public static RollBookListRes of(Integer statusCode , String message, List<RollBook> rollbook) {
		RollBookListRes res = new RollBookListRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		if(rollbook!=null) {
			for(RollBook r : rollbook) {
				RollBookVO item = new RollBookVO();
				
				item.setDate(r.getDate());
				item.setRid(r.getRoomMember().getRoom().getId());
				item.setUid(r.getRoomMember().getUser().getId());
				item.setEmail(r.getRoomMember().getUser().getEmail());
				item.setNickname(r.getRoomMember().getUser().getNickname());
				item.setState(r.getState());				
				
				res.rollbooks.add(item);
			}
		}
		return res;
	}
	
	
	
}
