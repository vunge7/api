# === STAGE 1: BUILD ===
FROM ubuntu:latest AS build

# Instalar dependências e o Maven
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    maven \
    && rm -rf /var/lib/apt/lists/*

# Definir diretório de trabalho
WORKDIR /app

# Copiar todos os ficheiros do projeto
COPY . .

# Gerar o JAR (sem executar testes)
RUN mvn clean package -DskipTests


# === STAGE 2: RUNTIME ===
FROM openjdk:17-jdk-slim

# Instalar bibliotecas necessárias para JasperReports (fontes, gráficos, etc.)
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    fontconfig \
    libfreetype6 \
    libx11-6 \
    libxext6 \
    libxrender1 \
    wget \
    cabextract \
    xfonts-utils && \
    echo "msttcorefonts msttcorefonts/accepted-mscorefonts-eula select true" | debconf-set-selections && \
    apt-get install -y ttf-mscorefonts-installer && \
    fc-cache -f -v && \
    rm -rf /var/lib/apt/lists/*

# Definir diretório de trabalho
WORKDIR /app

# Expor a porta do Spring Boot
EXPOSE 8080

# Copiar o JAR da build anterior
COPY --from=build /target/api-0.0.1-SNAPSHOT.jar -api.jar

# Copiar a pasta de relatórios JasperReports
COPY --from=build /reports /reports

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "/api.jar"]
