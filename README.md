# RedTestFramework
RED vs Samsara

This test framework contains tests written for [Samsara](http://localhost:8080)  website. In “pages” package there are classes for all of Samsara website pages and their superclass BasePage and methods which describe the functionalities of pages are written in them.

“utils” package consists of Utils class and more utils classes. Utils class sets up web browser options and enables capturing screenshots of test failures.

Suites can be ran through sutes-runner.xml on suites level, while testng.xml enables cross-browser execution.

Visual scheme: https://whimsical.com/9FGwyUBJBhQ4kTcePQERt

### Pre-requisites

    Java 8 or higher
    Web browser (Firefox, Chrome, Internet Explorer, Edge)
    Maven
    Selenium Webdriver
    TestNG

### Running Tests

The following steps should get you set up for running Selenium tests locally on your machine:

    1. Clone this repository to your local machine.
    2. Declare this project as Maven
    3. Run as TestNG

### Supported browsers

    1. Chrome
    2. Firefox
    3. IE
    4. Edge
    5. Chrome headless
    

Test cases with steps, expected results and some comments and enhancements' proposals are stored in Red vs Samsara.xlsx.
