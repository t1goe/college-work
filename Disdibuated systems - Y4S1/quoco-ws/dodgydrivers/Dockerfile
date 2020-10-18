FROM openjdk:11.0.8-jre
ENV HOSTNAME=localhost
COPY target/*-jar-with-dependencies.jar /dodgydrivers.jar
CMD java -jar /dodgydrivers.jar ${container_name}