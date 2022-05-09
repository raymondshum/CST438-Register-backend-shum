package com.cst438.domain;

public class StudentDTO {
	public int student_id;
	public String studentName;
	public String studentEmail;
	public String studentStatus;
	public int studentCode;
	
	public StudentDTO() {
		this.student_id = 0;
		this.studentName = null;
		this.studentStatus = null;
		this.studentCode = 0;
	}
	
	public StudentDTO(String studentName, String studentEmail) {
		this.student_id = 0;
		this.studentName = studentName;
		this.studentEmail = studentEmail;
		this.studentStatus = null;
		this.studentCode = 0;
	}
	
	public StudentDTO(String studentName, String studentEmail, String studentStatus, int studentCode) {
		this.student_id = 0;
		this.studentName = studentName;
		this.studentEmail = studentEmail;
		this.studentStatus = studentStatus;
		this.studentCode = studentCode;
	}

	@Override
	public String toString() {
		return "StudentDTO [studentName=" + studentName + ", studentEmail=" + studentEmail + ", studentStatus="
				+ studentStatus + ", studentCode=" + studentCode + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		StudentDTO other = (StudentDTO) obj;
		if (student_id != other.student_id)
			return false;

		if (studentEmail == null) {
			if (other.studentEmail != null)
				return false;
		} else if (!studentEmail.equals(other.studentEmail))
			return false;
		
		if (studentName == null) {
			if (other.studentName != null)
				return false;
		} else if (!studentName.equals(other.studentName))
			return false;
		
		if (studentStatus == null) {
			if (other.studentStatus != null)
				return false;
		} else if (!studentStatus.equals(other.studentStatus))
			return false;
		
		if (studentCode != other.studentCode)
			return false;

		return true;
	}
	
	
}
