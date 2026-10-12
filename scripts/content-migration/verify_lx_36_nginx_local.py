"""Rzeczywisty Nginx i wlasny backend loopback; niezalezny model cwiczeniowy."""
import http.server
import json
import pathlib
import signal
import socket
import subprocess
import tempfile
import threading
import time
import urllib.error
import urllib.request

class Backend(http.server.BaseHTTPRequestHandler):
    def do_GET(self):
        payload=json.dumps({'path':self.path,'host':self.headers.get('Host'),
                            'proto':self.headers.get('X-Forwarded-Proto'),
                            'forwarded':self.headers.get('X-Forwarded-For')}).encode()
        self.send_response(200);self.send_header('Content-Length',str(len(payload)))
        self.end_headers();self.wfile.write(payload)
    def do_POST(self):
        self.do_GET()
    def log_message(self,*args):
        pass

backend=http.server.HTTPServer(('127.0.0.1',0),Backend)
thread=threading.Thread(target=backend.serve_forever,daemon=True);thread.start()
with socket.socket() as reservation:
    reservation.bind(('127.0.0.1',0));port=reservation.getsockname()[1]
process=None
try:
    with tempfile.TemporaryDirectory(prefix='javaquest-nginx-') as temporary:
        lab=pathlib.Path(temporary);lab.chmod(0o755)
        web=lab/'web';web.mkdir(mode=0o755)
        (web/'index.html').write_text('static fixture\n');(web/'index.html').chmod(0o644)
        (web/'asset.txt').write_text('asset\n');(web/'asset.txt').chmod(0o644)
        config=lab/'nginx.conf'
        config.write_text(f'''
user www-data;
worker_processes 1;
pid {lab}/nginx.pid;
error_log {lab}/error.log;
events {{ worker_connections 64; }}
http {{
    access_log {lab}/access.log;
    client_body_temp_path {lab}/body;
    proxy_temp_path {lab}/proxy;
    server_tokens off;
    server {{
        listen 127.0.0.1:{port};
        server_name course.lab;
        root {web};
        index index.html;
        client_max_body_size 1k;
        location / {{ try_files $uri $uri/ =404; }}
        location = /exact {{ return 200 "exact location"; }}
        location ^~ /priority/ {{ return 200 "prefix priority"; }}
        location ~ [.]json$ {{ return 200 "regex location"; }}
        location /api/ {{
            proxy_set_header Host $host;
            proxy_set_header X-Forwarded-Proto $scheme;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_pass http://127.0.0.1:{backend.server_port};
        }}
        location /strip/ {{ proxy_pass http://127.0.0.1:{backend.server_port}/; }}
        location = /actuator/env {{ return 403; }}
    }}
}}
''')
        command=['nginx','-p',str(lab)+'/', '-c',str(config)]
        subprocess.run(command+['-t'],capture_output=True,check=True)
        process=subprocess.Popen(command+['-g','daemon off;'],stdout=subprocess.DEVNULL,stderr=subprocess.PIPE)
        opener=urllib.request.build_opener(urllib.request.ProxyHandler({}))
        def get(path,data=None):
            request=urllib.request.Request(f'http://127.0.0.1:{port}{path}',data=data,headers={'Host':'course.lab'})
            try:
                with opener.open(request,timeout=3) as response:
                    return response.status,response.read(),response.headers
            except urllib.error.HTTPError as error:
                return error.code,error.read(),error.headers
        for attempt in range(40):
            if process.poll() is not None:
                raise RuntimeError(process.stderr.read().decode())
            try:
                result=get('/');break
            except urllib.error.URLError:
                time.sleep(0.05)
        else:
            raise RuntimeError('Own nginx did not start')
        assert result[0]==200 and result[1]==b'static fixture\n',(result[:2],(lab/'error.log').read_text())
        assert result[2]['Server']=='nginx',result[2]
        assert get('/asset.txt')[1]==b'asset\n'
        assert get('/missing')[0]==404
        assert get('/exact')[1]==b'exact location'
        assert get('/priority/item.json')[1]==b'prefix priority'
        assert get('/item.json')[1]==b'regex location'
        status,data,_=get('/api/products?limit=2')
        assert status==200
        forwarded=json.loads(data)
        assert forwarded=={'path':'/api/products?limit=2','host':'course.lab','proto':'http','forwarded':'127.0.0.1'},forwarded
        assert json.loads(get('/strip/products?limit=2')[1])['path']=='/products?limit=2'
        assert get('/actuator/env')[0]==403
        assert get('/api/upload',b'x'*2048)[0]==413
        access=(lab/'access.log').read_text()
        assert ' 404 ' in access and ' 403 ' in access and ' 413 ' in access
        process.send_signal(signal.SIGQUIT);process.wait(timeout=5);process=None
finally:
    if process is not None:
        process.send_signal(signal.SIGQUIT)
        try: process.wait(timeout=5)
        except subprocess.TimeoutExpired: process.kill();process.wait()
    backend.shutdown();backend.server_close();thread.join(timeout=3)
print('OK: nginx -t, real HTTP static/404, exact/^~/regex, proxy URI/headers, 403/413 and logs')
