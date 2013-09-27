# Exercise 3 - Cherry Pick Commit - Solution

So we are going to need to cherry pick a commit onto master, lets switch to the master branch and do the cherry-pick
```
$ git checkout master
Switched to branch 'master'

$ git cherry-pick 422361a
error: could not apply 422361a... switching the - to a +
hint: after resolving the conflicts, mark the corrected paths
hint: with 'git add <paths>' or 'git rm <paths>'
hint: and commit the result with 'git commit'
```
You are going to need to get the commit that we are going to cherry pick. From the commandline run git log and find the commit like "switching the - to a +"

Alright, well there were merge conflicts, so lets fix them.
```
$ git status
# On branch master
# You are currently cherry-picking.
#   (fix conflicts and run "git cherry-pick --continue")
#   (use "git cherry-pick --abort" to cancel the cherry-pick operation)
#
# Unmerged paths:
#   (use "git add <file>..." to mark resolution)
#
#       both modified:      src/main/java/com/example/git/SomeClass.java
#       both modified:      src/test/java/com/example/git/SomeClassTest.java
#
no changes added to commit (use "git add" and/or "git commit -a")

$ vim -p src/main/java/com/example/git/SomeClass.java src/test/java/com/example/git/SomeClassTest.java
2 files to edit

$ git add src/main/java/com/example/git/SomeClass.java src/test/java/com/example/git/SomeClassTest.java
```
Now we can let the cherry pick continue

```
$ git cherry-pick --continue
[master 8854408] switching the - to a +
2 files changed, 4 insertions(+), 4 deletions(-)
```

Okay, now that the cherry pick is done, what does the git commit graph look like?
```
* 8854408 - (HEAD, master) switching the - to a + (7 seconds ago) <Ethan Hall>
| * 422361a - (dev) switching the - to a + (5 minutes ago) <Ethan Hall>
| * 1d6ba78 - making class static (5 minutes ago) <Ethan Hall>
|/
* dcbb311 - Added a class w/ test (5 minutes ago) <Ethan Hall>
* e366514 - adding gradlew (5 minutes ago) <Ethan Hall>
* 1d7f0c8 - adding build.gradle (5 minutes ago) <Ethan Hall>
```
Sweet! Well, one more thing we need to do is merge master into dev (to give us a FF merge in the future)

```
$ git checkout dev
Switched to branch 'dev'
 
$ git merge master
Auto-merging src/main/java/com/example/git/SomeClass.java
CONFLICT (content): Merge conflict in src/main/java/com/example/git/SomeClass.java
Automatic merge failed; fix conflicts and then commit the result.
 
$ vim src/main/java/com/example/git/SomeClass.java
 
$ git commit -a
[dev 75adae2] Merge branch 'master' into dev
```

Excellent, now out dev branch has the most recent commit from master (allowing a FF).
```
*   75adae2 - (HEAD, dev) Merge branch 'master' into dev (6 seconds ago) <Ethan Hall>
|\
| * 8854408 - (master) switching the - to a + (69 seconds ago) <Ethan Hall>
* | 422361a - switching the - to a + (6 minutes ago) <Ethan Hall>
* | 1d6ba78 - making class static (6 minutes ago) <Ethan Hall>
|/
* dcbb311 - Added a class w/ test (6 minutes ago) <Ethan Hall>
* e366514 - adding gradlew (6 minutes ago) <Ethan Hall>
* 1d7f0c8 - adding build.gradle (6 minutes ago) <Ethan Hall>
```

Done!
