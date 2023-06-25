FROM adoptopenjdk/openjdk11:debian

# Install dependencies
RUN apt-get update && apt-get install -y \
    xvfb \
    xauth \
    x11vnc \
    x11-utils \
    xterm \
    curl

# Download and install sbt
RUN curl -L -o sbt.tar.gz https://github.com/sbt/sbt/releases/download/v1.5.5/sbt-1.5.5.tgz \
    && tar -xzf sbt.tar.gz \
    && rm sbt.tar.gz \
    && mv sbt /usr/local/sbt \
    && ln -s /usr/local/sbt/bin/sbt /usr/local/bin/sbt

ENV DISPLAY=:99

WORKDIR /Muehle
ADD . /Muehle

CMD Xvfb $DISPLAY -screen 0 1024x768x16 & \
    sleep 5 && \
    sbt test
