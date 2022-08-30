# Running the Docker container

## To set up RabbitMQ
First, setting up a RabbitMQ container is needed to ensure the proper behavior of the video container.
So, download the images from Docker Hub through the command (superuser privileges may be necessary in the following commands):

```bash
docker pull rabbitmq:3-management
docker pull docker.elastic.co/elasticsearch/elasticsearch:7.15.2
```

Then, run the container:

```bash
docker run -d -p 15672:15672 -p 5672:5672 rabbitmq:3-management
docker run -d --name es7152 -p 9200:9200 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.15.2
```

It is also required to insert the AWS credentials needed for the communication with an S3 bucket for saving the videos.

```bash
cloud.aws.credentials.access-key=YOUR_AWS_ACCESS_KEY
cloud.aws.credentials.secret-key=YOUR_AWS_SECRET_KEY
```

Eventually, from the terminal, go to the directory of the video service.

## To build the image

```bash
docker build -t video-docker .
```

## To run the container

```bash
docker run -d -p 8082:8082 video-docker
```
