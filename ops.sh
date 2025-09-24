#!/bin/bash

SCRIPTS_DIR="$(dirname "$0")/src/main/java/com/lumiere/scripts"

case $1 in
	logs) "$SCRIPTS_DIR/FailureTestLog.sh";;
	*) echo "Usage: ./ops.sh { logs }";;
esac
