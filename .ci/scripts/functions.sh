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
#  "${command}" "${args[@]}" "${@}"
  "${command}" --version
}
