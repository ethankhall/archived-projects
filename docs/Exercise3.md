# Exercise 3 - Cherry Picking

In this exercise we are going to have two branches. Master and dev. They will have the contents of [Exercise 1](Exercise1.md).

You are going to want to take a commit on dev and cherry-pick it into master. Cherry picking is essentually taking the diff of a commit and applying it to another branch. This doesn't require a common parent node, so it creates a new commit (with a new commit id). 

The consequince of this is that the branches (assuming were fast-forward able before) aren't fast-forward able now. So, to make the dev branch be able to fast-forward (FF) merge in the future, you are going to need to merge master into dev.

After this exercise is over, make a few commits and merge dev into master.

## [Solution](Exercise3_solutions.md)
