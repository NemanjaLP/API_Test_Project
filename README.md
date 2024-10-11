# API Testing Project with Rest Assured and Allure

This project demonstrates API testing using Rest Assured in Java, combined with Allure for test reporting. The goal is to provide a structured and maintainable setup for automated API tests, ensuring clear test reports and insights.

## Prerequisites

Before running the tests, make sure you have the following installed:

- Java JDK (version 8 or above)
- Maven (for dependency management and building the project)
- Allure CLI (for generating and viewing reports)

## Project Structure
- task/BrandsApiTest class is for testing the scenarios from the task assignment
- crudtest/ classes are for testing if the API satisfying the basic REST principles


## Usage
- cd to project root
- To run the test related to the interview task use the following command:
```mvn test -Dtest=src/test/java/com/dhimahi/task/BrandsApiTest.java```
- To run all the tests, use the following command in the Terminal: ```mvn clean test```
- Upon completing the tests generate the Allure test report using the following command: ```mvn allure:report```
- You can view the test report in your browser using the following command: ```allure serve target/dhimahi-test-results```