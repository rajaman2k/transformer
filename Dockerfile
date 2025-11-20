FROM openjdk:17-jdk
WORKDIR /app
COPY . .
RUN chmod +x mvnw && ./mvnw clean package -DskipTests
EXPOSE 8080
CMD ["java", "-jar", "target/monitoring-0.0.1-SNAPSHOT.jar"]