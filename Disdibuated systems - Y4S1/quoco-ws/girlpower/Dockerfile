FROM openjdk:11.0.8-jre
ENV HOSTNAME=localhost
COPY target/*-jar-with-dependencies.jar /girlpower.jar
CMD java -jar /girlpower.jar ${container_name}