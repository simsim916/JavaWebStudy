package com.example.demo.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.domain.GuestbookDTO;
import com.example.demo.domain.PageRequestDTO;
import com.example.demo.domain.PageResultDTO;
import com.example.demo.entity.Guestbook;
import com.example.demo.service.GuestbookService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class HomeController {

	GuestbookService guestbookService;
	
	@GetMapping("/home")
	//@GetMapping(value={"/", "/home"})
	// => void : 요청명.jsp 를 viewName 으로 처리함 (home.jsp)
	// 그러므로 "/" 요청은 .jsp 를 viewName 으로 찾게됨(제외)
	// => Boot 의 매핑메서드 에서 "/" 요청은 적용안됨(무시됨)
	// WebMvcConfig 의 addViewControllers 메서드로 해결
	public void home(Locale locale, Model model) {
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);

	}
	
	@GetMapping("/axtestform")
	public String axtestform() {
		return "axTest/axTestForm";
	}
	
	@GetMapping("/ginsert")
	public String ginsert() {
		
		GuestbookDTO dto = GuestbookDTO.builder()
				.title("aaa")
				.content("aaa")
				.writer("aaa")
				.build();
					
		System.out.println("** guest Insert => " +guestbookService.register(dto));
		
		return "redirect:/home";
	}
	
	@GetMapping("/glist")
	public String glist() {
		
		List<Guestbook> list = guestbookService.selectList();
		for ( Guestbook g : list)
		System.out.println("** guest list => " + g);
		
		return "redirect:/home";
	}
	
	@GetMapping("/gupdate")
	public String gupdate() {
		
		GuestbookDTO dto = GuestbookDTO.builder()
				.gno(0L)
				.title("update")
				.content("update")
				.writer("update")
				.build();
		
		System.out.println("** guest gupdate => " +guestbookService.register(dto));

		return "redirect:/home";
	}
	
	@GetMapping("/gdelete")
	// => 퀴리스트링으로 Test : /gdelete?gno=6
	public String gdelete(Long gno) {
		try {
			guestbookService.delete(gno);
			System.out.println("** guest gdelete => " +gno);
		} catch (Exception e) {
			System.out.println("gdelete exception : " + e.toString());
		}
		
		
		return "redirect:/home";
	}
	
	@GetMapping("/gpage")
	public String gpage() {
		PageRequestDTO requestDTO = PageRequestDTO.builder()
				.page(2).size(5).build();
				// => 출력할 pageNo, page당 출력할 row갯수 입력
		
		PageResultDTO<GuestbookDTO, Guestbook> resultDTO 
							= guestbookService.pageList(requestDTO);
		
		for(GuestbookDTO dto : resultDTO.getDtoList()) {
			System.out.println(dto);
		}
		
		return "redirect:home";
	}
}
