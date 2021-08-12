#!/bin/bash
# shellcheck disable=SC1090

set -euo pipefail

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")"
  /bin/pwd
)"

source "${script_dir}/source.sh"

project_to_verify=${1:-.}
shift

mvnw -N clean verify
mvnw clean install -f "${project_to_verify}/pom.xml" "${@}"
