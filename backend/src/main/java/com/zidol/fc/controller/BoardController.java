package com.zidol.fc.controller;

import java.nio.charset.Charset;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zidol.fc.domain.Board;
import com.zidol.fc.domain.Reply;
import com.zidol.fc.domain.User;
import com.zidol.fc.error.ErrorResponse;
import com.zidol.fc.error.board.DeleteByUnauthUserException;
import com.zidol.fc.error.board.UpdateByUnauthUserException;
import com.zidol.fc.service.BoardService;
import com.zidol.fc.service.UserService;

@RestController
public class BoardController {

	@Autowired
	BoardService boardService;

	@Autowired
	UserService userService;

	// 게시글 전체 리스트업
	@GetMapping("/find-all-board.act")
	public ResponseEntity<DataResponse> findAllBoard() {
		DataResponse dataResponse = new DataResponse();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		dataResponse.setStatus(StatusCode.OK.getStatus());
		dataResponse.setCode(StatusCode.OK.getCode());
		dataResponse.setData(boardService.findAllBoard());

		return new ResponseEntity<DataResponse>(dataResponse, headers, HttpStatus.OK);
	}

	// 게시글 상세페이지 이동
	@GetMapping("/find-board.act")
	public ResponseEntity<DataResponse> findBoard(@RequestParam long boardCode) {
		DataResponse dataResponse = new DataResponse();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		Map<String, Object> result = boardService.findByBoardCode(boardCode);

		if (result != null) {
			dataResponse.setStatus(StatusCode.OK.getStatus());
			dataResponse.setCode(StatusCode.OK.getCode());
			dataResponse.setData(result);

			return new ResponseEntity<DataResponse>(dataResponse, headers, HttpStatus.OK);
		} else {
			dataResponse.setStatus(StatusCode.NOT_FOUND.getStatus());
			dataResponse.setCode(StatusCode.NOT_FOUND.getCode());

			return new ResponseEntity<DataResponse>(dataResponse, headers, HttpStatus.NOT_FOUND);
		}
	}

	// 게시글 작성
	@PostMapping("/insert-board.act")
	public ResponseEntity<DataResponse> insertBoard(@RequestBody Map<String, String> params) {
		DataResponse dataResponse = new DataResponse();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		User user = userService.findByUserCode(Long.parseLong(params.get("userCode")));
		Board board = Board.builder().user(user).boardType(params.get("boardType")).boardTitle(params.get("boardTitle"))
				.boardContent(params.get("boardContent")).build();

		if (boardService.insertBoard(board) != null) {
			dataResponse.setStatus(StatusCode.OK.getStatus());
			dataResponse.setCode(StatusCode.OK.getCode());
			dataResponse.setData(board);

			return new ResponseEntity<DataResponse>(dataResponse, headers, HttpStatus.OK);
		} else {
			dataResponse.setStatus(StatusCode.NOT_FOUND.getStatus());
			dataResponse.setCode(StatusCode.NOT_FOUND.getCode());

			return new ResponseEntity<DataResponse>(dataResponse, headers, HttpStatus.NOT_FOUND);
		}

	}

	// 게시글 수정
	@PutMapping("/update-board.act")
	public ResponseEntity<DataResponse> updateBoard(@RequestBody Map<String, String> params) {
		DataResponse dataResponse = new DataResponse();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		Map<String, Object> result = boardService.findByBoardCode(Long.parseLong(params.get("boardCode")));
		Board board = (Board) result.get("board");
		User user = board.getUser();

		board.setBoardTitle(params.get("boardTitle"));
		board.setBoardContent(params.get("boardContent"));

		if (Long.parseLong(params.get("userCode")) == user.getUserCode()) {
			dataResponse.setStatus(StatusCode.OK.getStatus());
			dataResponse.setCode(StatusCode.OK.getCode());
			dataResponse.setData(boardService.updateBoard(board));

			return new ResponseEntity<DataResponse>(dataResponse, headers, HttpStatus.OK);
		} else {
			ErrorResponse.CustomFieldError customFieldError = new ErrorResponse.CustomFieldError("Board",
					params.get("userCode"), "작성자만 글을 수정할 수 있습니다.");
			throw new UpdateByUnauthUserException(customFieldError);
		}
	}

