#!/bin/bash

set -euo pipefail

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")"
  /bin/pwd
)"
project_root_dir="$(
  cd "${script_dir}"
  /usr/bin/git rev-parse --show-toplevel
)"

project_to_deploy=${1:-.}
m2_repository_dir="${project_root_dir}/.m2/repository"
distribution_file_root="${project_root_dir}/dist"
alt_deployment_repository_property_value="distribution::default::file://${ALT_DEPLOYMENT_REPOSITORY:-${distribution_file_root}}"

mvn deploy -f "${project_to_deploy}/pom.xml" "-DaltDeploymentRepository=${alt_deployment_repository_property_value}" -e
