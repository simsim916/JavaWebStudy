package servlet03_flow;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;

import mvcTest.StudentDTO;
import mvcTest.StudentService;

@WebServlet("/login")
public class Ex04_Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Ex04_Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 요청분석 -> 한글, request의 parameter
		request.setCharacterEncoding("utf-8");
		System.out.println("로그인 시도");
		String uri="home.jsp";
		try {
			int sno = 0;
			if ( request.getParameter("sno")!=null && request.getParameter("sno").length()>0){
				sno = Integer.parseInt(request.getParameter("sno"));
			}
					
			String name = request.getParameter("name");
			
			// 2. service
			// Service 의 selectOne : sno 확인 
			// 확인결과 성공 -> name 확인
			StudentService sc = new StudentService();
			StudentDTO dto=sc.selectOne(sno);
			System.out.println("로그인시도");
			if(name.equals(dto.getName())) {
				HttpSession session = request.getSession();
				session.setAttribute("StudentDTO", dto);
				System.out.println("** 로그인 성공 **");
				System.out.println("** 로그인 student => "+dto);
			}else {
				uri="servletTestForm/flowEx04_LoginForm.jsp";
				request.setAttribute("message", "로그인에 실패하였습니다.");
				System.out.println("로그인실패");
			}
			
			
		} catch (NullPointerException e) {
			
		} finally {
			
			// 3. View (Response) : forward
			// 성공 : index.html 
			// 실패 : LoginForm.jsp (재로그인)
			request.getRequestDispatcher(uri).forward(request,response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
