# Multistage Dockerfile for Spring Boot backend (Java 21)

#############################
# Builder
#############################
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy everything
COPY . /app

# Build depending on available files
RUN chmod +x mvnw gradlew || true && \
    if [ -f mvnw ]; then \
        echo "Building with mvnw..." && ./mvnw -B -DskipTests package; \
    elif [ -f pom.xml ]; then \
        echo "Building with mvn..." && mvn -B -DskipTests package; \
    elif [ -f gradlew ]; then \
        echo "Building with gradlew..." && chmod +x gradlew && ./gradlew bootJar -x test; \
    else \
        echo "No supported build file found (pom.xml, mvnw, gradlew)." >&2; exit 1; \
    fi && \
    JAR_PATH=$(ls target/*.jar 2>/dev/null || ls build/libs/*.jar 2>/dev/null) && \
    mkdir -p /app/dist && cp $JAR_PATH /app/dist/app.jar

#############################
# Runtime
#############################
FROM eclipse-temurin:21-jre AS runtime

ENV JAVA_OPTS="-Xms128m -Xmx512m"

WORKDIR /app

EXPOSE 8080

COPY --from=builder /app/dist/app.jar /app/app.jar

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
