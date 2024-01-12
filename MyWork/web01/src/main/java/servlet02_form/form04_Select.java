package servlet02_form;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/select")
public class form04_Select extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public form04_Select() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String job = request.getParameter("job");
		String[] interest = request.getParameterValues("interest");
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		if(job!=null && job.length()>0) {
			out.printf("<h3> ** 당신의 직업은 %s 입니다</h3>",job);
		}else {
			out.print("<h3>직업을 선택하지 않았습니다</h3>");
		}
		
		if(interest!=null && interest.length>0) {
			out.printf("<h3> ** 당신의 관심 분야는");
			for(String s : interest) {
				out.print(s);
			}
			out.print(" 입니다</h3>");
		}else {
			out.print("<h3>관심분야를 선택하지 않았습니다</h3>");
		}
		out.print("<br><br><h2><a href='javascript:history.go(-1)'>다시 입력하기</a></h2><br>");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
