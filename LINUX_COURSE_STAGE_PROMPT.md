# JavaQuest — Trzeci blok platformy: kurs Linux

> JEDYNE, obowiązujące źródło instrukcji dla pracy nad KURSEM LINUX (trzeci tor platformy, obok
> Javy i JavaScriptu). Stan prac: `LINUX_COURSE_WORK_PROGRESS.md`. Format lekcji, model danych
> i standard sekcji teorii są TE SAME co w kursie JavaScript (`JS_COURSE_STAGE_PROMPT.md`,
> sekcje „Model danych i standard 11 sekcji”, „Polskie znaki, jakość języka, weryfikacja”) —
> ten plik opisuje tylko to, co jest specyficzne dla Linuksa.

## Kontekst i cel

Panel główny ma przełącznik trzech kursów: **Java** (`/`, domyślny), **JavaScript** (`/js`)
i **Linux** (`/linux`). **Dyrektywa użytkownika (2026-09-27): kurs ma być „kozak merytorycznie”
— dużo bardziej rozbudowany niż same notatki, więcej rozdziałów i lekcji.** Zakres: pełny
program od pierwszego polecenia do samodzielnego przygotowania serwera produkcyjnego, na
poziomie zbliżonym do certyfikatów LPIC-1 / RHCSA, z naciskiem na praktykę programisty Javy
(serwer aplikacji, systemd, logi, sieć, SSH, bezpieczeństwo, Docker, skrypty).

Cztery części: **I. Fundamenty i praca w terminalu** (rozdziały 1-15), **II. Administracja
systemem** (16-29), **III. Sieć i serwery** (30-39), **IV. Automatyzacja, narzędzia i
środowiska** (40-46, w tym projekt końcowy: serwer dla aplikacji Spring Boot).

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
- **Notatki pokrywają 234 z 437 lekcji** (kolumna „Źródło” w tabeli). Pozostałe lekcje,
  oznaczone „uzupełnienie”, piszesz BEZ materiału źródłowego — na podstawie dokumentacji
  (strony `man`, dokumentacja dystrybucji, systemd, OpenSSH, Nginx itd.) i ZAWSZE z
  weryfikacją poleceń w prawdziwym systemie. Oznaczenie „LN.. + uzupełnienie” = notatka jest
  punktem wyjścia, ale nie wystarcza do pełnej lekcji.
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

## Struktura: 46 rozdziałów, 437 lekcji

## Część I. Fundamenty i praca w terminalu

### 1. `_lx_01_wprowadzenie` - Linux - wprowadzenie, historia i dystrybucje (8 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_WhatIsLinux` | Czym jest Linux: jądro, system GNU i otwarte oprogramowanie | uzupełnienie |
| `02_UnixAndLinuxHistory` | Historia: Unix, projekt GNU i Linus Torvalds | uzupełnienie |
| `03_KernelVsDistribution` | Jądro a dystrybucja - co dokłada dystrybutor | LN16 §1 + uzupełnienie |
| `04_DistributionFamilies` | Rodziny dystrybucji: Debian/Ubuntu, RHEL/Fedora/Rocky, Arch, Alpine | LN16 §1 + uzupełnienie |
| `05_ReleaseCyclesAndLts` | Cykle wydań, wersje LTS i okres wsparcia | uzupełnienie |
| `06_DesktopEnvironmentsVsServers` | Środowiska graficzne (GNOME, KDE, LXQt) a serwer bez GUI | LN16 §1 + uzupełnienie |
| `07_OpenSourceLicenses` | Licencje: GPL, MIT, Apache - co wolno z kodem | LN19 §2 (GPL-3) + uzupełnienie |
| `08_LinuxInJavaDeveloperWork` | Gdzie programista Javy spotyka Linuksa (serwery, Docker, CI, chmura) | uzupełnienie |

### 2. `_lx_02_wirtualizacja` - Linux - wirtualizacja i środowisko do nauki (10 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_WhatIsVirtualization` | Czym jest wirtualizacja | LN01 §1 |
| `02_HypervisorTypes` | Typy hypervisorów (typ 1 i typ 2) | LN01 §2 |
| `03_VmwareAndVirtualBox` | VMware i VirtualBox | LN01 §3-4, LN16 §4 |
| `04_VirtualizationAndCpuArchitecture` | Wirtualizacja a architektura procesora (VT-x, AMD-V, ARM) | LN01 §5-6 |
| `05_InstallingLinuxInVm` | Instalacja dystrybucji w maszynie wirtualnej (na przykładzie Lubuntu) | LN16 §1 + uzupełnienie |
| `06_VirtualDiskFilesAndReadyMachines` | Pliki dysków VDI/VMDK i gotowe maszyny (OSBoxes) | LN16 §2-3 |
| `07_VmResourcesRamAndCpu` | Przydział pamięci RAM i procesorów do VM | LN16 §8 |
| `08_ResizingVirtualDisks` | Powiększanie dysku VM (VBoxManage, GParted) | LN16 §5-7 |
| `09_SnapshotsAndCloning` | Migawki i klonowanie maszyn wirtualnych | uzupełnienie |
| `10_GuestAdditionsAndSharedFolders` | Dodatki gościa, schowek i foldery współdzielone | uzupełnienie |

### 3. `_lx_03_terminal_i_powloka` - Linux - terminal i powłoka: pierwsze kroki (11 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_TerminalShellAndConsole` | Terminal, powłoka i konsola - czym się różnią | LN28 §1 |
| `02_PromptAndCommandStructure` | Znak zachęty i budowa polecenia: opcje i argumenty | uzupełnienie |
| `03_GettingHelpManAndHelp` | Pomoc: man, --help i info | LN07 §4 + uzupełnienie |
| `04_WhatisAproposAndTldr` | whatis, apropos i tldr - szukanie właściwego polecenia | uzupełnienie |
| `05_TypeWhichAndWhereis` | type, which, whereis - skąd pochodzi polecenie | LN07 §3 + uzupełnienie |
| `06_BuiltinsVsExternalCommands` | Polecenia wbudowane powłoki a programy zewnętrzne | uzupełnienie |
| `07_CommandHistory` | Historia poleceń: history, !!, !n, Ctrl+R | uzupełnienie |
| `08_ReadlineKeyboardShortcuts` | Skróty klawiszowe w bashu (Readline) | uzupełnienie |
| `09_TabCompletion` | Uzupełnianie klawiszem Tab | uzupełnienie |
| `10_ExitStatusAndCommandChaining` | Kod wyjścia i łączenie poleceń: ;, &&, || | uzupełnienie |
| `11_AliasesBasics` | Aliasy - skróty własnych poleceń | uzupełnienie |

