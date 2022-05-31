package com.cst438.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Admin;
import com.cst438.domain.AdminRepository;
import com.cst438.domain.Student;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.StudentRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	AdminRepository adminRepository;
	
	@GetMapping("/student")
	public StudentDTO getStudent(@AuthenticationPrincipal OAuth2User principal) {
		String email = principal.getAttribute("email");
		Student student = studentRepository.findByEmail(email);
		
		if(student == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student does not exist: " + email);
		}
		
		return createStudent(student);
	}
	
	/**
	 * USER STORY(1/3): "As an administrator, I can add a student to the 
	 * system.  I input the student email and name.  The student email 
	 * must not already exists in the system."
	 */
	@PostMapping("/student")
	@Transactional
	public StudentDTO addStudent( @RequestBody StudentDTO newStudentDTO, @AuthenticationPrincipal OAuth2User principal) {
		String email = principal.getAttribute("email");
		Admin admin = adminRepository.findByEmail(email);
		
		if(admin == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not an admin: " + email);
		}
		
		Student student = studentRepository.findByEmail(newStudentDTO.studentEmail);
		
		if(student != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student already exists: " + newStudentDTO.studentEmail);
		}
		
		student = new Student();
		student.setEmail(newStudentDTO.studentEmail);
		student.setName(newStudentDTO.studentName);
		Student result = studentRepository.save(student);
		return createStudent(result);
	}
	
	/**
	 * USER STORY(2/3): "As an administrator, I can put student registration on HOLD."
	 */
	@PutMapping("/student/hold/{id}")
	@Transactional
	public void holdStudent( @PathVariable(value="id") int id, @AuthenticationPrincipal OAuth2User principal) {
		String email = principal.getAttribute("email");
		Admin admin = adminRepository.findByEmail(email);
		
		if(admin == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not an admin: " + email);
		}
		
		Student student = studentRepository.findById(id);
		
		if(student == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student does not exist: " + id);
		}
		
		student.setStatus("HOLD");
		student.setStatusCode(1);
		studentRepository.save(student);
	}
	
	/**
	 * USER STORY(3/3): "As an administrator, I can release the HOLD on student registration."
	 */
	@PutMapping("/student/release/{id}")
	public void releaseStudent( @PathVariable(value="id") int id, @AuthenticationPrincipal OAuth2User principal) {
		String email = principal.getAttribute("email");
		Admin admin = adminRepository.findByEmail(email);
		
		if(admin == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not an admin: " + email);
		}
		
		Student student = studentRepository.findById(id);
		
		if(student == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student does not exist: " + id);
		}
		
		student.setStatus("ACTIVE");
		student.setStatusCode(0);
		studentRepository.save(student);
	}
	
	/**
	 * Helper method to select which student properties to expose. If we chose return
	 * the entity, we may reveal information such as the password hash if these
	 * attributes are implemented.
	 * @param student Student object that StudentDTO is created from.
	 * @return studentDTO StudentDTO object.
	 */
	private StudentDTO createStudent(Student student) {
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.student_id = student.getStudent_id();
		studentDTO.studentName = student.getName();
		studentDTO.studentEmail = student.getEmail();
		studentDTO.studentCode = student.getStatusCode();
		studentDTO.studentStatus = student.getStatus();
		return studentDTO;
	}
}
