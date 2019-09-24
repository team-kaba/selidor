#!/bin/bash
# shellcheck disable=SC1090

set -euo pipefail

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")"
  /bin/pwd
)"

source "${script_dir}/env.sh"
source "${script_dir}/functions.sh"

if [ -z "${BINTRAY_REPOSITORY}" ]; then
  echo "'BINTRAY_REPOSITORY' must not be empty."
  exit 1
fi

target_repository=${BINTRAY_REPOSITORY}

build_name=${ARTIFACTORY_BUILD_NAME}
build_number=${ARTIFACTORY_BUILD_NUM}

echo "Promoting atrtifacts from ${ARTIFACTORY_SERVER_ID} to Bintray using jfrog cli."
echo "Build name: ${build_name}"
echo "Build number: ${build_number}"
echo "Target repository: ${target_repository}"

jfrog rt bd "${build_name}" "${build_number}" "${target_repository}"
