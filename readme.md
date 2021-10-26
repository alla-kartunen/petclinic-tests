## Tests for Spring PetClinic Sample Application

### <a href="https://github.com/spring-projects/spring-petclinic">See the application project here</a>

### About application
The application is a database of a veterinary clinic. It contains a search by clients, functionality for adding, editing, viewing information about pets, their owners, and visits, and information about specialists.

Petclinic is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/) with MySQL database.

###About test project
The aim of the test project was to study the test's interaction with databases, using **JDBC template** and **Data-Driven** testing. There was no aim to covering the whole project with tests.

###Test project's stack:

* Selenium
* Java
* Maven
* TestNG
* Allure Report

###Test project's design patterns:
* Page Objects
* Page Factory
* Steps/Chain of Invocations
* Value Object
* Builder
* Data Provider


### Allure Report

At the time the test's creation, the application had a search pagination bug, so some tests fail as expected.

The tests contain assertion for data from the Excel file, and one of the tests "edit pet" is skipped due to the wrong type of the pet.

<img width="1042" alt="allure-screenshot" src="https://i.ibb.co/6rJKYs4/allure-1.png">

<img width="1042" alt="allure-screenshot" src="https://i.ibb.co/tXnHSpZ/allure-2.png">