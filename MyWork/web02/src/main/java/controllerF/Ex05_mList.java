package controllerF;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MemberService;

public class Ex05_mList implements Ex04_Controller {

	@Override
	public String doUser(HttpServletRequest request, HttpServletResponse response) {
		MemberService sc = new MemberService();
		request.setAttribute("mList", sc.selectList());
		
		return "member/list.jsp";
	}

}
