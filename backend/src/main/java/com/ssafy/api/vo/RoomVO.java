package com.ssafy.api.vo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.db.entity.Guser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@ApiModel("RoomVO")
public class RoomVO{
	@ApiModelProperty(name="방 id (pk)", example="1")
	Long id;
	@ApiModelProperty(name="방 code", example="MzIxMDgwNjEyMzIyMTUy")
	String code;
	@ApiModelProperty(name="방 name", example="room_name")
	String name;
	@ApiModelProperty(name="방장 id", example="1")
	Long host_id;
	@ApiModelProperty(name="방 description", example="board_description")
	String description;
    @ApiModelProperty(name="방 생성 시간", example="2021-08-06 00:32:21.091000")
    LocalDateTime created_time;
    @ApiModelProperty(name="방 마지막 수정 시간", example="2021-08-06 00:32:21.186000")
    @LastModifiedDate
    LocalDateTime modified_time;
	public RoomVO(Long id, String code, String name, Long host_id, String description, LocalDateTime created_time,
			LocalDateTime modified_time) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.host_id = host_id;
		this.description = description;
		this.created_time = created_time;
		this.modified_time = modified_time;
	}
    
}
