FROM nginx
RUN apt-get update && apt-get upgrade -y
RUN apt-get install -y build-essential
RUN curl -fsSL https://deb.nodesource.com/setup_lts.x | bash -
RUN apt-get install -y nodejs

WORKDIR /app
COPY package*.json ./
COPY . .
COPY package.json /app

RUN npm install
RUN npm install -g @angular/cli
RUN npm run build
RUN cp -r app/dist/frontend /usr/share/nginx/html
RUN rm -rf node_modules
