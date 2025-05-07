# Etapa 1: Imagem base com o JDK
FROM eclipse-temurin:17-jdk-alpine
# Define o diretório de trabalho
WORKDIR /app
# Copia o JAR já gerado para dentro do contêiner
COPY target/*.jar app.jar
# Expondo a porta 8080, padrão do Spring Boot
EXPOSE 8080
# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app/app.jar"]