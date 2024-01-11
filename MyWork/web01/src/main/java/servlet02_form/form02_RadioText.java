package servlet02_form;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/radio")
public class form02_RadioText extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public form02_RadioText() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String gender = request.getParameter("gender");
		String content = request.getParameter("content");
		boolean mailcheck=false; 
		
		if(request.getParameter("mailcheck").equals("Yes")) {
			mailcheck=true;
		}
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.printf("<pre><h2>성별 : %s / 메일수신여부 : %s / 가입인사 : %s </h2><pre>",gender,mailcheck?"Yes":"No",content);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
