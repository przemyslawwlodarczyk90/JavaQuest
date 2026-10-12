"""Testuje rzeczywisty kod rozwiazan, bez sieci i bez zmian konfiguracji."""
import json
import pathlib
import subprocess
import sys
import tempfile

root = pathlib.Path(__file__).resolve().parents[2]
lesson = json.loads((root / 'src/main/resources/content/_lx_30_podstawy_sieci/04_PrivateAddressesAndNat.json').read_text(encoding='utf-8'))
classifier = lesson['exercises'][3]['solution'].split("<<'EOF'\n",1)[1].split('\nEOF',1)[0]
comparison = lesson['exercises'][2]['solution'].split("<<'PY'\n",1)[1].split('\nPY',1)[0]
with tempfile.TemporaryDirectory(prefix='javaquest-address-') as lab:
    script = pathlib.Path(lab) / 'classify.py'
    script.write_text(classifier, encoding='utf-8')
    addresses = ['10.0.2.15','172.16.0.0','172.31.255.255','192.168.56.10',
                 '100.64.0.0','100.127.255.255','198.51.100.77','127.0.0.1',
                 '8.8.8.8','224.0.0.1']
    output = subprocess.check_output([sys.executable, str(script), *addresses], text=True)
    labels = [line.split(maxsplit=1)[1] for line in output.splitlines()]
    assert labels == ['prywatny (RFC 1918)']*4 + ['CGNAT']*2 + ['specjalny (nieglobalny)']*2 + ['globalny unicast','multicast'], output
    same = subprocess.check_output([sys.executable,'-c',comparison,'8.8.8.8','8.8.8.8'], text=True)
    other = subprocess.check_output([sys.executable,'-c',comparison,'8.8.8.8','10.0.2.15'], text=True)
    assert 'matches local address' in same
    assert 'check NAT, VPN or proxy' in other
    invalid = subprocess.run([sys.executable,'-c',comparison,'not-an-IP','10.0.2.15'], capture_output=True)
    assert invalid.returncode != 0
print('OK: RFC1918/CGNAT boundaries, special addresses, multicast, local comparison and invalid response')
