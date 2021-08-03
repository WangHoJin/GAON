package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval= true, cascade=CascadeType.ALL,mappedBy = "host")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Room> rooms = new ArrayList<Room>();
    
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval= true, cascade=CascadeType.ALL,mappedBy = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<RoomMember> roomMembers = new ArrayList<RoomMember>();

}
