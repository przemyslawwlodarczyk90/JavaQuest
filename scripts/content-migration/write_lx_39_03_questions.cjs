module.exports=[
  ["Przy zmianie hasła usługowego użyto argumentu CLI zawierającego jawny sekret. Co jest ryzykiem?", "Ujawnienie w historii lub liście procesów zależnie od narzędzia", "Automatyczna revokacja wszystkich tokenów", "Automatyczna ochrona przez sól w shadow", "Zawsze brak śladów poza pamięcią", "Sekret powinien trafiać przez odpowiednią prywatną ścieżkę wejścia."],
  ["Przegląd sudo ograniczono do grupy sudo. Jaki dostęp można pominąć?", "Reguły dla użytkowników i innych grup w sudoers oraz dołączanych plikach", "Każdy wpis nologin bez żadnych reguł", "Wszystkie konta z samą grupą prywatną", "Każdy wpis passwd z niezerowym UID", "Uprawnienia sudo wynikają z efektywnej polityki, nie jednej grupy."],
  [
    "Co przechowuje drugie pole shadow?",
    "Skrót hasła lub znacznik jego stanu",
    "Hasło jawne",
    "Listę grup",
    "Klucz SSH",
    "Pole nie zawiera jawnego hasła."
  ],
  [
    "Co oznacza prefiks ! przed skrótem?",
    "Blokadę weryfikacji hasła",
    "Usunięcie konta",
    "Unieważnienie wszystkich tokenów",
    "Zakończenie sesji",
    "Blokada hasła ma określony zakres."
  ],
  [
    "Czy passwd -l odbiera każdą metodę dostępu?",
    "Nie, klucze i inne mechanizmy wymagają osobnej oceny",
    "Tak, usuwa authorized_keys",
    "Tak, kończy procesy",
    "Tak, usuwa tokeny API",
    "Hasło to tylko jedna metoda."
  ],
  [
    "Co oznacza pusty hash w shadow?",
    "Ryzyko logowania bez hasła zależne od stosu uwierzytelniania",
    "Zawsze bezpieczną blokadę",
    "Automatyczne usunięcie konta",
    "Kluczowe logowanie SSH wyłącznie",
    "Pustego pola nie myl z blokadą."
  ],
  [
    "Jak pam_unix traktuje puste hasło bez nullok?",
    "Domyślnie odmawia uwierzytelnienia pustym hasłem",
    "Zawsze dopuszcza",
    "Zamienia je w losowe hasło",
    "Usuwa konto",
    "Opcje stosu zmieniają zachowanie."
  ],
  [
    "Co identyfikuje $y$ w formacie crypt?",
    "yescrypt",
    "Surowy SHA256",
    "Klucz Ed25519",
    "Hasło jawne",
    "Prefiks opisuje algorytm."
  ],
  [
    "Co identyfikuje $6$?",
    "SHA512-crypt",
    "Surowy SHA512 bez soli",
    "yescrypt",
    "Skrót klucza SSH",
    "To format hasła crypt."
  ],
  [
    "Po co sól w skrócie hasła?",
    "Utrudnia współdzielenie prekomputacji dla kont",
    "Zwiększa uprawnienia konta",
    "Jest drugim hasłem administratora",
    "Sama gwarantuje silne hasło",
    "Sól nie zastępuje dobrego hasła."
  ],
  [
    "Dlaczego hash hasła powinien być kosztowny?",
    "Spowalnia zgadywanie offline",
    "Umożliwia szybkie odzyskanie hasła",
    "Eliminuje potrzebę soli",
    "Blokuje klucze SSH",
    "Koszt ogranicza szybkość prób."
  ],
  [
    "Czy dobry algorytm gwarantuje bezpieczeństwo krótkiego hasła słownikowego?",
    "Nie, mała przestrzeń zgadywania nadal jest problemem",
    "Tak, każde hasło jest równie silne",
    "Tak, sól zwiększa długość hasła użytkownika",
    "Tak, blokada online działa na wykradziony hash",
    "Rozróżnij siłę hasła i koszt funkcji."
  ],
  [
    "Które pole shadow określa wygaśnięcie konta?",
    "Ósme",
    "Trzecie",
    "Piąte",
    "Drugie",
    "Wygaśnięcie konta jest osobną datą."
  ],
  [
    "Co oznacza trzecie pole shadow?",
    "Dzień ostatniej zmiany hasła",
    "Ostatnie logowanie SSH",
    "Czas utworzenia tokenu",
    "UID konta",
    "Dni liczone są od epoki."
  ],
  [
    "Co określa minimalny wiek hasła?",
    "Najkrótszy odstęp przed jego zmianą",
    "Minimalną długość hasła",
    "Minimum prób logowania",
    "Długość blokady faillock",
    "Wiek i jakość to różne parametry."
  ],
  [
    "Co określa maksymalny wiek hasła?",
    "Czas do wygaśnięcia hasła według polityki",
    "Datę wygaśnięcia konta bezpośrednio",
    "Maksimum sesji SSH",
    "Maksimum znaków hasła",
    "chage -M dotyczy hasła."
  ],
  [
    "Co oznacza ostrzeżenie chage -W?",
    "Liczbę dni ostrzegania przed wygaśnięciem hasła",
    "Próg błędów logowania",
    "Okno zliczania faillock",
    "Termin usunięcia home",
    "Ostrzeżenie nie jest blokadą."
  ],
  [
    "Do czego służy chage -I?",
    "Okres nieaktywności po wygaśnięciu hasła",
    "Dni od ostatniego logowania",
    "Blokada po błędnych próbach",
    "Usunięcie nieużywanej grupy",
    "Nieaktywność dotyczy wygasłego hasła."
  ],
  [
    "Co odczytuje chage -l?",
    "Informacje o wieku hasła i wygaśnięciu konta",
    "Hash hasła jawnie",
    "Listę aktywnych tokenów",
    "Wszystkie sesje sieciowe",
    "To odczyt polityki."
  ],
  [
    "Jak ustawić termin wygaśnięcia konta?",
    "chage -E DATA KONTO",
    "chage -M DATA KONTO",
    "passwd -S DATA",
    "usermod -aG DATA KONTO",
    "Opcje wieku i daty mają różne znaczenia."
  ],
  [
    "Co pokazuje passwd -S?",
    "Stan hasła i parametry jego wieku",
    "Jawne hasło",
    "Treść authorized_keys",
    "Listę uprawnień sudo",
    "Status nie ujawnia skrótu."
  ],
  [
    "Czy status L w passwd -S dowodzi pełnego offboardingu?",
    "Nie, opisuje blokadę hasła",
    "Tak, obejmuje każdy token zewnętrzny",
    "Tak, usuwa cron",
    "Tak, usuwa grupy",
    "Offboarding wymaga szerszej listy."
  ],
  [
    "Po co nologin kontu usługi?",
    "Ogranicza użycie interaktywnej powłoki",
    "Uniemożliwia każdy proces tego UID",
    "Usuwa dostęp plikowy",
    "Odbiera capabilities automatycznie",
    "Usługa może nadal działać pod tym UID."
  ],
  [
    "Czy konto bez powłoki nie potrzebuje kontroli grup?",
    "Potrzebuje, procesy nadal używają praw UID i GID",
    "Nie potrzebuje żadnych kontroli",
    "Nie ma w ogóle UID",
    "Grupy dotyczą wyłącznie powłok",
    "Uprawnienia są używane przez procesy."
  ],
  [
    "Co wyznacza pełne uprawnienia tradycyjnego root?",
    "UID 0 w odpowiednim kontekście",
    "Sama nazwa root",
    "GID 0 zawsze",
    "Powłoka /bin/bash",
    "Nazwa nie jest źródłem uprawnień."
  ],
  [
    "Dlaczego przeglądać wszystkie UID 0?",
    "Dodatkowe konta mogą mieć uprawnienia root",
    "UID 0 oznacza konto z pustym hasłem",
    "Tylko root może istnieć w passwd z nazwy",
    "UID 0 oznacza zawsze usługę",
    "Sprawdź numery, nie tylko nazwy."
  ],
  [
    "Co zachowuje usermod -aG?",
    "Istniejące grupy dodatkowe przy dodawaniu",
    "Istniejące hasło przy jego kasowaniu",
    "Wszystkie aktywne sesje po usunięciu konta",
    "Wygaśnięcie tokenów",
    "Bez -a zestaw grup może zostać zastąpiony."
  ],
  [
    "Czy usunięcie z grupy zmienia od razu każdą starą sesję?",
    "Nie, procesy mogą zachować wcześniejsze grupy",
    "Tak, jądro przepisuje wszystkie procesy",
    "Tak, kończy sesję automatycznie",
    "Tak, usuwa każdy token",
    "Grupy procesu ustala się przy tworzeniu kontekstu."
  ],
  [
    "Dlaczego grupa docker może dawać bardzo szeroki dostęp?",
    "Uprzywilejowany daemon może wykonywać operacje na hoście",
    "Grupa zawsze jest tylko do logów",
    "Każdy daemon zawsze rootless",
    "Nie może montować plików hosta",
    "Oceń model demona, nie nazwę grupy."
  ],
  [
    "Dlaczego disk jest grupą wrażliwą?",
    "Dostęp do urządzeń blokowych może omijać prawa plików",
    "Daje wyłącznie statystyki wolnego miejsca",
    "Jest zawsze równoważna adm",
    "Dotyczy tylko dysku USB użytkownika",
    "Surowe urządzenia obejmują zawartość systemu plików."
  ],
  [
    "Czy adm zawsze jest równoważna UID 0?",
    "Nie, zwykle daje odczyt logów wymagający oceny",
    "Tak, zawsze zmienia UID na 0",
    "Tak, zawsze modyfikuje shadow",
    "Nie, nie daje żadnego dostępu",
    "Nie utożsamiaj wszystkich grup wrażliwych."
  ],
  [
    "Dlaczego shadow jest grupą wrażliwą?",
    "Odczyt hashy umożliwia próby offline",
    "Gwarantuje automatyczną zmianę hasła",
    "Daje każdemu procesowi UID 0",
    "Jest tylko etykietą bez praw",
    "Hash to dane wymagające ochrony."
  ],
  [
    "Co sprawdza pam_pwquality?",
    "Jakość nowego hasła przy jego zmianie",
    "Wszystkie aktywne klucze SSH",
    "Stan połączeń TCP",
    "Każdy istniejący hash retroaktywnie",
    "Moduł działa w odpowiednim stosie PAM."
  ],
  [
    "Czy plik pwquality.conf sam aktywuje politykę?",
    "Nie, moduł musi być użyty przez właściwy stos",
    "Tak, każdy program czyta plik",
    "Tak, ssh publickey używa go zawsze",
    "Tak, zastępuje PAM",
    "Konfiguracja i integracja to dwa elementy."
  ],
  [
    "Co oznacza minclass?",
    "Minimum klas znaków",
    "Minimum dni życia hasła",
    "Minimum prób w oknie",
    "Minimum grup konta",
    "To parametr jakości znaków."
  ],
  [
    "Co oznacza usercheck?",
    "Kontrolę podobieństwa hasła do nazwy użytkownika",
    "Kontrolę UID 0",
    "Kontrolę klucza hosta SSH",
    "Przegląd grup docker",
    "Opcja dotyczy jakości hasła."
  ],
  [
    "Co oznacza dictcheck?",
    "Kontrolę słownikową hasła",
    "Kontrolę DNS",
    "Listę pakietów słownika APT",
    "Odczyt passwd",
    "To kontrola przewidywalności."
  ],
  [
    "Po co enforce_for_root?",
    "Aby odpowiednia kontrola jakości była egzekwowana także przy zmianie przez root",
    "Aby root miał UID inny niż 0",
    "Aby root utracił wszystkie klucze",
    "Aby wymusić reboot",
    "Dotyczy działania modułu, nie całości uprawnień root."
  ],
  [
    "Czy ustawienie nowej polityki automatycznie wzmacnia stare hasła?",
    "Nie, stare hasła wymagają osobnej procedury",
    "Tak, zostają przeliczone bez znajomości",
    "Tak, sól zmienia się codziennie",
    "Tak, passwd usuwa słabe hashe od razu",
    "Polityka zmiany nie przebudowuje istniejącego sekretu."
  ],
  [
    "Co robi pam_faillock?",
    "Liczy błędne próby i stosuje politykę blokady",
    "Sprawdza jakość nowego hasła",
    "Szyfruje shadow",
    "Usuwa użytkownika",
    "Mechanizmy jakości i blokady są odrębne."
  ],
  [
    "Co oznacza deny w faillock?",
    "Próg błędnych prób objętych polityką",
    "Datę wygaśnięcia konta",
    "Minimalny wiek hasła",
    "Numer portu SSH",
    "Próg ma sens wraz z oknem i stosem."
  ],
  [
    "Co oznacza fail_interval?",
    "Okno czasowe uwzględniania błędów",
    "Czas życia hasła",
    "Czas ważności klucza SSH",
    "Odstęp między backupami",
    "Nie myl okna z czasem odblokowania."
  ],
  [
    "Co oznacza unlock_time?",
    "Czas polityki odblokowania po blokadzie",
    "Odstęp przed zmianą hasła",
    "Maksymalny wiek konta",
    "Czas rotacji soli",
    "Dotyczy blokady prób."
  ],
  [
    "Do czego służy faillock --user KONTO?",
    "Odczytuje zapis prób dla konta",
    "Usuwa konto",
    "Zmienia shell",
    "Unieważnia tokeny API",
    "Reset wymaga osobnej opcji."
  ],
  [
    "Co robi faillock --reset dla użytkownika?",
    "Czyści jego licznik prób",
    "Zmienia hasło",
    "Odbiera grupy",
    "Usuwa authorized_keys",
    "Reset licznika nie jest zmianą sekretu."
  ],
  [
    "Dlaczego kolejność modułów PAM ma znaczenie?",
    "Wpływa na przepływ sukcesu i błędu uwierzytelniania",
    "Zmienia tylko kolor komunikatu",
    "Moduły zawsze wykonują się identycznie",
    "Kolejność dotyczy tylko nazw plików",
    "Control flags i stos decydują o działaniu."
  ],
  [
    "Czy skopiowanie przykładu PAM wystarcza na każdej dystrybucji?",
    "Nie, trzeba uwzględnić lokalny stos i przetestować odzyskanie dostępu",
    "Tak, wszystkie stosy identyczne",
    "Tak, plik jest tylko dokumentacją",
    "Tak, testy są niepotrzebne",
    "Błąd może zablokować dostęp."
  ],
  [
    "Czy blokada online chroni przed zgadywaniem wykradzionego hasha?",
    "Nie, offline omija serwerowy licznik prób",
    "Tak, każdy hash łączy się z PAM",
    "Tak, deny jest zapisane w hash",
    "Tak, cron blokuje GPU",
    "Koszt hasha i jakość chronią offline."
  ],
  [
    "Jak ograniczyć ujawnienie podczas audytu shadow?",
    "Raportować nazwy i potrzebne pola bez hashy",
    "Publikować cały plik",
    "Wklejać hashe do quizu",
    "Dodawać shadow do Git",
    "Raport powinien minimalizować dane."
  ],
  [
    "Czy lastlog never logged in dowodzi zbędnego konta?",
    "Nie, konto może być usługowe lub logowanie nieobjęte źródłem",
    "Tak, zawsze usunąć od razu",
    "Tak, konto nie ma plików",
    "Tak, nie ma zadań",
    "Użycie trzeba zweryfikować z właścicielem."
  ],
  [
    "Dlaczego userdel -r bywa ryzykowne?",
    "Może usunąć potrzebny home i pocztę",
    "Zawsze usuwa wszystkie pliki UID na wszystkich dyskach",
    "Zawsze wycofuje tokeny zewnętrzne",
    "Zawsze kończy wszystkie sesje",
    "Przejęcie danych powinno poprzedzić usuwanie."
  ],
  [
    "Co oznacza osierocony UID pliku?",
    "Właściciel numeryczny nie ma obecnego mapowania konta",
    "Plik nie ma żadnych praw",
    "Plik został zaszyfrowany",
    "Plik zawsze usunięty",
    "UID może zostać ponownie przypisany."
  ],
  [
    "Model passwd ma extra:x:0:0. Co wykryto?",
    "Dodatkową nazwę z UID 0",
    "Wyłącznie dodatkową grupę",
    "Puste hasło",
    "Konto bez powłoki",
    "Uprawnienia wynikają z UID."
  ],
  [
    "Model svc ma shell nologin i UID 1200. Czy to dowód całej izolacji usługi?",
    "Nie, trzeba ocenić prawa, grupy i konfigurację procesu",
    "Tak, shell odbiera wszystkie prawa",
    "Tak, UID 1200 jest zawsze sandboxem",
    "Tak, proces nie może powstać",
    "Powłoka to pojedynczy element."
  ],
  [
    "Model empty ma puste drugie pole. Jaki wniosek do audytu?",
    "Ryzykowny stan do oceny stosu uwierzytelniania",
    "Hasło poprawnie zablokowane przez !",
    "Konto wygasło wczoraj",
    "Klucze SSH są usunięte",
    "Znaczenie logowania zależy od konfiguracji."
  ],
  [
    "Model bob zaczyna hash od ! i ma datę wygaśnięcia. Co rozróżnić?",
    "Blokadę hasła i osobne wygaśnięcie konta",
    "Dwa identyczne ustawienia",
    "Hasło jawne i sól",
    "Dwa tokeny API",
    "To różne mechanizmy."
  ],
  [
    "Po passwd -l konto nadal loguje się kluczem. Czy polecenie musiało zawieść?",
    "Nie, blokada hasła nie usuwa klucza",
    "Tak, -l zawsze usuwa klucze",
    "Tak, klucze nie działają bez hasła",
    "Tak, passwd zmienia sshd_config",
    "Weryfikuj zakres blokady."
  ],
  [
    "Wygaśnięcie konta ustawiono lokalnie, aplikacja używa SSO zewnętrznego. Co sprawdzić?",
    "Rzeczywiste źródło tożsamości i politykę aplikacji",
    "Uznać lokalne shadow za globalne",
    "Usunąć wszystkie lokalne logi",
    "Zmienić shell i zakończyć audyt",
    "Inny system uwierzytelnia osobno."
  ],
  [
    "Osoba odeszła, token API nadal ważny. Czego zabrakło?",
    "Odebrania dostępu poza hasłem systemowym",
    "Zmiany minlen wyłącznie",
    "Zmiany algorytmu hash wyłącznie",
    "Odczytu uname",
    "Token ma własny cykl."
  ],
  [
    "Klucz usunięto tylko na jednym z pięciu hostów. Co pozostaje?",
    "Inwentaryzacja i odwołanie na pozostałych miejscach",
    "Pełne odebranie dostępu wszędzie",
    "Automatyczne cofnięcie z GitHub",
    "Automatyczne wygaszenie certyfikatów",
    "Zakres revokacji musi być pełny."
  ],
  [
    "W authorized_keys klucz współdzielą dwie osoby. Jaki problem utrzymania?",
    "Trudno przypisać i selektywnie odwołać dostęp",
    "Lepsza identyfikacja indywidualna",
    "Automatyczna rotacja przy odejściu",
    "Brak potrzeby audytu",
    "Indywidualne klucze ułatwiają kontrolę."
  ],
  [
    "Konto usunięto przed przejęciem crona. Co może się zepsuć?",
    "Zadania i odpowiedzialność za ich wyniki",
    "Wyłącznie format hasha",
    "Wyłącznie długość klucza",
    "Wyłącznie data chage",
    "Zadania wymagają właściciela."
  ],
  [
    "Katalog usuniętego konta zawiera potrzebny skrypt. Co należało zrobić wcześniej?",
    "Przejąć dane i zadania według planu",
    "Zwiększyć deny",
    "Skrócić unlock_time",
    "Zmienić sól",
    "Offboarding obejmuje zasoby."
  ],
  [
    "Nowe konto otrzymało UID dawnej osoby. Co sprawdzić?",
    "Pliki i uprawnienia zachowane pod tym UID",
    "Tylko nazwę nowego konta",
    "Tylko długość hasła",
    "Tylko status timera",
    "Numeryczne własności mogą wrócić."
  ],
  [
    "Użytkownik usunięty z docker ma starą sesję. Co ocenić?",
    "Grupy istniejącego procesu i zakończenie sesji",
    "Tylko getent group jako pełny dowód",
    "Tylko nazwę konta",
    "Tylko nową maskę umask",
    "Stary kontekst może zachować dostęp."
  ],
  [
    "getent group nie wymienia osoby, ale id pokazuje grupę jako podstawową. Co wynika?",
    "Lista dodatkowych członków nie opisuje całego członkostwa",
    "id zawsze błędne",
    "Grupa nie daje praw",
    "Konto jest usunięte",
    "Podstawowa grupa jest w passwd/NSS."
  ],
  [
    "NSS używa LDAP, lokalny passwd nie pokazuje osoby. Czy osoba nie istnieje?",
    "Nie, sprawdź getent i źródło tożsamości",
    "Tak, tylko passwd decyduje",
    "Tak, LDAP jest wyłącznie do haseł",
    "Tak, brak lokalnego home usuwa konto",
    "Tożsamości mogą pochodzić z NSS."
  ],
  [
    "pwquality.conf ma minlen, ale zmiana hasła go ignoruje. Co zbadać?",
    "Stos PAM użyty przez dany program i jego opcje",
    "Wyłącznie numer UID",
    "Wyłącznie tryb home",
    "Wyłącznie plik passwd",
    "Konfiguracja bez integracji może nie działać."
  ],
  [
    "Hasło ustawione administracyjnie pomija jakość. Co sprawdzić?",
    "Politykę egzekwowania dla root i użyte narzędzie",
    "Wyłącznie lastlog",
    "Wyłącznie chage -E",
    "Wyłącznie grupę adm",
    "Ścieżki zmiany mogą mieć różne reguły."
  ],
  [
    "Po instalacji pam_pwquality stare hasła nadal działają. Czy to dziwne?",
    "Nie, polityka nie zna jawnych starych haseł",
    "Tak, każdy hash powinien się natychmiast zmienić",
    "Tak, powinna usuwać konta",
    "Tak, musi odwołać SSH keys",
    "Nowe kontrole dotyczą zmian."
  ],
  [
    "faillock.conf ma deny 5, lecz błędy nie są liczone. Co sprawdzić?",
    "Włączenie i kolejność modułu w stosie danej usługi",
    "Wyłącznie minclass",
    "Wyłącznie UID pliku home",
    "Wyłącznie datę konta",
    "Plik nie aktywuje sam modułu."
  ],
  [
    "Po pięciu próbach blokada jest łatwo wywoływana przez obcą osobę. Jaki kompromis trzeba ocenić?",
    "Ochronę zgadywania wobec ryzyka odmowy dostępu",
    "Wyłącznie długość soli",
    "Wyłącznie numer rewizji APT",
    "Wyłącznie nazwę powłoki",
    "Blokada może być narzędziem DoS."
  ],
  [
    "Ustawiono fail_interval 900 i unlock_time 900. Czy to ten sam parametr?",
    "Nie, okno zliczania i czas odblokowania są odrębne",
    "Tak, jeden zawsze ignorowany",
    "Tak, oba oznaczają wiek hasła",
    "Tak, oba datę konta",
    "Równa liczba nie znosi różnicy semantycznej."
  ],
  [
    "Operator resetuje faillock po ataku. Co powinno towarzyszyć resetowi?",
    "Ocena przyczyny i dalszego zagrożenia",
    "Założenie, że reset zmienił hasło",
    "Usunięcie dowodów",
    "Stałe wyłączenie kontroli",
    "Reset licznika nie usuwa przyczyny."
  ],
  [
    "Konto ma długi hash yescrypt i hasło słownikowe. Co jest słabością?",
    "Przewidywalny sekret",
    "Brak UID 0",
    "Brak grupy shadow",
    "Sama długość hasha",
    "Długość reprezentacji nie mierzy entropii hasła."
  ],
  [
    "Dwie osoby mają ten sam sekret i różne hashe. Co może to wyjaśniać?",
    "Różne sole",
    "Zawsze błąd PAM",
    "Zawsze różne hasła",
    "Usunięcie jednego konta",
    "Sól różnicuje wynik."
  ],
  [
    "Wykradziono shadow. Czy zmiana deny wystarczy?",
    "Nie, trzeba obsłużyć kompromitację sekretów i ryzyko offline",
    "Tak, deny blokuje obliczenia napastnika",
    "Tak, hash przestaje istnieć u napastnika",
    "Tak, chage -l odzyskuje plik",
    "Zdalna kopia pozostaje poza kontrolą PAM."
  ],
  [
    "Raport kont ma zawierać wyłącznie nazwy z pustym hasłem. Jakie dane zbędne?",
    "Całe drugie pole innych kont",
    "Nazwy wykrytych kont",
    "Zakres analizowanego źródła",
    "Data audytu",
    "Minimalizuj ujawnienie hashy."
  ],
  [
    "chage -M 90 ustawione, ale konto wygasa jutro przez -E. Co zinterpretować?",
    "Osobną datę wygaśnięcia konta",
    "M 90 anuluje E zawsze",
    "E oznacza tylko ostrzeżenie",
    "Konto ma 90 sesji",
    "Parametry nie są zamienne."
  ],
  [
    "chage -I 7 oznacza konto nieużywane 7 dni od lastlog. Czy to poprawne?",
    "Nie, to okres po wygaśnięciu hasła",
    "Tak, czyta lastlog",
    "Tak, liczy SSH sessions",
    "Tak, używa cron",
    "Nazwę nieaktywność interpretuj według narzędzia."
  ],
  [
    "usermod -G nowa bez -a usuwa inne grupy dodatkowe. Co zaszło?",
    "Zastąpiono zestaw grup",
    "Dodano tylko nową grupę",
    "Zmieniono UID",
    "Wyłączono NSS",
    "Opcja -a rozróżnia dodanie."
  ],
  [
    "Na koncie usługi uruchomiono proces przez root mimo nologin. Co wykazano?",
    "Root może uruchamiać proces z tym UID; shell nie jest pełną blokadą",
    "Udane logowanie SSH kluczem",
    "Brak praw plikowych UID",
    "Brak działania nologin w każdej sytuacji",
    "Uruchomienie przez administratora nie jest testem uwierzytelnienia."
  ],
  [
    "runuser jako root wykonał polecenie wygasłego konta. Czy to dowód dostępu SSH?",
    "Nie, test nie użył SSH ani jego stosu",
    "Tak, każda usługa uwierzytelnia identycznie",
    "Tak, expiry nie istnieje",
    "Tak, publickey zawsze działa",
    "Oceń właściwą ścieżkę dostępu."
  ],
  [
    "Chcesz testować PAM na serwerze zdalnym. Co przygotować?",
    "Dostęp awaryjny, kopię i plan odtworzenia stosu",
    "Wyłączyć wszystkie sesje przed testem",
    "Nadpisać stos bez kopii",
    "Usunąć konto administratora",
    "Błąd uwierzytelniania może odciąć dostęp."
  ],
  [
    "W shadow fixture znalazło się 10 pól. Co zrobić?",
    "Zgłosić nieprawidłowy format modelu",
    "Zignorować zawsze ostatnie pole",
    "Uznać konto za root",
    "Wnioskować o jakości hasła",
    "Poprawne mapowanie wymaga schematu."
  ],
  [
    "Nieznany prefiks hash pojawił się w audycie. Co zrobić?",
    "Zweryfikować format i wsparcie lokalnego mechanizmu",
    "Uznać go automatycznie za hasło jawne",
    "Usunąć konto bez analizy",
    "Zamienić na MD5 dla zgodności",
    "Nie zgaduj znaczenia nowego formatu."
  ],
  [
    "Blokada hasła i nologin ustawione, istniejąca sesja nadal działa. Co brakowało?",
    "Przeglądu i zakończenia aktywnych sesji według procedury",
    "Zmiany minclass",
    "Zmiany soli tylko",
    "Dopisania grupy adm",
    "Kontrole nowego dostępu nie zamykają zawsze istniejącego."
  ],
  [
    "Osoba znała współdzielone hasło usługi. Co uwzględnić w odejściu?",
    "Rotację tego sekretu i zależnych konfiguracji",
    "Wyłącznie usunięcie lokalnego passwd",
    "Wyłącznie zmianę home",
    "Wyłącznie datę shadow",
    "Współdzielone sekrety mają własny zakres."
  ],
  [
    "Konto jest właścicielem danych zespołu. Co przed usunięciem?",
    "Ustalić retencję i przekazanie własności",
    "Usunąć wszystko przez -r od razu",
    "Nadać wszystkim chmod 777",
    "Zostawić bezterminowo nieopisany UID",
    "Przekazanie musi zachować potrzebne zasoby."
  ],
  [
    "Konto ma brak logowań, ale aktywny timer. Co wynika?",
    "Brak interaktywnego logowania nie oznacza braku użycia",
    "Timer dowodzi, że hasło jest puste",
    "Timer zawsze działa jako root",
    "Należy usunąć konto natychmiast",
    "Usługowe użycie wymaga osobnej oceny."
  ],
  [
    "Audyt ograniczono do lokalnego passwd. Jak opisać wynik?",
    "Jako audyt tego źródła, bez twierdzenia o całym SSO",
    "Jako komplet wszystkich tożsamości świata",
    "Jako test SSH każdego konta",
    "Jako pełną revokację tokenów",
    "Zakres dowodu musi być jawny."
  ],
  [
    "Nowe konto laboratorium zderza się z istniejącą nazwą. Co zrobić?",
    "Przerwać tworzenie i wybrać niekolidującą nazwę",
    "Zmienić istniejącemu kontu hasło",
    "Usunąć istniejące konto",
    "Wymusić wspólny UID bez analizy",
    "Laboratorium nie powinno modyfikować cudzych kont."
  ],
  [
    "Przed userdel nazwa nadal istnieje, ale UID różni się od zapisanego. Co zrobić?",
    "Przerwać i zbadać zmianę tożsamości",
    "Usunąć konto bez kontroli",
    "Usunąć wszystkie UID większe od 1000",
    "Wyłączyć NSS globalnie",
    "Weryfikuj tożsamość zasobu przed usunięciem."
  ],
  [
    "Po userdel własny katalog ma pliki z numerycznym UID. Czy to możliwe?",
    "Tak, bez -r dane nie są automatycznie usuwane",
    "Nie, userdel usuwa cały dysk UID",
    "Nie, pliki tracą wszystkie prawa",
    "Nie, UID pliku jest tekstową nazwą",
    "Własność jest numeryczna."
  ],
  [
    "Usunięcie modelowej etykiety klucza przeszło. Co potwierdzono?",
    "Filtr modelu, nie rzeczywistą odmowę SSH",
    "Pełną revokację certyfikatu SSH",
    "Wyłączenie wszystkich sesji",
    "Zmianę hasła",
    "Model nie jest authorized_keys."
  ],
  [
    "W laboratorium zapisano pwquality.fixture, bez PAM. Co można raportować?",
    "Treść modelu, bez egzekwowania jakości",
    "Udane odrzucenie słabego hasła na konsoli",
    "Pełną aktywację PAM",
    "Odblokowanie faillock",
    "Nie deklaruj niewykonanych testów."
  ],
  [
    "Wykonano usermod -L -e 1 na własnym koncie testowym. Co sprawdzono odczytem shadow?",
    "Stan blokady hasła i pole wygaśnięcia",
    "Odmowę SSO w każdej aplikacji",
    "Usunięcie klucza SSH",
    "Zakończenie sesji",
    "Metadane to zakres tego sprawdzenia."
  ],
  [
    "Przed sprzątaniem własne konto ma aktywny proces. Co zrobić?",
    "Zidentyfikować i zakończyć własny proces przed usunięciem",
    "pkill wszystkich użytkowników",
    "Usunąć wszystkie konta usług",
    "Zrestartować hosta bez planu",
    "Sprzątanie ogranicz do laboratorium."
  ],
  [
    "Raport zawiera prawdziwe hashe. Co poprawić?",
    "Usunąć je z raportu i ocenić zakres ujawnienia",
    "Dodać raport publicznie do Git",
    "Uznać je za nieszkodliwe",
    "Zwiększyć tylko minlen",
    "Hashe są materiałem do prób offline."
  ],
  [
    "Plan odejścia obejmuje jeden host, osoba miała również CI i chmurę. Co jest brakujące?",
    "Odebranie tożsamości, kluczy i tokenów w całym uzgodnionym zakresie",
    "Wyłącznie nowe chage -M lokalnie",
    "Wyłącznie nowa grupa primary",
    "Wyłącznie zmiana passwd fixture",
    "Offboarding wykracza poza jeden Linux."
  ]
];
