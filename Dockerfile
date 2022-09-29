<<<<<<< HEAD
FROM openjdk:18-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
=======
FROM openjdk:18-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
>>>>>>> ee69ac6ca2c587f28cd794a115ac0f2f3d5f850f
ENTRYPOINT ["java","-jar","/app.jar"]