package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QPost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 방 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Transactional
@Repository
public class PostRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QPost qPost = QPost.post;
    public long deletePostById(Long id){
    	long res = jpaQueryFactory.delete(qPost).where(qPost.id.eq(id)).execute();
    	return res;
    }

}
