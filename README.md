# fusion-amsprogtest
Fusion Systems programming test

## Setup
1. Clone the git repository
1. cd to the git repository directory
   * `cd fusion-amsprogtest`
1. Run the [gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html#sec:using_wrapper) build to
verify that all the tests run and pass: 
   * `./gradlew clean build`
   * _The gradle wrapper will download the gradle runtime to your machine_ - Internet connection required
1. You should see the following output if the build and tests pass
   ```
   [barry@balibookair ~/dev/projects/fusion-amsprogtest (master)]$ ./gradlew clean build
   
   BUILD SUCCESSFUL in 2s
   5 actionable tasks: 5 executed
   ``` 

## IDEA project creation
1. Run the gradle task to create IntelliJ IDEA project files
   * `./gradlew idea`
1. Open the project directory in IDEA

## Coverage Report
You can view the unit test coverage report at [coverage-report/index.html](coverage-report/index.html)