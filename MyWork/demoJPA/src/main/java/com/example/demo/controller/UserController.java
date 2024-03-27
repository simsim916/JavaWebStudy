package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.UserDTO;
import com.example.demo.entity.Member;
import com.example.demo.jwtToken.TokenProvider;
import com.example.demo.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

// ** MemberController_Rest
// => SpringBoot JPA , React 사용, 계층적 uri 적용

//@CrossOrigin(origins = "http://localhost:3000")
// => Test용
@RestController
@RequestMapping("/user")
@Log4j2  //@Log4j -> Boot 에서는 2015년 이후 지원중단
@AllArgsConstructor // 모든 맴버변수 생성자 주입하므로 각각 @Autowired 할 필요없음
public class UserController {
	
	private MemberService service;
	private PasswordEncoder passwordEncoder;
	private TokenProvider tokenProvider;
	
    // ** 로그인 확인 
    // => Session 체크해서 react state값 유지
	// => Session 객체는 각 User별로 관리됨 
    @GetMapping("/check-server")
    public ResponseEntity<?> checkLogin(HttpSession session) {
    	log.info("** React_SpringBoot Connection 확인 중 **");
        return ResponseEntity.ok()
        		.body(Map.of("checkLogin", "확인하지않음",
        					 "checkData", "** ** Server 연결 성공, Port:8080 **" 	
        				));
        // => Map.of()
        //	- java 9 버전 부터 추가, 간편하게 초기화 가능
        //	  map.put(1, "sangwoo kang"); map.put(2, "james kang"); put(3, "stef you");
        //	  -> Map.of(key_1, "Value_sangwoo kang",
        //        		2, "james kang",
        //        		3, "stef you" )
        //	- 그러나 10개 까지만 초기화 가능 (10개 이상은 ofEntries() 사용)
        //	- unmodifiable(수정불가능) map을 리턴하므로 초기화후 수정불가능 (Immutable 객체)
        //	- 초기화 이후에 조회만 하는경우 주로사용함.(Key 관리 등)
    }
	
    // ** 로그인
    // => Token 적용후
    @PostMapping(value="/login", consumes="application/json;"  
								, produces="application/json;")  
	public ResponseEntity<?> login(HttpSession session, @RequestBody Member entity) {	
		// => Login 성공 : status OK & 토큰생성 & userDTO return
		//          오류 : status Error & ResponseDTO 이용 Exception_Message  
		log.info("** login Data 전달 확인=> "+entity);
		
		// 1) 입력받은 Password 보관
		String password=entity.getPassword();
		
		// 2) Service 실행
		// => id 일치 &  Password 확인
    	entity = service.selectOne(entity.getId());
		
    	if ( entity !=null && 
   			 passwordEncoder.matches(password, entity.getPassword()) ) {	
   			// => 로그인 성공
    		//	-> 로그인정보 session 보관 (추후 session Test)  
    		//	-> token 사용하면 요청정보에 userID 가 늘 전달되기 때문에 
    		//	   session에 반드시 보관할 필요는 없지만 Test 를 위해 보관함.   
   			session.setAttribute("loginID", entity.getId());
   			session.setAttribute("loginName", entity.getName());
   			log.info("*** login: session 보관 loginID = "+session.getAttribute("loginID"));
   			// => 현재 메서드에서는 session 값이 인식되지만 이후의 요청에서는 session 값은 유지되지 않을수 있음
   			//	  ( SecurityConfig 클래스의 sessionCreationPolicy_세션 정책 에 따름 )	
    	 
   			// => 로그인 성공 : 토큰생성
   			final String token = tokenProvider.create(entity);
			final UserDTO userDTO = UserDTO.builder()
					.token(token)
					.id(entity.getId())
					.username(entity.getName())
					.build();
			log.info("login 성공 token = "+token);
			return ResponseEntity.ok().body(userDTO);
		}else {
			// 로그인 실패 (id, pssword 오류 구분하지 않음)
			return ResponseEntity
					.status(HttpStatus.BAD_GATEWAY)  //502
					.body("Login failed.");
		}
	} //login
    
