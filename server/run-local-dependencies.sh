#!/bin/bash
command_exists () {
    type "$1" &> /dev/null ;
}

clear

if !(docker compose -f docker/local/docker-compose.yml pull); then
	echo "STARTING THE DOCKER..."
	sudo systemctl start docker.service
	
	if !(docker compose -f docker/local/docker-compose.yml pull); then
		exit 1
	fi
fi

docker compose -f docker/local/docker-compose.yml up -d;
echo "OPENING THE DATABASE"
sleep 1s
docker exec -it hacken_io_db bash
docker compose -f docker/local/docker-compose.yml down;

if [[ "$*" == *"--rm-db"* ]]; then
	echo "REMOVING THE DATABASE"
	sudo rm -R ./docker/local/db
fi