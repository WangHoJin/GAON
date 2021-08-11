package com.ssafy.api.response;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.api.vo.PostVO;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Post;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 해당 방의 게시판들을 얻어오는 API ([POST] /api/v1/boards/{id}/posts) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("PostListResponse")
public class PostListRes extends BaseResponseBody {

	List<PostVO> posts = new ArrayList<>();
	public static PostListRes of(Integer statusCode , String message, List<Post> posts) {
		PostListRes res = new PostListRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		if(posts!=null) {
			for(Post post : posts) {
				PostVO item = new PostVO(post.getId(), post.getTitle(), post.getContent(), post.getBoard().getId(), post.getCreated_time(), post.getModified_time(), post.getUser().getId(), post.getUser().getNickname(), post.getUser().getEmail());
				res.posts.add(item);
			}
		}
		return res;
	}
}
