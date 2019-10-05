function git_dirty_worktree() {
  [ -n "$(git status -s)" ]
}

function git_current_version_tag() {
  # Annotated tag だけを対象にするために `^{}` が付いたtagを対象としています。
  git show-ref --tags -d --abbrev=0 |
    cut -d' ' -f2 |
    sed -e 's!refs/tags/!!g' |
    grep -E '^v[0-9]+\.[0-9]+\.[0-9]+\^\{\}$' |
    sed -e 's!\^{}$!!g' |
    sort --version-sort --reverse --ignore-nonprinting |
    head -n 1
}

function git_head_refname() {
  git describe --dirty --exact-match --abbrev=0 2>/dev/null || echo "$(git rev-parse HEAD)$(git_dirty_worktree && echo '-dirty')"
}
