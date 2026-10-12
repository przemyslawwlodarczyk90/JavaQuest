"""Sprawdza selektor procesu bez wysylania sygnalow lub kasowania pcap."""
import json
import pathlib
import re
import subprocess

root=pathlib.Path(__file__).resolve().parents[2]
lesson=json.loads((root/'src/main/resources/content/_lx_32_diagnostyka_sieci/12_TcpdumpBasics.json').read_text(encoding='utf-8'))
command=lesson['exercises'][19]['solution']
pattern=re.search(r"pkill -INT -f '([^']+)'",command).group(1)
own=[
    'tcpdump -i enp0s8 -nn -w /var/tmp/kurs-tcpdump/pg.pcap -C 50 -W 4 -Z tcpdump tcp port 5432',
    '/usr/bin/tcpdump -i enp0s8 -nn -w /var/tmp/kurs-tcpdump/pg-2026.pcap -G 3600',
    '/usr/sbin/tcpdump -i enp0s8 -nn -w /var/tmp/kurs-tcpdump/pg.pcap']
other=[
    'tcpdump -i enp0s8 -nn -w /var/tmp/other.pcap',
    'tcpdump -i enp0s8 -nn -w /var/tmp/kurs-tcpdump-other/pg.pcap',
    'tcpdump -nn -r /var/tmp/kurs-tcpdump/http.pcap',
    'sudo tcpdump -i enp0s8 -nn -w /var/tmp/kurs-tcpdump/pg.pcap',
    'sh -c tcpdump -i enp0s8 -nn -w /var/tmp/kurs-tcpdump/pg.pcap']
result=subprocess.run(['C:/Program Files/Git/bin/bash.exe','-c','grep -E -- "$1"','scope',pattern],
                      input='\n'.join(own+other)+'\n',text=True,capture_output=True,check=True)
assert result.stdout.splitlines()==own,result.stdout
for exercise in lesson['exercises']:
    for match in re.findall(r'/var/tmp/[^\s\'\"]*',exercise['solution']):
        assert match.startswith('/var/tmp/kurs-tcpdump'),match
assert 'sudo pkill tcpdump' not in '\n'.join(e['solution'] for e in lesson['exercises'])
print('OK: selector includes own recording jobs; excludes other captures, playback and sudo/sh wrappers')
