# Step 1: Add course and assignments to register and gradebook tables
INSERT INTO gradebook.course (course_id, instructor, semester, title, year) values (40443, "jjolley@csumb.edu", "Fall", "BUS 305 - Principles of Management", 2021);
INSERT INTO gradebook.assignment (due_date, name, needs_grading, course_id) VALUES ("2021-09-04", "test-hw2", 0, 40443);
INSERT INTO gradebook.assignment (due_date, name, needs_grading, course_id) values ("2021-09-05", "test-hw3", 0, 40443);

# Step 2: Enroll the student using Postman (follow instructions on Readme.MD)

# Step 3: Add assignments associated with the newly enrolled student (test@csumb.edu)
INSERT INTO gradebook.assignment_grade (score, assignment_id, enrollment_id) VALUES (90, 3, 5);
INSERT INTO gradebook.assignment_grade (score, assignment_id, enrollment_id) VALUES (50, 4, 5);

# Step 4: Issue POST request for final grades on Postman