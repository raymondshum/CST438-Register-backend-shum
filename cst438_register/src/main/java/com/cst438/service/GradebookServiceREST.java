package com.cst438.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.cst438.domain.EnrollmentDTO;


public class GradebookServiceREST extends GradebookService {

	private RestTemplate restTemplate = new RestTemplate();

	@Value("${gradebook.url}")
	String gradebook_url;
	
	public GradebookServiceREST() {
		System.out.println("REST grade book service");
	}

	@Override
	public void enrollStudent(String student_email, String student_name, int course_id) {
		EnrollmentDTO enrollmentDTO = new EnrollmentDTO(student_email, student_email, course_id);
		System.out.println("Sending: " + enrollmentDTO);
		restTemplate.postForEntity(gradebook_url + "/enrollment", enrollmentDTO, null);
	}

}
