FROM openjdk:8-jdk-alpine

COPY tmp/auth-1.0-SNAPSHOT.jar 1_manage_account.jar
ENTRYPOINT ["java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker", "1_manage_account.jar"]

