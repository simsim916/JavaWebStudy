package com.ncs.spring02.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ncs.spring02.domain.MemberDTO;
import com.ncs.spring02.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

//IOC/DI 적용 ( @Component의 세분화 )
//스프링 프레임워크에서는 클래스들을 기능별로 분류하기위해 @을 추가함
//@Controller :사용자 요청을 제어하는 Controller 클래스
//	원래는 Controller로 인식시키기 위해서 Controller를 Interface로 구현해놨음
//	DispatcherServlet이 해당 객체를 Controller객체로 인식하게 해줌
//	 -> Interface Controller의 구현의무 없어짐
//		이로인해 메서드 handleRequest() 의 오버라이딩 의무가 없어짐
//		따라서 메서드이름, 매개변수, 리턴타입이 자유로워짐
//		 그러나 ModelAndView, String, void 타입만 사용 가능
//		그리고 메서드, 클래스 단위로 매핑해주는 @RequestMapping 사용가능
//		그러므로 하나의 컨트롤러 클래스에 여러개의 매핑메서드의 구현이 가능해짐
//		그래서 주로 DataBase의 테이블(Entity) 단위로 작성을 한다(MemberController.java)
//@Service : 비즈니스로직을 담당하는 Service 클래스
//@Repository : DB 연동을 담당하는 DAO 클래스
//	DB 연동과정에서 발생하는 예외를 변환 해주는 기능 추가

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
@Log4j
//DispatcherServlet이 해당 객체를 Controller객체로 인식하게 해줌
@Controller
@AllArgsConstructor
// member로 시작하는 요청들이 모두 여기로 오게함
@RequestMapping(value = "/member")
public class MemberController {
	
	MemberService service;
	PasswordEncoder passwordEncoder;
	// = new BCryptPasswordEncoder(); -> root--.xml에 bean을 등록

	@GetMapping("/log4jTest")
	public String log4jTest() {
		String name="simsim916";
		log.trace(service.selectOne(name));
		return "redirect:/home";
	}
	
	
	// ID 중복확인
	@GetMapping("/idDupCheck")
	public void idDupCheck(@RequestParam("id") String id, Model model) {
		// newID 존재여부 확인 & 결과처리
		if (service.selectOne(id) != null) {
			// 사용 불가능
			model.addAttribute("idUse", "F");
		} else {
			// 사용 가능
			model.addAttribute("idUse", "T");
		}

	} // idDupCheck

// Login Form 출력
// ver01 : return String
//	@RequestMapping(value = { "/loginForm" }, method = RequestMethod.GET)
//	public String loginForm(Model model) {
//		return "member/loginForm";
//	} // loginForm

// ver02 : return void
	// viewName이 생략됨 -> 요청명과 동일한 viewName을 자동으로 찾음
	// /WEB-INF/views/member/loginFom.jsp
	@RequestMapping(value = { "/loginForm" }, method = RequestMethod.GET)
	public void loginForm() {
	} // loginForm

	// login
	@PostMapping("/login")
	public String login(HttpSession session, Model model, MemberDTO dto) {
//		String id = request.getParameter("id");
//		dto.setID(id);
		// 맵핑 메서드에 인자로 정의되어있는 객체와
		// 동일한 컬럼명의 값은 자동으로 담아준다

		// 1 요청분석
		// => requst 로 전달되는 id, password 처리:
		// 매서드 매개변수로 MemberDTO 를 정의해주면 자동 처리
		// ( Parameter name 과 일치하는 setter 를 찾아 값을 할당해줌 )
		// => 전달된 password 보관
		String password = dto.getPassword();
		String uri = "redirect:/home"; // 성공시 uri

		// 2 서비스 & 결과 처리
		// id확인 -> 존재하면 Password 확인
		// 성공 : id, name은 session에 보관 후 home으로 이동
		// 실패 : 재로그인 유도
		dto = service.selectOne(dto.getId());
		// PasswordEncoder 적용
//		if (dto != null && dto.getPassword().equals(password)) {
		if (dto != null && passwordEncoder.matches(password, dto.getPassword())) {
			session.setAttribute("loginID", dto.getId());
			session.setAttribute("loginName", dto.getName());
		} else {
			uri = "memeber/loginForm";
			model.addAttribute("message", " 아이디, 비밀번호를 확인해주세요 ");
		} // if
		return uri;
	} // login

