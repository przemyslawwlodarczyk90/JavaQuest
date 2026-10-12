#!/bin/sh
# Laboratorium BusyBox: tylko wlasne porty 127.0.0.1 i pliki tymczasowe.
# Uruchomienie w docker-desktop WSL nie uruchamia silnika Docker.
set -eu
lab=$(mktemp -d /tmp/javaquest-loopback.XXXXXX)
tcp_pid=
udp_pid=
cleanup() {
    if [ -n "$tcp_pid" ]; then kill "$tcp_pid" 2>/dev/null || true; wait "$tcp_pid" 2>/dev/null || true; fi
    if [ -n "$udp_pid" ]; then kill "$udp_pid" 2>/dev/null || true; wait "$udp_pid" 2>/dev/null || true; fi
    rm -f "$lab/tcp" "$lab/udp" "$lab/tcp.err" "$lab/udp.err" "$lab/closed.err"
    rmdir "$lab"
}
trap cleanup EXIT
trap 'exit 130' INT TERM
for port in 55989 55990; do
    if netstat -an 2>/dev/null | grep -q ":$port "; then
        echo "Test port already used: $port" >&2
        exit 1
    fi
done
nc -l -s 127.0.0.1 -p 55989 > "$lab/tcp" 2> "$lab/tcp.err" &
tcp_pid=$!
i=0
until netstat -tln 2>/dev/null | grep -q '127.0.0.1:55989 .*LISTEN'; do
    i=$((i+1)); test "$i" -lt 30; sleep 0.1
done
printf 'private TCP exercise\n' | nc -n -w 1 127.0.0.1 55989
wait "$tcp_pid"
tcp_pid=
test "$(cat "$lab/tcp")" = 'private TCP exercise'
if netstat -tln 2>/dev/null | grep -q '127.0.0.1:55989 .*LISTEN'; then
    echo 'TCP listener still present' >&2; exit 1
fi
if printf 'closed test\n' | nc -n -w 1 127.0.0.1 55989 2> "$lab/closed.err"; then
    echo 'Unexpected success connecting to closed TCP port' >&2; exit 1
fi
nc -u -l -s 127.0.0.1 -p 55990 > "$lab/udp" 2> "$lab/udp.err" &
udp_pid=$!
i=0
until netstat -uln 2>/dev/null | grep -q '127.0.0.1:55990 '; do
    i=$((i+1)); test "$i" -lt 30; sleep 0.1
done
printf 'private UDP exercise\n' | nc -u -n -w 1 127.0.0.1 55990
i=0
until grep -q '^private UDP exercise$' "$lab/udp"; do
    i=$((i+1)); test "$i" -lt 30; sleep 0.1
done
kill "$udp_pid" 2>/dev/null || true
wait "$udp_pid" 2>/dev/null || true
udp_pid=
echo 'OK: TCP listener/delivery/closed port, UDP bound socket and actual receiver payload'
echo 'BusyBox loopback lab; not VM services, OpenBSD nc, ss, nmap or tcpdump verification.'
