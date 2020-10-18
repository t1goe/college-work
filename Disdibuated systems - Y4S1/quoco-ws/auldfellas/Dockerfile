FROM openjdk:11.0.8-jre
ENV HOSTNAME=localhost
COPY target/*-jar-with-dependencies.jar /auldfellas.jar
CMD java -jar /auldfellas.jar ${container_name}