#!/bin/bash
# uzycie: lx_screen.sh KOLUMNY WIERSZE 'polecenie' [klawisze tmux ...]
# Uruchamia polecenie jako anna w tmux (TERM=xterm) w ~/tekst, wysyla klawisze i wypisuje ekran.
# Klawisz "@" = zrzut ekranu w tym miejscu (ostatni zrzut zawsze na koncu).
C=$1; R=$2; CMD=$3; shift 3
MSYS_NO_PATHCONV=1 docker exec -u anna -w /home/anna -e LANG=C.UTF-8 lxlab bash -c '
C=$1; R=$2; CMD=$3; shift 3
tmux kill-session -t s 2>/dev/null
tmux new-session -d -s s -x $C -y $R "cd ~/tekst; PS1=\"\$ \" bash --norc -i"
tmux set -t s status off; sleep 0.3
tmux send-keys -t s "$CMD" Enter; sleep 0.8
for k in "$@"; do
  if [ "$k" = "@" ]; then tmux capture-pane -p -t s; echo "-----"; else tmux send-keys -t s "$k"; sleep 0.5; fi
done
tmux capture-pane -p -t s; tmux kill-session -t s' x "$C" "$R" "$CMD" "$@"
