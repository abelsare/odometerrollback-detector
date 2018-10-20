FROM openjdk:8-jre

EXPOSE 8080

ADD target/odometerrollback-detector-service.jar odometerrollback-detector/

CMD ["bash", "-c", "java -jar odometerrollback-detector/odometerrollback-detector-service.jar"]