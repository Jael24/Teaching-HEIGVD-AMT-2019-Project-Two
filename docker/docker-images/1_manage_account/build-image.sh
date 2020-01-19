mkdir tmp

mvn clean package -f ../../../microservices/1_manage_account/pom.xml
cp ../../../microservices/1_manage_account/target/auth-1.0-SNAPSHOT.jar ./tmp/

# docker build -t stalkerlog-auth/api .
