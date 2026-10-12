const fs=require('fs');
const ex=[
['Rozpocznij własne laboratorium w jednej sesji Bash, bez instalacji pakietów.','Zapisz maskę; utwórz katalog.', 'patch_mask=$(umask)\numask 077\npatch_lab=$(mktemp -d /tmp/javaquest-patching.XXXXXX)'],
['Zapisz dystrybucję i działające jądro.','Userspace i jądro mają osobne wersje.', 'cat /etc/os-release > "$patch_lab/os.txt"\nuname -r > "$patch_lab/kernel.txt"\ncat "$patch_lab/os.txt" "$patch_lab/kernel.txt"'],
['Sprawdź wersje apt i dpkg.','dpkg-query nie instaluje pakietów.', "dpkg-query -W apt dpkg"],
['Sprawdź kandydatów apt i dpkg.','policy nie odświeża indeksów.', 'apt-cache policy apt dpkg'],
['Przejrzyj definicje źródeł APT bez edycji.','Obsłuż list i sources.', 'find /etc/apt -maxdepth 2 -type f \\( -name sources.list -o -name "*.list" -o -name "*.sources" \\) -exec sed -n "1,100p" {} \\;'],
['Zapisz symulację upgrade.','-s nie instaluje; indeksy mogą być stare.', 'apt-get -s upgrade > "$patch_lab/plan.txt"\nsed -n "1,60p" "$patch_lab/plan.txt"'],
['Policz planowane instalacje i usunięcia.','Linie Inst i Remv opisują plan.', 'awk \'/^Inst / {i++} /^Remv / {r++} END {printf "install_or_upgrade=%d remove=%d\\n",i,r}\' "$patch_lab/plan.txt"'],
['Sprawdź wstrzymania pakietów bez zmiany stanu.','showhold jest odczytem.', 'apt-mark showhold'],
['Porównaj rewizje 1:2.4-0ubuntu1.9 i 1:2.4-0ubuntu1.10.','Nie sortuj tekstowo.', 'dpkg --compare-versions 1:2.4-0ubuntu1.10 gt 1:2.4-0ubuntu1.9'],
['Sprawdź epokę 2:1.0-1 wobec 1:99.0-1.','Epoka ma pierwszeństwo.', 'dpkg --compare-versions 2:1.0-1 gt 1:99.0-1'],
['Porównaj 3.0~rc1 z wydaniem 3.0.','Tylda sortuje przed pustym ciągiem.', 'dpkg --compare-versions 3.0~rc1 lt 3.0'],
['Utwórz model wersji zainstalowanych i progów poprawki.','To fikcyjny biuletyn, nie skan hosta.', 'printf "web-core\\t1:2.4-0ubuntu1.9\\t1:2.4-0ubuntu1.10\\nworker\\t2.0-1\\t2.0-1\\nagent\\t4.0-2\\t4.0-1\\n" > "$patch_lab/advisory.tsv"'],
['Wskaż starsze wersje w modelu biuletynu.','Nie cofaj nowszej wersji do progu.', 'while IFS="$(printf \'\\t\')" read -r package installed fixed; do\n if dpkg --compare-versions "$installed" lt "$fixed"; then printf "%s needs review\\n" "$package"; fi\ndone < "$patch_lab/advisory.tsv"'],
['Sprawdź instalację unattended-upgrades.','Kontener minimalny może go nie mieć.', 'if dpkg-query -W unattended-upgrades 2>/dev/null; then :; else printf "package not recorded\\n"; fi'],
['Zapisz konfigurację APT prywatnie i wyświetl ustawienia automatu.','Całość może zawierać proxy; nie publikuj jej.', 'apt-config dump > "$patch_lab/config.txt"\nawk \'/^APT::Periodic|^Unattended-Upgrade/ {print}\' "$patch_lab/config.txt"'],
['Sprawdź własny model automatu: aktualizacja list i instalacja codziennie, reboot wyłączony.','Nie kopiuj modelu do /etc.', 'printf \'APT::Periodic::Update-Package-Lists "1";\\nAPT::Periodic::Unattended-Upgrade "1";\\nUnattended-Upgrade::Automatic-Reboot "false";\\n\' > "$patch_lab/periodic.conf"\napt-config -c "$patch_lab/periodic.conf" shell LISTS APT::Periodic::Update-Package-Lists AUTO APT::Periodic::Unattended-Upgrade REBOOT Unattended-Upgrade::Automatic-Reboot'],
['Odczytaj sygnał reboot-required, jeśli istnieje.','Brak sygnału nie dowodzi braku wszystkich luk.', 'if test -e /run/reboot-required; then cat /run/reboot-required; else printf "no reboot marker\\n"; fi\nif test -f /run/reboot-required.pkgs; then cat /run/reboot-required.pkgs; fi'],
['Przygotuj własny model sygnału restartu.','Nie twórz plików w systemowym /run.', 'mkdir "$patch_lab/run-fixture"\nprintf "System restart required\\n" > "$patch_lab/run-fixture/reboot-required"\nprintf "linux-image-fixture\\nlinux-base-fixture\\n" > "$patch_lab/run-fixture/reboot-required.pkgs"'],
['Przejrzyj historię APT, jeśli istnieje.','Nie dopisuj do systemowego logu.', 'if test -r /var/log/apt/history.log; then tail -40 /var/log/apt/history.log; else printf "no apt history\\n"; fi'],
['Utwórz jawnie fikcyjny log dwóch prób aktualizacji.','Rozróżnij plan i wynik.', 'printf "fixture-only\\n2026-01-10 INFO Packages that will be upgraded: web-core\\n2026-01-10 INFO All upgrades installed\\n2026-01-11 ERROR Download failed: worker\\n" > "$patch_lab/unattended.log"'],
['Znajdź błąd w modelu logu.','Lista planowanych pakietów nie dowodzi sukcesu.', 'grep " ERROR " "$patch_lab/unattended.log"'],
['Zmapuj modelowy plik w procesie Python oczekującym na FIFO.','To własny proces, nie usługa systemowa.', 'printf "old fixture\\n" > "$patch_lab/library.bin"\ncat > "$patch_lab/map.py" <<\'PY\'\nimport mmap,sys\nwith open(sys.argv[1],"rb") as f:\n    with mmap.mmap(f.fileno(),0,access=mmap.ACCESS_READ) as view:\n        print(view[:].decode().strip(),flush=True)\n        sys.stdin.readline()\nPY\nmkfifo "$patch_lab/stop.fifo"\nexec 9<> "$patch_lab/stop.fifo"\npython3 "$patch_lab/map.py" "$patch_lab/library.bin" <&9 > "$patch_lab/old.txt" &\npatch_pid=$!\nfor attempt in $(seq 1 50); do test -s "$patch_lab/old.txt" && break; sleep 0.1; done\ntest -s "$patch_lab/old.txt"'],
['Zastąp modelowy plik przez rename, bez kończenia procesu.','Stara mapa zachowuje poprzedni inode.', 'printf "new fixture\\n" > "$patch_lab/library.next"\nmv -f -- "$patch_lab/library.next" "$patch_lab/library.bin"\ncat "$patch_lab/library.bin" "$patch_lab/old.txt"'],
['Potwierdź usuniętą mapę własnego procesu w /proc.','Nie skanuj cudzych procesów.', 'grep -F "$patch_lab/library.bin (deleted)" "/proc/$patch_pid/maps"'],
['Zakończ własny proces przez FIFO i zaczekaj.','Nie używaj pkill python.', 'printf "stop\\n" >&9\nwait "$patch_pid"\nexec 9>&-\npatch_pid='],
['Uruchom model ponownie i odczytaj nową treść.','Zamknięte stdin kończy ten proces.', 'python3 "$patch_lab/map.py" "$patch_lab/library.bin" </dev/null > "$patch_lab/new.txt"\ncat "$patch_lab/new.txt"'],
['Zapisz właścicieli i sposoby aktualizacji fikcyjnych komponentów spoza APT.','Aktualizacja JDK nie zmienia zależności jar.', 'printf "component\\towner\\tupdate_method\\nsource-php\\tweb-team\\trebuild-tested-source\\napp-jar\\tapp-team\\trebuild-and-deploy\\ncontainer-image\\tplatform-team\\trebuild-and-recreate\\n" > "$patch_lab/external.tsv"'],
['Zapisz plan okna dla dwóch instancji z bramką zdrowia.','To plan, nie operacja na serwerze.', 'printf "scope=fixture only\\nprecheck=restore test and versions\\nsequence=drain A, update A, restart A, check readiness, then B\\nabort=do not drain B if A is unhealthy\\npostcheck=versions, readiness, error rate\\n" > "$patch_lab/window.txt"'],
['Zapisz granice laboratorium.','Nie deklaruj instalacji poprawki ani restartu jądra.', 'printf "verified=read-only apt, dpkg comparisons, config syntax, own mapped file replacement\\nnot_verified=security installation, service restart, kernel reboot, livepatch\\n" > "$patch_lab/scope.txt"'],
['Usuń własne laboratorium i odtwórz maskę.','Sprawdź prefiks i kanoniczną ścieżkę.', 'case "$patch_lab" in /tmp/javaquest-patching.*) ;; *) exit 1;; esac\ntest "$(readlink -f "$patch_lab")" = "$patch_lab"\nrm -rf -- "$patch_lab"\numask "$patch_mask"\ntest ! -e "$patch_lab"']
];
const rows=require('./write_lx_39_02_questions.cjs');
if(ex.length!==30||rows.length!==100)throw Error(ex.length+'/'+rows.length);
const path='scripts/content-migration/lx_practice/_lx_39_02.txt';
if(fs.existsSync(path))throw Error('Preserve existing source');
fs.writeFileSync(path,ex.map(([p,h,s])=>'#EX\nP: '+p+'\nH: '+h+'\nS:\n'+s+'\n').join('')+rows.map(([q,a,b,c,d,e])=>'#Q\nQ: '+q+'\n+ '+a+'\n- '+b+'\n- '+c+'\n- '+d+'\nE: '+e+'\n').join(''));

