FROM maven:3.6.1-jdk-8-alpine AS develop
WORKDIR /app
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn -e -B package