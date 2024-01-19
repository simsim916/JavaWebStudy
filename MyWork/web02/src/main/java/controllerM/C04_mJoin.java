package controllerM;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.MemberDTO;
import service.MemberService;

@WebServlet("/mjoin")
public class C04_mJoin extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public C04_mJoin() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // 1 요청분석
      // request의 Parameter처리
      // 성공 -> 로그인 유도 loginForm.jsp
      // 실패 -> 재가입 유도 joinForm.jsp
      String uri = "member/loginForm.jsp";
      request.setCharacterEncoding("utf-8");

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
      if (service.insert(dto) > 0) {
         // 성공
         request.setAttribute("loginMessage", " 가입을 축하합니다 ");
      } else {
         // 실패
         request.setAttribute("message", " 가입을 실패했습니다 ");
         uri = "member/joinForm.jsp";
      }

      // 3 View 처리
      request.getRequestDispatcher(uri).forward(request, response);
   } // doGet

   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      doGet(request, response);
   } // doPost

} // class