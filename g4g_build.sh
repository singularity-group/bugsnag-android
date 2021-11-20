set -o nounset # aborts if variable not set
echo 'Reading path to cos repo from variable REPO'
OUTPUT_DIR="$REPO/CosPackages/BugsnagUnity/Packages/bugsnagunity/Runtime/Bugsnag/Plugins/Android"
./gradlew :bugsnag-plugin-android-anr:assembleRelease
./gradlew :bugsnag-android-core:assembleRelease
cp -f ./bugsnag-plugin-android-anr/build/outputs/aar/bugsnag-plugin-android-anr-release.aar $OUTPUT_DIR
cp -f ./bugsnag-android-core/build/outputs/aar/bugsnag-android-core-release.aar $OUTPUT_DIR/bugsnag-android-release.aar
