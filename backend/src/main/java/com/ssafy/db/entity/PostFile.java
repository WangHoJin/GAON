package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 방 모델 정의.
 */
@Entity
@Getter
@Setter
@ToString
public class PostFile extends BaseEntity{    
	@Column(name="origin_name")
	String originName;
	@Column(name="file_name")
    String fileName;
	@Column(name="file_size")
	Long fileSize;
	@Column(name="file_type")
	String fileType;
    
    @ManyToOne
    @JoinColumn(name="pid", nullable=false)
    Post post;
}
