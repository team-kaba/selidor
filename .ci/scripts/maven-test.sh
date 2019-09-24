#!/bin/bash
# shellcheck disable=SC1090

set -euo pipefail

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")"
  /bin/pwd
)"

source "${script_dir}/env.sh"
source "${script_dir}/functions.sh"

project_to_test=${1:-.}

mvnw -N clean verify
mvnw clean test -f "${project_to_test}/pom.xml"
