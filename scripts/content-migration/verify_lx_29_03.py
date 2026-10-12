"""Odczyty sysctl i dry-run procps; bez zapisu parametrow jadra."""
import json
import subprocess

with open('/tmp/javaquest-29-03.json') as source:
    lesson=json.load(source)
assert len(lesson['exercises']) == 20
before=subprocess.check_output(['sysctl', '-n', 'vm.swappiness',
                               'vm.max_map_count', 'net.core.somaxconn',
                               'net.ipv4.ip_local_port_range'], text=True)
assert '--dry-run' in subprocess.check_output(['sysctl', '--help'], text=True)
for index, exercise in enumerate(lesson['exercises'], 1):
    result=subprocess.run(['bash', '-c', exercise['solution']],
                          capture_output=True, text=True, timeout=30)
    if result.returncode:
        raise RuntimeError(f'{index}: {result.stderr}')
    if index == 3:
        values=result.stdout.splitlines()
        assert len(values)==2 and values[0]==values[1]
    expected={16:'vm.swappiness = 10',
              17:'net.ipv4.ip_local_port_range = 10240 65000\nnet.core.somaxconn = 8192',
              18:'vm.max_map_count = 262144\nvm.max_map_count = 65530',
              19:'100'}
    if index in expected:
        assert result.stdout.strip()==expected[index], (index,result.stdout)
after=subprocess.check_output(['sysctl', '-n', 'vm.swappiness',
                              'vm.max_map_count', 'net.core.somaxconn',
                              'net.ipv4.ip_local_port_range'], text=True)
assert before==after, 'Runtime values changed during read/dry-run lab'
print('OK: 20 rozwiazan 29/03; model nadpisania, backlog, niezmienione runtime')