	// logout
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, Model model) {
		session.invalidate();
		return "redirect:/";
	} // logout

	// Member Detail
	// 단일 Parameter의 경우 @RequestParam("...")을 활용
	@RequestMapping(value = { "/detail" }, method = RequestMethod.GET)
	// String jCode = request.getParameter("jCode")와 동일
	// 단, 해당하는 Parameter가 없으면 400 오류 발생
	// 그러므로 detail 요청에도 ?jCode=D 를 추가
	public String datail(HttpSession session, Model model, @RequestParam("jCode") String jCode) {
		// 1 요청분석
		// id를 session에서 get
		String id = (String) session.getAttribute("loginID");
		String uri = "member/memberDetail";

		// update 요청 확인 후 uri 수정
		if ("U".equals(jCode)) {
			uri = "member/updateForm";
		}

		// 2 Service 처리
		model.addAttribute("apple", service.selectOne(id));
		return uri;
	} // detail

	// Member List
	@RequestMapping(value = { "/memberList" }, method = RequestMethod.GET)
	public void mList(Model model) {
		model.addAttribute("banana", service.selectList());
	} // mList

	// JoinForm
	@RequestMapping(value = { "/joinForm" }, method = RequestMethod.GET)
	public void joinForm() {
	} // JoinForm

	// Join
	@RequestMapping(value = { "/join" }, method = RequestMethod.POST)
	public String join(HttpServletRequest request, Model model, MemberDTO dto) throws IOException {
		// 1 요청 분석
		// 이전 : 한글처리, request 값 -> dto에 set
		// 스프링 : 한글은 filter, request 처리는 parameter
		String uri = "member/loginForm";

		// upload File 처리
		// 주요 과제
		// 전달된 파일 저장 : file1 (서버의 물리적 실제저장위치 필요)
		// 전달된 파일명을 Table에 저장 : file2
		// MultipartFile : 위의 과정을 지원해주는 전용 객체

		// 1) 물리적 실제 저장위치 확인
		// 1.1) 현재 웹어플리케이션의 실행 위치 확인
		// eclipse 개발환경(배포전) --.eclipse.-- 포함
		// 톰캣 서버 배포 후 : --.eclipse.-- 포함되어있지 않음
		String realPath = request.getRealPath("/");
		System.out.println("realPath => " + realPath);

		// 1.2) realPath를 이용해서 물리적 저장 위치(file1) 확인
		// 이클립스가 포함되어 있다면(개발중이라면)
		if (realPath.contains(".eclipse."))
			// 개발중, 내 로컬주소 경로
			realPath = "Z:\\JavaWebStudy\\MyWork\\spring02\\src\\main\\webapp\\resources\\uploadImages\\";
		// 배포 후, 웹의 주소 경로
		// realPath => C:\\MTest\\IDESet\\apache-tomcat-9.0.85\\webapps\\spring02\\
		else
			realPath = "Z:\\JavaWebStudy\\MTest\\IDESet\\apache-tomcat-9.0.85\\webapps\\spring02\\resources\\uploadImages\\";

		// 1.3 폴더 만들기(없을수도 있음을 가정, File)
		// File type 객체 생성 : new File("경로");
		// => file.exists()
		// -> 파일 또는 폴더가 존재하는지 리턴
		// -> 폴더가 아닌, 파일존재 확인하려면 file.isDirectory() 도 함께 체크해야함.
		// ( 참고: https://codechacha.com/ko/java-check-if-file-exists/ )
		// => file.isDirectory() : 폴더이면 true 그러므로 false 이면 file 이 존재 한다는 의미가 됨.
		// => file.isFile()
		// -> 파일이 존재하는 경우 true 리턴,
		// file의 Path 가 폴더인 경우는 false 리턴
		File file = new File(realPath);
		if (!file.exists()) {
			// 저장 폴더가 존재하지 않는 경우 폴더를 만들어준다
			file.mkdir();
		}
		// --------------------------------------------
		// ** File Copy 하기 (IO Stream)
		// => 기본이미지(basicman4.png) 가 uploadImages 폴더에 없는경우 기본폴더(images) 에서 가져오기
		// => IO 발생: Checked Exception 처리
		file = new File(realPath + "basicman1.jpg"); // uploadImages 폴더에 화일존재 확인을 위함
		if (!file.isFile()) { // 존재하지않는 경우
			String basicImagePath = "Z:\\JavaWebStudy\\MyWork\\spring02\\src\\main\\webapp\\resources\\images\\basicman1.jpg";
			FileInputStream fi = new FileInputStream(new File(basicImagePath));
			// => basicImage 읽어 파일 입력바이트스트림 생성
			FileOutputStream fo = new FileOutputStream(file);
			// => 목적지 파일(realPath+"basicman4.png") 출력바이트스트림 생성
			FileCopyUtils.copy(fi, fo);
		}
		// --------------------------------------------
		// ** MultipartFile
		// => 업로드한 파일에 대한 모든 정보를 가지고 있으며 이의 처리를 위한 메서드를 제공한다.
		// -> String getOriginalFilename(),
		// -> void transferTo(File destFile),
		// -> boolean isEmpty()
		// 1.4) 저장경로 완성
		String file1 = "", file2 = "KarinaFlower1.jpg";
		// image_File을 선택
		MultipartFile uploadfilef = dto.getUploadfilef();
		if (uploadfilef != null && !uploadfilef.isEmpty()) {
			// 1.4.1 물리적위치 저장(file1)
			// 저장경로(realpath + 파일명) 완성
			file1 = realPath + uploadfilef.getOriginalFilename();
			// 해당 경로에 저장(붙여넣기)
			uploadfilef.transferTo(new File(file1));

			// 1.4.2 Table 저장경로 완성(file2)
			file2 = uploadfilef.getOriginalFilename();
		}
		dto.setUploadfile(file2);
		// 2 Service
		// passwordEncoder 적용
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		
		
		
		// ** *****************************************
		// ** Transaction_AOP 적용 *********************
		// 1. 준비: pom.xml (dependency) 확인
		// => AspectJ(기본제공), AspectJ Weaver(추가)

		// 2. servlet-context.xml AOP 설정

		// 3. Rollback Test
		// 3.1) Aop xml 적용전 => insert1 은 입력되고, insert2 에서 500_Dupl..Key 오류 발생
		// 3.2) Aop xml 적용후 => insert2 에서 오류발생시 모두 Rollback 되어 insert1, insert2 모두 입력 안됨

		// 3.1) Transaction 적용전 : 동일자료 2번 insert
		// => 첫번째는 입력완료(commit) 되고, 두번째자료 입력시 Key중복 오류발생 (500 발생)
		// 3.2) Transaction 적용후 : 동일자료 2번 insert
		// => 첫번째는 입력완료 되고, 두번째 자료입력시 Key중복 오류발생 하지만,
		// rollback 되어 둘다 입력 안됨
		
		if (service.insert(dto) > 0) {
			model.addAttribute("message", " 회원가입 성공 ");
		} else {
			uri = "member/joinForm";
			model.addAttribute("message", " 회원가입 실패 ");
		}
		return uri;
	} // Join

	// password 수정 (PasswordEncorder 추가 후)
	@GetMapping("/pwUpdate")
	public void pwUpdate() {
		// View_name 생략
	}

	// PasswordUpdate
	// service, DAO 에 pwUpdate(dto) 메서드 추가
	// 성공 : 세션 무효화, 로그인창으로
	// 실패 : pwUpdate
	@PostMapping("/pwUpdate")
	public String pwUpdate(HttpSession session, MemberDTO dto, Model model) {
		// 1 요쳥분석
		// id를 세션에서 가져오기
		dto.setId((String) session.getAttribute("loginID"));
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		String uri = "member/loginForm";

		// 2 Service
		if (service.pwUpdate(dto) > 0) {
			session.invalidate();
			model.addAttribute("message", " 비밀번호 변경 성공, 재로그인하세요 ");
		} else {
			model.addAttribute("message", " 비밀번호 변경 실패, 다시 시도하세요 ");
			uri = "member/pwUpdate";
		}
		return uri;
	} // pwUpdate

	// Update
	@RequestMapping(value = { "/update" }, method = RequestMethod.POST)
	public String update(HttpServletRequest request, HttpSession session, Model model, MemberDTO dto)
			throws IOException {
		// 1 요청 분석
		// 성공 : memberDetail
		// 실패 : updateForm
		// 출력하려면 dto 객체의 값("apple")이 필요하므로 보관
		String uri = "member/memberDetail";
		model.addAttribute("apple", dto);

		// uploadFile 처리
		// newImage 선택 여부
		// 선택 -> oldImage 삭제, newImage 저장 : uploadfilef 사용
		// 선택하지않음 -> oldImage가 uploadfile로 전달되었으므로 그냥 사용하면 됨
		MultipartFile uploadfilef = dto.getUploadfilef();
		if (uploadfilef != null && !uploadfilef.isEmpty()) {
			// newImage를 선택함
			// 1 물리적위치 저장(file1)
			String realPath = request.getRealPath("/");
			String file1, file2 = dto.getUploadfile();

			// 2 realPath를 이용해서 물리적 저장위치 확인
			if (realPath.contains(".eclipse."))
				realPath = "C:\\MTest\\StudyStudy\\spring02\\src\\main\\webapp\\resources\\uploadImages\\";
			else
				realPath = "C:\\MTest\\IDESet\\apache-tomcat-9.0.85\\webapps\\spring02\\resources\\uploadImages\\";

			// 3 oldFile 삭제
			// oldFile Name : dto.getUploadfile()
			// 삭제경로 : realPath + dto.getUploadfile()
			File delFile = new File(realPath + dto.getUploadfile());
			// 파일이 존재하면 삭제
			if (delFile.isFile())
				delFile.delete();

			// 4 newFile 저장
			file1 = realPath + uploadfilef.getOriginalFilename();
			uploadfilef.transferTo(new File(file1));

			// 5 Table 저장경로 완성(file2)
			file2 = uploadfilef.getOriginalFilename();
			dto.setUploadfile(file2);
		}

		// 2 Service
		if (service.update(dto) > 0) {
			model.addAttribute("message", " 회원정보수정 성공 ");
			// name을 수정한 경우를 대비해 session의 loginName을 수정
			session.setAttribute("loginName", dto.getName());
		} else {
			uri = "member/updateForm";
			model.addAttribute("message", " 회원정보수정 실패 ");
		}
		return uri;
	} // Update

	// Delete
	@RequestMapping(value = { "/delete" }, method = RequestMethod.GET)
	public String delete(HttpSession session, Model model, RedirectAttributes rttr) {
		// 1 요청분석
		// id를 session에서 get
		// id 삭제와 session 무력화
		String id = (String) session.getAttribute("loginID");
		String uri = "/home";

		// 2 Service 처리
		if (service.delete(id) > 0) {
			model.addAttribute("message", " 탈퇴 성공 ");
			// requestScope의 message를 redirect 시에도 유지하려면
			// session에 보관했다가 사용 후에는 삭제해야함
			// RedirectAttributes API가
			// session에 보관 후 redirect 되어진 요청 처리시에 requestScope에 옮기고,
			// session의 message는 삭제
			rttr.addFlashAttribute("message", " 탈퇴 성공 ");
			session.invalidate();
		} else {
			model.addAttribute("message", " 탈퇴 실패 ");
			rttr.addFlashAttribute("message", " 탈퇴 실패 ");
		}
		return uri;
	} // Delete
}
// class
