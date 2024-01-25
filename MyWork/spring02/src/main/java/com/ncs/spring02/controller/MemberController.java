package com.ncs.spring02.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ncs.spring02.domain.MemberDTO;
import com.ncs.spring02.service.MemberService;

@Controller
@RequestMapping(value = "member")
public class MemberController {
	@Autowired(required = false)
	MemberService service;

	// Member Login
//	@RequestMapping(value = {"/loginForm"}, method = RequestMethod.GET)
//	public String loginForm( Model model ) {
//		return "member/loginForm";
//	};
	// return void
	// -> viewName 생략 : 요청명과 동일한 viewName을 찾는다
	// /WEB-INF/views/ + value + .jsp 완성됨
	@RequestMapping(value = { "/loginForm" }, method = RequestMethod.GET)
	public void loginForm() {
	};

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpSession session, Model model, MemberDTO dto) {
		// 매핑메서드의 인자객체와 동일한 컬럼명의 값은 자동으로 할당
		// -> 아래의 구문은 필요없음
//		String id = request.getParameter("id");
//		String password = request.getParameter("password");

		String password = dto.getPassword();
		String uri = "redirect:/home";

		dto = service.selectOne(dto.getId());
		if (dto != null && dto.getPassword().equals(password)) {
			session.setAttribute("loginId", dto.getId());
			session.setAttribute("loginName", dto.getName());
		} else {
			uri = "member/loginForm";
			model.addAttribute("message", "로그인 실패 다시 로그인 하세요");
		}

		return uri;
	};

	// Member Logout
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	// Member JoinForm
	@RequestMapping(value = { "/joinForm" }, method = RequestMethod.GET)
	public String joinForm() {
		return "member/joinForm";
	};

	// Member Join
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	private String join(Model model, MemberDTO dto) {
		String uri = "member/loginForm";

		if (service.insert(dto) > 0) {
			model.addAttribute("message", "회원가입완료! 로그인 해주세요");
		} else {
			uri="member/joinForm";
			model.addAttribute("message", "회원가입실패! 다시 입력 해주세요");
		}

		return uri;
	}

	// Member List
	@RequestMapping(value = { "/memberList" }, method = RequestMethod.GET)
	public void mList(Model model) {
		model.addAttribute("mList", service.selectList());
	};

	// Member Detail
	// => 단일 Parameter 의 경우 @RequestParam("...") 활용
	// String jCode = request.getParameter("jCode") 과 동일
	// 단, 해당하는 Parameter 가 없으면 400 오류 발생
	// 그러므로 detail 요청에도 ?jCode=D 를 추가함.
	@RequestMapping(value = { "/detail" }, method = RequestMethod.GET)
	public String handleRequest(HttpSession session, Model model, @RequestParam("jCode") String jCode) {
		String uri = "member/detail";
		String id = (String) session.getAttribute("loginId");
		if ("U".equals(jCode)) {
			uri = "member/update";
		}

		model.addAttribute("mDetail", service.selectOne(id));
		return uri;
	};
	
	// Member Update
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	private String update(HttpSession session, Model model, MemberDTO dto) {
		String uri = "member/detail";

		if (service.update(dto) > 0) {
			model.addAttribute("mDetail", dto);
			model.addAttribute("message", "정보수정완료!");
			session.setAttribute("loginName", dto.getName());
		} else {
			uri="member/detail";
			model.addAttribute("mDetail", service.selectOne(dto.getId()));
			model.addAttribute("message", "정보수정실패! 다시 입력 해주세요");
		}

		return uri;
	}
	// Member Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	private String delete(HttpSession session, Model model) {
		
		String uri = "redirect:/";
		
		if (service.delete((String)session.getAttribute("loginID")) > 0) {
			model.addAttribute("message", "정보삭제완료!");
			session.invalidate();
		} else {
			model.addAttribute("message", "정보삭제실패! 다시 입력 해주세요");
		}
		
		return uri;
	}

}
