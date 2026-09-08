# JavaQuest — Etap 2: przebudowa jakości i wyglądu lekcji

> Ten plik zawiera GOTOWĄ, kompletną instrukcję do wklejenia jako pierwsza wiadomość w NOWEJ
> sesji Claude Code, kiedy zaczynamy drugi etap projektu (przebudowa treści i wyglądu lekcji
> JavaQuest). Nie jest to plik wykonywany automatycznie — to tekst do skopiowania i wklejenia.

---

Pracujemy nad drugim etapem projektu edukacyjnego JavaQuest. Pierwszy etap kursu został
zakończony, jednak obecna zawartość lekcji jest zbyt mało atrakcyjna wizualnie, nierówna
merytorycznie i miejscami wygląda jak surowa ściana tekstu.

Projekt ma część backend oraz frontend. Najpierw dokładnie przeanalizuj jego strukturę, sposób
przechowywania i wyświetlania lekcji, zadań oraz quizów. Sprawdź również, czy treści są zapisane
bezpośrednio w kodzie, w plikach danych, w bazie, czy są generowane przez osobny mechanizm.

Nie rozpoczynaj od masowego modyfikowania wszystkich lekcji. Najpierw przedstaw:

- Gdzie znajdują się treści lekcji, zadań, quizów i tytułów tematów.
- Jakie komponenty odpowiadają za ich renderowanie.
- Czy istnieje jeden wspólny model danych dla wszystkich lekcji.
- Gdzie znajduje się lampka lub status informujący, że lekcja jest gotowa.
- Jakie pliki należałoby zmienić, aby wprowadzić jeden spójny standard wizualny.
- Jak bezpiecznie przeprowadzić migrację wszystkich lekcji etapami.
- Jakie testy i sposoby weryfikacji są dostępne w projekcie.

Po analizie zaproponuj plan wykonania prac. Nie ogłaszaj zakończenia zadania, dopóki zmiany nie
zostaną naprawdę wdrożone i zweryfikowane.

## Główny cel

Każda lekcja JavaQuest ma być:

- poprawna technicznie;
- wyczerpująca merytorycznie;
- napisana prostym i naturalnym językiem;
- zrozumiała dla osoby uczącej się danego zagadnienia pierwszy raz;
- oparta na łatwych do wyobrażenia przykładach;
- wystarczająca do rozwiązania wszystkich przypisanych do niej ćwiczeń;
- atrakcyjna i czytelna wizualnie;
- praktyczna, a nie ograniczona do suchej definicji;
- spójna stylistycznie z pozostałymi lekcjami.

Na tym etapie projektujemy interfejs wyłącznie pod komputery. Nie poświęcaj czasu na
przygotowywanie osobnego wyglądu mobilnego. Zmiany nie powinny jednak celowo psuć istniejącej
aplikacji na mniejszych ekranach.

## Obowiązkowa struktura każdej lekcji

Każda lekcja powinna mieć uporządkowaną strukturę składającą się z następujących sekcji:

### 1. Wprowadzenie

Krótkie wyjaśnienie:

- czego dotyczy lekcja;
- czego użytkownik się nauczy;
- dlaczego zagadnienie jest ważne;
- z jakimi wcześniejszymi tematami się łączy.

Nie zaczynaj lekcji od dużej liczby technicznych szczegółów.

### 2. Prosta definicja

Każde nowe pojęcie musi otrzymać jednoznaczną definicję napisaną prostym językiem.

Definicja ma wyjaśniać, czym coś jest, ale również czym nie jest, jeżeli istnieje częste
nieporozumienie. Nie używaj nieprecyzyjnych uproszczeń, które później trzeba będzie odkręcać.

### 3. Analogia

Każda lekcja powinna zawierać łatwą do wyobrażenia analogię. Przykładowo:

- klasa jako plan domu, a obiekt jako konkretny wybudowany dom;
- klasa Vehicle jako ogólny opis pojazdu, a Car i Motorcycle jako bardziej szczegółowe typy;
- interfejs jako umowa określająca wymagane zachowanie;
- kolejka jako kolejka ludzi do kasy;
- stos jako stos talerzy;
- request i response jako zamówienie wysłane do restauracji i odpowiedź z kuchni.

Analogia ma pomagać w zrozumieniu, ale nie może zastępować technicznego wyjaśnienia ani
wprowadzać nieprawdziwych informacji.

### 4. Przykład wizualny

