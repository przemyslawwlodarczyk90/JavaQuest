# JavaQuest — Trzeci blok platformy: kurs Linux

> JEDYNE, obowiązujące źródło instrukcji dla pracy nad KURSEM LINUX (trzeci tor platformy, obok
> Javy i JavaScriptu). Stan prac: `LINUX_COURSE_WORK_PROGRESS.md`. Format lekcji, model danych
> i standard sekcji teorii są TE SAME co w kursie JavaScript (`JS_COURSE_STAGE_PROMPT.md`,
> sekcje „Model danych i standard 11 sekcji”, „Polskie znaki, jakość języka, weryfikacja”) —
> ten plik opisuje tylko to, co jest specyficzne dla Linuksa.

## Kontekst i cel

Panel główny ma przełącznik trzech kursów: **Java** (`/`, domyślny), **JavaScript** (`/js`)
i **Linux** (`/linux`). Kurs Linux ma dać praktyczną biegłość w pracy z systemem z perspektywy
programisty Javy: terminal i polecenia, uprawnienia, procesy, pakiety, sieć i SSH, usługi
systemd, serwer (Java/Apache/PHP/MySQL), WSL i Docker na WSL2.

## Źródło materiału

`dodatkowe materiały/LinuxMistery/src/main/java/org/example/notes/` — 33 pliki notatek
`LN01`…`LN31` (LN16 w trzech częściach: `LN16`, `LN16_..._Part2` = „LN16-2”,
`LN16_..._Part3` = „LN16-3”). Każda notatka to klasa Javy z komentarzami, podzielona na
ponumerowane sekcje (`1. ...`, `2. ...`) między liniami `=====`. Oznaczenie „LN07 §8-9”
w tabeli poniżej = sekcje 8 i 9 notatki LN07. Ostatnia sekcja wielu notatek („PODSUMOWANIE”)
nie ma osobnej lekcji — zasila blok `SUMMARY` lekcji danego tematu.

- Notatki to SUROWY, skrótowy materiał (ściąga) — NIE gotowa treść. Rozwijaj go do pełnej
  teorii i weryfikuj merytorycznie (część notatek dotyczy starszych narzędzi, np. `netstat`,
  `ifconfig`, Pathogen — pokazuj też nowoczesne odpowiedniki: `ss`, `ip`, natywne pakiety Vim).
- `LinuxMistery/src/main/java/exercises/*Servlet.java` to ćwiczenia z serwletów Javy (HTTP),
  NIEZWIĄZANE z Linuksem — świadomie NIE weszły do struktury kursu.
- Rozdział `_lx_20_skrypty_bash` oraz lekcja `_lx_15_systemd/07_JournalctlLogs` to
  UZUPEŁNIENIA spoza notatek (luki ważne dla praktyki: skrypty powłoki, cron, logi usług).
  Użytkownik może je usunąć — wtedy wystarczy skasować wpisy w `ChapterSeedData.java` i
  odpowiednie pliki JSON.
- Katalog źródłowy NIE jest serwowany przez platformę (materiał roboczy).

## Plan pracy — trzy kroki (dyrektywa użytkownika, 2026-09-26)

1. **Struktura** — rozdziały, lekcje, puste pliki treści. **ZROBIONE** (patrz niżej).
2. **Teoria** — pełne bloki teorii każdej lekcji (standard jak w kursie JS/Java).
3. **Ćwiczenia i quiz** — w osobnym kroku, po teorii. Liczba ćwiczeń/pytań na lekcję do
   potwierdzenia z użytkownikiem przed krokiem 3 (w kursie JS: 30 ćwiczeń / 100 pytań).

Nie przeskakuj kroków bez wyraźnej decyzji użytkownika.

## Specyfika treści kursu Linux

- **Żywe przykłady** — każdy blok `CODE_BASIC`/`CODE_PRACTICAL` pokazuje polecenie ORAZ jego
  wynik (jak w notatkach: `polecenie` + `-> wynik`), a przykładowe wyjście musi pochodzić z
  PRAWDZIWEGO uruchomienia — weryfikuj w kontenerze, np.
  `docker run --rm -it ubuntu:24.04 bash` (lub `debian`), dla usług systemd/sieci — w maszynie
  wirtualnej/WSL, jeśli kontener nie wystarcza (i zaznacz to w treści).
