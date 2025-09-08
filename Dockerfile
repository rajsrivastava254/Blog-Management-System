# Multistage Dockerfile for a Spring Boot backend (Java 21)
# - Works with Maven (pom.xml / mvnw) or Gradle (build.gradle / gradlew)
# - Builder stage produces a single runnable jar and copies it into a small runtime image
# - Usage:
#   docker build -t blog-backend .
#   docker run -p 8080:8080 blog-backend

#############################
# Builder
#############################
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy everything (simpler and more robust for typical projects).
# If build time matters, you can optimize by copying only pom.xml/mvnw/.mvn first.
COPY . /app

# Make wrappers executable if present, then build using whichever tooling exists.
# We skip tests for faster image builds; remove -DskipTests / -x test if you want tests run.
RUN chmod +x mvnw gradlew || true \
  && /bin/bash -lc '
    if [ -f mvnw ]; then
      echo "Building with mvnw..." && ./mvnw -B -DskipTests package;
    elif [ -f pom.xml ]; then
      echo "Building with mvn..." && mvn -B -DskipTests package;
    elif [ -f gradlew ]; then
      echo "Building with gradlew..." && chmod +x gradlew && ./gradlew bootJar -x test;
    else
      echo "No supported build file found (pom.xml, mvnw, gradlew)." >&2; exit 1;
    fi

    # find the produced jar (target/*.jar for maven, build/libs/*.jar for gradle) and copy to /app/dist/app.jar
    jar_path="$(ls target/*.jar 2>/dev/null || true)"
    if [ -z "$jar_path" ]; then
      jar_path="$(ls build/libs/*.jar 2>/dev/null || true)"
    fi
    if [ -z "$jar_path" ]; then
      echo "Could not find built jar in target/ or build/libs/." >&2; exit 1;
    fi
    mkdir -p /app/dist
    cp $jar_path /app/dist/app.jar
  '

#############################
# Runtime
#############################
FROM eclipse-temurin:21-jre AS runtime

# Optional: set default Java options. You can override at runtime: docker run -e JAVA_OPTS="-Xmx512m" ...
ENV JAVA_OPTS="-Xms128m -Xmx512m"

WORKDIR /app

# Expose the port commonly used by Spring Boot. Change if your app listens on a different port.
EXPOSE 8080

# Copy the fat jar produced by the builder
COPY --from=builder /app/dist/app.jar /app/app.jar

# Default entrypoint
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
