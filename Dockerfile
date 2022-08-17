ARG REPO=991420018350.dkr.ecr.us-east-1.amazonaws.com
FROM ${REPO}/openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]