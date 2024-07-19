#!/bin/bash

# Find the PID of the Java application process and kill it
PID=$(pgrep -f "java -jar target/Tiger_Talks-0.0.1-SNAPSHOT.jar")
if [ -z "$PID" ]; then
  echo "No Java application process found."
else
  echo "Killing Java application process with PID: $PID"
  kill $PID
fi