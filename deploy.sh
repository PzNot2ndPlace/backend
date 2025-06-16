#!/bin/bash

docker pull brawleryura1/notes-app:latest
docker stop notes-app || true
docker rm notes-app || true
docker run --name notes-app -detach -p 8080:8080 -e APP_VERSION=latest brawleryura1/notes-app:latest
