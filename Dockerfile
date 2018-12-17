FROM openjdk:8-jdk-alpine

MAINTAINER Travis McChesney <travisj37@gmail.com>

COPY ./target/mailit-1.1.jar /usr/local/mailit/mailit.jar
COPY ./config.yml /etc/mailit/config.yml

EXPOSE 8080
EXPOSE 8081

CMD ["java", "-jar", "/usr/local/mailit/mailit.jar", "server", "/etc/mailit/config.yml"]
