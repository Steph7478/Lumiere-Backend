#!/bin/bash

cols=$(tput cols)

WHITE="\033[1;37m"
ORANGE="\033[38;5;208m"
BLUE="\033[1;34m"
RED="\033[1;31m"
RESET="\033[0m"

echo
echo -e "${WHITE}=== Latest Test Logs ===${RESET}"
echo

awk -v width="$cols" -v ORANGE="$ORANGE" -v BLUE="$BLUE" -v RED="$RED" -v RESET="$RESET" '
/^Caused by:/ {
    if (NR>1) { print "\n" }
    split($0,a,"Reason:")
    # trim spaces from a[2]
    reason=a[2]
    gsub(/^ +| +$/,"",reason)

    printf "%s%s%s", ORANGE, a[1], RESET

    if (reason != "") {
        printf "\n%sReason:%s%s", RED, reason, RESET
    }
    print ""
    next
}

/<<< FAILURE!/ {
    print "\n" BLUE $0 RESET
}
' target/surefire-reports/com.lumiere.LumiereApplicationTests.txt
