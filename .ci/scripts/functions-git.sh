function git_dirty_worktree() {
  [ -n "$(git status -s)" ]
}

function git_current_version_tag() {
  git show-ref --tags -d --abbrev=0 | cut -d' ' -f2 | sed -e 's!refs/tags/!!g' | grep '^v' | sort --version-sort --reverse --ignore-nonprinting | head -n 1
}
