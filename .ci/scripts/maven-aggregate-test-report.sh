#!/bin/bash
# shellcheck disable=SC1090

set -euo pipefail

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")"
  /bin/pwd
)"

source "${script_dir}/source.sh"

test_report_dir="${TEST_REPORT_DIR:-${PROJECT_ROOT_DIR}/test-reports/junit}"

mkdir -p "${test_report_dir}"
find "${PROJECT_ROOT_DIR}" -type f -regextype egrep -regex ".*/target/(surefire|failsafe)-reports/.*\.xml" -not -name "failsafe-summary.xml" -print0 |
  xargs -0 -t -r -n 1000 cp -t "${test_report_dir}"
