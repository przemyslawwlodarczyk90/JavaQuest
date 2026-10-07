# Kurs Linux — stan prac

Instrukcje: `LINUX_COURSE_STAGE_PROMPT.md`. Ten plik: aktualny stan, ostatnia czynność, następny
krok. Oddzielny od `WORK_PROGRESS.md` (Java) i `JS_COURSE_WORK_PROGRESS.md` (JavaScript).

## Stan (2026-10-06)

- **Krok 1 (struktura) — ZROBIONY i ROZBUDOWANY (2026-09-27, dyrektywa „kozak merytorycznie”).**
  46 rozdziałów (`_lx_01`…`_lx_46`) w 4 częściach, 437 lekcji. 234 lekcje mają źródło w
  notatkach `LN01`-`LN31` (`dodatkowe materiały/LinuxMistery/`), 203 to uzupełnienia do pełnego
  programu. Mapowanie w tabeli w `LINUX_COURSE_STAGE_PROMPT.md`; sprawdzone skryptem, że każda
  sekcja każdej notatki jest przypisana do lekcji (poza końcowymi „PODSUMOWANIAMI”).
  Pierwsza wersja (20 rozdziałów / 182 lekcje) została zastąpiona — pliki były puste.
- Tor `LINUX` wpięty w platformę (enum, seed, `CourseTrackConstraintSync`, `/linux` we
  frontendzie, fallback SPA). Weryfikacja: po starcie backendu baza ma JAVA 41/760,
  JAVASCRIPT 14/90, LINUX 46/437 (rozdziały/lekcje), CHECK `chapters_track_check` obejmuje
  LINUX; `LessonContentFilesTest` przechodzi.
