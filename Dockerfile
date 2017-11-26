FROM openjdk:8-jdk

COPY config.yml /data/application/config.yml
COPY /target/file-publisher-1.0-SNAPSHOT.jar /data/application/application.jar

WORKDIR /data/application

RUN java -version

RUN java -jar application.jar db migrate config.yml

CMD ["java","-jar","application.jar","server","config.yml"]

EXPOSE 8080-8081