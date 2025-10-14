# === STAGE 1: BUILD ===
FROM maven:3.9.8-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar pom.xml e baixar dependências (cache eficiente)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar todo o código-fonte e gerar o .jar
COPY . .
RUN mvn clean package -DskipTests

# === STAGE 2: RUNTIME ===
FROM openjdk:17-jdk-slim

# Instalar bibliotecas necessárias para JasperReports (fontes, gráficos, etc.)
RUN apt-get update && apt-get install -y \
    fontconfig \
    libfreetype6 \
    libx11-6 \
    libxext6 \
    libxrender1 \
    fonts-dejavu \
    debconf-utils \
    && echo "ttf-mscorefonts-installer msttcorefonts/accepted-mscorefonts-eula select true" | debconf-set-selections \
    && apt-get install -y ttf-mscorefonts-installer \
    && fc-cache -f -v \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

EXPOSE 8080

# Copiar o jar gerado e os relatórios
COPY --from=build /app/target/api-0.0.1-SNAPSHOT.jar /app/api.jar
COPY --from=build /app/reports /app/reports

ENTRYPOINT ["java", "-jar", "/app/api.jar"]
