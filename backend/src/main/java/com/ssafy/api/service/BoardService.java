package com.ssafy.api.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.api.request.BoardRegisterPostReq;
import com.ssafy.api.request.PostRegisterPostReq;
import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.Post;
import com.ssafy.db.entity.PostFile;

/**
 *	방 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface BoardService {
	//게시판
	Board createBoard(BoardRegisterPostReq boardInfo);
	Board getBoardById(Long id);
	List<Board> getBoardsByRid(Long rid);
	Board modifyBoard(Long id, Map<String,String> boardInfo); // name, description 속성이 담겨있으면 수정함
	boolean removeBoard(Long id);
	
	//게시글
	Post writePost(Long bid, PostRegisterPostReq postInfo);
	Post updatePost(Long pid, Map<String,Object> postInfo); // bid(게시판 옮기기), name, content 속성이 담겨있으면 수정함
	boolean removePost(Long pid);
	Post getPostByPid(Long pid);
	List<Post> getPostsById(Long id);
	
	//파일
	PostFile registFile(Long pid, MultipartFile file);
	List<File> getFiles(Long pid);
	File getFileByPfid(Long pfid);
}