Tam, gdzie pomaga to w nauce, pokaż relacje wizualnie. Na przykład:

```
Vehicle → Car → ElectricCar
```

lub:

```
Klient → HTTP request → serwer → HTTP response → klient
```

Nie przedstawiaj wszystkiego jako zwykłego akapitu. Relacje, hierarchie i kolejne etapy powinny
być widoczne na pierwszy rzut oka.

### 5. Pierwsza wersja kodu – najprostszy przykład

Pierwszy przykład ma pokazywać wyłącznie podstawowy mechanizm, bez niepotrzebnych klas i
zależności.

Kod powinien:

- być kompletny w zakresie prezentowanego zagadnienia;
- używać czytelnych nazw;
- być prawidłowo sformatowany;
- zawierać tylko potrzebne komentarze;
- dać się łatwo przeanalizować linia po linii.

Po kodzie wyjaśnij najważniejsze instrukcje oraz efekt uruchomienia.

### 6. Druga wersja kodu – praktyczny przypadek

Drugi przykład ma pokazywać zastosowanie zagadnienia w sytuacji przypominającej prawdziwy
projekt.

Przykłady mogą dotyczyć między innymi:

- pojazdów i ich typów;
- produktów i zamówień;
- użytkowników i uprawnień;
- płatności;
- biblioteki i wypożyczania książek;
- systemu powiadomień;
- REST API;
- zapisu oraz odczytu plików;
- przetwarzania kolekcji;
- logowania zdarzeń;
- konfiguracji aplikacji.

Nie używaj stale jednego przykładu w całym kursie. Dobieraj domenę do omawianego problemu.

Na szerokim ekranie oba przykłady mogą być prezentowane w dwóch czytelnych kolumnach, jeśli ma
to sens. W przeciwnym razie powinny znajdować się jeden pod drugim.

### 7. Wyjaśnienie działania krok po kroku

Po przykładach wyjaśnij:

- co dzieje się w kodzie;
- w jakiej kolejności;
- jakie obiekty lub wartości powstają;
- co znajduje się w pamięci;
- co zwraca dana metoda;
- jakie będą wyniki;
- dlaczego kod zachowuje się właśnie w ten sposób.

Nie zakładaj, że użytkownik sam domyśli się brakujących etapów.

### 8. Praktyczne zastosowanie

Każda lekcja musi odpowiadać na pytania:

- Po co mi to jako programiście?
- Gdzie spotkam to w prawdziwym projekcie?
- Jaki problem rozwiązuje ta funkcja, konstrukcja albo biblioteka?
- Czy nadal używa się tego we współczesnych projektach?
- Czy istnieje nowsze lub częściej stosowane rozwiązanie?

### 9. Typowe błędy i pułapki

Dodaj sekcję pokazującą najczęstsze błędy. Jeżeli to możliwe, przedstaw:

- błędny kod;
- powód błędu;
- poprawioną wersję;
- zasadę pozwalającą uniknąć problemu.

Przykład błędny musi być wyraźnie oznaczony, żeby użytkownik nie pomylił go z zalecanym
rozwiązaniem.

### 10. Kiedy używać, a kiedy nie

Jeżeli zagadnienie dotyczy rozwiązania mającego określone zalety i ograniczenia, wyjaśnij:

- kiedy warto go użyć;
- kiedy będzie przerostem formy;
- jakie ma alternatywy;
- jakie konsekwencje może mieć zły wybór.

### 11. Podsumowanie

Na końcu umieść krótkie podsumowanie najważniejszych informacji. Nie powtarzaj całej lekcji
słowo w słowo.

## Pełne pokrycie API dla lekcji o "narzędziu z wieloma metodami"

Niektóre lekcje uczą nie tyle pojedynczego mechanizmu językowego, co CAŁEGO narzędzia/klasy
złożonej z wielu powiązanych metod — przykłady: `String`, `StringBuilder`, `Math`, `Arrays`,
`Random`/`SecureRandom`, `LocalDate`/`LocalTime`/`LocalDateTime`, `BigInteger`/`BigDecimal`,
`Integer`/inne klasy opakowujące, operatory (arytmetyczne/bitowe/porównania) jako zamknięty
zestaw symboli, oraz — w dalszych rozdziałach — kolekcje (`List`/`Map`/`Set` i ich odmiany),
`Stream`, klasy narzędziowe z `java.nio.file`, `java.util.Objects` itd.

