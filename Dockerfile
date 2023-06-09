FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1

RUN apt-get update && apt-get install -y \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libgl1-mesa-glx \
    libgtk-3-0 \
    openjfx \
    libgl1-mesa-dri \
    libgl1-mesa-dev \
    libcanberra-gtk-module \
    libcanberra-gtk3-module \
    default-jdk

WORKDIR /Muehle

COPY ./src ./src
COPY build.sbt ./
COPY ./project ./project
COPY ./target ./target

ENV DISPLAY=:0
EXPOSE 8080
CMD sbt run