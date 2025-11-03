#!/usr/bin/env bash

set -euo pipefail

echo "### Pre-filled Endpoint Caller ###"

read -rp "Save cookies to file? (y/n) [n]: " save_cookies
save_cookies=${save_cookies:-n}
save_cookies=${save_cookies,,}

COOKIE_FILE="cookies.txt"
if [[ "$save_cookies" == "y" || "$save_cookies" == "yes" ]]; then
    echo "Cookies will be saved to $COOKIE_FILE"
    COOKIE_OPT=(-c "$COOKIE_FILE" -b "$COOKIE_FILE")
else
    COOKIE_OPT=()
fi

while true; do
    echo
    echo "Choose an endpoint to call (or leave empty to quit):"
    echo "1) /register (pre-filled JSON)"
    echo "2) /login (pre-filled JSON)"
    echo "3) Custom URL"
    read -rp "Enter choice: " choice

    case "$choice" in
        1)
            URL="http://localhost:8080/api/v1/auth/register"
            METHOD="POST"
            BODY='{"email": "test2@test2.com", "name": "test", "password": "test.123"}'
            ;;
        2)
            URL="http://localhost:8080/api/v1/auth/login"
            METHOD="POST"
            BODY='{"email": "test2@test2.com", "password": "test.123"}'
            ;;
        3)
            read -rp "Enter full URL: " URL
            read -rp "HTTP Method (GET/POST/PUT/DELETE) [POST]: " METHOD
            METHOD=${METHOD:-POST}
            echo "Enter JSON body (or leave empty for none):"
            read -rp "Body: " BODY
            ;;
        "")
            echo "Exiting."
            break
            ;;
        *)
            echo "Invalid choice."
            continue
            ;;
    esac

    if [[ -n "$BODY" ]]; then
        echo "Current JSON body:"
        echo "$BODY"
        read -rp "Do you want to modify it? (y/n) [n]: " modify
        modify=${modify:-n}
        modify=${modify,,}
        if [[ "$modify" == "y" || "$modify" == "yes" ]]; then
            echo "Enter new JSON body:"
            read -rp "Body: " BODY
        fi
    fi

    CMD=(curl "${COOKIE_OPT[@]}" -v -X "$METHOD" -H "Content-Type: application/json" "$URL")
    if [[ -n "$BODY" ]]; then
        TMPFILE=$(mktemp)
        printf '%s' "$BODY" > "$TMPFILE"
        CMD+=(--data @"$TMPFILE")
    fi

    echo "Executing: $METHOD $URL"
    "${CMD[@]}"
    echo -e "\n-------------------------------\n"
done

if [[ "$save_cookies" == "y" || "$save_cookies" == "yes" ]]; then
    echo "Cookies saved in $COOKIE_FILE"
fi

echo "Done."
