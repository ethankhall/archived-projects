# Exercise 0 - Git Into

## What is Git
Git is a source control system. It allows you to manage versions of files. Any file. Git is used mostly with text files. Git tracks what changes in your repository.

#### Repository
A repository is Git lingo for where you keep source. A git repository contains *all* the changes in the source. This means that you have a full history of everything done in the repository. Unlike SVN where you need access to the server to review history, Git allows you to view history at any point in time.

Another difference is that Git doesn't track files. It tracks changes. Renaming a file only contains the filename changes, not the actual contents.

#### Commit
A commit in Git is the set of changes that have been done the repository since the last commit. Every commit (except the first) has at least one parent. Commits are like a [Directed Graph](http://en.wikipedia.org/wiki/Directed_graph). 

![Git Commit Graph](http://git-scm.com/figures/18333fig0317-tn.png "Commit Graph")

In the image above you can see the nodes called C0 - C6. Those are commits. In this case C0 is the first commit in the repository. C1 has a parent node of C0. C6 has two parents C5 and C4. C2 has two children, C4 and C3.

## Enough Talk
Let's create a new repository. First let's run a setup scrip to help later along. 

```
./scripts/exercise0.sh
```

This won't do anything for us now. But we will use it in a few steps. Change directories to `workspace/exercise0`.

So this is just a normal directory, like any other on your computer. Let's create a git repo.
```
git init
```

This command will create a .git directory in this folder. That is where git will store everything. Create a new file `touch README` then edit the file. If you ran the status command, it will look like this.
```
> git status
# On branch master
#
# Initial commit
#
# Untracked files:
#   (use "git add <file>..." to include in what will be committed)
#
#       README
nothing added to commit but untracked files present (use "git add" to track)
```

Git commands are very good at saying what is going on. Git reconizes that there is a file in it's repo. So we are going to need to tell git that it needs to track it. To do this you just run `git add README`.
