#!/bin/bash
# shellcheck disable=SC1090

set -euo pipefail

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")"
  /bin/pwd
)"

source "${script_dir}/env.sh"
source "${script_dir}/functions.sh"

type=${1}
build_name=${ARTIFACTORY_BUILD_NAME}
build_number=${ARTIFACTORY_BUILD_NUM}
build_url=${ARTIFACTORY_BUILD_URL}

if [ -z "${ARTIFACTORY_SERVER_ID:-}" ]; then
  echo "'ARTIFACTORY_SERVER_ID' must not be empty."
  exit 1
fi

echo "Uploading atrtifacts to ${ARTIFACTORY_SERVER_ID} using jfrog cli."
jfrog rt u --spec .ci/jfrog-cli-spec.json --spec-vars "dist-dir=${BUILT_ARTIFACTS_DIR};type=${type}"
jfrog --version

echo "Prepare build information of atrtifacts."
echo "Build name: ${build_name}"
echo "Build number: ${build_number}"
echo "Build url: ${build_url}"

echo "Collecting environment variables of build."
jfrog rt bce "${build_name}" "${build_number}"

echo "Collecting information from Git."
jfrog rt bag "${build_name}" "${build_number}" "${PROJECT_ROOT_DIR}"

echo "Publishing build information of atrtifacts to ${ARTIFACTORY_SERVER_ID} using jfrog cli."
jfrog rt bp --build-url "${build_url}" "${build_name}" "${build_number}"
