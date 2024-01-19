package controllerM;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MemberService;

@WebServlet("/mdetail")
public class C01_mDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public C01_mDetail() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberService sc = new MemberService();
		String uri="/member/mDetail.jsp";
		request.setAttribute("mDetail", 
				sc.selectOne((String)request.getSession().getAttribute("mId")));
		if (request.getParameter("code") != null) {
			switch (request.getParameter("code")) {
			case "U":
				uri="/member/updateForm.jsp";
			}
		}
			request.getRequestDispatcher(uri).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