- **Krok 2 (teoria) — W TOKU.** Rozdział `_lx_01_wprowadzenie` (8 lekcji) — ZROBIONY
  (2026-09-27): każda lekcja 14 bloków (pełny standard 11 sekcji + CODE_WRONG/CODE_RIGHT + NOTE),
  wyniki poleceń z prawdziwych uruchomień w kontenerach `ubuntu:24.04`, `debian:12`,
  `alpine:3.20`, `rockylinux:9`, `fedora:40`, `archlinux`, `eclipse-temurin:21-jre`.
  Rozdział `_lx_02_wirtualizacja` (10 lekcji, teoretyczny) — ZROBIONY (2026-10-02): po 14 bloków,
  polecenia z opisem efektu `# -> ...`; fragmenty sprawdzalne w kontenerze zweryfikowane
  (flagi `vmx`/`hypervisor` w `/proc/cpuinfo`, emulacja `--platform linux/arm64` -> `aarch64`).
  Rozdział `_lx_03_terminal_i_powloka` (11 lekcji) — ZROBIONY (2026-10-02): po 14 bloków, wyniki
  z prawdziwych sesji w kontenerze `lxlab` (patrz „Laboratorium” niżej).
  Rozdział `_lx_04_hierarchia_katalogow` (8 lekcji) — ZROBIONY (2026-10-02); porównanie usrmerge
  na obrazach `debian:11`, `debian:12`, `alpine:3.20`, `rockylinux:9`; urządzenia blokowe z
  kontenera `--privileged`. Notatka LN03 bywa nieaktualna (`/lib` „32-bitowe”, `/bin` „do trybu
  ratunkowego”) — w lekcjach poprawione.
  Rozdział `_lx_05_operacje_na_plikach` (15 lekcji) — ZROBIONY (2026-10-02); katalog ćwiczebny
  `~/lab` w `lxlab` (dokumenty/, sklep/ z projektem Maven, zdjecia/, start.sh, .ustawienia),
  dodatkowe katalogi `~/glob`, `~/ln`, `~/typy`; drugi użytkownik `piotr` do demonstracji praw.
  Rozdział `_lx_06_przegladanie_tekstu` (13 lekcji) — ZROBIONY (2026-10-04); dane w `~/tekst`
  (app.log 120 linii, sprzedaz.csv, pracownicy/dzialy, app-v1/v2.properties, pliki kodowań).
  Ekrany programów pełnoekranowych (less, more) zbierane skryptem
  `scripts/content-migration/lx_screen.sh` (tmux w `lxlab`, prawdziwy zrzut ekranu).
  Rozdział `_lx_07_strumienie_i_potoki` (12 lekcji) — ZROBIONY (2026-10-04); katalogi ćwiczebne
  `~/strumienie`, `~/xargs` (200 000 plików do „Argument list too long” — usunięte), `~/fifo`;
  przykład Javy (System.out/err) w kontenerze `eclipse-temurin:21-jdk`.
  Rozdział `_lx_08_edytory_nano_i_vim` (10 lekcji) — ZROBIONY (2026-10-04); w `lxlab` doinstalowany
  pełny `vim` 9.1; pliki ćwiczebne `~/edycja` (app.properties, Zamowienie.java); wszystkie ekrany
  nano/Vima to zrzuty z tmux (`lx_screen.sh`; średnik w klawiszach tmux trzeba podać jako `'\;'`).
  Rozdział `_lx_09_vim_zaawansowany` (10 lekcji) — ZROBIONY (2026-10-04); w lekcjach 8-10 w `lxlab`
  powstały `~/.vimrc`, Pathogen, vim-eunuch, NERDTree, Lightline — po rozdziale USUNIĘTE (`~/.vim`,
  `~/.vimrc`), żeby kolejne zrzuty Vima pokazywały konfigurację domyślną.
  Rozdział `_lx_10_wyszukiwanie_plikow` (10 lekcji) — ZROBIONY (2026-10-04); drzewo ćwiczebne `~/szukaj`
  (projekt sklep z target/.git, logi o różnych rozmiarach i datach — `touch -d`, kopie, dowiązania,
  plik użytkownika piotr); w `lxlab` zainstalowany `plocate` (baza aktualizowana ręcznie `updatedb`).
  Rozdział `_lx_11_grep_i_regex` (14 lekcji) — ZROBIONY (2026-10-04); dane w `~/regex` (GPL-3.txt z
  /usr/share/common-licenses, app.log, access.log w formacie Nginx, przykładowy auth.log, wyjatek.log
  z wyjątkiem Javy, projekt sklep jako repo Git z .gitignore); w `lxlab` doinstalowane
  `openssh-server` (prawdziwy sshd_config, usługa NIE działa) i `ripgrep`.
  Rozdział `_lx_12_sed` (8 lekcji) — ZROBIONY (2026-10-04); dane w `~/sed` (app.properties,
  nginx.conf, kopia sshd_config, prod.sed, ustaw.sh — zmień-albo-dopisz z ucieczką znaków).
  Rozdział `_lx_13_awk` (11 lekcji) — ZROBIONY (2026-10-04); dane w `~/awk`; domyślny `awk` w Ubuntu to
  mawk 1.3.4 — doinstalowany też `gawk` 5.2 (alternatywa `awk` przestawiona z powrotem na mawk:
  `update-alternatives --set awk /usr/bin/mawk`); `raport.awk` — przykładowy raport z logu Nginx.
  Rozdział `_lx_14_archiwizacja` (8 lekcji) — ZROBIONY (2026-10-04); dane w `~/archiwa` (logi/app.log —
  5 MB zróżnicowanego logu wygenerowanego awk, sklep z losowym sklep.jar); doinstalowane bzip2, xz,
  zstd, zip/unzip; pomiary kompresorów z `time` w lekcji 6.
  Rozdział `_lx_15_wget_i_curl` (6 lekcji) — ZROBIONY (2026-10-04): wget (pobieranie, -c, rekursja), curl (podstawy, metody/nagłówki/body, testowanie REST na sklep-api Spring Boot 4.1.1), sumy SHA-256/512. Lab: kontenery httpbin i sklep-api w sieci lxnet, serwer python na porcie 8000 (~/www), dane w ~/pobrane.
  Rozdział `_lx_16_uzytkownicy_i_grupy` (12 lekcji) — ZROBIONY (2026-10-04): passwd/shadow/group,
  useradd/adduser, usermod/userdel/gpasswd, passwd/chage, logowanie (NSS, PAM), powłoki i nologin,
  su/sudo, sudoers/visudo, who/w/last. Ciągła historia przykładów: konta kuba (1003), ola→onowak
  (1004), marek (usunięty), faktury (systemowe 995, /opt/faktury), deploy (sudoers NOPASSWD dla
  restartu `sklep`), grupa wdrozenia; sesje SSH z 192.168.56.1 na maszynie Lubuntu.
  **Dyrektywa użytkownika (2026-10-04): „skup się na pisaniu samych lekcji, nie wczuwaj się w
  dockera, nie jest potrzebny”** — od rozdziału 16 wyniki NIE są zbierane w kontenerze; przykłady
  są realistyczne (spójne ze stanem laboratorium), opisane jako „przykładowy wynik”, nie „wynik z
  prawdziwego uruchomienia”.
  Rozdział `_lx_17_uprawnienia` (10 lekcji) — ZROBIONY (2026-10-04): odczyt praw, chmod liczbowy i
  symboliczny, prawa katalogów, umask (Ubuntu: użytkownik 002, root/systemd 022), chown/chgrp/install,
  SUID/SGID/sticky (/srv/wspolne = 3775 root:programisci), ACL (u:kuba na /var/log/faktury), chattr,
  diagnoza. Katalog ćwiczebny `~/prawa`; konfiguracja aplikacji w `/etc/faktury`.
  Rozdział `_lx_18_pakiety_debian` (11 lekcji) — ZROBIONY (2026-10-04): APT/dpkg, update/upgrade/
  full-upgrade, źródła deb822 (ubuntu.sources), wyszukiwanie, apt-cache policy, zależności, remove/
  purge/autoremove, .deb, repozytoria z Signed-By (Adoptium), hold/pinning, unattended-upgrades.
  Rozdział `_lx_19_pakiety_inne_dystrybucje` (6 lekcji) — ZROBIONY (2026-10-04): dnf (Rocky 9), rpm,
  apk (Alpine, musl vs glibc), pacman (Arch), Snap/Flatpak/AppImage, SDKMAN.
  Rozdział `_lx_20_kompilacja_ze_zrodel` (7 lekcji) — ZROBIONY (2026-10-04): po co źródła, gcc/make/
  build-essential, configure/make/install (GNU Hello 2.12.1, Nginx 1.28 z PCRE2), biblioteki .so
  (libpowitanie.so.1 w ~/kompilacja), ldd/ldconfig, moduły Apache + apxs (mod_naglowek), usuwanie
  (make uninstall, /opt, Stow, checkinstall).
  Rozdział `_lx_21_procesy` (13 lekcji) — ZROBIONY (2026-10-05): proces, planista (EEVDF od 6.6), ps,
  rodzice/dzieci, /proc/PID, top/htop (+ top -H i jstack — w Javie 19+ nid dziesiętnie), sygnały
  (graceful shutdown Spring Boot 4: logger o.s.boot.tomcat.GracefulShutdown), kill/pkill/killall,
  nice/ionice, zadania w tle, nohup/disown/setsid, zombie/sieroty, demony i PID 1. Aplikacja `sklep`
  w ~/sklep (application.properties, sekrety.env); pod koniec rozdziału jako sklep.service (PID 5210).
  Rozdział `_lx_22_systemd` (11 lekcji) — ZROBIONY (2026-10-06): systemd/PID 1, usługi i stany, typy
  jednostek (ssh.socket w 24.04 — od 24.04 port z sshd_config przez sshd-socket-generator), pliki
  jednostek, systemctl, enable/mask/presety, drop-iny i daemon-reload (apache-custom z LN22 = nowa
  usługa, nie override), własna usługa, restart i zależności, targety, timery. Stan laboratorium po
  rozdziale: Apache zainstalowany, ale `disabled` (port 80 = Nginx); `sklep.service` przepisany —
  konto systemowe `sklep` (uid 994), jar w `/opt/sklep`, konfiguracja/sekrety w `/etc/sklep`
  (`sklep.env`, 640 root:sklep), `StateDirectory=sklep`, `Type=exec`, `SuccessExitStatus=143`,
  `Restart=on-failure`, `RestartSec=10s`, drop-in `baza.conf` (`Wants=postgresql.service`), PID 6120;
  JDK z APT `/usr/lib/jvm/java-21-openjdk-amd64`; timer `kopia-sklep.timer` (2:30, pg_dump do
  `/var/backups/sklep`); domyślny cel maszyny = `multi-user.target`. Przy okazji poprawione
  odwołania „rozdział 24” → 26 (logi) w lekcjach 16/08, 21/07, 21/13.
  Rozdział `_lx_23_start_systemu` (7 lekcji) — ZROBIONY (2026-10-06): sekwencja startu, BIOS/UEFI
  (VM z UEFI + Secure Boot, ESP `/dev/sda1` 1 GB FAT32 UUID 4A1B-7C2D, `/dev/sda2` ext4 29 GB UUID
  8c1f2d4e-6b7a-4e3d-9f12-5a0c7e9b3d21), GRUB (drop-in `/etc/default/grub.d/90-serwer.cfg`: menu 5 s,
  bez quiet splash; `GRUB_CMDLINE_LINUX` z console=ttyS0), jądro 6.8.0-48 (+45 zapasowe) i initramfs,
  start w systemd (blame/critical-chain), tryby rescue/emergency (`init=/bin/bash`, root zablokowany),
  wyłączanie (shutdown, molly-guard). W `/etc/fstab` jest drugi dysk: `UUID=b52e7c90-1d3f-4a8e-bc61-
  0f9e2d7a4c13 /srv/dane ext4 defaults,nofail,x-systemd.device-timeout=10s 0 2` (w lekcji 6 dysk
  odłączony — rozdział 24 może go „podłączyć z powrotem” jako /dev/sdb1).
  Rozdział `_lx_24_dyski_i_montowanie` (10 lekcji) — ZROBIONY (2026-10-06): nazwy urządzeń, UUID/
  PARTUUID (sdb1 LABEL=dane, PARTUUID 2d7e1b5a-...), MBR/GPT, fdisk/parted, mkfs, mount, fstab, swap,
  fsck, powiększanie. Stan laboratorium: trzeci dysk `sdc` (kopie.vdi, najpierw 10 GiB, w lekcji 10
  powiększony do 15 GiB), GPT, `sdc1` ext4 LABEL=kopie UUID e3a91f57-2c4d-4b8e-9a16-7d5c0b2f8e41
  (`-m 1`), montowany w `/srv/kopie` (fstab: defaults,noatime,nofail,x-systemd.automount,
  x-systemd.idle-timeout=10min,noexec,nosuid,nodev 0 2); kopie bazy przeniesione do `/srv/kopie/sklep`
  (drop-in `kopia-sklep.service.d/dysk.conf` z `RequiresMountsFor=/srv/kopie`, skrypt sprawdza
  `mountpoint`); swap: `/swap.img` zastąpiony `/swapfile` 4 GiB (600), `vm.swappiness=10`
  (`/etc/sysctl.d/90-swappiness.conf`). RAM maszyny 4 GiB (3.8Gi w free).
  Rozdział `_lx_25_systemy_plikow_lvm_luks` (9 lekcji) — ZROBIONY (2026-10-06): VFS i rodzaje systemów
  plików, FAT/exFAT/NTFS/ext4, ext4/XFS/Btrfs (Btrfs na obrazie ~/btrfs.img = /dev/loop7), i-węzły i
  dziennik, LUKS (`sdd1` 5 GiB → `/dev/mapper/sejf` → `/srv/sejf`, crypttab z /root/sejf.key, kopia
  nagłówka `/srv/kopie/sejf-naglowek.img`), LVM (koncepcje na serwerze `srv-test` 192.168.56.20 —
  Ubuntu Server z `ubuntu-vg`; praktyka: `dane_vg` z sde+sdf, potem sdg 10 GiB i pvmove — sde usunięty;
  LV `aplikacja` 4 GiB → `/srv/aplikacja`), RAID 1 `/dev/md0` (sdh + sdj po wymianie sdi) → `/srv/raid`,
  NFS (`/srv/dane/wspolne` 2775 anna:programisci, eksport dla 192.168.56.0/24, klient srv-test
  `/mnt/wspolne`), Samba (udział `[wspolne]`, `@programisci`). GID programisci = 1003.
  Rozdział `_lx_26_logi` (7 lekcji) — ZROBIONY (2026-10-06): /var/log (rsyslog + journald równolegle,
  znaczniki ISO w plikach Ubuntu 24.04), journalctl (trwały dziennik, drop-iny `journald.conf.d/limity.conf`:
  SystemMaxUse=500M, MaxRetentionSec=1month), filtrowanie, rsyslog (`/etc/rsyslog.d/30-sklep.conf`: local0 →
  `/var/log/sklep/zadania.log`; `90-centralny.conf` → TCP logi.firma.example:514 z kolejką), logrotate
  (`/etc/logrotate.d/sklep`, `raporty` z copytruncate), dmesg (dmesg_restrict=1, kuba w grupie adm), logi
  Javy (sklep: `LogsDirectory=sklep`, `Environment=LOGGING_LEVEL_*`, Logback do `/var/log/sklep/aplikacja.log`
  z rotacją, gc.log, HeapDump do /var/lib/sklep, `ExitOnOutOfMemoryError`, logback-spring.xml w /etc/sklep).
  Rozdział `_lx_27_harmonogram_zadan` (7 lekcji) — ZROBIONY (2026-10-06): cron (crontab anny: sprawdz-sklep
  co 5 min → potem przeniesione do timera użytkownika `~/.config/systemd/user/sprawdz-sklep.timer`,
  `loginctl enable-linger anna`; alias `crontab -i`), składnia (reguła LUB dni), `/etc/cron.d/sklep-zadania`
  (konto sklep: przelicz-rabaty co 10 min, usun-porzucone-koszyki 1:30), `/etc/cron.daily/porzadki`,
  pułapki środowiska (`%`, PATH, flock), at/atd (zainstalowany, enable), timery vs cron, kopia zapasowa:
  `/usr/local/bin/kopia-sklep.sh` (pg_dump + weryfikacja, migawki `rsync --link-dest` w
  `/srv/kopie/sklep/pliki/RRRR-MM-DD`, retencja 14), `kopia-sklep.service` (root, `RequiresMountsFor=/srv/kopie
  /srv/dane`, `OnFailure=powiadom@%n.service`, Nice/IOSchedulingClass), timer 2:30 Persistent.
  Przy okazji poprawione odwołanie Netplan → rozdział 31 (lekcja 23/05). Numeracja dalej: 28 monitorowanie,
  29 jądro, 30-32 sieć, 33 SSH, 34 rsync/scp, 35 zapora, 36 WWW, 37 bazy, 38 Java, 39 bezpieczeństwo.
  Rozdział `_lx_28_monitorowanie_wydajnosci` (10 lekcji) — ZROBIONY (2026-10-06): uptime/load/PSI (2 CPU),
  free/page cache, vmstat/iostat (analiza nocnej kopii 2:40 — sdb/sdc wysycone), pidstat/iotop/systemd-cgtop/
  smem, lsof (+L1, -b -w przy NFS), strace (+ eBPF tcpconnect), OOM (drop-in `sklep.service.d/pamiec.conf`:
  MemoryHigh=1000M, MemoryMax=1200M), ulimit (drop-in `nginx.service.d/limity.conf` LimitNOFILE=65536,
  `worker_rlimit_nofile 65536`, `worker_connections 16384`), narzędzia JDK (doinstalowany
  openjdk-21-jdk-headless; `sudo -u sklep jcmd 6120 ...`; JFR do /var/lib/sklep), sysstat/sar (włączony,
  /var/log/sysstat). Ubuntu: postgres UID 114.
  Rozdział `_lx_29_jadro_i_sprzet` (6 lekcji) — ZROBIONY (2026-10-06): uname (jądro GA 6.8.0-48, 6.8.0-49
  czeka na restart), moduły (`/etc/modules-load.d/kubernetes.conf`: overlay, br_netfilter; blokada
  usb-storage — tylko przykład), sysctl (`/etc/sysctl.d/80-serwer-aplikacji.conf`: somaxconn 8192,
  ip_local_port_range, inotify), /proc i /sys, sprzęt (VirtualBox: i7-12700H, 2 vCPU, 4 GB, e1000 enp0s3
  MAC 08:00:27:3a:5c:1e, SATA 00:0d.0, xHCI 00:0c.0), udev (`/etc/udev/rules.d/99-kopia-usb.rules` dla
  pendrive'a SanDisk 4C530001240915112472 → /dev/kopia-usb, grupa kopie GID 1009,
  `kopia-na-usb.service`).
  Rozdział `_lx_30_podstawy_sieci` (10 lekcji) — ZROBIONY (2026-10-06). USTALONA SIEĆ LABORATORIUM (dopisane
  też do lekcji 23/06 i 29/04): lubuntu-nauka ma 2 karty — `enp0s3` NAT 10.0.2.15/24 (brama 10.0.2.2, DNS
  VirtualBox 10.0.2.3, MAC 08:00:27:3a:5c:1e, jedyna trasa domyślna) i `enp0s8` host-only 192.168.56.10/24
  statycznie BEZ bramy (MAC 08:00:27:91:4d:07, IPv6 ULA fd00:56::10/64); gospodarz (laptop) 192.168.56.1 na
  host-only, 192.168.1.10 w LAN, router domowy 192.168.1.1, adres publiczny 198.51.100.77; srv-test
  192.168.56.20 (fd00:56::20, MAC 08:00:27:5e:b2:31), srv-docker (tylko NAT + przekierowania, potem
  host-only 192.168.56.30); sklep.firma.example A 198.51.100.77 / AAAA 2001:db8:4c2a:10::77; /etc/hosts:
  `192.168.56.20 srv-test`. Od lekcji 30/07 sklep nasłuchuje `server.address=127.0.0.1:8080` za Nginx
  (`/etc/nginx/sites-available/sklep`: listen 80 + [::]:80, proxy_pass 127.0.0.1:8080), nowy PID 15210.
  PostgreSQL nasłuchuje tylko localhost; pg_hba ma wpis dla 192.168.56.0/24 (do rozdziału 37).
  Rozdział `_lx_31_konfiguracja_sieci` (9 lekcji) — ZROBIONY (2026-10-07): ip, net-tools, hostname/hosts
  (`127.0.1.1 lubuntu-nauka.lab.local lubuntu-nauka`, wpisy srv-test i srv-docker), resolver
  (`/etc/systemd/resolved.conf.d/90-dns.conf`: FallbackDNS 1.1.1.1/9.9.9.9, DNSOverTLS=opportunistic),
  Netplan (srv-test: networkd, `/etc/netplan/60-hostonly.yaml`, DNS 127.0.0.1 — na srv-test działa dnsmasq
  dla lab.local z pulą DHCP .100-.200 i rezerwacją srv-docker 08:00:27:c4:18:a2 → .30), nmcli (lubuntu-nauka:
  NetworkManager, połączenie „hostonly” na enp0s8: 192.168.56.10/24 + fd00:56::10/64, never-default,
  DNS 192.168.56.20, search lab.local; zapis `/etc/netplan/90-NM-6f1c2a7e-....yaml`), ping/tracepath/mtr,
  dig/host/nslookup. Po migracji sklep.firma.example = 203.0.113.40 (AAAA 2001:db8:4c2a:10::40), TTL 60.
  Rozdział `_lx_32_diagnostyka_sieci` — PRAWIE GOTOWY (2026-10-07): lekcje 01-06 i 10-12 gotowe (netstat,
  -plnt, potoki i uprawnienia, ss, lsof -i, podstawy nmap; 10 legalność skanowania + Zenmap/ndiff, plik zakresu
  `~/skany/zakres-2026-10.txt` na srv-test; 11 netcat — warianty, `nc -zv -w`, prowizoryczny serwer, UDP, `/dev/tcp`;
  12 tcpdump — filtry BPF, flagi TCP, `-i lo` dla ruchu Nginx→sklep, `-w`/`-C`/`-W`/`-G`, Wireshark).
  Lekcje 07-09 (rodzaje skanów TCP/UDP, wykrywanie usług i systemu, skrypty NSE) PUSTE — generowanie treści
  zatrzymał filtr bezpieczeństwa DWUKROTNIE (2026-10-06 i 2026-10-07, także przy ujęciu „audyt własnego
  laboratorium”). Nie próbować ponownie bez decyzji użytkownika (np. napisze sam, scali tematy z lekcją 06
  w ogólnym zarysie albo lekcje zostaną usunięte ze struktury).
  Rozdział `_lx_33_ssh` (12 lekcji) — ZROBIONY (2026-10-07). Stan laboratorium: OpenSSH 9.6p1; odcisk klucza hosta
  srv-test ED25519 `SHA256:Qm7vR1kd2XoT5c8bLwz0eYfA3nH6uJpS9tGiK4lVxEo`. Klucze anny na lubuntu-nauka: `id_ed25519` (z hasłem,
  `SHA256:7fKq2Wm9...NwQk`, komentarz anna@lubuntu-nauka), `id_rsa_sw01` (RSA 4096, przełącznik sw-01 192.168.56.2),
  `deploy_sklep` (bez hasła, CI), `id_ed25519_github`; klucz z laptopa „anna@laptop-anna”. `~/.ssh/config` anny:
  `Include ~/.ssh/config.d/*`, aliasy srv-test/test (Port 2222), srv-docker, deploy-test (User deploy, Port 2222), sw01,
  `Host srv-*`, `Host *` na końcu (AddKeysToAgent 4h, IdentitiesOnly, ServerAlive 30/3); pliki config.d: `firma`
  (bastion.firma.example 203.0.113.10 → app01 10.20.0.11, db01 10.20.0.21, ProxyJump; użytkownik anna.kowalska),
  `tunele` (baza-lubuntu: LocalForward 15432), `github`. srv-test: sshd na porcie **2222** (`sshd_config.d/10-port.conf`,
  ssh.socket), `20-zabezpieczenia.conf` (PermitRootLogin no, hasła wyłączone, AllowGroups ssh-dostep [GID 1005: anna,
  deploy], MaxAuthTries 3, LoginGraceTime 30, ClientAlive 300/2, X11/agent forwarding off), ufw aktywny z regułą
  2222/tcp (szczegóły zapory — rozdział 35), konto kuba (uid 1002) bez dostępu SSH; deploy: klucz z
  `restrict,from=192.168.56.0/24,command=/usr/local/bin/wdroz-sklep.sh` (jar na stdin → /opt/sklep/releases, symlink
  /opt/sklep/sklep.jar); deploy key `~/.ssh/deploy_sklep_repo` (read-only) i klon repo sklep. GitHub: konto
  anna-kowalska, repo `sklep` (git 2.43, gałąź main). sklep na lubuntu-nauka ma (do debugowania) JDWP na 127.0.0.1:5005.
  Pozostałe lekcje (`_lx_34`…`_lx_46`) nadal PUSTE. Ćwiczenia i quiz — 0 wszędzie (krok 3).
