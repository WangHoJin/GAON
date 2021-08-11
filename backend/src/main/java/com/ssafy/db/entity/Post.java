package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 방 모델 정의.
 */
@Entity
@Getter
@Setter
@ToString(exclude = {"created_time","modified_time"})
//Entity 영속성 및 업데이트에 대한 Auditing 정보를 캡처하는 JPA Entity Listener
@EntityListeners(AuditingEntityListener.class)
public class Post extends BaseEntity{    
    String title;
    String content;
    
    @ManyToOne
    @JoinColumn(name="uid", nullable=false)
    @OnDelete(action=OnDeleteAction.CASCADE)
    Guser user;

    @ManyToOne
    @JoinColumn(name="bid", nullable=false)    
    Board board;
    
    @CreatedDate
    LocalDateTime created_time;
    @LastModifiedDate
    LocalDateTime modified_time;
    
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval= true, cascade=CascadeType.ALL,mappedBy = "post")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PostFile> files = new ArrayList<PostFile>();
    
}
