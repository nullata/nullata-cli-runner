# Spring Boot CLI Runner Web Service
This is a simple Spring Boot application that exposes a web service for running command-line scripts on the server. The service accepts a POST request with a JSON payload containing the command to run, and returns the standard output; error output and return code of the command as a JSON response.

## Why does this exist?
This is the easy way around limited functionality in some container images, where ssh and IaS tools like Anisble are not an option and you don't want to spend the time to modify the docker image or you just can't. The original intent is to use this within a full linux os distribution container and expose the ws port to a service or network within a cluster and having it run cli scripts from an attached volume.

### Building the Application
To build the application, run the following command from the root directory of the project:

````
mvn clean package
````
This will compile the source code, run the tests, and create a runnable JAR file in the target/ directory.

### Running the Application
To run the application, use the following command:

````
java -jar target/wscli-1.0.0.jar
````
This will start the application and make it available at `http://localhost:8484`.

### Using the Web Service
To use the web service, send a POST request to the `/nullsca/wscli/v1/run` endpoint with a JSON payload containing the command to run. The command should be provided as a string value for the command key in the JSON object. For example:

````
POST /nullsca/wscli/v1/run HTTP/1.1
Content-Type: application/json

{
  "command": "ls -l /tmp"
}
````
The response from the server will be a JSON object with the following keys:

`output`:   The standard output and error output of the command.
`rc`:       The return code of the command.
`error`: If the command is not provided in the request, the server will return a 400 Bad Request error with a message indicating that the command parameter was not supplied.
If the command is interrupted or is not found, the server will return a 500 Internal Server error message indicating the supplied command.

### Testing the Application
To run the tests for the application, use the following command:

````
mvn test
````
This will run the JUnit tests in the src/test directory and report the results.

### Dependencies
The following dependencies are used in this project:

- Spring Boot Web: Provides the web framework for the application.
- JUnit 5: Provides the testing framework for the application.
- Hamcrest: Provides additional matchers for use in the tests
