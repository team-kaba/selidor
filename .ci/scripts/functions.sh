# shellcheck disable=SC1090

set -euo pipefail

function mvnw() {
  local command="${PROJECT_ROOT_DIR}/mvnw"
  local args=("-U")
  if [ "${DEBUG:-}" == "true" ]; then
    args=("-X" "${args[@]}")
  else
    args=("-e" "${args[@]}")
  fi
  if [ -n "${MAVEN_LOCAL_REPOSITORY:-}" ]; then
    args=("-Dmaven.repo.local=${MAVEN_LOCAL_REPOSITORY}" "-Drepository=file://${PROJECT_ROOT_DIR}/${MAVEN_LOCAL_REPOSITORY}" "${args[@]}")
  fi
  if is_pull_request; then
    args=("-Drevision=$(pull_request_revision)" "${args[@]}")
  fi
  echo "Running command:" "${command}" "${args[@]}" "${@}"
  if [ "${DRY_RUN:-}" != "true" ]; then
    "${command}" "${args[@]}" "${@}"
  fi
}

function jfrog() {
  local command="${JFROG_CLI_BIN_DIR:-${PROJECT_ROOT_DIR}/.ci/.jfrog/bin}/jfrog"
  local args=()
  if [ "${DRY_RUN:-}" == "true" ]; then
    args=("--dry-run")
  fi
  echo "Running command:" "${command}" "${args[@]}" "${@}"
  "${command}" "${args[@]}" "${@}"
}

function is_pull_request() {
  [ -n "${VCS_PULL_REQUEST_ID:-}" ]
}

function pull_request_revision() {
  echo "$(get_revision_from_pom)-${VCS_PULL_REQUEST_ID}-SNAPSHOT"
}

function get_revision_from_pom() {
  xmllint --xpath '/*[local-name()="project"]/*[local-name()="properties"]/*[local-name()="revision"]/text()' "${PROJECT_ROOT_DIR}/pom.xml"
}

function is_set() {
  local has_failure=0
  for v in "${@}"; do
    [ -z "${!v:-}" ] && echo "${v} is not set." && has_failure=1
  done
  if [ "${has_failure}" -eq 0 ]; then
    true
  else
    false
  fi
}
