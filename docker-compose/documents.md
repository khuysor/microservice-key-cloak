# For Install key cloak and name it key-cloak
docker run --name key-cloak -p 8899:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.2.3 start-dev

# running all docker compose file 
docker-compose -f docker-compose.yml -f infra.yml -f monitoring.yml up -d