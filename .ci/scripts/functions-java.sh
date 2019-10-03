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
    args=("-Drevision=$(get_pull_request_revision_from_pom)" "${args[@]}")
  fi
  echo "Running command:" "${command}" "${args[@]}" "${@}"
  if [ "${DRY_RUN:-}" != "true" ]; then
    "${command}" "${args[@]}" "${@}"
  fi
}

function jfrog() {
  local command="${JFROG_CLI_BIN_DIR:-${PROJECT_ROOT_DIR}/.ci/.jfrog/bin}/jfrog"
  local args=()
  echo "Running command:" "${command}" "${args[@]}" "${@}"
  if [ "${DRY_RUN:-}" != "true" ]; then
    "${command}" "${args[@]}" "${@}"
  fi
}

function get_pull_request_revision_from_pom() {
  echo "$(get_revision_from_pom | sed -e 's!-SNAPSHOT$!!g')-pr${VCS_PULL_REQUEST_ID}-SNAPSHOT"
}

function get_revision_from_pom() {
  xml sel -N m=http://maven.apache.org/POM/4.0.0 -t -v '//m:project/m:properties/m:revision' "${PROJECT_ROOT_DIR}/pom.xml"
}

function set_revision_to_pom() {
  if [ -z "${1}" ]; then
    echo "[set_revision_to_pom] Pass new revision as an argument." >&2
    return 1
  fi
  xml ed -P -L -N m=http://maven.apache.org/POM/4.0.0 -u '//m:project/m:properties/m:revision' -v "${1}" "${PROJECT_ROOT_DIR}/pom.xml"
}

function cleanup_maven_repo() {
  MAVEN_LOCAL_REPOSITORY="${MAVEN_LOCAL_REPOSITORY:-/${HOME}/.m2/repository}"
  if [ -z "${1}" ]; then
    echo "[cleanup_maven_repo] Pass maven groupId as an argument." >&2
    return 1
  fi
  local repo_path
  repo_path="${MAVEN_LOCAL_REPOSITORY}"/$(echo "${1}" | tr . /)
  if [ -d "$repo_path" ]; then
    echo "Cleaning local maven repo '${1}'"
    rm -fr "$repo_path" 2>/dev/null || :
  fi
}

function get_next_version() {
  if [ -z "${1}" ]; then
    echo "[get_next_release] Pass the current revision as an argument." >&2
    return 1
  fi
  if [[ "${1}" =~ ^(.*)-SNAPSHOT$ ]]; then
    echo -n "${BASH_REMATCH[1]}"
  else
    local version
    version="$(bump_version "${1}")"
    echo -n "${version}-SNAPSHOT"
  fi
}

function bump_version() {
  if [ -z "${1}" ]; then
    echo "[bump_version] Pass the current revision as an argument." >&2
    return 1
  fi
  local version="${1}"
  if [[ "${version}" =~ ^(.*(\.|-)([A-Za-z]+))([0-9]+)$ ]]; then
    local prefix="${BASH_REMATCH[1]}"
    local suffix="${BASH_REMATCH[4]}"
    ((suffix++))
    echo -n "${prefix}${suffix}"
    return 0
  fi
  local snapshot
  if [[ "${version}" =~ ^(.*)-SNAPSHOT$ ]]; then
    snapshot="-SNAPSHOT"
    version="${BASH_REMATCH[1]}"
  fi
  readarray -td '.' tokens <<<"${version}."
  unset 'tokens[-1]'
  declare -p tokens >/dev/null
  local bumpIndex
  for i in "${!tokens[@]}"; do
    if [[ "${tokens[$i]}" =~ ^[0-9]+$ ]]; then
      bumpIndex=$i
    fi
  done
  if [ -z "${bumpIndex}" ]; then
    echo "unsupported version format. version='${version}'" >&2
    return 1
  fi
  ((tokens[bumpIndex]++))
  next="$(
    IFS=.
    echo -n "${tokens[*]}"
  )"
  echo -n "${next}${snapshot:-}"
}

function set_scm_tag_to_pom() {
  if [ -z "${1}" ]; then
    echo "[set_scm_tag_to_pom] Pass new tag as an argument." >&2
    return 1
  fi
  find "${PROJECT_ROOT_DIR}" -name pom.xml -exec xml ed -P -L -N m=http://maven.apache.org/POM/4.0.0 -u '//m:project/m:scm/m:tag' -v "${1}" {} \;
}