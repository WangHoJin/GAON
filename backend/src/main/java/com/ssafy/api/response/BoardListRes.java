package com.ssafy.api.response;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.api.vo.BoardVO;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Board;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 해당 방의 게시판들을 얻어오는 API ([POST] /api/v1/rooms/{id}/id/boards) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("BoardListResponse")
public class BoardListRes extends BaseResponseBody {

	List<BoardVO> boards = new ArrayList<>();
	public static BoardListRes of(Integer statusCode , String message, List<Board> boards) {
		BoardListRes res = new BoardListRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		if(boards!=null) {
			for(Board b : boards) {
				BoardVO item = new BoardVO();
				item.setId(b.getId());		
				item.setName(b.getName());
				item.setRid(b.getRoom().getId());
				item.setDescription(b.getDescription());
				res.boards.add(item);
			}
		}
		return res;
	}
}
