FROM node:16-alpine as build

WORKDIR /app
COPY package*.json ./

RUN npm install -g @angular/cli@16 && npm install


COPY . .
RUN ng build --configuration production


FROM nginx:alpine

COPY --from=build /app/dist/angular-frontend /usr/share/nginx/html
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
