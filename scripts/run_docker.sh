#!/usr/bin/env bash

CONTAINER_NAME=spring-boot-blog-demo
echo -e "\nSet docker container name as ${CONTAINER_NAME}\n"
IMAGE_NAME=${CONTAINER_NAME}:dev
echo -e "\nSet docker image name as ${IMAGE_NAME}\n"
PORT=80
PORTS=443
echo -e "Set docker image PORT to ${PORT}\n"
echo -e "Set docker image PORT to ${PORTS}\n"

echo -e "Create uber jar...\n"
mvn clean package

echo -e "\nStop running Docker containers with image tag ${CONTAINER_NAME}, and remove them...n"
docker stop $(docker ps -a | grep ${CONTAINER_NAME} | awk '{print $1}')
docker rm $(docker ps -a | grep ${CONTAINER_NAME} | awk '{print $1}')

echo -e "\nDocker build image with name ${IMAGE_NAME}...\n"
docker build -t ${IMAGE_NAME} -f docker/Dockerfile .

echo -e "\nStart Docker container of the image ${IMAGE_NAME} with name ${CONTAINER_NAME}...\n"
docker run -it -p ${PORT}:${PORT} -p ${PORTS}:${PORTS} --name ${CONTAINER_NAME} --restart unless-stopped ${IMAGE_NAME}