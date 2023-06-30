# Stage 1: Build Stage
FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1 as builder
RUN apt-get update && \
    apt-get install -y libxrender1 libxtst6 libxi6
WORKDIR /app
COPY build.sbt ./
COPY project/plugins.sbt ./project/
COPY project/build.properties ./project/
RUN sbt update
COPY . ./
RUN sbt test:compile
RUN sbt assembly

# Stage 2: Production Stage
FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1
RUN apt-get update && \
    apt-get install -y libxrender1 libxtst6 libxi6 libfreetype6 libfontconfig1
WORKDIR /app
COPY --from=builder /app/Muehle-assembly-0.1.0-SNAPSHOT.jar ./MUEHLE/
EXPOSE 8080
CMD ["java", "-jar", "/app/MUEHLE/Muehle-assembly-0.1.0-SNAPSHOT.jar"]
