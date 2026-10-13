"""Real AIDE in a private chroot; offline AppArmor syntax only."""
import json,pathlib,re,subprocess,tempfile
with open('/tmp/lesson08.json') as source:
    lesson=json.load(source)
assert len(lesson['exercises'])==30
checks={
17:'aide --config-check --config "$integ_lab/aide.conf" || exit 1',
18:'test -s "$integ_lab/aide.db" || exit 1; cmp "$integ_lab/aide.db" "$integ_lab/kopia/aide.db" || exit 1',
19:'aide --check --config "$integ_lab/aide.conf" >/dev/null; test "$?" = 0 || exit 1',
20:'test "$aide_rc" = 5 || exit 1',
21:'grep -q "File: $integ_lab/dane/app.conf" "$integ_lab/raport.txt" || exit 1',
22:'aide --check --config "$integ_lab/aide.conf" >/dev/null; test "$?" = 0 || exit 1; ! cmp -s "$integ_lab/aide.db" "$integ_lab/kopia/aide.db" || exit 1',
23:'aide --check --config "$integ_lab/aide.conf" --before="database_in=file:$integ_lab/kopia/aide.db" >/dev/null; test "$?" = 5 || exit 1'
}
script='''umask 077
integ_lab=$(mktemp -d /tmp/javaquest-aide.XXXXXX)
cleanup() {
 case "$integ_lab" in /tmp/javaquest-aide.*) if [ "$(readlink -f "$integ_lab")" = "$integ_lab" ]; then rm -rf -- "$integ_lab"; fi;; esac
}
trap cleanup EXIT
mkdir -p "$integ_lab/dane/lib"
printf '#!/bin/bash\\necho wdrozenie\\n' > "$integ_lab/dane/wdroz.sh"
printf 'port=8080\\nlog=INFO\\n' > "$integ_lab/dane/app.conf"
head -c 256 /dev/urandom > "$integ_lab/dane/lib/biblioteka.jar"
'''
for number in range(17,24):
    script+=lesson['exercises'][number-1]['solution']+'\n'+checks[number]+f'\nprintf "SOURCE {number} OK\\n"\n'
script+='''rm -- "$integ_lab/dane/lib/biblioteka.jar"
aide --check --config "$integ_lab/aide.conf" --before="database_in=file:$integ_lab/kopia/aide.db" >/dev/null
test "$?" = 7 || exit 1
printf 'Added+removed+changed=7 OK\\n'
'''
p=subprocess.run(['bash','--noprofile','--norc','-c',script],capture_output=True,text=True,timeout=120)
print(p.stdout)
print(p.stderr)
assert p.returncode==0,p.returncode
assert sum(x.startswith('SOURCE ') for x in p.stdout.splitlines())==7
# Extract the exact profile body; do not load it into the kernel.
with open('/tmp/lesson06.json') as source:
    apparmor=json.load(source)
command=apparmor['exercises'][10]['solution']
match=re.search(r"<<'EOF'\n(.*?)\nEOF",command,re.S)
assert match
with tempfile.TemporaryDirectory(prefix='javaquest-apparmor-',dir='/tmp') as folder:
    path=pathlib.Path(folder)/'jq-raport'
    path.write_text(match.group(1)+'\n')
    result=subprocess.run(['apparmor_parser','-QK',str(path)],capture_output=True,text=True,timeout=30)
    print(result.stdout)
    print(result.stderr)
    assert result.returncode==0,result.returncode
print('AIDE exact source 17-23 and codes 0/5/7 verified; AppArmor exact profile parsed -QK without loading. No MAC enforcement tested.')