- **Ćwiczenia (krok 3)** — praktyczne: `prompt` każe wykonać konkretne polecenie/sekwencję
  poleceń, `solution` to dokładne polecenia (i ew. oczekiwany wynik), nie opis słowny.
- **Bezpieczeństwo** — polecenia destrukcyjne (`rm -rf`, `chmod -R 777`, `dd`, `--delete` w
  rsync, skanowanie `nmap` cudzych sieci) zawsze z ostrzeżeniem w `PITFALL`/`CODE_WRONG`.
- Nazwy plików, katalogów i użytkowników w przykładach — po polsku (np. `raport.txt`,
  `/home/anna/projekty`), tak jak w pozostałych kursach.

## Architektura toru (zaimplementowane 2026-09-26)

- `CourseTrack.LINUX`; rozdziały w `ChapterSeedData.java`, sekcja „TRZECI BLOK PLATFORMY”,
  slugi z prefiksem `_lx_NN_...`, konstruktor 4-argumentowy z `CourseTrack.LINUX`.
- `CourseTrackConstraintSync` — przy starcie odtwarza ograniczenie CHECK kolumny
  `chapters.track` z `CourseTrack.values()` (Hibernate `ddl-auto=update` nie aktualizuje go
  sam; bez tego istniejąca baza odrzucała rozdziały LINUX).
- `GET /api/chapters?track=LINUX`; frontend: trasa `/linux`, przycisk „Linux” w
  `TrackSwitcher` (`App.jsx`), powrót z listy lekcji na `/linux` dla slugów `_lx_`
  (`LessonListPage.jsx`), trasy `/js`, `/linux`, `/krytyczne` dopisane w `SpaFallbackController`.
- Pliki treści: `src/main/resources/content/<rozdział>/<lekcja>.json`, na razie puste
  (`{"theory": [], "exercises": [], "quiz": []}`) — frontend pokazuje „w przygotowaniu”.
- Tytuły lekcji w nawigacji powstają automatycznie ze sluga (`LessonSlugTitles`, jak w Javie
  i JS); polskie tematy są w tabeli poniżej.

## Struktura: 20 rozdziałów, 182 lekcje

### 1. `_lx_01_wirtualizacja` - Linux - wirtualizacja i maszyny wirtualne

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_WhatIsVirtualization` | Czym jest wirtualizacja | LN01 §1 |
| `02_HypervisorTypes` | Typy hypervisorów (typ 1 i typ 2) | LN01 §2 |
| `03_VmwareAndVirtualBox` | VMware i VirtualBox | LN01 §3-4, LN16 §4 |
| `04_VirtualizationAndCpuArchitecture` | Wirtualizacja a architektura procesora | LN01 §5-6 |
| `05_LinuxDistributionsAndLubuntu` | Dystrybucje Linuksa na przykładzie Lubuntu | LN16 §1 |
| `06_VirtualDiskFilesAndReadyMachines` | Pliki dysków VDI/VMDK i gotowe maszyny (OSBoxes) | LN16 §2-3 |
| `07_ResizingVirtualDisks` | Powiększanie dysku VM (VBoxManage, GParted) | LN16 §5-7 |
| `08_VmResourcesRamAndCpu` | Przydział pamięci RAM i procesorów do VM | LN16 §8 |

### 2. `_lx_02_systemy_plikow` - Linux - systemy plików i struktura katalogów

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_WhatIsAFileSystem` | Czym jest system plików | LN02 §1 |
| `02_NtfsFatAndExt4` | NTFS, FAT i ext4 - porównanie | LN02 §2-4, §8 |
| `03_PartitionEncryptionBitlockerAndLuks` | Szyfrowanie partycji: BitLocker i LUKS | LN02 §5-7 |
| `04_RootDirectoryAndHierarchy` | Katalog główny / i hierarchia katalogów | LN03 §1, §17 |
| `05_BinSbinLibAndUsr` | Programy i biblioteki: /bin, /sbin, /lib, /usr | LN03 §2-3, §8, §15 |
| `06_EtcHomeAndRoot` | Konfiguracja i katalogi domowe: /etc, /home, /root | LN03 §6-7, §12 |
| `07_BootDevAndProc` | Start systemu, urządzenia i procesy: /boot, /dev, /proc | LN03 §4-5, §11, §16 |
| `08_VarTmpOptAndMnt` | Dane zmienne i dodatkowe: /var, /tmp, /opt, /mnt | LN03 §9-10, §13-14 |

