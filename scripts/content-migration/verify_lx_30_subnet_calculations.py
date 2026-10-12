"""Uruchamia samowystarczalne obliczenia Python z lekcji; bez operacji sieciowych."""
import ipaddress
import json
import pathlib
import re
import subprocess
import sys

root=pathlib.Path(__file__).resolve().parents[2]
lesson=json.loads((root/'src/main/resources/content/_lx_30_podstawy_sieci/03_SubnetsAndCidr.json').read_text(encoding='utf-8'))
outputs={}
for index, exercise in enumerate(lesson['exercises'],1):
    code_blocks=re.findall(r"python3 -c '([^']*)'", exercise['solution'], re.S)
    if not code_blocks:
        continue
    outputs[index]=[subprocess.check_output([sys.executable,'-c',code],text=True).strip() for code in code_blocks]
assert outputs[2]==['255.255.255.192']
assert outputs[3][0].splitlines()==[
    f'/{p} {ipaddress.ip_network(f"0.0.0.0/{p}").netmask} {2**(32-p)-2}' for p in range(20,31)]
assert outputs[7][0].splitlines()==[
    '192.168.10.0/26 192.168.10.63 62',
    '192.168.10.64/26 192.168.10.127 62',
    '192.168.10.128/26 192.168.10.191 62',
    '192.168.10.192/26 192.168.10.255 62']
assert outputs[9]==['True False']
assert outputs[10]==["[IPv4Network('10.20.0.0/23')]"]
assert outputs[11]==["[IPv4Network('10.20.1.0/24'), IPv4Network('10.20.2.0/24')]"]
assert outputs[12]==['255.255.254.0 0.0.1.255 512']
assert outputs[22]==['True']
assert outputs[26]==["[IPv4Address('10.0.0.0'), IPv4Address('10.0.0.1')]"]
print(f'OK: samowystarczalne obliczenia Python w {len(outputs)} cwiczeniach; reszta nie uruchamiana')
