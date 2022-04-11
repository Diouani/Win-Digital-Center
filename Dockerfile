FROM openjdk:8-jdk-alpine
COPY target/WDC-0.0.1-SNAPSHOT.jar WinDigital.jar
ENTRYPOINT ["java","-jar","/WinDigital.jar"]