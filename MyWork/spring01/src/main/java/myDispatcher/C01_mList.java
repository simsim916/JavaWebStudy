package myDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MemberService;

public class C01_mList implements MyController{

	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		MemberService sc = new MemberService();
		request.setAttribute("mList", sc.selectList());
		return "member/list";
	};
	
}