### 3. `_lx_03_podstawowe_polecenia` - Linux - podstawowe polecenia

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_ShellAndTerminal` | Powłoka i terminal - pierwsze polecenia | LN28 §1, LN04 wstęp |
| `02_LsListingFiles` | ls - listowanie plików i katalogów | LN04 §1 |
| `03_CdAndPwdNavigation` | cd i pwd - poruszanie się po katalogach | LN04 §2-3 |
| `04_HiddenFilesAndDotfiles` | Pliki ukryte i pliki konfiguracyjne (dotfiles) | LN07 §1 |
| `05_WildcardsAndGlobbing` | Symbole wieloznaczne (*, ?) w nazwach plików | LN04 §1 (ls *.txt, a*) |
| `06_TouchAndCat` | touch i cat - tworzenie i wyświetlanie plików | LN04 §4-5 |
| `07_MkdirCpAndMv` | mkdir, cp, mv - katalogi, kopiowanie i przenoszenie | LN04 §7-9 |
| `08_RmDeletingSafely` | rm - bezpieczne usuwanie plików i katalogów | LN04 §10 |
| `09_HeadTailWcAndSort` | head, tail, wc, sort - podgląd, liczenie i sortowanie | LN04 §12-14 |

### 4. `_lx_04_wejscie_wyjscie` - Linux - wejście, wyjście i potoki

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_StdinStdoutStderr` | Standardowe wejście, wyjście i wyjście błędów | LN05 §1, §3 |
| `02_FileDescriptors` | Deskryptory plików 0, 1, 2 | LN05 §2, §9 |
| `03_OutputRedirection` | Przekierowanie wyjścia: > i >> | LN05 §4 |
| `04_ErrorRedirection` | Przekierowanie błędów: 2>, 2>&1, &> | LN05 §5 |
| `05_InputRedirection` | Przekierowanie wejścia: < i here-doc | LN05 §6 |
| `06_Pipes` | Potoki | - łączenie poleceń | LN04 §6, LN14 §1, LN05 §7 |
| `07_DevNullAndPracticalScenarios` | /dev/null i praktyczne kombinacje przekierowań | LN05 §8, §10 |

### 5. `_lx_05_uzytkownicy_i_uprawnienia` - Linux - użytkownicy i prawa dostępu

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_ReadingPermissions` | Odczytywanie praw dostępu (ls -la) | LN06 §1 |
| `02_ChmodNumeric` | chmod liczbowy (755, 644) | LN06 §2, §6 |
| `03_ChmodSymbolic` | chmod symboliczny (u+x, go-w) | LN06 §3 |
| `04_FileVsDirectoryPermissions` | Prawa do plików a prawa do katalogów | LN06 §4 |
| `05_UmaskDefaultPermissions` | umask - domyślne prawa nowych plików | LN06 §5 |
| `06_ChownAndChgrp` | chown i chgrp - właściciel i grupa | LN06 §7-9 |
| `07_EtcPasswd` | Plik /etc/passwd - kim są użytkownicy systemu | LN14 §2, §9 |
| `08_EtcShadowAndLogin` | Plik /etc/shadow i proces logowania | LN14 §6-7 |
| `09_ShellsAndNologin` | Powłoki użytkowników i konta nologin | LN14 §3-4, §8 |
| `10_CreatingUsersAndGroups` | Tworzenie użytkowników i grup | LN14 §5, LN16-2 §5 |
| `11_SudoAndRoot` | sudo, konto root i restart systemu | LN16 §17-18 |

### 6. `_lx_06_pakiety` - Linux - zarządzanie pakietami

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_WhatIsApt` | APT - menedżer pakietów (which, --help) | LN07 §2-4 |
| `02_ListingInstalledPackages` | Lista zainstalowanych pakietów | LN07 §5 |
| `03_UpdateVsUpgrade` | apt update a apt upgrade | LN07 §6-7 |
| `04_RepositoriesAndSourcesList` | Repozytoria i plik sources.list | LN07 §8, LN16 §11 |
| `05_SearchingAndInstallingPackages` | Wyszukiwanie i instalowanie pakietów | LN07 §9-10, LN16 §12 |
| `06_DependenciesExplained` | Zależności pakietów i jak działa APT | LN07 §11, §14 |
| `07_RemovePurgeAndAutoremove` | Usuwanie pakietów: remove, purge, autoremove | LN07 §12-13 |
| `08_DebPackagesAndDpkg` | Pakiety .deb i dpkg | LN24 §2-5 |

