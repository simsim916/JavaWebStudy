package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.example.demo.domain.MemberDTO;
import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pageTest.PageMaker;
import pageTest.SearchCriteria;

@Log4j2
@AllArgsConstructor // 개별적인 @Autowired 생략가능
@Controller
@RequestMapping(value = "/member")
public class MemberController {

	MemberService service;
	PasswordEncoder passwordEncoder;

	// 2) Member_Jo Join List
	// => ver01) @Query("...") 에 JPQL, LEFT_JOIN 구문, MemberDTO return
	// => MemberDTO 는 JoDTO 상속
	@GetMapping("/mjoinList")
	public void mjoinList(Model model) {
		model.addAttribute("banana", service.findMemberJoin());
	}

// ** ID 중복확인
	@GetMapping("/idDupCheck")
	public void idDupCheck(@RequestParam("id") String id, Model model) {
		// 1) newID 존재여부 확인 & 결과처리
		if (service.selectOne(id) != null) {
			// => 사용 불가능
			model.addAttribute("idUse", "F");
		} else {
			// => 사용가능
			model.addAttribute("idUse", "T");
		}
	} // idDupCheck

// ** Login Form 출력	
	@GetMapping("/loginForm")
	public void loginForm() {
	} // loginForm

	@PostMapping("/login")
	public String login(HttpSession session, Model model, Member entity) {

		String password = entity.getPassword();
		String uri = "redirect:/home";

		// 2. 서비스 & 결과 처리
		entity = service.selectOne(entity.getId());

		// => PasswordEncoder 적용
		if (entity != null && passwordEncoder.matches(password, entity.getPassword())) {
			// 성공
			session.setAttribute("loginID", entity.getId());
			session.setAttribute("loginName", entity.getName());
		} else {
			// 실패
			uri = "member/loginForm";
			model.addAttribute("message", "~~ id 또는 password 오류 !! 다시하세요 ~ ");
		}
		return uri;
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	} // logout

	// ** Member Detail
	@GetMapping("/detail")
	public String detail(HttpSession session, Model model, @RequestParam("jCode") String jCode) {
		// 1. 요청분석
		// => id: session 에서 get
		// => detail 또는 수정 Page 출력을 위한 요청인지 jCode 로 구별
		String id = (String) session.getAttribute("loginID");
		String uri = "member/memberDetail"; // detail

		// => update 요청확인후 uri 수정
		if ("U".equals(jCode))
			uri = "member/updateForm";

		// 2. Service & 결과 처리
		model.addAttribute("apple", service.selectOne(id));
		return uri;
	} // detail

	// ** Member List
	@GetMapping("/memberList")
	public void mList(Model model) {
		model.addAttribute("banana", service.selectList());
	} // mList

	// ** Join Form **********************************
	@GetMapping("/joinForm")
	public void joinForm(Model model) {
		// => joList 에서 조목록 가져오기
		// joinForm 의 select 에 적용
		// model.addAttribute("banana", jservice.selectList());
	} // joinForm

