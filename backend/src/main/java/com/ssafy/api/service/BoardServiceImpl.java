package com.ssafy.api.service;

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
	public Board modifyBoard(Long id, BoardRegisterPostReq boardInfo) {
		Board board = boardRepository.findById(id).get();
		System.out.println(board);
		if(boardInfo.getRid() != null) {
			Room room = roomService.getRoomById(boardInfo.getRid());
			board.setRoom(room);
		}
		if(boardInfo.getName()!=null) {
			board.setName(boardInfo.getName());
		}
		if(boardInfo.getDescription()!=null) {
			board.setDescription(boardInfo.getDescription());
		}
		return boardRepository.save(board);
	}
	
	@Override
	public Board getBoardById(Long id) {
		return boardRepository.findById(id).get();
	}
	
	@Override
	public void removeBoard(Long id) {
		boardRepository.deleteById(id);
		boardRepository.flush();
	}

}
