# Stage 1: Build with Maven + Java 21
FROM maven:3.9.5-eclipse-temurin-21 AS build

WORKDIR /app

# Copy only pom first for dependency caching
COPY Blog-Management-System/pom.xml ./
RUN mvn dependency:go-offline -B

# Copy source
COPY Blog-Management-System/src ./src
# If you have other build files (like .mvn, settings.xml), copy them too:
# COPY Blog-Management-System-backend/.mvn ./.mvn

# Build package (skip tests in CI)
RUN mvn -B package -DskipTests

# Stage 2: Run on JDK 21
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy the fat jar from the builder
COPY --from=build /app/target/*.jar app.jar

# (Optional) Set Java options from env (example)
ENV JAVA_OPTS=""

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]
