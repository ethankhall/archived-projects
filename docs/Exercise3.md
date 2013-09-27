# Exercise 3 - Cherry Picking

In this exercise we are going to have two branches. Master and dev. They will have the contents of [Exercise 1](Exercise1.md).

You are going to want to take a commit on dev and cherry-pick it into master. Cherry picking is essentually taking the diff of a commit and applying it to another branch. This doesn't require a common parent node, so it creates a new commit (with a new commit id). 

The consequince of this is that the branches (assuming were fast-forward able before) aren't fast-forward able now. So, to make the dev branch be able to fast-forward (FF) merge in the future, you are going to need to merge master into dev.

After this exercise is over, make a few commits and merge dev into master.

### Discussion
#### Why would you want to do this?
Let say that you have a branch (dev) where you want to do all your work. This works pretty well until you find a critical bug in your production env (master). You have fixed the changes in dev, so you want to get the changes over into master. You could do like we did in [Exercise 1](Exercise1.md) and have a branch where all hotfixes are made then merged onto master and dev. This works, but sometimes you don't know there is an issue, but it has been fixed on another branch already. This is another way to do the same thing. 

#### What would you do.
In this situation I would make a new branch off master, cherry pick the commit onto that branch. QA the new branch, then merge it into master and dev (like in Exercise 1).

### [Solution](Exercise3_solutions.md)
