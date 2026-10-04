#!/bin/bash
# uzycie: sess.sh [rc|norc] < polecenia ; bash -i jako anna w pty, transkrypt od pierwszego promptu
MODE=${1:-norc}; H=/tmp/h$RANDOM
if [ "$MODE" = rc ]; then B='bash -i'; else B='bash --norc -i'; fi
{ cat; echo exit; } | MSYS_NO_PATHCONV=1 docker exec -i -u anna -w /home/anna -e TERM=dumb -e LANG=C.UTF-8 -e COLUMNS=100 -e PS1='$ ' -e HISTFILE=$H lxlab script -qfc "$B" /dev/null 2>&1 \
 | tr -d '\r' | sed -e 's/\x1b\[[0-9;?]*[a-zA-Z]//g' -e 's/\x1b\][^\a]*\a//g' | awk 'f; /^\$ / && !f {f=1; print}' | sed '$d'; MSYS_NO_PATHCONV=1 docker exec lxlab rm -f $H
