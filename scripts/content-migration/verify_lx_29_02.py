"""Odczyt stanu i parser konfiguracji kmod; bez ladowania modulow hosta."""
import json
import subprocess

with open('/tmp/javaquest-29-02.json') as source:
    lesson = json.load(source)
assert len(lesson['exercises']) == 20
for index, exercise in enumerate(lesson['exercises'], 1):
    result = subprocess.run(['bash', '-c', exercise['solution']],
                            capture_output=True, text=True, timeout=30)
    if result.returncode:
        raise RuntimeError(f'{index}: {result.stderr}')
    if index == 3:
        assert int(result.stdout.strip()) >= 0
    if index == 4:
        assert result.stdout.splitlines() == sorted(result.stdout.splitlines())
    expected = {15: 'blacklist pcspkr',
                16: 'options e1000 InterruptThrottleRate=3000',
                17: 'alias kurs_net e1000',
                18: 'install usb_storage /bin/false',
                20: 'overlay\nbr_netfilter'}
    if index in expected:
        assert result.stdout.strip() == expected[index], (index, result.stdout)
print('OK: 20 rozwiazan 29/02; stan procfs, parser kmod, probny plik startowy')
print('Nie testowano ladowania, usuwania ani startu modulow; kontener wspoldzieli jadro.')