- **Uwaga do weryfikacji live (2026-10-02):** port 5432 zajmuje teraz kontener Postgresa innego
  projektu (`offerbrowserprototype-postgres-1`) — NIE uruchamiaj na nim backendu platformy
  (ContentReset czyści tabele treści). Do czasu zwolnienia portu weryfikacja = build skryptem
  `lx_build_theory.js` + `LessonContentFilesTest`.
- **Pisanie `.txt` (nauczka z rozdziału 2):** każdy blok musi mieć choć jedno zdanie `body`
  PRZED linią `@@code` — skrypt odrzuca bloki z pustym `body`.

## Konwencje ustalone przy rozdziale 1 (stosuj dalej)

- **Format przykładów**: sesja terminala — linia `$ polecenie` (komentarz `# ...` po ASCII), pod
  nią DOSŁOWNY wynik z prawdziwego uruchomienia; skracanie wyniku oznaczaj `(...fragment wyniku)`.
  Kontynuacja linii jako `> `. Pole `code` bez polskich znaków (także komentarze).
- **Wyniki sprawdzaj w kontenerze** (Docker Desktop trzeba czasem uruchomić:
  `Start-Process "C:\Program Files\Docker\Docker\Docker Desktop.exe"`). Uwaga na Git Bash: ścieżki
  typu `/usr/...` w argumentach `docker run` są przerabiane na `C:/Program Files/Git/...` —
  opakuj polecenie w `bash -c '...'` albo ustaw `MSYS_NO_PATHCONV=1`. Kontener bez TTY wypisuje
  `ls` w jednej kolumnie — dla widoku terminala użyj `ls -C -w 100 -T 0`; komunikaty błędów z
  `bash -c` mają prefiks `bash: line 1:`, w sesji interaktywnej go nie ma (sprawdzaj `bash -i`).
