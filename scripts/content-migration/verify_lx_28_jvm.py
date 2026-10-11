"""Praktyka JVM na wlasnej malej aplikacji w nowym HOME tymczasowym."""
import json
import os
import pathlib
import signal
import subprocess
import tempfile

with open('/tmp/javaquest-28-09.json') as source:
    lesson = json.load(source)
with tempfile.TemporaryDirectory(prefix='javaquest-jvm-') as temp:
    root = pathlib.Path(temp)
    lab = root / 'jvm-diag-lab'
    env = {**os.environ, 'HOME': str(root)}
    pid = None
    try:
        for index, exercise in enumerate(lesson['exercises'], 1):
            result = subprocess.run(['bash', '-c', exercise['solution']], text=True,
                                    capture_output=True, timeout=30, env=env)
            if result.returncode:
                raise RuntimeError(f'{index}: {result.stderr}')
            if index == 3:
                pid = int((lab / 'pid').read_text())
            if index == 11:
                assert 'heap' in result.stdout.lower() and 'Metaspace' in result.stdout
            if index == 15:
                assert 'proba-oczekiwanie' in result.stdout and 'TIMED_WAITING' in result.stdout
            if index == 18:
                assert 'Native Memory Tracking:' in result.stdout
            if index == 24:
                assert (lab / 'proba.hprof').stat().st_size > 0
                assert (lab / 'proba.hprof').stat().st_mode & 0o777 == 0o600
            if index == 27:
                assert 'jdk.' in result.stdout and (lab / 'proba.jfr').stat().st_size > 0
            if index == 29:
                assert result.stdout.strip() == '182c'
        assert not lab.exists()
        print('OK: 30 rozwiazan JVM, NMT, stosy, prywatny heap dump, JFR; laboratorium usuniete')
    finally:
        if pid is not None:
            proc = pathlib.Path(f'/proc/{pid}/cmdline')
            if proc.exists() and str(lab).encode() in proc.read_bytes():
                os.kill(pid, signal.SIGTERM)
