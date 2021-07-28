package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

/**
 * 구글유저 모델 정의.
 */
@Entity
@Getter
@Setter
@ToString
public class Guser extends BaseEntity{
    String email;
    String nickname;

}
