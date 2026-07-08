# Estágio 1: Build do projeto usando Maven e Java 17
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Estágio 2: Execução do projeto usando uma imagem Java 17 ativa e oficial
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /target/meuBusao-0.0.1-SNAPSHOT.jar meuBusao.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "meuBusao.jar"]