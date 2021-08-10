package com.ssafy.api.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.api.request.BoardRegisterPostReq;
import com.ssafy.api.request.PostRegisterPostReq;
import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.Guser;
import com.ssafy.db.entity.Post;
import com.ssafy.db.entity.PostFile;
import com.ssafy.db.entity.Room;
import com.ssafy.db.repository.BoardRepository;
import com.ssafy.db.repository.BoardRepositorySupport;
import com.ssafy.db.repository.PostFileRepository;
import com.ssafy.db.repository.PostFileRepositorySupport;
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
	@Autowired
	PostFileRepository postFileRepository;
	@Autowired
	PostFileRepositorySupport postFileRepositorySupport;

	
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
//		Board board = new Board();
//		board.setId(bid);
		Guser user = new Guser();
		user.setId(postInfo.getUid());
		post.setBoard(getBoardById(bid));
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
	public Post getPostByPid(Long pid) {
		return postRepository.findById(pid).get();
	}

	@Override
	public List<Post> getPostsById(Long id) {
		return getBoardById(id).getPosts();
	}

	@Override
	public PostFile registFile(Long pid, MultipartFile file) {
		Post post = getPostByPid(pid);
		PostFile postFile = new PostFile();
		
        String originalName = file.getOriginalFilename(); //원본 파일명
        String storedName = pid+"_"+originalName; //서버에 저장될 파일명
        String fileExtension = originalName.substring(originalName.lastIndexOf(".")); //파일 확장자
		long fileSize = file.getSize(); //파일 사이즈
		
//		System.out.println("원본 파일명 : "+ originalFile);
//		System.out.println("저장 파일명 : "+ fileName);
//		System.out.println("파일 확장자 : "+ fileExtension);
//		System.out.println("파일 사이즈 : "+ fileSize);
		postFile.setPost(post);
		postFile.setOriginName(originalName);
		postFile.setFileName(storedName);
		postFile.setFileType(fileExtension);
		postFile.setFileSize(fileSize);
		
	     //파일을 저장하기 위한 파일 객체 생성  
		String rootPath = System.getProperty("user.dir");
        String savePath = rootPath+"\\files\\Room"+post.getBoard().getRoom().getId();
        String filePath = savePath + "\\" + storedName;
        /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
        if (!new File(savePath).exists()) {
            try{
                new File(savePath).mkdirs();
                System.out.println("경로 : " + savePath);
            }
            catch(Exception e){
            	System.out.println("경로 만들기 실패");
                e.getStackTrace();
            }
        }
        //파일 저장
        //경로 루트 files/Room[id]/[pid]_파일명
        try {
        	File newFile = new File(filePath);
        	System.out.println(newFile.getAbsolutePath());
        	System.out.println(newFile.getPath());
			file.transferTo(newFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return postFileRepository.save(postFile);
	}

	@Override
	public List<File> getFiles(Long pid) {
		Post post = getPostByPid(pid);
		List<PostFile> postFiles = post.getFiles();
		List<File> files = new ArrayList<>();
		
		String rootPath = System.getProperty("user.dir");
        String savePath = rootPath+"\\files\\Room"+post.getBoard().getRoom().getId();
        		
		for(PostFile pf : postFiles) {
			try {
				File file = new File(savePath+"\\"+pf.getFileName());
				files.add(file);
			} catch (Exception e) {
				System.out.println("파일 입출력 에러");
				e.printStackTrace();
				return null;
			}
		}
		return files;
	}

	@Override
	public List<Board> getBoardsByRid(Long rid) {
		return boardRepository.findByRoomId(rid).get();
	}
}
