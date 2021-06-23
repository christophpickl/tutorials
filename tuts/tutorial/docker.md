# Docker Commands

## Basics

* `docker images` ... list images on my system
* `docker pull IMAGE_NAME` ... download a given image
* `docker run IMAGE_NAME` ... run a new container with this image
* `docker run -it IMAGE_NAME sh` .. run image with interactive shell
* `docker run -e SPRING_PROFILES_ACTIVE=dev IMAGE_NAME` .. active spring profile
* `docker run -p 80:80 IMAGE_NAME` .. -p maps ports local_machine:docker_internal
* `docker run -e SPRING_PROFILES_ACTIVE=dev -p 80:80 -t bookstore-api/v1.0` ... full fledge example
* `docker ps -a` ... list all running containers

## Image Handling

* `docker tag xxx` ... tag an image
* `docker push xxx` ... push an image to the configured repo
* `docker build -t bookstore-api:1.0 .` ... build an tag an image
* `docker rmi --force IMAGE_NAME_OR_ID` ... remove an image
* `docker rmi $(docker images -q)` ... remove all images

## Container Handling

* `docker container stop CONTAINER_ID` ... stop single container
* `docker container stop $(docker ps -a -q)` OR: `docker ps -q | xargs docker stop` ... stop all containers
* `docker rm $(docker ps -q -a)` OR: `docker ps -a -q | xargs docker rm` ... stop containers (maybe owning a lock)
    * seful when getting a “image is being used by stopped container”

## Volume Handling

* `docker volume ls` ... list mounted volumes
* `docker volume create my_volume` ... create volume (see: /var/lib/docker/volumes/my_volume)
* `docker run -v my_volume:/var/lib/mysql mysql` ... "volume mounting", map volumes
* `docker run -v /my/host/path:/foo/bar` ... "bind mounting", map absolute pre-existing dir
* `docker run --mount type-bind,source=/host,target=/container mysql` ... more current/verbose alternative

## Repository

* NOTE: defining image name "nginx"
    * implicitly assumes "nginx/nginx" (user-account / image-repository)
    * implicitly assumes "docker.io/nginx/nginx" (default registry, like maven-central)
* `docker login private-registry.io` ... access a non-default registry
    * `docker run private-registry.io/organization/image`
    * `docker image tag my_image private-registry.io/my_image`
    * `docker push/pull private-registry.io/my_image`

## Misc

* `docker logs CONTAINER_ID` ... get stdout
* `docker inspect CONTAINER_ID` ... get some details (env vars)
* `docker history IMAGE` ... get sizes
* `docker run —cpus=.5 (50% max) —memory=100m IMAGE` ... limit resources
* `docker -H1.2.3.4:1234` ... work remotely

# DOCKERFILE

* order is relevant; each line is "one layer" (reusable/cached)
* ENTRYPOINT vs CMD (easily overridden)

# Docker Compose Commands

* `docker-compose build`
* `docker-compose up`
* `docker-compose down`
* `docker-compose --env-file ./path/to/env.dev up`
* multi container setup with old fashioned __links__:
    * `docker run -d --name=db postgres` ... `-d` = in background
    * `docker run -d --name=my_app --link db:db myapp:latest` ... `-link db:db` = host resolution
    * do a `cat /etc/hosts` in running container to see DNS entry -nice :)
    * BUT: links are deprecated => use __docker swarm__ instead! (or kubernetes)
    * NOTE: `link 80` ... same as: `link db:db`
* create separate networks (frontend VS backend) in docker compose
