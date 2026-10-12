"""Realne prawa DAC i integralnosc; unit sprawdzany tylko skladniowo."""
import json
import subprocess

with open('/tmp/javaquest-39-01.json') as source:
    lesson = json.load(source)
assert len(lesson['exercises']) == 30
checks = {
    2: 'test "$(stat -c %a "$security_lab/private.conf")" = 600',
    4: '! runuser -u anna -- cat "$security_lab/private.conf"',
    5: 'test "$(runuser -u anna -- cat "$security_lab/public.txt")" = service=model-only',
    6: 'test "$(stat -c %U "$security_lab/anna-work/notes.txt")" = anna',
    7: 'test "$(cat "$security_lab/private.conf")" = fixture-password=training-only',
    8: 'test "$(runuser -u anna -- cat "$security_lab/group.conf")" = group-fixture=read-only',
    9: 'test "$(cat "$security_lab/group.conf")" = group-fixture=read-only',
    10: 'test "$(stat -c %a "$security_lab/mask-022.txt")" = 644',
    11: 'test "$(stat -c %a "$security_lab/mask-077.txt")" = 600',
    12: 'test "$(stat -c %a "$security_lab/world-writable.txt")" = 666',
    13: 'test "$(cat "$security_lab/world-writable.txt")" = "insecure fixture"',
    14: '! runuser -u anna -- cat "$security_lab/traverse/item.txt"',
    15: 'test "$(runuser -u anna -- cat "$security_lab/traverse/item.txt")" = "known filename"',
    16: '! runuser -u anna -- ls "$security_lab/traverse"',
    17: 'test "$(stat -c %a "$security_lab/shared")" = 1777',
    18: 'test -f "$security_lab/shared/anna.txt"',
    19: 'test "$(cat "$security_lab/shared/anna.txt")" = "modified fixture"',
    20: 'test "$(cat "$security_lab/shared/anna.txt")" = "modified fixture"',
    23: '! cmp -s "$security_lab/integrity/data.txt" "$security_lab/integrity/backup.txt"',
    24: 'cmp "$security_lab/integrity/data.txt" "$security_lab/integrity/backup.txt"',
    28: 'test "$(grep -c =yes "$security_lab/fixture.service")" = 3',
    29: 'grep -q "not_tested=service runtime sandbox" "$security_lab/report.txt"',
    30: 'test ! -e "$security_lab"; test "$(umask)" = "$saved_umask"'
}
script = '''set -e
cleanup() {
 case "${security_lab:-}" in /tmp/javaquest-security.*)
  if [ -d "$security_lab" ] && [ "$(readlink -f "$security_lab")" = "$security_lab" ]; then rm -rf -- "$security_lab"; fi;;
 esac
}
trap cleanup EXIT
'''
for index, exercise in enumerate(lesson['exercises'], 1):
    script += exercise['solution'] + '\n' + checks.get(index, ':') + f'\nprintf "PASS {index}\\n"\n'
result = subprocess.run(['bash', '--noprofile', '--norc', '-c', script],
                        capture_output=True, text=True, timeout=120)
print(result.stdout)
print(result.stderr)
assert result.returncode == 0, result.returncode
assert sum(line.startswith('PASS ') for line in result.stdout.splitlines()) == 30
assert 'unexpected ' not in result.stdout
print('30 rozwiazan i asercje: OK; bez testu runtime systemd.')
