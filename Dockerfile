# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

# Make port 6001 available to the world outside this container
EXPOSE 6001

# The application's jar file
ARG JAR_PATH=build/libs/

# Add the application's jar to the container
COPY ${JAR_PATH}service2-0.0.1-SNAPSHOT.jar service2.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/service2.jar"]
