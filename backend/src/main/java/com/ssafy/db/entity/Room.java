package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * 방 모델 정의.
 */
@Entity
@Getter
@Setter
@ToString
public class Room extends BaseEntity{    
    String name;
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
    @Column(unique=true)
    String code;
    String description;
    
    @ManyToOne
    @JoinColumn(name="host_id", nullable=false)
    Guser host;
    
    @CreatedDate
    LocalDateTime created_time;
    @LastModifiedDate
    LocalDateTime modified_time;
}
