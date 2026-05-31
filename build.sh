#!/bin/bash
set -e
cd "$(dirname "$0")"
rm -rf out
mkdir -p out
find src/main/java -name '*.java' > sources.txt
javac -d out @sources.txt
rm sources.txt

echo "Build succeeded."
java -cp out com.airtribe.meditrack.Main
