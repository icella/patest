#!/usr/bin/env bash

 echo "statistics..."
java -jar ../lib/perf4j-0.9.16.jar ../logs/apptest.log -r -t 90000 -o upc.txt

echo "graph..."
java -jar ../lib/perf4j-0.9.16.jar -g upc.html ../logs/apptest.log
