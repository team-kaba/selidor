#!/bin/bash
# shellcheck disable=SC1090

set -euo pipefail

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")"
  /bin/pwd
)"

source "${script_dir}/source.sh"

project_to_verify=${1:-.}

set_parent_version_to_pom "$(get_pull_request_revision_from_pom)" "${project_to_verify}"
mvnw clean verify -f "${project_to_verify}/pom.xml"
