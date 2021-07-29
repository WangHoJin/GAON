package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.checkerframework.common.aliasing.qual.Unique;

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
    String password;
    @Column(unique=true)
    String code;
    String description;
}
