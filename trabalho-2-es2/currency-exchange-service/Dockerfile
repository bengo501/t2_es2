# Etapa de build com Maven para compilar o projeto e gerar o JAR
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
# Copia apenas o pom.xml e instala as dependências para aproveitar o cache
COPY pom.xml .
RUN mvn dependency:go-offline -B
# Copia o código fonte e executa o build
COPY src ./src
RUN mvn clean package -DskipTests
# Etapa final para uma imagem mais leve com apenas o JRE
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copia o JAR compilado da etapa de build anterior
COPY --from=build /app/target/currency-exchange-service-0.0.1-SNAPSHOT.jar app.jar
# Expondo a porta correta do serviço
EXPOSE 8000
# Limita o uso de memória e inicia o contêiner com o JAR
ENTRYPOINT ["java", "-Xmx512m", "-jar", "/app/app.jar"]
