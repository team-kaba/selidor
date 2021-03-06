#!/bin/bash
# shellcheck disable=SC1090

set -euo pipefail

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")"
  /bin/pwd
)"

source "${script_dir}/source.sh"

dir="${1:-${JFROG_CLI_BIN_DIR:-.ci/.jfrog/bin}}"
version="${2:-${JFROG_CLI_VERSION:-1.28.0}}"

command="${dir}/jfrog"

download_url="https://bintray.com/api/ui/download/jfrog/jfrog-cli-go/${version}/jfrog-cli-linux-amd64/jfrog"

if ! type "${command}" || [[ "$("${command}" --version)" != *" ${version}"* ]]; then
  echo "Downloading jfrog cli (version: ${version}) from ${download_url}."
  mkdir -p "${dir}"
  curl -fvLo "${command}" "${download_url}"
  chmod +x "${command}"
fi
jfrog --version

echo "Configuring jfrog cli (version: ${version})."
# configure のときは、クレデンシャルが引数に含まれるのでログ出力したくない。
# jfrog 関数ではなく、$command としてjfrogの実行ファイルを直接実行するようにしておく。
bintray_config="$("${command}" bt c show)"
if [[ "${bintray_config}" != *"User: ${BINTRAY_USER}"* ]]; then
  echo "Cofniguring JFrog CLI for bintray."
  "${command}" bt c --user "${BINTRAY_USER}" --key "${BINTRAY_KEY}" --licenses "${BINTRAY_DEFAULT_LICENSES}" --interactive=false
else
  echo "JFrog CLI for bintray is already configured. user=[${BINTRAY_USER}]"
  echo "${bintray_config}"
fi
artifactory_config="$("${command}" rt c show)"
if [[ "${artifactory_config}" != *"Server ID: ${ARTIFACTORY_SERVER_ID}"* ]]; then
  echo "Cofniguring JFrog CLI for ${ARTIFACTORY_SERVER_ID}."
  "${command}" rt c "${ARTIFACTORY_SERVER_ID}" --url "${ARTIFACTORY_URL}" --user "${ARTIFACTORY_USER}" --apikey "${ARTIFACTORY_APIKEY}" --interactive=false
else
  echo "JFrog CLI for ${ARTIFACTORY_SERVER_ID} is already configured. server=[${ARTIFACTORY_SERVER_ID}]"
  echo "${artifactory_config}"
fi
