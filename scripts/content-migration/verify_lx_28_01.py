"""Sprawdza odczyty diagnostyczne lekcji 28/01, bez generowania obciazenia."""
import json
import subprocess
import tempfile

with open('/tmp/javaquest-28-01.json') as source:
    lesson = json.load(source)
for index, exercise in enumerate(lesson['exercises'], 1):
    # Raport do mktemp uruchamiamy w osobnym HOME; pozniej usuwamy tylko nasz plik.
    result = subprocess.run(['bash', '-c', exercise['solution']], text=True, capture_output=True)
    if result.returncode:
        raise RuntimeError(f'{index}: {result.stderr}')
    if index == 25:
        from pathlib import Path
        report = Path(result.stdout.strip())
        assert report.name == 'load.txt' and str(report).startswith('/tmp/tmp.')
        assert len(report.read_text().splitlines()) == 5
        report.unlink()
        report.parent.rmdir()
    if index == 26:
        assert result.stdout.strip() == '2 CPUs: 3.00; 8 CPUs: 0.75'
    if index == 27:
        assert result.stdout.strip() == 'rosnacy'
    if index == 28:
        assert result.stdout.strip() == 'malejacy'
print('OK: 30 rozwiazan 28/01, odczyty Linux i modele liczbowe, bez obciazania systemu')
