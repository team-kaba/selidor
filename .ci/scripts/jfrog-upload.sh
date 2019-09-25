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

echo "Build name: ${build_name}"
echo "Build number: ${build_number}"
echo "Build url: ${build_url}"

echo "Uploading atrtifacts to ${ARTIFACTORY_SERVER_ID} using jfrog cli."
jfrog rt u --spec .ci/jfrog-cli-spec.json --spec-vars "dist-dir=${BUILT_ARTIFACTS_DIR};type=${type}" --build-name "${build_name}" --build-number "${build_number}"

echo "Prepare build information of atrtifacts."
echo "Collecting environment variables of build."
jfrog rt bce "${build_name}" "${build_number}"

echo "Collecting information from Git."
jfrog rt bag "${build_name}" "${build_number}" "${PROJECT_ROOT_DIR}"

echo "Publishing build information of atrtifacts to ${ARTIFACTORY_SERVER_ID} using jfrog cli."
jfrog rt bp --build-url "${build_url}" "${build_name}" "${build_number}"
# ログにビルド情報を出力するためにdry-runで実行し直す。
# dry-runを一回流すと、情報を集め直さないといけないみたい。
echo "Publishing build information of atrtifacts to ${ARTIFACTORY_SERVER_ID} using jfrog cli."
jfrog rt u --spec .ci/jfrog-cli-spec.json --spec-vars "dist-dir=${BUILT_ARTIFACTS_DIR};type=${type}" --build-name "${build_name}" --build-number "${build_number}" --dry-run
jfrog rt bce "${build_name}" "${build_number}"
jfrog rt bag "${build_name}" "${build_number}" "${PROJECT_ROOT_DIR}"
jfrog rt bp --dry-run --build-url "${build_url}" "${build_name}" "${build_number}"
