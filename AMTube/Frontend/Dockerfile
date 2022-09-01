# Stage 1: website rendering
FROM node:18-alpine AS builder

WORKDIR /usr/src/app
COPY . .
RUN npm install
RUN npm run build

# Stage 2: website hosting by nginx
FROM nginx:alpine

COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=builder /usr/src/app/dist/frontend /usr/share/nginx/html
EXPOSE 4200
# By default, nginx connects to port 80.
# Readapt when running the container, e.g. on the port 4200:
# docker run -d -p 4200:80 frontend-docker
