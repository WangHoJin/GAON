package com.ssafy.api.request;

import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 출석 조회 API ([POST] /api/v1/rollbook) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("RollBookGetReq")
public class RollBookGetReq {
	
	@ApiModelProperty(name="날짜", example="2021-08-09")
    LocalDate date;
	@ApiModelProperty(name="방 id", example="1")
    Long rid;
	
}
