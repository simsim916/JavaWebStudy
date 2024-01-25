package spDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import service.MemberService;

public class C02_mDetail implements Controller{
	@Autowired
	MemberService sc;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("mDetail", sc.selectOne("simsim916"));
		mv.setViewName("member/mDetail");
		return mv;
	};
}