### 4. `_lx_04_hierarchia_katalogow` - Linux - hierarchia katalogów i ścieżki (8 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_EverythingIsAFile` | Zasada: wszystko jest plikiem | LN03 §1, §5 |
| `02_RootDirectoryAndFhs` | Katalog główny / i standard FHS | LN03 §1, §17 |
| `03_AbsoluteAndRelativePaths` | Ścieżki bezwzględne i względne: ~, ., .. | LN04 §2 + uzupełnienie |
| `04_BinSbinLibAndUsr` | Programy i biblioteki: /bin, /sbin, /lib, /usr | LN03 §2-3, §8, §15 |
| `05_EtcHomeAndRoot` | Konfiguracja i katalogi domowe: /etc, /home, /root | LN03 §6-7, §12 |
| `06_BootDevProcAndSys` | Start, urządzenia i jądro: /boot, /dev, /proc, /sys | LN03 §4-5, §11, §16 |
| `07_VarTmpOptAndMnt` | Dane zmienne i dodatkowe: /var, /tmp, /opt, /mnt, /media | LN03 §9-10, §13-14 |
| `08_UsrMergeAndModernLayout` | Scalenie /usr i współczesny układ katalogów | LN03 §2-3 + uzupełnienie |

### 5. `_lx_05_operacje_na_plikach` - Linux - operacje na plikach i katalogach (15 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_LsListingFiles` | ls - listowanie plików i katalogów | LN04 §1 |
| `02_LsLongFormatExplained` | Format długi ls -l krok po kroku | LN04 §1, LN06 §1 |
| `03_CdAndPwdNavigation` | cd i pwd - poruszanie się po katalogach | LN04 §2-3 |
| `04_HiddenFilesAndDotfiles` | Pliki ukryte i pliki konfiguracyjne (dotfiles) | LN07 §1 |
| `05_TouchAndTimestamps` | touch i znaczniki czasu (atime, mtime, ctime) | LN04 §4 + uzupełnienie |
| `06_MkdirAndRmdir` | mkdir -p i rmdir | LN04 §8 |
| `07_CpCopying` | cp - kopiowanie (-r, -a, -i, -u) | LN04 §9 |
| `08_MvMovingAndRenaming` | mv - przenoszenie i zmiana nazwy | LN04 §7 |
| `09_RmDeletingSafely` | rm - bezpieczne usuwanie | LN04 §10 |
| `10_GlobbingWildcards` | Symbole wieloznaczne: *, ?, [...] | LN04 §1 (ls *.txt, a*) + uzupełnienie |
| `11_BraceExpansion` | Rozwijanie nawiasów: {a,b} i {1..10} | uzupełnienie |
| `12_HardAndSymbolicLinks` | Dowiązania twarde i symboliczne, i-węzły | uzupełnienie |
| `13_StatAndFileMetadata` | stat - metadane pliku | uzupełnienie |
| `14_FileTypeDetection` | file - rozpoznawanie typu pliku | LN11 §12 |
| `15_TreeDuAndDf` | tree, du i df - struktura katalogów i zajętość miejsca | uzupełnienie |

### 6. `_lx_06_przegladanie_tekstu` - Linux - przeglądanie i proste przetwarzanie tekstu (13 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_CatTacAndNl` | cat, tac i nl | LN04 §5 |
| `02_LessAndMore` | less i more - przeglądanie długich plików | uzupełnienie |
| `03_HeadAndTail` | head i tail, śledzenie pliku tail -f | LN04 §13 |
| `04_WcCounting` | wc - liczenie linii, słów i znaków | LN04 §13 |
| `05_SortSorting` | sort - sortowanie (liczbowe, po kolumnie, odwrotne) | LN04 §12 |
| `06_UniqDuplicates` | uniq - duplikaty i zliczanie wystąpień | uzupełnienie |
| `07_CutColumns` | cut - wycinanie kolumn | uzupełnienie |
| `08_PasteAndJoin` | paste i join - łączenie plików | uzupełnienie |
| `09_TrTranslatingCharacters` | tr - zamiana i usuwanie znaków | uzupełnienie |
| `10_TeeSplittingOutput` | tee - rozdzielanie strumienia | uzupełnienie |
| `11_DiffCommAndCmp` | diff, comm i cmp - porównywanie plików | uzupełnienie |
| `12_ColumnAndPrintf` | column i printf - formatowanie wyników | uzupełnienie |
| `13_EncodingsAndLineEndings` | Kodowania znaków i końce linii (iconv, dos2unix) | uzupełnienie |

### 7. `_lx_07_strumienie_i_potoki` - Linux - strumienie, przekierowania i potoki (12 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_StdinStdoutStderr` | Standardowe wejście, wyjście i wyjście błędów | LN05 §1, §3 |
| `02_FileDescriptors` | Deskryptory plików 0, 1, 2 | LN05 §2, §9 |
| `03_OutputRedirection` | Przekierowanie wyjścia: > i >> | LN05 §4 |
| `04_ErrorRedirection` | Przekierowanie błędów: 2>, 2>&1, &> | LN05 §5 |
| `05_InputRedirectionAndHereDoc` | Przekierowanie wejścia: < i here-doc << | LN05 §6 |
| `06_HereStrings` | Here-string <<< | uzupełnienie |
| `07_Pipes` | Potoki | - łączenie poleceń | LN04 §6, LN14 §1, LN05 §7 |
| `08_DevNullAndPracticalScenarios` | /dev/null i praktyczne kombinacje przekierowań | LN05 §8, §10 |
| `09_CommandSubstitution` | Podstawianie poleceń $( ) | uzupełnienie |
| `10_XargsBuildingCommands` | xargs - budowanie poleceń z wejścia | LN11 §9 + uzupełnienie |
| `11_ProcessSubstitution` | Podstawianie procesów <( ) i >( ) | uzupełnienie |
| `12_NamedPipesFifo` | Potoki nazwane (mkfifo) | uzupełnienie |

### 8. `_lx_08_edytory_nano_i_vim` - Linux - edytory: nano i Vim od podstaw (10 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_NanoEditor` | nano - prosty edytor na start | uzupełnienie |
| `02_StartingVimAndModes` | Uruchamianie Vima i tryby pracy | LN08 §1-2 |
| `03_SavingAndQuitting` | Zapisywanie i wychodzenie | LN08 §3 |
| `04_MovingAroundTheDocument` | Poruszanie się po dokumencie | LN08 §4 |
| `05_DeletingText` | Usuwanie tekstu | LN08 §5 |
| `06_YankAndPaste` | Kopiowanie i wklejanie (yank/paste) | LN08 §6 |
| `07_ReplacingText` | Zamiana tekstu | LN08 §7 |
| `08_UndoRedoAndRepeat` | Cofanie, ponawianie i powtarzanie kropką | LN08 §8 |
| `09_SearchingInVim` | Wyszukiwanie w Vimie | LN08 §9 |
| `10_EverydayWorkflow` | Najprzydatniejsze skróty i codzienny scenariusz pracy | LN08 §10-12 |

