#!/bin/bash
# shellcheck disable=SC1090

set -euo pipefail

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")"
  /bin/pwd
)"

source "${script_dir}/env.sh"
source "${script_dir}/functions.sh"

project_to_deploy=${1:-.}

mvnw -N clean verify
mvnw clean deploy -f "${project_to_deploy}/pom.xml" "-DaltDeploymentRepository=${ALT_DEPLOYMENT_REPOSITORY}"
