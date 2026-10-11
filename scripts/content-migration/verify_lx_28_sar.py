"""Sprawdza prawdziwe zbieranie i odczyt historii sysstat we wlasnym HOME."""
import json
import os
import pathlib
import subprocess
import tempfile

with open('/tmp/javaquest-28-10.json') as source:
    lesson = json.load(source)
with tempfile.TemporaryDirectory(prefix='javaquest-sar-') as temp:
    lab = pathlib.Path(temp) / 'sar-lab'
    env = {**os.environ, 'HOME': temp}
    for index, exercise in enumerate(lesson['exercises'], 1):
        result = subprocess.run(['bash', '-c', exercise['solution']], env=env,
                                capture_output=True, text=True, timeout=25)
        if result.returncode:
            raise RuntimeError(f'{index}: {result.stderr}')
        if index == 3:
            assert 'Average:' in result.stdout and '%idle' in result.stdout
        if index == 22:
            assert (lab / 'cpu.csv').read_text().startswith('# hostname;')
        if index == 23:
            assert 'sysstat' in json.loads((lab / 'cpu.json').read_text())
        if index == 25:
            assert (lab / 'proba.sa').stat().st_mode & 0o777 == 0o600
        if index == 29:
            assert result.stdout.strip() == 'mean_iowait=22%'
    assert not lab.exists()
print('OK: 30 rozwiazan sysstat, prawdziwe dane sadc, odczyty sar, eksport CSV/JSON; laboratorium usuniete')
