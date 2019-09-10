# shellcheck disable=SC1090

set -euo pipefail

function mvnw() {
  local args=("-U")
  if [ "${DEBUG:-}" == "true" ]; then
    args=("-X" "${args[@]}")
  else
    args=("-e" "${args[@]}")
  fi
  if [ -n "${MAVEN_LOCAL_REPOSITORY:-}" ]; then
    args=("-Dmaven.repo.local=${MAVEN_LOCAL_REPOSITORY}" "-Drepository=file://${PROJECT_ROOT_DIR}/${MAVEN_LOCAL_REPOSITORY}" "${args[@]}")
  fi
  echo "Running command:" "${PROJECT_ROOT_DIR}/mvnw" "${args[@]}" "$@"
  if [ "${DRY_RUN:-}" != "true" ]; then
    "${PROJECT_ROOT_DIR}/mvnw" "${args[@]}" "$@"
  fi
}
