"""Model command interfaces only: restoration on/off, successful/failed curl."""
import json,pathlib,shlex,subprocess,tempfile
lesson=json.loads(pathlib.Path('src/main/resources/content/_lx_39_bezpieczenstwo/05_SelinuxBasics.json').read_text(encoding='utf-8'))
solution=lesson['exercises'][23]['solution']
with tempfile.TemporaryDirectory(prefix='javaquest-selinux-boolean-') as folder:
    state=pathlib.Path(folder)/'state.txt'
    for initial in ['on','off']:
        for fail in [0,1]:
            state.write_text(initial,encoding='ascii')
            script='state='+shlex.quote(state.as_posix())+'\nfail='+str(fail)+'\n'
            script+=r'''
sudo() { "$@"; }
getsebool() { printf 'httpd_can_network_connect --> %s\n' "$(cat "$state")"; }
setsebool() { test "$1" = httpd_can_network_connect || return 1; printf '%s' "$2" > "$state"; }
curl() { if [ "$fail" = 1 ]; then return 7; else printf '200\n'; fi; }
'''
            script+=solution
            result=subprocess.run(['C:/Program Files/Git/bin/bash.exe','--noprofile','--norc','-c',script],capture_output=True,text=True,timeout=15)
            assert state.read_text(encoding='ascii')==initial,(initial,fail,result.stdout,result.stderr)
            assert ' --> '+initial in result.stdout
            print(f'OK: initial={initial}, curl_failure={fail}, restored={initial}')
print('Model only; no live SELinux enforcement or network boolean modified.')

