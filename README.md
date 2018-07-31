TestNG & Selenium WebDriver Pearson Automation
==================================================


#### Pre-Requisites for building the project

	- JDK 1.8
	- Maven 3.x
	- Eclipse
	- TestNG
		
	 
#### Framework and API Overview
		
	1. Extent Report : Reporting tool
	2. Rest Assured : Deal with REST API calls 
	3. Log4J : Logging
	4. JODA API : DateTime manipulations
	5. ALM integration for updating results directly into ALM test lab
	6. Docker and SauceLabs support
	7. SauceLab for execution test cases on cloud
	9. Jenkins Integration
	10. HTML customized report


#### How to deploy Autofusion and get Tests running

	1. Download the project by clicking the Download tab in the left navigation panel (A zip file would be downloaded)
	2. Unzip the downloaded project.
	3. Open Eclipse IDE and in tool-bar select : File > Import > Existing Maven Projects
	4. Select the Project unzipped in step2
	5. Select the build path with JDK 1.8 (Dont use jre oath)
	6. Once the project gets build, you can run tests individually (navigate to com.test.<packageName>, Right click a test and select Run As > "TestNG Test") or as a suite (navigate to src/test/resources/testngxml/TestNG.xml, Right click and select Run As > "TestNG Suite")
	7. Once the test execution completes, you can get the report from path : src/test/resources/essentials/web/report/Browser/<browserName>
	8. To change any property like browser, device, Run On Machine, responsiveness, ALM, sendMail etc., navigate to src/test/resources/configFiles/config.properties and edit config.properties accordingly.

