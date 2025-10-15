# === STAGE 1: BUILD ===
FROM ubuntu:22.04 AS build

# Evitar prompts interativos
ENV DEBIAN_FRONTEND=noninteractive

# Instalar Java, Maven e dependências de build
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    maven \
    git \
    unzip \
    cabextract \
    wget \
    && rm -rf /var/lib/apt/lists/*

# Definir diretório de trabalho
WORKDIR /app

# Copiar todo o código do projeto
COPY . .

# Build do projeto Maven
RUN mvn clean install

# === STAGE 2: RUNTIME ===
FROM openjdk:17.0.9-jdk-slim

EXPOSE 8080

# Evitar prompts interativos
ENV DEBIAN_FRONTEND=noninteractive

# Instalar bibliotecas necessárias para JasperReports + fontes
RUN apt-get update && apt-get install -y \
    fontconfig \
    libfreetype6 \
    libx11-6 \
    libxext6 \
    libxrender1 \
    xfonts-75dpi \
    xfonts-base \
    wget \
    cabextract \
    && rm -rf /var/lib/apt/lists/*

# Instalar manualmente fontes Microsoft (Times New Roman, Arial, Courier)
RUN mkdir -p /usr/share/fonts/truetype/msttcorefonts && \
    cd /usr/share/fonts/truetype/msttcorefonts && \
    wget -q https://downloads.sourceforge.net/corefonts/andale32.exe && \
    wget -q https://downloads.sourceforge.net/corefonts/arial32.exe && \
    wget -q https://downloads.sourceforge.net/corefonts/courie32.exe && \
    cabextract andale32.exe && cabextract arial32.exe && cabextract courie32.exe && \
    fc-cache -fv

# Copiar JAR do build
COPY --from=build /app/target/api-0.0.1-SNAPSHOT.jar /app.jar

# Copiar reports
COPY --from=build /app/reports /reports

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "/app.jar"]
