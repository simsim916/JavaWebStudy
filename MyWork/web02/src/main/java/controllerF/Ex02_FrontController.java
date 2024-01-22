package controllerF; 

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//** FrontController 패턴 2.
//=> Factory 패턴 적용
// - ServiceFactory
// - 개별컨트롤러(일반클래스) : 일관성을 위해 강제성 부여 ( interface 사용 )

@WebServlet(urlPatterns = {"*.fo"})
public class Ex02_FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Ex02_FrontController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) 요청분석
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		uri=uri.substring(uri.lastIndexOf("/"));
		
		System.out.printf("** URL => %s\n", request.getRequestURL());
		System.out.printf("** URI => %s\n", request.getRequestURI());
		System.out.printf("** uri => %s\n", uri);
		
		// 2) Service 실행
		// => Service Factory에 요청
		// => uri 를 전달하면 해당 서비스컨트롤러 를 생성해서 인스턴스를 제공
		// => ServiceFactory 생성 불가능 -> getInstance로 불러오기
		
		Ex03_ServiceFactiory sf = Ex03_ServiceFactiory.getInstance();
		System.out.println(sf);
		System.out.println(uri);
		Ex04_Controller controller = sf.getController(uri);
		System.out.println(controller);
		uri=controller.doUser(request, response);
		// 3) View 처리
		request.getRequestDispatcher(uri).forward(request, response);
	}

}
