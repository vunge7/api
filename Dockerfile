# === STAGE 1: BUILD ===
FROM ubuntu:latest AS build

RUN apt-get update && apt-get install -y openjdk-17-jdk maven

# Copia todo o codigo-fonte para dentro da imagem de build
COPY . .

# Gera o JAR
RUN mvn clean package -DskipTests

# === STAGE 2: RUNTIME ===
FROM openjdk:17-jdk-slim

EXPOSE 8080

# Copia o jar compilado
COPY --from=build target/api-0.0.1-SNAPSHOT.jar /api.jar

# Copia a pasta "reports" para o mesmo diretório do api.jar
COPY --from=build reports /reports

ENTRYPOINT ["java","-jar","/api.jar"]
