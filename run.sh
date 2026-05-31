#!/bin/bash
cd "$(dirname "$0")"
if [ ! -d out ]; then
  echo "Output folder not found. Building project first..."
  ./build.sh
fi
java -cp out com.airtribe.meditrack.Main
