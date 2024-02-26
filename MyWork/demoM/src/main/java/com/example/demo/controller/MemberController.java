package com.example.demo.controller;

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

import com.example.demo.domain.MemberDTO;
import com.example.demo.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import pageTest.PageMaker;
import pageTest.SearchCriteria;

@Log4j2 // @Log4j -> Boot 에서는 2015년 이후 지원중단
@Controller
@AllArgsConstructor
// member로 시작하는 요청들이 모두 여기로 오게함
@RequestMapping(value = "/member")
public class MemberController {

	MemberService service;
	PasswordEncoder passwordEncoder;
	// = new BCryptPasswordEncoder(); -> root--.xml에 bean을 등록

	@GetMapping(value = "/aximlist")
	public String aximlist(Model model) {
		model.addAttribute("banana", service.selectList());
		return "axTest/axMemberList";
	}
	
	@GetMapping(value="axmcri")
	public String anmcri(Model model, HttpServletRequest request,
			SearchCriteria cri, PageMaker pageMaker) {
		cri.setSnoEno();
		
		model.addAttribute("banana", service.selectList());
		
		
		return "axTest/axmPageList";
	}

	@GetMapping("/log4jTest")
	public String log4jTest() {
		String name = "simsim916";
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

	@RequestMapping(value = { "/loginForm" }, method = RequestMethod.GET)
	public void loginForm() {
	} // loginForm

	// login
	@PostMapping("/login")
	public String login(HttpSession session, Model model, MemberDTO dto) {
		String password = dto.getPassword();
		String uri = "redirect:/home"; // 성공시 uri

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

	@RequestMapping(value = { "/detail" }, method = RequestMethod.GET)
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
		if (!realPath.contains("-tomcat-"))
			// 개발중, 내 로컬주소 경로
			realPath += "\\resources\\uploadImages\\";
		// 배포 후, 웹의 주소 경로
		// realPath => C:\\MTest\\IDESet\\apache-tomcat-9.0.85\\webapps\\demoM\\
		else
			realPath = "Z:\\JavaWebStudy\\MTest\\IDESet\\apache-tomcat-9.0.85\\webapps\\demoM\\resources\\uploadImages\\";

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
			String basicImagePath = "Z:\\JavaWebStudy\\MyWork\\demoM\\src\\main\\webapp\\resources\\images\\basicman1.jpg";
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
				realPath = "C:\\MTest\\StudyStudy\\demoM\\src\\main\\webapp\\resources\\uploadImages\\";
			else
				realPath = "C:\\MTest\\IDESet\\apache-tomcat-9.0.85\\webapps\\demoM\\resources\\uploadImages\\";

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
