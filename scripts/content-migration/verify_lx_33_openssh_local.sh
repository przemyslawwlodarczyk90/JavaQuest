#!/usr/bin/env bash
# Git Bash/OpenSSH: wlasne klucze, agent i konfiguracja; bez polaczen i GitHub API.
set -euo pipefail
lab=$(mktemp -d)
agent_started=0
cleanup() {
    if [ "$agent_started" = 1 ]; then ssh-agent -k >/dev/null; fi
    rm -f -- "$lab/key" "$lab/key.pub" "$lab/encrypted" "$lab/encrypted.pub" \
        "$lab/config" "$lab/effective" "$lab/message" "$lab/message.sig" \
        "$lab/allowed_signers" "$lab/agent.sock"
    rmdir -- "$lab"
}
trap cleanup EXIT
ssh-keygen -q -t ed25519 -N '' -C 'private-course-test' -f "$lab/key"
public=$(awk '{print $1, $2}' "$lab/key.pub")
test "$(ssh-keygen -y -f "$lab/key" | awk '{print $1, $2}')" = "$public"
test "$(ssh-keygen -lf "$lab/key" | awk '{print $2}')" = "$(ssh-keygen -lf "$lab/key.pub" | awk '{print $2}')"
ssh-keygen -q -t ed25519 -a 16 -N 'training-only' -f "$lab/encrypted"
encrypted_public=$(ssh-keygen -y -P 'training-only' -f "$lab/encrypted")
ssh-keygen -q -p -P 'training-only' -N 'updated-training-only' -f "$lab/encrypted" >/dev/null
test "$(ssh-keygen -y -P 'updated-training-only' -f "$lab/encrypted")" = "$encrypted_public"
eval "$(ssh-agent -a "$lab/agent.sock" -s)" >/dev/null
agent_started=1
ssh-add "$lab/key" >/dev/null 2>&1
test "$(ssh-add -l | awk '{print $2}')" = "$(ssh-keygen -lf "$lab/key.pub" | awk '{print $2}')"
ssh-add -D >/dev/null 2>&1
if ssh-add -l >/dev/null 2>&1; then echo 'Expected own agent to be empty' >&2; exit 1; fi
cat > "$lab/config" <<EOF
Host srv-test
    HostName 192.168.56.20
    User anna
    Port 2222
    IdentityFile $lab/key
    IdentitiesOnly yes
    ProxyJump bastion
Host bastion
    HostName 192.168.56.10
    User anna
Host *
    ServerAliveInterval 30
    ServerAliveCountMax 3
    BatchMode yes
EOF
chmod 600 "$lab/config"
ssh -G -F "$lab/config" srv-test > "$lab/effective"
grep -qx 'hostname 192.168.56.20' "$lab/effective"
grep -qx 'user anna' "$lab/effective"
grep -qx 'port 2222' "$lab/effective"
grep -qx 'identitiesonly yes' "$lab/effective"
grep -qx 'proxyjump bastion' "$lab/effective"
grep -qx 'serveraliveinterval 30' "$lab/effective"
grep -qx 'serveralivecountmax 3' "$lab/effective"
printf 'private course signature fixture\n' > "$lab/message"
ssh-keygen -Y sign -f "$lab/key" -n file "$lab/message" >/dev/null 2>&1
printf 'student namespaces="file" %s\n' "$public" > "$lab/allowed_signers"
ssh-keygen -Y verify -f "$lab/allowed_signers" -I student -n file -s "$lab/message.sig" < "$lab/message" >/dev/null
if ssh-keygen -Y verify -f "$lab/allowed_signers" -I student -n other -s "$lab/message.sig" < "$lab/message" >/dev/null 2>&1; then
    echo 'Wrong namespace accepted' >&2; exit 1
fi
printf 'tampered\n' >> "$lab/message"
if ssh-keygen -Y verify -f "$lab/allowed_signers" -I student -n file -s "$lab/message.sig" < "$lab/message" >/dev/null 2>&1; then
    echo 'Tampered message accepted' >&2; exit 1
fi
echo 'OK: key recovery/fingerprints/passphrase, own agent, ssh -G, signatures and negative cases'
