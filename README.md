# Teaching-HEIGVD-AMT-2019-Project-Two
## Introduction
This application has been created for the course of AMT in 2019. This application uses Spring in order to create two API. The first API allow to manage account. The second API expose 2 mains entities : Movie and Actor, and a Character entity which links Actor and Movie.

## Setup for development

- Make sure you have `git` installed.
- Clone the repo on your local machine.

## Running the application

- Make sure you have `docker` and `docker-compose` installed.
- Run the script `ProjectTwo.sh` located in the root folder.
- The APIs are now running on `http://localhost:8080/api/` and `http://localhost:8081/api/`

## Connecting to the application

- Create a user with the POST endpoint, then you can use the login endpoint to get a JWT token. You can now use every endpoint.

## Other documentation

[Functionalities](/doc/Functionalities.md)

[Implementation](/doc/Implementation.md)

[Testing](/doc/Testing.md)

[Performance](/doc/Performance.md)

[Known Bugs](/doc/KnownBug.md)

