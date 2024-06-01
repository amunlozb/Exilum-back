# Use the official Maven/Java 17 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3.8.4-openjdk-17-slim AS build-env

# Set the working directory to /app
WORKDIR /app

# Copy the pom.xml file to download dependencies
COPY pom.xml ./

# Copy local code to the container image.
COPY src ./src

# Download dependencies and build a release artifact.
RUN mvn package -DskipTests

# Use OpenJDK for base image.
# https://hub.docker.com/_/openjdk
# https://docs.docker.com/develop/develop-images/multistage-build/#use-multi-stage-builds
FROM openjdk:17-jdk-slim

# Copy the jar to the production image from the builder stage.
COPY --from=build-env /app/target/demo-*.jar /demo.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the web service on container startup.
CMD ["java", "-jar", "/demo.jar"]
