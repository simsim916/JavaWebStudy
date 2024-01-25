package com.ncs.spring01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import service.MemberService;

@Controller
public class MemberController {
	@Autowired(required = false)
	MemberService service;
	
	// Member List
	@RequestMapping(value = {"/mlistsp2", "/mlist"}, method = RequestMethod.GET)
	public String mList( Model model ) {
		model.addAttribute("mList", service.selectList());
		return "member/list";
	};
	
	// Member Detail
	@RequestMapping(value = {"/mdetailsp2", "/mdetail"}, method = RequestMethod.GET)
	public String handleRequest(Model model) {
		model.addAttribute("mDetail", service.selectOne("simsim916"));
		return "member/mDetail";
	};
}
