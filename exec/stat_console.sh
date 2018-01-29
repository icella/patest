#!/usr/bin/env bash
set -e

log_name=$1
 echo "statistics..."
java -jar stat/perf4j-0.9.16.jar logs/apptest.log -r -t 90000 -o stat/results/${log_name}.txt

echo "graph..."
java -jar stat/perf4j-0.9.16.jar -g stat/results/${log_name}.html logs/apptest.log
