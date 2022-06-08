package com.cst438.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import com.cst438.domain.Student;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.StudentRepository;


@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {
	/*
	 * used by React Login front end component to test if user is 
	 * logged in.  
	 *   response 401 indicates user is not logged in
	 *   a redirect response take user to Semester front end page.
	 */
	
	@Value("${frontend.post.login.url}")
	String redirect_url;
	
	@Autowired
	StudentRepository studentRepository;
	
	@GetMapping("/user")
	public String user (@AuthenticationPrincipal OAuth2User principal){
		// used by front end to display user name.
		System.out.println(principal.getAttributes());
		return "redirect:" + redirect_url;
	}
	
	@GetMapping("/info")
	public Map<String, String> getInfo(@AuthenticationPrincipal OAuth2User principal) {
		Map<String, String> map = new HashMap<>();
		map.put("Hello", "World");
		return map;
	}
	

}