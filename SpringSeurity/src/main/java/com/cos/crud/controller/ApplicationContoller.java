package com.cos.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.crud.model.MyUser;
import com.cos.crud.repository.MyUserRepository;
import com.cos.crud.security.MyUserDetails;



@Controller //IOC 제어의 역전
public class ApplicationContoller {
	
	@Autowired //DI 의존적 주입
	private MyUserRepository mRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@GetMapping("/home")
	public @ResponseBody String home() {
		return "<h1>home</h1>";
	}
	
	@GetMapping("/admin/test")
	public @ResponseBody String adminTest(@AuthenticationPrincipal MyUserDetails userDetail) {
		StringBuffer sb = new StringBuffer();
		sb.append("Id >> " + userDetail.getUser().getId() + "<br/>");
		sb.append("Username >> " + userDetail.getUsername() + "<br/>");
		sb.append("Password >> " + userDetail.getPassword() + "<br/>");
		sb.append("Email >> " + userDetail.getUser().getEmail() + "<br/>");
		return sb.toString();
	}
	
	@GetMapping("/board/test")
	public @ResponseBody String boardTest() {
		return "<h1>board</h1>";
	}
	
	@GetMapping("/user/joinForm")
	public String join() {
		return "joinForm";
	}
	
	// 시큐리티 쓸 때 해야할것.
	// 1) CSRF 설정 (security config에서 개발할때는 csrf를 disable 해놓고 하자.)
	// 2) password 인코딩
	@PostMapping("/user/create")
	public String create(MyUser user) { //잭슨 라이브러리가 폼 데이터를 받아서 객체에 자동으로 집어넣어 줌.
		/*Spring Security를 적용한다면 password 암호화는 필수! 왜냐면 아예 DB에 Insert가 안됨. 막혀있음.*/
		String rawPassword = user.getPassword();
		String encPassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		return "redirect:/home";
	}
	
	@PostMapping("/user/loginForm")
	public String loginForm() {
		return "loginForm";
	}
}
