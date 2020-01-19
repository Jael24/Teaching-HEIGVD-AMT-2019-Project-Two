cd docker/docker-topologies/
docker-compose up -d --build db_1 db_2
cd ../../microservices/1_manage_account/ 
mvn clean spring-boot:run &
cd ../../2_expose_entities/
mvn clean spring-boot:run
