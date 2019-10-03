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

current_version="$(get_revision_from_pom)"
release_version="$(get_next_version "${current_version}")"

if [[ "${release_version}" =~ -SNAPSHOT$ ]]; then
  echo "Current version must be SNAPSHOT. version='${current_version}'"
  exit 1
fi

version_tag="v${release_version}"

set_revision_to_pom "${release_version}"
set_scm_tag_to_pom "${version_tag}"

git add 'pom.xml' '**/pom.xml'
git commit -m ":bookmark: Release ${release_version}"
git tag --cleanup=whitespace -a "${version_tag}" -m "${release_version}"

git worktree add .release_worktree "${version_tag}"
pushd .release_worktree && "${script_dir}/maven-deploy.sh" || popd &&
  echo "Test failed. Reverting git commit and tag." &&
  git reset HEAD~ &&
  git tag -d "${version_tag}" && exit 1
popd
git worktree remove .release_worktree

next_development_version="$(get_next_version "${release_version}")"

set_revision_to_pom "${next_development_version}"
set_scm_tag_to_pom HEAD

git add 'pom.xml' '**/pom.xml'
git commit -m ":arrow_up: Next development version (${next_development_version})"
