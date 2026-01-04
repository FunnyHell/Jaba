# Этап 1: Сборка
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Этап 2: Запуск
# Используем актуальный образ Eclipse Temurin
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Копируем собранный jar
COPY --from=build /app/target/*.jar app.jar

# Создаем папку для аватар
RUN mkdir -p uploads/avatars

ENTRYPOINT ["java", "-jar", "app.jar"]