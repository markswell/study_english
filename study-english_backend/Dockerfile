FROM openjdk:17

ENV MEDIAS_CONTEXT_PATH=/opt

ARG JAR_FILE=./build/libs/study-english.jar
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]