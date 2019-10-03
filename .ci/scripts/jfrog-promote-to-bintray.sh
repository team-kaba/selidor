#!/bin/bash
# shellcheck disable=SC1090

set -euo pipefail

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")"
  /bin/pwd
)"

source "${script_dir}/source.sh"
source "${PROJECT_ROOT_DIR}/${JFROG_CLI_UPLOADED_BUILD_INFO}"

if [ -z "${BINTRAY_REPOSITORY:-}" ]; then
  echo "'BINTRAY_REPOSITORY' must not be empty."
  exit 1
fi

if [ -z "${BINTRAY_PACKAGE:-}" ]; then
  echo "'BINTRAY_REPOSITORY' must not be empty."
  exit 1
fi

version_tag=$(git_current_version_tag)
if [ -z "${version_tag}" ]; then
  echo "HEAD($(git rev-parse HEAD) is not tagged as a release version."
  exit 1
fi

artifactory_build="${JFROG_CLI_BUILD_NAME}/${JFROG_CLI_BUILD_NUMBER}"
target_location="${BINTRAY_REPOSITORY}/${BINTRAY_PACKAGE}/${version_tag}"
build_artifacts_dir=".build-artifacts/${artifactory_build}/"

echo "Promoting atrtifacts from ${ARTIFACTORY_SERVER_ID} to Bintray using jfrog cli."
echo "  Source build: ${artifactory_build}"
echo "  Target location: ${target_location}"

echo "  Downloading build artifacts from ${ARTIFACTORY_SERVER_ID} to ${build_artifacts_dir}."
jfrog rt dl --build="${artifactory_build}" oss-release-local "${build_artifacts_dir}"

echo "  Creating Bintray version (${version_tag})."
jfrog bt vc --vcs-tag "${version_tag}" "${target_location}"

echo "  Uploading build artifacts to Bintray repository ${target_location}."
jfrog bt u --flat=false --publish "${build_artifacts_dir}(*)" "${target_location}" '{1}'

echo "  Set build status of ${artifactory_build} as 'RELEASED'."
jfrog rt bpr --status=RELEASED "${JFROG_CLI_BUILD_NAME}" "${JFROG_CLI_BUILD_NUMBER}" oss-release-local
