mvn clean install
docker build -t odometerrollback-detector .
docker run -p 8080:8080 odometerrollback-detector
