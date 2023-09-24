FROM clojure:temurin-17-tools-deps AS build

WORKDIR /app

# Load dependencies
COPY deps.edn /app
RUN clj -P && clj -P -X:build

# Build uberjar
COPY src/ /app/src
COPY build.clj /app
RUN clj -T:build uber



FROM eclipse-temurin:17 AS run

WORKDIR /app

COPY --from=build /app/target/*-standalone.jar /app/standalone.jar

CMD ["java", "-jar", "standalone.jar"]
