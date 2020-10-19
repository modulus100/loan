FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=build/libs/loan-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]