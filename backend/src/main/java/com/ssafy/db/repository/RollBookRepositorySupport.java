package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QRollBook;
import com.ssafy.db.entity.RollBook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 방 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Transactional
@Repository
public class RollBookRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QRollBook qRollBook = QRollBook.rollBook;
    
	public List<RollBook> findByDateAndRid(LocalDate date, Long rid) {
		List<RollBook> list = jpaQueryFactory.select(qRollBook).from(qRollBook).where(qRollBook.date.eq(date).and(qRollBook.roomMember.room.id.eq(rid))).fetch();
		System.out.println(list);
		return list;
	}

    
    
    
    
}
