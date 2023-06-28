#!/bin/bash
sbt clean
sbt assembly
sudo docker build -t mill .
