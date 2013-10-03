#!/bin/bash
 
set -x

#Setup PATH_DIR
PATH_DIR=`dirname "$0"`
PATH_DIR="`( cd \"$PATH_DIR\"/.. && pwd )`" 

EXERCISE_NAME="exercise0"
EXERCISE_WORKING_DIR="$PATH_DIR/workspace/$EXERCISE_NAME"
EXERCISE_REPO_DIR="$PATH_DIR/repo/$EXERCISE_NAME"
WIP_REPO="$PATH_DIR/repo/wip"

rm -rf $EXERCISE_WORKING_DIR
rm -rf $EXERCISE_REPO_DIR

mkdir -p $EXERCISE_WORKING_DIR
mkdir -p $EXERCISE_REPO_DIR

pushd $EXERCISE_REPO_DIR
git init --bare
popd
