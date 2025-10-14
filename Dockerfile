# === STAGE 2: RUNTIME ===
FROM openjdk:17-jdk-slim

# Instalar bibliotecas necessárias para JasperReports (fontes, gráficos, etc.)
RUN apt-get update && apt-get install -y \
    fontconfig \
    libfreetype6 \
    libx11-6 \
    libxext6 \
    libxrender1 \
    && echo "msttcorefonts msttcorefonts/accepted-mscorefonts-eula select true" | debconf-set-selections \
    && apt-get install -y ttf-mscorefonts-installer \
    && rm -rf /var/lib/apt/lists/*

EXPOSE 8080

# Copiar o jar
COPY --from=build target/api-0.0.1-SNAPSHOT.jar /api.jar

# Copiar a pasta de relatórios
COPY --from=build reports /reports

ENTRYPOINT ["java","-jar","/api.jar"]