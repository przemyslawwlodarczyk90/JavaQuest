"""Wlasny klaster PostgreSQL, prywatny Unix socket; bez TCP i baz uzytkownika."""
import os
import pathlib
import pwd
import subprocess
import tempfile

binary=pathlib.Path('/usr/lib/postgresql/16/bin')
account=pwd.getpwnam('postgres')
port='55439'
with tempfile.TemporaryDirectory(prefix='javaquest-pg-') as temporary:
    lab=pathlib.Path(temporary);os.chown(lab,account.pw_uid,account.pw_gid)
    socket=lab/'socket';socket.mkdir();os.chown(socket,account.pw_uid,account.pw_gid)
    data=lab/'data'
    def as_postgres(command,*args):
        return subprocess.run(['runuser','-u','postgres','--',str(binary/command),*map(str,args)],text=True,capture_output=True,check=True)
    def sql(database,query,user='postgres',check=True):
        return subprocess.run([str(binary/'psql'),'-X','-A','-t','-v','ON_ERROR_STOP=1',
                               '-h',str(socket),'-p',port,'-U',user,'-d',database,'-c',query],
                              text=True,capture_output=True,check=check)
    connection=['-h',str(socket),'-p',port,'-U','postgres']
    started=False
    try:
        as_postgres('initdb','-D',data,'--no-locale','--encoding=UTF8','--auth-local=trust','--auth-host=reject')
        as_postgres('pg_ctl','-D',data,'-l',lab/'postgres.log','-o',
                    f"-p {port} -c listen_addresses='' -c unix_socket_directories='{socket}' -c shared_buffers=16MB -c max_connections=10 -c dynamic_shared_memory_type=mmap",'-w','start')
        started=True
        assert sql('postgres','SHOW listen_addresses').stdout.strip()==''
        sql('postgres','CREATE DATABASE course_source')
        sql('course_source',"CREATE TABLE products(id integer PRIMARY KEY, price numeric(8,2)); INSERT INTO products VALUES (1,12.50),(2,7.50)")
        sql('course_source','CREATE ROLE course_reader LOGIN; GRANT CONNECT ON DATABASE course_source TO course_reader; GRANT USAGE ON SCHEMA public TO course_reader; GRANT SELECT ON products TO course_reader')
        assert sql('course_source','SELECT sum(price) FROM products',user='course_reader').stdout.strip()=='20.00'
        denied=sql('course_source','INSERT INTO products VALUES (3,1)',user='course_reader',check=False)
        assert denied.returncode!=0 and 'permission denied' in denied.stderr
        sql('course_source','BEGIN; INSERT INTO products VALUES (3,100); ROLLBACK;')
        assert sql('course_source','SELECT count(*) FROM products').stdout.strip()=='2'
        archive=lab/'course.dump'
        subprocess.run([str(binary/'pg_dump'),*connection,'-Fc','--no-owner','--no-privileges','-f',str(archive),'course_source'],check=True,capture_output=True)
        listing=subprocess.check_output([str(binary/'pg_restore'),'--list',str(archive)],text=True)
        assert 'TABLE DATA public products' in listing
        sql('postgres','CREATE DATABASE course_restored')
        subprocess.run([str(binary/'pg_restore'),*connection,'--exit-on-error','--no-owner','--no-privileges','-d','course_restored',str(archive)],check=True,capture_output=True)
        assert sql('course_restored','SELECT count(*), sum(price) FROM products').stdout.strip()=='2|20.00'
        plain=lab/'course.sql'
        subprocess.run([str(binary/'pg_dump'),*connection,'--no-owner','--no-privileges','-f',str(plain),'course_source'],check=True,capture_output=True)
        sql('postgres','CREATE DATABASE course_plain')
        subprocess.run([str(binary/'psql'),*connection,'-X','-v','ON_ERROR_STOP=1','--single-transaction','-d','course_plain','-f',str(plain)],check=True,capture_output=True)
        assert sql('course_plain','SELECT count(*), sum(price) FROM products').stdout.strip()=='2|20.00'
        hba=data/'pg_hba.conf';original=hba.read_text()
        assert sql('postgres','SELECT count(*) FROM pg_hba_file_rules WHERE error IS NOT NULL').stdout.strip()=='0'
        try:
            with hba.open('a') as output: output.write('\ninvalid_connection_type all all trust\n')
            assert sql('postgres','SELECT count(*) FROM pg_hba_file_rules WHERE error IS NOT NULL').stdout.strip()=='1'
        finally:
            hba.write_text(original)
        assert sql('postgres','SELECT count(*) FROM pg_hba_file_rules WHERE error IS NOT NULL').stdout.strip()=='0'
        print('OK: read-only role/denied insert, rollback, custom dump/restore, SQL restore, HBA parser errors')
    finally:
        if started: as_postgres('pg_ctl','-D',data,'-m','fast','-w','stop')
