package com.ssafy.db.entity;


import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
public class Board extends BaseEntity{    
    String name;
    @ManyToOne
    @JoinColumn(name="rid", nullable=false)
    Room room;
    String description;
    
    @OneToMany(fetch=FetchType.LAZY, orphanRemoval= true, cascade=CascadeType.ALL,mappedBy = "board")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Post> posts = new ArrayList<Post>();
}
