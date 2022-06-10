#FROM ubuntu:latest
#
#RUN apt-get update && apt-get install -y openjdk-17-jdk
#
#WORKDIR /usr/local/bin/
#
#ADD target/propertyrental-0.0.1-SNAPSHOT.jar .
#
#ENTRYPOINT ["java", "-jar", "propertyrental-0.0.1-SNAPSHOT.jar"]

FROM maven:3-openjdk-17-slim
EXPOSE 8080
COPY . /app/
WORKDIR /app
RUN mvn clean package -DskipTests
ENTRYPOINT echo java -jar target/propertyrental-0.0.1-SNAPSHOT.jar; exec java -jar target/propertyrental-0.0.1-SNAPSHOT.jar