"""Own loopback TCP/UDP/Unix sockets, ss filters, drift and cleanup."""
import os,pathlib,socket,subprocess,tempfile,threading
root=pathlib.Path(tempfile.mkdtemp(prefix='javaquest-listening-',dir='/tmp'))
stop=threading.Event()
sockets=[]
threads=[]
def output(*args):
    return subprocess.check_output(args,text=True)
def serve(server,kind):
    server.settimeout(.2)
    while not stop.is_set():
        try:
            if kind=='udp':
                data,address=server.recvfrom(4096)
                server.sendto(b'fixture:'+data,address)
            else:
                client,_=server.accept()
                with client:
                    client.settimeout(2)
                    data=client.recv(4096)
                    client.sendall(b'fixture:'+data)
        except socket.timeout:
            continue
        except OSError:
            break
def start(kind):
    server=socket.socket(socket.AF_UNIX if kind=='unix' else socket.AF_INET,
                         socket.SOCK_DGRAM if kind=='udp' else socket.SOCK_STREAM)
    server.bind(str(root/'fixture.sock') if kind=='unix' else ('127.0.0.1',0))
    if kind!='udp':
        server.listen(5)
    sockets.append(server)
    thread=threading.Thread(target=serve,args=(server,kind),daemon=True)
    threads.append(thread)
    thread.start()
    return server
def tcp_state(port):
    return output('ss','-H','-ltnp','sport = :'+str(port))
try:
    tcp=start('tcp')
    udp=start('udp')
    unix=start('unix')
    tcp_port=tcp.getsockname()[1]
    udp_port=udp.getsockname()[1]
    assert '127.0.0.1:'+str(tcp_port) in tcp_state(tcp_port)
    assert 'pid='+str(os.getpid()) in tcp_state(tcp_port)
    udp_state=output('ss','-H','-lunp','sport = :'+str(udp_port))
    assert '127.0.0.1:'+str(udp_port) in udp_state
    assert 'pid='+str(os.getpid()) in udp_state
    assert not output('ss','-H','-ltn','sport = :'+str(udp_port))
    assert str(root/'fixture.sock') in output('ss','-H','-lxnp')
    with socket.create_connection(('127.0.0.1',tcp_port),timeout=2) as c:
        c.sendall(b'tcp')
        assert c.recv(4096)==b'fixture:tcp'
    with socket.socket(socket.AF_INET,socket.SOCK_DGRAM) as c:
        c.settimeout(2)
        c.sendto(b'udp',('127.0.0.1',udp_port))
        assert c.recvfrom(4096)[0]==b'fixture:udp'
    with socket.socket(socket.AF_UNIX,socket.SOCK_STREAM) as c:
        c.settimeout(2)
        c.connect(str(root/'fixture.sock'))
        c.sendall(b'unix')
        assert c.recv(4096)==b'fixture:unix'
    expected={('tcp','127.0.0.1',tcp_port),('udp','127.0.0.1',udp_port)}
    actual=set(expected)
    extra=start('tcp')
    extra_port=extra.getsockname()[1]
    assert tcp_state(extra_port)
    actual.add(('tcp','127.0.0.1',extra_port))
    assert actual-expected=={('tcp','127.0.0.1',extra_port)}
    assert not expected-actual
    assert ('udp','127.0.0.1',tcp_port) not in expected
    exe=os.readlink('/proc/'+str(os.getpid())+'/exe')
    owner=output('dpkg-query','-S',exe)
    assert 'python3' in owner
    assert os.path.exists('/proc/'+str(os.getpid())+'/cmdline')
    extra.close()
    threads[-1].join(timeout=2)
    assert not tcp_state(extra_port)
    print('OK: actual TCP/UDP/Unix payloads, ss protocol/address/PID, package owner, own drift and closure.')
finally:
    stop.set()
    for server in sockets:
        server.close()
    for thread in threads:
        thread.join(timeout=2)
    path=root/'fixture.sock'
    if path.exists():
        path.unlink()
    assert root.resolve()==root
    assert root.name.startswith('javaquest-listening-')
    root.rmdir()
    assert not root.exists()

# Execute the exact source commands for the read-only inventory and own HTTP.
import json
with open('/tmp/lesson.json') as source:
    lesson=json.load(source)
script='''set -e
cleanup_source() {
 if [ -n "${lab_pid:-}" ] && [ -r "/proc/$lab_pid/cmdline" ] && tr '\\0' ' ' < "/proc/$lab_pid/cmdline" | grep -Fq -- "$audit_lab"; then
  kill "$lab_pid" 2>/dev/null || true; wait "$lab_pid" 2>/dev/null || true
 fi
 case "${audit_lab:-}" in /tmp/javaquest-ports.*)
  if [ -d "$audit_lab" ] && [ "$(readlink -f "$audit_lab")" = "$audit_lab" ]; then rm -rf -- "$audit_lab"; fi;;
 esac
}
trap cleanup_source EXIT
'''
for number in list(range(1,12))+[28,30]:
    script+=lesson['exercises'][number-1]['solution']+'\n'
    if number==8:
        script+='test -n "$(ss -H -ltnp "sport = :$lab_port" | grep -F "pid=$lab_pid,")"\n'
    if number==9:
        script+='test "$(curl -s -o /dev/null -w "%{http_code}" "http://127.0.0.1:$lab_port/")" = 200\n'
    if number==28:
        script+='lab_pid=\n'
    script+=f'printf "SOURCE {number} OK\\n"\n'
result=subprocess.run(['bash','--noprofile','--norc','-c',script],capture_output=True,text=True,timeout=60)
print(result.stdout)
print(result.stderr)
assert result.returncode==0,result.returncode
assert sum(x.startswith('SOURCE ') for x in result.stdout.splitlines())==13
print('Exact source: 1-11, 28, 30 passed. Live systemd 13-16 not executed.')


