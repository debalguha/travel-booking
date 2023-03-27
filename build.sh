docker rmi $(docker image ls|grep 'sb.*sv' |tr -s ' '| cut -d' ' -f3)
mvn clean package spring-boot:repackage  -f ./pom.xml
docker build -t travelapp/sbgatewaysv:latest ./travel-gateway
docker build -t travelapp/sbflightsv:latest ./flight-booking-service
docker build -t travelapp/sbhotelsv:latest ./hotel-booking-service
docker build -t travelapp/sbtaxisv:latest ./taxi-booking-service
docker build -t travelapp/sbtravelsv:latest ./travel-booking-app