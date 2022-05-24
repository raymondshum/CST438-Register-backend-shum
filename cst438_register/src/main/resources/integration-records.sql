INSERT INTO register.enrollment (student_id, year, semester, course_id) VALUES (1, 2021, "Fall", 40443);
INSERT INTO gradebook.assignment (due_date, name, needs_grading, course_id) VALUES ("2021-09-04", "test-hw2", 0, 40443);
INSERT INTO gradebook.assignment_grade (score, assignment_id, enrollment_id) VALUES (90, 3, 5);
INSERT INTO gradebook.assignment_grade (score, assignment_id, enrollment_id) VALUES (50, 4, 5);