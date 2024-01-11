package servlet02_form;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/check")
public class form03_Check extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public form03_Check() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// => CheckBox 처리
	    //   -> 하나의 Name 에 복수개의 Value 들이 있음
	    //   -> request.getParameterValues("gift") 를 이용해서 배열로 처리    
		String[] gift = request.getParameterValues("gift");
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.print("<h3>당신이 원하는건<h3>");
		out.print("<h4>");
		for (String s : gift) {
			out.print(s);
		}
		out.print(" 입니다. </h4>");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
