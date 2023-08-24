#!/usr/bin/env sh
echo $SERVICE_CREDENTIALS_JSON | base64 -d > firebase.json
echo $KEYSTORE_CONTENT | base64 -d > keystore.jks