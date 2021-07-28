package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 방 모델 정의.
 */
@Entity
@Getter
@Setter
@ToString
public class Room{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer rid = null;
    
    String name;
    Integer host_id;
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
    String code;
    String description;
}
