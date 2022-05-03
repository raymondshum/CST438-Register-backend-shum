package com.cst438;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.cst438.controller.StudentController;
import com.cst438.domain.Student;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(classes = {StudentController.class})
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest
public class JunitTestStudent {
	
	public static final String TEST_STUDENT_EMAIL = "test@csumb.edu";
	public static final String TEST_STUDENT_NAME = "test";
	public static final int TEST_STUDENT_ID = 1;
	public static final String STUDENT_ENDPOINT = "/student";
	public static final String HOLD_ENDPOINT = "/hold";
	public static final String RELEASE_ENDPOINT = "/release";
	private static Student student;
	
	@MockBean
	StudentRepository studentRepository;
	
	@Autowired
	private MockMvc mvc;
	
	@BeforeEach
	public void setUp() {
		student = new Student();
		student.setEmail(TEST_STUDENT_EMAIL);
		student.setName(TEST_STUDENT_NAME);
		student.setStatusCode(0);
		student.setStudent_id(TEST_STUDENT_ID);
	}
	
	@Test
	public void getStudentShouldFindExistingStudent() throws Exception {
		given(studentRepository.findByEmail(TEST_STUDENT_EMAIL)).willReturn(student);
		
		MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
				.get(STUDENT_ENDPOINT + "?email=" + TEST_STUDENT_EMAIL)
				.accept(MediaType.APPLICATION_JSON))
		.andReturn().getResponse();

		StudentDTO result = fromJsonString(response.getContentAsString(), StudentDTO.class);
		
		assertEquals(200, response.getStatus());
		assertEquals(TEST_STUDENT_ID, result.student_id);
	}
	
	@Test
	public void getStudentShouldNotFindNonExistentStudent() throws Exception {
		given(studentRepository.findByEmail(TEST_STUDENT_EMAIL)).willReturn(null);
		
		MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
				.get(STUDENT_ENDPOINT + "?email=" + TEST_STUDENT_EMAIL)
				.accept(MediaType.APPLICATION_JSON))
		.andReturn().getResponse();
		
		assertEquals(400, response.getStatus());
		verify(studentRepository, times(1)).findByEmail(TEST_STUDENT_EMAIL);
	}
	
	/**
	 * USER STORY(1/3): "As an administrator, I can add a student to the 
	 * system.  I input the student email and name.  The student email 
	 * must not already exists in the system."
	 */
	@Test
	void addStudentShouldAddNewStudent() throws Exception {
		given(studentRepository.findByEmail(TEST_STUDENT_EMAIL)).willReturn(null);
		given(studentRepository.save(any(Student.class))).willReturn(student);
		
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.studentEmail = TEST_STUDENT_EMAIL;
		studentDTO.studentName = TEST_STUDENT_NAME;
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post(STUDENT_ENDPOINT)
				.content(asJsonString(studentDTO))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		MockHttpServletResponse response = mvc
				.perform(builder)
				.andReturn()
				.getResponse();

		StudentDTO result = fromJsonString(response.getContentAsString(), StudentDTO.class);
		assertEquals(200, response.getStatus());
		assertEquals(TEST_STUDENT_ID, result.student_id);
		verify(studentRepository).save(any(Student.class));
	}
	
	/**
	 * USER STORY(1/3): "As an administrator, I can add a student to the 
	 * system.  I input the student email and name.  The student email 
	 * must not already exists in the system."
	 */
	@Test
	void addStudentShouldNotAddExistingStudent() throws Exception {
		given(studentRepository.findByEmail(TEST_STUDENT_EMAIL)).willReturn(student);
		
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.studentEmail = TEST_STUDENT_EMAIL;
		studentDTO.studentName = TEST_STUDENT_NAME;
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post(STUDENT_ENDPOINT)
				.content(asJsonString(studentDTO))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		MockHttpServletResponse response = mvc
				.perform(builder)
				.andReturn()
				.getResponse();
		
		assertEquals(400, response.getStatus());
		verify(studentRepository, times(0)).save(any(Student.class));
	}
	
	/**
	 * USER STORY(2/3): "As an administrator, I can put student registration on HOLD."
	 */
	@Test
	void holdStudentShouldHoldExistingStudent() throws Exception {
		given(studentRepository.findById(TEST_STUDENT_ID)).willReturn(student);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(
				STUDENT_ENDPOINT+ HOLD_ENDPOINT + "/" + TEST_STUDENT_ID);
		mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
		
		verify(studentRepository, times(1)).findById(TEST_STUDENT_ID);
	}
	
	/**
	 * USER STORY(2/3): "As an administrator, I can put student registration on HOLD."
	 */
	@Test
	void holdStudentShouldNotHoldNonExistentStudent() throws Exception {
		given(studentRepository.findById(TEST_STUDENT_ID)).willReturn(null);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(
				STUDENT_ENDPOINT+ HOLD_ENDPOINT + "/" + TEST_STUDENT_ID);
		mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		verify(studentRepository, times(1)).findById(TEST_STUDENT_ID);
	}
	
	/**
	 * USER STORY(3/3): "As an administrator, I can release the HOLD on student registration."
	 */
	@Test
	void releaseStudentShouldReleaseExistingStudent() throws Exception {
		given(studentRepository.findById(TEST_STUDENT_ID)).willReturn(student);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(
				STUDENT_ENDPOINT+ RELEASE_ENDPOINT + "/" + TEST_STUDENT_ID);
		mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
		
		verify(studentRepository, times(1)).findById(TEST_STUDENT_ID);
	}
	
	/**
	 * USER STORY(3/3): "As an administrator, I can release the HOLD on student registration."
	 */
	@Test
	void releaseStudentShouldNotReleaseNonExistentStudent() throws Exception {
		given(studentRepository.findById(TEST_STUDENT_ID)).willReturn(null);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(
				STUDENT_ENDPOINT+ RELEASE_ENDPOINT + "/" + TEST_STUDENT_ID);
		mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		verify(studentRepository, times(1)).findById(TEST_STUDENT_ID);
	}
	
	private static String asJsonString(final Object obj) {
		try {

			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> T  fromJsonString(String str, Class<T> valueType ) {
		try {
			return new ObjectMapper().readValue(str, valueType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
