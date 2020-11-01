FROM openjdk:11.0.8-jre
COPY target/client-*-jar-with-dependencies.jar /client.jar
CMD java -jar /client.jar ${CONTAINER_NAME}