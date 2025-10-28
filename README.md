# Booking API Automation Framework

## 1. Project Overview

This repository contains an API automation framework for the "Booking System" API. The framework is built from scratch using Java, RestAssured, and TestNG. It covers end-to-end scenarios, performs data validation, and generates comprehensive test reports.

This project demonstrates the transition from manual testing (documented in Postman) to a full automation suite.

## 2. Technologies & Tools 🛠️

* **Language:** Java
* **API Automation:** RestAssured
* **Testing Framework:** TestNG (for parallel execution and assertions)
* **Reporting:** Allure Reports
* **Build Tool:** Maven
* **Manual API Testing:** Postman
* **CI/CD (Manual):** Newman (for running Postman collections via CLI)
* **Version Control:** Git & GitHub

## 3. How to Run the Project

**Prerequisites:**
* Java JDK 11 (or newer)
* Apache Maven
* Allure Commandline (to generate reports)

**Running Tests:**
1.  Clone the repository:
    ```bash
    git clone [https://github.com/youssif-gassar/RestAssured-Booking-API-Automation.git](https://github.com/youssif-gassar/RestAssured-Booking-API-Automation.git)
    ```
2.  Navigate to the project directory:
    ```bash
    cd RestAssured-Booking-API-Automation
    ```
3.  Run the TestNG suite using Maven:
    ```bash
    mvn clean test
    ```

**Generating the Allure Report:**
After running the tests, use the following commands to generate and open the Allure report:

```bash
allure serve allure-results