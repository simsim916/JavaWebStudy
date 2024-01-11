package servlet01;

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

@WebServlet("/list")
public class Ex02_MVC01List extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Ex02_MVC01List() {
		super();
	}

	// ** MVC 패턴1 - StudentList 출력하기
	// => 요청 Service 처리
	// => 결과 출력

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 요청 service 처리
		StudentService sc = new StudentService();
		List<StudentDTO> list = sc.selectList();
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		// 결과 출력 : 출력내용을 Response 객체의 Body 영역에 Write
		// - 한글처리
		// - 출력객체 생성 & 출력

		out.printf("<html><body>");
		out.printf("<h2> ** Servlet_MVC StudentList ** </h2>");

		if (list != null) {

			out.printf("<table><thead><tr>");
			out.printf("<th>학생번호</th><th>이름</th><th>나이</th><th>조번호</th><th>정보</th><th>포인트</th>");
			out.printf("</tr></thead><tbody>");

			for (int i = 0; i < list.size(); i++) {
				out.printf("<tr>");
				out.printf("<td>%d</td>", list.get(i).getSno());
				out.printf("<td>%s</td>", list.get(i).getName());
				out.printf("<td>%d</td>", list.get(i).getAge());
				out.printf("<td>%d</td>", list.get(i).getJno());
				out.printf("<td>%s</td>", list.get(i).getInfo());
				out.printf("<td>%.2f</td>", list.get(i).getPoint());
				out.printf("</tr>");
			}
			out.printf("</tbody></table>");
		}
		out.printf("</body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
