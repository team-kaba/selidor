# shellcheck disable=SC1090

script_dir="$(
  cd "$(dirname "${BASH_SOURCE:-$0}")" || exit
  /bin/pwd
)"

source "${script_dir}/env.sh"
source "${script_dir}/functions.sh"
source "${script_dir}/functions-git.sh"
source "${script_dir}/functions-java.sh"