- **Jądro w przykładach** to `6.6.87.2-microsoft-standard-WSL2` (Docker Desktop na WSL2) — lekcje
  to wyjaśniają (rozdział 1, lekcja 3); przy systemd/sieci/dyskach potrzebny będzie inny sposób
  weryfikacji (VM/WSL z systemd) — zaznaczaj w treści, gdy wyniku nie da się uzyskać w kontenerze.
- **Pisanie**: lekcję piszę w pliku `.txt` (bloki `@@ TYP | Nagłówek`, kod po linii `@@code`) i
  konwertuję `node scripts/content-migration/lx_build_theory.js <plik.txt> <plik.json>` — skrypt
  zachowuje `exercises`/`quiz`, odrzuca polskie znaki w `code`, cyrylicę, puste `body`.
- **Weryfikacja live**: API `/api/**` wymaga teraz JWT (moduł logowania) — zamiast `curl` na
  endpoint sprawdzaj po starcie backendu tabelę `content_blocks` w Postgresie:
  `docker start javaquest-postgres` (użytkownik `user`, baza `pgDB`), potem
  `mvnw.cmd resources:resources` + `mvnw.cmd spring-boot:run` (JAVA_HOME jak w `WORK_PROGRESS.md`)
  i zapytanie `select l.slug, count(b.id) from lessons l join chapters c ... left join
  content_blocks b ...` dla rozdziału + regresja jednej lekcji Javy i JS. Po sprawdzeniu zatrzymaj
  backend i uruchom `mvnw.cmd test -Dtest=LessonContentFilesTest`.

