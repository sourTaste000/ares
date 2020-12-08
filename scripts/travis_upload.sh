#!/bin/bash
# uploads build artifacts to aresclient.org if branch is master and not a pr

if [ "$TRAVIS_BRANCH" != "master" ] || [ "$TRAVIS_PULL_REQUEST" = "true" ]; then
  exit 0
fi

FORGE_PATH = ""
FABRIC_PATH = ""

for filename in $TRAVIS_BUILD_DIR/build/*.jar; do
  if [ filename == *"forge" ]; then
    FORGE_PATH = filename
  fi
  if [ filename == *"fabric" ]; then
    FABRIC_PATH = filename
  fi
done

if [ -z "$FORGE_PATH" ] && [ -z "$FABRIC_PATH" ]; then
  echo $(curl -F token=$UPLOAD_TOKEN -F message="$TRAVIS_COMMIT_MESSAGE" -F forge=@$FORGE_PATH -F fabric=@$FABRIC_PATH https://aresclient.org/beta)
else
  echo "Couldn't find path to forge and fabric builds!"
fi