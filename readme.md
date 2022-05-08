# CST 438-40_SU22: Software Engineering

## Course Description

---

> This course prepares students for large-scale software development using software engineering principles and techniques. Coverage includes software development process, requirements analysis and specification, software design, implementation, testing, and project management. Students are expected to work in teams to carry out a realistic software project.

---

## Project Description

> This project was cloned from Professor David Wisneski's starter code: [Link](https://github.com/dwisneski/cst438_register_backend/tree/master/cst438_register)
>
> It is a Spring Boot REST API server (documentation for built in endpoints in the link above). It represents the backend of a student registration service. The goal of this project is to mimic the path of a Jr. Software Engineer who joins a team, particpates in Sprint planning, implements user stories in an existing codebase and then participates in code review. The phases of the project are listed below.

---

## Build Instructions

Required software:
- [JDK11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
- [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)
- [Apache Maven](https://maven.apache.org/wrapper/)

> Note: Our developer environment made use of Eclipse for the project. You do not necessarily need to use Eclipse. You
can choose alternatives such as IntelliJ or to simply install Maven and run the project from command line. However, those
steps have not been tested for this project.

Steps:
1. Run the [db_register.sql](https://github.com/raymondshum/CST438-Register-backend-shum/blob/master/cst438_register/src/main/resources/db_register.sql) script to build the register SQL database and insert sample records.
2. Edit the following attributes in the application.properties file (cst438_register\src\main\resources\application.properties) to reflect your local MySQL username and password:
    - <code>spring.datasource.username</code>
    - <code>spring.datasource.password</code>
3. If using Eclipse:
    - (Install Dependencies) Select cst438_register\pom.xml
        - Right-click && <code>"Maven"</code> && <code>"Update Project..."</code>
    - (Run Project) Select cst438_register\src\main\java\com\cst438\Cst4380wRegistrationApplication.java
        - Right-click &&<code> "Run as Spring Boot Application"</code> 
4. If using CLI (bash):
    - Enter the following command from the root directory of the project folder:
        - <code>./mvnw spring-boot:run</code>

---

## Project Phases

---

### Week 1, Assignment 1

- Sprint planning using Pivotal Tracker: [PLACEHOLDER](#)
- Planned relational schema necessary to support user stories by drawing an EER Diagram using MySQL Workbench: [PLACHOLDER](#)

### Week 2, Assignment 2

- Implemented the following user stories:
    - As an administrator, I can add a student to the system.  I input the student email and name.  The student email must not already exists in the system.
    - As an administrator, I can put student registration on HOLD.
    - As an administrator, I can release the HOLD on student registration.
- Implemented JUnit test cases for each story.
- Requested code review on PR: [PLACEHOLDER](#)
- Delievered code review for PR: [PLACEHOLDER](#)

---