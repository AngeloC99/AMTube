# Running the Docker container
From the terminal, go to the directory of the service (superuser privileges may be necessary).
## To build the image

```bash
docker build -t api-gateway-docker .
```

## To run the container

```bash
docker run -d -p 9000:9000 api-gateway-docker
```
