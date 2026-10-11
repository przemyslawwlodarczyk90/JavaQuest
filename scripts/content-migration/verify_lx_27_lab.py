"""Nieszkodliwe proby praktyki 27 w istniejacym kontenerze lxlab."""
import json
import os
import pathlib
import re
import subprocess
import tempfile


def run(*args, **kwargs):
    return subprocess.run(args, text=True, capture_output=True, check=True, **kwargs)


with tempfile.TemporaryDirectory(prefix='javaquest-cron-') as temp:
    root = pathlib.Path(temp)
    for name in ['10-pierwszy', '20-drugi', 'porzadki', 'porzadki.sh', 'raport']:
        p = root / name
        p.write_text('#!/bin/sh\necho proba\n')
        p.chmod(0o644 if name == 'raport' else 0o755)
    chosen = run('run-parts', '--test', str(root)).stdout.splitlines()
    assert [pathlib.Path(p).name for p in chosen] == ['10-pierwszy', '20-drugi', 'porzadki']
    listed = run('run-parts', '--list', str(root)).stdout.splitlines()
    assert [pathlib.Path(p).name for p in listed] == ['10-pierwszy', '20-drugi', 'porzadki', 'raport']
    assert run('run-parts', '--report', '--regex', '^porzadki$', str(root)).stdout.strip().endswith('proba')
    assert run('/bin/sh', '-c', 'printf "%s\\n" {1..3}').stdout.strip() == '{1..3}'
    assert run('/bin/bash', '-c', 'printf "%s\\n" {1..3}').stdout == '1\n2\n3\n'
    env = run('env', '-i', 'HOME=/home/anna', 'PATH=/usr/bin:/bin', '/bin/sh', '-c', 'env').stdout
    assert 'JAVA_HOME=' not in env
    pipe = run('/bin/bash', '-c', 'false | cat; echo $?; set -o pipefail; false | cat; echo $?').stdout
    assert pipe == '0\n1\n'
    lockfile = root / 'raport.lock'
    with lockfile.open('w') as held:
        import fcntl
        fcntl.flock(held, fcntl.LOCK_EX | fcntl.LOCK_NB)
        failed = subprocess.run(['flock', '-n', str(lockfile), 'true'])
        assert failed.returncode == 1
    run('flock', '-n', str(lockfile), 'true')
print('OK: run-parts, dash/Bash, minimalne env, pipefail, flock')

# Nie uruchamiamy atd; powstaja tylko nasze nieszkodliwe zlecenia, od razu anulowane.
for expression in ['now + 30 minutes', 'now + 2 hours', 'now + 3 days', '22:00 tomorrow', 'noon tomorrow', 'teatime tomorrow']:
    proc = run('runuser', '-u', 'anna', '--', 'at', *expression.split(), input='echo proba-javaquest\n')
    match = re.search(r'job (\d+) at ', proc.stderr)
    assert match, proc.stderr
    number = match.group(1)
    try:
        content = run('runuser', '-u', 'anna', '--', 'at', '-c', number).stdout
        assert 'echo proba-javaquest' in content
        assert 'umask' in content and '\ncd ' in content
    finally:
        run('runuser', '-u', 'anna', '--', 'atrm', number)
print('OK: 6 form czasowych at, at -c, atrm; zlecenia anulowane')

calendars = json.loads(pathlib.Path('/tmp/javaquest-calendars.json').read_text())
for expression in calendars:
    parsed = run('systemd-analyze', 'calendar', '--iterations=3', expression)
    assert 'Normalized form:' in parsed.stdout or 'Original form:' in parsed.stdout
print(f'OK: {len(calendars)} kalendarzy systemd; sprawdzenie parsera, bez aktywacji uslug')

with tempfile.TemporaryDirectory(prefix='javaquest-backup-') as temp:
    root = pathlib.Path(temp)
    source = root / 'zrodlo'
    source.mkdir()
    (source / 'staly.txt').write_text('bez zmian\n')
    (source / 'zmienny.txt').write_text('pierwsza wersja\n')
    first, second, restored = [root / x for x in ['dzien1', 'dzien2', 'odtworzenie']]
    run('rsync', '-a', str(source) + '/', str(first) + '/')
    (source / 'zmienny.txt').write_text('druga, dluzsza wersja\n')
    run('rsync', '-a', '--link-dest=' + str(first), str(source) + '/', str(second) + '/')
    assert (first / 'staly.txt').stat().st_ino == (second / 'staly.txt').stat().st_ino
    assert (first / 'zmienny.txt').read_text() == 'pierwsza wersja\n'
    assert (second / 'zmienny.txt').read_text() == 'druga, dluzsza wersja\n'
    run('rsync', '-a', str(first) + '/', str(restored) + '/')
    run('diff', '-r', str(first), str(restored))
    (source / 'staly.txt').unlink()
    planned = run('rsync', '-ani', '--delete', str(source) + '/', str(restored) + '/').stdout
    assert '*deleting' in planned and (restored / 'staly.txt').exists()
    run('rsync', '-a', '--delete', str(source) + '/', str(restored) + '/')
    assert not (restored / 'staly.txt').exists() and (first / 'staly.txt').exists()
print('OK: rsync, link-dest, niezalezna historia, odtworzenie, podglad i usuwanie tylko w katalogu testowym')

# Unikalne nazwy baz; usuwamy tylko zasoby tej konkretnej proby.
import uuid
token = uuid.uuid4().hex[:12]
original, restored = 'jq_backup_' + token, 'jq_restore_' + token
with tempfile.TemporaryDirectory(prefix='javaquest-pgdump-') as temp:
    root = pathlib.Path(temp)
    root.chmod(0o777)
    archive = root / 'kopia.dump'
    pg = ('runuser', '-u', 'postgres', '--')
    created = []
    try:
        for name in [original, restored]:
            run(*pg, 'createdb', name)
            created.append(name)
        run(*pg, 'psql', '-v', 'ON_ERROR_STOP=1', '-d', original, '-c',
            'CREATE TABLE faktury (kwota integer); INSERT INTO faktury VALUES (100), (250);')
        run(*pg, 'pg_dump', '-Fc', '-f', str(archive), original)
        run(*pg, 'pg_restore', '--list', str(archive))
        run(*pg, 'pg_restore', '--exit-on-error', '-d', restored, str(archive))
        result = run(*pg, 'psql', '-At', '-d', restored, '-c', 'SELECT count(*), sum(kwota) FROM faktury;')
        assert result.stdout.strip() == '2|350'
    finally:
        for name in reversed(created):
            run(*pg, 'dropdb', name)
print('OK: PostgreSQL custom dump, spis archiwum, rzeczywiste odtworzenie i kontrola danych; bazy testowe usuniete')
