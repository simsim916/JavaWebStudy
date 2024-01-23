package spDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import service.MemberService;

public class C01_mList implements Controller{

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
		MemberService sc = new MemberService();
		ModelAndView mv = new ModelAndView();
		mv.addObject("mList", sc.selectList());
		mv.setViewName("member/list");
		return mv;
	};
	
}
