curl http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email": "test@test.com", "name": "test", "password": "test.123"}' \
  -v

# curl -c cookies.txt https://example.com

curl http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "test@test.com", "password": "test.123"}' \
  -v
