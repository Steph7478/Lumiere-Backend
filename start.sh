#!/bin/bash

SPRING_PROFILE="dev"
ENV_FILE=".env.${SPRING_PROFILE}"

REDIS_PID=
MINIO_PID=

cleanup() {
    echo ""
    echo "=========================================================="
    echo "🚨 Shutting down background services..."
    
    if [ ! -z "$REDIS_PID" ]; then
        kill "$REDIS_PID"
        echo "✅ Redis (PID $REDIS_PID) stopped."
    fi

    if [ ! -z "$MINIO_PID" ]; then
        kill "$MINIO_PID"
        echo "✅ MinIO (PID $MINIO_PID) stopped."
    fi
    
    echo "=========================================================="
    exit 0
}

trap cleanup SIGINT

echo ""
echo "=========================================================="
echo "STARTING DEVELOPMENT ENVIRONMENT"
echo "Active Profile: ${SPRING_PROFILE}"
echo "Loading environment from: ${ENV_FILE}"
echo "=========================================================="
echo ""

set -a
if [ -f "$ENV_FILE" ]; then
    source "$ENV_FILE"
else
    echo "[ERROR] Environment file not found: $ENV_FILE"
    exit 1
fi
set +a
echo "Environment loaded successfully."

echo "-> Starting Redis Server on port $REDIS_PORT..."
redis-server --port "$REDIS_PORT" --requirepass "$REDIS_PASSWORD" &
REDIS_PID=$!

echo "-> Starting MinIO Server (API: ${MINIO_ENDPOINT_URL})..."
minio.exe server ./minio_data --console-address ":9001" &
MINIO_PID=$!

sleep 5

echo "-> Configuring MinIO bucket policies..."
mc.exe alias set local http://localhost:9000 "$MINIO_ROOT_USER" "$MINIO_ROOT_PASSWORD"

mc.exe mb --ignore-existing local/product-pictures

mc.exe anonymous set download local/product-pictures

echo "✅ Bucket 'product-pictures' is now PUBLIC."

echo ""
echo "-> Services started. Press [Ctrl + C] to stop all services and exit."
echo "------------------------------------------------------------"

wait

# echo "-> Starting Spring Boot Application..."
# export SPRING_PROFILES_ACTIVE="${SPRING_PROFILE}"
# exec mvn spring-boot:run