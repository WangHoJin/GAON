package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Guser;
import com.ssafy.db.entity.QGuser;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.User;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class GoogleUserRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
//    QUser qUser = QUser.user;
    QGuser qGuser = QGuser.guser;

	public Optional<Guser> findGuserByEmail(String email) {
		Guser guser = jpaQueryFactory.select(qGuser).from(qGuser)
                .where(qGuser.email.eq(email)).fetchOne();
        if(guser == null) return Optional.empty();
        return Optional.ofNullable(guser);
	}
	
	
    @Transactional
    public Long updateByUserId(Guser guser) {
       
       Long affectedRow = jpaQueryFactory.update(qGuser)
             .where(qGuser.email.eq(guser.getEmail()))
             .set(qGuser.nickname, guser.getNickname())
             .execute();
       if(affectedRow != 0) {
    	   System.out.println("::::   "+guser.getNickname()+" 으로 변경 완료   ::::::");
       }
       
       return affectedRow;
    }
    
    @Transactional
    public void deleteByEmail(String email) {
    	Long affectedRow = jpaQueryFactory.delete(qGuser)
                .where(qGuser.email.eq(email))
                .execute();
    	if(affectedRow != 0) {
     	   System.out.println("::::   "+email+" 삭제완료   ::::::");
        }         
    }
    
}
