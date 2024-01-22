package controllerF;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MemberService;

public class Ex05_mDetail implements Ex04_Controller{

	@Override
	public String doUser(HttpServletRequest request, HttpServletResponse response) {
		MemberService sc = new MemberService();
		request.setAttribute("mDetail", sc.selectOne("simsim916"));
		
		return "member/mDetail.jsp";
	}
	
}
