# SkyNest
The Sky Nest cloud service is a scalable and safe system designed to provide data storage and user control.

## Frontend

## Backend
The backend server runs in Spring Boot, and is built using Maven and Java 11.


To just run the backend, install docker first.

For how to install docker on Ubuntu go to here: https://docs.docker.com/engine/install/ubuntu/. If you're on Windows, you need to first install Ubuntu using WSL: https://docs.microsoft.com/en-us/windows/wsl/install.

Use <code>docker compose up</code> in the root folder (where <code>docker-compose.yml</code> is) to start the backend server/databases.
An <code>.env</code> file should also be in root. Use <code>CTRL+C</code> or <code>CMD+C</code> to stop docker.

To run the app locally, start the database docker services <code>skynest-db</code> and <code>skynest-token-db-init</code> - for example, using <code>docker compose skynest-db skynest-token-db-init</code>.
Then start the app using Maven, or use the IDE of your choice to do it for you. In IntelliJ, for example, open the folder <code>backend/</code> as a project and right-click on <code>SkyNestApplication.java</code> in <code>src/main/java/com/htecgroup/skynest/</code>.

## QA