### 9. `_lx_09_vim_zaawansowany` - Linux - Vim zaawansowany (10 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_OperatorsAndMotions` | Gramatyka Vima: operatory i ruchy | LN08 §10 + uzupełnienie |
| `02_TextObjects` | Obiekty tekstowe (ciw, di", dap) | uzupełnienie |
| `03_VisualAndBlockMode` | Tryb wizualny i blokowy | uzupełnienie |
| `04_SubstituteWithRegex` | Zamiana :s z wyrażeniami regularnymi i zakresami | LN08 §7 + uzupełnienie |
| `05_RegistersAndClipboard` | Rejestry i schowek systemowy | uzupełnienie |
| `06_Macros` | Makra - nagrywanie i odtwarzanie | uzupełnienie |
| `07_BuffersWindowsAndTabs` | Bufory, okna i karty | uzupełnienie |
| `08_VimrcConfiguration` | Plik .vimrc i opcje set | LN09 §1-7 |
| `09_PluginsWithPathogen` | Wtyczki i Pathogen | LN12 §1-4, §7 |
| `10_NativePackagesAndPopularPlugins` | Natywne pakiety Vim 8+ i popularne wtyczki (NERDTree, Lightline) | LN12 §5-6, §8-10 |

### 10. `_lx_10_wyszukiwanie_plikow` - Linux - wyszukiwanie plików (10 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_FindByName` | find - wyszukiwanie po nazwie i ścieżce | LN11 §1-2, §7 |
| `02_FindWithRegex` | find z wyrażeniami regularnymi (-regex) | LN11 §3 |
| `03_FindByType` | find - wyszukiwanie po typie (-type) | LN11 §4 |
| `04_FindBySize` | find - wyszukiwanie po rozmiarze (-size) | LN11 §5 |
| `05_FindByModificationTime` | find - wyszukiwanie po czasie modyfikacji (-mtime, -mmin) | LN11 §6 |
| `06_FindByPermissionsAndOwner` | find - po uprawnieniach i właścicielu (-perm, -user) | uzupełnienie |
| `07_FindLogicalOperatorsAndPrune` | find - operatory logiczne i pomijanie katalogów (-prune) | uzupełnienie |
| `08_FindExec` | Wykonywanie poleceń na wynikach (-exec, -delete) | LN11 §8 |
| `09_FindWithXargs` | find i xargs - wydajne operacje masowe | LN11 §9, §13 |
| `10_LocateAndIndex` | locate i indeks plików, find a locate | LN11 §10-11, §14 |

### 11. `_lx_11_grep_i_regex` - Linux - grep i wyrażenia regularne (14 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_GrepBasics` | grep - podstawy wyszukiwania tekstu | LN18 §1-2, LN04 §11 |
| `02_GrepOptions` | Opcje grep: -i, -v, -w, -c, -n, -l | LN18 §3, §5-7, §11 |
| `03_GrepRecursiveAndMultipleFiles` | grep rekurencyjnie i w wielu plikach | LN18 §4, §8, §16 |
| `04_GrepContextLines` | Linie kontekstu: -A, -B, -C | uzupełnienie |
| `05_GrepWithPipesAndLogs` | grep w potokach i logach systemowych | LN18 §9-10, §15, §17 |
| `06_RegexLiteralsDotAndAnchors` | Regex: dosłowny tekst, kropka, kotwice ^ i $ | LN19 §1-6 |
| `07_RegexCharacterClasses` | Regex: klasy znaków [], [^] i klasy POSIX | LN19 §7-8 + uzupełnienie |
| `08_RegexQuantifiers` | Regex: kwantyfikatory *, +, ?, {n,m} | LN19 §9-12, §15 |
| `09_RegexAlternationAndGroups` | Regex: alternatywa | i grupy () | LN19 §13-14 |
| `10_BackreferencesAndWordBoundaries` | Odwołania wsteczne i granice słów | uzupełnienie |
| `11_BreEreAndPcre` | BRE, ERE i PCRE: grep, grep -E, grep -P | LN18 §12-13 + uzupełnienie |
| `12_GrepFixedStrings` | grep -F - wyszukiwanie bez wyrażeń regularnych | LN18 §14 |
| `13_RegexPracticeOnRealFiles` | Ćwiczenia na prawdziwym pliku (licencja GPL): komentarze, puste linie, liczenie | LN19 §2-3, §16-20 |
| `14_RipgrepModernAlternative` | ripgrep - nowoczesna alternatywa | uzupełnienie |

### 12. `_lx_12_sed` - Linux - sed: edytor strumieniowy (8 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_WhatIsSed` | Czym jest sed i kiedy go używać | LN18 §18 + uzupełnienie |
| `02_SubstituteCommand` | Polecenie s///: flagi g, i i numer wystąpienia | uzupełnienie |
| `03_AddressesAndRanges` | Adresy i zakresy linii | uzupełnienie |
| `04_DeletePrintInsertAppend` | d, p, -n, i, a, c | uzupełnienie |
| `05_InPlaceEditing` | Edycja w miejscu (-i) i kopie zapasowe | uzupełnienie |
| `06_SedRegexGroups` | Grupy i odwołania w sed | uzupełnienie |
| `07_MultipleCommandsAndScripts` | Wiele poleceń (-e, ;) i pliki skryptów sed | uzupełnienie |
| `08_EditingConfigFilesWithSed` | Praktyka: edycja plików konfiguracyjnych sedem | uzupełnienie |

### 13. `_lx_13_awk` - Linux - awk: przetwarzanie kolumn i raporty (11 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_AwkFieldsAndSyntax` | awk - składnia i pola (kolumny) | LN20 §1-4 |
| `02_FieldSeparatorsFsAndOfs` | Separatory pól: -F, FS i OFS | LN20 §3, §7 |
| `03_AwkPatternsAndConditions` | Wzorce, warunki i if/else | LN20 §5, §11 |
| `04_AwkWithPipes` | awk w potokach | LN20 §6 |
| `05_AwkBeginAndEnd` | Bloki BEGIN i END | LN20 §8 |
| `06_AwkVariablesAndAggregation` | Zmienne, liczenie i sumowanie | LN20 §9 |
| `07_AwkRegex` | awk z wyrażeniami regularnymi | LN20 §10 |
| `08_AwkAssociativeArrays` | Tablice asocjacyjne - grupowanie danych | uzupełnienie |
| `09_AwkPrintfAndBuiltinFunctions` | printf i funkcje wbudowane (length, substr, split, toupper) | LN20 §12 + uzupełnienie |
| `10_AwkReportsFromLogs` | Raporty z logów - scenariusze z praktyki | LN20 §13 |
| `11_GrepSedOrAwk` | grep, sed czy awk - dobór narzędzia i częste błędy | LN18 §18-20, LN20 §14-16 |

### 14. `_lx_14_archiwizacja` - Linux - archiwizacja i kompresja (8 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_GzipAndGunzip` | gzip i gunzip - kompresja pojedynczego pliku | LN10 §1-2 |
| `02_TarArchives` | tar - archiwum wielu plików | LN10 §3 |
| `03_ListingAndExtractingTar` | Przeglądanie i rozpakowywanie archiwów tar | LN10 §4-5 |
| `04_TarGzCombined` | Archiwa tar.gz | LN10 §6 |
| `05_StreamingZcatAndTar` | Rozpakowywanie strumieniowe (zcat | tar) | LN10 §7 |
| `06_Bzip2XzAndZstd` | bzip2, xz i zstd - porównanie formatów | LN10 §8, §10-11 + uzupełnienie |
| `07_ZipAndUnzip` | zip i unzip - zgodność z Windows | uzupełnienie |
| `08_ArchivingBestPractices` | Dobre praktyki: -C, wykluczenia, uprawnienia | uzupełnienie |

### 15. `_lx_15_wget_i_curl` - Linux - pobieranie danych i HTTP: wget i curl (6 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_WgetDownloadingFiles` | wget - pobieranie plików z internetu | LN10 §9 |
| `02_WgetResumeAndRecursive` | wget - wznawianie i pobieranie rekurencyjne | uzupełnienie |
| `03_CurlBasics` | curl - podstawy | LN13 §9 + uzupełnienie |
| `04_CurlMethodsHeadersAndBody` | curl - metody HTTP, nagłówki i ciało żądania | uzupełnienie |
| `05_CurlTestingRestApis` | curl do testowania REST API aplikacji Spring Boot | uzupełnienie |
| `06_ChecksumsSha256` | Sumy kontrolne SHA-256 - weryfikacja pobranych plików | LN21 §4 |

## Część II. Administracja systemem

### 16. `_lx_16_uzytkownicy_i_grupy` - Linux - użytkownicy i grupy (12 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_UsersUidsAndSystemAccounts` | Użytkownicy, UID i konta systemowe | LN14 §2 + uzupełnienie |
| `02_EtcPasswd` | Plik /etc/passwd | LN14 §2, §9 |
| `03_EtcShadow` | Plik /etc/shadow - hasła | LN14 §6 |
| `04_GroupsAndEtcGroup` | Grupy i plik /etc/group | uzupełnienie |
| `05_CreatingUsersUseradd` | Tworzenie użytkowników (useradd, adduser) | LN14 §5, LN16-2 §5 |
| `06_ModifyingAndDeletingUsers` | Modyfikowanie i usuwanie (usermod, userdel, groupadd) | uzupełnienie |
| `07_PasswordsAndAging` | Hasła i ich ważność (passwd, chage) | uzupełnienie |
| `08_LoginProcess` | Jak działa logowanie | LN14 §7 |
| `09_ShellsAndNologin` | Powłoki użytkowników i konta nologin | LN14 §3-4, §8 |
| `10_SuAndSudo` | su i sudo - praca z uprawnieniami roota | LN16 §17 |
| `11_SudoersConfiguration` | Konfiguracja sudoers (visudo) | uzupełnienie |
| `12_WhoIsLoggedIn` | id, whoami, who, w, last | uzupełnienie |

### 17. `_lx_17_uprawnienia` - Linux - prawa dostępu (10 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_ReadingPermissions` | Odczytywanie praw dostępu (ls -la) | LN06 §1 |
| `02_ChmodNumeric` | chmod liczbowy (755, 644, 600) | LN06 §2, §6 |
| `03_ChmodSymbolic` | chmod symboliczny (u+x, go-w) | LN06 §3 |
| `04_FileVsDirectoryPermissions` | Prawa do plików a prawa do katalogów | LN06 §4 |
| `05_UmaskDefaultPermissions` | umask - domyślne prawa nowych plików | LN06 §5 |
| `06_ChownAndChgrp` | chown i chgrp - właściciel i grupa | LN06 §7-8 |
| `07_SuidSgidAndStickyBit` | Bity specjalne: SUID, SGID i sticky | uzupełnienie |
| `08_AccessControlLists` | Listy ACL: setfacl i getfacl | uzupełnienie |
| `09_FileAttributesChattr` | Atrybuty plików: chattr i lsattr | uzupełnienie |
| `10_PermissionTroubleshooting` | Diagnozowanie problemów z uprawnieniami w praktyce | LN06 §9-10 |

### 18. `_lx_18_pakiety_debian` - Linux - pakiety w Debianie i Ubuntu (APT, dpkg) (11 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_WhatIsApt` | APT - menedżer pakietów | LN07 §2-4 |
| `02_UpdateVsUpgrade` | apt update a apt upgrade (i full-upgrade) | LN07 §6-7 |
| `03_RepositoriesAndSourcesList` | Repozytoria i sources.list | LN07 §8, LN16 §11 |
| `04_SearchingAndInstallingPackages` | Wyszukiwanie i instalowanie pakietów | LN07 §9-10, LN16 §12 |
| `05_ListingAndInspectingPackages` | Lista i szczegóły pakietów (apt list, apt show, apt-cache policy) | LN07 §5 + uzupełnienie |
| `06_DependenciesExplained` | Zależności i jak APT je rozwiązuje | LN07 §11, §14 |
| `07_RemovePurgeAndAutoremove` | Usuwanie: remove, purge, autoremove | LN07 §12-13, §15 |
| `08_DebPackagesAndDpkg` | Pakiety .deb i dpkg | LN24 §2-5 |
| `09_ThirdPartyReposAndGpgKeys` | Repozytoria zewnętrzne, PPA i klucze GPG | uzupełnienie |
| `10_HoldingAndPinningVersions` | Blokowanie i przypinanie wersji pakietów | uzupełnienie |
| `11_UnattendedUpgrades` | Automatyczne aktualizacje bezpieczeństwa | uzupełnienie |

### 19. `_lx_19_pakiety_inne_dystrybucje` - Linux - pakiety w innych dystrybucjach i formaty uniwersalne (6 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_DnfAndYum` | dnf i yum (Fedora, RHEL, Rocky) | uzupełnienie |
| `02_RpmPackages` | Pakiety .rpm i polecenie rpm | uzupełnienie |
| `03_ApkInAlpine` | apk w Alpine (obrazy Dockera) | uzupełnienie |
| `04_PacmanInArch` | pacman w Arch Linux | uzupełnienie |
| `05_SnapAndFlatpak` | Snap i Flatpak | uzupełnienie |
| `06_SdkmanForJavaTools` | SDKMAN - wiele wersji JDK, Mavena i Gradle | uzupełnienie |

### 20. `_lx_20_kompilacja_ze_zrodel` - Linux - kompilacja ze źródeł i biblioteki (7 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_WhyBuildFromSource` | Kiedy i po co instalować ze źródeł | LN23 §1 |
| `02_BuildToolchainGccAndMake` | Narzędzia kompilacji: gcc, make, build-essential | uzupełnienie |
| `03_ConfigureMakeInstall` | ./configure, make, make install | LN21 §9, LN23 §6-8 |
| `04_SharedLibrariesSo` | Biblioteki dynamiczne .so | LN23 §3 |
| `05_LddAndLdconfig` | ldd i ldconfig - zależności bibliotek | uzupełnienie |
| `06_ApacheModulesAndApxs` | Moduły dynamiczne Apache i apxs2 | LN23 §4-5 |
| `07_UninstallingSourceBuilds` | Usuwanie programów zainstalowanych ze źródeł | uzupełnienie |

### 21. `_lx_21_procesy` - Linux - procesy i sygnały (13 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_WhatIsAProcess` | Czym jest proces | LN15 §1 |
| `02_CpuScheduler` | Scheduler - jak jądro przydziela procesor | LN15 §2 |
| `03_PsCommand` | ps - wyświetlanie procesów | LN15 §3, §6 |
| `04_ParentAndChildProcesses` | Procesy rodzice i dzieci (PPID, pstree) | LN15 §4 |
| `05_ProcFilesystemForProcesses` | /proc/<PID> - proces od środka | LN03 §11 + uzupełnienie |
| `06_TopAndHtop` | top i htop - monitorowanie na żywo | LN15 §7-8 |
| `07_SignalsExplained` | Sygnały: SIGTERM, SIGKILL, SIGHUP, SIGINT | uzupełnienie |
| `08_KillPkillAndKillall` | kill, pkill i killall | LN15 §9 |
| `09_NiceAndRenice` | nice i renice - priorytety procesów | LN15 §10 |
| `10_BackgroundJobs` | Zadania w tle: &, jobs, bg, fg | LN15 §11 |
| `11_NohupAndDisown` | nohup, disown i zamykanie terminala | LN15 §5, LN21 §6 |
| `12_ZombiesAndOrphans` | Procesy zombie i sieroty | uzupełnienie |
| `13_DaemonsAndPid1` | Demony i systemd jako proces nr 1 | LN15 §12-14 |

### 22. `_lx_22_systemd` - Linux - usługi i systemd (11 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_WhatIsSystemd` | Czym jest systemd | LN22 §1, LN30 §1 |
| `02_DaemonsAndServices` | Demony i usługi | LN22 §2, LN21 §10 |
| `03_UnitTypes` | Typy jednostek: service, socket, timer, target, mount | uzupełnienie |
| `04_UnitFilesStructure` | Pliki jednostek .service i ich sekcje | LN22 §3-5 |
| `05_SystemctlStartStopStatus` | systemctl: start, stop, restart, reload, status | LN22 §10 |
| `06_EnableAndAutostart` | Autostart usług: enable i disable | LN22 §9, §11 |
| `07_OverridesAndDaemonReload` | Nadpisywanie jednostek (edit, drop-in) i daemon-reload | LN22 §6-8, §12-13 |
| `08_WritingOwnServiceUnit` | Własna usługa od zera | LN22 §7 + uzupełnienie |
| `09_RestartPoliciesAndDependencies` | Polityki restartu i zależności między usługami | uzupełnienie |
| `10_TargetsAndSystemStates` | Targety i stany systemu | uzupełnienie |
| `11_SystemdTimers` | Timery systemd | uzupełnienie |

### 23. `_lx_23_start_systemu` - Linux - proces uruchamiania systemu (7 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_BootSequenceOverview` | Kolejność uruchamiania - przegląd | LN03 §4 + uzupełnienie |
| `02_BiosAndUefi` | BIOS i UEFI | uzupełnienie |
| `03_GrubBootloader` | Program rozruchowy GRUB | uzupełnienie |
| `04_KernelAndInitramfs` | Jądro i initramfs | LN03 §4 + uzupełnienie |
| `05_InitSystemAndDefaultTarget` | System init i domyślny target | uzupełnienie |
| `06_RescueAndEmergencyMode` | Tryb ratunkowy i awaryjny | uzupełnienie |
| `07_ShutdownRebootAndPower` | Wyłączanie i restart systemu | LN16 §18 + uzupełnienie |

### 24. `_lx_24_dyski_i_montowanie` - Linux - dyski, partycje i montowanie (10 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_BlockDevicesAndNaming` | Urządzenia blokowe i nazwy (sda, nvme0n1) | LN03 §5 + uzupełnienie |
| `02_LsblkBlkidAndUuid` | lsblk, blkid i identyfikatory UUID | uzupełnienie |
| `03_PartitionTablesMbrAndGpt` | Tablice partycji MBR i GPT | uzupełnienie |
| `04_PartitioningFdiskAndParted` | Partycjonowanie: fdisk i parted | uzupełnienie |
| `05_CreatingFileSystemsMkfs` | Tworzenie systemu plików (mkfs) | LN02 §3 + uzupełnienie |
| `06_MountingAndUnmounting` | mount i umount | LN03 §9 + uzupełnienie |
| `07_Fstab` | Trwałe montowanie: /etc/fstab | uzupełnienie |
| `08_SwapSpace` | Przestrzeń wymiany (swap) | uzupełnienie |
| `09_FsckCheckingAndRepair` | Sprawdzanie i naprawa systemu plików (fsck) | uzupełnienie |
| `10_GrowingPartitionsAndFileSystems` | Powiększanie partycji i systemu plików | LN16 §5-7 + uzupełnienie |

### 25. `_lx_25_systemy_plikow_lvm_luks` - Linux - systemy plików, szyfrowanie, LVM i RAID (9 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_WhatIsAFileSystem` | Czym jest system plików | LN02 §1 |
| `02_NtfsFatAndExt4` | NTFS, FAT i ext4 - porównanie | LN02 §2-4, §8 |
| `03_Ext4XfsAndBtrfs` | ext4, XFS i Btrfs - wybór systemu plików | uzupełnienie |
| `04_InodesAndJournaling` | I-węzły i kronikowanie | uzupełnienie |
| `05_PartitionEncryptionBitlockerAndLuks` | Szyfrowanie partycji: BitLocker i LUKS | LN02 §5-7 |
| `06_LvmConcepts` | LVM - woluminy fizyczne, grupy i woluminy logiczne | uzupełnienie |
| `07_LvmInPractice` | LVM w praktyce: tworzenie, powiększanie, migawki | uzupełnienie |
| `08_SoftwareRaidMdadm` | Programowy RAID (mdadm) | uzupełnienie |
| `09_NetworkFileSystemsNfsAndSmb` | Sieciowe systemy plików: NFS i Samba | uzupełnienie |

### 26. `_lx_26_logi` - Linux - logi systemowe (7 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_WhereLogsLive` | Gdzie są logi: /var/log | LN03 §13 + uzupełnienie |
| `02_JournalctlBasics` | journalctl - dziennik systemd | uzupełnienie |
| `03_JournalctlFiltering` | Filtrowanie dziennika: usługa, czas, priorytet | uzupełnienie |
| `04_RsyslogAndSyslog` | rsyslog i protokół syslog | uzupełnienie |
| `05_Logrotate` | logrotate - rotacja logów | uzupełnienie |
| `06_DmesgKernelMessages` | dmesg - komunikaty jądra | uzupełnienie |
| `07_JavaApplicationLogsOnServer` | Logi aplikacji Java na serwerze | uzupełnienie |

### 27. `_lx_27_harmonogram_zadan` - Linux - harmonogram zadań (7 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_CronBasics` | cron - zadania cykliczne | uzupełnienie |
| `02_CrontabSyntax` | Składnia crontab | uzupełnienie |
| `03_SystemCronDirectories` | Systemowy cron: /etc/crontab i cron.d | uzupełnienie |
| `04_CronEnvironmentPitfalls` | Pułapki środowiska cron (PATH, logi, uprawnienia) | uzupełnienie |
| `05_AtOneTimeJobs` | at - zadania jednorazowe | uzupełnienie |
| `06_SystemdTimersVsCron` | Timery systemd a cron | uzupełnienie |
| `07_ScheduledBackupsInPractice` | Praktyka: zaplanowana kopia zapasowa | LN17 §13 + uzupełnienie |

### 28. `_lx_28_monitorowanie_wydajnosci` - Linux - monitorowanie i wydajność (10 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_UptimeAndLoadAverage` | uptime i średnie obciążenie | uzupełnienie |
| `02_MemoryFreeAndCaches` | Pamięć: free, bufory i pamięć podręczna | uzupełnienie |
| `03_VmstatAndIostat` | vmstat i iostat | uzupełnienie |
| `04_PerProcessResourceUsage` | Zużycie zasobów przez procesy | LN15 §7-8 + uzupełnienie |
| `05_LsofOpenFiles` | lsof - otwarte pliki i porty | uzupełnienie |
| `06_StraceSystemCalls` | strace - śledzenie wywołań systemowych | uzupełnienie |
| `07_OomKiller` | OOM killer - brak pamięci | uzupełnienie |
| `08_UlimitAndResourceLimits` | ulimit i limity zasobów | uzupełnienie |
| `09_JavaProcessDiagnostics` | Diagnostyka procesu Javy: jps, jcmd, jstack | uzupełnienie |
| `10_SysstatHistoricalData` | sysstat i sar - dane historyczne | uzupełnienie |

### 29. `_lx_29_jadro_i_sprzet` - Linux - jądro i sprzęt (6 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_KernelVersionAndUname` | Wersja jądra i uname | uzupełnienie |
| `02_KernelModules` | Moduły jądra: lsmod, modprobe, modinfo | uzupełnienie |
| `03_SysctlParameters` | Parametry jądra: sysctl | uzupełnienie |
| `04_ProcAndSysInterfaces` | Interfejsy /proc i /sys | LN03 §11 + uzupełnienie |
| `05_HardwareInformation` | Informacje o sprzęcie: lscpu, lspci, lsusb, dmidecode | uzupełnienie |
| `06_UdevAndDevices` | udev i obsługa urządzeń | LN03 §5 + uzupełnienie |

## Część III. Sieć i serwery

### 30. `_lx_30_podstawy_sieci` - Linux - podstawy sieci TCP/IP (10 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_NetworkLayersModel` | Modele warstwowe: OSI i TCP/IP | uzupełnienie |
| `02_IpAddressesAndClasses` | Adresy IPv4 i klasy adresów | LN16 §10 |
| `03_SubnetsAndCidr` | Podsieci i notacja CIDR | uzupełnienie |
| `04_PrivateAddressesAndNat` | Adresy prywatne i NAT | uzupełnienie |
| `05_GatewayAndRouting` | Brama domyślna i routing | uzupełnienie |
| `06_DnsExplained` | DNS - jak działa rozwiązywanie nazw | uzupełnienie |
| `07_PortsTcpAndUdp` | Porty, TCP i UDP | LN26 §3, §7 |
| `08_TcpHandshakeAndConnectionStates` | Uzgadnianie połączenia TCP i stany połączeń | LN27 §7 |
| `09_Ipv6Basics` | Podstawy IPv6 | uzupełnienie |
| `10_VmNetworkingNatAndBridged` | Sieć maszyny wirtualnej: NAT, bridged, host-only | LN16 §9, LN13 §2 |

### 31. `_lx_31_konfiguracja_sieci` - Linux - konfiguracja sieci (9 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_IpCommand` | Polecenie ip: adresy, trasy, interfejsy | uzupełnienie |
| `02_IfconfigAndNetTools` | ifconfig i pakiet net-tools | LN16 §12, LN13 §3 |
| `03_HostnameAndHostsFile` | Nazwa hosta i plik /etc/hosts | uzupełnienie |
| `04_DnsResolverConfiguration` | Konfiguracja DNS: resolv.conf i systemd-resolved | uzupełnienie |
| `05_NetplanOnUbuntu` | Netplan w Ubuntu | uzupełnienie |
| `06_NetworkManagerNmcli` | NetworkManager i nmcli | uzupełnienie |
| `07_StaticIpConfiguration` | Statyczny adres IP | uzupełnienie |
| `08_PingAndTraceroute` | Łączność: ping i traceroute | LN13 §3 + uzupełnienie |
| `09_DnsToolsDigAndNslookup` | Narzędzia DNS: dig, nslookup, host | uzupełnienie |

### 32. `_lx_32_diagnostyka_sieci` - Linux - porty i diagnostyka sieci (12 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_NetstatBasics` | netstat - instalacja i podstawowe użycie | LN27 §1-4 |
| `02_ListeningPortsNetstatPlnt` | Porty nasłuchujące: netstat -plnt | LN27 §5-6, §8-10 |
| `03_NetstatWithPipesAndRoot` | netstat w potokach i uprawnienia roota | LN27 §11-12, §14-16 |
| `04_SsModernReplacement` | ss - nowoczesny następca netstat | LN27 §13 |
| `05_LsofForNetworkConnections` | lsof -i - kto używa portu | uzupełnienie |
| `06_NmapBasicScans` | nmap - podstawowe skanowanie hostów i portów | LN26 §1-2, §4-6, §11 |
| `07_TcpScanTypes` | Rodzaje skanów TCP i skanowanie UDP | LN26 §7-8 |
| `08_ServiceAndOsDetection` | Wykrywanie usług, wersji i systemu | LN26 §9-10, §12 |
| `09_NmapScriptsAndLocalNetwork` | Skrypty NSE, skan sieci lokalnej i zapis wyników | LN26 §13-15 |
| `10_ScanningLegalityAndZenmap` | Legalność skanowania i Zenmap | LN26 §16-20 |
| `11_NetcatTesting` | netcat - testowanie połączeń | uzupełnienie |
| `12_TcpdumpBasics` | tcpdump - podgląd ruchu sieciowego | uzupełnienie |

### 33. `_lx_33_ssh` - Linux - SSH (12 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_SshClientServerArchitecture` | SSH - architektura klient-serwer | LN16 §13, LN16-2 §2 |
| `02_InstallingSshAndConnecting` | Instalacja serwera SSH i łączenie się | LN16 §14-15 |
| `03_PublicKeyCryptography` | Kryptografia klucza publicznego | LN16-2 §6, §8 |
| `04_KeyGenerationSshKeygen` | Generowanie kluczy: ssh-keygen (ed25519, RSA) | LN16-2 §7 |
| `05_PasswordlessLoginWithKeys` | Logowanie kluczem: ssh-copy-id i authorized_keys | LN16-2 §4-5, §9-13 |
| `06_SshAgent` | ssh-agent i hasło do klucza | uzupełnienie |
| `07_SshConfigAliases` | Aliasy w ~/.ssh/config | LN16-3 §1-3 |
| `08_SshdConfigAndPort` | Konfiguracja serwera: sshd_config i zmiana portu | LN16-3 §4-7 |
| `09_SshHardening` | Zabezpieczanie SSH | LN16-2 §12 + uzupełnienie |
| `10_SshTunnelsAndPortForwarding` | Tunele SSH i przekierowanie portów | uzupełnienie |
| `11_JumpHosts` | Serwery pośredniczące (ProxyJump) | uzupełnienie |
| `12_SshKeysForGitAndGithub` | Klucze SSH dla Gita i GitHuba | LN16-3 §8-15 |

### 34. `_lx_34_transfer_i_synchronizacja` - Linux - transfer i synchronizacja plików (scp, rsync) (12 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_ScpFileTransfer` | scp - kopiowanie plików przez SSH | LN16 §16, LN16-2 §1, §3 |
| `02_Sftp` | sftp - interaktywny transfer plików | uzupełnienie |
| `03_WhatIsRsync` | Czym jest rsync i czym różni się od cp i scp | LN17 §1 |
| `04_DeltaTransferAlgorithm` | Algorytm przesyłania różnic | LN17 §2 |
| `05_RsyncSyntaxAndArchiveFlag` | Składnia rsync i flaga -a | LN17 §3-5 |
| `06_LocalSynchronization` | Synchronizacja lokalna i ukośnik na końcu ścieżki | LN17 §6 |
| `07_RsyncOverSsh` | rsync przez SSH | LN17 §7 |
| `08_MirrorWithDelete` | Lustro katalogu (--delete) i --dry-run | LN17 §8 |
| `09_ExcludeAndInclude` | Wykluczenia: --exclude i --include | LN17 §9 |
| `10_PermissionsResumeAndBandwidth` | Uprawnienia, wznawianie i limit pasma | LN17 §10-12 |
| `11_RsyncBackups` | rsync jako kopia zapasowa i najczęstsze błędy | LN17 §13-15 |
| `12_ServingFilesOverHttp` | Serwowanie plików przez HTTP (python -m http.server) | LN13 §1, §4-10 |

### 35. `_lx_35_zapora_sieciowa` - Linux - zapora sieciowa (6 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_FirewallConcepts` | Zapora sieciowa - pojęcia i zasada domyślnej blokady | LN13 §8 + uzupełnienie |
| `02_UfwOnUbuntu` | ufw w Ubuntu | uzupełnienie |
| `03_NftablesAndIptables` | nftables i iptables | uzupełnienie |
| `04_Firewalld` | firewalld (RHEL, Fedora) | uzupełnienie |
| `05_FirewallForApplicationServer` | Zapora dla serwera aplikacji (SSH, HTTP, HTTPS, porty wewnętrzne) | uzupełnienie |
| `06_Fail2ban` | fail2ban - ochrona przed zgadywaniem haseł | uzupełnienie |

### 36. `_lx_36_serwery_www` - Linux - serwery WWW: Apache i Nginx (9 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_ApacheInstallationAndCheck` | Apache HTTP Server - instalacja i sprawdzanie działania | LN21 §7-9, §13 |
| `02_ApacheStructureAndConfigFiles` | Struktura Apache i pliki konfiguracyjne | LN21 §11-12 |
| `03_ApacheVirtualHosts` | Hosty wirtualne Apache | uzupełnienie |
| `04_DirectoryIndexConfiguration` | DirectoryIndex i restart Apache | LN25 §5-14 |
| `05_PhpWithApache` | PHP w Apache: kompilacja ze źródeł i rejestracja modułu | LN23 §2, §6-14 |
| `06_NginxBasics` | Nginx - podstawy i konfiguracja | uzupełnienie |
| `07_ReverseProxyForSpringBoot` | Reverse proxy dla aplikacji Spring Boot | uzupełnienie |
| `08_HttpsWithLetsEncrypt` | HTTPS z Let's Encrypt (certbot) | uzupełnienie |
| `09_WebServerLogsAndTroubleshooting` | Logi serwera WWW i rozwiązywanie problemów | uzupełnienie |

### 37. `_lx_37_bazy_danych_na_serwerze` - Linux - bazy danych na serwerze (8 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_MysqlInstallation` | Instalacja MySQL Community | LN24 §1, §3-7 |
| `02_MysqlDataDirectory` | Struktura danych MySQL | LN24 §8 |
| `03_MysqlBackups` | Kopie zapasowe MySQL i częste błędy | LN24 §9, §12-14 |
| `04_PhpMyAdmin` | phpMyAdmin - instalacja i dostęp | LN24 §10-11, LN25 §1-4 |
| `05_PostgresqlInstallation` | Instalacja PostgreSQL | uzupełnienie |
| `06_PostgresqlUsersAndAccess` | Użytkownicy PostgreSQL i pg_hba.conf | uzupełnienie |
| `07_PostgresqlBackups` | Kopie zapasowe PostgreSQL (pg_dump, pg_restore) | uzupełnienie |
| `08_DatabaseServerSecurity` | Bezpieczeństwo serwera bazy danych | uzupełnienie |

### 38. `_lx_38_java_na_serwerze` - Linux - aplikacje Java na serwerze (9 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_JdkVsJreInstallation` | JDK i JRE - instalacja przez APT | LN21 §1-2 |
| `02_MultipleJdksUpdateAlternatives` | Wiele wersji JDK: update-alternatives | uzupełnienie |
| `03_JavaHomeAndPath` | Zmienne JAVA_HOME i PATH | LN21 §3 |
| `04_MavenAndGradleInstallation` | Instalacja Mavena i Gradle | LN21 §5 + uzupełnienie |
| `05_RunningJarInBackground` | Uruchamianie jara w tle (nohup) i jego ograniczenia | LN21 §6 |
| `06_SpringBootAsSystemdService` | Aplikacja Spring Boot jako usługa systemd | uzupełnienie |
| `07_JvmMemorySettingsOnServer` | Ustawienia pamięci JVM na serwerze | uzupełnienie |
| `08_ApplicationConfigAndSecrets` | Konfiguracja i sekrety aplikacji na serwerze | uzupełnienie |
| `09_DeploymentAndRollback` | Wdrożenie nowej wersji i wycofanie | uzupełnienie |

### 39. `_lx_39_bezpieczenstwo` - Linux - bezpieczeństwo i hartowanie systemu (9 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_SecurityPrinciples` | Zasady: najmniejsze uprawnienia, obrona warstwowa | uzupełnienie |
| `02_UpdatesAndPatching` | Aktualizacje i łatki bezpieczeństwa | uzupełnienie |
| `03_AccountAndPasswordSecurity` | Bezpieczeństwo kont i haseł | LN14 §6 + uzupełnienie |
| `04_ListeningServicesAudit` | Przegląd usług nasłuchujących | LN27 §15 + uzupełnienie |
| `05_SelinuxBasics` | SELinux - podstawy | uzupełnienie |
| `06_AppArmorBasics` | AppArmor - podstawy | uzupełnienie |
| `07_AuthLogsAndLoginAudit` | Logi uwierzytelniania i audyt logowań | uzupełnienie |
| `08_FileIntegrityAndChecksums` | Integralność plików i sumy kontrolne | LN21 §4 + uzupełnienie |
| `09_ServerSecurityChecklist` | Lista kontrolna bezpieczeństwa serwera | uzupełnienie |

## Część IV. Automatyzacja, narzędzia i środowiska

### 40. `_lx_40_zmienne_srodowiskowe` - Linux - zmienne środowiskowe i konfiguracja powłoki (7 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_ShellVsEnvironmentVariables` | Zmienne powłoki a zmienne środowiskowe | uzupełnienie |
| `02_ExportAndEnv` | export, env i printenv | uzupełnienie |
| `03_PathVariable` | Zmienna PATH | LN21 §3 |
| `04_BashStartupFiles` | Pliki startowe: .bashrc, .bash_profile, .profile | LN21 §3 + uzupełnienie |
| `05_SystemWideEnvironment` | Konfiguracja systemowa: /etc/environment i /etc/profile.d | LN21 §3 |
| `06_AliasesAndFunctionsInBashrc` | Aliasy i funkcje w .bashrc | uzupełnienie |
| `07_PromptCustomizationPs1` | Własny znak zachęty (PS1) | uzupełnienie |

### 41. `_lx_41_skrypty_bash_podstawy` - Linux - skrypty bash: podstawy (10 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_FirstScriptAndShebang` | Pierwszy skrypt i shebang | uzupełnienie |
| `02_VariablesAndQuoting` | Zmienne i cudzysłowy | uzupełnienie |
| `03_ReadingUserInput` | Odczyt danych od użytkownika (read) | uzupełnienie |
| `04_IfConditions` | Instrukcja if | uzupełnienie |
| `05_TestOperators` | Testy: [ ], [[ ]], porównania i testy plików | uzupełnienie |
| `06_CaseStatement` | Instrukcja case | uzupełnienie |
| `07_ForLoops` | Pętle for | uzupełnienie |
| `08_WhileAndUntilLoops` | Pętle while i until | uzupełnienie |
| `09_ArgumentsAndExitCodes` | Argumenty skryptu i kody wyjścia | uzupełnienie |
| `10_FunctionsInBash` | Funkcje | uzupełnienie |

### 42. `_lx_42_skrypty_bash_zaawansowane` - Linux - skrypty bash: poziom zaawansowany (10 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_ArraysAndAssociativeArrays` | Tablice i tablice asocjacyjne | uzupełnienie |
| `02_StringManipulation` | Operacje na napisach (${zmienna#...}, ${zmienna/...}) | uzupełnienie |
| `03_ArithmeticInBash` | Arytmetyka w bashu | uzupełnienie |
| `04_StrictModeSetEuo` | Tryb ścisły: set -euo pipefail | uzupełnienie |
| `05_TrapAndCleanup` | trap i sprzątanie po skrypcie | uzupełnienie |
| `06_GetoptsOptionParsing` | Opcje skryptu: getopts | uzupełnienie |
| `07_ProcessingFilesLineByLine` | Przetwarzanie plików linia po linii | uzupełnienie |
| `08_DebuggingAndShellcheck` | Debugowanie (bash -x) i shellcheck | uzupełnienie |
| `09_PracticalAdminScripts` | Praktyczne skrypty administracyjne | uzupełnienie |
| `10_ScriptsForJavaApplications` | Skrypty startowe i wdrożeniowe dla aplikacji Java | uzupełnienie |

### 43. `_lx_43_powloki_i_terminale` - Linux - powłoki, terminale i tmux (8 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_BashVsZsh` | bash a zsh | LN28 §2 |
| `02_OhMyZshInstallation` | Oh My Zsh - czym jest i jak go zainstalować | LN28 §3-4, §8 |
| `03_ZshrcAndPlugins` | Plik .zshrc i wtyczki | LN28 §5-6 |
| `04_Powerlevel10kTheme` | Motyw Powerlevel10k | LN28 §7 |
| `05_TerminatorAndGuake` | Terminator i Guake | LN28 §9-15 |
| `06_TmuxBasics` | tmux - podstawy: sesje, okna, panele | uzupełnienie |
| `07_TmuxOnRemoteServers` | tmux na serwerze - praca odporna na rozłączenia | uzupełnienie |
| `08_ScreenAlternative` | GNU screen | uzupełnienie |

### 44. `_lx_44_wsl` - Linux - WSL (Windows Subsystem for Linux) (9 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_WhatIsWsl` | Czym jest WSL | LN29 §1 |
| `02_Wsl1VsWsl2` | WSL1 a WSL2 | LN29 §2 |
| `03_InstallingWslAndDistributions` | Instalacja WSL i dystrybucje | LN29 §3-4 |
| `04_WindowsTerminalAndTools` | Windows Terminal i praca w WSL | LN29 §8 |
| `05_WslFilesAndWindowsDrives` | Gdzie WSL trzyma dane i dostęp do dysków Windows | LN29 §5-6 |
| `06_WslLimitations` | Ograniczenia WSL względem prawdziwego Linuksa | LN29 §7, §15 |
| `07_GuiAppsAndXServer` | Aplikacje graficzne, X Server i WSLg | LN29 §9-14 |
| `08_SystemdInWsl2` | systemd w WSL2 | LN30 §2-13 |
| `09_WslVsVmVsServer` | WSL, maszyna wirtualna czy serwer | LN30 §14-16, LN29 §16 |

### 45. `_lx_45_kontenery_i_docker` - Linux - kontenery od środka i Docker na WSL2 (8 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_NamespacesAndCgroups` | Mechanizmy jądra: przestrzenie nazw i cgroups | LN31 §3 + uzupełnienie |
| `02_DockerVsVirtualMachine` | Docker a maszyna wirtualna | LN31 §1-2 |
| `03_DockerDesktopAndWsl2Architecture` | Docker Desktop i architektura na WSL2 | LN31 §4-7 |
| `04_PortsAndContainerCommunication` | Porty i komunikacja kontenerów | LN31 §8, §12-13 |
| `05_VolumesAndFileSystemPerformance` | Wolumeny i wydajność systemu plików | LN31 §9-10, §17 |
| `06_DockerComposeInWsl2` | Docker Compose w WSL2 | LN31 §11 |
| `07_DockerEngineOnNativeLinux` | Docker Engine na zwykłym Linuksie (grupa docker, tryb rootless) | LN31 §19 + uzupełnienie |
| `08_DebuggingLimitationsAndWhenToUse` | Diagnostyka, ograniczenia i kiedy to ma sens | LN31 §14-16, §18, §20 |

### 46. `_lx_46_projekt_koncowy` - Linux - projekt końcowy: serwer dla aplikacji Spring Boot (9 lekcji)

| Lekcja (plik JSON) | Temat | Źródło w notatkach |
|---|---|---|
| `01_ProjectOverview` | Założenia projektu i architektura | uzupełnienie |
| `02_ProvisioningServerAndUsers` | Przygotowanie serwera i kont użytkowników | uzupełnienie |
| `03_SecuringSshAndFirewall` | Zabezpieczenie SSH i zapory | uzupełnienie |
| `04_InstallingJavaAndPostgresql` | Instalacja Javy i PostgreSQL | uzupełnienie |
| `05_DeployingApplicationAsService` | Wdrożenie aplikacji jako usługi systemd | uzupełnienie |
| `06_NginxReverseProxyAndTls` | Nginx jako reverse proxy i HTTPS | uzupełnienie |
| `07_LogsMonitoringAndBackups` | Logi, monitorowanie i kopie zapasowe | uzupełnienie |
| `08_AutomationScript` | Skrypt automatyzujący całą instalację | uzupełnienie |
| `09_FinalChecklist` | Końcowa lista kontrolna i przegląd | uzupełnienie |

