# Etapa de build: compila el jar
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Etapa de runtime: solo el JRE
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos el jar generado (cualquier nombre)
COPY --from=build /app/target/*.jar app.jar

# Render pasa PORT por env; Spring Boot ya lo lee con server.port=${PORT:8080}
ENV PORT=8080

CMD ["java", "-jar", "app.jar"]
