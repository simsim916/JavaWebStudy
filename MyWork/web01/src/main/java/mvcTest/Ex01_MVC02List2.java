package mvcTest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvcTest.StudentService;
import mvcTest.StudentDTO;

@WebServlet("/list22")
public class Ex01_MVC02List2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Ex01_MVC02List2() {
		super();
	}

	// ** MVC 패턴2 - StudentList 출력하기
	// => 요청 Service 처리
	// => 결과 출력 (Java 스크립트)

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 요청 service 처리
		StudentService sc = new StudentService();
		List<StudentDTO> list = sc.selectList();
		System.out.println("테스트1");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		// 결과 출력 : Jsp, Java 스크립트
		// => Service 결과물인 List을 Jsp 가 출력할 수 있도록 Attribute에 보관
		//		-> request에 setAttribut()
		
		request.setAttribute("stList", list);		
		// => Forward
		
		request.getRequestDispatcher("mvcTestJsp/ex02_MVC02List.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
