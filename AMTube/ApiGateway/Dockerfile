FROM maven:3.8.1-openjdk-11
COPY . .
RUN mvn install
WORKDIR /target
EXPOSE 9000
CMD ["java", "-jar", "ApiGateway-0.0.1-SNAPSHOT.jar"]