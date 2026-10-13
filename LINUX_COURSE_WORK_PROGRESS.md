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
  Rozdział `_lx_34_transfer_i_synchronizacja` (12 lekcji) — ZROBIONY (2026-10-07): scp (SFTP pod spodem od 9.0, `-O`),
  sftp (konto `hurtownia` tylko-SFTP z chrootem `/srv/sftp/hurtownia` root:root 755, podkatalog `do-sklepu` 2770
  hurtownia:sklep, grupa `sftp-partnerzy` dopisana do `AllowGroups` w `20-zabezpieczenia.conf`, `40-sftp.conf` z
  `ForceCommand internal-sftp -d /do-sklepu`), rsync 3.2.7: quick check, algorytm różnicowy (`~/zrzuty/sklep.sql`,
  `--rsyncable`), `-a`/`-i`, ukośniki (`/srv/kopie/anna/` — katalog anny na dysku kopii), przez SSH (`--rsync-path="sudo
  rsync"` + `/etc/sudoers.d/anna-rsync` na srv-test; klucz `kopia_offsite` z `rrsync -ro /home/anna/kopie`), `--delete`
  (pliki statyczne `/var/www/sklep`, `--backup-dir`, `--max-delete`, skrypt `lustro-dane.sh` z `mountpoint`), wykluczenia
  (`wdrozenie-wyklucz.txt`), prawa/wznawianie/pasmo (`kopia-etc.sh` + `kopia-etc.service`, migawki `/srv/kopie/etc/DATA`),
  migawki `--link-dest` (nawiązanie do `kopia-sklep.sh` z rozdz. 27; serwer off-site `backup` z kontem `kopie` i skryptem
  `pobierz-kopie-sklep.sh`, dowiązanie `ostatnia`), `python3 -m http.server` (Python 3.12.3, `~/udostepnione`) i `jwebserver`.
  ufw aktywny także na lubuntu-nauka (reguły w rozdziale 35).
  Rozdział `_lx_35_zapora_sieciowa` (6 lekcji) — ZROBIONY (2026-10-07). ZAPORA LABORATORIUM: lubuntu-nauka — ufw
  (skrypt `~/infra/zapora/lubuntu-nauka.sh`): deny incoming/allow outgoing/deny routed, `limit` 22/tcp z 192.168.56.0/24,
  'Nginx Full', 2049 i 111 z lab, Samba z lab, logging low; przekierowanie VirtualBox NAT gospodarz 8022 → 22 (SSH z NAT
  zablokowany). Elasticsearch (testowy, port 9200) i Actuator `management.server.port=8081` przestawione na 127.0.0.1
  (`network.host`, `MANAGEMENT_SERVER_ADDRESS` w `/etc/sklep/sklep.env`); JDWP 127.0.0.1:5005. srv-test — ufw `limit`
  2222/tcp z 192.168.56.0/24; fail2ban 1.0.2 (`jail.local`: bantime 1h + increment, findtime 10m, maxretry 5, ignoreip
  192.168.56.1 i .10, banaction nftables-multiport, backend systemd; `[sshd]` port 2222 maxretry 3). lubuntu-nauka:
  jail `sklep-logowanie` (filtr na `LogowanieListener ... Nieudane logowanie: email=... ip=<HOST>` w
  `/var/log/sklep/aplikacja.log`, porty http/https, maxretry 10/5m, bantime 30m); Nginx przekazuje X-Real-IP/X-Forwarded-For,
  `SERVER_FORWARD_HEADERS_STRATEGY=native`. Nowe maszyny: `deb-lab` 192.168.56.40 (Debian 12, `/etc/nftables.conf`
  z zestawem `admin`, port 9100 dla 192.168.56.20), `rocky-lab` 192.168.56.50 (Rocky 9, firewalld: public = http/https
  + rich rule 192.0.2.10→9100; internal = źródło 192.168.56.0/24 z ssh, cockpit, usługą `sklep` 8081 (Nginx przed
  Spring Boot; `semanage port -a -t http_port_t -p tcp 8081`)).
  Rozdział `_lx_36_serwery_www` (9 lekcji) — ZROBIONY (2026-10-07). Apache 2.4.58 na **srv-test** (port 80, ufw 'Apache
  Full' z lab): `conf-available/zabezpieczenia.conf` (ServerTokens Prod, nagłówki, `-Indexes`), mod headers; hosty
  `strona.lab.local` (+www; `/var/www/strona`, `DirectoryIndex index.php index.html`, katalog `cenniki` z `+Indexes`),
  `docs.lab.local` (Javadoc), `panel.lab.local` (SPA, `FallbackResource`), host domyślny `000-domyslny.conf` (404),
  `000-default` wyłączony; rekordy dnsmasq `/etc/dnsmasq.d/www.conf`; hasła strony w `/etc/strona/baza.php`. PHP 8.3.12 ze
  źródeł w `/opt/php-8.3` jako moduł (`libphp.so`, `mods-available/php.{load,conf}`), MPM przełączony na **prefork**,
  `php.ini-production`. Nginx 1.24.0 na lubuntu-nauka: `sites-available/sklep` (upstream `sklep_app` 127.0.0.1:8080
  max_fails + 8082 backup, keepalive 16, `/assets/` alias /var/www/sklep/, `/actuator/` 404 poza health, `/ws/`,
  `client_max_body_size 20m`, `error_page 502 503 504 /przerwa.html` z /var/www/sklep-bledy, log `sklep-access.log` w
  formacie `czasy` z `conf.d/log-czasy.conf`), druga instancja `sklep@8082`; strona `pomoc.lab.local` (/var/www/pomoc).
  `app01` (produkcja, 203.0.113.40): certbot 2.9.0, certyfikat Let's Encrypt dla sklep.firma.example + www (do
  2027-01-05), `listen 443 ssl http2`, HSTS 1 rok, ufw 'Nginx Full'.
  Rozdział `_lx_37_bazy_danych_na_serwerze` (8 lekcji) — ZROBIONY (2026-10-08). srv-test: MySQL Community 8.4.6 z paczki
  `.deb` (dpkg + `apt --fix-broken install`, ~/mysql-deb), `mysql_secure_installation`; konta `root@localhost`,
  `admin@localhost` (ALL, panel), `strona@localhost` (DML na `strona`), `kopie@localhost` (odczyt; hasło w
  `/root/.my.cnf`); datadir przeniesiony do **/srv/mysql** (alias AppArmor), `/var/lib/mysql.stary`; skrypt
  `/usr/local/bin/kopia-mysql.sh` (cron root 3:00, `/srv/kopie/mysql`, 14 dni); `require_secure_transport = ON`
  (`mysql.conf.d/90-tls.cnf`). PHP na srv-test przełączone na **php8.3-fpm** z pakietów (moduł ze źródeł wyłączony,
  MPM z powrotem `event`); phpMyAdmin 5.2.1 z pakietu + `conf-available/phpmyadmin-dostep.conf` (Require ip 127.0.0.1
  192.168.56.0/24). lubuntu-nauka: PostgreSQL 16.10 (klaster 16/main), role `sklep` (DML), **`sklep_migracje`**
  (właściciel bazy, `FLYWAY_USER` w sklep.env), `raporty` (SELECT, CONNECTION LIMIT 5); `conf.d/90-siec.conf`
  listen 'localhost,192.168.56.10'; pg_hba: linia 131 `hostssl sklep raporty 192.168.56.20/32 scram-sha-256`; ufw 5432
  tylko z 192.168.56.20; `conf.d/80-dziennik.conf` (log_connections). kopia-sklep.sh zapisuje też
  `globalne-DATA.sql` (pg_dumpall --globals-only). deb-lab: PostgreSQL 17.6 z PGDG (`/etc/apt/sources.list.d/pgdg.sources`),
  odtworzona baza sklep z kopii.
  Rozdział `_lx_38_java_na_serwerze` (9 lekcji) — ZROBIONY (2026-10-08). lubuntu-nauka: Java — openjdk-21-jdk-headless +
  jre (21.0.8), temurin-21-jdk (Adoptium), openjdk-17-jre-headless (dla `raporty-legacy.service`, Spring Boot 2.7,
  pełna ścieżka do Javy 17); domyślna Java w trybie auto (21); `~/.bashrc` anny z JAVA_HOME, `/etc/environment` z
  JAVA_HOME, `/etc/profile.d/java.sh` (JAVA_HOME z alternatywy javac), `/etc/profile.d/gradle.sh`; Maven 3.8.7 z apt,
  wrapper projektu 3.9.11, Gradle 9.1.0 w `/opt/gradle-9.1.0` (+ dowiązanie /opt/gradle). sklep: szablon
  **`sklep@.service`** (instancje `sklep@8080` i `sklep@8082`, `--server.port=%i`), drop-iny `java.conf` (pełna
  ścieżka), `poswiadczenia.conf` (`LoadCredential=spring.datasource.password:/etc/sklep/poswiadczenia/...`,
  `SPRING_CONFIG_IMPORT=optional:configtree:${CREDENTIALS_DIRECTORY}/`), hasło usunięte z sklep.env; JAVA_OPTS:
  `-XX:MaxRAMPercentage=60 -XX:InitialRAMPercentage=30 -XX:+UseG1GC -XX:+ExitOnOutOfMemoryError
  -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/var/lib/sklep`; `/etc/sklep/sklep-8082.env` dla instancji 8082;
  wydania w **`/opt/sklep/releases/WERSJA/sklep.jar`** (1.2.0 … 1.4.0), dowiązanie `/opt/sklep/sklep.jar`, skrypt
  `/usr/local/bin/wdroz-sklep.sh WERSJA JAR | --wycofaj WERSJA` (restart 8082 → 8080, readiness, retencja 5);
  aktualnie działa 1.3.2 (po ręcznym wycofaniu z 1.4.0). srv-test: openjdk-21-jdk-headless, konto `ci` (/var/lib/ci,
  `~/.m2/settings.xml` z lustrem nexus.firma.example), usługa **`raporty.service`** (konto raporty, /opt/raporty,
  port 8090, sandbox z oceną 3.1, ExecStartPost czekające na readiness, łagodne zatrzymanie 30 s / TimeoutStopSec 45).
  Rozdział `_lx_39_bezpieczenstwo` (9 lekcji) — ZROBIONY (2026-10-08). lubuntu-nauka: `/etc/sudoers.d/deploy` tylko
  `wdroz-sklep.sh` i `systemctl restart sklep@8080/8082`; konto `test` (było z pustym hasłem) i `onowak` (odejście z
  zespołu) zablokowane `usermod -L -e 1`; `libpam-pwquality` (minlen 12, minclass 3), `pam_faillock` (deny 5,
  unlock 900); kuba usunięty z grupy docker (Docker rootless); usunięte avahi-daemon, cups; rpcbind (usługa +
  gniazdo) zamaskowany, NFS tylko v4 (`vers3=n`); Elasticsearch wyłączony; JDWP zdjęty z JAVA_OPTS;
  `~/infra/porty-oczekiwane.txt` + `sprawdz-porty.sh`; SSH lubuntu-nauka: `20-zabezpieczenia.conf` (bez roota i haseł);
  auditd z regułami `50-serwer.rules`; `~/infra/kontrola-bezpieczenstwa.sh` (10 punktów OK/UWAGA), Lynis (64 → 71).
  srv-test: auditd (`/etc/audit/rules.d/50-serwer.rules`: konta, sudoers, ssh_konfig, polecenia_root; `99-zablokuj.rules`
  z `-e 2`), rsyslog `90-centralny.conf` (auth → logi.firma.example), AIDE (`aide.conf.d/90_serwer`, baza kopiowana na
  lubuntu-nauka `~/kopie/aide/`), AppArmor: `local/usr.sbin.mysqld` (+ /srv/kopie/raporty), `secure-file-priv =
  /srv/kopie/raporty` (`91-eksport.cnf`), profil `kopia-mysql` (enforce); timer `kontrola-bezpieczenstwa.timer`
  (7:00, OnFailure=powiadom@); `~/infra/wyjatki.md`. rocky-lab: SELinux enforcing, `httpd_can_network_connect on`,
  fcontext `/srv/www(/.*)?` → httpd_sys_content_t.
  Rozdział `_lx_40_zmienne_srodowiskowe` (7 lekcji) — ZROBIONY (2026-10-08). lubuntu-nauka: zmienne środowiskowe anny
  przeniesione z `~/.bashrc` do **`~/.profile`** (EDITOR=vim, JAVA_HOME, KOPIE_DIR); `~/.bash_profile` wczytujący
  `~/.profile` (+ MAVEN_OPTS=-Xmx2g); `~/.bash_aliases` (mvni, mvnq, gs, gl, ports, dfh, sysfail; funkcje logi, port,
  mkcd, jarinfo, dzis); prompt z kodem błędu i `__git_ps1`; `~/bin/kopia-projektu.sh` (z własnym PATH/JAVA_HOME),
  `~/bin/przejdz.sh`; `/etc/sudoers.d/anna-java` (env_keep JAVA_HOME). srv-test: LANG=pl_PL.UTF-8 (localectl), strefa
  Europe/Warsaw, `/etc/profile.d/historia.sh` (HISTTIMEFORMAT), proxy firmowe proxy.firma.example:3128 w
  `/etc/environment` (+ no_proxy), `/etc/apt/apt.conf.d/95proxy`, `/etc/systemd/system.conf.d/proxy.conf`
  (DefaultEnvironment), JAVA_TOOL_OPTIONS z proxy w `/etc/raporty/raporty.env`. app01/app02: `/etc/srodowisko`
  (PROD/TEST) + `/etc/profile.d/zz-prompt-prod.sh` (kolorowy znacznik).
  Rozdział `_lx_41_skrypty_bash_podstawy` (10 lekcji) — ZROBIONY (2026-10-08). Skrypty anny w `~/bin` na lubuntu-nauka:
  `stan-sklepu.sh`, `sprawdz-instancje.sh`, `archiwizuj-raport.sh` (+ wersja `-zle`), `nowe-konto-testowe.sh`,
  `czysc-baze-testowa.sh [-y]`, `pilnuj-sklepu.sh` (od lekcji 10 na bibliotece), `zrzut-szybki.sh`,
  `kontrola-miejsca.sh`, `stan-serwerow.sh`, `kompresuj-logi.sh`, `czekaj-na-sklep.sh`, `kopia-katalogu.sh [-n] [-v]`,
  `spakuj.sh`, `sklep-ctl.sh {status|restart|logi|wersja} [8080|8082]`, `sprzataj.sh`, `raport-instancji.sh`;
  `~/infra/serwery.txt` (srv-test, srv-docker, deb-lab). Biblioteka **`/usr/local/lib/sklep/funkcje.sh`** (loguj,
  zakoncz, czekaj_na_gotowosc, wersja_sklepu, wymagaj_montowania; log `/var/log/sklep/skrypty.log`). Raporty w
  `/srv/sklep/raporty/` (m.in. „raport wrzesien.csv”, sprzedaz.csv 214 pozycji), archiwum `/srv/kopie/raporty/RRRR-MM/`.
  Baza `sklep_test` (rola `ola` — pg_read_all_data).
  Rozdział `_lx_42_skrypty_bash_zaawansowane` (10 lekcji) — ZROBIONY (2026-10-09). lubuntu-nauka: `~/bin/kopia-katalogu.sh`
  (getopts `":nve:b:h"`), `kody-http.sh`, `kopia-raportow.sh`, `statystyki-kopii.sh`, `uporzadkuj-nazwy.sh`,
  `/usr/local/bin/import-cennika.sh` (trap, flock, mktemp, \copy przez STDIN), `konta-z-listy.sh [-n]`,
  `/etc/sklep/kopia.conf`; ShellCheck 0.9.0, `start-lokalny.sh` z `# shellcheck disable=SC2086`, `PS4` z plikiem/linią/funkcją.
  srv-test: szablon `/usr/local/share/szablony/skrypt-admin.sh` (loguj przez logger, uruchom/-n, flock, kody 0/1/2/3),
  `/usr/local/bin/sprawdz-dyski.sh` (PROG, kod 1), `rotuj-kopie.sh` (ZOSTAW=7, /var/backups/mysql, timer 04:30),
  `raport-nocny.sh` (SLEDZ=1 → /var/tmp/raport-nocny.slad, bez sekretów w śladzie); konto ci: `~/bin/sklep-testowy.sh`
  (start w tle na 18080, gotowość, TERM→KILL po 30 s, trap). srv-docker: `~/sklep-obraz` z `entrypoint.sh` (`exec`,
  tablica opcji, JAVA_OPTS przez read -a), obraz `sklep:1.4.1`, stary `entrypoint-stary.sh` bez exec (kod 137).
  Rozdział `_lx_43_powloki_i_terminale` (8 lekcji) — ZROBIONY (2026-10-09). lubuntu-nauka (konto anna): zsh 5.9 jako
  powłoka logowania (`chsh`), Oh My Zsh (`~/.zshrc.pre-oh-my-zsh`, `custom/anna.zsh` wczytujący ~/.profile przez
  `emulate sh` i ~/.bash_aliases, `custom/aliasy-konflikty.zsh` — unalias gp; gl anny wygrywa), wtyczki git sudo z mvn
  gradle docker systemd zsh-autosuggestions zsh-syntax-highlighting, Powerlevel10k (lean, `~/.p10k.zsh`, java_version
  włączony, próg czasu 5 s; powitanie `stan-sklepu.sh` nad instant prompt), czcionka MesloLGS NF w QTerminalu; root
  z powrotem na bashu. Prompt w lekcjach: `~ > ` / `➜  ~` (przed p10k). Terminator 2.1.3 (układ `lab`: srv-test +
  deb-lab w grupie), Guake 3.10 (autostart, F12, wysokość 45); tmux 3.4 (`~/.tmux.conf`: mysz, historia 100000,
  base-index 1, vi, `|`/`-`, `prefiks r`), `~/bin/tmux-sklep.sh`; port szeregowy ttyUSB0 (grupa dialout).
  srv-test: `~/.tmux.conf` z czerwonym paskiem, alias `srv-test-t` (RemoteCommand tmux new -A -s praca) w
  ~/.ssh/config anny na lubuntu-nauka, `/srv/tmux` (grupa admini), baza `sklep_raporty` (import-2026-10.sql, 12
  tabel), `~/.screenrc`, screen 4.9.1. rocky-lab: EPEL + screen 4.8.
  Rozdział `_lx_44_wsl` (9 lekcji) — ZROBIONY (2026-10-10). laptop-anna (Windows 11 w wersji ANGIELSKIEJ — wyniki
  poleceń Windows po angielsku, bez polskich znaków w kodzie; prompt `PS C:\Users\anna>`, w WSL `anna@laptop-anna:~$`):
  WSL 2.6.1.0, jądro 6.6.87.2-1, WSLg 1.0.66; dystrybucje: **Ubuntu-24.04** (domyślna, systemd domyślnie, przeniesiona
  do `D:\wsl\ubuntu`), **Debian** (skonwertowany z WSL1 do WSL2, systemd włączony ręcznie, PostgreSQL 15,
  `/etc/binfmt.d/WSLInterop.conf`, timer użytkownika `zrzut-sklep`, zadanie Harmonogramu „WSL Debian”),
  AlmaLinux-9, docker-desktop; klon Ubuntu-test usunięty. `/etc/wsl.conf` Ubuntu: `[boot] systemd=true`,
  `[interop] appendWindowsPath=false` (VS Code dopisany do PATH w ~/.profile), `[automount] options=metadata,umask=22,fmask=11`.
  `.wslconfig`: memory=8GB, processors=4, swap=4GB, vmIdleTimeout, autoMemoryReclaim=gradual, **networkingMode=mirrored**;
  reguła zapory Hyper-V `sklep-8080`. W Ubuntu: openjdk-21-jdk (pełne) + maven, visualvm, x11-apps, klucze SSH
  skopiowane do `~/.ssh` (z C:), git `core.autocrlf input` + credential.helper GCM z Windows, projekt w
  `~/projekty/sklep` (`.gitattributes` eol=lf), `~/bin/srodowisko.sh` (wizytówka środowiska). app01: KVM, 2 GB RAM,
  strefa Etc/UTC; srv-test: VirtualBox, 2 CPU, ~4 GB. Docker Desktop na laptopie (obraz eclipse-temurin:21-jre).
  Rozdział `_lx_45_kontenery_i_docker` (8 lekcji) — ZROBIONY (2026-10-10). srv-docker: Docker Engine 28.4 z repozytorium
  download.docker.com, `/etc/docker/daemon.json` (log-driver local 10m×3, live-restore, pula 10.210.0.0/16), ufw (22, 80);
  sieć `sklep-net` (db postgres:16, sklep:1.4.2, web nginx:1.27 na :80; hasła w `~/sekrety/db`), `~/sklep-stack`
  (compose.yaml + nginx/sklep.conf z client_max_body_size 20m, sklep.env z SKLEP_PLATNOSCI_KLUCZ), obraz sklep:1.4.2
  (eclipse-temurin:21-jre, Java 21.0.9), kontener `raporty` (błąd konfiguracji), wolumen `notatki`. laptop-anna (Docker
  Desktop 28.4, integracja z Ubuntu-24.04, kontekst `srv-docker` przez SSH): `~/projekty/sklep/compose.yaml` (db z
  healthcheck, app build ., pgadmin w profilu narzedzia, `.env` w .gitignore), wolumeny `sklep_pgdata`, `pgdata`, `m2`;
  kontener pg-dev na 5433. lubuntu-nauka: Docker rootless dla kuby (UID 1003, subuid 296608, linger).
  Rozdział `_lx_46_projekt_koncowy` (9 lekcji) — ZROBIONY (2026-10-12). Nowy serwer **sklep01** (chmura, KVM, Ubuntu
  24.04, 2 vCPU, 4 GB, 203.0.113.50, sklep.firma.example + www, strefa Etc/UTC, LANG C.UTF-8): konta anna (sudo z hasłem),
  deploy (sudo tylko wdroz-sklep.sh), sklep (systemowe 998), ubuntu zablokowane; SSH `sshd_config.d/10-sklep01.conf`
  (grupa ssh-uzytkownicy, 50-cloud-init.conf usunięty), ufw (limit 22, 80/443), fail2ban; openjdk-21-jre-headless,
  PostgreSQL 16 (rola/baza sklep, `conf.d/90-sklep01.conf` shared_buffers 512MB, max_connections 50), hasło w
  `/etc/sklep/poswiadczenia/spring.datasource.password`; `sklep.service` (MemoryMax=2G, piaskownica, ReadWritePaths
  /var/lib/sklep + /srv/sklep/raporty, ekspozycja 3.4), wydanie 1.5.0; Nginx + certbot (HSTS, strona serwisowa,
  /actuator/ → 404, SERVER_FORWARD_HEADERS_STRATEGY=framework); journald 1G, timery sprawdz-sklep (1 min, OnFailure=
  powiadom@), kopia-bazy (02:30 UTC, pg_dump -Fc, rsync na kopie.firma.example), proba-odtworzenia (nd 04:00);
  `kontrola-sklep01.sh`. Repozytorium `~/infra/sklep01` na laptopie (README, pliki/, instaluj.sh idempotentny,
  kontrola-zewnetrzna.sh, cwiczenie-odtworzenia.sh — RTO 14 min). sklep02 (203.0.113.51) — kopia testowa ze skryptu.
  **KROK 2 (teoria) ZAKOŃCZONY DLA WSZYSTKICH 437 LEKCJI** (2026-10-12). Lekcje 32/07-09 za zgodą użytkownika przeniesione
  z tematów nmap na audyt własnych systemów: `07_OpenPortsAudit` (ss/lsof, `~/infra/porty-oczekiwane.txt`,
  `~/bin/porty-roznice.sh`, JDWP raporty-legacy przeniesiony na 127.0.0.1), `08_ExposedServicesAndBanners`
  (server_tokens off, SERVER_ERROR_INCLUDE_STACKTRACE=never, Actuator: health,info,metrics + port zarządzania
  127.0.0.1:9080), `09_LocalNetworkInventory` (arp-scan, dzierżawy dnsmasq, `~/infra/urzadzenia.csv`, nieznany host
  kali-kuba 192.168.56.117); zmienione slugi w ChapterSeedData i plan w LINUX_COURSE_STAGE_PROMPT.md. Ćwiczenia i quiz — 0 wszędzie (krok 3).
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
- Stan: rozdziały `_lx_01`–`_lx_28` GOTOWE (01/01 30/100, 01-02 reszta 20/60, 03-07, 10-18, 21, 22, 24, 25 i 26 — 30/100, 08-09, 19, 20, 23 — 20/60). Rozdział 27: 7 lekcji, każda 30/100. Rozdział 28: 10 lekcji po 30/100. Rozdział 29: 01_KernelVersionAndUname 20/60; 02_KernelModules 20/60; 03_SysctlParameters 20/60; 04_ProcAndSysInterfaces i 05_HardwareInformation 20/60; następna 06_UdevAndDevices. Pozostałe lekcje 29–46 wymagają praktyki. Rozdział 26 (2026-10-10): journald trwały z /etc/systemd/journald.conf.d/limity.conf (SystemMaxUse=500M, MaxRetentionSec=1month); rsyslog: /etc/rsyslog.d/30-sklep.conf (local0.* → /var/log/sklep/zadania.log, & stop) i 90-centralny.conf (omfwd TCP z kolejką do logi.firma.example); /etc/logrotate.d/sklep (weekly, rotate 8, zstd) i /etc/logrotate.d/raporty (copytruncate, su raporty raporty); kopia-sklep.sh loguje przez logger -t kopia -p local0.info; kuba w grupie adm (journalctl -k bez sudo, dmesg_restrict=1); wpis testowy '<4>test-kursu' w /dev/kmsg; sklep: drop-iny z systemctl edit — LOGGING_LEVEL_PL_FIRMA_SKLEP=INFO, LogRateLimitIntervalSec=30s/LogRateLimitBurst=20000, LOGGING_STRUCTURED_FORMAT_CONSOLE=ecs; logi plikowe Logbacka w /var/log/sklep (aplikacja.log, gc.log z -Xlog filecount=5,filesize=20m). Rozdział 25 (2026-10-10): sklep ma drop-in runtime.conf (RuntimeDirectory=sklep), upload aplikacji /srv/dane/sklep/upload, sesje /srv/dane/sklep/sesje z regułą /etc/tmpfiles.d/sklep-sesje.conf (1d); LUKS: sdd1 → /dev/mapper/sejf → /srv/sejf (crypttab z /root/sejf.key, 3 gniazda, kopia nagłówka /srv/kopie/sejf-naglowek.img); LVM: dane_vg na sdf (8G) i sdg (10G) — aplikacja 4.5G ext4 /srv/aplikacja, bazy 3G xfs /srv/bazy (UUID w fstab), sde wycofany; RAID1 md0 na sdj+sdk (sdh wymieniony) → /srv/raid, mdadm.conf z ARRAY i MAILADDR; NFS: eksport /srv/dane/wspolne 192.168.56.0/24, klient srv-test (/mnt/wspolne automount), Samba udział [wspolne] (valid users @programisci, grupa GID 1003), srv-test /mnt/smb z credentials=/root/.smb-wspolne; sshfs zainstalowany; serwer srv-test (Ubuntu Server, vda, ubuntu-vg powiększony do 37G). Rozdział 24 (2026-10-10): dyski sda 30G (sda1 ESP, sda2 /), sdb 20G (sdb1 ext4 LABEL=dane, /srv/dane, UUID b52e7c90-...), sdc 15G po powiększeniu (sdc1 ext4 LABEL=kopie, UUID e3a91f57-2c4d-4b8e-9a16-7d5c0b2f8e41, PARTLABEL kopie, -m 1); fstab: /srv/kopie z defaults,noatime,nofail,x-systemd.automount,x-systemd.idle-timeout=10min,noexec,nosuid,nodev; /tmp jako tmpfs size=1G,noexec,nosuid,nodev; swap = /swapfile 4G (600), /swap.img usunięty, vm.swappiness=10 w /etc/sysctl.d/90-swappiness.conf; kopia-sklep zapisuje do /srv/kopie/sklep (postgres 750) z drop-inem dysk.conf (RequiresMountsFor=/srv/kopie); kopie tablic w ~/sdb-tablica.txt, ~/sdc-tablica.txt, /root/sdb-gpt.bin; kopia /etc/fstab.2026-10-10. Ćwiczenia z partycjonowaniem/mkfs/fsck robione na urządzeniach loop (obrazy w ~ usuwane). Pendrive testowy sdd1 (exFAT) pojawia się w 24/05-06. Rozdział 23 (2026-10-10): maszyna lubuntu-nauka = VirtualBox UEFI + Secure Boot, GPT: sda1 ESP 1G (UUID 4A1B-7C2D, PARTUUID 3f0d6a2c-...), sda2 ext4 / (UUID 8c1f2d4e-6b7a-4e3d-9f12-5a0c7e9b3d21), NVRAM Boot0004=ubuntu (shimx64.efi), sieć przez NetworkManager (NetworkManager-wait-online w blame), jądra 6.8.0-45/-48, a na końcu 23/07 restart do 6.8.0-49; GRUB: /etc/default/grub.d/90-serwer.cfg (menu, TIMEOUT=3, bez quiet), GRUB_CMDLINE_LINUX="console=tty0 console=ttyS0,115200", 40_custom bez własnych wpisów; /etc/initramfs-tools/modules: nvme; fstab: /srv/dane (UUID b52e7c90-..., dysk odłączony) z nofail,x-systemd.device-timeout=10s; usługa pobierz-flagi (oneshot, drop-in RemainAfterExit=yes); root zablokowany; zainstalowany molly-guard; drugi użytkownik w sesji: kuba. Uwaga: w 22/10 poprawiono /proc/cmdline i czasy startu do tej maszyny. Rozdział 22 (2026-10-10): ćwiczenia sprzątają po sobie (notatki.service, awaria.service, demo-*, sklep.target, sprzatanie-tmp, jednostki systemd-run usunięte); zostają: sklep jako konto systemowe `sklep` (UID 994) z /opt/sklep, /etc/sklep (sklep.env 640), StateDirectory /var/lib/sklep, drop-iny sklepu `baza.conf` (Wants=postgresql) i `powiadom.conf` (OnFailure=powiadom@%n.service) + szablon `powiadom@.service` (logger -t awaria), `ctrl-alt-del.target` zamaskowany, domyślny cel multi-user.target, timer `kopia-sklep.timer` (02:30, Persistent, RandomizedDelaySec=10min; kopie w /var/backups/sklep), Apache disabled (nie zamaskowany), nginx bez drop-inów. Pomocniczy skrypt dopisywania brakujących ćwiczeń/pytań: scratchpad `merge.js` (wstawia #EX przed pierwszym #Q, #Q na koniec) — przy pisaniu dużych plików liczba #Q łatwo wychodzi poniżej 100, zawsze sprawdzaj wynik `lx_build_practice.js`. Rozdział 21 (2026-10-10): aplikacja sklep działa na końcu jako usługa systemd `sklep.service` (Type=simple, User=anna, Restart=on-failure, NRestarts=1); pomocnicze: ~/start-sklep.sh, ~/raport-proces.sh, ~/procesy/praca.sh (trap TERM/INT), /tmp/sklep.pid; konto `raporty` (UID 102) z usługą raporty.service. Rozdział 20 (2026-10-10): ćwiczenia 20/07 sprzątają laboratorium — usunięte /opt/nginx, /opt/hello*, stow, checkinstall, cmake, ~/zrodla, libpowitanie z /usr/local/lib, deb-src wyłączone; zostały ~/NOTATKI.md, ~/BUILD-nginx.txt, ~/kompilacja (powitanie, czesc, czesc-dyn, czesc-rpath + libpowitanie.so*), build-essential, libssl-dev, pkg-config, zlib1g-dev, apache2-dev, Apache 2.4.58 (MPM event, headers, proxy/proxy_http -> /api na 8080, PHP 8.3 przez FPM). Rozdział 19 (2026-10-10): ćwiczenia na osobnych maszynach/kontenerach (Rocky 9.6 z EPEL i dnf-automatic, alpine:3.20, archlinux z yay-bin), w lxlab: snap yq, flatpak (Flathub --user, DBeaver usunięty), SDKMAN anny: java 21.0.8-tem (default) i 17.0.16-tem, maven 3.9.11 i 3.6.3, auto_env=true; usługa sklep używa /usr/lib/jvm/java-21-openjdk-amd64/bin/java. Rozdział 18 (2026-10-10): ćwiczenia zakładają stan laboratorium z teorii i na końcu sprzątają po sobie — zostają: repozytorium Adoptium (`adoptium.sources` + klucz, przypięcie `preferences.d/adoptium` 100/500 dla temurin-*), przypięcie `postgresql-16` (Pin version 16.*, 1001; klient zaktualizowany do 16.10), `apache2-utils` ręczny, `python3-venv` USUNIĘTY (venv `~/projekt-venv` zostaje), `52unattended-upgrades-local` (blacklist openjdk-21/temurin, Remove-Unused-Kernel-Packages, bez restartu), `90lock-timeout` (600 s); PPA git-core, repozytorium firmowe i lokalne usunięte. Liczby z ćwiczeń: 487 pakietów (48 ręcznych/439 auto), vim 7.8 z fazowaną 7.9, openssl/libssl3t64 3.0.13-0ubuntu3.6 → automat w 18/11 zainstalował 3.7; VM lubuntu-nauka: jądra 6.8.0-45 i -48 (41 usunięte). Skrypt pomocniczy budowania+commitu: przy dopisywaniu do pliku .txt heredoc w Bash potrafi się wyłożyć na apostrofach — dopiski pisać Write do scratchpada i `cat >>`. Rozdział 16 (2026-10-09): ćwiczenia zakładają konta tymczasowe (ewa, adam, tomek, staz, dyzurny...) i na końcu je usuwają, żeby nie zmieniać stanu laboratorium z teorii; po rozdziale w bazie 32 konta (doszli kuba 1003, onowak 1004, faktury 995, deploy 1005), w sudoers.d: anna, deploy (restart sklep), wdrozenia (journalctl -u sklep -n 50). Rozdział 15 (2026-10-09): sklep-api po restarcie ma produkty 1 laptop 4200.0 i 2 mysz 80.0 (kolejne id rosną: 3 monitor, 4 kabel, 5 przejsciowka); skróty plików ćwiczebnych z ~/pobrane/sumy policzone lokalnie (sha256sum w Git Bash wypisuje `*` przed nazwą — na Linuksie dwie spacje). Rozdział 14: wyniki gzip/bzip2 dla licencja.txt (GPL-3, 35149 B) policzone lokalnie (gzip -N: 1→14234 … 6→12143, 9→12137; bzip2 10706; xz 11436); tar 1.35 lokalnie w Git Bash = jak w lab. Dane ~/awk zrekonstruowane z teorii rozdziału 13: sprzedaz.csv 8 transakcji (suma 15005; Krakow 4200/80/1100, Warszawa 900/4500, Gdansk 3900/75, Poznan 250), aplikacje.txt (sklep-api 1.4.2 52, sklep-web 2.0.1 18, raporty 0.9.7 7, platnosci 3.1.0 33), app.log 120 linii (12 ERROR, 12 WARN, minuty 10:00/10:01/10:02 = 59/60/1), wyjatek.log 14 linii. Test `LessonContentFilesTest`: `JAVA_HOME=~/.jdks/openjdk-25.0.2 ./mvnw -q test -Dtest=LessonContentFilesTest`. Fakty laboratorium ~/sed: app.log ma 12 linii (1 ERROR o 10:00:07), nginx.conf — proxy_pass w linii 6, kopia sshd_config — #Port 22 w linii 23, #PermitRootLogin w 42, #PasswordAuthentication w 66, Include w 12; UID: ubuntu 1000, anna 1001, piotr 1002. Od 2026-10-09: wyniki przykładów samowystarczalnych (`<<<`, `printf`) sprawdzane lokalnie w Git Bash (GNU grep 3.0; uwaga: grep z Git for Windows ignoruje `
` we wzorcach z `-f` — na Linuksie nie); licencja GPL-3 identyczna z laboratoryjną jest w `C:/Program Files/Git/mingw64/share/licenses/xz/COPYING.GPLv3` (674 linie), a ripgrep 15 z VS Code w `.../Microsoft VS Code/*/resources/app/node_modules/@github/copilot-win32-x64/ripgrep/bin/win32-x64/rg.exe` (w Git Bash `--path-separator //`). Dane ~/szukaj: 28 plików (kopie 8 plików + 2 dowiązania, .tmp i kopie bazy w backup mają 0 B, pliki .java po 1 linii).
  Źródła w `scripts/content-migration/lx_practice/_lx_RR_LL.txt`.
- **DECYZJA UŻYTKOWNIKA (2026-10-12): dwa poziomy, przypisane per rozdział** (bez trzeciego poziomu; „zależy mi na
  solidnym materiale, nawet do nauki”):
  - **PEŁNY = 30 ćwiczeń / 100 pytań** — rozdziały 03-07, 10-18, 21, 22, 24-28, 30-42, 45.
  - **NIEPEŁNY = 20 ćwiczeń / 60 pytań** — rozdziały 01 (wprowadzenie), 02 (wirtualizacja), 08-09 (nano/Vim),
    19 (pakiety innych dystrybucji), 20 (kompilacja ze źródeł), 23 (start systemu), 29 (jądro i sprzęt),
    43 (powłoki i terminale), 44 (WSL), 46 (projekt końcowy — synteza wcześniejszych rozdziałów).
  - Wyjątek: `_lx_01/01_WhatIsLinux` zostaje 30/100 (pilotaż już gotowy).
  - Budowanie: `node scripts/content-migration/lx_build_practice.js PLIK.txt LEKCJA.json 20 60` dla poziomu niepełnego
    (skrypt przyjmuje oczekiwane liczby jako 3. i 4. argument; domyślnie 30/100).
- **Do przeglądu (znalezione 2026-10-09):** ćwiczenia `_lx_07_07` i `_lx_11_05` podają statystyki `access.log` niezgodne z prawdziwym plikiem (np. 41×192.168.56.20, 18×/api/produkty, adres 10.0.0.7). Prawdziwe dane (teoria 13/10): 60 żądań; adresy 17×203.0.113.7, 17×192.168.1.10, 9×192.168.1.11, 9×10.0.0.5, 8×198.51.100.23; kody 200:27, 201:7, 302:7, 403:6, 404:7, 500:6; ścieżki bez parametrów po 8×/api/zamowienia, /api/produkty/1, /api/produkty.
- Lekcje 32/07-09: przepisane na audyt własnych systemów (decyzja użytkownika 2026-10-12: „rób wszystko”).

## Następny krok

**Krok 3: od _lx_42_skrypty_bash_zaawansowane/01_ArraysAndAssociativeArrays.json (zawsze sprawdź audit_lx_practice.cjs: next) do końca rozdziału 46.** Poziomy 30/100 i 20/60 są już uzgodnione. Pracować bez pytań o zgodę, bez raportów między rozdziałami, lokalne commity po polsku, bez push i stopki współautorstwa. Lekcje 32/07–09 obejmują audyt własnych systemów zgodnie z istniejącą decyzją użytkownika.

## Kontynuacja praktyki — 2026-10-11

- Rozdział 27: 7 × 30 ćwiczeń i 100 pytań; commit `0c91941`. Rozdział 28: 10 × 30/100. Teoria wszystkich tych lekcji porównana z HEAD, bez zmian. Źródła TXT i generatory zapisane w scripts/content-migration.
- 27 sprawdzono w Ubuntu 24.04 (lxlab): run-parts, minimalne env, dash/Bash, pipefail, flock, sześć form czasu at z anulowaniem, parser 30 kalendarzy systemd, rsync --link-dest i odtworzenie, pg_dump -Fc i rzeczywiste pg_restore z kontrolą 2/350. Skrypt verify_lx_27_lab.py. Kontener nie ma systemd jako PID 1: uruchamianie timerów/usług nie było testowane w działającym managerze.
- Wszystkie 300 rozwiązań rozdziału 28 wykonano w lxlab. Skrypty: verify_lx_28_01.py, verify_lx_28_reads.py (02–08), verify_lx_28_jvm.py (09), verify_lx_28_sar.py (10). Skopiować JSON-y do /tmp/javaquest-28-XX.json przed uruchomieniem. Dodatkowe asercje: rachunki, deleted FD, hardlinki 2→1, strace ENOENT/prawa 600, MemoryError przez RLIMIT_AS, EMFILE własnego procesu, NMT, stos własnego wątku, prywatny heap dump i JFR, prawdziwy plik sadc i eksporty CSV/JSON. Nie wywoływano OOM hosta ani benchmarków obciążających produkcję.
- Doinstalowane w lxlab: at, rsync, PostgreSQL 16, sysstat 12.6.1, strace 6.8, openjdk-21-jdk-headless. Tymczasowe bazy oraz laboratoria plików/JVM/sar posprzątane. PostgreSQL uruchomiony przez pg_ctlcluster; nie testowano systemd.
- LessonContentFilesTest: 2 testy, 0 błędów. Backend 8082 na osobnej bazie javaquest-linux-validation-db (127.0.0.1:55432): autoryzowane API wszystkich 10 lekcji 28 potwierdziło 30 ćwiczeń i 100 pytań. Regresja API Java 01/00 i JavaScript 01/01: teoria, ćwiczenia i stan quizu OK. Baza validation nie jest bazą użytkownika.
- 29/01 KernelVersionAndUname: 20/60, wszystkie rozwiązania wykonane, asercje ABI i sortowania wersji. Źródło _lx_29_01.txt, generator write_lx_29_01.cjs, verifier verify_lx_29_01.py. Ta lekcja jest początkiem kolejnego rozdziału; wymaga odświeżenia zasobów i importu przy jego weryfikacji API.
- **Następna brakująca lekcja: _lx_39_bezpieczenstwo/04_ListeningServicesAudit.json**. Pełne uzgodnione liczby praktyki ma 370/437 lekcji; 67 nadal niekompletnych. API 29–37 i regresja Java/JavaScript potwierdzone; API 38+ oraz pełne usługi/VM pozostają do sprawdzenia. Cały kurs NIE jest ukończony.
- Obowiązuje polecenie użytkownika: do końca, bez pytań o zgodę i bez raportów między rozdziałami. Heartbeat doko-czenie-kursu-linux-javaquest ACTIVE co 30 minut, powiadomienia failed_runs_only. Sprawdzać faktyczny stan JSON-ów przed kolejną lekcją. Nie wyłączać kontynuacji, dopóki nie ma kompletnej praktyki i weryfikacji.
- Zachować niezwiązane zmiany użytkownika w application.properties, a/ i part.bin; nie commitować ich z treścią kursu. Najnowsze polecenie użytkownika: częste lokalne commity między lekcjami (każda ukończona lekcja osobno), po polsku, bez push i stopki współautorstwa.
- Po weryfikacji zatrzymano własny backend testowy i kontener javaquest-linux-validation-db. Kontener zachowany do kolejnych kontroli; można go uruchomić ponownie. lxlab pozostaje dostępny. Do walidacji nie używać bazy innego projektu zajmującej port 5432.
- 29/02 KernelModules: 20/60, wszystkie 20 rozwiązań przetestowane przez verify_lx_29_02.py; parser kmod 31, procfs i plik próbny. Teoria bez zmian, LessonContentFilesTest przechodzi. W lxlab brak /lib/modules; nie wykonywano load/unload współdzielonego jądra ani aktywacji modules-load. Doinstalowano kmod. 29/01 zapisano osobnym commitem 9f7fec1, zgodnie z poleceniem częstych commitów między lekcjami.
- 29/03 SysctlParameters: 20/60; wszystkie 20 rozwiązań przeszło verify_lx_29_03.py w zastępczym Ubuntu 24.04 (procps 4.0.4) przez chroot WSL --system -u root. Asercje odczytów, dry-run, modelu nadpisania i backlogu oraz niezmienionych wartości runtime. Teoria bez zmian, LessonContentFilesTest przechodzi. Nie testowano zapisów sysctl ani trwałości po reboot. API rozdziału 29 nadal do sprawdzenia.
- Docker Desktop obecnie nie startuje: stary socket %LOCALAPPDATA%/Docker/run/dockerInference (błąd Inference manager). Move-Item nie powiódł się; usuwanie socketu zostało odrzucone przez automatyczny przegląd. Nie omijać odmowy. Zastępcze Ubuntu jest w /run/javaquest-validation-root w pamięci WSL --system; /proc i /sys zamontowane ro, /dev osobny tmpfs z null/zero/random/urandom. Nie ma systemd. Po restarcie WSL katalog zniknie. Źródłowy ubuntu-base w %TEMP%/javaquest-ubuntu-base.tar.gz. Próba ekstrakcji do małej dystrybucji Docker zapełniła jej dysk; usunięto własne /tmp/javaquest-validation-root i /tmp/javaquest-ubuntu-base.tar.gz przez WSL --system -u root, przywrócono 54 MB wolnego. Nie dotknięto obrazów ani wolumenów Docker.
- Ponowny audyt faktycznych plików: istnieją także commity 63d9430 (29/03), 9dce7c0 (29/04) i bf6b140 (29/05); 279/437 lekcji ma praktykę, następna 29/06. Weryfikacja 29/04–05 wymaga jeszcze sprawdzenia zapisanych verifierów i wyników. Katalog /run zastępczego WSL --system zniknął między kolejnymi wywołaniami; nie zakładać jego trwałości. Instalację środowiska i test najlepiej uruchamiać razem albo użyć osobnego trwałego laboratorium.
- 2026-10-12: rozdział 29 gotowy — 29/03-06 po 20/60 (sysctl, /proc i /sys, sprzęt, udev; pendrive w 29/06 to sdl, reguła 99-kopia-usb.rules + kopia-na-usb.service, grupa kopie). LessonContentFilesTest OK. Bez weryfikacji w kontenerze (wyniki przykładowe).
- 2026-10-12: poprawiono niespójności access.log w 07/07 i 11/05 (źródła TXT i JSON). Usunięto wymyślone wyniki 41/27/12, 18/11/8, niepotwierdzony rozkład 500/404; polecenia zwracają rzeczywisty wynik pliku. Klient 10.0.0.5 dopasowany literalnie i z granicą pola. Ranking URL usuwa query string, pytanie o czołówkę zgodne z raportem 13/10: trzy ścieżki po 8. Test verify_lx_access_log_filters.sh na jawnym osobnym fixture potwierdził kod HTTP (nie rozmiar 500), dokładny IP (nie 10.0.0.50), normalizację URL, uniq i rankingi. Nie odtworzono pełnego pierwotnego logu; nie deklarować takiej weryfikacji. Obie lekcje nadal 30/100, teoria bez zmian, LessonContentFilesTest przechodzi.

- Aktualizacja audytu: pierwotne niespójności access.log naprawione w 0f98359. Starsze wzmianki o konieczności ich naprawy są nieaktualne. Kontynuować wszystkie brakujące lekcje, nie wyłączać heartbeat. Bez push.
- 2026-10-12: poprawka jakości 30/04: porównanie adresów nie uznaje różnicy za samodzielny dowód NAT; waliduje odpowiedź IPv4 i uwzględnia VPN/proxy. Klasyfikator adresów odróżnia globalny unicast, multicast i zakresy specjalne, więc 198.51.100.77 i 127.0.0.1 nie trafiają do publicznych. verify_lx_30_address_classification.py wykonał rzeczywisty kod Python rozwiązań (Python 3.13 Windows), sprawdził granice RFC1918/CGNAT, adresy specjalne, multicast, zgodność i różnicę oraz błędną odpowiedź. Nie testowano zdalnej usługi ani sieci VM; teoria bez zmian, 30/100 i LessonContentFilesTest OK.
- 2026-10-12: verify_lx_30_subnet_calculations.py wykonał samowystarczalne fragmenty Python z 12 ćwiczeń 30/03. Potwierdzone maski /20–30, podział /24 na /26, przynależność hostów, poprawna agregacja /23 i brak agregacji niewyrównanej pary, overlap Docker/VPN oraz oba hosty /31. Nie wykonano ipcalc, zmian tras/firewalla/PostgreSQL ani ćwiczeń wymagających VM i oryginalnych logów.
- 2026-10-12: rozdział 30 gotowy — 10 lekcji po 30/100 (warstwy, IPv4, CIDR, NAT, routing, DNS, porty, stany TCP, IPv6, sieci VM). Ćwiczenia sprzątają po sobie (dummy0, tabele nft *-kurs, adresy tymczasowe); w 30/10 maszyna testowa srv-docker (NAT + host-only 192.168.56.30). Skrypt scratchpad dropq.js usuwa nadmiarowe pytania po prefiksie. LessonContentFilesTest OK.
- 2026-10-12: rozdział 31 gotowy — 9 lekcji po 30/100 (ip, net-tools, hostname i /etc/hosts, resolver, Netplan, nmcli, adres statyczny, ping/traceroute, dig). Stan laboratorium w ćwiczeniach: srv-test = Netplan statyczny 192.168.56.20 + dnsmasq (DNS lab.local, DHCP .100-.200, rezerwacja srv-docker .30), lubuntu-nauka = nmcli profil hostonly (DNS 192.168.56.20, lab.local). LessonContentFilesTest OK.
- 2026-10-12: dodano audit_lx_practice.cjs dla wszystkich 437 lekcji. Sprawdza przypisane poziomy 30/100 lub 20/60, jednoznaczne ABCD, identyczne pytania/opcje (z zachowaniem wielkości liter istotnej dla flag), rozkład A–D, pola ćwiczeń, polskie znaki w solution oraz obecność i liczby źródłowych TXT. Audyt nie znalazł błędów w istniejącej praktyce; braki raportuje osobno. Opcja --require-complete musi przejść przed wyłączeniem automatyzacji. Nie dowodzi poprawności merytorycznej ani wykonania poleceń.
- 2026-10-12: verify_lx_30_ipv6_calculations.py wykonał obliczenia z rozwiązań 30/09 (Python 3.13 i Git Bash Windows): rozwinięcie/skrócenie IPv6, klasyfikacja loopback/link-local/ULA/documentation, 2^64 adresów i 65536 podsieci, modified EUI-64 fe80::a00:27ff:fe91:4d07. Nie wykonano prób łączności, konfiguracji interfejsów ani IPv6 usług VM. LessonContentFilesTest OK.
- 2026-10-12: rozdział 32 gotowy — 12 lekcji po 30/100 (netstat, -plnt, potoki, ss, lsof -i, nmap, audyt portów, banery, inwentaryzacja, legalność i Zenmap, netcat, tcpdump). Skany i przechwytywanie w ćwiczeniach wyłącznie w laboratorium 192.168.56.0/24 i scanme.nmap.org. Pliki pomocnicze w ćwiczeniach: ~/infra (porty-oczekiwane.txt, urzadzenia.csv), ~/skany (zakres, wykluczenia, zgoda-szablon). LessonContentFilesTest OK.
- 2026-10-12: verify_lx_32_loopback.sh wykonany w docker-desktop WSL (BusyBox 1.36.1): własny nasłuch TCP 127.0.0.1:55989, przesłany payload, zniknięcie LISTEN i odmowa dla zamkniętego portu; własne UDP 127.0.0.1:55990 z potwierdzeniem treści u odbiorcy. Własne procesy/pliki usunięte. Nie weryfikuje VM, OpenBSD nc, ss, nmap ani tcpdump. Dystrybucja Docker ma 53.7 MB wolnego; silnika Docker nie uruchamiano.
- 2026-10-12: poprawiono 32/12: pliki przechwyceń wyłącznie w /var/tmp/kurs-tcpdump (katalog anna:tcpdump 0770), zatrzymywanie tylko własnych zadań zapisujących do niego i sygnałem INT. Usunięto szerokie pkill tcpdump i rm /var/tmp/*.pcap*. verify_lx_tcpdump_scope.py sprawdził rzeczywisty selektor przez grep ERE: rozróżnia zadania kursu, cudze przechwycenia, odczyt pliku i wrappery sudo/sh. Nie uruchamiano tcpdump, rotacji ani kasowania pcap na VM. Teoria bez zmian, 30/100, LessonContentFilesTest OK.
- 2026-10-12: rozdział 33 gotowy — 12 lekcji po 30/100 (SSH: wprowadzenie, instalacja, kryptografia klucza publicznego, ssh-keygen, logowanie kluczem, ssh-agent, aliasy ~/.ssh/config, sshd_config i port 2222, zabezpieczenia, tunele -L/-R/-D, ProxyJump/bastion, klucze dla Gita i GitHuba, deploy keys, podpisy commitów). Ćwiczenia na lubuntu-nauka/srv-test/srv-docker; zmiany sshd w drop-inach z sesją zapasową, testy sprzątane. LessonContentFilesTest OK.
- 2026-10-12: verify_lx_33_openssh_local.sh przeszedł na Git Bash/OpenSSH 9.7p1 Windows: Ed25519, zgodność odcisków private/public, odtworzenie public key (porównane typ+blob bez komentarza), szyfrowany klucz i zmiana hasła, osobny ssh-agent/add/list/delete, parser ssh -G z aliasem/portem/ProxyJump/keepalive, podpis i weryfikacja oraz odrzucenie złej namespace i zmienionej treści. Tymczasowe klucze, agent i pliki posprzątane; nie zmieniono kluczy/config użytkownika. Nie łączono się z hostami/GitHubem, nie dodano kluczy do kont, nie testowano sshd i tuneli ani uprawnień POSIX w VM.
- 2026-10-12: rozdział 34 gotowy — 12 lekcji po 30/100 (scp, sftp z kontem chroot, rsync: quick check, algorytm różnicowy, -a i --itemize-changes, ukośniki, przez SSH z --rsync-path i rrsync, lustro z --delete i bezpiecznikami, wykluczenia, uprawnienia/--partial/--bwlimit, migawki --link-dest i kody wyjścia, python3 -m http.server i jwebserver). Ćwiczenia z --delete na katalogach testowych w /tmp; zapora tylko dla 192.168.56.0/24. LessonContentFilesTest OK.
- 2026-10-12: verify_lx_34_rsync_local.py dwukrotnie wykonany na rzeczywistym rsync 3.2.7 Ubuntu 24.04 w osobnym chroot tmpfs /run (run_lx_rsync_isolated_lab.sh). Potwierdzone: ukośnik źródła, delete dry-run i rzeczywiste usunięcie, exclude oraz ochrona plików odbiorcy/--delete-excluded, quick check pomijający zmienioną treść przy tym samym rozmiarze/mtime i naprawa przez -c, hardlinki dwóch migawek, oddzielny inode pliku zmienionego, zachowanie poprzedniej migawki, kod 23, częściowe usunięcie z --max-delete=1 i kod 25. Laboratoria chroot/pliki/mounty usunięte; nie zmieniano konfiguracji hosta. Nie testowano transferów SSH/SFTP ani całej sekwencji 30 ćwiczeń każdej lekcji.
- 2026-10-12: w 34/08 MirrorWithDelete poprawiono pytanie o --max-delete: limit nie daje transakcji/rollback, kod 25 może wystąpić po częściowym usunięciu. Przypadek potwierdzono rzeczywistym rsync: 3 zbędne pliki, limit 1, pozostały 2. Źródło TXT i JSON zgodne, teoria bez zmian, nadal 30/100, LessonContentFilesTest OK.
- 2026-10-12: rozdział 35 gotowy — 6 lekcji po 30/100 (pojęcia zapory: netfilter, łańcuchy, DROP/REJECT, conntrack; ufw z limit i pułapką Dockera; nftables i iptables na deb-lab 192.168.56.40; firewalld na rocky-lab 192.168.56.50 z SELinux; zapora serwera aplikacji z inwentaryzacją i zaporą jako kodem; fail2ban z własnym filtrem i X-Forwarded-For). LessonContentFilesTest OK.
- 2026-10-12: rozdział 36 gotowy — 9 lekcji po 30/100 (Apache: instalacja na srv-test, struktura available/enabled i hartowanie, hosty wirtualne z hostem domyślnym, DirectoryIndex i FallbackResource, PHP ze źródeł z apxs2 i prefork; Nginx: podstawy i location, reverse proxy dla Spring Boot z nagłówkami i upstream, HTTPS z certbot na app01, logi i diagnoza 403/404/413/500/502/504). LessonContentFilesTest OK.
- 2026-10-12: verify_lx_36_nginx_local.py przeszedł na rzeczywistym Nginx 1.24.0 Ubuntu 24.04 i własnym backendzie Python przez loopback. Potwierdzone nginx -t, HTTP 200 dla index/asset, 404, exact location, ^~ przed regex, regex, proxy_pass bez URI zachowujący ścieżkę/query i z ukośnikiem usuwający prefiks, Host/X-Forwarded-Proto/X-Forwarded-For, 403 dla zablokowanej ścieżki, 413 dla żądania ponad limit i wpisy access.log. Pierwsza próba ujawniła brak traverse w katalogu chroot; runner poprawiony na 0755 i ponowiony test przeszedł. To osobny model konfiguracji; nie test całej aplikacji sklep, TLS, certbota, Apache, systemd ani VM.
- Runner run_lx_rsync_isolated_lab.sh przyjmuje profil rsync (domyślny) lub nginx; instalacja i cały test w jednym wywołaniu WSL, chroot w tmpfs /run, policy-rc.d blokuje automatyczny start usług. Po rozszerzeniu runnera ponownie wykonano profil rsync: wszystkie asercje OK. Własne backendy, procesy Nginx, katalogi i mounty sprzątnięte; find /run nie znalazł pozostałych javaquest-rsync.*. Nie zmieniano konfiguracji sieci ani usług hosta.
- 2026-10-12: rozdział 37 gotowy — 8 lekcji po 30/100 (MySQL: instalacja, katalog danych, kopie mysqldump, phpMyAdmin; PostgreSQL: instalacja i klastry, role i pg_hba.conf, kopie pg_dump/pg_restore/pg_basebackup, przegląd bezpieczeństwa serwera bazy). LessonContentFilesTest OK.
- 2026-10-12: verify_lx_37_postgresql_local.py przeszedł na rzeczywistym PostgreSQL 16.15 Ubuntu 24.04 w osobnym klastrze, przez prywatny Unix socket (listen_addresses puste, bez TCP). Potwierdzone SELECT roli read-only, odmowa INSERT, rollback, pg_dump -Fc i pg_restore --list oraz pełne odtworzenie 2 rekordów/sumy 20.00, plain SQL dump i psql --single-transaction, pg_hba_file_rules wykrywający własną błędną regułę i brak błędu po przywróceniu. Klaster zatrzymany i własne pliki usunięte. Runner ma profil postgresql; nie używa baz użytkownika. Nie testowano MySQL, phpMyAdmin, pg_basebackup, systemd ani pełnej konfiguracji sieci bazy.
- Docker ponownie dostępny. Uruchomiono wyłącznie javaquest-linux-validation-db (127.0.0.1:55432) oraz własny backend walidacyjny na 18082 (PID 27460, JavaQuestApplication, -Xmx512m). Backend importuje treść; regresja API 29–37 i Java/JavaScript jeszcze w toku, nie deklarować wyniku. Nie dotykać javaquest-postgres ani innych kontenerów użytkownika. Po teście zatrzymać własny backend i validation-db.
- 2026-10-12: verify_lx_practice_api.ps1 przeszedł na osobnej bazie validation i backendzie 18082: wszystkie 84 lekcje Linux 29–37. Porównano pełne pola theory oraz prompt/hint/solution ćwiczeń z bieżącymi JSON, liczby pytań, drawSize=20 i passPercent=80, listy lekcji. Regresja pierwszej lekcji Java i JavaScript również zgodna z plikami. Logowanie wyłącznie istniejącym kontem linux-validation@example.invalid; token niewypisywany, bez rejestracji/e-maili i prób quizów. Backend importował 143 s, -Xmx512m. Nie sprawdzano tu API lekcji 38+.
- Po API zatrzymano własny PID 27460 po weryfikacji main class i --server.port=18082 oraz wyłącznie javaquest-linux-validation-db. Baza zachowana na kolejne testy. javaquest-postgres i kontenery użytkownika pozostawione. Docker jest dostępny; stare wzmianki o braku silnika są historyczne.

- Audyt poprawiony: lekcja z czesciowa praktyka trafia do partial i next, nie do ready. W trakcie pracy 38/08 miala 30/80; audyt prawidlowo wskazal ja przed pusta 38/09. Kontynuowac od rzeczywistego pierwszego braku, wlacznie z niepelna liczba pytan. --require-complete nadal wymaga braku bledow i wszystkich lekcji.
- 2026-10-12: dokończono 38/08 ApplicationConfigAndSecrets z 30/80 do 30/100, zachowując istniejące 80 pytań, 30 ćwiczeń i teorię. Dopisano 20 scenariuszy: priorytety formatów/profili/lokalizacji, optional bez walidacji, prawa katalogów i umask, kopie sekretów/historia Git, tokeny w curl/logach, odtwarzanie kluczy szyfrujących i rozróżnienie dostarczenia sekretu od poprawnej autoryzacji. Źródło complete_lx_38_08.cjs wymaga początkowego 80 i nie nadpisuje innego stanu.
- ConfigFixtureProbe.java + verify_lx_38_config.ps1 przeszły na rzeczywistym Spring Boot 3.4.4 projektu/JDK 25 Windows: properties przed YAML, odczyt fikcyjnego sekretu przez configtree, kolejność profili prod,monitoring, argument CLI przed plikami i optional przy braku katalogu. Osobne pliki tymczasowe sprzątnięte, bez usług sklepu/systemd i sekretów użytkownika. Maven classpath przekazywany przez Java @argfile (limit długości Windows). LessonContentFilesTest OK, audit_lx_practice bez błędów. Nie wykonano pełnej sekwencji 30 ćwiczeń na usługach VM; API 38/08 jeszcze do sprawdzenia. Dokumentacja pomocnicza: https://docs.spring.io/spring-boot/reference/features/external-config.html (bieżąca wersja 4.1.1; reguły użyte tutaj sprawdzone również na wersji projektu).

- Przy pisaniu 38/09: istniejaca teoria nazywa ln -sfn atomowym przelaczeniem; zwykle zastapienie takiego symlinka obejmuje unlink/create. W praktyce uzyc nowego symlinka i mv -Tf w tym samym systemie plikow. Teorii nie zmieniano zgodnie z poleceniem zachowania theory; niescislosc pozostaje do przegladu przed zamknieciem kursu.
- 2026-10-12: 38/09 DeploymentAndRollback: 30/100, teoria zachowana. Źródła write_lx_38_09.cjs + write_lx_38_09_cases.cjs tworzą 50 pytań pojęciowych i 50 konkretnych scenariuszy bez powtarzania identycznych pytań. W praktyce użyto nowego symlinka i mv -Tf w tym samym katalogu zamiast przypisywania atomowości ln -sfn. Nowy materiał rozróżnia rollback artefaktu, konfiguracji i danych, status HTTP vs readiness, wersję per instancja, retencję i reakcję na nieudane etapy.
- verify_lx_38_09.py wykonał wszystkie 30 rozwiązań w jednej sesji Bash lxlab: zbudowano i uruchomiono prawdziwe jary Java 1.0/1.1, sprawdzono sumy, wykryto uszkodzoną kopię, przełączono i wycofano symlink, uruchomiono własny HTTP na losowym porcie loopback, potwierdzono odrzucenie DOWN mimo HTTP200 i błędnego JSON, blokadę zmiany modelu primary przy niegotowym standby, propozycję retencji i sprzątnięcie własnego procesu/katalogu. To model kolejności i endpointów plikowych, nie rzeczywisty systemd/Actuator/Nginx failover ani test migracji. LessonContentFilesTest OK, globalny audyt bez błędów.

- 2026-10-12: 39/01 SecurityPrinciples: 30 ćwiczeń i 100 autorskich pytań, rozkład 25/25/25/25, teoria zachowana. verify_lx_39_01.py wykonał wszystkie 30 rozwiązań w lxlab: prawa root/anna/nobody, odmowy odczytu/zapisu, grupowy odczyt bez zapisu, umask, traverse bez listowania, sticky bit i osobna ochrona treści, wykrycie zmiany SHA256 i odtworzenie. Własny katalog tymczasowy usunięty. systemd-analyze verify potwierdził składnię własnego unit, nie działanie sandboxa usług. Nie zmieniano kont, usług ani zapory hosta. Globalny audyt bez błędów; API 38+ pozostaje do sprawdzenia.
- LessonContentFilesTest po 39/01: 2 testy, 0 błędów i 0 niepowodzeń.

- 2026-10-12: 39/02 UpdatesAndPatching: 30/100, teoria zachowana, ABCD po 25. Wszystkie 30 rozwiązań wykonano przez verify_lx_39_02.py w lxlab: odczyty APT/dpkg, symulacja upgrade, porównania rewizji/epoki/tyldy, model biuletynu i konfiguracji automatu. Rzeczywisty własny proces Python mapował stary plik; po rename potwierdzono deleted w /proc/PID/maps, zakończono go przez FIFO i nowy proces odczytał nowy plik. Własne pliki i proces posprzątane. Nie instalowano poprawek, nie restartowano usług ani jądra; nie sprawdzano działania unattended-upgrades/Livepatch/systemd na VM. Źródła zasad automatu: https://ubuntu.com/server/docs/how-to/software/automatic-updates/ i https://documentation.ubuntu.com/security/security-updates/. API 38+ nadal do sprawdzenia.
- LessonContentFilesTest po końcowym TXT/JSON 39/02: 2 testy, 0 błędów; audyt 369/437 z pełnymi liczbami, pierwszy brak 39/03.

- 2026-10-12: 39/03 AccountAndPasswordSecurity: 30/100, zachowana teoria, ABCD po 25. verify_lx_39_03.py wykonał wszystkie 30 rozwiązań w lxlab: modele passwd/shadow bez prawdziwych hashy, wykrywanie UID0/pustego hasła/blokady/pól, utworzenie własnego unikatowego konta z nologin, passwd -S, rzeczywiste chage i usermod -L/-e, dodatkowa grupa i odebranie jej. Konto i grupy usunięte, wykazano osierocony numeryczny UID własnego katalogu i posprzątano go. Nie zmieniano żadnego istniejącego konta. Nie testowano uwierzytelniania PAM/SSH, egzekwowania pwquality ani faillock; pliki polityk to jawne modele. Źródła: https://man7.org/linux/man-pages/man8/usermod.8.html , https://man7.org/linux/man-pages/man1/passwd.1.html , https://man7.org/linux/man-pages/man1/chage.1.html . Globalny audyt bez błędów; 370/437 ma pełne liczby praktyki, pierwszy brak 39/04.
- LessonContentFilesTest po 39/03: 2 testy, 0 błędów. W praktyce rozróżniono adm (zwykle logi) od grup mogących dawać szeroką kontrolę hosta; istniejąca teoria zestawia je z grupami równoważnymi root, pozostawiono ją zgodnie z poleceniem zachowania theory. Przegląd tej precyzji pozostaje przed zamknięciem kursu.
- 2026-10-12: regresja API 38–39 przeszła na własnym backendzie 18082 i bazie validation: 18 lekcji, pełne porównanie theory i ćwiczeń oraz liczby pytań. Obejmuje 9 gotowych lekcji rozdziału 38, 3 gotowe 39/01–03 oraz 6 nadal pustych praktyk 39/04–09; zgodność pustych pól NIE oznacza ukończenia lekcji. Regresja Java/JavaScript także OK. Pierwsza próba ujawniła błąd założenia w weryfikatorze: drawSize API = min(20, pula), zgodnie z QuizService. Poprawiono oczekiwanie dla pustych pul i ponowiono całość z sukcesem. Startup/import 155 s, -Xmx512m. Zatrzymano własny PID31280 po sprawdzeniu main class i portu oraz wyłącznie javaquest-linux-validation-db. Nie dotykano baz użytkownika.
- 2026-10-13: 39/04 ListeningServicesAudit: 30/100, teoria zachowana, ABCD po 25. Docker niedostępny, więc NIE wykonano całej sesji w lxlab. Lokalnie w Git Bash przeszły ćwiczenia oparte na fixture: start laboratorium, filtry UDP i adresów wszystkich interfejsów, model ss, normalizacja, diff/comm, skrypt sprawdz-porty.sh (NADMIAR/BRAK z kodem 1 oraz OK z kodem 0), plan naprawy, usunięcie JDWP z kopii env, raport zakresu i sprzątanie. Nie wykonano ćwiczeń na żywym systemie: ss -p, własny serwer HTTP na 127.0.0.1:18555, readlink /proc/PID/exe + dpkg -S, ps -o unit, list-sockets, ssh.socket, dpkg-query, is-enabled. Do sprawdzenia w lxlab/VM, gdy Docker będzie dostępny. Audyt bez błędów, 371/437, pierwszy brak 39/05. LessonContentFilesTest: 2 testy, 0 błędów.

- 2026-10-13: uzupełniająca weryfikacja 39/04 w osobnym Ubuntu chroot tmpfs /run przez profil listening: rzeczywiste własne TCP/UDP/Unix z poprawnym payload, ss z adresem i PID, oddzielenie protokołów, /proc/exe i pakiet Python, wykrycie dodatkowego własnego TCP i zniknięcie po zamknięciu. Ponadto wykonano dokładne rozwiązania istniejącej lekcji 1–11, 28 i 30: realne ss TCP/UDP, HTTP 127.0.0.1:18555, zgodny PID, HTTP200, binarka /usr/bin/python3.12 oraz pakiet python3.12-minimal, stop własnego procesu i sprzątanie. To nie pełne 30 ćwiczeń; systemd 13–16 nadal nie testowany runtime, brak skanu z zewnątrz i testu zapory. LessonContentFilesTest 2/0/0.
- Runner listening instaluje iproute2/python3/sudo/curl/procps we własnym chroot, /proc montuje tylko do odczytu dla tego profilu i sprząta mounty. Docker API niedostępne; docker desktop start nie zakończył uruchamiania, zatrzymano tylko własne wiszące CLI desktop start/info po weryfikacji argumentów, bez zatrzymywania usług użytkownika ani usuwania stanu Dockera. WSL/chroot umożliwił dalszą weryfikację. Równoległy zapis ukończył 39/04 w commit97af3c04; zachowano jego istniejące 30/100 i nie zastępowano ich drugim zestawem. Audyt aktualny 371/437, next=_lx_39_bezpieczenstwo/05_SelinuxBasics.json.
- 2026-10-13: 39/05 SelinuxBasics: 30/100, teoria zachowana, ABCD po 25. Ćwiczenia na rocky-lab: własny katalog /srv/jq-www, osobny blok Nginx 127.0.0.1:8008 (http_port_t), 403 przez var_t/user_home_t (mv vs cp), fcontext+restorecon, chcon i przywrócenie, backend Pythona 127.0.0.1:18080 i 502 przez name_connect, przełącznik bez -P i wyłączenie, audit2allow tylko do odczytu, name_bind na 8089 i semanage port, pełne sprzątanie (conf, port, fcontext, katalog). NIE wykonano na Rocky (brak Dockera/VM w tej sesji) — do sprawdzenia. Audyt bez błędów, 372/437, pierwszy brak 39/06. LessonContentFilesTest OK.

- 2026-10-13: poprawka 39/05 ćwiczenia 24 i raportu 30: tymczasowe włączenie httpd_can_network_connect przywraca poprzedni stan przez trap EXIT w subshell, zamiast bezwarunkowo wyłączać zastany przełącznik. Test modelu Bash z interfejsami getsebool/setsebool/curl potwierdził cztery przypadki: początkowe on/off i sukces/błąd curl; zawsze przywrócono stan. Nie uruchamiano SELinux ani nie zmieniano przełączników hosta. Teoria i wszystkie 100 pytań zachowane, nadal 30/100. Audyt bez błędów, 372/437, kolejny brak _lx_39_bezpieczenstwo/06_AppArmorBasics.json.
- LessonContentFilesTest po poprawce 39/05: 2 testy, 0 błędów. API zmienionych 39/04–05 nadal do sprawdzenia po uruchomieniu izolowanego backendu; bieżący brak Docker API nie blokuje pracy nad treścią i modelami.
- 2026-10-13: 39/06 AppArmorBasics: 30/100, teoria zachowana, ABCD po 25. Ćwiczenia na srv-test: odczyty aa-status/JSON/etykiet, profil mysqld tylko do odczytu; własny skrypt /usr/local/bin/jq-raport.sh z profilem jq-raport (complain -> ALLOWED, aa-enforce -> DENIED dla /etc/shadow i /tmp), rozszerzenie local/jq-raport, apparmor_parser -QK, jednostka jq-raport.service z AppArmorProfile= i drop-in w /run, pełne sprzątanie (apparmor_parser -R, pliki, /srv/jq-aa). NIE wykonano na Ubuntu w tej sesji (brak Dockera/VM) — do sprawdzenia, szczególnie przejście profilu dla skryptu uruchamianego przez AppArmorProfile=. Audyt bez błędów, 373/437. LessonContentFilesTest OK.
- 2026-10-13: 39/07 AuthLogsAndLoginAudit: 30/100, teoria zachowana, ABCD po 25. Sesja na lubuntu-nauka (na srv-test reguły audytu są zablokowane -e 2): odczyty journalctl ssh/sudo, last/lastb/lastlog; modelowy auth.log (15 linii) — analiza Accepted/Failed/Invalid, porażki przed sukcesem, odciski kluczy, sudo, logowania nocne, porównanie kopii lokalnej z centralną; tymczasowe reguły auditctl (-w z kluczem jq_konfig, execve z własnym auid jq_root) i ich usunięcie; aureport; model 50-serwer.rules bez ładowania. Ćwiczenia na fixture (1, 7–14, 25, 28–30) przeszły lokalnie w Git Bash; części na żywym systemie i auditd NIE wykonano (brak VM w tej sesji). Audyt 374/437, LessonContentFilesTest OK.
- 2026-10-13: 39/08 FileIntegrityAndChecksums: 30/100, teoria zachowana, ABCD po 25. Laboratorium na srv-test w /var/tmp/javaquest-integrity.* (ext4 dla chattr): listy SHA256SUMS i -c, efekt lawinowy, plik .sha512 producenta, własny klucz GPG w osobnym GNUPGHOME (podpis .asc, BAD po zmianie, brak klucza w pustej bazie, odcisk), dpkg --verify / md5sums / debsums tylko do odczytu, własna konfiguracja i baza AIDE w laboratorium (init, check z kodem bitowym, update jako zatarcie śladów, sprawdzenie z kopią przez --before), find -newer/-cmin, podrobiony mtime vs ctime, chattr +i, odczyt systemowego AIDE, diff list. Lokalnie w Git Bash przeszły ćwiczenia 1–12 (GPG w krótkiej ścieżce — długa ścieżka scratchpada psuje gniazdo gpg-agent na Windows), 16, 24, 25, 28. NIE wykonano AIDE, dpkg/debsums, chattr (brak Linuksa w tej sesji). Audyt 375/437, LessonContentFilesTest OK.

- 2026-10-13: verify_lx_39_integrity_local.py przeszedł w osobnym Ubuntu chroot tmpfs /run, profil integrity. Wykonano dokładne rozwiązania AIDE 39/08 nr 17–23 na własnych danych: config-check, init i kopia bazy, czyste check=0, dodanie/zmiana check=5, szczegóły zmiany praw 600→644, update maskujący zmianę i check=0, wykrycie innego skrótu bazy, check z zachowaną kopią przez --before ponownie=5. Dodatkowe usunięcie własnego pliku potwierdziło wszystkie bity z kodem 7. Wszystkie dane/chroot/mounty sprzątnięte; find /run nie wykazał pozostałości. Nie testowano chattr +i, debsums ani GPG w tym runnerze.
- Ten sam runner sprawdził dokładny profil AppArmor z 39/06 ćwiczenia 11 przez apparmor_parser -QK: składnia zaakceptowana, bez ładowania do jądra. Ostrzeżenie o brakującym interfejsie cache w chroot nie przerwało parsera. NIE potwierdza to enforce/complain, odmów ALLOWED/DENIED ani przejścia profilu dla skryptu/systemd. Dokumentacja: https://www.apparmor.net/man/master/apparmor_parser/ oraz https://manpages.debian.org/bookworm/aide/aide.1.en.html . LessonContentFilesTest 2 testy, 0 błędów.
- 2026-10-13: 39/09 ServerSecurityChecklist: 30/100, teoria zachowana, ABCD po 25. Na lubuntu-nauka: odczyty żywego systemu (reboot-required, sshd -T, ufw, aa-status, konta, sudoers.d, systemd-analyze security, porty), skrypt kontrola.sh na plikach modelowych (funkcje zamiast eval; 6×UWAGA -> naprawy -> 6×OK, kod 1/0), plik wyjątków z zaległym przeglądem, certyfikat 10-dniowy vs próg 30 dni, wiek kopii, Lynis (audyt, warning/suggestion, show details, decyzje, custom.prf skip-test=BOOT-5122 tworzony i usuwany), historia CSV, przejściowy timer systemd-run jq-kontrola z wynikiem exit-code przy uwadze. Lokalnie przeszły ćwiczenia modelowe (1, 7–15, 29, 30; openssl -subj w Git Bash wymaga MSYS2_ARG_CONV_EXCL). Lynis, systemd-run i odczyty żywego systemu NIE wykonane. Rozdział 39 — praktyka kompletna (9 × 30/100). Audyt 376/437, LessonContentFilesTest OK.
- 2026-10-13: 40/01 ShellVsEnvironmentVariables: 30/100, teoria zachowana, ABCD po 25. WSZYSTKIE 30 rozwiązań wykonane w jednej sesji Bash lokalnie (Git Bash, GNU bash; Java 25 dla Srodowisko.java): eksport/export -n, zmienne jednorazowe, puste vs nieustawione, proces potomny vs source, readonly (atrybut nie przechodzi do dziecka), declare -i, $$/$BASHPID/$?/$!, kopia środowiska z chwili startu, /proc/PID/environ, env -i (pełna ścieżka bash), getenv vs -D vs JAVA_TOOL_OPTIONS. Lokalny test wykrył i poprawiono: :? wewnątrz $( ) nie przerywa skryptu. Audyt 377/437.
- 2026-10-13 (później): Docker Desktop wstał w trakcie sesji (działa tylko kontener użytkownika arnocrm-redis-1 — nie ruszany). Weryfikacja w jednorazowych kontenerach `docker run --rm ubuntu:24.04` (sudo usunięte z rozwiązań): 39/08 — wszystkie ćwiczenia poza dpkg/debsums/odczytem systemowego AIDE; prawdziwe AIDE 0.18: init, check kod 0, po zmianach kod 5 (f+++ dla .ukryty.sh, f = p.. dla app.conf, f > ... ....H dla wdroz.sh), podmiana bazy -> kod 0, kopia bazy ujawnia zmiany; chattr +i w kontenerze odmówiony (brak CAP_LINUX_IMMUTABLE — na VM działa). Poprawki po teście: ćw. 19 wypisuje linię „AIDE found”, wyjaśnienie pytania o .C.. uwzględnia format AIDE 0.18 (H). 39/04 — wszystko poza systemctl (brak systemd w kontenerze): ss -tulpnH, własny serwer na 127.0.0.1:18555 z PID, readlink /proc/PID/exe = /usr/bin/python3.12, dpkg -S -> python3.12-minimal, model/diff/comm/skrypt NADMIAR/BRAK.
- 2026-10-13: 40/02 ExportAndEnv: 30/100, teoria zachowana, ABCD po 25. WSZYSTKIE 30 rozwiązań wykonane jako anna (sudo NOPASSWD) w jednorazowym kontenerze ubuntu:24.04: printenv/kody, export -p/-n, env TZ/-u/-i/-C/-0, środowisko minimalne vs terminal, skrypt zależny od KOPIE_DIR i poprawiony, shebang env vs /bin/bash, sudo env_reset/secure_path/--preserve-env, sudo echo $HOME vs bash -c, | sudo tee, sudo -i pwd, diff środowisk (SUDO_*), symulacja crona, env w xargs. Test obalił założenie: przy NIEUSTAWIONYM PATH env/glibc szuka w /bin:/usr/bin, więc `env -i bash` i `#!/usr/bin/env bash` działają na Linuksie (zawodzi tylko Git Bash). Ćw. 17 przerobione na PATH=/opt/brak; poprawione pytania/wskazówki w 40/01 i 40/02. 40/01 powtórnie sprawdzone w Ubuntu (bez ćw. Javy, te przeszły lokalnie z JDK 25). Audyt 378/437.
- 2026-10-13: 40/03 PathVariable: 30/100, teoria zachowana, ABCD po 25. WSZYSTKIE 30 rozwiązań wykonane jako anna w kontenerze ubuntu:24.04: kolejność katalogów (bin0/bin/bin2 z jqwersja), hash (stara ścieżka do hash -r, hash -d), ./ vs nazwa (127), dopisanie na koniec nie przesłania ls, . i pusty element w PATH (podpowłoki), sprawdz_path, duplikaty i awk RS=:, ochrona case, nadpisany PATH i ratunek pełną ścieżką, dpkg -S coreutils, dowiązanie zamiast PATH, sudo/secure_path, which vs command -v. Test wykrył: /bin i /sbin (dowiązania 777) fałszywie „zapisywalne” — poprawione na find -L. Audyt 379/437.
- 2026-10-13: 40/04 BashStartupFiles: 30/100, teoria zachowana, ABCD po 25. WSZYSTKIE 30 rozwiązań wykonane jako anna w kontenerze ubuntu:24.04 na kopii /etc/skel w laboratoryjnym HOME (prawdziwe pliki anny nietknięte): znaczniki w .profile/.bashrc dla bash -l/-i/-c, shopt login_shell i $-, .bash_profile przesłaniający .profile (znikają ~/bin i ll) i poprawka, pierwszeństwo .bash_login, zmienne w .profile vs aliasy w .bashrc, dziedziczenie przez powłokę nie-logowania, BASH_ENV, powitanie przed/po ochronie case psujące wyjście, .bash_logout tylko dla powłok logowania, warunek [ -d ~/bin ]. Audyt 380/437.
- 2026-10-13: 40/05 SystemWideEnvironment: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna (sudo) w kontenerze ubuntu:24.04 z curl, python3 i openjdk-21: kopie i przywrócenie /etc/environment oraz pam_env.conf (cmp), konto testowe jqenv (usunięte userdel -r), JQ_FIRMA widoczna w su - i sudo -u (pam_env), brak rozwinięcia $HOME w /etc/environment vs @{HOME} w pam_env.conf, profile.d tylko dla powłok logowania i tylko *.sh, fałszywe proxy 403 vs aplikacja 200: curl bez/z no_proxy, adres IP poza no_proxy, apt-config -c, Pobierz.java (HttpClient) ignoruje https_proxy, -D/JAVA_TOOL_OPTIONS -> 403, nonProxyHosts i domyślne omijanie localhost -> 200. systemctl show-environment niedostępne w kontenerze (obsłużone komunikatem). Audyt 381/437.
- 2026-10-13: 40/06 AliasesAndFunctionsInBashrc: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna w kontenerze ubuntu:24.04 (sesja z expand_aliases jak terminal; aliasy w pliku laboratorium, nie w ~/.bash_aliases): alias/unalias/type, argumenty doklejane na końcu, funkcje (mkcd, ${1:-}, ${1:?} w podpowłoce, local, kod zwrotu, "$@"), alias vs funkcja o tej samej nazwie (\nazwa), ominięcie aliasu ls na 3 sposoby, alias w skrypcie -> 127 i expand_aliases, export -f/-fn, alias w cudzysłowach zamraża $(date), funkcja dzis, .bash_aliases wczytywany przez wzorcowy .bashrc, rm -I (jedno pytanie), alias sudo='sudo ', alias Gita w osobnym pliku, $( ) a zmienne globalne. Audyt 382/437.
- 2026-10-13: 40/07 PromptCustomizationPs1: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna w kontenerze ubuntu:24.04 (hostname lubuntu-nauka, git, sudo): podgląd ${PS1@P} dla \u \h \H \w \W \t \d \j \$ (root #), PROMPT_DIRTRIM, kolory i cat -v, \[ \] -> ^A/^B tylko w powłoce interaktywnej (sprawdzone osobno bash -i), PS2/PS3 (select)/PS4 z LINENO, funkcja __kod z $?, __git_ps1 z gwiazdką, PROMPT_COMMAND tylko w bash -i, znacznik środowiska PROD/TEST z pliku (bez duplikatów), tymczasowy /etc/profile.d/zz-jq-prompt.sh: przez PROMPT_COMMAND przetrwa .bashrc, samo przypisanie PS1 nie (0 trafień) — plik usunięty; debian_chroot, tput, PS1 nie jest eksportowana. Rozdział 40 — praktyka kompletna (7 × 30/100). Audyt 383/437.
- 2026-10-13: 41/01 FirstScriptAndShebang: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna w kontenerze ubuntu:24.04 (python3, file, shellcheck): bash skrypt vs ./ (126 bez x), 127 poza PATH, kod ostatniego polecenia i exit 3, bash -n (kod 2), /bin/sh -> dash i [[: not found, brak shebangu -> os.execv Exec format error i naprawa, CRLF, python przez env, #!/bin/bash -x, source vs proces, exit w source (podpowłoka), $0/basename/readlink -f, chmod 700, heredoc z/bez cudzysłowów, shellcheck SC2086. UWAGA: bash 5.2 dla CRLF zgłasza „cannot execute: required file not found” (kod 127), nie „bad interpreter” jak w teorii (CODE_PRACTICAL) — w praktyce opisano obie wersje; teorię do przeglądu przed zamknięciem kursu. Audyt 384/437.
- 2026-10-13: 41/02 VariablesAndQuoting: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna w kontenerze ubuntu:24.04: apostrofy/cudzysłowy/\, ${plik}_x, podział na słowa (funkcja pokaz), rm $plik na kopii usuwa raport i wrzesien.csv, wzorzec w zmiennej, ${#}, :- := :+ :?, obcinanie nowych linii w $( ), archiwizuj-zle.sh vs archiwizuj.sh, literówka w rm -rf tylko przez echo (z / pokazuje /bin /boot ...), ${x:?} przerywa, set -u, JAVA_OPTS vs tablica, [ $x = abc ] kod 2, rm -- "-n.txt", echo -n vs printf, operacje ${x##*/} ${x%/*} ${x%.csv} ${x//-/_}, $'\t', $(< plik). Test obalił dwa założenia: bash -u -c kończy się kodem 127; echo "-e a\tb" nie traktuje -e jako opcji (tylko argument równy dokładnie -e/-n) — ćwiczenie przerobione na v='-n'. Audyt 385/437.
- 2026-10-13: 41/03 ReadingUserInput: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna w kontenerze ubuntu:24.04; terminal symulowany przez `script -qec ... /dev/null` (pty): read -p/-r/-s/-t (kod 142)/-n 1/-e -i/-a/-d ''/REPLY, IFS=: z getent, IFS= zachowuje spacje, pętla while read i ostatnia linia bez \n, [ -t 0 ], -p widoczne tylko z terminala, skrypt nowe-konto.sh (skrót hasła do pliku zamiast PostgreSQL) w pty / z potoku / różne hasła / anulowanie, czysc-zle.sh ([T/n] z /dev/null kasuje) vs czysc.sh (-y, odmowa bez terminala, [t/N]), mapfile, read -u 3, walidacja liczby, hasło w argumentach widoczne w ps (bash -c 'sleep 30; :' — przy jednym poleceniu bash robi exec i argumenty znikają). Audyt 386/437.
- 2026-10-13: 41/04 IfConditions: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna w kontenerze ubuntu:24.04 (python3, curl, iproute2, procps; `docker run --init`): grep -q/id/mountpoint, &&/||, pułapka A && B || C, if/elif z (( )), kod samej instrukcji if, bash -n (kod 2), strażnik pilnuj.sh na lokalnej aplikacji z plikiem PID: STOP -> „nie odpowiada” + restart, KILL -> AWARIA, świadome zatrzymanie -> bez zmian; kopia.sh z kodami 1/2, grep -c kod 1, curl -f (22 dla 404). Uwaga: bez --init zabity proces zostaje zombie i kill -0 się udaje (ćw. 14 dawało wtedy złą gałąź) — na VM z systemd problemu nie ma; dopisano pytania o zombie. Audyt 387/437.
- 2026-10-13: 41/05 TestOperators: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna (sudo) w kontenerze ubuntu:24.04 --init: napisy/liczby/(( )), tabela -e -f -d -L -s, -r/-w/-x z chmod 000 (root czyta), -nt/-ot, [[ == wzorzec ]], =~ z BASH_REMATCH, pusta zmienna w [ ] (kod 2) vs [[ ]], "10"<"9", 08 vs 10#08, integer expression expected, awk dla ułamków, > w [ ] tworzy plik 90, kontrola.sh w 4 scenariuszach (brak / stara / pusta / OK), type -a [, [$x (127), == w dash, -v, sudo test dla pliku 600 roota, zerwane dowiązanie. DO PRZEGLĄDU TEORII: CODE_RIGHT 41/05 podaje „kod: 1” po poprawionym prog-zle.sh, a if bez spełnionej gałęzi zwraca 0 (sprawdzone). Audyt 388/437.
- 2026-10-13: 41/06 CaseStatement: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna w kontenerze ubuntu:24.04 --init: tak/nie, typy plików, godziny, ;& i ;;&, wzorzec w cudzysłowach i zmienna jako wzorzec, nocasematch i ${x,,}, *.gz przed *.sql.gz (zły/dobry), walidacja liczb i instancji, panel lab-ctl.sh (start/st/wersja/stopp kod 2/pomoc kod 0/port 9000 kod 2/stop), case w dash vs [[ w dash, brak ;; (bash -n), opcje -*, klasy [[:upper:]], extglob +([0-9]), menu, case bez dopasowania kod 0. Audyt 389/437.
- 2026-10-13: 41/07 ForLoops: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna w kontenerze ubuntu:24.04 --init: listy, pliki z wzorca, pusty wzorzec vs nullglob, {01..03}/{0..100..25}/{a..e}, {1..$n} vs seq vs (( )), continue/break/break 2, "$@" vs $*, tablice i indeksy, $(ls) ze spacją (5 obrotów) vs wzorzec, kompresuj.sh (-mtime +7, tylko 2 z 3 plików, drugie uruchomienie bez zmian), stan-instancji.sh z licznikiem i exit $(( bledy > 0 )), suma linii, nawiasy cp{,.bak}/mkdir, date -d przez granicę miesiąca, pętla po PID, mv ze spacją, wynik pętli | sort. Audyt 390/437.
- 2026-10-13: 41/08 WhileAndUntilLoops: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna w kontenerze ubuntu:24.04 --init: odliczanie, until na pliku flagi, kolumny i CSV (suma 2408.50 przez awk w pętli i jednym awk), potok (licznik 0) vs < <(...) (2), lastpipe, ostatnia linia bez \n, find -print0 + read -d '', skrypt zdalnie.sh czytający stdin jak ssh (obsłużony 1 serwer) i naprawy < /dev/null oraz <&3, czekaj.sh z SECONDS (gotowy po 4 s / błąd po 3 s), ponawianie while true i until z limitem, timeout 124, ps przez podstawienie procesu, parsowanie klucz=wartość, dwa pliki naraz (3/4), until bez obrotów. Wyłapano własny błąd rachunkowy w pytaniu (suma) — poprawiony. Audyt 391/437.
- 2026-10-13: 41/09 ArgumentsAndExitCodes: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna w kontenerze ubuntu:24.04 --init (rsync): $0/$#/"$@"/"$*"/$@, ${10}, shift/shift 2, kody 2/126/127, 130 (bash -c 'kill -INT $$' — procesy w tle w powłoce nieinteraktywnej ignorują INT), 143/137 przez wait, PIPESTATUS i pipefail, exit 256/-1/300, kod ostatniego polecenia (test -> 1, echo maskuje cp), kopia-katalogu.sh z teorii: brak arg/-x -> 2, -h -> 0, -n, -v, brak źródła -> 1, rsync kod 11 -> 1, -- dla -dziwny, -o z wartością i --wyjscie=, spakuj $* vs "$@", $1 w funkcji, getopts "nvo:", stderr vs stdout, exit w pętli. Audyt 392/437.
- 2026-10-13: 41/10 FunctionsInBash: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna w kontenerze ubuntu:24.04 --init (python3, curl): funkcje z kodem i wynikiem $( ), local vs globalna (101), $1 vs $0, declare -F/-f, return 3, wywołanie przed definicją, funkcja ls i command ls, rekurencja (10! = 3628800), biblioteka funkcje.sh (644) z loguj/zakoncz/czekaj_na_gotowosc/wersja_aplikacji/wymagaj_montowania, pilnuj.sh uruchamiający brakującą instancję, montowanie.sh (exit 1 dla /srv/kopie), raport-zle (instancja gc + komunikat w wyniku) vs raport (local, >&2), exit w $( ), source z exit w podpowłoce, local x=$(false) daje 0, FUNCNAME, tablica do funkcji, read dwóch wartości, funkcja w potoku, export -f z xargs, strażnik biblioteki. Rozdział 41 — praktyka kompletna (10 × 30/100). Audyt 393/437.
- 2026-10-13: 42/01 ArraysAndAssociativeArrays: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna w kontenerze ubuntu:24.04 --init z openjdk-21-jre: tworzenie/odczyt/[-1]/unset bez przenumerowania i przenumerowanie, [@] vs [*] vs $tab, wycinki, mapfile/read -ra/mapfile -d '' z find -print0, declare -A i -v, brak declare -A (declare -a port2=([0]="8090")), kody-http.sh (200×2, 304, 404×2, 502), liczenie IP, polecenie w napisie vs tablicy, eval z wstrzyknięciem, java "${opcje[*]}" -> Invalid initial heap size, IFS w podpowłoce, zawiera(), sortowanie, deduplikacja, local -n, conf z pliku, tablice nie przechodzą przez export. Pytania z dodatkowymi faktami (${tab[@]/#/x}, -v dla pustej wartości, mapfile -s, +=) sprawdzone w Git Bash. Audyt 394/437.
- 2026-10-13: 42/02 StringManipulation: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna w kontenerze ubuntu:24.04: ${#} ${##*/} ${%/*}, % vs %% (sklep-1, .sql.gz), wycinki i pułapka ${s:-2}, zamiany zwykłe i zakotwiczone, ^^ ,, , ^, klucz=wartość z = w wartości, uporzadkuj-nazwy.sh (idempotentny; mv -n w coreutils 9.4 wypisuje „not replacing” i zwraca błąd), rozbiór JDBC przez grep -oP \K, wzorzec ze zmiennej z/bez cudzysłowów, extglob @(jar|war) i przycinanie, BASH_REMATCH, kontrola formatu daty, liczenie ukośników, printf, IFS=, read -ra i łączenie, time 2000× basename ≈ 2,1 s vs ${p##*/} ≈ 0,007 s, sed vs pętla. Audyt 395/437.
- 2026-10-13: 42/03 ArithmeticInBash: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna w kontenerze ubuntu:24.04 (bc): operatory, zaokrąglanie, 08 vs 10#08, podstawy (010 = 8), kod (( 0 )) = 1 i (( n++ )) przy 0 przerywa skrypt z set -e ((( ++n )) działa), operator warunkowy, 1/3*3 = 0, procent 412/19319 (0% vs 2%), alarm przez mnożenie, awk/bc, błąd dla 3.5, przepełnienie 64-bit, dzielenie przez 0, SECONDS i min:ss, rozmiar MB w górę/dokładnie, wiek pliku, odsetek 5xx (15%), grosze (59,97 zł), porównanie ułamków awk, let/declare -i, $RANDOM 1..6, maska 022 (644/755), %05d, suma kolumny bash vs awk, hms, date -d @epoch. Dodatkowo sprawdzono: bc obcina (2/3 -> .66), printf '%.2f' 3.4 w pl_PL.UTF-8 -> invalid number. Audyt 396/437.
- 2026-10-13: 42/04 StrictModeSetEuo: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna w kontenerze ubuntu:24.04: -e/-u/pipefail osobno, wyjątki -e (if, !, ||, false && true vs true && false, funkcja w warunku), local x=$(false) vs x=$(false), inherit_errexit, (( licznik++ )), grep -c || true, diff kod 1, policz-zle.sh z bash -x i poprawka, kopia-raportow.sh z mountpoint (PUNKT=/ przechodzi, katalog lab — kod 1), trap ERR z $LINENO, IFS bez spacji, yes | head z pipefail = 141; bash -c z -u kończy się kodem 127.
- 2026-10-13: 42/05 TrapAndCleanup: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna w kontenerze ubuntu:24.04 (TMPDIR w laboratorium): trap EXIT/-p/-, mktemp (600/700), cudzysłowy w trap (pozostałość), demo-trap.sh ok/blad (kod 2)/INT 130/TERM 143 (komunikat Terminated)/KILL 137 z pozostałością — sygnały przez timeout --preserve-status (sygnał trafia do całej grupy, więc sleep kończy się od razu), $? w pułapce przed i po rm, EXIT bez exit zachowuje kod 5, exit 0 maskuje błąd, ERR z $LINENO/$BASH_COMMAND, -E w funkcjach, import-zle.sh zostawia tryb on, import-cennika.sh (flock na pliku w lab, zły nagłówek, INT/TERM, druga kopia — kod 1), flock -n/-w, trap "" INT, trap - INT (130), HUP 129, podpowłoka.
- 2026-10-13: 42/06 GetoptsOptionParsing: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna w kontenerze ubuntu:24.04 (rsync): śledzenie opt/OPTARG/OPTIND (4, 6, 7), demo-getopts.sh (błędy : i \?, opcja po argumencie, -b5000, -nb, --, -b -n jako wartość, pusta wartość), tryb zwykły vs cichy i OPTERR=0, kopia-katalogu.sh na katalogach lab (symulacja, kopia z wykluczeniami, walidacje), bez shift, local OPTIND w funkcji, tablice -s i -D, getopts w dash, getopt util-linux (permutacja, eval set --, błędy). UWAGA teoria (CODE_WRONG): kopia-zle.sh z set -u kończy się „OPTARG: unbound variable” (kod 1), a bez -u walidacją „-b wymaga liczby” — nie „potrzebne ZRODLO i CEL” jak w przykładzie; do poprawy przy przeglądzie teorii.
- 2026-10-13: 42/07 ProcessingFilesLineByLine: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna w kontenerze ubuntu:24.04 (sudo, file, python3): CSV z CRLF i bez końcowego \n (120 B, wc -l 4 vs pętla 5), od -c „I T \r”, CODE_WRONG/CODE_RIGHT odtworzone 1:1, IFS== z = w wartości, parser z białą listą vs source (PWNED powstaje tylko przy source), TSV, CSV z cudzysłowami (read vs python csv), mapfile, konta-z-listy.sh (symulacja, walidacja loginów, sudo z kontami jq*, ponowne uruchomienie, userdel -r), 200000 linii: pętla ~1,4 s vs awk ~0,04 s, $(cut) na 2000 liniach ~3,9 s vs ${l%% *} ~0,01 s, xargs -P 4 (4 s → 1 s), cat zjadający wejście pętli, read -u 3, potok vs < <( ). Uwaga: declare -A na zmiennej skalarnej daje dodatkowy element [0].
- 2026-10-13: 42/08 DebuggingAndShellcheck: 30/100, teoria zachowana, ABCD po 25. Wszystkie ćwiczenia wykonane jako anna w kontenerze ubuntu:24.04 (shellcheck 0.9.0): bash -n (brak fi/done — unexpected end of file, kod 2; nawias), 127 mimo poprawnej składni, porzadki.sh z teorii realnie usuwa wazny.tmp w złym katalogu (ślad z PS4), reguły SC2164/2086/2046/2035, poprawka bez uwag, set -v vs -x (plik, nie bash -c), ++/+++, PS4 z FUNCNAME i BASH_SOURCE (funkcje.sh), fragment set -x/+x, { set +x; } 2>/dev/null, BASH_XTRACEFD, SC2155/2034/2164/2162/2181/2068/2046, --severity=warning (SC2068 to error), disable=SC2086 z uzasadnieniem, SC1091 i -x, .shellcheckrc, -f gcc, kody 0/1, pętla „CI”, hasło w śladzie (1) i wersja SLEDZ/BASH_XTRACEFD (0 — linia „+ set +x” trafia do pliku śladu), SC3010 dla sh.
- 2026-10-13: 42/09 PracticalAdminScripts: 30/100, teoria zachowana, ABCD po 25. Ćwiczenia 1–27 i 29–30 wykonane jako anna (w grupie sudo) w kontenerze ubuntu:24.04 pod `script` (stderr jako terminal): szablon (logger z `2>/dev/null || true`, bo w kontenerze brak /dev/log; blokada w KATALOG_BLOKAD), -h 0 / -x 2, [[ -t 2 ]] (0 B w pliku), druga kopia 0, uruchom -n, sprawdz-dyski (PROG 0 → 1, 100 → 0), rotuj-kopie (usuwa 21–23, nazwy ze spacją i \n, kody 3 dla złego katalogu i /, :? → 1), CODE_WRONG odtworzony (12 → 7 plików, kod 0), SC2164/SC2012/SC2115, raport-kont, /etc/shadow, certyfikat 20 dni, wczytywanie konfiguracji z kontrolą -O i -perm /022, :- vs :=, find -mtime, jednostki jako pliki, git. Ćwiczenie 28 (systemd-analyze calendar, journalctl -t) NIE wykonane — brak systemd w kontenerze. UWAGA teoria: CODE_PRACTICAL/CODE_RIGHT pokazują usuwanie mysql-2026-09-30/29/28, a kod (sort -n -r, tail -n +8) usuwa najstarsze kopie — do poprawy przy przeglądzie teorii.
