# branch handling

* show branches:
  * all: `git branch -a`
  * remote: `git branch -r`
  * local: `git branch`
* create branch:
  * `git checkout -b NAME`
* cleanup branches:
  * `git fetch -p` (p = prune, delete outdated local branches)
* delete local:
  * `git branch -d NAME`
  * in case of warnings being issued: `git branch -D name`