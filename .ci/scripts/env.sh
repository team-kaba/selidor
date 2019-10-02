# shellcheck disable=SC1090

set -euo pipefail

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")"
  /bin/pwd
)"

PROJECT_ROOT_DIR="$(
  cd "${script_dir}"
  /usr/bin/git rev-parse --show-toplevel
)"
export PROJECT_ROOT_DIR

BUILT_ARTIFACTS_DIR="${PROJECT_ROOT_DIR}/${MAVEN_DISTRIBUTION_DIR:-dist}"
export BUILT_ARTIFACTS_DIR

ALT_DEPLOYMENT_REPOSITORY="distribution::default::file://${BUILT_ARTIFACTS_DIR}"
export ALT_DEPLOYMENT_REPOSITORY

if [ "${CI:-}" != "true" ]; then
  env_file="${PROJECT_ROOT_DIR}/.env.local"
elif [ "${CIRCLECI}" == "true" ]; then
  env_file="${PROJECT_ROOT_DIR}/.circleci/.env"
fi

if [ -f "${env_file:-}" ]; then
  source "${env_file}"
else
  echo "'${env_file}' is not found."
fi

source "${PROJECT_ROOT_DIR}/.ci/project.env"
