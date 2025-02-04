Pclover.formatter-final
------------------------------
A brief description of your Java project. Explain what the project does and any specific goals or functionalities.

Table of Contents
==================
*Prerequisites
*Getting Started
*Running the Project
*Testing
*Troubleshooting
===================================================================================
*Prerequisites
===============
	-Java Development Kit (JDK): Ensure JDK version 8 or higher is installed.
		You can check your JDK version by running:
			java -version
	-IntelliJ IDEA: Download and install IntelliJ IDEA. Community Edition is sufficient unless your project requires advanced features in the Ultimate Edition.
====================================================================================
*Getting Started
================
1-download the source code 

2-Open Project in IntelliJ
	-Open IntelliJ IDEA.
	-Select File > Open.
	-Navigate to the cloned repository directory and select it to open.

3-Import Project Dependencies
	-IntelliJ should prompt you to import dependencies automatically.
	-You can also manually trigger dependency import:
	Maven: Right-click the pom.xml file and select Add as Maven Project.

4-Configure the Project SDK
	-Go to File > Project Structure.
	-Under Project Settings > Project, set the Project SDK to your installed JDK version.

======================================================================================
*Running the Project
====================
1-Build the Project:
	-From IntelliJ, select Build > Build Project.
	-Ensure the project builds without errors.

2-Run the Application:
	-Go to Run > Run… and select the main class (usually has a public static void main method) to start the application.
	-Alternatively, you can right-click the main class file and select Run 'ClassName.main()'.
======================================================================================
Testing
=======
1-Run Unit Tests:
	-Go to Run > Run… > All Tests to execute all unit tests.
	-Alternatively, if you are using Maven or Gradle, you can run tests from the command line:
	-Maven: mvn test

2-View Test Results:
	-IntelliJ provides a test result window after executing tests, which displays success and failure details.
======================================================================================
Troubleshooting
================
-Project SDK Issues: Ensure the correct JDK version is selected under File > Project Structure > Project.
-Dependency Errors: If you encounter errors related to missing dependencies, re-import them:
-For Maven: Right-click pom.xml > Reload Maven Project.