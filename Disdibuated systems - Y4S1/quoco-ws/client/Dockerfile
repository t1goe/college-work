FROM openjdk:11.0.8-jre
ENV HOSTNAME=localhost
COPY target/*-jar-with-dependencies.jar /client.jar
CMD java -jar /client.jar ${container_name}