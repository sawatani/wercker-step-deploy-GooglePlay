. $WERCKER_STEP_ROOT/setup.sh

WERCKER_ANDROID_GOOGLEPLAY_SERVICE_ACCOUNT_KEY_FILE_PATH="${WERCKER_STEP_ROOT}/key.p12"
echo "${WERCKER_ANDROID_GOOGLEPLAY_SERVICE_ACCOUNT_KEY_BASE64}" | base64 -d > "$WERCKER_ANDROID_GOOGLEPLAY_SERVICE_ACCOUNT_KEY_FILE_PATH"

sbt run
