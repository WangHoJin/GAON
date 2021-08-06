package com.ssafy.api.service;

import java.util.Map;

import com.ssafy.api.request.BoardRegisterPostReq;
import com.ssafy.db.entity.Board;

/**
 *	방 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface BoardService {
	Board createBoard(BoardRegisterPostReq boardInfo);
	Board getBoardById(Long id);
	//name, description 속성이 담겨있으면 수정함
	Board modifyBoard(Long id, Map<String,String> boardInfo);
	boolean removeBoard(Long id);
}
