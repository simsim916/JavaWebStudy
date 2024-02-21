package com.ncs.spring02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ncs.spring02.domain.JoDTO;
import com.ncs.spring02.service.JoService;
import com.ncs.spring02.service.MemberService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping(value = "/jo")
@AllArgsConstructor 
// -> 모든 맴버변수를 초기화하는 생성자 자동 추가 & 사용 (Autowired 생략가능)
public class JoController {
//	@Autowired(required = false)
	JoService service;
//	@Autowired
	MemberService service2;

	// joList
	@RequestMapping(value = "/joList", method = RequestMethod.GET)
	public void jList(Model model) {
		model.addAttribute("banana", service.selectList());
	} // joList

	// joDetail
	@RequestMapping(value = "/joDetail", method = RequestMethod.GET)
	public String jDetail(Model model, @RequestParam("jno") String jno, @RequestParam("jCode") String jCode) {
		String uri = "/jo/joDetail";

		if ("U".equals(jCode)) {
			uri = "jo/joUpdate";
		}

		model.addAttribute("apple", service.selectOne(jno));
		model.addAttribute("mList", service2.selectList(jno));
		return uri;
	} // joDetail

	// insertForm
	@RequestMapping(value = { "/joInsert" }, method = RequestMethod.GET)
	public void joinForm() {
	} // insertForm

	// JoInsert
	@RequestMapping(value = { "/insert" }, method = RequestMethod.GET)
	public String join(Model model, JoDTO dto) {
		// 1 요청 분석
		// 이전 : 한글처리, request 값 -> dto에 set
		// 스프링 : 한글은 filter, request 처리는 parameter
		String uri = "/jo/joList";

		// 2 Service
		if (service.insert(dto) > 0) {
			model.addAttribute("banana", service.selectList());
			model.addAttribute("message", " 조 추가 성공 ");
		} else {
			uri = "jo/joInsert";
			model.addAttribute("message", " 조 추가 실패 ");
		}
		return uri;
	} // JoInsert

	// joUpdate
	@RequestMapping(value = { "/update" }, method = RequestMethod.GET)
	public String update(Model model, JoDTO dto) {
		// 1 요청 분석
		// 성공 : memberDetail
		// 실패 : updateForm
		// 출력하려면 dto 객체의 값("apple")이 필요하므로 보관
		String uri = "jo/joDetail";
		model.addAttribute("apple", dto);

		// 2 Service
		if (service.update(dto) > 0) {
			model.addAttribute("message", " 조 정보수정 성공 ");
		} else {
			uri = "jo/joUpdate";
			model.addAttribute("message", " 조 정보수정 실패 ");
		}
		return uri;
	} // joUpdate

	// joDelete
	@RequestMapping(value = { "/joDelete" }, method = RequestMethod.GET)
	public String datail(@RequestParam("jno") String jno, Model model, RedirectAttributes rttr) {
		String uri = "/jo/joList";

		// 2 Service 처리
		if (service.delete(jno) > 0) {
			model.addAttribute("banana", service.selectList());
			model.addAttribute("message", " 삭제 성공 ");
			rttr.addFlashAttribute("message", " 삭제 성공 ");
		} else {
			model.addAttribute("message", " 삭제 실패 ");
			rttr.addFlashAttribute("message", " 삭제 실패 ");
		}
		return uri;
	} // joDelete

} // class