	// ** Join
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(HttpServletRequest request, Model model, Member entity) throws IOException {
		// 1. 요청분석
		String uri = "member/loginForm"; // 성공시

		// *** Upload File 처리 **************************
		// 1) 물리적 실제저장위치 확인
		// 1.1) 현재 웹어플리케이션의 실행위치 확인
		// => 이클립스 개발환경 (배포전) : ~~ -tomcat- ~~ 포함되어있지 않음
		// => 톰캣 서버 배포후 : ~~ -tomcat- ~~ 포함
		// realPath => C:\MTest\IDESet\apache-tomcat-9.0.85\webapps\spring02\
		String realPath = request.getRealPath("/");
		log.info("** realPath => " + realPath);
		// => Spring Boot_realPath => C:\MTest\MyWork\demoM\src\main\webapp\

		// 1.2) realPath 를 이용해서 물리적저장위치 (file1) 확인
		realPath += "resources\\uploadImages\\";

		// 1.3) 폴더 만들기 (없을수도 있음을 가정, File 실습)
		File file = new File(realPath);
		if (!file.exists()) {
			// => 저장폴더가 존재하지 않는경우 만들어줌
			file.mkdir();
		}

		// --------------------------------------------
		// ** File Copy 하기 (IO Stream 실습)
		// => 기본이미지(basicman1.png) 가 uploadImages 폴더에 없는경우 기본폴더(images) 에서 가져오기
		// => IO 발생: Checked Exception 처리
		file = new File(realPath + "basicman1.jpg"); // uploadImages 폴더에 화일존재 확인을 위함
		if (!file.isFile()) { // 존재하지않는 경우
			String basicImagePath = "C:\\MTest\\MyWork\\demoM\\src\\main\\webapp\\resources\\images\\basicman1.jpg";
			FileInputStream fi = new FileInputStream(new File(basicImagePath));
			// => basicImage 읽어 파일 입력바이트스트림 생성
			FileOutputStream fo = new FileOutputStream(file);
			// => 목적지 파일(realPath+"basicman1.jpg") 출력바이트스트림 생성
			FileCopyUtils.copy(fi, fo);
		}

		// 1.4) 저장경로 완성
		// => 기본 이미지 저장
		String file1 = "", file2 = "basicman1.jpg";

		MultipartFile uploadfilef = entity.getUploadfilef();
		if (uploadfilef != null && !uploadfilef.isEmpty()) {
			// => image_File 을 선택함
			// 1.4.1) 물리적위치 저장 (file1)
			file1 = realPath + uploadfilef.getOriginalFilename(); // 저장경로(relaPath+화일명) 완성
			uploadfilef.transferTo(new File(file1)); // 해당경로에 저장(붙여넣기)

			// 1.4.2) Table 저장경로 완성 (file2)
			file2 = uploadfilef.getOriginalFilename();
		}
		// --------------------------------------------

		entity.setUploadfile(file2);
		// 2. Service & 결과
		// => PasswordEncoder 적용
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));

		// ** Service 처리
		try {
			log.info("** member insert 성공 => \n" + service.save(entity));
			model.addAttribute("message", "~~ 회원가입 성공 !! 로그인 후 이용하세요 ~~");
		} catch (Exception e) {
			log.info("** member insert Exception => " + e.toString());
			uri = "member/joinForm";
			model.addAttribute("message", "~~ 회원가입 실패 !! 다시 하세요 ~~");
		}

		return uri;
	} // join

	// ** Password 수정 (PasswordEncorder 추가후)
	@GetMapping("/pwUpdate")
	public void pwUpdate() {
		// View_name 생략
	}

	// ** PasswordUpdate
	@PostMapping("/pwUpdate")
	public String pwUpdate(HttpSession session, Member entity, Model model) {
		// 1) 요청분석
		// => id : session 에서
		// => password : 암호화
		entity.setId((String) session.getAttribute("loginID"));
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		String uri = "member/loginForm"; // 성공시

		// 2) Service
		try {
			service.updatePassword1(entity.getId(), entity.getPassword());
			session.invalidate();
			model.addAttribute("message", "Password 수정 성공!!, 재로그인 하세요 ~~");
		} catch (Exception e) {
			model.addAttribute("message", "Password 수정 실패!!, 다시 하세요 ~~");
			uri = "member/pwUpdate";
		}

		return uri;
	} // pwUpdate

	// ** Update
	@PostMapping("/update")
	public String update(HttpServletRequest request, HttpSession session, Model model, Member entity)
			throws IOException {
		// 1. 요청분석

		String uri = "member/memberDetail"; // 성공시
		model.addAttribute("apple", entity);

		MultipartFile uploadfilef = entity.getUploadfilef();
		if (uploadfilef != null && !uploadfilef.isEmpty()) {
			// => newImage 를 선택함
			// 1) 물리적위치 저장 (file1)
			String realPath = request.getRealPath("/");
			String file1;

			// 2) realPath 를 이용해서 물리적저장위치 (file1) 확인
			realPath += "resources\\uploadImages\\";

			// 3) oldFile 삭제
			// => oldFile Name : dto.getUploadfile()
			// => 삭제경로 : realPath+dto.getUploadfile()
			File delFile = new File(realPath + entity.getUploadfile());
			if (delFile.isFile())
				delFile.delete(); // file 존재시 삭제

			// 4) newFile 저장
			file1 = realPath + uploadfilef.getOriginalFilename(); // 저장경로(relaPath+화일명) 완성
			uploadfilef.transferTo(new File(file1)); // 해당경로에 저장(붙여넣기)

			// 5) Table 저장경로 수정
			entity.setUploadfile(uploadfilef.getOriginalFilename());
		}
		// --------------------------------------------

		// 2. Service & 결과
		try {
			log.info("\n update : " + service.save(entity));
			model.addAttribute("message", "~~ 회원 정보 수정 성공 !! ~~");
			session.setAttribute("loginName", entity.getName());
		} catch (Exception e) {
			uri = "member/updateForm";
			model.addAttribute("message", "~~ 회원 정보 수정 오류 !! 다시 하세요 ~~");
		}
		return uri;
	} // update

	// ** Delete
	@GetMapping("/delete")
	public String delete(HttpSession session, Model model, RedirectAttributes rttr) {
		// 1. 요청분석
		// => id: session 에서 get
		// => delete & session 처리
		String id = (String) session.getAttribute("loginID");
		String uri = "redirect:/home";

		// 2. Service & 결과 처리
		try {
			log.info("\ndelete : ");
			service.deleteById(id);
			rttr.addFlashAttribute("message", "~~ 탈퇴 성공 !! 1개월 후 재가입 가능합니다. ~~");
			session.invalidate();
		} catch (Exception e) {
			rttr.addFlashAttribute("message", "~~ 탈퇴 실패 !! 관리자에게 연락 하세요 ~~");
		}
		return uri;
	} // delete

} // class