	// 게시글 삭제
	@DeleteMapping("/delete-board.act")
	public ResponseEntity<DataResponse> deleteBoard(@RequestBody Map<String, Long> params) {
		DataResponse dataResponse = new DataResponse();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		Map<String, Object> result = boardService.findByBoardCode(params.get("boardCode"));
		Board board = (Board) result.get("board");
		User user = board.getUser();

		if (params.get("userCode") == user.getUserCode()) {
			boardService.deleteAllReply(board.getReply());
			boardService.deleteBoard(params.get("boardCode"));
			dataResponse.setStatus(StatusCode.OK.getStatus());
			dataResponse.setCode(StatusCode.OK.getCode());

			return new ResponseEntity<DataResponse>(dataResponse, headers, HttpStatus.OK);
		} else {
			ErrorResponse.CustomFieldError customFieldError = new ErrorResponse.CustomFieldError("Board",
					String.valueOf(params.get("userCode")), "작성자만 글을 삭제할 수 있습니다.");
			throw new DeleteByUnauthUserException(customFieldError);
		}

	}

	// 댓글 등록
	@PostMapping("/insert-reply.act")
	public ResponseEntity<DataResponse> insertReply(@RequestBody Map<String, String> params) {
		DataResponse dataResponse = new DataResponse();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		User user = userService.findByUserCode(Long.parseLong(params.get("userCode")));
		Map<String, Object> result = boardService.findByBoardCode(Long.parseLong(params.get("boardCode")));
		Board board = (Board) result.get("board");
		Reply reply = Reply.builder().user(user).board(board).replyContent(params.get("replyContent")).build();

		if (boardService.insertReply(reply) != null) {
			dataResponse.setStatus(StatusCode.OK.getStatus());
			dataResponse.setCode(StatusCode.OK.getCode());
			dataResponse.setData(reply);

			return new ResponseEntity<DataResponse>(dataResponse, headers, HttpStatus.OK);
		} else {
			dataResponse.setStatus(StatusCode.NOT_FOUND.getStatus());
			dataResponse.setCode(StatusCode.NOT_FOUND.getCode());

			return new ResponseEntity<DataResponse>(dataResponse, headers, HttpStatus.NOT_FOUND);
		}

	}

	// 댓글 수정
	@PutMapping("/update-reply.act")
	public ResponseEntity<DataResponse> updateReply(@RequestBody Map<String, String> params) {
		DataResponse dataResponse = new DataResponse();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		Reply reply = boardService.findByReplyCode(Long.parseLong(params.get("replyCode")));
		User user = reply.getUser();

		reply.setReplyContent(params.get("boardTitle"));

		if (Long.parseLong(params.get("userCode")) == user.getUserCode()) {
			dataResponse.setStatus(StatusCode.OK.getStatus());
			dataResponse.setCode(StatusCode.OK.getCode());
			dataResponse.setData(boardService.updateReply(reply));

			return new ResponseEntity<DataResponse>(dataResponse, headers, HttpStatus.OK);
		} else {
			ErrorResponse.CustomFieldError customFieldError = new ErrorResponse.CustomFieldError("Reply",
					params.get("userCode"), "작성자만 댓글을 수정할 수 있습니다.");
			throw new UpdateByUnauthUserException(customFieldError);
		}
	}

	// 댓글 삭제
	@DeleteMapping("/delete-reply.act")
	public ResponseEntity<DataResponse> deleteReply(@RequestBody Map<String, Long> params) {
		DataResponse dataResponse = new DataResponse();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		Reply reply = boardService.findByReplyCode(params.get("replyCode"));
		User user = reply.getUser();

		if (params.get("userCode") == user.getUserCode()) {
			boardService.deleteReply(params.get("replyCode"));
			dataResponse.setStatus(StatusCode.OK.getStatus());
			dataResponse.setCode(StatusCode.OK.getCode());

			return new ResponseEntity<DataResponse>(dataResponse, headers, HttpStatus.OK);
		} else {
			ErrorResponse.CustomFieldError customFieldError = new ErrorResponse.CustomFieldError("Reply",
					String.valueOf(params.get("userCode")), "작성자만 댓글을 삭제할 수 있습니다.");
			throw new DeleteByUnauthUserException(customFieldError);
		}
	}
}