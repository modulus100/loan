FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=build/libs/loan-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]