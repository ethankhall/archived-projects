## Exercise 2 - Rebase from origin -  Solution
```
$ cd workspace/exercise2
$ mv books/Pirde_and_Predudice.txt books/Pride_and_Predudice.txt
$ git add books/Pride_and_Predudice.txt
$ wget http://www.gutenberg.org/ebooks/36519.txt.utf-8
$ mv 36519.txt.utf-8 books/The_Childhood_of_Distinguished_Women.txt
$ git add books/The_Childhood_of_Distinguished_Women.txt
$ echo "- [The Childhood of Distinguished Women][http://www.gutenberg.org/ebooks/36519.txt.utf-8]" >> README.md
$ git commit -a -m "added book and fixed spelling"
[master 57a8fbe] added book and fixed spelling
 3 files changed, 1884 insertions(+)
 rename books/{Pirde_and_Predudice.txt => Pride_and_Predudice.txt} (100%)
 create mode 100644 books/The_Childhood_of_Distinguished_Women.txt

```

Now we have made the changes that we want. In the meantime, origin seems to have move forward. So let's push and see what happens. Before you try to push, you should fetch to get up-to-date.
```
$ git fetch
remote: Counting objects: 11, done.
remote: Compressing objects: 100% (8/8), done.
remote: Total 8 (delta 0), reused 0 (delta 0)
Unpacking objects: 100% (8/8), done.
From /home/ethan/temp/repo/exercise2
   26eb569..da6e559  master     -> origin/master

$ git push
To /home/ethan/temp/repo/exercise2
 ! [rejected]        master -> master (non-fast-forward)
error: failed to push some refs to '/home/ethan/temp/repo/exercise2'
hint: Updates were rejected because the tip of your current branch is behind
hint: its remote counterpart. Integrate the remote changes (e.g.
hint: 'git pull ...') before pushing again.
hint: See the 'Note about fast-forwards' in 'git push --help' for details.
```

:-( Looks like we can't push. Lets look at the tree of commits.

```
* 57a8fbe - (HEAD, master) added book and fixed spelling (5 minutes ago) <Ethan Hall>
| * da6e559 - (origin/master, origin/HEAD) adding rest of books (25 minutes ago) <Ethan Hall>
|/  
* 26eb569 - adding more books (25 minutes ago) <Ethan Hall>
* 2ecd2e1 - inital commit w/ mody dick (25 minutes ago) <Ethan Hall>
```

So you see how 57a8fbe (master) has a parent node of 26eb569, Well origin/master (where GitHub could be) is da6e559. orign/master has the same parent node. So for there to be a fast-forward you need to make 57a8fbe have a parent node of da6e559. There are two ways to do this:
- [Rebase](http://git-scm.com/book/en/Git-Branching-Rebasing).
- [Merge](http://git-scm.com/book/en/Git-Branching-Basic-Branching-and-Merging#Basic-Merging).
This tutorial will only go though the rebase. It's up to you to do the merge.

*DO NOT REBASE A BRANCH PUSHED TO A REMOTE*. It will mess everything up for any other users.

To do the rebase, run `git pull --rebase`
```
$ git pull --rebase 
First, rewinding head to replay your work on top of it...
Applying: added book and fixed spelling
Using index info to reconstruct a base tree...
M       README.md
<stdin>:26881: trailing whitespace.
???Project Gutenberg's The Childhood of Distinguished Women, by Selina A. Bower
<stdin>:26882: trailing whitespace.

<stdin>:26883: trailing whitespace.
This eBook is for the use of anyone anywhere at no cost and with
<stdin>:26884: trailing whitespace.
almost no restrictions whatsoever.  You may copy it, give it away or
<stdin>:26885: trailing whitespace.
re-use it under the terms of the Project Gutenberg License included
warning: squelched 1878 whitespace errors
warning: 1883 lines add whitespace errors.
Falling back to patching base and 3-way merge...
Auto-merging README.md
CONFLICT (content): Merge conflict in README.md
Failed to merge in the changes.
Patch failed at 0001 added book and fixed spelling
The copy of the patch that failed is found in:
   /home/ethan/temp/workspace/exercise2/.git/rebase-apply/patch

When you have resolved this problem, run "git rebase --continue".
If you prefer to skip this patch, run "git rebase --skip" instead.
To check out the original branch and stop rebasing, run "git rebase --abort".
```

So there was a conflict, there was an issue becuase the same line was edited in README.md. So let's edit it.

After opening the file you will see something like
```
<<<<<<< HEAD
- [The Adventures of Sherlock Holmes][http://www.gutenberg.org/ebooks/1661.txt.utf-8]
- [The Prince][http://www.gutenberg.org/ebooks/1232.txt.utf-8]
- [Alice's Adventures in Wonderland][http://www.gutenberg.org/ebooks/11.txt.utf-8]
- [Grimms' Fairy Tales][http://www.gutenberg.org/ebooks/2591.txt.utf-8]
=======
- [The Childhood of Distinguished Women][http://www.gutenberg.org/ebooks/36519.txt.utf-8]
>>>>>>> added book and fixed spelling
```

The section like `<<<<<<< HEAD` means that the following secion is in origin/master. The section after `=======` is the other part of the merge. The commit that the bottom section can be found `>>>>>>> added book and fixed spelling`.

We want to merge the two sections, so delete the lines with `<<<`, `===`, `>>>` and that will be what we want.

So now that the file is complete, we want to complete the rebase. You can use `git status` to see what files need to be fixed.

```
$ git add README.md
$ git rebase --continue
Applying: added book and fixed spelling
```

Now, the commit that we did is now inline with the origin/master.

```
* dab9378 - (HEAD, master) added book and fixed spelling (29 seconds ago) <Ethan Hall>
* da6e559 - (origin/master, origin/HEAD) adding rest of books (54 minutes ago) <Ethan Hall>
* 26eb569 - adding more books (54 minutes ago) <Ethan Hall>
* 2ecd2e1 - inital commit w/ mody dick (54 minutes ago) <Ethan Hall>
```

If you notice, the top most commit (dab9378) used to be 57a8fbe.

Now we can push, and it will work.

```
$ git push
Counting objects: 8, done.
Delta compression using up to 4 threads.
Compressing objects: 100% (5/5), done.
Writing objects: 100% (5/5), 35.90 KiB | 0 bytes/s, done.
Total 5 (delta 2), reused 0 (delta 0)
To /home/ethan/temp/repo/exercise2
   62562a9..c2a4827  master -> master
```
