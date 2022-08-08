# Running the Docker container
From the terminal, go to the directory of the service (superuser privileges may be necessary).
## To build the image

```bash
docker build -t notification-docker .
```

## To run the container

```bash
docker run -it -p 8081:8081 notification-docker
```
