package com.ssafy.api.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.api.request.RollBookGetReq;
import com.ssafy.api.request.RoomRegisterPostReq;
import com.ssafy.db.entity.Guser;
import com.ssafy.db.entity.RollBook;
import com.ssafy.db.entity.Room;
import com.ssafy.db.repository.RollBookRepository;
import com.ssafy.db.repository.RollBookRepositorySupport;
import com.ssafy.db.repository.RoomRepository;
import com.ssafy.db.repository.RoomRepositorySupport;


/**
 *	방 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Transactional
@Service("RollBookService")
public class RollBookServiceImpl implements RollBookService {

	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	RoomRepositorySupport roomRepositorySupport;
	
	@Autowired
	RollBookRepository rollBookRepository;
	
	@Autowired
	RollBookRepositorySupport rollBookRepositorySupport;
	
	



	@Override
	@Transactional
	public RollBook create(RollBook rollBook) {		
		System.out.println("롤북 create impl");
//		Long rid = rollBookRepository.findOne(rollBook);
		RollBook temp = rollBookRepository.findByRoomMemberAndDate(rollBook.getRoomMember(), rollBook.getDate());
		
		if(temp != null) {
			temp.setState(rollBook.getState());			
			System.out.println("출석부 업데이트");
			
			return rollBookRepository.save(temp);
		}else {
			
			System.out.println("출석부 새로 저장"+rollBook);
			return rollBookRepository.save(rollBook);
		}
	}



	@Override
	public List<RollBook> getRollBooksByDateAndRid(LocalDate date, Long rid) {
		
		return rollBookRepositorySupport.findByDateAndRid(date, rid);
	}

	//rollBookRepository.save(rollBook)

}