Dla TAKICH lekcji nie wystarczy pokazać kilku przykładowych metod — teoria MUSI systematycznie
wymienić i pokazać (choćby krótkim przykładem użycia) WSZYSTKIE metody/operatory realnie
związane z omawianym zjawiskiem, które są używane w praktyce, nie tylko te, które akurat
pojawiły się w pierwotnym materiale źródłowym. Zbuduj to jako czytelną, wyczerpującą listę
(np. tabelę albo pogrupowane punkty w sekcji definicji/zastosowania/wizualnej), a nie
przypadkowy podzbiór.

Nie każda lekcja tego wymaga — zwykły mechanizm językowy (np. pętla `for`, instrukcja `if`,
konstruktor) nie ma "listy metod" do wyczerpania. Rozpoznaj to rozróżnienie świadomie: jeśli
temat lekcji to KONKRETNA klasa/interfejs z biblioteki standardowej albo zamknięty zestaw
operatorów, zastosuj to pełne pokrycie; jeśli to koncepcja/konstrukcja języka, trzymaj się
zwykłej struktury 11 sekcji bez sztucznego dopisywania listy.

Przy migracji rozdziałami: zanim uznasz lekcję za skończoną, zadaj sobie pytanie "czy ta lekcja
opisuje narzędzie z wieloma metodami, i czy wymieniłem/pokazałem WSZYSTKIE istotne w praktyce
metody tego narzędzia?" — jeśli nie, uzupełnij PRZED przejściem dalej. Dotyczy to też lekcji już
zmigrowanych we wcześniejszych etapach — wróć i popraw je, zanim ruszysz dalej z nowymi
rozdziałami.

## Zgodność teorii z zadaniami i quizami

Teoria każdej lekcji musi dostarczać wiedzy potrzebnej do wykonania wszystkich przypisanych
zadań.

Dla każdej lekcji sprawdź:

- Jakich pojęć wymagają zadania.
- Czy wszystkie te pojęcia zostały wcześniej wyjaśnione.
- Czy w teorii znajduje się przykład podobnego mechanizmu, ale nie gotowe rozwiązanie zadania.
- Czy poziom trudności zadań rośnie stopniowo.
- Czy treść zadania jest jednoznaczna.
- Czy quiz sprawdza materiał rzeczywiście omówiony w lekcji.
- Czy odpowiedzi quizu są jednoznaczne.
- Czy wskazana odpowiedź jest naprawdę poprawna.
- Czy wyjaśnienie odpowiedzi tłumaczy jej przyczynę, a nie tylko informuje, że jest poprawna.

Nie podawaj w teorii identycznego rozwiązania jak w ćwiczeniu. Użytkownik ma otrzymać potrzebne
narzędzia i analogiczny przykład, ale nadal powinien samodzielnie rozwiązać zadanie.

## Weryfikacja merytoryczna

Nie ograniczaj się do rozbudowywania istniejących tekstów. Każdą lekcję sprawdź pod kątem
prawdziwości informacji.

Szczególnie dokładnie weryfikuj:

- funkcje zależne od konkretnej wersji Javy;
- status funkcji preview, incubator oraz funkcji stabilnych;
- numery i znaczenie JEP-ów;
- wersje LTS;
- API usunięte, oznaczone jako przestarzałe albo zastąpione;
- zachowanie JVM;
- bezpieczeństwo pamięci;
- protokół HTTP;
- REST;
- kodowanie znaków;
- biblioteki zewnętrzne;
- Lombok;
- Spring;
- JPA i Hibernate;
- narzędzia budowania.

W kwestiach zależnych od wersji korzystaj przede wszystkim z dokumentacji OpenJDK, JEP-ów oraz
oficjalnej dokumentacji bibliotek.

Nie używaj kategorycznych stwierdzeń typu „to całkowicie eliminuje ryzyko”, jeśli technologia
jedynie to ryzyko ogranicza. Wyraźnie rozdzielaj uproszczenie dydaktyczne od ścisłego wyjaśnienia
technicznego.

## Polskie znaki i język

Popraw polskie znaki oraz błędy językowe we wszystkich treściach widocznych dla użytkownika:

- nazwach rozdziałów;
- nazwach tematów i lekcji;
- teorii;
- zadaniach;
- quizach;
- odpowiedziach;
- wyjaśnieniach odpowiedzi;
- przyciskach;
- etykietach;
- komunikatach;
- podsumowaniach.

Przykładowo teksty typu:

