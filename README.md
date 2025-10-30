PLAYWRIGHT JUNIT5 FRAMEWORK

# Maven Command to Execute test cases
- mvn clean verify -Dbrowser=chromium -Dheadless=true

# Framework Setup
- Java 17
- Intellij
- Maven
- Node Js

# Pom.xml Plugins
- allure-maven
- maven-failsafe-plugin
- maven-surefire-plugin
- maven-compiler-plugin

# Junit5 Configuration
- junit-platform.properties

# Maven Command to Open Allure Reports
- mvn io.qameta.allure:allure-maven:serve

# Terminal Command to Open Trace Viewer
npx playwright show-trace fileName.zip
