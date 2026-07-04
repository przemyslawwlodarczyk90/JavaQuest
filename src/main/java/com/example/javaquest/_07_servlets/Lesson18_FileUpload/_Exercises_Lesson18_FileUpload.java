package com.example.javaquest._07_servlets.Lesson18_FileUpload;

public class _Exercises_Lesson18_FileUpload {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicSingleFileUpload {
        /*
         * 🧪 Zadanie 1:
         * Skonfiguruj serwlet "/upload" z MultipartConfigElement (ustawionym na
         * Wrapperze PRZED tomcat.start()). Ręcznie zbuduj ciało multipart/form-data z
         * JEDNĄ częścią plikową "file" o nazwie "test.txt" i dowolnej treści tekstowej.
         * Wyślij POST /upload i zweryfikuj, że serwlet poprawnie odczytał nazwę pliku
         * (getSubmittedFileName()) i jego zawartość przez request.getPart("file").
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_UploadDifferentFileContent {
        /*
         * 🧪 Zadanie 2:
         * Ten sam serwlet co w Zadaniu 1, ale prześlij dłuższą, inną treść pliku i inną
         * nazwę pliku. Zweryfikuj, że filePart.getSize() zwraca dokładną liczbę bajtów
         * przesłanej treści oraz że odczytana treść zgadza się z oryginałem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_MultipleTextFieldsAndOneFile {
        /*
         * 🧪 Zadanie 3:
         * Zbuduj ciało multipart z DWIEMA częściami: zwykłym polem tekstowym
         * "description" (bez filename) oraz częścią plikową "file". Serwlet odczytuje
         * obie części przez request.getPart(...) i zwraca ich wartości w odpowiedzi.
         * Zweryfikuj obie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_UploadEmptyFile {
        /*
         * 🧪 Zadanie 4:
         * Wyślij część plikową o ZEROWEJ długości treści (pusta tablica bajtów). Serwlet
         * powinien zgłosić rozmiar 0 bez rzucania wyjątku. Zweryfikuj poprawne, bezpieczne
         * zachowanie dla pustego uploadu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_GetPartsIteratesAllParts {
        /*
         * 🧪 Zadanie 5:
         * Zbuduj ciało multipart z TRZEMA różnymi nazwanymi częściami. Serwlet używa
         * request.getParts() (iteracja po wszystkich częściach) i zwraca liczbę części
         * oraz listę ich nazw. Zweryfikuj, że liczba wynosi 3 i nazwy się zgadzają.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ContentTypeOfPart {
        /*
         * 🧪 Zadanie 6:
         * W ręcznie budowanym ciele multipart ustaw nagłówek części plikowej na
         * "Content-Type: application/json". Serwlet odczytuje part.getContentType() i
         * zwraca go w odpowiedzi. Zweryfikuj, że wartość to dokładnie
         * "application/json".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_MissingMultipartConfigElementError {
        /*
         * 🧪 Zadanie 7:
         * Zarejestruj DRUGI serwlet pod "/upload-broken" BEZ wywołania
         * wrapper.setMultipartConfigElement(...). Wyślij do niego poprawnie zbudowane
         * ciało multipart i przechwyć wyjątek rzucony przez request.getPart(...) w
         * serwlecie, zwracając status 500 z komunikatem wyjątku. Zweryfikuj, że pierwszy
         * (skonfigurowany) serwlet działa normalnie, a ten - nie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SubmittedFileNameVsPartName {
        /*
         * 🧪 Zadanie 8:
         * Zbuduj część plikową, gdzie NAZWA CZĘŚCI (name="file") różni się wyraźnie od
         * ORYGINALNEJ NAZWY PLIKU (filename="raport-koncowy.txt"). Serwlet zwraca OBIE
         * wartości osobno (part.getName() i part.getSubmittedFileName()) - zweryfikuj,
         * że są różne i poprawnie odczytane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_UploadBinaryLikeContent {
        /*
         * 🧪 Zadanie 9:
         * Zbuduj tablicę bajtów NIEBĘDĄCYCH tekstem (np. wartości 0-255 w pętli) jako
         * treść pliku, z Content-Type "application/octet-stream". Serwlet odczytuje
         * surowe bajty, zwraca ich długość oraz kilka pierwszych bajtów jako wartości
         * szesnastkowe - zweryfikuj brak uszkodzenia danych binarnych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TempDirectoryLocationConfigured {
        /*
         * 🧪 Zadanie 10:
         * Utwórz OSOBNY katalog tymczasowy (inny niż baseDir Tomcata) i przekaż jego
         * ścieżkę do konstruktora `new MultipartConfigElement(customTempDir)`. Wyślij
         * upload i zweryfikuj, że mimo innej lokalizacji katalogu tymczasowego upload
         * działa poprawnie.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_UploadWithSizeValidationServlet {
        /*
         * 🧪 Zadanie 11:
         * Serwlet ręcznie sprawdza filePart.getSize() przeciwko stałej maksymalnego
         * rozmiaru (np. 100 bajtów) i pisze niestandardową odpowiedź błędu, jeśli plik ją
         * przekracza. Wyślij mały plik (akceptowany, 200) i duży plik (odrzucony przez
         * serwlet) w dwóch osobnych zadaniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MultipartConfigElementSizeLimits {
        /*
         * 🧪 Zadanie 12:
         * Użyj przeciążonego konstruktora MultipartConfigElement(location, maxFileSize,
         * maxRequestSize, fileSizeThreshold) z niskim limitem maxFileSize. Wyślij plik
         * PRZEKRACZAJĄCY ten limit i przechwyć wyjątek rzucony przez sam Tomcat/Servlet
         * API, a następnie plik MIESZCZĄCY SIĘ w limicie, który powinien się udać.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_UploadThenForwardResultPage {
        /*
         * 🧪 Zadanie 13:
         * Po udanym uploadzie serwlet ustawia request.setAttribute("uploadedFileName",
         * ...) i wywołuje RequestDispatcher.forward() do drugiego serwletu
         * "potwierdzającego", który renderuje wynik na podstawie tego atrybutu.
         * Zweryfikuj finalną treść odpowiedzi pochodzącą z serwletu potwierdzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_UploadWithLoggingFilter {
        /*
         * 🧪 Zadanie 14:
         * Zarejestruj filtr (Lekcja 14) na "/*" mierzący czas obsługi całego zadania
         * uploadu (PRZED i PO chain.doFilter). Wyślij upload i zweryfikuj, że log czasu
         * filtra opakowuje log przetwarzania pliku przez serwlet.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MultipleFilesInOneRequest {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj ciało multipart z DWIEMA częściami plikowymi ("file1" i "file2"), każda
         * z własną nazwą i treścią. Serwlet iteruje getParts(), filtruje tylko te z
         * niepustym getSubmittedFileName() i zwraca liczbę plików oraz sumaryczny rozmiar
         * bajtów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_UploadWithFormFieldValidation {
        /*
         * 🧪 Zadanie 16:
         * Ciało multipart zawiera wymagane pole tekstowe "author" obok pliku. Serwlet
         * odrzuca (400) cały upload, gdy "author" jest puste/brak, w przeciwnym razie
         * przetwarza normalnie i dołącza autora do komunikatu sukcesu. Przetestuj oba
         * przypadki (z autorem i bez) w osobnych zadaniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ChecksumVerificationAfterUpload {
        /*
         * 🧪 Zadanie 17:
         * Klient oblicza prostą sumę kontrolną (np. XOR wszystkich bajtów) treści pliku
         * PRZED wysłaniem i dokleja ją jako dodatkową część tekstową "checksum". Serwlet
         * przelicza sumę z otrzymanych bajtów i porównuje z przesłaną wartością,
         * zwracając "MATCH" lub "MISMATCH".
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RejectDisallowedFileExtension {
        /*
         * 🧪 Zadanie 18:
         * Serwlet sprawdza rozszerzenie pliku z filePart.getSubmittedFileName() wobec
         * listy dozwolonych (np. tylko ".txt"). Wyślij plik z dozwolonym rozszerzeniem
         * (akceptowany) i plik z niedozwolonym rozszerzeniem, np. ".exe" (odrzucony
         * statusem 400), w dwóch osobnych zadaniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_UploadResponseAsSimpleJsonText {
        /*
         * 🧪 Zadanie 19:
         * Serwlet buduje ODPOWIEDŹ w formacie JSON RĘCZNIE (StringBuilder, bez Gson/
         * Jackson) z polami fileName, size i status. Klient odbiera surowy tekst
         * odpowiedzi i wypisuje go, weryfikując poprawny kształt tekstu JSON.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ConcurrentSequentialUploadsCounter {
        /*
         * 🧪 Zadanie 20:
         * Serwlet uploadu utrzymuje pole AtomicInteger "uploadCount" (instancyjne lub
         * statyczne). Wyślij 3 kolejne uploady (różne nazwy plików) do tego samego
         * serwletu. Osobny serwlet "/upload-count" zwraca finalną wartość licznika po
         * wszystkich trzech - zweryfikuj wartość 3.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_UploadThenListenerAudited {
        /*
         * 🧪 Zadanie 21:
         * Dodaj ServletContextListener (Lekcja 15) ustawiający w contextInitialized()
         * atrybut kontekstu "totalUploads" jako AtomicInteger(0). Serwlet uploadu
         * inkrementuje go przy każdym udanym uploadzie. Osobny serwlet "/upload-stats"
         * zwraca bieżącą sumę po kilku uploadach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MultipartWithMixedFieldsFullExtraction {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj realistyczne ciało multipart symulujące formularz profilu: jedna część
         * plikowa "avatar" oraz kilka pól tekstowych ("name", "email", "bio"). Serwlet
         * wyciąga WSZYSTKIE części do jednej struktury (np. Map<String,String> + osobno
         * bajty avatara) i wypisuje pełne podsumowanie w odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_AuthFilterProtectingUploadEndpoint {
        /*
         * 🧪 Zadanie 23:
         * Zarejestruj filtr autoryzacji (Lekcja 14) wymagający nagłówka "X-Api-Key"
         * PRZED dotarciem do serwletu "/upload" skonfigurowanego pod multipart. Wyślij
         * upload BEZ nagłówka (zablokowany 401, ciało NIGDY nie jest parsowane) oraz Z
         * poprawnym nagłówkiem (przetwarzany normalnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_UploadWithRedirectAfterSuccess {
        /*
         * 🧪 Zadanie 24:
         * Po udanym przetworzeniu pliku serwlet wywołuje sendRedirect("/upload-success")
         * (wzorzec Post/Redirect/Get z Lekcji 17) zamiast pisać potwierdzenie
         * bezpośrednio. Użyj klienta z followRedirects(NORMAL) i zweryfikuj finalną
         * treść strony potwierdzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ManualMultipartBuilderRefactoredAsUtility {
        /*
         * 🧪 Zadanie 25:
         * Wydziel logikę budowania ciała multipart (boundary, nagłówki, treść) do
         * uniwersalnej metody/klasy narzędziowej przyjmującej DOWOLNĄ listę pól (mix pól
         * tekstowych i plikowych), zamiast pojedynczego, sztywnego przypadku z lekcji.
         * Użyj jej do zbudowania i wysłania żądania z 4 różnymi częściami naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_LargeFileSimulatedInMemory {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj w pamięci umiarkowanie duży fragment bajtów (np. 50 000 bajtów
         * powtarzającego się wzorca, NIE z dysku) jako treść pliku. Wyślij upload i
         * zweryfikuj, że serwlet zwraca dokładnie ten sam rozmiar oraz - dla wyrywkowej
         * kontroli - sumę kontrolną fragmentu treści, potwierdzając brak obcięcia
         * danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_UploadEndpointWithFilterInjectedRequestId {
        /*
         * 🧪 Zadanie 27:
         * Filtr (Lekcja 14) generuje identyfikator zadania i ustawia go jako atrybut
         * request PRZED chain.doFilter() docierającym do serwletu uploadu. Serwlet
         * dołącza ten identyfikator do odpowiedzi - zweryfikuj, że atrybuty ustawione
         * przez filtr są widoczne również dla zadań POST typu multipart.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_RejectRequestMissingBoundaryMatch {
        /*
         * 🧪 Zadanie 28:
         * Celowo wyślij żądanie, w którym wartość boundary w nagłówku Content-Type NIE
         * ZGADZA SIĘ z granicą faktycznie użytą w ciele żądania. Przechwyć i zweryfikuj
         * wyjątek parsowania po stronie serwletu, a następnie porównaj z drugim,
         * poprawnie dopasowanym żądaniem, które kończy się sukcesem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullPipelineListenerFilterUploadForward {
        /*
         * 🧪 Zadanie 29:
         * Kapstone częściowy: połącz ServletContextListener (licznik łącznych bajtów
         * uploadu jako atrybut kontekstu), filtr autoryzacji chroniący "/upload", sam
         * serwlet uploadu (walidacja + przetwarzanie) oraz forward() do dedykowanego
         * serwletu potwierdzenia po sukcesie - wszystko w jednym cyklu zadanie-odpowiedź,
         * łącząc Lekcje 14+15+17+18.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneMultiFileUploadWithValidationAuditAndReport {
        /*
         * 🧪 Zadanie 30:
         * Kapstone: wyślij JEDNO żądanie zawierające 3 pliki o różnych rozszerzeniach i
         * rozmiarach oraz 2 pola tekstowe. Serwlet waliduje każdy plik osobno (lista
         * dozwolonych rozszerzeń + maksymalny rozmiar), aktualizuje licznik audytu
         * (atrybut kontekstu ustawiony przez listener) i zwraca jeden zbiorczy raport
         * tekstowy z wynikiem akceptacji/odrzucenia dla każdego pliku z osobna.
         */
        public static void main(String[] args) { }
    }
}
