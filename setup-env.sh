#!/usr/bin/env sh
echo "$SERVICE_CREDENTIALS_JSON" | base64 -d > firebase.json
echo "$KEYSTORE_CONTENT" | base64 -d > keystore.jks

SERVICE_CREDENTIALS_PATH="$(readlink -f firebase.jks)"
export SERVICE_CREDENTIALS_PATH

KEYSTORE_PATH="$(readlink -f keystore.jks)"
export KEYSTORE_PATH