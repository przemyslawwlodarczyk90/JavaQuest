#!/usr/bin/env bash
# Jawny maly fixture do weryfikacji filtrow; nie oryginalny log kursu.
set -euo pipefail
export LC_ALL=C
lab=$(mktemp -d)
trap 'rm -f -- "$lab/access.log"; rmdir -- "$lab"' EXIT
cat > "$lab/access.log" <<'EOF'
10.0.0.5 - - [12/Oct/2026:10:00:00 +0000] "GET /api/zamowienia HTTP/1.1" 500 10 "-" "test"
10.0.0.50 - - [12/Oct/2026:10:00:01 +0000] "GET /api/zamowienia HTTP/1.1" 500 10 "-" "test"
192.168.1.10 - - [12/Oct/2026:10:00:02 +0000] "GET /api/produkty?limit=5 HTTP/1.1" 200 500 "-" "test"
192.168.1.10 - - [12/Oct/2026:10:00:03 +0000] "GET /api/produkty?limit=10 HTTP/1.1" 200 10 "-" "test"
203.0.113.7 - - [12/Oct/2026:10:00:04 +0000] "GET /admin HTTP/1.1" 404 10 "-" "test"
203.0.113.7 - - [12/Oct/2026:10:00:05 +0000] "GET /admin HTTP/1.1" 404 10 "-" "test"
EOF
cd "$lab"
test "$(grep '" 500 ' access.log | wc -l)" -eq 2
test "$(grep '" 500 ' access.log | grep '/api/zamowienia' | wc -l)" -eq 2
test "$(grep '" 500 ' access.log | grep -v '^10[.]0[.]0[.]5 ' | wc -l)" -eq 1
test "$(grep '" 404 ' access.log | awk '{print $7}' | sort -u)" = /admin
test "$(awk '{sciezka=$7; sub(/\?.*/, "", sciezka); print sciezka}' access.log | sort | uniq -c | sort -nr | head -n 3 | awk '{print $1, $2}')" = $'2 /api/zamowienia\n2 /api/produkty\n2 /admin'
test "$(awk '{print $1}' access.log | sort | uniq -c | sort -nr | head -n 3 | awk '{print $1, $2}')" = $'2 203.0.113.7\n2 192.168.1.10\n1 10.0.0.50'
printf 'OK: precyzyjny kod HTTP, dokladny adres IP, normalizacja URL, unikalne sciezki i ranking\n'
