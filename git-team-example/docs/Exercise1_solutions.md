## Exercise 1 Solution
```
# Lets make sure that we are on the master branch.
$ git checkout master
Switched to branch 'master'

# Merge bugfix into master
$ git merge bugfix
Updating 0e3f388..5da5f91
Fast-forward
 src/main/java/com/example/git/SomeClass.java     | 2 +-
 src/test/java/com/example/git/SomeClassTest.java | 1 +
 2 files changed, 2 insertions(+), 1 deletion(-)


# Merge bugfix into dev
$ git checkout dev
$ git merge bugfix
Auto-merging src/test/java/com/example/git/SomeClassTest.java
CONFLICT (content): Merge conflict in src/test/java/com/example/git/SomeClassTest.java
Auto-merging src/main/java/com/example/git/SomeClass.java
CONFLICT (content): Merge conflict in src/main/java/com/example/git/SomeClass.java
Automatic merge failed; fix conflicts and then commit the result.

# We need to fix the merge conflicts, test the changes
$ vim -p src/main/java/com/example/git/SomeClass.java src/test/java/com/example/git/SomeClassTest.java
$ ./gradlew test

# Commit the changes
$ git add src/
$ git commit
[dev 7816f7a] Merge branch 'master' into dev

# Make some changes
$ vim -p src/main/java/com/example/git/SomeClass.java src/test/java/com/example/git/SomeClassTest.java
$ git add src
$ git commit 

# Merge the changes into bugfix and then master
$ git checkout bugfix
Switched to branch 'bugfix'
$ git merge dev
Updating 425aa3f..c2cf4c5
Fast-forward
 src/main/java/com/example/git/SomeClass.java     | 2 +-
 src/test/java/com/example/git/SomeClassTest.java | 7 +++----
 2 files changed, 4 insertions(+), 5 deletions(-)
$ git checkout master
Switched to branch 'master'
$ git merge bugfix
Updating 2e3ed8c..c2cf4c5
Fast-forward
 src/main/java/com/example/git/SomeClass.java | 1 -
 1 file changed, 1 deletion(-)
```
