FROM openjdk:11.0.8-jre
ENV HOSTNAME=localhost
COPY target/*-jar-with-dependencies.jar /broker.jar
CMD java -jar /broker.jar ${container_name}