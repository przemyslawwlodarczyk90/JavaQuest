"""Wykonuje 30 rozwiazan w jednej sesji Bash; jar i HTTP sa wlasnym modelem."""
import json
import os
import signal
import subprocess

with open('/tmp/javaquest-38-09.json') as source:
    lesson=json.load(source)
assert len(lesson['exercises'])==30
checks={
    2:'test "$(java -jar "$deploy_lab/releases/1.0/app.jar")" = 1.0',
    3:'test "$(java -jar "$deploy_lab/releases/1.1/app.jar")" = 1.1',
    7:'test "$(sha256sum "$deploy_lab/candidate/app.jar" | cut -d" " -f1)" != "$(sha256sum "$deploy_lab/releases/1.1/app.jar" | cut -d" " -f1)"',
    8:'cmp "$deploy_lab/candidate/app.jar" "$deploy_lab/releases/1.1/app.jar"',
    9:'test "$(stat -c %a "$deploy_lab/releases/1.0/app.jar")" = 444',
    12:'test "$(java -jar "$deploy_lab/current.jar")" = 1.0',
    13:'test "$(cat "$deploy_lab/previous.link")" = releases/1.0/app.jar',
    14:'test "$(readlink "$deploy_lab/current.jar")" = releases/1.1/app.jar\ntest ! -e "$deploy_lab/current.next"',
    15:'test "$(java -jar "$deploy_lab/current.jar")" = 1.1',
    17:'test "$(readlink "$deploy_lab/current.jar")" = releases/1.0/app.jar',
    20:'kill -0 "$server_pid"\ntest "$port" -gt 0\ntest "$port" -le 65535',
    24:'if ready; then printf "DOWN accepted incorrectly\\n" >&2; exit 1; fi',
    25:'ready',
    26:'test "$(cat "$deploy_lab/standby.state")" = "1.1 UP"\ntest "$(cat "$deploy_lab/primary.state")" = "1.1 UP"',
    27:'test "$(cat "$deploy_lab/primary.state")" = "1.0 UP"\nready',
    28:'test -d "$deploy_lab/releases/0.8"\ntest -f "$deploy_lab/releases/1.0/app.jar"\ntest -f "$deploy_lab/releases/1.1/app.jar"',
    29:'if kill -0 "$server_pid" 2>/dev/null; then printf "Own server remained alive\\n" >&2; exit 1; fi\nserver_pid=',
    30:'test ! -e "$deploy_lab"'
}
script='''set -e
cleanup() {
 if [ -n "${server_pid:-}" ] && [ -r "/proc/$server_pid/cmdline" ] && tr '\\0' ' ' < "/proc/$server_pid/cmdline" | grep -Fq -- "$deploy_lab/health-fixture"; then
   kill "$server_pid" 2>/dev/null || true; wait "$server_pid" 2>/dev/null || true
 fi
 case "${deploy_lab:-}" in /tmp/javaquest-deploy.*)
   if [ -d "$deploy_lab" ] && [ "$(readlink -f "$deploy_lab")" = "$deploy_lab" ]; then rm -rf -- "$deploy_lab"; fi;;
 esac
}
trap cleanup EXIT
trap 'exit 130' INT TERM
'''
for index,exercise in enumerate(lesson['exercises'],1):
    script+=exercise['solution']+'\n'+checks.get(index,':')+f'\nprintf "PASS {index}\\n"\n'
process=subprocess.Popen(['bash','--noprofile','--norc','-c',script],text=True,
                         stdout=subprocess.PIPE,stderr=subprocess.PIPE,start_new_session=True)
try:
    output,errors=process.communicate(timeout=120)
except subprocess.TimeoutExpired:
    os.killpg(process.pid,signal.SIGTERM)
    try: output,errors=process.communicate(timeout=5)
    except subprocess.TimeoutExpired:
        os.killpg(process.pid,signal.SIGKILL);output,errors=process.communicate()
    raise RuntimeError('Own deployment lab timed out')
if process.returncode:
    raise RuntimeError(f'Lab failed ({process.returncode})\n{output}\n{errors}')
assert sum(line.startswith('PASS ') for line in output.splitlines())==30,output
assert 'mismatch detected' in output
assert 'curl exit=0' in output and 'not ready' in output
assert 'invalid response rejected' in output and 'primary unchanged' in output
print('OK: 30 solutions; real jars/checksums/symlink rollback, own HTTP readiness gate and cleanup')
print('Not a systemd, production rolling, Nginx failover or database migration test.')
