# Edwin Meck - QA
# Coding Challenge - Marvel API - v1 (PART TWO)

## Running The Tests
1- Google Chrome & Chrome Driver must both be installed to run the Selenium Tests. It will need to be added to a directory called 'drivers', which should be located in the Project Root.
   Chrome Driver can be downloaded here: https://sites.google.com/a/chromium.org/chromedriver/downloads. The Chrome Driver which matches the User's current Chrome version should be downloaded.
2- Character Ids to be used in the tests must be stored in a file './test/resources/characterIdsTestData.txt' There must be one id per line and no trailing or leading whitespaces. 
3- Public and private api keys are required for authentication on any calls made. These must be stored in "/src/main/resources/application.properties". 

The Tests can be run in two ways:

### IDE
The first is through the IDE; in Intellij, this can be done by many ways, such as pressing the Green Arrow next to the Test, or right clicking the Test in the Project Structure.

### Command Line
The second way of running is via the Command Line; If you don't have Maven Installed, this needs to be installed on your machine, and the location set as an Environment Variable on your machine. 
In addition, you must be using a JDK (as opposed to JRE), and have your JAVA_HOME Environment Variable pointing to that location.
All the required dependencies will be downloaded by maven.
Once done, the tests can be run simply via the following command from the Project Root Directory:

````````````
mvn test
````````````
## Comments
Selenium has been used for confirming what is visibly displayed on the web pages. 
In a professional environment,I would discuss with the team to use htmlUnit instead, which is faster and does not need webdrivers or a browser. 
Since the tests are few and clear at this stage, I decided to use JsonPath to get data from the responses, 
but I would recommend deserialising the response into Java Objects, which are easier to manipulate and validate.