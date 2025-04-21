# HoangVuNguyenLuong.Java.Selenium.Cucumber

This project implements a **Selenium WebDriver** test automation framework using **Java**, **Cucumber (BDD)**, and the **Page Object Model (POM)** design pattern. It is structured as a Maven project, facilitating efficient web application testing.

---

## 📌 Features

- **Behavior-Driven Development (BDD)** with Cucumber and Gherkin syntax
- **Selenium WebDriver** for browser automation
- **Page Object Model (POM)** for maintainable and reusable code
- **Maven** for dependency management and build automation
- **Extent Reports** for detailed and visually appealing test reports

---

## 🗂️ Project Structure

src/
└── main/
    └── java/
        ├── pages/
        │   ├── LoginPage.java
        │   ├── HomePage.java
        │   └── BasePage.java
        ├── core/
        │   └── DriverFactory.java
        └── utils/
            └── ConfigReader.java


---

## 🚀 Getting Started

### Prerequisites

- Java 8 or higher
- Maven 3.6 or higher
- An IDE like IntelliJ IDEA or Eclipse

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/tonnipp/HoangVuNguyenLuong.Java.Selenium.Cucumber.git
2. Navigate to the project directory
   
   cd HoangVuNguyenLuong.Java.Selenium.Cucumber
   
3. Build the project using Maven

   mvn clean install

🧪 Running Tests

mvn test

📝 Writing Feature Files

Feature files are written in Gherkin syntax and located in the resources/features directory.

📊 View Test Report
After test execution, an Extent HTML report is generated automatically.

Location: extent-reports/index.html

