# Kurs Linux — stan prac

Instrukcje: `LINUX_COURSE_STAGE_PROMPT.md`. Ten plik: aktualny stan, ostatnia czynność, następny
krok. Oddzielny od `WORK_PROGRESS.md` (Java) i `JS_COURSE_WORK_PROGRESS.md` (JavaScript).

## Stan (2026-10-02)

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
  Pozostałe lekcje (`_lx_16`…`_lx_46`) nadal PUSTE. Ćwiczenia i quiz — 0 wszędzie (krok 3).
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

## Następny krok

**Krok 2 — teoria, rozdział `_lx_16_uzytkownicy_i_grupy`**, potem kolejne rozdziały
po kolei (dyrektywa użytkownika 2026-10-04: „zrób wszystkie lekcje”, bez pytania o zgodę między
rozdziałami). Ćwiczenia i quiz dopiero w kroku 3 (liczby do potwierdzenia z użytkownikiem). Commit
po każdym rozdziale, komunikat po polsku, bez stopki o współautorstwie.
