#!/bin/bash
 
set -x

#Setup PATH_DIR
PATH_DIR=`dirname "$0"`
PATH_DIR="`( cd \"$PATH_DIR\"/.. && pwd )`" 

EXERCISE_NAME="exercise3"
EXERCISE_PATCH_DIR="$PATH_DIR/patchdir/$EXERCISE_NAME"
EXERCISE_WORKING_DIR="$PATH_DIR/workspace/$EXERCISE_NAME"

rm -rf $EXERCISE_WORKING_DIR
mkdir -p $EXERCISE_WORKING_DIR
cd $EXERCISE_WORKING_DIR

git init

git am < $EXERCISE_PATCH_DIR/0001-adding-build.gradle.patch
git am < $EXERCISE_PATCH_DIR/0002-adding-gradlew.patch
git am < $EXERCISE_PATCH_DIR/0003-Added-a-class-w-test.patch
git checkout -b dev
git am < $EXERCISE_PATCH_DIR/0004-making-class-static.patch
git am < $EXERCISE_PATCH_DIR/0005-Fixing-typo.patch
