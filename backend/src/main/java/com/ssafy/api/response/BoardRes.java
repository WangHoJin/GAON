package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Board;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 게시판 등록 API ([POST] /api/v1/room) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("BoardResponse")
public class BoardRes extends BaseResponseBody {
	
	@ApiModelProperty(name="게시판 id (pk)", example="1")
	Long id;
	@ApiModelProperty(name="게시판 Name", example="board_name")
    String name;
	@ApiModelProperty(name="게시판이 속한 방의 id", example="1")
    Long rid;
	@ApiModelProperty(name="게시판 Description", example="board_description")
	String description;

	public static BoardRes of(Integer statusCode , String message, Board board) {
		BoardRes res = new BoardRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		if(board!=null) {
			res.setId(board.getId());		
			res.setName(board.getName());
			res.setRid(board.getRoom().getId());
			res.setDescription(board.getDescription());
		}
		return res;
	}
	
}
