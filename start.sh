#!/bin/bash
SPRING_PROFILE="dev"
ENV_FILE=".env.${SPRING_PROFILE}"

echo ""
echo "=========================================================="
echo "STARTING DEVELOPMENT ENVIRONMENT"
echo "Active Profile: ${SPRING_PROFILE}"
echo "Loading environment from: ${ENV_FILE}"
echo "=========================================================="
echo ""

set -a
source "$ENV_FILE"
set +a

if [ -z "$REDIS_PORT" ]; then
    echo "[ERROR] REDIS_PORT not found in $ENV_FILE. Please check your file."
    exit 1
fi

echo "-> Starting Redis Server on port $REDIS_PORT..."
redis-server --port "$REDIS_PORT" --requirepass "$REDIS_PASSWORD"

echo ""
echo "=========================================================="
echo "APPLICATION FINISHED."
echo "=========================================================="