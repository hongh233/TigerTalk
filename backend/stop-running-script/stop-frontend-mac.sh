#!/bin/bash

# Find the PID of the npm start process and kill it along with its children
PIDS=$(pgrep -f "npm start")
if [ -z "$PIDS" ]; then
  echo "No npm start process found."
else
  echo "Killing npm start processes with PIDs: $PIDS"
  pkill -TERM -P $PIDS
  pkill -TERM -f "npm start"
  pkill -9 -f "npm start"
fi

# Ensure no process is using port 3000
PORT_PID=$(lsof -t -i:3000)
if [ -z "$PORT_PID" ]; then
  echo "No process found using port 3000."
else
  echo "Killing process on port 3000 with PID: $PORT_PID"
  kill -9 $PORT_PID
fi