    /*    
    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        // 세션 무효화
    	session.invalidate();
    	log.info("~~ 로그아웃 성공 ~~");
        return ResponseEntity.ok("~~ 로그아웃 성공 ~~");
    }
    
    => Token 적용전
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Member entity,  HttpSession session, Model model){

		// 1. 요청분석
    	String password = entity.getPassword();
		// 2. 서비스 처리
    	entity = service.selectOne(entity.getId());
    	log.info("login newEntity => "+entity);
		if ( entity !=null && 
			 passwordEncoder.matches(password, entity.getPassword()) ) {	
			// => 로그인 성공: 로그인 정보 session 보관 & Front로 전송  
			session.setAttribute("loginID", entity.getId());
			session.setAttribute("loginName", entity.getName());
			
			// => response 로 전송할 객체(UserDTO) 생성
			//    빌더 패턴적용, userDTO 의 값 변경 방지를 위해 final 사용.	
			final UserDTO userDTO = UserDTO.builder()
								.id(entity.getId())
								.username(entity.getName())
								.build();
			log.info("~~ 로그인 성공, HttpStatus.OK => "+HttpStatus.OK);
			return ResponseEntity.ok(userDTO);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("~~ id 또는 password 오류 ~~");
			// => HttpStatus.UNAUTHORIZED: 401 (메서드내부 오류에서는 500대 오류코드 사용바람직) 
		}
    } //login
	*/

	
    
    
//	// ** ID 중복확인
//	@GetMapping("/idDupCheck")
//	public String idDupCheck(Member entity, Model model) {
//		// 1) newID 확인
//		if ( service.selectOne(entity.getId()) !=null ) {
//			// => 존재 : 사용불가
//			model.addAttribute("idUse", "F");
//		}else {
//			// => 없으면: 사용가능
//			model.addAttribute("idUse", "T");
//		}
//		return "member/idDupCheck";
//	}
//
//	// ** MemberList
//	@GetMapping("/memberList")
//	public void memberList(Model model) {
//		model.addAttribute("banana", service.selectList());
//	}
//	
//	// ** MemberDetail
//	@GetMapping(value ="/mdetail")
//	public String mdetail(HttpServletRequest request, Model model, Member entity) {
//		model.addAttribute("apple", service.selectOne(entity.getId()));
//		
//		if ( "U".equals(request.getParameter("jCode")) )
//			 return "member/memberUpdate";
//		else return "member/memberDetail";
//	} //mdetail
//
//
//	// ** Join 기능
//	// => JoinForm: GET
//	@GetMapping(value="/memberJoin")
//	public void memberJoin() {
//		// viewName 생략 -> 요청명이 viewName 이 됨
//	}
//	
//	// => Join Service 처리: POST ( React 용으로 수정해야함 )
//	@PostMapping(value="/join")
//	public String join(HttpServletRequest request, 
//					Member entity, Model model) throws IOException  {
//		// 1. 요청분석 & Service
//		// => 성공: 로그인유도 (loginForm 으로, member/loginForm.jsp)
//		// => 실패: 재가입유도 (joinForm 으로, member/memberJoin.jsp)
//		String uri="member/loginForm";
//		
//		// ** PasswordEncoder (암호화 적용)
//		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
//		
//		// ** MultipartFile ***********************
//		String realPath = "D:\\MTest\\MyWork\\demoJpa\\src\\main\\webapp\\resources\\uploadImages\\";
//		// => 기본 이미지 지정하기
//		String file1, file2="resources/uploadImages/basicman4.png";
//		
//		// => 저장경로 완성
//		MultipartFile uploadfilef = entity.getUploadfilef();
//		if ( uploadfilef!=null && !uploadfilef.isEmpty() ) {
//			// => image_File 을 선택함 -> 저장 (저장경로: relaPath+화일명)
//			// 1.3.1) 물리적위치 저장 (file1)
//			file1 = realPath + uploadfilef.getOriginalFilename(); //저장경로 완성 
//			uploadfilef.transferTo(new File(file1)); //해당경로에 저장(붙여넣기)
//			
//			// 1.3.2) Table 저장경로 완성 (file2)
//			file2 = "resources/uploadImages/" + uploadfilef.getOriginalFilename();
//		} // Image 선택한 경우
//		
//		// 1.4) 완성된 경로를 dto 에 set
//		entity.setUploadfile(file2);
//		
//		// 2. Service 처리
//		try {
//			log.info("** insert 성공 id => "+service.save(entity));
//			model.addAttribute("message", "~~ 회원가입 성공!! 로그인후 이용하세요 ~~");
//		} catch (Exception e) {
//			log.info("** insert Exception => "+e.toString());
//			model.addAttribute("message", "~~ 회원가입 실패!! 다시 하세요 ~~");
//			uri="member/memberJoin";
//		}
//		
//		// 3. View 
//		return uri;
//	} // Join_Post
//	
//	// ** Member Update
//	// => 요청: home 에서 내정보수정 -> 내정보수정Form (memberUpdate.jsp) 출력
//	// => 수정후 submit -> 수정 Service 
//	//		-> 성공: detail
//	//		-> 실패: 재시도 유도 (memberUpdate.jsp)
//	@PostMapping(value="/mupdate")
//	public String memberUpdte(HttpSession session,
//							  Member entity, Model model) throws IOException {
//		
//		// => 처리결과에 따른 화면 출력을 위해서 dto 의 값을 Attribute에 보관
//		model.addAttribute("apple", entity);
//		String uri="member/memberDetail";
//		
//		// ** password는 수정불가
//		
//		// *** ImageUpload 처리 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//		
//		MultipartFile uploadfilef = entity.getUploadfilef(); 
//		// => new Image 를 선택한 경우에만 처리하면 됨 
//		if ( uploadfilef!=null && !uploadfilef.isEmpty() ) {
//			// => Image 재선택 MultipartFile 처리
//			String realPath = "D:\\MTest\\MyWork\\demoJpa\\src\\main\\webapp\\resources\\uploadImages\\";
//			
//			// => 물리적위치에 저장 (file1)
//			String file1 = realPath + uploadfilef.getOriginalFilename(); //저장경로 완성
//			uploadfilef.transferTo(new File(file1)); // IO 발생: Checked Exception 처리  
//			
//			// => Table 저장경로 완성 (file2)
//			String file2="resources/uploadImages/" + uploadfilef.getOriginalFilename();
//			entity.setUploadfile(file2);
//		} // Image 선택 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//		
//		// => Service 처리
//		try {
//			log.info("** update 성공 id => "+service.save(entity));
//			session.setAttribute("loginName", entity.getName());
//			// => 이름을 수정한 경우 session 값 변경 
//			model.addAttribute("message", "~~ 회원정보 수정 성공 ~~");
//		} catch (Exception e) {
//			log.info("** update Exception => "+e.toString());
//			model.addAttribute("message", "~~ 회원정보 수정 실패 !! 다시 하세요 ~~");
//			uri="member/memberUpdate";
//		}
//		
//		return uri;
//	} //memberUpdte
//	
	// ** Member Delete: 회원탈퇴
//	@GetMapping(value="/mdelete")
//	public String mdelete(HttpSession session, Member entity, RedirectAttributes rttr) {
//		
//		String uri = "redirect:/home";
//		
//		try {
//			log.info("** delete 성공 id => "+service.delete(entity.getId()));
//			rttr.addFlashAttribute("message", "~~ 탈퇴 성공!! 1개월후 재가입 가능 합니다 ~~") ;	
//			 if ( ((String)session.getAttribute("loginID")).equals("admin") ) {
//				 // => 관리자에 의한 강제탈퇴 : memberList.jsp
//				 uri="redirect:memberList";
//			 }else {
//				 // => 본인탈퇴 : home.jsp, session 무효화 
//				 session.invalidate();
//			 }
//		} catch (Exception e) {
//			log.info("** delete Exception => "+e.toString());
//			rttr.addFlashAttribute("message", "~~ 탈퇴 실패 ~~");
//		}
//		
//		return uri;
//	} // mdelete

} //class
