# === STAGE 2: RUNTIME ===
FROM openjdk:17-jdk-slim

EXPOSE 8080

# Instalar bibliotecas necessárias para JasperReports + fontes Microsoft
ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y \
    fontconfig \
    libfreetype6 \
    libx11-6 \
    libxext6 \
    libxrender1 \
    xfonts-75dpi \
    xfonts-base \
    wget \
    && rm -rf /var/lib/apt/lists/*

# Instalar manualmente fontes Microsoft (Times New Roman, Arial, Courier)
RUN mkdir -p /usr/share/fonts/truetype/msttcorefonts && \
    cd /usr/share/fonts/truetype/msttcorefonts && \
    wget -q https://downloads.sourceforge.net/corefonts/andale32.exe && \
    wget -q https://downloads.sourceforge.net/corefonts/arial32.exe && \
    wget -q https://downloads.sourceforge.net/corefonts/courie32.exe && \
    # Extrair as fontes TTF
    cabextract andale32.exe && cabextract arial32.exe && cabextract courie32.exe && \
    fc-cache -fv

# Copiar JAR do build
COPY --from=build /app/target/api-0.0.1-SNAPSHOT.jar /app.jar

# Copiar reports
COPY --from=build /app/reports /reports

ENTRYPOINT ["java", "-jar", "/app.jar"]
