"""Own account lifecycle only. No PAM authentication or SSH tests."""
import json,subprocess
with open('/tmp/javaquest-39-03.json') as source:
    lesson=json.load(source)
assert len(lesson['exercises'])==30
checks={
 3:'test "$(awk -F: \'$3==0 {print $1}\' "$account_lab/passwd.fixture" | wc -l)" = 2',
 5:'test "$(awk -F: \'$2=="" {print $1}\' "$account_lab/shadow.fixture")" = empty',
 6:'test "$(awk -F: \'substr($2,1,1)=="!" {print $1}\' "$account_lab/shadow.fixture")" = bob',
 8:'test "$(wc -l < "$account_lab/shadow.fixture")" = 4',
 9:'test "$(stat -c %a "$account_lab/shadow.fixture")" = 600',
 10:'test "$lab_uid" -gt 0; test "$(getent passwd "$lab_user" | cut -d: -f7)" = /usr/sbin/nologin',
 11:'test "$(passwd -S "$lab_user" | awk \'{print $2}\')" = L',
 12:'test "$(stat -c %u "$account_lab/home")" = "$lab_uid"',
 13:'test "$(awk -F: -v u="$lab_user" \'$1==u {print $4,$5,$6,$7}\' /etc/shadow)" = "1 90 14 7"',
 14:'test "$(awk -F: -v u="$lab_user" \'$1==u {print $8}\' /etc/shadow)" = "$(python3 -c \'import datetime;print((datetime.date(2030,12,31)-datetime.date(1970,1,1)).days)\')"',
 16:'test "$(passwd -S "$lab_user" | awk \'{print $2}\')" = L',
 17:'test "$(awk -F: -v u="$lab_user" \'$1==u {print $8}\' /etc/shadow)" = 1',
 18:'id -nG "$lab_user" | tr " " "\\n" | grep -Fx "$lab_group"',
 19:'! id -nG "$lab_user" | tr " " "\\n" | grep -Fx "$lab_group"',
 21:'test "$(cat "$account_lab/keys.fixture")" = fixture-key-alice',
 23:'test "$(awk -F \'\\t\' \'$2=="bob" {print $1}\' "$account_lab/access.tsv" | wc -l)" = 2',
 27:'! pgrep -u "$lab_uid"',
 28:'! getent passwd "$lab_user"; ! getent group "$lab_group"',
 29:'test "$(find "$account_lab" -uid "$lab_uid" | wc -l)" = 1',
 30:'test ! -e "$account_lab"; test "$(umask)" = "$account_mask"'
}
script='''set -e
cleanup() {
 case "${lab_user:-}" in jqsec[0-9]*)
  if getent passwd "$lab_user" >/dev/null && [ "$(getent passwd "$lab_user" | cut -d: -f6)" = "$account_lab/home" ]; then userdel "$lab_user"; fi;;
 esac
 case "${lab_group:-}" in jqteam[0-9]*) if getent group "$lab_group" >/dev/null; then groupdel "$lab_group"; fi;; esac
 case "${account_lab:-}" in /tmp/javaquest-account.*)
  if [ -d "$account_lab" ] && [ "$(readlink -f "$account_lab")" = "$account_lab" ]; then rm -rf -- "$account_lab"; fi;;
 esac
}
trap cleanup EXIT
'''
for i,exercise in enumerate(lesson['exercises'],1):
    script+=exercise['solution']+'\n'+checks.get(i,':')+f'\nprintf "PASS {i}\\n"\n'
result=subprocess.run(['bash','--noprofile','--norc','-c',script],capture_output=True,text=True,timeout=120)
print(result.stdout)
print(result.stderr)
assert result.returncode==0,result.returncode
assert sum(line.startswith('PASS ') for line in result.stdout.splitlines())==30
print('30 solutions passed; no existing account modified; no PAM/SSH enforcement claimed.')

