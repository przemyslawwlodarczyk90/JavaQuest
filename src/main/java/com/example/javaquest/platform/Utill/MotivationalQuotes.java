package com.example.javaquest.platform.Utill;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/** Published Polish excerpts, checked against the linked editions on 2026-10-25.
 * No application-authored translations. Historical spelling follows the cited edition.
 * Verse line breaks are rendered as spaces; source metadata is retained for verification.
 * Only the excerpt and author are displayed in the learning dashboard.
 */
public final class MotivationalQuotes {
    public record Quote(int id, String text, String author, String sourceText,
                        String sourceUrl, String work, String location) {}
    private record Source(String author, String url, String work) {}
    private static final Source AURELIUS = new Source("Marek Aureliusz", "https://wolnelektury.pl/katalog/lektura/rozmyslania-marek-aureliusz.html", "Rozmyślania — opublikowany przekład Mariana Reitera, wydanie Wolnych Lektur");
    private static final Source FREDRO = new Source("Aleksander Fredro", "https://pl.wikisource.org/wiki/Zapiski_starucha", "Zapiski starucha — Dzieła, tom XI, Warszawa 1880; pisownia wydania");
    private static final Source ODA = new Source("Adam Mickiewicz", "https://wolnelektury.pl/katalog/lektura/oda-do-mlodosci.html", "Oda do młodości — wydanie Wolnych Lektur");
    private static final Source MLODZI = new Source("Adam Asnyk", "https://wolnelektury.pl/katalog/lektura/do-mlodych.html", "Do młodych — Poezye, tom 3, Warszawa 1898; wydanie Wolnych Lektur");
    private static final Source ZALE = new Source("Adam Asnyk", "https://wolnelektury.pl/katalog/lektura/daremne-zale.html", "Daremne żale — Poezye, tom 3, Warszawa 1898; wydanie Wolnych Lektur");
    private static final Source ZDANIA = new Source("Adam Mickiewicz", "https://pl.wikisource.org/wiki/Zdania_i_uwagi", "Zdania i uwagi z dzieł Jakóba Bema, Anioła Ślązaka i Sę-Martena — przekład/adaptacja Adama Mickiewicza, Poezje, tom I, Lwów 1929");