## Laboratorium do zbierania wyników (od rozdziału 3)

- Trwały kontener `lxlab` (`ubuntu:24.04`, hostname `lubuntu-nauka`, użytkownik `anna` z sudo bez
  hasła, `unminimize` + `man-db`, `tealdeer`, `bash-completion`, `tree`, `file`, `nano`, `vim-tiny`,
  `curl`). Odtworzenie: `docker run -d --name lxlab --hostname lubuntu-nauka ubuntu:24.04 sleep
  infinity` + instalacja pakietów + `useradd -m -s /bin/bash anna`; do `~/.bashrc` anny dopisane
  `PS1='$ '`. Cache tldr trzeba wypakować ręcznie z
  `https://github.com/tldr-pages/tldr/releases/latest/download/tldr.zip` do
  `~/.cache/tealdeer/tldr-pages` (stary adres archiwum już nie działa).
- Transkrypty sesji interaktywnej (prompt `$ `, prawdziwy pseudoterminal, działa historia `!!`,
  Tab): polecenia na stdin do `docker exec -i -u anna ... lxlab script -qfc "bash -i" /dev/null`
  (skrypt pomocniczy: `scripts/content-migration/lx_session.sh [rc|norc]`). Uwaga: `sudo` w takiej
  sesji „zjada” resztę wejścia — polecenia z sudo uruchamiaj osobno, bez `script`.
