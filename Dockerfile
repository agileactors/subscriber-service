FROM openjdk:11-jre

COPY target/subscriber-service*.jar ./subscriber-service.jar

ENTRYPOINT ["java", "-jar", "subscriber-service.jar"]