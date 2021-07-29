#!/bin/bash
# shellcheck disable=SC1090

set -euo pipefail

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")"
  /bin/pwd
)"

source "${script_dir}/source.sh"

project_to_deploy=${1:-.}

original_refname=$(get_git_refname_from_pom)
actual_refname=$(git_head_refname)
set_git_refname_to_pom "${actual_refname}"

mvnw -N clean verify
mvnw clean deploy -DskipRemoteStaging "-Dgpg.keyName=${SIGNING_GPG_KEYNAME}" -f "${project_to_deploy}/pom.xml" --no-transfer-progress "${@}"

set_git_refname_to_pom "${original_refname}"
