# HoangVuNguyenLuong.Java.Selenium.Cucumber
This project implements a Selenium WebDriver test automation framework using Java, Cucumber (BDD), and the Page Object Model (POM) design pattern. It is structured as a Maven project, facilitating efficient web application testing.​

📌 Features

Behavior-Driven Development (BDD) with Cucumber and Gherkin syntax
Selenium WebDriver for browser automation
Page Object Model (POM) for maintainable and reusable code
Maven for dependency management and build automation
Extent Reports for detailed and visually appealing test reports​
🗂️ Project Structure


├── src
│   ├── main
│   │   └── java
│   │       ├── core          # Core framework components
│   │       ├── pages         # Page Object classes
│   │       └── utils         # Utility classes
│   └── test
│       └── java
│           └── stepdefs      # Cucumber step definitions
├── resources
│   ├── features              # Cucumber feature files
│   └── config.properties     # Configuration files
├── extent-reports            # Test execution reports
├── pom.xml                   # Maven configuration file
├── README.md                 # Project documentation
└── .gitignore                # Git ignore rules
🚀 Getting Started

Prerequisites
Java 8 or higher
Maven 3.6 or higher
An IDE like IntelliJ IDEA or Eclipse​
Installation
Clone the repository
git clone https://github.com/tonnipp/HoangVuNguyenLuong.Java.Selenium.Cucumber.git
Navigate to the project directory
cd HoangVuNguyenLuong.Java.Selenium.Cucumber
Build the project using Maven
mvn clean install
QA Automation Expert

🧪 Running Tests

Execute the following command to run the test suite:​

mvn test
Test execution reports will be generated in the extent-reports directory.​
GitHub
+9
QA Automation Expert
+9
BrowserStack
+9

📝 Writing Feature Files

Feature files are written in Gherkin syntax and located in the resources/features directory.​
QA Automation Expert

Example:

Feature: User Login

  Scenario: Successful login with valid credentials
    Given the user is on the login page
    When the user enters valid username and password
    Then the user should be redirected to the dashboard
