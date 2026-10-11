"""Odczyty wersji jadra i dystrybucji; bez aktualizacji lub restartu."""
import json
import subprocess

with open('/tmp/javaquest-29-01.json') as source:
    lesson = json.load(source)
for index, exercise in enumerate(lesson['exercises'], 1):
    result = subprocess.run(['bash', '-c', exercise['solution']], capture_output=True,
                            text=True, timeout=20)
    if result.returncode:
        raise RuntimeError(f'{index}: {result.stderr}')
    if index == 17:
        assert result.stdout.strip() == 'base=6.8.0 abi=48 flavour=generic'
    if index == 18:
        assert result.stdout.splitlines() == ['6.8.0-9-generic', '6.8.0-48-generic']
    if index == 19:
        assert result.stdout.strip() == 'kernel release sources agree'
print('OK: 20 rozwiazan 29/01; odczyty, model ABI i sortowanie wersji')
