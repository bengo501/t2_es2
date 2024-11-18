# Etapa de build com Maven para compilar o projeto e gerar o JAR
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml . 
# Instala apenas as dependências para usar cache
RUN mvn dependency:go-offline -B
# Copia o código fonte e executa o build
COPY src ./src
RUN mvn clean package -DskipTests
# Etapa final para uma imagem mais leve com apenas o JRE
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copia o JAR compilado da etapa anterior
COPY --from=build /app/target/api-gateway-0.0.1-SNAPSHOT.jar app.jar
# Expondo a porta usada pelo API Gateway
EXPOSE 8765
# Limita o uso de memória com parâmetros JVM
ENTRYPOINT ["java", "-Xmx512m", "-jar", "/app/app.jar"]
