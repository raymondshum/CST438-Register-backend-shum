package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Admin;
import com.cst438.domain.AdminDTO;
import com.cst438.domain.AdminRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController
{
	@Autowired
	AdminRepository adminRepository;
	
	@GetMapping("/admin")
	public AdminDTO getadmin(@AuthenticationPrincipal OAuth2User principal) {
		String email = principal.getAttribute("email");
		Admin admin = adminRepository.findByEmail(email);
		
		if(admin == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin does not exist: " + email);
		}
		
		return createAdminDTO(admin);
	}
	
	private AdminDTO createAdminDTO(Admin admin) {
		AdminDTO adminDTO = new AdminDTO();
		adminDTO.admin_id = admin.getAdmin_id();
		adminDTO.email = admin.getEmail();
		return adminDTO;
	}
}