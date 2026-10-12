const fs = require('fs');
function revise(file, changes) {
  let text = fs.readFileSync(file, 'utf8').replace(/\r\n/g, '\n');
  for (const [old, replacement] of changes) {
    if (!text.includes(old)) throw Error(`Missing fragment in ${file}: ${old.slice(0,70)}`);
    text = text.replace(old, replacement);
  }
  fs.writeFileSync(file, text);
}
revise('scripts/content-migration/lx_practice/_lx_07_07.txt', [
  [`H: grep ' 500 ' | awk '{print $7}' | sort | uniq -c | sort -nr | head -n 5.
S:
$ head -n 1 ~/regex/access.log
192.168.56.20 - - [04/Oct/2026:09:12:01 +0000] "GET /api/produkty HTTP/1.1" 200 1532 "-" "curl/8.5.0"
$ grep ' 500 ' ~/regex/access.log | awk '{print $7}' | sort | uniq -c | sort -nr | head -n 5
      7 /api/zamowienia
      3 /api/koszyk
      1 /api/produkty/42`,
   `H: Dopasuj kod za cudzysłowem żądania; sama liczba 500 może wystąpić w innym polu.
S:
head -n 1 ~/regex/access.log
grep '\" 500 ' ~/regex/access.log | awk '{print $7}' | sort | uniq -c | sort -nr | head -n 5`],
  [`$ awk '{print $1}' ~/regex/access.log | sort | uniq -c | sort -nr | head -n 3
     41 192.168.56.20
     27 192.168.56.31
     12 10.0.0.7`,
   `awk '{print $1}' ~/regex/access.log | sort | uniq -c | sort -nr | head -n 3`]
]);
revise('scripts/content-migration/lx_practice/_lx_11_05.txt', [
  [`$ grep '\" 500 ' access.log | grep "/api/zamowienia" | wc -l
4`, `grep '\" 500 ' access.log | grep "/api/zamowienia" | wc -l`],
  [`P: Wyklucz z wyniku żądania z adresu monitoringu.
H: grep -v adres.
S:
$ grep '\" 500 ' access.log | grep -v "^10.0.0.7 " | wc -l
4`,
   `P: Wyklucz z błędów 500 żądania klienta 10.0.0.5 i policz pozostałe.
H: Zakotwicz adres na początku; kropki muszą być literalne, a spacja zamyka pierwsze pole.
S:
grep '\" 500 ' access.log | grep -v '^10[.]0[.]0[.]5 ' | wc -l`],
  [`H: awk '{print $7}' | sort | uniq -c | sort -nr | head -n 3.
S:
$ awk '{print $7}' access.log | sort | uniq -c | sort -nr | head -n 3
     18 /api/produkty
     11 /api/zamowienia
      8 /api/koszyk`,
   `H: Usuń parametry zapytania z pola 7 przed grupowaniem; przy równych liczbach ranking porządkuje także tekst.
S:
awk '{sciezka=$7; sub(/\\?.*/, "", sciezka); print sciezka}' access.log | sort | uniq -c | sort -nr | head -n 3`],
  [`$ grep '\" 404 ' access.log | awk '{print $7}' | sort -u
/admin
/favicon.ico
/wp-login.php`, `grep '\" 404 ' access.log | awk '{print $7}' | sort -u`],
  [`Q: Ile błędów 500 dotyczyło /api/zamowienia w lekcji?
+ 4
- 6
- 7
- 0
E: Wynik ćwiczenia.`,
   `Q: Co liczy grep '\" 500 ' access.log | grep "/api/zamowienia" | wc -l?
+ Żądania z kodem 500, których wiersz zawiera /api/zamowienia
- Wszystkie żądania z kodem 500 niezależnie od ścieżki
- Wyłącznie poprawne odpowiedzi dla /api/zamowienia
- Liczbę unikalnych adresów klientów
E: Oba filtry muszą dopasować ten sam wiersz; sama ścieżka nie wskazuje kodu odpowiedzi.`],
  [`Q: Jak wykluczyć żądania z adresu monitoringu 10.0.0.7?
+ grep -v "^10.0.0.7 "
- grep -v 10
- grep -x 10.0.0.7
- grep -c 10.0.0.7
E: Kotwica i spacja.`,
   `Q: Jak wykluczyć żądania klienta o dokładnym adresie 10.0.0.5 w pierwszym polu?
+ grep -v '^10[.]0[.]0[.]5 '
- grep -v 10
- grep -x '10[.]0[.]0[.]5'
- grep -c '10[.]0[.]0[.]5'
E: Kotwica ustala początek wiersza, [.] oznacza literalną kropkę, spacja zamyka pole, a -v odwraca dopasowanie.`],
  [`Q: Dlaczego we wzorcu "^10.0.0.7 " jest spacja na końcu?
+ Żeby nie wykluczyć adresów takich jak 10.0.0.70
- Bo spacja jest wymagana
- Bez powodu
- Żeby ignorować kropki
E: Granica pola.`,
   `Q: Dlaczego wzorzec '^10[.]0[.]0[.]5 ' kończy się spacją?
+ Żeby zamknąć pole adresu i nie dopasować 10.0.0.50
- Żeby usunąć wszystkie znaki białe z wyniku
- Żeby kropki oznaczały dowolny znak
- Żeby dopasować wyłącznie puste wiersze
E: Spacja po adresie jest granicą pierwszego pola w tym formacie logu.`],
  [`Q: Która ścieżka była najczęściej żądana w access.log z lekcji?
+ /api/produkty
- /api/zamowienia
- /api/koszyk
- /admin
E: Ranking.`,
   `Q: Jak wygląda czołówka raportu 13/10 po usunięciu parametrów zapytań ze ścieżek?
+ /api/zamowienia, /api/produkty/1 i /api/produkty mają po 8 żądań
- /api/produkty ma samodzielnie 18 żądań
- /api/koszyk ma samodzielnie 11 żądań
- /admin ma samodzielnie 27 żądań
E: Trzy ścieżki są na czele z równą liczbą 8; raport grupuje URL bez części po znaku zapytania.`],
  [`Q: Jakie ścieżki dały 404 w access.log z lekcji?
+ /admin, /favicon.ico, /wp-login.php
- /api/produkty
- /api/zamowienia
- Żadne
E: Typowe skanowania.`,
   `Q: Co zwraca grep '\" 404 ' access.log | awk '{print $7}' | sort -u?
+ Unikalne wartości pola ścieżki z żądań zakończonych kodem 404
- Wszystkie ścieżki wraz z liczbami żądań
- Unikalne adresy IP wszystkich klientów
- Wyłącznie ścieżki z błędami 500
E: Pierwszy filtr wybiera kod, awk wybiera pole 7, a sort -u usuwa powtórzenia wartości.`]
]);
