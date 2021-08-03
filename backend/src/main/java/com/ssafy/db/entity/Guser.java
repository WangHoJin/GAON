package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

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
    
    @OneToMany(mappedBy ="host")
    private List<Room> rooms = new ArrayList<Room>();

}
