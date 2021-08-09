package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QPostFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 방 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Transactional
@Repository
public class PostFileRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QPostFile qPostFile = QPostFile.postFile;
    public long deletePostFileById(Long id){
    	long res = jpaQueryFactory.delete(qPostFile).where(qPostFile.id.eq(id)).execute();
    	return res;
    }
}
