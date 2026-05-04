#!/usr/bin/env bash
set -euo pipefail

mkdir -p lib

curl -L \
  -o lib/sqlite-jdbc-3.36.0.3.jar \
  https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.36.0.3/sqlite-jdbc-3.36.0.3.jar
