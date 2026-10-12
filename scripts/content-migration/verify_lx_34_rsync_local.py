"""Rzeczywisty rsync na wlasnych plikach; bez SSH i katalogow kursanta."""
import os
import pathlib
import subprocess
import tempfile

def run(*args):
    return subprocess.check_output(['rsync', *map(str,args)],text=True)

with tempfile.TemporaryDirectory(prefix='javaquest-rsync-') as temporary:
    lab=pathlib.Path(temporary)
    source=lab/'source';source.mkdir()
    (source/'app.jar').write_bytes(b'AAAA')
    (source/'notes.txt').write_text('course fixture\n')
    (source/'temp.tmp').write_text('temporary\n')
    (source/'cache').mkdir();(source/'cache'/'item').write_text('cache\n')
    nested=lab/'nested';nested.mkdir()
    flat=lab/'flat';flat.mkdir()
    run('-a',source,nested)
    run('-a',str(source)+'/',str(flat)+'/')
    assert (nested/'source'/'app.jar').read_bytes()==b'AAAA'
    assert (flat/'app.jar').read_bytes()==b'AAAA' and not (flat/'source').exists()
    stale=flat/'stale.txt';stale.write_text('only in destination')
    output=run('-ani','--delete',str(source)+'/',str(flat)+'/')
    assert 'deleting' in output and 'stale.txt' in output and stale.exists()
    run('-ai','--delete',str(source)+'/',str(flat)+'/')
    assert not stale.exists()
    excluded=lab/'excluded';excluded.mkdir()
    run('-a','--exclude=*.tmp','--exclude=/cache/',str(source)+'/',str(excluded)+'/')
    assert (excluded/'app.jar').exists()
    assert not (excluded/'temp.tmp').exists() and not (excluded/'cache').exists()
    (excluded/'protected.tmp').write_text('receiver-only excluded file')
    run('-a','--delete','--exclude=*.tmp',str(source)+'/',str(excluded)+'/')
    assert (excluded/'protected.tmp').exists(), 'excluded receiver file should be protected'
    run('-a','--delete','--delete-excluded','--exclude=*.tmp',str(source)+'/',str(excluded)+'/')
    assert not (excluded/'protected.tmp').exists()
    empty=lab/'empty';empty.mkdir()
    limited=lab/'limited';limited.mkdir()
    for name in ('one','two','three'):
        (limited/name).write_text('receiver-only')
    limit_result=subprocess.run(['rsync','-a','--delete','--max-delete=1',str(empty)+'/',str(limited)+'/'],capture_output=True)
    assert limit_result.returncode==25,limit_result.returncode
    assert len(list(limited.iterdir()))==2, 'max-delete is a deletion limit, not all-or-nothing rollback'
    destination=flat/'app.jar'
    stamp=(source/'app.jar').stat()
    destination.write_bytes(b'BBBB')
    os.utime(destination,ns=(stamp.st_atime_ns,stamp.st_mtime_ns))
    run('-a',str(source)+'/',str(flat)+'/')
    assert destination.read_bytes()==b'BBBB', 'quick check should skip same size/mtime'
    run('-ac',str(source)+'/',str(flat)+'/')
    assert destination.read_bytes()==b'AAAA', 'checksum should detect differing content'
    first=lab/'snapshot1';first.mkdir()
    second=lab/'snapshot2';second.mkdir()
    run('-a',str(source)+'/',str(first)+'/')
    run('-a','--link-dest='+str(first),str(source)+'/',str(second)+'/')
    assert (first/'app.jar').stat().st_ino==(second/'app.jar').stat().st_ino
    assert (first/'app.jar').stat().st_nlink==2
    (source/'app.jar').write_bytes(b'new version with changed size')
    third=lab/'snapshot3';third.mkdir()
    run('-a','--link-dest='+str(second),str(source)+'/',str(third)+'/')
    assert (third/'app.jar').stat().st_ino!=(second/'app.jar').stat().st_ino
    assert (first/'app.jar').read_bytes()==b'AAAA'
    assert (third/'notes.txt').stat().st_ino==(second/'notes.txt').stat().st_ino
    result=subprocess.run(['rsync','-a',str(lab/'missing')+'/',str(flat)+'/'],capture_output=True)
    assert result.returncode==23,result.returncode
print('OK: trailing slash, delete dry-run/actual, excludes/protection, max-delete partial/code25, quick check/checksum, hardlinks/code23')
