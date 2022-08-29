# Running the Docker container

## To set up RabbitMQ
First, setting up a RabbitMQ and an ElasticSearch container are needed to ensure the proper behavior of the video container.
So, download the images from Docker Hub through the command (superuser privileges may be necessary in the following commands):

```bash
docker pull rabbitmq:3-management
```

Then, run the container:

```bash
docker run -d -p 15672:15672 -p 5672:5672 rabbitmq:3-management
```

It is also required to insert the AWS credentials needed for the communication with an S3 bucket for saving the videos.

```bash
cloud.aws.credentials.access-key=YOUR_AWS_ACCESS_KEY
cloud.aws.credentials.secret-key=YOUR_AWS_SECRET_KEY
```

Eventually, from the terminal, go to the directory of the Notification service.

## To build the image

```bash
docker build -t video-docker .
```

## To run the container

```bash
docker run -d -p 8082:8082 video-docker
```
