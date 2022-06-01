# SkyNest
The Sky Nest cloud service is a scalable and safe system designed to provide data storage and user control.

## Frontend

## Backend
The backend server uses Spring Boot and is built using Maven and Java 11. The whole server can be built and deployed with docker.

> [How to install docker on Ubuntu](https://docs.docker.com/engine/install/ubuntu/). On Windows, first [install Ubuntu using WSL](https://docs.microsoft.com/en-us/windows/wsl/install) or follow this guide for [Docker Desktop](https://docs.docker.com/desktop/windows/install/).

### To run everything in docker:
- put the <code>.env</code> file next to <code>docker-compose.yml</code>
- start the backend server/databases with <code>docker compose up</code> from the same folder
  - use <code>CTRL+C</code> or <code>CMD+C</code> to stop docker

### To run the sever locally:
- start the database docker services <code>skynest-db</code> and <code>skynest-token-db-init</code>
    >For example, using <code>docker compose skynest-db skynest-token-db-init</code>.
- start the app using Maven, or use the IDE of your choice to do it for you
    > For example, use IntelliJ to open the folder <code>backend/</code> as a project and right-click on <code>SkyNestApplication.java</code> in <code>src/main/java/com/htecgroup/skynest/</code>.

### To reset the databases:
- make sure none of the database services are still running
  - check with <code>docker ps</code>
  >   For example, if <code>skynest-db</code> is still running, use <code>docker stop skynest-db</code>.
- delete the folder <code>.db_data/</code>

## QA
