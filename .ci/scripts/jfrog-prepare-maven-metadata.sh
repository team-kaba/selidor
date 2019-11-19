#!/bin/bash
# shellcheck disable=SC1090

set -euo pipefail

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")"
  /bin/pwd
)"

source "${script_dir}/source.sh"

type="${1}"

project_revision=$(get_project_revision_from_pom)
group_id=$(get_project_root_group_id_from_pom)
jfrog rt dl --spec "${PROJECT_ROOT_DIR}/.ci/jfrog-cli-rt-download-maven-metadata.json" --spec-vars "dist-dir=${BUILT_ARTIFACTS_DIR};type=${type};root=${group_id//.//};version=${project_revision}"

if [ -d "${BUILT_ARTIFACTS_DIR}" ]; then
  find "${BUILT_ARTIFACTS_DIR}" -type f -name '*.xml' -execdir sh -c 'f="${1}"; md5sum  "${f}" > "${f}".md5' _ {} \;
  find "${BUILT_ARTIFACTS_DIR}" -type f -name '*.xml' -execdir sh -c 'f="${1}"; sha1sum "${f}" > "${f}".sha1' _ {} \;
fi
