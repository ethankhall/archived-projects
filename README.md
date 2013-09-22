# About
After trying to switch several team over to Git, I have realized that there is no good online tutorial on how to do this. So, I am going to try to make one. To do this I am going to use scripts (woo) to help simulate what would happen. Using patches. If you want, take a peak at the `scripts` folder. That will have all that you need to run. Please also take a look at the `docs` folder. That will have all the steps and what we are trying to acomplish for that example. It will also tell you what scripts to run, and when. Seeing how we may need to push things onto the git repo.

Best of luck! Let me know if you run into any troubles with this. I am testing this on Linux, and not using only git commandline tools. All the commit ID's will be different, so you can't use any of the ones that I put. I will try to use tags and branches vs commit id's.

If/when you see a commit tree, it was done using this command:

```
git log --graph --pretty=format:'%Cred%h%Creset -%C(yellow)%d%Creset %s %Cgreen(%cr) %C(bold blue)<%an>%Creset' --abbrev-commit --date=relative --all
```

without color

```git log --graph '--pretty=format:%h -%d %s (%cr) <%an>' --abbrev-commit '--date=relative' --all```

Any / All pull requests are much appreciated

## Exercise 1
This excersise it to try and merge a 'hotfix' branch into master (prod) then, we need to be able to fast-forward merge dev into master. So we need to merge into master to keep the shared node between master ( and hotfix ) the most recent in their branch. To do this, we have to merge!

[ Instructions and more details for Step 1](docs/Exercise1.md)
