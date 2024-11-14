FROM openjdk:11-jre
COPY target/employee-management-0.0.1-SNAPSHOT.jar employee-management-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "employee-management-0.0.1-SNAPSHOT.jar"]