    public static final List<Quote> ALL = List.of(
        q(1, AURELIUS, "Kochaj sztukę, której się nauczyłeś, i w niej znajdź spokój.", "Księga IV, 31"),
        q(2, AURELIUS, "Ćwicz władzę wnioskowania.", "Księga III, 9"),
        q(3, AURELIUS, "Nie działaj nigdy pod przymusem ani wbrew dobru publicznemu, ani bez rozwagi, ani w wewnętrznej rozterce.", "Księga III, 5"),
        q(4, AURELIUS, "Nadto bądź ducha pogodnego i nie miej potrzeby pomocy zewnętrznej, i nie pożądaj spokoju takiego, którego inni użyczają.", "Księga III, 5"),
        q(5, AURELIUS, "Nie czyń nic bez rozwagi, lecz tylko według zasady dobrze obmyślanej.", "Księga IV, 2"),
        q(6, AURELIUS, "Jak najczęściej więc dostarczaj sam sobie tej ustroni i odświeżaj sam siebie.", "Księga IV, 3 — o wewnętrznym spokoju"),
        q(7, AURELIUS, "Dopóki żyjesz, dopóki można, bądź dobrym.", "Księga IV, 17"),
        q(8, AURELIUS, "Nie pojmuj spraw tak, jak je pojmuje człowiek zły lub jak on chce, byś ty sądził, lecz uważaj, jakim jest coś rzeczywiście.", "Księga IV, 11"),
        q(9, AURELIUS, "Korzystaj z teraźniejszości w sposób rozumny i słuszny. Bądź rozsądny w odpoczynku.", "Księga IV, 26"),
        q(10, AURELIUS, "Zawsze masz możność żyć szczęśliwie, jeśli pójdziesz dobrą drogą i zechcesz dobrze myśleć i czynić.", "Księga V, 34"),
        q(11, AURELIUS, "Jeżeli coś jest dla ciebie bardzo trudne, nie sądź, że to jest niemożliwe dla człowieka w ogóle.", "Księga VI, 19"),
        q(12, AURELIUS, "Nie wstydź się korzystać z obcej pomocy.", "Księga VII, 7"),
        q(13, AURELIUS, "Nie niepokój się o przyszłość. Staniesz bowiem tam, gdy będzie potrzeba, uzbrojony w ten sam rozum, którego pomocy zażywasz wobec wypadków bieżących.", "Księga VII, 8"),
        q(14, AURELIUS, "Pamiętaj, że jest cechą wolności zarówno zmienić zdanie, jak pójść za zdaniem człowieka prowadzącego na drogę prostą.", "Księga VIII, 16"),
        q(15, AURELIUS, "Zawsze i przy każdym — o ile to możliwe — spostrzeżeniu badaj je pod względem przyrodniczym, uczuciowym i logicznym.", "Księga VIII, 13"),
        q(16, AURELIUS, "Pracuj, ale nie czuj się z tego powodu nieszczęśliwym ani nie chciej, by się z tego powodu nad tobą litowano lub cię podziwiano.", "Księga IX, 12"),
        q(17, AURELIUS, "Nie przedtem zaczniesz innych uczyć pisania i czytania, zanim się sam w tym nie wyćwiczysz.", "Księga XI, 29"),
        q(18, AURELIUS, "Po co domysły, gdy możesz po rozwadze dojść do wniosku, jakie ma być twe działanie?", "Księga X, 12"),
        q(19, AURELIUS, "Nabądź teoretycznej znajomości, jak wszystko przechodzi w co innego, ciągle się tym zajmuj i ćwicz się w tej gałęzi wiedzy.", "Księga X, 11"),
        q(20, AURELIUS, "Nic się nikomu nie zdarza, do zniesienia czego nie byłby z natury przysposobiony.", "Księga V, 18"),
        q(21, AURELIUS, "Wypadki zewnętrzne ani troszeczkę nie dotykają duszy ani nie mają wstępu do duszy, ani nie mogą duszy zmienić, ani poruszyć.", "Księga V, 19"),
        q(22, AURELIUS, "Rozum bowiem zwraca i układa wszelką przeszkodę swej działalności w kierunku zamierzonym: czynnikiem dodatnim staje się czynnik ujemny, a toruje drogę to, co na drodze stało.", "Księga V, 20"),
        q(23, AURELIUS, "Nie bądź ani gadatliwym, ani nie zajmuj się od razu zbyt wielu sprawami.", "Księga III, 5"),
        q(24, AURELIUS, "A myśl niech będzie prosta, bez sztucznych dodatków.", "Księga III, 5"),
        q(25, AURELIUS, "Nie napełniaj sam siebie niepokojem, sam się poskrom!", "Księga IV, 26"),
        q(26, AURELIUS, "Spróbuj, jak ci się nada życie człowieka dobrego, zadowolonego z przeznaczenia i poprzestającego na prawej działalności własnej i życzliwym stosunku do innych.", "Księga IV, 25"),
        q(27, AURELIUS, "Nie błąkaj się tu i tam, lecz przy każdym postanowieniu myśl o tym, co jest sprawiedliwe, a w każdym spostrzeżeniu zachowuj zdolność pojmowania.", "Księga IV, 22"),
        q(28, AURELIUS, "Świat — to zmiana, życie — to wyobrażenie.", "Księga IV, 3"),
        q(29, AURELIUS, "A nic innego nie nazywam spokojem jak wewnętrzny ład.", "Księga IV, 3"),
        q(30, AURELIUS, "Jeżeli możesz, poucz. A jeżeli nie, pamiętaj, że na ten wypadek masz sobie dane pobłażanie.", "Księga IX, 11"),
        q(31, FREDRO, "Kto doznał, ten poznał.", "Zapisek 14"),
        q(32, FREDRO, "Krytyka mądra oświeca, głupia gasi.", "Zapisek 18"),
        q(33, FREDRO, "Bądź oszczędnym, abyś mógł być szczodrym.", "Zapisek 53"),
        q(34, FREDRO, "Od zrób do zrobię sto mil drogi.", "Zapisek 145"),
        q(35, FREDRO, "Niecierpliwość prędzéj spóźni niż przyspieszy.", "Zapisek 151"),
        q(36, FREDRO, "Do naprawy budynku trzeba pionu i dźwigni, topór nie wystarcza.", "Zapisek 160"),
        q(37, FREDRO, "Nie umieć niema grzechu, ale wielkim błędem udawać, że się umie, czego się nie umie.", "Zapisek 172"),
        q(38, FREDRO, "Jak się oglądniesz, zadziwisz się nieraz gdzieś zaszedł.", "Zapisek 286"),
        q(39, FREDRO, "Chcieć wszystko przewidzieć, to jest stać na miejscu; ale i stojąc można się przewrócić, idzie więc o to w którą pójść stronę.", "Zapisek 288"),
        q(40, FREDRO, "Nie wskazuj drogi jeżeli jej dobrze nie znasz bo zbałamucisz tylko.", "Zapisek 303"),
        q(41, ODA, "Młodości! dodaj mi skrzydła!", "wers 2"),
        q(42, ODA, "W szczęściu wszystkiego są wszystkich cele;", "wers 33"),
        q(43, ODA, "A ze słabością łamać uczmy się za młodu!", "wers 43"),
        q(44, ODA, "Tam sięgaj, gdzie wzrok nie sięga, Łam, czego rozum nie złamie!", "wersy 48–49"),
        q(45, ODA, "Zestrzelmy myśli w jedno ognisko, I w jedno ognisko duchy!", "wersy 54–55"),
        q(46, MLODZI, "Szukajcie prawdy jasnego płomienia, Szukajcie nowych, nieodkrytych dróg!", "wersy 1–2"),
        q(47, MLODZI, "Nieście więc wiedzy pochodnię na czele I nowy udział bierzcie w wieków dziele — Przyszłości podnoście gmach!", "wersy 13–15"),
        q(48, ZALE, "Trzeba z Żywymi naprzód iść, Po życie sięgać nowe:", "wersy 9–10"),
        q(49, ZDANIA, "W słowach tylko chęć widzim, w działaniu potęgę: Trudniej dzień dobrze przeżyć, niż napisać księgę.", "I, 8 — Słowo i czyn"),
        q(50, ZDANIA, "Prawy mędrzec cieszy się, jak pielgrzym ubogi, Gdy spotka na pustyni towarzysza drogi;", "II, 6 — Mędrzec, fragment")
    );

    private static Quote q(int id, Source source, String text, String location) {
        return new Quote(id, text, source.author(), text, source.url(), source.work(), location);
    }

    /** Uniform choice; excludes the last displayed quote, when provided by the client. */
    public static Quote randomExcluding(Integer previousId) {
        var candidates = ALL.stream().filter(q -> previousId == null || q.id() != previousId).toList();
        return candidates.get(ThreadLocalRandom.current().nextInt(candidates.size()));
    }

    private MotivationalQuotes() {}
}
