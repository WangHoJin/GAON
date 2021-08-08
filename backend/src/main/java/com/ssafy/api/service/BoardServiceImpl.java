package com.ssafy.api.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.api.request.BoardRegisterPostReq;
import com.ssafy.api.request.PostRegisterPostReq;
import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.Guser;
import com.ssafy.db.entity.Post;
import com.ssafy.db.entity.Room;
import com.ssafy.db.repository.BoardRepository;
import com.ssafy.db.repository.BoardRepositorySupport;
import com.ssafy.db.repository.PostRepository;
import com.ssafy.db.repository.PostRepositorySupport;


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
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	PostRepositorySupport postRepositorySupport;

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
	
	@Override
	public boolean removeBoard(Long id) {
		long res = boardRepositorySupport.deleteRoomById(id);
		if(res>0) return true;
		else return false;
	}

	@Override
	public Post writePost(Long bid, PostRegisterPostReq postInfo) {
		Post post = new Post();
//		post.setBoard(getBoardById(postInfo.getBid()));
		Board board = new Board();
		Guser user = new Guser();
		board.setId(bid);
		user.setId(postInfo.getUid());
		
		post.setBoard(board);
		post.setUser(user);
		post.setTitle(postInfo.getTitle());
		post.setContent(postInfo.getContent());
		
		return postRepository.save(post);
	}

	@Override
	public Post updatePost(Long pid, Map<String, Object> postInfo) {
		Post post = postRepository.findById(pid).get();
		Long bid = Long.parseLong(String.valueOf(postInfo.get("bid")));
		String title = (String) postInfo.get("title");
		String content = (String) postInfo.get("content");
		if(bid!=null) {
			Board board = new Board();
			board.setId(bid);
			post.setBoard(board);
		}
		if(title!=null) {
			post.setTitle(title);
		}
		if(content!=null) {
			post.setContent(content);
		}
		return postRepository.save(post);
	}

	@Override
	public boolean removePost(Long pid) {
		try {
			if(postRepositorySupport.deletePostById(pid)>0) {
				return true;
			} else return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Post getPostById(Long pid) {
		return postRepository.findById(pid).get();
	}
}
