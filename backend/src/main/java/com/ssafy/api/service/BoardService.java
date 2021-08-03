package com.ssafy.api.service;

import com.ssafy.api.request.BoardRegisterPostReq;
import com.ssafy.db.entity.Board;

/**
 *	방 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface BoardService {
	Board createBoard(BoardRegisterPostReq boardInfo);
	Board getBoardById(Long id);
	Board modifyBoard(Long id, BoardRegisterPostReq boardInfo);
	void removeBoard(Long id);
}
