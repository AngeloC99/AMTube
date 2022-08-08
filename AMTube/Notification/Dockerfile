FROM maven:3.8.1-openjdk-11
COPY . .
RUN mvn install
WORKDIR /target
EXPOSE 8081
CMD ["java", "-jar", "Notification-0.0.1-SNAPSHOT.jar"]
