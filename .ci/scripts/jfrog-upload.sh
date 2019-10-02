#!/bin/bash
# shellcheck disable=SC1090

set -euo pipefail

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")"
  /bin/pwd
)"

source "${script_dir}/source.sh"

type="${1}"
module_name="${ARTIFACTORY_MODULE_NAME}"

if [ -z "${ARTIFACTORY_SERVER_ID:-}" ]; then
  echo "'ARTIFACTORY_SERVER_ID' must not be empty."
  exit 1
fi

echo "Module: ${module_name}"

echo "Uploading atrtifacts to ${ARTIFACTORY_SERVER_ID} using jfrog cli."
jfrog rt u --spec .ci/jfrog-cli-spec.json --spec-vars "dist-dir=${BUILT_ARTIFACTS_DIR};type=${type}" --module "${module_name}" --props "artifactory.licenses=Apache-2.0"

echo "Prepare build information of atrtifacts."
echo "Collecting environment variables of build."
jfrog rt bce

echo "Collecting information from Git."
jfrog rt bag --config "${PROJECT_ROOT_DIR}/.ci/jfrog-cli-bag-config-${type}.yml" "${PROJECT_ROOT_DIR}"

echo "Publishing build information of atrtifacts to ${ARTIFACTORY_SERVER_ID} using jfrog cli."
jfrog rt bp
# ログにビルド情報を出力するためにdry-runで実行し直す。
# dry-runを一回流すと、情報を集め直さないといけないみたい。
echo "Publishing build information of atrtifacts to ${ARTIFACTORY_SERVER_ID} using jfrog cli."
jfrog rt u --spec .ci/jfrog-cli-spec.json --spec-vars "dist-dir=${BUILT_ARTIFACTS_DIR};type=${type}" --module "${module_name}" --props "artifactory.licenses=Apache-2.0"
jfrog rt bce
jfrog rt bag --config "${PROJECT_ROOT_DIR}/.ci/jfrog-cli-bag-config-${type}.yml" "${PROJECT_ROOT_DIR}"
jfrog rt bp --dry-run

echo "export JFROG_CLI_BUILD_NAME=\"${JFROG_CLI_BUILD_NAME}\"" >"${PROJECT_ROOT_DIR}/${JFROG_CLI_UPLOADED_BUILD_INFO}"
echo "export JFROG_CLI_BUILD_NUMBER=\"${JFROG_CLI_BUILD_NUMBER}\"" >>"${PROJECT_ROOT_DIR}/${JFROG_CLI_UPLOADED_BUILD_INFO}"
echo "export JFROG_CLI_BUILD_URL=\"${JFROG_CLI_BUILD_URL}\"" >>"${PROJECT_ROOT_DIR}/${JFROG_CLI_UPLOADED_BUILD_INFO}"

[[ -z $(git status -s) ]] || echo '!!!!!!!!!! dirty !!!!!!!!!'
