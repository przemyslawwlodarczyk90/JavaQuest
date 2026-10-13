#!/bin/sh
# WSL docker-desktop: caly chroot w tmpfs /run, usuwany po tescie.
set -eu
profile=${1:-rsync}
case "$profile" in
    rsync) verifier=verify_lx_34_rsync_local.py; set -- rsync python3 ;;
    nginx) verifier=verify_lx_36_nginx_local.py; set -- nginx python3 ;;
    postgresql) verifier=verify_lx_37_postgresql_local.py; set -- postgresql-16 python3 ;;
    listening) verifier=verify_lx_39_listening_local.py; set -- iproute2 python3 sudo curl procps ;;
    *) echo 'Supported profiles: rsync, nginx, postgresql, listening' >&2; exit 1 ;;
esac
test "$(stat -f -c %T /run)" = tmpfs
lab=$(mktemp -d /run/javaquest-rsync.XXXXXX)
dev_mounted=0
proc_mounted=0
cleanup() {
    if [ "$proc_mounted" = 1 ]; then umount "$lab/proc"; fi
    if [ "$dev_mounted" = 1 ]; then umount "$lab/dev"; fi
    case "$lab" in /run/javaquest-rsync.*) ;; *) echo 'Unexpected lab path' >&2; exit 1;; esac
    test "$(realpath "$lab")" = "$lab"
    rm -rf -- "$lab"
}
trap cleanup EXIT
trap 'exit 130' INT TERM
archive=/mnt/host/c/Users/kapit/AppData/Local/Temp/javaquest-ubuntu-base.tar.gz
test -f "$archive"
tar -xzf "$archive" -C "$lab"
chmod 755 "$lab"
cp /etc/resolv.conf "$lab/etc/resolv.conf"
mount -t tmpfs tmpfs "$lab/dev"
dev_mounted=1
mknod -m 666 "$lab/dev/null" c 1 3
mknod -m 666 "$lab/dev/zero" c 1 5
mknod -m 666 "$lab/dev/random" c 1 8
mknod -m 666 "$lab/dev/urandom" c 1 9
mkdir -m 1777 "$lab/dev/shm"
if [ "$profile" = listening ]; then
    mount -t proc -o ro proc "$lab/proc"
    proc_mounted=1
fi
printf '#!/bin/sh\nexit 101\n' > "$lab/usr/sbin/policy-rc.d"
chmod 755 "$lab/usr/sbin/policy-rc.d"
chroot "$lab" /bin/bash -c 'export DEBIAN_FRONTEND=noninteractive LANG=C; apt-get update -qq && apt-get install -y -qq "$@"' javaquest-packages "$@" > "$lab/tmp/install.log" 2>&1 || {
    tail -20 "$lab/tmp/install.log"; exit 1;
}
cp "/mnt/host/d/projekty/javaQuest/scripts/content-migration/$verifier" "$lab/tmp/verify_lab.py"
if [ "$profile" = listening ]; then
    cp /mnt/host/d/projekty/javaQuest/src/main/resources/content/_lx_39_bezpieczenstwo/04_ListeningServicesAudit.json "$lab/tmp/lesson.json"
fi
if [ "$profile" = rsync ]; then chroot "$lab" /usr/bin/rsync --version | head -1; fi
if [ "$profile" = nginx ]; then chroot "$lab" /usr/sbin/nginx -v; fi
if [ "$profile" = postgresql ]; then chroot "$lab" /usr/lib/postgresql/16/bin/postgres --version; fi
chroot "$lab" /usr/bin/python3 /tmp/verify_lab.py