### 7. `_lx_07_vim` - Linux - edytor Vim

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_StartingVimAndModes` | Uruchamianie Vima i tryby pracy | LN08 §1-2 |
| `02_SavingAndQuitting` | Zapisywanie i wychodzenie | LN08 §3 |
| `03_MovingAroundTheDocument` | Poruszanie się po dokumencie | LN08 §4 |
| `04_DeletingText` | Usuwanie tekstu | LN08 §5 |
| `05_YankAndPaste` | Kopiowanie i wklejanie (yank/paste) | LN08 §6 |
| `06_ReplacingText` | Zamiana tekstu | LN08 §7 |
| `07_UndoRedoAndRepeat` | Cofanie, ponawianie i powtarzanie | LN08 §8 |
| `08_SearchingInVim` | Wyszukiwanie w Vimie | LN08 §9 |
| `09_AdvancedShortcutsAndWorkflow` | Przydatne skróty i przykładowy scenariusz pracy | LN08 §10-12 |
| `10_VimrcConfiguration` | Plik .vimrc i opcje set | LN09 §1-7 |
| `11_PluginsWithPathogen` | Wtyczki i Pathogen | LN12 §1-4, §7 |
| `12_NativePackagesAndPopularPlugins` | Natywne pakiety Vim 8 i popularne wtyczki | LN12 §5-6, §8-10 |

### 8. `_lx_08_archiwizacja` - Linux - archiwizacja, kompresja i pobieranie plików

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_GzipAndGunzip` | gzip i gunzip - kompresja pojedynczego pliku | LN10 §1-2 |
| `02_TarArchives` | tar - archiwum wielu plików | LN10 §3 |
| `03_ListingAndExtractingTar` | Przeglądanie i rozpakowywanie archiwów tar | LN10 §4-5 |
| `04_TarGzCombined` | Archiwa tar.gz | LN10 §6 |
| `05_StreamingZcatAndTar` | Rozpakowywanie strumieniowe (zcat | tar) | LN10 §7 |
| `06_Bzip2AndFormatComparison` | bzip2 i porównanie formatów kompresji | LN10 §8, §10-11 |
| `07_WgetDownloadingFiles` | wget - pobieranie plików z internetu | LN10 §9 |

### 9. `_lx_09_wyszukiwanie_plikow` - Linux - wyszukiwanie plików

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_FindByName` | find - wyszukiwanie po nazwie | LN11 §1-2, §7 |
| `02_FindWithRegex` | find z wyrażeniami regularnymi (-regex) | LN11 §3 |
| `03_FindByType` | find - wyszukiwanie po typie (-type) | LN11 §4 |
| `04_FindBySize` | find - wyszukiwanie po rozmiarze (-size) | LN11 §5 |
| `05_FindByModificationTime` | find - wyszukiwanie po dacie modyfikacji | LN11 §6 |
| `06_FindExec` | Wykonywanie poleceń na wynikach (-exec) | LN11 §8 |
| `07_FindWithXargs` | find i xargs | LN11 §9, §13 |
| `08_LocateAndIndex` | locate i indeks plików (find a locate) | LN11 §10-11 |
| `09_FileCommand` | file - rozpoznawanie typu pliku | LN11 §12, §14 |

### 10. `_lx_10_grep_regex_awk` - Linux - grep, wyrażenia regularne i awk

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_GrepBasics` | grep - podstawy wyszukiwania tekstu | LN18 §1-2, LN04 §11 |
| `02_GrepOptions` | Opcje grep: -i, -v, -w, -c, -n | LN18 §3, §5-7, §11 |
| `03_GrepRecursiveAndMultipleFiles` | grep rekurencyjnie i w wielu plikach | LN18 §4, §8, §16 |
| `04_GrepWithPipesAndLogs` | grep w potokach i logach systemowych | LN18 §9-10, §15, §17 |
| `05_RegexLiteralsDotAndAnchors` | Regex: dosłowny tekst, kropka, kotwice ^ i $ | LN19 §1-6 |
| `06_RegexCharacterClasses` | Regex: klasy znaków [] i [^] | LN19 §7-8 |
| `07_RegexQuantifiers` | Regex: kwantyfikatory *, +, ?, {n,m} | LN19 §9-12, §15 |
| `08_RegexAlternationAndGroups` | Regex: alternatywa | i grupy () | LN19 §13-14 |
| `09_GrepExtendedAndFixedStrings` | grep -E i grep -F | LN18 §12-14, LN19 §16-20 |
| `10_AwkFieldsAndSyntax` | awk - składnia i pola (kolumny) | LN20 §1-4 |
| `11_AwkConditionsAndRegex` | awk - warunki, if/else i wyrażenia regularne | LN20 §5-6, §10-11 |
| `12_AwkBeginEndAndAggregation` | awk - BEGIN/END, liczenie i sumowanie | LN20 §8-9 |
| `13_AwkOutputFormatting` | awk - separator OFS i printf | LN20 §7, §12-13 |
| `14_GrepVsSedVsAwk` | grep, sed czy awk - dobór narzędzia | LN18 §18-19, LN20 §14-16 |

