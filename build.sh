#!/bin/bash
sbt assembly
sudo docker build -t mill .
