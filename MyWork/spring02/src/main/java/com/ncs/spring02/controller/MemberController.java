package com.ncs.spring02.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ncs.spring02.domain.MemberDTO;
import com.ncs.spring02.service.MemberService;

//** Spring 의 redirect ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

//** RedirectAttributes
//=> Redirect 할 때 파라메터를 쉽게 전달할 수 있도록 지원하며,
// addAttribute, addFlashAttribute, getFlashAttribute 등의 메서드가 제공됨.
//=> addAttribute
// - url에 퀴리스트링으로 파라메터가 붙어 전달됨. 
// - 그렇기 때문에 전달된 페이지에서 파라메터가 노출됨.

//=> addFlashAttribute
// - Redirect 동작이 수행되기 전에 Session에 값이 저장되고 전달 후 소멸된다.
// - Session을 선언해서 집어넣고 사용 후 지워주는 수고를 덜어주고,
//    -> url에 퀴리스트링으로 붙지 않기때문에 깨끗하고 f5(새로고침)에 영향을 주지않음.  
//    -> 주의사항 
//       받는쪽 매핑메서드의 매개변수로 parameter를 전달받는 VO가 정의되어 있으면
//       이 VO 생성과 관련된 500 발생 하므로 주의한다.
//      ( Test : JoController 의 jupdate 성공시 redirect:jdetail )
//      단, VO로 받지 않는 경우에는 url에 붙여 전달하면서 addFlashAttribute 사용가능함        

//=> getFlashAttribute
//    - insert 성공 후 redirect:jlist 에서 Test (JoController, 결과는 null)
//    - 컨트롤러에서 addFlashAttribute 가 session에 보관한 값을 꺼내는것은 좀더 확인이 필요함 

//** redirect 로 한글 parameter 전달시 한글깨짐
//=> 한글깨짐이 발생하는경우 사용함.
//=> url 파라메터 로 전달되는 한글값 을 위한 encoding
//    - String message = URLEncoder.encode("~~ member 가 없네용 ~~", "UTF-8");
//      mv.setViewName("redirect:mlist?message="+message);  
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

//** Model & ModelAndView **

//=> Model(interface)
//-> controller처리 후 데이터(Model) 을 담아서 반환 
//-> 구현클래스 : ConcurrentModel, ExtendedModelMap 등.
//-> 아래의 매핑 메서드들 처럼, ModelAndView 보다 심플한 코드작성 가능하므로 많이사용됨. 
// mv.setViewName("~~~~~") 하지않고 viewName 을 return 

//=> ModelAndView (class)
//-> controller처리 후 데이터(Model) 와 viewName 을 담아서 반환
//-> Object -> ModelAndView
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

//** @RequestMapping
//=> DefaultAnnotationHandlerMapping에서 컨트롤러를 선택할 때 대표적으로 사용하는 애노테이션. 
//=> DefaultAnnotationHandlerMapping은 클래스와 메서드에 붙은 @RequestMapping 애노테이션 정보를 결합해 최종 매핑정보를 생성한다.
//=> 기본적인 결합 방법은 클래스 레벨의 @RequestMapping을 기준으로 삼고, 
//  메서드 레벨의 @RequestMapping으로 세분화하는 방식으로 사용된다.

//** @RequestMapping 특징
//=> url 당 하나의 컨트롤러에 매핑되던 다른 핸들러 매핑과 달리 메서드 단위까지 세분화하여 적용할 수 있으며,
// url 뿐 아니라 파라미터, 헤더 등 더욱 넓은 범위를 적용할 수 있다. 
//=> 요청과 매핑메서드 1:1 mapping 
//=> value="/mlist" 
// : 이때 호출되는 메서드명과 동일하면 value 생략가능 그러나 value 생략시 404 (확인필요함)
// : 해당 메서드 내에서 mv.setViewName("...."); 을 생략 
//  또는 아래의 메서드를 사용하는 경우에는 void 로 작성 (view 를 return 하지않음) 하는 경우
//   요청명을 viewName 으로 인식 즉, mv.setViewName("mlist") 으로 처리함.
//  또는 return "mlist" ( 즉, mlist.jsp 를 viewName으로 인식 )

//** @RequestMapping 속성
//=> value : URL 패턴 ( 와일드카드 * 사용 가능 )
//  @RequestMapping(value="/post")
//  @RequestMapping(value="/post.*")
//  @RequestMapping(value="/post/**/comment")
//  @RequestMapping(value={"/post", "/P"}) : 다중매핑 가능

//=> method 
// @RequestMapping(value="/post", method=RequestMethod.GET)
// -> url이 /post인 요청 중 GET 메서드인 경우 호출됨
// @RequestMapping(value="/post", method=RequestMethod.POST)
// -> url이 /post인 요청 중 POST 메서드인 경우 호출됨
//    GET, POST, PUT, DELETE, OPTIONS, TRACE 총 7개의 HTTP 메서드가 정의되어 있음.
//    ( 이들은 아래 @GetMapping ... 등으로도 좀더 간편하게 사용가능
//      그러나 이들은 메서드 레벨에만 적용가능    )  