### 11. `_lx_11_procesy` - Linux - procesy

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_WhatIsAProcess` | Czym jest proces | LN15 §1 |
| `02_CpuScheduler` | Scheduler - jak jądro przydziela procesor | LN15 §2 |
| `03_PsCommand` | ps - wyświetlanie procesów | LN15 §3, §6 |
| `04_ParentAndChildProcesses` | Procesy rodzice i dzieci (PPID, pstree) | LN15 §4 |
| `05_TopAndHtop` | top i htop - monitorowanie na żywo | LN15 §7-8 |
| `06_KillAndSignals` | kill i sygnały | LN15 §9 |
| `07_NiceAndRenice` | nice i renice - priorytety procesów | LN15 §10 |
| `08_BackgroundJobs` | Zadania w tle: &, jobs, bg, fg | LN15 §11 |
| `09_NohupAndClosingTerminal` | nohup i zamykanie terminala | LN15 §5, LN21 §6 |
| `10_DaemonsAndSystemdAsPid1` | Demony i systemd jako proces nr 1 | LN15 §12-14, LN21 §10 |

### 12. `_lx_12_siec_i_ssh` - Linux - sieć i SSH

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_NatVsBridgedNetworking` | Sieć maszyny wirtualnej: NAT a bridged | LN16 §9, LN13 §2 |
| `02_IpAddressClasses` | Adresy IP i ich klasy | LN16 §10 |
| `03_CheckingNetworkConnectivity` | Sprawdzanie adresów i łączności (ifconfig, ip, ping) | LN16 §12, LN13 §3 |
| `04_SshClientServerArchitecture` | SSH - architektura klient-serwer | LN16 §13, LN16-2 §2 |
| `05_InstallingSshAndConnecting` | Instalacja serwera SSH i łączenie się | LN16 §14-15 |
| `06_ScpFileTransfer` | scp - przesyłanie plików przez SSH | LN16 §16, LN16-2 §1, §3 |
| `07_PublicKeyCryptography` | Kryptografia klucza publicznego | LN16-2 §6, §8 |
| `08_PasswordlessSshWithKeys` | Logowanie kluczem: ssh-keygen i ssh-copy-id | LN16-2 §4, §7, §9-13 |
| `09_SshConfigAliases` | Aliasy w ~/.ssh/config | LN16-3 §1-3 |
| `10_ChangingSshPort` | Zmiana portu SSH i sshd_config | LN16-3 §4-7 |
| `11_SshKeysForGitAndGithub` | Klucze SSH dla Gita i GitHuba | LN16-3 §8-15 |
| `12_ServingFilesOverHttp` | Serwowanie plików przez HTTP (python -m http.server) | LN13 §1, §4-10 |

