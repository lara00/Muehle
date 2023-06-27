FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1
ENV DISPLAY=host.docker.internal:0.0
RUN apt-get update && \
    apt-get install -y libxrender1 libxtst6 libxi6
WORKDIR /app
COPY Muehle-assembly-0.1.0-SNAPSHOT.jar bin/
EXPOSE 8080
CMD java -jar bin/Muehle-assembly-0.1.0-SNAPSHOT.jar