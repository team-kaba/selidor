#!/bin/bash
# shellcheck disable=SC1090

set -euo pipefail

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")"
  /bin/pwd
)"

source "${script_dir}/env.sh"
source "${script_dir}/functions.sh"

mvnw de.qaware.maven:go-offline-maven-plugin:1.2.1:resolve-dependencies
