#!/bin/bash

# Find the PID of the Java application process and kill it
PID=$(pgrep -f "java -jar backend/target/Tiger_Talks-0.0.1-SNAPSHOT.jar")
if [ -z "$PID" ]; then
  echo "No Java application process found."
else
  echo "Killing Java application process with PID: $PID"
  kill $PID
fi

# Ensure no process is using port 8085
PORT_PID=$(lsof -t -i:8085)
if [ -z "$PORT_PID" ]; then
  echo "No process found using port 8085."
else
  echo "Killing process on port 8085 with PID: $PORT_PID"
  kill -9 $PORT_PID
fi