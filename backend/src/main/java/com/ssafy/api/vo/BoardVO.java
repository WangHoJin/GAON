package com.ssafy.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("BoardVO")
public class BoardVO{
	@ApiModelProperty(name="게시판 id (pk)", example="1")
	Long id;
	@ApiModelProperty(name="게시판 Name", example="board_name")
	String name;
	@ApiModelProperty(name="게시판이 속한 방의 id", example="1")
	Long rid;
	@ApiModelProperty(name="게시판 Description", example="board_description")
	String description;
}
