"""Read-only APT and own mmap model; no service or kernel restart."""
import json,os,signal,subprocess
with open('/tmp/javaquest-39-02.json') as source:
    lesson=json.load(source)
assert len(lesson['exercises'])==30
checks={
 6:'test -s "$patch_lab/plan.txt"',
 12:'test "$(wc -l < "$patch_lab/advisory.tsv")" = 3',
 13:'test "$(while IFS="$(printf \'\\t\')" read -r p i f; do dpkg --compare-versions "$i" lt "$f" && printf "%s\\n" "$p"; done < "$patch_lab/advisory.tsv")" = web-core',
 16:'test "$(apt-config -c "$patch_lab/periodic.conf" shell REBOOT Unattended-Upgrade::Automatic-Reboot)" = "REBOOT=\'false\'"',
 18:'test "$(wc -l < "$patch_lab/run-fixture/reboot-required.pkgs")" = 2',
 21:'test "$(grep -c " ERROR " "$patch_lab/unattended.log")" = 1',
 22:'test "$(cat "$patch_lab/old.txt")" = "old fixture"; kill -0 "$patch_pid"',
 23:'test "$(cat "$patch_lab/library.bin")" = "new fixture"; test "$(cat "$patch_lab/old.txt")" = "old fixture"',
 26:'test "$(cat "$patch_lab/new.txt")" = "new fixture"',
 27:'test "$(wc -l < "$patch_lab/external.tsv")" = 4',
 28:'grep -q "do not drain B" "$patch_lab/window.txt"',
 29:'grep -q "not_verified=" "$patch_lab/scope.txt"',
 30:'test ! -e "$patch_lab"; test "$(umask)" = "$patch_mask"'
}
script='''set -e
cleanup() {
 if [ -n "${patch_pid:-}" ] && [ -r "/proc/$patch_pid/cmdline" ] && tr '\\0' ' ' < "/proc/$patch_pid/cmdline" | grep -Fq -- "$patch_lab/map.py"; then
  kill "$patch_pid" 2>/dev/null || true; wait "$patch_pid" 2>/dev/null || true
 fi
 case "${patch_lab:-}" in /tmp/javaquest-patching.*)
  if [ -d "$patch_lab" ] && [ "$(readlink -f "$patch_lab")" = "$patch_lab" ]; then rm -rf -- "$patch_lab"; fi;;
 esac
}
trap cleanup EXIT
trap 'exit 130' TERM INT
'''
for i,ex in enumerate(lesson['exercises'],1):
    script+=ex['solution']+'\n'+checks.get(i,':')+f'\nprintf "PASS {i}\\n"\n'
p=subprocess.Popen(['bash','--noprofile','--norc','-c',script],stdout=subprocess.PIPE,stderr=subprocess.PIPE,text=True,start_new_session=True)
try:
    out,err=p.communicate(timeout=120)
except subprocess.TimeoutExpired:
    os.killpg(p.pid,signal.SIGTERM)
    out,err=p.communicate(timeout=10)
    raise AssertionError('Timeout: '+out+err)
print(out)
print(err)
assert p.returncode==0,p.returncode
assert sum(l.startswith('PASS ') for l in out.splitlines())==30
print('30 solutions verified; no packages installed and no service/kernel restart.')

