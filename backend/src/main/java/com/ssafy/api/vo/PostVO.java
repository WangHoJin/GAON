package com.ssafy.api.vo;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostVO {    
	@ApiModelProperty(name="게시글 id (pk)", example="1")
	Long id;
	@ApiModelProperty(name="게시글 제목", example="post_title")
	String title;
	@ApiModelProperty(name="게시글 내용", example="post_content")
    String content;
    @ApiModelProperty(name="게시판 id (pk)", example="1")
    Long bid;
    @ApiModelProperty(name="게시글 작성 시간", example="2021-08-06 21:18:21.670000")
    LocalDateTime created_time;
    @ApiModelProperty(name="게시글 수정 시간", example="2021-08-06 21:18:21.670000")
    LocalDateTime modified_time;
    @ApiModelProperty(name="작성자 id (pk)", example="1")
    Long uid;
    @ApiModelProperty(name="작성자 닉네임 (pk)", example="김민정")
    String nickname;
    @ApiModelProperty(name="작성자 이메일 (pk)", example="")
    String email;
    
    public PostVO() {}

	public PostVO(Long id, String title, String content, Long bid, LocalDateTime created_time,
			LocalDateTime modified_time, Long uid, String nickname, String email) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.bid = bid;
		this.created_time = created_time;
		this.modified_time = modified_time;
		this.uid = uid;
		this.nickname = nickname;
		this.email = email;
	}

    

}
