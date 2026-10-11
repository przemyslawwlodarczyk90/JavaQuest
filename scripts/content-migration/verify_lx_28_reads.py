"""Wykonuje odczyty diagnostyczne lekcji 28/02-08 w lxlab."""
import json
import subprocess

for number in ['02', '03', '04', '05', '06', '07', '08']:
    with open(f'/tmp/javaquest-28-{number}.json') as source:
        lesson = json.load(source)
    for index, exercise in enumerate(lesson['exercises'], 1):
        result = subprocess.run(['bash', '-c', exercise['solution']], capture_output=True, text=True, timeout=25)
        if result.returncode:
            raise RuntimeError(f'28/{number} cwiczenie {index}: {result.stderr}')
        if number == '02' and index == 26:
            assert result.stdout.strip() == 'available=25%'
        if number == '02' and index == 27:
            assert result.stdout.strip() == 'swap_used=512 MiB'
        if number == '02' and index == 28:
            assert result.stdout.strip() == 'RSS=256 MiB'
        if number == '03' and index == 27:
            assert result.stdout.strip() == 'read=3840 KiB/s'
        if number == '03' and index == 28:
            assert result.stdout.strip() == 'throughput=8.0 MiB/s'
        if number == '03' and index == 29:
            assert result.stdout.strip() == 'queue_model=2.0'
        if number == '04' and index == 27:
            assert result.stdout.strip() == 'RSS=512 MiB'
        if number == '04' and index == 28:
            assert result.stdout.strip() == 'CPU=50%'
        if number == '04' and index == 29:
            assert result.stdout.strip() == 'normalized=50%'
        if number == '05' and index == 19:
            assert '(deleted)' in result.stdout
        if number == '05' and index == 20:
            assert '(deleted)' in result.stdout
        if number == '05' and index == 21:
            assert result.stdout.strip() == '2\n1'
        if number == '06' and index == 13:
            assert result.stdout.strip() == '600'
        if number == '06' and index == 22:
            assert 'brak.txt' in result.stdout and 'ENOENT' in result.stdout
        if number == '07' and index == 27:
            assert result.stdout.strip() == 'MemoryError: process address-space limit'
        if number == '07' and index == 28:
            assert result.stdout.strip() == 'headroom=300 MiB'
        if number == '07' and index == 29:
            assert result.stdout.strip() == 'non_heap_budget=384 MiB'
        if number == '08' and index == 18:
            assert result.stdout.strip() == 'EMFILE: per-process descriptor limit'
        if number == '08' and index == 28:
            assert result.stdout.strip() == 'fd_headroom=3096'
        if number == '08' and index == 29:
            assert result.stdout.strip() == 'limit_bytes=67108864'
    print(f'OK: 30 rozwiazan 28/{number}, w tym sprawdzenie wynikow modeli liczbowych')
