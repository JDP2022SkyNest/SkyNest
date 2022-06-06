FROM maven:3.8.5-openjdk-11
WORKDIR /backend
COPY . .
RUN mvn clean install -Pdocker
CMD mvn spring-boot:run -Pdocker