- wyjatkowo;
- wlasciwa;
- zaleznosci;
- pamiec;
- narzedzie;
- wywolywana;

powinny zostać poprawione na:

- wyjątkowo;
- właściwa;
- zależności;
- pamięć;
- narzędzie;
- wywoływana.

Nie zmieniaj automatycznie:

- nazw klas;
- nazw metod;
- nazw zmiennych;
- ścieżek;
- identyfikatorów;
- nazw pakietów;
- fragmentów składni;
- komend;
- wartości wymagających znaków ASCII.

Poprawki muszą być wykonywane świadomie, a nie przez globalną zamianę znaków.

Styl tekstów powinien być naturalny. Nie nadużywaj:

- wielkich liter;
- wykrzykników;
- cudzysłowów;
- nawiasów;
- sztucznych określeń;
- bardzo długich akapitów;
- powtarzania tego samego zdania innymi słowami.

## Nowa szata graficzna lekcji

Przygotuj jeden wspólny i wielokrotnie używany system prezentowania treści. Nie twórz osobnych,
przypadkowych stylów dla każdej lekcji.

Zaprojektuj rozpoznawalne warianty kafelków:

- wprowadzenie;
- definicja;
- analogia;
- przykład wizualny;
- podstawowy kod;
- praktyczny kod;
- opis działania;
- zastosowanie;
- ważna informacja;
- ostrzeżenie lub pułapka;
- błędny kod;
- poprawny kod;
- podsumowanie.

Każdy typ powinien mieć spójny:

- kolor;
- ikonę;
- nagłówek;
- obramowanie;
- tło;
- odstępy;
- typografię.

Zachowaj obecny ciemny charakter aplikacji, ale popraw:

- kontrast tekstu;
- czytelność kodu;
- szerokość kolumny;
- wielkość nagłówków;
- odstępy między sekcjami;
- hierarchię informacji;
- wyróżnienie najważniejszych elementów;
- formatowanie fragmentów kodu;
- czytelność zakładek „Teoria”, „Zadania” i „Quiz”.

Kod nie może być wyświetlany jako zwykły tekst. Powinien znajdować się w prawidłowym bloku kodu
z zachowanymi wcięciami i czytelnym wyróżnianiem składni, jeśli projekt już obsługuje taką
możliwość.

Nie dodawaj dużej liczby dekoracji, animacji ani jaskrawych kolorów. Interfejs ma wyglądać
nowocześnie i przyjemnie, ale najważniejsza jest czytelność materiału.

## Usunięcie oznaczenia gotowej lekcji

Wszystkie lekcje są obecnie traktowane jako gotowe, dlatego lampka lub inne oznaczenie „lekcja
gotowa” nie wnosi już wartości.

Usuń je z interfejsu wraz z niepotrzebnym tekstem lub tooltipem.

Najpierw sprawdź, czy status ten:

- steruje dostępnością lekcji;
- wpływa na filtrowanie;
- jest wykorzystywany przez backend;
- znajduje się w bazie danych;
- ma znaczenie dla postępu użytkownika;
- jest używany w testach.

Jeśli status jest nadal potrzebny wewnętrznie, pozostaw dane i logikę backendową, a usuń
wyłącznie niepotrzebne oznaczenie wizualne. Nie usuwaj kolumny z bazy ani pól modelu bez analizy
zależności.

## Sposób realizacji

Nie przerabiaj wszystkich lekcji jednocześnie.

Podziel pracę na etapy:

### Etap 1 – analiza

Przeanalizuj projekt i przedstaw raport bez modyfikowania plików.

### Etap 2 – standard treści i komponenty

Zaproponuj docelowy model sekcji lekcji oraz wspólne komponenty wizualne.

### Etap 3 – wzorcowa lekcja

Wybierz jedną reprezentatywną lekcję i przerób ją w całości:

- teoria;
- przykłady;
- układ wizualny;
- zadania;
- quiz;
- polskie znaki;
- zgodność merytoryczna.

Najlepiej wybierz lekcję zawierającą definicje, analogię, kod, praktyczne zastosowanie oraz
pułapki, aby można było sprawdzić wszystkie typy nowych sekcji.

Po wykonaniu wzorcowej lekcji zatrzymaj się i przedstaw rezultat do zatwierdzenia. Nie przerabiaj
jeszcze całego kursu.

### Etap 4 – migracja rozdziałami

Po zatwierdzeniu wzorca przerabiaj kurs rozdziałami albo małymi paczkami lekcji.

