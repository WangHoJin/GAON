package com.ssafy.api.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.api.request.BoardRegisterPostReq;
import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.Room;
import com.ssafy.db.repository.BoardRepository;
import com.ssafy.db.repository.BoardRepositorySupport;


/**
 *	방 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("boardService")
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	BoardRepositorySupport boardRepositorySupport;
	
	@Autowired
	RoomService roomService;
	
	@Override
	@Transactional
	public Board createBoard(BoardRegisterPostReq boardInfo) {
		Board board = new Board();
		Room room = roomService.getRoomById(boardInfo.getRid());
		board.setName(boardInfo.getName());
		board.setRoom(room);
		board.setDescription(boardInfo.getDescription());
		return boardRepository.save(board);
	}

	@Override
	public Board modifyBoard(Long id, Map<String ,String> boardInfo) {
		Board board = boardRepository.findById(id).get();
		String name = boardInfo.get("name");
		String description = boardInfo.get("description");
		System.out.println(board);
		if(name!=null) {
			board.setName(name);
		}
		if(description!=null) {
			board.setDescription(description);
		}
		return boardRepository.save(board);
	}
	
	@Override
	public Board getBoardById(Long id) {
		return boardRepository.findById(id).get();
	}
	
//	@Transactional
	@Override
	public boolean removeBoard(Long id) {
		long res = boardRepositorySupport.deleteRoomById(id);
		if(res>0) return true;
		else return false;
	}

}
