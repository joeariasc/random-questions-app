#!/bin/bash

echo "$SERVICE_CREDENTIALS_JSON" | base64 -d > firebase.json
echo "$KEYSTORE_CONTENT" | base64 -d > keystore.jks

SERVICE_CREDENTIALS_PATH="$(pwd)"/"$(basename firebase.json)"
KEYSTORE_PATH="$(pwd)"/"$(basename keystore.jks)"

echo "SERVICE_CREDENTIALS_PATH=$SERVICE_CREDENTIALS_PATH" >> "$GITHUB_ENV"
echo "KEYSTORE_PATH=$KEYSTORE_PATH" >> "$GITHUB_ENV"