### 13. `_lx_13_rsync` - Linux - synchronizacja i kopie zapasowe (rsync)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_WhatIsRsync` | Czym jest rsync i czym różni się od cp i scp | LN17 §1 |
| `02_DeltaTransferAlgorithm` | Algorytm przesyłania różnic (delta-transfer) | LN17 §2 |
| `03_SyntaxAndArchiveFlag` | Składnia i flaga -a | LN17 §3-5 |
| `04_LocalSynchronization` | Synchronizacja lokalna i ukośnik na końcu ścieżki | LN17 §6 |
| `05_RsyncOverSsh` | rsync przez SSH | LN17 §7 |
| `06_MirrorWithDelete` | Lustro katalogu (--delete) i --dry-run | LN17 §8 |
| `07_ExcludeAndInclude` | Wykluczenia: --exclude i --include | LN17 §9 |
| `08_PermissionsResumeAndBandwidth` | Uprawnienia, wznawianie i limit pasma | LN17 §10-12 |
| `09_RsyncAsBackup` | rsync jako kopia zapasowa i najczęstsze błędy | LN17 §13-15 |

### 14. `_lx_14_porty_i_diagnostyka_sieci` - Linux - porty i diagnostyka sieci

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_WhatIsAPort` | Czym jest port (TCP i UDP) | LN26 §3, §7 |
| `02_NetstatBasics` | netstat - instalacja i podstawowe użycie | LN27 §1-4 |
| `03_ListeningPortsNetstatPlnt` | Porty nasłuchujące: netstat -plnt | LN27 §5-6, §8-10 |
| `04_TcpConnectionStates` | Stany połączeń TCP | LN27 §7 |
| `05_NetstatWithPipesAndRoot` | netstat w potokach i uprawnienia roota | LN27 §11-12, §14-16 |
| `06_SsModernReplacement` | ss - nowoczesny następca netstat | LN27 §13 |
| `07_NmapBasicScans` | nmap - podstawowe skanowanie hostów i portów | LN26 §1-2, §4-6, §11 |
| `08_TcpScanTypes` | Rodzaje skanów TCP i skanowanie UDP | LN26 §7-8 |
| `09_ServiceAndOsDetection` | Wykrywanie usług, wersji i systemu | LN26 §9-10, §12 |
| `10_NmapScriptsAndLocalNetwork` | Skrypty NSE, skan sieci lokalnej i zapis wyników | LN26 §13-15 |
| `11_ScanningLegalityAndZenmap` | Legalność skanowania i Zenmap | LN26 §16-20 |

### 15. `_lx_15_systemd` - Linux - usługi i systemd

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_WhatIsSystemd` | Czym jest systemd | LN22 §1, LN30 §1 |
| `02_DaemonsAndServices` | Demony i usługi | LN22 §2, LN21 §10 |
| `03_UnitFilesStructure` | Pliki jednostek .service i ich sekcje | LN22 §3-5 |
| `04_SystemctlStartStopStatus` | systemctl: start, stop, restart, status | LN22 §10 |
| `05_EnableAndAutostart` | Autostart usług: systemctl enable | LN22 §9, §11 |
| `06_OverridesAndDaemonReload` | Nadpisywanie jednostek i daemon-reload | LN22 §6-8, §12-13 |
| `07_JournalctlLogs` | journalctl - logi usług (uzupełnienie) | brak w notatkach - uzupełnienie |

