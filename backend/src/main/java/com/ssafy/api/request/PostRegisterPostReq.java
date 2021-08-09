package com.ssafy.api.request;

import java.time.LocalDateTime;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.ssafy.db.entity.Room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 방 등록 API ([POST] /api/v1/board/{id}) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("PostRegisterPostRequest")
public class PostRegisterPostReq {
//	@ApiModelProperty(name="게시판 id (pk)", example="1")
//    Long bid;
	@ApiModelProperty(name="게시글 작성자 id (pk)", example="1")
    Long uid;
	@ApiModelProperty(name="게시글 제목", example="post_title")
	String title;
	@ApiModelProperty(name="게시글 내용", example="post_content")
	String content;
}
