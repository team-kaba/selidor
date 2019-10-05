#!/bin/bash
# shellcheck disable=SC1090

set -euo pipefail

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")"
  /bin/pwd
)"

source "${script_dir}/source.sh"

if git_dirty_worktree; then
  echo "Cannot prepare the release because you have local modifications."
  exit 1
fi

current_refname="$(get_git_refname_from_pom)"
current_version="$(get_revision_from_pom)"
release_version="$(get_next_version "${current_version}")"

if [[ "${release_version}" =~ -SNAPSHOT$ ]]; then
  echo "Current version must be SNAPSHOT. version='${current_version}'"
  exit 1
fi

version_tag="v${release_version}"

set_revision_to_pom "${release_version}"
set_git_refname_to_pom "${version_tag}"

git add 'pom.xml' '**/pom.xml'
git commit -m ":bookmark: Release ${release_version}"
git tag --cleanup=whitespace -a "${version_tag}" -m "${release_version}"

clean_worktree="${PROJECT_ROOT_DIR}/.release-worktree"
if [ -d "${clean_worktree}" ]; then
  if git worktree list | grep "${clean_worktree}"; then
    git worktree remove "${clean_worktree}"
  else
    rm -rf "${clean_worktree}"
  fi
fi
git worktree add "${clean_worktree}" "${version_tag}"
pushd "${clean_worktree}"
"${script_dir}/maven-deploy.sh" || (popd &&
  git reset HEAD~ &&
  git tag -d "${version_tag}" &&
  echo "Test failed. Reverting git commit and tag. exiting..." &&
  exit 1)
popd
echo "Successfully completed tests."

next_development_version="$(get_next_version "${release_version}")"

set_revision_to_pom "${next_development_version}"
set_git_refname_to_pom "${current_refname}"

git add 'pom.xml' '**/pom.xml'
git commit -m ":arrow_up: Next development version (${next_development_version})"