### 16. `_lx_16_serwer_www` - Linux - serwer: Java, Apache, PHP i MySQL

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_JdkVsJreInstallation` | JDK i JRE - instalacja przez APT | LN21 §1-2 |
| `02_EnvironmentVariablesJavaHomeAndPath` | Zmienne środowiskowe: JAVA_HOME i PATH | LN21 §3 |
| `03_ChecksumsSha256` | Sumy kontrolne SHA-256 | LN21 §4 |
| `04_MavenInstallation` | Instalacja Mavena | LN21 §5 |
| `05_ApacheHttpServerBasics` | Apache HTTP Server - instalacja i sprawdzanie działania | LN21 §7-9, §13 |
| `06_ApacheStructureAndConfigFiles` | Struktura Apache i pliki konfiguracyjne | LN21 §11-12 |
| `07_SharedLibrariesAndApacheModules` | Biblioteki .so, moduły Apache i apxs2 | LN23 §2-5 |
| `08_BuildingPhpFromSource` | Kompilacja PHP ze źródeł | LN23 §1, §6-8 |
| `09_IntegratingPhpWithApache` | Rejestracja PHP w Apache i test | LN23 §9-14 |
| `10_MysqlInstallation` | Instalacja MySQL Community | LN24 §1, §3-7 |
| `11_MysqlDataAndBackup` | Dane MySQL i kopie zapasowe | LN24 §8-9, §12-14 |
| `12_PhpMyAdmin` | phpMyAdmin - instalacja i dostęp | LN24 §10-11, LN25 §1-4 |
| `13_DirectoryIndexConfiguration` | DirectoryIndex i restart Apache | LN25 §5-14 |

### 17. `_lx_17_powloki_i_terminale` - Linux - powłoki i terminale

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_BashVsZsh` | bash a zsh | LN28 §2 |
| `02_OhMyZshInstallation` | Oh My Zsh - czym jest i jak go zainstalować | LN28 §3-4, §8 |
| `03_ZshrcAndPlugins` | Plik .zshrc i wtyczki | LN28 §5-6 |
| `04_Powerlevel10kTheme` | Motyw Powerlevel10k | LN28 §7 |
| `05_TerminatorAndGuake` | Terminator i Guake | LN28 §9-15 |

### 18. `_lx_18_wsl` - Linux - WSL (Windows Subsystem for Linux)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_WhatIsWsl` | Czym jest WSL | LN29 §1 |
| `02_Wsl1VsWsl2` | WSL1 a WSL2 | LN29 §2 |
| `03_InstallingWslAndDistributions` | Instalacja WSL i dystrybucje | LN29 §3-4, §8 |
| `04_WslFilesAndWindowsDrives` | Gdzie WSL trzyma dane i dostęp do dysków Windows | LN29 §5-6 |
| `05_WslLimitations` | Ograniczenia WSL względem prawdziwego Linuksa | LN29 §7, §15 |
| `06_GuiAppsAndXServer` | Aplikacje graficzne i X Server | LN29 §9-14 |
| `07_SystemdInWsl2` | systemd w WSL2 | LN30 §2-13 |
| `08_WslVsVmVsServer` | WSL, maszyna wirtualna czy serwer | LN30 §14-16, LN29 §16 |

### 19. `_lx_19_docker_na_wsl2` - Linux - Docker na WSL2

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_DockerVsVirtualMachine` | Docker a maszyna wirtualna | LN31 §1-2 |
| `02_WhyDockerNeedsLinux` | Dlaczego Docker potrzebuje jądra Linuksa | LN31 §3 |
| `03_DockerDesktopAndWsl2Architecture` | Docker Desktop i architektura na WSL2 | LN31 §4-7 |
| `04_PortsAndContainerCommunication` | Porty i komunikacja kontenerów | LN31 §8, §12-13 |
| `05_VolumesAndFileSystemPerformance` | Wolumeny i wydajność systemu plików | LN31 §9-10, §17 |
| `06_DockerComposeInWsl2` | Docker Compose w WSL2 | LN31 §11 |
| `07_DebuggingLimitationsAndWhenToUse` | Diagnostyka, ograniczenia i kiedy to ma sens | LN31 §14-16, §18-20 |

### 20. `_lx_20_skrypty_bash` - Linux - skrypty powłoki bash (uzupełnienie)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_FirstScriptAndShebang` | Pierwszy skrypt i shebang | brak w notatkach - uzupełnienie |
| `02_VariablesAndEnvironment` | Zmienne w skryptach i zmienne środowiskowe | brak w notatkach - uzupełnienie (nawiązanie: LN21 §3) |
| `03_ConditionsAndTests` | Warunki i testy (if, [[ ]]) | brak w notatkach - uzupełnienie |
| `04_Loops` | Pętle for i while | brak w notatkach - uzupełnienie |
| `05_ArgumentsAndExitCodes` | Argumenty skryptu i kody wyjścia | brak w notatkach - uzupełnienie |
| `06_FunctionsInBash` | Funkcje w bashu | brak w notatkach - uzupełnienie |
| `07_CronScheduling` | Harmonogram zadań: cron | brak w notatkach - uzupełnienie (nawiązanie: LN17 §13 backup) |

