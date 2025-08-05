# Keycloak Realm Export and Copy Guide

This guide explains how to export a Keycloak realm from a running Keycloak container and copy the exported realm JSON file to your local machine.

## Prerequisites
- Docker installed and running.
- Access to the Keycloak container ID or name.
- Basic knowledge of Docker commands.

---

## Steps

### 1. Access the Keycloak container shell
Run the following command to open a bash shell inside your Keycloak container:

```bash
docker exec -it <container_id_or_name> bash
