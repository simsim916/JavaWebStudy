package controllerM;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.MemberDTO;
import service.MemberService;

@WebServlet("/mupdate")
public class C05_mUpdate extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public C05_mUpdate() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      String uri = "/mdetail";
      request.setCharacterEncoding("utf-8");
      // 1 요청분석
      // request의 Parameter처리
      // 성공 -> mdetail 로 (mDetail.jsp)
      // 실패 -> 재수정 유도 (updateForm.jsp)
      // -> redirect 또는 전달된 값들을 apple 에 저장
      MemberDTO dto = new MemberDTO();
      dto.setId(request.getParameter("id"));
      dto.setPassword(request.getParameter("password"));
      dto.setName(request.getParameter("name"));
      dto.setAge(Integer.parseInt(request.getParameter("age")));
      dto.setJno(Integer.parseInt(request.getParameter("jno")));
      dto.setInfo(request.getParameter("info"));
      dto.setPoint(Double.parseDouble(request.getParameter("point")));
      dto.setBirthday(request.getParameter("birthday"));
      dto.setRid(request.getParameter("rid"));

      // 2 Service처리
      // Service 객체 생성
      MemberService service = new MemberService();
      if (service.update(dto) > 0) {
         // 성공
    	 request.getSession().setAttribute("mName", dto.getName());
         request.setAttribute("loginMessage", " 수정 완료 ");
      } else {
         // 실패
         request.setAttribute("message", " 수정실패 ");
         uri = "/web02/mdetail?code=U";
      }

      // 3 View 처리
      response.sendRedirect(uri);
   } // doGet

   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      doGet(request, response);
   } // doPost

} // class