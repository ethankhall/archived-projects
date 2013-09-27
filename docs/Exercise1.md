## Exercise 1

### Setup
Run `./scripts/exercise1.sh`

Change directory into `workspace/exercise1`. This is where this exercise will take place.

### Info
There are three branches: master, bugfix, dev
- Master is 'prod'
- Bugfix is for hotfixes
- Dev is where dev should be merged into.

### Goal
We need to take the bugfix branch, merge it onto master. This commit should be a fast-forward ( Ref: [git-scm.com](http://git-scm.com/book/en/Git-Branching-Basic-Branching-and-Merging#Basic-Branching) )
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

### [Solution](Exercise1_solutions.md)