- Od rozdziału 6: w `~/.bashrc` anny `export LANG=C.UTF-8` (oba skrypty też ustawiają `LANG`;
  kolejność sortowania jak w `C`, ale znaki wielobajtowe działają). Zainstalowany `tmux` —
  `lx_screen.sh KOL WIER 'polecenie' [klawisze... @=zrzut]` daje prawdziwy ekran programów
  pełnoekranowych. Polecenia z `cat > plik` + Ctrl+D zawieszają `lx_session.sh` — omijać.
  W polu `code` nie wolno polskich liter — przykłady ze znakami spoza ASCII robić na czeskim
  tekście (`Příliš žluťoučký kůň`) lub `café`.

## Krok 3 (ćwiczenia i quiz) — ustalenia (2026-10-07)

- Decyzja użytkownika: **30 ćwiczeń i 100 pytań na lekcję**, dużo praktycznych poleceń i przykładów;
  **najpierw dokończyć teorię wszystkich rozdziałów, dopiero potem ćwiczenia** (polecenie z 2026-10-07).
- Narzędzie: `scripts/content-migration/lx_build_practice.js <plik.txt> <lekcja.json>` (format `#EX` P:/H:/S:,
  `#Q` Q:/+/-/-/-/E:; waliduje 30/100, brak polskich liter w solution, 3 błędne opcje, rozkłada litery
  poprawnych odpowiedzi równo). Źródła .txt trzymane w `scripts/content-migration/lx_practice/`.
