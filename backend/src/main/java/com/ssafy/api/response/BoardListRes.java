package com.ssafy.api.response;

import java.util.ArrayList;
import java.util.List;

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

	@Getter
	@Setter
	@ApiModel("BoardSimpleRes")
	public 
	static class BoardSimpleRes{
		@ApiModelProperty(name="게시판 id (pk)", example="1")
		Long id;
		@ApiModelProperty(name="게시판 Name", example="board_name")
		String name;
		@ApiModelProperty(name="게시판이 속한 방의 id", example="1")
		Long rid;
		@ApiModelProperty(name="게시판 Description", example="board_description")
		String description;
	}

	List<BoardSimpleRes> boards = new ArrayList<>();
	public static BoardListRes of(Integer statusCode , String message, List<Board> boards) {
		BoardListRes res = new BoardListRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		if(boards!=null) {
			for(Board b : boards) {
				BoardSimpleRes item = new BoardSimpleRes();
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
