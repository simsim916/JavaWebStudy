package servlet01;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvcTest.StudentService;
import mvcTest.StudentDTO;

//** Get
//- request 의 header 영역의 url 뒤에 쿼리스트링으로 전달,
//- 일반적으로 256 byte 이내로 크기제한이 있는 것으로 알려져 있으나,
//	이 용량은 브라우져 또는 장비에 따라 다를수 있음
//- 결론은 이미지 등 무거운 자료의 전송은 대부분 불가능 하므로 이때는 post로 해야함. 

//** Post
//- request 의 body 영역에 담겨져 전달됨
//- 크기제한 없음, 보안성 유리 

@WebServlet("/getpost")
public class Ex03_GetPost extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Ex03_GetPost() {
		super();
	}

	// ** MVC 패턴1 - StudentList 출력하기
	// => 요청 Service 처리
	// => 결과 출력

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1) request 의 Parameter 처리
		// => 한글처리, getParameter 처리하기 전에
	    //   - Tomcat(WAS) 은 Get 방식요청에서는 "UTF-8" 을 default 로 적용함 
	    //   ( html 문서에서 "UTF-8" 작성되었고 , Get 방식으로 전송되면 생략가능
	    //     단, post 방식에서는 반드시 처리해야함 )
		request.setCharacterEncoding("utf-8");
		
		
		
		// input 태그의 name이 id인 value값을 return 
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		//  해당하는 Parameter 가 없는 경우 null을 return
		//  Parameter는 존재하지만 값이 없는 경우 : null 이 아닌 공백
		String password = request.getParameter("password");
		
		// 요청 service 처리
		StudentService sc = new StudentService();
		List<StudentDTO> list = sc.selectList();

		// 결과 출력 : 출력내용을 Response 객체의 Body 영역에 Write
		// - 한글처리
		// - 출력객체 생성 & 출력
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		out.printf("<html><body>");
		out.printf("<h2> ** Get/Post Test ** </h2>");
		out.printf("<h3> ** id = %s ** </h2>",id);
		out.printf("<h3> ** name = %s ** </h2>",name);
		if (password!=null && password.length()>0) {
			out.printf("<h3> ** password = %s ** </h2>",password);
		} else {
			out.printf("<h3> ** password = Null or Empty ** </h2>",password);
		}

		out.printf("</body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