- Stan: `_lx_01/01_WhatIsLinux` — 30/100 GOTOWE (pilotaż). Pozostałe lekcje — 0/0.
- **Do decyzji (sugestia użytkownika z 2026-10-07): nie każda lekcja potrzebuje 30/100** — „niektóre tematy po
  macoszemu, a niektóre porządnie”. Propozycja poziomów: **A** (główne polecenia: grep, find, sed/awk, prawa,
  systemctl, ssh, rsync, ip...) 30/100; **B** (standardowe, m.in. nano/Vim) ok. 15/40; **C** (teoretyczne/poboczne:
  rozdział 2, historia, legalność, Zenmap, „czym jest X”) ok. 5/15. Przed krokiem 3: przypisać poziom każdej
  lekcji w tabeli i dać użytkownikowi do akceptacji; walidacja w `lx_build_practice.js` (dziś sztywne 30/100)
  do dostosowania.

## Następny krok

**Krok 2 — teoria: `_lx_34_transfer_i_synchronizacja`**, potem kolejne (lekcje 32/07-09 czekają na decyzję użytkownika). Dyrektywa 2026-10-07: pracować bez pytania o zgodę także między rozdziałami; dużo commitów, BEZ push, bez stopki współautorstwa, potem kolejne rozdziały
po kolei (dyrektywa użytkownika 2026-10-04: „zrób wszystkie lekcje”, bez pytania o zgodę między
rozdziałami). Ćwiczenia i quiz dopiero w kroku 3 (liczby do potwierdzenia z użytkownikiem). Commit
po każdym rozdziale, komunikat po polsku, bez stopki o współautorstwie.
