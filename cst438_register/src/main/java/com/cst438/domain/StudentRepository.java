package com.cst438.domain;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository <Student, Integer> {
	
	public Student findByEmail(String email);
	
	public Student findById(int id);
	
	@SuppressWarnings("unchecked")
	public Student save(Student s);
}
