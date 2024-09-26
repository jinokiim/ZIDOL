package com.zidol.fc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zidol.fc.domain.Board;
import com.zidol.fc.domain.Reply;
import com.zidol.fc.domain.User;
import com.zidol.fc.repository.BoardRepository;
import com.zidol.fc.repository.ReplyRepository;

@Service
public class BoardService {

	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	ReplyRepository replyRepository;
	
	public List<Map<String, Object>> findAllBoard() {
		List<Map<String, Object>> results = new ArrayList<>();
		List<Board> boards = boardRepository.findAll();
		
		for(Board board : boards) {
			Map<String, Object> result = new HashMap<>();
			result.put("user", board.getUser());
			result.put("board", board);
			results.add(result);
		}
		
		return results;
	}
	
	public Map<String, Object> findByBoardCode(long boardCode) {
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> replys = new ArrayList<>();
		Board board = boardRepository.findByBoardCode(boardCode);
		User user = board.getUser();
		List<Reply> tempReplys = board.getReply();
		
		for(Reply reply : tempReplys) {
			Map<String, Object> temp = new HashMap<>();
			temp.put("user", reply.getUser());
			temp.put("reply", reply);
			replys.add(temp);
		}
		
		result.put("user", user);
		result.put("board", board);
		result.put("replys", replys);
		
		return result;
	}
	
	public Board insertBoard(Board board) {
		return boardRepository.save(board);
	}
	
	public Board updateBoard(Board board) {
		return boardRepository.save(board);
	}
	
	public void deleteBoard(long boardCode) {
		boardRepository.deleteById(boardCode);
	}
	
	public Reply findByReplyCode(long replyCode) {
		return replyRepository.findByReplyCode(replyCode);
	}
	
	public Reply insertReply(Reply reply) {
		return replyRepository.save(reply);
	}
	
	public Reply updateReply(Reply reply) {
		return replyRepository.save(reply);
	}
	
	public void deleteReply(long replyCode) {
		replyRepository.deleteById(replyCode);
	}
	
	public void deleteAllReply(List<Reply> reply) {
		replyRepository.deleteAllInBatch(reply);
	}
}



