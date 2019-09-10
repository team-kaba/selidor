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

if [ -z "${CI:-}" ]; then
  env_file="${PROJECT_ROOT_DIR}/.env.local"
fi

if [ -f "${env_file:-}" ]; then
  source "${env_file}"
fi
