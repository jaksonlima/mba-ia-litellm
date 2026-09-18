FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace
COPY gradlew settings.gradle build.gradle ./
COPY gradle gradle
RUN ./gradlew --no-daemon dependencies -q
COPY src src
RUN ./gradlew --no-daemon bootJar -q \
 && java -Djarmode=tools -jar build/libs/app.jar extract --layers --destination extracted

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/extracted/dependencies/ ./
COPY --from=build /workspace/extracted/spring-boot-loader/ ./
COPY --from=build /workspace/extracted/snapshot-dependencies/ ./
COPY --from=build /workspace/extracted/application/ ./
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
