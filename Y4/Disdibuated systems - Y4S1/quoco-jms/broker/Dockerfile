FROM openjdk:11.0.8-jre
COPY target/broker-*-jar-with-dependencies.jar /broker.jar
CMD java -jar /broker.jar ${CONTAINER_NAME}