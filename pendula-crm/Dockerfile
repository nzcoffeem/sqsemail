FROM openjdk:11
COPY build/libs/pendula-crm-0.0.1-SNAPSHOT.*.jar app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod","/app.jar"]