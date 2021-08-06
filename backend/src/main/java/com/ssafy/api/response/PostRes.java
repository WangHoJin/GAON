package com.ssafy.api.response;

import java.time.LocalDateTime;

import com.ssafy.api.vo.PostVO;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.Post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 게시판 등록 API ([POST] /api/v1/rooms) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("BoardResponse")
public class PostRes extends BaseResponseBody {
	
	PostVO post;

	public static PostRes of(Integer statusCode , String message, Post post) {
		PostRes res = new PostRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		if(post!=null) {
			res.post = new PostVO(post.getId(), post.getTitle(), post.getContent(), post.getUser().getId(), post.getBoard().getId(), post.getCreated_time(), post.getModified_time());	
		}
		return res;
	}
}
