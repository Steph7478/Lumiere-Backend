#!/bin/bash

SCRIPTS_DIR="$(dirname "$0")/src/main/java/com/lumiere/scripts"

case $1 in
	logs) "$SCRIPTS_DIR/FailureTestLog.sh";;
	endpoints)  "$SCRIPTS_DIR/Endpoints.sh";;
	*) echo "Usage: ./ops.sh { logs } { endpoints }";;
esac
