# === STAGE 1: BUILD ===
FROM maven:3.9.4-openjdk-17-slim AS build

# Definir diretório de trabalho
WORKDIR /app

# Copiar ficheiros de dependências primeiro para cache do Maven
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar todo o código-fonte
COPY src ./src

# Build do projeto
RUN mvn clean package -DskipTests

# === STAGE 2: RUNTIME ===
FROM openjdk:17-jdk-slim

# Expor porta da aplicação
EXPOSE 8080

# Instalar bibliotecas necessárias para JasperReports + fontes
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        fontconfig \
        libfreetype6 \
        libx11-6 \
        libxext6 \
        libxrender1 \
        ttf-mscorefonts-installer \
        ca-certificates \
    && fc-cache -f -v \
    && rm -rf /var/lib/apt/lists/*

# Copiar JAR gerado do build
COPY --from=build /app/target/api-0.0.1-SNAPSHOT.jar ./app.jar

# Copiar reports se necessário
COPY --from=build /app/reports ./reports

# Entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]
