mkdir tmp

mvn clean package -f ../../../microservices/2_expose_entities/pom.xml
cp ../../../microservices/2_expose_entities/target/expose_entities-1.0-SNAPSHOT.jar ./tmp/

# docker build -t stalkerlog-auth/api .
