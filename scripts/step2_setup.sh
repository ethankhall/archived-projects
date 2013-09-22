#!/bin/bash
 
set -x

#Setup PATH_DIR
PATH_DIR=`dirname "$0"`
PATH_DIR="`( cd \"$PATH_DIR\"/.. && pwd )`" 

STEP_NAME="step2"
STEP_PATCH_DIR="$PATH_DIR/pathdir/$STEP_NAME"
STEP_WORKING_DIR="$PATH_DIR/workspace/$STEP_NAME"
STEP_REPO_DIR="$PATH_DIR/repo/$STEP_NAME"

rm -rf $STEP_WORKING_DIR
rm -rf $STEP_REPO_DIR

mkdir -p $STEP_WORKING_DIR
mkdir -p $STEP_REPO_DIR

cd $STEP_REPO_DIR

#Create the upsteam repo, and add 2 books
git init
git am < $STEP_PATCH_DIR/0001-inital-commit-w-mody-dick.patch
git am < $STEP_PATCH_DIR/0002-adding-more-books.patch

#Move into the working dir
pushd $STEP_WORKING_DIR

#Clone step repo
git clone $STEP_REPO_DIR .
git checkout -b fix_spelling

#Move out of the working dir into repo
popd

#Add the rest of the books to "upstream"
git am < $STEP_PATCH_DIR/0003-adding-rest-of-books.patch

