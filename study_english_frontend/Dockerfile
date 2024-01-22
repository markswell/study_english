FROM nginx:latest

ARG BUNDLE=./study_english_frontend/dist/study_english
ARG SERVER_CONFIG=./study_english_frontend/nginx.conf

ADD ${BUNDLE} /usr/share/nginx/html/

COPY ${SERVER_CONFIG}  /etc/nginx/conf.d/default.conf

EXPOSE 80