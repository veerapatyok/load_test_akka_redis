#!/bin/bash -e

. /opt/bitnami/base/functions
. /opt/bitnami/base/helpers

print_welcome_page

if [[ "$1" == "nami" && "$2" == "start" ]] || [[ "$1" == "/run.sh" ]]; then
  . /init.sh
  nami_initialize redis
  info "Starting redis... "
fi

exec tini -- "$@"
