## Step 1

### Setup
run `./scripts/step1.sh`
Change directory into workspace/step1. This is where this exercise will take place.

### Info
There are three branches: master, bugfix, dev
- Master is 'prod'
- Bugfix is for hotfixes
- Dev is where dev should be merged into.

### Goal
We need to take the bugfix branch, merge it onto master. This commit should be a fast-forward ( Ref: [git-scm.com][http://git-scm.com/book/en/Git-Branching-Basic-Branching-and-Merging#Basic-Branching] )
Next, we need to merge master into dev. Let's take a look at the current structure of the repo. Please note, the sha1 hashes may be different.

```
* 425aa3f - (master, bugfix) Fixing typo (10 minutes ago) <Ethan Hall>
| * 2f0544c - (HEAD, dev) making class static (10 minutes ago) <Ethan Hall>
|/  
* 1899c66 - Added a class w/ test (10 minutes ago) <Ethan Hall>
* e030711 - adding build.gradle (10 minutes ago) <Ethan Hall>
```

From this picture, we can see that both master and bugfix branches are on commit 425aa3f. Dev is at 2f0544c (making class static).
Now we need to merge in bugfix into dev. There may be a merge conflict here (there actually should be one). So you will need to fix them.
After you make the changed be sure to run the tests! `./gradlew test`.

Now make some changes in the source and commit it.

After you have commited it, merge dev into bugfix, into master.

### Solution
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
