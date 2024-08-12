FROM openjdk:17-jre-slim

LABEL maintainer="group02@dal.ca"

COPY target/Tiger_Talks-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "/app.jar"]