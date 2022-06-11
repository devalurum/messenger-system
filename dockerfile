FROM amazoncorretto:11-alpine3.12
COPY build/libs/messenger-system.jar /
ENTRYPOINT ["java", "-Dspring.profiles.active=local", "-jar", "messenger-system.jar"]
EXPOSE 8080
