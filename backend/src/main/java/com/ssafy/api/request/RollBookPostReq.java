package com.ssafy.api.request;

import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 출석 등록 API ([POST] /api/v1/rollbook) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("RollBookPostReq")
public class RollBookPostReq {
	
	@ApiModelProperty(name="날짜", example="2021-08-08")
    LocalDate date;
	@ApiModelProperty(name="방 id", example="1")
    Long rid;
	@ApiModelProperty(name="유저 id", example="1")
    Long uid;
	@ApiModelProperty(name="출결상태", example="출석")
	String state;
	
}