//=> params : 요청 파라미터와 값으로도 구분 가능함.
// @RequestMapping(value="/post", params="useYn=Y")
// -> /post?useYn=Y 일 경우 호출됨
// @RequestMapping(value="/post", params="useYn!=Y")
// ->  not equal도 가능
// @RequestMapping(value="/post", parmas="useYn")
// > 값에 상관없이 파라미터에 useYn이 있을 경우 호출됨
// @RequestMapping(value="/post", params="!useYn")
// > 파라미터에 useYn이 없어야 호출됨
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

//** Lombok 지원 로그메시지  
//=> @Log4j Test
// -> dependency 필요함 (pom.xml 확인)
// -> 로깅레벨 단계 준수함 ( log4j.xml 의 아래 logger Tag 의 level 확인)
//    TRACE > DEBUG > INFO > WARN > ERROR > FATAL(치명적인)
//    <logger name="com.ncs.green">
//       <level value="info" />
//    </logger>   

// -> Logger 사용과의 차이점 : "{}" 지원안됨 , 호출명 log
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

@Controller
@RequestMapping(value = "member")
public class MemberController {
	@Autowired(required = false)
	MemberService service;

	// Member Login
//	@RequestMapping(value = {"/loginForm"}, method = RequestMethod.GET)
//	public String loginForm( Model model ) {
//		return "member/loginForm";
//	};
	// return void
	// -> viewName 생략 : 요청명과 동일한 viewName을 찾는다
	// /WEB-INF/views/ + value + .jsp 완성됨
	@RequestMapping(value = { "/loginForm" }, method = RequestMethod.GET)
	public void loginForm() {
	};

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpSession session, Model model, MemberDTO dto) {
		// 매핑메서드의 인자객체와 동일한 컬럼명의 값은 자동으로 할당
		// -> 아래의 구문은 필요없음
//		String id = request.getParameter("id");
//		String password = request.getParameter("password");

		String password = dto.getPassword();
		String uri = "redirect:/home";

		dto = service.selectOne(dto.getId());
		if (dto != null && dto.getPassword().equals(password)) {
			session.setAttribute("loginId", dto.getId());
			session.setAttribute("loginName", dto.getName());
		} else {
			uri = "member/loginForm";
			model.addAttribute("message", "로그인 실패 다시 로그인 하세요");
		}

		return uri;
	};

	// Member Logout
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	// Member JoinForm
	@RequestMapping(value = { "/joinForm" }, method = RequestMethod.GET)
	public String joinForm() {
		return "member/joinForm";
	};

	// Member Join
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	private String join(Model model, MemberDTO dto) {
		String uri = "member/loginForm";

		if (service.insert(dto) > 0) {
			model.addAttribute("message", "회원가입완료! 로그인 해주세요");
		} else {
			uri="member/joinForm";
			model.addAttribute("message", "회원가입실패! 다시 입력 해주세요");
		}

		return uri;
	}

	// Member List
	@RequestMapping(value = { "/memberList" }, method = RequestMethod.GET)
	public void mList(Model model) {
		model.addAttribute("mList", service.selectList());
	};

	// Member Detail
	// => 단일 Parameter 의 경우 @RequestParam("...") 활용
	// String jCode = request.getParameter("jCode") 과 동일
	// 단, 해당하는 Parameter 가 없으면 400 오류 발생
	// 그러므로 detail 요청에도 ?jCode=D 를 추가함.
	@RequestMapping(value = { "/detail" }, method = RequestMethod.GET)
	public String handleRequest(HttpSession session, Model model, @RequestParam("jCode") String jCode) {
		String uri = "member/detail";
		String id = (String) session.getAttribute("loginId");
		if ("U".equals(jCode)) {
			uri = "member/update";
		}

		model.addAttribute("mDetail", service.selectOne(id));
		return uri;
	};
	
	// Member Update
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	private String update(HttpSession session, Model model, MemberDTO dto) {
		String uri = "member/detail";

		if (service.update(dto) > 0) {
			model.addAttribute("mDetail", dto);
			model.addAttribute("message", "정보수정완료!");
			session.setAttribute("loginName", dto.getName());
		} else {
			uri="member/detail";
			model.addAttribute("mDetail", service.selectOne(dto.getId()));
			model.addAttribute("message", "정보수정실패! 다시 입력 해주세요");
		}

		return uri;
	}
	// Member Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	private String delete(HttpSession session, Model model, RedirectAttributes rttr) {
		
		String uri = "redirect:/home";
		if (service.delete((String)session.getAttribute("loginId")) > 0) {
//			model.addAttribute("message", "정보삭제완료!");
			// => requestScope 의 message 를 redirect 시에도 유지하려면
			//		session 에 보관했다가 사용 후에는 삭제해야함
			// 	session 에 보관 후 redirect 되어진 요청 처리시에 requestScope에 옮기고
			//    	session 의 message 는 삭제
			// -> 이것을 처리해주는 API RedirectAttributes
			rttr.addFlashAttribute("message", "정보삭제완료!");
			session.invalidate();
		} else {
			rttr.addFlashAttribute("message", "정보삭제실패! 다시 입력 해주세요");
		}
		
		return uri;
	}

}
