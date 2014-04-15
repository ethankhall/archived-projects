## What is a commit

A commit in Git is the set of changes that have been done the repository since the last commit. Every commit (except the first) has at least one parent. Commits are like a [Directed Graph](http://en.wikipedia.org/wiki/Directed_graph). 

![Git Commit Graph](http://git-scm.com/figures/18333fig0317-tn.png "Commit Graph")

In the image above you can see the nodes called C0 - C6. Those are commits. In this case C0 is the first commit in the repository. C1 has a parent node of C0. C6 has two parents C5 and C4. C2 has two children, C4 and C3.

