#!/bin/bash

echo "$SERVICE_CREDENTIALS_JSON" | base64 -d > firebase.json
echo "$KEYSTORE_CONTENT" | base64 -d > keystore.jks

export SERVICE_CREDENTIALS_PATH="$(pwd)"/"$(basename firebase.json)"
export KEYSTORE_PATH="$(pwd)"/"$(basename keystore.jks)"
