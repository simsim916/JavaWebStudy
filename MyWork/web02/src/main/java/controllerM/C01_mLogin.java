package controllerM;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.MemberDTO;
import service.MemberService;

@WebServlet("/login")
public class C01_mLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public C01_mLogin() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		MemberService sc = new MemberService();
		MemberDTO dto = sc.selectOne(id);
		if (dto != null && dto.getPassword().equals(password)) {
			request.getSession().setAttribute("mId", dto.getId());
			request.getSession().setAttribute("mName", dto.getName());
			request.getRequestDispatcher("home.jsp").forward(request, response);
		}
		request.setAttribute("loginMessage", "로그인에 실패하셨습니다. 다시 시도해주세요.");
		response.sendRedirect("member/loginForm.jsp");
	}

}
