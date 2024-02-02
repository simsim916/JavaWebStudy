package com.ncs.spring02.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ncs.spring02.domain.BoardDTO;
import com.ncs.spring02.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	BoardService service;

	// List
	@GetMapping("/boardList")
	public void boardList(Model model) {
		model.addAttribute("list", service.selectList());
	}

	// Detail
	// => 글요청 처리중, 글을 읽기 전 상태
	// -> 조회수 증가 
	//  loginID와 board 의 id가 다른 경우
	@GetMapping("/detail")
	public String boardDetail(Model model, HttpSession session, @RequestParam("seq") int seq) {
		String uri = "board/boardList";
		
		if (session.getAttribute("loginId") == null) {
			model.addAttribute("message", "로그인 후 조회가능합니다.");
			model.addAttribute("list", service.selectList());
		} else {
			uri = "board/boardDetail";
			
			// => 조회수 증가 : selectOne 의 결과를 보관
			BoardDTO dto = service.selectOne(seq);
			if (!dto.getId().equals((String)session.getAttribute("loginId"))) {
				dto.setCnt(dto.getCnt()+1);
				service.update(dto);
			}
			
			model.addAttribute("board", service.selectOne(seq));
		}
		
		
		
		return uri;
	}

	// Insert
	@GetMapping("/boardInsert")
	public String boardInsert(Model model, HttpSession session) {
		String uri = "board/boardList";

		if (session.getAttribute("loginId") == null) {
			model.addAttribute("message", "로그인 후 글쓰기가능합니다.");
			model.addAttribute("list", service.selectList());
		} else {
			uri = "board/boardInsert";
		}
		return uri;
	}

	@GetMapping("/insert")
	public String insert(Model model, HttpSession session, BoardDTO dto) {
		String uri = "board/boardList";

		dto.setId((String) session.getAttribute("loginId"));
		if (service.insert(dto) > 0) {
			model.addAttribute("message", "게시글 등록 완료!");
			model.addAttribute("list", service.selectList());
		} else {
			model.addAttribute("message", "게시글 등록 실패 다시 확인해주세요!");
			uri = "board/boardInsert";
		}

		return uri;
	}

	// Update
	@GetMapping("/boardUpdate")
	public void boardUpdate(Model model, @RequestParam("seq") int seq) {
		model.addAttribute("board", service.selectOne(seq));
		model.addAttribute("seq", seq);
	}

	@GetMapping("/update")
	public String update(Model model, HttpSession session, BoardDTO dto, @RequestParam("seq") int seq) {
		String uri = "board/boardDetail";
		dto.setId((String) session.getAttribute("loginId"));
		if (service.update(dto) > 0) {
			model.addAttribute("message", "게시글 수정 완료!");
			model.addAttribute("board", service.selectOne(seq));
		} else {
			model.addAttribute("message", "게시글 등록 실패 다시 확인해주세요!");
			model.addAttribute("board", service.selectOne(seq));
			uri = "board/boardUpdate";
		}

		return uri;
	}

	// Delete
	@GetMapping("/delete")
	public String delete(Model model, @RequestParam("seq") int seq) {
		String uri = "board/boardList";
		if (service.delete(seq) > 0) {
			model.addAttribute("message", "게시글 삭제 완료!");
			model.addAttribute("list", service.selectList());
		} else {
			model.addAttribute("message", "게시글 삭제 실패 다시 확인해주세요!");
			model.addAttribute("board", service.selectOne(seq));
			uri = "board/boardDetail";
		}
		return uri;
	}
	
	// Reply Insert
	@GetMapping("/replyInsert")
	public void replyInsert(BoardDTO dto) {
		// => 답글처리를 위해 부모글의 root, step, indent 를 인자로 전달받으면,
		//		이 인자에 담겨진 값은 requestScope과 동일
	}
	
}
