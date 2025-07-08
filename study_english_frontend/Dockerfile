FROM nginx:latest

ARG BUNDLE=./dist/study_english
ARG SERVER_CONFIG=./nginx.conf

ADD ${BUNDLE} /usr/share/nginx/html/

COPY ${SERVER_CONFIG}  /etc/nginx/conf.d/default.conf

EXPOSE 80
