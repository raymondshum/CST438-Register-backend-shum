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

## Build Instructions (Registration Service)

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

## Build Instructions (Integrated Registration and Backend Services Using RabbitMQ)

Steps:
1. Set up the Registration Service backend (reference build instructions above).
2. Set up the Gradbook Service backend using the same procedure. Repository here: (Link)[https://github.com/NStankovich/CST438-Gradebook-backend-Stankovich/] 
3. Run the [db-gradebook.sql](https://github.com/raymondshum/CST438-Register-backend-shum/blob/master/cst438_register/src/main/resources/db_register.sql) script to build the gradebook tables.
4. Install [Erlang](https://www.erlang.org/downloads).
5. Install [RabbitMQ](https://www.rabbitmq.com/download.html).

To test the backend integration:
1. Install [Postman](https://www.postman.com/downloads/) to test API Routes.
2. In <code>cst438_register\src\main\resources\application.properties</code> and the gradebook equivalent, you would alter the following entries:
    - <code>registration.service = MQ</code> && <code>gradebook.service = MQ</code>
        - MQ will use communication via asynchronous communication via RabbitMQ.
    - <code>registration.service = REST</code> && <code>gradebook.service = REST</code>
        - REST will use HTTP methods to send requests between backend services.

To test API routes:
1. Run the following SQL script to prepare the registration and gradebook databases: [integration-records](https://github.com/raymondshum/CST438-Register-backend-shum/blob/master/cst438_register/src/main/resources/db_register.sql)
2. Run the following routes on Postman:
    - <code>POST</code> http://localhost:8080/schedule?year=2021&semester=Fall
        - <code>BODY</code> is <code>JSON</code> format: <code> {"course_id" : 40443}   </code> 
    - <code>POST</code> http://localhost:8081/course/40443/finalgrades
3. Enrollment records should be updated in the enrollment tables for databases in both services after each route is contacted.
4. To test again, rebuild the gradebook and register databases by rerunning the sql scripts:
    - [db_register.sql](https://github.com/raymondshum/CST438-Register-backend-shum/blob/master/cst438_register/src/main/resources/db_register.sql)
    - [db-gradebook.sql](https://github.com/raymondshum/CST438-Register-backend-shum/blob/master/cst438_register/src/main/resources/db_register.sql)
---

## Project Phases

---

### Week 1, Assignment 1

- Sprint planning using Pivotal Tracker: [Link](https://www.pivotaltracker.com/n/projects/2565257)
- Planned relational schema necessary to support user stories by drawing an EER Diagram using MySQL Workbench: [Link](https://github.com/raymondshum/CST438-Register-backend-shum/blob/master/cst438_register/src/main/resources/shum_assn1_eer_diagram.png)

### Week 2, Assignment 2

- Implemented the following user stories:
    - As an administrator, I can add a student to the system.  I input the student email and name.  The student email must not already exists in the system.
    - As an administrator, I can put student registration on HOLD.
    - As an administrator, I can release the HOLD on student registration.
- Implemented JUnit test cases for each story.
- Requested code review on PR: [dev-week2](https://github.com/raymondshum/CST438-Register-backend-shum/pull/1)
- Delievered code review for PR: [dev](https://github.com/NStankovich/CST438-Gradebook-backend-Stankovich/pull/1)

### Week 3, Assignment 3

- Implemented React front end: [Link](https://github.com/raymondshum/CST438-Register-frontend-shum)
- Implemented the following user story:
    - Add a button on the home page for add a new student with name, email.  Set the status code to 0.  Add a new route in App.js  
- Requested code review on PR: [dev-week3](https://github.com/raymondshum/CST438-Register-frontend-shum/pull/1)
- Delivered code review for PR: [PLACEHOLDER](#)


### Week 4, Assignment 4

- Integrated gradebook and registration backends using RabbitMQ.
    - You can find my partner's (Nicholas Stankovich) gradebook backend repository here: [Link](https://github.com/NStankovich/CST438-Gradebook-backend-Stankovich/)
- Requested code review on PR: [PLACEHOLDER](#)
- Delivered code review for PR: [PLACEHOLDER](#)
---