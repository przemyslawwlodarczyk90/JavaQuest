#!/bin/sh
# WSL docker-desktop: caly chroot w tmpfs /run, usuwany po tescie.
set -eu
test "$(stat -f -c %T /run)" = tmpfs
lab=$(mktemp -d /run/javaquest-rsync.XXXXXX)
dev_mounted=0
cleanup() {
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
cp /etc/resolv.conf "$lab/etc/resolv.conf"
mount -t tmpfs tmpfs "$lab/dev"
dev_mounted=1
mknod -m 666 "$lab/dev/null" c 1 3
mknod -m 666 "$lab/dev/zero" c 1 5
mknod -m 666 "$lab/dev/random" c 1 8
mknod -m 666 "$lab/dev/urandom" c 1 9
printf '#!/bin/sh\nexit 101\n' > "$lab/usr/sbin/policy-rc.d"
chmod 755 "$lab/usr/sbin/policy-rc.d"
chroot "$lab" /bin/bash -c 'export DEBIAN_FRONTEND=noninteractive LANG=C; apt-get update -qq && apt-get install -y -qq rsync python3' > "$lab/tmp/install.log" 2>&1 || {
    tail -20 "$lab/tmp/install.log"; exit 1;
}
cp /mnt/host/d/projekty/javaQuest/scripts/content-migration/verify_lx_34_rsync_local.py "$lab/tmp/verify_rsync.py"
chroot "$lab" /usr/bin/rsync --version | head -1
chroot "$lab" /usr/bin/python3 /tmp/verify_rsync.py