Po każdej paczce wykonaj:

- kompilację backendu;
- budowanie frontendu;
- dostępne testy;
- kontrolę działania aplikacji;
- przegląd zmienionych lekcji;
- weryfikację kodu w przykładach;
- kontrolę polskich znaków;
- kontrolę zgodności teorii z zadaniami i quizami.

### Etap 5 – audyt całości

Na końcu sprawdź cały kurs pod względem:

- spójności wizualnej;
- powtarzających się treści;
- brakujących zagadnień;
- błędów technicznych;
- polskich znaków;
- uszkodzonych fragmentów kodu;
- nieprawidłowych odpowiedzi quizowych;
- niewyjaśnionych pojęć wymaganych przez zadania;
- pozostałości oznaczenia „lekcja gotowa”.

## Ochrona istniejącej funkcjonalności

Nie zmieniaj bez potrzeby:

- mechanizmu uruchamiania kodu;
- logiki sprawdzania zadań;
- zapisywania postępu;
- routingu;
- API;
- struktury bazy danych;
- zależności projektu;
- konfiguracji środowiska;
- działających testów.

Jeśli zmiana modelu danych lekcji okaże się konieczna, najpierw wyjaśnij:

- dlaczego jest potrzebna;
- jakie pliki obejmie;
- jak zostaną przeniesione istniejące dane;
- jak unikniesz utraty treści;
- jak sprawdzisz poprawność migracji.

Nie wykonuj dużej automatycznej migracji bez wcześniejszego przygotowania i sprawdzenia
wzorcowej lekcji.

## Raportowanie postępu

Po każdym etapie podaj dokładnie:

- co zostało przeanalizowane;
- co zostało zmienione;
- jakie pliki zmodyfikowano;
- co zostało zweryfikowane;
- jakie polecenia uruchomiono;
- które testy przeszły;
- których testów nie udało się wykonać;
- jakie problemy nadal pozostają;
- jaki powinien być następny krok.

Nie pisz, że zadanie jest zakończone, jeżeli wykonano wyłącznie edycję plików bez uruchomienia
testów lub sprawdzenia działania aplikacji.

## Zasady commitowania

Po każdym ukończonym rozdziale (w ramach Etapu 4 – migracja rozdziałami) wykonaj lokalny commit
git obejmujący zmiany tego rozdziału. Commit ma zawierać wyłącznie zwięzły, opisowy komunikat po
polsku (co zmieniono, w którym rozdziale) — **bez żadnej stopki o współautorstwie, bez
`Co-Authored-By`, bez linku do sesji Claude**. Chcę móc sam zdecydować, kiedy i co wypycham
(`git push`) — nie wykonuj `git push` samodzielnie, zatrzymaj się na lokalnym commicie.

Rozpocznij teraz wyłącznie od etapu 1: przeanalizuj projekt i przygotuj raport oraz plan. Na tym
etapie nie modyfikuj żadnych plików.

---

## Pamięć projektu i możliwość kontynuowania pracy

Po wstępnej analizie uporządkuj i skróć CLAUDE.md. Zapisz w nim wyłącznie trwałe informacje o
projekcie, architekturze, zasadach pracy, uruchamianiu i weryfikacji.

Utwórz osobny plik WORK_PROGRESS.md, zawierający:

- aktualny etap prac;
- ostatnią ukończoną czynność;
- zmienione pliki;
- wyniki testów;
- problemy i decyzje;
- dokładny następny krok.

Aktualizuj ten plik po każdym etapie i przed zakończeniem sesji. Gdy użytkownik w nowej sesji
napisze „kontynuuj”, najpierw przeczytaj CLAUDE.md i WORK_PROGRESS.md, sprawdź aktualny stan
plików oraz zmiany w Git, a następnie wznów pracę od zapisanego punktu. Nie rozpoczynaj projektu
od początku i nie powtarzaj ukończonych etapów.

`WORK_PROGRESS.md` ma leżeć w katalogu głównym repozytorium i być JEDYNYM plikiem, który trzeba
otworzyć, żeby jednym rzutem oka sprawdzić, na jakim etapie stoi praca — bez przeszukiwania historii
rozmowy ani commitów. Trzymaj go zwięzłym i aktualnym (kilkanaście-kilkadziesiąt linii, nie
rozrastający się dziennik) — nadpisuj nieaktualne sekcje, zamiast dopisywać kolejne wpisy pod spodem.
