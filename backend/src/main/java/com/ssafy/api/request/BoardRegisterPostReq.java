package com.ssafy.api.request;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.db.entity.Room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 방 등록 API ([POST] /api/v1/room) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("BoardRegisterPostRequest")
public class BoardRegisterPostReq {
	@ApiModelProperty(name="게시판 Name", example="board_name")
    String name;
	@ApiModelProperty(name="게시판이 속한 방의 id", example="1")
    Long rid;
	@ApiModelProperty(name="게시판 Description", example="board_description")
	String description;
}
