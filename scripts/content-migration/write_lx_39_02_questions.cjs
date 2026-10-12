module.exports=[
  [
    "Pakiet z poprawką jest dostępny, ale niski priorytet pin go nie wybiera. Co zbadać?",
    "Efektywne apt preferences i policy dla pakietu",
    "Wyłącznie wersję terminala",
    "Wyłącznie stan FIFO",
    "Wyłącznie datę pliku jar",
    "Dostępność wersji i wybór kandydata zależą od polityki."
  ],
  [
    "Czym różni się apt update od instalacji poprawki?",
    "Odświeża indeksy, nie instaluje pakietu",
    "Restartuje wszystkie procesy",
    "Przełącza jądro",
    "Odtwarza bazę",
    "Indeksy opisują dostępne wersje."
  ],
  [
    "Co pokazuje Installed w apt-cache policy?",
    "Wersję zainstalowanego pakietu",
    "Wersję działającego procesu z gwarancją",
    "Listę CVE",
    "Czas rebootu",
    "Stan pakietu i procesu są odrębne."
  ],
  [
    "Co oznacza Candidate w policy?",
    "Wersję wybraną przez politykę APT",
    "Największy numer w internecie",
    "Zawsze wersję bez luk",
    "Wersję jądra w RAM",
    "Źródła i priorytety wpływają na kandydata."
  ],
  [
    "Do czego służy apt-get -s upgrade?",
    "Symuluje plan bez instalacji",
    "Testuje pełną aplikację po restarcie",
    "Instaluje tylko poprawki CVE",
    "Odświeża wszystkie indeksy",
    "Symulacja nie wykonuje wdrożenia."
  ],
  [
    "Dlaczego stary indeks osłabia ocenę aktualizacji?",
    "Może nie zawierać nowych wersji",
    "Zmienia prawa plików",
    "Usuwa identyfikatory CVE",
    "Zatrzymuje sshd",
    "Plan zależy od lokalnych metadanych."
  ],
  [
    "Jak odczytać istniejące hold?",
    "apt-mark showhold",
    "apt-mark unhold",
    "apt-get purge",
    "dpkg --clear-avail",
    "showhold nie zmienia wstrzymania."
  ],
  [
    "Co wymaga dokumentacji przy hold?",
    "Powód, właściciel i termin przeglądu",
    "Sam numer aktualnej wersji bez terminu przeglądu",
    "Wstrzymanie bez daty zakończenia",
    "Informacja o dawnym błędzie bez osoby odpowiedzialnej",
    "Wyjątek potrzebuje kontroli czasu i odpowiedzialności."
  ],
  [
    "Czym jest USN?",
    "Biuletynem bezpieczeństwa Ubuntu",
    "Wynikiem lokalnego skanowania pamięci",
    "Formatem pakietu jar",
    "Nazwą firewalla",
    "Biuletyn wiąże lukę z wydaniami i poprawkami."
  ],
  [
    "Co identyfikuje CVE?",
    "Zidentyfikowaną podatność",
    "Polecenie do aktualizacji",
    "Każdy podatny host",
    "Zawsze publiczny exploit",
    "Identyfikator nie dowodzi ekspozycji hosta."
  ],
  [
    "Co sprawdzić poza samym CVE?",
    "Produkt, wydanie, konfigurację i poprawioną wersję",
    "Sam CVSS bez identyfikacji produktu",
    "Wersję upstream bez rewizji dystrybucji",
    "Obecność identyfikatora w dowolnym raporcie",
    "Ocena wymaga kontekstu instalacji."
  ],
  [
    "Dlaczego upstream numer nie wystarcza w Ubuntu?",
    "Dystrybucja może backportować poprawki",
    "Każdy stary numer jest bezpieczny",
    "Każdy nowy numer jest podatny",
    "Ubuntu ignoruje rewizje",
    "Rewizja dystrybucji i biuletyn są istotne."
  ],
  [
    "Jak porównywać wersje pakietów Debian?",
    "dpkg --compare-versions",
    "Zwykłe sortowanie alfabetyczne",
    "Porównanie długości ciągu",
    "Wyłącznie część przed myślnikiem",
    "dpkg obsługuje epokę i rewizje."
  ],
  [
    "Która z 1:2.4-0ubuntu1.9 i 1:2.4-0ubuntu1.10 jest nowsza?",
    "1:2.4-0ubuntu1.10",
    "Obie zawsze równe",
    "1:2.4-0ubuntu1.9",
    "Nie mają porządku w dpkg",
    "Numery rewizji są porównywane numerycznie."
  ],
  [
    "Jak epoka wpływa na 2:1.0-1 wobec 1:99.0-1?",
    "Pierwsza wersja jest nowsza",
    "Druga zawsze nowsza",
    "Epoka jest ignorowana",
    "Rozstrzyga rozmiar pakietu",
    "Epoka jest porównywana przed upstream."
  ],
  [
    "Jak dpkg porządkuje 3.0~rc1 i 3.0?",
    "Prerelease jest starszy",
    "Prerelease zawsze nowszy",
    "Wersje zawsze równe",
    "Tylda oznacza błąd składni",
    "Tylda ma specjalny porządek."
  ],
  [
    "Co zmienia instalacja nowej biblioteki na dysku?",
    "Pliki pakietu, nie automatycznie każdą starą mapę procesu",
    "Wszystkie mapy RAM natychmiast",
    "Działające jądro zawsze",
    "Wersje wszystkich jar",
    "Proces może nadal używać starego inode."
  ],
  [
    "Co oznacza deleted przy bibliotece w maps?",
    "Proces nadal mapuje inode usunięty z przestrzeni nazw",
    "Proces na pewno zakończony",
    "Nowa biblioteka nie istnieje",
    "Jądro zostało zrestartowane",
    "Mapa może przetrwać usunięcie nazwy."
  ],
  [
    "Do czego służy needrestart -r l?",
    "Raportuje potrzeby restartu w trybie listy",
    "Wymusza reboot",
    "Usuwa stare jądra",
    "Instaluje JDK",
    "Tryb l nie jest poleceniem wykonania restartów."
  ],
  [
    "Jakie zachowanie needrestart opisuje Ubuntu 24.04 domyślnie?",
    "Automatyczny restart dotkniętych usług po transakcjach APT",
    "Wyłącznie ręczny restart w każdym obrazie",
    "Automatyczny reboot przy każdym pakiecie",
    "Brak integracji z APT",
    "Konfiguracja i wyjątki wymagają osobnego odczytu."
  ],
  [
    "Czy restart usługi i reboot to ta sama operacja?",
    "Nie, mają różny zakres",
    "Tak, zawsze przełączają jądro",
    "Tak, oba tylko odświeżają indeks",
    "Nie, reboot nigdy nie dotyka usług",
    "Restart procesu nie uruchamia nowego jądra."
  ],
  [
    "Co odczytuje uname -r?",
    "Wersję działającego jądra",
    "Najnowocześniejsze jądro repozytorium",
    "Wersję apt",
    "Wersję systemd na dysku",
    "Jądro zainstalowane może jeszcze nie działać."
  ],
  [
    "Co sygnalizuje reboot-required?",
    "Żądanie restartu zgłoszone przez mechanizm pakietów",
    "Listę wszystkich CVE",
    "Zakończony reboot",
    "Potwierdzenie zdrowia aplikacji",
    "Sygnał nie jest pełnym audytem bezpieczeństwa."
  ],
  [
    "Co zawiera reboot-required.pkgs?",
    "Nazwy pakietów zgłaszających potrzebę restartu",
    "Hasła usług",
    "Listę aktywnych TCP",
    "Wyłącznie nowe wersje aplikacji",
    "To pomoc w ustaleniu przyczyny sygnału."
  ],
  [
    "Dlaczego brak markeru reboot nie zamyka audytu?",
    "Nie obejmuje wszystkich komponentów i stanów",
    "Dowodzi braku internetu",
    "Oznacza uszkodzenie dpkg",
    "Oznacza zawsze aktualny kernel",
    "Weryfikuj pakiety i procesy oddzielnie."
  ],
  [
    "Co oznacza Automatic-Reboot false?",
    "Automat ma nie wykonywać rebootu na tej podstawie",
    "Wyłączenie instalacji poprawek",
    "Wyłączenie wszystkich restartów usług",
    "Brak potrzeby rebootu",
    "Ustawienie polityki nie usuwa potrzeby restartu."
  ],
  [
    "Co sprawdzić przy unattended-upgrades?",
    "Efektywną konfigurację, harmonogram i logi",
    "Wyłącznie obecność pakietu automatu",
    "Wyłącznie obecność timera bez wyniku transakcji",
    "Wyłącznie listę planowanych pakietów",
    "Obecność pakietu nie dowodzi działania."
  ],
  [
    "Co oznaczają allowed origins?",
    "Dopuszczone pochodzenie aktualizacji dla automatu",
    "Każdy pakiet z dowolnego serwera",
    "Listę hostów do rebootu",
    "Listę portów APT",
    "Pochodzenie ogranicza zakres instalacji automatycznej."
  ],
  [
    "Dlaczego należy czytać cały wynik dry-run?",
    "Może zawierać pominięcia i błędy",
    "Każdy dry-run zawsze udany",
    "Dry-run instaluje bez logu",
    "Wystarczy pierwsza linia",
    "Symulacja pozwala rozpoznać przeszkody."
  ],
  [
    "Który dowód lepiej potwierdza wykonanie aktualizacji?",
    "Historia transakcji oraz stan wersji po operacji",
    "Sama lista planowanych pakietów",
    "Pobrany plik bez instalacji",
    "Utworzony katalog raportu",
    "Potwierdź rezultat, nie zamiar."
  ],
  [
    "Do czego służy apt-config dump?",
    "Pokazuje konfigurację APT",
    "Odświeża źródła",
    "Restartuje timer",
    "Przywraca pakiety",
    "To odczyt konfiguracji."
  ],
  [
    "Dlaczego nie publikować całego dump bez przeglądu?",
    "Może zawierać ustawienia proxy i dane dostępu",
    "Zawsze zawiera klucze SSH",
    "Zawsze usuwa logi",
    "Nie da się go odczytać lokalnie",
    "Raport wymaga kontroli ujawnianych danych."
  ],
  [
    "Czy pakiet z dpkg -i jest zawsze poza APT?",
    "Nie, może mieć odpowiednie repozytorium aktualizacji",
    "Tak, APT nigdy go nie widzi",
    "Tak, nie ma wpisu w dpkg",
    "Nie, zawsze ma aktywne repozytorium",
    "Sprawdź faktyczną dostępność źródła i kandydata."
  ],
  [
    "Czego APT zwykle nie aktualizuje w własnym jar?",
    "Wbudowanych zależności aplikacji",
    "Pakietu JDK z jego repozytorium",
    "Pakietu openssl z repozytorium",
    "Pakietu apt z repozytorium",
    "Jar wymaga własnego cyklu budowy."
  ],
  [
    "Jak uaktualnić ręcznie zbudowane PHP?",
    "Przez kontrolowany proces budowy, testów i wdrożenia",
    "Samym apt update bez pakietu PHP",
    "Wyłącznie rebootem",
    "Zmianą nazwy katalogu",
    "Odpowiedzialność zależy od sposobu dostarczenia."
  ],
  [
    "Dlaczego nowe image nie naprawia już uruchomionego kontenera?",
    "Instancja nadal używa dotychczasowego obrazu",
    "Pull zawsze restartuje kontener",
    "Image zmienia hostowe jądro",
    "Tag jest tożsamy z PID",
    "Potrzebne odtworzenie instancji z właściwego obrazu."
  ],
  [
    "Co aktualizuje zależność aplikacji Maven?",
    "Zmiana zależności i nowa budowa oraz wdrożenie",
    "Samo apt upgrade JDK",
    "Restart starego jar bez przebudowy",
    "Zmiana numeru w dokumentacji bez nowego artefaktu",
    "Komponent w artefakcie musi zostać zastąpiony."
  ],
  [
    "Dlaczego inwentaryzacja zawiera właściciela?",
    "Ktoś odpowiada za śledzenie i wdrożenie poprawki",
    "Właściciel zastępuje biuletyny",
    "Właściciel dowodzi braku luk",
    "To hasło root",
    "Odpowiedzialność zapobiega pominięciom."
  ],
  [
    "Co powinno poprzedzać okno aktualizacji bazy?",
    "Sprawdzona kopia i plan odtworzenia",
    "Tylko zmiana motd",
    "Usunięcie poprzedniego backupu",
    "Wyłączenie logów",
    "Możliwość odtworzenia ogranicza ryzyko awarii."
  ],
  [
    "Co sprawdza readiness po restarcie?",
    "Gotowość instancji do obsługi ruchu w przyjętym kontrakcie",
    "Wszystkie CVE świata",
    "Zawsze spójność każdej kopii bazy",
    "Zawsze brak regresji biznesowej",
    "Readiness ma określony zakres."
  ],
  [
    "Dlaczego restart dwóch instancji jednocześnie jest ryzykowny?",
    "Może wyłączyć całą zdolność obsługi",
    "Zawsze przyspiesza rollback bez kosztu",
    "Nigdy nie zmienia dostępności",
    "Sam poprawia kompatybilność danych",
    "Kolejność i bramki zdrowia chronią dostępność."
  ],
  [
    "Co ogranicza okno serwisowe?",
    "Uzgodniony czas i zakres zmian z planem reakcji",
    "Każdą podatność automatycznie",
    "Potrzebę testów",
    "Potrzebę logów",
    "Okno organizuje wykonanie operacji."
  ],
  [
    "Czy kopia plików pakietu zapewnia rollback bazy?",
    "Nie, dane i migracje mają osobny plan",
    "Tak, zawsze cofa schemat",
    "Tak, jeśli plik ma chmod 600",
    "Tak, bez testu odtworzenia",
    "Rollback kodu nie jest rollbackiem danych."
  ],
  [
    "Dlaczego Livepatch nie zastępuje wszystkich aktualizacji?",
    "Dotyczy określonych poprawek jądra, nie całego userspace",
    "Aktualizuje każdy jar",
    "Zawsze zmienia wersję każdego pakietu",
    "Usuwa potrzebę inwentaryzacji",
    "Zakres usługi trzeba sprawdzić."
  ],
  [
    "Czy CVSS sam ustala kolejność wszystkich zmian?",
    "Nie, liczy się też ekspozycja i wykorzystanie komponentu",
    "Tak, zastępuje inwentaryzację",
    "Tak, dowodzi aktywnego ataku",
    "Nie, identyfikuje użytkownika",
    "Priorytet wymaga kontekstu i zagrożenia."
  ],
  [
    "Dlaczego test po poprawce jest konieczny?",
    "Poprawka może zmienić zachowanie zależności",
    "Ponieważ apt zawsze niszczy dane",
    "Ponieważ wersje nie mają znaczenia",
    "Aby usunąć biuletyn",
    "Kontroluj funkcje i zdrowie usługi."
  ],
  [
    "Co jest dobrym zakończeniem zmiany?",
    "Potwierdzone wersje, zdrowie i zapis wyniku",
    "Tylko rozpoczęcie pobierania",
    "Tylko brak wiadomości na czacie",
    "Tylko restart terminala",
    "Dziennik powinien opisywać faktyczny rezultat."
  ],
  [
    "Jak reagować na błąd podpisu repozytorium?",
    "Wyjaśnić problem z zaufaniem i źródłem",
    "Wyłączyć kontrolę podpisu bez analizy",
    "Zignorować wszystkie ostrzeżenia",
    "Nadać chmod 777 źródłom",
    "Podpisy chronią łańcuch dostarczenia."
  ],
  [
    "Co robi --only-upgrade w apt-get install?",
    "Ogranicza wskazane pakiety do już zainstalowanych",
    "Gwarantuje tylko poprawki bezpieczeństwa",
    "Gwarantuje brak restartów usług",
    "Aktualizuje wydanie Ubuntu",
    "Opcja nie klasyfikuje rodzaju poprawki."
  ],
  [
    "Dlaczego pełne upgrade może wymagać analizy usunięć?",
    "Rozwiązywanie zależności może zmienić zestaw pakietów",
    "Usunięcia zawsze są atakiem",
    "Symulacja nie pokazuje zależności",
    "Każde upgrade odinstalowuje JDK",
    "Przejrzyj plan przed wykonaniem."
  ],
  [
    "Co daje monitoring nieudanych aktualizacji?",
    "Wykrywa utrzymującą się lukę w procesie utrzymania",
    "Instaluje brakujące pakiety samym alarmem",
    "Gwarantuje brak exploitów",
    "Zastępuje testy odtworzenia",
    "Alert musi prowadzić do działania."
  ],
  [
    "Po apt update licznik pakietów zmienił się, ale dpkg nadal ma starą wersję. Co zaszło?",
    "Odświeżono metadane, nie wykonano instalacji",
    "Proces już na pewno poprawiony",
    "Zrestartowano jądro",
    "Zmieniono dane aplikacji",
    "To dwa etapy cyklu aktualizacji."
  ],
  [
    "Policy pokazuje brak kandydata dla pakietu z ręcznego deb. Co zbadać?",
    "Źródło aktualizacji i zgodność repozytorium",
    "Tylko prawa do logu",
    "Tylko uptime",
    "Wystarczy zrestartować ssh",
    "Brak źródła może blokować aktualizacje."
  ],
  [
    "Skaner alarmuje po upstream numerze, lecz USN podaje poprawioną rewizję zainstalowaną lokalnie. Co zrobić?",
    "Zweryfikować mapowanie skanera i pełną wersję dystrybucji",
    "Uznać upstream numer za jedyne kryterium",
    "Wymusić downgrade do wersji upstream",
    "Zamknąć alarm bez zapisania pełnej wersji",
    "Backporty wymagają poprawnej interpretacji."
  ],
  [
    "Dwa hosty mają tę samą wersję upstream, lecz różne rewizje Ubuntu. Co porównać?",
    "Pełne wersje z progiem biuletynu dla wydania",
    "Wyłącznie wersję upstream",
    "Wyłącznie datę binarki",
    "Wyłącznie liczbę oczekujących aktualizacji",
    "Rewizja może zawierać poprawkę."
  ],
  [
    "Pakiet jest hold od pół roku bez właściciela. Jaki brak procesu utrzymania to ujawnia?",
    "Nieprzeglądany wyjątek blokujący aktualizacje",
    "Udokumentowany i zamknięty wyjątek",
    "Udowodnione zastosowanie poprawki",
    "Potwierdzony automatyczny restart",
    "Wstrzymanie wymaga terminu i uzasadnienia."
  ],
  [
    "Usunięto hold, ale kandydat nadal stary. Co dalej?",
    "Sprawdzić indeksy, źródła i pinning",
    "Uznać poprawkę za zainstalowaną",
    "Usunąć logi",
    "Zrestartować tylko terminal",
    "Zdjęcie hold nie tworzy nowej wersji."
  ],
  [
    "Automat działa, lecz omija PPA. Co najpierw sprawdzić?",
    "Czy origin PPA jest dopuszczony",
    "Uznać każde PPA za automatycznie dopuszczone",
    "Założyć, że apt update instaluje pominięte pakiety",
    "Wnioskować o instalacji z samej obecności kandydata",
    "Instalacja automatyczna ma politykę źródeł."
  ],
  [
    "Log mówi Packages that will be upgraded, później ERROR. Jaki wniosek?",
    "Plan nie jest dowodem zakończonej transakcji",
    "Wszystkie pakiety na pewno poprawione",
    "Błąd dotyczy tylko kolorów terminala",
    "Reboot już się odbył",
    "Oceń dalszy log i stan pakietów."
  ],
  [
    "History.log potwierdza upgrade, lecz usługa nadal mapuje deleted bibliotekę. Co zostało do sprawdzenia?",
    "Restart procesu i jego stan po zmianie",
    "Tylko ponowne apt update",
    "Tylko chmod biblioteki",
    "Tylko zmiana nazwy logu",
    "Pakiet na dysku nie aktualizuje starej mapy."
  ],
  [
    "Własny Python pokazuje old fixture, a ścieżka na dysku new fixture. Co demonstruje model?",
    "Rozdzielenie otwartego inode od nowej nazwy pliku",
    "Rzeczywistą lukę OpenSSL hosta",
    "Udany reboot",
    "Pełny sandbox systemd",
    "To model mechanizmu mapowania."
  ],
  [
    "W maps własnego procesu pojawia się library.bin deleted po rename. Czy plik nowy musi być uszkodzony?",
    "Nie, wpis dotyczy starego mapowanego inode",
    "Tak, rename zawsze uszkadza plik",
    "Tak, dpkg musi być usunięty",
    "Nie, proces już nie działa",
    "Nowa ścieżka i stara mapa współistnieją."
  ],
  [
    "Po ponownym uruchomieniu model odczytuje new fixture. Co potwierdzono?",
    "Nowy proces otworzył nowy plik",
    "Wszystkie usługi produkcyjne poprawione",
    "Wszystkie CVE usunięte",
    "Jądro przełączone",
    "Zakres dowodu jest lokalny."
  ],
  [
    "W kontenerze uname różni się od pakietów kernel userspace. Co uwzględnić?",
    "Kontener używa jądra gospodarza",
    "Kontener uruchomił własne jądro z deb",
    "dpkg zawsze kłamie",
    "uname pokazuje wersję Java",
    "Zainstalowane pakiety nie wyznaczają jądra kontenera."
  ],
  [
    "Po instalacji jądra uname nadal stary na VM. Co jest możliwe?",
    "Nowe jądro jeszcze nie zostało uruchomione",
    "apt update musi zmieniać uname",
    "Kopia bazy automatycznie cofnęła jądro",
    "Zawsze atak na DNS",
    "Sprawdź plan rebootu i wybór bootloadera."
  ],
  [
    "Reboot VM zakończony, uname nadal stary. Co sprawdzić?",
    "Wybrane jądro i przyczynę bootowania starej wersji",
    "Tylko kolor promptu",
    "Tylko ilość logów aplikacji",
    "Tylko wersję curl",
    "Sam reboot nie gwarantuje wyboru oczekiwanego jądra."
  ],
  [
    "Brak reboot-required na ręcznie utrzymanym systemie. Czy system na pewno aktualny?",
    "Nie, marker nie zastępuje weryfikacji",
    "Tak, każdy komponent raportuje marker",
    "Tak, każdy jar używa tego pliku",
    "Tak, marker obejmuje kontenery",
    "Ocena wymaga innych dowodów."
  ],
  [
    "Automatic-Reboot false, ale nginx restartował po APT. Czy to sprzeczność?",
    "Nie, polityka rebootu różni się od restartów usług",
    "Tak, false blokuje wszystkie restarty",
    "Tak, APT nie może integrować needrestart",
    "Nie, nginx jest jądrem",
    "Zakresy ustawień są różne."
  ],
  [
    "Instancja A po poprawce nie przechodzi readiness. Co z B?",
    "Pozostawić B obsługującą ruch i zbadać A",
    "Natychmiast wyłączyć B",
    "Usunąć kopie",
    "Wyłączyć wszystkie sondy",
    "Bramka zdrowia chroni dostępną instancję."
  ],
  [
    "Readiness OK, lecz wzrosły błędy płatności. Co zrobić?",
    "Ocenić regresję funkcji i plan reakcji",
    "Uznać każdy test biznesowy za zbędny",
    "Wyłączyć liczniki błędów",
    "Zamknąć zmianę wyłącznie po readiness",
    "Sonda nie obejmuje każdej funkcji."
  ],
  [
    "Aktualizacja JDK ukończona, JVM uruchomiona wcześniej. Co zweryfikować?",
    "Wersję i pliki używane przez działającą JVM oraz restart",
    "Tylko java -version w nowej powłoce",
    "Tylko nazwę jar",
    "Tylko właściciela portu",
    "Nowa powłoka nie dowodzi wersji starego procesu."
  ],
  [
    "CVE dotyczy biblioteki w jar, systemowy openssl aktualny. Co aktualizować?",
    "Właściwą zależność aplikacji i artefakt",
    "Wyłącznie pakiet openssl",
    "Wyłącznie hostname",
    "Wyłącznie certyfikat systemowy",
    "Różne komponenty mają osobne cykle."
  ],
  [
    "Obraz ma nowy tag, działający kontener utworzono tydzień temu. Co sprawdzić?",
    "Digest obrazu instancji i odtworzenie po aktualizacji",
    "Tylko nazwę tagu na stronie",
    "Tylko datę hostowego rebootu",
    "Tylko apt w hoście",
    "Tag nie jest dowodem używanej zawartości."
  ],
  [
    "Host poprawiony, image zawiera starą bibliotekę userspace. Co wynika?",
    "Aktualizacja hosta nie poprawiła tej zawartości obrazu",
    "Kontener zawsze dziedziczy wszystkie pakiety hosta",
    "Każdy reboot podmienia image",
    "Docker ignoruje userspace",
    "Obraz wymaga własnej aktualizacji."
  ],
  [
    "PHP w /opt zbudowany ze źródeł bez pakietu. Jak rozpoznać właściciela zmian?",
    "Z inwentaryzacji i procedury budowy",
    "Z samego apt list",
    "Z pliku reboot-required",
    "Z samego uname",
    "Menedżer pakietów nie prowadzi tej instalacji."
  ],
  [
    "MySQL z deb ma aktywne oficjalne repozytorium i kandydata. Czy zawsze aktualizowany ręcznie?",
    "Nie, trzeba ocenić politykę APT i automatu",
    "Tak, każdy dpkg -i trwale wyklucza APT",
    "Tak, dpkg usuwa metadane pakietu",
    "Nie, kandydat oznacza już instalację",
    "Pochodzenie początkowej instalacji nie przesądza całego cyklu."
  ],
  [
    "Źródło repozytorium jest wyłączone po zmianie wydania. Co może to spowodować?",
    "Brak dalszych kandydatów z tego źródła",
    "Natychmiastowy restart każdej usługi",
    "Usunięcie pamięci JVM",
    "Zmianę klucza SSH",
    "Źródła są częścią kontroli utrzymania."
  ],
  [
    "Pakiet ma candidate niższy niż installed. Co jest właściwe?",
    "Zbadać politykę i historię, nie cofać automatycznie",
    "Wymusić downgrade tylko dlatego, że candidate jest niższy",
    "Uznać stan za bezpieczny bez kontroli biuletynu",
    "Usunąć hold, ignorując priorytety źródeł",
    "Niższy numer nie jest sam w sobie poprawką."
  ],
  [
    "Biuletyn dotyczy innego wydania Ubuntu. Co zrobić?",
    "Odnaleźć informację właściwą dla używanego wydania",
    "Użyć jego progu bez weryfikacji wydania",
    "Uznać poprawkę na jednym wydaniu za poprawkę na każdym",
    "Porównać tylko część upstream",
    "Pakiety i progi różnią się między wydaniami."
  ],
  [
    "CVE jest krytyczne, ale komponent nie jest zainstalowany. Co zanotować?",
    "Dowód zakresu inwentaryzacji i brak tego komponentu",
    "Udane zastosowanie nieistniejącej poprawki",
    "Gwarancję braku innych CVE",
    "Hasło admina",
    "Brak komponentu wymaga wiarygodnej identyfikacji."
  ],
  [
    "Podatna funkcja jest wyłączona, poprawka istnieje. Czy zrezygnować z utrzymania?",
    "Nie, ocenę ekspozycji udokumentować i nadal zaplanować poprawkę",
    "Tak, nigdy jej nie instalować",
    "Tak, usunąć biuletyn",
    "Tak, wyłączyć monitoring",
    "Mitigacja i usunięcie przyczyny mają osobne role."
  ],
  [
    "Poprawka wymaga restartu SSH na zdalnym hoście. Co przygotować?",
    "Zapasową sesję lub dostęp awaryjny i test konfiguracji",
    "Wyłączyć każdą aktywną sesję przed testem",
    "Usunąć klucz hosta bez planu",
    "Zmienić port losowo",
    "Zachowaj możliwość odzyskania dostępu."
  ],
  [
    "Plan upgrade przewiduje usunięcie krytycznego pakietu. Co zrobić?",
    "Wyjaśnić zależności i plan przed wykonaniem",
    "Wykonać w ciemno, bo to symulacja",
    "Usunąć log symulacji",
    "Zignorować Remv",
    "Symulacja jest momentem na wykrycie niepożądanych działań."
  ],
  [
    "apt update zwraca błąd jednego źródła. Czy późniejszy plan jest na pewno świeży?",
    "Nie, część indeksów może pozostać stara",
    "Tak, każdy błąd resetuje je do najnowszych",
    "Tak, upgrade sam naprawia DNS zawsze",
    "Tak, wystarczy kod upgrade",
    "Oceń wynik odświeżenia każdego źródła."
  ],
  [
    "Zegar hosta jest błędny, weryfikacja repozytorium nie działa. Co sprawdzić?",
    "Czas i przyczynę błędu zamiast wyłączać zaufanie",
    "Tylko chmod sources",
    "Tylko wielkość swap",
    "Tylko nazwę usługi",
    "Weryfikacja metadanych może zależeć od czasu."
  ],
  [
    "Brakuje miejsca podczas instalacji poprawki. Co jest potrzebne?",
    "Ocena stanu transakcji i bezpieczne odzyskanie miejsca",
    "Założenie pełnego sukcesu",
    "Usunięcie całego /var bez analizy",
    "Wyłączenie podpisów",
    "Nieudana transakcja wymaga naprawy i potwierdzenia."
  ],
  [
    "dpkg zgłasza pakiet niedokonfigurowany. Czy sama wersja zamyka zmianę?",
    "Nie, trzeba naprawić stan konfiguracji i sprawdzić działanie",
    "Tak, numer wystarczy",
    "Tak, history.log zastępuje działanie",
    "Tak, reboot usunie wszystkie błędy",
    "Wersja i poprawny stan instalacji są różne."
  ],
  [
    "Timer jest aktywny, log automatu od tygodnia nie pokazuje sukcesu. Co zbadać?",
    "Ostatnie uruchomienia, błędy i konfigurację",
    "Uznać timer za dowód aktualnego systemu",
    "Wyłączyć logowanie",
    "Zwiększyć chmod na /etc",
    "Harmonogram nie gwarantuje udanej transakcji."
  ],
  [
    "Pakiet jest blacklist w automacie. Co wpisać do utrzymania?",
    "Wyjątek z uzasadnieniem, właścicielem i terminem",
    "Wyłącznie nazwę koloru",
    "Dowód instalacji poprawki",
    "Hasło serwera",
    "Pominięcie trzeba monitorować."
  ],
  [
    "Raport pokazuje 0 oczekujących aktualizacji. Czy dowodzi braku luk w jar?",
    "Nie, zakres APT nie obejmuje każdej zależności jar",
    "Tak, APT analizuje wszystkie jary",
    "Tak, jeśli serwer ma mały uptime",
    "Tak, jeżeli apt jest nowy",
    "Inwentaryzacja musi wykraczać poza pakiety OS."
  ],
  [
    "Usługa została wyłączona z automatycznych restartów. Co trzeba zapewnić?",
    "Kontrolowany restart i weryfikację w osobnej procedurze",
    "Trwałe pomijanie poprawki",
    "Usunięcie usługi z dokumentacji",
    "Brak monitoringu",
    "Wyjątek od automatu przenosi odpowiedzialność."
  ],
  [
    "Livepatch ma status błędu. Czy przyjąć, że jądro poprawione?",
    "Nie, zweryfikować stan i plan alternatywny",
    "Tak, samo zainstalowanie klienta wystarczy",
    "Tak, false w reboot wystarczy",
    "Tak, uptime dowodzi sukcesu",
    "Obecność narzędzia nie jest wynikiem jego działania."
  ],
  [
    "Po zmianie pakietu numer binarki nowy, proces startował wcześniej. Jaki dowód jest brakujący?",
    "Stan działającego procesu po restartowaniu",
    "Sama nazwa pliku",
    "Sama ilość RAM",
    "Sama obecność źródła APT",
    "Weryfikuj aktywną instancję."
  ],
  [
    "Backup istnieje, ale nigdy nie odtwarzano. Co zrobić przed ryzykowną zmianą?",
    "Sprawdzić odtworzenie w odpowiednim środowisku",
    "Uznać nazwę pliku za dowód",
    "Usunąć poprzednie kopie",
    "Opublikować kopię danych",
    "Użyteczność kopii wymaga próby."
  ],
  [
    "Nowa wersja zmienia schemat danych. Co ogranicza rollback?",
    "Kompatybilność starego kodu z nowym schematem i plan danych",
    "Tylko nazwa symlinka",
    "Tylko numer JDK",
    "Tylko port TCP",
    "Zmiana artefaktu nie cofa migracji."
  ],
  [
    "Audyt pyta, co naprawdę wykonano w modelu mmap. Co odpowiedzieć?",
    "Mapowanie, wymianę pliku i ponowny odczyt własnego procesu",
    "Instalację poprawki OpenSSL",
    "Restart serwera produkcyjnego",
    "Reboot hosta",
    "Raport musi odpowiadać dowodom."
  ],
  [
    "Weryfikator pyta o brak systemd w lxlab. Co można potwierdzić tutaj?",
    "Odczyty APT i model procesów, bez działania timerów systemd",
    "Udany start każdego timera",
    "Pełną politykę Livepatch",
    "Działanie produkcyjnego failover",
    "Środowisko ogranicza zakres testu."
  ],
  [
    "Raport konfiguracji zawiera hasło proxy. Jak go udostępnić?",
    "Po usunięciu danych dostępu i kontroli zakresu",
    "W całości publicznie",
    "Jako opcję quizu z prawdziwym hasłem",
    "Przez zmianę hasła na nazwę hosta w systemie bez planu",
    "Raport nie powinien ujawniać sekretów."
  ],
  [
    "Proces testowy czeka na FIFO. Jak zakończyć go precyzyjnie?",
    "Wysłać sygnał przez własne FIFO i wait na własny PID",
    "pkill wszystkich python",
    "Zatrzymać Docker Desktop",
    "Zrestartować hosta",
    "Sprzątanie ma obejmować własne zasoby."
  ],
  [
    "Przed rm -rf zmienna laboratorium jest pusta. Co zrobić?",
    "Przerwać sprzątanie, sprawdzić zakres",
    "Usunąć /tmp w całości",
    "Użyć wildcard całego dysku",
    "Zignorować prefiks",
    "Walidacja ścieżki chroni cudze dane."
  ]
];
