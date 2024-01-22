# study_english
This project is a base to arrange my english lessons, all files needed to watching (books, audios and videos) there isn't here to avoid legal problems.

## requirements
you need have a angular 16.1.0 or higher, git, docker and docker compose

## How install
You just need a local clone of this project.

Build back and with command: ./study-english_backend/gradlew clean build

Build frontend with command: cd ./study_english_frontend && ng build

Change in the docker-compose.yaml your LOCAL_FILES_PATH

Now you just need run the command: cd .. && docker-compose up -d


