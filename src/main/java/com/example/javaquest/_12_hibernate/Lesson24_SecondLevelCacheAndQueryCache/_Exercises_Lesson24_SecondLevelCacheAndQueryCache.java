package com.example.javaquest._12_hibernate.Lesson24_SecondLevelCacheAndQueryCache;

public class _Exercises_Lesson24_SecondLevelCacheAndQueryCache {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicL2CacheSetup {
        /*
         * 🧪 Zadanie 1:
         * Utworz encje Country (id, name, @Cacheable @Cache(usage=READ_WRITE)) na H2
         * ("jdbc:h2:mem:l24ex01;DB_CLOSE_DELAY=-1") z L2 cache skonfigurowanym jak w
         * lekcji (hibernate-jcache + Ehcache). Zapisz jeden kraj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_L2CacheHitAcrossSessions {
        /*
         * 🧪 Zadanie 2:
         * Wlacz hibernate.generate_statistics=true. Odczytaj Country w DWOCH roznych
         * Session (find po tym samym id) - sprawdz
         * statistics.getSecondLevelCacheHitCount() PRZED i PO drugim odczycie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_EntityWithoutCacheableAnnotation {
        /*
         * 🧪 Zadanie 3:
         * Utworz DRUGA encje BEZ @Cacheable (mimo wlaczonego L2 globalnie). Odczytaj
         * ja w DWOCH roznych Session i zweryfikuj (statystyki), ze L2 NIE zostal
         * uzyty (brak jawnej adnotacji = brak cache'owania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ReadOnlyCacheStrategy {
        /*
         * 🧪 Zadanie 4:
         * Zmien @Cache(usage=CacheConcurrencyStrategy.READ_ONLY) (zamiast READ_WRITE)
         * na encji, ktora NIGDY sie nie zmienia (np. Country). Zapisz w komentarzu,
         * dlaczego READ_ONLY jest szybszy niz READ_WRITE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_QueryCacheBasicSetup {
        /*
         * 🧪 Zadanie 5:
         * Wlacz hibernate.cache.use_query_cache=true. Wykonaj HQL "from Country" z
         * setCacheable(true) DWA razy w roznych Session - sprawdz
         * statistics.getQueryCacheHitCount().
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_QueryCacheRequiresExplicitSetCacheable {
        /*
         * 🧪 Zadanie 6:
         * Wykonaj TO SAMO zapytanie HQL BEZ setCacheable(true) DWA razy w roznych
         * Session - zweryfikuj, ze query cache NIE zostal uzyty mimo
         * use_query_cache=true globalnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_L2CacheUpdateInvalidation {
        /*
         * 🧪 Zadanie 7:
         * Odczytaj Country (trafia do L2), potem ZMIEN jego pole w NOWEJ Session i
         * zapisz. Odczytaj GO PONOWNIE w KOLEJNEJ, NOWEJ Session - zweryfikuj, ze
         * widac ZAKTUALIZOWANA wartosc (L2 zostal poprawnie uniewazniony po update).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_L2CacheAfterDelete {
        /*
         * 🧪 Zadanie 8:
         * Odczytaj Country (trafia do L2), usun go w NOWEJ Session. Sprobuj
         * odczytac go PONOWNIE w KOLEJNEJ Session - zweryfikuj, ze find() zwraca
         * null (L2 zostal poprawnie uniewazniony po delete).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareStatisticsWithAndWithoutCache {
        /*
         * 🧪 Zadanie 9:
         * Zbuduj DWIE SessionFactory - jedna z L2 wlaczonym, jedna BEZ - odczytaj TE
         * SAME dane 10 razy w roznych Session dla kazdej i porownaj
         * getEntityLoadCount() (liczba faktycznych ladowan z bazy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_MissingCacheProviderConfigurationError {
        /*
         * 🧪 Zadanie 10:
         * Sprobuj wlaczyc use_second_level_cache=true BEZ konfiguracji
         * region.factory_class/provider (brakujaca konfiguracja). Zapisz w komentarzu
         * blad, jaki Hibernate zwraca przy budowie SessionFactory.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_NonstrictReadWriteStrategy {
        /*
         * 🧪 Zadanie 11:
         * Uzyj @Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE). Zapisz w
         * komentarzu roznice wzgledem READ_WRITE (mniej scisla spojnosc, ale wyzsza
         * wydajnosc - odpowiednie dla danych, gdzie chwilowa niespojnosc jest
         * akceptowalna).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_L2CacheForAssociatedCollection {
        /*
         * 🧪 Zadanie 12:
         * Dodaj do Country @OneToMany List<City> z WLASNYM @Cache na KOLEKCJI
         * (osobna region cache dla kolekcji, nie tylko encji). Zweryfikuj (statystyki)
         * ze kolekcja rowniez trafia do L2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_QueryCacheInvalidationOnEntityChange {
        /*
         * 🧪 Zadanie 13:
         * Wykonaj cache'owane zapytanie HQL zwracajace liste Country. Dodaj NOWY
         * kraj i wykonaj TO SAMO zapytanie PONOWNIE - zweryfikuj, ze wynik
         * uwzglednia NOWY kraj (Hibernate automatycznie uniewaznia query cache przy
         * zmianie encji tego typu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SeparateCacheRegionsPerEntity {
        /*
         * 🧪 Zadanie 14:
         * Skonfiguruj DWIE rozne encje z L2 wlaczonym - kazda TRAFIA do INNEJ,
         * OSOBNEJ "region" cache (domyslnie region = pelna nazwa klasy). Sprawdz w
         * komentarzu, czy Ehcache faktycznie utworzyl dwie osobne "cache" wewnetrznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CacheEvictProgrammatically {
        /*
         * 🧪 Zadanie 15:
         * Uzyj sessionFactory.getCache().evict(Country.class, id) (JPA-owe API
         * cache'a) zeby RECZNIE usunac JEDEN wpis z L2 - zweryfikuj (statystyki), ze
         * kolejny odczyt SPOWODOWAL ponowne zapytanie do bazy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CacheEvictAllForEntityType {
        /*
         * 🧪 Zadanie 16:
         * Uzyj sessionFactory.getCache().evictEntityData(Country.class) - usun
         * WSZYSTKIE wpisy tego typu z L2 naraz. Zweryfikuj, ze KOLEJNY odczyt
         * KAZDEGO kraju wymaga ponownego zapytania do bazy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MeasurePerformanceGainFromL2 {
        /*
         * 🧪 Zadanie 17:
         * Zmierz czas 1000 odczytow TEGO SAMEGO Country w 1000 ROZNYCH Session: raz z
         * L2 WLACZONYM, raz z L2 WYLACZONYM - zapisz oba czasy w komentarzu i policz
         * przyspieszenie procentowe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_QueryCacheWithParameterVariations {
        /*
         * 🧪 Zadanie 18:
         * Wykonaj TO SAMO zapytanie HQL z RUZNYMI wartosciami parametru (np. rozne
         * kraje) - zapisz w komentarzu, ze KAZDA unikalna kombinacja
         * parametrow ma WLASNY wpis w query cache (nie jeden wspolny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_L2CacheWithFrequentlyChangingDataAntiPattern {
        /*
         * 🧪 Zadanie 19:
         * Wlacz L2 dla encji symulujacej dane CZESTO zmieniane (np. StockLevel
         * aktualizowany co sekunde w petli). Zapisz w komentarzu obserwacje - czy L2
         * w ogole POMAGA w takim scenariuszu, czy wrecz PRZESZKADZA (ciagle
         * uniewaznianie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareReadOnlyVsReadWritePerformance {
        /*
         * 🧪 Zadanie 20:
         * Zmierz czas 5000 odczytow encji z READ_ONLY vs 5000 odczytow TEJ SAMEJ
         * struktury z READ_WRITE - zapisz oba czasy w komentarzu (READ_ONLY powinien
         * byc szybszy, bo nie musi obslugiwac blokad wspolbieznosci).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullL2CacheStrategyPerEntityType {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj system z 3 roznymi encjami, KAZDA z INNA, uzasadniona strategia
         * cache'a: Country (READ_ONLY - nigdy sie nie zmienia), Product
         * (READ_WRITE - rzadkie zmiany ceny), StockLevel (BEZ L2 - czeste zmiany) -
         * napisz w komentarzu uzasadnienie KAZDEGO wyboru.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CacheWarmupStrategyOnStartup {
        /*
         * 🧪 Zadanie 22:
         * Napisz metode "warmUpCache(SessionFactory)" wywolywana ZARAZ po starcie,
         * ktora PROAKTYWNIE laduje wszystkie "rzadko zmieniane" encje do L2 (zamiast
         * czekac na pierwsze zadanie uzytkownika) - zmierz efekt na czas PIERWSZEGO
         * realnego zapytania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DistributedCacheConsiderationsDiscussion {
        /*
         * 🧪 Zadanie 23:
         * Bez terminala: napisz wyjasnienie (min. 4 zdania), dlaczego w systemie z
         * WIELOMA instancjami aplikacji (load balancer) L2 cache oparty na Ehcache
         * "in-process" (jak w tej lekcji) NIE wystarczy - potrzebny byłby cache
         * ROZPROSZONY (np. Hazelcast, Redis jako L2 provider).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_QueryCacheWithComplexJoinQueries {
        /*
         * 🧪 Zadanie 24:
         * Wykonaj ZLOZONE zapytanie HQL (join fetch + group by, podglad z Lesson19)
         * z setCacheable(true) - zweryfikuj, czy query cache dziala TAKZE dla
         * zlozonych zapytan, nie tylko prostych "from X".
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MonitoringCacheHitRatioOverTime {
        /*
         * 🧪 Zadanie 25:
         * Napisz metode "printCacheHitRatio(Statistics)" obliczajaca PROCENT trafien
         * cache'a (hitCount / (hitCount + missCount)) - wywolaj ja PO serii operacji
         * i zinterpretuj wynik w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CacheSizeLimitConfiguration {
        /*
         * 🧪 Zadanie 26:
         * Skonfiguruj Ehcache z OGRANICZONYM rozmiarem cache (np. max 100 wpisow) -
         * zapisz 200 roznych encji i sprawdz w komentarzu zachowanie po
         * przekroczeniu limitu (eviction najstarszych wpisow, LRU).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CacheAsideVsReadThroughPatternDiscussion {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: napisz porownanie (min. 3 punkty) wzorca "cache-aside"
         * (aplikacja sama zarzadza cache'em) vs "read-through" (jak dziala L2
         * Hibernate - transparentnie dla kodu aplikacji) - zalety/wady kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FullPerformanceReportL2AndQueryCache {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj KOMPLETNY benchmark porownujacy 4 konfiguracje: bez cache, TYLKO
         * L2 encji, TYLKO query cache, OBA naraz - wypisz tabele z czasami i liczba
         * zapytan SQL dla kazdej konfiguracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullReportOnL2CacheDecisionFramework {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz "framework decyzyjny" (min. 5 punktow, w komentarzu)
         * pomagajacy zdecydowac, KTORE encje w REALNYM projekcie warto objac L2
         * cache - z pytaniami o czestotliwosc zmian, czestotliwosc odczytow, rozmiar
         * danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullSecondLevelCacheCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY system e-commerce (Country,
         * Product, Category) z: przemyslana strategia cache per encja (Zadanie 21),
         * cache warmup (Zadanie 22), query cache dla raportow (Zadanie 24), i
         * monitoring hit ratio (Zadanie 25) - zademonstruj pelny scenariusz z
         * podsumowaniem statystyk na koniec.
         */
        public static void main(String[] args) { }
    }
}
