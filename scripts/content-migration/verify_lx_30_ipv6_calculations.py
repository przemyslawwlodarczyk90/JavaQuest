"""Sprawdza kod obliczen IPv6; nie testuje interfejsow ani lacznosci VM."""
import json
import pathlib
import re
import subprocess
import sys

root=pathlib.Path(__file__).resolve().parents[2]
lesson=json.loads((root/'src/main/resources/content/_lx_30_podstawy_sieci/09_Ipv6Basics.json').read_text(encoding='utf-8'))
outputs={}
for index in (12,13,14):
    code=re.search(r"python3 -c '([^']*)'",lesson['exercises'][index-1]['solution'],re.S).group(1)
    outputs[index]=subprocess.check_output([sys.executable,'-c',code],text=True).splitlines()
assert outputs[12]==['2001:0db8:0000:0000:0000:ff00:0042:8329','2001:db8::ff00:42:8329']
assert outputs[13]==['::1 True False True','fe80::1 False True True','fd00:56::10 False False True','2001:db8::1 False False True']
assert outputs[14]==['18446744073709551616','65536']
bash=pathlib.Path('C:/Program Files/Git/bin/bash.exe')
if not bash.exists():
    raise RuntimeError('Git Bash required for exact EUI-64 shell calculation')
code=lesson['exercises'][14]['solution'].split('\nip -6 addr',1)[0]
eui=subprocess.check_output([str(bash),'-c',code],text=True).strip()
assert eui=='fe80::a00:27ff:fe91:4d07',eui
print('OK: expansion/compression, scopes, /64 count, /48 subnet count and modified EUI-64')
