FROM maven:3.8.1-openjdk-11
COPY . .
RUN mvn install
WORKDIR /target
EXPOSE 8080
CMD ["java", "-jar", "userManagement-0.0.1-SNAPSHOT.jar"]