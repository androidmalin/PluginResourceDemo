#!/bin/bash
./gradlew clean
rm -rf app/src/main/assets/*.apk
./gradlew -q plugin:assembleDebug
cp plugin/build/outputs/apk/debug/plugin-debug.apk app/src/main/assets/
