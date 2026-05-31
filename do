#!/usr/bin/env bash
set -e
cd "$(dirname "$0")"

if [ "$#" -lt 1 ]; then
  echo "Usage: ./do {build|run|test}"
  exit 1
fi

case "$1" in
  build)
    ./build.sh
    ;;
  run)
    ./run.sh
    ;;
  test)
    if [ ! -d out ]; then
      ./build.sh
    fi
    java -cp out com.airtribe.meditrack.test.TestRunner
    ;;
  *)
    echo "Usage: ./do {build|run|test}"
    exit 1
    ;;
esac
