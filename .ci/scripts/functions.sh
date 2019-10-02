function is_set() {
  local has_failure=0
  for v in "${@}"; do
    [ -z "${!v:-}" ] && echo "${v} is not set." && has_failure=1
  done
  return ${has_failure}
}

function is_pull_request() {
  [[ -n "${VCS_PULL_REQUEST_ID:-}" ]]
}
