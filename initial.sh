#!/usr/bin/env bash


# Docker
docker pull rabbitmq
docker run -d --hostname valkyrie --name valkyrie-harvest -p 4369:4369 -p 5671:5671 -p 5672:5672 -p 15672:15672 rabbitmq
docker exec valkyrie-harvest  rabbitmq-plugins enable rabbitmq_management

docker pull mssql
docker run -p 3306:3306 --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:latest
# Login at http://localhost:15672/ (or the IP of your docker host)
# Login at http://localhost:15672/ (or the IP of your docker host)
# using guest/guest


