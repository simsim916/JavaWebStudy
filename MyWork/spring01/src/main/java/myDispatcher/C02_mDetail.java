package myDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MemberService;

public class C02_mDetail implements MyController{

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		MemberService sc = new MemberService();
		request.setAttribute("mDetail", sc.selectOne("simsim916"));
		return "member/mDetail";
	};
}
