FROM maven:3.8.5-openjdk-11
WORKDIR /backend
COPY . .
RUN mvn clean install -Pno-tests
CMD mvn spring-boot:run -Pno-tests