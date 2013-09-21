#!/bin/bash
 
set -x

#Setup PATH_DIR
PATH_DIR=`dirname "$0"`
PATH_DIR="`( cd \"$PATH_DIR\"/.. && pwd )`" 
PATCH_DIR="$PATH_DIR/pathdir/step1"

rm -rf $PATH_DIR/.git

git init

git am < $PATCH_DIR/0001-adding-build.gradle.patch
git am < $PATCH_DIR/0002-adding-gradlew.patch
git am < $PATCH_DIR/0003-Added-a-class-w-test.patch
git checkout -b dev
git am < $PATCH_DIR/0004-making-class-static.patch
git checkout -b bugfix master
git am < $PATCH_DIR/0005-Fixing-typo.patch
