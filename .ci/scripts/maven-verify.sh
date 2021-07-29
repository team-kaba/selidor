#!/bin/bash
# shellcheck disable=SC1090

set -euo pipefail

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")"
  /bin/pwd
)"

source "${script_dir}/source.sh"

project_to_verify=${1:-.}

project_revision=$(get_project_revision_from_pom)
revision="${project_revision}"
# smoke-testのようにReactor modulesの外側でparentのversionを${revision}としている場合、実際のバージョンに設定する必要があります。
fix_not_reactor_parent_revision "${revision}" "${project_to_verify}"

mvnw clean verify -f "${project_to_verify}/pom.xml"
