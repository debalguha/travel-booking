docker rmi $(docker image ls|grep 'sb.*sv' |tr -s ' '| cut -d' ' -f3)
mvn clean package -f ./travel-booking/pom.xml
docker build -t sbgatewaysv:latest ./travel-gateway
docker build -t sbflightsv:latest ./flight-booking-service
docker build -t sbhotelsv:latest ./hotel-booking-service
docker build -t sbtaxisv:latest ./taxi-booking-service
docker build -t sbtravelsv:latest ./travel-booking-app