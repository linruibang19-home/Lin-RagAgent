FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /workspace
COPY . .

RUN mvn -pl Lin-RagAgent-business/Lin-RagAgent-business-chat -am clean package -DskipTests \
    && cp "$(find Lin-RagAgent-business/Lin-RagAgent-business-chat/target -maxdepth 1 \
      -name 'Lin-RagAgent-business-chat-*.jar' ! -name '*-sources.jar' | head -n 1)" /workspace/app.jar

FROM eclipse-temurin:17-jre

WORKDIR /app
COPY --from=builder /workspace/app.jar /app/app.jar

EXPOSE 10000

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
