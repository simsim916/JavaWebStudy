package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Member;
import com.example.demo.service.JoService;
import com.example.demo.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

//** AuthController
//=> SpringBoot JPA , 계층적 uri 적용, React
//=> JWT 인증 Test : 인증이 필요한 요청 구현

@RestController
@RequestMapping(value = "/auth")
@Log4j2
@AllArgsConstructor
public class AuthController {

	MemberService service;
	PasswordEncoder passwordEncoder;
	JoService jservice;

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// 로그아웃
	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpSession session) {
		// 세션 무효화
		session.invalidate();
		log.info("~~ 로그아웃 성공 ~~");
		return ResponseEntity.ok("~~ 로그아웃 성공 ~~");
	}

//	- principal: 객체형, AuthenticationPrincipal(인증정보 or 인증본인) 이며 @AuthenticationPrincipal 로 제공받을수있음.
	// - credentials: 객체형, Password를 의미하며 보통은 null 로 처리
	// - authorities: Collection Type, 권한목록
	// ** User Detail
	@GetMapping("/userdetail")
	public ResponseEntity<?> userDetail(HttpSession session, @AuthenticationPrincipal String userId) {
		// 1. 요청분석
		// => userId: 인증받은 token 에서 get
		// 스프링 시큐리티 필터가 작동되면 JwtAuthenticationFilter 클래스의
		// doFilterInternal() 메서드가 호출되어 request 객체에서 token을 꺼내서
		// 분석하고, 인증되면 SecurityContext에 인증된 Authentication 객체를 넣어두고
		// 현재 스레드내에서 공유되도록 관리하고 있으며,
		// @AuthenticationPrincipal 으로 이 정보를 제공해줌.
		// ( 구체적 동작원리 https://sas-study.tistory.com/410,
		// https://cantcoding.tistory.com/87 참고 )

		log.info("** idList 전송된 reqData_id 확인 => " + userId);
		log.info("** session 에 보관한 loginID 확인 => " + session.getAttribute("loginID"));
		// => session 값은 AuthController 의 /signin 요청 메서드에서 저장함.
		// => 8080접속 session 과 3000접속과는 origin이 다른 별개의 session 이므로
		// 3000 요청으로 호출되어 실행되는 위 session의 값은 null 이다.
		// ( SecurityConfig.java 의 filterChain 메서드의
		// sessionManagement()~~ chain 설정값과는 무관함 )

		// 2. Service & 결과 처리
		Member entity = service.selectOne(userId);
		if (entity != null) {
			log.info("*** userDetail 성공, id=" + userId);
			return ResponseEntity.ok().body(entity);
		} else {
			log.info("*** userDetail 실패, id=" + userId);
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("userDetail failed.");
		}
	} // detail

} // class
