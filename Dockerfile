FROM openjdk:17-jdk-slim

# Install Maven
RUN apt-get update && apt-get install -y maven

WORKDIR /app
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Run the application
EXPOSE 8080
CMD ["java", "-jar", "target/*.jar"]