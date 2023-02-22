cd jenkins-compose
docker-compose down
docker rm container jenkins-compose
docker image rm --force jenkins-compose_my-jenkins
cd ..
mvn clean package
cd jenkins-compose
docker compose up