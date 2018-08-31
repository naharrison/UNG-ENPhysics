#!/bin/csh -f

mvn exec:java -Dexec.mainClass="$1"
