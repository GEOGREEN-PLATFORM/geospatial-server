FROM openjdk:21-slim
LABEL authors="GEOGREEN-PLATFORM"
COPY target/geospatial-server*.jar /geospatial-server.jar
ENTRYPOINT ["java", "-jar", "/geospatial-server.jar"]