# shellcheck disable=SC1090

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")" || exit
  /bin/pwd
)"

PROJECT_ROOT_DIR="$(
  cd "${script_dir}" || exit
  /usr/bin/git rev-parse --show-toplevel
)"
export PROJECT_ROOT_DIR

source "${PROJECT_ROOT_DIR}/.ci/project.env"
