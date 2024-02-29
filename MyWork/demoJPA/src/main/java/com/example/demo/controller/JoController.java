package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.JoDTO;
import com.example.demo.entity.Jo;
import com.example.demo.service.JoService;
import com.example.demo.service.MemberService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping(value = "/jo")
// 모든 맴버변수를 초기화하는 생성자를 자동추가, 사용
// 그러므로 @Autowired는 생략 가능
@AllArgsConstructor
public class JoController {
//	@Autowired(required = false)
	JoService service;
//	@Autowired(required = false)
	MemberService serviceM;

	// joList
	@GetMapping("/joList")
	public void jList(Model model) {
		model.addAttribute("banana", service.selectJoList());
		
	} // joList

	// joDetail
	@GetMapping("/jodetail")
	public String jDetail(Model model, @RequestParam("jno") String jno, @RequestParam("jCode") String jCode) {
		String uri = "/jo/joDetail";
		model.addAttribute("apple", service.selectJoDetail(jno));

		if ("U".equals(jCode)) {
			uri = "jo/joUpdate";
		}

		if ("D".equals(jCode)) {
			model.addAttribute("banana", serviceM.findByJno(jno));
		}
		return uri;
	} // joDetail

	// insertForm
	@GetMapping("/joInsert")
	public void joinForm() {
	} // insertForm

	// JoInsert
	@GetMapping("/insert")
	public String join(Model model, Jo entity, RedirectAttributes rttr) {
		// 1 요청 분석
		// 이전 : 한글처리, request 값 -> dto에 set
		// 스프링 : 한글은 filter, request 처리는 parameter
		String uri = "redirect:joList";

		// 2 Service
		try {
			service.save(entity);
			model.addAttribute("banana", service.selectJoList());
			rttr.addFlashAttribute("message", " 조 추가 성공 ");
		} catch (Exception e) {
			uri = "jo/joInsert";
			rttr.addFlashAttribute("message", " 조 추가 실패 ");
		}
		
		return uri;
	} // JoInsert

	// joUpdate
	@GetMapping("/update")
	public String update(Model model, Jo entity) {
		// 1 요청 분석entity
		// 성공 : memberDetail
		// 실패 : updateForm
		// 출력하려면 dto 객체의 값("apple")이 필요하므로 보관
		String uri = "jo/joDetail";
		model.addAttribute("apple", entity);

		
		// 2 Service
		try {
			service.save(entity);
			model.addAttribute("message", " 조 정보수정 성공 ");
		} catch (Exception e) {
			uri = "jo/joUpdate";
			model.addAttribute("message", " 조 정보수정 실패 ");
		}
		return uri;
	} // joUpdate

	// joDelete
	@GetMapping("/joDelete")
	public String datail(@RequestParam("jno") String jno, Model model, RedirectAttributes rttr) {
		String uri = "redirect:joList";

		// 2 Service 처리
		try {
			service.delete(jno);
			model.addAttribute("banana", service.selectJoList());
			rttr.addFlashAttribute("message", " 삭제 성공 ");
		} catch (Exception e) {
			rttr.addFlashAttribute("message", " 삭제 실패 ");
		}
		return uri;
	} // joDelete

} // class
