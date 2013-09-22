#!/bin/bash
 
set -x

#Setup PATH_DIR
PATH_DIR=`dirname "$0"`
PATH_DIR="`( cd \"$PATH_DIR\"/.. && pwd )`" 

EXERCISE_NAME="exercise2"
EXERCISE_PATCH_DIR="$PATH_DIR/patchdir/$EXERCISE_NAME"
EXERCISE_WORKING_DIR="$PATH_DIR/workspace/$EXERCISE_NAME"
EXERCISE_REPO_DIR="$PATH_DIR/repo/$EXERCISE_NAME"

rm -rf $EXERCISE_WORKING_DIR
rm -rf $EXERCISE_REPO_DIR

mkdir -p $EXERCISE_WORKING_DIR
mkdir -p $EXERCISE_REPO_DIR

cd $EXERCISE_REPO_DIR

#Create the upsteam repo, and add 2 books
git init
git am < $EXERCISE_PATCH_DIR/0001-inital-commit-w-mody-dick.patch
git am < $EXERCISE_PATCH_DIR/0002-adding-more-books.patch

#Move into the working dir
pushd $EXERCISE_WORKING_DIR

#Clone step repo
git clone $EXERCISE_REPO_DIR .
git checkout -b fix_spelling

#Move out of the working dir into repo
popd

#Add the rest of the books to "upstream"
git am < $EXERCISE_PATCH_DIR/0003-adding-rest-of-books.patch

