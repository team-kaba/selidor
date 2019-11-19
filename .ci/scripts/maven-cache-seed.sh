#!/bin/bash
# shellcheck disable=SC1090

set -euo pipefail

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")"
  /bin/pwd
)"

source "${script_dir}/source.sh"

project_revision=$(get_project_revision_from_pom)
revision="$(revision_for_current_build "${project_revision}")"

echo "revision=${revision}"
cat "${@}"
